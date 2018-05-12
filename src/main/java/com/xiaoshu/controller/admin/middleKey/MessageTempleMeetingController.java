package com.xiaoshu.controller.admin.middleKey;

import com.xiaoshu.entity.MessageTemple;
import com.xiaoshu.service.MessageTempleService;
import com.xiaoshu.util.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/messageTemple")
public class MessageTempleMeetingController {

    @Resource private MessageTempleService messageTempleService;


    /**
     * 分页查询
     * @return view
     * @author XGB
     * @date 2018-04-08 13:15
     */
    @RequestMapping("/listMeeting")
    public String list(Pager pager, Model model, String search, String refId, String refType ) {
        if(pager == null){ pager = new Pager(); }
        try{
            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            List<MessageTemple> list = messageTempleService.getMeetingListByKey(startRow,pageSize,search, refId,refType);
            int totalNum = messageTempleService.getCountMeetingByKey(search,refId,refType);//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("refId", refId);
            model.addAttribute("refType", refType);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/messageRecord/messageTemple_list";
    }

}
