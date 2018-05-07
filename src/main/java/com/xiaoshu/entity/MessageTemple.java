package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 短信模板实体类
 * @author XGB
 * @date: 2018-04-08 9:50
 */
@Component
@XmlRootElement(name = "MessageTemple.class")
@Table(name = "message_temple")
public class MessageTemple implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID, COMMODITY_ID , TEMPLE_NAME, TEMPLE_ID, TEMPLE_TYPE , CREATE_TIME, UPDATE_TIME, DESC_M, STATUS ,SIGN
	 *
	 * id,commodityId ,templeName, templeId, templeType,createTime, updateTime, descM, status ,sign
	 */

	@Id
	@Column(name = "ID")
	private String id; // (ID)

	@Column(name="COMMODITY_ID")
	private Integer commodityId;//商品id -1查询全部

	@Column(name="TEMPLE_NAME")
	private String templeName;//模板名称

	@Column(name="TEMPLE_ID")
	private Integer templeId;//模板id

	@Column(name="TEMPLE_TYPE")
	private String templeType;//模板类型

	@Column(name="CREATE_TIME")
	private String createTime; //创建时间

	@Column(name="UPDATE_TIME")
	private String updateTime; //更新时间

	@Column(name="DESC_M")
	private String descM; //模板描述(DESC_M)

	@Column(name="STATUS")
	private int status;//模板状态 0删除 1存在 -1查询全部

	@Column(name="SIGN")
	private String sign;//模板签名

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getTempleName() {
		return templeName;
	}

	public void setTempleName(String templeName) {
		this.templeName = templeName;
	}

	public Integer getTempleId() {
		return templeId;
	}

	public void setTempleId(Integer templeId) {
		this.templeId = templeId;
	}

	public String getTempleType() {
		return templeType;
	}

	public void setTempleType(String templeType) {
		this.templeType = templeType;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public MessageTemple() { }

	public MessageTemple(String id, Integer commodityId, String templeName, Integer templeId, String templeType, String createTime, String updateTime, String descM, int status, String sign) {
		this.id = id;
		this.commodityId = commodityId;
		this.templeName = templeName;
		this.templeId = templeId;
		this.templeType = templeType;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.descM = descM;
		this.status = status;
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "MessageTemple{" +
				"id='" + id + '\'' +
				", commodityId=" + commodityId +
				", templeName='" + templeName + '\'' +
				", templeId=" + templeId +
				", templeType='" + templeType + '\'' +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", descM='" + descM + '\'' +
				", status=" + status +
				", sign='" + sign + '\'' +
				'}';
	}
}