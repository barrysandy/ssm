package com.xiaoshu.controller.admin.shop;

import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller")
public class AdminSellerController {
    @Resource private SellerService sellerService;
    @Resource private OrderWriteOffByUserService orderWriteOffByUserService;
    @Resource private FocusedUserInfoService focusedUserInfoService;


    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-03-09 17:01
     */
    @RequestMapping("/list")
    public String list(Pager pager, Model model, String search, String menuid) {
        try{
            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            map.put("menuid",menuid);
            List<Seller> list = sellerService.findAllSellerService(startRow,pageSize,search);
            int totalNum = sellerService.totalSellerByKeyService(search);
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
            model.addAttribute("menuid",menuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/seller/seller_list";
    }

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-03-09 17:01
     */
    @RequestMapping("/toEdit")
    public String toEdit(String id, Model model, String menuId){
        try{
            Seller rule = new Seller();
            if(StringUtils.isNotBlank(id)){
                rule = sellerService.findSellerByIdService(Integer.parseInt(id));
            }
            model.addAttribute("bean", rule);
            model.addAttribute("id", id);
            model.addAttribute("menuId",menuId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/seller/seller_edit";
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-03-09 17:50
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(Seller bean){
        try{
            if(bean!=null){
                String id = bean.getId()+"";
                if(StringUtils.isNotBlank(id) && !"0".equals(id)){
                    sellerService.updateSellerService(bean);
                }else{
                    sellerService.saveSellerService(bean);
                }
            }
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }

    /**
     * 预览
     * @param id
     * @param model
     * @return
     * @author XGB
     * @date 2018-03-09 17:50
     */
    @RequestMapping("/toView")
    public String toView(String id, Model model, String menuid){
        try{
            if(id != null){
                Seller bean = sellerService.findSellerByIdService(Integer.parseInt(id));
                model.addAttribute("bean", bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("menuid",menuid);
        return "admin/seller/seller_view";
    }

    /**
     * 删除
     * @param id 主键ID
     * @return String
     * @author XGB
     * @date 2018-03-09 17:50
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id){
        try{
            sellerService.deleteSellerService(Integer.parseInt(id));
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }

    /**
     * 核销人员管理
     * @param id 活动id
     * @return view
     * @author XGB
     * @date 2018-03-06 17:01
     */
    @RequestMapping("/listScanUser")
    public String listScanUser(Model model, Integer id, String menuId) {
        try{
            List<OrderWriteOffByUser> list = orderWriteOffByUserService.findAllWaterBillService(id);
            if(list != null){
                Iterator<OrderWriteOffByUser> iterator = list.iterator();
                while (iterator.hasNext()){
                    OrderWriteOffByUser orderWriteOffByUser = iterator.next();
                    if(orderWriteOffByUser != null){
                        if(orderWriteOffByUser.getUserId() != null){
                            FocusedUserInfo user = focusedUserInfoService.selectByPrimaryKey(orderWriteOffByUser.getUserId());
                            if(user != null){
                                orderWriteOffByUser.setUser(user);
                            }
                        }
                    }
                }
            }
            model.addAttribute("list", list);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("id", id);
        model.addAttribute("menuId", menuId);
        return "admin/seller/seller_ScanList";
    }

    /**
     * 添加核销二维码展示页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-03-06 17:24
     */
    @RequestMapping("/toAddScan")
    public String toAddScan(String menuId,String id,Model model){
        String url = com.xiaoshu.api.Set.SYSTEM_URL + "wechatInUser/userAddSellerScanInUser?id=" + id +"&menuId=" + menuId ;
        try {
            url = URLEncoder.encode(url,"UTF-8");
            model.addAttribute("menuId", menuId);
            model.addAttribute("id", id);
            model.addAttribute("url", url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/seller/seller_ScanView";
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
    public String addScaninterface(String userId, Integer id, HttpServletResponse response){
        int json = 0;
        response.setCharacterEncoding("UTF-8");
        try{
            int exit = orderWriteOffByUserService.existService(id,userId);
            if(exit == 0){
                String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
                OrderWriteOffByUser orderWriteOffByUser = new OrderWriteOffByUser(0, id, userId, 1, time);
                json =  orderWriteOffByUserService.saveService(orderWriteOffByUser);
            }
            response.getWriter().print(json);
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
    public String delScan(Integer id){
        try{
            orderWriteOffByUserService.deleteService(id);
        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


}
