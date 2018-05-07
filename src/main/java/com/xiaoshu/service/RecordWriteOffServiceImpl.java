package com.xiaoshu.service;


import javax.annotation.Resource;

import com.xiaoshu.dao.RecordWriteOffMapper;
import com.xiaoshu.entity.RecordWriteOff;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("recordWriteOffService")
public class RecordWriteOffServiceImpl implements RecordWriteOffService{


	@Resource private RecordWriteOffMapper mapper;


	@Override
	public int saveRecordWriteOffService(RecordWriteOff recordWriteOff) throws Exception {
		return mapper.saveRecordWriteOff(recordWriteOff);
	}

	@Override
	public List<RecordWriteOff> findRecordWriteOffBySellerIdService(int index, int pageSize, String sellerId) throws Exception {
		return mapper.findRecordWriteOffBySellerId(index,pageSize,sellerId);
	}

	@Override
	public int totalRecordWriteOffBySellerIdService(String sellerId) throws Exception {
		return mapper.totalRecordWriteOffBySellerId(sellerId);
	}

	@Override
	public List<RecordWriteOff> findRecordWriteOffByUserIdService(int index, int pageSize, String userId) throws Exception {
		return mapper.findRecordWriteOffByUserId(index,pageSize,userId);
	}

	@Override
	public int totalRecordWriteOffByUserIdService(String userId) throws Exception {
		return mapper.totalRecordWriteOffByUserId(userId);
	}

	@Override
	public 	int deleteById(String id) throws Exception{
		return mapper.deleteById(id);
	}

	@Override
	public List<RecordWriteOff> findRecordWriteOffByOrderUserId(int index,int pageSize,String orderUserId) throws Exception{
		return mapper.findRecordWriteOffByOrderUserId(index,pageSize,orderUserId);
	}

	@Override
	public int totalRecordWriteOffByOrderUserId(String orderUserId) throws Exception{
		return mapper.totalRecordWriteOffByOrderUserId(orderUserId);
	}
}
