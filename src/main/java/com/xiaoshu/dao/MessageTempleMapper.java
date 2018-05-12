package com.xiaoshu.dao;


import com.xiaoshu.entity.MessageTemple;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface MessageTempleMapper {


	/** save one */
	@Insert("INSERT INTO message_temple (ID,COMMODITY_ID,TEMPLE_NAME, TEMPLE_ID,TEMPLE_TYPE, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS ,SIGN,REF_ID,REF_TYPE) " +
			"VALUES(#{id},#{commodityId},#{templeName},#{templeId},#{templeType},#{createTime},#{updateTime},#{descM},#{status},#{sign},#{refId},#{refType} )")
	Integer save(MessageTemple bean);

	/** update templeId */
	@Update("UPDATE message_temple SET TEMPLE_ID=#{templeId},UPDATE_TIME=#{updateTime} where ID = #{id}")
	Integer updateCodeStateAndCreateTimeById(@Param("templeId") Integer templeId ,@Param("updateTime") String updateTime, @Param("id") String id);

	/** update all */
	@Update("UPDATE message_temple SET COMMODITY_ID=#{commodityId},TEMPLE_NAME=#{templeName},TEMPLE_ID=#{templeId},TEMPLE_TYPE=#{templeType},CREATE_TIME=#{createTime},UPDATE_TIME=#{updateTime},DESC_M=#{descM}," +
			"STATUS=#{status},SIGN=#{sign},REF_ID=#{refId},REF_TYPE=#{refType} WHERE ID=#{id} ")
	Integer updateAll(MessageTemple bean);

	/** delete ById */
	@Delete("DELETE FROM message_temple WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id);

	/** 按照 商品 id 查询 短信模板集合 */
	@Select("SELECT ID,COMMODITY_ID,TEMPLE_NAME, TEMPLE_ID,TEMPLE_TYPE, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS ,SIGN,REF_ID,REF_TYPE " +
			"FROM message_temple WHERE COMMODITY_ID = #{commodityId}")
	List<MessageTemple> listByCommodityId(@Param("commodityId") Integer commodityId);

	/** 按照 引用id 和引用类型查询 短信模板集合 */
	@Select("SELECT ID,COMMODITY_ID,TEMPLE_NAME, TEMPLE_ID,TEMPLE_TYPE, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS ,SIGN,REF_ID,REF_TYPE " +
			"FROM message_temple WHERE REF_ID = #{refId} AND REF_TYPE = #{refType}")
	List<MessageTemple> getListByRefIdAndRefType(@Param("refId") String refId,@Param("refType") String refType);

	/** select ByID */
	@Select("SELECT ID,COMMODITY_ID,TEMPLE_NAME, TEMPLE_ID,TEMPLE_TYPE, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS ,SIGN,REF_ID,REF_TYPE " +
			"FROM message_temple WHERE ID = #{id}")
	MessageTemple getById(@Param("id") String id);

	/**
	 * 查询列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param key 关键字
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	List<MessageTemple> listByKey(@Param("index") int index, @Param("pageSize") int pageSize, @Param("key") String key,@Param("status") Integer status, @Param("commodityId") Integer commodityId);
	/**
	 * 统计
	 * @param key 关键字
	 * @return 返回数量
	 * @throws Exception 抛出异常
	 */
	Integer countByKey(@Param("key") String key,@Param("status") Integer status, @Param("commodityId") Integer commodityId);

	/** 按照模板类型和商品ID统计模板数 用于查询某个类型的模板是否存在 */
	@Select("SELECT COUNT(ID) FROM message_temple WHERE TEMPLE_TYPE = #{templeType} AND COMMODITY_ID = #{commodityId}")
	Integer countByTTypeAndCId(@Param("templeType") String templeType, @Param("commodityId") Integer commodityId);

	@Select("SELECT COUNT(ID) FROM message_temple WHERE TEMPLE_TYPE = #{templeType} AND REF_ID = #{refId}")
	Integer countByRefIdAndType(@Param("templeType") String templeType, @Param("refId") String refId);

	/** getListMeeting */
	List<MessageTemple> getMeetingListByKey(@Param("index") int index,@Param("pageSize") int pageSize,@Param("key") String key,
											@Param("refId") String refId,@Param("refType") String refType) throws Exception;

	/** getCountMeeting */
	Integer getCountMeetingByKey(@Param("key") String key,@Param("refId") String refId,@Param("refType") String refType) throws Exception;

}
