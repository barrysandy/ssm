package com.xiaoshu.service;


import com.xiaoshu.entity.PersistentMessageQueue;

import java.util.List;

/** 标准版 */
public  interface PersistentMessageQueueService {

	/** save one */
	Integer save(PersistentMessageQueue bean) throws Exception;

	/** update 状态和处理结果以及处理时间 */
	Integer updateCodestatusAndResultById( Integer status, String result, String consumeTime, String id) throws Exception;

	/** update all */
	Integer updateAll(PersistentMessageQueue bean) throws Exception;

	/** delete ById */
	Integer deleteById( String id) throws Exception;

	/** 按照条件查询消息队列集合 */
	List<PersistentMessageQueue> listByCondition(int index, int pageSize, String key, Integer rank, String msgFrom , Integer status) throws Exception;

	/** 按照条件统计消息队列数量 */
	Integer countByCondition( String key, Integer rank, String msgFrom , Integer status) throws Exception;

	/** 按照id查询 */
	PersistentMessageQueue getById( String id) throws Exception;

	/** 按照url查询 */
	PersistentMessageQueue getByUrl(String url);

	/** 按照URL统计总数 */
	Integer countByUrl(String url);

	/** 统计消息总数 */
	Integer countAll() throws Exception;

}
