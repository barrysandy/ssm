package com.xiaoshu.service;


import com.xiaoshu.dao.OrderRefundMapper;
import com.xiaoshu.entity.OrderRefund;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 标准版 */
@Service("orderRefundService")
public class OrderRefundServiceImpl implements OrderRefundService{

	@Resource private OrderRefundMapper mapper;

	/** save one */
	@Override
	public Integer save(OrderRefund bean) throws Exception {
		return mapper.save(bean);
	}

	/** update 状态 */
	@Override
	public Integer updateTypeStateById( Integer typeState,Integer oldTypeState, String handleTime, String id) throws Exception {
		return mapper.updateTypeStateById(typeState,oldTypeState,handleTime, id);
	}

	/** update all */
	@Override
	public Integer updateAll(OrderRefund bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** update 处理退款的结果 */
	@Override
	public Integer updateRefundResultByOderNo(String refundResult,String orderNo ) throws Exception {
		return mapper.updateRefundResultByOderNo(refundResult,orderNo);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		return mapper.deleteById(id);
	}

	@Override
	public Integer getTypeStateById(String id) throws Exception {
		return mapper.getTypeStateById(id);
	}

	@Override
	public Integer countByOrderNo(String orderNo) throws Exception {
		return mapper.countByOrderNo(orderNo);
	}

	/** getById */
	@Override
	public OrderRefund getById(String id) throws Exception {
		return mapper.getById(id);
	}

	@Override
	public OrderRefund getByOrderNo(String orderNo) throws Exception {
		return mapper.getByOrderNo(orderNo);
	}

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
	@Override
	public List<OrderRefund> listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(int index,int pageSize,String date1 ,String date2,String key, Integer typeState,  Integer status, String userId, Integer sellerId) throws Exception {
		return mapper.listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(index, pageSize , date1 , date2, key, typeState, status, userId, sellerId);
	}
	/**
	 * 统计订单
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询退款申请的订单名称订单号列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	@Override
	public Integer countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(String date1,String date2,String key,Integer typeState,Integer status,String userId,Integer sellerId) throws Exception {
		return mapper.countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(date1, date2, key, typeState, status, userId,sellerId);
	}


}
