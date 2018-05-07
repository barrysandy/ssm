package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 关键词回复表的实体类<br/>
* @author: Kun
* @date: 2018-01-09 10:50
 */
public class KeyWords{
	
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
	 * 关键字
	 */
	private String keyes;
	public String getKeyes(){
		return keyes;
	}
	public void setKeyes(String keyes){
		this.keyes=keyes== null ? null : keyes.trim();
	}
	
	
	/**
	 * 关键词回复
	 */
	private String valuess;
	public String getValuess(){
		return valuess;
	}
	public void setValuess(String valuess){
		this.valuess=valuess== null ? null : valuess.trim();
	}

	/**
	 * 消息类型  文本类型 text 图片类型 image 文章类型 art 语音类型 voice 音乐类型 music 视频类型 video
	 */
	private String typess;
	public String getTypess(){
		return typess;
	}
	public void setTypess(String typess){
		this.typess=typess== null ? null : typess.trim();
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
	 * PARENT_MENU_ID 所属公众号的Menu_ID(绑定公众号的Menu_ID，确定该关键字的所属公众号)
	 */
	private String parentMenuId;
	public String getParentMenuId(){
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId){
		this.parentMenuId=parentMenuId== null ? null : parentMenuId.trim();
	}

	public KeyWords() {
	}

	public KeyWords(String id, String keyes, String valuess, String typess, Date createTime, Date updateTime, String status, String sort, String menuId, String parentMenuId) {
		this.id = id;
		this.keyes = keyes;
		this.valuess = valuess;
		this.typess = typess;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.sort = sort;
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
	}

	@Override
	public String toString() {
		return "KeyWords{" +
				"id='" + id + '\'' +
				", keyes='" + keyes + '\'' +
				", valuess='" + valuess + '\'' +
				", typess='" + typess + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", status='" + status + '\'' +
				", sort='" + sort + '\'' +
				", menuId='" + menuId + '\'' +
				", parentMenuId='" + parentMenuId + '\'' +
				'}';
	}
}