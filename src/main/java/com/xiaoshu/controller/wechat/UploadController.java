package com.xiaoshu.controller.wechat;

import com.xiaoshu.api.Api;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.UserService;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import com.xiaoshu.util.JsonUtils;
import com.xiaoshu.util.StringUtils;
import com.xiaoshu.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 网站文件上传都走这个Controller
 * Created by Kun on 2017/12/22 0022.
 * @author kun
 */
@Controller
@RequestMapping(value = "upload")
public class UploadController extends BaseController {

    @Resource
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 上传图片
     * @param headImgUp 文件
     * @return Map,其中包含服务器放回的文件String ,以及组装之后的可访问路径
     * @author zhou.zhengkun
     * @date 2017/12/22 0022 10:31
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/uploadImage",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String uploadImage(@RequestParam(required = false) MultipartFile headImgUp) throws Exception {
        try{
            //得到文件map对象
            String fileId = UploadUtils.uploadFile(headImgUp,null);
            String fileUrl = UploadUtils.getFileUrl(fileId);
            Map<String,Object> result = new HashMap<String,Object>(4);
            result.put("fileId",fileId);
            result.put("fileUrl",fileUrl);
            return JsonUtils.turnJson(true,"1",result);
        }catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.turnJson(false,"2",e);
        }
    }

    /**
     * 用户修改头像(跳转页面)
     * @author zhou.zhengkun
     * @date 2018/01/11 0011 14:50
     */
    @RequestMapping(value = "/changeHeadImg")
    public ModelAndView changeHeadImg(HttpServletRequest request, Model model){
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser != null){
            String headImg = currentUser.getHeadImg();
            String id = currentUser.getUserid()+"";
            model.addAttribute("oldHeadImg",headImg);
            model.addAttribute("id",id);
        }
        return toVm("system/changeHeadImg");
    }

    /**
     * 保存头像
     * @param userId 用户ID
     * @param headImg 用户头像
     * @return String
     * @author zhou.zhengkun
     * @date 2018/01/12 0012 09:54
     */
    @RequestMapping(value = "/saveHeadImg")
    @ResponseBody
    public String saveHeadImg(String userId, String headImg, HttpSession session){
        try{
            if (StringUtils.isBlank(userId)){
                return JsonUtils.turnJson(false,"参数错误","userId : " +userId);
            }
            User user = userService.findOneUser(Integer.valueOf(userId));
            if (user != null){
                user.setHeadImg(headImg);
                userService.updateUser(user);
                session.setAttribute("currentUser",user);
                User newUser = userService.findOneUser(Integer.valueOf(userId));
                if(newUser != null){
                    if(newUser.getHeadImg() != null){
                        if(headImg.equals(newUser.getHeadImg())){
                            //保存成功更新资源状态
                            ToolsHttpRequest.sendPost(Api.UPDATE_FILE_STATUS, "material_id=" + newUser.getHeadImg());
                            if(user.getHeadImg() != null){
                                if(!"".equals(user.getHeadImg())){
                                    if(!user.getHeadImg().equals(newUser.getHeadImg())){
                                        String returnStr = ToolsHttpRequest.sendGet(Api.DEL_FILE, "material_id=" + user.getHeadImg());
                                        System.out.println("==>>旧资源清除情况：" + returnStr + " material_id=" + user.getHeadImg());
                                    }

                                }
                            }
                        }
                    }
                    newUser.setHeadImg(ToolsImage.getImageUrlByServer(newUser.getHeadImg()));
                    session.setAttribute("currentUser",newUser);
                }
            }
        }catch (Exception e){
            return JsonUtils.turnJson(false,"error :" +e.getMessage(),e);
        }
        return JsonUtils.turnJson(true,"success",null);
    }

    /***
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     *
     * @param file
     * @return
     */
    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/upload/"
                        + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 重定向
        return "redirect:list";
    }

    /***
     * 读取上传文件中得所有文件并返回
     *
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list() {
        String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/upload/";
        ModelAndView mav = new ModelAndView("list");
        File uploadDest = new File(filePath);
        String[] fileNames = uploadDest.list();
        for (int i = 0; i < fileNames.length; i++) {
            //打印出文件名
            System.out.println(fileNames[i]);
        }
        return mav;
    }

    @RequestMapping("/imgTest")
    public String imgTest(){
        return "list";
    }
}
