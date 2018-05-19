package com.xiaoshu.controller.frontend.meeting;

import com.xiaoshu.entity.FocusedUserInfo;
import com.xiaoshu.entity.MeetingCard;
import com.xiaoshu.service.MeetingCardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/cardInUser")
public class MeetingCardController {

    @Resource private MeetingCardService meetingCardService;

    /**
     * cardInUser/index?menuId=107
     * */
    @RequestMapping("index")
    public String index(HttpServletRequest request, String menuId){
        try{
            FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
            if(focusedUserInfo != null){
                MeetingCard bean = meetingCardService.getCountByUserId(focusedUserInfo.getId());
                if(bean != null){
                    request.setAttribute("exit",1);
                    request.setAttribute("bean",bean);
                }else {
                    request.setAttribute("exit",0);
                    request.setAttribute("bean",new MeetingCard(null,null,null,null,null,null,1));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("menuId",menuId);
        return "mobile/meeting/card/index";
    }



    @RequestMapping("/saveCard")
    @ResponseBody
    /**
     * 返回 -1 参数校验失败 返回-2 用户登录超时
     */
    public String saveCard(HttpServletRequest request,String menuId, String name, String phone, String address){
        Integer i = 0;
        try{
            if(menuId != null && name != null && phone != null && address != null){
                FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
                if(focusedUserInfo != null){
                    MeetingCard meetingCard = meetingCardService.getCountByUserId(focusedUserInfo.getId());
                    if(meetingCard == null){
                        MeetingCard newBewn = new MeetingCard(UUID.randomUUID().toString(), name, phone, address, focusedUserInfo.getId(), focusedUserInfo.getOpenid(), 1);
                        i = meetingCardService.save(newBewn);
                    }else{
                        i = -3;
                    }
                }else {
                    i = -2;
                }
            }else {
                i = -1;
            }
        }catch(Exception e){
            return String.valueOf(i);
        }
        return String.valueOf(i);
    }

}
