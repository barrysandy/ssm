package com.xiaoshu.controller.frontend.activityReception;

import com.xiaoshu.api.Api;
import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.tools.JSSDKTiket;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 微信活动前端
 * @name: WechatActivityController
 * @author: XGB
 * @date: 2018-02-06 16:49
 */
@Controller
@RequestMapping(value = "/activityReception")
public class ActivityReceptionController extends BaseController {

    @Resource private WechatActivityService wechatActivityService;
    @Resource private WechatActivitySignSetService wechatActivitySignSetService;
    @Resource private WechatActivitySignService wechatActivitySignService;
    @Resource private PublicAccountInfoService publicAccountInfoService;
    @Resource private FocusedUserInfoService focusedUserInfoService;

    /**
     * 普通报名方式
     * @param id 活动id
     * @return String
     * @author XGB
     * @date 2018-02-07 10:50
     */
    @RequestMapping("/toSignUpNoUser")
    @ResponseBody
    public ModelAndView toSignUpNoUser(HttpServletRequest request, String menuId, String id, String from, String isappinstalled){
        if(from == null){from = "singlemessage";}
        if(isappinstalled == null){isappinstalled = "0";}
        /** 准备签名参数 */
        String share = "&from=" + from + "&isappinstalled=" + isappinstalled ;
        /** mapKey签名成功后会将本次签名存入map的key值，下次进入该控制器会先判断Map中的值是否存在，不存在则刷新（重新生成） */
        String mapKey =  "activityReception/toSignUpNoUser?menuId=" + menuId + "&id=" + id + share;
        /** url签名页面，一定是当前的controller路径并附带参数和值 */
        String url = Set.SYSTEM_URL + mapKey;
        /** link签名分享链接，页面分享后，点击进入的链接 */
        String link = url;
        /** imgUrl签名分享图片，页面分享后，分享出来的小图片 */
        String imgUrl = null;
        /** shareTitle签名分享标题 */
        String shareTitle = null;
        /** shareDesc签名分享描述 */
        String shareDesc = null;
        try{
            /** 活动WechatActivity对象 */
            WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
            processingSharing(request,wechatActivitySignSetService,wechatActivity,imgUrl,shareTitle,shareDesc,link,url,mapKey,menuId,id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return toVm("mobile/activitySign/sign_toSignUpNoUser");
    }




    /**
     * 授权登录方式报名(开启分享功能)
     * @param id 活动id
     * @return String
     * @author XGB
     * @date 2018-02-28 10:45
     */
    @RequestMapping("/toSignUpInUser")
    public ModelAndView toSignUpInUser(HttpServletRequest request, String menuId, String id, String from, String isappinstalled){
        if(from == null){from = "singlemessage";}
        if(isappinstalled == null){isappinstalled = "0";}
        /** 准备签名参数 */
        String share = "&from=" + from + "&isappinstalled=" + isappinstalled ;
        /** mapKey签名成功后会将本次签名存入map的key值，下次进入该控制器会先判断Map中的值是否存在，不存在则刷新（重新生成） */
        String mapKey =  "activityReception/toSignUpInUser?menuId=" + menuId + "&id=" + id + share;
        /** url签名页面，一定是当前的controller路径并附带参数和值 */
        String url = Set.SYSTEM_URL + mapKey;
        /** link签名分享链接，页面分享后，点击进入的链接 */
        String link = url;
        /** imgUrl签名分享图片，页面分享后，分享出来的小图片 */
        String imgUrl = null;
        /** shareTitle签名分享标题 */
        String shareTitle = null;
        /** shareDesc签名分享描述 */
        String shareDesc = null;
        try{
            /** 授权登录的用户对象 */
            FocusedUserInfo focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
            if(focusedUserInfo != null){
                int exitSign = wechatActivitySignService.selectUserExitSign(id,focusedUserInfo.getId());
                request.setAttribute("sign",exitSign);
                if(exitSign == 0){
                    request.setAttribute("msg","");
                }else{
                    request.setAttribute("msg","你已经报名过了!");
                }
            }

            /** 活动WechatActivity对象 */
            WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);

            /** 需要验证是否关注了另一个号 */
            if(wechatActivity != null){
                int time = ToolsDate.getTheRelationshipBetweenTwoTimeNodesStringDate(new Date(),wechatActivity.getBeginTime(),wechatActivity.getEndTime());
                request.setAttribute("time",time);
                if(!"-1".equals(wechatActivity.getSubscribeWechatId())){
                    if(!wechatActivity.getBindingWechatId().equals(wechatActivity.getSubscribeWechatId())){
                        PublicAccountInfo publicAccountInfo = publicAccountInfoService.selectByPrimaryKey(wechatActivity.getSubscribeWechatId());
                        FocusedUserInfo focusedUserInfoExit = focusedUserInfoService.selectByUnionid(focusedUserInfo.getUnionid(),publicAccountInfo.getParentMenuId());
                        if(focusedUserInfoExit != null){
                            request.setAttribute("subscribe","1");
                        }else{
                            request.setAttribute("subscribe","0");
                        }
                    }
                }else if("-1".equals(wechatActivity.getSubscribeWechatId())){
                    request.setAttribute("subscribe","-1");
                }
            }
            processingSharing(request,wechatActivitySignSetService,wechatActivity,imgUrl,shareTitle,shareDesc,link,url,mapKey,menuId,id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return toVm("mobile/activitySign/sign_toSignUpInUser");
    }


    /**
     * 接收报名参数
     * @param id 活动id
     * @return String
     * @author XGB
     * @date 2018-02-07 10:50
     */
    @RequestMapping("/saveSignUp")
    @ResponseBody
    public String saveSignUp(String params[],String sorts[],String values[],String id,String share,int authorised,String prizes, HttpServletRequest request, HttpServletResponse response){
        try{
            FocusedUserInfo focusedUserInfo = null;//授权登录的用户
            if(params != null){
                if(params.length > 0){
                    //随机统一标识
                    String uniqueIdentifier = UUID.randomUUID().toString();
                    uniqueIdentifier = ToolsString.putOffBar(uniqueIdentifier);
                    WechatActivitySign wechatActivitySign = new WechatActivitySign(uniqueIdentifier,"", "", "", null, id, "", "", 1, -1, new Date(), null);
                    /** 授权登录报名 */
                    if(authorised != -1){
                        focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
                        if(focusedUserInfo != null){
                            wechatActivitySign.setOpenid(focusedUserInfo.getOpenid());
                            wechatActivitySign.setUnionid(focusedUserInfo.getUnionid());
                            wechatActivitySign.setUserId(focusedUserInfo.getId());
                            int exitSign = wechatActivitySignService.selectUserExitSign(id,focusedUserInfo.getId());//判断是否已经报名
                            if(exitSign == 0){
                                int saveRecord = wechatActivitySignService.saveRecord(wechatActivitySign);
                                if(saveRecord > 0){
                                    WechatActivitySignSet nicknameSignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "nickname", focusedUserInfo.getNickName(), 1, "", -1, "", 9999, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
                                    wechatActivitySignSetService.saveRecord(nicknameSignSet);
                                    WechatActivitySignSet headimgurlSignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "headimgurl", focusedUserInfo.getHeadImgUrl(), 1, "", -1, "", 10000, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
                                    wechatActivitySignSetService.saveRecord(headimgurlSignSet);
                                    for(int i=0;i< params.length ;i++){
                                        String name = params[i];
                                        String value = values[i];
                                        int sort = Integer.parseInt(sorts[i]);
                                        //组装属性值
                                        WechatActivitySignSet wechatActivitySignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), name, value, 1, "", -1, "", sort, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
                                        wechatActivitySignSetService.saveRecord(wechatActivitySignSet);
                                    }
                                }
                            }
                        }
                    }
                    /** 非授权的方式提交报名 */
                    else{
                        List<WechatActivitySignSet> wechatActivitySignSet = wechatActivitySignSetService.getAllByActivityId(id,0);
                        if(wechatActivitySignSet != null){
                            int flag = 0;
                            Iterator<WechatActivitySignSet> iterator = wechatActivitySignSet.iterator();
                            while (iterator.hasNext()){
                                WechatActivitySignSet iteratorBean = iterator.next();
                                if(iteratorBean.getRequired() != -1){ //必须属性
                                    String valuese = iteratorBean.getValuese();
                                    int exitSign = wechatActivitySignSetService.selectUserExitSignBySignSetValue(id,valuese);
                                    flag += exitSign;
                                }
                            }
                            if(flag == 0){

                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 接收报名参数
     * @param allParams 报名参数
     * @return String
     * @author XGB
     * @date 2018-02-07 10:50
     */
    @RequestMapping("/saveSignUpAjaxNoUser")
    @ResponseBody
    public String saveSignUpAjaxNoUser(String allParams,String id,String authorised, HttpServletRequest request, HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            FocusedUserInfo focusedUserInfo = null;//授权登录的用户
            if(allParams != null){
                String[] items = allParams.split("~");
                if(items.length > 0){
                    //随机统一标识
                    String uniqueIdentifier = UUID.randomUUID().toString();
                    uniqueIdentifier = ToolsString.putOffBar(uniqueIdentifier);
                    WechatActivitySign wechatActivitySign = new WechatActivitySign(uniqueIdentifier,"", "", "", null, id, "", "", 1, -1, new Date(), null);
                    /** 授权登录报名 */
                    if(Integer.parseInt(authorised) != -1){
                        focusedUserInfo = (FocusedUserInfo) request.getSession().getAttribute("user");
                        if(focusedUserInfo != null){
                            wechatActivitySign.setOpenid(focusedUserInfo.getOpenid());
                            wechatActivitySign.setUnionid(focusedUserInfo.getUnionid());
                            wechatActivitySign.setUserId(focusedUserInfo.getId());
                            int exitSign = wechatActivitySignService.selectUserExitSign(id,focusedUserInfo.getId());//判断是否已经报名
                            if(exitSign == 0){
                                int saveRecord = wechatActivitySignService.saveRecord(wechatActivitySign);
                                if(saveRecord > 0){
                                    WechatActivitySignSet nicknameSignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "nickname", focusedUserInfo.getNickName(), 1, "", -1, "", 9999, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
                                    wechatActivitySignSetService.saveRecord(nicknameSignSet);
                                    WechatActivitySignSet headimgurlSignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), "headimgurl", focusedUserInfo.getHeadImgUrl(), 1, "", -1, "", 10000, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
                                    wechatActivitySignSetService.saveRecord(headimgurlSignSet);
                                    for(int i=0;i< items.length ;i++){
                                        String[] attribute = items[i].split("%");
                                        System.out.println("attributeLen: " + attribute.length);
                                        String name = attribute[0];
                                        String value = attribute[1];
                                        int sort = Integer.parseInt(attribute[2]);
                                        //组装属性值
                                        WechatActivitySignSet wechatActivitySignSet = new WechatActivitySignSet(ToolsString.putOffBar(ToolsString.putOffBar(UUID.randomUUID().toString())), name, value, 1, "", -1, "", sort, 0, -1, null, id,wechatActivitySign.getId(), 1, new Date(),null);
                                        wechatActivitySignSetService.saveRecord(wechatActivitySignSet);
                                    }
                                    response.getWriter().print("1");
                                }else{
                                    response.getWriter().print("0");
                                }
                            }else{
                                response.getWriter().print("-1");
                            }
                        }
                    }
                    /** 非授权的方式提交报名 */
                    else{
                        List<WechatActivitySignSet> wechatActivitySignSet = wechatActivitySignSetService.getAllByActivityId(id,0);
                        if(wechatActivitySignSet != null){
                            int flag = 0;
                            Iterator<WechatActivitySignSet> iterator = wechatActivitySignSet.iterator();
                            while (iterator.hasNext()){
                                WechatActivitySignSet iteratorBean = iterator.next();
                                if(iteratorBean.getRequired() != -1){ //必须属性
                                    String valuese = iteratorBean.getValuese();
                                    int exitSign = wechatActivitySignSetService.selectUserExitSignBySignSetValue(id,valuese);
                                    flag += exitSign;
                                }
                            }
                            if(flag == 0){

                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }






    /**
     * 活动报名签名方法
     * @param request
     * @param wechatActivitySignSetService
     * @param wechatActivity
     * @param imgUrl
     * @param shareTitle
     * @param shareDesc
     * @param link
     * @param url
     * @param mapKey
     * @param menuId
     * @param id
     */
    private static void processingSharing(HttpServletRequest request,WechatActivitySignSetService wechatActivitySignSetService,WechatActivity wechatActivity,String imgUrl,String shareTitle,String shareDesc,String link,String url,String mapKey,String menuId,String id){
        if(wechatActivity != null){
            /** 支持分享 */
            if(wechatActivity.getShare() != -1){
                if(wechatActivity.getShareImage() != null){
                    /** 获取文件服务器的图片路径 */
                    if(!"".equals(wechatActivity.getShareImage())){
                        String param = "material_id=" + wechatActivity.getShareImage();
                        String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
                        if(json != null){
                            if(!"".equals(json)){
                                JSONObject jsonObject = JSONObject.fromObject(json);
                                /** 设置分享图片 */
                                imgUrl = jsonObject.getString("url");
                            }
                        }
                    }
                }
                /** 设置分享标题和描述 */
                shareTitle = wechatActivity.getShareTitle();
                shareDesc = wechatActivity.getShareDescM();

                /** 配置分享的URL和Image和Desc等信息 */
                request.setAttribute("shareLink", link);
                request.setAttribute("shareImgUrl", imgUrl);
                request.setAttribute("shareTitle", shareTitle);
                request.setAttribute("shareDesc", shareDesc);
                /** 用于页面签名的参数 */
                request.setAttribute("url", url);
                request.setAttribute("mapKey", mapKey);
                request.setAttribute("menuId", menuId);
            }
            /** 查询活动报名配置属性 */
            List<WechatActivitySignSet> wechatActivitySignSet = wechatActivitySignSetService.getAllByActivityId(id,0);
            request.setAttribute("activity",wechatActivity);
            request.setAttribute("attribute",wechatActivitySignSet);
            int setSize = wechatActivitySignSet.size();
            if(wechatActivity.getAuthorised() != -1){setSize = setSize -2;}
            request.setAttribute("setsize",setSize);
        }
    }

}
