package com.xiaoshu.controller.frontend.shop;

import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.CommodityPriceList;
import com.xiaoshu.service.CommodityPriceListService;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.tools.ssmImage.ToolsImage;
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

/**
 * 商城模块
 * @name: ShopController
 * @author: XGB
 * @date: 2018-03-11 8:01
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopController extends BaseController {

	@Resource private CommodityService commodityService;
	@Resource private OrderService orderService;
	@Resource private CommodityPriceListService commodityPriceListService;


	/**
	 * 商品购买详情 - - 单一商品
	 * @return String
	 * @author XGB
	 * @date 2018-02-28 10:45
	 */
	@RequestMapping("/activityCommodityNoUser")
	public ModelAndView singleCommodityNoUser(HttpServletRequest request, String menuId, int id ,String from, String isappinstalled){
		String view = "mobile/mall/activityCommodityDetails";
		String buy ="1";
		if(from == null){from = "singlemessage";}
		if(isappinstalled == null){isappinstalled = "0";}
		/** 准备签名参数 */
		String share = "&from=" + from + "&isappinstalled=" + isappinstalled ;
		/** mapKey签名成功后会将本次签名存入map的key值，下次进入该控制器会先判断Map中的值是否存在，不存在则刷新（重新生成） */
		String mapKey =  "shop/activityCommodityNoUser?menuId=" + menuId + "&id=" + id + share;
		/** url签名页面，一定是当前的controller路径并附带参数和值 */
		String url = Set.SYSTEM_URL + mapKey;
		/** link签名分享链接，页面分享后，点击进入的链接 */
		String shareLink = url;
		/** imgUrl签名分享图片，页面分享后，分享出来的小图片 */
		String shareImgUrl = null;
		/** shareTitle签名分享标题 */
		String shareTitle = null;
		/** shareDesc签名分享描述 */
		String shareDesc = null;
		try{
			Commodity bean = commodityService.findCommodityByIdService(id);
			if(bean != null){
				//Todo 统计订单的数量并设置商品是否能继续购买
				if(bean.getMaxNumber() != -1 || bean.getMaxNumber2() != -1){
					int maxNumber = orderService.countByNUMBER(bean.getId());
					int maxNumber2 = orderService.countByNUMBER2(bean.getId());
					if(bean.getMaxNumber() != -1){
						if(maxNumber >= bean.getMaxNumber()){
							buy ="0";
						}
					}
					if(bean.getMaxNumber2() != -1){
						if(maxNumber2 >= bean.getMaxNumber2()){
							buy ="0";
						}
					}
				}
				shareImgUrl = bean.getShareImage();
				shareTitle = bean.getShareTitle();
				shareDesc = bean.getShareDescM();
				if(bean.getUrl() != null){
					if(!"".equals(bean.getUrl())){
						view = bean.getUrl();
					}
				}
				List<CommodityPriceList> groupPriceList = commodityPriceListService.listGroupPTimeByCommodityId(bean.getId());
				if(groupPriceList != null){
					if(groupPriceList.size() > 0){
						String priceTime = groupPriceList.get(0).getPriceTime();
						List<CommodityPriceList> commodityPriceList = commodityPriceListService.listByCommodityIdAndPTime(bean.getId(),priceTime);
						request.setAttribute("commodityPriceList",commodityPriceList);
					}else {
						request.setAttribute("commodityPriceList",null);
					}
				}
			}
			processingSharing(request,shareImgUrl,shareTitle,shareDesc,shareLink,url,mapKey,menuId);
			request.setAttribute("commodity",bean);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("buy",buy);
		request.setAttribute("id",id);
		request.setAttribute("menuId",menuId);
		return toVm(view);
	}


    /**
     * 填写订单信息
     * @return String
     * @author XGB
     * @date 2018-03-13 9:22
     */
    @RequestMapping("/toCreateOrderInUser")
    public ModelAndView toCreateOrderInUser(HttpServletRequest request, int id, String menuId, String from, String isappinstalled){
        try{
            Commodity bean = commodityService.findCommodityByIdService(id);
            request.setAttribute("commodity",bean);
			List<CommodityPriceList> groupPriceList = commodityPriceListService.listGroupPTimeByCommodityId(bean.getId());
			if(groupPriceList != null){
				String[] choseDay = new String[groupPriceList.size()];
				for (int i=0;i<groupPriceList.size();i++ ){
					choseDay[i] = groupPriceList.get(i).getDescM();
					String priceTime = groupPriceList.get(i).getPriceTime();
					List<CommodityPriceList> commodityPriceList = commodityPriceListService.listByCommodityIdAndPTime(bean.getId(),priceTime);
					request.setAttribute("commodityPriceList" + i + 1,commodityPriceList);
				}
				request.setAttribute("choseDay",choseDay);
				request.setAttribute("choseDayLength",choseDay.length);
			}

        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("id",id);
        request.setAttribute("menuId",menuId);
        return toVm("mobile/mall/toCreateOrderInUser2");
    }

	/**
	 * 获取价格
	 * @return String
	 * @author XGB
	 * @date 2018-04-03 15:26
	 */
	@RequestMapping("/interfaceGetPriceJSON")
	@ResponseBody
	public String interfaceGetPriceJSON(HttpServletResponse response, int id, String priceTime){
		try{
			List<CommodityPriceList> commodityPriceList = commodityPriceListService.listByCommodityIdAndDesC(id,priceTime);
			StringBuffer stringBuffer = new StringBuffer();
			if(commodityPriceList != null){
				for(int i = 0; i< commodityPriceList.size();i++) {
					stringBuffer.append(commodityPriceList.get(i).getPrice() + ",");
				}
			}
			response.getWriter().print(stringBuffer.toString());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 活动报名签名方法
	 * @param request
	 * @param shareImgUrl
	 * @param shareTitle
	 * @param shareDesc
	 * @param shareLink
	 * @param url
	 * @param mapKey
	 * @param menuId
	 */
	private static void processingSharing(HttpServletRequest request,String shareImgUrl,String shareTitle,String shareDesc,String shareLink,String url,String mapKey,String menuId){
		/** 设置分享标题和描述 */
		/** 配置分享的URL和Image和Desc等信息 */
		request.setAttribute("shareLink", shareLink);
		request.setAttribute("shareImgUrl", shareImgUrl);
		request.setAttribute("shareTitle", shareTitle);
		request.setAttribute("shareDesc", shareDesc);
		/** 用于页面签名的参数 */
		request.setAttribute("url", url);
		request.setAttribute("mapKey", mapKey);
		request.setAttribute("menuId", menuId);
	}
}
