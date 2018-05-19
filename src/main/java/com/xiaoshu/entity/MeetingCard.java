package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 会员卡实体类
 * @author XGB
 * @date 2018-05-19 10:37
 */
@Component
@Table(name="t_meeting_card")
@XmlRootElement(name = "MeetingCard.class")
public class MeetingCard implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name="NAME")
	private String name;// 姓名

	@Column(name="PHONE")
	private String phone;//手机电话

	@Column(name="ADDRESS")
	private String address;//邮寄地址

	@Column(name = "USER_ID")
	private String userId; //所属用户ID。

	@Column(name = "USER_OPENID")
	private String userOpenid; //所属用户OPENID。

	@Column(name = "NUMBER_TOTAL")
	private Integer numberTotal; //数量。

	private FocusedUserInfo userInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserOpenid() {
		return userOpenid;
	}

	public void setUserOpenid(String userOpenid) {
		this.userOpenid = userOpenid;
	}

	public Integer getNumberTotal() {
		return numberTotal;
	}

	public void setNumberTotal(Integer numberTotal) {
		this.numberTotal = numberTotal;
	}

	public FocusedUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(FocusedUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public MeetingCard() { }

	public MeetingCard(String id, String name, String phone, String address, String userId, String userOpenid, Integer numberTotal) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.userId = userId;
		this.userOpenid = userOpenid;
		this.numberTotal = numberTotal;
	}

	public MeetingCard(String id, String name, String phone, String address, String userId, String userOpenid, Integer numberTotal, FocusedUserInfo userInfo) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.userId = userId;
		this.userOpenid = userOpenid;
		this.numberTotal = numberTotal;
		this.userInfo = userInfo;
	}

	@Override
	public String toString() {
		return "MeetingCard{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", userId='" + userId + '\'' +
				", userOpenid='" + userOpenid + '\'' +
				", numberTotal=" + numberTotal +
				", userInfo=" + userInfo +
				'}';
	}
}