package com.xiaoshu.service;


import javax.annotation.Resource;

import com.xiaoshu.dao.OrderCodesMapper;
import com.xiaoshu.entity.OrderCodes;
import org.springframework.stereotype.Service;

import java.util.List;

/** 标准版 */
@Service("orderCodeService")
public class OrderCodesServiceImpl implements OrderCodesService{

	@Resource private OrderCodesMapper mapper;


	/** save one */
	@Override
	public Integer save(OrderCodes bean) throws Exception {
		return mapper.save(bean);
	}

	/** update 状态 */
	@Override
	public Integer updateCodeStateAndCreateTimeById(Integer codeState,String createTime,String id) throws Exception {
		return mapper.updateCodeStateAndCreateTimeById(codeState,createTime,id);
	}

	/** update all */
	@Override
	public Integer updateAll(OrderCodes bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** 按照订单id以及核销码状态查询核销码集合 */
	@Override
	public List<OrderCodes> listByOrderIdAndCodeState(Integer orderId,String codeState) throws Exception {
		return mapper.listByOrderIdAndCodeState(orderId,codeState);
	}

	/** 按照订单id查询核销码集合 */
	@Override
	public List<OrderCodes> listByOrderId(Integer orderId) throws Exception {
		return mapper.listByOrderId(orderId);
	}


	/** 按照订单id查询一个核销码，只对单一核销码使用 */
	@Override
	public OrderCodes getOneByOrderId(Integer orderId) throws Exception {
		return mapper.getOneByOrderId(orderId);
	}

	/** 按照id查询核销码 */
	@Override
	public OrderCodes getById(Integer id) throws Exception {
		return mapper.getById(id);
	}

	/** 统计核销码总数 */
	@Override
	public Integer countAll() throws Exception {
		return mapper.countAll();
	}

	/** 统计某个核销码数量 */
	@Override
	public Integer countByUseCode(String useCode) throws Exception {
		return mapper.countByUseCode(useCode);
	}

}
