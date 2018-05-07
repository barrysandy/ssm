package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;

import com.xiaoshu.dao.FocusedUserInfoMapper;
import com.xiaoshu.dao.PublicAccountInfoMapper;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsUnicode;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.tools.AdvancedUtil;
import org.springframework.stereotype.Service;


/**
 * 关注用户信息表
* @author: Kun
* @date: 2018-01-04 15:28
 */
@Service("focusedUserInfoService")
public class FocusedUserInfoServiceImpl implements FocusedUserInfoService {

	@Resource private FocusedUserInfoMapper mapper;

	@Resource private MenuService menuService;

	@Resource private PublicAccountInfoMapper publicAccountInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<FocusedUserInfo> selectByPage(Map map) throws Exception{
		List<FocusedUserInfo> list = mapper.selectByPage(map);
		//转NickName
		if(list != null){
			Iterator<FocusedUserInfo> iterator = list.iterator();
			while (iterator.hasNext()){
				FocusedUserInfo user = iterator.next();
				if(user != null){
					//解析NickName
					if(user.getNickName() != null && !"".equals(user.getNickName())){
						user.setNickName(ToolsUnicode.unicode2String(user.getNickName()));
					}
					//关注时间格式化输出
					if(user.getSubscribeTime() != null && !"".equals(user.getSubscribeTime())){
						//subscribe_time为用户关注公众号时间，注意：单位为秒，不是毫秒，要转换为毫秒要乘以1000，这个官网开发文档没有说明。
						Long longdate = Long.parseLong(user.getSubscribeTime())* 1000;
						String subscribeTime = ToolsDate.longTimeFormatStringTime(ToolsDate.simpleMinute,longdate);
						user.setSubscribeTime(subscribeTime);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) throws Exception{
		return mapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public FocusedUserInfo selectByPrimaryKey(String id) throws Exception{
		FocusedUserInfo user = mapper.selectByPrimaryKey(id);
		if(user != null){
			if(user.getNickName() !=null ){
				if(!"".equals(user.getNickName())){
					user.setNickName(ToolsUnicode.unicode2String(user.getNickName()));
				}
			}
			//关注时间格式化输出
			if(user.getSubscribeTime() != null && !"".equals(user.getSubscribeTime())){
				//subscribe_time为用户关注公众号时间，注意：单位为秒，不是毫秒，要转换为毫秒要乘以1000，这个官网开发文档没有说明。
				Long longdate = Long.parseLong(user.getSubscribeTime())* 1000;
				String subscribeTime = ToolsDate.longTimeFormatStringTime(ToolsDate.simpleMinute,longdate);
				user.setSubscribeTime(subscribeTime);
			}
		}
		return user;
	}

	/**
	 * 更新
	 */
	@Override
	public int update(FocusedUserInfo bean)  throws Exception {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		if(bean.getNickName() != null){
			bean.setNickName(ToolsUnicode.string2Unicode(bean.getNickName()));
		}
		return mapper.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
	 * @author: Kun
	 * @date: 2018-01-04 15:28
	 */
	@Override
	public int updateNotSetNickName(FocusedUserInfo bean) throws Exception {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return mapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(FocusedUserInfo bean)  throws Exception {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return mapper.insert(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id)  throws Exception{
		FocusedUserInfo bean = mapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return mapper.updateByPrimaryKey(bean);
	}

	/**
	 * 根据openid查询用户是否存在
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-01-18 14:30
	 */
	@Override
	public int selectExitByOpenid(String openid) throws Exception{
		return mapper.selectExitByOpenid(openid);
	}

	/**
	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-01-18 15:47
	 */
	@Override
	public int insertUser(FocusedUserInfo record) throws Exception{
		return mapper.insertUser(record);
	}

	/**
	 * 通过openid查询
	 * @param openid
	 * @return FocusedUserInfo 实体类
	 * @author: XGB
	 * @date: 2018-01-18 15:58
	 */
	@Override
	public FocusedUserInfo selectByOpenid(String openid) throws Exception{
		FocusedUserInfo user = mapper.selectByOpenid(openid);
		if(user != null){
			if(user.getNickName() !=null ){
				if(!"".equals(user.getNickName())){
					user.setNickName(ToolsUnicode.unicode2String(user.getNickName()));
				}
			}
			//关注时间格式化输出
			if(user.getSubscribeTime() != null && !"".equals(user.getSubscribeTime())){
				//subscribe_time为用户关注公众号时间，注意：单位为秒，不是毫秒，要转换为毫秒要乘以1000，这个官网开发文档没有说明。
				Long longdate = Long.parseLong(user.getSubscribeTime())* 1000;
				String subscribeTime = ToolsDate.longTimeFormatStringTime(ToolsDate.simpleMinute,longdate);
				user.setSubscribeTime(subscribeTime);
			}
		}
		return user;
	}


	/**
	 * 通过unionid和parentMenuId查询
	 * @param unionid
	 * @param parentMenuId
	 * @return FocusedUserInfo 实体类
	 * @author: XGB
	 * @date: 2018-02-1 10:17
	 */
	public FocusedUserInfo selectByUnionid(String unionid,String parentMenuId) throws Exception{
		return mapper.selectByUnionid(unionid,parentMenuId);
	}

	/**
	 * 通过parentMenuId查询一个openid
	 * @param parentMenuId
	 * @return openid OR null
	 * @author: XGB
	 * @date: 2018-02-1 10:59
	 */
	public String getRandomOneOpenidByParentMenuId(String parentMenuId) throws Exception{
		return mapper.getRandomOneOpenidByParentMenuId(parentMenuId);
	}

	/**
	 * 更新User信息
	 * @param record
	 * @return  返回影响数据库的记录条数
	 * @throws Exception 向上抛出异常
	 */
	@Override
	public int updateUser(FocusedUserInfo record) throws Exception{
		return mapper.updateUser(record);
	}


	/**
	 * 更新User关注状态
	 * @param menuId PARENT_MENU_ID父菜单ID
	 * @param openid 用户标识
	 * @param subscribe 关注状态
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	@Override
	public int updateUserSubscribe(String menuId,String openid,Integer subscribe) throws Exception{
		return mapper.updateUserSubscribe(menuId,openid,subscribe);
	}

	/**
	 * 通过unionid和parentMenuId查询用户
	 * @param unionid
	 * @param parentMenuId
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	@Override
	public FocusedUserInfo getByUnionidAndParentMenuId(String unionid,String parentMenuId) throws Exception {
		return mapper.getByUnionidAndParentMenuId(unionid,parentMenuId);
	}

	@Override
	public FocusedUserInfo getOtherByUnionidAndParentMenuId( String unionid, String parentMenuId) throws Exception {
		return mapper.getOtherByUnionidAndParentMenuId(unionid,parentMenuId);
	}


	@Override
	public FocusedUserInfo getOtherByUnionidAndId( String unionid, String id) throws Exception {
		return mapper.getOtherByUnionidAndId(unionid,id);
	}


	/**
	 * 通过openid查询ID
	 * @param openid
	 * @return ID
	 * @author: XGB
	 * @date: 2018-03-28 10:24
	 */
	@Override
	public String getIdByOpenid(String openid) throws Exception{
		return mapper.getIdByOpenid(openid);
	}


	/**
	 * 更新User联系电话额备注
	 * @param username
	 * @param userphone
	 * @param id
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	@Override
	public Integer updateUserContents(String username, String userphone,String id) throws Exception {
		return mapper.updateUserContents(username,userphone,id);
	}



	@Override
	public List<FocusedUserInfo> getListNotNameAndHeadUser(Integer index, Integer pageSize , String menuId) throws Exception {
		return mapper.getListNotNameAndHeadUser(index,pageSize,menuId);
	}

	@Override
	public Integer countNotNameAndHeadUser( String menuId) throws Exception {
		return mapper.countNotNameAndHeadUser(menuId);
	}


	@Override
	public String getNickNameByOpenid( String openid) throws Exception {
		return mapper.getNickNameByOpenid(openid);
	}


	@Override
	public void updateUserInfoByUnionidAndParentMenuId(FocusedUserInfo user) throws Exception {
		FocusedUserInfo userInfoOther;
		if(user.getNickName() == null){
			userInfoOther = mapper.getOtherByUnionidAndParentMenuId(user.getUnionid(),user.getParentMenuId());
			updateUser(userInfoOther,user,menuService,mapper);
		}
		else if(user.getNickName() != null){
			if("".equals(user.getNickName())){
				userInfoOther = mapper.getOtherByUnionidAndParentMenuId(user.getUnionid(),user.getParentMenuId());
				updateUser(userInfoOther,user,menuService,mapper);
			}
		}
	}



	@Override
	public int updateUserInfoByWechatInterface(FocusedUserInfo user,String menuId,String openid) throws Exception {
		//获取公众号Map
		MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
		Map<String, String> map = mapPublicNumber.getMap();
		if(map != null && map.size() == 0){
			System.out.println("map not data");
			JobPublicAccount.ToRefreshMapJobPublicAccount();
		}
		//读取Map中的accessToken
		String accessToken = map.get("accessToken" + menuId);
		//读取Map中的openPlatform 用来判定该公众号是否绑定了开放平台
		//String openPlatform = map.get("openPlatform" + menuId);

		//授权获取用户信息，当然未关注的用户只能获取openid
		user = AdvancedUtil.getUserInfo(accessToken, openid);
		if(user != null){
			user.setStatus(1);
			if(user.getNickName() != null && !"".equals(user.getNickName())){
				user.setNickName(ToolsUnicode.string2Unicode(user.getNickName()));
			}
			String unionid = AdvancedUtil.getUnionidUserInfo(accessToken, openid);
			user.setUnionid(unionid);
			user.setUpdateTime(new Date());
			int i = mapper.updateUser(user);
			if( i > 0){
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Update user id:" + user.getId() + " Openid:" + user.getOpenid() + " complete!! Date:" + ToolsDate.getStringDate(ToolsDate.simpleSecond));
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			}
			return i;
		}else{
			return 0;
		}
	}

	/** 更新用户 */
	private static void updateUser(FocusedUserInfo userInfoOther,FocusedUserInfo user,MenuService menuService,FocusedUserInfoMapper mapper){
		if(userInfoOther != null){
			if(userInfoOther.getNickName() != null){
				if(!"".equals(userInfoOther.getNickName())){
					user.setNickName(userInfoOther.getNickName());
					user.setHeadImgUrl(userInfoOther.getHeadImgUrl());
					user.setCity(userInfoOther.getCity());
					user.setCountry(userInfoOther.getCountry());
					user.setLanguage(userInfoOther.getLanguage());
					user.setProvince(userInfoOther.getProvince());
					user.setSex(userInfoOther.getSex());
					user.setUpdateTime(new Date());
					user.setSubscribeTime(userInfoOther.getSubscribeTime());

					String menuId = userInfoOther.getMenuId();
					String parentMenuId = menuService.getParentIdByMenuId(menuId);

					userInfoOther.setParentMenuId(parentMenuId);

					int result = mapper.updateByPrimaryKeySelective(user);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ UPDATE userInfoOther ^^^^^^^^^^^^^^^^^^^" + user.getOpenid() + " result :" + result);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				}else {
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Dot UPDATE ^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				}
			}else {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Dot UPDATE ^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			}
		}else{
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Not Update Incomplete user information binding ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		}
	}


}
