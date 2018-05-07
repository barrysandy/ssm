package com.xiaoshu.controller.admin.activity;

import com.xiaoshu.api.Api;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.WechatActivity;
import com.xiaoshu.entity.WechatActivityPrize;
import com.xiaoshu.service.WechatActivityPrizeService;
import com.xiaoshu.service.WechatActivityService;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * 微信活动奖品表
 * @name: WechatActivityPrizeController
 * @author: XGB
 * @date: 2018-02-11 10:51
 */
@Controller
@RequestMapping(value = "/activityPrize")
public class WechatActivityPrizeController extends BaseController {

	@Resource private WechatActivityService wechatActivityService;
	@Resource private WechatActivityPrizeService wechatActivityPrizeService;

	/**
	 * 分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author XGB
	 * @date 2018-02-06 17:01
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search,String menuid) {
		Map map = new HashMap(8);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		map.put("menuid",menuid);
		List<WechatActivityPrize> list = wechatActivityPrizeService.selectByPage(map);
		if(list != null){
			Iterator<WechatActivityPrize> iterator = list.iterator();
			while (iterator.hasNext()){
				WechatActivityPrize wechatActivityPrizeTemp = iterator.next();
				String wechatActivityTitle = wechatActivityService.getTitleByPrimaryKey(wechatActivityPrizeTemp.getWechatActivityId());
				if(wechatActivityTitle != null){
					wechatActivityPrizeTemp.setWechatActivityId(wechatActivityTitle);
				}
			}
		}
		int totalNum = wechatActivityPrizeService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		model.addAttribute("menuid",menuid);
		return toVm("admin/wechatActivity/wechatActivityPrize_list");
	}

	/**
	 * 编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author XGB
	 * @date 2018-02-11 11:28
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuid){
		try{
			WechatActivityPrize rule = new WechatActivityPrize();
			rule.setCommodityId(-1);
			if(StringUtils.isNotBlank(id)){
				rule = wechatActivityPrizeService.getByPrimaryKey(id);
			}
			if(rule != null){
				if(rule.getImage() != null){
					if(!"".equals(rule.getImage())){
						//替换素材的在文件服务器上的url
						String param = "material_id=" + rule.getImage();
						String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
						if(json != null){
							if(!"".equals(json)){
								JSONObject jsonObject = JSONObject.fromObject(json);
								String image = jsonObject.getString("url");
								model.addAttribute("image",image);
							}
						}
					}
				}
			}

			//查询全部可用的活动，ID和TITLE
			List<WechatActivity> activityList = wechatActivityService.getAllActivity();
			model.addAttribute("activityList", activityList);
			model.addAttribute("bean",rule);
			model.addAttribute("menuid",menuid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivityPrize_edit");
	}

	/**
	 * 预览
	 * @param id
	 * @param model
	 * @return
	 * @author XGB
	 * @date 2018-02-11 14:20
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model,String menuid){
		WechatActivityPrize rule = new WechatActivityPrize();
		try{
			if(StringUtils.isNotBlank(id)){
				rule = wechatActivityPrizeService.getByPrimaryKey(id);
			}
			if(rule != null){
				if(rule.getImage() != null){
					if(!"".equals(rule.getImage())){
						//替换素材的在文件服务器上的url
						String param = "material_id=" + rule.getImage();
						String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
						if(json != null){
							if(!"".equals(json)){
								JSONObject jsonObject = JSONObject.fromObject(json);
								String image = jsonObject.getString("url");
								model.addAttribute("image",image);
							}
						}
					}
				}
			}
			model.addAttribute("bean",rule);
		}catch (Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivityPrize_view");
	}

	/**
	 * 保存操作
	 * @param bean 实体类
	 * @return String
	 * @author XGB
	 * @date 2018-02-11 10:57
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(WechatActivityPrize bean){
		String oldImage = "";//旧资源
		String newImage = bean.getImage();//新资源
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					WechatActivityPrize oldWechatActivityPrize = wechatActivityPrizeService.getByPrimaryKey(id);
					if(oldWechatActivityPrize != null){
						if(oldWechatActivityPrize.getImage() != null){
							if( !"".equals(oldWechatActivityPrize.getImage())){
								oldImage = oldWechatActivityPrize.getImage();//旧资源
							}
						}
					}
					bean.setUpdateTime(new Date());
					wechatActivityPrizeService.updateRecord(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(ToolsString.putOffBar(u.toString()));
					bean.setCreateTime(new Date());
					wechatActivityPrizeService.saveRecord(bean);
				}

				//更新文件服务器资源的引用
				ToolsImage.updateSSMFile(newImage,oldImage);
			}
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}


	/**
	 * 删除
	 * @param id 主键ID
	 * @return String
	 * @author XGB
	 * @date 2018-02-11 10:57
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			WechatActivityPrize wechatActivityPrize= wechatActivityPrizeService.getByPrimaryKey(id);
			wechatActivityPrizeService.delete(id);
			//删除资源
			ToolsImage.deleteSSMFile(wechatActivityPrize.getImage());
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
}
