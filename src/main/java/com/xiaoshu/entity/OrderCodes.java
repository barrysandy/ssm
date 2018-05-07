package com.xiaoshu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 订单核销码实体类
 * @author XGB
 * @date: 2018-03-21 11:00
 */
@Component
@XmlRootElement(name = "OrderCodes.class")
@Table(name = "order_codes")
public class OrderCodes implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID, ORDER_ID, ORDER_NO, CODE_NAME, USE_CODE, CODE_IMAGE, CREATE_TIME, USER_ID, COMMODITY_ID, SELLER_ID, CODE_STATE
	 *
	 * id, orderId, orderNo, codeName, useCode, codeImage, createTime, userId, commodityId, sellerId, codeState
	 */

	@Id
	@Column(name = "ID")
	private String id; // (ID)

	@Column(name="ORDER_ID")
	private int orderId;//订单id (ORDER_ID)

	@Column(name="ORDER_NO")
	private String orderNo;//订单No (ORDER_NO)

	@Column(name="CODE_NAME")
	private String codeName;//核销码名称 (CODE_NAME)

	@Column(name="USE_CODE")
	private String useCode;//核销使用码 (USE_CODE)

	@Column(name="CODE_IMAGE")
	private String codeImage;//核销使用的二维码图片 (CODE_IMAGE)

	@Column(name="CREATE_TIME")
	private String createTime; //核销码创建时间 (CREATE_TIME)

	@Column(name="USER_ID")
	private String userId;//核销码用户id (USER_ID)

	@Column(name="COMMODITY_ID")
	private int commodityId;//商品id (COMMODITY_ID)

	@Column(name="SELLER_ID")
	private int sellerId;//所属商家id (SELLER_ID)

	@Column(name="CODE_STATE")
	private int codeState;//核销使用码状态 -1全部状态 0不能使用 1可使用 2已使用(CODE_STATE)


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getUseCode() {
		return useCode;
	}

	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}

	public String getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(String codeImage) {
		this.codeImage = codeImage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getCodeState() {
		return codeState;
	}

	public void setCodeState(int codeState) {
		this.codeState = codeState;
	}

	public OrderCodes() {
	}

	public OrderCodes(String id, int orderId, String orderNo, String codeName, String useCode, String codeImage, String createTime, String userId, int commodityId, int sellerId, int codeState) {
		this.id = id;
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.codeName = codeName;
		this.useCode = useCode;
		this.codeImage = codeImage;
		this.createTime = createTime;
		this.userId = userId;
		this.commodityId = commodityId;
		this.sellerId = sellerId;
		this.codeState = codeState;
	}

	@Override
	public String toString() {
		return "OrderCode{" +
				"id='" + id + '\'' +
				", orderId=" + orderId +
				", orderNo='" + orderNo + '\'' +
				", codeName='" + codeName + '\'' +
				", useCode='" + useCode + '\'' +
				", codeImage='" + codeImage + '\'' +
				", createTime='" + createTime + '\'' +
				", userId='" + userId + '\'' +
				", commodityId=" + commodityId +
				", sellerId=" + sellerId +
				", codeState=" + codeState +
				'}';
	}
}