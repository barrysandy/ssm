package com.xiaoshu.service;

import com.xiaoshu.entity.WechatActivity;

import java.util.List;
import java.util.Map;

/**
 * 微信活动表
 * @author: XGB
 * @date: 2018-02-06 16:51
 */
public interface WechatActivityService {

	/**
	 * 分页查询
	 * @return List
     * @author: XGB
     * @date: 2018-02-06 16:51
	 */
	List<WechatActivity> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
     * @author: XGB
     * @date: 2018-02-06 16:51
	 */
	WechatActivity getByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: XGB
     * @date: 2018-02-06 16:51
	 */
	Integer selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 16:51
	 */
	Integer updateRecord(WechatActivity record);
	
	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 16:51
	 */

	Integer saveRecord(WechatActivity record);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 16:51
	 */
	Integer delete(String id);

	/**
	 * 查询所有活动，活动id，title字段
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 11:37
	 */
	List<WechatActivity> getAllActivity();

	/**
	 * 通过主键ID查询活动TITLE
	 * @param id 实体类
	 * @return title
	 * @author: XGB
	 * @date: 2018-02-11 14:14
	 */
	String getTitleByPrimaryKey(String id);

	/**
	 * 通过主键ID查询活动SCAN_USER_ID_ARRAY
	 * @param id 实体类
	 * @return scanUserIdArray
	 * @author: XGB
	 * @date: 2018-03-06 16:37
	 */
	String getScanUserIdArrayById(String id);

	/**
	 * 更新数据
	 * @param id 活动id
	 * @param scanUserIdArray 活动scanUserIdArray
	 * @return int 更新活动
	 * @author: XGB
	 * @date: 2018-03-07 10:04
	 */
	Integer updateScanUser(String id,String scanUserIdArray);


	String getUrlById(String id ) throws Exception;


	/** 获取当前正在进行的会话活动 */
	List<WechatActivity> getSignSessionList(String nowTime,String parentMenuId) throws Exception;

}
