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
		<div class="aui-t-header">
			<div class="header">
				<div class="header-background"></div>
				<div class="toolbar statusbar-padding">
					<div class="header-title">
						<div class="title">核销订单</div>
					</div>
				</div>
			</div>
		</div>
		<div class="aui-s-title b-line"></div>
		<div class="aui-s-title b-line">
			<div class="aui-s-content" style="margin-top: -3em;">
				<div align="center">
					<h2>订单编号：${order.orderNo }</h2>
					<br>
					<br>
				</div>
				<div>
					<span style="font-size: 1.2rem;">
						商品名称：${order.userUseTime} ${commodity.commodityName}<br>
						订单数量：${order.numberDescM}<br>
						订单金额：<span style="font-size: 1.5rem;color: red;">¥${order.orderAmountMoney}</span><br>
						订单联系人：${order.userName}<br>
						订单电话：${order.userPhone}<br>
						<c:if test="${order.typeState eq '0'}">
							<span style="color: greenyellow;">未付款</span><br>
						</c:if>
						<c:if test="${order.typeState eq '1'}">
							<span style="color: green;">未使用</span><br>
						</c:if>
						<c:if test="${order.typeState eq '2'}">
							<span style="color: red;">已消费</span><br>
						</c:if>
					</span>
				</div>
				<c:if test="${order.typeState eq '1'}">
					<br>
					<!-- button end-->
					<div class="aui-btn-item">
						<a href="javascript:;" class="btn btn-search" id="binding">核销订单</a>
					</div>
					<br>
					<br>
				</c:if>
			</div>

		</div>

		<input id="userId" type="hidden" value="${user.id }"/>
		<input id="menuId" type="hidden" value="${menuId }"/>
		<input id="useCode" type="hidden" value="${useCode }"/>
		<input id="orderId" type="hidden" value="${orderId }"/>
	</div>
</div>
<script type="text/javascript">
    var userId = $("#userId").val();
    var menuId = $("#menuId").val();
    var useCode = $("#useCode").val();
    var orderId = $("#orderId").val();
    $('#binding').on('click', function(){
        popTipShow.confirm('核销提示','确定要核销该订单了吗？',['确 定','取 消'],
            function(e){
                //callback 处理按钮事件
                var button = $(e.target).attr('class');
                if(button == 'ok'){
                    //按下确定按钮执行的操作
                    //todo ....
                    this.hide();
                    //1 添加成功 0 保存失败 -1用户验证失败 -2商家验证失败
                    $.get("${path}/orderWrite/ConfirmationFlowerInUserInUser",{orderId:orderId,userId:userId,menuId:menuId,useCode:useCode},function(info){
                        if(info == 1){//有加载更多数据
                            webToast("核销成功!","middle",2000);//top middle bottom 控制显示位置
                            $("#binding").text("核销成功");
                            $("#binding").css("background-color","red");
                        }else if(info == 0){//有加载更多数据
                            webToast("核销失败，你已核销定过了!","middle",2000);//top middle bottom 控制显示位置
                            $("#binding").text("已经核销");
                            $("#binding").css("background-color","red");
                        }
                    });
                }
                if(button == 'cancel') {
                    //按下取消按钮执行的操作
                    //todo ....
                    this.hide();
                    setTimeout(function() {
                        webToast("您“取消”了","middle", 2000);
                    }, 300);
                }
            }
        );
    });
</script>
</body>
</html>