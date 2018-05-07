package com.xiaoshu.service;

import com.xiaoshu.entity.OrderWriteOffByUser;

import java.util.List;

public interface OrderWriteOffByUserService {
	/**
	 * 保存核销人员
	 */
	int saveService(OrderWriteOffByUser bean) throws Exception;
	
	/**
	 * 删除核销人员
	 * @param id
	 */
	int deleteService(Integer id) throws Exception;
	

	/**
	 * 查询商家的核销人员是否存在
	 * @param seller_id
	 * @param user_id
	 * @return 1表示存在 0表示不存在
	 * @throws Exception
	 */
	int existService(Integer seller_id ,String user_id) throws Exception;
	
	/**
	 * 更新核销人员
	 * @param OrderWriteOffByUser
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	int updateService(OrderWriteOffByUser bean) throws Exception;
	
	/**
	 * 查询OrderWriteOffByUser
	 * @param Integer id 
	 * @return   返回OrderWriteOffByUser
	 * @throws Exception 抛出异常
	 */
	OrderWriteOffByUser findByIDService(Integer id) throws Exception;
	
	/**
	 * 查询OrderWriteOffByUser列表
	 * @param Integer seller_id 商家id
	 * @return   返回OrderWriteOffByUser集合
	 * @throws Exception 抛出异常
	 */
	List<OrderWriteOffByUser> findAllWaterBillService(Integer seller_id) throws Exception;
	
	/**
	 * 统计OrderWriteOffByUser数量
	 * @param Integer seller_id 商家id
	 * @return   返回统计数量
	 * @throws Exception 抛出异常
	 */
	int totalService(Integer seller_id) throws Exception;

	/**
	 * 查询是否存在核销关系
	 * @param userId
	 * @return 1表示存在 0表示不存在
	 * @throws Exception
	 */
	int existUser(String userId) throws Exception;
}
