package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;

import com.xiaoshu.dao.InterfaceInfoMapper;
import com.xiaoshu.entity.InterfaceInfo;
import org.springframework.stereotype.Service;

/**
 * 接口配置表
* @author: Kun
* @date: 2018-01-19 10:01
 */
@Service("interfaceInfoService")
public class InterfaceInfoServiceImpl implements InterfaceInfoService {

	@Resource
	private InterfaceInfoMapper interfaceInfoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<InterfaceInfo> selectByPage(Map map) {
		List<InterfaceInfo> list = interfaceInfoMapper.selectByPage(map);
		if(list != null){
			Iterator<InterfaceInfo> iterator = list.iterator();
			while(iterator.hasNext()){
				InterfaceInfo bean = iterator.next();
				if(bean != null){
					if(bean.getType() == 2){ //内部接口
						if(bean.getUrl().indexOf(com.xiaoshu.api.Set.SYSTEM_URL) == -1 ){
							bean.setUrl(com.xiaoshu.api.Set.SYSTEM_URL + bean.getUrl());
						}
					}
				}

			}
		}
		return list;
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return interfaceInfoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public InterfaceInfo selectByPrimaryKey(String id) {
		InterfaceInfo bean = interfaceInfoMapper.selectByPrimaryKey(id);
		if(bean.getUrl().indexOf(com.xiaoshu.api.Set.SYSTEM_URL) == -1 ){
			bean.setUrl(com.xiaoshu.api.Set.SYSTEM_URL + bean.getUrl());
		}
		return bean;
	}

	/**
	 * 更新
	 */
	@Override
	public int update(InterfaceInfo bean) {
		return interfaceInfoMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(InterfaceInfo bean) {
		return interfaceInfoMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		InterfaceInfo bean = interfaceInfoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return interfaceInfoMapper.updateByPrimaryKey(bean);
	}


	/**
	 * 通过KEYES查询
	 * @param keyes 实体类
	 * @return String url
	 * @author: XGB
	 * @date: 2018-01-30 15:24
	 */
	@Override
	public String selectUrlByKeyes(String keyes){
		return  interfaceInfoMapper.selectUrlByKeyes(keyes);
	}
}
