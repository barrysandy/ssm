package com.xiaoshu.controller.scan;

import com.xiaoshu.controller.frontend.shop.order.OrderApiController;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.CommodityGroup;
import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.Log;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.*;
import com.xiaoshu.tools.sendMsg.EnumsTemplateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/scan")
public class ScanController {
    private final Logger log = LoggerFactory.getLogger(ScanController.class);

    @Resource private CommodityService commodityService;
    @Resource private CommodityGroupService commodityGroupService;
    @Resource private MessageRecordService messageRecordService;
    @Resource private OrderService orderService;
    @Resource private FocusedUserInfoService focusedUserInfoService;
    @Resource private PublicAccountInfoService publicAccountInfoService;
    @Resource private LogService logService;

    /**
     * 接口扫描商品状态，并更新过期商品
     * @return String
     * @author XGB
     * @date 2018-04-10 16:26
     */
    @RequestMapping("/interfaceScanUpdateTimeStatus")
    @ResponseBody
    public String interfaceScanUpdateTimeStatus(){
        Integer scanTotal = 0 ;
        Integer updateTotal = 0 ;
        try{
            int total = commodityService.countNewList(-1,1,-1);
            int pageSize = 10;
            int totalPage = ToolsPage.totalPage(total, pageSize);//总页数
            if(totalPage > 0){
                for (int i = 0;i<totalPage;i++){
                    int index = i * pageSize;
                    List<Commodity> list = commodityService.getNewListAFewData(index,pageSize,"-1",1);
                    scanTotal = scanTotal + list.size();
                    Iterator<Commodity> iterator = list.iterator();
                    while (iterator.hasNext()){
                        Commodity commodity = iterator.next();
                        if (commodity != null){
                            String date1 = commodity.getCreateDate();
                            String date2 = commodity.getInvalidDate();
                            //0 表示date在两个时间之间 1 表示date在时间之前 2表示在时间之后 3表示异常,可能date1>date2
                            int states = ToolsDate.getTheRelationshipBetweenTwoTimeNodesStringDate(new Date(),date1,date2);
                            if(states == 2){
                                //todo 更新改活动为过期活动
                                //timeStatus;//状态 0过期  1正常 2 未开始
                                int result = commodityService.updateTimeStatusById(commodity.getId(),0);
                                if(result > 0){
                                    if(commodity.getTypese() == 1){
                                        //TODO 单一商品
                                        if(commodity.getGroupNumber() != -1 || commodity.getGroupNumber2() != -1){
                                            int max = 0;//TODO 判断订单 订单付款成功判断是否已经报满  max = 0 未组团成功  max = 1 组团成功
                                            max = OrderApiController.getMax(commodity,orderService, max);
                                            if(max == 0){
                                                //TODO 进行群体组团失败退款
                                                messageRecordService.sendSingleGroupFailMassMsg(commodity.getId(), EnumsTemplateType.SINGLE_BUY_GROUP_FIAL_MASS);
                                            }
                                        }
                                    }

                                }
                                updateTotal = updateTotal + result;
                            }
                        }
                    }
                }
            }


            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("---------------------- InterfaceScanUpdateTimeStatus ----------------");
            System.out.println("---------------------- Scan_TotalPage: " + totalPage);
            System.out.println("---------------------- Scan_Total: " + scanTotal);
            log.info("InterfaceScanUpdateTimeStatus  Scan_Total: " + scanTotal);
            System.out.println("---------------------- Update has overdue total: " + updateTotal);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 扫描过期团
     * @return String
     * @author XGB
     * @date 2018-04-10 16:26
     */
    @RequestMapping("/interfaceScanGroup")
    @ResponseBody
    public String interfaceScanGroup(HttpServletResponse response){
        String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
        Integer scanTotal = 0 ;
        Integer updateTotal = 0 ;
        try{
            int total = commodityGroupService.countByStatusAndAfterTime(nowTime,0);
            int pageSize = 10;
            int totalPage = ToolsPage.totalPage(total, pageSize);//总页数
            if(totalPage > 0){
                for (int i = 0;i<totalPage;i++){
                    int index = i * pageSize;
                    List<CommodityGroup> list = commodityGroupService.getListByStatusAndAfterTime(index,pageSize,nowTime,0);
                    scanTotal = scanTotal + list.size();
                    Iterator<CommodityGroup> iterator = list.iterator();
                    while (iterator.hasNext()){
                        CommodityGroup commodityGroup = iterator.next();
                        if (commodityGroup != null){
                            if(commodityGroup.getMaxPerson() > commodityGroup.getTotalPerson() && commodityGroup.getStatus() == 0){
                                //更新
                                int result = commodityGroupService.updateStatusById(-1,commodityGroup.getId());
                                if (result > 0){
                                    //TODO 更新组团失败 并通知所有失败者 发送群发短信 并通知退款
                                    messageRecordService.sendGroupBuyFailMassMsg(commodityGroup.getCommodityId(),EnumsTemplateType.GROUP_BUY_FIAL_MASS,commodityGroup.getId());

                                    Commodity commodity = commodityService.getDateBeanById(commodityGroup.getCommodityId());
                                    if (commodity != null){
                                        String date1 = commodity.getCreateDate();
                                        String date2 = commodity.getInvalidDate();
                                        //0 表示date在两个时间之间 1 表示date在时间之前 2表示在时间之后 3表示异常,可能date1>date2
                                        int states = ToolsDate.getTheRelationshipBetweenTwoTimeNodesStringDate(new Date(),date1,date2);
                                        if(states == 2){
                                            //timeStatus;//状态 0过期  1正常 2 未开始
                                            commodityService.updateTimeStatusById(commodity.getId(),0);
                                        }

                                    }

                                }
                                updateTotal = updateTotal + result;
                            }

                        }
                    }
                }
            }


            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("---------------------- InterfaceScanGroup ----------------------------");
            System.out.println("---------------------- Scan_TotalPage: " + totalPage);
            System.out.println("---------------------- Scan_Total: " + scanTotal);
            log.info("InterfaceScanGroup  Scan_Total: " + scanTotal);
            System.out.println("---------------------- Update has overdue total: " + updateTotal);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * scan/interfaceScanUpdateUserInfo
     * 接口扫描没有头像等信息的用户补全信息
     * @return String
     * @author XGB
     * @date 2018-04-25 11:59
     */
    @RequestMapping("/interfaceScanUpdateUserInfo")
    @ResponseBody
    public String interfaceScanUpdateUserInfo(String menuId){
        Integer scanTotal = 0 ;
        Integer updateTotal = 0 ;
        try{
            //TODO 判断当前的公众号类型
            int accountType = publicAccountInfoService.getAccountTypeByParentMenuId(menuId);
            System.out.println("accountType: " + accountType);
            int total = focusedUserInfoService.countNotNameAndHeadUser(menuId);
            int pageSize = 30;
            int totalPage = ToolsPage.totalPage(total, pageSize);//总页数
            if(totalPage > 0){
                for (int i = 0;i<totalPage;i++){
                    int index = i * pageSize;
                    List<FocusedUserInfo> list = focusedUserInfoService.getListNotNameAndHeadUser(index,pageSize,menuId);
                    scanTotal = scanTotal + list.size();
                    Iterator<FocusedUserInfo> iterator = list.iterator();
                    while (iterator.hasNext()){
                        FocusedUserInfo userInfo = iterator.next();
                        if (userInfo != null){
                            if(userInfo.getUnionid() != null){
                                if(!"".equals(userInfo.getUnionid())){
                                    //TODO 服务号需要通过关联的订阅号实现用户数据同步
                                    if(accountType == 2){
                                        System.out.println("------------ [out] 1_interfaceUserInfoIntegrity accountType == 2  ------------");
                                        focusedUserInfoService.updateUserInfoByUnionidAndParentMenuId(userInfo);
                                    }
                                    //TODO 订阅号/测试号直接调用更新用户信息
                                    if(accountType == 3 || accountType == 6){
                                        System.out.println("------------ [out] 2_interfaceUserInfoIntegrity accountType == 3/6  ------------");
                                        focusedUserInfoService.updateUserInfoByWechatInterface(userInfo,menuId,userInfo.getOpenid());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("---------------------- interfaceScanUpdateUserInfo ----------------");
            System.out.println("---------------------- Scan_TotalPage: " + totalPage);
            System.out.println("---------------------- Scan_Total: " + scanTotal);
            log.info("interfaceScanUpdateUserInfo  Scan_Total: " + scanTotal);
            System.out.println("---------------------- Update User total: " + updateTotal);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        }catch(Exception e){
            e.printStackTrace();
        }
        return "总共扫描：【" + scanTotal + "】条，更新数据：【" + updateTotal +"】条。";
    }


    /**
     * scan/interfaceScanLogInvalid
     * 接口扫描清理失效日志
     * @return String
     * @author XGB
     * @date 2018-04-25 11:59
     */
    @RequestMapping("/interfaceScanLogInvalid")
    @ResponseBody
    public String interfaceScanLogInvalid(){
        Integer total = 0;
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);//过去一月
            Date dateOneMonthAgo = c.getTime();
            total = logService.deleteInvalidLog(dateOneMonthAgo);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("---------------------- interfaceScanLogInvalid ----------------");
        System.out.println("---------------------- Scan_Delete_Total: " + total);
        log.info("interfaceScanLogInvalid  Scan_Delete_Total: " + total);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        return null;
    }

}
