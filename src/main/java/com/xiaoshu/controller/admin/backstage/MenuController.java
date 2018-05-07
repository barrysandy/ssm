package com.xiaoshu.controller.admin.backstage;


import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshu.api.Api;
import com.xiaoshu.entity.KeyWords;
import com.xiaoshu.entity.PublicAccountInfo;
import com.xiaoshu.service.KeyWordsService;
import com.xiaoshu.service.PublicAccountInfoService;
import com.xiaoshu.tools.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoshu.entity.Menu;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.service.MenuService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtils;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("menu")
@Controller
public class MenuController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private PublicAccountInfoService publicAccountInfoService;

	@Resource
	private KeyWordsService keyWordsService;
	
	static Logger logger = Logger.getLogger(MenuController.class);
	
	
	@RequestMapping("menuIndex")
	public String index(HttpServletRequest request,HttpServletResponse response,Integer menuid){
		String currentOperationIds = (String) request.getSession().getAttribute("currentOperationIds");
		String[] OperationIdArr = currentOperationIds.split(",");
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		for (Operation operation : operationList) {
			if(StringUtils.existStrArr(operation.getOperationid().toString(),OperationIdArr)){
				map.put(operation.getOperationcode(),true);
			}else{
				map.put(operation.getOperationcode(),false);
			}
		}
		request.setAttribute("operationInfo", map);
		return "menu";
	}
	
	@RequestMapping("treeGridMenu")
	public void treeGridMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String parentId = request.getParameter("parentId");
			JSONArray jsonArray = getListByParentId(parentId,0);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单展示错误",e);
		}
	}
	
	public JSONArray getListByParentId(String parentId, int l)throws Exception{
		JSONArray jsonArray = this.getTreeGridMenuByParentId(parentId,l);
		JSONArray resultJsonArray = new JSONArray();
		
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			resultJsonArray.add(jsonObject);
			if(!"isParent".equals(jsonObject.getString("state"))){
				continue;
			}else{
				resultJsonArray.addAll(getListByParentId(jsonObject.getString("menuid"),++l));
			}
		}
		return resultJsonArray;
	}
	
	
	
	public JSONArray getTreeGridMenuByParentId(String parentId, int l)throws Exception{
		JSONArray jsonArray = new JSONArray();
		Menu menu = new Menu();
		menu.setParentid(Integer.parseInt(parentId));
		List<Menu> list = menuService.findMenu(menu);
		for(Menu m : list){
			JSONObject jsonObject = new JSONObject();
			Integer menuId = m.getMenuid();
			jsonObject.put("menuid", menuId);
			jsonObject.put("menuname", m.getMenuname());
			jsonObject.put("parentid", m.getParentid());
			jsonObject.put("iconcls", m.getIconcls());
			jsonObject.put("state", m.getState());
			jsonObject.put("seq", m.getSeq());
			jsonObject.put("menuurl", m.getMenuurl());
			jsonObject.put("menudescription", m.getMenudescription());
			jsonObject.put("level", l);
			jsonObject.put("isLeaf", (StringUtils.isEmpty(m.getState())||"close".equals(m.getState()) ));
			jsonObject.put("parent", m.getParentid().compareTo(new Integer(0))>0?m.getParentid():null);
			jsonObject.put("laoded", true);
			jsonObject.put("expanded", true);
			
			// 加上该页面菜单下面的按钮
			Operation operation = new Operation();
			operation.setMenuid(menuId);
			List<Operation> operaList = operationService.findOperation(operation);
			if (operaList!=null && operaList.size()>0) {
				String string = "";
				for (Operation o : operaList) {
					string += o.getOperationname() + ",";
				}
				jsonObject.put("operationnames", string.substring(0,string.length()-1));
			} else {
				jsonObject.put("operationnames", "");
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	
	@RequestMapping({"reserveMenu"})
	public void reserveMenu(HttpServletRequest request,HttpServletResponse response,Menu menu){
		String menuId = menu.getMenuid()==null?"":menu.getMenuid().toString();
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(menuId)) {  //更新操作
				menu.setMenuid(Integer.parseInt(menuId));
				menuService.updateMenu(menu);
			} else {
				String parentId = menu.getParentid()==null?"":menu.getParentid().toString();
				menu.setParentid(Integer.parseInt(parentId));
				if (isLeaf(parentId)) {
					// 添加操作
					if("1".equals(parentId)){
						menu.setState("close");
					}
					menuService.addMenu(menu);
					createSonMean(Integer.parseInt(parentId),menu.getMenuname(),menuService,publicAccountInfoService,keyWordsService);

					// 更新他上级状态。变成isParent
					menu = new Menu();
					menu.setMenuid(Integer.parseInt(parentId));
					menu.setState("isParent");
					menuService.updateMenu(menu);
				} else {
					// 添加操作
					if("1".equals(parentId)){
						menu.setState("close");
					}
					menuService.addMenu(menu);
					createSonMean(Integer.parseInt(parentId),menu.getMenuname(),menuService,publicAccountInfoService,keyWordsService);
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单保存错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败！");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	// 判断是不是叶子节点
	public boolean isLeaf(String menuId){
		boolean flag = false;
		try {
			Menu menu = new Menu();
			menu.setParentid(Integer.parseInt(menuId));
			List<Menu> list = menuService.findMenu(menu);
			if (list==null || list.size()==0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断菜单是否叶子节点错误",e);
		}
		return flag;
	}

	@RequestMapping("deleteMenu")
	public void deleteMenu(HttpServletRequest request,HttpServletResponse response,Integer id){
		JSONObject result = new JSONObject();
		try {
			Menu menu = menuService.findMenuByMenuid(id);
			String parentId = menu.getParentid().toString();
			if (!isLeaf(id.toString())) {  //不是叶子节点，说明有子菜单，不能删除
				result.put("errorMsg", "该菜单下面有子菜单，不能直接删除");
			} else {
				menu = new Menu();
				menu.setParentid(Integer.parseInt(parentId));
				long sonNum = menuService.countMenu(menu);
				if (sonNum == 1) {  
					// 只有一个孩子，删除该孩子，且把父亲状态置为""或close
					menu = new Menu();
					menu.setMenuid(Integer.parseInt(parentId));
					Menu parentMenu = menuService.findMenuByMenuid(Integer.parseInt(parentId));
					if(parentMenu.getParentid().compareTo(1)==0){
						menu.setState("close");
					}else{
						menu.setState("");
					}
					menuService.updateMenu(menu);
					
					menuService.deleteMenu(id);
				} else {
					//不只一个孩子，直接删除
					menuService.deleteMenu(id);
				}
				result.put("success", true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单删除错误",e);
			result.put("errorMsg", "对不起，删除失败！");
		}
		WriterUtil.write(response, result.toString());
	}

	//getParentIdByMenuId


	/**
	 * 根据MenuId查询父MenuId  menu/interfaceGetParentMenuId?menuId=menuId
	 * @param menuId
	 * @return parentMenuId
	 * @author XGB
	 * @date 2018-01-20 8:55
	 */
	@RequestMapping(value="/interfaceGetParentMenuId",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String interfaceGetParentMenuId(String menuId) {
		try{
			String parentMenuId = menuService.getParentIdByMenuId(menuId);
			return JSONUtils.toStringJSON(parentMenuId);
		} catch (Exception e){
			e.printStackTrace();
			return JSONUtils.toStringJSON("0");
		}
	}

	/**
	 * 创建子菜单,并补全初始化关键字
	 * @param parentId
	 * @param menuname
	 * @param menuService
	 * @param publicAccountInfoService
	 * @param keyWordsService
	 */
	public static void createSonMean(Integer parentId,String menuname,MenuService menuService,PublicAccountInfoService publicAccountInfoService,KeyWordsService keyWordsService){
		try{
			Menu menuParent = menuService.selectMenuByParentID(parentId);
			if(menuParent != null){
				if(menuParent.getMenudescription() != null){
					if( !"".equals(menuParent.getMenudescription()) && "Auto_Add_Menu".equals(menuParent.getMenudescription()) ){//父菜单是微信管理
						int menuIdNew = menuService.selectMenuIDByName(menuname);
						menuService.updateIsParent(menuIdNew,"isParent");
						if(menuIdNew != 0){
							Menu m1 = new Menu(null, "粉丝管理", "focusedUserInfo/toUserListPage.htm", menuIdNew, "", null,"fa fa-child", 1);
							Menu m2 = new Menu(null, "关键字管理", "keyWords/list.htm", menuIdNew, "", null,"fa fa-key", 2);
							Menu m3 = new Menu(null, "素材管理", "wechatMaterial/list", menuIdNew, "", null,"fa fa-edit", 3);
							Menu m4 = new Menu(null, "文章管理", "art/list.htm", menuIdNew, "", null,"fa fa-list-alt", 4);
							Menu m5 = new Menu(null, "菜单设置", "weChatMenu/list", menuIdNew, "", null,"fa fa-sliders", 5);
							Menu m6 = new Menu(null, "基础设置", "publicAccountInfo/getInfoByUserId.htm", menuIdNew, "", null,"fa fa-users", 6);
							menuService.addMenu(m1);
							menuService.addMenu(m2);
							menuService.addMenu(m3);
							menuService.addMenu(m4);
							menuService.addMenu(m5);
							menuService.addMenu(m6);
							//基础设置的menuId
							int baseSetMenuId = menuService.selectMenuIDByParentIDANDUrl(menuIdNew,"publicAccountInfo/getInfoByUserId.htm");
							PublicAccountInfo publicAccountInfo = new PublicAccountInfo(UUID.randomUUID() + "", menuname, "公众号的原始ID", "必须和微信公众号配置一致的token", "APPID", "APPSECRET",
									0, Api.WECHAT_INTERFACE_URL.replaceAll("SIGN",menuIdNew+""), new Date(), null, 1, "这是一个新的公众号", 0, "7100", "",
									"暂无", "暂无", "暂无", "暂无", baseSetMenuId+"",menuIdNew+"");
							publicAccountInfoService.insert(publicAccountInfo);

							//关键字管理menuId
							int keyMenuId = menuService.selectMenuIDByParentIDANDUrl(menuIdNew,"keyWords/list.htm");
							//创建初始关键字
							KeyWords attentionBean = new KeyWords(String.valueOf(UUID.randomUUID()), "ATTENTION_REPLY", "欢迎关注" + menuname, "text", new Date(), null, "1", "1", keyMenuId+"", menuIdNew+"");
							KeyWords autoBean = new KeyWords(String.valueOf(UUID.randomUUID()), "AUTO_REPLY", "默认回复", "text", new Date(), null, "1", "2", keyMenuId+"", menuIdNew+"");
							keyWordsService.insert(attentionBean);
							keyWordsService.insert(autoBean);
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
