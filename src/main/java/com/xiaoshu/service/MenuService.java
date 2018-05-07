package com.xiaoshu.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.MenuMapper;
import com.xiaoshu.entity.Menu;
import com.xiaoshu.entity.MenuExample;
import com.xiaoshu.util.StringUtils;

@Service
public class MenuService {

	@Autowired MenuMapper menuMapper;

	public List<Menu> findMenu(Menu menu) throws Exception {
		return menuMapper.select(menu);
	};

	public long countMenu(Menu menu) throws Exception {
		return menuMapper.selectCount(menu);
	};

	public void addMenu(Menu menu) throws Exception {
		menuMapper.insert(menu);
	};

	public void updateMenu(Menu menu) throws Exception {
		if(StringUtils.isNotEmpty(menu.getIconcls())){
			menu.setIconcls(menu.getIconcls()); 
		}
		menuMapper.updateByPrimaryKeySelective(menu);
	};

	public void deleteMenu(Integer menuid) throws Exception {
		menuMapper.deleteByPrimaryKey(menuid);
	};

	@SuppressWarnings("rawtypes")
	public List<Menu> menuTree(Map map) throws Exception {
		Integer parentId = (Integer)map.get("parentId");
		String[] menuIdStrs = (String[]) map.get("menuIds");
		Integer menuIds[] = StringUtils.stringArrToIntergerArr(menuIdStrs);
		MenuExample example = new MenuExample();
		example.createCriteria().andMenuidIn(Arrays.asList(menuIds)).andParentidEqualTo(parentId);
		return menuMapper.selectByExample(example);
	}

	public Menu findMenuByMenuid(Integer menuid) {
		return menuMapper.selectByPrimaryKey(menuid);
	};

	public String getParentIdByMenuId(String menuId){
		return menuMapper.getParentIdByMenuId(menuId);
	}

	/**
	 * 通过菜parentId和menuUrl来获取菜单menuId
	 * @param menuId
	 * @param menuUrl
	 * @return 被查询的menuId OR null
	 */
	public int selectMenuIDByParentIDANDUrl(Integer menuId,String menuUrl){
		return menuMapper.selectMenuIDByParentIDANDUrl(menuId,menuUrl);
	}

	/**
	 * 按menuId查询菜单
	 * @param menuId = parentId
	 * @return 被查询的menuId OR null
	 */
	public Menu selectMenuByParentID(Integer menuId){
		return menuMapper.selectMenuByParentID(menuId);
	}

	/**
	 * 通过menuName查询菜单menuId
	 * @param menuName
	 * @return 被查询的menuId OR 0
	 */
	public int selectMenuIDByName(String menuName){
		return menuMapper.selectMenuIDByName(menuName);
	}

	/**
	 * 通过menuId更新状态
	 * @param menuId
	 * @param state
	 * @return 被查询的menuId OR 0
	 */
	public int updateIsParent(Integer menuId,String state){
		return menuMapper.updateIsParent(menuId,state);
	}


	/**
	 * 通过menuId更新名称
	 * @param menuId
	 * @param menuName
	 * @return 被查询的menuId OR 0
	 */
	public int updateMeanName(Integer menuId,String menuName){
		return menuMapper.updateMeanName(menuId,menuName);
	}
}
