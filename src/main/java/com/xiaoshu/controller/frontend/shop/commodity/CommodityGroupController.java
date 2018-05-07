package com.xiaoshu.controller.frontend.shop.commodity;

import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.CommodityPriceList;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.WechatActivity;
import com.xiaoshu.service.CommodityPriceListService;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.SellerService;
import com.xiaoshu.service.WechatActivityService;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsPage;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.vo.CommodityVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 最新活动列表页面模块
 * @name: ShopController
 * @author: XGB
 * @date: 2018-04-11 15:42
 */
@Controller
@RequestMapping(value = "/commodityGroup")
public class CommodityGroupController extends BaseController {

	@Resource private CommodityService commodityService;
	@Resource private WechatActivityService wechatActivityService;


	/**
	 * 最新活动首页
	 * @param menuId 公众号标识
	 * @return String
	 * @author XGB
	 * @date 2018-04-04 15:10
	 */
	@RequestMapping("/listInUser")
	public ModelAndView listInUser(HttpServletRequest request,String menuId, Integer typese,Integer cpage){

		FocusedUserInfo user = (FocusedUserInfo) request.getSession().getAttribute("user");
		if(user != null){
			if(cpage <= 0){ cpage = 1; }
			Integer pageSize = 10;
			List<Commodity> list;
			List<CommodityVo> listCommodityVo = new ArrayList<CommodityVo>();
			try{
				int tatal = commodityService.countNewList(typese,-1,1);//售卖中的状态
				//TODO 组团商品展示
				int totalPage = ToolsPage.totalPage(tatal, pageSize);//总页数
				int index = (cpage - 1) * pageSize;
				list = commodityService.getNewList(index,pageSize,typese,-1,1);

				//TODO 组装VO类
				listCommodityVo = getListVo(list,listCommodityVo, menuId,wechatActivityService,0);


				request.setAttribute("list",listCommodityVo);
				request.setAttribute("size",list.size());
				request.setAttribute("menuId",menuId);
				request.setAttribute("typese",typese);
				request.setAttribute("cpage",cpage);
				request.setAttribute("totalPage",totalPage);

			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return toVm("mobile/mall/group/list");
	}



	/**
	 * 最新活动首页数据加载
	 * @param menuId 公众号标识
	 * @return String
	 * @author XGB
	 * @date 2018-04-20 10:28
	 */
	@RequestMapping("/listInUserJson")
	@ResponseBody
	public String listInUserJson(HttpServletRequest request, HttpServletResponse response, String menuId, Integer typese, Integer cpage){

		FocusedUserInfo user = (FocusedUserInfo) request.getSession().getAttribute("user");
		if(user != null){
			if(cpage <= 0){ cpage = 1; }
			Integer pageSize = 10;
			List<Commodity> list ;
			List<CommodityVo> listCommodityVo = new ArrayList<CommodityVo>();
			try{
				//TODO 组团商品展示
				int index = (cpage - 1) * pageSize;
				list = commodityService.getNewList(index,pageSize,typese,-1,1);
				//TODO 组装VO类
				listCommodityVo = getListVo(list,listCommodityVo, menuId,wechatActivityService,1);
				String json = JSONUtils.toJSONString(listCommodityVo);
				response.getWriter().print(json);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return null;
	}



	private static List<CommodityVo> getListVo(List<Commodity> list,List<CommodityVo> listCommodityVo,String menuId,WechatActivityService wechatActivityService,Integer type){
		try{
			if(list != null){
				if(list.size() > 0){
					Iterator<Commodity> iterator = list.iterator();
					while (iterator.hasNext()){
						Commodity commodity = iterator.next();
						if(commodity != null){
							CommodityVo commodityVo = new CommodityVo(commodity.getId(), commodity.getCommodityName(), String.valueOf(commodity.getCommodityPrice()),
									commodity.getTimeStatus(), commodity.getTypese(), "", "",commodity.getImage(),commodity.getCreateGroupNumber(),commodity.getWechatActivityId());
							String url = "";
							if(commodity.getTypese() == 0){
								url = "JavaScript:;" ;
								if(commodity.getWechatActivityId() != null){
									if(!"".equals(commodity.getWechatActivityId())){
										String activityUrl = wechatActivityService.getUrlById(commodity.getWechatActivityId()) ;
										if(activityUrl != null){
											if(!"".equals(activityUrl)){
												url = activityUrl;
											}
										}
									}
								}
							}else if(commodity.getTypese() == 1){
								url = Set.SYSTEM_URL + "shop/activityCommodityNoUser?menuId=" + menuId + "&id=" + commodity.getId() + "&from=singlemessage&isappinstalled=0" ;
							}else if(commodity.getTypese() == 2){
								url = Set.SYSTEM_URL + "shop/groupCommodityDetailsInUser?menuId=" + menuId + "&id="  + commodity.getId() + "&shareUserId=1&shareGroupId=1&from=singlemessage&isappinstalled=0" ;
							}
//							url = URLEncoder.encode(url,"utf-8");
							commodityVo.setUrl(url);
							listCommodityVo.add(commodityVo);
						}
					}
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return listCommodityVo;
	}

}
