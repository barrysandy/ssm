package com.xiaoshu.tools.ssmImage;

import com.xiaoshu.api.Api;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsHttpRequest;
import net.sf.json.JSONObject;

/**
 * 字符串Tools
 * 
 * @author XGB
 *
 */
public class ToolsImage {

	/**
	 * 更新文件服务器资源的引用，资源有变化
	 * @param newImage 新资源material_id
	 * @param oldImage 旧资源material_id
	 * @return 更新的结果 1表示更新1条 0表示未更新
	 */
	public static String updateSSMFile(String newImage, String oldImage) {
		String returnStr = "";
		if(!oldImage.equals(newImage)){
			if(!"".equals(newImage)){
				ToolsHttpRequest.sendPost(Api.UPDATE_FILE_STATUS, "material_id=" + newImage);
			}
			if(!"".equals(oldImage)){
				returnStr = ToolsHttpRequest.sendGet(Api.DEL_FILE, "material_id=" + oldImage);
				System.out.println("==>>旧资源清除情况：" + returnStr + " material_id=" + oldImage);
			}
		}
		if("".equals(oldImage) && !"".equals(newImage)){ //保存
			returnStr = ToolsHttpRequest.sendPost(Api.UPDATE_FILE_STATUS, "material_id=" + newImage);
			System.out.println("==>>新资源保存情况：" + returnStr + " material_id=" + newImage);
		}
		return returnStr;
	}

	/**
	 * 删除文件服务器资源的引用，资源删除Delete
	 * @param material_id 资源material_id
	 * @return 删除的结果 1表示删除1条 0表示未删除
	 */
	public static String deleteSSMFile(String material_id) {
		String returnStr = "";
		returnStr = ToolsHttpRequest.sendGet(Api.DEL_FILE, "material_id=" + material_id);
		System.out.println("==>>删除资源情况：" + returnStr + " material_id=" + material_id);
		return returnStr;
	}

	/**
	 * 获取服务器图片
	 * @param material_id
	 * @return
	 */
	public static String getImageUrlByServer(String material_id) {
		String imageUrl = "";
		if(material_id != null){
			if(!"".equals(material_id)){
				//替换素材的在文件服务器上的url
				String param = "material_id=" + material_id;
				String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
				if(json != null){
					if(!"".equals(json)){
						JSONObject jsonObject = JSONObject.fromObject(json);
						imageUrl = jsonObject.getString("url");
					}
				}
			}
		}
		return imageUrl;
	}


	/**
	 * 获取服务器文件的绝对路径
	 * @param material_id
	 * @return
	 */
	public static String getFilePathByServer(String material_id) {
		String filePath = null;
		if(material_id != null){
			if(!"".equals(material_id)){
				//替换素材的在文件服务器上的url
				String param = "material_id=" + material_id;
				String json = ToolsHttpRequest.sendGet(Api.GET_FILE_PATH, param);
				if(json != null){
					if(!"".equals(json)){
						System.out.println("json: " + json);
						JSONObject jsonObject = JSONObject.fromObject(json);
						filePath = jsonObject.getString("diskPath");
						filePath = ToolsASCIIChang.asciiToString(filePath);
					}
				}
			}
		}
		return filePath;
	}

	/**
	 * 传入,分割开的图片字符串，获取第一个（封面图片）
	 * @param arrayImg
	 * @return
	 */
	public static String getFristImageUrlByServer(String arrayImg) {
		String imageUrl = "";
		String[] array = arrayImg.split(",");
		if(array != null) {
			if (array.length > 1) {
				String material_id = array[0];
				if(material_id != null && !"".equals(material_id)){
					//替换素材的在文件服务器上的url
					String param = "material_id=" + material_id;
					String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
					if(json != null){
						if(!"".equals(json)){
							JSONObject jsonObject = JSONObject.fromObject(json);
							imageUrl = jsonObject.getString("url");
						}
					}
				}
			}
		}
		return imageUrl;
	}

}