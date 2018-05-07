package com.xiaoshu.controller.admin.activity;

import com.xiaoshu.api.Api;
import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.*;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * 微信活动表
 * @name: WechatActivityController
 * @author: XGB
 * @date: 2018-02-06 16:49
 */
@Controller
@RequestMapping(value = "/activity")
public class WechatActivityController extends BaseController {

	@Resource private WechatActivityService wechatActivityService;
	@Resource private WechatActivitySignSetService wechatActivitySignSetService;
	@Resource private WechatActivityPrizeService wechatActivityPrizeService;
	@Resource private FocusedUserInfoService focusedUserInfoService;
	@Resource private SellerService sellerService;
	@Resource private PublicAccountInfoService publicAccountInfoService;
	/**
	 * 分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author XGB
	 * @date 2018-02-06 17:01
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, Model model, String search,String menuid) {
		//屏蔽公众号之分
		menuid =null;
		try{
			Map map = new HashMap(8);
			map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
			map.put("pageSize", pager.getPageSize());
			map.put("search", search);
			map.put("menuid",menuid);
			List<WechatActivity> list = wechatActivityService.selectByPage(map);
			int totalNum = wechatActivityService.selectCount(map);
			pager.setFullListSize(totalNum);
			pager.setList(list);
			model.addAttribute("pager", pager);
			model.addAttribute("search", search);
			model.addAttribute("menuid",menuid);
		}catch (Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivity_list");
	}

	/**
	 * 编辑页面
	 * @param id 主键ID
	 * @return view
	 * @author XGB
	 * @date 2018-02-07 10:50
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String id,Model model,String menuid){
		try{
			WechatActivity rule = new WechatActivity();
			if(StringUtils.isNotBlank(id)){
				rule = wechatActivityService.getByPrimaryKey(id);
			}
			//查询全部可用的公众号
			List<PublicAccountInfo> paInfoList = publicAccountInfoService.selectListAll(1);
			model.addAttribute("paInfoList", paInfoList);
			model.addAttribute("bean", rule);
			model.addAttribute("menuid",menuid);
			if(rule != null){
				if(rule.getShareImage() != null){
					if(!"".equals(rule.getShareImage())){
						//替换素材的在文件服务器上的url
						String param = "material_id=" + rule.getShareImage();
						String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
						if(json != null){
							if(!"".equals(json)){
								JSONObject jsonObject = JSONObject.fromObject(json);
								String image = jsonObject.getString("url");
								model.addAttribute("image",image);
							}
						}
					}
				}
			}

			//查询报名属性集合
			List<WechatActivitySignSet> listSet = wechatActivitySignSetService.getAllByActivityId(rule.getId(),0);
			model.addAttribute("listSet", listSet);

			//查询奖品集合
			List<WechatActivityPrize> listPrize = wechatActivityPrizeService.getAllActivityPrize();
			model.addAttribute("listPrize", listPrize);
			StringBuffer stringBuffer = new StringBuffer();
			if(listPrize != null){
				Iterator<WechatActivityPrize> iterator = listPrize.iterator();
				while (iterator.hasNext()){
					WechatActivityPrize prizeTemp = iterator.next();
					stringBuffer.append("<option value='"+prizeTemp.getId()+"'>"+prizeTemp.getName()+"</option>");
				}
			}
			model.addAttribute("prizeSelet",stringBuffer.toString());
			model.addAttribute("wechatActivityId",rule.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivity_edit");
	}


	/**
	 * 预览
	 * @param id
	 * @param model
	 * @return
	 * @author XGB
	 * @date 2018-02-07 10:50
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model,String menuid){
		WechatActivity rule = new WechatActivity();
		try{
			if(StringUtils.isNotBlank(id)){
				rule = wechatActivityService.getByPrimaryKey(id);
			}
			if(rule.getBindingWechatId() != null){
				if(!"".equals(rule.getBindingWechatId())){
					//查询活动绑定的公众号
					PublicAccountInfo bindingWechat = publicAccountInfoService.selectByPrimaryKey(rule.getBindingWechatId());
					if(bindingWechat != null){
						model.addAttribute("bindingWechat", bindingWechat.getAccountName());
					}
				}
			}
			//查询活动关联的订阅号
			PublicAccountInfo subscribeWechat = publicAccountInfoService.selectByPrimaryKey(rule.getSubscribeWechatId());
			//替换素材的在文件服务器上的url
			if(rule != null){
				if(rule.getShareImage() != null){
					if(!"".equals(rule.getShareImage())){
						//替换素材的在文件服务器上的url
						String param = "material_id=" + rule.getShareImage();
						String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
						if(json != null){
							if(!"".equals(json)){
								JSONObject jsonObject = JSONObject.fromObject(json);
								String image = jsonObject.getString("url");
								model.addAttribute("image",image);
							}
						}
					}
				}
			}
			//查询报名属性集合
			List<WechatActivitySignSet> listSet = wechatActivitySignSetService.getAllByActivityId(rule.getId(),0);
			model.addAttribute("bean", rule);
			if(subscribeWechat != null){
				model.addAttribute("subscribeWechat", subscribeWechat.getAccountName());
			}
			model.addAttribute("listSet", listSet);
		}catch (Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivity_view");
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
	public String saveOrUpdate(WechatActivity bean,String setchineseCharacterName[],String setnamePlaceholder[],String setname[],String settypese[],Integer setrequired[],Integer setsort[],
			String setverificationType[],Integer sethide[],Integer setrepeat[],String setdescM[],Integer setsetType[],String[] prizeid){
		String oldImage = "";//旧资源
		String newImage = bean.getShareImage();//新资源
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					WechatActivity oldWechatActivity = wechatActivityService.getByPrimaryKey(id);
					if(oldWechatActivity != null){
						if(oldWechatActivity.getShareImage() != null){
							if( !"".equals(oldWechatActivity.getShareImage())){
								oldImage = oldWechatActivity.getShareImage();//旧资源
							}
						}
					}
					bean.setUpdateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));
					//更新url
					updateUrl(bean,publicAccountInfoService);
					String parentMenuId;
					if(bean.getBindingWechatId() != null){
						parentMenuId = publicAccountInfoService.getParentMenuIdById(bean.getBindingWechatId());
						if(parentMenuId != null){
							bean.setParentMenuId(parentMenuId);
						}
					}
					System.out.println("=============================================");
					System.out.println("Current BindingWechatId " + bean.getBindingWechatId());
					System.out.println("Current ParentMenuId " + bean.getParentMenuId());
					System.out.println("=============================================");

					wechatActivityService.updateRecord(bean);
                    //删除以前的属性,并更新属性
					updateActivity(wechatActivitySignSetService,wechatActivityPrizeService,bean,setchineseCharacterName,setnamePlaceholder,setname,settypese,setrequired,setsort, setverificationType,sethide,setrepeat,setdescM,setsetType,prizeid);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(ToolsString.putOffBar(u.toString()));
					bean.setCreateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));
					String parentMenuId;
					if(bean.getBindingWechatId() != null){
						parentMenuId = publicAccountInfoService.getParentMenuIdById(bean.getBindingWechatId());
						if(parentMenuId != null){
							bean.setParentMenuId(parentMenuId);
						}
					}
					System.out.println("=============================================");
					System.out.println("Current BindingWechatId " + bean.getBindingWechatId());
					System.out.println("Current ParentMenuId " + bean.getParentMenuId());
					System.out.println("=============================================");

					wechatActivityService.saveRecord(bean);
					//更新url
					updateUrl(bean,publicAccountInfoService);
                    //删除以前的属性,并更新属性
                    updateActivity(wechatActivitySignSetService,wechatActivityPrizeService,bean,setchineseCharacterName,setnamePlaceholder,setname,settypese,setrequired,setsort, setverificationType,sethide,setrepeat,setdescM,setsetType,prizeid);
                }
				//更新文件服务器资源的引用
				ToolsImage.updateSSMFile(newImage,oldImage);
			}


			WechatActivity newBean = wechatActivityService.getByPrimaryKey(bean.getId());
			//TODO 同步关联
			if("sign".equals(bean.getTypes()) || "signSession".equals(bean.getTypes())) {
				String json = JSONUtils.toJSONString(newBean);
				json = ToolsString.getStrRemoveBracket(json);
				json = ToolsASCIIChang.stringToAscii(json);
				//TODO 维护关联活动
				String url = com.xiaoshu.api.Set.SYSTEM_URL + "commodity/interfaceMaintain";
				String param = "json=" + json;
				String result = ToolsHttpRequest.sendPost(url,param);
				if(result != null) {
					System.out.println("====================活动维护关联商品=========================");
					System.out.println("===================="+result+"=========================");
					System.out.println("====================活动维护关联商品=========================");
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
	 * @date 2018-02-07 10:50
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
			wechatActivityService.delete(id);
			//删除资源
			ToolsImage.deleteSSMFile(wechatActivity.getShareImage());

			if(wechatActivity.getTypes() != null){
				if("sign".equals(wechatActivity.getTypes())){
					//TODO 删除关联活动
					String url = com.xiaoshu.api.Set.SYSTEM_URL + "commodity/interfaceDel";
					String param = "id=" + wechatActivity.getCommodityId();
					ToolsHttpRequest.sendPost(url,param);
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
	 * @date 2018-02-07 10:50
	 */
	@RequestMapping("/interfaceDel")
	@ResponseBody
	public String interfaceDel(String id){
		try{
			WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
			if(wechatActivity != null){
				System.out.println("wechatActivity");
				wechatActivityService.delete(id);
				//删除资源
				if(wechatActivity.getShareImage() != null){
					if(!"".equals(wechatActivity.getShareImage())){
						ToolsImage.deleteSSMFile(wechatActivity.getShareImage());
					}
				}

				if(wechatActivity.getTypes() != null){
					if("sign".equals(wechatActivity.getTypes())){
						//TODO 删除关联活动
						String url = com.xiaoshu.api.Set.SYSTEM_URL + "commodity/interfaceDel";
						String param = "id=" + wechatActivity.getCommodityId();
						ToolsHttpRequest.sendPost(url,param);
					}
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return "ok";
	}


    /**
     * 更新活动
     */
	public static void updateActivity(WechatActivitySignSetService wechatActivitySignSetService,WechatActivityPrizeService wechatActivityPrizeService,WechatActivity bean,String setchineseCharacterName[],String setnamePlaceholder[],String setname[],String settypese[],Integer setrequired[],Integer setsort[],
                                      String setverificationType[],Integer sethide[],Integer setrepeat[],String setdescM[],Integer setsetType[],String[] prizeid){
        try{
            //删除以前的属性,并更新属性
            if(setname != null){
                wechatActivitySignSetService.deleteByWechatActivityId(bean.getId(),0);
                if(bean.getAuthorised() != -1){//授权开启，需要添加两个字段
					WechatActivitySignSet nicknameSignSet = new WechatActivitySignSet(ToolsString.putOffBar(UUID.randomUUID().toString()),"昵称", "nickname", "","", 0, "input", -1, "-1", 10000,1, -1, "微信昵称", bean.getId(), null, 1, new Date(), null);
					wechatActivitySignSetService.saveRecord(nicknameSignSet);
					WechatActivitySignSet headimgurlSignSet = new WechatActivitySignSet(ToolsString.putOffBar(UUID.randomUUID().toString()),"头像", "headimgurl", "","", 0, "input", -1, "-1", 10001,1, -1, "微信头像", bean.getId(), null, 1, new Date(), null);
					wechatActivitySignSetService.saveRecord(headimgurlSignSet);
                }

                /** 循环保存wechatActivitySignSet */
                if(setname != null){
					for (int i = 0; i < setname.length; i++ ) {
						if(setname[i] != null){
							if(!"nickname".equals(setname[i]) && !"headimgurl".equals(setname[i])){
								WechatActivitySignSet wechatActivitySignSet = new WechatActivitySignSet(ToolsString.putOffBar(UUID.randomUUID().toString()),setchineseCharacterName[i], setname[i], setnamePlaceholder[i],"", setsetType[i], settypese[i], setrequired[i], setverificationType[i], setsort[i],sethide[i], setrepeat[i], setdescM[i], bean.getId(), null, 1, new Date(), null);
								wechatActivitySignSetService.saveRecord(wechatActivitySignSet);
							}
						}

					}
				}

            }

            //去掉以前的奖品引用
            List<WechatActivityPrize> listPrize = wechatActivityPrizeService.getByActivityId(bean.getId());
            if(listPrize != null){
                for (int i = 0; i < listPrize.size(); i++ ) {
                    //删除活动奖品引用
                    wechatActivityPrizeService.updateWechatActivityIdById(listPrize.get(i).getId(),"-1");
                }
            }
            if(prizeid != null){
                for (int i = 0; i < prizeid.length; i++ ) {
                    //更新活动奖品引用
                    wechatActivityPrizeService.updateWechatActivityIdById(prizeid[i],bean.getId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


	/**
	 * 更新url
	 * @param bean
	 */
	public static void updateUrl(WechatActivity bean,PublicAccountInfoService publicAccountInfoService){
		/** 对URL进行新的定义 */
		int share = bean.getShare();
		int authorised = bean.getAuthorised();//授权开关
		StringBuffer sb = new StringBuffer();
		if(authorised != -1){ sb.append(Set.SYSTEM_URL + "activityReception/toSignUp" + Set.INTERCEPTOR_USER_URL); }
		if(authorised == -1){ sb.append(Set.SYSTEM_URL + "activityReception/toSignUp" + Set.INTERCEPTOR_NOTNEED_URL); }
		sb.append("?menuId=");
		if(bean != null){
			if(bean.getBindingWechatId() != null){
				if(!"".equals(bean.getBindingWechatId())){
					PublicAccountInfo publicAccountInfo = publicAccountInfoService.selectByPrimaryKey(bean.getBindingWechatId());
					sb.append(publicAccountInfo.getParentMenuId());
				}
			}
		}
		sb.append("&id="+bean.getId());
		if(share != -1){
			sb.append("&from=singlemessage&isappinstalled=0");
		}
		bean.setUrl(sb.toString());
	}



	/**
	 * 核销人员管理
	 * @param id 活动id
	 * @return view
	 * @author XGB
	 * @date 2018-03-06 17:01
	 */
	@RequestMapping("/listScanUser")
	public ModelAndView listScanUser(Model model,String id) {
		List<FocusedUserInfo> list = new ArrayList<FocusedUserInfo>();
		try{
			WechatActivity bean = wechatActivityService.getByPrimaryKey(id);
			if(bean != null){
				PublicAccountInfo publicAccountInfo = publicAccountInfoService.selectByPrimaryKey(bean.getBindingWechatId());
				model.addAttribute("menuId",publicAccountInfo.getParentMenuId());
				if(bean.getScanUserIdArray() != null){
					if(!"".equals(bean.getScanUserIdArray())){
						String[] userIdArray = bean.getScanUserIdArray().split(",");
						for (int i=0 ; i < userIdArray.length ; i++){
							String userId = userIdArray[i];
							FocusedUserInfo userInfo = focusedUserInfoService.selectByPrimaryKey(userId);
							if(userInfo != null){
								list.add(userInfo);
							}
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		model.addAttribute("list", list);
		model.addAttribute("id", id);
		return toVm("admin/wechatActivity/wechatActivity_ScanList");
	}


	/**
	 * 添加核销二维码展示页面
	 * @param id 主键ID
	 * @return view
	 * @author XGB
	 * @date 2018-03-06 17:24
	 */
	@RequestMapping("/toAddScan")
	public ModelAndView toAddScan(String menuId,String id,Model model){
		String url = Set.SYSTEM_URL + "wechatInUser/userAddScanInUser?id=" + id +"&menuId=" + menuId ;
		try {
            url = URLEncoder.encode(url,"UTF-8");
            model.addAttribute("menuId", menuId);
            model.addAttribute("id", id);
            model.addAttribute("url", url);
        }catch (Exception e){
		    e.printStackTrace();
        }
		return toVm("admin/wechatActivity/wechatActivity_ScanView");
	}

	/**
	 * 添加核销人员
	 * @param id 主键ID
	 * @return view
	 * @author XGB
	 * @date 2018-03-06 20:28
	 */
	@RequestMapping("/addScaninterface")
	@ResponseBody
	public String addScaninterface(String userId, String id, HttpServletResponse response){
	    int json = 0;
        response.setCharacterEncoding("UTF-8");
		try{
			String scan = wechatActivityService.getScanUserIdArrayById(id);
			if(scan == null){
				scan = userId + ",";
                json = wechatActivityService.updateScanUser(id,scan);
                response.getWriter().print(json);
			}else{
				if(scan.indexOf(userId) == -1 ){
					scan = scan + userId + ",";
                    json = wechatActivityService.updateScanUser(id,scan);
                    response.getWriter().print(json);
				}else{
                    response.getWriter().print(json);
                }
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 删除核销人员
	 * @param id 主键ID
	 * @return String
	 * @author XGB
	 * @date 2018-02-07 10:50
	 */
	@RequestMapping("/delScan")
	@ResponseBody
	public String delScan(String userId,String id){
		try{
			String scanUserIdArray = wechatActivityService.getScanUserIdArrayById(id);
			if(scanUserIdArray != null){
				if(!"".equals(scanUserIdArray)){
					scanUserIdArray = scanUserIdArray.replaceAll(userId+",","");
					wechatActivityService.updateScanUser(id,scanUserIdArray);
				}
			}
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}



	/**
	 * 维护商品活动关系
	 * @return String
	 * @author XGB
	 * @date 2018-04-10 16:26
	 */
	@RequestMapping("/interfaceMaintain")
	@ResponseBody
	public String interfaceMaintain(String json,HttpServletResponse response){
		try{
			if(json != null){
				if(!"".equals(json)){
					json = ToolsASCIIChang.asciiToString(json);
					Commodity commodity = JSONUtils.toBean(json, Commodity.class);
					if(commodity != null){
						Seller seller = sellerService.findSellerByIdService(commodity.getSellerId());
						String  publicId = publicAccountInfoService.getIdByParentMenuId(seller.getMenuId());
						String id = commodity.getWechatActivityId();
						WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
						if(wechatActivity == null ) {
							wechatActivity = new WechatActivity();
							//TODO 新建关联活动
//							Date date1 = ToolsDate.StringformatDate(ToolsDate.simpleSecond,commodity.getCreateDate());
//							Date date2 = ToolsDate.StringformatDate(ToolsDate.simpleSecond,commodity.getInvalidDate());

							String title = "";
							int share = commodity.getShare();
//							String shareTitle = "";
//							String shareDescM = "";
//							String shareImage = "";
							String menuId = "";
							String bingWechatId = "";
							int commodityId = commodity.getId();

							if(publicId != null){
								if(!"".equals(publicId)){
									bingWechatId = publicId;
								}
							}
							if(commodity.getCommodityName() != null){
								if(!"".equals(commodity.getCommodityName())){
									title = commodity.getCommodityName();
								}
							}
//							if(commodity.getShareTitle() != null){
//								if(!"".equals(commodity.getShareTitle())){
//									shareTitle = commodity.getShareTitle();
//								}
//							}
//
//							if(commodity.getShareDescM() != null){
//								if(!"".equals(commodity.getShareDescM())){
//									shareDescM = commodity.getShareDescM();
//								}
//							}
//
//							if(commodity.getShareImage() != null){
//								if(!"".equals(commodity.getShareImage())){
//									shareImage = commodity.getShareImage();
//								}
//							}
							if(seller.getMenuId() != null){
								if(!"".equals(seller.getMenuId())){
									menuId = seller.getMenuId();
								}
							}
							wechatActivity.setId(id);
							wechatActivity.setTitle(title);
							wechatActivity.setShare(share);
//							wechatActivity.setShareTitle(shareTitle);
//							wechatActivity.setShareDescM(shareDescM);
//							wechatActivity.setShareImage(shareImage);
							wechatActivity.setMenuId(menuId);
							wechatActivity.setBeginTime(commodity.getCreateDate());
							wechatActivity.setEndTime(commodity.getInvalidDate());
							wechatActivity.setBindingWechatId(bingWechatId);
							wechatActivity.setCommodityId(commodityId);

							wechatActivity.setSubscribeWechatId("-1");
							wechatActivity.setPrizesType("auto");
							wechatActivity.setTypes("sign");
							wechatActivity.setStatus(-1);
							wechatActivity.setCreateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));


							int result = wechatActivityService.saveRecord(wechatActivity);
							response.getWriter().print(result);
						}else {
							//TODO 维护关联活动
//							Date date1 = ToolsDate.StringformatDate(ToolsDate.simpleSecond,commodity.getCreateDate());
//							Date date2 = ToolsDate.StringformatDate(ToolsDate.simpleSecond,commodity.getInvalidDate());

							String title = "";
							int share = commodity.getShare();
//							String shareTitle = "";
//							String shareDescM = "";
//							String shareImage = "";
							String menuId = "";
							String bingWechatId = "";
							int commodityId = commodity.getId();

							if(publicId != null){
								if(!"".equals(publicId)){
									bingWechatId = publicId;
								}
							}
							if(commodity.getCommodityName() != null){
								if(!"".equals(commodity.getCommodityName())){
									title = commodity.getCommodityName();
								}
							}
//							if(commodity.getShareTitle() != null){
//								if(!"".equals(commodity.getShareTitle())){
//									shareTitle = commodity.getShareTitle();
//								}
//							}
//
//							if(commodity.getShareDescM() != null){
//								if(!"".equals(commodity.getShareDescM())){
//									shareDescM = commodity.getShareDescM();
//								}
//							}
//
//							if(commodity.getShareImage() != null){
//								if(!"".equals(commodity.getShareImage())){
//									shareImage = commodity.getShareImage();
//								}
//							}
							if(seller.getMenuId() != null){
								if(!"".equals(seller.getMenuId())){
									menuId = seller.getMenuId();
								}
							}
							wechatActivity.setId(id);
							wechatActivity.setTitle(title);
							wechatActivity.setShare(share);
							wechatActivity.setMenuId(menuId);
							wechatActivity.setBeginTime(commodity.getCreateDate());
							wechatActivity.setEndTime(commodity.getInvalidDate());
							wechatActivity.setBindingWechatId(bingWechatId);
							wechatActivity.setCommodityId(commodityId);

							wechatActivity.setSubscribeWechatId("-1");
							wechatActivity.setPrizesType("auto");
							wechatActivity.setTypes("sign");
							wechatActivity.setStatus(-1);
							wechatActivity.setUpdateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));

							int result = wechatActivityService.updateRecord(wechatActivity);
							response.getWriter().print(result);
						}
					}
				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
