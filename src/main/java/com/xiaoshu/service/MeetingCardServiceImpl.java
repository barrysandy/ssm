package com.xiaoshu.service;


import com.xiaoshu.dao.MeetingCardMapper;
import com.xiaoshu.entity.MeetingCard;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 标准版 */
@Service("meetingCardService")
public class MeetingCardServiceImpl implements MeetingCardService{

	@Resource private MeetingCardMapper mapper;

	/** save one */
	@Override
	public Integer save(MeetingCard bean) throws Exception {
		return mapper.save(bean);
	}


	/** update all */
	@Override
	public Integer updateAll(MeetingCard bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById( String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** select ById */
	@Override
	public MeetingCard getById( String id) throws Exception {
		return mapper.getById(id);
	}


	/** select getCountByUserId */
	@Override
	public MeetingCard getCountByUserId( String userId) throws Exception {
		return mapper.getCountByUserId(userId);
	}

	/** select List */
	@Override
	public List<MeetingCard> getList(Integer index, Integer pageSize,String key) throws Exception {
		return mapper.getList(index,pageSize,key);
	}

	/** Count List */
	@Override
	public Integer getCountList(String key) throws Exception {
		return mapper.getCountList(key);
	}


}
