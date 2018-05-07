package com.xiaoshu.dao;


import com.xiaoshu.entity.CommodityPriceList;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface CommodityPriceListMapper {


	/** save one */
	@Insert("INSERT INTO commodity_price_list (ID, COMMODITY_ID, PRICE_NAME, PRICE, PRICE_UNIT, STATUS, PRICE_TIME,CREATE_TIME, DESC_M,SORT, ADDRESS, REMARK,CONTACTS,CONTACTS_PHONE) " +
			"VALUES(#{id},#{commodityId},#{priceName},#{price},#{priceUnit},#{status},#{priceTime},#{createTime},#{descM},#{sort},#{address},#{remark},#{contacts},#{contactsPhone} )")
	Integer save(CommodityPriceList bean);


	/** update all */
	@Update("UPDATE commodity_price_list SET COMMODITY_ID=#{commodityId},PRICE_NAME=#{priceName},PRICE=#{price},PRICE_UNIT=#{priceUnit},STATUS=#{status}," +
			"CREATE_TIME=#{createTime},PRICE_TIME=#{priceTime},DESC_M=#{descM},SORT=#{sort},ADDRESS=#{address},REMARK=#{remark},CONTACTS=#{contacts},CONTACTS_PHONE=#{contactsPhone}  WHERE ID=#{id} ")
	Integer updateAll(CommodityPriceList bean);

	/** delete ById */
	@Delete("DELETE FROM commodity_price_list WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id);

	/** 按照 商品 id 查询集合 */
	@Select("SELECT * FROM commodity_price_list WHERE COMMODITY_ID = #{commodityId} AND STATUS = 1 ORDER BY SORT")
	List<CommodityPriceList> listByCommodityIdInStatus(@Param("commodityId") Integer commodityId);

	/** 按照 商品 id 查询集合 */
	@Select("SELECT * FROM commodity_price_list WHERE COMMODITY_ID = #{commodityId} ORDER BY SORT")
	List<CommodityPriceList> listByCommodityId(@Param("commodityId") Integer commodityId);

	/** 按照id查询核销码 */
	@Select("SELECT * FROM commodity_price_list WHERE ID = #{id} ")
	CommodityPriceList getById(@Param("id") String id);


	/** 按照 商品 id 对价格进行日期组合查询集合 */
	@Select("SELECT * FROM commodity_price_list WHERE COMMODITY_ID = #{commodityId} GROUP BY PRICE_TIME")
	List<CommodityPriceList> listGroupPTimeByCommodityId(@Param("commodityId") Integer commodityId);


	/** 按照 商品 id 和 PRICE_TIME 查询集合 */
	@Select("SELECT * FROM commodity_price_list WHERE COMMODITY_ID = #{commodityId} AND STATUS = 1 AND PRICE_TIME = #{priceTime} ORDER BY SORT")
	List<CommodityPriceList> listByCommodityIdAndPTime(@Param("commodityId") Integer commodityId,@Param("priceTime") String priceTime);

	/** 按照 商品 id 和 PRICE_TIME 查询集合 */
	@Select("SELECT * FROM commodity_price_list WHERE COMMODITY_ID = #{commodityId} AND STATUS = 1 AND DESC_M = #{descM} ORDER BY SORT")
	List<CommodityPriceList> listByCommodityIdAndDesC(@Param("commodityId") Integer commodityId,@Param("descM") String descM);

	/** 按照 商品 id 和 PRICE_TIME 查询组团的价格 */
	@Select("SELECT * FROM commodity_price_list WHERE COMMODITY_ID = #{commodityId} AND STATUS = 1 AND DESC_M = #{descM} ORDER BY SORT limit 0,1")
	CommodityPriceList getByCommodityIdAndDesC(@Param("commodityId") Integer commodityId,@Param("descM") String descM);

	/** update address */
	@Update("UPDATE commodity_price_list SET ADDRESS=#{address}  WHERE DESC_M=#{descM} AND COMMODITY_ID=#{commodityId}")
	Integer updateAddressByDescM(@Param("address") String address,@Param("descM") String descM,@Param("commodityId") Integer commodityId);


	/** update contacts */
	@Update("UPDATE commodity_price_list SET CONTACTS=#{contacts}  WHERE DESC_M=#{descM} AND COMMODITY_ID=#{commodityId}")
	Integer updateContactsByDescM(@Param("contacts") String contacts,@Param("descM") String descM,@Param("commodityId") Integer commodityId);

	/** update contactsPhone */
	@Update("UPDATE commodity_price_list SET CONTACTS_PHONE=#{contactsPhone}  WHERE DESC_M=#{descM} AND COMMODITY_ID=#{commodityId}")
	Integer updateContactsPhoneByDescM(@Param("contactsPhone") String contactsPhone,@Param("descM") String descM,@Param("commodityId") Integer commodityId);


}
