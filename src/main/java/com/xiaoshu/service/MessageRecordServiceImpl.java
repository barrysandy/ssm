package com.xiaoshu.service;


import com.xiaoshu.api.Api;
import com.xiaoshu.dao.*;
import com.xiaoshu.entity.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.sendMsg.IndustrySMS;
import com.xiaoshu.tools.sendMsg.MsgTemplate;
import com.xiaoshu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/** 标准版 */
@Service("messageRecordService")
public class MessageRecordServiceImpl implements MessageRecordService{
	private static Logger log = LoggerFactory.getLogger(OrderRefundService.class);

	private static final String CONSULTATION_PHONE = "18602822925";

	@Resource private MessageRecordMapper mapper;
	@Resource private OrderMapper orderMapper;
	@Resource private MessageTempleMapper messageTempleMapper;
	@Resource private MessageRecordMapper messageRecordMapper;
	@Resource private CommodityMapper commodityMapper;
	@Resource private CommodityPriceListMapper commodityPriceListMapper;



	@Override
	public Integer save(MessageRecord bean) throws Exception {
		return mapper.save(bean);
	}


	@Override
	public Integer updateResponseStatusById(String responseStatus, Date updateTime, String id) throws Exception {
		return mapper.updateResponseStatusById(responseStatus,updateTime,id);
	}


	@Override
	public Integer updateAll(MessageRecord bean) throws Exception {
		return mapper.updateAll(bean);
	}

	@Override
	public Integer deleteById(String id) throws Exception {
		return mapper.deleteById(id);
	}


	public MessageRecord getById(String id) throws Exception {
		return mapper.getById(id);
	}


	@Override
	public List<MessageRecord> listByKeyWord(Integer index,Integer pageSize,String keyword,String date1, String date2) throws Exception {
		return mapper.listByKeyWord(index, pageSize, keyword, date1, date2);
	}


	@Override
	public Integer countByKeyWord(String keyword,String date1,String date2) throws Exception {
		return mapper.countByKeyWord(keyword, date1, date2);
	}



	@Override
	public 	Integer countByCode(String code) throws Exception {
		return mapper.countByCode(code);
	}





