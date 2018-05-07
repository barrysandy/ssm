package com.xiaoshu.controller.frontend.shop;

import com.xgb.springrabbitmq.dto.DtoMessage;
import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.api.Api;
import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.enumeration.EnumsMQName;
import com.xiaoshu.po.UserClickGroup;
import com.xiaoshu.po.UserClickGroupMap;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 商城模块
 * @name: ShopController
 * @author: XGB
 * @date: 2018-03-11 8:01
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopGroupController extends BaseController {

	@Resource private OrderService orderService;
	@Resource private CommodityService commodityService;
	@Resource private CommodityPriceListService commodityPriceListService;
	@Resource private CommodityGroupService commodityGroupService;
	@Resource private CommodityGroupMemberService commodityGroupMemberService;
	@Resource private PublicAccountInfoService publicAccountInfoService;

	@Autowired private DeadLetterPublishService deadLetterPublishService;

	/** 组团详情页面地址 */
	private static String GROUP_URL = "mobile/mall/group/activityCommodityDetailsGroup";

	/**
	 * 商品购买详情 - - 组团商品
	 * @return String
	 * @author XGB
	 * @date 2018-4-12 16:50
	 */
	@RequestMapping("/groupCommodityDetailsInUser")
	public ModelAndView groupCommodityDetailsInUser(HttpServletRequest request, String menuId, int id, String shareUserId,String shareGroupId ,String from, String isappinstalled){
		//常亮设置
		String view = GROUP_URL;
		String subscribe = "0";//判断用户是否关注另一个公众号
		String buy ="1";//能否购买 1能购买 0不能购买
        Integer leader = 0; //是否为团长 1为团长
        Integer groupState = 0; //组团状态 1为组团成功
		Integer groupFail = 0; //组团是否失败 0正常状态，时间还未到或者组团已经成功了 1状态失败
		Integer needNumber = 0; //组团差多少人
		Integer countDown = 0; //倒计时
		Integer status = 0;//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
		Integer hiddenSet = 5; //设置隐藏的节点 多人人开始隐藏
		Integer hiddenTotal = 0;//隐藏的总人数
		Integer totalMember = 0;//总人数
		CommodityGroup commodityGroup = null;
		String qrCode = Set.SYSTEM_URL +  "resources/img/QRCode/qrcode001.jpg";//二维码地址
		if(from == null){from = "singlemessage";}
		if(isappinstalled == null){isappinstalled = "0";}
	    FocusedUserInfo user = (FocusedUserInfo) request.getSession().getAttribute("user");
		if(user != null){
			try{
                List<CommodityGroupMember> listCommodityGroupMember = null;
                //Todo 判断当前用户是否已经建立团
                String groupId = null;
				CommodityGroupMember commodityGroupMember = commodityGroupMemberService.getByUserIdAndCId(id,user.getId());
                if(commodityGroupMember != null){
                    if(commodityGroupMember.getId() != null){
						groupId = commodityGroupMember.getGroupId();
						leader = commodityGroupMember.getLender();
						listCommodityGroupMember = commodityGroupMemberService.getListByGroupIdAndStatus(groupId,1);
						if(listCommodityGroupMember != null){
							if(listCommodityGroupMember.size() > 0) {
								commodityGroup = commodityGroupService.getById(groupId);
								groupState = commodityGroup.getStatus();
								needNumber = commodityGroup.getMaxPerson() - listCommodityGroupMember.size();
								//倒计时计算
								countDown = ToolsDate.getCountDown(commodityGroup.getGroupTime()); //倒计时
								System.out.println(countDown);
								request.setAttribute("countDown",countDown);

								//TODO status 状态 1
								if(leader == 1){
									//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
									status = 1;
								}
								//TODO status 状态 2
								else if(leader == 0){
									//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
									status = 2;
								}
							}
						}
                    }
                }

				//TODO 进入是否携带分享参数
				if(shareGroupId != null){
					if(!"".equals(shareGroupId) && !"1".equals(shareGroupId)){
						//进入的是分享链接
						if(!shareUserId.equals(user.getId()) ){
							//进入的是别人分享链接
							listCommodityGroupMember = commodityGroupMemberService.getListByGroupIdAndStatus(shareGroupId,1);
							if(listCommodityGroupMember.size() > 0) {
								commodityGroup = commodityGroupService.getById(shareGroupId);
								groupState = commodityGroup.getStatus();
								needNumber = commodityGroup.getMaxPerson() - listCommodityGroupMember.size();
								//倒计时计算
								countDown = ToolsDate.getCountDown(commodityGroup.getGroupTime()); //倒计时
								System.out.println(countDown);
								request.setAttribute("countDown",countDown);
							}
							CommodityGroupMember commodityGroupMember1 = commodityGroupMemberService.getByUserIdAndCId(id,user.getId());
							if(commodityGroupMember1 != null) {
								//TODO 自己建立的团与分享无关
								CommodityGroupMember commodityGroupMemberMy = commodityGroupMemberService.getByUserIdAndCId(id,user.getId());
								if(commodityGroupMemberMy != null) {
									if (commodityGroupMemberMy.getLender() == 1) {
										listCommodityGroupMember = commodityGroupMemberService.getListByGroupIdAndStatus(commodityGroupMemberMy.getGroupId(),1);
										if(listCommodityGroupMember != null) {
											if (listCommodityGroupMember.size() > 0) {
												commodityGroup = commodityGroupService.getById(shareGroupId);
												groupState = commodityGroup.getStatus();
												needNumber = commodityGroup.getMaxPerson() - listCommodityGroupMember.size();
												//倒计时计算
												countDown = ToolsDate.getCountDown(commodityGroup.getGroupTime()); //倒计时
												System.out.println(countDown);
												request.setAttribute("countDown",countDown);
											}
										}
										//TODO status 状态 1
										//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
										status = 1;
									}else {
										//TODO status 状态 2
										//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
										status = 2;
									}
								}else {
									//TODO status 状态 2
									//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
									status = 2;
								}
							}else {
								//TODO status 状态 3
								//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
								status = 3;
								request.setAttribute("joinGroupId",shareGroupId);
							}
						}else {
							//进入的是自己的分享链接
							CommodityGroupMember commodityGroupMember1 = commodityGroupMemberService.getByUserIdAndCId(id,user.getId());
							if(commodityGroupMember1 != null) {
								//TODO status 状态 1
								//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
								status = 1;
							}else {
								//TODO status 状态 0
								//默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接
								status = 0;
							}
						}

					}
				}
				//不可移动该项
				System.out.println("==============status==================");
				System.out.println("status: " + status + "  默认  0表示直接进入非分享链接并且自己未开团 1表示自己的团，忽略分享与否，是团长 2已经参团，忽略分享，非团长 3未参团，进入分享链接");
				System.out.println("==============status==================");
                request.setAttribute("status",status);
				request.setAttribute("groupId",groupId);
                request.setAttribute("listCommodityGroupMember",listCommodityGroupMember);
                if(listCommodityGroupMember != null){
					hiddenTotal = listCommodityGroupMember.size() - hiddenSet ;//隐藏的总人数
					totalMember = listCommodityGroupMember.size();
					request.setAttribute("hiddenSet",hiddenSet);
					request.setAttribute("hiddenTotal",hiddenTotal );
					request.setAttribute("totalMember",totalMember );
				}

				request.setAttribute("hiddenSet",hiddenSet);
				request.setAttribute("hiddenTotal",hiddenTotal );
				request.setAttribute("totalMember",totalMember );

                request.setAttribute("leader",leader);


				//TODO 验证当前的组团是否超时（除开组团成功条件）
                if(commodityGroup != null){
                	if(commodityGroup.getStatus() == 0){
						String groupTime = commodityGroup.getGroupTime();
						String currentTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
						//比较当前时间
						String maxTime = ToolsDate.getMaxTime(ToolsDate.simpleSecond ,groupTime ,currentTime);
						if(maxTime.equals(currentTime)){//超时
							if(commodityGroup.getTotalPerson() < commodityGroup.getMaxPerson()){//人数为达到，失败
								groupFail = 1;
							}
						}
					}
				}


				request.setAttribute("groupFail",groupFail);
                request.setAttribute("groupState",groupState);
                request.setAttribute("needNumber",needNumber);


				//Todo JSSDK 签名参数配置
				/** 准备签名参数 */
				String share = "&from=" + from + "&isappinstalled=" + isappinstalled ;
				/** mapKey签名成功后会将本次签名存入map的key值，下次进入该控制器会先判断Map中的值是否存在，不存在则刷新（重新生成） */
				String mapKey   =  "shop/groupCommodityDetailsInUser?menuId=" + menuId + "&id=" + id + "&shareUserId=" + shareUserId  + "&shareGroupId=" + shareGroupId + share;
				/** url签名页面，一定是当前的controller路径并附带参数和值 */
				String url = Set.SYSTEM_URL + mapKey;
				/** link签名分享链接，页面分享后，点击进入的链接 */
				String shareLink = Set.SYSTEM_URL +"shop/groupCommodityDetailsInUser?menuId=" + menuId + "&id=" + id + "&shareUserId=" + user.getId() + "&shareGroupId=" + groupId + share;
				/** imgUrl签名分享图片，页面分享后，分享出来的小图片 */
				String shareImgUrl = null;
				/** shareTitle签名分享标题 */
				String shareTitle = null;
				/** shareDesc签名分享描述 */
				String shareDesc = null;

				Commodity bean = commodityService.findCommodityByIdService(id);
				if(bean != null){
					shareImgUrl = bean.getShareImage();
					if(commodityGroup != null){
						shareTitle = "【还差" + String.valueOf(commodityGroup.getMaxPerson() - commodityGroup.getTotalPerson())  + "人成团】" + bean.getShareTitle();
					}else {
						shareTitle =  bean.getShareTitle();
					}
					shareDesc = bean.getShareDescM();
					if(bean.getUrl() != null){
						if(!"".equals(bean.getUrl())){
							view = bean.getUrl();
						}
					}

					//TODO 商品价格展示
					List<CommodityPriceList> groupPriceList = commodityPriceListService.listGroupPTimeByCommodityId(bean.getId());
					if(groupPriceList != null){
						if(groupPriceList.size() > 0 ){
							request.setAttribute("price",groupPriceList.get(0));
						}else {
							request.setAttribute("price",null);
						}
					}else {
						request.setAttribute("price",null);
					}
				}

				//Todo 当前页面进行JSSDK签名
				processingSharing(request,shareImgUrl,shareTitle,shareDesc,shareLink,url,mapKey,menuId);
				request.setAttribute("commodity",bean);

				//Todo 统计售出的订单数量

				Integer total = orderService.countByCommdityId(id);
				request.setAttribute("total",total);


				//Todo 统计订单的数量并设置商品是否能继续购买
				int maxGroup = bean.getMaxGroup();
				//Todo 统计成功组团数，判断能否继续组团
				if(maxGroup != -1){
					int currentTotal = commodityGroupService.countByStatus(bean.getId(),1);
					if(currentTotal >= maxGroup){
						buy ="0";
					}else {
						buy ="1";
					}
				}

				//TODO 关注判断
				subscribe = ToolsHttpRequest.sendGet(Api.FOCUSED_USER_JUDGING_SUB,"openid=" + user.getOpenid() + "&menuId=" + menuId);
				if(subscribe != null){
					if(!"".equals(subscribe) && !"1".equals(subscribe)){
						String wechatMenuId = publicAccountInfoService.getOtherParentMenuIdByMenuId(menuId);
						//TODO 替代临时二维码方法
						if(user.getUnionid() != null){
							if(!"".equals(user.getUnionid())){
								UserClickGroup userClickGroup = new UserClickGroup(String.valueOf(id), "COMMODITY", menuId, wechatMenuId, user.getOpenid(), user.getUnionid(), commodityGroup.getId(), commodityGroup.getGroupCode());
								Map<String, UserClickGroup> map = UserClickGroupMap.getInstance().getMap();
								map.put(user.getUnionid(),userClickGroup);

								//TODO 消息队列方式定时设置Map过期
								//TODO 死信延迟消息
								String urlMq =  Set.SYSTEM_URL + "interfaceMqMap/removeUserClickGroupMap";
								String paramsMq = "mapKey=" + ToolsASCIIChang.stringToAscii(user.getUnionid());
								DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), urlMq, "get" ,paramsMq , null);
								String message = DtoMessage.transformationToJson(dtoMessage);
								System.out.println("=================" + message);
								System.out.println("=================deadLetterPublishService=" + deadLetterPublishService);
								deadLetterPublishService.send(EnumsMQName.DEAD_JSSDK_INVALID,message);

							}
						}



//						//TODO 创建关注临时二维码
//						if(wechatMenuId != null){
//							if(!"".equals(wechatMenuId)){
//								String gid ;
//								if(commodityGroup != null){
//									if(!"".equals(commodityGroup.getId()) && !"-1".equals(commodityGroup.getId())){
//										gid = commodityGroupService.getGroupCodeById(commodityGroup.getId());
//									}else {
//										gid = "000000000000000000";
//									}
//								}else{
//									gid = "000000000000000000";
//								}
//								String scene_id = gid + id ;
//								String json = WeChatQRCode.getParametricQRCode(wechatMenuId ,259200,"QR_STR_SCENE",scene_id);
//								if(json != null){
//									JSONObject object = JSONObject.fromObject(json);
//									String ticket = object.getString("ticket");
//									String saveFilePath = "upfile/image/QRCode/GID" + gid + "MID"+menuId+"CId" + id + ".jpg";//服务器保存文件路径
//									String path = request.getSession().getServletContext().getRealPath(saveFilePath);
//									WeChatQRCode.showQrcode(ticket,path);
//									qrCode = Set.SYSTEM_URL + saveFilePath;//服务器保存文件路径
//									System.out.println("path : " + path);
//									System.out.println("qrCode : " + qrCode);
//								}
//							}
//						}
					}
				}


			}catch (Exception e){
				e.printStackTrace();
			}
			request.setAttribute("buy",buy);
			request.setAttribute("shareGroupId",shareGroupId);
			request.setAttribute("shareUserId",shareUserId);
			request.setAttribute("id",id);
			request.setAttribute("menuId",menuId);
			request.setAttribute("subscribe",subscribe);
			request.setAttribute("qrCode",qrCode);
		}
		System.out.println("==============view==================");
		System.out.println(view);
		System.out.println("==============view==================");
		return toVm(view);
	}

	/**
	 * 填写创建团信息
	 * @return String
	 * @author XGB
	 * @date 2018-04-13 9:17
	 */
	@RequestMapping("/toCreateGroupOrderInUser")
	public ModelAndView toCreateGroupOrderInUser(HttpServletRequest request, int id, String menuId, String from, String isappinstalled){
		try{
			Commodity bean = commodityService.findCommodityByIdService(id);
			request.setAttribute("commodity",bean);
			List<CommodityPriceList> groupPriceList = commodityPriceListService.listGroupPTimeByCommodityId(bean.getId());
			if(groupPriceList != null){
				if(groupPriceList.size() > 0){
					request.setAttribute("groupPriceList",groupPriceList);
					String[] choseDay = new String[groupPriceList.size()];
					for (int i=0;i<groupPriceList.size();i++ ){
						choseDay[i] = groupPriceList.get(i).getDescM();
					}
					request.setAttribute("price",groupPriceList.get(0).getPrice());
					request.setAttribute("choseDay",choseDay);
					request.setAttribute("choseDayLength",choseDay.length);
				}
			}else {
				request.setAttribute("groupPriceList",null);
				request.setAttribute("price","0.00");
				request.setAttribute("choseDay",null);
				request.setAttribute("choseDayLength",0);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("id",id);
		request.setAttribute("menuId",menuId);
		return toVm("mobile/mall/group/toCreateOrderGroupInUser");
	}


	/**
	 * 填写参团信息
	 * @return String
	 * @author XGB
	 * @date 2018-04-14 12:08
	 */
	@RequestMapping("/toJoinGroupOrderInUser")
	public ModelAndView toJoinGroupOrderInUser(HttpServletRequest request, Integer id, String menuId,String groupId, String from, String isappinstalled){
		try{
			Double price = 0.00;
			Commodity bean = commodityService.findCommodityByIdService(id);
			CommodityGroup commodityGroup = commodityGroupService.getById(groupId);
			if(commodityGroup != null && bean != null){
				List<Order> listOrder = orderService.getListByGroupId(groupId);
				if(listOrder != null) {
					if(listOrder.size() > 0) {
						Order order = listOrder.get(0);
						String[] choseDay = {order.getUserUseTime()};
						List<CommodityPriceList> commodityPriceLists = commodityPriceListService.listByCommodityIdAndDesC(id,choseDay[0]);
						if(commodityPriceLists != null){
							if(commodityPriceLists.size() > 0){
								price = commodityPriceLists.get(0).getPrice();
							}
						}
						request.setAttribute("day",order.getUserUseTime());
						request.setAttribute("choseDay",choseDay);
						request.setAttribute("choseDayLength",choseDay.length);
					}
				}
				request.setAttribute("commodity",bean);
				request.setAttribute("price",price);
				request.setAttribute("groupId",groupId);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("id",id);
		request.setAttribute("menuId",menuId);
		return toVm("mobile/mall/group/toJoinOrderGroupInUser");
	}

	/**
	 * 判断组团上限是否达到
	 * shop/interfaceCanCreateGroupOrder?id=
	 * @param id 商品id
	 * @return String
	 * @author XGB
	 * @date 2018-4-13 14:10
	 */
	@RequestMapping("/interfaceCanCreateGroupOrder")
	@ResponseBody
	public String interfaceCanCreateGroupOrder(int id){
		try{
			Commodity bean = commodityService.findCommodityByIdService(id);
			if(bean != null) {
				int maxGroup = bean.getMaxGroup();
				//Todo 统计成功组团数，判断能否继续组团
				if(maxGroup != -1){
					int currentTotal = commodityGroupService.countByStatus(bean.getId(),1);
					if(currentTotal >= maxGroup){
						return "max";
					}else {
						return "ok";
					}
				}
			}

		}catch (Exception e){
			e.printStackTrace();
			return "exception";
		}
		return "ok";
	}


    /**
     * 判断能否参团
     * shop/canJoinGroupOrderInUser?id=&groupId=
     * @param id 商品id
     * @return String
     * @author XGB
     * @date 2018-4-13 14:10
     */
    @RequestMapping("/canJoinGroupOrderInUser")
    @ResponseBody
    public String canJoinGroupOrderInUser(HttpServletRequest request,int id,String groupId){
        FocusedUserInfo user = (FocusedUserInfo) request.getSession().getAttribute("user");
        try{
            Commodity bean = commodityService.findCommodityByIdService(id);
            if(bean != null) {
                int maxGroup = bean.getMaxGroup();
                //Todo 统计成功组团数，判断能否继续组团
                if(maxGroup != -1){
                    int currentTotal = commodityGroupService.countByStatus(bean.getId(),1);
                    if(currentTotal >= maxGroup){
                        return "max";
                    }else {
                        CommodityGroup commodityGroup = commodityGroupService.getById(groupId);
                        if(commodityGroup != null){
							String groupTime = commodityGroup.getGroupTime();
							String currentTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);

							//比较当前时间
							String maxTime = ToolsDate.getMaxTime(ToolsDate.simpleSecond ,groupTime ,currentTime);
							//timeout
							if(maxTime.equals(currentTime)){//超时
								if(commodityGroup.getTotalPerson() < commodityGroup.getMaxPerson()){//人数为达到，失败
									return "timeout";
								}
							}

							//ok
                            if(commodityGroup.getStatus() == 0){
                                return "ok";
                            }else {
							//max
                                return "max";
                            }
                        }else {
							//noExit
                            return "noExit";
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return "exception";
        }
        return "ok";
    }


    /**
	 * 活动报名签名方法
	 * @param request
	 * @param shareImgUrl
	 * @param shareTitle
	 * @param shareDesc
	 * @param shareLink
	 * @param url
	 * @param mapKey
	 * @param menuId
	 */
	private static void processingSharing(HttpServletRequest request,String shareImgUrl,String shareTitle,String shareDesc,String shareLink,String url,String mapKey,String menuId){
		/** 设置分享标题和描述 */
		/** 配置分享的URL和Image和Desc等信息 */
		request.setAttribute("shareLink", shareLink);
		request.setAttribute("shareImgUrl", shareImgUrl);
		request.setAttribute("shareTitle", shareTitle);
		request.setAttribute("shareDesc", shareDesc);
		/** 用于页面签名的参数 */
		request.setAttribute("url", url);
		request.setAttribute("mapKey", mapKey);
		request.setAttribute("menuId", menuId);
	}
}
