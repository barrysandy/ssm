package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * WechatMaterial实体类===微信素材实体类
 * @author XGB
 * @date: 2018-01-22 14:24
 */

/**
 * 对于常用的素材，开发者可通过本接口上传到微信服务器，永久使用。新增的永久素材也可以在公众平台官网素材管理模块中查询管理。
 * 请注意：
 * 	1、最近更新：永久图片素材新增后，将带有URL返回给开发者，开发者可以在腾讯系域名内使用（腾讯系域名外使用，图片将被屏蔽）。
 * 	2、公众号的素材库保存总数量有上限：图文消息素材、图片素材上限为5000，其他类型为1000。
 * 	3、素材的格式大小等要求与公众平台官网一致：
 * 		图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式
 * 		语音（voice）：2M，播放长度不超过60s，mp3/wma/wav/amr格式
 * 		视频（video）：10MB，支持MP4格式
 * 		缩略图（thumb）：64KB，支持JPG格式
 * 	4、图文消息的具体内容中，微信后台将过滤外部的图片链接，图片url需通过"上传图文消息内的图片获取URL"接口上传图片获取。
 * 	5、"上传图文消息内的图片获取URL"接口所上传的图片，不占用公众号的素材库中图片数量的5000个的限制，图片仅支持jpg/png格式，大小必须在1MB以下。
 */
@Component
@XmlRootElement(name = "WechatMaterial.class")
public class WechatMaterial implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 素材ID
	 */
	private String id;

	/**
	 * 素材描述
	 */
	private String descM;

	/**
	 * 素材类型
	 * 图片（image）语音（voice）视频（video）缩略图（thumb）
	 */
	private String typese;

	/**
	 * 素材在服务器的url，上传成功后返回该URL路径
	 */
	private String url;
	/**
	 * 素材material_id，用于获取微信公众号上的素材时必须的参数
	 */
	private String material_id;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 删除标识符（1：正常  -1：删除）
	 */
	private int status;

	/**
	 * 系统菜单ID(这个是关联管理后台那个菜单的)
	 */
	private String menuId;

	/**
	 * PARENT_MENU_ID 所属公众号的Menu_ID(绑定公众号的Menu_ID，确定该关键字的所属公众号)
	 */
	private String parentMenuId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public String getTypese() {
		return typese;
	}

	public void setTypese(String typese) {
		this.typese = typese;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public WechatMaterial() {
	}

	public WechatMaterial(String id, String descM, String typese, String url, String material_id, Date createTime, Date updateTime, int status, String menuId, String parentMenuId) {
		this.id = id;
		this.descM = descM;
		this.typese = typese;
		this.url = url;
		this.material_id = material_id;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
	}

	@Override
	public String toString() {
		return "WechatMaterial{" +
				"id='" + id + '\'' +
				", descM='" + descM + '\'' +
				", typese='" + typese + '\'' +
				", url='" + url + '\'' +
				", material_id='" + material_id + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", status=" + status +
				", menuId='" + menuId + '\'' +
				", parentMenuId='" + parentMenuId + '\'' +
				'}';
	}
}
