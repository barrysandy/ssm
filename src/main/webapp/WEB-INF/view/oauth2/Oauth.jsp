<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>Loading....</title>
    
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width">
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<script type="text/javascript" src="<%=basePath%>/resources/js/jquery.min.js"></script>
	
	<style type="text/css">
		.spinner {
		  margin: 100px auto;
		  width: 64px;
		  height: 64px;
		  position: relative;
		}
		 
		.cube1, .cube2 {
		  background-color: #67CF22;
		  width: 64px;
		  height: 64px;
		  position: absolute;
		  top: 0;
		  left: 0;
		   
		  -webkit-animation: cubemove 1.8s infinite ease-in-out;
		  animation: cubemove 1.8s infinite ease-in-out;
		}
		 
		.cube2 {
		  -webkit-animation-delay: -0.9s;
		  animation-delay: -0.9s;
		}
		 
		@-webkit-keyframes cubemove {
		  25% { -webkit-transform: translateX(42px) rotate(-90deg) scale(0.5) }
		  50% { -webkit-transform: translateX(42px) translateY(42px) rotate(-180deg) }
		  75% { -webkit-transform: translateX(0px) translateY(42px) rotate(-270deg) scale(0.5) }
		  100% { -webkit-transform: rotate(-360deg) }
		}
		 
		@keyframes cubemove {
		  25% {
		    transform: translateX(42px) rotate(-90deg) scale(0.5);
		    -webkit-transform: translateX(42px) rotate(-90deg) scale(0.5);
		  } 50% {
		    transform: translateX(42px) translateY(42px) rotate(-179deg);
		    -webkit-transform: translateX(42px) translateY(42px) rotate(-179deg);
		  } 50.1% {
		    transform: translateX(42px) translateY(42px) rotate(-180deg);
		    -webkit-transform: translateX(42px) translateY(42px) rotate(-180deg);
		  } 75% {
		    transform: translateX(0px) translateY(42px) rotate(-270deg) scale(0.5);
		    -webkit-transform: translateX(0px) translateY(42px) rotate(-270deg) scale(0.5);
		  } 100% {
		    transform: rotate(-360deg);
		    -webkit-transform: rotate(-360deg);
		  }
		}	
	</style>	
  </head>
  <body>
  <input type="hidden" id="url" value="${url }">
  <div class="spinner">
	  <div class="cube1"></div>
	  <div class="cube2"></div>
  </div>
	<!-- 登录拦截 -->
	<script type="text/javascript">
		$(function(){
			var url = $("#url").val();
			//alert(url);
			window.location.href = url;
		});
	</script>
  </body>
</html>