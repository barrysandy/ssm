package com.xiaoshu.controller.wechat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.xiaoshu.api.Set;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.service.FocusedUserInfoServiceImpl;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.api.WeChatAPI;
import com.xiaoshu.wechat.pojo.WeixinOauth2Token;
import com.xiaoshu.wechat.tools.AdvancedUtil;
import com.xiaoshu.wechat.tools.WeChatSaveAndUpdateUserMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/oauth2")

/**
 * 微信网页oauth2.0授权
 * @name: OauthController
 * @author: XGB
 * @date: 2018-01-30 9:44
 */

public class OauthController {
	
	@Resource private FocusedUserInfoServiceImpl focusedUserInfoServiceImpl;


	/**
	 * 微信oauth2.0授权登录（非前后端分离方式授权登录），拉去用户信息，并将拉的信息存储入库 (未关用户无法获得其他信息)
     * URL（oauth2/oauthinterface?url=URL）
	 * @param code 获取授权申请的code 自动发送参数，用户不用管
	 * @param url 授权成功跳转url
	 * @author: XGB
	 * @date: 2018-01-30 9:45
	 */
	@RequestMapping("oauthinterface")
	public String oauth(HttpServletRequest req,@RequestParam("code") String code,@RequestParam("url") String url){
		try{
            if(url != null){
                if(!"".equals(url)){
                    url = ToolsASCIIChang.asciiToString(url);
                    String [] strArray = url.split("menuId=");//截取url中的menuId
                    String menuId = strArray[1];//获取menuId
                    //menuId 去掉后面的参数
                    String[] menuIds = menuId.split("&");
                    if(menuIds.length > 0){
                        menuId = menuIds[0];
                    }
                    System.out.println("------------ [out] menuId strArray :" + menuId + " ------------");
                    FocusedUserInfo useroauth = null;//通过网页授权获取用户信息
                    if (!"authdeny".equals(code)) {//用户同意授权
                        //读取Map中的token
                        MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
                        Map<String, String> map = mapPublicNumber.getMap();
                        if(map != null && map.size() == 0){
                            System.out.println("map not data");
                            JobPublicAccount.ToRefreshMapJobPublicAccount();
                        }
                        String appId = map.get("appId" + menuId);
                        String appSecret = map.get("appSecret" + menuId);
                        String accessToken = map.get("accessToken" + menuId);
                        String openPlatform = map.get("openPlatform" + menuId);
                        // 获取网页授权access_token
                        WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(appId, appSecret, code);
                        // 网页授权接口访问凭证
                        if(weixinOauth2Token != null){//获取授权成功
                            String accessTokenOauth = weixinOauth2Token.getAccess_token();
                            // 用户标识
                            String openId = weixinOauth2Token.getOpenId();
                            // 获取用户信息
                            useroauth = AdvancedUtil.getSNSUserInfo(accessTokenOauth, openId);
                            if(openPlatform != null){
                                if(!"".equals(openPlatform)){
                                    // 获取用户unionid
                                    String unionid = AdvancedUtil.getUnionidUserInfo(accessToken, openId);
                                    useroauth.setUnionid(unionid);
                                }
                            }
                            //获取openid
                            String openid = useroauth.getOpenid();
                            //对用户进行处理（保存/更新）
                            WeChatSaveAndUpdateUserMsg.handleUser(openid,menuId);
                            FocusedUserInfo userNew = focusedUserInfoServiceImpl.selectByOpenid(openid);
                            req.getSession().removeAttribute("user");
                            req.getSession().setAttribute("user", userNew);
                        }
                        else if(weixinOauth2Token == null){//获取授权失败
                            return "redirect:/"+url;
                        }
                    }
                }
            }
        }catch (Exception e){
		    e.printStackTrace();
        }
		return "redirect:/"+url;
	}

    /**
     * 微信oauth2.0预授权页面
     * URL（oauth2/oauth2ToLoginNoUser?url=URL）
     * @param url 授权成功跳转url
     * @author: XGB
     * @date: 2018-01-30 9:45
     */
	@RequestMapping("oauth2ToLoginNoUser")
	public String oauth2ToLoginNoUser(HttpServletRequest req, @RequestParam("url") String url){
	    String urlRequest = (String) req.getAttribute("urls");
	    String returnRrl = "oauth2/Oauth2";
		try{
            if(url != null){
                if(!"".equals(url)){
                    url = ToolsASCIIChang.asciiToString(url);
                    String [] strArray = url.split("menuId=");//截取url中的menuId
                    String menuId = strArray[1];//获取menuId
                    //menuId 去掉后面的参数
                    String[] menuIds = menuId.split("&");
                    if(menuIds.length > 0){
                        menuId = menuIds[0];
                    }
                    //读取Map中的token
                    MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
                    Map<String, String> map = mapPublicNumber.getMap();
                    if(map != null && map.size() == 0){
                        System.out.println("------------ [out] map not data ------------");
                        JobPublicAccount.ToRefreshMapJobPublicAccount();
                    }
                    String appId = map.get("appId" + menuId);
                    System.out.println("------------ [out] WeChat Oauth2.0 Preauthorized Page appId:" + appId + " ------------");
                    if(appId != null){
                        urlRequest = ToolsASCIIChang.stringToAscii(urlRequest);
                        String redirect_uri = Set.SYSTEM_URL + "oauth2/oauthinterface?url="+urlRequest;
                        String urls = WeChatAPI.Oauth2WeChat.replaceAll("APPID",appId).replaceAll("REDIRECT_URI",redirect_uri);
                        req.setAttribute("url", urls);
                        req.setAttribute("urlRequest", urlRequest);
                        return returnRrl;
                    }
                }
            }
        }catch (Exception e){
		    e.printStackTrace();
        }
        return returnRrl;
	}
}
