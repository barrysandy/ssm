package com.xiaoshu.dao;

import com.xiaoshu.entity.WeChatMenu;
import java.util.*;
public interface WeChatMenuMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-09 10:53
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
    int insert(WeChatMenu record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
    int insertSelective(WeChatMenu record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WeChatMenu 实体类
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
    WeChatMenu selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
    int updateByPrimaryKeySelective(WeChatMenu record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
    int updateByPrimaryKey(WeChatMenu record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
    List<WeChatMenu> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
    int selectCount(Map record);

	/**
	 * 通过父级ID查询菜单
	 * @param parentId 父级ID
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/18 0018 16:01
	 */
    List<WeChatMenu> getMenuListByParentId(String parentId);

	/**
	 * 精准查询 - menuId/名字 (两者选一个都可以,两个都传默认按照menuId进行查询)
	 * @param map (menuId/menuName)
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/18 0018 16:15
	 */
    WeChatMenu getMenuByIdOrName(Map map);

	/**
	 * 通过一级菜单Id,查询二级菜单
	 * @param firstMenuId 一级菜单Id
	 * @return List
	 * @author zhou.zhengkun
	 * @date 2018/01/22 0022 17:17
	 */
    List<WeChatMenu> getSecondMenuListByFirstMenuId(String firstMenuId);

	/**
	 * 获取配置的menuList
	 * @return List
	 * @author zhou.zhengkun
	 * @date 2018/01/23 0023 14:37
	 */
	List<WeChatMenu> getUpdateWechatMenuList(String parentMenuId);
}