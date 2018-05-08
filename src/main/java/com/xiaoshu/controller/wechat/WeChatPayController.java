package com.xiaoshu.controller.wechat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xgb.springrabbitmq.dto.DtoMessage;
import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.api.*;
import com.xiaoshu.enumeration.EnumsMQName;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.vo.WeChatPayNotify;
import com.xiaoshu.vo.WeChatPayRefund;
import com.xiaoshu.wechat.pay.*;
import org.apache.ibatis.annotations.Param;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay")
public class WeChatPayController {
	private static Logger log = LoggerFactory.getLogger(WeChatPayController.class);

	/**
	 * 发起支付请求接口
	 * URL：pay/topayServletNoUser?menuId=38&openId=ozAuT0c63kMJ2vusNDZw7RWWVzlQ&userId=001&orderNo=1110001&body=&money=1.00
	 * @param userId 用户id
	 * @param openId 用户的openid
	 * @param orderNo 订单编号
	 * @param body 订单描述
	 * @param money 订单金额
	 * @param menuId  公众号标识
	 */
	@RequestMapping("topayServletNoUser")
	public String topayServletNoUser(HttpServletRequest req,HttpServletResponse resp,String menuId,
							   @Param("userId")String userId,@Param("openId")String openId,@Param("orderNo")String orderNo,@Param("money")String money,@Param("body")String body){
		resp.setCharacterEncoding("UTF-8");
		String appid = "";//appid
		String appsecret = "";//app秘钥
		String partner = "";//商户账号
		String partnerkey = "";//商户支付秘钥
		String notify_url = "";//支付通知地址
		String notify_error_url = "";//支付失败通知地址
		try {
			//金额转化为分为单位
			float sessionmoney = Float.parseFloat(money);
			String finalmoney = String.format("%.2f", sessionmoney);
			finalmoney = finalmoney.replace(".", "");
			//商户相关资料
			//读取Map中的token
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> map = mapPublicNumber.getMap();
			if(map != null && map.size() == 0){
				System.out.println("map not data");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
			appid = map.get("appId" + menuId);
			appsecret = map.get("appSecret" + menuId);
			partner = map.get("mchId" + menuId);
			partnerkey = map.get("mchKey" + menuId);
			notify_url = map.get("notifyUrl" + menuId);//支付通知地址
			notify_error_url = map.get("notifyErrorUrl" + menuId);//支付失败通知地址

			//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
			String currTime = TenpayUtil.getCurrTime();
			//8位日期
			String strTime = currTime.substring(8, currTime.length());
			//四位随机数
			String strRandom = TenpayUtil.buildRandom(4) + "";
			//10位序列号,可以自行调整。
			String strReq = strTime + strRandom;


			//商户号
			String mch_id = partner;
			//子商户号  非必输
			//String sub_mch_id="";
			//设备号   非必输
			String device_info="";
			//随机数
			String nonce_str = strReq;
			//商品描述
			//String body = describe;

			//附加数据
			String attach = userId;
			//商户订单号
			String out_trade_no = orderNo;
			int intMoney = Integer.parseInt(finalmoney);

			//总金额以分为单位，不带小数点
			int total_fee = intMoney;
			//订单生成的机器 IP
			String spbill_create_ip = req.getRemoteAddr();
			//订 单 生 成 时 间   非必输
			//String time_start ="";
			//订单失效时间      非必输
			//String time_expire = "";
			//商品标记   非必输
			//String goods_tag = "";
			//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。


			String trade_type = "JSAPI";
			String openid = openId;
			//非必输
			//String product_id = "";
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", appid);
			packageParams.put("mch_id", mch_id);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("attach", attach);
			packageParams.put("out_trade_no", out_trade_no);

			System.out.println("total_fee" + total_fee );
			//这里写的金额为1 分到时修改
			packageParams.put("total_fee", total_fee+"");
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);

			packageParams.put("trade_type", trade_type);
			packageParams.put("openid", openid);

			RequestHandler reqHandler = new RequestHandler(req, resp);
			reqHandler.init(appid, appsecret, partnerkey);

			String sign = reqHandler.createSign(packageParams);
			String xml="<xml>"+
			"<appid>"+appid+"</appid>"+
			"<mch_id>"+mch_id+"</mch_id>"+
			"<nonce_str>"+nonce_str+"</nonce_str>"+
			"<sign>"+sign+"</sign>"+
			"<body><![CDATA["+body+"]]></body>"+
			"<attach>"+attach+"</attach>"+
			"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
			"<attach>"+attach+"</attach>"+
			//金额，这里写的1 分到时修改
			"<total_fee>"+total_fee+"</total_fee>"+
			"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
			"<notify_url>"+notify_url+"</notify_url>"+
			"<trade_type>"+trade_type+"</trade_type>"+
			"<openid>"+openid+"</openid>"+
			"</xml>";
			String allParameters = reqHandler.genPackage(packageParams);
			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			Map<String, Object> dataMap2 = new HashMap<String, Object>();
			String prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if(!"".equals(prepay_id)){
				System.out.println("prepay_id: "+prepay_id);
				SortedMap<String, String> finalpackage = new TreeMap<String, String>();
				String appid2 = appid;
				String timestamp = Sha1Util.getTimeStamp();
				String nonceStr2 = nonce_str;
				String prepay_id2 = "prepay_id="+prepay_id;
				String packages = prepay_id2;
				finalpackage.put("appId", appid2);
				finalpackage.put("timeStamp", timestamp);
				finalpackage.put("nonceStr", nonceStr2);
				finalpackage.put("package", packages);
				finalpackage.put("signType", "MD5");
				String finalsign = reqHandler.createSign(finalpackage);//sign 最终签名
				//将支付参数组装为JSON
				PayParam payParam = new PayParam();
				payParam.setAppId(appid2);
				payParam.setNonceStr(nonceStr2);
				payParam.setPackageValue(packages);
				payParam.setPaySign(finalsign);
				payParam.setSignType("MD5");
				payParam.setTimeStamp(timestamp);
				payParam.setPrepayId(prepay_id);
				String JSONStr = JSONUtils.toJSONString(payParam);
				System.out.println(JSONStr);
				resp.getWriter().append(JSONStr);
			}else {
				PayParam payParam = new PayParam("","","","","","","");
				String JSONStr = JSONUtils.toJSONString(payParam);
				System.out.println(JSONStr);
				resp.getWriter().append(JSONStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 微信支付通知接口
	 * URL：pay/payNotify
	 */
	@RequestMapping("interfacePayNotify")
	private String interfacePayNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/xml;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		/** 解析结果存在HashMap中 */
		Map<String ,String> map = new HashMap<String, String>();
		InputStream inputStream = req.getInputStream();
		try{
			/** 读入输入流 */
			SAXReader reader = new SAXReader();
			Document doc = reader.read(inputStream);
			/** 得到xml根目录 */
			Element root = doc.getRootElement();
			/** 得到元素的所有子节点 */
			List<Element> elementList = root.elements();
			for(Element e : elementList){
				map.put(e.getName() , e.getText());
			}

			//TODO 进行业务处理，比如写入账单，进行账单价格对照，然后修改与商品价格相差很多的攻击攻击购买方式的订单状态...

			String appid = map.get("appid");
			String attach = map.get("attach");
			String bank_type = map.get("bank_type");
			String cash_fee = map.get("cash_fee");
			String fee_type = map.get("fee_type");
			String is_subscribe = map.get("is_subscribe");
			String mch_id = map.get("mch_id");
			String nonce_str = map.get("nonce_str");
			String openid = map.get("openid");
			String out_trade_no = map.get("out_trade_no");
			String result_code = map.get("result_code");
			String return_code = map.get("return_code");
			String sign = map.get("sign");
			String time_end = map.get("time_end");
			String total_fee = map.get("total_fee");
			String trade_type = map.get("trade_type");
			String transaction_id = map.get("transaction_id");
			if(out_trade_no != null){
				if(out_trade_no != null){
					if(!"".equals(total_fee)){
						WeChatPayNotify weChatPayNotify = new WeChatPayNotify(appid, attach, bank_type, cash_fee, fee_type, is_subscribe, mch_id, nonce_str, openid, out_trade_no, result_code, return_code, sign, time_end, total_fee, trade_type, transaction_id);
						String json = JSONUtils.toJSONString(weChatPayNotify);
						//TODO Rabbit消息队列 检查账单
						String url = Api.CHECK_WATER;
						json = ToolsASCIIChang.stringToAscii(json);
						String params = "json="+json;
						DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), url, "get" ,params , null);
						String message = DtoMessage.transformationToJson(dtoMessage);
						ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
						DeadLetterPublishService deadLetterPublishServiceContext = context.getBean(DeadLetterPublishService.class);
						deadLetterPublishServiceContext.send(EnumsMQName.DEAD_ORDER_CHECK , message);
					}
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}finally {
			inputStream.close();
		}
		return null;
	}

	/**
	 * 微信支付通知接口
	 * URL：pay/payNotify
	 */
	@RequestMapping("interfacePayNotifyError")
	private String interfacePayNotifyError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		resp.setContentType("text/xml;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		log.info("------------ [SYSOUT[" + nowTime + "] WeChatPayController interfacePayNotifyError] Error  ------------");
		return null;
	}

	/**
	 * 解析微信退款的XML为 WeChatPayRefund
	 * @param xml
	 * @return
	 */
	public static WeChatPayRefund getWeChatPayRefundBean(String xml){
		WeChatPayRefund weChatPayRefund = null;
		Map<String ,String> map = new HashMap<String, String>();
		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		try{
			/** 读入输入流 */
			SAXReader reader = new SAXReader();
			Document doc = reader.read(inputStream);
			/** 得到xml根目录 */
			Element root = doc.getRootElement();
			/** 得到元素的所有子节点 */
			List<Element> elementList = root.elements();
			for(Element e : elementList){
				map.put(e.getName() , e.getText());
			}
			String return_code = map.get("return_code");
			String return_msg = map.get("return_msg");
			String appid = map.get("appid");
			String mch_id = map.get("mch_id");
			String nonce_str = map.get("nonce_str");
			String sign = map.get("sign");
			String result_code = map.get("result_code");
			String transaction_id = map.get("transaction_id");
			String out_trade_no = map.get("out_trade_no");
			String out_refund_no = map.get("out_refund_no");
			String refund_id = map.get("refund_id");
			String refund_channel = map.get("refund_channel");
			String refund_fee = map.get("refund_fee");
			String coupon_refund_fee = map.get("coupon_refund_fee");
			String total_fee = map.get("total_fee");
			String cash_fee = map.get("cash_fee");
			String coupon_refund_count = map.get("coupon_refund_count");
			String cash_refund_fee = map.get("cash_refund_fee");
			String err_code = map.get("err_code");
			String err_code_des = map.get("err_code_des");
			if(return_msg != null){
				if("OK".equals(result_code) || "SUCCESS".equals(result_code) || "FAIL".equals(result_code)) {
					weChatPayRefund = new WeChatPayRefund(return_code, return_msg, appid, mch_id, nonce_str, sign, result_code, transaction_id, out_trade_no, out_refund_no,
							refund_id, refund_channel, refund_fee, coupon_refund_fee, total_fee, cash_fee, coupon_refund_count, cash_refund_fee, err_code, err_code_des);
					if(weChatPayRefund != null) {
						return weChatPayRefund;
					}
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try{
				inputStream.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return weChatPayRefund;
	}


}
