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
	<title>${user.nickName }</title>
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
							<div class="title">领奖中心</div>
						</div>
					</div>
				</div>
			</div>
		<div class="aui-s-title b-line">
			<div class="aui-s-content">
				<h2>将二维码展示给核销人员进行核销领奖。</h2>
				<p style="margin-top: 0.75rem;">注意：绿色为可以兑奖，红色为已经领取了。</p>
			</div>
		</div>

		<div class="devider b-line"></div>
		<!-- 消息中心 begin -->
		<div class="aui-news-content">
			<div class="aui-news-list">
				<ul class="moreBox">
					<c:forEach items="${list }" var="bean">
					<li>
						<a href="javascript:;">
							<div class="aui-news-avatar">
								<span><img src="http://qr.liantu.com/api.php?text=${bean.fromatUpdateTime }"/></span>
								<c:if test="${bean.status eq '0'}"><span class="aui-badge aui-badge-r aui-badge-alert" style="background: green;"></span></c:if>
								<c:if test="${bean.status eq '1'}"><span class="aui-badge aui-badge-r aui-badge-alert" style="background: red;"></span></c:if>
							</div>
							<div class="aui-news-text">
								<div class="aui-text-hd">
									<h4>${bean.code}</h4>
									<em style="width: 10rem;"><fmt:formatDate value="${bean.createTime}" pattern="MM-dd HH:mm" ></fmt:formatDate></em>
								</div>
								<div class="aui-text-bd">
									<p>${bean.descM}</p>
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
                            $.get("${path}/wechatInUser/userPrizeJSON",{menuId:menuId,currentPage:currentPage},function(info){
                                webToast(info,"bottom",2000);//top middle bottom 控制显示位置
                                if(info != 0 || info != "0"){//有加载更多数据
                                    var obj = eval("("+info+")");
                                    for(var i = 0;i<obj.length;i++ ){
                                        var id = obj[i].id;
                                        var code = obj[i].code;
                                        var img = "http://qr.liantu.com/api.php?text=" + obj[i].fromatUpdateTime;
                                        var descM = obj[i].descM;
                                        var createTime = obj[i].fromatCreateTime;
                                        var states = obj[i].status;
                                        var color = "<span class='aui-badge aui-badge-r aui-badge-alert' style='background: red;'></span>";
                                        if(states == 0){
                                        	color = "<span class='aui-badge aui-badge-r aui-badge-alert' style='background: green;'></span>";
										}else{
                                            color = "<span class='aui-badge aui-badge-r aui-badge-alert' style='background: red;'></span>";
										}
                                        $(".moreBox").append("<li><a href='javascript:;'><div class='aui-news-avatar'><span><img src="+img+"/></span>"+ color +"</div><div class='aui-news-text'><div class='aui-text-hd'><h4>"+code+"</h4><em style='width: 10rem;'>"+createTime+"</em></div><div class='aui-text-bd'><p>"+descM+"</p></div></div></a></li>");
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