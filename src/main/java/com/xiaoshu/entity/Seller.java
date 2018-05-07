package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 商家实体类
 * @author XGB
 *
 */
@Component
@XmlRootElement(name = "Seller.class")
public class Seller implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商家
	 */
	private int id;
	private String sellerName;
	private String loginName;
	private String password;
	private String email;
	private String phone;
	private String openid;
	private String qq;
	private String menuId;
	private List<OrderWriteOffByUser> owobUser;//商家核销人员

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public List<OrderWriteOffByUser> getOwobUser() {
		return owobUser;
	}

	public void setOwobUser(List<OrderWriteOffByUser> owobUser) {
		this.owobUser = owobUser;
	}

	public Seller() {
	}

	public Seller(int id, String sellerName, String loginName, String password, String email, String phone, String openid, String qq, String menuId, List<OrderWriteOffByUser> owobUser) {
		this.id = id;
		this.sellerName = sellerName;
		this.loginName = loginName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.openid = openid;
		this.qq = qq;
		this.menuId = menuId;
		this.owobUser = owobUser;
	}

	@Override
	public String toString() {
		return "Seller{" +
				"id=" + id +
				", sellerName='" + sellerName + '\'' +
				", loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", openid='" + openid + '\'' +
				", qq='" + qq + '\'' +
				", menuId='" + menuId + '\'' +
				", owobUser=" + owobUser +
				'}';
	}
}