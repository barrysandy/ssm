package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 关注用户分组表的实体类<br/>
* @author: Kun
* @date: 2018-01-10 10:32
 */
public class FocusedUserGroup{
	
	/**
	 * 主键ID
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 分组名
	 */
	private String groupName;
	public String getGroupName(){
		return groupName;
	}
	public void setGroupName(String groupName){
		this.groupName=groupName== null ? null : groupName.trim();
	}
	
	
	/**
	 * 拥有的操作权限
	 */
	private String permissions;
	public String getPermissions(){
		return permissions;
	}
	public void setPermissions(String permissions){
		this.permissions=permissions== null ? null : permissions.trim();
	}
	
	
	/**
	 * 现有人数
	 */
	private String number;
	public String getNumber(){
		return number;
	}
	public void setNumber(String number){
		this.number=number== null ? null : number.trim();
	}
	
	
	/**
	 * 允许最大分组人数
	 */
	private String mixNumber;
	public String getMixNumber(){
		return mixNumber;
	}
	public void setMixNumber(String mixNumber){
		this.mixNumber=mixNumber== null ? null : mixNumber.trim();
	}
	
	
	/**
	 * 描述
	 */
	private String descM;
	public String getDescM(){
		return descM;
	}
	public void setDescM(String descM){
		this.descM=descM== null ? null : descM.trim();
	}
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
	
	/**
	 * -1已删除 1未删除
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}
	
	
	/**
	 * 排序
	 */
	private String sort;
	public String getSort(){
		return sort;
	}
	public void setSort(String sort){
		this.sort=sort== null ? null : sort.trim();
	}
	
	
	/**
	 * 预留字段
	 */
	private String temp;
	public String getTemp(){
		return temp;
	}
	public void setTemp(String temp){
		this.temp=temp== null ? null : temp.trim();
	}

	/**
	 * 系统菜单ID
	 */
	private String menuId;
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
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