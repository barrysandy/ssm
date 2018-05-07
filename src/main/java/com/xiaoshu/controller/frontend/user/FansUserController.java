package com.xiaoshu.controller.frontend.user;

import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsPage;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.single.MapUserPhoneCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 用户模块
 * @name: UserController
 * @author: XGB
 * @date: 2018-03-06 11:15
 */
@Controller
@RequestMapping(value = "/wechatInUser")
public class FansUserController extends BaseController {

	@Resource private FocusedUserInfoService focusedUserInfoService;
	@Resource private WechatActivityWinningService wechatActivityWinningService;
	@Resource private WechatActivityService wechatActivityService;
	@Resource private OrderService orderService;
	@Resource private SellerService sellerService;
	@Resource private OrderWriteOffByUserService orderWriteOffByUserService;

	/**
	 * 用户中心
	 * @return String
	 * @author XGB
	 * @date 2018-02-28 10:45
	 */
	@RequestMapping("/userCenter")
	public ModelAndView userCenter(HttpServletRequest request, String menuId, String from, String isappinstalled){
		try{
			/** 授权登录的用户对象 */
			FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
			if(focusedUserInfo != null){
				int existUser = orderWriteOffByUserService.existUser(focusedUserInfo.getId());
				request.setAttribute("write",existUser);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("menuId",menuId);
		return toVm("mobile/user/userCenter");
	}


	/**
	 * 用户中奖列表
	 * @return String
	 * @author XGB
	 * @date 2018-03-06 15:08
	 */
	@RequestMapping("/userPrize")
	public ModelAndView userPrize(HttpServletRequest request, String menuId, int currentPage, String from, String isappinstalled){
		try{
			/** 授权登录的用户对象 */
			FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
			List<WechatActivityWinning> listWin = wechatActivityWinningService.getListPageByUserId(focusedUserInfo.getId(),0,10);
			if(listWin != null){
				for (WechatActivityWinning winning : listWin){
					String qrCode = Set.SYSTEM_URL + "wechatInUser/scanPrizeInUser?code=" + winning.getCode() + "&menuId=" + menuId ;
					qrCode = URLEncoder.encode(qrCode,"UTF-8");
					winning.setFromatUpdateTime(qrCode);
				}
			}
			int totalRecords = wechatActivityWinningService.selectCountByUserId(focusedUserInfo.getId());//总记录数
			int totalPage = ToolsPage.totalPage(totalRecords, 10);//总页数
			request.setAttribute("list",listWin);
			request.setAttribute("totalPage",totalPage);
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("menuId",menuId);
		return toVm("mobile/user/userPrizeList");
	}

	/**
	 * 用户中奖列表JSON
	 * @return String
	 * @author XGB
	 * @date 2018-03-06 15:08
	 */
	@RequestMapping("/userPrizeJSON")
	@ResponseBody
	public String userPrizeJSON(HttpServletRequest request, HttpServletResponse response, String menuId, int currentPage){
		try{
			response.setCharacterEncoding("UTF-8");
			FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
			int totalRecords = wechatActivityWinningService.selectCountByUserId(focusedUserInfo.getId());//总记录数
			int totalPage = ToolsPage.totalPage(totalRecords, 10);//总页数
			if(currentPage > totalPage){
				response.getWriter().print("0");
			}else{
				int startRow =(currentPage - 1) * 10;//当前查询的开始记录
				List<WechatActivityWinning> listWin = wechatActivityWinningService.getListPageByUserId(focusedUserInfo.getId(),startRow,10);
				if(listWin != null){
					Iterator<WechatActivityWinning> iterator = listWin.iterator();
					while (iterator.hasNext()){
						WechatActivityWinning winning = iterator.next();
						winning.setFromatCreateTime(ToolsDate.getStringDateToFormat(winning.getCreateTime(),"MM-dd HH:mm"));
						String qrCode = Set.SYSTEM_URL + "wechatInUser/scanPrizeInUser?code=" + winning.getCode() + "&menuId=" + menuId ;
						qrCode = URLEncoder.encode(qrCode,"UTF-8");
						winning.setFromatUpdateTime(qrCode);
					}
					response.getWriter().print(JSONUtils.toJSONString(listWin));
				}else{
					response.getWriter().print("0");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 用户订单列表
	 * @return String
	 * @author XGB
	 * @date 2018-03-12 9:55
	 */
	@RequestMapping("/userOrder")
	public ModelAndView userOrder(HttpServletRequest request, String menuId,Integer typeState){
		try{
			/** 授权登录的用户对象 */
			FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
			if(focusedUserInfo != null){
				String userId = focusedUserInfo.getId();
				String userId2 = null;
				if(focusedUserInfo.getUnionid() != null){
					//TODO 用当前的 menuId 换取绑定的另一个 menuId
//					String parentMenuId = publicAccountInfoService.getOtherParentMenuIdByMenuId(menuId);
					FocusedUserInfo focusedUserInfo2 = focusedUserInfoService.getOtherByUnionidAndId(focusedUserInfo.getUnionid(),userId);
					userId2 = focusedUserInfo2.getId();
				}
				List<Order> listOrder = orderService.listByUserId(0,10,userId,userId2,typeState);
				int totalRecords = orderService.countByUserId(userId,userId2,typeState);//总记录数
				int totalPage = ToolsPage.totalPage(totalRecords, 10);//总页数
				request.setAttribute("list",listOrder);
				request.setAttribute("totalPage",totalPage);
				request.setAttribute("typeState",typeState);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("menuId",menuId);
		return toVm("mobile/user/userOrderList");
	}

	/**
	 * 用户订单列表JSON
	 * @return String
	 * @author XGB
	 * @date 2018-03-06 15:08
	 */
	@RequestMapping("/userOrderJSON")
	@ResponseBody
	public String userOrderJSON(HttpServletRequest request, HttpServletResponse response, String menuId, Integer currentPage,Integer typeState){
		try{
			response.setCharacterEncoding("UTF-8");
			FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
			String userId = focusedUserInfo.getId();
			String userId2 = null;
			if(focusedUserInfo.getUnionid() != null){
				//TODO 用当前的 menuId 换取绑定的另一个 menuId
//				String parentMenuId = publicAccountInfoService.getOtherParentMenuIdByMenuId(menuId);
				FocusedUserInfo focusedUserInfo2 = focusedUserInfoService.getOtherByUnionidAndId(focusedUserInfo.getUnionid(),userId);
				userId2 = focusedUserInfo2.getId();
			}
			int totalRecords = orderService.countByUserId(userId,userId2,typeState);//总记录数
			int totalPage = ToolsPage.totalPage(totalRecords, 10);//总页数
			if(currentPage > totalPage){
				response.getWriter().print("0");
			}else{
				int startRow =(currentPage - 1) * 10;//当前查询的开始记录
				List<Order> listOrder = orderService.listByUserId(startRow,10,userId,userId2,typeState);
				if(listOrder != null){
					response.getWriter().print(JSONUtils.toJSONString(listOrder));
				}else{
					response.getWriter().print("0");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 授权添加核销人员 wechatInUser/userAddScanInUser?menuId=&id=
	 * @return String
	 * @author XGB
	 * @date 2018-03-06 17:45
	 */
	@RequestMapping("/userAddScanInUser")
	public ModelAndView userAddScanInUser(HttpServletRequest request, String menuId, String id){
		try{
			WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
			request.setAttribute("bean",wechatActivity);
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("menuId",menuId);
		return toVm("mobile/user/userAddScan");
	}


	/**
	 * 核销奖品码 wechatInUser/scanPrizeInUser?code=&menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-03-07 14:37
	 */
	@RequestMapping("/scanPrizeInUser")
	public ModelAndView scanPrizeInUser(HttpServletRequest request, String menuId, String code){
		/** 返回的视图名称 */
		String views = "";
		/** 用户 */
		FocusedUserInfo user = (FocusedUserInfo) request.getSession().getAttribute("user");
		/** authority 为扫码者的权限 0表示不能进行核销 1表示有权限进行核销 */
		String authority = "0";
		/** state 核销码状态 0 未核销 1已核销 */
		int state = 0;
		try{
			if(code != null && menuId != null){
				if(!"".equals(code) && !"".equals(menuId)){
					WechatActivityWinning winning = wechatActivityWinningService.getByCode(code);
					if(winning != null){
						state = winning.getStatus();
					}
					WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(winning.getWechatActivityId());
					request.setAttribute("bean",wechatActivity);
					request.setAttribute("winning",winning);
					String scanUserIdArray = wechatActivity.getScanUserIdArray();
					/** authority 判断扫码者的权限 0表示不能进行核销 1表示有权限进行核销 */
					if(user != null){
						if(scanUserIdArray.indexOf(user.getId()) != -1 ){
							authority = "1";
						}
					}

					views = "mobile/user/userPrizeScan";
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		request.setAttribute("menuId",menuId);
		request.setAttribute("authority",authority);
		request.setAttribute("state",state);
		return toVm(views);
	}

	/**
	 * 确认核销中奖 wechatInUser/confirmScaninPrizeCodeinterface?code=
	 * @param code
	 * @author XGB
	 * @date 2018-03-07 15:37
	 */
	@RequestMapping("/confirmScaninPrizeCodeinterface")
	@ResponseBody
	public String confirmScaninPrizeCodeinterface(String code, HttpServletResponse response){
		int json = 0;
		response.setCharacterEncoding("UTF-8");
		try{
			json = wechatActivityWinningService.updateStateByCode(code);
			response.getWriter().print(json);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 授权添加商家订单核销人员 wechatInUser/userAddSellerScanInUser?menuId=&id=
	 * @return String
	 * @author XGB
	 * @date 2018-03-06 17:45
	 */
	@RequestMapping("/userAddSellerScanInUser")
	public ModelAndView userAddSellerScanInUser(HttpServletRequest request, String menuId, Integer id){
		try{
			Seller seller = sellerService.findSellerByIdService(id);
			request.setAttribute("bean",seller);
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("menuId",menuId);
		return toVm("mobile/user/userAddSellerScan");
	}


	/**
	 * 绑定电话页面 wechatInUser/bingPhoneInUser?menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-4-11 13:57
	 */
	@RequestMapping("/bingPhoneInUser")
	public ModelAndView bingPhoneInUser(HttpServletRequest request, String menuId){
		try{
			FocusedUserInfo userInfo = (FocusedUserInfo)request.getSession().getAttribute("user");
			if(userInfo != null){
				FocusedUserInfo user = focusedUserInfoService.selectByPrimaryKey(userInfo.getId());
				request.setAttribute("userReq",user);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("menuId",menuId);
		return toVm("mobile/user/bingPone/toBing");
	}


	/**
	 * 绑定电话页面 wechatInUser/getPhoneCodeInUser?menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-4-11 14:32
	 */
	@RequestMapping("/getPhoneCodeInUser")
	@ResponseBody
	public String getPhoneCodeInUser(HttpServletRequest request,HttpServletResponse response,String userphone){
		try{
			if(userphone == null){
				response.getWriter().print(0);
			}
			if(userphone != null){
				FocusedUserInfo userInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
				if(userInfo != null){
					Map<String, String> map = MapUserPhoneCode.getInstance().getMap();
					map.remove(userInfo.getId() + "bingPoneCode");//移除key
					String code = ToolsString.generateRandNumber(6);
					System.out.println("=============== code ===================" + code);
					System.out.println("=============== code ===================" + code);
					System.out.println("=============== code ===================" + code);
					System.out.println("=============== code ===================" + code);
					map.put(userInfo.getId() + "bingPoneCode",code);//获取
					response.getWriter().print("验证已经发送到你的手机，请注意查收!" + code);
				}else {
					response.getWriter().print(0);
				}
			}else {
				response.getWriter().print(0);
			}
		}catch (Exception e){
			try{
				response.getWriter().print(0);
			}catch (Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 绑定电话页面 wechatInUser/PostBingPhoneInUser?menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-4-11 14:32
	 */
	@RequestMapping("/PostBingPhoneInUser")
	@ResponseBody
	public String PostBingPhoneInUser(HttpServletRequest request,HttpServletResponse response,String username, String userphone, String code){
		try{
			if(code == null){
				response.getWriter().print("验证码不能为空!");
			}
			if(username == null){
				response.getWriter().print("联系人不能为空!");
			}
			if(userphone == null){
				response.getWriter().print("联系人电话不能为空!");
			}

			if(code != null && username != null && userphone != null){
				FocusedUserInfo userInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
				if(userInfo != null){
					if(code != null && !"".equals(code)){
						Map<String, String> map = MapUserPhoneCode.getInstance().getMap();
						String mCode = map.get(userInfo.getId() + "bingPoneCode");//获取
						if(mCode != null){
							if(code.equals(mCode)){
								int result = focusedUserInfoService.updateUserContents(username,userphone,userInfo.getId());
								if(result > 0){
									response.getWriter().print("修改成功!");
									map.remove(userInfo.getId() + "bingPoneCode");//移除key
								}else {
									response.getWriter().print("服务器错误，请稍后重试!");
								}
							}
							else {
								response.getWriter().print("验证码错误!");
							}
						}else {
							response.getWriter().print("验证码不正确!");
						}
					}

				}
			}else {
				response.getWriter().print("请完善所有信息!");
			}

		}catch (Exception e){
			try{
				response.getWriter().print("服务器错误，请稍后重试!");
			}catch (Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 绑定电话页面 wechatInUser/PostBingPhoneInUserNoCode?menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-4-11 14:32
	 */
	@RequestMapping("/PostBingPhoneInUserNoCode")
	@ResponseBody
	public String PostBingPhoneInUserNoCode(HttpServletRequest request,HttpServletResponse response,String username, String userphone){
		try{
			if(username == null){
				response.getWriter().print("联系人不能为空!");
			}
			if(userphone == null){
				response.getWriter().print("联系人电话不能为空!");
			}

			if(username != null && userphone != null){
				FocusedUserInfo userInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
				if(userInfo != null){
					int result = focusedUserInfoService.updateUserContents(username,userphone,userInfo.getId());
					if(result > 0){
						response.getWriter().print("修改成功!");
					}else {
						response.getWriter().print("服务器错误，请稍后重试!");
					}

				}
			}else {
				response.getWriter().print("请完善所有信息!");
			}

		}catch (Exception e){
			try{
				response.getWriter().print("服务器错误，请稍后重试!");
			}catch (Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}


}
