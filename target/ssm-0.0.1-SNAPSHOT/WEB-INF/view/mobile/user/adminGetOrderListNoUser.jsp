<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width">
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title></title>
	<%@ include file="/WEB-INF/common_andy.jsp" %>
	<script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
	<title>（赏花）订单表</title>
	<link rel="stylesheet" type="text/css" href="${path}/resources/azenui/css/ui.css">
	<!--=========引入Alert=========-->
	<script type="text/javascript" src="${path}/resources/alert/1.1/alertPopShow.js"></script>
	<script type="text/javascript" src="${path}/resources/alert/1.1/jquery-1.10.1.min.js"></script>
	<link rel="stylesheet" href="${path}/resources/alert/1.1/common.css">
</head>
<body>
<div class="aui-container">
	<div class="aui-page">
		<div class="aui-page">
			<div class="aui-t-header">
				<div class="header">
					<div class="header-background"></div>
					<div class="toolbar statusbar-padding">
						<div class="header-title">
							<div class="title">订单中心</div>
						</div>
					</div>
				</div>
			</div>
		<div class="aui-s-title b-line"></div>
		<div class="devider b-line"></div>
			<!-- 文字列表 begin -->
			<div class="aui-l-content">
				<div class="aui-menu-list">
					<ol class="moreBox">
						<c:forEach items="${list }" var="list">
							<li class="b-line"><h3>${list.userUserTime} ${list.userName} ${list.userPhone} ${list.numberDescM}</h3></li>
						</c:forEach>
					</ol>
				</div>
			</div>
			<!-- 文字列表 end -->
	</div>
</div>
	<script type="text/javascript" src="${path}/resources/js/lordMore/lord.js"></script>
	<script type="text/javascript">
		lording = 0;
        currentPage = 2;
        totalPage = "${totalPage }";
        descM = '00001';
        window.onscroll = function(){
            if(lording == 0 || lording == "0"){
                if(parseInt(currentPage) > parseInt(totalPage)){
                    webToast("数据已经加载完了···","bottom",2000);//top middle bottom 控制显示位置
                    lording = 1;
				}else{
                    if(getScrollTop() + getWindowHeight() == getScrollHeight()){
                        setTimeout(function(){
                            webToast("加载中···","bottom",2000);//top middle bottom 控制显示位置
                            //请求是否还有更多数据
                            $.get("${path}/order/adminGetOrderListNoUserJSON",{cpage:currentPage,descM:descM},function(info){
                                webToast(info,"bottom",2000);//top middle bottom 控制显示位置
                                if(info != 0 || info != "0"){//有加载更多数据
                                    var obj = eval("("+info+")");
                                    for(var i = 0;i<obj.length;i++ ){
                                        var userUserTime = obj[i].userUserTime;
                                        var userName = obj[i].userName;
                                        var userPhone = obj[i].userPhone;
                                        var numberDescM = obj[i].numberDescM;
                                        var order_amountMoney = obj[i].order_amountMoney;
                                        $(".moreBox").append("<li class='b-line'><h3>"+ userUserTime +" "+ userName +" "+ userPhone +" " + numberDescM +"</h3></li>");
                                    }
                                    currentPage = parseInt(currentPage) + 1;
                                    webToast("加载成功","bottom",1000);//top middle bottom 控制显示位置
                                }
                            });
                        },0);
                    }
				}
			}
        };
	</script>
</body>
</html>