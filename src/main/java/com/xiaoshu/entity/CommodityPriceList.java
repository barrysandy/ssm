package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 商品价格实体类
 * @author XGB
 * @date: 2018-03-21 11:00
 */
@Component
@XmlRootElement(name = "CommodityPriceList.class")
@Table(name = "commodity_price_list")
public class CommodityPriceList implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID, COMMODITY_ID, PRICE_NAME, PRICE, PRICE_UNIT, STATUS, DESC_M, CREATE_TIME, PRICE_TIME ,SORT ,ADDRESS ,REMARK
	 *
	 * id, commodityId, priceName, price, priceUnit, status, descM, createTime, priceTime ,sort ,address ,remark
	 */

	@Id
	@Column(name = "ID")
	private String id; // (ID)

	@Column(name="COMMODITY_ID")
	private int commodityId;//商品id

	@Column(name="PRICE_NAME")
	private String priceName;//价格名称

	@Column(name="PRICE")
	private double price;//商品价格

	@Column(name="PRICE_UNIT")
	private String priceUnit;//单位 如 /个 /人 .....

	@Column(name = "STATUS")
	private int status;//状态 1正常 0隐藏

	@Column(name = "SORT")
	private int sort;//排序 越小越排前面

	@Column(name = "DESC_M")
	private String descM; //描述。

	@Column(name="PRICE_TIME")
	private String priceTime; //时间

	@Column(name="CREATE_TIME")
	private String createTime; //创建时间

	@Column(name="ADDRESS")
	private String address; //地址

	@Column(name="REMARK")
	private String remark; //额外标记

	@Column(name="CONTACTS")
	private String contacts; //该日期的联系人

	@Column(name="CONTACTS_PHONE")
	private String contactsPhone; //该日期的联系人电话


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public String getPriceTime() {
		return priceTime;
	}

	public void setPriceTime(String priceTime) {
		this.priceTime = priceTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public CommodityPriceList() {
	}

	public CommodityPriceList(String id, int commodityId, String priceName, double price, String priceUnit, int status, int sort, String descM, String priceTime, String createTime, String address, String remark, String contacts, String contactsPhone) {
		this.id = id;
		this.commodityId = commodityId;
		this.priceName = priceName;
		this.price = price;
		this.priceUnit = priceUnit;
		this.status = status;
		this.sort = sort;
		this.descM = descM;
		this.priceTime = priceTime;
		this.createTime = createTime;
		this.address = address;
		this.remark = remark;
		this.contacts = contacts;
		this.contactsPhone = contactsPhone;
	}

	@Override
	public String toString() {
		return "CommodityPriceList{" +
				"id='" + id + '\'' +
				", commodityId=" + commodityId +
				", priceName='" + priceName + '\'' +
				", price=" + price +
				", priceUnit='" + priceUnit + '\'' +
				", status=" + status +
				", sort=" + sort +
				", descM='" + descM + '\'' +
				", priceTime='" + priceTime + '\'' +
				", createTime='" + createTime + '\'' +
				", address='" + address + '\'' +
				", remark='" + remark + '\'' +
				", contacts='" + contacts + '\'' +
				", contactsPhone='" + contactsPhone + '\'' +
				'}';
	}
}