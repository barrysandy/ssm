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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">报名详情</td>
                </tr>
                <tr>
                    <td class="table-header">姓名</td>
                    <td colspan="7">&nbsp;${bean.name}</td>
                </tr>
                <tr>
                    <td class="table-header">电话</td>
                    <td colspan="7">&nbsp;${bean.phone}</td>
                </tr>
                <tr>
                    <td class="table-header">地址</td>
                    <td colspan="7">&nbsp;${bean.address}</td>
                </tr>
                <tr>
                    <td class="table-header">数量</td>
                    <td colspan="7">&nbsp;${bean.numberTotal}</td>
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
