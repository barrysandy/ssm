package com.xiaoshu.controller.admin.wechat;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.dao.MenuMapper;
import com.xiaoshu.entity.PublicAccountInfo;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.service.PublicAccountInfoService;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StochasticUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>description：公众号基础信息表</p>
 * PublicAccountInfoController
 * @author Kun
 * @date 2018-01-03 17:04
 */
@Controller
@RequestMapping(value = "/publicAccountInfo")
public class PublicAccountInfoController extends BaseController {

	@Resource private PublicAccountInfoService publicAccountInfoService;
	@Resource private MenuMapper menuMapper;

	/**
	 * 公众帐号基础信息分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-19 15:15
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search,String usable) {
		Map<String,Object> map = new HashMap<String,Object>(8);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		if (!StringUtils.equals("-1",usable)){
			map.put("usable",usable);
		}
		List<PublicAccountInfo> list = publicAccountInfoService.selectByPage(map);
		int totalNum = publicAccountInfoService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		model.addAttribute("usable",usable);
		return toVm("admin/publicAccountInfo/publicAccountInfo_list");
	}

	/**
	 * 公众帐号基础信息编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author Kun
	 * @date 2018-01-19 15:15
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model){
		try{
			PublicAccountInfo rule = new PublicAccountInfo();
			if(StringUtils.isNotBlank(id)){
				rule = publicAccountInfoService.selectByPrimaryKey(id);
			}
			model.addAttribute("bean", rule);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/publicAccountInfo/publicAccountInfo_edit");
	}

	/**
	 * 公众帐号基础信息预览
	 * @param id
	 * @param model
	 * @return view
	 * @author Kun
	 * @date 2018-01-19 15:15
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		PublicAccountInfo rule = new PublicAccountInfo();
		if(StringUtils.isNotBlank(id)){
			rule = publicAccountInfoService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/publicAccountInfo/publicAccountInfo_view");
	}

	/**
	 * 查询用户的微信公众号帐号信息
	 * @return view
	 * @author zhou.zhengkun
	 * @date 2018/01/04 0004 10:07
	 */
	@RequestMapping(value = "/getInfoByUserId")
	public String getInfoByUserId(Model model,String menuid){
		PublicAccountInfo bean = publicAccountInfoService.getInfoByMenuid(menuid);
		model.addAttribute("bean",bean);
		model.addAttribute("menuid",menuid);
		return "admin/publicAccountInfo/publicAccountInfo";
	}

	/**
	 * 公众帐号基础信息保存操作
	 * @param bean 实体类
	 * @return String
	 * @author Kun
	 * @date 2018-01-19 15:15
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(PublicAccountInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					bean.setStatus(1);
					int i = publicAccountInfoService.update(bean);
					/** 立即更新内存Map 数据*/
					if(i > 0 ){
						JobPublicAccount.updateMapData(bean.getParentMenuId(),bean);
					}
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					int i = publicAccountInfoService.insert(bean);
					/** 立即往内存写入Map 数据*/
					if(i > 0 ){
						JobPublicAccount.updateMapData(bean.getParentMenuId(),bean);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/**
	 * 公众帐号基础信息删除
	 * @param id 主键ID
	 * @return String
	 * @author Kun
	 * @date 2018-01-19 15:15
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			publicAccountInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}


	/**
	 * 保存用户微信公众号帐号信息
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/04 0004 10:08
	 */
	@RequestMapping(value="/publicAccountSave")
	public String publicAccountSave(PublicAccountInfo publicAccountInfo, HttpSession session,String menuid) throws Exception{
		String id = publicAccountInfo.getId();
		if (StringUtils.isBlank(id)){
			/*新增*/
			id = StochasticUtil.getUUID();
			publicAccountInfo.setId(id);
			publicAccountInfo.setCreateTime(new Date());
			publicAccountInfo.setStatus(1);
			publicAccountInfo.setMenuId(menuid);
			publicAccountInfo.setDescM("用户微信公众号帐号信息");
			publicAccountInfoService.insert(publicAccountInfo);
		}else{
			/*更新*/
			publicAccountInfo.setStatus(1);
			publicAccountInfo.setMenuId(menuid);
			publicAccountInfo.setUpdateTime(new Date());
			publicAccountInfoService.update(publicAccountInfo);

			//同步更新菜单名称
			int menuId = Integer.parseInt(publicAccountInfo.getParentMenuId());
			String accountName = publicAccountInfo.getAccountName();
			menuMapper.updateMeanName(menuId,accountName);
		}
		return "redirect:/publicAccountInfo/getInfoByUserId.htm?menuid="+menuid;
	}

	/**
	 * 通过menuId查询帐号部分信息
	 * @param menuId 菜单ID
	 * @param returnContent 返回内容 (appId,appSecret,effectiveTime)
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/17 0017 15:44
	 */
	@RequestMapping(value = "interface/selectInfoByMenuId",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String selectInfoByMenuId(String menuId,String returnContent){
		try {
			if (StringUtils.isBlank(menuId)){
				return JsonUtils.turnJson(false,"参数错误","menuId : " +menuId);
			}else{
				PublicAccountInfo bean = publicAccountInfoService.getInfoByMenuid(menuId);
				if (bean != null){
					String appId = bean.getAppId();
					String appSecret = bean.getAppSecret();
					String effectiveTime = bean.getEffectiveTime();
					if (StringUtils.equals(returnContent,"appId")){
					    return JsonUtils.turnJson(true,"success",appId);
                    } else if (StringUtils.equals(returnContent,"appSecret")){
                        return JsonUtils.turnJson(true,"success",appSecret);
                    } else if (StringUtils.equals(returnContent,"effectiveTime")){
                        return JsonUtils.turnJson(true,"success",effectiveTime);
                    } else {
                        return JsonUtils.turnJson(true,"success","appId :" + appId +" ,appSecret :"+appSecret
                        + " ,effectiveTime :" +effectiveTime);
                    }
				}else{
				    return JsonUtils.turnJson(false,"没有查询到帐号信息",null);
                }
			}
		}catch (Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error :" +e.getMessage(),e);
		}
	}



	/**
	 * 查询所有微信公众号帐号信息 publicAccountInfo/interfaceGetAll?usable=1
	 * @param usable 公众号启用状态
	 * @author XGB
	 * @date 2018/01/17  21:12
	 */
	@RequestMapping("/interfaceGetAll")
	@ResponseBody
	public String interfaceGetAll(int usable) {
		try {
			List<PublicAccountInfo> bean = publicAccountInfoService.selectListAll(usable);
			if (bean != null) {
				return JSONUtils.toJSONString(bean);
			} else {
				return "0";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 查询用户的微信公众号帐号信息 publicAccountInfo/getTokenByMenuid?menuId=menuId
	 * @param menuId 公众号menuId
	 * @author XGB
	 * @date 2018/01/04 0004 10:07
	 */
	@RequestMapping(value = "/getTokenByMenuid",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getInfoByMenuid(String menuId){
		PublicAccountInfo bean = publicAccountInfoService.getInfoByMenuid(menuId);
		if(bean != null){
			if(bean.getToken() != null && !"".equals(bean.getToken())){
				//返回token
				return bean.getToken();
			}else{
				return "0";
			}
		}else{
			return "0";
		}
	}

}
