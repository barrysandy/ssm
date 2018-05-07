package com.xiaoshu.wechat.payrefund;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * 微信公众号退款工具类
 * @author: XGB
 * @date: 2018-04-02 09:50
 */

public class ClientCustomSSL {

    /**
     * 微信退款业务
     * @param file 微信商户的的加密文件
     * @param apiUrl 退款接口地址
     * @param xml 退款组装的xml数据
     * @param mchKey
     * @throws Exception
     */
    public static String doWechatRefund(File file , String apiUrl,String xml,String mchKey) throws Exception {
        String jsonStr = null;
        /** 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的 */
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream fileInputStream = new FileInputStream(file);//P12文件目录
        try {
            keyStore.load(fileInputStream, mchKey.toCharArray());//这里写密码..默认是你的MCHID
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            fileInputStream.close();
        }

        //SSL协议向微信服务器发起退款请求
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchKey.toCharArray()).build();//这里也是写密码的
        // 只允许TLSv1协议
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httpost = new HttpPost(apiUrl); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(xml, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                response.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            httpclient.close();
        }
        return jsonStr;
    }

}
