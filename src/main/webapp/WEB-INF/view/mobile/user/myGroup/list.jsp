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
	<title>我的组团</title>
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
							<div class="title">我的组团</div>
						</div>
					</div>
				</div>
			</div>
		<div class="aui-s-title b-line"></div>

		<div class="devider b-line"></div>
		<!-- 消息中心 begin -->
		<div class="aui-news-content" style="background-color: #ffffff;" id="divalist">
			<c:forEach items="${list }" var="bean">
				<div class="aui-news-list">
					<ul class="moreBox">
							<li>
								<a href="javascript:;">
									<div class="aui-news-avatar" style="width: 40%;" onclick="toGroup('${bean.id }')">
										<span style="width: 100%;"><img src="${bean.image }"/></span>
									</div>
									<div class="aui-news-text">
										<div class="aui-text-hd" onclick="toGroup('${bean.id }')">
											<h4 style="margin-top: 0.1rem;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;font-size: 1.3rem;">${bean.name}</h4>
										</div>
										<div class="aui-text-bd"style="margin-top: 0.2rem;">
											<p style="color: #feb94c;font-size: 0.8em;">倒计时：<span class="isTime" onclick="setTime('${bean.time}',event)">--:--:--:--</span></p>
										</div>
										<div class="aui-text-bd"style="margin-top: 0.2rem;font-size: 1.2rem;" onclick="toGroup('${bean.id }')">
											<c:forEach items="${bean.user }" var="userBean" begin="0" end = "${bean.hiddenSet - 1 }">
												<img src="${userBean.headImgUrl }" style="width: 2.5rem;height: 2.5rem;border-radius:2.6rem; ">
											</c:forEach>
											<c:if test="${bean.totalPerson > bean.hiddenSet }">
												<input style="width: 2.5rem;height: 2.5rem;border-radius:2.6rem;background-color: grey;color: white;vertical-align:middle;" value=" +${bean.hiddenTotal }" disabled="disabled"/>
											</c:if>
										</div>
									</div>
								</a>
							</li>
					</ul>
				</div>
				<div align="center" style="margin-top: 0.7rem;margin-bottom: 0.4rem" onclick="toGroup('${bean.id }')">
					<c:if test="${bean.time > 0 }">
						<c:if test="${bean.status == 0 }">
							<span style="color: #439dc4;font-size: 1em;font-weight:bold;">还差${bean.total }人，分享给好友</span>
						</c:if>
						<c:if test="${bean.status == 1 }">
							<span style="color: #d0d0d0;font-size: 1em;font-weight:bold;">组团成功</span>
						</c:if>
					</c:if>
					<c:if test="${bean.time <= 0 }">
						<c:if test="${bean.status == 0 }">
							<span style="color: #d0d0d0;font-size: 1em;font-weight:bold;">组团失败，还差${bean.total }人</span>
						</c:if>
					</c:if>
				</div>
				<div class="devider b-line"></div>
			</c:forEach>
		</div>
		<!-- 消息中心 end -->

	</div>
