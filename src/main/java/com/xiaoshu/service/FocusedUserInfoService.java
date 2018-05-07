package com.xiaoshu.service;

import com.xiaoshu.entity.FocusedUserInfo;

import java.util.List;
import java.util.Map;

/**
 * 关注用户信息表
 * @author: Kun
 * @date: 2018-01-04 15:28
 */
public interface FocusedUserInfoService {

	/**
	 * 分页查询
	 * @return List
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
	List<FocusedUserInfo> selectByPage(Map map) throws Exception;
	
	/**
 	 * 通过主键ID查询
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
	FocusedUserInfo selectByPrimaryKey(String id) throws Exception;
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
	int selectCount(Map record) throws Exception;
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
	 int update(FocusedUserInfo bean) throws Exception;

	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
	 * @author: Kun
	 * @date: 2018-01-04 15:28
	 */
	int updateNotSetNickName(FocusedUserInfo bean) throws Exception;
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
	 int insert(FocusedUserInfo bean) throws Exception;
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-04 15:28
	 */
	 int delete(String id) throws Exception;


	/**
	 * 根据openid查询用户是否存在
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-01-18 14:30
	 */
	 int selectExitByOpenid(String openid) throws Exception;

	/**
	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-01-18 15:47
	 */
	int insertUser(FocusedUserInfo record) throws Exception;

	/**
	 * 通过openid查询
	 * @param openid
	 * @return FocusedUserInfo 实体类
	 * @author: XGB
	 * @date: 2018-01-18 15:58
	 */
	FocusedUserInfo selectByOpenid(String openid) throws Exception;

	/**
	 * 通过unionid和parentMenuId查询
	 * @param unionid
	 * @param parentMenuId
	 * @return FocusedUserInfo 实体类
	 * @author: XGB
	 * @date: 2018-02-1 10:17
	 */
	FocusedUserInfo selectByUnionid(String unionid,String parentMenuId) throws Exception;

	/**
	 * 通过parentMenuId查询一个openid
	 * @param parentMenuId
	 * @return openid OR null
	 * @author: XGB
	 * @date: 2018-02-1 10:59
	 */
	String getRandomOneOpenidByParentMenuId(String parentMenuId) throws Exception;

	/**
	 * 更新User信息
	 * @param record
	 * @author: XGB
	 * @return  返回影响数据库的记录条数
	 * @throws Exception 向上抛出异常
	 */
	int updateUser(FocusedUserInfo record) throws Exception;

	/**
	 * 更新User关注状态
	 * @param menuId PARENT_MENU_ID父菜单ID
	 * @param openid 用户标识
	 * @param subscribe 关注状态
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	int updateUserSubscribe(String menuId,String openid,Integer subscribe) throws Exception;

	/**
	 * 通过unionid和parentMenuId查询用户
	 * @param unionid
	 * @param parentMenuId
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	FocusedUserInfo getByUnionidAndParentMenuId(String unionid,String parentMenuId) throws Exception;

	FocusedUserInfo getOtherByUnionidAndParentMenuId( String unionid, String parentMenuId) throws Exception;

	FocusedUserInfo getOtherByUnionidAndId( String unionid, String id) throws Exception;

	/**
	 * 通过openid查询ID
	 * @param openid
	 * @return ID
	 * @author: XGB
	 * @date: 2018-03-28 10:24
	 */
	String getIdByOpenid(String openid) throws Exception;

	/**
	 * 更新User联系电话额备注
	 * @param username
	 * @param userphone
	 * @param id
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	Integer updateUserContents(String username, String userphone,String id) throws Exception;


	/** 查询用户信息不全的用户集合 */
	List<FocusedUserInfo> getListNotNameAndHeadUser(Integer index, Integer pageSize, String menuId) throws Exception;

	/**
	 * 统计用户信息不全的用户并补全信息
	 */
	Integer countNotNameAndHeadUser( String menuId) throws Exception;

	String getNickNameByOpenid( String openid) throws Exception;

	/**
	 * 按照关联更新用户信息
	 * @param user 当前的用户
	 * @throws Exception
	 */
	void updateUserInfoByUnionidAndParentMenuId(FocusedUserInfo user) throws Exception;

	/**
	 * 用微信接口更新用户信息
	 * @param menuId 公众号标识
	 * @throws Exception
	 */
	int updateUserInfoByWechatInterface(FocusedUserInfo user,String menuId,String openid) throws Exception;
}
