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
	<title>${commodity.commodityName }</title>
	<%@ include file="/WEB-INF/common_andy.jsp" %>
	<script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${path}/resources/azenui/css/ui.css">
	<link rel="stylesheet" href="${path}/resources/mobile/mall/css/style.css" />
	<script src="${path}/resources/mobile/mall/js/jquery.min.js"></script>
	<script src="${path}/resources/mobile/mall/js/jquery.carouFredSel.js"></script>
	<style type="text/css">
		img{ height: auto; width: auto\9; width:100%; }
		.alertText{
			z-index:999;
			width:100%; 
			height:2.5rem; 
			background:red;
			position: fixed;
			top: 0px;
			margin: auto;
			font-size:1.5rem;
			color:#ffffff;
			display:none;
		}
	</style>
	<script>
	$(function(){
		setFontSize = function() {
	        var w = $(window).width();
	        $("html").css({
	            fontSize: (w) / 360 * 10
	        });
	    }
	    setFontSize()
	    $(window).resize(function () {
	        setFontSize();
	    });
	    $('#carousel4').carouFredSel({
			circular: true,
			responsive:true,
			direction:'left',
			height:250,
			items: {
				visible: 1,
				minimum: 1,
				height:'100%'
			},
			pagination: "#carousel-pager4",//指定圆点坐标
			scroll:{
				items:1,
				fx:'directscroll',
				easing:'linear',
				duration:500,
				pauseOnHover:false
				},
			auto:{
				play:'auto',
				pauseDuration:2500
				}
		});
	})
</script>
</head>
<body>
	<div class="ydBody">
		<div class="hd-lunbo">
			<div class="m-carousel li-inline">
		        <ul id="carousel4">
		            <c:forEach items="${listImage }" var="img" begin="1">
						<li><img src="${img.url }" alt=""/></li>
					</c:forEach>
		        </ul>
		        <div class="pager" id="carousel-pager4"></div>
	        </div>
		</div>
		<div class="ydCon">
			<div class="title">${commodity.commodityName }</div>
			<div class="f-clear ydLine">
				<ul class="ydTime">
					<li>商品有效期</li>
					<li>${commodity.invalidDate }</li>
				</ul>
			</div>
			<div class="f-clear ydLine">
				<span class="ydNum">购买数量：</span>
				<div class="ydSL f-clear">
					<span onclick="reduceNumber()">-</span>
					<span><input type="text" id="Number" onchange="changeTotal()" value="1" readonly="readonly"/></span>
					<span onclick="addNumber()">+</span>
				</div>
			</div>
			<div class="f-clear ydLine">
				${commodity.commodityDetails }
			</div>
		</div>
		<div class="ydFooter">
	        <div class="footLeft">
	        	<span class="span1">合计:</span>
	        	<span class="span2">￥<span>
	        	<span class="span3" id="Total" >${commodity.commodityPrice }</span>
	       	</div>
	       	<c:if test="${commodity.commodityState == 1}">
	       		<c:if test="${commodity.commodityStock == 0}">
		        	<div class="footRight" id="subOrder" style="background-color:grey;">没有库存</div>
	       		</c:if>
	       		<c:if test="${commodity.commodityStock >= 1}">
		        	<div class="footRight" id="subOrder" onclick="subOrder()" >去支付</div>
	       		</c:if>
	       	</c:if>
	       	<c:if test="${commodity.commodityState == 0}">
		        <div class="footRight" id="subOrder" style="background-color:grey;">暂不支持购买</div>
	       	</c:if>
	    </div>
	</div>
  	<input type="hidden" id="Price" value="${commodity.commodityPrice }"/><br>
  	<input type="hidden" id="commodityStock" value="${commodity.commodityStock }"/><br>
  	<input type="hidden" id="id" value="${commodity.id }"/><br>
  	<input type="hidden" id="commodityState" value="${commodity.commodityState }"/><br>
  	<div align="center" id="div1" class="alertText">xxx</div>
</body>
	
	<script type="text/javascript">
  	/**
  	* 点击提交订单按钮
  	*/
  	function alertText(text){
  		$('#div1').text(text);   
  		$('#div1').fadeIn(500);        //淡入
  		setTimeout(function () {
  			$('#div1').fadeOut(500);       //淡出
        }, 1500);
  	}
  	function subOrder(){
  		
  		$("#subOrder").attr("disabled", true); 
  		setTimeout(function () {
  			$("#subOrder").attr("disabled", false); 
        }, 2000);
  		var Number = $("#Number").val();
  		var Price = $("#Price").val();
  		var Total = $("#Total").text();
  		var id = $("#id").val();
  		if(Number == 0){}else{
  	  		/**
	  		* 发起提交订单请求之前判断库存
	  		* 
	  		*/
  	  		$.get("${path}/order/getStockNoUser",{id:id},function(info){
  	  			if(info != -1 && info != -2){
	  	  			if(parseInt(Number) <= parseInt(info)){
	  	  				//发起订单请求
	  	  				toOrder(id,Price,Number,Total);
	  	  			}else{
	  	  				alertText("商品库存不足");
	  	  			}
  	  			}else if(info == -1){
  	  				alertText("发起请求参数错误，请刷新后重试！");
  	  			}else if(info == -2){
  	  				alertText("购买的商品已经被商家下架！");
  	  			}
  			});
  		}
  	}
  	
  	
  	function toOrder(id,Price,Number,Total){
  		/**
  		* 发起提交订单请求（Post）
  		* info 请求返回数据（非0 则为订单id，否则订单生成失败）
  		*/
  		$.post("${path}/order/placeAnOrderNoUser",{id:id,Price:Price,Number:Number,Total:Total},function(info){
  			if(info != -1 && info != -2 && info != -3 && info != -4 && info != 0){
  				window.location.href="${path}/order/payOrderInUser?order_id="+info+"&menuId=${menuId }";
  			}else if(info == -1){
  				alertText("发起请求参数错误，请刷新后重试！");
  			}else if(info == -2){
  				alertText("购买的商品已经被商家下架！");
  			}else if(info == -3){
  				alertText("生成订单编失败，请刷新后重试！");
  			}else if(info == -4){
  				alertText("商品库存不足！");
  			}else if(info == 0){
  				alertText("订单生成失败，请重试！");
  			}
		});
  		
  	}
  	
  	
  	/**
	* 计算数量
	*/
  	function changeTotal(){
  		var Number = $("#Number").val();
  		var Price = $("#Price").val();
  		var Total = parseFloat(Price) * Number + "";
  		var money = Math.round(Total*100)/100;
  		$("#Total").text(money);
  	}
  	/**
	* 减少数量
	*/
  	function reduceNumber(){
  		var Number = parseInt($("#Number").val());
  		if(Number <= 1){
  			Number = 1;
  		}
  		else{
  			Number = Number - 1;
  		}
  		$("#Number").val(Number);
  		changeTotal();
  	}
  	/**
	* 增加数量
	*/
	function addNumber(){
		var Number = parseInt($("#Number").val());
  		var commodityStock = parseInt($("#commodityStock").val());
		if(Number >= commodityStock){
  			Number = commodityStock;
  			
  		}else{
  			Number = Number + 1;
  		}
		
		$("#Number").val(Number);
		changeTotal();
  	}
  </script>

</html>