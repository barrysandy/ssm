package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 文章的实体类<br/>
* @author: Kun
* @date: 2018-01-09 10:49
 */
public class Art{
	
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
	 *  标题
	 */
	private String artTitle;
	public String getArtTitle(){
		return artTitle;
	}
	public void setArtTitle(String artTitle){
		this.artTitle=artTitle== null ? null : artTitle.trim();
	}
	
	
	/**
	 * 内容
	 */
	private String artSummary;
	public String getArtSummary(){
		return artSummary;
	}
	public void setArtSummary(String artSummary){
		this.artSummary=artSummary== null ? null : artSummary.trim();
	}
	
	
	/**
	 * 封面
	 */
	private String artImg;
	public String getArtImg(){
		return artImg;
	}
	public void setArtImg(String artImg){
		this.artImg=artImg== null ? null : artImg.trim();
	}
	
	
	/**
	 * 链接
	 */
	private String artUrl;
	public String getArtUrl(){
		return artUrl;
	}
	public void setArtUrl(String artUrl){
		this.artUrl=artUrl== null ? null : artUrl.trim();
	}
	
	
	/**
	 * 时间字符串
	 */
	private String dateStr;
	public String getDateStr(){
		return dateStr;
	}
	public void setDateStr(String dateStr){
		this.dateStr=dateStr;
	}
	
	
	/**
	 * 第几期 判断在同一期
	 */
	private int currentPeriod;
	public int getCurrentPeriod(){
		return currentPeriod;
	}
	public void setCurrentPeriod(int currentPeriod){
		this.currentPeriod=currentPeriod;
	}
	
	
	/**
	 * 本期的位置 1表示在第一个栏位 2表示在第二个栏位 依次类推
	 */
	private int currentPeriodSort;
	public int getCurrentPeriodSort(){
		return currentPeriodSort;
	}
	public void setCurrentPeriodSort(int currentPeriodSort){
		this.currentPeriodSort=currentPeriodSort;
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
	 * -1已删除 1未删除
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
	 * 其他描述
	 */
	private String descM;
	public String getDescM(){
		return descM;
	}
	public void setDescM(String descM){
		this.descM=descM== null ? null : descM.trim();
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