package com.xiaoshu.controller.admin.meeting;

import com.xiaoshu.entity.MeetingCoordinate;
import com.xiaoshu.service.MeetingCoordinateService;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/meetingCoordinate")
public class AdminMeetingCoordinateController {
    @Resource private MeetingCoordinateService meetingCoordinateService;


    /**
     * 分页查询
     * @param meetingId
     * @return view
     * @author XGB
     * @date 2018-05-18 15:30
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request,String meetingId) {
        try{
            List<MeetingCoordinate> list = meetingCoordinateService.getListByMeetingId(meetingId);
            request.setAttribute("list",list);
            request.setAttribute("meetingId",meetingId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/coordinate/list";
    }


    /**
     * 预览
     * @param id
     * @param model
     * @return
     * @author XGB
     * @date 2018-05-18 15:30
     */
    @RequestMapping("/toView")
    public String toView(String id, Model model){
        try{
            if(id != null){
                MeetingCoordinate bean = meetingCoordinateService.getById(id);
                model.addAttribute("bean", bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/coordinate/view";
    }

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-05-18 15:30
     */
    @RequestMapping("/toEdit")
    public String toEdit( Model model,String id,String meetingId, String menuId){
        try{
            MeetingCoordinate bean =  new MeetingCoordinate("-1","0.0", "0.0", "0.0,0.0", meetingId, "", "");
            if(StringUtils.isNotBlank(id) && !"-1".equals(id) && !"".equals(id)){
                bean = meetingCoordinateService.getById(id);
            }
            bean.setMeetingId(meetingId);
            model.addAttribute("bean", bean);
            model.addAttribute("id", id);
            model.addAttribute("menuId",menuId);
            model.addAttribute("meetingId",meetingId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/coordinate/edit";
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-05-18 15:30
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(MeetingCoordinate bean){
        try{
            if(bean != null){
                if(bean.getId() != null && !"".equals(bean.getId()) && !"-1".equals(bean.getId())){
                    meetingCoordinateService.updateAll(bean);
                }else{
                    meetingCoordinateService.save(bean);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return JsonUtils.turnJson(false,"error" + e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-05-18 15:30
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            meetingCoordinateService.deleteById(id);
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


}
