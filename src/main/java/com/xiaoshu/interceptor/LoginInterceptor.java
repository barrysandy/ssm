package com.xiaoshu.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.tools.ToolsASCIIChang;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoshu.entity.User;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		// 请求的路径
		String url = request.getServletPath().toString();
		int contain = url.indexOf(com.xiaoshu.api.Set.INTERCEPTORURL);//匹配路径为接口
		int containNus = url.indexOf(com.xiaoshu.api.Set.INTERCEPTOR_NOTNEED_URL);//匹配路径为不需要Session中的user
		int containUser = url.indexOf(com.xiaoshu.api.Set.INTERCEPTOR_USER_URL);//匹配路径为需要Session中的user
		if(contain != -1 ){
			//请求url地址是接口
			return true;
		} else if(containNus != -1 ){
			//请求url地址不需要user
			return true;
		} else if(containUser != -1 ){//需要session 中有user
			//请求url地址需要user
			//从SESSION获取对象
            FocusedUserInfo user = (FocusedUserInfo) request.getSession().getAttribute("user");
			String servletPath = request.getServletPath();
			if(user != null){
				//filterChain.doFilter(request, response);//请求：我自己定义请求，例外就是页面JS,JSP,HTML
				return true;
			}else{
				StringBuffer sb = new StringBuffer("?");
				//获取请求的所有参数map
				Map<String,String[]> map = request.getParameterMap();
				//参数名称
				Set<String> key = map.keySet();
				//参数迭代器
				Iterator<String> iterator = key.iterator();
				while(iterator.hasNext()){
					String param = iterator.next();
					sb.append(param);
					sb.append("=");
					sb.append(map.get(param)[0]);
					if(iterator.hasNext()){
						sb.append("&");
					}
				}
				//前往微信授权登录
				String basePath = "/oauth2/oauth2ToLoginNoUser";// OauthController 下的oauth2/oauth2ToLoginNoUser
				String urls = (servletPath + sb.toString()).substring(1);// 获取拦截请求 + 拦截的请求参数并去掉拦截地址的前一个斜杠（/）
                request.setAttribute("urls",urls);
				urls = ToolsASCIIChang.stringToAscii(urls);
                request.getRequestDispatcher(basePath+"?url="+urls).forward(request, response);
				return true;
			}
		}
		else{
			//请求url地址需要currentUser(管理后台的session为currentUser)
			HttpSession session = request.getSession();
			User currentUser = ((User) session.getAttribute("currentUser"));
			if (currentUser == null) {
				if (url.contains("login.htm") || url.contains("auto.htm")) {
					return true;
				}else{
					Cookie[] cookies = request.getCookies();
					if(cookies != null){
						for(int i=0; i<cookies.length; i++) {
							Cookie cookie = cookies[i];
							if("autoLogin".equals(cookie.getName())){
								response.sendRedirect("auto.htm");
								return true;
							}
						}
					}
				}
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return true;
			}
			return true;
		}
	}
}
