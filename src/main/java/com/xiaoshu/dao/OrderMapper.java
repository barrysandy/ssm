package com.xiaoshu.dao;


import com.xiaoshu.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface OrderMapper {

	/** save one */
	int save(Order bean);

	/** delete ById */
	@Delete("delete from orders where order_id=#{order_id}")
	Integer deleteById(@Param("id") Integer id) throws Exception;

	/** 更新订单信息 */
	@Update("UPDATE orders SET ORDER_NAME=#{orderName}, ORDER_NO=#{orderNo}, CREATE_TIME=#{createTime}, PAYMENT_TIME=#{paymentTime}, DESC_M=#{descM}, NUMBER_DESC_M=#{numberDescM}," +
			"ORDER_AMOUNT_MONEY=#{orderAmountMoney}, TYPE_STATE=#{typeState}, STATUS=#{status}, USER_ID=#{userId}, USER_NAME=#{userName}, USER_PHONE=#{userPhone}, " +
			"USER_USE_TIME=#{userUseTime}, SELLER_ID=#{sellerId}, INVALID_TIME=#{invalidTime}, IMAGE=#{image}, USE_CODE=#{useCode},TRANSACTION_ID=#{transactionId}," +
			"PAY_TYPE=#{payType},NUMBER={number},NUMBER2={number2},COMMODITY_ID=#{commodityId},ORDER_TYPE=#{orderType},GROUP_ID=#{groupId} WHERE ID = #{id}")
	Integer updateAll(Order bean) throws Exception;

	/** 更新订单流程状态 未支付-支付-已完成.. */
	@Update("UPDATE orders SET TYPE_STATE=#{typeState},DESC_M=#{descM} WHERE ORDER_NO = #{orderNo} AND TYPE_STATE = #{oldTypeState} ")
	Integer updateOldTypeStateToOrderStateByOrderNo(@Param("orderNo") String orderNo, @Param("typeState") Integer typeState, @Param("oldTypeState") Integer oldTypeState,@Param("descM") String descM) throws Exception;

	/** 更新订单状态额外状态 */
	@Update("UPDATE orders SET STATUS=#{state} WHERE ORDER_NO = #{orderNo} AND STATUS = #{oldState} ")
	Integer updateOldStateToOrderStateByOrderNo(@Param("orderNo") String orderNo, @Param("state") Integer state, @Param("oldState") Integer oldState) throws Exception;

	/** 更新订单 groupId */
	@Update("UPDATE orders SET GROUP_ID=#{groupId} WHERE ORDER_NO = #{orderNo} ")
	Integer updateGroupIdByOrderNo(@Param("orderNo") String orderNo, @Param("groupId") String groupId ) throws Exception;


	/** 按照 id 查询订单 */
	Order getById(@Param("id") Integer id) throws Exception;

	/** 按照 orderNo 查询订单 */
	Order getByOrderNo(@Param("orderNo") String orderNo ) throws Exception;

	/** 按照 GROUP_ID  USER_ID  查询订单 */
	@Select("SELECT * FROM orders WHERE GROUP_ID =#{groupId } AND USER_ID=#{userId} ")
	Order getByGroupIdAndUserId(@Param("groupId") String groupId ,@Param("userId") String userId) throws Exception;

	/** 按照 GROUP_ID  查询订单集合 */
	@Select("SELECT * FROM orders WHERE GROUP_ID =#{groupId } AND TYPE_STATE=1 ")
	List<Order> getListByGroupId(@Param("groupId") String groupId ) throws Exception;

	/** 按照 id 查询 orderNo */
	@Select("SELECT ID FROM orders WHERE ORDER_NO = #{orderNo }")
	Integer getIdByOrderNo(@Param("orderNo") String orderNo) throws Exception;

	/** 按照 orderNo 查询 id  */
	@Select("SELECT ORDER_NO FROM orders WHERE ID = #{id }")
	String getOrderNoById(@Param("id") Integer id) throws Exception;

	/** 按照 orderNo 查询订单状态（typeState 订单状态 0未付款 1已付款 2已消费  -1查询所有状态） */
	@Select("SELECT TYPE_STATE FROM orders WHERE ORDER_NO = #{orderNo }")
	Integer getTypeStateByOrderNo(@Param("orderNo") String orderNo ) throws Exception;

	/** 查询 orderNo 是否存在 */
	@Select("SELECT COUNT(ID) FROM orders WHERE ORDER_NO = #{orderNo }")
	Integer countByOrderNo(@Param("orderNo") String orderNo ) throws Exception;


	/** 查询今天的订单数量 */
	@Select("SELECT COUNT(ID) FROM orders WHERE CREATE_TIME > #{today }")
	Integer countToday(@Param("today") String today ) throws Exception;

	/** 按商品统计已经购买的订单数量 */
	@Select("SELECT COUNT(ID) FROM orders WHERE COMMODITY_ID = #{commodityId } AND TYPE_STATE >= 1")
	Integer countByCommdityId(@Param("commodityId") Integer commodityId ) throws Exception;


	/** 查询最后一个订单编号 */
	@Select("SELECT ORDER_NO FROM orders ORDER BY ID DESC LIMIT 1 ")
	String getLastOrderNo() throws Exception;

	/**
	 * 查询订单列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询订单列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	 List<Order> listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(@Param("date1") String date1, @Param("date2") String date2, @Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("key") String key,
																	@Param("typeState") Integer typeState, @Param("status") Integer status,@Param("userId") Integer userId, @Param("sellerId") Integer sellerId,@Param("commodityId") Integer commodityId) throws Exception;


	/**
	 * 统计订单
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询订单列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	Integer countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(@Param("date1") String date1, @Param("date2") String date2, @Param("key") String key,
															 @Param("typeState") Integer typeState, @Param("status") Integer status,@Param("userId") Integer userId, @Param("sellerId") Integer sellerId,@Param("commodityId") Integer commodityId) throws Exception;

	/**
	 * 查询订单列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param userId 用户的id，查询某个用户的订单列表
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	List<Order> listByUserId(@Param("index") Integer index,@Param("pageSize") Integer pageSize,@Param("userId") String userId,@Param("userId2") String userId2,@Param("typeState") Integer typeState) throws Exception;

	/**
	 * 统计订单数量
	 * @param userId 用户的id，查询某个用户的订单列表
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	Integer countByUserId(@Param("userId") String userId,@Param("userId2") String userId2,@Param("typeState") Integer typeState) throws Exception;

	/** 绑定订单所属信息 */
	@Update("UPDATE orders SET USER_ID=#{userId} WHERE ORDER_NO = #{orderNo}")
	Integer updateBelongBy(@Param("orderNo") String orderNo,@Param("userId") String userId) throws Exception;

	/** 绑定微信订单号信息 */
	@Update("UPDATE orders SET TRANSACTION_ID=#{transactionId} WHERE ORDER_NO = #{orderNo}")
	Integer updateTransactionIdByOrderOn(@Param("orderNo") String orderNo,@Param("transactionId") String transactionId) throws Exception;

	/** 更新订单的支付类型 */
	@Update("UPDATE orders SET PAY_TYPE=#{payType} WHERE ORDER_NO = #{orderNo} ")
	Integer updatePayTypeByOrderOn(@Param("orderNo") String orderNo,@Param("payType") String payType) throws Exception;

	/** 更新订单电话，联系人，和用户时间信息 */
	@Update("UPDATE orders SET USER_NAME=#{userName},USER_PHONE=#{userPhone},USER_USE_TIME=#{userUseTime} WHERE ORDER_NO = #{orderNo} ")
	Integer updateContactsMsg(@Param("orderNo") String orderNo,@Param("userName") String userName,@Param("userPhone") String userPhone,@Param("userUseTime") String userUseTime) throws Exception;


	/** 按照订单描述和其状态为支付完成的订单列表 */
	@Select("SELECT * FROM orders WHERE DESC_M=#{descM} AND typeState = 1 ORDER BY createDate DESC LIMIT #{index},#{pageSize} ")
	List<Order> listBydescM(@Param("index") Integer index,@Param("pageSize") Integer pageSize,@Param("descM") String descM) throws Exception;

	/**
	 * 统计订单数量
	 * @param descM   订单描述
	 * @return   返回订单数量
	 * @throws Exception 抛出异常
	 */
	/** 统计 按照订单描述和其状态为支付完成的订单列表 */
	@Select("SELECT COUNT(ID) FROM orders WHERE DESC_M=#{descM} AND TYPE_STATE = 1")
	Integer countBydescM(@Param("descM") String descM) throws Exception;


	/** 按照useCode查询所有核销码数量 */
	@Select("SELECT COUNT(USE_CODE) FROM orders WHERE USE_CODE = #{useCode} ")
	Integer countByUseCode(@Param("useCode")String useCode) throws Exception;

	/** 查询所有已经付款的订单，用于群发短信 */
//	@Select("SELECT ID, ORDER_NAME, ORDER_NO,NUMBER_DESC_M,ORDER_AMOUNT_MONEY,TYPE_STATE, STATUS, USER_ID, USER_NAME, USER_PHONE, USER_USE_TIME, SELLER_ID FROM orders WHERE COMMODITY_ID=#{commodityId} AND TYPE_STATE = 1")
	List<Order> listByCommodityIdAndHasPay(@Param("commodityId")Integer commodityId, @Param("groupId")String groupId) throws Exception;


	/** 统计商品订单的成人数量 */
	@Select("SELECT COUNT(NUMBER) FROM orders WHERE COMMODITY_ID =#{commodityId} AND TYPE_STATE = 1")
	Integer countByNUMBER(@Param("commodityId")Integer commodityId) throws Exception;

	/** 统计商品订单的儿童数量 */
	@Select("SELECT COUNT(NUMBER2) FROM orders WHERE COMMODITY_ID =#{commodityId} AND TYPE_STATE = 1")
	Integer countByNUMBER2(@Param("commodityId")Integer commodityId) throws Exception;

}