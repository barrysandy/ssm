package com.xiaoshu.dao;


import com.xiaoshu.entity.MessageRecord;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/** 标准版 */
public interface MessageRecordMapper {

	/** save one */
	@Insert("INSERT INTO message_record (ID, MOBILE, SIGN, CONTENT, USER_ID, RESPONSE_STATUS, CREATE_TIME, UPDATE_TIME, DESC_M, CODE, STATUS ) " +
			"VALUES(#{id},#{mobile},#{sign},#{content},#{userId},#{responseStatus},#{createTime},#{updateTime},#{descM},#{code},#{status} )")
	Integer save(MessageRecord bean);

	/** update 状态 */
	@Update("UPDATE message_record SET RESPONSE_STATUS=#{responseStatus},UPDATE_TIME=#{updateTime} where ID = #{id}")
	Integer updateResponseStatusById(@Param("responseStatus") String responseStatus, @Param("updateTime") Date updateTime, @Param("id") String id);

	/** update all */
	@Update("UPDATE message_record SET MOBILE=#{mobile},SIGN=#{sign},CONTENT=#{content},USER_ID=#{userId},RESPONSE_STATUS=#{responseStatus}," +
			"CREATE_TIME=#{createTime},UPDATE_TIME=#{updateTime},DESC_M=#{descM},STATUS=#{status} WHERE ID=#{id} ")
	Integer updateAll(MessageRecord bean);

	/** delete ById */
	@Delete("DELETE FROM message_record WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id);

	/** select ById */
	@Select("SELECT ID, MOBILE, SIGN, CONTENT, USER_ID, RESPONSE_STATUS, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS FROM message_record WHERE ID=#{id}")
	MessageRecord getById(@Param("id") String id);

	/** 按照签名或者短信内容查询消息记录集合 */
	List<MessageRecord> listByKeyWord(@Param("index") Integer index,@Param("pageSize") Integer pageSize,@Param("keyword") String keyword,@Param("date1") String date1,@Param("date2") String date2);

	/** 按照签名或者短信内容统计 */
	Integer countByKeyWord(@Param("keyword") String keyword,@Param("date1") String date1,@Param("date2") String date2);

	/** 按照签名或者短信内容统计 */
	@Select("SELECT COUNT(ID) FROM  message_record WHERE CODE = #{code}")
	Integer countByCode(@Param("code") String code);

}
