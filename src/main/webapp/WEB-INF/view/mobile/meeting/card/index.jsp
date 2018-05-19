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
    <title>西行护照领取</title>

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
            <lab el class="weui_label">姓名：</label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="name" id="name" value="${bean.name}" placeholder="请输入联系人姓名" maxlength="11" />
        </div>
    </div>
    <div class="weui_cell" style="background-color: #ffffff;">
        <div class="weui_cell_hd">
            <lab el class="weui_label">手机号：</label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="tel" id="phone" value="${bean.phone}" placeholder="请输入手机号" maxlength="11" />
        </div>
    </div>
    <div class="weui_cell" style="margin-top: 15%;background-color: #ffffff;">
        <div class="weui_cell_hd">
            <lab el class="weui_label">收货地址：</label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" type="name" id="address" value="${bean.address}" placeholder="请输入收货地址" maxlength="100" />
        </div>
    </div>
    <button class="weui_btn weui_btn_primary" style="margin-top:20px;background-color: #f64347;" onclick="sub()">提交</button>
</div>
<script type="text/javascript" src="${path}/resources/css/userCenterBingPhonePage/js/jquery-3.1.0.min.js"></script>
<script type="text/javascript">

    function sub() {
        var name = $("#name").val();
        var phone = $("#phone").val();
        var address = $("#address").val();
        var menuId = '${menuId}';
        if(name == ""){
            webToast("请输入你的姓名","middle",1000);//top middle bottom 控制显示位置
            return false;
        }
        if(phone == ""){
            webToast("请输入你的电话","middle",1000);//top middle bottom 控制显示位置
            return false;
        }
        if(address == ""){
            webToast("请输入你的领取护照地址","middle",1000);//top middle bottom 控制显示位置
            return false;
        }
        if(phone != ""){
            if(!isPoneAvailable(phone)){
                webToast("请输入正确的手机号码","middle",1000);//top middle bottom 控制显示位置
                return false;
            }
        }

        $.post("${path}/cardInUser/saveCard",{'menuId':menuId,'name':name,'phone':phone,'address':address},function(info){
            if(info == "0"){
                webToast("申请护照失败","bottom",2000);//top middle bottom 控制显示位置
            }
            if(info == "1"){
                webToast("申请护照成功","bottom",2000);//top middle bottom 控制显示位置
            }
            if(info == "-1"){
                webToast("参数校验失败","bottom",2000);//top middle bottom 控制显示位置
            }
            if(info == "-2"){
                webToast("登录超时，请刷新重试","bottom",2000);//top middle bottom 控制显示位置
            }
            if(info == "-3"){
                webToast("你已经申请成功了","bottom",2000);//top middle bottom 控制显示位置
            }else{
                webToast("服务器错误，请稍后重试","bottom",2000);//top middle bottom 控制显示位置
            }
        });


    }

    function clickButton(obj) {
        var phone = $("#phone").val();
        if(!isPoneAvailable(phone)){
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