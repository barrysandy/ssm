package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 系统字典目录表的实体类<br/>
* @author: Kun
* @date: 2018-01-16 16:29
 */
public class SysCodeCategory{
	
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
	 * 目录名
	 */
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name== null ? null : name.trim();
	}
	
	
	/**
	 * 目录CODE
	 */
	private String code;
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code=code== null ? null : code.trim();
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
	 * -1未删除 1删除
	 */
	private String status;
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status== null ? null : status.trim();
	}
	
}