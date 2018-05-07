package com.xiaoshu.service;


import com.xiaoshu.dao.MessageTempleMapper;
import com.xiaoshu.entity.MessageTemple;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 标准版 */
@Service("messageTempleService")
public class MessageTempleServiceImpl implements MessageTempleService{

	@Resource private MessageTempleMapper mapper;

	/** save one */
	@Override
	public Integer save(MessageTemple bean) throws Exception {
		int exit = mapper.countByTTypeAndCId(bean.getTempleType(),bean.getCommodityId());
		if(exit == 0){
			return mapper.save(bean);
		}else{
			return 0;
		}
	}

	/** update templeId */
	@Override
	public Integer updateCodeStateAndCreateTimeById(Integer templeId ,String updateTime,String id) throws Exception {
		return mapper.updateCodeStateAndCreateTimeById(templeId,updateTime,id);
	}

	/** update all */
	@Override
	public Integer updateAll(MessageTemple bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** 按照 商品 id 查询 短信模板集合 */
	@Override
	public List<MessageTemple> listByCommodityId(Integer commodityId) throws Exception {
		return mapper.listByCommodityId(commodityId);
	}

	/** select ByID */
	@Override
	public MessageTemple getById(String id) throws Exception {
		return mapper.getById(id);
	}

	/**
	 * 查询列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param key 关键字
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	@Override
	public List<MessageTemple> listByKey(int index,int pageSize,String key, Integer status,Integer commodityId) throws Exception {
		return mapper.listByKey(index,pageSize,key,status,commodityId);
	}
	/**
	 * 统计
	 * @param key 关键字
	 * @return 返回数量
	 * @throws Exception 抛出异常
	 */
	@Override
	public Integer countByKey(String key,Integer status,Integer commodityId) throws Exception {
		return mapper.countByKey(key,status,commodityId);
	}

	/** 按照模板类型和商品ID统计模板数 用于查询某个类型的模板是否存在 */
	@Override
	public Integer countByTTypeAndCId(String templeType,Integer commodityId) throws Exception{
		return mapper.countByTTypeAndCId(templeType,commodityId);
	}

}
