package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * 持久化的消息队列实体类
 * @author XGB
 * @date 2018-03-26 09:46
 */
@Component
@XmlRootElement(name = "PersistentMessageQueue.class")
public class PersistentMessageQueue implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID, RANK, URL, MSG_FROM, CREATE_TIME, PRE_EXECUTION_TIME , CONSUME_TIME, DESC_M, RESULT, STATUS
	 *
	 * id, rank, url, msgFrom, createTime, preExecutionTime , consumeTime, descM, result, status
	 */

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name="RANK")
	private int rank;// 消息等级 一共10等级，1~10级 1级为最高等级 最优先执行的消息队列（查询时-1表示所有等级）

	@Column(name="URL")
	private String url;//消息处理的地址

	@Column(name="MSG_FROM")
	private String msgFrom;//消息来源 当前支持 1，系统消息SYSTEM_MQ 2，用户消息 USER_MQ 3，管理员消息 ADMIN_MQ 4，接口消息  INTERFACE_MQ 5，其他消息 OTHER_MQ

	@Column(name="CREATE_TIME")
	private String createTime; //创建时间

	@Column(name = "PRE_EXECUTION_TIME")
	private String preExecutionTime;//消息预执行时间

	@Column(name = "CONSUME_TIME")
	private String consumeTime;//消息消费时间

	@Column(name = "DESC_M")
	private String descM; //消息描述。

	@Column(name = "RESULT")
	private String result; //消息处理结果。

	@Column(name="STATUS")
	private int status;//消息队列的状态 0待处理 1已处理 (STATE) -1表示查询全部

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPreExecutionTime() {
		return preExecutionTime;
	}

	public void setPreExecutionTime(String preExecutionTime) {
		this.preExecutionTime = preExecutionTime;
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public PersistentMessageQueue() { }

	public PersistentMessageQueue(String id, int rank, String url, String msgFrom, String createTime, String preExecutionTime, String consumeTime, String descM, String result, int status) {
		this.id = id;
		this.rank = rank;
		this.url = url;
		this.msgFrom = msgFrom;
		this.createTime = createTime;
		this.preExecutionTime = preExecutionTime;
		this.consumeTime = consumeTime;
		this.descM = descM;
		this.result = result;
		this.status = status;
	}

	@Override
	public String toString() {
		return "PersistentMessageQueue{" +
				"id='" + id + '\'' +
				", rank=" + rank +
				", url='" + url + '\'' +
				", msgFrom='" + msgFrom + '\'' +
				", createTime='" + createTime + '\'' +
				", preExecutionTime='" + preExecutionTime + '\'' +
				", consumeTime='" + consumeTime + '\'' +
				", descM='" + descM + '\'' +
				", result='" + result + '\'' +
				", status=" + status +
				'}';
	}
}