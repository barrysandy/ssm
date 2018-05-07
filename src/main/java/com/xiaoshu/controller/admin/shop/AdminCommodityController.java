package com.xiaoshu.controller.admin.shop;

import com.xiaoshu.api.*;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.*;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.Pager;
import com.xiaoshu.util.StringUtils;
import com.xiaoshu.vo.CommodityImage;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/commodity")
public class AdminCommodityController {
    @Resource private CommodityService commodityService;
    @Resource private SellerService sellerService;
    @Resource private CommodityPriceListService commodityPriceListService;

    /**
     * 分页查询
     * @param pager 分页
     * @param model model
     * @return view
     * @author XGB
     * @date 2018-03-09 17:01
     */
    @RequestMapping("/list")
    public String list(Pager pager, Model model, String search, String menuid, String seller_id) {
        try{
            if(seller_id == null){
                seller_id = "-1";
                if("".equals(seller_id)){
                    seller_id = "-1";
                }
            }
            Map map = new HashMap(8);
            int startRow = (pager.getPageNo() - 1) * pager.getPageSize();
            int pageSize = pager.getPageSize();
            map.put("startRow", startRow);
            map.put("pageSize", pager.getPageSize());
            map.put("search", search);
            map.put("menuid",menuid);
            map.put("seller_id",seller_id);
            List<Commodity> list = commodityService.findAllCommodityByKeyService(startRow, pageSize, search, Integer.parseInt(seller_id));
            if(list != null){
                if(list.size() > 0){
                    Iterator<Commodity> iterator = list.iterator();
                    while(iterator.hasNext()){
                        Commodity commodity = iterator.next();
                        Seller seller = sellerService.findSellerByIdService(commodity.getSellerId());
                        commodity.setSeller(seller);
                        String arrimg = commodity.getArrayImg();
                        if(arrimg != null && !"".equals(arrimg)){
                            String[] arrStrImg = arrimg.split(",");//
                            if(arrStrImg.length > 0){
                                for (int i = 0; i < arrStrImg.length; i++) {
                                    if( i== 0){
                                        commodity.setArrayImg(arrStrImg[i]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int totalNum = commodityService.totalCommodityByKeyService(search,Integer.parseInt(seller_id));//统计总记录数
            pager.setFullListSize(totalNum);
            pager.setList(list);
            model.addAttribute("pager", pager);
            model.addAttribute("bean", list);
            model.addAttribute("search", search);
            model.addAttribute("seller_id", seller_id);
            model.addAttribute("menuid",menuid);
            if(seller_id != "-1"){
                Seller seller = sellerService.findSellerByIdService(Integer.parseInt(seller_id));
                model.addAttribute("seller",seller);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/commodity/commodity_list";
    }

    /**
     * 编辑页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-03-09 17:01
     */
    @RequestMapping("/toEdit")
    public String toEdit( Model model,String id,Integer sellerId,String menuId){
        String imageShare = "";
        try{
            Commodity bean = new Commodity(0, "", "", 0, "", 0.01,
                    sellerId, null, 1, "", "", "", 0, "", "",
                    "", "", -1, -1, -1, -1, 0, "-1",1,0,-1,-1,"",1);

            bean.setMaxNumber(-1);
            bean.setMaxNumber2(-1);
            if(StringUtils.isNotBlank(id)){
                bean = commodityService.findCommodityByIdService(Integer.parseInt(id));
                //每次更新设置为可用
                bean.setTimeStatus(1);
            }
            if(bean.getShareImage() != null){
                imageShare = ToolsImage.getImageUrlByServer(bean.getShareImage());
            }

            bean.setCommodityDetails(ToolsString.conversionCSSCodeIcon(bean.getCommodityDetails()));
            model.addAttribute("bean", bean);
            model.addAttribute("image", imageShare);
            model.addAttribute("id", id);
            model.addAttribute("menuId",menuId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/commodity/commodity_edit";
    }

    /**
     * 编辑视频页面
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-03-09 17:01
     */
    @RequestMapping("/toEditVideo")
    public String toEditVideo( Model model,String id,String menuId){
        try{
            Commodity bean =  commodityService.findCommodityByIdService(Integer.parseInt(id));
            model.addAttribute("bean", bean);
            model.addAttribute("id", id);
            model.addAttribute("menuId",menuId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/commodity/commodity_editVideo";
    }


    /**
     * 商品价格编辑器
     * @param id 主键ID
     * @return view
     * @author XGB
     * @date 2018-03-09 17:01
     */
    @RequestMapping("/toEditPrice")
    public String toEditPrice( Model model,Integer id,Integer sellerId,String menuId){
        try{
            if(id != 0){
                List<CommodityPriceList> groupPriceList = commodityPriceListService.listGroupPTimeByCommodityId(id);
                if(groupPriceList != null){
                    String[] choseDay = new String[groupPriceList.size()];
                    for (int i=0;i<groupPriceList.size();i++ ){
                        choseDay[i] = groupPriceList.get(i).getDescM();
                        String priceTime = groupPriceList.get(i).getPriceTime();
                        List<CommodityPriceList> commodityPriceList = commodityPriceListService.listByCommodityIdAndPTime(id,priceTime);
                        model.addAttribute("list" + i,commodityPriceList);
                    }
                    model.addAttribute("choseDay",choseDay);
                    model.addAttribute("choseDayLength",choseDay.length);
                }
                model.addAttribute("id", id);
                model.addAttribute("menuId",menuId);
                model.addAttribute("sellerId",sellerId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "admin/commodity/commodity_editPrice";
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
    public String saveOrUpdate(Commodity bean){
        try{
            if(bean != null){
                if(bean.getId() != null && bean.getId() != 0){
                    commodityService.updateCommodityService(bean);
                }else{
                    commodityService.saveCommodityService(bean);
                }
            }
            String wechatActivityId;
            if(bean.getWechatActivityId() != null){
                if(!"-1".equals(bean.getWechatActivityId())) {
                    wechatActivityId = bean.getWechatActivityId();
                }else {
                    wechatActivityId = UUID.randomUUID().toString();
                }
            }else {
                wechatActivityId = UUID.randomUUID().toString();
            }

            bean.setWechatActivityId(wechatActivityId);
            bean.setCommodityDetails("");
            System.out.println("====================bean.getTypese()=========================");
            System.out.println(bean.getTypese());
            System.out.println("====================bean.getTypese()=========================");
            //TODO 同步关联
            if( bean.getTypese() == 0 || bean.getTypese() == 3 ) {
                String json = JSONUtils.toJSONString(bean);
                json = ToolsString.getStrRemoveBracket(json);
                json = ToolsASCIIChang.stringToAscii(json);
                //TODO 维护关联活动
                String url = com.xiaoshu.api.Set.SYSTEM_URL + "activity/interfaceMaintain";
                String param = "json=" + json;
                String result = ToolsHttpRequest.sendPost(url,param);
                if(result != null) {
                    System.out.println("====================商品维护关联活动=========================");
                    System.out.println("===================="+result+"=========================");
                    System.out.println("====================商品维护关联活动=========================");
                    if("1".equals(result)) {
                        //Todo 成功，更新id
                        commodityService.updateActivityIdById(bean.getId(),wechatActivityId);
                    }
                }
            }

        }catch(Exception e){
            return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }


    /**
     * 保存操作
     * @param bean 实体类
     * @return String
     * @author XGB
     * @date 2018-03-09 17:50
     */
    @RequestMapping("/saveOrUpdateVideo")
    @ResponseBody
    public String saveOrUpdateVideo(Commodity bean){
        try{
            if(bean != null){
                commodityService.updateVideoService(bean);
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
                Commodity rule = commodityService.findCommodityByIdService(Integer.parseInt(id));
                List<CommodityImage> listCommodityImage = new ArrayList<CommodityImage>();
                if(rule.getArrayImg() != null){
                    /** 截取arrayImg ，替换material_id 为 url 生成图片集合*/
                    String[] arrayImg = rule.getArrayImg().split(",");
                    if(arrayImg != null){
                        if(arrayImg.length > 0){
                            for (int i = 0;i<arrayImg.length ;i++){
                                if(arrayImg[i] != null){
                                    if(!"".equals(arrayImg[i])){
                                        CommodityImage commodityImage = new CommodityImage();
                                        commodityImage.setMaterial_id(arrayImg[i]);
                                        //替换素材的在文件服务器上的url
                                        String imageUrl = ToolsImage.getImageUrlByServer(arrayImg[i]);
                                        commodityImage.setUrl(imageUrl);
                                        listCommodityImage.add(commodityImage);
                                    }
                                }


                            }
                        }
                    }
                }
                model.addAttribute("listCommodityImage", listCommodityImage);
                model.addAttribute("id", id);
                model.addAttribute("bean", rule);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("menuid",menuid);
        return "admin/commodity/commodity_view";
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
    public String del(String id,HttpServletRequest request){
        try{
            Commodity bean = commodityService.findCommodityByIdService(Integer.parseInt(id));
            int deleteResult = commodityService.deleteCommodityService(Integer.parseInt(id));
            if(deleteResult > 0) {
                if(bean != null){
                    if(bean.getArrayImg() != null){
                        if(!"".equals(bean.getArrayImg())){
                            /** 截取arrayImg ，替换material_id 为 url 生成图片集合*/
                            String[] arrayImg = bean.getArrayImg().split(",");
                            if(arrayImg != null){
                                for (int i = 0;i<arrayImg.length ;i++){
                                    if(arrayImg[i] != null){
                                        if(!"".equals(arrayImg[i])){
                                            //删除素材的在文件服务器上的url
                                            String param = "material_id=" + arrayImg[i];
                                            String json = ToolsHttpRequest.sendGet(Api.DEL_FILE, param);
                                            System.out.println("------------ [out] Delete material_id [" + param + "] the URL of the material on the file server Return: " + json + " ------------");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                List<String> listDetailsImage = ToolsString.getImg(bean.getCommodityDetails());
                if(listDetailsImage != null) {
                    if(listDetailsImage.size() > 0){
                        Iterator<String> iterator = listDetailsImage.iterator();
                        while (iterator.hasNext()) {
                            String url = iterator.next();
                            System.out.println(url);
                            String[] urls = url.split("resources");
                            url = urls[1];
                            System.out.println(">>>**url**" + url);
                            String root = request.getRealPath("/resources");//文件的根目录
                            System.out.println(">>>**root**" + root);
                            File file = new File(root,url);
                            if(file.exists()){
                                file.delete();
                                System.out.println(">>>**delete file complete!**" + file);
                            }
                        }
                    }
                }

            }
            if(bean.getTypese() != null){
                if("SIGN".equals(bean.getTypese())){
                    //TODO 删除关联活动
                    String url = com.xiaoshu.api.Set.SYSTEM_URL + "activity/interfaceDel";
                    String param = "id=" + bean.getWechatActivityId();
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
     * @date 2018-03-09 17:50
     */
    @RequestMapping("/interfaceDel")
    @ResponseBody
    public String interfaceDel(String id,HttpServletRequest request){
        try{
            Commodity bean = commodityService.findCommodityByIdService(Integer.parseInt(id));
            if(bean != null){
                int deleteResult = commodityService.deleteCommodityService(Integer.parseInt(id));
                if(deleteResult > 0) {
                    if(bean != null){
                        if(bean.getArrayImg() != null){
                            if(!"".equals(bean.getArrayImg())){
                                /** 截取arrayImg ，替换material_id 为 url 生成图片集合*/
                                String[] arrayImg = bean.getArrayImg().split(",");
                                if(arrayImg != null){
                                    for (int i = 0;i<arrayImg.length ;i++){
                                        if(arrayImg[i] != null){
                                            if(!"".equals(arrayImg[i])){
                                                //删除素材的在文件服务器上的url
                                                String param = "material_id=" + arrayImg[i];
                                                String json = ToolsHttpRequest.sendGet(Api.DEL_FILE, param);
                                                System.out.println("------------ [out] Delete material_id [" + param + "] the URL of the material on the file server Return: " + json + " ------------");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    List<String> listDetailsImage = ToolsString.getImg(bean.getCommodityDetails());
                    if(listDetailsImage != null) {
                        if(listDetailsImage.size() > 0){
                            Iterator<String> iterator = listDetailsImage.iterator();
                            while (iterator.hasNext()) {
                                String url = iterator.next();
                                System.out.println(url);
                                String[] urls = url.split("resources");
                                url = urls[1];
                                System.out.println(">>>**url**" + url);
                                String root = request.getRealPath("/resources");//文件的根目录
                                System.out.println(">>>**root**" + root);
                                File file = new File(root,url);
                                if(file.exists()){
                                    file.delete();
                                    System.out.println(">>>**delete file complete!**" + file);
                                }
                            }
                        }
                    }

                }
                if(bean.getTypese() != null){
                    if("SIGN".equals(bean.getTypese())){
                        //TODO 删除关联活动
                        String url = com.xiaoshu.api.Set.SYSTEM_URL + "activity/interfaceDel";
                        String param = "id=" + bean.getWechatActivityId();
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
     * 查询商品状态（上架/下架）
     * URL commodity/getState?id=1
     */
    @RequestMapping("getState")
    @ResponseBody
    public String getState(HttpServletRequest req, HttpServletResponse resp, @RequestParam("id") int id){
        resp.setCharacterEncoding("UTF-8");
        String data = "0";
        try {
            data = String.valueOf(commodityService.findCommodityStateByIdService(id));
            System.out.println(" data :" + data);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 更新商品状态（上架/下架）（Commodity）
     * URL   commodity/upState?id=1&commodityState=1
     */
    @RequestMapping("upState")
    public String upState(HttpServletRequest req,HttpServletResponse resp,@RequestParam("id") int id,@RequestParam("commodityState") int commodityState){
        resp.setCharacterEncoding("UTF-8");
        try {
            int i = commodityService.updateCommodityStateService(id, commodityState);
            resp.getWriter().print(i);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                    WechatActivity wechatActivity = JSONUtils.toBean(json, WechatActivity.class);
                    if(wechatActivity != null){
                        Commodity commodity = commodityService.findCommodityByIdService(wechatActivity.getCommodityId());
                        if(commodity == null ) {
                            //TODO 不支持新建关联商品
                        }else {
                            //TODO 维护关联商品
                            commodity.setCommodityName(wechatActivity.getTitle());
                            commodity.setCreateDate(wechatActivity.getBeginTime());
                            commodity.setInvalidDate(wechatActivity.getEndTime());
                            commodity.setShare(wechatActivity.getShare());
                            int result = commodityService.updateCommodityService(commodity);
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
