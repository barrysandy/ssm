package com.xiaoshu.vo;

/**
 * 所属类别:商品列表，展示列表
* @author: XGB
* @date: 2018-04-19 13:43
 */
public class CommodityVo {
	private Integer id;
	private String name;
	private String price;
	private Integer status;
	private Integer type;
	private String button;
	private String url;
	private String image;
	private Integer total;//组团总是
	private String wechatActivityId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getWechatActivityId() {
		return wechatActivityId;
	}

	public void setWechatActivityId(String wechatActivityId) {
		this.wechatActivityId = wechatActivityId;
	}

	public CommodityVo() { }

	public CommodityVo(Integer id, String name, String price, Integer status, Integer type, String button, String url, String image, Integer total, String wechatActivityId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.status = status;
		this.type = type;
		this.button = button;
		this.url = url;
		this.image = image;
		this.total = total;
		this.wechatActivityId = wechatActivityId;
	}

	@Override
	public String toString() {
		return "CommodityVo{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price='" + price + '\'' +
				", status=" + status +
				", type=" + type +
				", button='" + button + '\'' +
				", url='" + url + '\'' +
				", image='" + image + '\'' +
				", total=" + total +
				", wechatActivityId='" + wechatActivityId + '\'' +
				'}';
	}
}