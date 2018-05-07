package com.xiaoshu.dao;


import com.xiaoshu.entity.Commodity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface CommodityMapper {
	/**
	 * 保存商品
	 */
	Integer saveCommodityDao(Commodity bean);
	
	/**
	 * 更新商品信息
	 * @param bean
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update commodity set id=#{id},groupNumber=#{groupNumber},groupNumber2=#{groupNumber2},url=#{url},commodityName=#{commodityName}," +
			"commodityDetails=#{commodityDetails},commodityState=#{commodityState},commodityStock=#{commodityStock},commodityPrice=#{commodityPrice}," +
			"sellerId=#{sellerId},arrayImg=#{arrayImg},createDate=#{createDate},invalidDate=#{invalidDate}" +
			",share=#{share},shareTitle=#{shareTitle},shareDescM=#{shareDescM},shareImage=#{shareImage},maxNumber=#{maxNumber},maxNumber2=#{maxNumber2}," +
			"typese=#{typese},wechatActivityId=#{wechatActivityId},timeStatus=#{timeStatus},maxGroup=#{maxGroup},createGroupNumber=#{createGroupNumber}," +
			"videoMaterialId=#{videoMaterialId},image=#{image},showList=#{showList}  Where id=#{id} ")
	Integer updateCommodityDao(Commodity bean);

	/**
	 * 更新商品视频信息
	 * @param bean
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update commodity set videoMaterialId=#{videoMaterialId} Where id=#{id} ")
	Integer updateVideoDao(Commodity bean);
	
	/**
	 * 更新/修改商品库存
	 * @param id 商品id
	 * @param commodityStock 商品库存
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update commodity set commodityStock=#{commodityStock} Where id=#{id} AND commodityStock=#{oldCommodityStock} ")
	Integer updateCommodityStock(@Param("id") Integer id,@Param("commodityStock") Integer commodityStock,@Param("oldCommodityStock") Integer oldCommodityStock);
	
	
	/**
	 * 更新商品上下架状态
	 * @param id 商品id
	 * @param commodityState 商品状态
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update commodity set commodityState=#{commodityState} Where id=#{id} ")
	Integer updateCommodityStateDao(@Param("id") Integer id,@Param("commodityState") Integer commodityState);


	/**
	 * 更新商品对应的活动id
	 * @param id 商品id
	 * @param wechatActivityId 活动id
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update commodity set wechatActivityId=#{wechatActivityId} Where id=#{id} ")
	Integer updateActivityIdById(@Param("id") Integer id,@Param("wechatActivityId") String wechatActivityId);

	/**
	 * 更新商品的 totalGroup 人数
	 * @param totalGroup
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update commodity set totalGroup=#{totalGroup} Where id=#{id} AND totalGroup=#{oldTotalGroup} ")
	Integer updateTotalGroupById(@Param("id") Integer id,@Param("totalGroup") Integer totalGroup,@Param("oldTotalGroup") Integer oldTotalGroup);


	/**
	 * 更新商品的 timeStatus 人数
	 * @param timeStatus
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update commodity set timeStatus=#{timeStatus} Where id=#{id} ")
	Integer updateTimeStatusById(@Param("id") Integer id,@Param("timeStatus") Integer timeStatus);
	
	/**
	 * 删除商品
	 * @param id
	 */
	@Delete("delete from commodity where id=#{id}")
	Integer deleteCommodityDao(@Param("id") Integer id);
	
	/**
	 * 按照commodityName查询商品
	 * @param commodityName 
	 * @return
	 */
	@Select("select * from commodity where commodityName = #{commodityName}")
	Commodity findCommodityByNameDao(@Param("commodityName")String commodityName);
	
	
	/**
	 * 按照id查询商品
	 * @param id 
	 * @return
	 */
	//@Select("select * from commodity where id = #{id }")
	Commodity findCommodityByIdDao(@Param("id") Integer id );

	/**
	 * 按照id查询商品时间数据
	 * @param id
	 * @return
	 */
	@Select("select id,createDate,invalidDate from commodity where id = #{id }")
	Commodity getDateBeanById(@Param("id") Integer id );

	//@Select("select * from commodity where id = #{id }")
	Commodity findCommodityByIdPriceStatusOne(@Param("id") Integer id );

	
	/**
	 * 查询上架商品（第一个）
	 * @param commodityState
	 * @return
	 */
	@Select("select * from commodity where commodityState = #{commodityState} limit 0,1 ")
	Commodity findFristSellGoodsDao(@Param("commodityState")Integer commodityState);
	
	
	/**
	 * 按照id查询商品上下架状态
	 * @param id 
	 * @return 返回商品状态
	 */
	@Select("select commodityState from commodity where id = #{id }")
	Integer findCommodityStateByIdDao(@Param("id") Integer id );
	
	/**
	 * 按照id查询商品库存
	 * @param id 
	 * @return 返回商品库存
	 */
	@Select("select commodityStock from commodity where id = #{id }")
	Integer findcommodityStockByIdDao(@Param("id") Integer id );
	
	/**
	 * 按照commodityName、commodityDetails、commodityStock模糊分页查询商品列表
	 * @param index
	 * @param pageSize
	 * @param key
	 * @return 返回商品集合
	 * @throws Exception 抛出异常
	 */
	List<Commodity> findAllCommodityByKeyDao(@Param("index") Integer index, @Param("pageSize") Integer pageSize,
													@Param("key") String key, @Param("seller_id") Integer seller_id);
	
	/**
	 * 按照key统计商品个数
	 * @return
	 * @throws Exception
	 */
	Integer totalCommodityByKeyDao(@Param("key") String key,@Param("seller_id") Integer seller_id);
	

	/**
	 * 统计总记录数
	 */
	@Select("select count(*) from commodity")
	Integer totalCommodityDao();
	
	/**
	 * 查询商品库存
	 */
	@Select("select commodityStock from commodity where id = #{id }")
	Integer getCommodityStockByIDDao(@Param("id") Integer id);

	// SIGN 报名类型商品（维护活动类com.xiaoshu.entity.WechatActivity）  SINGLE 单一购买商品  GROUP 组团商品 。(url)
	/**
	 * 最新活动列表
	 * timeStatus  1 表示正在  0 表示过期
  	 */
	List<Commodity> getNewList(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("typese")Integer typese,@Param("timeStatus")Integer timeStatus, @Param("showList")Integer showList);

	/**
	 * 最新活动列表少量数据
	 * timeStatus  1 表示正在  0 表示过期
	 */
	List<Commodity> getNewListAFewData(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("typese")String typese,@Param("timeStatus")Integer timeStatus);


	/**
	 * 统计最新活动列表
	 */
//	@Select("select count(*) from commodity where typese = #{typese} AND timeStatus = #{timeStatus} ")
	Integer countNewList( @Param("typese") Integer typese, @Param("timeStatus")Integer timeStatus, @Param("showList")Integer showList);

	/**
	 * 按照 id 查询 wechatActivityId
	 * @param id
	 * @return
	 */
	@Select("select wechatActivityId from commodity where id = #{id}")
	String getWechatActivityIdById(@Param("id") Integer id);


	/** get MaxGroup */
	@Select("select maxGroup from commodity where id = #{id}")
	Integer getMaxGroupById(@Param("id") Integer id);

	/** get createGroupNumber */
	@Select("select createGroupNumber from commodity where id = #{id}")
	Integer getCreateGroupNumberById(@Param("id") Integer id);



	@Select("select shareTitle,shareDescM,shareImage from commodity where id = #{id }")
	Commodity getLestById(@Param("id") Integer id );

}
