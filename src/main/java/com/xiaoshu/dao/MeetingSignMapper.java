package com.xiaoshu.dao;


import com.xiaoshu.entity.MeetingSign;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface MeetingSignMapper {

	/** save list */
	void saveList(@Param("list") List<MeetingSign> lsit) throws Exception;

	/** save one */
	@Insert("INSERT INTO t_meeting_sign (ID, NAME, HEAD_IMAGE, PHONE, SIGN_CODE, COMPANY , PERSON_TYPE, POSITION, JOIN_DINNER, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS,MEETING_ID) " +
			"VALUES(#{id},#{name},#{headImage},#{phone},#{signCode},#{company},#{personType},#{position},#{joinDinner},#{createTime},#{updateTime},#{descM},#{status},#{meetingId} )")
	Integer save(MeetingSign bean) throws Exception;

	/** update 状态 */
	@Update("UPDATE t_meeting_sign SET STATUS=#{status},UPDATE_TIME=#{updateTime} where ID = #{id}")
	Integer updateResponseStatusById(@Param("status") Integer status, @Param("updateTime") String updateTime, @Param("id") String id) throws Exception;

	/** update join */
	@Update("UPDATE t_meeting_sign SET JOIN_DINNER=#{joinDinner} where ID = #{id}")
	Integer updateJoinById(@Param("joinDinner") Integer joinDinner, @Param("id") String id) throws Exception;

	/** update all */
	@Update("UPDATE t_meeting_sign SET NAME=#{name},HEAD_IMAGE=#{headImage},PHONE=#{phone},SIGN_CODE=#{signCode},COMPANY=#{company},PERSON_TYPE=#{personType}," +
			"POSITION=#{position},JOIN_DINNER=#{joinDinner},CREATE_TIME=#{createTime},UPDATE_TIME=#{updateTime},DESC_M=#{descM},STATUS=#{status},MEETING_ID=#{meetingId} WHERE ID=#{id} ")
	Integer updateAll(MeetingSign bean) throws Exception;

	/** delete ById */
	@Delete("DELETE FROM t_meeting_sign WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id) throws Exception;

	/** delete ByMeetingId */
	@Delete("DELETE FROM t_meeting_sign WHERE MEETING_ID=#{meetingId}")
	Integer deleteByMeetingId(@Param("meetingId") String meetingId) throws Exception;

	/** select ById */
	@Select("SELECT ID,NAME,HEAD_IMAGE,PHONE,SIGN_CODE,COMPANY,PERSON_TYPE,POSITION,JOIN_DINNER,CREATE_TIME,UPDATE_TIME,DESC_M,STATUS,MEETING_ID " +
			"FROM t_meeting_sign WHERE ID=#{id}")
	MeetingSign getById(@Param("id") String id) throws Exception;

	/** select List */
	List<MeetingSign> getListByKeyWord(@Param("id") String id ,@Param("status") Integer status ,@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("keyword") String keyword ) throws Exception;

	/** Count List */
	Integer getCountByKeyWord(@Param("id") String id ,@Param("status") Integer status ,@Param("keyword") String keyword ) throws Exception;

	/** Count SIGN_CODE */
	@Select("SELECT COUNT(ID) FROM t_meeting_sign WHERE SIGN_CODE = #{signCode} ")
	Integer getCountBySignCode(@Param("signCode") String signCode) throws Exception;

	/** Count SIGN_user */
	@Select("SELECT COUNT(ID) FROM t_meeting_sign WHERE NAME = #{name} AND PHONE = #{phone} AND MEETING_ID =#{}")
	Integer getCountUserByNameAndPhone(@Param("name") String name,@Param("phone") String phone,@Param("meetingId") String meetingId) throws Exception;

}
