package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 签字人实体类
 * @author XGB
 * @date 2018-05-9 15:37
 */
@Component
@Table(name="t_meeting_sign")
@XmlRootElement(name = "MeetingSign.class")
public class MeetingSign implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * ID, NAME, HEAD_IMAGE, PHONE, SIGN_CODE, COMPANY , PERSON_TYPE, POSITION, JOIN_DINNER, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS
	 *
	 * id, name, headImage, phone, signCode, company , personType, position, joinDinner, createTime, updateTime,descM,status
	 */

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name="NAME")
	private String name;// 姓名

	@Column(name="HEAD_IMAGE")
	private String headImage;// 头像

	@Column(name="PHONE")
	private String phone;//手机电话

	@Column(name="SIGN_CODE")
	private String signCode;//签到码

	@Column(name="COMPANY")
	private String company;//单位-公司-机构

	@Column(name="PERSON_TYPE")
	private String personType;//类型(VIP  政府代表  风景区代表  涉旅企业代表  个人  媒体)

	@Column(name="POSITION")
	private String position;//职位

	@Column(name="JOIN_DINNER")
	private Integer joinDinner;//是否参加晚宴

	@Column(name="CREATE_TIME")
	private String createTime; //创建时间

	@Column(name="UPDATE_TIME")
	private String updateTime; //更新时间

	@Column(name = "DESC_M")
	private String descM; //描述。

	@Column(name="STATUS")
	private Integer status;//状态 0未签到 1 已签到 -1查询所有

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getJoinDinner() {
		return joinDinner;
	}

	public void setJoinDinner(Integer joinDinner) {
		this.joinDinner = joinDinner;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public MeetingSign() { }

	public MeetingSign(String id, String name, String headImage, String phone, String signCode, String company, String personType, String position, Integer joinDinner, String createTime, String updateTime, String descM, Integer status) {
		this.id = id;
		this.name = name;
		this.headImage = headImage;
		this.phone = phone;
		this.signCode = signCode;
		this.company = company;
		this.personType = personType;
		this.position = position;
		this.joinDinner = joinDinner;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.descM = descM;
		this.status = status;
	}

	@Override
	public String toString() {
		return "MeetingSign{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", headImage='" + headImage + '\'' +
				", phone='" + phone + '\'' +
				", signCode='" + signCode + '\'' +
				", company='" + company + '\'' +
				", personType='" + personType + '\'' +
				", position='" + position + '\'' +
				", joinDinner=" + joinDinner +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", descM='" + descM + '\'' +
				", status=" + status +
				'}';
	}
}