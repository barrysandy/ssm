package com.xiaoshu.job;

import com.xiaoshu.api.Api;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import static com.xiaoshu.tools.ToolsHttpRequest.*;

/**
 * Created by XGB on 2018/05/4
 * 扫描任务
 */
@Component
public class JobScan {

    private int count = 0;

    /**
     * 定时刷新商品
     */
    // 每3min 执行一次
    @Scheduled(cron = "0 0/3 * * * ? ")
    public void sacn(){
        //设置第一次不执行
        count++;
        if(count % 10 == 1 && count != 0){
            //刷新商品的状态
            sendGet(Api.SCAN_COMMODITY_STATE,null);
            //扫描组团
            sendGet(Api.SCAN_GROUP,null);
        }
        if(count >= 300){
            count = 0;
        }

    }


}
