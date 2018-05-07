package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;

import com.xiaoshu.dao.PublicAccountInfoMapper;
import com.xiaoshu.entity.PublicAccountInfo;
import org.springframework.stereotype.Service;

/**
 * 
 * 公众号基础信息表
* @author Kun
* @date 2018-01-03 17:04
 */
@Service("publicAccountInfoService")
public class PublicAccountInfoServiceImpl implements PublicAccountInfoService {

	@Resource
	private PublicAccountInfoMapper mapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<PublicAccountInfo> selectByPage(Map map) {
		return mapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return mapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public PublicAccountInfo selectByPrimaryKey(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(PublicAccountInfo bean) {
		return mapper.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 新增
	 */
	@Override
	public int insert(PublicAccountInfo bean) {
		return mapper.insert(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		PublicAccountInfo bean = mapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return mapper.updateByPrimaryKey(bean);
	}

	@Override
	public PublicAccountInfo getInfoByUserId(Integer userId,String menuId) {
		Map<String,Object> paraMap = new HashMap<String,Object>(4);
		paraMap.put("userId",userId);
		paraMap.put("menuId",menuId);
		return mapper.getInfoByUserId(paraMap);
	}

	/**
	 * 查询PublicAccountInfo
	 * @param menuId 公众号menuId
	 * @return
	 */
	@Override
	public PublicAccountInfo getInfoByMenuid(String menuId) {
		return mapper.getInfoByMenuid(menuId);
	}

	@Override
	public PublicAccountInfo getByParentMenuId(String parentMenuId) {
		return mapper.getByParentMenuId(parentMenuId);
	}

	@Override
	public List<PublicAccountInfo> selectListAll(int usable) {
		return mapper.selectListAll(usable);
	}

	@Override
	public String getParentMenuIdByOpenPlatform(String openPlatform,String parentMenuId){
		return mapper.getParentMenuIdByOpenPlatform(openPlatform,parentMenuId);
	}


	@Override
	public String getIdByParentMenuId(String parentMenuId){
		return mapper.getIdByParentMenuId(parentMenuId);
	}

	@Override
	public String getOtherParentMenuIdByMenuId(String parentMenuId){
		return mapper.getOtherParentMenuIdByMenuId(parentMenuId);
	}

	@Override
	public String getParentMenuIdById(String id) throws Exception{
		return mapper.getParentMenuIdById(id);
	}

	/** 获取公众号类型 */
	@Override
	public int getAccountTypeByParentMenuId( String parentMenuId) throws Exception{
		return mapper.getAccountTypeByParentMenuId(parentMenuId);
	}
}
