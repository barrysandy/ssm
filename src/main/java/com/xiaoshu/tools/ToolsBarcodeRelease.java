package com.xiaoshu.tools;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简易生成条形码（EN-13码）
 *
 * @=============================================
 *
 * @author : xgb
 * @create : 2018-5-14 17:40
 * @update :
 * @E-mail : 865815412@qq.com
 * @desc :
 *
 * @=============================================
 */

public class ToolsBarcodeRelease {

    public static final Logger log = LoggerFactory.getLogger(ToolsBarcodeRelease.class);
    /**
     * 生成商品条形码
     *
     * @param savePath  商品条形码图片存放路径：C://barcode//images//
     * @param jbarCode  商品条形码：13位
     * @param imgFormat 图片格式
     * @return 图片存放路径+图片名称+图片文件类型
     */
    public static String createBarCode(String savePath, String jbarCode, String imgFormat, String jbarPathCode) {

        // 校验全部省略……
//         if(StringUtils.isNotEmpty(savePath)){
//            return null;
//         }
//         if(StringUtils.isNotEmpty(jbarCode)){
//            return null;
//         }
//         if(StringUtils.isNotEmpty(imgFormat)){
//            return null;
//         }
//         if( jbarCode.length() != 13){
//            return null;
//         }
        try {
            BufferedImage bi = null;
            int len = jbarCode.length();

            // 实例化JBarcode
            // 这里三个参数，必要填写
            JBarcode jbarcode13 = new JBarcode(EAN13Encoder.getInstance(),
                    WidthCodedPainter.getInstance(),
                    EAN13TextPainter.getInstance());

            // 获取到前12位
            String barCode = jbarCode.substring(0, len - 1);

            // 获取到校验位
            String code = jbarCode.substring(len - 1, len);
            String checkCode = jbarcode13.calcCheckSum(barCode);

            if (!code.equals(checkCode)) {
                return "EN-13 条形码最后一位校验码 不对，应该是： " + checkCode;
            }

            /*
             * 最重要的是这里的设置，如果明白了这里的设置就没有问题 如果是默认设置，
             * 那么设置就是生成一般的条形码 如果不是默认
             * 设置，那么就可以根据自己需要设置
             */

            // 尺寸，面积，大小
            jbarcode13.setXDimension(Double.valueOf(0.8).doubleValue());//值越小密度越细，条形码宽度越宽
            // 条形码高度
            jbarcode13.setBarHeight(Double.valueOf(30).doubleValue());
            // 宽度率
            jbarcode13.setWideRatio(Double.valueOf(20).doubleValue());

            // 是否校验最后一位，默认是false
            jbarcode13.setShowCheckDigit(true);

            // 生成二维码
            bi = jbarcode13.createBarcode(barCode);

            // 定义图片名称
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            String imgName = sdf.format(new Date()) + "_" + jbarCode;

            // 保存二维码图片

            FileOutputStream fileOutputStream = null;
//            String imgPath = savePath + imgName + "." + imgFormat;
            String imgPath = savePath + File.separator  + jbarPathCode + "." + imgFormat;
            try {
                try {
                    savePath = URLDecoder.decode(savePath, "UTF-8");
                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                    savePath = "C://barcode//images//";
                }
                File dirFile = new File(savePath);

                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                fileOutputStream = new FileOutputStream(imgPath);
                log.info("\nsave it imgPath :" + imgPath);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            ImageUtil.encodeAndWrite(bi, imgFormat, fileOutputStream, 96, 96);
            fileOutputStream.close();

            // 返回路径
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  条形码13位校验
     *  步骤1 偶数位之和为a
     *  步骤2 a*3
     *  步骤3 基数位之和为b
     *  步骤4 c = a + b
     *  步骤5 取c的个数位为d，10-d 如果等于10 则为0 并将结果赋值给e
     *
     *  例如：234235654652的校验码的计算如下表：
     *  数据码 校验码
     *  代码位置序号 13 12 11 10 9 8 7 6 5 4 3 2 1
     *  数字码 2 3 4 2 3 5 6 5 4 6 5 2
     *  偶数位 3 + 2 + 5 + 5 + 6 + 2
     *  奇数位 2 + 4 + 3 + 5 + 4 + 5
     *  步骤1：3+2+5+5+6+2=23
     *  步骤2：23*3=69
     *  步骤3：2+4+3+5+4+5=23
     *  步骤4：69+23=92
     *  步骤5：10-2=8步骤6：
     *  校验码为 8
     *  @author xgb
     * @param str 传入的12为纯数字参数
     * @return 返回的字符为13位经过校验的纯数字字符
     */
    public static String getbarCodeAddLast(String str){
        /** 参数校验*/
        if(str == null){return null;}
        if(str != null){if("".equals(str)){return null;}}
        if(str != null){if("".equals(str)){if(str.length() != 12){return null;}}}
        /** 字符切割 */
        int a = 0;
        int b = 0;
        char[] chars = str.toCharArray();
        for(int i = 0; i < chars.length ; i++){
            if(i%2 == 1){ //基数
                String current = chars[i] + "";
                a = Integer.parseInt(current) + a;
            }else if(i%2 == 0){ //偶数
                String current = chars[i] + "";
                b = Integer.parseInt(current) + b;
            }
        }

        //EN-13 位数位校验步骤
        //步骤1 偶数位之和为a
        //已完成在循环
        //步骤2 a*3
        a = a * 3 ;
        //步骤3 基数位之和为b
        //已完成在循环
        //步骤4 c = a + b
        int c =  a + b ;
        String cStr = String.valueOf(c);
        System.out.println(" C code is  :" + cStr);
        //步骤5 取c的个数位为d，10-d 如果等于10 则为0 并将结果赋值给e
        char[] cStrArray = cStr.toCharArray();
        if(cStrArray != null){
            if(cStrArray.length > 0){
                String d = cStrArray[cStrArray.length - 1] + "";
                System.out.println("尾数 last is :" + cStrArray[cStrArray.length - 1] );
                String e = String.valueOf(10 - Integer.parseInt(d));
                if(e.length() >= 2){
                    e = "0";
                }
                str = str + e;
            }
        }
        return str;
    }

    /**
     * @param args
     * @throws InvalidAtributeException
     */
    public static void main(String[] args) throws InvalidAtributeException {
        String jbarCode = ToolsBarcodeRelease.getbarCodeAddLast("829134829134");
        String path = ToolsBarcodeRelease.createBarCode("D://test//", jbarCode, ImageUtil.JPEG,"829134");
        System.out.println(path);
    }
}