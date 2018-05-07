package com.xiaoshu.service;

import java.util.*;
import java.util.List;
import javax.annotation.Resource;
import com.xiaoshu.dao.WeChatMenuMapper;
import com.xiaoshu.entity.WeChatMenu;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 微信公众号菜单配置表
* @author: Kun
* @date: 2018-01-09 10:53
 */
@Service("weChatMenuService")
public class WeChatMenuServiceImpl implements WeChatMenuService {

	@Resource
	private WeChatMenuMapper weChatMenuMapper;
	@Resource
	private MenuService menuService;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<WeChatMenu> selectByPage(Map map) {
		return weChatMenuMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return weChatMenuMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public WeChatMenu selectByPrimaryKey(String id) {
		return weChatMenuMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(WeChatMenu bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return weChatMenuMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(WeChatMenu bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return weChatMenuMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		WeChatMenu bean = weChatMenuMapper.selectByPrimaryKey(id);
		bean.setStatus("-1");
		return weChatMenuMapper.updateByPrimaryKey(bean);
	}

	/**
	 * 通过父级ID查询菜单
	 */
	@Override
	public String getMenuListByParentId(String parentId) {
		List<WeChatMenu> weChatMenuList = weChatMenuMapper.getMenuListByParentId(parentId);
		if (CollectionUtils.isEmpty(weChatMenuList)){
			return JsonUtils.turnJson(true,"success",weChatMenuList);
		}
		return JsonUtils.turnJson(false,"没有查询到相关信息",null);
	}

	/**
	 * 通过menuId或者menuName精确查询菜单
	 */
	@Override
	public String getMenuByIdOrName(Map map) {
		WeChatMenu bean = weChatMenuMapper.getMenuByIdOrName(map);
		if (bean != null){
			JSONObject jsonObject = JSONObject.fromObject(bean);
			return JsonUtils.turnJson(true,"success",jsonObject);
		}
		return JsonUtils.turnJson(false,"没有查询到相关信息",null);
	}

	/**
	 * 通过一级菜单Id,查询二级菜单
	 */
	@Override
	public String getSecondMenuListByFirstMenuId(String firstMenuId) {
		List<WeChatMenu> menuList = weChatMenuMapper.getSecondMenuListByFirstMenuId(firstMenuId);
		if (CollectionUtils.isEmpty(menuList)){
			return JsonUtils.turnJson(false,"没有二级菜单",null);

		}
		return JsonUtils.turnJson(true,"success",menuList);
	}

	@Override
	public List<WeChatMenu> getUpdateWechatMenuList(String parentMenuId) {
		List<WeChatMenu> resultList = weChatMenuMapper.getUpdateWechatMenuList(parentMenuId);
		if (!CollectionUtils.isEmpty(resultList)){
			for (WeChatMenu weChatMenu : resultList) {
				String id = weChatMenu.getId();
				String type = weChatMenu.getMenuType();
				if (StringUtils.isEquals(type,"CompleteButton")){
					List<WeChatMenu> childList = weChatMenuMapper.getMenuListByParentId(id);
					weChatMenu.setSubMenu(childList);
				}
			}
		}
		return resultList;
	}

}
