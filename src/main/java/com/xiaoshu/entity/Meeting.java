package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 会议实体类
 * @author XGB
 * @date 2018-05-9 15:59
 */
@Component
@Table(name="t_meeting")
@XmlRootElement(name = "Meeting.class")
public class Meeting implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name="IMAGE")
	private String image;// 会议封面

	@Column(name="TITLE")
	private String title;// 会议标题

	@Column(name = "DESC_M")
	private String descM; //会议描述（编辑器）。

	@Column(name="NAME")
	private String name;//会议发起者姓名

	@Column(name="PHONE")
	private String phone;//会议发起者电话

	@Column(name="CREATE_TIME")
	private String createTime; //创建时间

	@Column(name="UPDATE_TIME")
	private String updateTime; //更新时间

	@Column(name="BEGIN_TIME")
	private String beginTime; //开始时间

	@Column(name="END_TIME")
	private String endTime; //结束时间

	@Column(name="SIGN_TOTAL")
	private Integer signTotal; //签到总人数

	@Column(name="STATUS")
	private int status;//状态

	@Column(name="EXCEL_PATH")
	private String excelPath;//导入的Excel路径

	@Column(name="ADDRESS")
	private String address;//会议地址

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
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

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getSignTotal() {
		return signTotal;
	}

	public void setSignTotal(Integer signTotal) {
		this.signTotal = signTotal;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Meeting() { }

	public Meeting(String id, String image, String title, String descM, String name, String phone, String createTime, String updateTime, String beginTime, String endTime, Integer signTotal, int status, String excelPath, String address) {
		this.id = id;
		this.image = image;
		this.title = title;
		this.descM = descM;
		this.name = name;
		this.phone = phone;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.signTotal = signTotal;
		this.status = status;
		this.excelPath = excelPath;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Meeting{" +
				"id='" + id + '\'' +
				", image='" + image + '\'' +
				", title='" + title + '\'' +
				", descM='" + descM + '\'' +
				", name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", beginTime='" + beginTime + '\'' +
				", endTime='" + endTime + '\'' +
				", signTotal=" + signTotal +
				", status=" + status +
				", excelPath='" + excelPath + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}