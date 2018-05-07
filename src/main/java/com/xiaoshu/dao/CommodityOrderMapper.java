package com.xiaoshu.dao;


import com.xiaoshu.entity.CommodityOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CommodityOrderMapper {
	/**
	 * 保存一个订单商品
	 */
	@Insert("insert into commodityorder(commodity_id,commodity_number,commodity_price,order_id) "
			+ "values(#{commodity_id}, #{commodity_number},#{commodity_price},#{order_id} )")
	int saveCommodityOrderDao(CommodityOrder commodityOrder) throws Exception;
	
	/**
	 * 删除订单商品
	 * @param order_id 订单id
	 */
	@Delete("delete from commodityorder where order_id=#{order_id}")
	int deleteCommodityOrderDao(@Param("order_id") int order_id) throws Exception;
	

	/**
	 * 按照order_id查询订单商品
	 * @param order_id
	 * @return 返回商订单品集合
	 * @throws Exception 抛出异常
	 */
	
	List<CommodityOrder> findAllCommodityOrderByOrderIdDao(@Param("order_id") int order_id) throws Exception;

}
