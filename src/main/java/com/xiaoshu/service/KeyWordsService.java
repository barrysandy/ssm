package com.xiaoshu.service;

import com.xiaoshu.entity.KeyWords;

import java.util.*;

/**
 * 关键词回复表
 * @author: Kun
 * @date: 2018-01-09 10:50
 */
public interface KeyWordsService {

	/**
	 * 分页查询
	 * @return List
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
	List<KeyWords> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
	KeyWords selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
	 int update(KeyWords bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
	 int insert(KeyWords bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-09 10:50
	 */
	 int delete(String id);

	/**
	 * 按keyes查询关键字：精准查询
	 * @param menuId PARENT_MENU_ID为公众号的menuId
	 * @param keyes 查询的关键字完全匹配字符
	 * @author: XGB
	 * @return 返回查询到的关键字
	 * @throws Exception
	 */
	KeyWords selectByKey(String menuId,String keyes);
}
