package com.xiaoshu.dao;

import com.xiaoshu.entity.SysCodeCategory;

import java.util.*;
public interface SysCodeCategoryMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-16 16:29
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
    int insert(SysCodeCategory record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
    int insertSelective(SysCodeCategory record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return SysCodeCategory 实体类
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
    SysCodeCategory selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
    int updateByPrimaryKeySelective(SysCodeCategory record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
    int updateByPrimaryKey(SysCodeCategory record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
    List<SysCodeCategory> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-16 16:29
	 */
    int selectCount(Map record);
}