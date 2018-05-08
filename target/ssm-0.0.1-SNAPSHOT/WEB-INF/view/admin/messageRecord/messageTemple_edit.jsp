<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
    <title>新增短信模板</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/messageTemple/saveOrUpdate";
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

        function getTemplates() {
            layer.open({
                type: 2,
                title: '支持的模板ID',
                skin: 'layui-layer-rim',
                area: ['80%', '80%'],
                content: '${path}/messageTemple/getTemplates'
            });
        }

    </script>
</head>
<body>
<div class="g-layout">
    <div class="layout-center">
        <form id="editForm" action="${path}/messageTemple/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="createTime" value="${bean.createTime}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">新增短信模板</td>
                    </tr>
                    <tr>
                        <td class="table-header">模板名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="templeName" id="mobile" value="${bean.templeName}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span>模板ID<a onclick="getTemplates()" style="color: red;">（查看支持的模板ID）</a></span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="templeId" value="${bean.templeId}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span>模板签名</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="sign" value="${bean.sign}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span>所属商品ID</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="commodityId" value="${bean.commodityId}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">模板类型<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select class="u-input" name="templeType">
                                <option value="SINGLE_BUY" selected="selected">【单一商品】购买</option>
                                <option value="SINGLE_BUY_GROUP_SUC_MASS">【单一商品】购买成团成功群发短信</option>
                                <option value="SINGLE_BUY_GROUP_SUC_TOBUY">【单一商品】购买成团时组合购买短信</option>
                                <option value="SINGLE_BUY_GROUP_FIAL_MASS">【单一商品】购买成团超时失败群发短信</option>
                                <option value="GROUP_BUY">【组团商品】购买</option>
                                <option value="GROUP_BUY_SUC_MASS">【组团商品】购买成团成功群发短信</option>
                                <option value="GROUP_BUY_SUC_TOBUY">【组团商品】购买成团时组合购买短信</option>
                                <option value="GROUP_BUY_FIAL_MASS">【组团商品】购买成团超时失败群发短信</option>
                                <option value="REFUND">【统一商品】统一退款模板</option>
                                <option value="WINNING">【会话活动商品】中奖生成订单提醒</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">状态<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select class="u-input" name="status">
                                <option value="1" selected="selected">正常</option>
                                <option value="0" >禁用</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header"><span>描述</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}"></td>
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
