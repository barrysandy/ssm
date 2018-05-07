package com.xiaoshu.controller.admin.activity;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.WechatActivity;
import com.xiaoshu.entity.WechatActivitySign;
import com.xiaoshu.entity.WechatActivitySignSet;
import com.xiaoshu.service.WechatActivityService;
import com.xiaoshu.service.WechatActivitySignService;
import com.xiaoshu.service.WechatActivitySignSetService;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * 微信活动报名表
 * @name: WechatActivitySignController
 * @author: XGB
 * @date: 2018-02-24 14:09
 */
@Controller
@RequestMapping(value = "/activitySign")
public class WechatActivitySignController extends BaseController {

	@Resource private WechatActivityService wechatActivityService;
	@Resource private WechatActivitySignService wechatActivitySignService;
	@Resource private WechatActivitySignSetService wechatActivitySignSetService;

	/**
	 * 分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author XGB
	 * @date 2018-02-06 17:01
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager,String id, Model model, String search,int draw) {
		try{
			Map map = new HashMap(8);
			map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
			map.put("pageSize", pager.getPageSize());
			map.put("search", search);
			int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
			int pageSize = pager.getPageSize();
			WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
			if(wechatActivity != null){
				model.addAttribute("authorised", wechatActivity.getAuthorised());
			}
			List<WechatActivitySign> list = wechatActivitySignService.selectByPage(search,startRow,pageSize,id,draw);
			List<WechatActivitySignSet> listIsSet = wechatActivitySignSetService.getAllByActivityId(id,0);
			if(list != null){
				if(list.size() > 0){
					Iterator<WechatActivitySign> iterator = list.iterator();
					while(iterator.hasNext()){
						WechatActivitySign wechatActivitySign = iterator.next();
						List<WechatActivitySignSet> listSet = wechatActivitySignSetService.getAllValuesByActivitySignId(wechatActivitySign.getId());
						if(listSet != null){
							if(listSet.size() > 0){
								wechatActivitySign.setWechatActivitySignSet(listSet);
							}
						}
					}
				}
			}
			int totalNum = wechatActivitySignService.selectCount(search,startRow,pageSize,id,draw);
			pager.setFullListSize(totalNum);
			pager.setList(list);
			model.addAttribute("pager", pager);
			model.addAttribute("listSet", listIsSet);
			model.addAttribute("search", search);
			model.addAttribute("id",id);
			model.addAttribute("draw",draw);
		}catch (Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivitySign_list");
	}



	/**
	 * 删除
	 * @param id 主键ID
	 * @return String
	 * @author XGB
	 * @date 2018-02-25 8:46
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			wechatActivitySignService.deleteByPrimaryKey(id);
			wechatActivitySignSetService.deleteByWechatActivitySignId(id,1);
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}
}
