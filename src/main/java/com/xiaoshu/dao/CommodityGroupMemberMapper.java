package com.xiaoshu.dao;


import com.xiaoshu.entity.CommodityGroupMember;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface CommodityGroupMemberMapper {

	/**
	 * ID, COMMODITY_ID, CREATE_DATE, DESC_M, USER_ID, LENDER, ORDER_NO,GROUP_ID, STATUS
	 *
	 * id, commodityId, createDate, descM, userId, lender,orderNo,groupId, status
	 */

	/** save one */
	@Insert("INSERT INTO commodity_group_member " +
			"(ID, COMMODITY_ID, CREATE_DATE, DESC_M, USER_ID, LENDER, ORDER_NO,GROUP_ID,STATUS ) " +
			"VALUES(#{id},#{commodityId},#{createDate},#{descM},#{userId},#{lender},#{orderNo},#{groupId},#{status} )")
	Integer save(CommodityGroupMember bean)throws Exception;

	/** update all */
	@Update("UPDATE commodity_group_member SET COMMODITY_ID=#{commodityId},CREATE_DATE=#{createDate},DESC_M=#{descM},USER_ID=#{userId},LENDER=#{lender},ORDER_NO=#{orderNo},GROUP_ID=#{groupId},STATUS=#{status} WHERE ID=#{id} ")
	Integer updateAll(CommodityGroupMember bean)throws Exception;

	/** delete ById */
	@Delete("DELETE FROM commodity_group_member WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id)throws Exception;

	/** get ById */
	@Select("select * from commodity_group_member where ID = #{id}")
	CommodityGroupMember getById(@Param("id") String id)throws Exception;


	/** update Re_Set GROUP_ID LENDER  */
	@Update("UPDATE commodity_group_member SET LENDER = 0 WHERE GROUP_ID = #{groupId} ")
	Integer updateReSetLenderById(@Param("groupId") String groupId)throws Exception;

	/** update LENDER */
	@Update("UPDATE commodity_group_member SET LENDER=#{lender} WHERE ID=#{id} ")
	Integer updateLenderById(@Param("lender") Integer lender, @Param("id") String id)throws Exception;

	/** update Status */
	@Update("UPDATE commodity_group_member SET STATUS=#{status} WHERE ID=#{id} ")
	Integer updateStatusById(@Param("status") Integer status, @Param("id") String id)throws Exception;


	/** LIST By GroupIdAndStatus */
	List<CommodityGroupMember> getListByGroupIdAndStatus(@Param("groupId") String groupId, @Param("status") Integer status)throws Exception;

	/** count ByStatus  */
	Integer countByStatus(@Param("groupId") String groupId,@Param("status") Integer status)throws Exception;

	/** get GROUP_ID */
	@Select("select GROUP_ID from commodity_group_member where COMMODITY_ID = #{commodityId} AND USER_ID = #{userId} ")
	String getGroupIdById(@Param("commodityId") Integer commodityId,@Param("userId") String userId)throws Exception;

	/** get CommodityGroupMember ByUserIdAndCId */
	@Select("select * from commodity_group_member where COMMODITY_ID = #{commodityId} AND USER_ID = #{userId} ")
	CommodityGroupMember getByUserIdAndCId(@Param("commodityId") Integer commodityId,@Param("userId") String userId)throws Exception;

}
