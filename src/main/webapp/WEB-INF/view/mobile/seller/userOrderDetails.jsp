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
						<div class="title">我的订单</div>
					</div>
				</div>
			</div>
		</div>
		<div class="aui-s-title b-line"></div>
		<div class="aui-s-title b-line">
			<div class="aui-s-content" style="margin-top: -3em;">
				<div align="center">
					<h2>订单编号：${order.order_no }</h2>
					<c:if test="${order.order_typeState eq '0'}">
						<br>
						<br>
					</c:if>
					<c:if test="${order.order_typeState eq '1'}">
						<br>
						<span style="color: red;font-size: 1.6rem;">核销码</span>
						<br>
						<br>
						<img src="http://qr.liantu.com/api.php?text=${code}" width="45%"/>
						<br>
						<br>
					</c:if>
				</div>
				<div>
					<span style="font-size: 1.2rem;">
						商品名称：${order.userUserTime} ${commodity.commodityName}<br>
						订单数量：${order.numberDescM}<br>
						订单金额：<span style="font-size: 1.5rem;color: red;">¥${order.order_amountMoney}</span><br>
						订单联系人：${order.userName}<br>
						订单电话：${order.userPhone}<br>
					</span>
				</div>
				<c:if test="${order.order_typeState eq '0'}">
					<br>
					<div class="aui-btn-item">
						<a href="${path}/order/payOrderInUser?order_id=${order_id }&menuId=${menuId }" class="btn btn-search" style="background-color: red;">立即支付</a>
					</div>
					<br>
					<br>
				</c:if>
			</div>

		</div>

		<input id="userId" type="hidden" value="${user.id }"/>
		<input id="id" type="hidden" value="${order.order_id }"/>
	</div>
</div>
</body>
</html>