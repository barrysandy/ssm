package com.xiaoshu.controller.admin.activity;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.WechatActivity;
import com.xiaoshu.entity.WechatActivitySign;
import com.xiaoshu.entity.WechatActivitySignSet;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 微信活动接口
 * @name: WechatActivityController
 * @author: XGB
 * @date: 2018-02-06 16:49
 */
@Controller
@RequestMapping(value = "/activityinterface")
public class WechatActivityInterfaceController extends BaseController {

	@Resource private WechatActivityService wechatActivityService;
	@Resource private FocusedUserInfoService focusedUserInfoService;
	@Resource private WechatActivitySignService wechatActivitySignService;
	@Resource private WechatActivitySignSetService wechatActivitySignSetService;


	/**
	 * activityinterface/getListJson?menuId=
	 * 获取正在进行的活动列表（会话活动）
	 * @author XGB
	 * @date 2018-04-27 11:28
	 */
	@RequestMapping("/getListJson")
	@ResponseBody
	public String getListJson(String menuId) {
		try{
			String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
			List<WechatActivity> list = wechatActivityService.getSignSessionList(nowTime,menuId);
			if(list != null){
				Iterator<WechatActivity> iterator = list.iterator();
				while (iterator.hasNext()){
					WechatActivity wechatActivity = iterator.next();
					if(wechatActivity != null){
						if(wechatActivity.getDescM() != null){
							if(!"".equals(wechatActivity.getDescM())){
								wechatActivity.setDescM(ToolsASCIIChang.stringToAscii(wechatActivity.getDescM()));
							}
						}
						if(wechatActivity.getTitle() != null){
							if(!"".equals(wechatActivity.getTitle())){
								wechatActivity.setTitle(ToolsASCIIChang.stringToAscii(wechatActivity.getTitle()));
							}
						}
						if(wechatActivity.getReturnPage() != null){
							if(!"".equals(wechatActivity.getReturnPage())){
								wechatActivity.setReturnPage(ToolsASCIIChang.stringToAscii(wechatActivity.getReturnPage()));
							}
						}
					}
				}
				String json = JSONUtils.toJSONString(list);
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ MenuId:"+ menuId + " activityinterface/getListJson !! Date:" + nowTime );
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ List json:"+ json );
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				return json;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * activityinterface/signSession
	 * 报名（会话活动）
	 * @author XGB
	 * @date 2018-04-27 13:25
	 */
	@RequestMapping("/signSession")
	@ResponseBody
	public String signSession(String menuId,String openid,String id,String name,String phone ,String address) {
		try{
			if(menuId != null && openid != null && id != null){

				WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
				FocusedUserInfo focusedUserInfo = focusedUserInfoService.selectByOpenid(openid);
				if(focusedUserInfo != null){
					if(name != null){
						name = ToolsASCIIChang.asciiToString(name);
					}
					if(phone != null){
						phone = ToolsASCIIChang.asciiToString(phone);
					}
					String uniqueIdentifier = UUID.randomUUID().toString();
					uniqueIdentifier = ToolsString.putOffBar(uniqueIdentifier);
					WechatActivitySign wechatActivitySign = new WechatActivitySign(uniqueIdentifier,"", "", "", null, id, "", "", 1, -1, new Date(), null);
					wechatActivitySign.setOpenid(focusedUserInfo.getOpenid());
					wechatActivitySign.setUnionid(focusedUserInfo.getUnionid());
					wechatActivitySign.setUserId(focusedUserInfo.getId());
					int exitSign = wechatActivitySignService.selectUserExitSign(id,focusedUserInfo.getId());//判断是否已经报名
					if(exitSign == 0){
						int saveRecord = wechatActivitySignService.saveRecord(wechatActivitySign);
						if(saveRecord > 0){
							WechatActivitySignSet nicknameSignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "nickname", focusedUserInfo.getNickName(), 1, "", -1, "", 9999, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
							wechatActivitySignSetService.saveRecord(nicknameSignSet);
							System.out.println("=============================================");
							System.out.println("保存 nicknameSignSet: " + nicknameSignSet);
							System.out.println("=============================================");
							WechatActivitySignSet headimgurlSignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "headimgurl", focusedUserInfo.getHeadImgUrl(), 1, "", -1, "", 10000, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
							wechatActivitySignSetService.saveRecord(headimgurlSignSet);
							System.out.println("=============================================");
							System.out.println("保存 headimgurlSignSet: " + headimgurlSignSet);
							System.out.println("=============================================");
							if(name != null){
								if(!"".equals(name)){
									System.out.println("=============================================");
									System.out.println("保存 name: " + name);
									System.out.println("=============================================");
									//组装属性值
									WechatActivitySignSet wechatActivitySignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "name", name, 1, "", -1, "", 1, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
									wechatActivitySignSetService.saveRecord(wechatActivitySignSet);
								}
							}
							if(phone != null){
								if(!"".equals(phone)){
									System.out.println("=============================================");
									System.out.println("保存 phone: " + phone);
									System.out.println("=============================================");
									//组装属性值
									WechatActivitySignSet wechatActivitySignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "phone", phone, 1, "", -1, "", 2, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
									wechatActivitySignSetService.saveRecord(wechatActivitySignSet);
								}
							}if(address != null){
								if(!"".equals(address)){
									System.out.println("=============================================");
									System.out.println("保存 address: " + address);
									System.out.println("=============================================");
									//组装属性值
									WechatActivitySignSet wechatActivitySignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "address", address, 1, "", -1, "", 2, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
									wechatActivitySignSetService.saveRecord(wechatActivitySignSet);
								}
							}
							return "1";
						}else{
							return "0";
						}
					}else {
						return "2";
					}
				}else{
					return "3";
				}
			}else {
				return "0";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "0";
	}


}
