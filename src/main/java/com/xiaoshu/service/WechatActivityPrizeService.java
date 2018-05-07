package com.xiaoshu.service;

import com.xiaoshu.entity.WechatActivityPrize;

import java.util.List;
import java.util.Map;

/**
 * 微信活奖品动表
 * @author: XGB
 * @date: 2018-02-11 10:53
 */
public interface WechatActivityPrizeService {

	/**
	 * 分页查询
	 * @return List
     * @author: XGB
     * @date: 2018-02-11 10:53
	 */
	List<WechatActivityPrize> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
     * @author: XGB
     * @date: 2018-02-11 10:53
	 */
	WechatActivityPrize getByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: XGB
     * @date: 2018-02-11 10:53
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-11 10:53
	 */
	 int updateRecord(WechatActivityPrize record);
	
	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-11 10:53
	 */

	 int saveRecord(WechatActivityPrize record);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 16:51
	 */
	 int delete(String id);

	/**
	 * 按奖品的活动id查询奖品
	 * @param id 活动id
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 15:14
	 */
	List<WechatActivityPrize> getByActivityId(String id);

	/**
	 * 查询所有活动奖品，活动id，name字段
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 15:31
	 */
	List<WechatActivityPrize> getAllActivityPrize();

	/**
	 * 更新奖品的所属活动
	 * @param  id
	 * @param  wechatActivityId
	 * @return int 更新结果
	 * @author: XGB
	 * @date: 2018-02-11 16:50
	 */
	int updateWechatActivityIdById(String id,String wechatActivityId);
}
