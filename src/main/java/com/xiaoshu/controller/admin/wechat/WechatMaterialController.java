package com.xiaoshu.controller.admin.wechat;

import java.util.*;

import com.xiaoshu.api.Api;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.controller.wechat.WeChatController;
import com.xiaoshu.entity.WechatMaterial;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.service.WechatMaterialService;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.wechat.api.WeChatAPI;
import com.xiaoshu.wechat.tools.WeChatMaterialTools;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 微信素材
 * @name: WechatMaterialController
 * @author: Kun
 * @date: 2018-01-23 10:41
 */
@Controller
@RequestMapping(value = "/wechatMaterial")
public class WechatMaterialController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(WeChatController.class);

	@Resource
	private WechatMaterialService wechatMaterialService;
	
	/**
	 * 微信素材分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-23 10:41
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search,String menuid,String parentId) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		map.put("menuId",menuid);
		List<WechatMaterial> list = wechatMaterialService.selectByPage(map);
		int totalNum = wechatMaterialService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		model.addAttribute("menuId",menuid);
		model.addAttribute("parentMenuId",parentId);
		return toVm("admin/wechatMaterial/wechatMaterial_list");
	}
	
	/**
	 * 微信素材编辑页面
	* @param id 主键ID
	* @return view
	* @author Kun
	* @date 2018-01-23 10:41
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuId,String parentMenuId){
		try{
			WechatMaterial rule = new WechatMaterial();
			if(StringUtils.isNotBlank(id)){
				rule = wechatMaterialService.selectByPrimaryKey(id);
				model.addAttribute("menuId",rule.getMenuId());
				model.addAttribute("parentMenuId",rule.getParentMenuId());
				if(rule != null){
					if(rule.getTypese() != null && rule.getUrl() != null){
						if("image".equals(rule.getTypese())){
							if (!"".equals(rule.getUrl())){
								String param = "material_id=" + rule.getUrl();
								String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
								if(json != null){
									if(!"".equals(json)){
										JSONObject jsonObject = JSONObject.fromObject(json);
										String image = jsonObject.getString("url");
										model.addAttribute("image",image);
									}else{
										rule.setUrl("无效的material_id，该资源已经被删除了。");
									}
								}else{
									rule.setUrl("无效的material_id，该资源已经被删除了。");
								}
							}
						}
						if("voice".equals(rule.getTypese())){
							if (!"".equals(rule.getUrl())){
								String param = "material_id=" + rule.getUrl();
								String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
								if(json != null){
									if(!"".equals(json)){
										JSONObject jsonObject = JSONObject.fromObject(json);
										String voice = jsonObject.getString("url");
										model.addAttribute("voice",voice);
									}else{
										rule.setUrl("无效的material_id，该资源已经被删除了。");
									}
								}else{
									rule.setUrl("无效的material_id，该资源已经被删除了。");
								}
							}
						}

						if("video".equals(rule.getTypese())){
							if (!"".equals(rule.getUrl())){
								String param = "material_id=" + rule.getUrl();
								String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
								if(json != null){
									if(!"".equals(json)){
										JSONObject jsonObject = JSONObject.fromObject(json);
										String video = jsonObject.getString("url");
										model.addAttribute("video",video);
									}else{
										rule.setUrl("无效的material_id，该资源已经被删除了。");
									}
								}else{
									rule.setUrl("无效的material_id，该资源已经被删除了。");
								}
							}
						}

						if("thumb".equals(rule.getTypese())){
							if (!"".equals(rule.getUrl())){
								String param = "material_id=" + rule.getUrl();
								String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
								if(json != null){
									if(!"".equals(json)){
										JSONObject jsonObject = JSONObject.fromObject(json);
										String thumb = jsonObject.getString("url");
										model.addAttribute("thumb",thumb);
									}else{
										rule.setUrl("无效的material_id，该资源已经被删除了。");
									}
								}else{
									rule.setUrl("无效的material_id，该资源已经被删除了。");
								}
							}
						}

					}
				}
			}else{
				model.addAttribute("menuId",menuId);
				model.addAttribute("parentMenuId",parentMenuId);
//				model.addAttribute("image","");
			}
			model.addAttribute("bean", rule);

		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatMaterial/wechatMaterial_edit");
	}
	
	/**
	 * 微信素材预览
	 * @param id
	 * @param model
	 * @return view
	 * @author Kun
	 * @date 2018-01-23 10:41
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		WechatMaterial rule = new WechatMaterial();
		if(StringUtils.isNotBlank(id)){
			rule = wechatMaterialService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/wechatMaterial/wechatMaterial_view");
	}
	
	/**
	 * 微信素材保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-23 10:41
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(WechatMaterial bean,HttpServletRequest req, HttpServletResponse resp){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					WechatMaterial rule = new WechatMaterial();
					if(StringUtils.isNotBlank(id)){
						rule = wechatMaterialService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					if( (!rule.getUrl().equals(bean.getUrl())) || ("0".equals(bean.getMaterial_id()) || "upload Permanent material failure".equals(bean.getMaterial_id())
							|| "file not find".equals(bean.getMaterial_id()) || "map not data accessToken".equals(bean.getMaterial_id()) ) ){//如果素材url发生了改变，则重新更新material_id
						saveOrUpdateUtil(bean,wechatMaterialService,1);
						//移除旧资源引用
						//删除文件服务器的引用及文件
						String url = Api.DEL_FILE;
						String param = "material_id=" + rule.getUrl();
						String json = ToolsHttpRequest.sendGet(url, param);
						if("1".equals(json)){
							del_material(rule.getMaterial_id(),rule.getParentMenuId());
						}
						log.info("System Msg [file server] delete Old File material_id: " + rule.getUrl()+ " Total: " + json);
					}else{
						wechatMaterialService.update(bean);
					}

				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					saveOrUpdateUtil(bean,wechatMaterialService,0);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 微信素材删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-23 10:41
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			WechatMaterial bean = wechatMaterialService.selectByPrimaryKey(id);
			if(bean != null){
				int i = wechatMaterialService.delete(id);
				if(i> 0){
					if(bean.getUrl() != null){
						//删除文件服务器的引用及文件
						if(!"".equals(bean.getUrl())){
							String url = Api.DEL_FILE;
							String param = "material_id=" + bean.getUrl();
							String json = ToolsHttpRequest.sendGet(url, param);
							if("1".equals(json)){
								del_material(bean.getMaterial_id(),bean.getParentMenuId());
							}
						}
					}

				}
			}

		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}


	/**
	 * 删除上传的永久素材
	 * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param media_id
	 * @param menuId
	 * @return
	 */
	public static String del_material(String media_id,String menuId) {
		try {

			//读取Map中的token
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> map = mapPublicNumber.getMap();
			if(map != null && map.size() == 0){
				System.out.println("map not data");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
			String accessToken = map.get("accessToken" + menuId);
			String url = WeChatAPI.Del_Material.replace("ACCESS_TOKEN", accessToken);
			String param = "media_id=" + media_id;
			String str = ToolsHttpRequest.sendPost(url, param);
			System.out.println("Delete WeChat material :" + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 保存或者更新素材
	 * @param bean 素材实体类
	 * @param wechatMaterialService
	 * @param type 如果为0为插入 1为更新
	 */
	public static String saveOrUpdateUtil(WechatMaterial bean,WechatMaterialService wechatMaterialService,int type) {
		String returnStr = "0";//调用素材上传方法的返回值
		int saveOrupdate = 0;//调用保存或更新接口的返回值
		if (bean != null) {
			if (bean.getTypese() != null && bean.getUrl() != null) {
				if (!"".equals(bean.getTypese()) && !"".equals(bean.getUrl())) {
					String param = "material_id=" + bean.getUrl();
					String json = ToolsHttpRequest.sendGet(Api.GET_FILE_PATH, param);
					if (json != null) {
						if (!"".equals(json)) {
							JSONObject jsonObject = JSONObject.fromObject(json);
							String diskPath = jsonObject.getString("diskPath");
							diskPath = ToolsASCIIChang.asciiToString(diskPath);
							String material_id = WeChatMaterialTools.addMaterial(bean.getParentMenuId(), diskPath, bean.getTypese());
							bean.setMaterial_id(material_id);
							returnStr = material_id;
						}
					}
				}
			}
			if (type == 0) {
				saveOrupdate = wechatMaterialService.insert(bean);
			} else {
				saveOrupdate = wechatMaterialService.update(bean);
			}
			if(saveOrupdate > 0){
				//更新文件服务器资源的引用
				if(!"0".equals(bean.getMaterial_id()) && !"upload Permanent material failure".equals(bean.getMaterial_id())
						&& !"file not find".equals(bean.getMaterial_id()) && !"map not data accessToken".equals(bean.getMaterial_id())){//returnStr不为0,1空表示为material_id是微信服务器返回的，素材上传成功的
					String url = Api.UPDATE_FILE_STATUS;
					String param = "material_id=" + bean.getUrl();
					ToolsHttpRequest.sendPost(url, param);
				}
			}

		}
		return returnStr;
	}
}
