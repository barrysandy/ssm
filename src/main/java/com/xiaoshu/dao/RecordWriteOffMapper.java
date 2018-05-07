package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.RecordWriteOff;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface RecordWriteOffMapper {

	/**
	 * 保存一个RecordWriteOff
	 */
	@Insert("insert into record_write_off(ID,CODE,ORDER_NO,SELLER_ID,USER_ID,CREATE_TIME,ORDER_USER_ID) "
			+ "values(#{id}, #{code},#{orderNo},#{sellerId},#{userId},#{createTime},#{orderUserId} )")
	int saveRecordWriteOff(RecordWriteOff recordWriteOff) throws Exception;

	/**
	 * 按照sellerId查询RecordWriteOff
	 * @param sellerId
	 * @return 返回RecordWriteOff集合
	 * @throws Exception 抛出异常
	 */
	@Select("select ID as id,CODE as code,ORDER_NO as orderNo,SELLER_ID as sellerId,USER_ID as userId,CREATE_TIME as createTime ORDER_USER_ID as orderUserId from  record_write_off where SELLER_ID =#{sellerId} order by CREATE_TIME desc limit #{index},#{pageSize} ")
	List<RecordWriteOff> findRecordWriteOffBySellerId(@Param("index") int index,@Param("pageSize") int pageSize,@Param("sellerId") String sellerId) throws Exception;
	
	/**
	 * 按照sellerId统计查询到的RecordWriteOff
	 * @param sellerId
	 * @return
	 * @throws Exception
	 */
	@Select("select COUNT(ID) from  record_write_off WHERE SELLER_ID = #{sellerId} ")
	int totalRecordWriteOffBySellerId(@Param("sellerId") String sellerId) throws Exception;

	/**
	 * 按照userId查询RecordWriteOff
	 * @param userId
	 * @return 返回RecordWriteOff集合
	 * @throws Exception 抛出异常
	 */
	@Select("select * from  record_write_off where USER_ID =#{userId} order by CREATE_TIME desc limit #{index},#{pageSize} ")
	List<RecordWriteOff> findRecordWriteOffByUserId(@Param("index") int index,@Param("pageSize") int pageSize,@Param("userId") String userId) throws Exception;

	/**
	 * 按照userId统计查询到的RecordWriteOff
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Select("select COUNT(ID) from  record_write_off WHERE USER_ID = #{userId} ")
	int totalRecordWriteOffByUserId(@Param("userId") String userId) throws Exception;

	/**
	 * 按照orderUserId查询RecordWriteOff
	 * @param orderUserId
	 * @return 返回RecordWriteOff集合
	 * @throws Exception 抛出异常
	 */
	@Select("select * from  record_write_off where ORDER_USER_ID =#{orderUserId} order by CREATE_TIME desc limit #{index},#{pageSize} ")
	List<RecordWriteOff> findRecordWriteOffByOrderUserId(@Param("index") int index,@Param("pageSize") int pageSize,@Param("orderUserId") String orderUserId) throws Exception;

	/**
	 * 按照orderUserId统计查询到的RecordWriteOff
	 * @param orderUserId
	 * @return
	 * @throws Exception
	 */
	@Select("select COUNT(ID) from  record_write_off WHERE ORDER_USER_ID = #{orderUserId} ")
	int totalRecordWriteOffByOrderUserId(@Param("orderUserId") String orderUserId) throws Exception;

	@Delete("delete from record_write_off where ID = #{id}")
	int deleteById(@Param("id") String id) throws Exception;
}
