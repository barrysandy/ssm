package com.xiaoshu.controller.admin.activity;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsString;
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
 * 微信问答活动管理
 * @name: WechatActivityTestQuestionsController
 * @author: XGB
 * @date: 2018-03-05 11:39
 */
@Controller
@RequestMapping(value = "/wechatActivityTestQuestions")
public class WechatActivityTestQuestionsController extends BaseController {

	@Resource private WechatActivityTestQuestionsService wechatActivityTestQuestionsService;
	@Resource private WechatActivityAnswerService wechatActivityAnswerService;
	/**
	 * 分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author XGB
	 * @date 2018-03-05 11:50
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search) {
		try{
			Map map = new HashMap(8);
			map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
			map.put("pageSize", pager.getPageSize());
			map.put("search", search);
			List<WechatActivityTestQuestions> list = wechatActivityTestQuestionsService.selectByPage(map);
			int totalNum = wechatActivityTestQuestionsService.selectCount(map);
			pager.setFullListSize(totalNum);
			pager.setList(list);
			model.addAttribute("pager", pager);
			model.addAttribute("search", search);
		}catch (Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivityTestQuestions_list");
	}


	/**
	 * 编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author XGB
	 * @date 2018-03-05 11:53
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			WechatActivityTestQuestions rule = new WechatActivityTestQuestions();
			if(StringUtils.isNotBlank(id)){
				rule = wechatActivityTestQuestionsService.getByPrimaryKey(id);
			}
			model.addAttribute("bean",rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivityTestQuestions_edit");
	}

	/**
	 * 保存操作
	 * @param bean 实体类
	 * @return String
	 * @author XGB
	 * @date 2018-02-07 10:50
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(WechatActivityTestQuestions bean,String setoptions[],String setcorrect[]){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					bean.setUpdateTime(new Date());
					if(setcorrect != null){
						for (int i = 0; i < setcorrect.length; i++ ) {
							if("1".equals(setcorrect[i])){//设置正确的答案
								bean.setAnswerId(setoptions[i]);
								break;
							}
						}
					}
					wechatActivityTestQuestionsService.updateByPrimaryKeySelective(bean);
					//删除以前的答案,并更新答案
					if(setoptions != null){
						wechatActivityAnswerService.deleteByQuestionId(bean.getId());
						for (int i = 0; i < setoptions.length; i++ ) {
							String answerId = UUID.randomUUID().toString();
							WechatActivityAnswer answer = new WechatActivityAnswer(answerId, setoptions[i], i + 1, "", 1, new Date(), null, bean.getId());
							wechatActivityAnswerService.saveRecord(answer);
						}
					}
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					if(setcorrect != null){
						for (int i = 0; i < setcorrect.length; i++ ) {
							if("1".equals(setcorrect[i])){//设置正确的答案
								bean.setAnswerId(setoptions[i]);
								break;
							}
						}
					}
					wechatActivityTestQuestionsService.saveRecord(bean);
					//删除以前的答案,并更新答案
					if(setoptions != null){
						wechatActivityAnswerService.deleteByQuestionId(bean.getId());
						for (int i = 0; i < setoptions.length; i++ ) {
							String answerId = UUID.randomUUID().toString();
							WechatActivityAnswer answer = new WechatActivityAnswer(answerId, setoptions[i], i + 1, "", 1, new Date(), null, bean.getId());
							wechatActivityAnswerService.saveRecord(answer);
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
	 * 删除
	 * @param id 主键ID
	 * @return String
	 * @author XGB
	 * @date 2018-03-05 13:50
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			wechatActivityTestQuestionsService.deleteByPrimaryKey(id);
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}


}
