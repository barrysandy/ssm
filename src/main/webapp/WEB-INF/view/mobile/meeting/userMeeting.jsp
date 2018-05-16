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
	<%@ include file="/WEB-INF/common_andy.jsp" %>
	<script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
	<style type="text/css">
		img {
			height: auto;
			width: auto \9;
			width: 100%;
		}
	</style>
	<title>会议流程</title>
</head>
<body>

<div align="center">
	<h4 style="margin-top: 2rem;color: grey;font-size: 1.4rem;">${meeting.title }</h4>
	<div>
		<img style="margin-top: 2rem;" src="${meeting.image}" width="95%"/>
		<div style="margin-top: 1rem;">
			${meeting.descM}
		</div>
	</div>
</div>
</body>
</html>