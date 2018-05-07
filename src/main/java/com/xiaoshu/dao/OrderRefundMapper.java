package com.xiaoshu.dao;


import com.xiaoshu.entity.OrderRefund;
import org.apache.ibatis.annotations.*;

import java.util.List;


/** 标准版 */
public interface OrderRefundMapper {

	/** save one */
	@Insert("INSERT INTO order_refund (ID, TRANSACTION_ID, PAY_TYPE, ORDER_NO, CREATE_TIME, HANDLE_TIME, RETURN_TIME, DESC_M," +
			" ORDER_MONEY, REFUND_FEE, REFUND_FEE_TYPE, REFUND_DESC ,REFUND_ACCOUNT , ORDER_NAME , TYPE_STATE , STATUS , USER_ID ,SELLER_ID,REFUND_RESULT) " +
			"VALUES(#{id},#{transactionId},#{payType},#{orderNo},#{createTime},#{handleTime},#{returnTime},#{descM},#{orderMoney}," +
			"#{refundFee},#{refundFeeType},#{refundDesc},#{refundAccount},#{orderName},#{typeState},#{status},#{userId},#{sellerId},#{refundResult} )")
	Integer save(OrderRefund bean);

	/** update 状态 */
	@Update("UPDATE order_refund SET TYPE_STATE=#{typeState},HANDLE_TIME=#{handleTime} where ID = #{id} AND TYPE_STATE=#{oldTypeState}")
	Integer updateTypeStateById(@Param("typeState") Integer typeState,@Param("oldTypeState") Integer oldTypeState ,@Param("handleTime") String handleTime, @Param("id") String id);

	/** update 处理退款的结果 */
	@Update("UPDATE order_refund SET REFUND_RESULT=#{refundResult} where ORDER_NO=#{orderNo}")
	Integer updateRefundResultByOderNo(@Param("refundResult") String refundResult,@Param("orderNo") String orderNo );

	/** update all */
	@Update("UPDATE order_refund SET TRANSACTION_ID=#{transactionId},PAY_TYPE=#{payType},ORDER_NO=#{orderNo},CREATE_TIME=#{createTime},HANDLE_TIME=#{handleTime}," +
			"RETURN_TIME=#{returnTime},DESC_M=#{descM},ORDER_MONEY=#{orderMoney},REFUND_FEE=#{refundFee},REFUND_FEE_TYPE=#{refundFeeType}" +
			",REFUND_DESC=#{refundDesc},REFUND_ACCOUNT=#{refundAccount},ORDER_NAME=#{orderName},TYPE_STATE=#{typeState},STATUS=#{status},USER_ID=#{userId},REFUND_RESULT=#{refundResult} WHERE ID=#{id} ")
	Integer updateAll(OrderRefund bean);

	/** delete ById */
	@Delete("DELETE FROM order_refund WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id);

	@Select("SELECT TYPE_STATE FROM order_refund WHERE ID=#{id}")
	Integer getTypeStateById(@Param("id") String id);

	@Select("SELECT COUNT(ORDER_NO) FROM order_refund WHERE ORDER_NO=#{orderNo}")
	Integer countByOrderNo(@Param("orderNo") String orderNo);

	/** getById */
	@Select("SELECT * FROM order_refund WHERE ID=#{id}")
	OrderRefund getById(@Param("id") String id);

	/** getById */
	@Select("SELECT * FROM order_refund WHERE ORDER_NO=#{orderNo}")
	OrderRefund getByOrderNo(@Param("orderNo") String orderNo);

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
	List<OrderRefund> listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(@Param("index") int index, @Param("pageSize") int pageSize,@Param("date1") String date1, @Param("date2") String date2, @Param("key") String key,
														@Param("typeState") Integer typeState, @Param("status") Integer status, @Param("userId") String userId, @Param("sellerId") Integer sellerId);
	/**
	 * 统计订单
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询退款申请的订单名称订单号列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	Integer countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(@Param("date1") String date1, @Param("date2") String date2, @Param("key") String key,
																@Param("typeState") Integer typeState, @Param("status") Integer status, @Param("userId") String userId, @Param("sellerId") Integer sellerId);


}
