package com.xiaoshu.service;

import javax.annotation.Resource;

import com.xiaoshu.dao.OrderWriteOffByUserMapper;
import com.xiaoshu.entity.OrderWriteOffByUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderWriteOffByUserService")
public class OrderWriteOffByUserServiceImpl implements OrderWriteOffByUserService{
	
	@Resource private OrderWriteOffByUserMapper mapper;

	@Override
	public int saveService(OrderWriteOffByUser bean) throws Exception {
		return mapper.saveDao(bean);
	}

	@Override
	public int deleteService(Integer id) throws Exception {
		return mapper.deleteDao(id);
	}

	@Override
	public int existService(Integer seller_id, String user_id) throws Exception {
		return mapper.existDao(seller_id, user_id);
	}

	@Override
	public List<OrderWriteOffByUser> findAllWaterBillService(Integer seller_id) throws Exception {
		return mapper.findAllWaterBillDao(seller_id);
	}

	@Override
	public int totalService(Integer seller_id) throws Exception {
		return mapper.totalDao(seller_id);
	}

	@Override
	public int updateService(OrderWriteOffByUser bean) throws Exception {
		return mapper.updateDao(bean);
	}

	@Override
	public OrderWriteOffByUser findByIDService(Integer id) throws Exception {
		return mapper.findByIDDao(id);
	}

	@Override
	public int existUser(String userId) throws Exception{
		return mapper.existUser(userId);
	}
}
