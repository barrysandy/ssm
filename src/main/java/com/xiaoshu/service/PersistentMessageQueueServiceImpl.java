package com.xiaoshu.service;


import com.xiaoshu.dao.PersistentMessageQueueMapper;
import com.xiaoshu.entity.PersistentMessageQueue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("persistentMessageQueueService")
public class PersistentMessageQueueServiceImpl implements PersistentMessageQueueService{
	
	@Resource private PersistentMessageQueueMapper mapper;

	/** save one */
	@Override
	public Integer save(PersistentMessageQueue bean) throws Exception {
		return mapper.save(bean);
	}

	/** update 状态和处理结果以及处理时间 */
	@Override
	public Integer updateCodestatusAndResultById( Integer status, String result, String consumeTime, String id) throws Exception {
		return mapper.updateCodestatusAndResultById( status, result,consumeTime,id);
	}

	/** update all */
	@Override
	public Integer updateAll(PersistentMessageQueue bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById( String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** 按照条件查询消息队列集合 */
	@Override
	public List<PersistentMessageQueue> listByCondition(int index, int pageSize, String key, Integer rank, String msgFrom , Integer status) throws Exception {
		return mapper.listByCondition( index, pageSize, key, rank, msgFrom , status);
	}

	/** 按照条件统计消息队列数量 */
	@Override
	public Integer countByCondition( String key, Integer rank, String msgFrom , Integer status) throws Exception {
		return mapper.countByCondition(key, rank, msgFrom , status);
	}

	/** 按照id查询 */
	@Override
	public PersistentMessageQueue getById( String id) throws Exception {
		return mapper.getById(id);
	}


	/** 按照url查询 */
	@Override
	public PersistentMessageQueue getByUrl(String url) {
		return mapper.getByUrl(url);
	}

	/** 按照URL统计总数 */
	@Override
	public Integer countByUrl(String url) {
		return mapper.countByUrl(url);
	}


	/** 统计消息总数 */
	@Override
	public Integer countAll() throws Exception {
		return mapper.countAll();
	}

}
