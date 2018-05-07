package com.xiaoshu.dao;

import com.xiaoshu.entity.WechatActivity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface WechatActivityMapper {

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:28
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:28
	 */
    int saveRecord(WechatActivity record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:29
	 */
    int saveSelective(WechatActivity record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WechatActivity 实体类
     * @author: XGB
     * @date: 2018-02-06 11:30
	 */
	WechatActivity getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:30
	 */
    int updateByPrimaryKeySelective(WechatActivity record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:30
	 */
    int updateByPrimaryKey(WechatActivity record);

    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: XGB
     * @date: 2018-02-06 11:31
	 */
    List<WechatActivity> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: XGB
     * @date: 2018-02-06 11:31
	 */
    int selectCount(Map record);

	/**
	 * 查询所有活动，活动id，title字段
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 11:37
	 */
	@Select("SELECT ID,TITLE FROM wechat_activity")
	List<WechatActivity> getAllActivity();


	/**
	 * 通过主键ID查询活动TITLE
	 * @param id 实体类
	 * @return title
	 * @author: XGB
	 * @date: 2018-02-11 14:14
	 */
	@Select("SELECT TITLE FROM wechat_activity where ID=#{id}")
	String getTitleByPrimaryKey(String id);

	/**
	 * 通过主键ID查询活动SCAN_USER_ID_ARRAY
	 * @param id 实体类
	 * @return scanUserIdArray
	 * @author: XGB
	 * @date: 2018-03-06 16:37
	 */
	@Select("SELECT SCAN_USER_ID_ARRAY FROM wechat_activity where ID=#{id}")
	String getScanUserIdArrayById(@Param("id") String id);

	/**
	 * 更新数据
	 * @param id 活动id
	 * @param scanUserIdArray 活动scanUserIdArray
	 * @return int 更新活动
	 * @author: XGB
	 * @date: 2018-03-07 10:04
	 */
	@Update("update wechat_activity set SCAN_USER_ID_ARRAY = #{scanUserIdArray}  where ID=#{id} ")
	int updateScanUser(@Param("id") String id,@Param("scanUserIdArray") String scanUserIdArray);


	@Select("SELECT URL FROM wechat_activity WHERE ID=#{id} ")
	String getUrlById(@Param("id") String id );

	/** 获取当前正在进行的会话活动 */
	List<WechatActivity> getSignSessionList(@Param("nowTime") String nowTime,@Param("parentMenuId") String parentMenuId);

}