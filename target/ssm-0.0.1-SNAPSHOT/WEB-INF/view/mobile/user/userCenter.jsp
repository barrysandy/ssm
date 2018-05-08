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
	<link rel="stylesheet" type="text/css" href="${path}/resources/azenui/css/ui.css">

</head>
<body>

	<div class="aui-container">
		<div class="aui-page">
			<div class="aui-t-header">
				<div class="header">
					<div class="header-background"></div>
					<div class="toolbar statusbar-padding">
						<div class="header-title">
							<div class="title">个人中心</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${user.subscribe == 1 }">
				<div class="aui-s-title b-line">
					<div class="aui-s-content" align="center">
						<img src="${user.headImgUrl }" style="width:6rem; display:block; border:none;border-radius:6rem">
						<div style="margin-top:1rem;">
								${user.nickName }
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${user.subscribe == 0 }">
				<div class="aui-s-title b-line">
					<div class="aui-s-content" align="center">
					</div>
				</div>
			</c:if>
			<div class="devider b-line"></div>
			<!-- 带图标、说明、跳转的列表项 最新版本 html begin -->
			<div class="aui-list-cells">
				<a href="${path}/wechatInUser/userOrder?menuId=${menuId }&currentPage=1&typeState=-1" class="aui-list-cell">
					<div class="aui-list-cell-fl"><img src="${path}/resources/img/icon/userCenter/order.png"></div>
					<div class="aui-list-cell-cn" style="margin-left:1rem;">订单中心</div>
				</a>
				<a href="${path}/myGroupInUser/list?menuId=${menuId }&cpage=1" class="aui-list-cell">
					<div class="aui-list-cell-fl"><img src="${path}/resources/img/icon/userCenter/group.png"></div>
					<div class="aui-list-cell-cn" style="margin-left:1rem;">我的拼团</div>
				</a>
				<a href="${path}/wechatInUser/userPrize?menuId=${menuId }&currentPage=1" class="aui-list-cell">
					<div class="aui-list-cell-fl"><img src="${path}/resources/img/icon/userCenter/prize.png"></div>
					<div class="aui-list-cell-cn" style="margin-left:1rem;">活动中奖</div>
				</a>
				<a href="${path}/recordWriteInUser/recordWriteOff?cpage=1&userId=${user.id }&menuId=${menuId }" class="aui-list-cell">
					<div class="aui-list-cell-fl"><img src="${path}/resources/img/icon/userCenter/use.png"></div>
					<div class="aui-list-cell-cn" style="margin-left:1rem;">核销记录</div>
				</a>
				<c:if test="${write > 0 }">
					<a href="${path}/recordWriteInUser/recordWriteOffByMe?cpage=1&orderUserId=${user.id }&menuId=${menuId }" class="aui-list-cell">
						<div class="aui-list-cell-fl"><img src="${path}/resources/img/icon/userCenter/use.png"></div>
						<div class="aui-list-cell-cn" style="margin-left:1rem;">商家核销记录</div>
					</a>
				</c:if>
				<a href="${path}/wechatInUser/bingPhoneInUser?menuId=${menuId }" class="aui-list-cell">
					<div class="aui-list-cell-fl"><img src="${path}/resources/img/icon/userCenter/contact.png"></div>
					<div class="aui-list-cell-cn" style="margin-left:1rem;">联系方式</div>
				</a>

			</div>
			<!-- 带图标、说明、跳转的列表项 end -->
	
		</div>
	</div>


	<!-- 底部导航 begin -->
	<div class="tab-bar tab-bottom" id="footer" style="background-color: white;">
		<a class="tab-button " href="${path}/commodityGroup/listInUser?menuId=${menuId }&typese=-1&cpage=1"><i class="tab-button-icon icon icon-serve"></i><span class="tab-button-txt">最新活动</span></a>
		<a class="tab-button " href="${path}/wechatInUser/userCenter?menuId=${menuId }"><i class="tab-button-icon icon icon-myme"></i><span class="tab-button-txt" style="color: #fea200;">个人中心</span></a>
	</div>
	<!-- 导航切换 end -->

</body>
</html>