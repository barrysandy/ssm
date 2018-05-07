package com.xiaoshu.wechat.tools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoshu.entity.WeChatMenu;
import com.xiaoshu.wechat.menu.Button;
import com.xiaoshu.wechat.menu.ClickButton;
import com.xiaoshu.wechat.menu.ComplexButton;
import com.xiaoshu.wechat.menu.Menu;
import com.xiaoshu.wechat.menu.ViewButton;

/**
 * 微信生成菜单实体类
 * @author XGB
 *
 */
public class WeChatCreateMenu {
	private static Logger log = LoggerFactory.getLogger(WeChatCreateMenu.class);
	/**
	 * 创建微信菜单
	 * @param menuList 菜单集合
	 * @param appId 第三方用户唯一凭证
	 * @param appSecret 第三方用户唯一凭证密钥
	 * @return 返回创建状态
	 */
	public static String createMenu(List<WeChatMenu> menuList, String access_token,String appId,String appSecret){
		String returnStr = "菜单创建失败！";
		// 调用接口获取凭证
		//AccessToken token = CommonUtil.getToken(appId, appSecret);
		if (null != access_token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(menuList), access_token);
			// 判断菜单创建结果
			if (result){
				log.info("菜单创建成功！");
				returnStr = "菜单创建成功！";
			}
			else{
				log.info("菜单创建失败！");
				returnStr = "菜单创建失败！";
			}
		}
		return returnStr;
	}
	
	
	/**
	 * 定义菜单结构
	 */
	private static Menu getMenu(List<WeChatMenu> menuList) {
		Button btnMenu[] = new Button[menuList.size()];
		for (int i = 0; i < menuList.size(); i++) {
			if("CompleteButton".equals(menuList.get(i).getMenuType())){//复合型菜单需要组装菜单
				ComplexButton tempBtn = new ComplexButton();
				tempBtn.setName(menuList.get(i).getMenuName());
				Button btnSub[] = new Button[menuList.get(i).getSubMenu().size()];
				for (int j = 0; j < menuList.get(i).getSubMenu().size(); j++) {
					if("ClickButton".equals(menuList.get(i).getSubMenu().get(j).getMenuType()) || "click".equals(menuList.get(i).getSubMenu().get(j).getMenuType())){
						ClickButton subTemp = new ClickButton();
						subTemp.setKey(menuList.get(i).getSubMenu().get(j).getMenuKey());
						subTemp.setName(menuList.get(i).getSubMenu().get(j).getMenuName());
						subTemp.setType("click");
						btnSub[j] = subTemp;
					}else if("ViewButton".equals(menuList.get(i).getSubMenu().get(j).getMenuType()) || "view".equals(menuList.get(i).getSubMenu().get(j).getMenuType())){
						ViewButton subTemp = new ViewButton();
						subTemp.setName(menuList.get(i).getSubMenu().get(j).getMenuName());
						subTemp.setType("view");
						subTemp.setUrl(menuList.get(i).getSubMenu().get(j).getMenuUrl());
						btnSub[j] = subTemp;
					}
				}
				tempBtn.setSub_button(btnSub);
				btnMenu[i] = tempBtn;
				
				
			} else if("ViewButton".equals(menuList.get(i).getMenuType()) || "view".equals(menuList.get(i).getMenuType())){
				ViewButton tempView = new ViewButton();
				tempView.setName(menuList.get(i).getMenuName());
				tempView.setType("view");
				tempView.setUrl(menuList.get(i).getMenuUrl());
				btnMenu[i] = tempView;
			}else if("ClickButton".equals(menuList.get(i).getMenuType()) || "click".equals(menuList.get(i).getMenuType())){
				ClickButton tempClick = new ClickButton();
				tempClick.setKey(menuList.get(i).getMenuKey());
				tempClick.setName(menuList.get(i).getMenuName());
				tempClick.setType("click");
				btnMenu[i] = tempClick;
			}
		}
		//生成菜单
		Menu menu = new Menu();
		menu.setButton(btnMenu);//
		return menu;
	}
	
}
