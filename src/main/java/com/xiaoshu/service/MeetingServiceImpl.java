package com.xiaoshu.service;


import com.xiaoshu.dao.*;
import com.xiaoshu.entity.Meeting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/** 标准版 */
@Service("meetingService")
public class MeetingServiceImpl implements MeetingService{

	@Resource private MeetingMapper mapper;

	/** save one */
	@Override
	public Integer save(Meeting bean) throws Exception {
		return mapper.save(bean);
	}

	/** update 状态 */
	@Override
	public Integer updateResponseStatusById(Integer status,String updateTime,String id) throws Exception {
		return mapper.updateResponseStatusById(status,updateTime, id);
	}

	/** update all */
	@Override
	public Integer updateAll(Meeting bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** select ById */
	@Override
	public Meeting getById(String id) throws Exception {
		return mapper.getById(id);
	}

	/** select List */
	@Override
	public List<Meeting> getListByKeyWord(Integer status,Integer index, Integer pageSize, String keyword, String date1, String date2) throws Exception {
		return mapper.getListByKeyWord(status,index, pageSize, keyword, date1, date2);
	}

	/** Count List */
	@Override
	public Integer getCountByKeyWord(Integer status,String keyword,String date1,String date2) throws Exception {
		return mapper.getCountByKeyWord(status,keyword, date1, date2);
	}
}
