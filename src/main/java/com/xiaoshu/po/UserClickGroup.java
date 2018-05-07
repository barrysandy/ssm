package com.xiaoshu.po;

/**
 * 由于订阅号未开放生成带参数二维码的接口，满足特点需求，关注推送特定图文，需要记录未关注用户点击的组团，并加入HashMap
 * @author: XGB
 * @date: 2018-04-25 9:48
 */
public class UserClickGroup {
	private String clickId; //点击的id
	private String clickType;//点击的类型 COMMODITY 商品
	private String currentMenuId;//当前授权的公众号标识
	private String associationMenuId;//关联的公众号标识
	private String userOpenid;//用户的openid
	private String userUnionid;//用户的userUnionid
	private String groupId;//如果是组团商品，此项特有
	private String groupCode;//如果是组团商品，此项特有

	public String getClickId() {
		return clickId;
	}

	public void setClickId(String clickId) {
		this.clickId = clickId;
	}

	public String getClickType() {
		return clickType;
	}

	public void setClickType(String clickType) {
		this.clickType = clickType;
	}

	public String getCurrentMenuId() {
		return currentMenuId;
	}

	public void setCurrentMenuId(String currentMenuId) {
		this.currentMenuId = currentMenuId;
	}

	public String getAssociationMenuId() {
		return associationMenuId;
	}

	public void setAssociationMenuId(String associationMenuId) {
		this.associationMenuId = associationMenuId;
	}

	public String getUserOpenid() {
		return userOpenid;
	}

	public void setUserOpenid(String userOpenid) {
		this.userOpenid = userOpenid;
	}

	public String getUserUnionid() {
		return userUnionid;
	}

	public void setUserUnionid(String userUnionid) {
		this.userUnionid = userUnionid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public UserClickGroup() {
	}

	public UserClickGroup(String clickId, String clickType, String currentMenuId, String associationMenuId, String userOpenid, String userUnionid, String groupId, String groupCode) {
		this.clickId = clickId;
		this.clickType = clickType;
		this.currentMenuId = currentMenuId;
		this.associationMenuId = associationMenuId;
		this.userOpenid = userOpenid;
		this.userUnionid = userUnionid;
		this.groupId = groupId;
		this.groupCode = groupCode;
	}

	@Override
	public String toString() {
		return "UserClickingGroup{" +
				"clickId='" + clickId + '\'' +
				", clickType='" + clickType + '\'' +
				", currentMenuId='" + currentMenuId + '\'' +
				", associationMenuId='" + associationMenuId + '\'' +
				", userOpenid='" + userOpenid + '\'' +
				", userUnionid='" + userUnionid + '\'' +
				", groupId='" + groupId + '\'' +
				", groupCode='" + groupCode + '\'' +
				'}';
	}
}