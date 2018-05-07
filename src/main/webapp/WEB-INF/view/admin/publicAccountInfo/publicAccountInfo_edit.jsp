<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>帐号信息修改</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/publicAccountInfo/saveOrUpdate";
            $.post(url, $("#editForm").serializeArray(), function (result) {
                if (result.state) {
                    layer.open({
                        type: 1,
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >操作成功</div>',
                        btn: ['关闭'],
                        btn2: function () {
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
                        btn2: function () {
                            layer.closeAll();
                        },
                        end: function () {
                            cancel();
                        }
                    });
                }
            }, "json");
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
        <form id="editForm" action="${path}/publicAccountInfo/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="parentMenuId" value="${bean.parentMenuId}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">公众帐号基础信息</td>
                    </tr>
                    <tr>
                        <td class="table-header">公众帐号名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="accountName"
                                               value="${bean.accountName}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">公众号原始ID<span style="color: red">*</span></td>
                        <td colspan="3"><input type="text" class="u-input" name="accountId" value="${bean.accountId}">
                        </td>
                        <td class="table-header">token<span style="color: red">*</span></td>
                        <td colspan="3"><input type="text" class="u-input" name="token" value="${bean.token}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">AppId<span style="color: red">*</span></td>
                        <td colspan="3"><input type="text" class="u-input" name="appId" value="${bean.appId}"></td>
                        <td class="table-header">App_Secret<span style="color: red">*</span></td>
                        <td colspan="3"><input type="text" class="u-input" name="appSecret" value="${bean.appSecret}">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">帐号类型<span style="color: red">*</span></td>
                        <td colspan="3">
                            <select name="accountType" class="u-input">
                                <option value="1" <c:if test="${bean.accountType == 1}">selected="selected"</c:if> >个人号</option>
                                <option value="2" <c:if test="${bean.accountType == 2}">selected="selected"</c:if> >服务号</option>
                                <option value="3" <c:if test="${bean.accountType == 3}">selected="selected"</c:if> >订阅号</option>
                                <option value="4" <c:if test="${bean.accountType == 4}">selected="selected"</c:if> >企业号</option>
                                <option value="5" <c:if test="${bean.accountType == 5}">selected="selected"</c:if> >小程序</option>
                                <option value="6" <c:if test="${bean.accountType == 6}">selected="selected"</c:if> >测试号</option>
                            </select>
                        </td>
                        <td class="table-header">是否启用<span style="color: red">*</span></td>
                        <td colspan="3">
                            <select name="usable" class="u-input">
                                <option value="0" <c:if test="${bean.usable == 0}">selected="selected"</c:if> >不启用</option>
                                <option value="1" <c:if test="${bean.usable == 1}">selected="selected"</c:if> >启用</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">接口地址<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="interfaceUrl"value="${bean.interfaceUrl}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">token有效期<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="effectiveTime" value="${bean.effectiveTime}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">开放平台</td>
                        <td colspan="7"><input type="text" class="u-input" name="openPlatform" value="${bean.openPlatform}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">商户平台账号</td>
                        <td colspan="3"><input type="text" class="u-input" name="mchId" value="${bean.mchId}"></td>
                        <td class="table-header">商户平台秘钥</td>
                        <td colspan="3"><input type="text" class="u-input" name="mchKey" value="${bean.mchKey}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">支付错误链接</td>
                        <td colspan="3"><input type="text" class="u-input" name="notifyErrorUrl" value="${bean.notifyErrorUrl}"></td>
                        <td class="table-header">支付成功链接</td>
                        <td colspan="3"><input type="text" class="u-input" name="notifyUrl" value="${bean.notifyUrl}">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">描述</td>
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
</html>
