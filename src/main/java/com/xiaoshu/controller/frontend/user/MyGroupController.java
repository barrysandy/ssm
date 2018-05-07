package com.xiaoshu.controller.frontend.user;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsPage;
import com.xiaoshu.vo.MyGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用户组团模板
 * @name: MyGroupController
 * @author: XGB
 * @date: 2018-04-16 14:24
 */
@Controller
@RequestMapping(value = "/myGroupInUser")
public class MyGroupController extends BaseController {

	@Resource private FocusedUserInfoService focusedUserInfoService;
	@Resource private CommodityGroupService commodityGroupService;
	@Resource private CommodityGroupMemberService commodityGroupMemberService;
	/**
	 * 用户组团列表
	 * @return String
	 * @author XGB
	 * @date 2018-04-16 14:30
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, String menuId,Integer cpage){
		Integer pageSize = 10;
		try{
			/** 授权登录的用户对象 */
			FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
			if(focusedUserInfo != null){
				Integer index =(cpage - 1) * pageSize;//当前查询的开始记录
				List<MyGroup> list = commodityGroupService.getListMyGroupByUserId(index,pageSize,focusedUserInfo.getId());
				if(list != null) {
					if(list.size() > 0) {
						Iterator<MyGroup> iterator = list.iterator();
						if(iterator != null) {
							while (iterator.hasNext()){
								MyGroup myGroup = iterator.next();
								Integer countDown = ToolsDate.getCountDown(myGroup.getTime()); //倒计时
								System.out.println("==============countDown==================");
								System.out.println(countDown);
								System.out.println("==============countDown==================");
								myGroup.setTime(String.valueOf(countDown));
								if(myGroup != null) {
									List<CommodityGroupMember> listMember = commodityGroupMemberService.getListByGroupIdAndStatus(myGroup.getGroupId(), 1);
									Iterator<CommodityGroupMember> iteratorGroupMember = listMember.iterator();
									if (iteratorGroupMember != null) {
										List<FocusedUserInfo> user = new ArrayList<FocusedUserInfo>();
										while (iteratorGroupMember.hasNext()){
											CommodityGroupMember commodityGroupMember = iteratorGroupMember.next();
											if(commodityGroupMember != null) {
												FocusedUserInfo focusedUserInfoTemp = focusedUserInfoService.selectByPrimaryKey(commodityGroupMember.getUserId());
												if(focusedUserInfoTemp != null) {
													user.add(focusedUserInfoTemp);
												}
											}
										}
										myGroup.setUser(user);
										myGroup.setTotalPerson(user.size());
										myGroup.setHiddenSet(5);
										myGroup.setHiddenTotal(myGroup.getTotalPerson() - myGroup.getHiddenSet());
									}
								}
							}
						}
					}
				}
				Integer totalRecords = commodityGroupService.countMyGroupByUserId(focusedUserInfo.getId());//总记录数
				Integer totalPage = ToolsPage.totalPage(totalRecords, pageSize);//总页数
				request.setAttribute("list",list);
				request.setAttribute("totalPage",totalPage);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("cpage",cpage);
		request.setAttribute("menuId",menuId);
		return toVm("mobile/user/myGroup/list");
	}

	/**
	 * 用户组团列表JSON
	 * @return String
	 * @author XGB
	 * @date 2018-04-16 14:30
	 */
	@RequestMapping("/listJSON")
	@ResponseBody
	public String listJSON(HttpServletRequest request, HttpServletResponse response, Integer currentPage){
		Integer pageSize = 10;
		try{
			/** 授权登录的用户对象 */
			FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
			if(focusedUserInfo != null){
				Integer index =(currentPage - 1) * pageSize;//当前查询的开始记录
				List<MyGroup> list = commodityGroupService.getListMyGroupByUserId(index,pageSize,focusedUserInfo.getId());
				if(list != null) {
					if(list.size() > 0) {
						Iterator<MyGroup> iterator = list.iterator();
						if(iterator != null) {
							while (iterator.hasNext()){
								MyGroup myGroup = iterator.next();
								Integer countDown = ToolsDate.getCountDown(myGroup.getTime()); //倒计时
								myGroup.setTime(String.valueOf(countDown));
								if(myGroup != null) {
									List<CommodityGroupMember> listMember = commodityGroupMemberService.getListByGroupIdAndStatus(myGroup.getGroupId(), 1);
									Iterator<CommodityGroupMember> iteratorGroupMember = listMember.iterator();
									if (iteratorGroupMember != null) {
										List<FocusedUserInfo> user = new ArrayList<FocusedUserInfo>();
										while (iteratorGroupMember.hasNext()){
											System.out.println();
											CommodityGroupMember commodityGroupMember = iteratorGroupMember.next();
											if(commodityGroupMember != null) {
												FocusedUserInfo focusedUserInfoTemp = focusedUserInfoService.selectByPrimaryKey(commodityGroupMember.getUserId());
												if(focusedUserInfoTemp != null) {
													user.add(focusedUserInfoTemp);
												}
											}
										}
										myGroup.setUser(user);
										myGroup.setTotalPerson(user.size());
										myGroup.setHiddenSet(5);
										myGroup.setHiddenTotal(myGroup.getTotalPerson() - myGroup.getHiddenSet());
									}
									String jsonButton = "";
									if(Integer.parseInt(myGroup.getTime()) > 0){
										if(Integer.parseInt(myGroup.getStatus()) == 0){
											jsonButton = "<span style='color: #439dc4;font-size: 1em;font-weight:bold;'>还差" + myGroup.getTotal() + "人，分享给好友</span>";
										}else if(Integer.parseInt(myGroup.getStatus()) == 1){
											jsonButton = "<span style='color: #d0d0d0;font-size: 1em;font-weight:bold;'>组团成功</span>";
										}
									}else {
										if(Integer.parseInt(myGroup.getStatus()) == 0){
											jsonButton = "<span style='color: #d0d0d0;font-size: 1em;font-weight:bold;'>组团失败，还差" + myGroup.getTotal() + "人</span>";
										}
									}
									myGroup.setJsonButton(jsonButton);
								}
							}
						}
					}
				}
				String json = JSONUtils.toJSONString(list);
				response.getWriter().print(json);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


}
