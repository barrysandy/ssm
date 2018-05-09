package com.xiaoshu.dao;


import com.xiaoshu.entity.Meeting;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface MeetingSignMapper {

	/** save one */
	@Insert("INSERT INTO t_meeting_sign (ID, NAME, HEAD_IMAGE, PHONE, SIGN_CODE, COMPANY , PERSON_TYPE, POSITION, JOIN_DINNER, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS) " +
			"VALUES(#{id},#{name},#{headImage},#{phone},#{signCode},#{company},#{personType},#{position},#{joinDinner},#{createTime},#{updateTime},#{descM},#{status} )")
	Integer save(Meeting bean);

	/** update 状态 */
	@Update("UPDATE t_meeting_sign SET STATUS=#{status},UPDATE_TIME=#{updateTime} where ID = #{id}")
	Integer updateResponseStatusById(@Param("status") Integer status, @Param("updateTime") String updateTime, @Param("id") String id);

	/** update all */
	@Update("UPDATE t_meeting_sign SET NAME=#{name},HEAD_IMAGE=#{headImage},PHONE=#{phone},SIGN_CODE=#{signCode},COMPANY=#{company},PERSON_TYPE=#{personType}," +
			"POSITION=#{position},JOIN_DINNER=#{joinDinner},CREATE_TIME=#{createTime},UPDATE_TIME=#{updateTime},DESC_M=#{descM},STATUS=#{status} WHERE ID=#{id} ")
	Integer updateAll(Meeting bean);

	/** delete ById */
	@Delete("DELETE FROM t_meeting_sign WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id);

	/** select ById */
	@Select("SELECT ID, NAME, HEAD_IMAGE, PHONE, SIGN_CODE, COMPANY , PERSON_TYPE, POSITION, JOIN_DINNER, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS " +
			"FROM t_meeting_sign WHERE ID=#{id}")
	Meeting getById(@Param("id") String id);

	/** select List */
	List<Meeting> getListByKeyWord(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("keyword") String keyword, @Param("date1") String date1, @Param("date2") String date2);

	/** Count List */
	Integer getCountByKeyWord(@Param("keyword") String keyword, @Param("date1") String date1, @Param("date2") String date2);

}
