package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 关注用户信息表的实体类<br/>
* @author: Kun
* @date: 2018-01-04 15:28
 */
@Component
public class FocusedUserInfo{

	/**
	 * 主键
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}


	/**
	 * 昵称
	 */
	private String nickName;
	public String getNickName(){
		return nickName;
	}
	public void setNickName(String nickName){
		this.nickName=nickName== null ? null : nickName.trim();
	}


	/**
	 * 微信用户openid,用户唯一标识
	 */
	private String openid;
	public String getOpenid(){
		return openid;
	}
	public void setOpenid(String openid){
		this.openid=openid== null ? null : openid.trim();
	}


	/**
	 * 微信用户unionid,多个公众号之间判断是否是同一个用户
	 */
	private String unionid;
	public String getUnionid(){
		return unionid;
	}
	public void setUnionid(String unionid){
		this.unionid=unionid== null ? null : unionid.trim();
	}


	/**
	 * language用户的语言，简体中文为zh_CN
	 */
	private String language;
	public String getLanguage(){
		return language;
	}
	public void setLanguage(String language){
		this.language=language== null ? null : language.trim();
	}


	/**
	 * 国家
	 */
	private String country;
	public String getCountry(){
		return country;
	}
	public void setCountry(String country){
		this.country=country== null ? null : country.trim();
	}


	/**
	 * 省份
	 */
	private String province;
	public String getProvince(){
		return province;
	}
	public void setProvince(String province){
		this.province=province== null ? null : province.trim();
	}


	/**
	 * 城市
	 */
	private String city;
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city=city== null ? null : city.trim();
	}


	/**
	 * 头像
	 */
	private String headImgUrl;
	public String getHeadImgUrl(){
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl){
		this.headImgUrl=headImgUrl== null ? null : headImgUrl.trim();
	}


	/**
	 * 关注状态: 1=已关注，0=未关注
	 */
	private int subscribe;
	public int getSubscribe(){
		return subscribe;
	}
	public void setSubscribe(int subscribe){
		this.subscribe=subscribe;
	}
	public String getSubscribeStr(){
		String result = "";
		if (this.getSubscribe() == 1){
			result = "已关注";
		} else if (this.getSubscribe() == 0){
			result = "未关注";
		}
		return result;
	}


	/**
	 * 为用户关注公众号时间，注意：单位为秒，不是毫秒，要转换为毫秒要乘以1000，这个官网开发文档没有说明。
	 */
	private String subscribeTime;
	public String getSubscribeTime(){
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime){
		this.subscribeTime=subscribeTime;
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
	 * 删除标识符（1：正常  -1：删除）
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}


	/**
	 * 菜单ID
	 */
	private String menuId;
	public String getMenuId(){
		return menuId;
	}
	public void setMenuId(String menuId){
		this.menuId=menuId== null ? null : menuId.trim();
	}


	/**
	 * 分组ID
	 */
	private String groupId;
	public String getGroupId(){
		return groupId;
	}
	public void setGroupId(String groupId){
		this.groupId=groupId== null ? null : groupId.trim();
	}

	/**
	 * 性别GENDER=1表示男，=2表示女
	 */
	private int sex;
	public int getSex(){
		return sex;
	}
	public void setSex(int sex){
		this.sex=sex;
	}
	public String getSexStr() {
		String result = "";
		if (this.getSex() == 1) {
			result = "男";
		} else if (this.getSex() == 2) {
			result = "女";
		}
		return result;
	}


	/**
	 * 用户名,备注名称
	 */
	@Column(name = "username")
	private String username;

	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 用户名电话，也是非微信浏览器登录的账号
	 */
	@Column(name = "userphone")
	private String userphone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	/**
	 * 分组名字
	 */
	private String groupName;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	@Override
	public String toString() {
		return "FocusedUserInfo{" +
				"id='" + id + '\'' +
				", nickName='" + nickName + '\'' +
				", openid='" + openid + '\'' +
				", unionid='" + unionid + '\'' +
				", language='" + language + '\'' +
				", country='" + country + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", headImgUrl='" + headImgUrl + '\'' +
				", subscribe=" + subscribe +
				", subscribeTime='" + subscribeTime + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", status=" + status +
				", menuId='" + menuId + '\'' +
				", groupId='" + groupId + '\'' +
				", sex=" + sex +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", userphone='" + userphone + '\'' +
				", groupName='" + groupName + '\'' +
				", parentMenuId='" + parentMenuId + '\'' +
				'}';
	}
}