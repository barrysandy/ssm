package com.xiaoshu.dao;

import com.xiaoshu.entity.FocusedUserGroup;

import java.util.*;
public interface FocusedUserGroupMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-10 10:32
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
    int insert(FocusedUserGroup record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
    int insertSelective(FocusedUserGroup record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return FocusedUserGroup 实体类
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
    FocusedUserGroup selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
    int updateByPrimaryKeySelective(FocusedUserGroup record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
    int updateByPrimaryKey(FocusedUserGroup record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
    List<FocusedUserGroup> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
    int selectCount(Map record);

	/**
	 * 通过menuId查询用户分组List
	 * @param menuId 系统菜单ID
	 * @return List
	 * @author zhou.zhengkun
	 * @date 2018/01/10 0010 14:03
	 */
    List<FocusedUserGroup> getListByParentMenuId(String menuId);
}