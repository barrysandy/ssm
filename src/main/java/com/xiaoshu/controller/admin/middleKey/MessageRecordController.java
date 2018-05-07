package com.xiaoshu.controller.admin.middleKey;

import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.single.SignMessageMQ;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/messageRecord")
public class MessageRecordController {
    @Resource private MessageRecordService messageRecordService;

    /**
     * 分页查询
     * @return view
     * @author XGB
     * @date 2018-03-22 10:24
     */
    @RequestMapping("/list")
    public String list(Pager pager, Model model, String search, String menuid,String date1,String date2 ) {
        if(pager == null){ pager = new Pager(); }
        if(date1 == null || "".equals(date1) ){ date1 = "0"; }
        if(date2 == null || "".equals(date2) ){ date2 = "0"; }
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
            List<MessageRecord> list = messageRecordService.listByKeyWord(startRow,pageSize ,search , date1, date2);
            int totalNum = messageRecordService.countByKeyWord(search , date1, date2);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("date1", date1);
            model.addAttribute("date2", date2);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageRecord_list";


//        return "redirect:/messageRecord/listAllParam?fullListSize=0&list=&pageSize=10&pageNumber=1&pageCount=0&searchId=&sortCriterion=&search=&menuid=" + menuid + "&typeState=-1&status=0&date1=0&date2=0&sellerId=-1&userId=-1";
    }

    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-03-22 10:24
     */
//    @RequestMapping("/listAllParam")
//    public String listAllParam(Pager pager, Model model, String search, String menuid,String date1,String date2 ) {
//        try{
//            if(date1 != null && date1.length() == 10 ){date1 = date1 + " 00:00:00";}
//            if(date2 != null && date2.length() == 10 ){date2 = date2 + " 23:59:59";}
//
//            //对传入的Date参数进行处理
//            if(date1 == null || "0".equals(date1) || "".equals(date1)){
//                date1 = ToolsDate.getStringDateLastMonth(-3);
//            }
//            if(date2 == null || "0".equals(date2) || "".equals(date2)){
//                date2 = ToolsDate.getStringDate(ToolsDate.simpleSecond);
//            }
//            date2 = ToolsDate.getMaxTime(ToolsDate.simpleSecond,date1, date2);
//
//            Map map = new HashMap(8);
//            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
//            int pageSize = pager.getPageSize();
//            map.put("startRow", startRow);
//            map.put("pageSize", pager.getPageSize());
//            map.put("search", search);
//            map.put("menuid",menuid);
//            List<MessageRecord> list = messageRecordService.listByKeyWord(startRow,pageSize ,search , date1, date2);
//            int totalNum = messageRecordService.countByKeyWord(search , date1, date2);//统计总记录数
//            pager.setFullListSize(totalNum);
//            pager.setList(list);
//            model.addAttribute("date1", date1);
//            model.addAttribute("date2", date2);
//            model.addAttribute("pager", pager);
//            model.addAttribute("bean", list);
//            model.addAttribute("search", search);
//            model.addAttribute("menuid",menuid);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "admin/messageRecord/messageRecord_list";
//    }

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-03-22 11:10
     */
    @RequestMapping("/toEdit")
    public String toEdit(String id, Model model, String menuid){
        try{
            MessageRecord bean = new MessageRecord();
            if(StringUtils.isNotBlank(id)){
                bean = messageRecordService.getById(id);
            }
            model.addAttribute("bean", bean);
            model.addAttribute("menuid",menuid);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageRecord_edit";
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-03-22 11:26
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(MessageRecord bean,String set){
        try{
            if(bean != null){
                String id = bean.getId();
                if(StringUtils.isNotBlank(id)){
                    bean.setUpdateTime(new Date());
                    messageRecordService.updateAll(bean);
                }else{
                    UUID u = UUID.randomUUID();
                    bean.setId(u.toString());
                    bean.setCreateTime(new Date());
                    messageRecordService.save(bean);
                }
                if(set != null){
                    if(!"".equals(set) && "1".equals(set)){
                        //调用发送短信API
//                        String mobile = bean.getMobile();
//                        String content = bean.getContent();
//                        String template = MsgTemplate.ONE_POINT_FIVE_HOURS_CODE;
//                        String[] param = {content,bean.getSign()};
//                        HashMap<String, Object> map = IndustrySMS.link(mobile, template, "",param);
//                        String status = (String) map.get("status");
//                        String msg = (String) map.get("msg");
//                        if (status != null){
//                            if(!"0".equals(status)){//发送成功
//                                bean.setResponseStatus(status + "," + msg);
//                            }else {
//                                bean.setResponseStatus(status + "," + msg);
//                            }
//                            messageRecordService.updateAll(bean);
//                        }

                    }
                }

            }
        }catch(Exception e) {
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
            MessageRecord bean = messageRecordService.getById(id);
            model.addAttribute("bean", bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("menuid",menuid);
        return "admin/messageRecord/messageRecord_view";
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
            int i = messageRecordService.deleteById(id);
            System.out.println("------------ [log.info System Message] : Delete MessageRecord ID = " + id + "  RESP is " + i +" ------------");
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 加入消息群发队列中
     */
    @RequestMapping("/addQueueMessage")
    @ResponseBody
    public String addQueueMessage(HttpServletRequest req, HttpServletResponse resp, @RequestParam("ids") String ids){
        resp.setCharacterEncoding("UTF-8");
        SignMessageMQ signMessageMQ = SignMessageMQ.getInstance();
        Map<String,List<MessageRecord>> map = signMessageMQ.getMap();
        List<MessageRecord> listMQ = map.get("sendMsg");
        try {
            String id[] = ids.split(",");
            int length = listMQ.size();
            String[] arrayMQ = new String[length];
            Integer imq = 0 ;
            for(MessageRecord messageRecord : listMQ) {
                arrayMQ[imq] = messageRecord.getId();
                imq ++ ;
            }
            for (int i = 0; i < id.length; i++) {
                String idTemp = id[i];
                MessageRecord messageRecord = messageRecordService.getById(idTemp);
                if(messageRecord != null){
                    if(messageRecord.getId() != null){
                        if(arrayMQ.length > 0){
                            int index = Arrays.binarySearch(arrayMQ, idTemp);
                            if( index == -1){
                                listMQ.add(messageRecord);
                            }
                        }else {
                            listMQ.add(messageRecord);
                        }
                    }
                }
            }
            map.put("sendMsg",listMQ);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        System.out.println(JsonUtils.turnJson(true,"success",null));
        return JsonUtils.turnJson(true,"success",null);
    }



    /**
     * 预群发队列
     * @return view
     * @author XGB
     * @date 2018-03-26 13:48
     */
    @RequestMapping("/preList")
    public String preList(HttpServletRequest request) {
        try{
            SignMessageMQ signMessageMQ = SignMessageMQ.getInstance();
            Map<String,List<MessageRecord>> map = signMessageMQ.getMap();
            List<MessageRecord> list = map.get("sendMsg");
            request.setAttribute("bean", list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageRecord_preList";
    }



    /**
     * 移除预群发队列
     * @return view
     * @author XGB
     * @date 2018-03-26 14:02
     */
    @RequestMapping("/removePre")
    @ResponseBody
    public String removePre(String id){
        try{
            SignMessageMQ signMessageMQ = SignMessageMQ.getInstance();
            Map<String,List<MessageRecord>> map = signMessageMQ.getMap();
            List<MessageRecord> list = map.get("sendMsg");
            Iterator<MessageRecord> iterator = list.iterator();
            while(iterator.hasNext()) {
                MessageRecord messageRecord = iterator.next();
                if(messageRecord != null){
                    if(id.equals(messageRecord.getId())){
                        iterator.remove();
                        System.out.println("------------ [System Message] remove ArrayList ID ：" + id + " ------------");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        System.out.println(JsonUtils.turnJson(true,"success",null));
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 移除整个预群发队列
     * @return view
     * @author XGB
     * @date 2018-03-26 14:02
     */
    @RequestMapping("/removePreAll")
    @ResponseBody
    public String removePreAll(){
        try{
            SignMessageMQ signMessageMQ = SignMessageMQ.getInstance();
            Map<String,List<MessageRecord>> map = signMessageMQ.getMap();
            List<MessageRecord> list = map.get("sendMsg");
            list.clear();
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        System.out.println(JsonUtils.turnJson(true,"success",null));
        return JsonUtils.turnJson(true,"success",null);
    }


//    /**
//     * 执行整个预群发队列
//     * @return view
//     * @author XGB
//     * @date 2018-03-27 16:23
//     */
//    @RequestMapping("/runQueueMessage")
//    @ResponseBody
//    public String runQueueMessage(HttpServletRequest request){
//        try{
//            SignMessageMQ signMessageMQ = SignMessageMQ.getInstance();
//            Map<String,List<MessageRecord>> map = signMessageMQ.getMap();
//            List<MessageRecord> list = map.get("sendMsg");
//            Iterator<MessageRecord> iterator = list.iterator();
//            while(iterator.hasNext()) {
//                MessageRecord messageRecord = iterator.next();
//                String id = UUID.randomUUID().toString();
//                String url = com.xiaoshu.api.Set.SYSTEM_URL + "persistentMessageQueue/sendMsg!id="+id;
//                String time = ToolsDate.getStringDateToFormat(new Date(),ToolsDate.simpleSecond);
//                PersistentMessageQueue persistentMessageQueue = new PersistentMessageQueue( id, 2, url, "ADMIN_MQ",time , time, null, messageRecord.getId(), "", 0);
//                int saveResult = persistentMessageQueueService.save(persistentMessageQueue);
//                if(saveResult > 0){
//                    queue.offer(new Message(5, url, 10));
//                    iterator.remove();
//                }
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
//        }
//        System.out.println(JsonUtils.turnJson(true,"success",null));
//        return JsonUtils.turnJson(true,"success",null);
//
//    }

}
