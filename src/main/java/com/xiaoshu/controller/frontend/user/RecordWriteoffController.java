package com.xiaoshu.controller.frontend.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshu.entity.RecordWriteOff;
import com.xiaoshu.service.RecordWriteOffService;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recordWriteInUser")
public class RecordWriteoffController {

	@Resource private RecordWriteOffService recordWriteOffService;
	private static final int PAGE_SIZE = 15;
	/**
	 * 查询我的核销记录 recordWriteInUser/recordWriteOff?cpage=1&userId=&menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-03-21 09:30
	 */
	@RequestMapping("recordWriteOff")
	public String recordWriteOff(HttpServletRequest req,Integer cpage,String userId,String menuId){
		try {
			int pageSize = PAGE_SIZE;
			int index = (cpage - 1) * pageSize;
			List<RecordWriteOff> list = recordWriteOffService.findRecordWriteOffByUserIdService(index, pageSize, userId);
			int totalRecords = recordWriteOffService.totalRecordWriteOffByUserIdService(userId);;//总记录数
			int totalPage = ToolsPage.totalPage(totalRecords, pageSize);//总页数
			if(list != null){
				req.setAttribute("size", list.size());
			}else{
				req.setAttribute("size", 0);
			}
			req.setAttribute("list", list);
			req.setAttribute("totalPage", totalPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("menuId", menuId);
		return "mobile/user/userRecordWriteOff";
	}

	/**
	 * 查询我的核销记录 recordWriteInUser/recordWriteOffByJSON?cpage=1&userId=&menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-03-21 09:35
	 */
	@RequestMapping("recordWriteOffByJSON")
	public String recordWriteOffByJSON(HttpServletResponse response, Integer cpage, String userId, String menuId){
		try {
			int pageSize = PAGE_SIZE;
			int index = (cpage - 1) * pageSize;
			List<RecordWriteOff> list = recordWriteOffService.findRecordWriteOffByUserIdService(index, pageSize, userId);
			response.getWriter().print(JSONUtils.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 查询我核销的订单记录 recordWriteInUser/recordWriteOffByMe?cpage=1&userId=&menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-03-21 10:00
	 */
	@RequestMapping("recordWriteOffByMe")
	public String recordWriteOffByMe(HttpServletRequest req,Integer cpage,String orderUserId,String menuId){
		try {
			int pageSize = PAGE_SIZE;
			int index = (cpage - 1) * pageSize;
			List<RecordWriteOff> list = recordWriteOffService.findRecordWriteOffByOrderUserId(index, pageSize, orderUserId);
			int totalRecords = recordWriteOffService.totalRecordWriteOffByOrderUserId(orderUserId);;//总记录数
			int totalPage = ToolsPage.totalPage(totalRecords, pageSize);//总页数
			if(list != null){
				req.setAttribute("size", list.size());
			}else{
				req.setAttribute("size", 0);
			}
			req.setAttribute("list", list);
			req.setAttribute("totalPage", totalPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("menuId", menuId);
		return "mobile/user/userRecordWriteOffByMe";
	}

	/**
	 * 查询我核销的订单记录 recordWriteInUser/recordWriteOffByMeJSON?cpage=1&userId=&menuId=
	 * @return String
	 * @author XGB
	 * @date 2018-03-21 10:04
	 */
	@RequestMapping("recordWriteOffByMeJSON")
	public String recordWriteOffByMeJSON(HttpServletResponse response, Integer cpage, String orderUserId, String menuId){
		try {
			int pageSize = PAGE_SIZE;
			int index = (cpage - 1) * pageSize;
			List<RecordWriteOff> list = recordWriteOffService.findRecordWriteOffByOrderUserId(index, pageSize, orderUserId);
			response.getWriter().print(JSONUtils.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
