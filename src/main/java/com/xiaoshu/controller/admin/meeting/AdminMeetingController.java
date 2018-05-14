package com.xiaoshu.controller.admin.meeting;

import com.xgb.springrabbitmq.dto.DtoMessage;
import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.api.Set;
import com.xiaoshu.entity.Meeting;
import com.xiaoshu.entity.MeetingSign;
import com.xiaoshu.enumeration.EnumsMQName;
import com.xiaoshu.service.MeetingService;
import com.xiaoshu.service.MeetingSignService;
import com.xiaoshu.service.MessageRecordService;
import com.xiaoshu.tools.*;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/meeting")
public class AdminMeetingController {

    @Autowired private DeadLetterPublishService deadLetterPublishService;
    @Resource private MeetingService meetingService;
    @Resource private MeetingSignService meetingSignService;
    @Resource private MessageRecordService messageRecordService;
    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-05-9 17:22
     */
    @RequestMapping("/list")
    public String list(String menuid,String parentId,Pager pager, Model model, String search, String date1,String date2,Integer status) {
        try{
            if(pager == null){ pager = new Pager(10); }
            if(status == null){ status = -1;}
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
            List<Meeting> list = meetingService.getListByKeyWord(status,startRow,pageSize, search, date1, date2);
            int totalNum = meetingService.getCountByKeyWord(status,search, date1, date2);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("date1", date1);
            model.addAttribute("date2", date2);
            model.addAttribute("status", status);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/meeting_list";
    }


    /**
     * 预览
     * @param id
     * @param model
     * @return
     * @author XGB
     * @date 2018-05-10 9:42
     */
    @RequestMapping("/toView")
//    @ArchivesLog(operationType="查询操作:",operationName="单个查询会议")
    public String toView(String id, Model model, String menuid){
        try{
            if(id != null){
                Meeting bean = meetingService.getById(id);
                String image = ToolsImage.getImageUrlByServer(bean.getImage());
                model.addAttribute("image", image);
                model.addAttribute("bean", bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("menuid",menuid);
        return "admin/meeting/meeting_view";
    }

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-5-10 10:09
     */
    @RequestMapping("/toEdit")
    public String toEdit( Model model,String id, String menuId){
        System.out.println("id: " + id );
        String imageShare = "";
        try{
            Meeting bean =  new Meeting("-1", "", "", "", "", "", "", "", "", "", 0, 0,"","");
            System.out.println("bean: " + bean );
            if(id != null){
                if(StringUtils.isNotBlank(id) && !"-1".equals(id)){
                    bean = meetingService.getById(id);
                }
            }
            if(bean != null){
                if( bean.getImage() != null){
                    imageShare = ToolsImage.getImageUrlByServer(bean.getImage());
                }
            }
            System.out.println("bean: " + bean );
            model.addAttribute("bean", bean);
            model.addAttribute("image", imageShare);
            model.addAttribute("id", id);
            model.addAttribute("menuId",menuId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/meeting_edit";
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-5-10 10:09
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(Meeting bean){
        try{
            String oldImage = "";
            if(bean != null){
                if(bean.getId() != null && !"".equals(bean.getId()) && !"-1".equals(bean.getId())){
                    Meeting old = meetingService.getById(bean.getId());
                    meetingService.updateAll(bean,old.getImage());
                }else{
                    meetingService.save(bean,oldImage);
                }
            }
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error" + e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-5-10 11:21
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            meetingService.deleteById(id);
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }

    /**
     * 更新Excel文件
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-5-10 18:08
     */
    @RequestMapping("/updateExcel")
    @ResponseBody
    public String updateExcel(String id,String excelPath){
        int i = 0;
        try{
            i = meetingService.updateExcelById(excelPath,id);
        }catch(Exception e){
            return String.valueOf(i);
        }
        return String.valueOf(i);
    }


    /**
     * meeting/readerExcelAndAddData
     * 执行解析Excel文件
     * @param id 主键ID
     * @param x 文档的标题数
     * @param y 文档的数量条数（包含标题行）
     * @param type 添加数据模式 0删除以前的 1追加模式
     * @author XGB
     * @date 2018-5-11 10:05
     */
    @RequestMapping("/readerExcelAndAddData")
    @ResponseBody
    public String readerExcelAndAddData(String id,String tableName,Integer x,Integer y,Integer type){
        int total = 0;
        try{
            //TODO 初始化参数
            if(x == null || x == 0){x = 7;}//设置默认解析标题数
            if(y == null || y == 0){y = 10;}//设置默认解析记录条数（包含标题行）
            if(tableName == null || "".equals(tableName)){tableName = "Sheet1";}//设置默认解析表名称
            String filePath = null;//文件地址
            if(y == null || y == 0){y = 10;}
            int maxX = x;
            if(type == 0){
                //删除以前的数据
                meetingSignService.deleteByMeetingId(id);
            }

            if(Set.SYSTEM_ENVIRONMENTAL == 0){//测试环境
                filePath = "G:\\3715b6fcfbb6418fb4ed2269be48b48b.xlsx";
            }else if(Set.SYSTEM_ENVIRONMENTAL == 1){//正式环境
                //TODO 换取文件地址
                Meeting meeting = meetingService.getById(id);
                if(meeting.getExcelPath() != null){
                    filePath = ToolsImage.getFilePathByServer(meeting.getExcelPath());
                }
            }

            //TODO 解析文档并添加新参会人员
            if(filePath != null){
                if(!"".equals(filePath)){
                    String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
                    //TODO 获取导入数据的集合
                    List<MeetingSign> listMeetingSign = new ArrayList<MeetingSign>();
                    List<Object> list = ExcelReader.getListExcelObject(MeetingSign.class,filePath,tableName,x,y,maxX);
                    if(list != null){
                        for (int i = 0;i < list.size();i++){
                            Object listObject = list.get(i);
                            MeetingSign meetingSign = (MeetingSign)listObject;
                            if(meetingSign.getName() != null && meetingSign.getPhone()!= null){
                                meetingSign.setId(UUID.randomUUID().toString());
                                String phone = meetingSign.getPhone();
                                phone = phone.replaceAll("\\.","");
                                phone = phone.replaceAll("E10","");
                                meetingSign.setPhone(phone);
                                meetingSign.setCreateTime(nowTime);
                                meetingSign.setJoinDinner(0);
                                meetingSign.setMeetingId(id);
                                meetingSign.setSignCode(meetingSignService.getSignCode());
                                meetingSign.setStatus(0);
                                listMeetingSign.add(meetingSign);
                            }
                        }
                    }

                    if(listMeetingSign != null){
                        if(listMeetingSign.size() > 0){
                            Iterator<MeetingSign> iterator = listMeetingSign.iterator();
                            while (iterator.hasNext()){
                                MeetingSign bean = iterator.next();
                                if(bean != null){
                                    int r = meetingSignService.save(bean);
                                    if(r != -1){
                                        total = total + r ;
                                    }
                                }
                            }
                        }
                    }
                    //meetingSignService.saveList(listMeetingSign);
                    //total = listMeetingSign.size();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return String.valueOf(0);
        }
        return String.valueOf(total);
    }


    /**
     * meeting/downloadExcel
     * 新增Excel文件
     * @param excelPath
     * @return String
     * @author XGB
     * @date 2018-5-11 11:14
     */
    @RequestMapping("/downloadExcel")
    public String downloadExcel(String excelPath,HttpServletResponse response){
        System.out.println("excelPath: " + excelPath);
        OutputStream out = null;
        FileInputStream in;
        try {
            String path = ToolsImage.getFilePathByServer(excelPath);
            in = new FileInputStream(new File(path));
            out = response.getOutputStream();
            String fileName = excelPath + ".xls";// 文件名
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.getWriter().print(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null ;
    }


    /**
     * 发送群发短信/生成条码
     * @author XGB
     * @date 2018-05-12 19:35
     */
    @RequestMapping("/sendMeetingMessage")
    @ResponseBody
    public String sendMeetingMessage(String id){
        int total = 0;
        try{
            //发送短信
            total = messageRecordService.sendMeetingMsg(id,"MEETING_MSG_ALL");
            //TODO 使用消息队列处理条码生成功能-异步处理
            String url =  Set.SYSTEM_URL + "interfaceMqMeeting/createBarCode";
            String params = "meetingId=" + id ;
            DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), url, "get" ,params , null);
            String message = DtoMessage.transformationToJson(dtoMessage);
            System.out.println("=================" + message);
            deadLetterPublishService.send(EnumsMQName.DEAD_LETTER,message);

        }catch(Exception e) {
            e.printStackTrace();
            return "-1";
        }
        return String.valueOf(total);
    }

    /**
     * meeting/interfaceAddCodeToMap?code=
     * 添加条形码
     * @author XGB
     * @date 2018-05-14 15:16
     */
    @RequestMapping("/interfaceAddCodeToMap")
    @ResponseBody
    public String interfaceAddCodeToMap(String code){
        System.out.println(" code : " + code );
        try{

        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
