<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
    <title>关键词回复表新增</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/keyWords/saveOrUpdate.htm";
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
        <form id="editForm" action="${path}/keyWords/saveOrUpdate.htm" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuid}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">关键词回复表</td>
                    </tr>
                    <tr>
                        <td class="table-header">关键字<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="keyes" id="keyes" value="${bean.keyes}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span id="titleName">关键词回复</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="valuess" value="${bean.valuess}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">消息类型<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="typess" class="u-input" id="typese">
                                <option value="text" <c:if test="${bean.typess == 'text'}">selected="selected"</c:if> >文本消息</option>
                                <option value="image" <c:if test="${bean.typess == 'image'}">selected="selected"</c:if> >图片消息</option>
                                <option value="art" <c:if test="${bean.typess == 'art'}">selected="selected"</c:if> >文章消息</option>
                                <option value="voice" <c:if test="${bean.typess == 'voice'}">selected="selected"</c:if> >语音消息</option>
                                <option value="video" <c:if test="${bean.typess == 'video'}">selected="selected"</c:if> >视频消息</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">消息配置<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select class="u-input" id="keySet">
                                <option value="NULL" <c:if test="${bean.keyes != 'AUTO_REPLY' && bean.keyes != 'ATTENTION_REPLY'}">selected="selected"</c:if> >非首关/默认回复配置</option>
                                <option value="AUTO_REPLY" <c:if test="${bean.keyes == 'AUTO_REPLY'}">selected="selected"</c:if> >默认消息回复（唯一：注意关键字列表中不能有多个默认消息回复AUTO_REPLY）</option>
                                <option value="ATTENTION_REPLY" <c:if test="${bean.keyes == 'ATTENTION_REPLY'}">selected="selected"</c:if> >首次关注公众号回复（唯一：注意关键字列表中不能有多个首次关注公众号回复ATTENTION_REPLY）</option>
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
