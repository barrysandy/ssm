package com.xiaoshu.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * 商品订单实体类
 * @author XGB
 *
 */
@Component
@XmlRootElement(name = "CommodityOrder.class")
public class CommodityOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer co_id;
	private Integer commodity_id;//商品id
	private Commodity commodity;//商品
	private Integer commodity_number;//商品订单数量
	private double commodity_price;//商品下单价格
	private Integer order_id;//订单id
	public Integer getCo_id() {
		return co_id;
	}
	public void setCo_id(Integer co_id) {
		this.co_id = co_id;
	}
	public Integer getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(Integer commodity_id) {
		this.commodity_id = commodity_id;
	}
	public Commodity getCommodity() {
		return commodity;
	}
	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}
	public Integer getCommodity_number() {
		return commodity_number;
	}
	public void setCommodity_number(Integer commodity_number) {
		this.commodity_number = commodity_number;
	}
	public double getCommodity_price() {
		return commodity_price;
	}
	public void setCommodity_price(double commodity_price) {
		this.commodity_price = commodity_price;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public CommodityOrder(Integer co_id, Integer commodity_id, Commodity commodity, Integer commodity_number,
			double commodity_price, Integer order_id) {
		super();
		this.co_id = co_id;
		this.commodity_id = commodity_id;
		this.commodity = commodity;
		this.commodity_number = commodity_number;
		this.commodity_price = commodity_price;
		this.order_id = order_id;
	}
	
	public CommodityOrder(Integer co_id, Integer commodity_id, Integer commodity_number, double commodity_price,
			Integer order_id) {
		super();
		this.co_id = co_id;
		this.commodity_id = commodity_id;
		this.commodity_number = commodity_number;
		this.commodity_price = commodity_price;
		this.order_id = order_id;
	}
	public CommodityOrder() {
		super();
	}
	@Override
	public String toString() {
		return "CommodityOrder [co_id=" + co_id + ", commodity_id=" + commodity_id + ", commodity=" + commodity
				+ ", commodity_number=" + commodity_number + ", commodity_price=" + commodity_price + ", order_id="
				+ order_id + "]";
	}
	
	
}