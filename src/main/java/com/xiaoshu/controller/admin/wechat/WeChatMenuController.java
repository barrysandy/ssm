package com.xiaoshu.controller.admin.wechat;

import java.util.*;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.dao.MenuMapper;
import com.xiaoshu.entity.WeChatMenu;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.service.WeChatMenuService;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import com.xiaoshu.wechat.tools.WeChatCreateMenu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;

/**
 * 微信公众号菜单配置表
 * @name: WeChatMenuController
 * @author: Kun
 * @date: 2018-01-09 10:53
 */
@Controller
@RequestMapping(value = "/weChatMenu")
public class WeChatMenuController extends BaseController {

	@Resource
	private WeChatMenuService weChatMenuService;
	@Resource
	private MenuMapper menuMapper;
	
	/**
	 * 微信公众号菜单配置表分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author Kun
	 * @date 2018-01-09 10:53
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search ,String menuid) {
		Map map = new HashMap(8);
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		map.put("menuId",menuid);
		List<WeChatMenu> list = weChatMenuService.selectByPage(map);
		int totalNum = weChatMenuService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		model.addAttribute("menuId",menuid);
		return toVm("admin/weChatMenu/weChatMenu_list");
	}
	
	/**
	 * 微信公众号菜单配置表编辑页面
	* @param id 主键ID
	* @return view
	* @author Kun
	* @date 2018-01-09 10:53
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuId,String firstMenuId){
		try{
			WeChatMenu rule = new WeChatMenu();
			if(StringUtils.isNotBlank(id)){
				rule = weChatMenuService.selectByPrimaryKey(id);
			}
			model.addAttribute("menuId",menuId);
			model.addAttribute("bean", rule);
			model.addAttribute("firstMenuId",firstMenuId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/weChatMenu/weChatMenu_edit");
	}
	/**
	 * 微信公众号菜单配置表预览
	* @param id
	* @param model
	* @return
	* @author Kun
	* @date 2018-01-09 10:53
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		WeChatMenu rule = new WeChatMenu();
		if(StringUtils.isNotBlank(id)){
			rule = weChatMenuService.selectByPrimaryKey(id);
		}
		model.addAttribute("bean", rule);
		return toVm("admin/weChatMenu/weChatMenu_view");
	}
	/**
	 * 微信公众号菜单配置表保存操作
	* @param bean 实体类
	* @return String
	* @author Kun
	* @date 2018-01-09 10:53
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(WeChatMenu bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					bean.setUpdateTime(new Date());
					weChatMenuService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus("1");
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					weChatMenuService.insert(bean);
				}
			}
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);

	}
	/**
	 * 微信公众号菜单配置表删除
	* @param id 主键ID
	* @return String
	* @author Kun
	* @date 2018-01-09 10:53
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			weChatMenuService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

	/**
	 * 通过parentId查询菜单
	 * @param parentId 父级菜单ID (一级菜单parentId 为 ROOT)
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/18 0018 15:55
	 */
	@RequestMapping(value = "/interface/getMenuListByParentId",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getMenuListByParentId(String parentId){
		try {
			if (StringUtils.isBlank(parentId)){
				return JsonUtils.turnJson(false,"参数错误","parentId :"+parentId);
			}else{
				return weChatMenuService.getMenuListByParentId(parentId);
			}
		}catch (Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error :" +e.getMessage(),e);
		}
	}

	/**
	 * 精准查询 - menuId/名字 (两者选一个都可以,两个都传默认按照menuId进行查询)
	 * @param menuId 菜单的主键ID
	 * @param menuName 菜单名字
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/18 0018 16:15
	 */
	@RequestMapping(value = "/interface/getMenuByIdOrName",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getMenuByIdOrName(String menuId,String menuName){
		try {
			if (StringUtils.isBlank(menuId) && StringUtils.isBlank(menuName)){
				return JsonUtils.turnJson(false,"参数错误","menuId :" +menuId+"; menuName :" +menuName);
			}else{
				Map<String,Object> paraMap = new HashMap<String,Object>(4);
				paraMap.put("menuId",menuId);
				paraMap.put("menuName",menuName);
				//todo 当通过名字查询的时候 需要传入系统框架的menuId,因为不同公众号可能有相同菜单名字
				return weChatMenuService.getMenuByIdOrName(paraMap);
			}
		}catch (Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error :" +e.getMessage(),e);
		}
	}

	/**
	 * 通过以及菜单Id查询二级菜单列表
	 * @param firstMenuId 一级菜单ID
	 * @return String
	 * @author zhou.zhengkun
	 * @date 2018/01/22 0022 17:13
	 */
	@RequestMapping(value = "/interface/getSecondMenuList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getSecondMenuList(String firstMenuId){
		try {
			if (StringUtils.isBlank(firstMenuId)){
				return JsonUtils.turnJson(false,"参数错误",null);
			} else{
				return weChatMenuService.getSecondMenuListByFirstMenuId(firstMenuId);
			}
		}catch (Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error :"+e.getMessage(),e);
		}
	}

	/**
	 * 同步本地菜单配置到微信公众号
	 * URL weChatMenu/interface/updateWechatMenu?parentMenuId=
	 * @param
	 * @return
	 * @author XGB
	 * @date 2018/01/23 0023 14:13
	 */
	@RequestMapping(value = "/interface/updateWechatMenu",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateWechatMenu(@RequestParam("parentMenuId") String parentMenuId){
		try {
			parentMenuId = menuMapper.getParentIdByMenuId(parentMenuId);
			List<WeChatMenu> updateList = weChatMenuService.getUpdateWechatMenuList(parentMenuId);
			//读取Map中的token
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> map = mapPublicNumber.getMap();
			if(map != null && map.size() == 0){
				System.out.println("map not data");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
			String accessToken = map.get("accessToken" + parentMenuId);
			String appId = map.get("appId" + parentMenuId);
			String appSecret = map.get("appSecret" + parentMenuId);
			String respString = WeChatCreateMenu.createMenu(updateList,accessToken,appId,appSecret);
			System.out.println("菜单创建结果：" + respString);
			return JsonUtils.turnJson(true,"sucess",respString);
		}catch (Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error : "+e.getMessage(),e);
		}
	}
}
