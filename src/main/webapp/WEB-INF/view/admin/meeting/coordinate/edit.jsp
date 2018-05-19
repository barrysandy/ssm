<!doctype html>
<html>
<head>
    <title>点位编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />

    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.6&key=barrysandy"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>

    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/meetingCoordinate/saveOrUpdate";
            $.post(url, $("#editForm").serializeArray(), function (result) {
                if (result.state) {
                    layer.open({
                        type: 1,
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >操作成功</div>',
                        btn: ['关闭'],
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            cancel();
                        }
                    })
                } else {
                    layer.open({
                        type: 1,
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >操作失败</div>',
                        btn: ['关闭'],
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            cancel();
                        }
                    });
                }
            },"json");
        }
        function cancel() {
            window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    </script>
</head>
<body>
<div class="g-layout">
    <div class="layout-center">
        <form id="editForm" action="${path}/meeting/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}"/>
                <input type="hidden" name="meetingId" value="${bean.meetingId}"/>
                <input type="hidden" name="coordinate" value="${bean.coordinate}"/>
                <input type="hidden" id="x" name="x" value="${bean.x}"/>
                <input type="hidden" id="y" name="y" value="${bean.y}"/>
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">点位编辑</td>
                    </tr>
                    <tr>
                        <td class="table-header">点位名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="name" value="${bean.name}" maxlength="120"></td>
                    </tr>
                    <tr>
                        <td class="table-header">地图标点<span style="color: red">*</span></td>
                        <td colspan="7">地图标点：
                            <div id="container" style="width: 100%;height: 500px;"></div>
                        </td>
                    </tr>
                    <script type="text/javascript">
                        var p_x = $("#x").val();
                        var p_y = $("#y").val();
                        //初始化地图对象，加载地图
                        map = new AMap.Map("container", {
                            resizeEnable: true,
                            center: [104.06, 30.66],//地图中心点
                            zoom: 13 //地图显示的缩放级别
                        });
                        markers = [];
                        positions = [ [$("#x").val(), $("#y").val()] ];
                        for(var i = 0, marker; i < positions.length; i++) {
                            addMarker(positions[i],"0");
                        }
                        map.on('click', function(e) {
                            var x = e.lnglat.getLng();
                            var y = e.lnglat.getLat();
                            p_x = x;
                            p_y = y;
                            $("#x").val(p_x)
                            $("#y").val(p_y)
                            var positionsTemp = [ [ x, y] ];
                            addMarker(positionsTemp[0],"1");
                        });

                        /**
                         * 移添加 Marker
                         */
                        function addMarker(position,type) {

                            if(type == "1"){
                                $(".amap-icon").remove();
                            }
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
                    <%--<tr>--%>
                        <%--<td class="table-header">点位经纬度<span style="color: red">*</span></td>--%>
                        <%--<td colspan="7">经度：<input type="text" class="u-input" name="x" value="${bean.x}" maxlength="120"> 纬度：<input type="text" class="u-input" name="y" value="${bean.y}" maxlength="120"></td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td class="table-header">点位描述<span style="color: red">*</span></td>
                        <td colspan="7"><textarea id="descM" name="descM" type="text/plain" class="text-word" style="width:99%;height:300px;">${bean.descM }</textarea>
                        <%--<td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}" maxlength="120"></td>--%>
                    </tr>
                </table>
            </div>
        </form>
    </div>
    <div class="layout-foot f-b-t f-p-t-xs f-bg-light noprint" style="height:40px">
        <div class="f-right f-m-r-xs">
            <button type="button" class="u-btn success" onClick="saveOrUpdate()">保存</button>
            <button type="button" class="u-btn" onClick="cancel()">关闭</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">


    $("input[type=file]").change(function(evenet){
        var inputFile = evenet.target;
        var id = inputFile.getAttribute("id");
        doUploadImage();
    })//选择文件后自动上传

    function objclick2(event){
        var img = event.target;
        var url =  img.getAttribute("id");
        var arrayImg = document.getElementById("inputImg");
        var con = confirm("确定要删除该图片吗？"); //在页面上弹出对话框
        if(con == true){
            var strValue = arrayImg.getAttribute("value");
            var newStrArr = "";
            strs = strValue.split(","); //字符分割
            for (i=0;i<strs.length ;i++ )
            {
                if(strs[i] == url){

                }else{
                    newStrArr = newStrArr + strs[i]+",";
                }
            }
            /** 替换掉 ',,'  为 ','  */
            newStrArr = newStrArr.replace(',,', ',');
            if(newStrArr == ","){newStrArr = "";}
            img.parentNode.removeChild(img);
            arrayImg.setAttribute("value", newStrArr);
        }
    }

</script>
<!--=========编辑器插件=========-->
<script type="text/javascript" charset="utf-8" src="${path}/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path}/resources/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${path}/resources/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('descM');

    function isFocus(e){
        alert(UE.getEditor('descM').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('descM').blur();
        UE.dom.domUtils.preventDefault(e)
    }
</script>
</html>
