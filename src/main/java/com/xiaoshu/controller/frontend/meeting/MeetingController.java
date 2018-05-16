package com.xiaoshu.controller.frontend.meeting;

import com.xiaoshu.api.Set;
import com.xiaoshu.entity.Meeting;
import com.xiaoshu.entity.MeetingSign;
import com.xiaoshu.service.MeetingService;
import com.xiaoshu.service.MeetingSignService;
import com.xiaoshu.tools.*;
import com.xiaoshu.tools.single.MapMeetingCache;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

@Controller
@RequestMapping("/meeting")
public class MeetingController {

    @Resource private MeetingService meetingService;
    @Resource private MeetingSignService meetingSignService;

    /**
     * meeting/interfaceMyCodeNoUser?id=&code=
     * 用户条形码访问页面
     * @param id
     * @param code
     * @author XGB
     * @date 2018-05-14 15:42
     */
    @RequestMapping("interfaceMyCodeNoUser")
    public String interfaceMyCodeNoUser(HttpServletRequest request, String id, String code){
        String url = "";
        try{
            if(id != null && code != null){
                Meeting meeting = meetingService.getById(id);
                MeetingSign meetingSign = meetingSignService.getBySignCode(code,id);
                if(meeting != null && meetingSign != null){
                    request.setAttribute("meeting",meeting);
                    request.setAttribute("meetingSign",meetingSign);
                    String path = Set.SYSTEM_URL + "/resources/upfile/signCode/" + id + "/" + File.separator + code + ".jpeg";//服务器保存文件路径
                    request.setAttribute("code", path);
                    url = "mobile/meeting/userCode";
                }
            }
            System.out.println("url:" + url );
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    /**
     * meeting/myCodeStatusNoUser?id=&code=
     * 用户条形码状态 是否被扫描了
     * @param id
     * @param code
     * @author XGB
     * @date 2018-05-14 15:42
     */
    @RequestMapping(value = "/myCodeStatusNoUser", method = RequestMethod.GET)
    @ResponseBody
    public String myCodeStatusNoUser(HttpServletRequest request, String id, String code){
        try{
            if(id != null && code != null){
                Integer status = meetingSignService.getStatusBySignCode(code,id);
                if(status != null){
                    System.out.println("myCodeStatusNoUser status:" + status);
                    return String.valueOf(status);
                }
            }
        }catch (Exception e){
            System.out.println("myCodeStatusNoUser status: e" + e);
            e.printStackTrace();
            return "0";
        }
        System.out.println("myCodeStatusNoUser status:0");
        return "0";
    }


    /**
     * meeting/myMeetingNoUser?id=
     * 用户条形码访问页面
     * @param id
     * @author XGB
     * @date 2018-05-15 9:22
     */
    @RequestMapping("myMeetingNoUser")
    public String myMeetingNoUser(HttpServletRequest request, String id){
        Meeting meeting ;
        if(id != null){
            try{
                Map map = MapMeetingCache.getInstance().getMap();
                if(map.get(id) != null){
                    meeting = (Meeting) map.get(id);
                    request.setAttribute("meeting",meeting);
                    System.out.println(" Get it in Map!!!" + meeting);
                }else{
                    meeting = meetingService.getById(id);
                    if(meeting != null){
                        if(meeting.getImage() != null){
                            String url = ToolsImage.getImageUrlByServer(meeting.getImage());
                            meeting.setImage(url);
                        }
                        map.put(id,meeting);
                        request.setAttribute("meeting",meeting);
                        System.out.println(" Get it in databases!!!" + meeting);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "mobile/meeting/userMeeting";
    }
}
