package com.xiaoshu.wechat.tools;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 天气POJO类
 * @author Administrator
 *
 */
@XmlRootElement(name = "Weather.class")
public class Weather implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String city;//城市
	private String weather1;//天气  多云
	private String weather2;//天气 转雨
	private String wind_direction;//风向
	private String wind_scale;//风级
	private String dressing_suggestion;//穿衣指南
	private String climate_proposal;//气候建议
	private String outgoing_advice;//外出建议
	/**
	 * 城市
	 * @return
	 */
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 天气  多云
	 * @return
	 */
	public String getWeather1() {
		return weather1;
	}
	public void setWeather1(String weather1) {
		this.weather1 = weather1;
	}
	/**
	 * 天气 转雨
	 * @return
	 */
	public String getWeather2() {
		return weather2;
	}
	public void setWeather2(String weather2) {
		this.weather2 = weather2;
	}
	/**
	 * 风向
	 * @return
	 */
	public String getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(String windDirection) {
		wind_direction = windDirection;
	}
	/**
	 * 风级
	 * @return
	 */
	public String getWind_scale() {
		return wind_scale;
	}
	public void setWind_scale(String windScale) {
		wind_scale = windScale;
	}
	/**
	 * 穿衣指南
	 * @return
	 */
	public String getDressing_suggestion() {
		return dressing_suggestion;
	}
	public void setDressing_suggestion(String dressingSuggestion) {
		dressing_suggestion = dressingSuggestion;
	}
	/**
	 * 气候建议
	 * @return
	 */
	public String getClimate_proposal() {
		return climate_proposal;
	}
	public void setClimate_proposal(String climateProposal) {
		climate_proposal = climateProposal;
	}
	/**
	 * 外出建议
	 * @return
	 */
	public String getOutgoing_advice() {
		return outgoing_advice;
	}
	public void setOutgoing_advice(String outgoingAdvice) {
		outgoing_advice = outgoingAdvice;
	}
	public Weather(String city, String weather1, String weather2,
			String windDirection, String windScale, String dressingSuggestion,
			String climateProposal, String outgoingAdvice) {
		super();
		this.city = city;
		this.weather1 = weather1;
		this.weather2 = weather2;
		wind_direction = windDirection;
		wind_scale = windScale;
		dressing_suggestion = dressingSuggestion;
		climate_proposal = climateProposal;
		outgoing_advice = outgoingAdvice;
	}
	public Weather() {
		super();
	}
	@Override
	public String toString() {
		return "Weather [city=" + city + ", climate_proposal="
				+ climate_proposal + ", dressing_suggestion="
				+ dressing_suggestion + ", outgoing_advice=" + outgoing_advice
				+ ", weather1=" + weather1 + ", weather2=" + weather2
				+ ", wind_direction=" + wind_direction + ", wind_scale="
				+ wind_scale + "]";
	}
	
}
