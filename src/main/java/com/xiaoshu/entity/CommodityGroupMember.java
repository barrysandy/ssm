package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品组团成员实体类
 *
 * @author XGB
 */
@Component
@XmlRootElement(name = "CommodityGroupMember.class")
@Table(name = "commodity_group_member")
public class CommodityGroupMember implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     * ID, COMMODITY_ID, CREATE_DATE, DESC_M, USER_ID, LENDER, ORDER_NO,GROUP_ID, STATUS
     *
     * id, commodityId, createDate, descM, userId, lender,orderNo,groupId, status
     */

    @Id
    @Column(name = "ID")
    private String id;//ID

    @Column(name = "COMMODITY_ID")
    private Integer commodityId;//商品Id

    @Column(name = "CREATE_DATE")
    private Date createDate;//创建时间

    @Column(name = "DESC_M")
    private String descM; //描述。

    @Column(name = "USER_ID")
    private String userId;//参团成员ID

    @Column(name = "LENDER")
    private Integer lender;//是否为队长 1表示为队长 0表示为普通成员

    @Column(name = "ORDER_NO")
    private String orderNo;//订单编号

    @Column(name = "GROUP_ID")
    private String groupId;//拼团ID

    @Column(name = "STATUS")
    private  Integer status;//成员状态  0 未付款的成员 1 付款成员 2 已经退出，会被移除的成员 -1查询所有

    private FocusedUserInfo user;//组团成员对象

    private Order order;//订单实体对象

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescM() {
        return descM;
    }

    public void setDescM(String descM) {
        this.descM = descM;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLender() {
        return lender;
    }

    public void setLender(Integer lender) {
        this.lender = lender;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FocusedUserInfo getUser() {
        return user;
    }

    public void setUser(FocusedUserInfo user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public CommodityGroupMember() { }

    public CommodityGroupMember(String id, Integer commodityId, Date createDate, String descM, String userId, Integer lender, String orderNo, String groupId, Integer status) {
        this.id = id;
        this.commodityId = commodityId;
        this.createDate = createDate;
        this.descM = descM;
        this.userId = userId;
        this.lender = lender;
        this.orderNo = orderNo;
        this.groupId = groupId;
        this.status = status;
    }

    public CommodityGroupMember(String id, Integer commodityId, Date createDate, String descM, String userId, Integer lender, String orderNo, String groupId, Integer status, FocusedUserInfo user, Order order) {
        this.id = id;
        this.commodityId = commodityId;
        this.createDate = createDate;
        this.descM = descM;
        this.userId = userId;
        this.lender = lender;
        this.orderNo = orderNo;
        this.groupId = groupId;
        this.status = status;
        this.user = user;
        this.order = order;
    }

    @Override
    public String toString() {
        return "CommodityGroupMember{" +
                "id='" + id + '\'' +
                ", commodityId=" + commodityId +
                ", createDate=" + createDate +
                ", descM='" + descM + '\'' +
                ", userId='" + userId + '\'' +
                ", lender=" + lender +
                ", orderNo='" + orderNo + '\'' +
                ", groupId='" + groupId + '\'' +
                ", status=" + status +
                ", user=" + user +
                ", order=" + order +
                '}';
    }
}