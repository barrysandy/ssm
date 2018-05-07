package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 核销记录实体类
 * @author XGB
 *
 */
@Component
@XmlRootElement(name = "RecordWriteOff.class")
public class RecordWriteOff implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//(ID)
	private String code;//核销码
	private String orderNo;//订单
	private int sellerId;//商家
	private String userId;//核销的者，核销人员ID
	private String orderUserId;//核销订单的用户的ID
	private String createTime; //核销时间。

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderUserId() {
		return orderUserId;
	}

	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public RecordWriteOff() {
	}

	public RecordWriteOff(String id, String code, String orderNo, int sellerId, String userId, String orderUserId, String createTime) {
		this.id = id;
		this.code = code;
		this.orderNo = orderNo;
		this.sellerId = sellerId;
		this.userId = userId;
		this.orderUserId = orderUserId;
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "RecordWriteOff{" +
				"id='" + id + '\'' +
				", code='" + code + '\'' +
				", orderNo='" + orderNo + '\'' +
				", sellerId=" + sellerId +
				", userId='" + userId + '\'' +
				", orderUserId='" + orderUserId + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}