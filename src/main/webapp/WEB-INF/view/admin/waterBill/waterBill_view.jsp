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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">${bean.orderNo}订单流水详情</td>
                </tr>
                <tr>
                    <td class="table-header">订单流水ID</td>
                    <td colspan="7">&nbsp;${bean.id}</td>
                </tr>
                <tr>
                    <td class="table-header">订单编号</td>
                    <td colspan="7">&nbsp;${bean.orderNo}</td>
                </tr>
                <tr>
                    <td class="table-header">流水记录时间</td>
                    <td colspan="7">&nbsp;<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                </tr>
                <tr>
                    <td class="table-header">金额</td>
                    <td colspan="7">&nbsp;${bean.money}</td>
                </tr>
                <tr>
                    <td class="table-header">用户标识</td>
                    <td colspan="7">&nbsp;${bean.openid}</td>
                </tr>
                <tr>
                    <td class="table-header">用户id</td>
                    <td colspan="7">&nbsp;${bean.userId}</td>
                </tr>
                <tr>
                    <td class="table-header">备注</td>
                    <td colspan="7">&nbsp;${bean.remarks}</td>
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