</div>
	<script type="text/javascript" src="${path}/resources/js/lordMore/lord.js"></script>
	<script type="text/javascript">
        /** 初始化启动时间点击事件来设置倒计时 */
		$(".isTime").click();

        /** 设置倒计时 */
		function setTime(time,e) {
		    //如果time为正整数开始计时
			if(parseInt(time) > 0){
                countDown(time,e);
			}else{
                //如果time为负整数或0设置时间为00:00:00:00
                var span = e.target;
                span.innerHTML = "00:00:00:00" ;
			}

        }

        /** 带天数的倒计时 */
        function countDown(times,e){
            var timer = setInterval(function(){
                var day=0,
                    hour=0,
                    minute=0,
                    second=0;//时间默认值
                if(times > 0){
                    day = Math.floor(times / (60 * 60 * 24));
                    hour = Math.floor(times / (60 * 60)) - (day * 24);
                    minute = Math.floor(times / 60) - (day * 24 * 60) - (hour * 60);
                    second = Math.floor(times) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
                }
                if (day <= 9) day = '0' + day;
                if (hour <= 9) hour = '0' + hour;
                if (minute <= 9) minute = '0' + minute;
                if (second <= 9) second = '0' + second;
                //console.log(day+"天:"+hour+"小时："+minute+"分钟："+second+"秒");
                var span = e.target;
                span.innerHTML = day + ":" + hour + ":" + minute + ":" + second ;
//              $(".times").text(day + ":" + hour + ":" + minute + ":" + second);
                times --;
            },1000);
            if(times <= 0){
                clearInterval(timer);
            }
        }

        //进入详情
        function toGroup(id) {
            var menuId = "${menuId }";
            window.location.href = "${path}/shop/groupCommodityDetailsInUser?menuId=" + menuId + "&id=" + id + "&shareUserId=1&shareGroupId=1&from=singlemessage&isappinstalled=0";
        }


        //下滑到底部加载数据
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
                            $.get("${path}/myGroupInUser/listJSON",{currentPage:currentPage},function(info){
                                webToast(info,"bottom",2000);//top middle bottom 控制显示位置
                                if(info != 0 || info != "0"){//有加载更多数据
                                    var className = "isTime" + currentPage;
                                    var obj = eval("("+info+")");
                                    for(var i = 0;i<obj.length;i++ ){
                                        var id = obj[i].id;
                                        var groupId = obj[i].groupId;
                                        var orderId = obj[i].orderId;
                                        var name = obj[i].name;
                                        var imageTemp = obj[i].image;
                                        var time = obj[i].time;
                                        var total = obj[i].total;
                                        var status = obj[i].status;
                                        var jsonButton = obj[i].jsonButton;
                                        var user = obj[i].user;
                                        var listImage = "";
                                        var totalPerson = obj[i].totalPerson;
                                        var hiddenSet = obj[i].hiddenSet;
                                        var hiddenTotal = obj[i].hiddenTotal;
                                        for(var j = 0;j<user.length;j++){
                                            if(j < parseInt(hiddenSet)){
                                                var headImgUrl = user[j].headImgUrl;
                                                listImage = listImage + "<img src="+headImgUrl+" style='width: 2.5rem;height: 2.5rem;border-radius:2.6rem; '>";
											}
										}
                                    	if(parseInt(totalPerson) > parseInt(hiddenSet)){
                                            $("#divalist").append("<div class='aui-news-list'><ul class='moreBox'><li><a href='javascript:;'><div class='aui-news-avatar' style='width: 40%;' onclick='toGroup("+id+")'><span style='width: 100%;'><img src="+imageTemp+"></span></div><div class='aui-news-text'><div class='aui-text-hd' onclick='toGroup("+id+")'><h4 style='margin-top: 0.1rem;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;font-size: 1.3rem;'>"+name+"</h4></div><div class='aui-text-bd'style='margin-top: 0.2rem;'><p style='color: #feb94c;font-size: 0.8em;'>倒计时：<span class="+className+" onclick='setTime("+time+",event)'>--:--:--:--</span></p></div><div class='aui-text-bd'style='margin-top: 0.2rem;font-size: 1.2rem;' onclick='toGroup("+id+")'>"+listImage+"" + "<input style='width: 2.5rem;height: 2.5rem;border-radius:2.6rem;background-color: grey;color: white;vertical-align:middle;' value=' +" + hiddenTotal + " ' disabled='disabled'/></div> </div></a></li></ul></div><div align='center' style='margin-top: 0.7rem;margin-bottom: 0.4rem' onclick='toGroup("+id+")'>"+jsonButton+"</div><div class='devider b-line'></div>");
										}else{
                                            $("#divalist").append("<div class='aui-news-list'><ul class='moreBox'><li><a href='javascript:;'><div class='aui-news-avatar' style='width: 40%;' onclick='toGroup("+id+")'><span style='width: 100%;'><img src="+imageTemp+"></span></div><div class='aui-news-text'><div class='aui-text-hd' onclick='toGroup("+id+")'><h4 style='margin-top: 0.1rem;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;font-size: 1.3rem;'>"+name+"</h4></div><div class='aui-text-bd'style='margin-top: 0.2rem;'><p style='color: #feb94c;font-size: 0.8em;'>倒计时：<span class="+className+" onclick='setTime("+time+",event)'>--:--:--:--</span></p></div><div class='aui-text-bd'style='margin-top: 0.2rem;font-size: 1.2rem;' onclick='toGroup("+id+")'>"+listImage+"</div> </div></a></li></ul></div><div align='center' style='margin-top: 0.7rem;margin-bottom: 0.4rem' onclick='toGroup("+id+")'>"+jsonButton+"</div><div class='devider b-line'></div>");
										}
                                    }
                                    $("."+className).click();
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