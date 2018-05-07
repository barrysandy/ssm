package com.xiaoshu.dao;

import com.xiaoshu.entity.SysCode;

import java.util.*;
public interface SysCodeMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-16 16:28
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
    int insert(SysCode record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
    int insertSelective(SysCode record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return SysCode 实体类
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
    SysCode selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
    int updateByPrimaryKeySelective(SysCode record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
    int updateByPrimaryKey(SysCode record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
    List<SysCode> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-16 16:28
	 */
    int selectCount(Map record);

	/**
	 * 通过目录ID查询字典数据
	 * @param categoryId 目录Id
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/18 0018 10:51
	 */
    List<SysCode> getListByCategoryId(String categoryId);
}