package com.xiaoshu.service;

import com.xiaoshu.entity.MeetingCoordinate;

import java.util.List;

/** 标准版 */
public  interface MeetingCoordinateService {
	/** save one */
	Integer save(MeetingCoordinate bean) throws Exception;


	/** update all */
	Integer updateAll(MeetingCoordinate bean) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	/** select ById */
	MeetingCoordinate getById(String id) throws Exception;


	/** select List */
	List<MeetingCoordinate> getListByMeetingId(String meetingId) throws Exception;

	/** select getNameByCoordinate */
	MeetingCoordinate getNameByCoordinate( String x, String y) throws Exception;
}
