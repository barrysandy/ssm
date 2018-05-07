package com.xiaoshu.service;

import com.xiaoshu.entity.CommodityPriceList;

import java.util.List;

/** 标准版 */
public  interface CommodityPriceListService {

	/** save one */
	Integer save(CommodityPriceList bean) throws Exception;


	/** update all */
	Integer updateAll(CommodityPriceList bean) throws Exception;

	/** delete ById */
	Integer deleteById( String id) throws Exception;

	/** 按照 商品 id 查询集合 */
	List<CommodityPriceList> listByCommodityIdInStatus( Integer commodityId) throws Exception;

	/** 按照 商品 id 查询集合 */
	List<CommodityPriceList> listByCommodityId( Integer commodityId) throws Exception;

	/** 按照id查询核销码 */
	CommodityPriceList getById( String id) throws Exception;

	/** 按照 商品 id 对价格进行日期组合查询集合 */
	List<CommodityPriceList> listGroupPTimeByCommodityId(Integer commodityId) throws Exception;

	/** 按照 商品 id 和 PRICE_TIME 查询集合 */
	List<CommodityPriceList> listByCommodityIdAndPTime(Integer commodityId,String priceTime) throws Exception;

	/** 按照 商品 id 和 descM 查询集合 */
	List<CommodityPriceList> listByCommodityIdAndDesC(Integer commodityId,String descM) throws Exception;

	/** 按照 商品 id 和 PRICE_TIME 查询组团的价格 */
	CommodityPriceList getByCommodityIdAndDesC(Integer commodityId,String descM) throws Exception;

	/** update address */
	Integer updateAddressByDescM(String address,String descM,Integer commodityId) throws Exception;

	/** update contacts */
	Integer updateContactsByDescM(String contacts,String descM,Integer commodityId) throws Exception;

	/** update contactsPhone */
	Integer updateContactsPhoneByDescM(String contactsPhone,String descM,Integer commodityId) throws Exception;

}
