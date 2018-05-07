package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 文件资源实体类
 * @author XGB
 * @date: 2018-04-17 16:11
 */
@Component
@XmlRootElement(name = "FileResource.class")
@Table(name = "file_resource")
public class FileResource implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID, URL, TYPESE, MATERIAL_ID, CITE_ID, CITE_CLASS ,CREATE_TIME ,UPDATE_TIME ,DESC_M
	 *
	 * id, url, typese, materialId, citeId, citeClass ,createTime , updateTime , descM
	 */

	@Id
	@Column(name = "ID")
	private String id; // (ID)

	@Column(name="URL")
	private String url;//资源地址

	@Column(name="TYPESE")
	private String typese;//资源类型 图片 文件 ...

	@Column(name="MATERIAL_ID")
	private String materialId;//资源materialId

	@Column(name="CITE_ID")
	private Integer citeId;//引用ID 例如商品的ID，活动的ID ...

	@Column(name="CITE_CLASS")
	private String citeClass;//引用CLASS 例如商品的为Commodity ...

	@Column(name="CREATE_TIME")
	private String createTime; //创建时间。(CREATE_TIME)

	@Column(name="UPDATE_TIME")
	private String updateTime; //更新时间。(UPDATE_TIME)

	@Column(name="DESC_M")
	private String descM; //描述(DESC_M)

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTypese() {
		return typese;
	}

	public void setTypese(String typese) {
		this.typese = typese;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Integer getCiteId() {
		return citeId;
	}

	public void setCiteId(Integer citeId) {
		this.citeId = citeId;
	}

	public String getCiteClass() {
		return citeClass;
	}

	public void setCiteClass(String citeClass) {
		this.citeClass = citeClass;
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

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public FileResource() { }

	public FileResource(String id, String url, String typese, String materialId, Integer citeId, String citeClass, String createTime, String updateTime, String descM) {
		this.id = id;
		this.url = url;
		this.typese = typese;
		this.materialId = materialId;
		this.citeId = citeId;
		this.citeClass = citeClass;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.descM = descM;
	}

	@Override
	public String toString() {
		return "FileResource{" +
				"id='" + id + '\'' +
				", url='" + url + '\'' +
				", typese='" + typese + '\'' +
				", materialId='" + materialId + '\'' +
				", citeId=" + citeId +
				", citeClass='" + citeClass + '\'' +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", descM='" + descM + '\'' +
				'}';
	}
}