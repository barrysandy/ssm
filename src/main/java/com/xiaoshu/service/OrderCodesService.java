package com.xiaoshu.service;

import com.xiaoshu.entity.OrderCodes;

import java.util.List;

/** 标准版 */
public  interface OrderCodesService {

	/** save one */
	Integer save(OrderCodes bean) throws Exception;

	/** update 状态 */
	Integer updateCodeStateAndCreateTimeById(Integer codeState,String createTime,String id) throws Exception;

	/** update all */
	Integer updateAll(OrderCodes bean) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	/** 按照订单id以及核销码状态查询核销码集合 */
	List<OrderCodes> listByOrderIdAndCodeState(Integer orderId,String codeState) throws Exception;

	/** 按照订单id查询核销码集合 */
	List<OrderCodes> listByOrderId(Integer orderId) throws Exception;


	/** 按照订单id查询一个核销码，只对单一核销码使用 */
	OrderCodes getOneByOrderId(Integer orderId) throws Exception;

	/** 按照id查询核销码 */
	OrderCodes getById(Integer id) throws Exception;

	/** 统计核销码总数 */
	Integer countAll() throws Exception;

	/** 统计某个核销码数量 */
	Integer countByUseCode(String useCode) throws Exception;

}
