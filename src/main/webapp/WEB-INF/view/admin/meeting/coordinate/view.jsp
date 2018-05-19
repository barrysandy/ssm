<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>查看详细</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>

    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.6&key=barrysandy"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>

    <script>
        function cancel() {
//            window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    </script>
</head>
<body>
<div class="g-layout">
    <div class="layout-center">
        <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
            <table class="m-table-forms inline">
                <tr>
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">点位详情</td>
                </tr>
                <tr>
                    <td class="table-header">点位名称</td>
                    <td colspan="7">&nbsp;${bean.name}</td>
                </tr>
                <tr>
                    <td class="table-header">点位详情</td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>
                <tr>
                    <td class="table-header">点位坐标</td>
                    <td colspan="7">&nbsp;${bean.coordinate}</td>
                </tr>
                <tr>
                    <td class="table-header">点位坐标</td>
                    <td colspan="7"><div id="container" style="width: 100%;height: 500px;"></div></td>
                    <script type="text/javascript">
                        x = '${bean.x}';
                        y = '${bean.y}';
                        //初始化地图对象，加载地图
                        map = new AMap.Map("container", {
                            resizeEnable: true,
                            center: [x, y],//地图中心点
                            zoom: 13 //地图显示的缩放级别
                        });
                        markers = [];
                        positions = [ [x, y] ];
                        for(var i = 0, marker; i < positions.length; i++) {
                            addMarker(positions[i],"0");
                        }

                        /**
                         * 移添加 Marker
                         */
                        function addMarker(position,type) {
                            new AMap.Marker({
                                map: map,
                                position: position,
                                icon: new AMap.Icon({
                                    size: new AMap.Size(40, 50),  //图标大小
                                    image: "http://webapi.amap.com/theme/v1.3/images/newpc/way_btn2.png",
                                    imageOffset: new AMap.Pixel(0, -60)
                                })
                            });
                        }
                    </script>
                </tr>
            </table>
        </div>


    </div>
    <div class="layout-foot f-b-t f-p-t-xs f-bg-light noprint" style="height:40px">
        <div class="f-right f-m-r-xs">
            <button type="button" class="u-btn" onClick="cancel()">关闭</button>
        </div>
    </div>
</div>
</body>
</html>
