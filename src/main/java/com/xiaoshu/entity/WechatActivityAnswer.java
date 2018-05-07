package com.xiaoshu.entity;

import java.util.Date;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信活动问答题的答案实体类<br/>
* @author: XGB
* @date: 2018-03-05 10:46
 */
public class WechatActivityAnswer {

	private String id; //(ID)
	private String options; //答案选项。(OPTIONS)
	private int sort; //排序。(SORT)
	private String descM; //问答题描述。(DESC_M)
	private int status; //状态。-1冻结的题 ，1进行的题。(STATUS)
	private Date createTime; //创建时间。(CREATE_TIME)
	private Date updateTime; //更新时间。(UPDATE_TIME)
	private String questionsId;//问题ID(QUESTIONS_ID)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getQuestionsId() {
		return questionsId;
	}

	public void setQuestionsId(String questionsId) {
		this.questionsId = questionsId;
	}

	public WechatActivityAnswer(String id, String options, int sort, String descM, int status, Date createTime, Date updateTime, String questionsId) {
		this.id = id;
		this.options = options;
		this.sort = sort;
		this.descM = descM;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.questionsId = questionsId;
	}

	public WechatActivityAnswer() {
	}

	@Override
	public String toString() {
		return "WechatActivityAnswer{" +
				"id='" + id + '\'' +
				", options='" + options + '\'' +
				", sort=" + sort +
				", descM='" + descM + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", questionsId='" + questionsId + '\'' +
				'}';
	}
}