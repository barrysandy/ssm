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
	<title>订单中心</title>
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
					<div class="aui-tab t-line">
						<ul class="aui-b-border">
							<c:if test="${typeState == -1}">
								<li class="aui-hit r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=-1'">全部订单</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=0'">未付款</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=1'">已付款</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=2'">已完成</li>
							</c:if>
							<c:if test="${typeState == 0}">
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=-1'">全部订单</li>
								<li class="aui-hit r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=0'">未付款</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=1'">已付款</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=2'">已完成</li>
							</c:if>
							<c:if test="${typeState == 1}">
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=-1'">全部订单</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=0'">未付款</li>
								<li class="aui-hit r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=1'">已付款</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=2'">已完成</li>
							</c:if>
							<c:if test="${typeState >= 2}">
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=-1'">全部订单</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=0'">未付款</li>
								<li class="r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=1'">已付款</li>
								<li class="aui-hit r-line" onclick="location='${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=2'">已完成</li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>

		<div class="devider b-line"></div>
		<!-- 消息中心 begin -->
		<div class="aui-news-content">
			<div class="aui-news-list" style="margin-top: 3rem;">
				<ul class="moreBox">
					<c:forEach items="${list }" var="bean">
						<li onclick="toOrder('${bean.id }','${bean.typeState }')">
							<a href="javascript:;">
								<div class="aui-news-avatar" style="width: 25%;">
									<img src="${bean.image }" width="80%;"/>
								</div>
								<div class="aui-news-text" style="width: 75%;">
									<div class="aui-text-hd" style="width: 100%;">
										<h4 style="margin-left: 0.5rem;margin-top: 0.25rem;width: 70%;">${bean.orderNo}</h4>
										<em style="width: 10rem;margin-top: 1rem;width: 30%;">
											<c:if test="${bean.typeState eq '0'}"><span style="color: red;">未付款</span></c:if>
											<c:if test="${bean.typeState eq '1'}"><span style="color: green;">已付款</span></c:if>
											<c:if test="${bean.typeState eq '2'}"><span style="color: blue;">已完成</span></c:if>
											<c:if test="${bean.typeState eq '3'}"><span style="color: rebeccapurple;">退款中</span></c:if>
											<c:if test="${bean.typeState eq '4'}"><span style="color: lightseagreen;">已退款</span></c:if>
										</em>
									</div>
									<div class="aui-text-bd" style="width: 100%;">
										<p style="margin-left: 0.5rem;">${bean.orderName} ${bean.numberDescM} ${bean.userUseTime}</p>
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

	<!-- 底部导航 begin -->
	<div class="tab-bar tab-bottom" id="footer" style="background-color: white;">
		<a class="tab-button " href="${path}/commodityGroup/listInUser?menuId=${menuId }&typese=-1&cpage=1"><i class="tab-button-icon icon icon-serve"></i><span class="tab-button-txt">最新活动</span></a>
		<a class="tab-button " href="${path}/wechatInUser/userCenter?menuId=${menuId }"><i class="tab-button-icon icon icon-myme"></i><span class="tab-button-txt" style="color: #fea200;">个人中心</span></a>
	</div>
	<!-- 导航切换 end -->
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
        typeState = "${typeState }";
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
                            $.get("${path}/wechatInUser/userOrderJSON",{menuId:menuId,currentPage:currentPage,typeState:typeState},function(info){
                                webToast(info,"bottom",2000);//top middle bottom 控制显示位置
                                if(info != 0 || info != "0"){//有加载更多数据
                                    var obj = eval("("+info+")");
                                    for(var i = 0;i<obj.length;i++ ){
                                        var id = obj[i].id;
                                        var orderNo = obj[i].orderNo;
                                        var image = obj[i].image;
                                        var userUseTime = obj[i].userUseTime;
                                        var numberDescM = obj[i].numberDescM;
                                        var descM = obj[i].orderName + " " + numberDescM + " " + userUseTime;
                                        var states = obj[i].typeState;
                                        if( states == 0 || states == '0'){
                                        	var type = "<span style='color: red;'>未付款</span>";
										}else if( states == 1 || states == '1'){
                                            var type = "<span style='color: green;'>已付款</span>";
                                        }else if( states == 2 || states == '2'){
                                            var type = "<span style='color: blue;'>已完成</span>";
                                        }else if( states == 3 || states == '3'){
                                            var type = "<span style='color: rebeccapurple;'>退款中</span>";
                                        }else if( states == 4 || states == '4'){
                                            var type = "<span style='color: lightseagreen;'>已退款</span>";
                                        }
                                        $(".moreBox").append("<li onclick='toPayOrder("+id+","+states+")'><a href='javascript:;'><div class='aui-news-avatar' style='width: 25%;'><img src="+image+" width='80%;'/></div><div class='aui-news-text' style='width: 75%;'><div class='aui-text-hd' style='width: 100%;'><h4 style='margin-left: 0.5rem;margin-top: 0.25rem;width: 70%;'>"+orderNo+"</h4><em style='width: 10rem;margin-top: 1rem;width: 30%;'>"+type+"</em></div><div class='aui-text-bd' style='width: 100%;'><p style='margin-left: 0.5rem;'>"+descM+"</p></div></div></a></li>");
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