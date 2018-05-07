package com.xiaoshu.entity;

import java.util.Date;
import java.util.List;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信活动报名表的实体类<br/>
* @author: XGB
* @date: 2018-02-06 11:18
 */
public class WechatActivitySign {

	private String id; //(ID)
	private String openid; //报名微信openid。如果支持授权登录和获取保存用户信息，用户在报名时会自动补充该信息。(OPENID)
	private String unionid;//微信用户unionid,多个公众号之间判断是否是同一个用户(UNIONID)
	private String userId; //报名微信用户id。如果支持授权登录和获取保存用户信息，用户在报名时会自动补充该信息。(USER_ID)
	private List<WechatActivitySignSet> wechatActivitySignSet; //活动报名配置。动态的活动报名所需列。
	private String wechatActivityId; //微信活动的id。确定报的那个活动。(WECHAT_ACTIVITY_ID)
	private String menuId;//系统菜单ID(这个是关联管理后台那个菜单的)(MENU_ID)
	private String parentMenuId; //系统父级菜单ID(PARENT_MENU_ID)
	private int status; //状态。-1逻辑删除 ，1存在。(STATUS)
	private int draw; //中奖标识。-1未中奖 ，1中奖。(DRAW)
	private Date createTime; //创建时间。(CREATE_TIME)
	private Date updateTime; //更新时间。(UPDATE_TIME)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<WechatActivitySignSet> getWechatActivitySignSet() {
		return wechatActivitySignSet;
	}

	public void setWechatActivitySignSet(List<WechatActivitySignSet> wechatActivitySignSet) {
		this.wechatActivitySignSet = wechatActivitySignSet;
	}

	public String getWechatActivityId() {
		return wechatActivityId;
	}

	public void setWechatActivityId(String wechatActivityId) {
		this.wechatActivityId = wechatActivityId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
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

	public WechatActivitySign() {
	}

	public WechatActivitySign(String id, String openid, String unionid, String userId, List<WechatActivitySignSet> wechatActivitySignSet, String wechatActivityId, String menuId, String parentMenuId, int status, int draw, Date createTime, Date updateTime) {
		this.id = id;
		this.openid = openid;
		this.unionid = unionid;
		this.userId = userId;
		this.wechatActivitySignSet = wechatActivitySignSet;
		this.wechatActivityId = wechatActivityId;
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
		this.status = status;
		this.draw = draw;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "WechatActivitySign{" +
				"id='" + id + '\'' +
				", openid='" + openid + '\'' +
				", unionid='" + unionid + '\'' +
				", userId='" + userId + '\'' +
				", wechatActivitySignSet=" + wechatActivitySignSet +
				", wechatActivityId='" + wechatActivityId + '\'' +
				", menuId='" + menuId + '\'' +
				", parentMenuId='" + parentMenuId + '\'' +
				", status=" + status +
				", draw=" + draw +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}