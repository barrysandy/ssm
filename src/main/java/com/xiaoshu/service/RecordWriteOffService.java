package com.xiaoshu.service;

import java.util.List;

import com.xiaoshu.entity.RecordWriteOff;

public interface RecordWriteOffService {
	/**
	 * 保存一个RecordWriteOff
	 */
	int saveRecordWriteOffService(RecordWriteOff recordWriteOff) throws Exception;
	

	/**
	 * 按照sellerId查询RecordWriteOff
	 * @param sellerId
	 * @return 返回RecordWriteOff集合
	 * @throws Exception 抛出异常
	 */
	
	List<RecordWriteOff> findRecordWriteOffBySellerIdService(int index,int pageSize,String sellerId) throws Exception;
	
	/**
	 * 按照sellerId统计查询到的RecordWriteOff
	 * @param sellerId
	 * @return
	 * @throws Exception
	 */
	int totalRecordWriteOffBySellerIdService(String sellerId) throws Exception;
	
	/**
	 * 按照userId查询RecordWriteOff
	 * @param userId
	 * @return 返回RecordWriteOff集合
	 * @throws Exception 抛出异常
	 */

	List<RecordWriteOff> findRecordWriteOffByUserIdService(int index,int pageSize,String userId) throws Exception;

	/**
	 * 按照userId统计查询到的RecordWriteOff
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	int totalRecordWriteOffByUserIdService(String userId) throws Exception;

	/**
	 * 按照orderUserId查询RecordWriteOff
	 * @param orderUserId
	 * @return 返回RecordWriteOff集合
	 * @throws Exception 抛出异常
	 */
	List<RecordWriteOff> findRecordWriteOffByOrderUserId(int index,int pageSize,String orderUserId) throws Exception;

	/**
	 * 按照orderUserId统计查询到的RecordWriteOff
	 * @param orderUserId
	 * @return
	 * @throws Exception
	 */
	int totalRecordWriteOffByOrderUserId(String orderUserId) throws Exception;

	int deleteById(String id) throws Exception;
}
