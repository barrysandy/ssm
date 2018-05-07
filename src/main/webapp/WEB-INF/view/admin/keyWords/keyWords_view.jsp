<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>查看详细</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">关键词回复表详情</td>
                </tr>
                <tr>
                    <td class="table-header">关键字</td>
                    <td colspan="7">&nbsp;${bean.keyes}</td>
                </tr>
                <tr>
                    <td class="table-header"><span id="titleName">关键词回复</span></td>
                    <td colspan="7">&nbsp;${bean.valuess}</td>
                </tr>
                <tr>
                    <td class="table-header">消息类型</td>
                    <td colspan="7">&nbsp;${bean.typess}<input type="hidden" id="keyType" value="${bean.typess}"></td>
                </tr>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="7">&nbsp;<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                </tr>
            </table>

            <c:if test="${bean.typess !='text'}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">关联素材详情</td>
                    </tr>
                    <tr>
                        <td class="table-header">素材描述</td>
                        <td colspan="7">&nbsp;${material.descM}</td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >素材类型</span></td>
                        <td colspan="7">&nbsp;${material.typese}</td>
                    </tr>
                    <tr>
                        <td class="table-header">素材url</td>
                        <td colspan="7">&nbsp;${material.url}</td>
                    </tr>
                    <tr>
                        <td class="table-header">素材预览</td>
                        <c:if test="${material.typese == 'image'}">
                            <td colspan="7"><img src="${material.url}" style="max-height: 120px;"></td>
                        </c:if>
                        <c:if test="${material.typese == 'voice'}">
                            <td colspan="7">
                                <audio id="voice"  preload="preload" controls="controls" src="${material.url}">你的浏览器不支持语音插件.</audio>
                            </td>
                        </c:if>
                        <c:if test="${material.typese == 'video'}">
                            <td colspan="7">
                                <video width="320" height="240" controls="controls">
                                    <source src="movie.ogg" type="video/ogg">
                                    <source id="video" src="${material.url}" type="video/mp4">你的浏览器不支持视频插件.
                                </video><!-- 视频显示框 -->
                            </td>
                        </c:if>

                    </tr>
                    <tr>
                        <td class="table-header">微信服务器material_id</td>
                        <td colspan="7">&nbsp;${material.material_id}</td>
                    </tr>
                    <tr>
                        <td class="table-header">创建时间</td>
                        <td colspan="7">&nbsp;<fmt:formatDate value="${material.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                    </tr>
                </table>
            </c:if>

        </div>


    </div>
    <div class="layout-foot f-b-t f-p-t-xs f-bg-light noprint" style="height:40px">
        <div class="f-right f-m-r-xs">
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
        if($("#keyType").val() == "text" ){
            $("#titleName").text("回复文本内容");
        }else if($("#keyType").val() == "image" ){
            $("#titleName").text("回复图片素材material_id");
        }else if($("#keyType").val() == "art" ){
            $("#titleName").text("回复文章的期");
        }else if($("#keyType").val() == "voice" ){
            $("#titleName").text("回复语音素材material_id");
        }else if($("#keyType").val() == "video" ){
            $("#titleName").text("回复视频素材material_id");
        }else{
            $("#titleName").text("回复文本内容");
        }
    });

</script>
</html>
