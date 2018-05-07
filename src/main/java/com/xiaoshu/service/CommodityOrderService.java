package com.xiaoshu.service;


import com.xiaoshu.entity.CommodityOrder;

import java.util.List;

public interface CommodityOrderService {
	/**
	 * 保存一个订单商品
	 */
	int saveCommodityOrderService(CommodityOrder commodityOrder) throws Exception;
	
	
	/**
	 * 删除订单商品
	 * @param order_id 订单id
	 */
	int deleteCommodityOrderService(int order_id) throws Exception;
	

	/**
	 * 按照order_id查询订单商品
	 * @param order_id
	 * @return 返回商订单品集合
	 * @throws Exception 抛出异常
	 */
	
	List<CommodityOrder> findAllCommodityOrderByOrderIdService(int order_id) throws Exception;

}
