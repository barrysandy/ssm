package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 商品实体类
 * @author XGB
 *
 */
@Component
@XmlRootElement(name = "Commodity.class")
public class Commodity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品
	 */
	private Integer id;
	private String commodityName;//商品名称
	private String commodityDetails;//详情描述
	private Integer commodityStock;//库存
	private String image;//商品封面图片
	private Double commodityPrice;//商品均价
	private Integer sellerId;//商品所属的商家Seller
	private Seller seller;//商品所属的商家
	private Integer commodityState;//商品状态 1上架 0下架    查询中-1表示忽略该属性条件
	private String arrayImg;//保存图片集合，第一“，”解析的为是商品列表图片
	private String createDate;//有效开始时间
	private String invalidDate;//失效时间
	private Integer share; //是否支持分享。 -1 不支持分享，页面菜单中将隐藏分享的一系列按钮   1支持分享。(share)
	private String shareTitle; //分享标题。(shareTitle)
	private String shareDescM; //分享描述。(shareDescM)
	private String shareImage; //分享图片。(shareImage)
	private String url; //详情的页面路径。(url)
	private Integer maxNumber;//商品成人购买上限 -1无上限
	private Integer maxNumber2;//商品儿童购买上限 -1无上限
	private Integer groupNumber;//商品组团条件 -1无条件
	private Integer groupNumber2;//商品儿童组团条件 -1无条件
	private Integer typese; //商品类型   0 报名类型商品（维护活动类com.xiaoshu.entity.WechatActivity）  1 单一购买商品  2 组团商品 3 公众号报名回复，此项需要隐藏。
	private String wechatActivityId; //活动id PS：type=SIGN才维护该项 (one-one)
	private Integer timeStatus;//状态 0过期  1正常 2 未开始
	private Integer totalGroup;//下单人数
	private Integer maxGroup;//最多组团数  如果 -1无条件
	private Integer createGroupNumber;//一个团人数 最少1人，如果类型为GROUP商品初始化为1
	private String videoMaterialId;//商品视频图片
	private Integer showList;//是否显示在列表页面  0 不显示 1 显示 -1 无视该条件
	private List<FileResource> resource; //资源集合
	private FileResource videoResource; //封面资源

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityDetails() {
		return commodityDetails;
	}

	public void setCommodityDetails(String commodityDetails) {
		this.commodityDetails = commodityDetails;
	}

	public Integer getCommodityStock() {
		return commodityStock;
	}

	public void setCommodityStock(Integer commodityStock) {
		this.commodityStock = commodityStock;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getCommodityPrice() {
		return commodityPrice;
	}

	public void setCommodityPrice(Double commodityPrice) {
		this.commodityPrice = commodityPrice;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Integer getCommodityState() {
		return commodityState;
	}

	public void setCommodityState(Integer commodityState) {
		this.commodityState = commodityState;
	}

	public String getArrayImg() {
		return arrayImg;
	}

	public void setArrayImg(String arrayImg) {
		this.arrayImg = arrayImg;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}

	public Integer getMaxNumber2() {
		return maxNumber2;
	}

	public void setMaxNumber2(Integer maxNumber2) {
		this.maxNumber2 = maxNumber2;
	}

	public Integer getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}

	public Integer getGroupNumber2() {
		return groupNumber2;
	}

	public void setGroupNumber2(Integer groupNumber2) {
		this.groupNumber2 = groupNumber2;
	}

	public Integer getTypese() {
		return typese;
	}

	public void setTypese(Integer typese) {
		this.typese = typese;
	}

	public String getWechatActivityId() {
		return wechatActivityId;
	}

	public void setWechatActivityId(String wechatActivityId) {
		this.wechatActivityId = wechatActivityId;
	}

	public Integer getTimeStatus() {
		return timeStatus;
	}

	public void setTimeStatus(Integer timeStatus) {
		this.timeStatus = timeStatus;
	}

	public Integer getTotalGroup() {
		return totalGroup;
	}

	public void setTotalGroup(Integer totalGroup) {
		this.totalGroup = totalGroup;
	}

	public Integer getMaxGroup() {
		return maxGroup;
	}

	public void setMaxGroup(Integer maxGroup) {
		this.maxGroup = maxGroup;
	}

	public Integer getCreateGroupNumber() {
		return createGroupNumber;
	}

	public void setCreateGroupNumber(Integer createGroupNumber) {
		this.createGroupNumber = createGroupNumber;
	}

	public String getVideoMaterialId() {
		return videoMaterialId;
	}

	public void setVideoMaterialId(String videoMaterialId) {
		this.videoMaterialId = videoMaterialId;
	}

	public Integer getShowList() {
		return showList;
	}

	public void setShowList(Integer showList) {
		this.showList = showList;
	}

	public List<FileResource> getResource() {
		return resource;
	}

	public void setResource(List<FileResource> resource) {
		this.resource = resource;
	}

	public FileResource getVideoResource() {
		return videoResource;
	}

	public void setVideoResource(FileResource videoResource) {
		this.videoResource = videoResource;
	}

	public Commodity() { }

	public Commodity(Integer id, String commodityName, String commodityDetails, Integer commodityStock, String image, Double commodityPrice, Integer sellerId, Seller seller, Integer commodityState, String arrayImg, String createDate, String invalidDate, Integer share, String shareTitle, String shareDescM, String shareImage, String url, Integer maxNumber, Integer maxNumber2, Integer groupNumber, Integer groupNumber2, Integer typese, String wechatActivityId, Integer timeStatus, Integer totalGroup, Integer maxGroup, Integer createGroupNumber, String videoMaterialId, Integer showList) {
		this.id = id;
		this.commodityName = commodityName;
		this.commodityDetails = commodityDetails;
		this.commodityStock = commodityStock;
		this.image = image;
		this.commodityPrice = commodityPrice;
		this.sellerId = sellerId;
		this.seller = seller;
		this.commodityState = commodityState;
		this.arrayImg = arrayImg;
		this.createDate = createDate;
		this.invalidDate = invalidDate;
		this.share = share;
		this.shareTitle = shareTitle;
		this.shareDescM = shareDescM;
		this.shareImage = shareImage;
		this.url = url;
		this.maxNumber = maxNumber;
		this.maxNumber2 = maxNumber2;
		this.groupNumber = groupNumber;
		this.groupNumber2 = groupNumber2;
		this.typese = typese;
		this.wechatActivityId = wechatActivityId;
		this.timeStatus = timeStatus;
		this.totalGroup = totalGroup;
		this.maxGroup = maxGroup;
		this.createGroupNumber = createGroupNumber;
		this.videoMaterialId = videoMaterialId;
		this.showList = showList;
	}

	public Commodity(Integer id, String commodityName, String commodityDetails, Integer commodityStock, String image, Double commodityPrice, Integer sellerId, Seller seller, Integer commodityState, String arrayImg, String createDate, String invalidDate, Integer share, String shareTitle, String shareDescM, String shareImage, String url, Integer maxNumber, Integer maxNumber2, Integer groupNumber, Integer groupNumber2, Integer typese, String wechatActivityId, Integer timeStatus, Integer totalGroup, Integer maxGroup, Integer createGroupNumber, String videoMaterialId, Integer showList, List<FileResource> resource, FileResource videoResource) {
		this.id = id;
		this.commodityName = commodityName;
		this.commodityDetails = commodityDetails;
		this.commodityStock = commodityStock;
		this.image = image;
		this.commodityPrice = commodityPrice;
		this.sellerId = sellerId;
		this.seller = seller;
		this.commodityState = commodityState;
		this.arrayImg = arrayImg;
		this.createDate = createDate;
		this.invalidDate = invalidDate;
		this.share = share;
		this.shareTitle = shareTitle;
		this.shareDescM = shareDescM;
		this.shareImage = shareImage;
		this.url = url;
		this.maxNumber = maxNumber;
		this.maxNumber2 = maxNumber2;
		this.groupNumber = groupNumber;
		this.groupNumber2 = groupNumber2;
		this.typese = typese;
		this.wechatActivityId = wechatActivityId;
		this.timeStatus = timeStatus;
		this.totalGroup = totalGroup;
		this.maxGroup = maxGroup;
		this.createGroupNumber = createGroupNumber;
		this.videoMaterialId = videoMaterialId;
		this.showList = showList;
		this.resource = resource;
		this.videoResource = videoResource;
	}
}