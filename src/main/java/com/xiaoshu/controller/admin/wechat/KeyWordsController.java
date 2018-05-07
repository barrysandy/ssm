package com.xiaoshu.controller.admin.wechat;

import java.util.*;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.KeyWords;
import com.xiaoshu.entity.WechatMaterial;
import com.xiaoshu.enumeration.EnumsAutomaticReplyType;
import com.xiaoshu.service.KeyWordsService;
import com.xiaoshu.service.WechatMaterialService;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 关键词回复表
 * @name: KeyWordsController
 * @author: Kun
 * @date: 2018-01-09 10:50
 */
@Controller
@RequestMapping(value = "/keyWords")
public class KeyWordsController extends BaseController {

	@Resource
	private KeyWordsService keyWordsService;

	@Resource
	private WechatMaterialService wechatMaterialService;
	
	/**
	 * 关键词回复表分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-09 10:50
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search,String menuid) {
		Map map = new HashMap(8);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		map.put("menuid",menuid);
		List<KeyWords> list = keyWordsService.selectByPage(map);
		int totalNum = keyWordsService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		model.addAttribute("menuid",menuid);
		return toVm("admin/keyWords/keyWords_list");
	}
	
	/**
	 * 关键词回复表编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author Kun
	 * @date 2018-01-09 10:50
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuid){
		try{
			KeyWords rule = new KeyWords();
			if(StringUtils.isNotBlank(id)){
				rule = keyWordsService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
			model.addAttribute("menuid",menuid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/keyWords/keyWords_edit");
	}

	/**
	 * 关键词回复表预览
	 * @param id
	 * @param model
	 * @return
	 * @author Kun
	 * @date 2018-01-09 10:50
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model,String menuid){
		KeyWords rule = new KeyWords();
		if(StringUtils.isNotBlank(id)){
			rule = keyWordsService.selectByPrimaryKey(id);
			if(rule != null){
				if(rule.getValuess() != null && rule.getKeyes() != null){
					//非文本关键字需展示引用的素材
					if(!"".equals(rule.getValuess()) && !EnumsAutomaticReplyType.TEXT.equals(rule.getTypess())){
						WechatMaterial material = wechatMaterialService.getWMmaterMaterialIdAndParentMenuId(rule.getValuess(),rule.getParentMenuId());
						model.addAttribute("material", material);
					}
				}
			}
		}
		model.addAttribute("bean", rule);
		model.addAttribute("menuid",menuid);
		return toVm("admin/keyWords/keyWords_view");
	}

	/**
	 * 关键词回复表保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-09 10:50
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(KeyWords bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					bean.setUpdateTime(new Date());
					keyWordsService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus("1");
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					keyWordsService.insert(bean);
				}
			}
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
	/**
	 * 关键词回复表删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-09 10:50
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			keyWordsService.delete(id);
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/**
	 * 关键词查询 keyWords/getKeyinterfaces
	 * @param keyes 关键字
	 * @return menuId 关键字所属公众号
	 * @return valuess 匹配到的值
	 * @author XGB
	 * @date 2018-01-16 16:09
	 */
	@RequestMapping(value = "/getKeyinterfaces",produces="text/html; charset=UTF-8")
	@ResponseBody
	public String getKey(String menuId,String keyes){
		try{
		    if(keyes != null && menuId != null && !"".equals(keyes) && !"".equals(menuId)){
				keyes = ToolsASCIIChang.asciiToString(keyes);
                KeyWords keyWords = keyWordsService.selectByKey(menuId,keyes);
                return JSONUtils.toJSONString(keyWords);
            }else{
                return null;
            }

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
