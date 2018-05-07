package com.xiaoshu.entity;

import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 公众号基础信息表的实体类<br/>
* @author Kun
* @date 2018-01-03 17:04
 */
public class PublicAccountInfo{
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
	 * 公众帐号名称
	 */
	private String accountName;
	public String getAccountName(){
		return accountName;
	}
	public void setAccountName(String accountName){
		this.accountName=accountName== null ? null : accountName.trim();
	}


	/**
	 * 公众帐号原始ID
	 */
	private String accountId;
	public String getAccountId(){
		return accountId;
	}
	public void setAccountId(String accountId){
		this.accountId=accountId== null ? null : accountId.trim();
	}


	/**
	 * token
	 */
	private String token;
	public String getToken(){
		return token;
	}
	public void setToken(String token){
		this.token=token== null ? null : token.trim();
	}


	/**
	 * AppId
	 */
	private String appId;
	public String getAppId(){
		return appId;
	}
	public void setAppId(String appId){
		this.appId=appId== null ? null : appId.trim();
	}


	/**
	 * App_Secret
	 */
	private String appSecret;
	public String getAppSecret(){
		return appSecret;
	}
	public void setAppSecret(String appSecret){
		this.appSecret=appSecret== null ? null : appSecret.trim();
	}


	/**
	 * 1个人号 2服务号 3订阅号 4企业号 5小程序 6测试号
	 */
	private int accountType;
	public int getAccountType(){
		return accountType;
	}
	public void setAccountType(int accountType){
		this.accountType=accountType;
	}

	/**
	 * 接口地址(微信公众号配置地址)
	 */
	private String interfaceUrl;
	public String getInterfaceUrl(){
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl){
		this.interfaceUrl=interfaceUrl== null ? null : interfaceUrl.trim();
	}


	/**
	 * 创建日期
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


	/**
	 * 修改日期
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
	 * 公众号启用状态 0未启用 系统不会去获取该公众号的AccessToken，并且该公众号的接口不能正常使用  1已启用
	 */
	private int usable;
	public int getUsable(){
		return usable;
	}
	public void setUsable(int usable){
		this.usable=usable;
	}
	public String getUsableStr(){
		String result = "";
		if (this.getUsable() == 0){
			result = "未启用";
		} else if (this.getUsable() == 1){
			result = "已启用";
		}
		return result;
	}


	/**
	 * ACCESS_TOKEN有效时间(单位：ms)
	 */
	private String effectiveTime;
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	/**
	 * 公众号绑定的开放平台 openPlatform
	 */
	private String openPlatform;
	public String getOpenPlatform(){
		return openPlatform;
	}
	public void setOpenPlatform(String openPlatform){
		this.openPlatform=openPlatform== null ? null : openPlatform.trim();
	}


	/**
	 * 微信支付，商户平台账号mch_id
	 */
	private String mchId;
	public String getMchId(){
		return mchId;
	}
	public void setMchId(String mchId){
		this.mchId=mchId== null ? null : mchId.trim();
	}


	/**
	 * 微信支付，商户平台的api秘钥
	 */
	private String mchKey;
	public String getMchKey(){
		return mchKey;
	}
	public void setMchKey(String mchKey){
		this.mchKey=mchKey== null ? null : mchKey.trim();
	}


	/**
	 * 微信支付错误跳转页面
	 */
	private String notifyErrorUrl;
	public String getNotifyErrorUrl(){
		return notifyErrorUrl;
	}
	public void setNotifyErrorUrl(String notifyErrorUrl){
		this.notifyErrorUrl=notifyErrorUrl== null ? null : notifyErrorUrl.trim();
	}


	/**
	 * notify_url 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
	 */
	private String notifyUrl;
	public String getNotifyUrl(){
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl){
		this.notifyUrl=notifyUrl== null ? null : notifyUrl.trim();
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
	 * 系统父级菜单ID
	 */
	private String parentMenuId;
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public PublicAccountInfo() {
	}

	public PublicAccountInfo(String id, String accountName, String accountId, String token, String appId, String appSecret, int accountType, String interfaceUrl, Date createTime, Date updateTime, int status, String descM, int usable, String effectiveTime, String openPlatform, String mchId, String mchKey, String notifyErrorUrl, String notifyUrl, String menuId, String parentMenuId) {
		this.id = id;
		this.accountName = accountName;
		this.accountId = accountId;
		this.token = token;
		this.appId = appId;
		this.appSecret = appSecret;
		this.accountType = accountType;
		this.interfaceUrl = interfaceUrl;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.descM = descM;
		this.usable = usable;
		this.effectiveTime = effectiveTime;
		this.openPlatform = openPlatform;
		this.mchId = mchId;
		this.mchKey = mchKey;
		this.notifyErrorUrl = notifyErrorUrl;
		this.notifyUrl = notifyUrl;
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
	}
}