package com.xiaoshu.controller.admin.activity;

import com.xgb.springrabbitmq.dto.DtoMessage;
import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.enumeration.EnumsMQName;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 微信活动抽奖表
 * @name: WechatActivityDrawController
 * @author: XGB
 * @date: 2018-02-06 16:49
 */
@Controller
@RequestMapping(value = "/activityDraw")
public class WechatActivityDrawController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(WechatActivityDrawController.class);

	@Resource private WechatActivityService wechatActivityService;
	@Resource private WechatActivitySignService wechatActivitySignService;
	@Resource private WechatActivitySignSetService wechatActivitySignSetService;
	@Resource private FocusedUserInfoService focusedUserInfoService;
	@Resource private WechatActivityWinningService wechatActivityWinningService;
	@Resource private WechatActivityPrizeService wechatActivityPrizeService;
	@Resource private CommodityService commodityService;

	@Autowired private DeadLetterPublishService publishService;

	/**
	 * 分页查询
	 * @param pager 分页
	 * @param model model
	 * @return view
	 * @author XGB
	 * @date 2018-02-06 17:01
	 */
	@RequestMapping("/list")
	public ModelAndView list(Pager pager, String id, Model model, String search, int draw) {
		try{
			Map map = new HashMap(8);
			map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
			map.put("pageSize", pager.getPageSize());
			map.put("search", search);
			map.put("id",id);
			int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
			int pageSize = pager.getPageSize();
			List<WechatActivityWinning> list = wechatActivityWinningService.selectByPage(map);
			List<WechatActivitySignSet> listIsSet = wechatActivitySignSetService.getAllByActivityId(id,0);
			if(list != null){
				if(list.size() > 0){
					Iterator<WechatActivityWinning> iterator = list.iterator();
					while(iterator.hasNext()){
						WechatActivityWinning wechatActivityWinning = iterator.next();
						List<WechatActivitySignSet> listSet = wechatActivitySignSetService.getAllValuesByActivitySignId(wechatActivityWinning.getWechatActivitySignId());
						if(listSet != null){
							if(listSet.size() > 0){
								wechatActivityWinning.setWechatActivitySignSet(listSet);
							}
						}
					}
				}
			}
			int totalNum = wechatActivityWinningService.selectCount(map);
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
		return toVm("admin/wechatActivity/wechatActivityWinning_list");
	}

	/**
	 * 清理活动中奖名单
	 * @param id 活动id
	 * @return
	 */
	@RequestMapping("/cleanDraw")
	@ResponseBody
	public String cleanDraw(String id){
		try{
			return wechatActivityWinningService.deleteByWechatActivitySign(id) + "";
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 随机抽奖
	 * Access URL ：activityDraw/DrawFractionalline?id=
	 * @param id
	 *  @param total
	 */
	@RequestMapping("DrawFractionalline")
	public String DrawFractionalline(HttpServletRequest req, HttpServletResponse resp, @RequestParam("id") String id, @RequestParam("total") Integer total){
		resp.setCharacterEncoding("UTF-8");
		try{
			WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
			if(wechatActivity != null){
				//所有数据
				List<WechatActivitySign> listSign =  wechatActivitySignService.getAllSignByActivityId(id,0);
				req.setAttribute("listSign", listSign);
				StringBuffer headImg = new StringBuffer("");
				StringBuffer name = new StringBuffer("");
				if(wechatActivity.getAuthorised() == 1){//支持授权
					for (int i = 0; i < listSign.size(); i++) {
						String user_id = listSign.get(i).getUserId();
						FocusedUserInfo usertemp = focusedUserInfoService.selectByPrimaryKey(user_id);
						if((i + 1) == listSign.size()){
							appendNameAndHeadImg(usertemp,headImg);
							name.append(usertemp.getId());
							name.append("·");
							name.append(usertemp.getNickName());
						}else{
							appendNameAndHeadImg(usertemp,headImg);
							headImg.append("、");
							name.append(usertemp.getId());
							name.append("·");
							name.append(usertemp.getNickName());
							name.append("、");
						}
					}
				}else{//不支持授权
					for (int i = 0; i < listSign.size(); i++) {
							headImg.append("resources/RandomDraw/img/0000.jpg");
							headImg.append("、");
							name.append(listSign.get(i).getId());
							name.append("·");
							List<WechatActivitySignSet> listSet = wechatActivitySignSetService.getAllValuesByActivitySignId(listSign.get(i).getId());
							if(listSet != null){
								if(listSet.size() > 0){
									if(listSet.get(0) != null && listSet.get(0).getValuese() != null){
										name.append(listSet.get(0).getValuese());
									}else{
										name.append(i);
									}
								}else{
									name.append(i);
								}
							}else{
								name.append(i);
							}
							name.append("、");
						}
					}
				req.setAttribute("headImg", headImg);
				req.setAttribute("name", name);
				req.setAttribute("size", listSign.size());
				req.setAttribute("max", total);
				req.setAttribute("id", id);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/wechatActivity/RandomDraw/Draw";
	}


	/**
	 * 追加img
	 * @param usertemp
	 * @param headImg
	 */
	public static void appendNameAndHeadImg(FocusedUserInfo usertemp,StringBuffer headImg){
		if(usertemp != null){
			if(usertemp.getHeadImgUrl() != null){
				if(!"".equals(usertemp.getHeadImgUrl())){
					headImg.append(usertemp.getHeadImgUrl());
				}else{
					headImg.append("resources/RandomDraw/img/0000.jpg");
				}
			}else{
				headImg.append("resources/RandomDraw/img/0000.jpg");
			}
		}else{
			headImg.append("resources/RandomDraw/img/0000.jpg");
		}
	}



	/**
	 * 将中奖者数据保存
	 *  Access URL ：activityDraw/UpdateDrawFractionalline?strArrStu=&id=
	 * @param
	 */
	@RequestMapping("UpdateDrawFractionalline")
	public String UpdateDrawFractionalline(HttpServletRequest req,HttpServletResponse resp,@RequestParam("strArrStu") String strArrStu,@RequestParam("id") String id){
		resp.setCharacterEncoding("UTF-8");
		try{
			StringBuffer prizeDesc = new StringBuffer("");
			WechatActivity wechatActivity = wechatActivityService.getByPrimaryKey(id);
			if(wechatActivity != null){
				prizeDesc.append(wechatActivity.getTitle() + "活动，包含以下奖品：");
			}

			//奖品相关的商品id
			List<String> listCommdityId = new ArrayList<String>();
			List<WechatActivityPrize> listPrize = wechatActivityPrizeService.getByActivityId(wechatActivity.getId());
			if(listPrize != null){
				for(WechatActivityPrize prize : listPrize){
					prizeDesc.append(prize.getName() + " * " + prize.getQuantity());
					//TODO 将商品添加到集合中
					if(prize.getCommodityId() != -1){
						listCommdityId.add(String.valueOf(prize.getCommodityId()));
					}

				}
			}
			String names[] = strArrStu.split("、");
			int respStr = 0;
			for (int i = 0; i < names.length; i++) {
				String ids[] = names[i].split("·");
				String userId = ids[0];
				String signId = "";
				String code = ToolsString.generateRandNumber(6);
				while (wechatActivityWinningService.getExitByCode(code) != 0){
					code = ToolsString.generateRandNumber(6);
				}
				WechatActivitySign sign = null;
				if(userId != null && !"".equals(userId)){
					sign = wechatActivitySignService.getByUserAndActivityId(id,userId);
					if(sign != null){
						signId = sign.getId();
					}
				}

				//TODO 生成中奖
				WechatActivityWinning winnings = new WechatActivityWinning(ToolsString.putOffBar(UUID.randomUUID().toString()), prizeDesc.toString(), code, null,null, id, signId, userId, 0, new Date(), null);
				respStr = wechatActivityWinningService.saveRecord(winnings);

				//TODO 生成订单
				if(listCommdityId != null){
					if(listCommdityId.size() > 0){
						Iterator<String> iterator = listCommdityId.iterator();
						while (iterator.hasNext()){
							String cid = iterator.next();
							Integer commodityId = Integer.parseInt(cid);
							Commodity commodity = commodityService.findCommodityByIdService(commodityId);
							String userName = wechatActivitySignSetService.getValueByAIDAndUID(sign.getId(),sign.getWechatActivityId(),"name");
							String userPhone = wechatActivitySignSetService.getValueByAIDAndUID(sign.getId(),sign.getWechatActivityId(),"phone");
							//createOrder(userId,userName,userPhone,commodity, orderService, orderCodesService,resp,messageRecordService);
							//TODO 生成订单（通过消息方式）
							//TODO 死信延迟消息 QUEUE_NAME
							String url =  Set.SYSTEM_URL + "interfaceMqOrder/createOrder";
							String params = "userId=" + userId + "&userName=" + userName + "&userPhone=" + userPhone + "&commodityId=" + commodityId;
							DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), url, "get" ,params , null);
							String message = DtoMessage.transformationToJson(dtoMessage);
							System.out.println("=================" + message);
							publishService.send(EnumsMQName.DEAD_LETTER,message);

						}
					}
				}
			}
			//返回保存状态
			if(respStr > 0 ){
				resp.getWriter().print("保存成功");
			}else{
				resp.getWriter().print("保存失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 预览
	 * @param id
	 * @param model
	 * @return
	 * @author XGB
	 * @date 2018-02-26 10:50
	 */
	@RequestMapping("/toView")
	public ModelAndView toView(String id,Model model){
		WechatActivityWinning rule = new WechatActivityWinning();
		try{
			if(StringUtils.isNotBlank(id)){
				rule = wechatActivityWinningService.getByPrimaryKey(id);
			}
			if(rule.getUserId() != null){
				if(!"".equals(rule.getUserId())){
					FocusedUserInfo focusedUserInfo = focusedUserInfoService.selectByPrimaryKey(rule.getUserId());
					model.addAttribute("focusedUserInfo", focusedUserInfo);
				}
			}
			//奖品信息
			List<WechatActivityPrize> prizeList = wechatActivityPrizeService.getByActivityId(rule.getWechatActivityId());
			if(prizeList != null){
				rule.setPrizeList(prizeList);
			}
			//报名信息
			List<WechatActivitySignSet> listSet = wechatActivitySignSetService.getAllValuesByActivitySignId(rule.getWechatActivitySignId());
			if(listSet != null){
				model.addAttribute("listSet", listSet);
			}

			model.addAttribute("bean", rule);
		}catch (Exception e){
			e.printStackTrace();
		}
		return toVm("admin/wechatActivity/wechatActivityWinning_view");
	}

	/**
	 * 删除
	 * @param id 主键ID
	 * @return String
	 * @author XGB
	 * @date 2018-02-27 14:46
	 */
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		try{
			wechatActivityWinningService.deleteByPrimaryKey(id);
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}

}
