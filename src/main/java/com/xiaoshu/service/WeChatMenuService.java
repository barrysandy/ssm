package com.xiaoshu.service;

import com.xiaoshu.entity.WeChatMenu;
import java.util.*;

/**
 * 微信公众号菜单配置表
 * @author: Kun
 * @date: 2018-01-09 10:53
 */
public interface WeChatMenuService {

	/**
	 * 分页查询
	 * @return List
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
	List<WeChatMenu> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
	WeChatMenu selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
	 int update(WeChatMenu bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:53
	 */
	 int insert(WeChatMenu bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-09 10:53
	 */
	 int delete(String id);

	 /**
	  * 通过父级ID查询菜单
	  * @param parentId 父级ID
	  * @return String
	  * @author zhou.zhengkun
	  * @date 2018/01/18 0018 16:01
	  */
	 String getMenuListByParentId(String parentId);

	/**
	 * 精准查询 - menuId/名字 (两者选一个都可以,两个都传默认按照menuId进行查询)
	 * @param map (menuId/menuName)
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/18 0018 16:15
	 */
	 String getMenuByIdOrName(Map map);

	 /**
	  * 通过一级菜单Id,查询二级菜单
	  * @param firstMenuId 一级菜单Id
	  * @return String
	  * @author zhou.zhengkun
	  * @date 2018/01/22 0022 17:17
	  */
	 String getSecondMenuListByFirstMenuId(String firstMenuId);

	 /**
	  * 获取配置的menuList
	  * @return List
	  * @author zhou.zhengkun
	  * @date 2018/01/23 0023 14:37
	  */
	List<WeChatMenu> getUpdateWechatMenuList(String parentMenuId);
}
