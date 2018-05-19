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
	<script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
	<script src="${path }/resources/layui/layui.js"></script>
	<script src="${path }/resources/layui/layuiUtil.js"></script>

	<script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
	<title>签到码</title>
</head>
<body>

<div align="center">
	<h4 style="margin-top: 2rem;color: green;font-size: 1.4rem;">${meeting.title }</h4>
	<div align="center">
		<h2 style="color: red;font-size: 1.4rem;">签到码：${meetingSign.signCode }</h2>
		<h2 style="color: black;font-size: 1rem;">也可以使用你的电话号码进行签到</h2>
		<img style="margin-top: 2rem;" src="${code}" width="80%"/>
		<h2 id="status0" style="color: grey;font-size: 1.4rem;">等待扫描</h2>
		<div  id="status1" style="display: none;">
			<h2 style="color: green;font-size: 1.4rem;">签到成功</h2>
			<img style="margin-top: 0rem;" src="${path}/resources/img/icon/yes.png" width=8%"/>
		</div>
		<div  id="status2" style="display: none;">
			<h2 style="color: green;font-size: 1.4rem;">已签到</h2>
			<img style="margin-top: 0rem;" src="${path}/resources/img/icon/yes.png" width=8%"/>
		</div>
		<div>
			<h2><a href="${path}/meeting/myMeetingNoUser?id=${meeting.id}" style="color: black;font-size: 1rem;text-decoration:none;">查看会议流程</a></h2>
		</div>
	</div>
</div>

<input id="meetingId" type="hidden" value="${meeting.id }"/>
<input id="meetingSignId" type="hidden" value="${meetingSign.id }"/>
<input id="signCode" type="hidden" value="${meetingSign.signCode }"/>
<input id="idStatus" type="hidden" value="${meetingSign.status }"/>
<script>
	var status = $("#idStatus").val();
    function layuiUtilMsg(data) {
        LayuiUtil.msg(data);
    }
    function getStatus() {
        var id = $("#meetingId").val();
        var signCode = $("#signCode").val();
        var url = "${path}/meeting/myCodeStatusNoUser";
        $.get(url,{'id':id ,'code':signCode },function(data){
            if(data == "1" || data == 1){
                status = 1;
                $("#status0").hide();
                $("#status1").show();
			}else {
//                layuiUtilMsg(data);
			}

        });
    }
    $(function() {
        if(status == "0" || status == 0){
            $("#status0").show();
            $("#status1").hide();
            $("#status2").hide();
            setInterval(function(){
                if(status == "0" || status == 0){
                    getStatus();
                }else if(status == "1" || status == 1){
                    layuiUtilMsg("签到成功!!!!");
                    status = 2;
                    setTimeout(function(){
                        var id = $("#meetingId").val();
                        window.location.href = "${path}/meeting/myMeetingNoUser?id=" +  id ;
                    }, 1000);
                }
            }, 500);
		}else if(status == "1" || status == 1){
            $("#status0").hide();
            $("#status1").hide();
            $("#status2").show();
            setTimeout(function(){
                var id = $("#meetingId").val();
                window.location.href = "${path}/meeting/myMeetingNoUser?id=" +  id ;
            }, 1000);
		}
    });
</script>
</body>
</html>