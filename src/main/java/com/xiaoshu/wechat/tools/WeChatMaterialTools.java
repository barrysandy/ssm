package com.xiaoshu.wechat.tools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.api.WeChatAPI;

import com.xiaoshu.wechat.pojo.WeixinUploadNews;
import net.sf.json.JSONObject;

/**
 * 微信素材工具类
 *
 */
public class WeChatMaterialTools {


    /**
     * 上传其他永久素材(图片素材的上限为5000，其他类型为1000)
     * @param menuId 公众号标识
     * @param fileurl 媒体路径E://apache-tomcat-6/webapps/fxy/img/icon/kefu001.jpg
     * @param type 媒体类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return 素材素材成功后的media_id
     */
    public static String addMaterial(String menuId,String fileurl, String type) {
        try {
            //上传素材
            //读取Map中的token
            MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
            Map<String, String> map = mapPublicNumber.getMap();
            if(map != null && map.size() == 0){
                System.out.println("===>>map not data");
                JobPublicAccount.ToRefreshMapJobPublicAccount();
                return "map not data accessToken";
            }
            String accessToken = map.get("accessToken" + menuId);
            String path = WeChatAPI.Add_Material.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
//            String result = WeChatMaterialTools.connectHttpsByPost(path, new File(fileurl)).replaceAll("[\\\\]", "");
            File file = new File(fileurl);
            if(file.exists()){
                String result = WeChatMaterialTools.connectHttpsByPost(path, file);
                JSONObject resultJSON = JSONObject.fromObject(result);
                if (resultJSON != null) {
                    if (resultJSON.get("media_id") != null) {
                        System.out.println("===>>resultJSON" + resultJSON);
                        return (String)resultJSON.get("media_id");
                    } else {
                        Integer errcode = (Integer) resultJSON.get("errcode");
                        System.out.println("errcode：" + errcode);
                        return "upload Permanent material failure";
                    }
                }
            }else {
                return "file not find";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }


	/**
	 * Http发送POST 保存微信素材图片 请求
	 * @param path 请求路径
	 * @param file 文件路径  [格式   F://1.jpg]
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String connectHttpsByPost(String path,File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL urlObj = new URL(path);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        String result = null;
        con.setDoInput(true);

        con.setDoOutput(true);

        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="
                        + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\""

                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("Sending an exception to the POST request！" + e);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }
	
	/**
	 * 删除微信素材
	 * @param access_token 全局access_token
	 * @param media_id 素材的media_id
	 * @return 返回 0(成功)/失败信息
	 * @throws Exception
	 */
	public static String deleteMaterial(String access_token,String media_id) throws Exception {
	    // 请求地址    
	    String GetReqUrl = "https://qyapi.weixin.qq.com/cgi-bin/material/del?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";    
	    // 测试用的 media_id ，来自上传永久图文素材接口    
	    // 替换 GetReqUrl 中的参数    
	    GetReqUrl = GetReqUrl.replace("ACCESS_TOKEN", access_token).replace("MEDIA_ID", media_id);  
	    System.out.println("请求地址："+GetReqUrl);
	    // 开始请求    
	    JSONObject JSONResult = ToolsHttpConnectUtil.httpRequest(GetReqUrl, "GET", null);    
	    // 打印结果    
	    if (JSONResult != null) {    
	        System.out.println("删除成功！");    
	        System.out.println(JSONResult.toString());    
	    } else {    
	        System.out.println("删除失败！");    
	    }
		return media_id;
		
	}


    /**
     * 上传图文消息素材，获取"media_id;或用于群发消息"
     * @param access_token
     * @param type  ①"uploadFodderUrl"(上传图文消息) ②"sendMessageUrl"(群发消息) ③"delMessageUrl"( 删除消息，但消息卡片无法删除)
     * @return
     */
    public static String jsonInfoUpload(String access_token, String type){
//      org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
//      String posturl = String.format("%s?access_token=%s", uploadurl,access_token);
        String url = null;
        String param = null;
        if ("uploadFodderUrl".equals(type)){
            url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + access_token;
            param = "media_id";
        }else if("sendMessageUrl".equals(type)){
            url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + access_token;
            param = "errmsg";
        }else if("delMessageUrl".equals(type)){
            url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=" + access_token;
            param = "errmsg";
        }
        String responseContent = ToolsHttpRequest.sendPost(url,param);
        System.out.println(responseContent);
        return responseContent;
    }

    public static String uploadNews(String access_token, String type){
        StringBuffer sb = new StringBuffer("\"articles\": [");
        for(int i = 0; i< 2; i++ ){
            WeixinUploadNews weixinUploadNews = new WeixinUploadNews();
            weixinUploadNews.setAuthor("xxx");
            weixinUploadNews.setContent("content");
            weixinUploadNews.setContent_source_url("www.qq.com");
            weixinUploadNews.setDigest("digest");
            weixinUploadNews.setThumb_media_id("qI6_Ze_6PtV7svjolgs-rN6stStuHIjs9_DidOHaj0Q-mwvBelOXCFZiq2OsIU-p");
            weixinUploadNews.setShow_cover_pic("0");

        }
        String json = "";
        return "";
    }


}
