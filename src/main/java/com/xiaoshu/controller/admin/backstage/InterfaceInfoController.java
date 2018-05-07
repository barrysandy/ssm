package com.xiaoshu.controller.admin.backstage;

import java.util.*;

import com.xiaoshu.config.util.FormModel;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.InterfaceInfo;
import com.xiaoshu.service.InterfaceInfoService;
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
 * 接口配置表
 * @name: InterfaceInfoController
 * @author: Kun
 * @date: 2018-01-19 10:01
 */
@Controller
@RequestMapping(value = "/interfaceInfo")
public class InterfaceInfoController extends BaseController {

	@Resource
	private InterfaceInfoService interfaceInfoService;
	
	/**
	 * 接口配置表分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-19 10:01
	 */
	@RequestMapping("/list")
	public ModelAndView list(@FormModel("pager")Pager pager, Model model, String search, String startDate, String endDate, String type,String pageSize) {
		Map<String,Object> map = new HashMap<String,Object>(8);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		map.put("type",type);
		if (StringUtils.isNotBlank(startDate)){
			map.put("startDate",startDate);
		}
		if (StringUtils.isNotBlank(endDate)){
			map.put("endDate",endDate + " 23:59:59");
		}
		List<InterfaceInfo> list = interfaceInfoService.selectByPage(map);
		int totalNum = interfaceInfoService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("type",type);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return toVm("admin/interfaceInfo/interfaceInfo_list");
	}
	
	/**
	 * 接口配置表编辑页面
	* @param id 主键ID
	* @return view
	* @author Kun
	* @date 2018-01-19 10:01
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			InterfaceInfo rule = new InterfaceInfo();
			if(StringUtils.isNotBlank(id)){
				rule = interfaceInfoService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/interfaceInfo/interfaceInfo_edit");
	}
	
	/**
	 * 接口配置表预览
	 * @param id
	 * @param model
	 * @return view
	 * @author Kun
	 * @date 2018-01-19 10:01
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		InterfaceInfo rule = new InterfaceInfo();
		if(StringUtils.isNotBlank(id)){
			rule = interfaceInfoService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/interfaceInfo/interfaceInfo_view");
	}
	
	/**
	 * 接口配置表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-19 10:01
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(InterfaceInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					InterfaceInfo rule = new InterfaceInfo();
					if(StringUtils.isNotBlank(id)){
						rule = interfaceInfoService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
//					if(bean.getUrl().indexOf(com.xiaoshu.api.Set.SYSTEM_URL) == -1 ){
//						bean.setUrl(com.xiaoshu.api.Set.SYSTEM_URL + bean.getUrl());
//					}
					interfaceInfoService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
//					if(bean.getUrl().indexOf(com.xiaoshu.api.Set.SYSTEM_URL) == -1 ){
//						bean.setUrl(com.xiaoshu.api.Set.SYSTEM_URL + bean.getUrl());
//					}
					interfaceInfoService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/**
	 * 关键词查询 interfaceInfo/getUrlinterfaces
	 * @param keyes 接口标识
	 * @return url 接口地址
	 * @author XGB
	 * @date 2018-01-30 15:27
	 */
	@ResponseBody
	@RequestMapping(value = "/getUrlinterfaces",produces = "application/json;charset=UTF-8")
	public String getUrlinterfaces(String keyes){
		try{
			if(keyes != null && !"".equals(keyes)){
				String url = interfaceInfoService.selectUrlByKeyes(keyes);
				return url;
			}else{
				return null;
			}

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 接口配置表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-19 10:01
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			interfaceInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}


}
