package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 订单实体类
 * @author XGB
 *
 */
@Component
@XmlRootElement(name = "Order.class")
@Table(name = "order")
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID, ORDER_NAME, ORDER_NO, CREATE_TIME, PAYMENT_TIME, DESC_M, NUMBER_DESC_M, ORDER_AMOUNT_MONEY, TYPE_STATE, STATUS, USER_ID, USER_NAME, USER_PHONE, USER_USE_TIME, SELLER_ID, INVALID_TIME, IMAGE, USE_CODE,GROUP_ID
	 *
	 * id, orderName, orderNo, createTime, paymentTime, descM, numberDescM, orderAmountMoney, typeState, status, userId, userName, userPhone, userUseTime, sellerId, invalidTime, image, useCode , groupId
	 */

	@Id
	@Column(name = "ID")
	private Integer id;//订单id

	@Column(name = "ORDER_NAME")
	private String orderName;//订单名称

	@Column(name = "ORDER_NO")
	private String orderNo;//订单编号

	@Column(name = "CREATE_TIME")
	private String createTime;//订单提交时间

	@Column(name = "PAYMENT_TIME")
	private String paymentTime;//订单支付时间

	@Column(name = "DESC_M")
	private String descM; //订单描述。

	@Column(name = "NUMBER_DESC_M")
	private String numberDescM; //订单数量描述。

	@Column(name = "ORDER_AMOUNT_MONEY")
	private Double orderAmountMoney;//订单金额

	@Column(name = "TYPE_STATE")
	private Integer typeState;//订单状态 0未付款 1已付款 2已消费 3退款中 4已退款 5退款失败 6免费可以使用的订单 -1查询所有状态

	@Column(name = "STATUS")
	private Integer status;//订单存在状态 0未删除 -1删除

	@Column(name = "USER_ID")
	private String userId;//订单用户id

	@Column(name = "USER_NAME")
	private String userName;//联系人名称

	@Column(name = "USER_PHONE")
	private String userPhone;//联系人电话

	@Column(name = "USER_USE_TIME")
	private String userUseTime;//订单消费时间（只有填写了改时间的才会起效）

	@Column(name = "SELLER_ID")
	private Integer sellerId;//所属商家id

	@Column(name = "INVALID_TIME")
	private String invalidTime;//订单失效时间

	@Column(name = "IMAGE")
	private String image;//订单商品封面

	@Column(name = "USE_CODE")
	private String useCode;//核销使用码

	@Column(name = "TRANSACTION_ID")
	private String transactionId;//订单号

	@Column(name = "PAY_TYPE")
	private String payType; //支付类型。参考EnumsPayType 的类型

	@Column(name = "NUMBER")
	private Integer number;//成人数量

	@Column(name = "NUMBER2")
	private Integer number2;//儿童数量

	@Column(name = "COMMODITY_ID")
	private Integer commodityId;//单一商品绑定该项

	@Column(name = "ORDER_TYPE")
	private Integer orderType;//订单类型 1 单一商品订单  2 组团商品订单 -1查询忽略该条件

	@Column(name = "GROUP_ID")
	private String groupId;//拼团ID


	private FocusedUserInfo user;//订单所属用户
	private List<OrderCodes> orderCodes;//订单核销码
	private List<CommodityOrder> commodityOrder;//订单核销码

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public String getNumberDescM() {
		return numberDescM;
	}

	public void setNumberDescM(String numberDescM) {
		this.numberDescM = numberDescM;
	}

	public Double getOrderAmountMoney() {
		return orderAmountMoney;
	}

	public void setOrderAmountMoney(Double orderAmountMoney) {
		this.orderAmountMoney = orderAmountMoney;
	}

	public Integer getTypeState() {
		return typeState;
	}

	public void setTypeState(Integer typeState) {
		this.typeState = typeState;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserUseTime() {
		return userUseTime;
	}

	public void setUserUseTime(String userUseTime) {
		this.userUseTime = userUseTime;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUseCode() {
		return useCode;
	}

	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber2() {
		return number2;
	}

	public void setNumber2(Integer number2) {
		this.number2 = number2;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public FocusedUserInfo getUser() {
		return user;
	}

	public void setUser(FocusedUserInfo user) {
		this.user = user;
	}

	public List<OrderCodes> getOrderCodes() {
		return orderCodes;
	}

	public void setOrderCodes(List<OrderCodes> orderCodes) {
		this.orderCodes = orderCodes;
	}

	public List<CommodityOrder> getCommodityOrder() {
		return commodityOrder;
	}

	public void setCommodityOrder(List<CommodityOrder> commodityOrder) {
		this.commodityOrder = commodityOrder;
	}

	public Order() { }

	public Order(Integer id, String orderName, String orderNo, String createTime, String paymentTime, String descM, String numberDescM, Double orderAmountMoney, Integer typeState, Integer status, String userId, String userName, String userPhone, String userUseTime, Integer sellerId, String invalidTime, String image, String useCode, String transactionId, String payType, Integer number, Integer number2, Integer commodityId, Integer orderType, String groupId) {
		this.id = id;
		this.orderName = orderName;
		this.orderNo = orderNo;
		this.createTime = createTime;
		this.paymentTime = paymentTime;
		this.descM = descM;
		this.numberDescM = numberDescM;
		this.orderAmountMoney = orderAmountMoney;
		this.typeState = typeState;
		this.status = status;
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userUseTime = userUseTime;
		this.sellerId = sellerId;
		this.invalidTime = invalidTime;
		this.image = image;
		this.useCode = useCode;
		this.transactionId = transactionId;
		this.payType = payType;
		this.number = number;
		this.number2 = number2;
		this.commodityId = commodityId;
		this.orderType = orderType;
		this.groupId = groupId;
	}

	public Order(Integer id, String orderName, String orderNo, String createTime, String paymentTime, String descM, String numberDescM, Double orderAmountMoney, Integer typeState, Integer status, String userId, String userName, String userPhone, String userUseTime, Integer sellerId, String invalidTime, String image, String useCode, String transactionId, String payType, Integer number, Integer number2, Integer commodityId, Integer orderType, String groupId, FocusedUserInfo user, List<OrderCodes> orderCodes, List<CommodityOrder> commodityOrder) {
		this.id = id;
		this.orderName = orderName;
		this.orderNo = orderNo;
		this.createTime = createTime;
		this.paymentTime = paymentTime;
		this.descM = descM;
		this.numberDescM = numberDescM;
		this.orderAmountMoney = orderAmountMoney;
		this.typeState = typeState;
		this.status = status;
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userUseTime = userUseTime;
		this.sellerId = sellerId;
		this.invalidTime = invalidTime;
		this.image = image;
		this.useCode = useCode;
		this.transactionId = transactionId;
		this.payType = payType;
		this.number = number;
		this.number2 = number2;
		this.commodityId = commodityId;
		this.orderType = orderType;
		this.groupId = groupId;
		this.user = user;
		this.orderCodes = orderCodes;
		this.commodityOrder = commodityOrder;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", orderName='" + orderName + '\'' +
				", orderNo='" + orderNo + '\'' +
				", createTime='" + createTime + '\'' +
				", paymentTime='" + paymentTime + '\'' +
				", descM='" + descM + '\'' +
				", numberDescM='" + numberDescM + '\'' +
				", orderAmountMoney=" + orderAmountMoney +
				", typeState=" + typeState +
				", status=" + status +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", userPhone='" + userPhone + '\'' +
				", userUseTime='" + userUseTime + '\'' +
				", sellerId=" + sellerId +
				", invalidTime='" + invalidTime + '\'' +
				", image='" + image + '\'' +
				", useCode='" + useCode + '\'' +
				", transactionId='" + transactionId + '\'' +
				", payType='" + payType + '\'' +
				", number=" + number +
				", number2=" + number2 +
				", commodityId=" + commodityId +
				", orderType=" + orderType +
				", groupId='" + groupId + '\'' +
				", user=" + user +
				", orderCodes=" + orderCodes +
				", commodityOrder=" + commodityOrder +
				'}';
	}
}