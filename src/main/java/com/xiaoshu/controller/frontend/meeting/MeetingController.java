package com.xiaoshu.controller.frontend.meeting;

import com.xiaoshu.api.Set;
import com.xiaoshu.entity.Meeting;
import com.xiaoshu.entity.MeetingSign;
import com.xiaoshu.service.MeetingService;
import com.xiaoshu.service.MeetingSignService;
import com.xiaoshu.service.MessageRecordService;
import com.xiaoshu.tools.ExcelReader;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
    @Resource private MeetingService meetingService;
    @Resource private MeetingSignService meetingSignService;
    @Resource private MessageRecordService messageRecordService;

    /**
     * meeting/interfaceMyCode?id=&code=
     * 用户条形码访问页面
     * @param id
     * @param code
     * @author XGB
     * @date 2018-05-14 15:42
     */
    @RequestMapping("/interfaceMyCode")
    public String toView(HttpServletRequest request, String id, String code){
        String url = "";
        try{
            if(id != null && code != null){
                Meeting meeting = meetingService.getById(id);
                MeetingSign meetingSign = meetingSignService.getBySignCode(code);
                if(meeting != null && meetingSign != null){
                    request.setAttribute("meeting",meeting);
                    request.setAttribute("meetingSign",meetingSign);
                    url = "mobile/meeting/userCode";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return url;
    }

}
