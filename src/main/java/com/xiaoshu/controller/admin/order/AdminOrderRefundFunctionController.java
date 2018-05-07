package com.xiaoshu.controller.admin.order;

import com.xgb.springrabbitmq.dto.DtoMessage;
import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.api.Set;
import com.xiaoshu.entity.*;
import com.xiaoshu.enumeration.EnumsMQName;
import com.xiaoshu.enumeration.EnumsPayType;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/orderRefund")
public class AdminOrderRefundFunctionController {

    @Resource private OrderRefundService orderRefundService;
    @Resource private OrderService orderService;
    @Resource private SellerService sellerService;

    @Autowired private DeadLetterPublishService deadLetterPublishService;

    /**
     * 自动退款申请，并将退款加入消息队列中
     * @param id 订单ID
     * @return String
     * @author XGB
     * @date 2018-04-19 11:19
     */
    @RequestMapping("/interfaceAutoReurn")
    @ResponseBody
    public String interfaceAutoReurn(Integer id){
        try{
            Order order = orderService.getById(id);
            if(order != null) {
                String orderRefundId = null;
                if(order.getTypeState() == 1 ) { //已付款
                    int cout = orderRefundService.countByOrderNo(order.getOrderNo());
                    if(cout == 0){
                        String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
                        if(order.getTransactionId() != null){
                            Double money = order.getOrderAmountMoney() * 100;
                            int orderMoney = ( new Double(money) ).intValue();
                            int refundFee = orderMoney;
                            String refundDesc = "";
                            String refundAccount = "REFUND_SOURCE_RECHARGE_FUNDS";
                            orderRefundId = UUID.randomUUID().toString();
                            OrderRefund orderRefund = new OrderRefund(orderRefundId, order.getTransactionId(), EnumsPayType.WECHAT_PAY, order.getOrderNo(), time, "", "", "",
                                    orderMoney, refundFee, "CNY", refundDesc,refundAccount, order.getOrderName(), 1, 1, order.getUserId(), order.getSellerId(),"");
                            int i = orderRefundService.save(orderRefund);
                            if(i > 0){
                                //Todo 修改订单状态为4退款中  订单状态 0未付款 1已付款 2已消费 3退款中 4已退款 -1查询所有状态
                                int resultOrder = orderService.updateOldTypeStateToOrderStateByOrderNo(orderRefund.getOrderNo(),3,1,"退款中");
                                if(resultOrder == 0) {
                                    orderRefundService.deleteById(orderRefundId);
                                }
                            }
                        }
                    }
                }
                OrderRefund orderRefund = orderRefundService.getById(orderRefundId);
                //TODO 使用消息处理退款业务
                processPersistentMQ(orderRefundService,deadLetterPublishService , sellerService,orderRefund);
            }

        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

    /**
     * 消息队列处理退款业务 10s 延迟
     * @param orderRefundService
     * @param deadLetterPublishService
     * @param sellerService
     * @param orderRefund
     */
    public static void processPersistentMQ(OrderRefundService orderRefundService, DeadLetterPublishService deadLetterPublishService , SellerService sellerService,OrderRefund orderRefund){
        try{
            if(orderRefund != null) {
                if (orderRefund.getTypeState() == 1) { //已提交
                    /** 换取商户的 绑定的服务号id*/
                    Seller seller = sellerService.findSellerByIdService(orderRefund.getSellerId());
                    String menuId = seller.getMenuId();
                    String time = ToolsDate.getStringDateToFormat(new Date(), ToolsDate.simpleSecond);
                    orderRefundService.updateTypeStateById(2,1,time,orderRefund.getId());


                    //TODO Rabbit消息队列 处理退款申请
                    String url = Set.SYSTEM_URL + "interfaceMqOrderRefund/interfaceWechatReurn";
                    String sign = "一个半小时旅游圈";
                    String params = "orderNo=" + orderRefund.getOrderNo() +"&menuId=" + menuId +  "&sign=" + ToolsASCIIChang.stringToAscii(sign);
                    DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), url, "get" ,params , null);
                    String message = DtoMessage.transformationToJson(dtoMessage);
                    deadLetterPublishService.send(EnumsMQName.DEAD_ORDER_CHECK , message);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
