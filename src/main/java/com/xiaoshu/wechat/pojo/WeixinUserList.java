package com.xiaoshu.wechat.pojo;

import java.util.List;

/**
 * 关注用户列表
 * @author XGB
 */
public class WeixinUserList {
	/**
	 * 公众账号的总关注用户数
	 */
	private int total;

	/**
	 * 获取的OpenID个数
	 */
	private int count;

	/**
	 * OpenID列表
	 */
	private List<String> data;

	/**
	 * 拉取列表的后一个用户的OPENID
	 */
	private String next_openid;

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
	public WeixinUserList(int total, int count, List<String> data, String next_openid) {
		super();
		this.total = total;
		this.count = count;
		this.data = data;
		this.next_openid = next_openid;
	}
	public WeixinUserList() {
		super();
	}
	@Override
	public String toString() {
		return "WeixinUserList [total=" + total + ", count=" + count + ", data=" + data + ", next_openid=" + next_openid
				+ "]";
	}

	
}
