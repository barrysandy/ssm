package com.xiaoshu.controller.frontend.shop.commodity;

import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.enumeration.EnumsPayType;
import com.xiaoshu.service.CommodityPriceListService;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.service.SellerService;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.vo.CommodityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 商品模块
 * @name: ShopController
 * @author: XGB
 * @date: 2018-04-04 15:03
 */
@Controller
@RequestMapping(value = "/commodity")
public class CommodityController extends BaseController {

	@Resource private SellerService sellerService;
	@Resource private CommodityService commodityService;
	@Resource private OrderService orderService;

	/**
	 * 获取商品购买链接
	 * @param id 主键ID
	 * @return String
	 * @author XGB
	 * @date 2018-04-04 15:10
	 */
	@RequestMapping("/interfaceCopeLink")
	@ResponseBody
	public String interfaceCopeLink(HttpServletResponse response,int id){
		String data = "0";
		try{
			Commodity commodity = commodityService.findCommodityByIdService(id);
			if(commodity != null){
				Seller seller = sellerService.findSellerByIdService(commodity.getSellerId());
				if(seller != null){
					String menuId = seller.getMenuId();
					if(menuId != null){
						data = Set.SYSTEM_URL + "shop/activityCommodityNoUser?menuId="+menuId+"&id="+id+"&from=singlemessage&isappinstalled=0";
					}
				}

			}
			response.getWriter().print(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断订单能否下单（订单上限已满）
	 * commodity/interfaceGetTotalOrder?id=
	 * @param id 商品id
	 * @return String
	 * @author XGB
	 * @date 2018-4-9 9:33
	 */
	@RequestMapping("/interfaceGetTotalOrder")
	@ResponseBody
	public String interfaceGetTotalOrder(int id){
		try{
			Commodity bean = commodityService.findCommodityByIdService(id);
			//Todo 统计订单的数量并设置商品是否能继续购买
			if(bean.getMaxNumber() != -1 || bean.getMaxNumber2() != -1){
				int maxNumber = orderService.countByNUMBER(bean.getId());
				int maxNumber2 = orderService.countByNUMBER2(bean.getId());
				if(bean.getMaxNumber() != -1){
					if(maxNumber >= bean.getMaxNumber()){
						return "max";
					}
				}
				if(bean.getMaxNumber2() != -1){
					if(maxNumber2 >= bean.getMaxNumber2()){
						return "max";
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return "exception";
		}
		return "ok";
	}


}
