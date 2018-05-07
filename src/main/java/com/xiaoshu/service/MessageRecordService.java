package com.xiaoshu.service;

import com.xiaoshu.entity.MessageRecord;

import java.util.Date;
import java.util.List;

/** 标准版 */
public  interface MessageRecordService {

	/** save one */
	Integer save(MessageRecord bean) throws Exception;

	/** update 状态 */
	Integer updateResponseStatusById(String responseStatus, Date updateTime, String id) throws Exception;

	/** update all */
	Integer updateAll(MessageRecord bean) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	/** select ById */
	MessageRecord getById(String id) throws Exception;

	/** 按照签名或者短信内容查询消息记录集合 */
	List<MessageRecord> listByKeyWord(Integer index,Integer pageSize,String keyword,String date1, String date2) throws Exception;

	/** 按照签名或者短信内容统计 */
	Integer countByKeyWord(String keyword,String date1,String date2) throws Exception;

	/** 按照签名或者短信内容统计 */
	Integer countByCode(String code) throws Exception;


	/**
	 * ID 0 :发送单一商品购买短信（未满足开团人数）
	 * @param id 订单id
	 * @param type 短信类型 SINGLE_BUY
	 * @throws Exception
	 */
	void sendSingleBuyMsg(Integer id,String type) throws Exception;


	/**
	 * ID 1 : 发送单一商品购买（满足开团人数，群发开团短信）
	 * @param id 商品id
	 * @param type 短信类型 SINGLE_BUY_GROUP_SUC_MASS
	 * @throws Exception
	 */
	void sendSingleGroupSucBuyMsg(Integer id,String type) throws Exception;

	/**
	 * ID 2 : 单一商品购买（满足开团人数，合并0-1的短信）
	 * @param id 商品id
	 * @param type 短信类型 SINGLE_BUY_GROUP_SUC_TOBUY
	 * @throws Exception
	 */
	void sendSingleGroupSucBuyToBuyMsg(Integer id,String type) throws Exception;


	/**
	 * ID 3 : 单一商品购买（未满足开团人数，时间到达，群发失败短信）
	 * @param id 商品id
	 * @param type 短信类型 SINGLE_BUY_GROUP_FIAL_MASS
	 * @throws Exception
	 */
	void sendSingleGroupFailMassMsg(Integer id,String type) throws Exception;





	/**
	 * ID 4 : 组团商品购买（当前团未满短信）
	 * @param id 订单id
	 * @param type 短信类型 GROUP_BUY
	 * @param groupId 组团ID
	 * @throws Exception
	 */
	void sendGroupBuyMsg(Integer id,String type,String groupId) throws Exception;



	/**
	 * ID 5 : 组团商品购买（当前团满群发组团成功短信）
	 * @param id 商品id
	 * @param type 短信类型 GROUP_BUY_SUC_MASS
	 * @param groupId 组团ID
	 * @throws Exception
	 */
	void sendGroupBuySucMassMsg(Integer id,String type,String groupId) throws Exception;



	/**
	 * ID 6 : 组团商品购买（当前团满短信时，合并4-5短信）
	 * @param id 订单id
	 * @param type 短信类型 GROUP_BUY_SUC_TOBUY
	 * @param groupId 组团ID
	 * @throws Exception
	 */
	void sendGroupBuySucMassToBuyMsg(Integer id,String type,String groupId) throws Exception;


	/**
	 * ID 7 : 组团商品购买（未满足开团人数，时间到达，群发失败短信）
	 * @param id 商品id
	 * @param type 短信类型 GROUP_BUY_FIAL_MASS
	 * @param groupId 组团ID
	 * @throws Exception
	 */
	void sendGroupBuyFailMassMsg(Integer id,String type,String groupId) throws Exception;

	/**
	 * ID 8 :
	 * 发送退款短信
	 * @param id 订单id
	 * @param type 短信类型 REFUND
	 * @throws Exception
	 */
	void sendRefundMsg(Integer id,String type) throws Exception;

	/**
	 * ID 9 :
	 * 发送活动中奖短信
	 * @param id 订单id
	 * @param type 短信类型 WINNING
	 * @throws Exception
	 */
	void sendWiningMsg(Integer id,String type) throws Exception;

	/**
	 * ID 10 :
	 * 发送退款失败短信
	 * @param id 订单id
	 * @param type 短信类型 REFUND_FAIL
	 * @throws Exception
	 */
	void sendRefundFailMsg(Integer id,String type,String sign,String errCodeDes) throws Exception;

}
