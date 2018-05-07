package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;

import com.xiaoshu.dao.KeyWordsMapper;
import com.xiaoshu.entity.KeyWords;
import org.springframework.stereotype.Service;

/**
 * 关键词回复表
* @author: Kun
* @date: 2018-01-09 10:50
 */
@Service("keyWordsService")
public class KeyWordsServiceImpl implements KeyWordsService {

	@Resource
	private KeyWordsMapper keyWordsMapper;
	@Resource
	private MenuService menuService;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<KeyWords> selectByPage(Map map) {
		return keyWordsMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return keyWordsMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public KeyWords selectByPrimaryKey(String id) {
		return keyWordsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(KeyWords bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return keyWordsMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(KeyWords bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return keyWordsMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		KeyWords bean = keyWordsMapper.selectByPrimaryKey(id);
		bean.setStatus("-1");
		return keyWordsMapper.updateByPrimaryKey(bean);
	}

	/**
	 * 按keyes查询关键字：精准查询
	 * @param menuId PARENT_MENU_ID为公众号的menuId
	 * @param keyes 查询的关键字完全匹配字符
	 * @author: XGB
	 * @return 返回查询到的关键字
	 * @throws Exception
	 */
	@Override
	public KeyWords selectByKey(String menuId,String keyes) {
		return keyWordsMapper.selectByKey(menuId,keyes);
	}

}
