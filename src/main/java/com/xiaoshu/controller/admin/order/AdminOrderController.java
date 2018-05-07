package com.xiaoshu.controller.admin.order;

import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.Order;
import com.xiaoshu.service.FocusedUserInfoService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class AdminOrderController {

    @Resource private OrderService orderService;
    @Resource private FocusedUserInfoService focusedUserInfoService;

    /**
     * 分页查询
     * @return view
     * @author XGB
     * @date 2018-03-10 13:54
     */
    @RequestMapping("/list")
    public String list(String menuid) {
        return "redirect:/order/listAllParam?fullListSize=0&list=&pageSize=10&pageNumber=1&pageCount=0&searchId=&sortCriterion=&search=&menuid=0" + menuid + "&typeState=-1&status=0&date1=0&date2=0&sellerId=-1&userId=-1&commodityId=-1";
    }

    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-03-10 13:54
     */
    @RequestMapping("/listAllParam")
    public String listAllParam(Pager pager, Model model, String search, String menuid,String date1,String date2,
                       int typeState,int status,int userId,int sellerId,int commodityId) {
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

            if(userId == 0){ userId = -1;}
            if(sellerId == 0){ sellerId = -1;}

            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            map.put("menuid",menuid);
            List<Order> listOrder = orderService.listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(date1,date2,startRow, pageSize, search, typeState, status,userId,sellerId,commodityId);
            int totalNum = orderService.countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(date1,date2,search, typeState, status,userId,sellerId,commodityId);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(listOrder);
            model.addAttribute("date1", date1);
            model.addAttribute("date2", date2);
            model.addAttribute("typeState", typeState);
            model.addAttribute("status", status);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("userId", userId);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", listOrder);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
            model.addAttribute("commodityId",commodityId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/order/order_list";
    }

    /**
     * 导出到excel文档
     * @author XGB
     * @date 2018-04-3 9:32
     */
    @RequestMapping("/listExcel")
    public String listExcel(int pageNo, int pageSize, String search, String date1, String date2, int typeState, int status, int userId, int sellerId,int commodityId, HttpServletResponse response) {
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

            if(userId == 0){ userId = -1;}
            if(sellerId == 0){ sellerId = -1;}

            Map map = new HashMap(8);
            int startRow = (pageNo - 1) * pageSize;
            map.put("startRow", startRow);
            map.put("pageSize", pageSize);
            map.put("search", search);
            List<Order> list = orderService.listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(date1,date2,startRow, pageSize, search, typeState, status,userId,sellerId,commodityId);
            createExcle(list, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 导出全部到excel文档
     * @author XGB
     * @date 2018-04-3 9:32
     */
    @RequestMapping("/listExcelAll")
    public String listExcelAll(String search, String date1, String date2, int typeState, int status, int userId, int sellerId,int commodityId, HttpServletResponse response) {
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

            if(userId == 0){ userId = -1;}
            if(sellerId == 0){ sellerId = -1;}

            Map map = new HashMap(8);
            int startRow = 0;
            int pageSize = orderService.countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(date1, date2, search, typeState, status, userId, sellerId, commodityId);
            map.put("startRow", 0);
            map.put("pageSize", pageSize);
            map.put("search", search);
            List<Order> list = orderService.listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(date1,date2,startRow, pageSize, search, typeState, status,userId,sellerId,commodityId);
            createExcle(list, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
    public String toView(String id, Model model, String menuid){
        try{
            if(id != null){
                Order bean = orderService.getById(Integer.parseInt(id));
                if(bean != null){
                    if(bean.getUserId() != null){
                        if(!"".equals(bean.getUserId())){
                            FocusedUserInfo userInfo = focusedUserInfoService.selectByPrimaryKey(bean.getUserId() + "");
                            bean.setUser(userInfo);
                        }

                    }
                }
                model.addAttribute("bean", bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("menuid",menuid);
        return "admin/order/order_view";
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
    public String del(int id,int isDel){
        try{
            if(isDel == 0){
                Order order = orderService.getById(id);
//                order.setStatus(-1);
                orderService.updateOldStateToOrderStateByOrderNo(order.getOrderNo(),-1,order.getStatus());
            }else if(isDel == 1){
                orderService.deleteById(id);
            }else if(isDel == -1){
                Order order = orderService.getById(id);
//                order.setStatus(0);
                orderService.updateOldStateToOrderStateByOrderNo(order.getOrderNo(),0,order.getStatus());
            }
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }

    /**
     * 导出excel
     * @param list 导出的数据
     * @param response HttpServletResponse
     */
    private static void createExcle(List<Order> list, HttpServletResponse response) {
        if(list != null){
            if(list.size() > 0) {
                // 第一步，创建一个webbook，对应一个Excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                HSSFSheet sheet = wb.createSheet("order");
                sheet.setColumnWidth(0,1500);
                sheet.setColumnWidth(1,5000);
                sheet.setColumnWidth(2,8000);
                sheet.setColumnWidth(3,3500);
                sheet.setColumnWidth(4,3000);
                sheet.setColumnWidth(5,3200);
                sheet.setColumnWidth(6,5000);
                sheet.setColumnWidth(7,3000);
                sheet.setColumnWidth(8,7500);
                sheet.setColumnWidth(9,5000);
                sheet.setColumnWidth(10,9200);
                sheet.setColumnWidth(11,2500);
                sheet.setColumnWidth(12,4000);
                sheet.setColumnWidth(13,2500);
                HSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setWrapText(true);

                // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                HSSFRow row = sheet.createRow((short) 0);
                // 第四步，创建单元格，并设置值表头 设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                style.setWrapText(true);

                HSSFCell cell = row.createCell((short) 0);
                String[] title = {"编号","订单号","订单名称","订单联系人","联系人电话","使用时间","数量描述","订单金额(￥)","支付订单号","创建时间","用户ID","成人票","儿童票","总票数--成人/儿童","总金额(￥)"};
                for(int i =0;i<title.length;i++) {
                    cell.setCellValue(title[i]);
                    cell.setCellStyle(style);
                    cell = row.createCell((short) i + 1);
                }
                // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
                Double total = 0.00;
                synchronized (total){
                    int number = 0;
                    int number2 = 0;
                    String totalPerson = "";
                    for (int i = 0; i < list.size(); i++){
                        row = sheet.createRow(i + 1);
                        Order bean = list.get(i);
                        // 第四步，创建单元格，并设置值
                        String rowNumber = String.valueOf(i + 1);
                        String[] values = {rowNumber,bean.getOrderNo(),bean.getOrderName(),bean.getUserName(),bean.getUserPhone(),
                                bean.getUserUseTime(),bean.getNumberDescM(),String.valueOf(bean.getOrderAmountMoney()),bean.getTransactionId(),
                                bean.getCreateTime(),bean.getUserId(),String.valueOf(bean.getNumber()),String.valueOf(bean.getNumber2()),totalPerson,String.valueOf(total)};
                        total = total + bean.getOrderAmountMoney();
                        number = number + bean.getNumber();
                        number2 = number2 + bean.getNumber2();
                        totalPerson = number + " 人 / " + number2 + " 人";
                        for (int j = 0; j < values.length ; j++) {
                            if(j < values.length - 2 ){
                                row.createCell((short) j).setCellValue(values[j]);
                                row.setRowStyle(cellStyle);
                            }else if(j == values.length - 2 ) {
                                if(i +1  == list.size()){
                                    row.createCell((short) j).setCellValue(totalPerson);
                                    row.setRowStyle(cellStyle);
                                }
                            }else if(j == values.length - 1 ) {
                                if(i +1  == list.size()){
                                    row.createCell((short) j).setCellValue(total);
                                    row.setRowStyle(cellStyle);
                                }
                            }
                        }

                    }
                }
                row.setHeight((short) 300);
                OutputStream out = null;
                try {
                    out = response.getOutputStream();
                    String fileName = "订单表.xls";// 文件名
                    response.setContentType("application/x-msdownload");
                    response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                    wb.write(out);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
