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
	<title>我的订单</title>
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
		<div class="aui-s-title b-line">
			<div class="aui-s-content">
				<p style="margin-top: 0.75rem;">提示：<span style="color: #008000">绿色</span>已付款，<span style="color: #ff0000">红色</span>为未付款，<span style="color: #0000ff">蓝色</span>为已完成。</p>
			</div>
		</div>

		<div class="devider b-line"></div>
		<!-- 消息中心 begin -->
		<div class="aui-news-content">
			<div class="aui-news-list">
				<ul class="moreBox">
					<c:forEach items="${list }" var="bean">
						<li onclick="toOrder('${bean.id }','${bean.typeState }')">
							<a href="javascript:;">
								<div class="aui-news-avatar">
									<span><img src="${bean.image }"/></span>
									<c:if test="${bean.typeState eq '0'}"><span class="aui-badge aui-badge-r aui-badge-alert" style="background: red;"></span></c:if>
									<c:if test="${bean.typeState eq '1'}"><span class="aui-badge aui-badge-r aui-badge-alert" style="background: green;"></span></c:if>
									<c:if test="${bean.typeState eq '2'}"><span class="aui-badge aui-badge-r aui-badge-alert" style="background: blue;"></span></c:if>
								</div>
								<div class="aui-news-text">
									<div class="aui-text-hd">
										<h4>${bean.orderNo}</h4>
										<em style="width: 10rem;">${bean.createTime}</em>
									</div>
									<div class="aui-text-bd">
										<p>${bean.orderName} ${bean.numberDescM} ${bean.userUseTime}</p>
									</div>
								</div>
							</a>
						</li>
					</c:forEach>
				</ul>

			</div>
		</div>
		<!-- 消息中心 end -->

	</div>
</div>
	<script type="text/javascript" src="${path}/resources/js/lordMore/lord.js"></script>
	<script type="text/javascript">

        function toOrder(order_id,typeState) {
            var menuId = "${menuId }";
            window.location.href = "${path}/order/orderDetailsInUser?order_id="+order_id+"&menuId="+menuId;
        }
		lording = 0;
        currentPage = 2;
        totalPage = "${totalPage }";
        menuId = "${menuId }";
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
                            $.get("${path}/wechatInUser/userOrderJSON",{menuId:menuId,currentPage:currentPage},function(info){
                                webToast(info,"bottom",2000);//top middle bottom 控制显示位置
                                if(info != 0 || info != "0"){//有加载更多数据
                                    var obj = eval("("+info+")");
                                    for(var i = 0;i<obj.length;i++ ){
                                        var id = obj[i].id;
                                        var code = obj[i].orderNo;
                                        var image = obj[i].image;
                                        var userUseTime = obj[i].userUseTime;
                                        var numberDescM = obj[i].numberDescM;
                                        var descM = obj[i].orderName + " " + numberDescM + " " + userUseTime;
                                        var createTime = obj[i].createTime;
                                        var states = obj[i].typeState;
                                        var color = "<span class='aui-badge aui-badge-r aui-badge-alert' style='background: red;'></span>";
                                        if(states == 0){
                                        	color = "<span class='aui-badge aui-badge-r aui-badge-alert' style='background: green;'></span>";
										}else if(states == 1){
                                            color = "<span class='aui-badge aui-badge-r aui-badge-alert' style='background: red;'></span>";
										}else if(states == 2){
                                            color = "<span class='aui-badge aui-badge-r aui-badge-alert' style='background: blue;'></span>";
                                        }
                                        $(".moreBox").append("<li onclick='toPayOrder("+id+","+states+")'><a href='javascript:;'><div class='aui-news-avatar'><span><img src="+image+"/></span>"+ color +"</div><div class='aui-news-text'><div class='aui-text-hd'><h4>"+code+"</h4><em style='width: 10rem;'>"+createTime+"</em></div><div class='aui-text-bd'><p>"+descM+"</p></div></div></a></li>");
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