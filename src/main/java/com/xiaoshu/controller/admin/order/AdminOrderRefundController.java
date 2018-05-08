package com.xiaoshu.controller.admin.order;

import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/orderRefund")
public class AdminOrderRefundController {


    @Resource private OrderRefundService orderRefundService;
    @Resource private SellerService sellerService;
    @Autowired private DeadLetterPublishService deadLetterPublishService;

    /**
     * 分页查询
     * @return view
     * @author XGB
     * @date 2018-03-15 10:07
     */
    @RequestMapping("/list")
    public String list(String menuid) {
        return "redirect:/orderRefund/listAllParam?fullListSize=0&list=&pageSize=10&pageNumber=1&pageCount=0&searchId=&sortCriterion=&search=&menuid=" + menuid + "date1=0&date2=0&status=-1&typeState=-1&userId=&sellerId=-1";
    }

    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-03-15 10:07
     */
    @RequestMapping("/listAllParam")
    public String listAllParam(Pager pager, Model model, String search, String menuid,String date1,String date2,int status,int typeState,String userId,int sellerId) {
        try{
            if(date1 != null && date1.length() == 10 ){date1 = date1 + " 00:00:00";}
            if(date2 != null && date2.length() == 10 ){date2 = date2 + " 23:59:59";}

            //对传入的Date参数进行处理
            if(date1 == null || "0".equals(date1) || "".equals(date1)){
                date1 = ToolsDate.getStringDateLastMonth(-3);
            }
            if(date2 == null || "0".equals(date2) || "".equals(date2)){
                date2 = ToolsDate.getStringDate(ToolsDate.simpleSecond);
            }
            date2 = ToolsDate.getMaxTime(ToolsDate.simpleSecond,date1, date2);

            if(userId == null ){ userId = ""; }

            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            map.put("menuid",menuid);
            List<OrderRefund> list = orderRefundService.listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(startRow,pageSize,date1 ,date2, search, typeState, status, userId, sellerId);
            int totalNum = orderRefundService.countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(date1,date2, search, typeState, status, userId, sellerId);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("date1", date1);
            model.addAttribute("date2", date2);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("status",status);
            model.addAttribute("typeState",typeState);
            model.addAttribute("userId",userId);
            model.addAttribute("sellerId",sellerId);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/orderRefund/orderRefund_list";
    }


    /**
     * 管理后台确认退款申请，并将退款加入消息队列中
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-03-10 18:18
     */
    @RequestMapping("/confirmReurn")
    @ResponseBody
    public String confirmReurn(String id, HttpServletRequest request){
        try{

            OrderRefund orderRefund = orderRefundService.getById(id);
            AdminOrderRefundFunctionController.processPersistentMQ(orderRefundService, deadLetterPublishService , sellerService, orderRefund);
        }catch (Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 预览
     * @param id
     * @param model
     * @return
     * @author XGB
     * @date 2018-04-02 15:15
     */
    @RequestMapping("/toView")
    public String toView(String id, Model model, String menuid){
        try{
            OrderRefund bean = orderRefundService.getById(id);
            model.addAttribute("bean", bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("menuid",menuid);
        return "admin/orderRefund/orderRefund_view";
    }


    /**
     * 预览
     * @param orderNo
     * @param model
     * @return
     * @author XGB
     * @date 2018-04-02 15:15
     */
    @RequestMapping("/viewRefund")
    public String viewRefund(String orderNo, Model model, String menuid){
        try{
            OrderRefund bean = orderRefundService.getByOrderNo(orderNo);
            String refundResult = bean.getRefundDesc();
            System.out.println(refundResult);
            model.addAttribute("refundResult",refundResult);
            model.addAttribute("bean", bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("menuid",menuid);
        return "admin/orderRefund/orderRefund_view";
    }

    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-04-02 17:26
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            orderRefundService.deleteById(id);
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


}
