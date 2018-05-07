package com.xiaoshu.entity;

import java.util.Date;
import java.util.List;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信活动问答题的实体类<br/>
* @author: XGB
* @date: 2018-03-05 10:38
 */
public class WechatActivityTestQuestions {

	private String id; //(ID)
	private String title; //问答题标题。(TITLE)
	private int sort; //排序。(SORT)
	private String descM; //问答题描述。(DESC_M)
	private int status; //状态。-1冻结的题 ，1进行的题。(STATUS)
	private Date createTime; //创建时间。(CREATE_TIME)
	private Date updateTime; //更新时间。(UPDATE_TIME)
	private String answerId;//正确答案(ANSWER_ID)
	private String wechatActivityId; //绑定的活动ID(WECHAT_ACTIVITY_ID)
	private List<WechatActivityAnswer> listWechatActivityAnswer; //题目的答案。

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getWechatActivityId() {
		return wechatActivityId;
	}

	public void setWechatActivityId(String wechatActivityId) {
		this.wechatActivityId = wechatActivityId;
	}

	public List<WechatActivityAnswer> getListWechatActivityAnswer() {
		return listWechatActivityAnswer;
	}

	public void setListWechatActivityAnswer(List<WechatActivityAnswer> listWechatActivityAnswer) {
		this.listWechatActivityAnswer = listWechatActivityAnswer;
	}

	public WechatActivityTestQuestions(String id, String title, int sort, String descM, int status, Date createTime, Date updateTime, String answerId, String wechatActivityId, List<WechatActivityAnswer> listWechatActivityAnswer) {
		this.id = id;
		this.title = title;
		this.sort = sort;
		this.descM = descM;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.answerId = answerId;
		this.wechatActivityId = wechatActivityId;
		this.listWechatActivityAnswer = listWechatActivityAnswer;
	}

	public WechatActivityTestQuestions() {
	}

	@Override
	public String toString() {
		return "WechatActivityTestQuestions{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", sort=" + sort +
				", descM='" + descM + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", answerId='" + answerId + '\'' +
				", wechatActivityId='" + wechatActivityId + '\'' +
				", listWechatActivityAnswer=" + listWechatActivityAnswer +
				'}';
	}
}