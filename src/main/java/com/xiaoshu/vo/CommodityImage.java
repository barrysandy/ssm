package com.xiaoshu.vo;


/**
 * 所属类别:实体类 <br/> 
 * 用途: CommodityImage商品图片实体类<br/>
* @author: XGB
* @date: 2018-03-010 11:01
 */
public class CommodityImage {
	private String material_id;
	private String url;

	public String getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CommodityImage(String material_id, String url) {
		this.material_id = material_id;
		this.url = url;
	}

	public CommodityImage() {
	}

	@Override
	public String toString() {
		return "CommodityImage{" +
				"material_id='" + material_id + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}