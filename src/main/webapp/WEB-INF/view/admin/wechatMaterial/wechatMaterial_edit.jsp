<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>微信素材新增</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" src="http://zsxx.zhishengxixing.com:80/ssm_file//static/js/jquery-1.8.3.min.js"></script>
    <style >
        .upimageClass{
            width: 120px;
            height: auto;
        }
    </style>
    <script>
        function saveOrUpdate() {
            var url = "${path}/wechatMaterial/saveOrUpdate.htm";
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
                        area: ['300px', '200px'],
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
        <form id="editForm" action="${path}/wechatMaterial/saveOrUpdate.html" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">微信素材</td>
                    </tr>
                    <tr>
                        <td class="table-header">素材描述<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}" placeholder="请填写素材的描述"></td>
                    </tr>
                    <tr>
                        <td class="table-header">素材类型<span style="color: red">*</span></td>
                        <td colspan="7">
                            <!-- 素材类型 图片（image）语音（voice）视频（video）缩略图（thumb） -->
                            <select id="typese" name="typese" class="u-input">
                                <option value="image" <c:if test="${bean.typese == 'image'}">selected="selected"</c:if> >图片素材（2M，支持bmp/png/jpeg/jpg/gif格式）</option>
                                <option value="voice" <c:if test="${bean.typese == 'voice'}">selected="selected"</c:if> >语音素材（2M，播放长度不超过60s，支持mp3/wma/wav/amr格式）</option>
                                <option value="video" <c:if test="${bean.typese == 'video'}">selected="selected"</c:if> >视频素材（10MB，支持MP4格式）</option>
                                <option value="thumb" <c:if test="${bean.typese == 'thumb'}">selected="selected"</c:if> >缩略图素材（64KB，支持JPG格式）</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="imageTr" style="display: none;">
                        <td class="table-header">图片素材<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div id="showIma">
                                <img src="${image}" height="120px;" onclick="objclick2(event)">
                            </div>
                            <input id="file" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Image.js">
                                updateImg = "";//上传文件的返回url
                                material_id = "";//上传文件的返回material_id
                            </script>
                        </td>
                    </tr>

                    <tr class="voiceTr" style="display: none;">
                        <td class="table-header">语音素材<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div id="showVoice">
                                <audio id="voice"  preload="preload" controls="controls" src="${voice}">你的浏览器不支持语音插件.</audio>
                            </div>
                            <input id="fileVoice" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Voice.js">
                                updateVoice = "";//上传文件的返回url
                                material_id = "";//上传文件的返回material_id
                            </script>
                        </td>
                    </tr>

                    <tr class="videoTr" style="display: none;">
                        <td class="table-header">视频素材<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div id="showVideo">
                                <video width="320" height="240" controls="controls">
                                    <source src="movie.ogg" type="video/ogg">
                                    <source id="video" src="${video}" type="video/mp4">你的浏览器不支持视频插件.
                                </video><!-- 视频显示框 -->
                            </div>
                            <input id="fileVideo" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Video.js">
                                updateVideo = "";//上传文件的返回url
                            </script>
                        </td>
                    </tr>

                    <tr class="thumbTr" style="display: none;">
                        <td class="table-header">缩略图素材<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div id="showThumb">
                                <img id="thumb" src="${thumb}" height="120px;" onclick="objclick2(event)">
                            </div>
                            <input id="fileThumb" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Thumb.js">
                                updateThumb = "";//上传文件的返回url
                            </script>
                        </td>
                    </tr>

                    <tr>
                        <td class="table-header">系统服务器素材material_id（上传后自动生成<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" id="inputImg"  name="url" value="${bean.url}" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">微信服务器素材material_id（保存后自动生成）<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="material_id" value="${bean.material_id}" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">素材状态<span style="color: red">*</span></td>
                        <td colspan="7">
                            <!-- 素材类型 图片（image）语音（voice）视频（video）缩略图（thumb） -->
                            <select name="status" class="u-input">
                                <option value="1" <c:if test="${bean.status == '1'}">selected="selected"</c:if> >启用</option>
                                <option value="-1" <c:if test="${bean.status == '-1'}">selected="selected"</c:if> >不启用</option>
                            </select>
                        </td>
                    </tr>
                    <tr style="display: none;">
                        <td class="table-header">系统菜单ID<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="menuId" value="${menuId}"></td>
                    </tr>
                    <tr style="display: none;">
                        <td class="table-header">parentMenuId绑定公众号的Menu_ID，确定该关键字的所属公众号<span style="color: red">*</span>
                        </td>
                        <td colspan="7"><input type="text" class="u-input" name="parentMenuId" value="${parentMenuId}"></td>
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
<script type="text/javascript">

    /**
     * 初始化隐藏和显示选择的类型
     */
    $(function(){
        heidmaterial();
        if($("#typese").val() == "image" ){
            $(".imageTr").show();
            $("#file").attr("disabled", false);
        }else if($("#typese").val() == "voice" ){
            $(".voiceTr").show();
            $("#fileVoice").attr("disabled", false);
        }else if($("#typese").val() == "video" ){
            $(".videoTr").show();
            $("#fileVideo").attr("disabled", false);
        }
        else if($("#typese").val() == "thumb" ){
            $(".thumbTr").show();
            $("#fileThumb").attr("disabled", false);
        }else{
            $(".imageTr").show();
            $("#file").attr("disabled", false);
        }
    });

    /**
     * select选择的类型
     */
    $("select#typese").change(function(){
        heidmaterial();
        if($("#typese").val() == "image" ){
            $(".imageTr").show();
            $("#file").attr("disabled", false);
        }else if($("#typese").val() == "voice" ){
            $(".voiceTr").show();
            $("#fileVoice").attr("disabled", false);
        }else if($("#typese").val() == "video" ){
            $(".videoTr").show();
            $("#fileVideo").attr("disabled", false);
        }
        else if($("#typese").val() == "thumb" ){
            $(".thumbTr").show();
            $("#fileThumb").attr("disabled", false);
        }else{
            $(".imageTr").show();
            $("#file").attr("disabled", false);
        }
    });

    /**
     * 隐藏上传的元素tr
     */
    function heidmaterial(){
        $("#file").attr("disabled", true);
        $("#fileVoice").attr("disabled", true);
        $("#fileVideo").attr("disabled", true);
        $("#fileThum").attr("disabled", true);
        $(".imageTr").hide();
        $(".voiceTr").hide();
        $(".videoTr").hide();
        $(".thumbTr").hide();
    }

    $("input[type=file]").change(function(){
        if($("#typese").val() == "image" ){
            doUploadImage();
        }else if($("#typese").val() == "voice" ){
            doUploadVoice();
        }else if($("#typese").val() == "video" ){
            doUploadVideo();
        }
        else if($("#typese").val() == "thumb" ){
            doUploadThumb();
        }
    })//选择文件后自动上传

    /**
     * 图片点击事件
     * @param e
     */
    function objclick2(e){
        //获取当前被点击的图片
        var img = e.target;
        //获取提交表单的隐藏图片输入框
        var inputImg = document.getElementById("inputImg");
        //删除提示
        var con = confirm("确定要删除该图片吗？"); //在页面上弹出对话框
        if(con == true){
            //设置输入框为值为""并删除点击的img元素
            inputImg.setAttribute("value", "");
            img.parentNode.removeChild(img);
        }
    };
</script>

</body>
</html>
