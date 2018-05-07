package com.xiaoshu.controller.frontend.apiInterface;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.vo.CommodityVoQRCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用接口API
 * @name: ApiInterfaceController
 * @author: XGB
 * @date: 2018-04-24
 */
@Controller
@RequestMapping(value = "/apiInterface")
public class ApiInterfaceController extends BaseController {


	@Resource private FocusedUserInfoService focusedUserInfoService;
	@Resource private PublicAccountInfoService publicAccountInfoService;
	@Resource private WechatActivityService wechatActivityService;
	@Resource private CommodityService commodityService;
	@Resource private CommodityGroupService commodityGroupService;


	/**
	 * apiInterface/interfaceGetUserInfo?openid=
	 * openid 获取用户信息JSON
	 * @return String
	 * @author XGB
	 * @date 2018-03-28 17:19
	 */
	@RequestMapping("/interfaceGetUserInfo")
	public String interfaceGetUserInfo(HttpServletRequest request, HttpServletResponse response, String openid){
		try{
			response.setCharacterEncoding("UTF-8");
			FocusedUserInfo focusedUserInfo =  focusedUserInfoService.selectByOpenid(openid);
			request.setAttribute("bean", focusedUserInfo);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "admin/focusUser/focusedUserInfo_view";
	}


	/**
	 * apiInterface/interfaceGetActivityUrl?id=
	 * id 商品id
	 * @return String
	 * @author XGB
	 * @date 2018-04-20 14:11
	 */
	@RequestMapping("/interfaceGetActivityUrl")
	@ResponseBody
	public String interfaceGetActivityUrl( HttpServletResponse response, Integer id){
		try{
			response.setCharacterEncoding("UTF-8");
			String wechatActivityId = commodityService.getWechatActivityIdById(id);
			if(wechatActivityId != null){
				String url =  wechatActivityService.getUrlById(wechatActivityId);
				if(url != null){
					return url ;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * apiInterface/interfaceGetUserInfoByID?id=
	 * id 获取用户信息
	 * @return String
	 * @author XGB
	 * @date 2018-03-28 17:19
	 */
	@RequestMapping("/interfaceGetUserInfoByID")
	public String interfaceGetUserInfoByID(HttpServletRequest request, HttpServletResponse response, String id){
		try{
			response.setCharacterEncoding("UTF-8");
			FocusedUserInfo focusedUserInfo =  focusedUserInfoService.selectByPrimaryKey(id);
			request.setAttribute("bean", focusedUserInfo);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "admin/focusUser/focusedUserInfo_view";
	}


	/**
	 * apiInterface/interfaceGetUserInfoByOpenid?openid=
	 * openid 获取用户信息
	 * @return String
	 * @author XGB
	 * @date 2018-04-25 10:17
	 */
	@RequestMapping("/interfaceGetUserInfoByOpenid")
	@ResponseBody
	public String interfaceGetUserInfoByOpenid(HttpServletResponse response, String openid){
		try{
			response.setCharacterEncoding("UTF-8");
			FocusedUserInfo focusedUserInfo =  focusedUserInfoService.selectByOpenid(openid);
			if(focusedUserInfo != null){
				String json = ToolsString.getStrRemoveBracket(JSONUtils.toJSONString(focusedUserInfo));
				response.getWriter().print(json);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 判断用户是否关注另一个公众号
	 * URL1: Api.FOCUSED_USER_JUDGING_SUB
	 * URL2: apiInterface/interfaceJudgingSubscriptions?openid=&menuId=
	 */
	@RequestMapping("/interfaceJudgingSubscriptions")
	@ResponseBody
	public String interfaceJudgingSubscriptions(String openid,String menuId){
		String result = "0";
		try{
			//TODO 参数校验
			if(openid != null && menuId != null){
				if(!"".equals(openid) && !"".equals(menuId)){
					//TODO 判断用户是否存在
					FocusedUserInfo userInfo = focusedUserInfoService.selectByOpenid(openid);
					if(userInfo == null){
						return result;
					}
					//TODO 判断当前的menuId的公众号是否绑定了另一个公众号
					PublicAccountInfo publicAccountInfo = publicAccountInfoService.getByParentMenuId(menuId);
					if(publicAccountInfo != null){
						if(publicAccountInfo.getOpenPlatform() != null){
							if(!"".equals(publicAccountInfo.getOpenPlatform())){
								String otherParentMenuId = publicAccountInfoService.getParentMenuIdByOpenPlatform(publicAccountInfo.getOpenPlatform(),menuId);
								if(otherParentMenuId != null){
									if(!"".equals(otherParentMenuId)){
										//TODO 利用UNICODE机制判断当前用户是否在另一个公众号内有数据
										FocusedUserInfo otherUserInfo = focusedUserInfoService.getByUnionidAndParentMenuId(userInfo.getUnionid(),otherParentMenuId);
										if(otherUserInfo != null){
											result = "1";
										}
									}
								}
							}
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 用一个公众号标识换取另一个与之关联的标识
	 * URL1: Api.GET_OTHER_MENUID
	 * URL2: apiInterface/interfaceGetOtherMenuId?&menuId=
	 */
	@RequestMapping("/interfaceGetOtherMenuId")
	@ResponseBody
	public String interfaceGetOtherMenuId(String menuId){
		String result = "0";
		try{
			//TODO 参数校验
			if(menuId != null){
				if(!"".equals(menuId)){
					String wechatMenuId = publicAccountInfoService.getOtherParentMenuIdByMenuId(menuId);
					if(wechatMenuId != null){
						if(!"".equals(wechatMenuId)){
							return wechatMenuId;
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 用一个公众号标识换取另一个与之关联的标识
	 * URL1: Api.GET_COMMODITY_BY_ID
	 * URL2: apiInterface/interfaceGetCommodity?&id=
	 */
	@RequestMapping("/interfaceGetCommodity")
	@ResponseBody
	public String interfaceGetCommodity(int id){
		try{
			Commodity commodity = commodityService.findCommodityByIdService(id);

		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/interfaceGetGroupId")
	@ResponseBody
	public String interfaceGetGroupId(String groupCode){
		try{
			String id = commodityGroupService.geteIdByGroupCod(groupCode);
			if(id == null){
				if("".equals(id)){
					return "-1";
				}
				return "-1";
			}
			return id;
		}catch (Exception e){
			e.printStackTrace();
			return "-1";
		}
	}


	@RequestMapping("/interfaceGetCommdity")
	@ResponseBody
	public String interfaceGetCommdity(Integer id,String shareGroupId){
		System.out.println("===========================================");
		System.out.println("===========================================id " + id );
		System.out.println("===========================================shareGroupId " + shareGroupId );
		try{
			CommodityVoQRCode commodityVoQRCode = new CommodityVoQRCode();
			Commodity commodity = commodityService.getLestById(id);
			commodityVoQRCode.setCid(id);
			commodityVoQRCode.setGid(shareGroupId);
			commodityVoQRCode.setImage(commodity.getShareImage());
			commodityVoQRCode.setDescM(ToolsASCIIChang.stringToAscii(commodity.getShareDescM()));
			commodityVoQRCode.setTitle(ToolsASCIIChang.stringToAscii(commodity.getShareTitle()));
			if(!"-1".equals(shareGroupId)){
				CommodityGroup commodityGroup = commodityGroupService.getById(shareGroupId);
				if(commodityGroup != null){
					int i = commodityGroup.getMaxPerson() - commodityGroup.getTotalPerson();
					commodityVoQRCode.setTitle(ToolsASCIIChang.stringToAscii("【还差" + i  + "人成团】" + commodity.getShareTitle()));
				}
			}
			if(commodityVoQRCode != null){
				return ToolsString.getStrRemoveBracket(JSONUtils.toJSONString(commodityVoQRCode));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



}
