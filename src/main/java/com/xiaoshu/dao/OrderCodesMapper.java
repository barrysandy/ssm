package com.xiaoshu.dao;


import com.xiaoshu.entity.OrderCodes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/** 标准版 */
public interface OrderCodesMapper {

	/** save one */
	@Insert("INSERT INTO order_codes (ID, ORDER_ID, ORDER_NO, CODE_NAME, USE_CODE, CODE_IMAGE, CREATE_TIME, USER_ID, COMMODITY_ID, SELLER_ID, CODE_STATE) " +
			"VALUES(#{id},#{orderId},#{orderNo},#{codeName},#{useCode},#{codeImage},#{createTime},#{userId},#{commodityId},#{sellerId},#{codeState} )")
	Integer save(OrderCodes bean) throws Exception;

	/** update 状态 */
	@Update("UPDATE order_codes SET CODE_STATE=#{codeState},CREATE_TIME=#{createTime} where ID = #{id}")
	Integer updateCodeStateAndCreateTimeById(@Param("codeState") Integer codeState,@Param("createTime") String createTime,@Param("id") String id) throws Exception;

	/** update all */
	@Update("UPDATE order_codes SET ORDER_ID=#{orderId},ORDER_NO=#{orderNo},CODE_NAME=#{codeName},USE_CODE=#{useCode},CODE_IMAGE=#{codeImage}," +
			"CREATE_TIME=#{createTime},USER_ID=#{userId},COMMODITY_ID=#{commodityId},SELLER_ID=#{sellerId},CODE_STATE=#{codeState} WHERE ID=#{id} ")
	Integer updateAll(OrderCodes bean) throws Exception;

	/** delete ById */
	@Delete("DELETE FROM order_codes WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id) throws Exception;

	/** 按照订单id以及核销码状态查询核销码集合 */
	@Select("SELECT * FROM order_codes WHERE ORDER_ID = #{orderId} AND CODE_STATE != #{codeState}")
	List<OrderCodes> listByOrderIdAndCodeState(@Param("orderId") Integer orderId,@Param("codeState") String codeState) throws Exception;

	/** 按照订单id查询核销码集合 */
	@Select("SELECT * FROM order_codes WHERE ORDER_ID = #{orderId}")
	List<OrderCodes> listByOrderId(@Param("orderId") Integer orderId) throws Exception;


	/** 按照订单id查询一个核销码，只对单一核销码使用 */
	@Select("SELECT * FROM order_codes WHERE ORDER_ID = #{orderId} LIMIT 0,1")
	OrderCodes getOneByOrderId(@Param("orderId") Integer orderId) throws Exception;

	/** 按照id查询核销码 */
	OrderCodes getById(@Param("id") Integer id) throws Exception;

	/** 统计核销码总数 */
	@Select("SELECT COUNT(ID) FROM order_codes")
	Integer countAll() throws Exception;

	/** 统计某个核销码数量 */
	@Select("SELECT COUNT(ID) FROM order_codes WHERE USE_CODE = #{useCode} ")
	Integer countByUseCode(@Param("useCode") String useCode) throws Exception;

}
