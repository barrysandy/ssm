package com.xiaoshu.controller.admin.backstage;

import java.util.*;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.SysCodeCategory;
import com.xiaoshu.service.SysCodeCategoryService;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统字典目录表
 * @name: SysCodeCategoryController
 * @author: Kun
 * @date: 2018-01-16 16:29
 */
@Controller
@RequestMapping(value = "/sysCodeCategory")
public class SysCodeCategoryController extends BaseController {

	@Resource
	private SysCodeCategoryService sysCodeCategoryService;
	
	/**
	 * 系统字典目录表分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-16 16:29
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search) {
		Map map = new HashMap(4);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<SysCodeCategory> list = sysCodeCategoryService.selectByPage(map);
		int totalNum = sysCodeCategoryService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("admin/sysCodeCategory/sysCodeCategory_list");
	}
	
	/**
	 * 系统字典目录表编辑页面
	* @param id 主键ID
	* @return view
	* @author Kun
	* @date 2018-01-16 16:29
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			SysCodeCategory rule = new SysCodeCategory();
			if(StringUtils.isNotBlank(id)){
				rule = sysCodeCategoryService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/sysCodeCategory/sysCodeCategory_edit");
	}
	
	/**
	 * 系统字典目录表预览
	 * @param id
	 * @param model
	 * @return view
	 * @author Kun
	 * @date 2018-01-16 16:29
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		SysCodeCategory rule = new SysCodeCategory();
		if(StringUtils.isNotBlank(id)){
			rule = sysCodeCategoryService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/sysCodeCategory/sysCodeCategory_view");
	}
	
	/**
	 * 系统字典目录表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-16 16:29
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(SysCodeCategory bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					/*更新*/
					sysCodeCategoryService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus("1");
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					sysCodeCategoryService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 系统字典目录表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-16 16:29
	 */
	@RequestMapping(value = "/del",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String del(String id){
		try{
			sysCodeCategoryService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
