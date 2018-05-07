package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 接口配置表的实体类<br/>
* @author: Kun
* @date: 2018-01-19 10:01
 */
public class InterfaceInfo{
	
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
	 * 接口描述
	 */
	private String descM;
	public String getDescM(){
		return descM;
	}
	public void setDescM(String descM){
		this.descM=descM== null ? null : descM.trim();
	}

	/**
	 * 接口标识，系统扫描接口列表时用来标识接口
	 */
	private String keyes;

	public String getKeyes(){
		return keyes;
	}
	public void setKeyes(String keyes){
		this.keyes=keyes== null ? null : keyes.trim();
	}

	/**
	 * 接口URL地址
	 */
	private String url;
	public String getUrl(){ return url; }
	public void setUrl(String url){
		this.url=url== null ? null : url.trim();
	}
	
	
	/**
	 * 参数
	 */
	private String params;
	public String getParams(){
		return params;
	}
	public void setParams(String params){
		this.params=params== null ? null : params.trim();
	}
	
	
	/**
	 * 接口类型 1微信Api 2内部接口
	 */
	private int type;
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type=type;
	}
	public String getTypeStr(){
		String result = "";
		if (this.getType() == 1) {
			result = "微信Api接口";
		} else if (this.getType() == 2) {
			result = "内部接口";
		}else if (this.getType() == 3) {
			result = "文件系统接口";
		}
		return result;
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
	 * -1 不使用 1使用
	 */
	private int isUse;
	public int getIsUse(){
		return isUse;
	}
	public void setIsUse(int isUse){
		this.isUse=isUse;
	}

}