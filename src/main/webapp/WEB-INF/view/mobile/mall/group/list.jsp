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

	<script src="${path}/resources/mobile/mall/topay/js/mui.min.js"></script>

	<!-- 微信UI -->
	<link rel="stylesheet" href="${path}/resources/weUI/weui.css">
	<link rel="stylesheet" href="${path}/resources/weUI/example.css">

	<script type="text/javascript" src="${path}/resources/css/mail/gruop/js/jquery.min.js"></script>
	<script type="text/javascript" src="${path}/resources/css/mail/gruop/js/amazeui.js"></script>
	<!-- 样式 -->
	<link rel="stylesheet" type="text/css" href="${path}/resources/css/mail/gruop/css/style.css">

	<!-- Alibaba图标样式 -->
	<link rel="stylesheet" type="text/css" href="${path}/resources/css/Alibaba/font/iconfont.css">
	<style>
		.signbutton1{
			 background-color: white;color:grey;height: 3rem;line-height:3rem ;
		 }
		.signbutton2{
			background-color: white;color:#71aecd;height: 3rem;line-height:3rem ;
		}

		.sbutton1{
			background-color: white;color:grey;height: 3rem;line-height:3rem ;
		}
		.sbutton2{
			background-color: white;color:#71aecd;height: 3rem;line-height:3rem ;
		}

		.groupbutton1{
			background-color: white;color:grey;height: 3rem;line-height:3rem ;
		}
		.groupbutton2{
			background-color: white;color:#71aecd;height: 3rem;line-height:3rem ;
		}
		.namecss{
			white-space: nowrap;overflow: hidden;text-overflow: ellipsis;font-size: 1.3rem;float: left;width: 75%;margin-top: -0.6rem;
		}
		.pricecss{
			color: #ffa200;font-size: 1.3rem;float: right;width: auto;margin-top: -0.6rem;
		}
	</style>

  </head>
	<body style="width: 100%;max-width: 100%;">
	<div id="header" style="font-size: 1.35rem;">最新活动</div>
	<!-----------公共版头----------->
	<div id="trip_list">
		<div id="item_name"></div>
		<ul id="moreBox">
			<c:forEach items="${list }" var="bean">
				<li class="view" onclick="location='${bean.url }'">
					<div class="pic" style="height: 18rem;">
						<img src="${bean.image }">
					</div>
					<div class="con">
						<div class="namecss">${bean.name }</div>
						<c:if test="${bean.type != 0 }">
							<div class="pricecss">￥${bean.price}</div>
						</c:if>
					</div>
					<div>
						<!-- 活动类型 -->
						<c:if test="${bean.type == 0 }">
							<c:if test="${bean.status == 0 }">
								<a class="button signbutton1" >活动已结束</a>
							</c:if>
							<c:if test="${bean.status == 1 }">
								<a class="button signbutton2" >马上报名</a>
							</c:if>
						</c:if>

						<!-- 单一商品类型 -->
						<c:if test="${bean.type == 1 }">
							<c:if test="${bean.status == 0 }">
								<a class="button sbutton1" >活动已结束</a>
							</c:if>
							<c:if test="${bean.status == 1 }">
								<a class="button sbutton2" >马上报名</a>
							</c:if>
						</c:if>

						<!-- 组团商品类型 -->
						<c:if test="${bean.type == 2 }">
							<c:if test="${bean.status == 0 }">
								<a class="button groupbutton1" >${bean.total}人组团</a>
							</c:if>
							<c:if test="${bean.status == 1 }">
								<a class="button groupbutton2">${bean.total}人组团</a>
							</c:if>
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>
		<div id="none" style="background-color: white;margin-bottom: 3rem;height: 8rem;">
			<img id="lordingImage" src="${path}/resources/img/icon/lording.gif" style="width: 2rem;height: 2rem;margin-top: -2rem;" />
			<span id="lordingComplate" style="width: 2rem;height: 2rem;margin-top: -2rem;display: none;" >数据加载完了</span>
		</div>
	</div>

	<!-- 底部导航 begin -->
	<div class="tab-bar tab-bottom" id="footer">
		<a class="tab-button " href="${path}/commodityGroup/listInUser?menuId=${menuId }&typese=-1&cpage=1"><i class="tab-button-icon icon icon-serve"></i><span class="tab-button-txt" style="color: #fea200;">最新活动</span></a>
		<a class="tab-button " href="${path}/wechatInUser/userCenter?menuId=${menuId }"><i class="tab-button-icon icon icon-myme"></i><span class="tab-button-txt">个人中心</span></a>
	</div>
	<!-- 导航切换 end -->

	<input type="hidden" id="size" value="${size }"/>
  	</body>

