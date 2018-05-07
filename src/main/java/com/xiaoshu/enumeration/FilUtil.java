package com.xiaoshu.enumeration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 魔数文件上传的类型判断的工具栏
 * @author XGB
 * @data 2018-4-8 20:45
 */
public class FilUtil {

    /** 判断文件类型  */
    public static FileType getType(String filePath) throws IOException {
        // 获取文件头
        String fileHead = getFileHeader(filePath);

        if (fileHead != null && fileHead.length() > 0) {
            fileHead = fileHead.toUpperCase();
            FileType[] fileTypes = FileType.values();

            for (FileType type : fileTypes) {
                if (fileHead.startsWith(type.getValue())) {
                    return type;
                }
            }
        }

        return null;
    }

    /** 读取文件头 */
    private static String getFileHeader(String filePath) throws IOException {
    	//这里需要注意的是，每个文件的魔数的长度都不相同，因此需要使用startwith
        byte[] b = new byte[28];
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
            inputStream.read(b, 0, 28);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return bytesToHex(b);
    }

    /** 将字节数组转换成16进制字符串 */
    public static String bytesToHex(byte[] src){  
        StringBuilder stringBuilder = new StringBuilder("");  
        if (src == null || src.length <= 0) {  
            return null;  
        }
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    } 

}