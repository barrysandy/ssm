package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品组团实体类
 *
 * @author XGB
 */
@Component
@XmlRootElement(name = "CommodityGroup.class")
@Table(name = "commodity_group")
public class CommodityGroup implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     * ID, GROUP_CODE , COMMODITY_ID, CREATE_DATE, DESC_M, TOTAL_PERSON, MAX_PERSON, STATUS
     *
     * id, groupCode , commodityId, createDate, descM, totalPerson, maxPerson, status
     */


    @Id
    @Column(name = "ID")
    private String id;//ID

    @Column(name="GROUP_CODE")
    private String groupCode; //组团编码yyyyMMddHHmmss + 4位随机数字

    @Column(name = "COMMODITY_ID")
    private Integer commodityId;//商品Id

    @Column(name = "CREATE_DATE")
    private Date createDate;//创建时间

    @Column(name="GROUP_TIME")
    private String groupTime; //拼团队长选择时间

    @Column(name = "DESC_M")
    private String descM; //描述。

    @Column(name = "TOTAL_PERSON")
    private  Integer totalPerson;//当前的拼团人数 参加和退团均会更新改数据

    @Column(name = "MAX_PERSON")
    private  Integer maxPerson;//当前的拼团最大人数 修改商品时会触发同步改项数据

    @Column(name = "STATUS")
    private  Integer status;//当前组的状态  0 组团未成功 1 组团成功 -1 组团失败

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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

    public String getGroupTime() {
        return groupTime;
    }

    public void setGroupTime(String groupTime) {
        this.groupTime = groupTime;
    }

    public String getDescM() {
        return descM;
    }

    public void setDescM(String descM) {
        this.descM = descM;
    }

    public Integer getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(Integer totalPerson) {
        this.totalPerson = totalPerson;
    }

    public Integer getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(Integer maxPerson) {
        this.maxPerson = maxPerson;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CommodityGroup() { }

    public CommodityGroup(String id, String groupCode, Integer commodityId, Date createDate, String groupTime, String descM, Integer totalPerson, Integer maxPerson, Integer status) {
        this.id = id;
        this.groupCode = groupCode;
        this.commodityId = commodityId;
        this.createDate = createDate;
        this.groupTime = groupTime;
        this.descM = descM;
        this.totalPerson = totalPerson;
        this.maxPerson = maxPerson;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommodityGroup{" +
                "id='" + id + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", commodityId=" + commodityId +
                ", createDate=" + createDate +
                ", groupTime='" + groupTime + '\'' +
                ", descM='" + descM + '\'' +
                ", totalPerson=" + totalPerson +
                ", maxPerson=" + maxPerson +
                ", status=" + status +
                '}';
    }
}