package com.xiaoshu.controller.admin.middleKey;

import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.MessageTemple;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.MessageRecordService;
import com.xiaoshu.service.MessageTempleService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.sendMsg.MsgTemplate;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/messageTemple")
public class MessageTempleController {

    private static Logger log = LoggerFactory.getLogger(MessageTempleController.class);

    @Resource private MessageTempleService messageTempleService;
    @Resource private CommodityService commodityService;


    /**
     * 分页查询
     * @return view
     * @author XGB
     * @date 2018-04-08 13:15
     */
    @RequestMapping("/list")
    public String list(Pager pager, Model model, String search, String menuid, Integer status,Integer commodityId ) {
        if(pager == null){ pager = new Pager(); }
        if(status == null || status == 0 ){ status = -1; }
        if(commodityId == null || commodityId == 0 ){ commodityId = -1; }
        try{
            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            map.put("menuid",menuid);
            List<MessageTemple> list = messageTempleService.listByKey(startRow,pageSize,search, status,commodityId);
            int totalNum = messageTempleService.countByKey(search,status,commodityId);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("status", status);
            model.addAttribute("commodityId", commodityId);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageTemple_list";
    }


    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-04-08 14:15
     */
    @RequestMapping("/toEdit")
    public String toEdit( Model model,String id, Integer commodityId){
        try{
            MessageTemple bean = new MessageTemple("", commodityId, "", 0, "", "", "", "", 0,"");
            if(StringUtils.isNotBlank(id)){
                bean = messageTempleService.getById(id);
            }
            model.addAttribute("bean", bean);
            model.addAttribute("commodityId",commodityId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageTemple_edit";
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-04-08 14:15
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(MessageTemple bean){
        try{
            if(bean != null){
                String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
                String id = bean.getId();
                if(StringUtils.isNotBlank(id)){
                    bean.setUpdateTime(time);
                    messageTempleService.updateAll(bean);
                }else{
                    UUID u = UUID.randomUUID();
                    bean.setId(u.toString());
                    bean.setCreateTime(time);
                    messageTempleService.save(bean);
                }
            }
        }catch(Exception e) {
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 一键创建短信模板
     * @param id 商品id
     * @return String
     * @author XGB
     * @date 2018-04-19 14:51
     */
    @RequestMapping("/createAll")
    @ResponseBody
    public String createAll(Integer id,String sign){
        String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
        try{
            Commodity bean = commodityService.findCommodityByIdService(id);
            if(bean != null){
                if(bean.getTypese() == 1){
                    //单一商品
                    MessageTemple bean1 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【单一商品】购买",
                            0, "SINGLE_BUY", nowTime, null, "一键创建", 1, sign);

                    MessageTemple bean2 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【单一商品】购买成团成功群发短信",
                            1, "SINGLE_BUY_GROUP_SUC_MASS", nowTime, null, "一键创建", 1, sign);

                    MessageTemple bean3 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【单一商品】购买成团时组合购买短信",
                            2, "SINGLE_BUY_GROUP_SUC_TOBUY", nowTime, null, "一键创建", 1, sign);

                    MessageTemple bean4 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【单一商品】购买成团超时失败群发短信",
                            3, "SINGLE_BUY_GROUP_FIAL_MASS", nowTime, null, "一键创建", 1, sign);
                    messageTempleService.save(bean1);
                    messageTempleService.save(bean2);
                    messageTempleService.save(bean3);
                    messageTempleService.save(bean4);

                }
                else if(bean.getTypese() == 2){
                    //组团商品
                    MessageTemple bean1 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【组团商品】购买",
                            4, "GROUP_BUY", nowTime, null, "一键创建", 1, sign);

                    MessageTemple bean2 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【组团商品】购买成团成功群发短信",
                            5, "GROUP_BUY_SUC_MASS", nowTime, null, "一键创建", 1, sign);

                    MessageTemple bean3 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【组团商品】购买成团时组合购买短信",
                            6, "GROUP_BUY_SUC_TOBUY", nowTime, null, "一键创建", 1, sign);

                    MessageTemple bean4 = new MessageTemple(UUID.randomUUID().toString(), id, id + "【组团商品】购买成团超时失败群发短信",
                            7, "GROUP_BUY_FIAL_MASS", nowTime, null, "一键创建", 1, sign);
                    messageTempleService.save(bean1);
                    messageTempleService.save(bean2);
                    messageTempleService.save(bean3);
                    messageTempleService.save(bean4);
                }
                if(bean.getTypese() == 1 || bean.getTypese() == 2 ) {
                    MessageTemple beanRefund = new MessageTemple(UUID.randomUUID().toString(), id, id + "【统一商品】统一退款模板",
                            8, "REFUND", nowTime, null, "一键创建", 1, sign);
                    messageTempleService.save(beanRefund);
                }
            }
        }catch(Exception e) {
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }



    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-04-08 14:15
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            int i = messageTempleService.deleteById(id);
            System.out.println("------------ [log.info System Message] : Delete MessageRecord ID = " + id + "  RESP is " + i +" ------------");
        }catch(Exception e){
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
     * @date 2018-04-08 14:35
     */
    @RequestMapping("/toView")
    public String toView(String id, Model model){
        try{
            if(id != null){
                MessageTemple bean = messageTempleService.getById(id);
                model.addAttribute("bean", bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageTemple_view";
    }


    /**
     * 获取短信模板集合
     * @date 2018-04-08 13:15
     */
    @RequestMapping("/getTemplates")
    public String getTemplates(HttpServletRequest request) {
        try{
            List<String> list = MsgTemplate.getListTemplate();
            request.setAttribute("list",list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageTemple_listTemplate";
    }


}
