package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;

import com.xiaoshu.dao.SysCodeMapper;
import com.xiaoshu.entity.SysCode;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.util.JsonUtils;
import org.springframework.stereotype.Service;

/**
 * 系统字典表
* @author: Kun
* @date: 2018-01-16 16:28
 */
@Service("sysCodeService")
public class SysCodeServiceImpl implements SysCodeService {

	@Resource
	private SysCodeMapper sysCodeMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<SysCode> selectByPage(Map map) {
		return sysCodeMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return sysCodeMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public SysCode selectByPrimaryKey(String id) {
		return sysCodeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(SysCode bean) {
		return sysCodeMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(SysCode bean) {
		return sysCodeMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		SysCode bean = sysCodeMapper.selectByPrimaryKey(id);
		bean.setStatus("-1");
		return sysCodeMapper.updateByPrimaryKey(bean);
	}

	/**
	 * 通过目录ID查询字典数据
	 */
	@Override
	public String getListByCategoryId(String categoryId) {
		List<SysCode> codeList = sysCodeMapper.getListByCategoryId(categoryId);
		return JsonUtils.turnJson(true,"success",codeList);
	}

}
