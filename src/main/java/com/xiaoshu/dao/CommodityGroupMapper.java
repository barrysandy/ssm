package com.xiaoshu.dao;


import com.xiaoshu.entity.CommodityGroup;
import com.xiaoshu.vo.MyGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface CommodityGroupMapper {


	/** save one */
	@Insert("INSERT INTO commodity_group (ID, GROUP_CODE ,COMMODITY_ID, CREATE_DATE,GROUP_TIME, DESC_M, TOTAL_PERSON, MAX_PERSON, STATUS ) " +
			"VALUES(#{id},#{groupCode},#{commodityId},#{createDate},#{groupTime},#{descM},#{totalPerson},#{maxPerson},#{status} )")
	Integer save(CommodityGroup bean);

	/** update all */
	@Update("UPDATE commodity_group SET GROUP_CODE#{groupCode},COMMODITY_ID=#{commodityId},CREATE_DATE=#{createDate},GROUP_TIME=#{groupTime},DESC_M=#{descM},TOTAL_PERSON=#{totalPerson},MAX_PERSON=#{maxPerson},STATUS=#{status} WHERE ID=#{id} ")
	Integer updateAll(CommodityGroup bean);

	/** delete ById */
	@Delete("DELETE FROM commodity_group WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id);

	/** get ById */
	@Select("select * from commodity_group where ID = #{id}")
	CommodityGroup getById(@Param("id") String id);


	/** update MaxPerson */
	@Update("UPDATE commodity_group SET MAX_PERSON=#{maxPerson} WHERE COMMODITY_ID=#{commodityId} ")
	Integer updateMaxPersonByCommdityId(@Param("maxPerson") Integer maxPerson,@Param("commodityId") String commodityId);

	/** update TotalPerson */
	@Update("UPDATE commodity_group SET TOTAL_PERSON=#{totalPerson} WHERE ID=#{id} AND TOTAL_PERSON=#{oldTotalPerson} ")
	Integer updateTotalPersonById(@Param("totalPerson") Integer totalPerson,@Param("oldTotalPerson") Integer oldTotalPerson,@Param("id") String id);

	/** update Status */
	@Update("UPDATE commodity_group SET STATUS=#{status} WHERE ID=#{id} ")
	Integer updateStatusById(@Param("status") Integer status,@Param("id") String id);


	/** LIST ByCommodityId */
	List<CommodityGroup> getListByCIDAndStatus(@Param("index") Integer index, @Param("pageSize") Integer pageSize,@Param("commodityId") Integer commodityId, @Param("status") String status);

	/** count ByStatus  */
	Integer countByStatus(@Param("commodityId") Integer commodityId,@Param("status") Integer status);


	/** LIST MyGroup */
	List<MyGroup> getListMyGroupByUserId(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("userId") String userId);

	/** countMyGroup */
	Integer countMyGroupByUserId(@Param("userId") String userId);

	/** LIST BY STATUS = 0  AND nowTime > GROUP_TIME */
	List<CommodityGroup> getListByStatusAndAfterTime(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("groupTime")String groupTime,@Param("status")Integer status);

	/** 统计最新活动列表 */
	Integer countByStatusAndAfterTime( @Param("groupTime")String groupTime,@Param("status")Integer status);

	@Select("select GROUP_CODE from commodity_group where ID = #{id}")
	String getGroupCodeById(@Param("id") String id);

	@Select("select ID from commodity_group where GROUP_CODE = #{groupCode}")
	String geteIdByGroupCod(@Param("groupCode") String groupCode);
}
