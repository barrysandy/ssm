package com.xiaoshu.dao;


import com.xiaoshu.entity.MeetingCoordinate;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface MeetingCoordinateMapper {

	/** save one */
	@Insert("INSERT INTO t_meeting_coordinate (ID, X, Y, COORDINATE, MEETING_ID, NAME , DESC_M) VALUES(#{id},#{x},#{y},#{coordinate},#{meetingId},#{name},#{descM} )")
	Integer save(MeetingCoordinate bean) throws Exception;


	/** update all */
	@Update("UPDATE t_meeting_coordinate SET X=#{x},Y=#{y},COORDINATE=#{coordinate},MEETING_ID=#{meetingId},NAME=#{name},DESC_M=#{descM} WHERE ID=#{id} ")
	Integer updateAll(MeetingCoordinate bean) throws Exception;

	/** delete ById */
	@Delete("DELETE FROM t_meeting_coordinate WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id) throws Exception;

	/** select ById */
	@Select("SELECT ID,X,Y,COORDINATE,MEETING_ID,NAME,DESC_M FROM t_meeting_coordinate WHERE ID=#{id}")
	MeetingCoordinate getById(@Param("id") String id) throws Exception;

	/** select List */
	@Select("SELECT ID,X,Y,COORDINATE,MEETING_ID,NAME,DESC_M FROM t_meeting_coordinate WHERE MEETING_ID=#{meetingId}")
	List<MeetingCoordinate> getListByMeetingId(@Param("meetingId") String meetingId) throws Exception;

	/** select getNameByCoordinate */
	@Select("SELECT ID,X,Y,COORDINATE,MEETING_ID,NAME,DESC_M FROM t_meeting_coordinate WHERE X=#{x} AND Y=#{y} ")
	MeetingCoordinate getNameByCoordinate(@Param("x") String x,@Param("y") String y) throws Exception;
}
