package com.xiaoshu.controller.admin.meeting;

import com.xiaoshu.entity.MeetingCard;
import com.xiaoshu.service.MeetingCardService;
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
@RequestMapping("/meetingCard")
public class AdminMeetingCardController {
    @Resource private MeetingCardService meetingCardService;


    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-05-19 11:00
     */
    @RequestMapping("/list")
    public String list(Pager pager, Model model, String search) {
        try{
            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            List<MeetingCard> list = meetingCardService.getList(startRow, pageSize, search);
            int totalNum = meetingCardService.getCountList(search);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/card/list";
    }


    /**
     * 预览
     * @param id
     * @param model
     * @return
     * @author XGB
     * @date 2018-05-19 11:00
     */
    @RequestMapping("/toView")
    public String toView(String id, Model model){
        try{
            if(id != null){
                MeetingCard bean = meetingCardService.getById(id);
                model.addAttribute("bean", bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "admin/meeting/card/view";
    }

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-05-19 11:00
     */
    @RequestMapping("/toEdit")
    public String toEdit( Model model,String id){
        try{
            MeetingCard bean =  new MeetingCard("-1","", "", "", "", "", 1);
            if(StringUtils.isNotBlank(id) && !"-1".equals(id) && !"".equals(id)){
                bean = meetingCardService.getById(id);
            }
            model.addAttribute("bean", bean);
            model.addAttribute("id", id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/meeting/card/edit";
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-05-19 11:00
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(MeetingCard bean){
        try{
            if(bean != null){
                if(bean.getId() != null && !"".equals(bean.getId()) && !"-1".equals(bean.getId())){
                    meetingCardService.updateAll(bean);
                }else{
                    //生成签到码
                    meetingCardService.save(bean);
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
     * @date 2018-05-19 11:00
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            meetingCardService.deleteById(id);
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }
//
//
//    /**
//     * 更新JoinDinner
//     * @param id 主键ID
//     * @return String
//     * @author XGB
//     * @date 2018-5-10 11:50
//     */
//    @RequestMapping("/updateJoinDinner")
//    @ResponseBody
//    public String updateJoinDinner(String id){
//        try{
//            MeetingSign meetingSign = meetingSignService.getById(id);
//            int join = 0;
//            if(meetingSign.getJoinDinner() == 0){
//                join = 1;
//            }
//            else if(meetingSign.getJoinDinner() == 1){
//                join = 0;
//            }
//            meetingSignService.updateJoinById(join,id);
//        }catch(Exception e){
//            return "0";
//        }
//        return "1";
//    }

}
