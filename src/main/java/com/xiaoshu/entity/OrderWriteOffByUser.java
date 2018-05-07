package com.xiaoshu.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 商家核销绑定者实体类
 * @author XGB
 *
 */
@Component
@XmlRootElement(name = "OrderWriteOffByUser.class")
public class OrderWriteOffByUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; //( ID )
	private Integer sellerId;//商家id ( SELLER_ID )
	private String userId;//user_id ( USER_ID )
	private Integer states;//核销状态，1可以核销 0不可核销 ( STATES )
	private String createDate;//加入核销时间 ( CREATE_DATE )
	private FocusedUserInfo user;//核销人员

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStates() {
		return states;
	}

	public void setStates(Integer states) {
		this.states = states;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public FocusedUserInfo getUser() {
		return user;
	}

	public void setUser(FocusedUserInfo user) {
		this.user = user;
	}

	public OrderWriteOffByUser() { }

	public OrderWriteOffByUser(Integer id, Integer sellerId, String userId, Integer states, String createDate) {
		this.id = id;
		this.sellerId = sellerId;
		this.userId = userId;
		this.states = states;
		this.createDate = createDate;
	}

	public OrderWriteOffByUser(Integer id, Integer sellerId, String userId, Integer states, String createDate, FocusedUserInfo user) {
		this.id = id;
		this.sellerId = sellerId;
		this.userId = userId;
		this.states = states;
		this.createDate = createDate;
		this.user = user;
	}
}