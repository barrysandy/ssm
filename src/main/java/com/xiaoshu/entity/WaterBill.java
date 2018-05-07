package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 账单流水实体类
 * @author XGB
 *
 */
@Component
@XmlRootElement(name = "WaterBill.class")
public class WaterBill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;//账单流水 ID
	private String remarks;//备注 REMARKS
	private String orderNo;//订单号 ORDER_NO
	private Date createTime; //创建时间。CREATE_TIME
	private Date updateTime; //更新时间。UPDATE_TIME
	private double money;//金额。MONEY
	private int status; //流水状态。0 删除 ，1正常  2异常 金额对不上 3已退款 。 STATUS -1查询全部
	private String openid;//用户标识 OPENID
	private String userId;//用户id USER_ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public WaterBill() {
	}

	public WaterBill(int id, String remarks, String orderNo, Date createTime, Date updateTime, double money, int status, String openid, String userId) {
		this.id = id;
		this.remarks = remarks;
		this.orderNo = orderNo;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.money = money;
		this.status = status;
		this.openid = openid;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "WaterBill{" +
				"id=" + id +
				", remarks='" + remarks + '\'' +
				", orderNo='" + orderNo + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", money=" + money +
				", status=" + status +
				", openid='" + openid + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}