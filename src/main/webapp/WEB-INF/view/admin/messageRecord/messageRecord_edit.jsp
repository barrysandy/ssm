<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
    <title>新增短信发送</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/messageRecord/saveOrUpdate.htm";
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
        <form id="editForm" action="${path}/messageRecord/saveOrUpdate.htm" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuid}">
                <input type="hidden" name="userId" value="${bean.userId}">
                <input type="hidden" name="responseStatus" value="${bean.responseStatus}">
                <input type="hidden" name="status" value="${bean.status}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">短信发送记录</td>
                    </tr>
                    <tr>
                        <td class="table-header">电话号码<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="mobile" id="mobile" value="${bean.mobile}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span>消息签名</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="sign" value="${bean.sign}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span>消息内容</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="content" value="${bean.content}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span>消息描述</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">消息配置<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select class="u-input" name="set">
                                <option value="0" selected="selected">默认不发送</option>
                                <option value="1" >保存就发送</option>
                            </select>
                        </td>
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

    /**
     * 初始化隐藏和显示选择的类型
     * text image art voice video
     */
    $(function(){
        if($("#typese").val() == "text" ){
            $("#titleName").text("回复文本内容");
        }else if($("#typese").val() == "image" ){
            $("#titleName").text("回复图片素材material_id");
        }else if($("#typese").val() == "art" ){
            $("#titleName").text("回复文章的期");
        }else if($("#typese").val() == "voice" ){
            $("#titleName").text("回复语音素材material_id");
        }else if($("#typese").val() == "video" ){
            $("#titleName").text("回复视频素材material_id");
        }else{
            $("#titleName").text("回复文本内容");
        }
    });

    /**
     * select选择的类型
     */
    $("select#typese").change(function(){
        if($("#typese").val() == "text" ){
            $("#titleName").text("回复文本内容");
        }else if($("#typese").val() == "image" ){
            $("#titleName").text("回复图片素材material_id");
        }else if($("#typese").val() == "art" ){
            $("#titleName").text("回复文章的期");
        }else if($("#typese").val() == "voice" ){
            $("#titleName").text("回复语音素材material_id");
        }else if($("#typese").val() == "video" ){
            $("#titleName").text("回复视频素材material_id");
        }else{
            $("#titleName").text("回复文本内容");
        }
    });

    /**
     * select选择的类型,配置
     */
    $("select#keySet").change(function(){
        if($("#keySet").val() == "NULL" ){
            $("#keyes").val("");
        }else if($("#keySet").val() == "AUTO_REPLY" ){
            $("#keyes").val("AUTO_REPLY");
        }else if($("#keySet").val() == "ATTENTION_REPLY" ){
            $("#keyes").val("ATTENTION_REPLY");
        }
    });

</script>
</html>
