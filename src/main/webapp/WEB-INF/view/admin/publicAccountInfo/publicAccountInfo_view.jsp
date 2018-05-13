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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">公众帐号基础信息详情</td>
                </tr>
                <tr>
                    <td class="table-header">公众帐号名称</td>
                    <td colspan="7">&nbsp;${bean.accountName}</td>
                </tr>
                <tr>
                    <td class="table-header">公众号原始ID</td>
                    <td colspan="3">&nbsp;${bean.accountId}</td>
                    <td class="table-header">token</td>
                    <td colspan="3">&nbsp;${bean.token}</td>
                </tr>
                <tr>
                    <td class="table-header">AppId</td>
                    <td colspan="3">&nbsp;${bean.appId}</td>
                    <td class="table-header">App_Secret</td>
                    <td colspan="3">&nbsp;${bean.appSecret}</td>
                </tr>
                <tr>
                    <td class="table-header">帐号类型</td>
                    <td colspan="3">&nbsp;${bean.accountType}</td>
                    <td class="table-header">是否启用</td>
                    <td colspan="3">&nbsp;${bean.usableStr}</td>
                </tr>
                <tr>
                    <td class="table-header">接口地址</td>
                    <td colspan="7">&nbsp;${bean.interfaceUrl}</td>
                </tr>
                <tr>
                    <td class="table-header">创建日期</td>
                    <td colspan="7">&nbsp;<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                </tr>
                <tr>
                    <td class="table-header">token有效期</td>
                    <td colspan="7">&nbsp;${bean.effectiveTime}</td>
                </tr>
                <tr>
                    <td class="table-header">开放平台</td>
                    <td colspan="7">&nbsp;${bean.openPlatform}</td>
                </tr>
                <tr>
                    <td class="table-header">商户平台账号</td>
                    <td colspan="3">&nbsp;${bean.mchId}</td>
                    <td class="table-header">商户平台秘钥</td>
                    <td colspan="3">&nbsp;${bean.mchKey}</td>
                </tr>
                <tr>
                    <td class="table-header">支付错误链接</td>
                    <td colspan="3">&nbsp;${bean.notifyErrorUrl}</td>
                    <td class="table-header">支付成功链接</td>
                    <td colspan="3">&nbsp;${bean.notifyUrl}</td>
                </tr>
                <tr>
                    <td class="table-header">系统菜单Id</td>
                    <td colspan="7">&nbsp;${bean.menuId}</td>
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
