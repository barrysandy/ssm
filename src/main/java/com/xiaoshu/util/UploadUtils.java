package com.xiaoshu.util;

/**
 * Created by Kun on 2017/12/22 0022.
 */

import net.mikesu.fastdfs.FastdfsClient;
import net.mikesu.fastdfs.FastdfsClientFactory;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传公共类
 * @author zhou.zhengkun
 * @date 2017/12/22 0022 09:42
 */
public class UploadUtils {

    private final static String CONFIG_FILE = "FastdfsClient.properties";
    private static String FILE_READ_URL = null;

    static {
        try {
            Configuration config = new PropertiesConfiguration(CONFIG_FILE);
            FILE_READ_URL = config.getString("file_read_url");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件 返回的应为一个字符换代码
     * @param file 文件
     * @return String
     * @author zhou.zhengkun
     * @date 2017/12/22 0022 10:16
     */
    public static String uploadFile(MultipartFile file, Map meta) throws Exception {
        //获取FastdsfClient类
        FastdfsClient fastdfsClient = FastdfsClientFactory.getFastdfsClient();
        String fileId = null;
        if (MapUtils.isEmpty(meta)) {
            Map meta2 = new HashMap<String, Object>(2);
            fileId = fastdfsClient.upload(file, meta2);
        } else {
            fileId = fastdfsClient.upload(file, meta);
        }
        return fileId;
    }

    public static String uploadFile(File file){
        //获取FastDFSClient类
        FastdfsClient fastdfsClient = FastdfsClientFactory.getFastdfsClient();
        String  fileId= null;
        try {
            fileId = fastdfsClient.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileId;
    }

    /***
     * 组装可访问的url
     * @param fileId 服务器返回的文件码
     * @return realUrl
     */
    public static String getFileUrl(String fileId) {
        try {
            if (StringUtils.isBlank(fileId)){
                return null;
            }else{
                return "http://"+FILE_READ_URL +"/"+ fileId;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传文件后 直接组装可访问的路径
     * @param file 文件
     * @return String(完整URL)
     * @author zhou.zhengkun
     * @date 2017/12/22 0022 10:19
     */
    public static String getUrlByUploadFile(MultipartFile file, Map meta) {
        //获取FastDFSClient类
        FastdfsClient fastdfsClient = FastdfsClientFactory.getFastdfsClient();
        String fileId = null;
        String url = null;
        if (MapUtils.isEmpty(meta)) {
            Map meta2 = new HashMap<String, Object>(4);
            try {
                fileId = fastdfsClient.upload(file, meta2);
                url = FILE_READ_URL + fileId;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                fileId = fastdfsClient.upload(file, meta);
                url = FILE_READ_URL + fileId;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * 删除服务器文件
     * @param fileId
     * @return boolean
     * @author zhou.zhengkun
     * @date 2017/12/22 0022 10:18
     */
    public static boolean removeFileById(String fileId){
        if(StringUtils.isBlank(fileId)){
            return false;
        }
        String suffix=null;
        FastdfsClient fastdfsClient = FastdfsClientFactory.getFastdfsClient();
        try {
            return fastdfsClient.delete(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
