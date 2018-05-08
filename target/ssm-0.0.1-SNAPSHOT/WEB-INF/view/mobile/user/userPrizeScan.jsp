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
						<div class="title">核销中奖码</div>
					</div>
				</div>
			</div>
		</div>
		<div class="aui-s-title b-line"></div>
		<c:if test="${authority eq '0' }">
			<div class="aui-s-title b-line">
				<div class="aui-s-content" align="center" style="margin-top: -3em;">
					<br>
					<br>
					<br>
					<h2>${user.nickName },你不是核销人员。</h2>
					<br>
					<br>
					<br>
				</div>
			</div>
		</c:if>
		<c:if test="${authority eq '1' }">
			<div class="aui-s-title b-line">
				<div class="aui-s-content" align="center" style="margin-top: -3em;">
					<br>
					<br>
					<br>
					<h2><b style="color: green;font-size: 2rem;">核销码：${winning.code }</b></h2>
					<br>
					<br>
					<br>
					<h2>${winning.descM }</h2>
					<br>
					<br>
					<br>
					<!-- button end-->
					<c:if test="${state == 0 }">
						<div class="aui-btn-item">
							<a href="javascript:;" class="btn btn-search" id="binding">确认核销</a>
						</div>
					</c:if>
					<c:if test="${state == 1 }">
						<div class="aui-btn-item">
							<a href="javascript:;" class="btn btn-search" style="background-color:red;">已核销</a>
						</div>
					</c:if>

				</div>
			</div>
		</c:if>
		
		<div class="aui-s-title b-line">
			<div class="aui-s-content" align="center" style="margin-top: -3em;">
				<div class="aui-btn-item">
					<a href="user/recordWriteOff?cpage=1&user_id=${user.id }" >查看核销记录</a>
				</div>
			</div>
		</div>
		
		<input id="code" type="hidden" value="${winning.code }"/>
	</div>
</div>


	<script type="text/javascript">
			var code = $("#code").val();
			$('#binding').on('click', function(){
				popTipShow.confirm('核销提示','确定要核销该中奖奖品吗？',['确 定','取 消'],
					function(e){
					  //callback 处理按钮事件
					  var button = $(e.target).attr('class');
					  if(button == 'ok'){
						//按下确定按钮执行的操作
						//todo ....				
						this.hide();
						//1 核销成功 0 核销失败
						$.get("${path}/wechatInUser/confirmScaninPrizeCodeinterface",{code:code},function(info){
							if(info == 1){//有加载更多数据
								webToast("核销成功!","middle",2000);//top middle bottom 控制显示位置
								$("#binding").text("已核销");
								$("#binding").css("background-color","red");
							}else if(info == 0){//有加载更多数据
								webToast("操作失败，你已核销过了!","middle",2000);//top middle bottom 控制显示位置
								$("#binding").text("已核销");
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