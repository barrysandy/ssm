package com.xiaoshu.service;

import com.xiaoshu.entity.MeetingSign;

import java.util.List;

/** 标准版 */
public  interface MeetingSignService {

	/** save list */
	void saveList(List<MeetingSign> lsit) throws Exception;

	/** save one */
	Integer save(MeetingSign bean) throws Exception;

	/** update 状态 */
	Integer updateResponseStatusById( Integer status, String updateTime, String id) throws Exception;

	/** update all */
	Integer updateAll(MeetingSign bean) throws Exception;

	/** update join */
	Integer updateJoinById(Integer joinDinner,String id) throws Exception;

	/** delete ById */
	Integer deleteById( String id) throws Exception;

	/** select ById */
	MeetingSign getById( String id) throws Exception;

	/** select BySignCode */
	MeetingSign getBySignCode( String signCode,String id) throws Exception;

	/** select ByPone */
	MeetingSign getByPone( String phone,String id) throws Exception;

	/** select StatusBySignCode */
	Integer getStatusBySignCode( String signCode,String id) throws Exception;

	/** select List */
	List<MeetingSign> getListByKeyWord(String id,Integer status ,Integer index, Integer pageSize, String keyword) throws Exception;

	/** Count List */
	Integer getCountByKeyWord(String id ,Integer status ,String keyword) throws Exception;

	/** get SIGN_CODE */
	String getSignCode() throws Exception;

	/** Count SIGN_user */
	Integer getCountUserByNameAndPhone( String name, String phone,String meetingId) throws Exception;

	/** Count byMeetingId */
	Integer getCountByMeetingId( String meetingId) throws Exception;

	/** delete ByMeetingId */
	Integer deleteByMeetingId(String meetingId) throws Exception;

	/** select getListByMeetingId */
	List<MeetingSign> getListByMeetingId(Integer index,Integer pageSize,String meetingId) throws Exception;

	/** Count getCountStatusByMeetingId */
	Integer getCountStatusByMeetingId(String meetingId,Integer status) throws Exception;

}
