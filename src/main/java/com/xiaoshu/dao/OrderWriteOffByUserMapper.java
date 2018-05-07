package com.xiaoshu.dao;


import com.xiaoshu.entity.OrderWriteOffByUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface OrderWriteOffByUserMapper {
	/**
	 * 保存核销人员
	 */
	@Insert("insert into order_write_off_by_user(sellerId,userId,states,createDate) values(#{sellerId},#{userId},#{states},#{createDate} )")
	int saveDao(OrderWriteOffByUser bean) throws Exception;
	
	/**
	 * 删除核销人员
	 * @param id
	 */
	@Delete("delete from order_write_off_by_user where id=#{id}")
	int deleteDao(@Param("id")Integer id) throws Exception;
	
	/**
	 * 更新核销人员
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update order_write_off_by_user set userId=#{userId} where id = #{id}")
	int updateDao(OrderWriteOffByUser bean) throws Exception;
	

	/**
	 * 查询商家的核销人员是否存在
	 * @param sellerId
	 * @param userId
	 * @return 1表示存在 0表示不存在
	 * @throws Exception
	 */
	@Select("select count(id) from order_write_off_by_user where sellerId = #{sellerId } AND userId = #{userId } ")
	int existDao(@Param("sellerId") Integer sellerId ,@Param("userId") String userId) throws Exception;
	
	/**
	 * 查询OrderWriteOffByUser
	 * @param id
	 * @return   返回OrderWriteOffByUser
	 * @throws Exception 抛出异常
	 */
	@Select("select * from  order_write_off_by_user where id = #{id } ")
	OrderWriteOffByUser findByIDDao(@Param("id") Integer id) throws Exception;
	
	
	/**
	 * 查询OrderWriteOffByUser列表
	 * @param sellerId 商家id
	 * @return   返回OrderWriteOffByUser集合
	 * @throws Exception 抛出异常
	 */
	@Select("select * from  order_write_off_by_user where sellerId = #{sellerId } ")
	List<OrderWriteOffByUser> findAllWaterBillDao(@Param("sellerId") Integer sellerId) throws Exception;
	
	/**
	 * 统计OrderWriteOffByUser数量
	 * @param sellerId 商家id
	 * @return   返回统计数量
	 * @throws Exception 抛出异常
	 */
	@Select("select count(id) from order_write_off_by_user where sellerId = #{sellerId } )")
	int totalDao(@Param("sellerId") Integer sellerId) throws Exception;


	/**
	 * 查询是否存在核销关系
	 * @param userId
	 * @return 1表示存在 0表示不存在
	 * @throws Exception
	 */
	@Select("select count(*) from order_write_off_by_user where userId = #{userId } ")
	int existUser(@Param("userId") String userId) throws Exception;

}