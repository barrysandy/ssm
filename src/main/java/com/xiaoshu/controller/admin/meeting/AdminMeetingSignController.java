package com.xiaoshu.controller.admin.meeting;

import com.xiaoshu.entity.Meeting;
import com.xiaoshu.entity.MeetingSign;
import com.xiaoshu.service.MeetingService;
import com.xiaoshu.service.MeetingSignService;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/meetingSign")
public class AdminMeetingSignController {
    @Resource private MeetingSignService meetingSignService;
    @Resource private MeetingService meetingService;


    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-05-9 17:22
     */
    @RequestMapping("/list")
    public String list(Pager pager, Model model, String search, Integer status,String id) {
        try{
            if(pager == null){ pager = new Pager(10); }
            if(status == null){ status = -1;}
            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            List<MeetingSign> list = meetingSignService.getListByKeyWord(id,status,startRow, pageSize, search);
            int totalNum = meetingSignService.getCountByKeyWord(id,status,search);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            Meeting meeting = meetingService.getById(id);
            int exit = 0;
            if(meeting != null){
                if(meeting.getExcelPath() != null){
                    if(!"".equals(meeting.getExcelPath())){
                        String fileUrl = ToolsImage.getImageUrlByServer(meeting.getExcelPath());
                        model.addAttribute("fileUrl", fileUrl);
                        exit = 1;
                    }
                }
            }
            model.addAttribute("exit", exit);
            model.addAttribute("id", id);
            model.addAttribute("status", status);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
            model.addAttribute("meeting", meeting);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/meetingSign_list";
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
    public String toView(String id, Model model){
        try{
            if(id != null){
                MeetingSign bean = meetingSignService.getById(id);
                model.addAttribute("bean", bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "admin/meeting/meetingSign_view";
    }

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-5-10 11:50
     */
    @RequestMapping("/toEdit")
    public String toEdit( Model model,String id,String meetingId, String menuId){
        try{
            MeetingSign bean =  new MeetingSign("-1","", "", "", "", "", "", "", 0, "", "", "", 0, "");
            if(StringUtils.isNotBlank(id) && !"-1".equals(id) && !"".equals(id)){
                bean = meetingSignService.getById(id);
            }
            bean.setMeetingId(meetingId);
            model.addAttribute("bean", bean);
            model.addAttribute("id", id);
            model.addAttribute("menuId",menuId);
            model.addAttribute("meetingId",meetingId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/meetingSign_edit";
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-5-10 11:50
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(MeetingSign bean){
        try{
            if(bean != null){
                if(bean.getId() != null && !"".equals(bean.getId()) && !"-1".equals(bean.getId())){
                    meetingSignService.updateAll(bean);
                }else{
                    //生成签到码
                    bean.setSignCode(meetingSignService.getSignCode());
                    meetingSignService.save(bean);
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
     * @date 2018-5-10 11:50
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            meetingSignService.deleteById(id);
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 更新JoinDinner
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-5-10 11:50
     */
    @RequestMapping("/updateJoinDinner")
    @ResponseBody
    public String updateJoinDinner(String id){
        try{
            MeetingSign meetingSign = meetingSignService.getById(id);
            int join = 0;
            if(meetingSign.getJoinDinner() == 0){
                join = 1;
            }
            else if(meetingSign.getJoinDinner() == 1){
                join = 0;
            }
            meetingSignService.updateJoinById(join,id);
        }catch(Exception e){
            return "0";
        }
        return "1";
    }

}
