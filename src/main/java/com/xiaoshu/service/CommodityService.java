package com.xiaoshu.service;

import com.xiaoshu.entity.Commodity;

import java.util.List;

public interface CommodityService {
	/**
	 * 保存商品
	 */
	Integer saveCommodityService(Commodity Commodity) throws Exception;
	
	/**
	 * 更新商品信息
	 * @param Commodity
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	Integer updateCommodityService(Commodity Commodity) throws Exception;

	/**
	 * 更新商品视频信息
	 * @param Commodity
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	Integer updateVideoService(Commodity Commodity) throws Exception;
	
	/**
	 * 更新商品库存
	 * @param id 商品id
	 * @param commodityStock 商品库存
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	Integer updateCommodityStockService(Integer id,Integer commodityStock,Integer oldCommodityStock) throws Exception;

	/**
	 * 更新商品对应的活动id
	 * @param id 商品id
	 * @param wechatActivityId 活动id
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	Integer updateActivityIdById(Integer id,String wechatActivityId) throws Exception;
	
	
	/**
	 * 更新商品上下架状态
	 * @param id 商品id
	 * @param commodityState 商品状态
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	Integer updateCommodityStateService(Integer id,Integer commodityState) throws Exception;

	/**
	 * 更新商品的 totalGroup 人数
	 * @param totalGroup
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	Integer updateTotalGroupById(Integer id,Integer totalGroup,Integer oldTotalGroup) throws Exception;


	/**
	 * 更新商品的 timeStatus 人数
	 * @param timeStatus
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	Integer updateTimeStatusById(Integer id,Integer timeStatus) throws Exception;
	
	/**
	 * 删除商品
	 * @param id
	 */
	Integer deleteCommodityService(Integer id) throws Exception;
	
	/**
	 * 按照commodityName查询商品
	 * @param commodityName
	 * @return
	 */
	Commodity findCommodityByNameService(String commodityName) throws Exception;
	/**
	 * 按照id查询商品
	 * @param id 
	 * @return
	 */
	Commodity findCommodityByIdService(Integer id ) throws Exception;

	Commodity getDateBeanById(Integer id );

	Commodity findCommodityByIdPriceStatusOne(Integer id ) throws Exception;
	/**
	 * 查询上架商品（第一个）
	 * @param commodityState
	 * @return
	 */
	Commodity findFristSellGoodsDao(Integer commodityState) throws Exception;
	
	
	/**
	 * 按照id查询商品上下架状态
	 * @param id 
	 * @return 返回商品状态
	 */
	Integer findCommodityStateByIdService(Integer id ) throws Exception;
	
	/**
	 * 按照id查询商品库存
	 * @param id 
	 * @return 返回商品库存
	 */
	Integer findcommodityStockByIdService(Integer id ) throws Exception;
	
	
	/**
	 * 按照commodityName、commodityDetails、commodityStock模糊分页查询商品列表
	 * @param index
	 * @param pageSize
	 * @param key
	 * @return 返回商品集合
	 * @throws Exception 抛出异常
	 */
	List<Commodity> findAllCommodityByKeyService(Integer index, Integer pageSize, String key, Integer seller_id) throws Exception;
	
	/**
	 * 按照key统计商品个数
	 * @return
	 * @throws Exception
	 */
	Integer totalCommodityByKeyService(String key,Integer seller_id) throws Exception;
	

	/**
	 * 统计总记录数
	 */
	Integer totalCommodityService() throws Exception;
	
	/**
	 * 查询商品库存
	 */
	Integer getCommodityStockByIDService(Integer id) throws Exception;


	List<Commodity> getNewList(Integer index,Integer pageSize,Integer typese,Integer timeStatus,Integer showList) throws Exception;


	/**
	 * 最新活动列表少量数据
	 * timeStatus  1 表示正在  0 表示过期
	 */
	List<Commodity> getNewListAFewData(Integer index,Integer pageSize, String typese, Integer timeStatus) throws Exception;


	/**
	 * 统计最新活动列表
	 */
	Integer countNewList( Integer typese, Integer timeStatus,Integer showList) throws Exception;

	/**
	 * 按照 id 查询 wechatActivityId
	 * @param id
	 * @return
	 */
	String getWechatActivityIdById(Integer id) throws Exception;


	/** get MaxGroup */
	Integer getMaxGroupById(Integer id) throws Exception;

	/** get createGroupNumber */
	Integer getCreateGroupNumberById(Integer id) throws Exception;

	Commodity getLestById(Integer id ) throws Exception;

}
