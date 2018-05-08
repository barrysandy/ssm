<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>查看详情</title>
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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">文章详情</td>
                </tr>
                <tr>
                    <td class="table-header" rowspan="4">封面</td>
                    <td colspan="2" rowspan="4"><img src="${bean.artImg}" style="width: auto;max-height: 120px;"></td>
                </tr>
                <tr>
                    <td class="table-header"> 标题</td>
                    <td colspan="4">&nbsp;${bean.artTitle}</td>
                </tr>
                <tr>
                    <td class="table-header">第几期</td>
                    <td colspan="4">&nbsp;${bean.currentPeriod}</td>
                </tr>
                <tr>
                    <td class="table-header">本期的位置</td>
                    <td colspan="4">&nbsp;${bean.currentPeriodSort}</td>
                </tr>

                <tr>
                    <td class="table-header">内容</td>
                    <td colspan="7"><textarea class="u-textarea" style="font-family: 'Microsoft YaHei', \5fae\8f6f\96c5\9ed1, arial, \5b8b\4f53,serif;width: 99%;">${bean.artSummary}</textarea></td>
                </tr>
                <tr>
                    <td class="table-header">链接</td>
                    <td colspan="7">&nbsp;${bean.artUrl}</td>
                </tr>
                <tr>
                    <td class="table-header"> 创建时间</td>
                    <td colspan="7">&nbsp;<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                </tr>
                <tr>
                    <td class="table-header">其他描述</td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
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
