package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 订单退款申请实体类
 *
 * @author XGB
 */
@Component
@XmlRootElement(name = "OrderRefund.class")
@Table(name = "order_refund")
public class OrderRefund implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID, TRANSACTION_ID, PAY_TYPE, ORDER_NO, CREATE_TIME, HANDLE_TIME, RETURN_TIME, DESC_M, ORDER_MONEY, REFUND_FEE, REFUND_FEE_TYPE, REFUND_DESC
     * ,REFUND_ACCOUNT , ORDER_NAME , TYPE_STATE , STATUS , USER_ID, SELLER_ID,REFUND_RESULT
     * <p>
     * id, transactionId, payType, orderNo, createTime, handleTime, returnTime, descM, orderMoney, refundFee, refundFeeType ,refundDesc
     * ,refundAccount , orderName ,typeState , status ,userId, sellerId,refundResult
     */

    @Id
    @Column(name = "ID")
    private String id;//商户退款单号

    @Column(name = "TRANSACTION_ID")
    private String transactionId;//微信订单号

    @Column(name = "PAY_TYPE")
    private String payType; //支付类型。参考EnumsPayType 的类型

    @Column(name = "ORDER_NO")
    private String orderNo;//退款订单编号

    @Column(name = "CREATE_TIME")
    private String createTime;//退款申请提交时间

    @Column(name = "HANDLE_TIME")
    private String handleTime;//管理员处理时间

    @Column(name = "RETURN_TIME")
    private String returnTime;//退款成功时间

    @Column(name = "DESC_M")
    private String descM; //描述。

    @Column(name = "ORDER_MONEY")
    private Integer orderMoney;//订单金额 ps:订单总金额，单位为分，只能为整数

    @Column(name = "REFUND_FEE")
    private Integer refundFee;//退款金额 ps:退款总金额，订单总金额，单位为分，只能为整数

    @Column(name = "REFUND_FEE_TYPE")
    private String refundFeeType;//退款货币种类 ps:退款货币类型，需与支付一致，或者不填。符合ISO 4217标准的三位字母代码，默认人民币：CNY

    @Column(name = "REFUND_DESC")
    private String refundDesc;//退款原因 ps:若商户传入，会在下发给用户的退款消息中体现退款原因

    @Column(name = "REFUND_ACCOUNT")
    private String refundAccount;//退款资金来源 ps:仅针对老资金流商户使用 REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款） REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款

    @Column(name = "ORDER_NAME")
    private String orderName;//退款订单名称

    @Column(name = "TYPE_STATE")
    private int typeState;//退款申请状态 0未提交 1已提交 2已处理 3已退款  -1查询所有状态

    @Column(name = "STATUS")
    private int status;//退款申请存在状态 1存在 0删除 -1查询全部

    @Column(name = "USER_ID")
    private String userId;//退款申请用户id

    @Column(name = "SELLER_ID")
    private int sellerId;//订单所属商家id

    @Column(name = "REFUND_RESULT")
    private String refundResult;//退款处理结果

    public OrderRefund() { }

    public OrderRefund(String id, String transactionId, String payType, String orderNo, String createTime, String handleTime, String returnTime, String descM, Integer orderMoney, Integer refundFee, String refundFeeType, String refundDesc, String refundAccount, String orderName, int typeState, int status, String userId, int sellerId, String refundResult) {
        this.id = id;
        this.transactionId = transactionId;
        this.payType = payType;
        this.orderNo = orderNo;
        this.createTime = createTime;
        this.handleTime = handleTime;
        this.returnTime = returnTime;
        this.descM = descM;
        this.orderMoney = orderMoney;
        this.refundFee = refundFee;
        this.refundFeeType = refundFeeType;
        this.refundDesc = refundDesc;
        this.refundAccount = refundAccount;
        this.orderName = orderName;
        this.typeState = typeState;
        this.status = status;
        this.userId = userId;
        this.sellerId = sellerId;
        this.refundResult = refundResult;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getDescM() {
        return descM;
    }

    public void setDescM(String descM) {
        this.descM = descM;
    }

    public Integer getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Integer orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundFeeType() {
        return refundFeeType;
    }

    public void setRefundFeeType(String refundFeeType) {
        this.refundFeeType = refundFeeType;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getTypeState() {
        return typeState;
    }

    public void setTypeState(int typeState) {
        this.typeState = typeState;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getRefundResult() {
        return refundResult;
    }

    public void setRefundResult(String refundResult) {
        this.refundResult = refundResult;
    }

    @Override
    public String toString() {
        return "OrderRefund{" +
                "id='" + id + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", payType='" + payType + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", returnTime='" + returnTime + '\'' +
                ", descM='" + descM + '\'' +
                ", orderMoney=" + orderMoney +
                ", refundFee=" + refundFee +
                ", refundFeeType='" + refundFeeType + '\'' +
                ", refundDesc='" + refundDesc + '\'' +
                ", refundAccount='" + refundAccount + '\'' +
                ", orderName='" + orderName + '\'' +
                ", typeState=" + typeState +
                ", status=" + status +
                ", userId='" + userId + '\'' +
                ", sellerId=" + sellerId +
                ", refundResult='" + refundResult + '\'' +
                '}';
    }
}