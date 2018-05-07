package com.xiaoshu.service;


import com.xiaoshu.entity.MessageTemple;

import java.util.List;

public interface MessageTempleService {

	/** save one */
	Integer save(MessageTemple bean) throws Exception;

	/** update templeId */
	Integer updateCodeStateAndCreateTimeById(Integer templeId ,String updateTime,String id) throws Exception;

	/** update all */
	Integer updateAll(MessageTemple bean) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	/** 按照 商品 id 查询 短信模板集合 */
	List<MessageTemple> listByCommodityId(Integer commodityId) throws Exception;

	/** select ByID */
	MessageTemple getById(String id) throws Exception;

	/**
	 * 查询列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param key 关键字
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	List<MessageTemple> listByKey(int index,int pageSize,String key, Integer status,Integer commodityId) throws Exception;
	/**
	 * 统计
	 * @param key 关键字
	 * @return 返回数量
	 * @throws Exception 抛出异常
	 */
	Integer countByKey(String key,Integer status,Integer commodityId) throws Exception;

	/** 按照模板类型和商品ID统计模板数 用于查询某个类型的模板是否存在 */
	Integer countByTTypeAndCId(String templeType,Integer commodityId) throws Exception;

}
