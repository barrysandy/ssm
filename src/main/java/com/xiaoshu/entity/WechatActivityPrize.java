package com.xiaoshu.entity;

import java.util.Date;
import java.util.List;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信活动奖品表的实体类<br/>
* @author: XGB
* @date: 2018-02-06 11:22
 */
public class WechatActivityPrize {

	private String id; //(ID)
	private String name; //奖品名称。(NAME)
	private String descM; //奖品描述。(DESC_M)
	private String image; //奖品图片。(IMAGE)
	private int quantity; //奖品数量。(QUANTITY)
	private String odds; //获奖概率。(ODDS)
	private Date invalidTime; //奖品失效时间。(INVALID_TIME)
	private String wechatActivityId; //微信活动的id。确定报的那个活动。(WECHAT_ACTIVITY_ID)
	private int status; //状态。-1逻辑删除 ，1存在。(STATUS)
	private Date createTime; //创建时间。(CREATE_TIME)
	private Date updateTime; //更新时间。(UPDATE_TIME)
	private String menuId;//系统菜单ID(这个是关联管理后台那个菜单的)(MENU_ID)
	private String parentMenuId; //系统父级菜单ID(PARENT_MENU_ID)
	private int commodityId; //商品ID，如果该值不为-1 ，表示奖品将和商品绑定，生成奖品为商品订单，能直接进行消费。(COMMODITY_ID)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getWechatActivityId() {
		return wechatActivityId;
	}

	public void setWechatActivityId(String wechatActivityId) {
		this.wechatActivityId = wechatActivityId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public WechatActivityPrize() { }

	public WechatActivityPrize(String id, String name, String descM, String image, int quantity, String odds, Date invalidTime, String wechatActivityId, int status, Date createTime, Date updateTime, String menuId, String parentMenuId, int commodityId) {
		this.id = id;
		this.name = name;
		this.descM = descM;
		this.image = image;
		this.quantity = quantity;
		this.odds = odds;
		this.invalidTime = invalidTime;
		this.wechatActivityId = wechatActivityId;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
		this.commodityId = commodityId;
	}

	@Override
	public String toString() {
		return "WechatActivityPrize{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", descM='" + descM + '\'' +
				", image='" + image + '\'' +
				", quantity=" + quantity +
				", odds='" + odds + '\'' +
				", invalidTime=" + invalidTime +
				", wechatActivityId='" + wechatActivityId + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", menuId='" + menuId + '\'' +
				", parentMenuId='" + parentMenuId + '\'' +
				", commodityId=" + commodityId +
				'}';
	}
}