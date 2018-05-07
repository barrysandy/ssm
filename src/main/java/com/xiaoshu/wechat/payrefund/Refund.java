package com.xiaoshu.wechat.payrefund;

import com.xiaoshu.entity.OrderRefund;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.api.WeChatAPI;
import com.xiaoshu.wechat.pay.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class Refund {

	public static String doWechatRefund(HttpServletRequest request, OrderRefund bean, String menuId) {
		String jsonStr = null;
		String resourcesPath = "resources/";//服务器resources文件路径
		String path = request.getSession().getServletContext().getRealPath(resourcesPath);
		path = path + File.separator + "cert" + File.separator + "apiclient_cert.p12";
		//G:/workspaceSVN/weChatManage/src/main/webapp/resources/cert/apiclient_cert.p12
		System.out.println(" ---------------- path  ---------------- : " + path );
		File file = new File(path);
		//将启用的公众号参数加入Map中
		MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
		Map<String, String> map = mapPublicNumber.getMap();
		if(map != null && map.size() == 0){
			System.out.println("map not data");
			JobPublicAccount.ToRefreshMapJobPublicAccount();
		}
		String out_refund_no = bean.getTransactionId();// 退款单号
		String out_trade_no = bean.getOrderNo();// 订单号
		String total_fee = String.valueOf(bean.getOrderMoney());// 总金额
		String refund_fee = String.valueOf(bean.getRefundFee());// 退款金额
		String nonce_str = ToolsString.getNonceStr();// 随机字符串
		String appid = map.get("appId"+ menuId); //微信公众号apid
		String appsecret = map.get("appSecret"+ menuId); //微信公众号appsecret
		String mch_id = map.get("mchId"+ menuId);  //微信商户id
		String op_user_id = map.get("mchId"+ menuId);//就是MCHID
		String partnerkey = map.get("mchKey"+ menuId);//商户平台上的那个KEY


		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		try {
			jsonStr = ClientCustomSSL.doWechatRefund(file, WeChatAPI.PAY_REFUND, xml,mch_id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
}
