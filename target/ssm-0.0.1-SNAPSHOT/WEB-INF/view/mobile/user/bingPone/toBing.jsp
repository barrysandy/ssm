<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width">
    <meta name="viewport" content="initial-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
    <title>联系方式</title>

    <link rel="stylesheet" href="css/phone.css">
    <link rel="stylesheet" type="text/css" href="${path}/resources/css/userCenterBingPhonePage/css/weui.min.css">
    <link rel="stylesheet" type="text/css" href="${path}/resources/css/userCenterBingPhonePage/css/jquery-weui.css">
    <link rel="stylesheet" type="text/css" href="${path}/resources/css/userCenterBingPhonePage/css/demos.css">

    <!--=========引入Alert=========-->
    <script type="text/javascript" src="${path}/resources/alert/1.1/alertPopShow.js"></script>
    <script type="text/javascript" src="${path}/resources/alert/1.1/jquery-1.10.1.min.js"></script>
    <link rel="stylesheet" href="${path}/resources/alert/1.1/common.css">
</head>
<body style="width: 90%;height: auto;">
<div style="background-color: #f2f2f2;">
    <div class="weui_cell" style="margin-top: 15%;background-color: #ffffff;">
        <div class="weui_cell_hd">
            <lab el class="weui_label">联系人：</label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="name" id="username" value="${userReq.username}" placeholder="请输入联系人姓名" maxlength="11" />
        </div>
    </div>
    <div class="weui_cell" style="background-color: #ffffff;">
        <div class="weui_cell_hd">
            <lab el class="weui_label">手机号：</label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="tel" id="userphone" value="${userReq.userphone}" placeholder="请输入手机号" maxlength="11" />
        </div>
    </div>
    <button class="weui_btn weui_btn_primary" style="margin-top:20px;background-color: #f64347;" onclick="sub()">提交</button>
</div>
<script type="text/javascript" src="${path}/resources/css/userCenterBingPhonePage/js/jquery-3.1.0.min.js"></script>
<script type="text/javascript">

    function sub() {
        var username = $("#username").val();
        var userphone = $("#userphone").val();
        if(username == ""){
            webToast("请输入联系人姓名","middle",1000);//top middle bottom 控制显示位置
            return false;
        }
        if(userphone == ""){
            webToast("请输入联系人电话","middle",1000);//top middle bottom 控制显示位置
            return false;
        }
        if(userphone != ""){
            if(!isPoneAvailable(userphone)){
                webToast("请输入正确的手机号码","middle",1000);//top middle bottom 控制显示位置
                return false;
            }
        }
        $.post("${path}/wechatInUser/PostBingPhoneInUserNoCode",{username:username,userphone:userphone},function(info){
            webToast(info,"bottom",2000);//top middle bottom 控制显示位置
        });


    }

    function clickButton(obj) {
        var userphone = $("#userphone").val();
        if(!isPoneAvailable(userphone)){
            webToast("请输入正确的手机号码","middle",1000);//top middle bottom 控制显示位置
            return false;
        }
    }


    /**
     * 手机号码验证
     * @param input
     * @returns {boolean}
     */
    function isPoneAvailable(input) {
        var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (!reg.test(input)) {
            return false;
        } else {
            return true;
        }
    }

</script>
</body>
</html>