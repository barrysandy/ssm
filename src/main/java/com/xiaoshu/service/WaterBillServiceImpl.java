package com.xiaoshu.service;

import com.xiaoshu.dao.WaterBillMapper;
import com.xiaoshu.entity.WaterBill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账单流水
* @author: XGB
* @date: 2018-01-09 10:50
 */
@Service("waterBillService")
public class WaterBillServiceImpl implements WaterBillService {

	@Resource private WaterBillMapper mapper;



	@Override
	public int save(WaterBill waterBill) throws Exception{
		return mapper.save(waterBill);
	}

	@Override
	public int deleteById(Integer id) throws Exception{
		return mapper.deleteById(id);
	}

	@Override
	public WaterBill getById(Integer id) throws Exception{
		return mapper.getById(id);
	}

	@Override
	public int existByOrderNo(String orderNo ) throws Exception {
		return mapper.existByOrderNo(orderNo);
	}


	@Override
	public WaterBill getByOrderNo(String orderNo) throws Exception {
		return mapper.getByOrderNo(orderNo);
	}

	@Override
	public List<WaterBill> getListByKey(int index,int pageSize,String date1,String date2,String key,int status) throws Exception{
		return mapper.getListByKey(index,pageSize,date1,date2,key,status);
	}

	@Override
	public int getTotalListByKey(String date1,String date2,String key,int status) throws Exception{
		return mapper.getTotalListByKey(date1,date2,key,status);
	}

	@Override
	public int updateStateById(Integer id,Integer status) throws Exception{
		return mapper.updateStateById(id,status);
	}

	@Override
	public int updateAllById(WaterBill waterBill) throws Exception {
		return mapper.updateAllById(waterBill);
	}
}
