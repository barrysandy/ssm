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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">订单详情</td>
                </tr>
                <tr>
                    <td class="table-header">名字</td>
                    <td colspan="7">&nbsp;${bean.orderName}</td>
                </tr>

                <tr>
                    <td class="table-header">支付订单号</td>
                    <td colspan="7">&nbsp;${bean.transactionId}</td>
                </tr>
                <tr>
                    <td class="table-header">订单编号</td>
                    <td colspan="7">&nbsp;${bean.orderNo}</td>
                </tr>
                <tr>
                    <td class="table-header">订单提交时间</td>
                    <td colspan="7">&nbsp;${bean.createTime}</td>
                </tr>
                <tr>
                    <td class="table-header">支付方式</td>
                    <td colspan="7">
                        <c:if test="${bean.payType eq 'WECHAT_PAY'}">微信</c:if>
                        <c:if test="${bean.payType eq 'ALIBABA_PAY'}">支付宝</c:if>
                    </td>
                </tr>


                <tr>
                    <td class="table-header">订单金额</td>
                    <td colspan="7">&nbsp;${bean.orderAmountMoney}</td>
                </tr>

                <tr>
                    <td class="table-header">订单描述</td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>

                <tr>
                    <td class="table-header">订单失效时间</td>
                    <td colspan="7">&nbsp;${bean.invalidTime}</td>
                </tr>

                <tr>
                    <td class="table-header">订单所属用户</td>
                    <td colspan="7">&nbsp;${bean.user.nickName}</td>
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
