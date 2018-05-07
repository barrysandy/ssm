package com.xiaoshu.entity;

import java.util.List;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信活动表的实体类<br/>
* @author: XGB
* @date: 2018-02-06 11:02
 */
public class WechatActivity {

	private String id; //(ID)
	private String title; //活动标题，简介。(TITLE)
	private String descM; //活动描述。(DESC_M)
	private String url; //活动链接（根据活动id，自动生成）。(URL)
	private String returnPage;// 如果填写了该项，会强制该链接进入填写的页面。(RETURN_PAGE)
	private String types;//活动类型 (TYPES)
	private Integer share; //是否支持分享。 -1 不支持分享，页面菜单中将隐藏分享的一系列按钮   1支持分享。(SHARE)
	private String shareTitle; //分享标题。(SHARE_TITLE)
	private String shareDescM; //分享描述。(SHARE_DESC_M)
	private String shareImage; //分享图片。(SHARE_IMAGE)
	private String bindingWechatId; //绑定的微信id，确定是那个微信号的活动。(BINDING_WECHAT_ID)
	private Integer authorised; //是否需要微信授权登录。 -1 不需要，1需要。说明：该项只有服务号才能开启。(AUTHORISED)
	private Integer supportGetWechatMsg; //是否支持登录后保存微信用户信息（头像，昵称，openid）。-1 不支持 1 支持。说明：该项配置只有服务号才有效。(SUPPORT_GET_WECHAT_MSG)
	private String subscribeWechatId; //订阅号id。如果支持授权登录，该项如果不为null OR '' ,表示是需要关注的subscribeWechatId订阅号才能报名。授权时也会获取订阅号的用户来同步用户信息(SUBSCRIBE_WECHAT_ID)
	private Integer prizes; //是否开启抽奖。-1 不开启， 1开启。(PRIZES)
	private String prizesType; //抽奖形式。目前只支持一种后台抽奖方式。(PRIZES_TYPE)
	private String beginTime; //报名开始时间。(BEGIN_TIME)
	private String endTime; //报名结束时间。(END_TIME)
	private Integer status; //状态。-1冻结的活动 ，1进行的活动。(STATUS)
	private String createTime; //创建时间。(CREATE_TIME)
	private String updateTime; //更新时间。(UPDATE_TIME)
	private String menuId;//系统菜单ID(这个是关联管理后台那个菜单的)(MENU_ID)
	private String parentMenuId; //系统父级菜单ID(PARENT_MENU_ID)

	private Integer commodityId;//商品id（报名活动要与商品建立one-one关系映射）(COMMODITY_ID)
	private String scanUserIdArray;//扫码人员（SCAN_USER_ID_ARRAY）
	private Integer timeStatus;//状态 0过期  1正常 2 未开始

