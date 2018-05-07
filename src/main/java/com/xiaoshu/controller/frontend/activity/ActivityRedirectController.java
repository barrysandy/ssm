package com.xiaoshu.controller.frontend.activity;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.FocusedUserInfoService;
import com.xiaoshu.service.WechatActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 微信活动前端
 * @name: WechatActivityController
 * @author: XGB
 * @date: 2018-02-06 16:49
 */
@Controller
@RequestMapping(value = "/activityRedirect")
public class ActivityRedirectController extends BaseController {

    @Resource private WechatActivityService wechatActivityService;
    @Resource private CommodityService commodityService;

    /**
     * 前往报名页面 activityRedirect/interfaceRedirect?&id=
     */
    @RequestMapping("/interfaceRedirect")
    @ResponseBody
    public String interfaceRedirect(Integer id){
        try{
            String wechatActivityId = commodityService.getWechatActivityIdById(id);
            String url = null ;
            if(wechatActivityId != null) {
                url = wechatActivityService.getUrlById(wechatActivityId);
            }
            System.out.println("=================================");
            System.out.println("url : " + url);
            System.out.println("=================================");
            if(url != null){
                return url;
            }else {
                return "0";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "0";
    }

}
