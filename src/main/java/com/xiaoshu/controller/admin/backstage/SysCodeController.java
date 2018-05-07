package com.xiaoshu.controller.admin.backstage;

import java.util.*;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.SysCode;
import com.xiaoshu.service.SysCodeService;
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
 * 系统字典表
 * @name: SysCodeController
 * @author: Kun
 * @date: 2018-01-16 16:28
 */
@Controller
@RequestMapping(value = "/sysCode")
public class SysCodeController extends BaseController {

	@Resource
	private SysCodeService sysCodeService;
	
	/**
	 * 系统字典表分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-16 16:28
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<SysCode> list = sysCodeService.selectByPage(map);
		int totalNum = sysCodeService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("admin/sysCode/sysCode_list");
	}
	
	/**
	 * 系统字典表编辑页面
	* @param id 主键ID
	* @return view
	* @author Kun
	* @date 2018-01-16 16:28
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String categoryId){
		try{
			SysCode rule = new SysCode();
			if(StringUtils.isNotBlank(id)){
				rule = sysCodeService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
			if (StringUtils.isBlank(categoryId)){
				categoryId = rule.getCategoryId();
			}
			model.addAttribute("categoryId",categoryId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/sysCode/sysCode_edit");
	}
	
	/**
	 * 系统字典表预览
	 * @param id
	 * @param model
	 * @return view
	 * @author Kun
	 * @date 2018-01-16 16:28
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		SysCode rule = new SysCode();
		if(StringUtils.isNotBlank(id)){
			rule = sysCodeService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/sysCode/sysCode_view");
	}
	
	/**
	 * 系统字典表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-16 16:28
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(SysCode bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					SysCode rule = new SysCode();
					if(StringUtils.isNotBlank(id)){
						rule = sysCodeService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus("1");
					sysCodeService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus("1");
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					sysCodeService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 系统字典表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-16 16:28
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			sysCodeService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/**
	 * 根据目录ID查询字典数据
	 * @param categoryId 目录ID
	 * @return List
	 * @author zhou.zhengkun
	 * @date 2018/01/18 0018 10:25
	 */
	@RequestMapping(value = "interface/getListByCategoryId" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getListByCategoryId(String categoryId){
		try {
			if (StringUtils.isBlank(categoryId)){
				return JsonUtils.turnJson(false,"参数错误",null);
			} else{
				return sysCodeService.getListByCategoryId(categoryId);
			}
		}catch (Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error " +e.getMessage() ,e);
		}
	}
}
