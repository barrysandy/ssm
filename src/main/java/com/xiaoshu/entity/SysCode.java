package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 系统字典表的实体类<br/>
* @author: Kun
* @date: 2018-01-16 16:28
 */
public class SysCode{
	
	/**
	 * 
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}
	
	
	/**
	 * 目录ID
	 */
	private String categoryId;
	public String getCategoryId(){
		return categoryId;
	}
	public void setCategoryId(String categoryId){
		this.categoryId=categoryId== null ? null : categoryId.trim();
	}
	
	
	/**
	 * 名字
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 值
	 */
	private String value;
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value=value== null ? null : value.trim();
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
	 * 
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
	
	/**
	 * 
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	
	
	/**
	 * -1 删除  1 未删除
	 */
	private String status;
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status== null ? null : status.trim();
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
	
}