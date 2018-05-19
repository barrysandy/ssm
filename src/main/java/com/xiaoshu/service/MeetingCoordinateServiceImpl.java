package com.xiaoshu.service;


import com.xiaoshu.dao.MeetingCoordinateMapper;
import com.xiaoshu.entity.MeetingCoordinate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/** 标准版 */
@Service("meetingCoordinateService")
public class MeetingCoordinateServiceImpl implements MeetingCoordinateService{

	@Resource private MeetingCoordinateMapper mapper;

	/** save one */
	@Override
	public Integer save(MeetingCoordinate bean) throws Exception {
		if(bean != null){
			if(bean.getId() != null){
				if("".equals(bean.getId()) || "-1".equals(bean.getId())){
					bean.setId(UUID.randomUUID().toString());
				}
			}
			if(bean.getX() != null && bean.getY() != null){
				bean.setCoordinate(bean.getX() + "," + bean.getY());
			}
		}
		return mapper.save(bean);
	}


	/** update all */
	@Override
	public Integer updateAll(MeetingCoordinate bean) throws Exception {
		if(bean != null){
			if(bean.getX() != null && bean.getY() != null){
				bean.setCoordinate(bean.getX() + "," + bean.getY());
			}
		}

		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		return mapper.deleteById(id);
	}
	/** select ById */
	@Override
	public MeetingCoordinate getById(String id) throws Exception {
		return mapper.getById(id);
	}


	/** select List */
	@Override
	public List<MeetingCoordinate> getListByMeetingId(String meetingId) throws Exception {
		return mapper.getListByMeetingId(meetingId);
	}

	/** select getNameByCoordinate */
	@Override
	public MeetingCoordinate getNameByCoordinate( String x, String y) throws Exception{
		return mapper.getNameByCoordinate(x,y);
	}
}
