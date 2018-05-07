package com.xiaoshu.vo;

/**
 * 所属类别:商品vo 用于扫码后的组装，展示列表
* @author: XGB
* @date: 2018-04-24 13:43
 */
public class CommodityVoQRCode {
	private Integer cid;
	private String gid;
	private String title;
	private String descM;
	private String image;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CommodityVoQRCode() {
	}

	public CommodityVoQRCode(Integer cid, String gid, String title, String descM, String image) {
		this.cid = cid;
		this.gid = gid;
		this.title = title;
		this.descM = descM;
		this.image = image;
	}

	@Override
	public String toString() {
		return "CommodityVoQRCode{" +
				"cid=" + cid +
				", gid='" + gid + '\'' +
				", title='" + title + '\'' +
				", descM='" + descM + '\'' +
				", image='" + image + '\'' +
				'}';
	}
}