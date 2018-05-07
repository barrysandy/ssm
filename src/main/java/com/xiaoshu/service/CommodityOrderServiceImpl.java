package com.xiaoshu.service;

import javax.annotation.Resource;

import com.xiaoshu.dao.CommodityOrderMapper;
import com.xiaoshu.entity.CommodityOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commodityOrderService")
public class CommodityOrderServiceImpl implements CommodityOrderService{
	
	@Resource
	private CommodityOrderMapper mapper;

	@Override
	public int saveCommodityOrderService(CommodityOrder commodityOrder) throws Exception {
		return mapper.saveCommodityOrderDao(commodityOrder);
	}

	@Override
	public int deleteCommodityOrderService(int order_id) throws Exception {
		return mapper.deleteCommodityOrderDao(order_id);
	}

	@Override
	public List<CommodityOrder> findAllCommodityOrderByOrderIdService(int order_id) throws Exception {
		return mapper.findAllCommodityOrderByOrderIdDao(order_id);
	}

}
