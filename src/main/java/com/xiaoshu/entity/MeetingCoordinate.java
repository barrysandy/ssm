package com.xiaoshu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 会议坐标实体类
 * @author XGB
 * @date 2018-05-18 15:00
 */
@Component
@Table(name="t_meeting_coordinate")
@XmlRootElement(name = "MeetingCoordinate.class")
public class MeetingCoordinate implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name="X")
	private String x;// 经度

	@Column(name="Y")
	private String y;// 维度

	@Column(name = "COORDINATE")
	private String coordinate; //坐标。

	@Column(name="MEETING_ID")
	private String meetingId;//会议ID

	@Column(name="NAME")
	private String name;//点位名称

	@Column(name="DESC_M")
	private String descM;//点位描述

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public MeetingCoordinate() { }

	public MeetingCoordinate(String id, String x, String y, String coordinate, String meetingId, String name, String descM) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.coordinate = coordinate;
		this.meetingId = meetingId;
		this.name = name;
		this.descM = descM;
	}

	@Override
	public String toString() {
		return "MeetingCoordinate{" +
				"id='" + id + '\'' +
				", x='" + x + '\'' +
				", y='" + y + '\'' +
				", coordinate='" + coordinate + '\'' +
				", meetingId='" + meetingId + '\'' +
				", name='" + name + '\'' +
				", descM='" + descM + '\'' +
				'}';
	}
}