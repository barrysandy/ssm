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

	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.6&key=barrysandy"></script>
	<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>

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
	<input type="hidden" id="positions" value="${positions}"/>
	<h4 style="margin-top: 2rem;color: grey;font-size: 1.4rem;">${meeting.title }</h4>
	<div>
		<img style="margin-top: 2rem;" src="${meeting.image}" width="95%"/>
		<div id="container" style="width: 100%;height: 200px;"></div>
		<script type="text/javascript">
			var positionsStr = $("#positions").val();
            var positions = eval(positionsStr);
            //初始化地图对象，加载地图
            map = new AMap.Map("container", {
                resizeEnable: true,
                center: [103.863959,30.689139],//地图中心点
                zoom: 13 //地图显示的缩放级别
            });
            AMap.plugin(['AMap.ToolBar','AMap.OverView'],
                function(){
                    map.addControl(new AMap.ToolBar());
                    map.addControl(new AMap.Scale());
                    map.addControl(new AMap.OverView({isOpen:true}));
                });
            markers = [];
            for(var i = 0, marker; i < positions.length; i++) {
                addMarker(positions[i],"0",i);
            }

            /**
             * 移添加 Marker
             */
            function addMarker(position,type,i) {
                var index = i;
                marker = new AMap.Marker({
                    map: map,
                    position: position,
                    icon: new AMap.Icon({
                        size: new AMap.Size(40, 50),  //图标大小
                        image: "http://webapi.amap.com/theme/v1.3/images/newpc/way_btn2.png",
                        imageOffset: new AMap.Pixel(0, -30)
                    })

                });
                marker.setTitle(index);
                marker.on('click', function(that) {
                    alert(that);
                    console.log(that);
                    var url = "${path}/meeting/getCoordinateNameNoUser";
                    /*
                    $.get(url,{'i':i},function(data){
                        alert(data);
                        marker.markOnAMAP({
                            name:'首开广场',
                            position:marker.getPosition()
                        })
                    });
					*/
                });

                markers.push(marker);

            }
		</script>
		<div style="margin-top: 1rem;">
			${meeting.descM}
		</div>
	</div>
</div>
</body>
</html>