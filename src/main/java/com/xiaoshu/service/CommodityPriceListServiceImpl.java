package com.xiaoshu.service;


import com.xiaoshu.dao.CommodityPriceListMapper;
import com.xiaoshu.dao.OrderCodesMapper;
import com.xiaoshu.entity.CommodityPriceList;
import com.xiaoshu.entity.OrderCodes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 标准版 */
@Service("commodityPriceListService")
public class CommodityPriceListServiceImpl implements CommodityPriceListService{

	@Resource private CommodityPriceListMapper mapper;


	/** save one */
	@Override
	public Integer save(CommodityPriceList bean) throws Exception {
		return mapper.save(bean);
	}


	/** update all */
	@Override
	public Integer updateAll(CommodityPriceList bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById( String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** 按照 商品 id 查询集合 */
	@Override
	public List<CommodityPriceList> listByCommodityIdInStatus( Integer commodityId) throws Exception {
		return mapper.listByCommodityIdInStatus(commodityId);
	}

	/** 按照 商品 id 查询集合 */
	@Override
	public List<CommodityPriceList> listByCommodityId( Integer commodityId) throws Exception {
		return mapper.listByCommodityId(commodityId);
	}

	/** 按照id查询核销码 */
	@Override
	public CommodityPriceList getById( String id) throws Exception {
		return mapper.getById(id);
	}

	/** 按照 商品 id 对价格进行日期组合查询集合 */
	@Override
	public List<CommodityPriceList> listGroupPTimeByCommodityId(Integer commodityId) throws Exception {
		return mapper.listGroupPTimeByCommodityId(commodityId);
	}

	/** 按照 商品 id 和 PRICE_TIME 查询集合 */
	@Override
	public List<CommodityPriceList> listByCommodityIdAndPTime(Integer commodityId,String priceTime) throws Exception {
		return mapper.listByCommodityIdAndPTime(commodityId,priceTime);
	}

	/** 按照 商品 id 和 descM 查询集合 */
	@Override
	public List<CommodityPriceList> listByCommodityIdAndDesC(Integer commodityId,String descM) throws Exception {
		return mapper.listByCommodityIdAndDesC(commodityId,descM);
	}

	/** 按照 商品 id 和 PRICE_TIME 查询组团的价格 */
	@Override
	public CommodityPriceList getByCommodityIdAndDesC(Integer commodityId,String descM) throws Exception {
		return mapper.getByCommodityIdAndDesC(commodityId,descM);
	}

	/** update address */
	@Override
	public Integer updateAddressByDescM(String address,String descM,Integer commodityId) throws Exception {
		return mapper.updateAddressByDescM(address,descM,commodityId);
	}


	/** update contacts */
	@Override
	public Integer updateContactsByDescM(String contacts,String descM,Integer commodityId) throws Exception {
		return mapper.updateContactsByDescM(contacts,descM,commodityId);
	}

	/** update contactsPhone */
	@Override
	public Integer updateContactsPhoneByDescM(String contactsPhone,String descM,Integer commodityId) throws Exception {
		return mapper.updateContactsPhoneByDescM(contactsPhone,descM,commodityId);
	}


}
