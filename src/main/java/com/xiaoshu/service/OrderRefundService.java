package com.xiaoshu.service;


import com.xiaoshu.entity.OrderRefund;

import java.util.List;

public interface OrderRefundService {

	/** save one */
	Integer save(OrderRefund bean) throws Exception;

	/** update 状态 */
	Integer updateTypeStateById( Integer typeState,Integer oldTypeState, String handleTime, String id) throws Exception;

	/** update 处理退款的结果 */
	Integer updateRefundResultByOderNo(String refundResult,String orderNo ) throws Exception;

	/** update all */
	Integer updateAll(OrderRefund bean) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	Integer getTypeStateById(String id) throws Exception;

	Integer countByOrderNo(String orderNo) throws Exception;

	/** getById */
	OrderRefund getById(String id) throws Exception;

	OrderRefund getByOrderNo(String orderNo) throws Exception;

	/**
	 * 查询订单申请列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询退款申请的订单名称订单号列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	List<OrderRefund> listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(int index,int pageSize,String date1 ,String date2,String key, Integer typeState,  Integer status, String userId, Integer sellerId) throws Exception;
	/**
	 * 统计订单
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询退款申请的订单名称订单号列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	Integer countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(String date1,String date2,String key,Integer typeState,Integer status,String userId,Integer sellerId) throws Exception;


}
