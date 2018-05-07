package com.xiaoshu.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author Kun
 * @date 2018/01/04
 */
public class BaseController {


    /**
     * action执行前会自动调用该方法
     *
     * @param request
     * @param response
     */
    @ModelAttribute
    public void beforeInvoke(HttpServletRequest request, HttpServletResponse response) {

    }

    public HttpServletRequest getRequest() {
        return ControllerContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ControllerContext.getResponse();
    }

    public ModelAndView toVm(String viewName) {
        return toVm(viewName, null);
    }

    @SuppressWarnings("unchecked")
    public ModelAndView toVm(String viewName, Object data) {
        ModelAndView mav = new ModelAndView(viewName);
        if (data != null) {
            if (data instanceof Map) {
                mav.addAllObjects((Map<String, ?>) data);
            } else {
                mav.addObject("data", data);
            }
        }
        return mav;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }
}
