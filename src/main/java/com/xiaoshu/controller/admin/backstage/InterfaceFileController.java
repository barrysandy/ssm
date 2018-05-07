package com.xiaoshu.controller.admin.backstage;

import com.xiaoshu.config.util.FormModel;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.InterfaceInfo;
import com.xiaoshu.service.InterfaceInfoService;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * 接口配置表
 * @name: InterfaceInfoController
 * @author: XGB
 * @date: 2018-05-2 18:16
 */
@Controller
@RequestMapping(value = "/interfaceInfo")
public class InterfaceFileController extends BaseController {

	@Resource
	private InterfaceInfoService interfaceInfoService;

	/**
	 * ssm_file文件系统接口配置表分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author XGB
	 * @date 2018-05-2 18:10
	 */
	@RequestMapping("/listFile")
	public ModelAndView listFile(@FormModel("pager")Pager pager, Model model, String search, String startDate, String endDate, String type) {
		if(type == null){
			type = "3";
		}
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
		return toVm("admin/interfaceInfoFile/interfaceInfo_list");
	}


	/**
	 * 接口配置表编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author XGB
	 * @date 2018-05-2 18:10
	 */
	@RequestMapping("/toEditFile")
	public ModelAndView toEditFile(String id,Model model){
		try{
			InterfaceInfo rule = new InterfaceInfo();
			if(StringUtils.isNotBlank(id)){
				rule = interfaceInfoService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/interfaceInfoFile/interfaceInfo_edit");
	}

}
