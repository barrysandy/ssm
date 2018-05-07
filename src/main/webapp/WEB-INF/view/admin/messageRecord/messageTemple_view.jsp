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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">短信模板详情</td>
                </tr>
                <tr>
                    <td class="table-header">模板名称</td>
                    <td colspan="7">&nbsp;${bean.templeName}</td>
                </tr>
                <tr>
                    <td class="table-header">模板ID</td>
                    <td colspan="7">&nbsp;${bean.templeId}</td>
                </tr>
                <tr>
                    <td class="table-header">商品ID</td>
                    <td colspan="7">&nbsp;${bean.commodityId}</td>
                </tr>
                <tr>
                    <td class="table-header">模板类型</td>
                    <td colspan="7">&nbsp;${bean.templeType}</td>
                </tr>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="7">&nbsp;${bean.createTime}</td>
                </tr>

                <tr>
                    <td class="table-header">更新时间</td>
                    <td colspan="7">&nbsp;${bean.updateTime}</td>
                </tr>
                <tr>
                    <td class="table-header">描述</td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>
                <tr>
                    <td class="table-header">状态</td>
                    <td colspan="7">&nbsp;${bean.status}</td>
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
