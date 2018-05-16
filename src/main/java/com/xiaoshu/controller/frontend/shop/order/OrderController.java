package com.xiaoshu.controller.frontend.shop.order;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * 商城模块
 * @name: ShopController
 * @author: XGB
 * @date: 2018-03-11 8:01
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(OrderController.class);

	@Resource private OrderService orderService;
	@Resource private OrderCodesService orderCodesService;
	@Resource private CommodityService commodityService;

	/**
	 * 获取商品最新库存
	 * Access URL ：order/getStock?id=
	 * @param id 商品id
	 * @return  商品库存
	 */
	@RequestMapping("getStockNoUser")
	@ResponseBody
	public String getStockNoUser(HttpServletResponse resp,@RequestParam("id") Integer id){
		/**
		 *  1.验证参数
		 **/
		try {
			if(id != null && !"".equals(id)){
				Commodity commodity = commodityService.findCommodityByIdService(id);
				if(commodity != null){
					int stock = commodityService.findcommodityStockByIdService(commodity.getId());//读取库存
					resp.getWriter().print(stock);
				}else{
					resp.getWriter().print(-2);//-2商品不存在
				}
			}else{
				resp.getWriter().print(-1);//-1参数错误
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * Controller_Function ：查询订单状态
	 * Access URL ：order/GET/orderState?order_no=
	 * @param order_no 订单编号order_no
	 * @return  (1) -1 传入的参数错误 (2)其它表示订单状态
	 */
	@ResponseBody
	@RequestMapping("/GET/orderState")
	public String GETorderState(HttpServletResponse resp,@RequestParam("order_no") String order_no){
		/**未支付订单到期取消场景：
		 *  1.验证code是否可用
		 *  2.删除订单关联的核销码
		 *  3.删除订单
		 **/
		try {
			if(order_no != null && !"".equals(order_no)){//判断传入的参数是否有效
				int i = orderService.getTypeStateByOrderNo(order_no);
				resp.getWriter().print(i);
			}
			else{
				resp.getWriter().print(-1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


//	/**
//	 * Controller_Function ：消息队列2小时到期，清除未付款的订单
//	 * Access URL ：order/cancelOrder?order_no=&code=
//	 * @param order_no 订单编号order_no
//	 * @param code 删除订单的验证码 由系统在接收到删除订单的消息时生成，并放入Map集合中，该消息code通过验证后方能删除订单（防止非删除订单），code只能使用一次
//	 * @return  (1) 0 传入的参数错误 (2)-1 表示 code验证失败 (3)>0 表示删除成功
//	 */


	/**
	 * Controller_Function ：查询订单详情
	 *Access URL ：order/orderDetailsInUser?order_id=
	 */
	@RequestMapping("/orderDetailsInUser")
	public String orderDetails(HttpServletRequest req, HttpServletResponse resp,@RequestParam("order_id") Integer order_id,@RequestParam("menuId") String menuId){
		String url = "mobile/user/flower/userOrderDetails";
		try {
			Order order = orderService.getById(order_id);
			int commodityId = 0 ;
			if(order != null){
				if(order.getDescM() != null){
					/** 花的活动 订单详情需要跳转到下面页面*/
					if("00001".equals(order.getDescM())){
						url = "mobile/user/userOrderDetails";
					}
				}
				OrderCodes orderCodes = orderCodesService.getOneByOrderId(order.getId());
				/** 展示唯一核销码 */
				setCodeImage(orderCodes,commodityId,commodityService,req,menuId);
				req.setAttribute("orderCode", orderCodes);
				req.setAttribute("order", order);
				req.setAttribute("order_id", order_id);
				req.setAttribute("menuId", menuId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * Controller_Function ：查询订单详情
	 *Access URL ：order/orderDetailsByOrderNoInUser?orderNo=
	 */
	@RequestMapping("/orderDetailsByOrderNoInUser")
	public String orderDetailsByOrderNoInUser(HttpServletRequest req, HttpServletResponse resp,@RequestParam("orderNo") String orderNo,@RequestParam("menuId") String menuId){
		String url = "mobile/user/flower/userOrderDetails";
		try {
			Order order = orderService.getByOrderNo(orderNo);
			int commodityId = 0 ;
			if(order != null){
				if(order.getDescM() != null){
					/** 花的活动 订单详情需要跳转到下面页面*/
					if("00001".equals(order.getDescM())){
						url = "mobile/user/flower/userOrderDetails";
					}
				}
				OrderCodes orderCodes = orderCodesService.getById(order.getId());
				/** 展示唯一核销码 */
				setCodeImage(orderCodes,commodityId,commodityService,req,menuId);
				req.setAttribute("orderCode", orderCodes);
				req.setAttribute("order", order);
				req.setAttribute("order_id", order.getId());
				req.setAttribute("menuId", menuId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}



	/**
	 * 获取订单JSON
	 * @return String
	 * @author XGB
	 * @date 2018-03-06 15:08
	 */
	@RequestMapping("/adminGetOrderListNoUserJSON")
	@ResponseBody
	public String adminGetOrderListNoUserJSON(HttpServletRequest request, HttpServletResponse response, int cpage,String descM){
		try{
			response.setCharacterEncoding("UTF-8");
			int pageSize = 20;
			int totalRecords = orderService.countBydescM(descM);//总记录数
			int totalPage = ToolsPage.totalPage(totalRecords, pageSize);//总页数
			if(cpage > totalPage){
				response.getWriter().print("0");
			}else{
				int index =(cpage - 1) * 10;//当前查询的开始记录
				List<Order> listOrder = orderService.listBydescM(index,pageSize,descM);
				if(listOrder != null){
					response.getWriter().print(JSONUtils.toJSONString(listOrder));
				}else{
					response.getWriter().print("0");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/** 展示唯一核销码 */
	public static void setCodeImage(OrderCodes orderCode,Integer commodityId,CommodityService commodityService,HttpServletRequest req,String menuId) {
		try {
			if(orderCode != null){
				commodityId = orderCode.getCommodityId();
				Commodity commodity = commodityService.findCommodityByIdService(commodityId);
				req.setAttribute("commodity", commodity);
				String code = com.xiaoshu.api.Set.SYSTEM_URL + "orderWrite/WriteOffOrderCodeInUser?menuId=" + menuId + "&useCode= " + orderCode.getUseCode()+ "&orderId= " + orderCode.getOrderId();
				code = URLEncoder.encode(code,"UTF-8");
				req.setAttribute("code", code);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
