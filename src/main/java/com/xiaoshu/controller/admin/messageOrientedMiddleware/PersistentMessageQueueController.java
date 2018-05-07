package com.xiaoshu.controller.admin.messageOrientedMiddleware;

import com.xiaoshu.entity.PersistentMessageQueue;
import com.xiaoshu.service.MessageRecordService;
import com.xiaoshu.service.PersistentMessageQueueService;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/persistentMessageQueue")
public class PersistentMessageQueueController {
    @Resource private MessageRecordService messageRecordService;
    @Resource private PersistentMessageQueueService persistentMessageQueueService;

//    /**
//     * 发送短信接口
//     * @return String
//     * @author XGB
//     * @date 2018-03-22 11:26
//     */
//    @RequestMapping("/sendMsg")
//    @ResponseBody
//    public String sendMsg(String id){
//        try {
//            PersistentMessageQueue bean = persistentMessageQueueService.getById(id);
//            if(bean != null ){
//                MessageRecord messageRecord = messageRecordService.getById(bean.getDescM());
//                System.out.println("调用发送短信API");
//                //调用发送短信API
////                String mobile = messageRecord.getMobile();
////                String content = messageRecord.getContent();
////                String template = Template.ONE_POINT_FIVE_HOURS_CODE;
////                String[] param = {content,messageRecord.getSign()};
////                HashMap<String, Object> map = IndustrySMS.link(mobile, template, "",param);
////                String status = (String) map.get("status");
////                String msg = (String) map.get("msg");
////                if(!"0".equals(status)){//发送成功
////                    messageRecord.setResponseStatus(status + "," + msg);
////                }else {
////                    messageRecord.setResponseStatus(status + "," + msg);
////                }
////                messageRecordService.updateAll(messageRecord);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }




    /**
     * 分页查询
     * @return view
     * @author XGB
     * @date 2018-03-26 10:50
     */
    @RequestMapping("/list")
    public String list(String menuid) {
        return "redirect:/persistentMessageQueue/listAllParam?fullListSize=0&list=&pageSize=10&pageNumber=1&pageCount=0&searchId=&sortCriterion=&search=&menuid=" + menuid + "&rank=-1&status=-1&msgFrom=";
    }

    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-03-22 10:24
     */
    @RequestMapping("/listAllParam")
    public String listAllParam(Pager pager, Model model, String search, String menuid, Integer rank,String msgFrom ,Integer status) {
        try{
            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            map.put("menuid",menuid);
            List<PersistentMessageQueue> list = persistentMessageQueueService.listByCondition(startRow, pageSize, search, rank,msgFrom ,status);
            int totalNum = persistentMessageQueueService.countByCondition(search, rank,msgFrom ,status);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("rank", rank);
            model.addAttribute("msgFrom", msgFrom);
            model.addAttribute("status", status);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/persistentMessageQueue/persistentMessageQueue_list";
    }

    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-03-22 11:43
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            PersistentMessageQueue persistentMessageQueue = persistentMessageQueueService.getById(id);
            int i = persistentMessageQueueService.deleteById(id);
            System.out.println("------------ [log.info System Message] : Delete PersistentMessageQueueService ID = " + id + "  RESP is " + i +" Object = " + persistentMessageQueue + " ------------");
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
     * @date 2018-04-02 15:15
     */
    @RequestMapping("/toView")
    public String toView(String id, Model model, String menuid){
        try{
            PersistentMessageQueue bean = persistentMessageQueueService.getById(id);
            model.addAttribute("bean", bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("menuid",menuid);
        return "admin/persistentMessageQueue/persistentMessageQueue_view";
    }

}
