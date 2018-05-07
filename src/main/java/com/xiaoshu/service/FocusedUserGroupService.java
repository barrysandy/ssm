package com.xiaoshu.service;

import com.xiaoshu.entity.FocusedUserGroup;
import java.util.*;

/**
 * 关注用户分组表
 * @author: Kun
 * @date: 2018-01-10 10:32
 */
public interface FocusedUserGroupService {

	/**
	 * 分页查询
	 * @return List
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
	List<FocusedUserGroup> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
	FocusedUserGroup selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @return 总条数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
	 int update(FocusedUserGroup bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-10 10:32
	 */
	 int insert(FocusedUserGroup bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-10 10:32
	 */
	 int delete(String id);

	 /**
	  * 通过menuId查询用户分组List
	  * @param menuId 系统菜单ID
	  * @return List
	  * @author zhou.zhengkun
	  * @date 2018/01/10 0010 14:03
	  */
	 List<FocusedUserGroup> getListByParentMenuId(String menuId);

	 /**
	  * 用户分组操作
	  * @param groupId 分组ID
	  * @author zhou.zhengkun
	  * @date 2018/01/10 0010 16:58
	  */
	 void userJoinGroup(String groupId,String userId);


	/**
	 * 通过ID查询名字
	 * @param groupId
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/10 0010 17:42
	 */
	String getGroupNameByGroupId(String groupId);
}
