package com.xiaoshu.controller.admin.shop;

import com.xiaoshu.entity.CommodityPriceList;
import com.xiaoshu.service.CommodityPriceListService;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/commodityPrice")
public class AdminCommodityPriceListController {
    @Resource private CommodityPriceListService commodityPriceListService;

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-04-04 11:12
     */
    @RequestMapping("/toEdit")
    public String toEdit( Model model,String id,String menuId,Integer commodityId){
        try{
            CommodityPriceList bean = new CommodityPriceList("", 0, "", 0.00, "", 1, 1, "", "", "", "", "","","");
            if(id == null){

            }else {
                bean = commodityPriceListService.getById(id);
            }
            model.addAttribute("bean",bean);
            model.addAttribute("menuId",menuId);
            model.addAttribute("commodityId",commodityId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/commodity/commodity_toEditPrice";
    }



    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-04-04 11:12
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(CommodityPriceList bean){
        try{
            if(bean != null){
                if("".equals(bean.getId())) {
                    bean.setId(UUID.randomUUID().toString());
                    if(bean.getCreateTime() != null){
                        if(!"".equals(bean.getCreateTime())){
                            Calendar calendar = ToolsDate.getCalendar(ToolsDate.simpleDay,bean.getCreateTime());
                            int year = calendar.get(Calendar.YEAR) ;
                            int month = calendar.get(Calendar.MONTH) + 1 ;
                            int day = calendar.get(Calendar.DAY_OF_MONTH) ;
                            //4月7日
                            String descM = new StringBuffer().append(month).append("月").append(day).append("日").toString();
                            //2018-04-07
                            StringBuffer sb = new StringBuffer();
                            sb.append(year);
                            sb.append("-");
                            if(month<10){
                                sb.append("0");
                            }
                            sb.append(month);
                            sb.append("-");
                            if(day<10){
                                sb.append("0");
                            }
                            sb.append(day);
                            String priceTime = sb.toString();
                            bean.setPriceTime(priceTime);
                            bean.setDescM(descM);
                        }
                    }
                    commodityPriceListService.save(bean);
                }else {
                    if(bean.getCreateTime() != null){
                        if(!"".equals(bean.getCreateTime())){
                            Calendar calendar = ToolsDate.getCalendar(ToolsDate.simpleDay,bean.getCreateTime());
                            int year = calendar.get(Calendar.YEAR) ;
                            int month = calendar.get(Calendar.MONTH) + 1 ;
                            int day = calendar.get(Calendar.DAY_OF_MONTH) ;
                            //4月7日
                            String descM = new StringBuffer().append(month).append("月").append(day).append("日").toString();
                            //2018-04-07
                            StringBuffer sb = new StringBuffer();
                            sb.append(year);
                            sb.append("-");
                            if(month<10){
                                sb.append("0");
                            }
                            sb.append(month);
                            sb.append("-");
                            if(day<10){
                                sb.append("0");
                            }
                            sb.append(day);
                            String priceTime = sb.toString();
                            bean.setPriceTime(priceTime);
                            bean.setDescM(descM);
                        }
                    }
                    commodityPriceListService.updateAll(bean);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-04-04 11:12
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            commodityPriceListService.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }

    /**
     * 更新地址
     * @author XGB
     * @date 2018-04-09 17:34
     */
    @RequestMapping("/updateAddress")
    @ResponseBody
    public String updateAddress(String address, String descM,Integer commodityId){
        try{
            commodityPriceListService.updateAddressByDescM(address,descM,commodityId);
        }catch(Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 更新联系人
     * @author XGB
     * @date 2018-04-09 17:34
     */
    @RequestMapping("/updateContacts")
    @ResponseBody
    public String updateContacts(String contacts, String descM,Integer commodityId){
        try{
            commodityPriceListService.updateContactsByDescM(contacts,descM,commodityId);
        }catch(Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }



    /**
     * 更新联系人电话
     * @author XGB
     * @date 2018-04-09 17:34
     */
    @RequestMapping("/updateContactsPhone")
    @ResponseBody
    public String updateContactsPhone(String contactsPhone, String descM,Integer commodityId){
        try{
            commodityPriceListService.updateContactsPhoneByDescM(contactsPhone,descM,commodityId);
        }catch(Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }



}
