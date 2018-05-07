package com.xiaoshu.dao;

import com.xiaoshu.entity.FocusedUserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.*;

public interface FocusedUserInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-04 15:28
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
    int insert(FocusedUserInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
    int insertSelective(FocusedUserInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return FocusedUserInfo 实体类
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
    FocusedUserInfo selectByPrimaryKey(String id);

	/**
	 * 通过unionid和parentMenuId查询
	 * @param unionid
	 * @param parentMenuId
	 * @return FocusedUserInfo 实体类
	 * @author: XGB
	 * @date: 2018-02-1 10:17
	 */
	@Select("select * from focused_user_info where UNIONID = #{unionid} AND  PARENT_MENU_ID = #{parentMenuId}")
	FocusedUserInfo selectByUnionid(@Param("unionid") String unionid,@Param("parentMenuId") String parentMenuId);

	/**
	 * 通过parentMenuId查询一个openid
	 * @param parentMenuId
	 * @return openid OR null
	 * @author: XGB
	 * @date: 2018-02-1 10:59
	 */
	@Select("select OPENID from focused_user_info where PARENT_MENU_ID = #{parentMenuId} ORDER BY SUBSCRIBE_TIME LIMIT 0,1")
	String getRandomOneOpenidByParentMenuId(@Param("parentMenuId") String parentMenuId);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
    int updateByPrimaryKeySelective(FocusedUserInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
    int updateByPrimaryKey(FocusedUserInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
    List<FocusedUserInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-04 15:28
	 */
    int selectCount(Map record);

	/**
	 * 根据openid查询用户是否存在
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-01-18 14:30
	 */
	@Select("select count(*) from focused_user_info where OPENID = #{openid}")
	int selectExitByOpenid(@Param("openid") String openid);

	/**
	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-01-18 15:47
	 */
	@Insert("INSERT INTO focused_user_info(ID,OPENID,UNIONID,SUBSCRIBE,MENU_ID,PARENT_MENU_ID,CREATE_TIME,STATUS) " +
            "VALUES(#{id},#{openid},#{unionid},#{subscribe},#{menuId},#{parentMenuId},#{createTime},#{status} )")
	int insertUser(FocusedUserInfo record);

	/**
	 * 通过openid查询
	 * @param openid
	 * @return FocusedUserInfo 实体类
	 * @author: XGB
	 * @date: 2018-01-18 15:58
	 */
	@Select("select * from focused_user_info where OPENID = #{openid}")
	FocusedUserInfo selectByOpenid(@Param("openid") String openid);


	/**
	 * 更新User信息
	 * @param record
	 * @return  返回影响数据库的记录条数
	 * @author: XGB
	 * @throws Exception 向上抛出异常
	 */
	@Update("update focused_user_info set " +
			"NICK_NAME=#{nickName},UNIONID=#{unionid},COUNTRY=#{country},PROVINCE=#{province},CITY=#{city},SEX=#{sex}," +
			"HEAD_IMG_URL=#{headImgUrl},SUBSCRIBE=#{subscribe},SUBSCRIBE_TIME=#{subscribeTime},UPDATE_TIME=#{updateTime},STATUS=#{status} " +
			"where OPENID=#{openid} ")
	int updateUser(FocusedUserInfo record);

	/**
	 * 更新User关注状态
	 * @param menuId PARENT_MENU_ID父菜单ID
	 * @param openid 用户标识
	 * @param subscribe 关注状态
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	@Update("update focused_user_info set SUBSCRIBE=#{subscribe} Where OPENID=#{openid} AND PARENT_MENU_ID=#{menuId} ")
	int updateUserSubscribe(@Param("menuId")String menuId,@Param("openid")String openid,@Param("subscribe")Integer subscribe);

	/**
	 * 通过unionid和parentMenuId查询用户
	 * @param unionid
	 * @param parentMenuId
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	@Select("select * from focused_user_info where UNIONID = #{unionid} AND PARENT_MENU_ID = #{parentMenuId}")
	FocusedUserInfo getByUnionidAndParentMenuId(@Param("unionid")String unionid,@Param("parentMenuId")String parentMenuId);

	/**
	 * 通过unionid和parentMenuId查询用户
	 * @param unionid
	 * @param parentMenuId
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	@Select("select * from focused_user_info where UNIONID = #{unionid} AND PARENT_MENU_ID != #{parentMenuId}")
	FocusedUserInfo getOtherByUnionidAndParentMenuId(@Param("unionid")String unionid,@Param("parentMenuId")String parentMenuId);

	@Select("select * from focused_user_info where UNIONID = #{unionid} AND ID != #{id} " )
	FocusedUserInfo getOtherByUnionidAndId(@Param("unionid")String unionid,@Param("id")String id);

	/**
	 * 通过openid查询ID
	 * @param openid
	 * @return ID
	 * @author: XGB
	 * @date: 2018-03-28 10:24
	 */
	@Select("select ID from focused_user_info where OPENID = #{openid}")
	String getIdByOpenid(String openid);

	/**
	 * 更新User联系电话额备注
	 * @param username
	 * @param userphone
	 * @param id
	 * @author: XGB
	 * @return 返回影响数据库的记录条数
	 */
	@Update("update focused_user_info set USER_NAME=#{username},USER_PHONE=#{userphone} Where ID=#{id} ")
	Integer updateUserContents(@Param("username")String username, @Param("userphone")String userphone,@Param("id")String id);


	/** 查询用户信息不全的用户集合 */
	@Select("SELECT * FROM focused_user_info WHERE NICK_NAME IS NULL OR NICK_NAME = '' AND PARENT_MENU_ID=#{menuId} LIMIT #{index},#{pageSize} ")
	List<FocusedUserInfo> getListNotNameAndHeadUser(@Param("index") Integer index, @Param("pageSize") Integer pageSize,@Param("menuId") String menuId) ;

	/**
	 * 统计用户信息不全的用户并补全信息
	 */
	@Select("SELECT COUNT(ID) FROM focused_user_info WHERE NICK_NAME IS NULL OR NICK_NAME = '' AND PARENT_MENU_ID=#{menuId} ")
	Integer countNotNameAndHeadUser( @Param("menuId") String menuId);


	/** 根据openid 查询 NICK_NAME */
	@Select("SELECT NICK_NAME FROM focused_user_info WHERE OPENID = #{openid} ")
	String getNickNameByOpenid( @Param("openid") String openid) ;

}