<script type="text/javascript">
	var size = $("#size").val();
	if(size < 10 ){
        $("#lordingImage").hide();
        $("#lordingComplate").show();
	}

    lording = 0;
    cpage = 2;
    totalPage = "${totalPage }";
    menuId = "${menuId }";
    function loaddingMore(){
		if(parseInt(cpage) > parseInt(totalPage)){
			//alert("数据已经加载完了···" + cpage +"-" + totalPage);//top middle bottom 控制显示位置
			$("#lordingImage").hide();
            $("#lordingComplate").show();

			lording = 1;
		}else{
			setTimeout(function(){
				//请求是否还有更多数据
				$.get("${path}/commodityGroup/listInUserJson",{menuId:menuId,cpage:cpage,typese:-1},function(info){
                    var obj = eval("(" + info + ")");
						for(var i = 0;i<obj.length;i++ ){
							var id = obj[i].id;
                            var name = obj[i].name;
							var price = obj[i].price;
							var status = obj[i].status;
							var type = obj[i].type;
							var button = obj[i].button;
							var url = obj[i].url;
							var image = obj[i].image;
							var total = obj[i].total;
							if(type == 0){
                                price = "<div class='pricecss'></div>";
							}
                            else if(type != 0){
                                price = "<div class='pricecss'>￥ " + price + "</div>";
                            }
							if(type == 0){
							    if(status == 0 ){
                                    button = "<a class='button signbutton1' >活动已结束</a>";
								}
                                if(status == 1 ){
                                    button = "<a class='button signbutton2' >马上报名</a>";
                                }
							}
                            if(type == 1){
                                if(status == 0 ){
                                    button = "<a class='button sbutton1' >活动已结束</a>";
                                }
                                if(status == 1 ){
                                    button = "<a class='button sbutton2' >马上报名</a>";
                                }
                            }
                            if(type == 2){
                                if(status == 0 ){
                                    button = "<a class='button groupbutton1' >" + total + "人组团</a>";
                                }
                                if(status == 1 ){
                                    button = "<a class='button groupbutton2' >" + total + "人组团</a>";
                                }
                            }
							$("#moreBox").append("<li class='view' onclick='toUrl("+id+","+type+")'><div class='pic'><img src=" + image + "></div><div class= 'con '><div class= 'namecss '>" + name + "</div> " + price +"</div><div>" + button + "</div> ");
						}
						cpage = parseInt(cpage) + 1;
						//alert("加载成功");//top middle bottom 控制显示位置
				});
			},200);
		}
	}

	function toUrl(id,type) {
        var menuId = '${menuId }';
        alert(id + " -- " + type );
        if(type == 0){
            $.get("${path}/apiInterface/interfaceGetActivityUrl",{id:id},function(info){
                if(info != null && info != ""){
                    window.location.href = info ;
				}else {
                    alert("no url");
				}
			});
        }
        if(type == 1){
            window.location.href =  "http://" + window.location.host + "/ssm/shop/activityCommodityNoUser?menuId=" + menuId + "&id=" + id + "&from=singlemessage&isappinstalled=0" ;
		}
        if(type == 2){
            window.location.href =  "http://" + window.location.host + "/ssm/shop/groupCommodityDetailsInUser?menuId=" + menuId + "&id="  + id + "&shareUserId=1&shareGroupId=1&from=singlemessage&isappinstalled=0" ;
        }
    }



    function getScrollTop(){
        var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
        if(document.body){
            bodyScrollTop = document.body.scrollTop;
        }
        if(document.documentElement){
            documentScrollTop = document.documentElement.scrollTop;
        }
        scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
        return scrollTop;
    }
    //文档的总高度
    function getScrollHeight(){
        var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
        if(document.body){
            bodyScrollHeight = document.body.scrollHeight;
        }
        if(document.documentElement){
            documentScrollHeight = document.documentElement.scrollHeight;
        }
        scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
        return scrollHeight;
    }
    function getWindowHeight(){
        var windowHeight = 0;
        if(document.compatMode == "CSS1Compat"){
            windowHeight = document.documentElement.clientHeight;
        }else{
            windowHeight = document.body.clientHeight;
        }
        return windowHeight;
    }
    window.onscroll = function(){
        if(getScrollTop() + getWindowHeight() == getScrollHeight()){
            //alert("已经到最底部了！!");
            loaddingMore();
        }
    };


</script>

</html>