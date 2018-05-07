package com.xiaoshu.service;

import com.xiaoshu.entity.InterfaceInfo;

import java.util.*;

/**
 * 接口配置表
 * @author: Kun
 * @date: 2018-01-19 10:01
 */
public interface InterfaceInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
	List<InterfaceInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 接口配置表实体类
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
	InterfaceInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
	 int update(InterfaceInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
	 int insert(InterfaceInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-19 10:01
	 */
	 int delete(String id);

	/**
	 * 通过KEYES查询
	 * @param keyes 实体类
	 * @return String url
	 * @author: XGB
	 * @date: 2018-01-30 15:24
	 */
	String selectUrlByKeyes(String keyes);
}
