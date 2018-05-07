package com.xiaoshu.service;

import com.xiaoshu.entity.SysCodeCategory;

import java.util.*;

/**
 * 系统字典目录表
 * @author: Kun
 * @date: 2018-01-16 16:29
 */
public interface SysCodeCategoryService {

	/**
	 * 分页查询
	 * @return List
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
	List<SysCodeCategory> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
	SysCodeCategory selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
	 int update(SysCodeCategory bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
	 int insert(SysCodeCategory bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-16 16:29
	 */
	 int delete(String id);
	
}
