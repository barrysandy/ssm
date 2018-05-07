package com.xiaoshu.entity;

import java.util.Date;
import java.util.List;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信活动中奖表的实体类<br/>
* @author: XGB
* @date: 2018-02-06 11:24
 */
public class WechatActivityWinning {

	private String id; //(ID)
	private String descM; //中奖描述。(DESC_M)
	private String code;  //核销码。(CODE)
	private List<WechatActivityPrize> prizeList; //关联的奖品
	private List<WechatActivitySignSet> wechatActivitySignSet; //获奖者的报名配置。。
	private String wechatActivityId; //微信活动的id。确定报的那个活动。(WECHAT_ACTIVITY_ID)
	private String wechatActivitySignId; //活动报名id。如果为非授权登录方式报名，该项能确定报名者的信息。(WECHAT_ACTIVITY_SIGN_ID)
	private String userId; //活动报名用户id。在授权登录开启时才会关联该id，用来确定登录状态下报名者的userId。(USER_ID)
	private int status; //核销状态。0 未核销 ，1已核销。(STATUS)
	private Date createTime; //创建时间。(CREATE_TIME)
	private Date updateTime; //更新时间。(UPDATE_TIME)
	private String fromatCreateTime;
	private String fromatUpdateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<WechatActivityPrize> getPrizeList() {
		return prizeList;
	}

	public void setPrizeList(List<WechatActivityPrize> prizeList) {
		this.prizeList = prizeList;
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

	public String getWechatActivitySignId() {
		return wechatActivitySignId;
	}

	public void setWechatActivitySignId(String wechatActivitySignId) {
		this.wechatActivitySignId = wechatActivitySignId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getFromatCreateTime() {
		return fromatCreateTime;
	}

	public void setFromatCreateTime(String fromatCreateTime) {
		this.fromatCreateTime = fromatCreateTime;
	}

	public String getFromatUpdateTime() {
		return fromatUpdateTime;
	}

	public void setFromatUpdateTime(String fromatUpdateTime) {
		this.fromatUpdateTime = fromatUpdateTime;
	}

	public WechatActivityWinning(String id, String descM, String code, List<WechatActivityPrize> prizeList, List<WechatActivitySignSet> wechatActivitySignSet, String wechatActivityId, String wechatActivitySignId, String userId, int status, Date createTime, Date updateTime) {
		this.id = id;
		this.descM = descM;
		this.code = code;
		this.prizeList = prizeList;
		this.wechatActivitySignSet = wechatActivitySignSet;
		this.wechatActivityId = wechatActivityId;
		this.wechatActivitySignId = wechatActivitySignId;
		this.userId = userId;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public WechatActivityWinning() {
	}

	@Override
	public String toString() {
		return "WechatActivityWinning{" +
				"id='" + id + '\'' +
				", descM='" + descM + '\'' +
				", code='" + code + '\'' +
				", prizeList=" + prizeList +
				", wechatActivitySignSet=" + wechatActivitySignSet +
				", wechatActivityId='" + wechatActivityId + '\'' +
				", wechatActivitySignId='" + wechatActivitySignId + '\'' +
				", userId='" + userId + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}