package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;
import com.xiaoshu.dao.FocusedUserGroupMapper;
import com.xiaoshu.dao.FocusedUserInfoMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.FocusedUserGroup;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.User;
import com.xiaoshu.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 关注用户分组表
* @author: Kun
* @date: 2018-01-10 10:32
 */
@Service("focusedUserGroupService")
public class FocusedUserGroupServiceImpl implements FocusedUserGroupService {

	@Resource
	private FocusedUserGroupMapper focusedUserGroupMapper;
	@Resource
	private FocusedUserInfoMapper focusedUserInfoMapper;
	@Resource
	private MenuService menuService;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<FocusedUserGroup> selectByPage(Map map) {
		return focusedUserGroupMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return focusedUserGroupMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public FocusedUserGroup selectByPrimaryKey(String id) {
		return focusedUserGroupMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(FocusedUserGroup bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return focusedUserGroupMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(FocusedUserGroup bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return focusedUserGroupMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		FocusedUserGroup bean = focusedUserGroupMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return focusedUserGroupMapper.updateByPrimaryKey(bean);
	}

	@Override
	public List<FocusedUserGroup> getListByParentMenuId(String menuId) {
		return focusedUserGroupMapper.getListByParentMenuId(menuId);
	}

	@Override
	public void userJoinGroup(String groupId,String userId) {

		if (userId != null ){
			/*这个用户是更新操作*/
			FocusedUserInfo user = focusedUserInfoMapper.selectByPrimaryKey(userId);
			String oldGroupId = user.getGroupId();
			if (StringUtils.isEquals(oldGroupId,groupId)){
				return;
			}
			FocusedUserGroup oldGroup = focusedUserGroupMapper.selectByPrimaryKey(oldGroupId);
			if (oldGroup != null){
				Integer oldNumber = Integer.valueOf(oldGroup.getNumber());
				if (oldNumber >= 1){
					oldGroup.setNumber(oldNumber - 1 + "");
					oldGroup.setUpdateTime(new Date());
					focusedUserGroupMapper.updateByPrimaryKeySelective(oldGroup);
				}
			}
		}

		FocusedUserGroup group = focusedUserGroupMapper.selectByPrimaryKey(groupId);
		if (group != null){

			Integer number = Integer.valueOf(group.getNumber());
			Integer maxNumber = Integer.valueOf(group.getMixNumber());
			if (number + 1 < maxNumber){
				group.setNumber(number + 1 + "");
			}
			group.setUpdateTime(new Date());
			focusedUserGroupMapper.updateByPrimaryKeySelective(group);
		}
	}

	@Override
	public String getGroupNameByGroupId(String groupId) {
		FocusedUserGroup bean = focusedUserGroupMapper.selectByPrimaryKey(groupId);
		if (bean != null){
			return bean.getGroupName();
		}
		return null;
	}

}
