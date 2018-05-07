package com.xiaoshu.controller.admin.backstage;

import java.util.*;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.FocusedUserGroup;
import com.xiaoshu.service.FocusedUserGroupService;
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
 * 关注用户分组表
 * @name: FocusedUserGroupController
 * @author: Kun
 * @date: 2018-01-10 10:32
 */
@Controller
@RequestMapping(value = "/focusedUserGroup")
public class FocusedUserGroupController extends BaseController {

	@Resource
	private FocusedUserGroupService focusedUserGroupService;

	/**
	 * 关注用户分组表分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-10 10:32
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search,String menuid) {
		Map map = new HashMap(8);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		map.put("menuId",menuid);
		List<FocusedUserGroup> list = focusedUserGroupService.selectByPage(map);
		int totalNum = focusedUserGroupService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		model.addAttribute("menuId",menuid);
		return toVm("admin/focusedUserGroup/focusedUserGroup_list");
	}
	
	/**
	 * 关注用户分组表编辑页面
	* @param id 主键ID
	* @return view
	* @author Kun
	* @date 2018-01-10 10:32
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuId){
		try{
			FocusedUserGroup rule = new FocusedUserGroup();
			if(StringUtils.isNotBlank(id)){
				rule = focusedUserGroupService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
			model.addAttribute("menuId",menuId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/focusedUserGroup/focusedUserGroup_edit");
	}
	
	/**
	 * 关注用户分组表预览
	 * @param id
	 * @param model
	 * @return view
	 * @author Kun
	 * @date 2018-01-10 10:32
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		FocusedUserGroup rule = new FocusedUserGroup();
		if(StringUtils.isNotBlank(id)){
			rule = focusedUserGroupService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/focusedUserGroup/focusedUserGroup_view");
	}
	
	/**
	 * 关注用户分组表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-10 10:32
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(FocusedUserGroup bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					focusedUserGroupService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setNumber("0");
					bean.setCreateTime(new Date());
					focusedUserGroupService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
	/**
	 * 关注用户分组表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-10 10:32
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			focusedUserGroupService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	
}
