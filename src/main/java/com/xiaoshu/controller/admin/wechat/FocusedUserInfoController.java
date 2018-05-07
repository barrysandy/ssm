package com.xiaoshu.controller.admin.wechat;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.FocusedUserGroup;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.service.FocusedUserGroupService;
import com.xiaoshu.service.FocusedUserInfoService;
import com.xiaoshu.service.MenuService;
import com.xiaoshu.service.PublicAccountInfoService;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.ToolsUnicode;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import com.xiaoshu.vo.JsonData;
import com.xiaoshu.wechat.api.WeChatAPI;
import com.xiaoshu.wechat.tools.AdvancedUtil;
import com.xiaoshu.wechat.tools.WeChatSaveAndUpdateUserMsg;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 关注用户信息表
 * @name: FocusedUserInfoController
 * @author: Kun
 * @date: 2018-01-04 15:28
 */
@Controller
@RequestMapping(value = "/focusedUserInfo")
public class FocusedUserInfoController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(FocusedUserInfoController.class);

	@Resource private FocusedUserInfoService focusedUserInfoService;
	@Resource private FocusedUserGroupService focusedUserGroupService;
	@Resource private MenuService menuService;
    @Resource private PublicAccountInfoService publicAccountInfoService;

	/**
	 * 关注用户列表页面
	 * @return view
	 * @author zhou.zhengkun
	 * @date 2018/01/04 0004 15:42
	 */
	@RequestMapping(value = "/toUserListPage")
	public ModelAndView toUserListPage(Pager pager, Model model, String search,String menuid,String startDate,String endDate){
	    try{
			String parentId = menuService.getParentIdByMenuId(menuid);
			Map<String,Object> map = new HashMap<String,Object>(8);
			map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
			map.put("pageSize", pager.getPageSize());
			String searchName = search;
			if(search != null){
				if(!"".equals(search)){
					searchName = ToolsUnicode.string2Unicode(searchName);
				}
			}
			map.put("search", searchName);
			map.put("menuId",menuid);
			if (StringUtils.isNotBlank(startDate)){
				map.put("startDate",startDate);
			}
			if (StringUtils.isNotBlank(endDate)){
				map.put("endDate",endDate + " 23:59:59");
			}
			List<FocusedUserInfo> list = focusedUserInfoService.selectByPage(map);
			int totalNum = focusedUserInfoService.selectCount(map);
//			String parentMenuId = menuService.getParentIdByMenuId(menuid);
//			List<FocusedUserGroup> groupList = focusedUserGroupService.getListByParentMenuId(parentMenuId);
//			model.addAttribute("groupList",groupList);
			pager.setFullListSize(totalNum);
			pager.setList(list);
			model.addAttribute("pager", pager);
			model.addAttribute("search", search);
			model.addAttribute("menuId",menuid);
			model.addAttribute("startDate",startDate);
			model.addAttribute("endDate",endDate);
			model.addAttribute("parentId",parentId);
		}catch (Exception e){
	    	e.printStackTrace();
		}
		return toVm("admin/focusUser/focusedUserInfo_list");
	}

	/**
	 * 关注用户信息表编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author Kun
	 * @date 2018-01-04 15:28
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuId){
		try{
			FocusedUserInfo rule = new FocusedUserInfo();
			if(StringUtils.isNotBlank(id)){
				rule = focusedUserInfoService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
			model.addAttribute("menuId",menuId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/focusUser/focusedUserInfo_edit");
	}

	/**
	 * 关注用户信息表预览
	 * @param id 主键ID
	 * @author Kun
	 * @date 2018-01-04 15:28
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		try {
			FocusedUserInfo rule = new FocusedUserInfo();
			if(StringUtils.isNotBlank(id)){
				rule = focusedUserInfoService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch (Exception e){
			e.printStackTrace();
		}
		return toVm("admin/focusUser/focusedUserInfo_view");
	}

	/**
	 * 关注用户信息表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-04 15:28
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(FocusedUserInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				/*用户分组操作*/
				String groupId = bean.getGroupId();
				if (!StringUtils.isEquals(groupId,"")){
					focusedUserGroupService.userJoinGroup(groupId,id);
				}
				if(StringUtils.isNotBlank(id)){
					/*更新*/
					FocusedUserInfo rule = new FocusedUserInfo();
					if(StringUtils.isNotBlank(id)){
						rule = focusedUserInfoService.selectByPrimaryKey(id);
						bean.setSubscribe(rule.getSubscribe());
					}
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					focusedUserInfoService.update(bean);
				}else{
					/*新增*/
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setSubscribe(1);
					bean.setId(u.toString());
					bean.setSubscribeTime("");
					bean.setCreateTime(new Date());
					focusedUserInfoService.insert(bean);
				}
			}
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error :"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/**
	 * 关注用户信息表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-04 15:28
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			focusedUserInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error :"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/** 根据openid、menuId  查询用户是否存在  focusedUserInfo/interfaceUserExit */
	@RequestMapping(value="/interfaceUserExit",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String wxtInterface(@RequestParam("openid") String openid,@RequestParam("menuId") String menuId) {
		/** 设置时间和返回的JSON格式数据 */
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
		JsonData jsonData = new JsonData(JsonData.STATUS_FAIL,null,null) ;
		String json =  JsonData.getJson(jsonData) ;
		System.out.println("------------ [out] interfaceUserExit openid : " + openid + " menuId :" + menuId + " ------------");
		try{
			jsonData.setStatus(JsonData.STATUS_OK);
			int i = focusedUserInfoService.selectExitByOpenid(openid);
            if(i == 0){//如果不存在则保存用户
				FocusedUserInfo user = new FocusedUserInfo();
				user.setOpenid(openid);
				user.setParentMenuId(menuId);
				user.setId(String.valueOf(UUID.randomUUID()));
				user.setCreateTime(new Date());
				user.setStatus(1);
				user.setSubscribe(1);
				//绑定用户的菜单menuId
				int sonMenuId = menuService.selectMenuIDByParentIDANDUrl(Integer.parseInt(menuId),"focusedUserInfo/toUserListPage.htm");
				if(sonMenuId != 0){
					user.setMenuId(sonMenuId + "");
				}
				//读取Map中的accessToken
				MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
				Map<String, String> map = mapPublicNumber.getMap();
				if(map != null && map.size() == 0){
					System.out.println("map not data");
					JobPublicAccount.ToRefreshMapJobPublicAccount();
				}
				//公众号是否绑定开放平台，绑定了则获取unionid
				String openPlatform = map.get("openPlatform" + menuId);
				if(openPlatform != null){
 					if(!"".equals(openPlatform)){
						String accessToken = map.get("accessToken" + menuId);
						String unionid = AdvancedUtil.getUnionidUserInfo(accessToken, openid);
						user.setUnionid(unionid);
					}
				}
				int saveResult = focusedUserInfoService.insertUser(user);
				if(saveResult > 0){
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("---------------------- Interface_Save WechatUserInfo --------------------------------------------------------------------------------------");
					System.out.println("---------------------- Save user [" + openid + "] complete!! Date:" + nowTime);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					jsonData.setData("2");
					return JsonData.getJson(jsonData) ;
				}else {
					jsonData.setData("0");
					return JsonData.getJson(jsonData) ;
				}

			}else { //用户已经存在
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("---------------------- Interface_Save WechatUserInfo Exit ---------------------------------------------------------------------------------");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				String nickName = focusedUserInfoService.getNickNameByOpenid(openid);
				if(nickName == null){
					jsonData.setDescM("update");
				}else{
					if("".equals(nickName)){
						jsonData.setDescM("update");
					}
				}
				jsonData.setData("1");
				return JsonData.getJson(jsonData) ;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return json ;
		}
	}



	/**
	 * 根据openid查询用户信息是否完整/并补全不完整的信息  focusedUserInfo/interfaceUserInfoIntegrity
	 * @param openid
	 * @param menuId
	 * @return 0 不完整 1 完整信息 2不完整并更新成功了
	 * @author XGB
	 * @date 2018-01-17 10:32
	 */
	@RequestMapping(value="/interfaceUserInfoIntegrity",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String interfaceUserInfoIntegrity(@RequestParam("openid") String openid,@RequestParam("menuId") String menuId) {
		/** 设置时间和返回的JSON格式数据 */
//		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
		JsonData jsonData = new JsonData(JsonData.STATUS_FAIL,null,null) ;
		String json =  JsonData.getJson(jsonData) ;
		try{
			FocusedUserInfo user = focusedUserInfoService.selectByOpenid(openid);
			if(user != null){
				jsonData.setStatus(JsonData.STATUS_OK);
				if(user.getUnionid() != null){
					if(!"".equals(user.getUnionid())){
						//TODO 判断当前的公众号类型
						//TODO 服务号需要通过关联的订阅号实现用户数据同步
						int accountType = publicAccountInfoService.getAccountTypeByParentMenuId(menuId);
						if(accountType == 2){
							System.out.println("------------ [out] 1_interfaceUserInfoIntegrity accountType == 2  ------------");
							focusedUserInfoService.updateUserInfoByUnionidAndParentMenuId(user);
							jsonData.setData("1");
							jsonData.setDescM("update2");
							json =  JsonData.getJson(jsonData) ;
							return json;
						}
						//TODO 订阅号/测试号直接调用更新用户信息
						if(accountType == 3 || accountType == 6){
							System.out.println("------------ [out] 2_interfaceUserInfoIntegrity accountType == 3/6  ------------");
							int result = focusedUserInfoService.updateUserInfoByWechatInterface(user,menuId,openid);
							jsonData.setData(String.valueOf(result));
							jsonData.setDescM("update3/6");
							json =  JsonData.getJson(jsonData) ;
							return json;
						}
					}
				}else{
					//TODO 直接调用更新用户信息
					System.out.println("------------ [out] 3_interfaceUserInfoIntegrity ------------");
					int result = focusedUserInfoService.updateUserInfoByWechatInterface(user,menuId,openid);
					jsonData.setData(String.valueOf(result));
					jsonData.setDescM("updateNoUnionId");
					json =  JsonData.getJson(jsonData) ;
					return json;
				}
			}else{
				return json;
			}
		} catch (Exception e){
			e.printStackTrace();
			return json;
		}
		return json;
	}




	/**
	 * 更新用户的关注状态  focusedUserInfo/interfaceUpdateUserSubscribe
	 * @param menuId
	 * @param openid
	 * @param subscribe
	 * @return 返回0表示更新失败 > 0 表示更新成功
	 * @author XGB
	 * @date 2018-01-22 10:15
	 */
	@RequestMapping(value="/interfaceUpdateUserSubscribe",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String interfaceUpdateUserSubscribe(@RequestParam("menuId") String menuId,@RequestParam("openid") String openid,@RequestParam("subscribe") Integer subscribe) {
		try{
			int result = focusedUserInfoService.updateUserSubscribe(menuId,openid,subscribe);
			if(result > 0){
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Openid:"+ openid + " Subscriber unsubscribe Complete!! Date:"+ ToolsDate.getStringDate(ToolsDate.simpleSecond));
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 拉取用户列表  focusedUserInfo/interfaceUpdateUserListFromWeChatAPI?menuId=31
	 * @param menuId 需要拉取更新用户列表的公众号id
	 * @return 返回0表示更新失败 > 0 表示更新成功
	 * @author XGB
	 * @date 2018-01-22 10:15
	 */
	@ResponseBody
    @RequestMapping(value="/interfaceUpdateUserListFromWeChatAPI",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String interfaceUpdateUserListFromWeChatAPI(HttpServletRequest req,HttpServletResponse resp,String menuId){
		resp.setCharacterEncoding("UTF-8");
		/**
		 * 应用场景
		 * 当管理员需要更新用户信息时（由于系统上线时关注公众号的用户并未被录入系统，所以需要同步系统和微信公众号关注的粉丝信息到服务器）
		 * 1.获取数据库的第一条记录，如果没有用户该功能不能正常运行，需要管理者手动取消关注然后再重新关注公众号，将数据录入系统（在关注公众号时系统会将用户信息录入系统）
		 * 2.将获得的第一条记录的openid作为参数去请求微信服务器拉取关注列表
		 * 3.判断next_openid是否存在，存在继续更新数据
		 */
		try {
			//读取Map
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> map = mapPublicNumber.getMap();
			if(map != null && map.size() == 0){
				System.out.println("map not data");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
			String accessToken = map.get("accessToken" + menuId);
			String openPlatform = map.get("openPlatform" + menuId);
			String accountType = map.get("accountType" + menuId);

			String next_openid = focusedUserInfoService.getRandomOneOpenidByParentMenuId(menuId);//获取公众号数据库内的一个用户
			if(next_openid != null){
				if(!"".equals(next_openid)){
					String url = WeChatAPI.User_Array;
					String param = "access_token="+accessToken+"&next_openid="+next_openid;
					String json = ToolsHttpRequest.sendGet(url,param );
					boolean flag = false;
					while (flag == false) {
						flag = userList(next_openid, param, accessToken,menuId, json, url,flag);
					}
					return "1";
				}
			}else{
				return "1";
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 微信来取用户列表
	 * @param next_openid
	 * @param param
	 * @param accessToken
	 * @param json
	 * @param url
	 * @param flag
	 * @return
	 */
	public boolean userList(String next_openid,String param,String accessToken,String menuId,String json,String url,boolean flag){
		if(next_openid != null){
			if(!"".equals(next_openid)){
				param = "access_token="+accessToken+"&next_openid="+next_openid;
				json = ToolsHttpRequest.sendGet(url,param );
				JSONObject josnObject = JSONObject.fromObject(json);
				next_openid = (String) josnObject.get(next_openid);
				saveOrupdateUser(json,accessToken,menuId,josnObject);
				if(next_openid == null){
					flag = true;
				}
			}else{
				flag = true;
			}
		}else{
			flag = true;
		}
		return flag;
	}

	/**
	 * 微信更新拉取列表的用户详细详细
	 * @param json
	 * @param accessToken
	 * @param josnObject
	 */
	public void saveOrupdateUser(String json,String accessToken,String menuId,JSONObject josnObject){
		if(json.indexOf("data") != -1){
			JSONObject dataJSONObject =  (JSONObject) josnObject.get("data");
			JSONArray openidJSONArray = (JSONArray) dataJSONObject.get("openid");
			List openidArray = openidJSONArray.toList(openidJSONArray);
			Iterator<String> iterator = openidArray.iterator();
			while(iterator.hasNext()){
				String openid = iterator.next();
				//保存用户/更新数据库
				WeChatSaveAndUpdateUserMsg.handleUser(openid,menuId);
			}
		}else{
//			System.out.println("不包含");
		}
	}



    /**
     * 加载显示页面 focusedUserInfo/toLord
     * @param content 显示内容
     * @return view
     * @author XGB
     * @date 2018-02-01 14:03
     */
    @RequestMapping("/toLord")
    public ModelAndView toLord(Model model,String content){
        try{
            model.addAttribute("content", content);
        }catch(Exception e){
            e.printStackTrace();
        }
        return toVm("admin/focusUser/toLord");
    }

}
