package com.xiaoshu.controller.rabbitmqHandle;

import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.WaterBill;
import com.xiaoshu.enumeration.EnumsMQAck;
import com.xiaoshu.enumeration.EnumsPayType;
import com.xiaoshu.service.FocusedUserInfoService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.service.WaterBillService;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.single.NackMessageMap;
import com.xiaoshu.vo.WeChatPayNotify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/interfaceMqWaterBill")
public class MqWaterBillController {

    private static Logger log = LoggerFactory.getLogger(MqWaterBillController.class);

    @Resource private OrderService orderService;
    @Resource private WaterBillService waterBillService;
    @Resource private FocusedUserInfoService focusedUserInfoService;

    /**
     * 检查账单/微信支付方式
     * @author XGB
     * @date 2018-03-22 14:33
     */
    @RequestMapping("/checkWaterBill")
    @ResponseBody
    public String checkWaterBill(String json){
        StringBuffer CODE = new StringBuffer("fail");
        String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
        try{
            if(json != null){
                if(!"".equals(json)){
                    json = ToolsASCIIChang.asciiToString(json);
                    json = ToolsString.getStrRemoveBracket(json);
                    WeChatPayNotify weChatPayNotify = JSONUtils.toBean(json,WeChatPayNotify.class);
                    if(weChatPayNotify != null){
                        Map<String,String> messageMap = NackMessageMap.getInstance().getMap();
                        String orderNo = weChatPayNotify.getOut_trade_no();
                        String transactionId = weChatPayNotify.getTransaction_id();
                        /** 更新订单的微信订单号*/
                        int updateTransaction = orderService.updateTransactionIdByOrderOn(orderNo,transactionId);
                        /** 更新订单的支付类型 */
                        int updatePayTypeByOrder = orderService.updatePayTypeByOrderOn(orderNo, EnumsPayType.WECHAT_PAY);
                        System.out.println("///////////////////////////////////////////");
                        System.out.println("更新订单的微信订单号:updateTransaction: " + updateTransaction + " updatePayTypeByOrder: " + updatePayTypeByOrder);
                        System.out.println("///////////////////////////////////////////");
                        /** 保存流水*/
                        int existByOrderNo = waterBillService.existByOrderNo(weChatPayNotify.getOut_trade_no());
                        System.out.println("///////////////////////////////////////////");
                        System.out.println("保存流水:existByOrderNo :" + existByOrderNo);
                        System.out.println("///////////////////////////////////////////");
                        if(existByOrderNo == 0) {
                            String userId = null;
                            if(weChatPayNotify.getOpenid() != null){
                                userId = focusedUserInfoService.getIdByOpenid(weChatPayNotify.getOpenid());
                                if(userId == null || "".equals(userId)){
                                    userId = weChatPayNotify.getOpenid();
                                }
                            }
                            WaterBill waterBill = new WaterBill(0, "[" + time + "]Save payNotify :JSON is ###" + json + " ###", weChatPayNotify.getOut_trade_no(), new Date(), null, Double.parseDouble(weChatPayNotify.getTotal_fee()) / 100 , 1, weChatPayNotify.getOpenid(),userId);
                            int i = waterBillService.save(waterBill);
                            log.info("------------ [LOG[" + time + "] WeChatPayController interfacePayNotify] save WaterBill result : " + i + " ------------");
                            messageMap.remove("PayNotify" + waterBill.getOrderNo());
                            System.out.println("///////////////////////////////////////////");
                            System.out.println("保存流水:save:" + i);
                            System.out.println("///////////////////////////////////////////");
                        }
                        if(orderNo != null ){
                            int exitWaterBill = waterBillService.existByOrderNo(orderNo);
                            /** 账单存在 */
                            if (exitWaterBill > 0) {
                                WaterBill waterBill =  waterBillService.getByOrderNo(orderNo);
                                if(waterBill != null){
                                    /** 比较金额 */
                                    Double waterMoney = waterBill.getMoney() *100;
                                    Double notyfyMoney = Double.parseDouble(weChatPayNotify.getTotal_fee());
                                    //金额对不上
                                    if(!waterMoney.equals(notyfyMoney)){
                                        CODE.append("**Total_fee error User actual payment :[" + waterBill.getMoney()*100 + "-->" + weChatPayNotify.getTotal_fee()+"]**");
                                        waterBill.setStatus(2);
                                        waterBill.setRemarks(CODE + " : " + json);
                                        int i = waterBillService.updateAllById(waterBill);
                                        messageMap.remove("PayNotify" + waterBill.getOrderNo());
                                        System.out.println("///////////////////////////////////////////");
                                        System.out.println("更新流水金额对不上:updateAllById" + i);
                                        System.out.println("///////////////////////////////////////////");
                                        return EnumsMQAck.ACK_OK;
                                    }else {
                                        CODE.setLength(0);
                                        CODE.append("Success ");
                                        //按照 orderNo 查询订单状态（typeState 订单状态 0未付款 1已付款 2已消费  -1查询所有状态
                                        int typeState = orderService.getTypeStateByOrderNo(orderNo);
                                        if(typeState == 0){
                                            int updateResult = orderService.updateOldTypeStateToOrderStateByOrderNo( orderNo, 1, 0,"");
                                            log.info("------------ [System Message] : UpdateOrder typeState updateResult： " + updateResult + " [ 1 is Success ] time ：" + time + " ------------");
                                            CODE.append("UpdateOrder typeState updateResult： " + updateResult + " [ 1 is Success ] time ：" + time + " ------------");
                                        }
                                        waterBill.setStatus(1);
                                        waterBill.setRemarks("Message queuing Check water : CODE:" + CODE + " JSON:" + json);
                                        int i = waterBillService.updateAllById(waterBill);
                                        messageMap.remove("PayNotify" + waterBill.getOrderNo());
                                        System.out.println("///////////////////////////////////////////");
                                        System.out.println("更新流水 金额正确:updateAllById" + i);
                                        System.out.println("///////////////////////////////////////////");
                                        return EnumsMQAck.ACK_OK;
                                    }
                                }
                            }else {
                                /** 账单不存在 */
                                FocusedUserInfo user =  focusedUserInfoService.selectByOpenid(weChatPayNotify.getOpenid());
                                WaterBill waterBill = new WaterBill(0, "Message queuing add water:" + json, weChatPayNotify.getOut_trade_no(), new Date(), new Date(), Double.parseDouble(weChatPayNotify.getTotal_fee()) / 100, 1, weChatPayNotify.getOpenid(), user.getId());
                                int saveResult = waterBillService.save(waterBill);
                                if(saveResult > 0){
                                    CODE.setLength(0);
                                    CODE.append("Success **UpdateOrder typeState saveResult： " + saveResult +"**");
                                }else {
                                    CODE.append("**save WaterBill Result： " + saveResult +"**");
                                }
                                log.info("------------ [System Message] : UpdateOrder typeState saveResult： " + saveResult + " [ 1 is Success ] time ：" + time + " ------------");
                                waterBill.setRemarks("Message queuing Check water : CODE:" + CODE + " JSON:" + json);
                                int i = waterBillService.updateAllById(waterBill);
                                messageMap.remove("PayNotify" + waterBill.getOrderNo());
                                System.out.println("///////////////////////////////////////////");
                                System.out.println("更新流水 账单不存在:updateAllById" + i);
                                System.out.println("///////////////////////////////////////////");
                                return EnumsMQAck.ACK_OK;
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("///////////////////////////////////////////");
            System.out.println("检查账单消息异常：" + EnumsMQAck.ACK_FAIL);
            System.out.println("///////////////////////////////////////////");
            return EnumsMQAck.ACK_FAIL;
        }
        log.info("------------ [log.info System Message] Enter interfaceCheckWaterBill : json: " + json +" end!! ------------");
        return EnumsMQAck.ACK_OK;
    }



    /**
     * 检查账单/微信支付方式（非消息队列方式检查，此为备用方案）
     * @author XGB
     * @date 2018-05-18 11:21
     */
    @RequestMapping("/checkWaterBillNotMQ")
    @ResponseBody
    public String checkWaterBillNotMQ(String json){
        StringBuffer CODE = new StringBuffer("fail");
        String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
        try{
            if(json != null){
                if(!"".equals(json)){
                    json = ToolsASCIIChang.asciiToString(json);
                    json = ToolsString.getStrRemoveBracket(json);
                    WeChatPayNotify weChatPayNotify = JSONUtils.toBean(json,WeChatPayNotify.class);
                    if(weChatPayNotify != null){
                        Map<String,String> messageMap = NackMessageMap.getInstance().getMap();
                        String orderNo = weChatPayNotify.getOut_trade_no();
                        String transactionId = weChatPayNotify.getTransaction_id();
                        /** 更新订单的微信订单号*/
                        int updateTransaction = orderService.updateTransactionIdByOrderOn(orderNo,transactionId);
                        /** 更新订单的支付类型 */
                        int updatePayTypeByOrder = orderService.updatePayTypeByOrderOn(orderNo, EnumsPayType.WECHAT_PAY);
                        System.out.println("///////////////////////////////////////////");
                        System.out.println("更新订单的微信订单号:updateTransaction: " + updateTransaction + " updatePayTypeByOrder: " + updatePayTypeByOrder);
                        System.out.println("///////////////////////////////////////////");
                        /** 保存流水*/
                        int existByOrderNo = waterBillService.existByOrderNo(weChatPayNotify.getOut_trade_no());
                        System.out.println("///////////////////////////////////////////");
                        System.out.println("保存流水:existByOrderNo :" + existByOrderNo);
                        System.out.println("///////////////////////////////////////////");
                        if(existByOrderNo == 0) {
                            String userId = null;
                            if(weChatPayNotify.getOpenid() != null){
                                userId = focusedUserInfoService.getIdByOpenid(weChatPayNotify.getOpenid());
                                if(userId == null || "".equals(userId)){
                                    userId = weChatPayNotify.getOpenid();
                                }
                            }
                            WaterBill waterBill = new WaterBill(0, "[" + time + "]Save payNotify :JSON is ###" + json + " ###", weChatPayNotify.getOut_trade_no(), new Date(), null, Double.parseDouble(weChatPayNotify.getTotal_fee()) / 100 , 1, weChatPayNotify.getOpenid(),userId);
                            int i = waterBillService.save(waterBill);
                            log.info("------------ [LOG[" + time + "] WeChatPayController interfacePayNotify] save WaterBill result : " + i + " ------------");
                            messageMap.remove("PayNotify" + waterBill.getOrderNo());
                            System.out.println("///////////////////////////////////////////");
                            System.out.println("保存流水:save:" + i);
                            System.out.println("///////////////////////////////////////////");
                        }
                        if(orderNo != null ){
                            int exitWaterBill = waterBillService.existByOrderNo(orderNo);
                            /** 账单存在 */
                            if (exitWaterBill > 0) {
                                WaterBill waterBill =  waterBillService.getByOrderNo(orderNo);
                                if(waterBill != null){
                                    /** 比较金额 */
                                    Double waterMoney = waterBill.getMoney() *100;
                                    Double notyfyMoney = Double.parseDouble(weChatPayNotify.getTotal_fee());
                                    //金额对不上
                                    if(!waterMoney.equals(notyfyMoney)){
                                        CODE.append("**Total_fee error User actual payment :[" + waterBill.getMoney()*100 + "-->" + weChatPayNotify.getTotal_fee()+"]**");
                                        waterBill.setStatus(2);
                                        waterBill.setRemarks(CODE + " : " + json);
                                        int i = waterBillService.updateAllById(waterBill);
                                        messageMap.remove("PayNotify" + waterBill.getOrderNo());
                                        System.out.println("///////////////////////////////////////////");
                                        System.out.println("更新流水金额对不上:updateAllById" + i);
                                        System.out.println("///////////////////////////////////////////");
                                        return EnumsMQAck.ACK_OK;
                                    }else {
                                        CODE.setLength(0);
                                        CODE.append("Success ");
                                        //按照 orderNo 查询订单状态（typeState 订单状态 0未付款 1已付款 2已消费  -1查询所有状态
                                        int typeState = orderService.getTypeStateByOrderNo(orderNo);
                                        if(typeState == 0){
                                            int updateResult = orderService.updateOldTypeStateToOrderStateByOrderNo( orderNo, 1, 0,"");
                                            log.info("------------ [System Message] : UpdateOrder typeState updateResult： " + updateResult + " [ 1 is Success ] time ：" + time + " ------------");
                                            CODE.append("UpdateOrder typeState updateResult： " + updateResult + " [ 1 is Success ] time ：" + time + " ------------");
                                        }
                                        waterBill.setStatus(1);
                                        waterBill.setRemarks("Message queuing Check water : CODE:" + CODE + " JSON:" + json);
                                        int i = waterBillService.updateAllById(waterBill);
                                        messageMap.remove("PayNotify" + waterBill.getOrderNo());
                                        System.out.println("///////////////////////////////////////////");
                                        System.out.println("更新流水 金额正确:updateAllById" + i);
                                        System.out.println("///////////////////////////////////////////");
                                        return EnumsMQAck.ACK_OK;
                                    }
                                }
                            }else {
                                /** 账单不存在 */
                                FocusedUserInfo user =  focusedUserInfoService.selectByOpenid(weChatPayNotify.getOpenid());
                                WaterBill waterBill = new WaterBill(0, "Message queuing add water:" + json, weChatPayNotify.getOut_trade_no(), new Date(), new Date(), Double.parseDouble(weChatPayNotify.getTotal_fee()) / 100, 1, weChatPayNotify.getOpenid(), user.getId());
                                int saveResult = waterBillService.save(waterBill);
                                if(saveResult > 0){
                                    CODE.setLength(0);
                                    CODE.append("Success **UpdateOrder typeState saveResult： " + saveResult +"**");
                                }else {
                                    CODE.append("**save WaterBill Result： " + saveResult +"**");
                                }
                                log.info("------------ [System Message] : UpdateOrder typeState saveResult： " + saveResult + " [ 1 is Success ] time ：" + time + " ------------");
                                waterBill.setRemarks("Message queuing Check water : CODE:" + CODE + " JSON:" + json);
                                int i = waterBillService.updateAllById(waterBill);
                                messageMap.remove("PayNotify" + waterBill.getOrderNo());
                                System.out.println("///////////////////////////////////////////");
                                System.out.println("更新流水 账单不存在:updateAllById" + i);
                                System.out.println("///////////////////////////////////////////");
                                return EnumsMQAck.ACK_OK;
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("///////////////////////////////////////////");
            System.out.println("检查账单消息异常：" + EnumsMQAck.ACK_FAIL);
            System.out.println("///////////////////////////////////////////");
            return EnumsMQAck.ACK_FAIL;
        }
        log.info("------------ [log.info System Message] Enter interfaceCheckWaterBill : json: " + json +" end!! ------------");
        return EnumsMQAck.ACK_OK;
    }

}
