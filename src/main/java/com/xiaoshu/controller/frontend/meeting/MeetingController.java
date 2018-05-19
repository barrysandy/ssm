package com.xiaoshu.controller.frontend.meeting;

import com.xiaoshu.api.Set;
import com.xiaoshu.entity.Meeting;
import com.xiaoshu.entity.MeetingCoordinate;
import com.xiaoshu.entity.MeetingSign;
import com.xiaoshu.service.MeetingCoordinateService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/meeting")
public class MeetingController {

    public static final String MEETING_URL2 = "meeting/myCodeNoUser?id=";

    @Resource private MeetingService meetingService;
    @Resource private MeetingSignService meetingSignService;
    @Resource private MeetingCoordinateService meetingCoordinateService;

    @RequestMapping("myCodeNoUser")
    public String myCodeNoUser(HttpServletRequest request, String id, String code){
        String url = "mobile/meeting/userCode";
        System.out.println("myCodeNoUser------------------ url : " + url);
        try{
            if(id != null && code != null){
                Meeting meeting = meetingService.getById(id);
                MeetingSign meetingSign = meetingSignService.getBySignCode(code,id);
                if(meeting != null && meetingSign != null){
                    request.setAttribute("meeting",meeting);
                    request.setAttribute("meetingSign",meetingSign);
                    String path = Set.SYSTEM_URL + "/resources/upfile/signCode/" + id + "/" + File.separator + code + ".jpeg";//服务器保存文件路径
                    request.setAttribute("code", path);
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
    public String myCodeStatusNoUser(String id, String code){
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
     * 会议页面
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
                    System.out.println(" Get it in Map!!!");
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

                List<MeetingCoordinate> listMap = meetingCoordinateService.getListByMeetingId(id);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("[");
                if(listMap != null){
                    if(listMap.size() > 0){
                        for(int i = 0;i < listMap.size();i ++){
                            if(i + 1 < listMap.size()){
                                stringBuffer.append("[");
                                stringBuffer.append(listMap.get(i).getX());
                                stringBuffer.append(",");
                                stringBuffer.append(listMap.get(i).getY());
                                stringBuffer.append("]");
                                if(listMap.size() > 1){
                                    stringBuffer.append(",");
                                }
                            }
                            if(i + 1 == listMap.size()){
                                stringBuffer.append("[");
                                stringBuffer.append(listMap.get(i).getX());
                                stringBuffer.append(",");
                                stringBuffer.append(listMap.get(i).getY());
                                stringBuffer.append("]");
                            }

                        }
                    }
                }
                stringBuffer.append("]");
                System.out.println("positions:" + stringBuffer.toString());
                request.setAttribute("listMap",listMap);
                request.setAttribute("positions",stringBuffer.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "mobile/meeting/userMeeting";
    }


    /**
     * meeting/getCoordinateNameNoUser
     * 获取坐标点的名称
     * @param x
     * @param y
     * @author XGB
     * @date 2018-05-14 15:42
     */
    @RequestMapping(value = "/getCoordinateNameNoUser", method = RequestMethod.GET)
    @ResponseBody
    public String getCoordinateNameNoUser(String x, String y){
        try{
            Map map = MapMeetingCache.getInstance().getMap();
            if(map.get(x+y) != null){
                String name = (String) map.get(x+y);
                System.out.println(" Get it in Map!!!");
                return name;
            }else{
                MeetingCoordinate meetingCoordinate = meetingCoordinateService.getNameByCoordinate(x,y);
                if(meetingCoordinate != null){
                    if(meetingCoordinate.getX() != null && meetingCoordinate.getY() != null){
                        map.put(meetingCoordinate.getX() + meetingCoordinate.getY(),meetingCoordinate.getName());
                        return meetingCoordinate.getName();
                    }
                    System.out.println(" Get it in databases!!!" + meetingCoordinate);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }
        return "0";
    }

}
