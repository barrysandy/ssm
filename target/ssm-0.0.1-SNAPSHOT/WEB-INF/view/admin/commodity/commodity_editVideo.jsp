<!doctype html>
<html>
<head>
    <title>商品视频上传编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdateVideo() {
            var url = "${path}/commodity/saveOrUpdateVideo";
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
        <form id="editForm" action="${path}/commodity/saveOrUpdateVideo" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuId}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">设置${bean.commodityName}的视频文件</td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >商品视频</span><span style="color: red">*</span></td>
                        <td colspan="7">
                                <div id="showIma">
                                    <video src="${bean.videoResource.url}" controls="controls" height="220px;" id="video">
                                        您的浏览器不支持 video 标签。
                                    </video>
                                </div>
                            <input id="file" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Video.js">
                                updateImg = "";//上传文件的返回url
                                material_id = "";//上传文件的返回material_id
                            </script>
                            <input type="text" id="inputImg" class="u-input" name="videoMaterialId" value="${bean.videoMaterialId}">
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
    <div class="layout-foot f-b-t f-p-t-xs f-bg-light noprint" style="height:40px">
        <div class="f-right f-m-r-xs">
            <button type="button" class="u-btn success" onClick="saveOrUpdateVideo()">保存</button>
            <button type="button" class="u-btn" onClick="cancel()">关闭</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">

    $("input[type=file]").change(function(evenet){
        var inputFile = evenet.target;
        var id = inputFile.getAttribute("id");
        doUploadVideo();
    })//选择文件后自动上传

</script>
</html>