	@Override
	public void sendSingleBuyMsg(Integer id,String type) throws Exception {
		//TODO 【0 单一商品】购买短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			if(id != 0) {
				Order order = orderMapper.getById(id);
				if(order != null) {
					List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(order.getCommodityId());
					MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
					String code = order.getOrderNo() + order.getUserUseTime() + type;
					int exit = messageRecordMapper.countByCode(code);
					if(exit <= 0) {
						log.info("------------ [LOG["+ nowTime +"]sendSingleBuyMsg] messageTemple : " + messageTemple + " ------------");
						String content = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

						//TODO 短信参数组装
						String sign = messageTemple.getSign();
						String title = order.getOrderName();
						String orderNo = order.getOrderNo();
						String userName = order.getUserName();
						String userPhone = order.getUserPhone();
						String number1 = String.valueOf(order.getNumber());
						String number2 = String.valueOf(order.getNumber2());
						String time = "";
						String contantAddress = "";
						CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
						if(commodityPriceList != null){
							time = commodityPriceList.getPriceTime();
							contantAddress = commodityPriceList.getAddress();
						}

						String[] param = new String[]{title,orderNo,userName,userPhone,number1,number2,time,contantAddress,CONSULTATION_PHONE,sign};
						HashMap<String, Object> map = IndustrySMS.link(userPhone, content, "",param);
						String status = (String) map.get("status");
						String msg =  (String) map.get("msg");
						String msgId = UUID.randomUUID().toString();

						MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, content,order.getUserId(), status, new Date(), new Date(), msg ,code, 1);
						messageRecordMapper.save(messageRecord);
					}else {
						log.info("------------ [LOG["+ nowTime +"]sendSingleBuyMsg] HasSend Code: " + code + " ------------");
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public void sendSingleGroupSucBuyMsg(Integer id,String type) throws Exception {
		//TODO 【1 单一商品】群发组团成功短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			Commodity commodity = commodityMapper.findCommodityByIdDao(id);
			if(commodity != null){
				List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(id);
				MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
				if(messageTemple != null){
					int max = 0;
					Integer hasSend = 0;
					List<Order> list = orderMapper.listByCommodityIdAndHasPay(id,"");
					if(list != null) {
						max = list.size();
						synchronized (hasSend){
							Iterator<Order> iterator = list.iterator();
							while (iterator.hasNext()){
								Order orderTemp = iterator.next();
								if(orderTemp != null) {
									Order order = orderMapper.getById(orderTemp.getId());
									String code = order.getOrderNo() + order.getUserUseTime() + type;
									String code2 = order.getOrderNo() + order.getUserUseTime() + "SINGLE_BUY_GROUP_SUC_TOBUY";
									int exit = messageRecordMapper.countByCode(code);
									int exit2 = messageRecordMapper.countByCode(code2);
									if(exit + exit2 <= 0) {
										String msgTemple = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

										//TODO 短信参数组装
										String sign = messageTemple.getSign();
										String title = order.getOrderName();
										String orderNo = order.getOrderNo();
										String userName = order.getUserName();
										String userPhone = order.getUserPhone();
										String number1 = String.valueOf(order.getNumber());
										String number2 = String.valueOf(order.getNumber2());
										String time = "";
										String contantAddress = "";
										String contantName = "";
										String contantPhone = "";
										CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
										if(commodityPriceList != null){
											time = commodityPriceList.getPriceTime();
											contantAddress = commodityPriceList.getAddress();
											contantName = commodityPriceList.getContacts();
											contantPhone = commodityPriceList.getContactsPhone();
										}

										String[] param = new String[]{userName,title,time,contantAddress,contantName,contantPhone,sign};
										HashMap<String, Object> map = IndustrySMS.link(userPhone, msgTemple, "",param);
										String status = (String) map.get("status");
										log.info("------------ [LOG["+nowTime+"] MessageTempleController interfaceSendAllMeg] sendAllMeg  status: " + status + " ------------");
										String msg =  (String) map.get("msg");
										String msgId = UUID.randomUUID().toString();

										MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, msgTemple,order.getUserId(), status, new Date(), new Date(), msg,code, 1);
										messageRecordMapper.save(messageRecord);

									}else {
										log.info("------------ [LOG["+ nowTime +"] MessageRecordController interfaceSendMsg] HasSend Code: " + code + " ------------");
									}
								}
							}
						}

					}

				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public void sendSingleGroupSucBuyToBuyMsg(Integer id,String type) throws Exception {
		//TODO 【2 单一商品】购买组合短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			if(id != 0) {
				Order order = orderMapper.getById(id);
				if(order != null) {
					List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(order.getCommodityId());
					MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
					String code = order.getOrderNo() + order.getUserUseTime() + type;
					int exit = messageRecordMapper.countByCode(code);
					if(exit <= 0) {
						String content = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

						//TODO 短信参数组装
						String sign = messageTemple.getSign();
						String title = order.getOrderName();
//						String orderNo = order.getOrderNo();
						String userName = order.getUserName();
						String userPhone = order.getUserPhone();
//						String number1 = String.valueOf(order.getNumber());
//						String number2 = String.valueOf(order.getNumber2());
//						String time = "";
//						String contantAddress = "";
//						String contantName = "";
//						String contantPhone = "";
//						CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
//						if(commodityPriceList != null){
//							time = commodityPriceList.getPriceTime();
//							contantAddress = commodityPriceList.getAddress();
//							contantName = commodityPriceList.getContacts();
//							contantPhone = commodityPriceList.getContactsPhone();
//						}

						String[] param = new String[]{userName,title,CONSULTATION_PHONE,sign};
						HashMap<String, Object> map = IndustrySMS.link(userPhone, content, "",param);
						String status = (String) map.get("status");
						String msg =  (String) map.get("msg");
						String msgId = UUID.randomUUID().toString();

						MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, content,order.getUserId(), status, new Date(), new Date(), msg ,code, 1);
						messageRecordMapper.save(messageRecord);
					}else {
						log.info("------------ [LOG["+ nowTime +"]sendSingleGroupSucBuyToBuyMsg] HasSend Code: " + code + " ------------");
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void sendSingleGroupFailMassMsg(Integer id,String type) throws Exception{
		//TODO 【3 单一商品】群发失败退款短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			Commodity commodity = commodityMapper.findCommodityByIdDao(id);
			if(commodity != null){
				List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(id);
				MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("---------------------- messageTemple: " + messageTemple);
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				if(messageTemple != null){
					Integer max = 0;
					Integer hasSend = 0;
					List<Order> list = orderMapper.listByCommodityIdAndHasPay(id,"");
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("---------------------- Order: " + list);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					if(list != null) {
						max = list.size();
						if(max > 0){
							synchronized (hasSend){
								Iterator<Order> iterator = list.iterator();
								while (iterator.hasNext()){
									Order orderTemp = iterator.next();
									if(orderTemp != null) {
										Order order = orderMapper.getById(orderTemp.getId());
										if(order != null){
											String code = order.getOrderNo() + order.getUserUseTime() + type;
											int exit = messageRecordMapper.countByCode(code);
											if(exit <= 0) {
												String msgTemple = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());
												//TODO 短信参数组装
												String sign = messageTemple.getSign();
												String title = order.getOrderName();
//												String orderNo = order.getOrderNo();
												String userName = order.getUserName();
												String userPhone = order.getUserPhone();
//												String number1 = String.valueOf(order.getNumber());
//												String number2 = String.valueOf(order.getNumber2());
//												String time = "";
//												String contantAddress = "";
//												String contantName = "";
//												String contantPhone = "";
//												CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
//												if(commodityPriceList != null){
//													time = commodityPriceList.getPriceTime();
//													contantAddress = commodityPriceList.getAddress();
//													contantName = commodityPriceList.getContacts();
//													contantPhone = commodityPriceList.getContactsPhone();
//												}


												String[] param = new String[]{userName,title,CONSULTATION_PHONE,sign};
												HashMap<String, Object> map = IndustrySMS.link(userPhone, msgTemple, "",param);
												String status = (String) map.get("status");
												log.info("------------ [LOG["+nowTime+"] MessageTempleController interfaceSendAllMeg] sendAllMeg  status: " + status + " ------------");
												String msg =  (String) map.get("msg");
												String msgId = UUID.randomUUID().toString();

												MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, msgTemple,order.getUserId(), status, new Date(), new Date(), msg,code, 1);
												int i = messageRecordMapper.save(messageRecord);
												if(i > 0){
													//TODO 退款业务
													ToolsHttpRequest.sendGet(Api.SEND_REFUND_AUTO,"id=" + order.getId());
												}

											}else {
												log.info("------------ [LOG["+ nowTime +"] MessageRecordController interfaceSendMsg] HasSend Code: " + code + " ------------");
											}
										}

									}
								}
							}
						}
					}

				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void sendGroupBuyMsg(Integer id,String type,String groupId) throws Exception {
		//TODO 【4 组团商品】购买短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			if(id != 0) {
				Order order = orderMapper.getById(id);
				if(order != null) {
					List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(order.getCommodityId());
					MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
					String code = order.getOrderNo() + order.getUserUseTime() + type;
					int exit = messageRecordMapper.countByCode(code);
					if(exit <= 0) {
						String content = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

						//TODO 短信参数组装
						String sign = messageTemple.getSign();
						String title = order.getOrderName();
						String orderNo = order.getOrderNo();
						String userName = order.getUserName();
						String userPhone = order.getUserPhone();
						String time = "";
						String contantAddress = "";
						CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
						if(commodityPriceList != null){
							time = commodityPriceList.getPriceTime();
							contantAddress = commodityPriceList.getAddress();
						}

						String[] param = new String[]{title,orderNo,userName,userPhone,time,contantAddress,CONSULTATION_PHONE,sign};
						HashMap<String, Object> map = IndustrySMS.link(userPhone, content, "",param);
						String status = (String) map.get("status");
						String msg =  (String) map.get("msg");
						String msgId = UUID.randomUUID().toString();

						MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, content,order.getUserId(), status, new Date(), new Date(), msg ,code, 1);
						messageRecordMapper.save(messageRecord);
					}else {
						log.info("------------ [LOG["+ nowTime +"]sendGroupBuyMsg] HasSend Code: " + code + " ------------");
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void sendGroupBuySucMassMsg(Integer id,String type,String groupId) throws Exception {
		//TODO 【5 组团商品】组团群发短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			Commodity commodity = commodityMapper.findCommodityByIdDao(id);
			if(commodity != null){
				List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(id);
				MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
				if(messageTemple != null){
					int max = 0;
					Integer hasSend = 0;
					List<Order> list = orderMapper.listByCommodityIdAndHasPay(id,groupId);
					if(list != null) {
						max = list.size();
						synchronized (hasSend){
							Iterator<Order> iterator = list.iterator();
							while (iterator.hasNext()){
								Order orderTemp = iterator.next();
								if(orderTemp != null) {
									Order order = orderMapper.getById(orderTemp.getId());
									String code = order.getOrderNo() + order.getUserUseTime() + type;
									String code2 = order.getOrderNo() + order.getUserUseTime() + "GROUP_BUY_SUC_TOBUY";
									int exit = messageRecordMapper.countByCode(code);
									int exit2 = messageRecordMapper.countByCode(code2);
									if(exit + exit2 <= 0) {
										String msgTemple = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

										//TODO 短信参数组装
										String sign = messageTemple.getSign();
										String title = order.getOrderName();
//										String orderNo = order.getOrderNo();
										String userName = order.getUserName();
										String userPhone = order.getUserPhone();
										String time = "";
										String contantAddress = "";
										String contantName = "";
										String contantPhone = "";
										CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
										if(commodityPriceList != null){
											time = commodityPriceList.getPriceTime();
											contantAddress = commodityPriceList.getAddress();
											contantName = commodityPriceList.getContacts();
											contantPhone = commodityPriceList.getContactsPhone();
										}
										String[] param = new String[]{userName,title,time,contantAddress,contantName,contantPhone,sign};
										HashMap<String, Object> map = IndustrySMS.link(userPhone, msgTemple, "",param);
										String status = (String) map.get("status");
										log.info("------------ [LOG["+nowTime+"] sendGroupBuySucMassMsg status: " + status + " ------------");
										String msg =  (String) map.get("msg");
										String msgId = UUID.randomUUID().toString();

										MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, msgTemple,order.getUserId(), status, new Date(), new Date(), msg,code, 1);
										messageRecordMapper.save(messageRecord);

									}else {
										log.info("------------ [LOG["+ nowTime +"] sendGroupBuySucMassMsg HasSend Code: " + code + " ------------");
									}
								}
							}
						}

					}

				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public void sendGroupBuySucMassToBuyMsg(Integer id,String type,String groupId) throws Exception {
		//TODO 【6 组团商品】购买组合短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			if(id != 0) {
				Order order = orderMapper.getById(id);
				if(order != null) {
					List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(order.getCommodityId());
					MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
					String code = order.getOrderNo() + order.getUserUseTime() + type;
					int exit = messageRecordMapper.countByCode(code);
					if(exit <= 0) {
						String content = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

						//TODO 短信参数组装
						String sign = messageTemple.getSign();
						String title = order.getOrderName();
						String orderNo = order.getOrderNo();
						String userName = order.getUserName();
						String userPhone = order.getUserPhone();
						String time = "";
						String contantAddress = "";
						String contantName = "";
						String contantPhone = "";
						CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
						if(commodityPriceList != null){
							time = commodityPriceList.getPriceTime();
							contantAddress = commodityPriceList.getAddress();
							contantName = commodityPriceList.getContacts();
							contantPhone = commodityPriceList.getContactsPhone();
						}

						String[] param = new String[]{title,orderNo,userName,userPhone,time,contantAddress,contantName,contantPhone,sign};
						HashMap<String, Object> map = IndustrySMS.link(userPhone, content, "",param);
						String status = (String) map.get("status");
						String msg =  (String) map.get("msg");
						String msgId = UUID.randomUUID().toString();

						MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, content,order.getUserId(), status, new Date(), new Date(), msg ,code, 1);
						messageRecordMapper.save(messageRecord);
					}else {
						log.info("------------ [LOG["+ nowTime +"]sendGroupBuySucMassToBuyMsg] HasSend Code: " + code + " ------------");
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void sendGroupBuyFailMassMsg(Integer id,String type,String groupId) throws Exception {
		//TODO 【7 组团商品】群发失败退款短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			Commodity commodity = commodityMapper.findCommodityByIdDao(id);
			if(commodity != null){
				List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(id);
				MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("---------------------- messageTemple: " + messageTemple);
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				if(messageTemple != null){
					Integer max = 0;
					Integer hasSend = 0;
					List<Order> list = orderMapper.listByCommodityIdAndHasPay(id,groupId);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("---------------------- Order: " + list);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					if(list != null) {
						max = list.size();
						if(max > 0){
							synchronized (hasSend){
								Iterator<Order> iterator = list.iterator();
								while (iterator.hasNext()){
									Order orderTemp = iterator.next();
									if(orderTemp != null) {
										Order order = orderMapper.getById(orderTemp.getId());
										if(order != null){
											String code = order.getOrderNo() + order.getUserUseTime() + type;
											int exit = messageRecordMapper.countByCode(code);
											if(exit <= 0) {
												//TODO 退款业务
												ToolsHttpRequest.sendGet(Api.SEND_REFUND_AUTO,"id=" + order.getId());
												String msgTemple = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

												//TODO 短信参数组装
												String sign = messageTemple.getSign();
												String title = order.getOrderName();
												String orderNo = order.getOrderNo();
												String userName = order.getUserName();
												String userPhone = order.getUserPhone();
//												String time = "";
//												String contantAddress = "";
//												String contantName = "";
//												String contantPhone = "";
//												CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
//												if(commodityPriceList != null){
//													time = commodityPriceList.getPriceTime();
//													contantAddress = commodityPriceList.getAddress();
//													contantName = commodityPriceList.getContacts();
//													contantPhone = commodityPriceList.getContactsPhone();
//												}

												String[] param = new String[]{userName,title,orderNo,CONSULTATION_PHONE,sign};
												HashMap<String, Object> map = IndustrySMS.link(userPhone, msgTemple, "",param);
												String status = (String) map.get("status");
												log.info("------------ [LOG["+nowTime+"] MessageTempleController interfaceSendAllMeg] sendAllMeg  status: " + status + " ------------");
												String msg =  (String) map.get("msg");
												String msgId = UUID.randomUUID().toString();

												MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, msgTemple,order.getUserId(), status, new Date(), new Date(), msg,code, 1);
												messageRecordMapper.save(messageRecord);

											}else {
												log.info("------------ [LOG["+ nowTime +"] MessageRecordController interfaceSendMsg] HasSend Code: " + code + " ------------");
											}
										}

									}
								}
							}
						}

					}

				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	@Override
	public void sendRefundMsg(Integer id,String type) throws Exception {
		//TODO 【8 统一商品】退款短信
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			if(id != 0) {
				Order order = orderMapper.getById(id);
				if(order != null) {
					List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(order.getCommodityId());
					log.info("------------ [LOG["+ nowTime +"]interfaceSendRefundMsg] sendMeg  param : " + id + " _ " + type + " ------------");
					MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
					String code = order.getOrderNo() + order.getUserUseTime() + type;
					int exit = messageRecordMapper.countByCode(code);
					if(exit <= 0) {
						log.info("------------ [LOG["+ nowTime +"]interfaceSendRefundMsg] messageTemple : " + messageTemple + " ------------");
						String content = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());

						//TODO 短信参数组装
						String sign = messageTemple.getSign();
						String title = order.getOrderName();
						String orderNo = order.getOrderNo();
						String userName = order.getUserName();
						String userPhone = order.getUserPhone();
//						String time = "";
//						String contantAddress = "";
//						String contantName = "";
//						String contantPhone = "";
//						CommodityPriceList commodityPriceList = commodityPriceListMapper.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
//						if(commodityPriceList != null){
//							time = commodityPriceList.getPriceTime();
//							contantAddress = commodityPriceList.getAddress();
//							contantName = commodityPriceList.getContacts();
//							contantPhone = commodityPriceList.getContactsPhone();
//						}

						String[] param = new String[]{userName,title,orderNo,CONSULTATION_PHONE,sign};
						HashMap<String, Object> map = IndustrySMS.link(userPhone, content, "",param);
						String status = (String) map.get("status");
						String msg =  (String) map.get("msg");
						String msgId = UUID.randomUUID().toString();

						MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, content,order.getUserId(), status, new Date(), new Date(), msg ,code, 1);
						messageRecordMapper.save(messageRecord);
					}else {
						log.info("------------ [LOG["+ nowTime +"]sendRefundMsg] HasSend Code: " + code + " ------------");
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * ID 9 :
	 * 发送活动中奖短信
	 * @param id 订单id
	 * @param type 短信类型 WINNING
	 * @throws Exception
	 */
	@Override
	public void sendWiningMsg(Integer id,String type) throws Exception{
		//TODO 【9 会话活动】中奖短信生成订单
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			if(id != 0) {
				Order order = orderMapper.getById(id);
				if(order != null) {
					List<MessageTemple> listTemple = messageTempleMapper.listByCommodityId(order.getCommodityId());
					MessageTemple messageTemple = MsgTemplate.getMessageTemple( listTemple,type);
					if(messageTemple != null){
						String code = order.getOrderNo() + order.getUserUseTime() + type;
						int exit = messageRecordMapper.countByCode(code);
						if(exit <= 0) {
							log.info("------------ [LOG["+ nowTime +"]sendWiningMsg] messageTemple : " + messageTemple + " ------------");
							String content = MsgTemplate.getMsgTemplate(messageTemple.getTempleId());
							//TODO 短信参数组装
							String sign = messageTemple.getSign();
							String title = order.getOrderName();
							String orderNo = order.getOrderNo();
							String userName = order.getUserName();
							String userPhone = order.getUserPhone();

							String[] param = new String[]{title,orderNo,userName,userPhone,CONSULTATION_PHONE,sign};
							HashMap<String, Object> map = IndustrySMS.link(userPhone, content, "",param);
							String status = (String) map.get("status");
							String msg =  (String) map.get("msg");
							String msgId = UUID.randomUUID().toString();

							MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, content,order.getUserId(), status, new Date(), new Date(), msg ,code, 1);
							messageRecordMapper.save(messageRecord);
						}else {
							log.info("------------ [LOG["+ nowTime +"]sendWiningMsg] HasSend Code: " + code + " ------------");
						}
					}

				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * ID 10 :
	 * 发送退款失败短信
	 * @param id 订单id
	 * @param type 短信类型 REFUND_FAIL
	 * @throws Exception
	 */
	@Override
	public void sendRefundFailMsg(Integer id,String type,String sign,String errCodeDes) throws Exception {
		//TODO 【10 退款失败】订单退款失败
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		try{
			if(id != 0) {
				Order order = orderMapper.getById(id);
				if(order != null) {
					String code = order.getOrderNo() + order.getUserUseTime() + type;
					int exit = messageRecordMapper.countByCode(code);
					if(exit <= 0) {
						String content = MsgTemplate.getMsgTemplate(10);
						//TODO 短信参数组装
						String orderNo = order.getOrderNo();
						String userName = order.getUserName();
						String userPhone = order.getUserPhone();
						String[] param = new String[]{orderNo,userName,userPhone,errCodeDes,sign};
						HashMap<String, Object> map = IndustrySMS.link(MsgTemplate.TEL_TEST, content, "",param);
						String status = (String) map.get("status");
						String msg =  (String) map.get("msg");
						String msgId = UUID.randomUUID().toString();
						MessageRecord messageRecord = new MessageRecord(msgId, userPhone, sign, content,order.getUserId(), status, new Date(), new Date(), msg ,code, 1);
						messageRecordMapper.save(messageRecord);
					}else {
						log.info("------------ [LOG["+ nowTime +"]sendRefundFailMsg] HasSend Code: " + code + " ------------");
					}

				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
