package com.xiaoshu.controller.admin.wechat;

import com.xiaoshu.api.Api;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.Art;
import com.xiaoshu.service.ArtService;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * 文章
 * @name: ArtController
 * @author: Kun
 * @date: 2018-01-09 10:49
 */
@Controller
@RequestMapping(value = "/art")
public class ArtController extends BaseController {

	@Resource
	private ArtService artService;

	/**
	 * 文章分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-09 10:49
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search,String menuid) {
		Map map = new HashMap(8);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		map.put("menuId",menuid);
		List<Art> list = artService.selectByPage(map);
		int totalNum = artService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		model.addAttribute("menuId",menuid);
		return toVm("admin/art/art_list");
	}
	
	/**
	 * 文章编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author Kun
	 * @date 2018-01-09 10:49
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuId){
		try{
			Art rule = new Art();
			if(StringUtils.isNotBlank(id)){
				rule = artService.selectByPrimaryKey(id,1);
			}
			model.addAttribute("bean", rule);
			model.addAttribute("menuId",menuId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/art/art_edit");
	}

	/**
	 * 文章预览
	 * @param id
	 * @param model
	 * @return
	 * @author Kun
	 * @date 2018-01-09 10:49
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		Art rule = new Art();
		if(StringUtils.isNotBlank(id)){
			rule = artService.selectByPrimaryKey(id,1);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/art/art_view");
	}

	/**
	 * 文章保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-09 10:49
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(Art bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					bean.setUpdateTime(new Date());
					Art oldbean = artService.selectByPrimaryKey(id,0);
					if(!oldbean.getArtImg().equals(bean.getArtImg())){//图片更新了
						//删除旧文件服务器的引用及文件
						if(!"".equals(oldbean.getArtImg())){
							String url = Api.DEL_FILE;
							String param = "material_id=" + oldbean.getArtImg();
							String json = ToolsHttpRequest.sendGet(url, param);
						}
					}
					artService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus("1");
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					artService.insert(bean);
				}
				//保存新引用
				String url = Api.UPDATE_FILE_STATUS;
				String param = "material_id=" + bean.getArtImg();
				ToolsHttpRequest.sendPost(url, param);
			}
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/**
	 * 文章删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-09 10:49
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			Art bean = artService.selectByPrimaryKey(id,0);
			if(bean != null){
				int i = artService.delete(id);
				if(i> 0){
					if(bean.getArtImg() != null){
						//删除文件服务器的引用及文件
						if(!"".equals(bean.getArtImg())){
							String url = Api.DEL_FILE;
							String param = "material_id=" + bean.getArtImg();
							String json = ToolsHttpRequest.sendGet(url, param);
						}
					}
				}
			}

		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);

	}

	/**
	 * 分期查询文章 art/interfaceGetPeriodArt
     * @param menuId
	 * @param current 期号
	 * @return List
	 * @author zhou.zhengkun
	 * @date 2018/01/17 0017 14:26
	 */
	@RequestMapping(value = "/interfaceGetPeriodArt" ,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String interfaceGetPeriodArt(@RequestParam("menuId") String menuId, @RequestParam("current") Integer current){
		try {
            List<Art> list = artService.selectByPeriod(menuId,current);
            return JSONUtils.toJSONString(list);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询本期文章 art/interfaceGetCurrentPeriodArt
	 * @param menuId
	 * @return Json格式的List<Art>实体类
	 * @author zhou.zhengkun
	 * @date 2018/01/17 0017 14:52
	 */
	@RequestMapping(value = "/interfaceGetCurrentPeriodArt",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCurrentPeriodArt(String menuId){
		try {
			return artService.getCurrentPeriodArt(menuId);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
