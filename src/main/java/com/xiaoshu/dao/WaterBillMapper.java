package com.xiaoshu.dao;

import com.xiaoshu.entity.WaterBill;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface WaterBillMapper {

	@Insert("insert into water_bill(REMARKS,ORDER_NO,CREATE_TIME,UPDATE_TIME,MONEY,STATUS,OPENID,USER_ID) values(#{remarks},#{orderNo},#{createTime},#{updateTime},#{money},#{status},#{openid},#{userId} )")
	int save(WaterBill waterBill);

	@Delete("delete from water_bill where ID=#{id}")
	int deleteById(@Param("wb_id")Integer id);
	
	@Select("select * from water_bill where ID = #{id}")
	WaterBill getById(@Param("id")Integer id);
	
	@Select("select count(ID) from water_bill where ORDER_NO = #{orderNo }")
	int existByOrderNo(@Param("orderNo") String orderNo );

	@Select("select * from water_bill where ORDER_NO = #{orderNo}")
	WaterBill getByOrderNo(@Param("orderNo") String orderNo);

	List<WaterBill> getListByKey(@Param("index") int index, @Param("pageSize") int pageSize,@Param("date1") String date1, @Param("date2") String date2, @Param("key") String key, @Param("status") int status);

	int getTotalListByKey(@Param("date1") String date1,@Param("date2") String date2,@Param("key") String key, @Param("status") int status);

	@Update("update water_bill set STATUS = #{status} where ID = #{id} ")
	int updateStateById(@Param("id")Integer id,@Param("status")Integer status);

	@Update("update water_bill set STATUS = #{status},REMARKS=#{remarks},REMARKS=#{remarks},ORDER_NO=#{orderNo},CREATE_TIME=#{createTime},UPDATE_TIME=#{updateTime},MONEY=#{money},STATUS=#{status},OPENID=#{openid},USER_ID=#{userId} where ID = #{id} ")
	int updateAllById(WaterBill waterBill);

}