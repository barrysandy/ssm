package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;

import com.xiaoshu.dao.SysCodeCategoryMapper;
import com.xiaoshu.entity.SysCodeCategory;
import org.springframework.stereotype.Service;


/**
 * 系统字典目录表
* @author: Kun
* @date: 2018-01-16 16:29
 */
@Service("sysCodeCategoryService")
public class SysCodeCategoryServiceImpl implements SysCodeCategoryService {

	@Resource
	private SysCodeCategoryMapper sysCodeCategoryMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<SysCodeCategory> selectByPage(Map map) {
		return sysCodeCategoryMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return sysCodeCategoryMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public SysCodeCategory selectByPrimaryKey(String id) {
		return sysCodeCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(SysCodeCategory bean) {
		return sysCodeCategoryMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(SysCodeCategory bean) {
		return sysCodeCategoryMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		SysCodeCategory bean = sysCodeCategoryMapper.selectByPrimaryKey(id);
		bean.setStatus("-1");
		return sysCodeCategoryMapper.updateByPrimaryKey(bean);
	}

}
