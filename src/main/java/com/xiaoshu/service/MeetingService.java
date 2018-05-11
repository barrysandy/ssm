package com.xiaoshu.service;

import com.xiaoshu.entity.Meeting;

import java.util.List;

/** 标准版 */
public  interface MeetingService {

	/** save one */
	Integer save(Meeting bean,String oldImage) throws Exception;

	/** update 状态 */
	Integer updateResponseStatusById(Integer status,String updateTime,String id) throws Exception;

	/** update all */
	Integer updateAll(Meeting bean,String oldImage) throws Exception;

	/** update excelPath */
	Integer updateExcelById(String excelPath,String id) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	/** select ById */
	Meeting getById(String id) throws Exception;

	/** select List */
	List<Meeting> getListByKeyWord(Integer status,Integer index, Integer pageSize, String keyword, String date1, String date2) throws Exception;

	/** Count List */
	Integer getCountByKeyWord(Integer status,String keyword,String date1,String date2) throws Exception;

}
