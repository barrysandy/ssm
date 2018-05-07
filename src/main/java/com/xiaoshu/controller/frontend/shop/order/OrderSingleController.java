package com.xiaoshu.controller.frontend.shop.order;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.Order;
import com.xiaoshu.entity.OrderCodes;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.OrderCodesService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 商城模块--单一商品下单
 * @name: ShopController
 * @author: XGB
 * @date: 2018-03-11 8:01
 */
@Controller
@RequestMapping(value = "/order")
public class OrderSingleController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(OrderSingleController.class);

	@Resource private OrderService orderService;
	@Resource private OrderCodesService orderCodesService;
	@Resource private CommodityService commodityService;


	/**
	 *Controller_Function ：单一商品下单
	 *Access URL ：order/placeAnOrder?id=&Price=&Number=&Total=
	 * @param id 商品id
	 * @param Price 商品价格
	 * @param Number 商品数量
	 * @param Total 总金额
	 * @return  (1) 1下单成功  (2)-1参数错误 (3)-2商品错误 (4)-3生成订单编失败 (5)-4商品库存不足 (6)0订单保存失败
	 */
	@RequestMapping("placeAnOrderNoUserActivity")
	@ResponseBody
	public String placeAnOrderNoUserActivity(HttpServletRequest req, HttpServletResponse resp,
		Integer id,Double Price,Integer Number,Integer Number2,Double Total,String userPhone,String userUserTime,String userName){
		FocusedUserInfo user = (FocusedUserInfo) req.getSession().getAttribute("user");
		/**下单场景：
		 *  1.验证参数
		 **/
		try {
			if(id != null && !"".equals(id) && Price != null && !"".equals(Price) && Number != null && !"".equals(Number) && Total != null && !"".equals(Total) && userPhone != null && !"".equals(userPhone) && userUserTime != null && !"".equals(userUserTime)){
				Commodity commodity = commodityService.findCommodityByIdService(id);
				if(commodity != null){
					String img = ToolsImage.getFristImageUrlByServer(commodity.getArrayImg());//封面图片
					String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
					String user_id = null;
					if(user != null){ user_id = user.getId(); }
					String orderName = commodity.getCommodityName();
					String numberDescM = " 成人票 * " + Number;
					if(Number2 > 0){ numberDescM =  numberDescM + " 儿童票 * " + Number2; }
					String useCodeOder = orderService.getOrderCode();
					int exitOder = orderService.countByUseCode(useCodeOder);
					while (exitOder > 0) {
						/** 重新获取Order useCode */
						useCodeOder = orderService.getOrderCode();
						exitOder = orderService.countByUseCode(useCodeOder);
					}
					//descM 00001 影响订单跳转的详情页面
					Order order = new Order(0, orderName, "",time, null, "",  numberDescM, Total, 0, 0, user_id, userName, userPhone, userUserTime, commodity.getSellerId(), time, img, useCodeOder,"",null,Number,Number2,commodity.getId(),1,"");
					String order_no = orderService.getOrderNumber();//生产订单编号 synchronize方法
					if(!"".equals(order_no)){
						order.setOrderNo(order_no);//订单
						int i = orderService.save(order);//保存订单
						if(i > 0){
//							//获取消息队列
//							DelayQueue<Message> queue =  (DelayQueue<Message>) req.getServletContext().getAttribute("MQ");
//							// 添加延时消息,message 延时3600 s  3600 * 1000  如果执行了取消订单操作时会释放库存
//							queue.offer(new Message(2, order.getOrderNo(), 3600 * 1000));//消息id 2表示订单 消息
							//TODO 使用RabbitMq 当前需求没有要求锁定库存以及释放库存---待需求开发
							log.info("------------ [System Message] : New Order_NO = " + order.getOrderNo() + " time ：" + time + " ------------");
							String useCode = orderService.getOrderCode();
							int exit = orderCodesService.countByUseCode(useCode);
							while (exit > 0) {
								/** 重新获取useCode */
								useCode = orderService.getOrderCode();
								exit = orderCodesService.countByUseCode(useCode);
							}
							OrderCodes ordecode = new OrderCodes(UUID.randomUUID().toString(), order.getId(), order.getOrderNo(), "", useCode, "", time, user.getId(), commodity.getId(), 0, 1);
							int saveState = orderCodesService.save(ordecode);
							if(saveState > 0){
								log.info("------------ [System Message] : New Order_CODE = " + useCode + " time ：" + time + " ------------");
							}
							resp.getWriter().print(order.getId());//下单成功
						}else{
							resp.getWriter().print(0);//0订单保存失败
						}
					}else{
						resp.getWriter().print(-3);//-3生成订单编失败
					}
				}else{
					resp.getWriter().print(-2);//-2商品错误
				}
			}else{
				resp.getWriter().print(-1);//-1参数错误
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
