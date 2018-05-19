package com.xiaoshu.service;

import com.xiaoshu.entity.MeetingCard;

import java.util.List;

/** 标准版 */
public  interface MeetingCardService {

	/** save one */
	Integer save(MeetingCard bean) throws Exception;


	/** update all */
	Integer updateAll(MeetingCard bean) throws Exception;

	/** delete ById */
	Integer deleteById( String id) throws Exception;

	/** select ById */
	MeetingCard getById( String id) throws Exception;


	/** select getCountByUserId */
	MeetingCard getCountByUserId( String userId) throws Exception;

	/** select List */
	List<MeetingCard> getList( Integer index, Integer pageSize ,String key) throws Exception;

	/** Count List */
	Integer getCountList(String key) throws Exception;

}
