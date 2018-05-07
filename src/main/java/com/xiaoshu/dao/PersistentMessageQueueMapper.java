package com.xiaoshu.dao;


import com.xiaoshu.entity.PersistentMessageQueue;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface PersistentMessageQueueMapper {

	/** save one */
	@Insert("INSERT INTO persistent_message_queue (ID, RANK, URL, MSG_FROM, CREATE_TIME,PRE_EXECUTION_TIME, CONSUME_TIME, DESC_M, RESULT, STATUS) " +
			"VALUES(#{id},#{rank},#{url},#{msgFrom},#{createTime},#{preExecutionTime},#{consumeTime},#{descM},#{result},#{status} )")
	Integer save(PersistentMessageQueue bean);

	/** update 状态和处理结果以及处理时间 */
	@Update("UPDATE persistent_message_queue SET RESULT=#{result},CONSUME_TIME=#{consumeTime},STATUS=#{status} where ID = #{id}")
	Integer updateCodestatusAndResultById(@Param("status") Integer status, @Param("result") String result, @Param("consumeTime") String consumeTime, @Param("id") String id);

	/** update all */
	@Update("UPDATE persistent_message_queue SET RANK=#{rank},URL=#{url},PRE_EXECUTION_TIME=#{preExecutionTime},MSG_FROM=#{msgFrom},CREATE_TIME=#{createTime},CONSUME_TIME=#{consumeTime}," +
			"DESC_M=#{descM},RESULT=#{result},STATUS=#{status} WHERE ID=#{id} ")
	Integer updateAll(PersistentMessageQueue bean);

	/** delete ById */
	@Delete("DELETE FROM persistent_message_queue WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id);

	/** 按照条件查询消息队列集合 */
	List<PersistentMessageQueue> listByCondition(@Param("index") int index, @Param("pageSize") int pageSize, @Param("key") String key, @Param("rank") Integer rank, @Param("msgFrom") String msgFrom , @Param("status") Integer status);

	/** 按照条件统计消息队列数量 */
	Integer countByCondition(@Param("key") String key,@Param("rank") Integer rank, @Param("msgFrom") String msgFrom ,@Param("status") Integer status);

	/** 按照id查询 */
	@Select("SELECT * FROM persistent_message_queue WHERE ID=#{id}")
	PersistentMessageQueue getById(@Param("id") String id);

	/** 按照url查询 */
	@Select("SELECT * FROM persistent_message_queue WHERE URL=#{url}")
	PersistentMessageQueue getByUrl(@Param("url") String url);

	/** 按照URL统计总数 */
	@Select("SELECT COUNT(URL) FROM persistent_message_queue WHERE URL=#{url}")
	Integer countByUrl(@Param("url") String url);

	/** 统计消息总数 */
	@Select("SELECT COUNT(ID) FROM persistent_message_queue")
	Integer countAll();

}
