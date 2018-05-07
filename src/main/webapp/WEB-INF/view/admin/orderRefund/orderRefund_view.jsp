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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">退款申请单:<span style="color: green;"> ${bean.orderNo}</span></td>
                </tr>
                <tr>
                    <td class="table-header">商户退款单号</td>
                    <td colspan="7">&nbsp;${bean.id}</td>
                </tr>
                <tr>
                    <td class="table-header">支付订单号</td>
                    <td colspan="7">&nbsp;${bean.transactionId}</td>
                </tr>
                <tr>
                    <td class="table-header">订单名称</td>
                    <td colspan="7">&nbsp;${bean.orderName}</td>
                </tr>
                <tr>
                    <td class="table-header">支付类型</td>
                    <td colspan="7">&nbsp;${bean.payType}</td>
                </tr>
                <tr>
                    <td class="table-header">订单金额</td>
                    <td colspan="7">&nbsp;${bean.orderMoney}（分）</td>
                </tr>
                <tr>
                    <td class="table-header">退款金额</td>
                    <td colspan="7">&nbsp;${bean.refundFee}（分）</td>
                </tr>
                <tr>
                    <td class="table-header">退款原因</td>
                    <td colspan="7">&nbsp;${bean.refundDesc}</td>
                </tr>
                <tr>
                    <td class="table-header">描述</td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>
                <tr>
                    <td class="table-header">退款处理结果</td>
                    <td colspan="7">&nbsp;${bean.refundResult}</td>
                </tr>
                <tr>
                    <td class="table-header">订单所属用户</td>
                    <td colspan="7">&nbsp;${bean.userId}</td>
                </tr>
                <tr>
                    <td class="table-header">订单提交时间</td>
                    <td colspan="7">&nbsp;${bean.createTime}</td>
                </tr>
                <tr>
                    <td class="table-header">申请时间</td>
                    <td colspan="7">&nbsp;${bean.createTime}</td>
                </tr>
                <tr>
                    <td class="table-header">处理时间</td>
                    <td colspan="7">&nbsp;${bean.handleTime}</td>
                </tr>
                <tr>
                    <td class="table-header">退款时间</td>
                    <td colspan="7">&nbsp;${bean.returnTime}</td>
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
