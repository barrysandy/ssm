package com.xiaoshu.service;

import com.xiaoshu.entity.SysCode;

import java.util.*;

/**
 * 系统字典表
 * @author: Kun
 * @date: 2018-01-16 16:28
 */
public interface SysCodeService {

	/**
	 * 分页查询
	 * @return List
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
	List<SysCode> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
	SysCode selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
	 int update(SysCode bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
	 int insert(SysCode bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-16 16:28
	 */
	 int delete(String id);

	 /**
	  * 通过目录ID查询字典数据
	  * @param categoryId 目录Id
	  * @return String
	  * @author zhou.zhengkun
	  * @date 2018/01/18 0018 10:51
	  */
	  String getListByCategoryId(String categoryId);
}
