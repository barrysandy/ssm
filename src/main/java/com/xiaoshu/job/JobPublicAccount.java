package com.xiaoshu.job;

import com.xiaoshu.api.Api;
import com.xiaoshu.entity.PublicAccountInfo;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.pojo.AccessToken;
import com.xiaoshu.wechat.tools.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by XGB on 2018/01/18
 * 公众号信息任务Map类
 */
@Component
public class JobPublicAccount {
    private static Logger log = LoggerFactory.getLogger(JobPublicAccount.class);

    /**
     * 定时刷新公众号的Map
     */
    // 每1小时执行一次
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void refreshMapJobPublicAccount(){
        ToRefreshMapJobPublicAccount();
    }


    /**
     * 刷新公众号的Map
     */
    public static void ToRefreshMapJobPublicAccount(){
        try {
            String url = Api.GET_ALLPUBLICNUMBER;
            String param = "usable=1";
            ToolsHttpRequest.sendGet(url, param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 更新单个公众号在内存的数据
     * @param parentMenuId 公众号的parentId
     * @param bean 需要写入/更新内存PublicAccountInfo对象
     * @author XGB
     * @date 2018-03-16 16:41
     */
    public static void updateMapData(String parentMenuId,PublicAccountInfo bean){
        //将启用的公众号参数加入Map中
        MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
        Map<String, String> map = mapPublicNumber.getMap();
        map.put("appId"+ parentMenuId ,bean.getAppId());
        map.put("appSecret"+ parentMenuId ,bean.getAppSecret());
        map.put("token"+ parentMenuId ,bean.getToken());
        map.put("openPlatform"+ parentMenuId ,bean.getOpenPlatform());//绑定的公众平台
        map.put("mchId"+ parentMenuId ,bean.getMchId());//支付的商户号
        map.put("mchKey"+ parentMenuId ,bean.getMchKey());//支付的秘钥
        map.put("notifyUrl"+ parentMenuId ,bean.getNotifyUrl());//支付成功通知地址
        map.put("notifyErrorUrl"+ parentMenuId ,bean.getNotifyErrorUrl());//支付失败通知地址
        map.put("accountType"+ parentMenuId ,bean.getAccountType()+"");//公众号类型
        map.put("effectiveTime"+ parentMenuId ,bean.getEffectiveTime()+"");//公众号AccessToken刷新时间
        log.info("------------ [log.info System Message] Update : ID: " + parentMenuId +" begin!! ------------");
        log.info("------------ [log.info System Message] : appId: " + map.get("appId" + parentMenuId));
        log.info("------------ [log.info System Message] : appSecret: " + map.get("appSecret" + parentMenuId) );
        log.info("------------ [log.info System Message] : token: " + map.get("token" + parentMenuId) );
        log.info("------------ [log.info System Message] : openPlatform: " + map.get("openPlatform" + parentMenuId) );
        log.info("------------ [log.info System Message] : mchId: " + map.get("mchId" + parentMenuId) );
        log.info("------------ [log.info System Message] : notifyUrl: " + map.get("notifyUrl" + parentMenuId) );
        log.info("------------ [log.info System Message] : notifyErrorUrl: " + map.get("notifyErrorUrl" + parentMenuId) );
        log.info("------------ [log.info System Message] : effectiveTime: " + map.get("effectiveTime" + parentMenuId) );
        try{
            //获取accessToken
            AccessToken accessToken = WeixinUtil.getAccessToken(bean.getAppId(),bean.getAppSecret());
            if(accessToken != null){
                //put accessToken
                map.put("accessToken"+ parentMenuId ,accessToken.getAccessToken());//accessToken
                log.info("------------ [log.info System Message] : Update the data of a single public number in memory By PublicAccountInfo parentMenuId = " + parentMenuId + " AccessToken is " + accessToken.getAccessToken() +"  ------------");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        log.info("------------ [log.info System Message] Update : ID: " + parentMenuId +" complete!! ------------");
    }


}
