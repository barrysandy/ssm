package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息记录实体类
 * @author XGB
 * @date: 2018-03-22 9:30
 */
@Component
@XmlRootElement(name = "MessageRecord.class")
@Table(name = "message_record")
public class MessageRecord implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID, MOBILE, SIGN, CONTENT, USER_ID, RESPONSE_STATUS, CREATE_TIME, UPDATE_TIME, DESC_M, STATUS ,CODE
	 *
	 * id, mobile, sign, content, userId, responseStatus, createTime, updateTime, descM, status ,code
	 */

	@Id
	@Column(name = "ID")
	private String id; // (ID)

	@Column(name="MOBILE")
	private String mobile;//目标电话号码 (MOBILE)

	@Column(name="SIGN")
	private String sign;//消息签名 (SIGN)

	@Column(name="CONTENT")
	private String content;//消息内容 (CONTENT)

	@Column(name="USER_ID")
	private String userId;//发送管理员的ID (USER_ID)

	@Column(name="RESPONSE_STATUS")
	private String responseStatus;//返回的状态消息 (RESPONSE_STATUS)

	@Column(name="CREATE_TIME")
	private Date createTime; //创建时间。(CREATE_TIME)

	@Column(name="UPDATE_TIME")
	private Date updateTime; //更新时间。(UPDATE_TIME)

	@Column(name="DESC_M")
	private String descM; //描述(DESC_M)

	@Column(name="CODE")
	private String code; //短信发送CODE，验证是否有重复发送的凭证(CODE),唯一  例如订单的短信 为订单号+订单的消费时间进行字符编码后生成的字符

	@Column(name="STATUS")
	private int status;//消息状态 -1删除 0存在(STATUS)

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public MessageRecord() { }

	public MessageRecord(String id, String mobile, String sign, String content, String userId, String responseStatus, Date createTime, Date updateTime, String descM, String code, int status) {
		this.id = id;
		this.mobile = mobile;
		this.sign = sign;
		this.content = content;
		this.userId = userId;
		this.responseStatus = responseStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.descM = descM;
		this.code = code;
		this.status = status;
	}

	@Override
	public String toString() {
		return "MessageRecord{" +
				"id='" + id + '\'' +
				", mobile='" + mobile + '\'' +
				", sign='" + sign + '\'' +
				", content='" + content + '\'' +
				", userId='" + userId + '\'' +
				", responseStatus='" + responseStatus + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", descM='" + descM + '\'' +
				", code='" + code + '\'' +
				", status=" + status +
				'}';
	}
}