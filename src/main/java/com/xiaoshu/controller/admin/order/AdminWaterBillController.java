package com.xiaoshu.controller.admin.order;

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
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.vo.WeChatPayNotify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/waterBill")
public class AdminWaterBillController {

    private static Logger log = LoggerFactory.getLogger(AdminWaterBillController.class);

    @Resource private OrderService orderService;
    @Resource private WaterBillService waterBillService;
    @Resource private FocusedUserInfoService focusedUserInfoService;

    /**
     * 分页查询
     * @return view
     * @author XGB
     * @date 2018-03-15 10:07
     */
    @RequestMapping("/list")
    public String list(String menuid) {
        return "redirect:/waterBill/listAllParam?fullListSize=0&list=&pageSize=10&pageNumber=1&pageCount=0&searchId=&sortCriterion=&search=&menuid=" + menuid + "date1=0&date2=0&status=-1";
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
    public String listAllParam(Pager pager, Model model, String search, String menuid,String date1,String date2,int status) {
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


            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            map.put("menuid",menuid);
            List<WaterBill> list = waterBillService.getListByKey(startRow,pageSize,date1,date2,search,status);
            int totalNum = waterBillService.getTotalListByKey(date1,date2,search,status);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("date1", date1);
            model.addAttribute("date2", date2);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("status",status);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/waterBill/waterBill_list";
    }

    /**
     * 预览
     * @param id
     * @param model
     * @return
     * @author XGB
     * @date 2018-03-10 18:31
     */
    @RequestMapping("/toView")
    public String toView(int id, Model model, String menuid){
        try{
            WaterBill bean = waterBillService.getById(id);
            model.addAttribute("bean", bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("menuid",menuid);
        return "admin/waterBill/waterBill_view";
    }

    /**
     * 预览
     * @param orderNo
     * @param model
     * @return
     * @author XGB
     * @date 2018-03-10 18:31
     */
    @RequestMapping("/viewWaterWall")
    public String viewWaterWall(String orderNo, Model model, String menuid){
        try{
            WaterBill bean = waterBillService.getByOrderNo(orderNo);
            model.addAttribute("bean", bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("menuid",menuid);
        return "admin/waterBill/waterBill_view";
    }

    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-03-10 18:18
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(int id){
        try{
            waterBillService.updateStateById(id,0);
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 检查账单/微信支付方式
     * @author XGB
     * @date 2018-03-22 14:33
     */
    @RequestMapping("/interfaceCheckWaterBill")
    @ResponseBody
    public String interfaceCheckWaterBill(String json){
        StringBuffer CODE = new StringBuffer("fail");
        String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
        try{
            if(json != null){
                if(!"".equals(json)){
                    json = ToolsASCIIChang.asciiToString(json);
                    json = ToolsString.getStrRemoveBracket(json);
                    WeChatPayNotify weChatPayNotify = JSONUtils.toBean(json,WeChatPayNotify.class);
                    if(weChatPayNotify != null){
                        String orderNo = weChatPayNotify.getOut_trade_no();
                        String transactionId = weChatPayNotify.getTransaction_id();
                        /** 更新订单的微信订单号*/
                        orderService.updateTransactionIdByOrderOn(orderNo,transactionId);
                        /** 更新订单的支付类型 */
                        orderService.updatePayTypeByOrderOn(orderNo, EnumsPayType.WECHAT_PAY);
                        /** 保存流水*/
                        int existByOrderNo = waterBillService.existByOrderNo(weChatPayNotify.getOut_trade_no());
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
                                        waterBillService.updateAllById(waterBill);
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
                                        waterBillService.updateAllById(waterBill);
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
                                waterBillService.updateAllById(waterBill);
                                return EnumsMQAck.ACK_OK;
                            }

                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return EnumsMQAck.ACK_FAIL;
            //CODE.append( " Exception :" + e.getMessage());
            //return CODE.toString();
        }
        log.info("------------ [log.info System Message] Enter interfaceCheckWaterBill : json: " + json +" end!! ------------");
        return EnumsMQAck.ACK_OK;
    }

}
