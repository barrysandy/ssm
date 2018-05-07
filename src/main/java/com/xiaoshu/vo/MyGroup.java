package com.xiaoshu.vo;


import com.xiaoshu.entity.FocusedUserInfo;

import java.util.List;

/**
 * 所属类别:我的组团，展示列表
* @author: XGB
* @date: 2018-04-16 13:34
 * SELECT
 * commodity_group.COMMODITY_ID AS id,commodity_group.Id AS groupId,orders.ID AS orderId,orders.ORDER_NAME AS name,orders.IMAGE AS image,
 * commodity_group.GROUP_TIME AS time,commodity_group.MAX_PERSON - commodity_group.TOTAL_PERSON AS total,commodity_group.STATUS AS status
 * FROM orders,commodity_group
 * WHERE USER_ID = '79bb9f01-87d7-4bf5-b54d-4d46b08a4dbc'
 */
public class MyGroup {
	private String id;
	private String groupId;
	private String orderId;
	private String name;
	private String image;
	private String time;
	private String total;
	private String status;
	private String jsonButton;
	private String desc;

	private Integer totalPerson;
	private Integer hiddenSet = 1;
	private Integer hiddenTotal;


	private List<FocusedUserInfo> user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJsonButton() {
		return jsonButton;
	}

	public void setJsonButton(String jsonButton) {
		this.jsonButton = jsonButton;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getTotalPerson() {
		return totalPerson;
	}

	public void setTotalPerson(Integer totalPerson) {
		this.totalPerson = totalPerson;
	}

	public Integer getHiddenSet() {
		return hiddenSet;
	}

	public void setHiddenSet(Integer hiddenSet) {
		this.hiddenSet = hiddenSet;
	}

	public Integer getHiddenTotal() {
		return hiddenTotal;
	}

	public void setHiddenTotal(Integer hiddenTotal) {
		this.hiddenTotal = hiddenTotal;
	}

	public List<FocusedUserInfo> getUser() {
		return user;
	}

	public void setUser(List<FocusedUserInfo> user) {
		this.user = user;
	}

	public MyGroup() { }

	public MyGroup(String id, String groupId, String orderId, String name, String image, String time, String total, String status, String jsonButton, String desc, Integer totalPerson, Integer hiddenSet, Integer hiddenTotal, List<FocusedUserInfo> user) {
		this.id = id;
		this.groupId = groupId;
		this.orderId = orderId;
		this.name = name;
		this.image = image;
		this.time = time;
		this.total = total;
		this.status = status;
		this.jsonButton = jsonButton;
		this.desc = desc;
		this.totalPerson = totalPerson;
		this.hiddenSet = hiddenSet;
		this.hiddenTotal = hiddenTotal;
		this.user = user;
	}

	@Override
	public String toString() {
		return "MyGroup{" +
				"id='" + id + '\'' +
				", groupId='" + groupId + '\'' +
				", orderId='" + orderId + '\'' +
				", name='" + name + '\'' +
				", image='" + image + '\'' +
				", time='" + time + '\'' +
				", total='" + total + '\'' +
				", status='" + status + '\'' +
				", jsonButton='" + jsonButton + '\'' +
				", desc='" + desc + '\'' +
				", totalPerson=" + totalPerson +
				", hiddenSet=" + hiddenSet +
				", hiddenTotal=" + hiddenTotal +
				", user=" + user +
				'}';
	}
}