	private List<WechatActivitySignSet> listWechatActivitySignSet; //活动报名配置。动态的活动报名所需列。
	private List<WechatActivityPrize> listPrize; //活动奖品，抽奖时会按照奖品来生成奖励。

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Integer getShare() {
		return share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareDescM() {
		return shareDescM;
	}

	public void setShareDescM(String shareDescM) {
		this.shareDescM = shareDescM;
	}

	public String getShareImage() {
		return shareImage;
	}

	public void setShareImage(String shareImage) {
		this.shareImage = shareImage;
	}

	public String getBindingWechatId() {
		return bindingWechatId;
	}

	public void setBindingWechatId(String bindingWechatId) {
		this.bindingWechatId = bindingWechatId;
	}

	public Integer getAuthorised() {
		return authorised;
	}

	public void setAuthorised(Integer authorised) {
		this.authorised = authorised;
	}

	public Integer getSupportGetWechatMsg() {
		return supportGetWechatMsg;
	}

	public void setSupportGetWechatMsg(Integer supportGetWechatMsg) {
		this.supportGetWechatMsg = supportGetWechatMsg;
	}

	public String getSubscribeWechatId() {
		return subscribeWechatId;
	}

	public void setSubscribeWechatId(String subscribeWechatId) {
		this.subscribeWechatId = subscribeWechatId;
	}

	public Integer getPrizes() {
		return prizes;
	}

	public void setPrizes(Integer prizes) {
		this.prizes = prizes;
	}

	public String getPrizesType() {
		return prizesType;
	}

	public void setPrizesType(String prizesType) {
		this.prizesType = prizesType;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
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

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getScanUserIdArray() {
		return scanUserIdArray;
	}

	public void setScanUserIdArray(String scanUserIdArray) {
		this.scanUserIdArray = scanUserIdArray;
	}

	public Integer getTimeStatus() {
		return timeStatus;
	}

	public void setTimeStatus(Integer timeStatus) {
		this.timeStatus = timeStatus;
	}

	public List<WechatActivitySignSet> getListWechatActivitySignSet() {
		return listWechatActivitySignSet;
	}

	public void setListWechatActivitySignSet(List<WechatActivitySignSet> listWechatActivitySignSet) {
		this.listWechatActivitySignSet = listWechatActivitySignSet;
	}

	public List<WechatActivityPrize> getListPrize() {
		return listPrize;
	}

	public void setListPrize(List<WechatActivityPrize> listPrize) {
		this.listPrize = listPrize;
	}


	public WechatActivity() { }

	public WechatActivity(String id, String title, String descM, String url, String returnPage, String types, Integer share, String shareTitle, String shareDescM, String shareImage, String bindingWechatId, Integer authorised, Integer supportGetWechatMsg, String subscribeWechatId, Integer prizes, String prizesType, String beginTime, String endTime, Integer status, String createTime, String updateTime, String menuId, String parentMenuId, Integer commodityId, String scanUserIdArray, Integer timeStatus) {
		this.id = id;
		this.title = title;
		this.descM = descM;
		this.url = url;
		this.returnPage = returnPage;
		this.types = types;
		this.share = share;
		this.shareTitle = shareTitle;
		this.shareDescM = shareDescM;
		this.shareImage = shareImage;
		this.bindingWechatId = bindingWechatId;
		this.authorised = authorised;
		this.supportGetWechatMsg = supportGetWechatMsg;
		this.subscribeWechatId = subscribeWechatId;
		this.prizes = prizes;
		this.prizesType = prizesType;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
		this.commodityId = commodityId;
		this.scanUserIdArray = scanUserIdArray;
		this.timeStatus = timeStatus;
	}

	public WechatActivity(String id, String title, String descM, String url, String returnPage, String types, Integer share, String shareTitle, String shareDescM, String shareImage, String bindingWechatId, Integer authorised, Integer supportGetWechatMsg, String subscribeWechatId, Integer prizes, String prizesType, String beginTime, String endTime, Integer status, String createTime, String updateTime, String menuId, String parentMenuId, Integer commodityId, String scanUserIdArray, Integer timeStatus, List<WechatActivitySignSet> listWechatActivitySignSet, List<WechatActivityPrize> listPrize) {
		this.id = id;
		this.title = title;
		this.descM = descM;
		this.url = url;
		this.returnPage = returnPage;
		this.types = types;
		this.share = share;
		this.shareTitle = shareTitle;
		this.shareDescM = shareDescM;
		this.shareImage = shareImage;
		this.bindingWechatId = bindingWechatId;
		this.authorised = authorised;
		this.supportGetWechatMsg = supportGetWechatMsg;
		this.subscribeWechatId = subscribeWechatId;
		this.prizes = prizes;
		this.prizesType = prizesType;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
		this.commodityId = commodityId;
		this.scanUserIdArray = scanUserIdArray;
		this.timeStatus = timeStatus;
		this.listWechatActivitySignSet = listWechatActivitySignSet;
		this.listPrize = listPrize;
	}

	@Override
	public String toString() {
		return "WechatActivity{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", descM='" + descM + '\'' +
				", url='" + url + '\'' +
				", returnPage='" + returnPage + '\'' +
				", types='" + types + '\'' +
				", share=" + share +
				", shareTitle='" + shareTitle + '\'' +
				", shareDescM='" + shareDescM + '\'' +
				", shareImage='" + shareImage + '\'' +
				", bindingWechatId='" + bindingWechatId + '\'' +
				", authorised=" + authorised +
				", supportGetWechatMsg=" + supportGetWechatMsg +
				", subscribeWechatId='" + subscribeWechatId + '\'' +
				", prizes=" + prizes +
				", prizesType='" + prizesType + '\'' +
				", beginTime='" + beginTime + '\'' +
				", endTime='" + endTime + '\'' +
				", status=" + status +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", menuId='" + menuId + '\'' +
				", parentMenuId='" + parentMenuId + '\'' +
				", commodityId=" + commodityId +
				", scanUserIdArray='" + scanUserIdArray + '\'' +
				", timeStatus=" + timeStatus +
				", listWechatActivitySignSet=" + listWechatActivitySignSet +
				", listPrize=" + listPrize +
				'}';
	}
}