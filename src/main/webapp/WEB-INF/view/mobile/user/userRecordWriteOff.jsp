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
						<div class="title">核销记录</div>
					</div>
				</div>
			</div>
		</div>
		<div class="aui-s-title b-line"></div>
		<div class="aui-s-title b-line"></div>
		<div class="aui-news-content">
			<div class="aui-news-list">
				<ul class="moreBox">
					<c:forEach items="${list }" var="list" varStatus="status">
					<li onclick="toOrder('${list.orderNo }')">
						<div class="aui-news-text">
							<div class="aui-text-bd" align="center">
									${ status.index + 1}
									<b>${list.orderNo }</b>
									${list.createTime }
							</div>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<c:if test="${size == 0 }">
			<div class="aui-s-title b-line">
				<div class="aui-s-content" align="center" style="margin-top: -3em;">
					<div class="aui-btn-item">
						<span>暂无核销记录</span>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${size != 0 }">
			<div class="aui-s-title b-line">
				<div class="aui-s-content" align="center" style="margin-top: -3em;">
					<div class="aui-btn-item">
						<span id="textMore" onclick="lordMore()">查看更多</span>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</div>
<input type="hidden" id="userId" value="${user.id }" />
</body>
<script type="text/javascript" src="${path}/resources/js/lordMore/lord.js"></script>
<script type="text/javascript">
    lording = 0;
    cpage = 2;
    totalPage = "${totalPage }";
    menuId = "${menuId }";
    userId = $("#userId").val();


    function toOrder(orderNo) {
        var menuId = "${menuId }";
        window.location.href = "${path}/order/orderDetailsByOrderNoInUser?orderNo="+orderNo+"&menuId="+menuId;
    }

	function lordMore() {
        if(lording == 0 || lording == "0"){
            if(parseInt(cpage) > parseInt(totalPage)){
                webToast("数据已经加载完了···","bottom",2000);//top middle bottom 控制显示位置
				$("#textMore").text("已经到底了···");
                lording = 1;
            }else{
				setTimeout(function(){
					webToast("加载中···","bottom",2000);//top middle bottom 控制显示位置
					//请求是否还有更多数据
					$.get("${path}/recordWriteInUser/recordWriteOffByJSON",{menuId:menuId,cpage:cpage,userId:userId},function(info){
						webToast(info,"bottom",2000);//top middle bottom 控制显示位置
						if(info != 0 || info != "0"){//有加载更多数据
							var obj = eval("("+info+")");
							for(var i = 0;i<obj.length;i++ ){
							    var id = obj[i].id;
								var no = ( ( parseInt(cpage) - parseInt(1) ) * parseInt(15) ) + ( parseInt(i) + parseInt(1) );
								var orderNo = obj[i].orderNo;
								var createTime = obj[i].createTime;
								$(".moreBox").append("<li onclick='toOrder("+id+")'><div class='aui-news-text'><div class='aui-text-bd' align='center'>"+no+" <b>"+orderNo+"</b> "+createTime+"</div></div></li>");
							}
							cpage = parseInt(cpage) + 1;
							webToast("加载成功","bottom",1000);//top middle bottom 控制显示位置
						}
					});
				},0);
            }
        }
    }
</script>
</html>