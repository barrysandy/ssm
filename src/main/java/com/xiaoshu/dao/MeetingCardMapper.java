package com.xiaoshu.dao;


import com.xiaoshu.entity.MeetingCard;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface MeetingCardMapper {

	/** save one */
	@Insert("INSERT INTO t_meeting_card (ID, NAME, PHONE, ADDRESS, USER_ID, USER_OPENID,NUMBER_TOTAL) VALUES(#{id},#{name},#{phone},#{address},#{userId},#{userOpenid},#{numberTotal} )")
	Integer save(MeetingCard bean) throws Exception;


	/** update all */
	@Update("UPDATE t_meeting_card SET ID=#{id},NAME=#{name},PHONE=#{phone},ADDRESS=#{address},USER_ID=#{userId},USER_OPENID=#{userOpenid},NUMBER_TOTAL=#{numberTotal} WHERE ID=#{id} ")
	Integer updateAll(MeetingCard bean) throws Exception;

	/** delete ById */
	@Delete("DELETE FROM t_meeting_card WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id) throws Exception;

	/** select ById */
	@Select("SELECT ID, NAME, PHONE, ADDRESS, USER_ID, USER_OPENID,NUMBER_TOTAL FROM t_meeting_card WHERE ID=#{id}")
	MeetingCard getById(@Param("id") String id) throws Exception;


	/** select getCountByUserId */
	@Select("SELECT  ID, NAME, PHONE, ADDRESS, USER_ID, USER_OPENID,NUMBER_TOTAL FROM t_meeting_card WHERE USER_ID=#{userId} limit 0,1 ")
	MeetingCard getCountByUserId(@Param("userId") String userId) throws Exception;

	/** select List */
	List<MeetingCard> getList(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("key") String key) throws Exception;

	/** Count List */
	Integer getCountList(@Param("key") String key) throws Exception;

}
