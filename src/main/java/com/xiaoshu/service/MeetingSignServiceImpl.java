package com.xiaoshu.service;


import com.xiaoshu.dao.MeetingSignMapper;
import com.xiaoshu.entity.MeetingSign;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsString;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/** 标准版 */
@Service("meetingSignService")
public class MeetingSignServiceImpl implements MeetingSignService{

	@Resource private MeetingSignMapper mapper;

	/** save list */
	@Override
	public void saveList(List<MeetingSign> lsit) throws Exception {
		mapper.saveList(lsit);
	}

	/** save one */
	@Override
	public Integer save(MeetingSign bean) throws Exception {
		bean.setId(UUID.randomUUID().toString());
		bean.setCreateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));
		int count = mapper.getCountUserByNameAndPhone(bean.getName(),bean.getPhone(), bean.getMeetingId());
		if(count == 0){
			return mapper.save(bean);
		}else {
			return -1;
		}
	}

	/** update 状态 */
	@Override
	public Integer updateResponseStatusById( Integer status, String updateTime, String id) throws Exception {
		return mapper.updateResponseStatusById(status, updateTime,id);
	}

	/** update all */
	@Override
	public Integer updateAll(MeetingSign bean) throws Exception {
		bean.setUpdateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));
		return mapper.updateAll(bean);
	}

	/** update join */
	@Override
	public Integer updateJoinById(Integer joinDinner,String id) throws Exception {
		return mapper.updateJoinById(joinDinner,id);
	}

	/** delete ById */
	@Override
	public Integer deleteById( String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** select ById */
	@Override
	public MeetingSign getById( String id) throws Exception {
		return mapper.getById(id);
	}

	/** select List */
	@Override
	public List<MeetingSign> getListByKeyWord(String id ,Integer status , Integer index, Integer pageSize, String keyword) throws Exception {
		return mapper.getListByKeyWord(id,status,index, pageSize, keyword);
	}

	/** Count List */
	@Override
	public Integer getCountByKeyWord(String id ,Integer status ,String keyword) throws Exception {
		return mapper.getCountByKeyWord(id,status,keyword);
	}

	/** get SIGN_CODE */
	@Override
	public String getSignCode() throws Exception {
		String code = ToolsString.generateRandNumber(6);
		try{
			int count = mapper.getCountBySignCode(code);
			if (count > 0){
				getSignCode();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return code;
	}

	/** Count SIGN_user */
	@Override
	public Integer getCountUserByNameAndPhone( String name, String phone,String meetingId) throws Exception {
		return mapper.getCountUserByNameAndPhone(name,phone,meetingId);
	}

	/** delete ByMeetingId */
	@Override
	public Integer deleteByMeetingId(String meetingId) throws Exception {
		return mapper.deleteByMeetingId(meetingId);
	}
}
