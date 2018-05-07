package com.xiaoshu.service;

import com.xiaoshu.entity.Order;

import java.util.List;

public interface OrderService {

	/** save one */
	int save(Order bean) throws Exception;

	/** delete ById */
	Integer deleteById( Integer id) throws Exception;

	/** 更新订单信息 */
	Integer updateAll(Order bean) throws Exception;

	/** 更新订单状态 */
	Integer updateOldTypeStateToOrderStateByOrderNo( String orderNo, Integer typeState, Integer oldTypeState,String descM) throws Exception;

	/** 更新订单状态额外状态 */
	Integer updateOldStateToOrderStateByOrderNo( String orderNo, Integer state, Integer oldState) throws Exception;

	/** 更新订单 groupId */
	Integer updateGroupIdByOrderNo( String orderNo, String groupId ) throws Exception;

	/** 按照 id 查询订单 */
	Order getById( Integer id) throws Exception;

	/** 按照 orderNo 查询订单 */
	Order getByOrderNo( String orderNo ) throws Exception;

	/** 按照 GROUP_ID  USER_ID  查询订单 */
	Order getByGroupIdAndUserId(String groupId , String userId) throws Exception;

	/** 按照 GROUP_ID  查询订单集合 */
	List<Order> getListByGroupId(String groupId ) throws Exception;

	/** 按照 id 查询 orderNo */
	Integer getIdByOrderNo( String orderNo) throws Exception;

	/** 按照 orderNo 查询 id  */
	String getOrderNoById( Integer id) throws Exception;

	/** 按照 orderNo 查询订单状态（typeState 订单状态 0未付款 1已付款 2已消费  -1查询所有状态） */
	Integer getTypeStateByOrderNo( String orderNo ) throws Exception;

	/** 查询 orderNo 是否存在 */
	Integer countByOrderNo( String orderNo ) throws Exception;


	/** 查询今天的订单数量 */
	Integer countToday( String today ) throws Exception;

	/** 按商品统计已经购买的订单数量 */
	Integer countByCommdityId(Integer commodityId ) throws Exception;

	/** 查询最后一个订单编号 */
	String getLastOrderNo() throws Exception;

	/**
	 * 查询订单列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询订单列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	List<Order> listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(String date1 ,String date2, Integer index, Integer pageSize, String key, Integer typeState, Integer status, Integer userId, Integer sellerId,Integer commodityId) throws Exception;


	/**
	 * 统计订单
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询订单列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	Integer countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(String date1, String date2, String key,Integer typeState, Integer status, Integer userId, Integer sellerId,Integer commodityId) throws Exception;

	/**
	 * 查询订单列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param userId 用户的id，查询某个用户的订单列表
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	List<Order> listByUserId(Integer index,Integer pageSize,String userId,String userId2,Integer typeState) throws Exception;

	/**
	 * 统计订单数量
	 * @param userId 用户的id，查询某个用户的订单列表
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	Integer countByUserId(String userId,String userId2,Integer typeState) throws Exception;

	/** 绑定订单所属信息 */
	Integer updateBelongBy(String orderNo, String userId) throws Exception;

	/** 绑定微信订单号信息 */
	Integer updateTransactionIdByOrderOn(String orderNo,String transactionId) throws Exception;

	/** 更新订单的支付类型 */
	Integer updatePayTypeByOrderOn(String orderNo,String payType) throws Exception;

	/** 更新订单电话，联系人，和用户时间信息 */
	Integer updateContactsMsg(String orderNo, String userName, String userPhone, String userUseTime) throws Exception;


	/** 按照订单描述和其状态为支付完成的订单列表 */
	List<Order> listBydescM(Integer index, Integer pageSize, String descM) throws Exception;

	/**
	 * 统计订单数量
	 * @param descM   订单描述
	 * @return   返回订单数量
	 * @throws Exception 抛出异常
	 */
	/** 统计 按照订单描述和其状态为支付完成的订单列表 */
	Integer countBydescM(String descM) throws Exception;


	/** 按照useCode查询所有核销码数量 */
	Integer countByUseCode(String useCode) throws Exception;

	/** 查询所有已经付款的订单，用于群发短信 */
	List<Order> listByCommodityIdAndHasPay(Integer commodityId , String groupId) throws Exception;


	/** 统计商品订单的成人数量 */
	Integer countByNUMBER(Integer commodityId) throws Exception;

	/** 统计商品订单的儿童数量 */
	Integer countByNUMBER2(Integer commodityId) throws Exception;

	/** 生成订单编号*/
	String getOrderNumber() throws Exception;

	/** 生成订单核销码编号*/
	String getOrderCode() throws Exception;



}
