package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信公众号菜单配置表的实体类<br/>
* @author: Kun
* @date: 2018-01-09 10:53
 */
public class WeChatMenu{
	
	/**
	 *  主键ID
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 *  父菜单ID
	 */
	private String parentId;
	public String getParentId(){
		return parentId;
	}
	public void setParentId(String parentId){
		this.parentId=parentId== null ? null : parentId.trim();
	}
	
	
	/**
	 *  菜单名称
	 */
	private String menuName;
	public String getMenuName(){
		return menuName;
	}
	public void setMenuName(String menuName){
		this.menuName=menuName== null ? null : menuName.trim();
	}
	
	
	/**
	 *  菜单类型(Button  ClickButton  CompleteButton Menu ViewButton)
	 */
	private String menuType;
	public String getMenuType(){
		return menuType;
	}
	public void setMenuType(String menuType){
		this.menuType=menuType== null ? null : menuType.trim();
	}
	
	
	/**
	 *  点击按钮的key
	 */
	private String menuKey;
	public String getMenuKey(){
		return menuKey;
	}
	public void setMenuKey(String menuKey){
		this.menuKey=menuKey== null ? null : menuKey.trim();
	}
	
	
	/**
	 *  视图菜单链接
	 */
	private String menuUrl;
	public String getMenuUrl(){
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl){
		this.menuUrl=menuUrl== null ? null : menuUrl.trim();
	}
	
	
	/**
	 *  一级菜单 0表示不是一级
	 */
	private String menuFirst;
	public String getMenuFirst(){
		return menuFirst;
	}
	public void setMenuFirst(String menuFirst){
		this.menuFirst=menuFirst== null ? null : menuFirst.trim();
	}
	
	
	/**
	 * 二级菜单 0表示不是二级菜单
	 */
	private String menuSecond;
	public String getMenuSecond(){
		return menuSecond;
	}
	public void setMenuSecond(String menuSecond){
		this.menuSecond=menuSecond== null ? null : menuSecond.trim();
	}
	
	
	/**
	 * 菜单描述
	 */
	private String menuDescribe;
	public String getMenuDescribe(){
		return menuDescribe;
	}
	public void setMenuDescribe(String menuDescribe){
		this.menuDescribe=menuDescribe== null ? null : menuDescribe.trim();
	}
	
	
	/**
	 * 排序
	 */
	private String menuSort;
	public String getMenuSort(){
		return menuSort;
	}
	public void setMenuSort(String menuSort){
		this.menuSort=menuSort== null ? null : menuSort.trim();
	}
	
	
	/**
	 *  创建时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 *  更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
	
	/**
	 * -1 已删除 1未删除
	 */
	private String status;
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status== null ? null : status.trim();
	}
	
	
	/**
	 * 系统菜单ID(这个是关联管理后台那个菜单的)
	 */
	private String menuId;
	public String getMenuId(){
		return menuId;
	}
	public void setMenuId(String menuId){
		this.menuId=menuId== null ? null : menuId.trim();
	}

	/**
	 * 子菜单
	 */
	private List<WeChatMenu> subMenu;
	public List<WeChatMenu> getSubMenu(){
		return subMenu;
	}
	public void setSubMenu(List<WeChatMenu> subMenu){
		this.subMenu=subMenu;
	}


	/**
	 * 系统父级菜单ID
	 */
	private String parentMenuId;
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
}