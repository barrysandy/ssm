package com.xiaoshu.tools.sendMsg;

import com.xiaoshu.entity.MessageTemple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 短信模板类，变更类型以+为替换符
 * @Description:
 * @author XGB
 * @date： 2018-04-08 14:45
 */
public class MsgTemplate {

	/**
	 * 400 电话
	 */
	public static final String TEL = "18781917461";

	public static final String TEL_TEST = "18781917461";

	/**
	 * 短信模板ID：0
	 * 单一商品购买（未满足开团人数购买短信）
	 */
	public static String SINGLE_BUY  = "感谢您订购一个半小时旅游圈“+”活动；订单号+，购买人信息：+ 联系电话：+ 成人+位，儿童+位，活动时间：+；地址：+。组团成功后，我们将在出发前48小时再次发送确认信息，不成功，我们将在届时3个工作日内原路返回款项。咨询电话：+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";

	/**
	 * 短信模板ID：1
	 * 单一商品购买（满足开团人数，群发开团短信）
	 */
	public static String SINGLE_BUY_GROUP_SUC_MASS = "+你好，你参与的“+”组团成功。请您于+上午，在+集合，联系人+，联系电话+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";

	/**
	 * 短信模板ID：2
	 * 单一商品购买（满足开团人数，合并的购买短信）
	 */
	public static String SINGLE_BUY_GROUP_SUC_TOBUY = "感谢您订购一个半小时旅游圈“+”活动；订单号+，购买人信息：+ 联系电话：+ 成人+位，儿童+位。已经组团成功。请您于+上午，在+集合，联系人+，联系电话+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";

	/**
	 * 短信模板ID：3
	 * 单一商品购买（未满足开团人数，时间到达，群发失败短信）
	 */
	public static String SINGLE_BUY_GROUP_FIAL_MASS = "+你好，你参与的“+”订单号+，组团超时失败。我们将在届时3个工作日内原路返回款项。咨询电话：+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";




	/**
	 * 短信模板ID：4
	 * 组团商品购买（当前团未满短信）
	 */
	public static String GROUP_BUY = "感谢您订购一个半小时旅游圈“+”活动；订单号+，购买人信息：+ 联系电话：+ ，活动时间：+；地址：+。组团成功后，我们将在出发前48小时再次发送确认信息，不成功，我们将在届时3个工作日内原路返回款项。咨询电话：+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";


	/**
	 * 短信模板ID：5
	 * 组团商品购买（当前团满群发组团成功短信）
	 */
	public static String GROUP_BUY_SUC_MASS = "+你好，你参与的“+”组团成功。请您于+上午，在+集合，联系人+，联系电话+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";


	/**
	 * 短信模板ID：6
	 * 组团商品购买（当前团满短信时，合并购买短信）
	 */
	public static String GROUP_BUY_SUC_TOBUY = "感谢您订购一个半小时旅游圈“+”活动；订单号+，购买人信息：+ 联系电话：+ 。已经组团成功。请您于+上午，在+集合，联系人+，联系电话+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";


	/**
	 * 短信模板ID：7
	 * 组团商品购买（未满足开团人数，时间到达，群发失败短信）
	 */
	public static String GROUP_BUY_FIAL_MASS = "+你好，你参与的“+”订单号+，组团超时失败。我们将在届时3个工作日内原路返回款项。咨询电话：+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";

	/**
	 * 短信模板ID：8
	 * REFUND 退款
	 */
	public static String REFUND ="+你好，你参与的“+”订单号+退款成功。咨询电话：+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";

	/**
	 * 短信模板ID：9
	 * WINNING 中奖短信
	 */
	public static String WINNING = "感谢您参与一个半小时旅游圈“+”活动；订单号+，中奖人信息：+ 联系电话：+ 。咨询电话：+。周边出游尽在一个半小时旅游圈，圈圈承包您的周末！【+】";


	/**
	 * 短信模板ID：10
	 * REFUND_FAIL 退款失败短信
	 */
	public static String REFUND_FAIL = "订单号+，联系人信息：+ 联系电话：+ 退单失败，+，详情请查看管理后台。【+】";


	/**
	 * 短信模板ID：11
	 * MEETING_MSG_ALL 发送会议通知短信
	 */
	public static String MEETING_MSG_ALL = "+先生/女生您好，+邀请你参加+会议，你的会议签到码为+，联系人：+ 联系人电话：+，会议地址+，会议时间+，感谢你准时参加。【+】";

	/**
	 * 获取模板
	 * @param id
	 * @return
	 */
	public static String getMsgTemplate(int id){
		String template = "";
		if(id == 0){
			template = SINGLE_BUY;
		}
		if(id == 1){
			template = SINGLE_BUY_GROUP_SUC_MASS;
		}
		if(id == 2){
			template = SINGLE_BUY_GROUP_SUC_TOBUY;
		}
		if(id == 3){
			template = SINGLE_BUY_GROUP_FIAL_MASS;
		}
		if(id == 4){
			template = GROUP_BUY;
		}
		if(id == 5){
			template = GROUP_BUY_SUC_MASS;
		}
		if(id == 6){
			template = GROUP_BUY_SUC_TOBUY;
		}
		if(id == 7){
			template = GROUP_BUY_FIAL_MASS;
		}
		if(id == 8){
			template = REFUND;
		}
		if(id == 9){
			template = WINNING;
		}if(id == 10){
			template = REFUND_FAIL;
		}if(id == 11){
			template = MEETING_MSG_ALL;
		}
		return template;
	}

	/** 获取 模板集合 */
	public static List<String> getListTemplate(){
		List<String> list = new ArrayList<String>();
		list.add(getMsgTemplate(0));
		list.add(getMsgTemplate(1));
		list.add(getMsgTemplate(2));
		list.add(getMsgTemplate(3));
		list.add(getMsgTemplate(4));
		list.add(getMsgTemplate(5));
		list.add(getMsgTemplate(6));
		list.add(getMsgTemplate(7));
		list.add(getMsgTemplate(8));
		list.add(getMsgTemplate(9));
		list.add(getMsgTemplate(10));
		list.add(getMsgTemplate(11));
		return list;
	}



	/**
	 * 从短信模板中获取对应类型的一个模板对象
	 * @param listTemple 模板集合
	 * @param type 模板类型
	 * @return 对应类型的模板对象或null
	 */
	public static MessageTemple getMessageTemple(List<MessageTemple> listTemple, String type){
		if(listTemple != null){
			Iterator<MessageTemple> iterator = listTemple.iterator();
			while (iterator.hasNext()){
				MessageTemple messageTempleTemp = iterator.next();
				if(type.equals(messageTempleTemp.getTempleType())) {
					return messageTempleTemp;
				}
			}
		}
		return null;
	}

}
