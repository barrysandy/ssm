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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">活动详情</td>
                </tr>
                <tr>
                    <td class="table-header">活动标题</td>
                    <td colspan="7">&nbsp;${bean.title}</td>
                </tr>
                <tr>
                    <td class="table-header"><span id="titleName">活动描述</span></td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>
                <tr>
                    <td class="table-header">活动链接</td>
                    <td colspan="7">&nbsp;${bean.url}</td>
                </tr>
                <tr>
                    <td class="table-header">活动配置页面</td>
                    <td colspan="7">&nbsp;${bean.returnPage}</td>
                </tr>
                <tr>
                    <td class="table-header">活动类型</td>
                    <td colspan="7">&nbsp;${bean.types}</td>
                </tr>
                <tr>
                    <td class="table-header">是否支持分享</td>
                    <td colspan="7">&nbsp;<c:if test="${bean.share == -1}">不支持</c:if><c:if test="${bean.share == 1}">支持</c:if></td>
                </tr>
                <c:if test="${bean.share == 1}">
                    <tr>
                        <td class="table-header">分享标题</td>
                        <td colspan="7">&nbsp;${bean.shareTitle}</td>
                    </tr>
                    <tr>
                        <td class="table-header">分享描述</td>
                        <td colspan="7">&nbsp;${bean.shareDescM}</td>
                    </tr>
                    <tr>
                        <td class="table-header">分享图片</td>
                        <td colspan="7">&nbsp;<img src="${image}" style="max-width: 120px;" /></td>
                    </tr>
                </c:if>
                <tr>
                    <td class="table-header">所属微信</td>
                    <td colspan="7">&nbsp;${bindingWechat}</td>
                </tr>
                <tr>
                    <td class="table-header">是否需要微信授权登录</td>
                    <td colspan="7">&nbsp;<c:if test="${bean.authorised == -1}">不支持</c:if><c:if test="${bean.authorised == 1}">支持</c:if></td>
                </tr>
                <c:if test="${bean.authorised == 1}">
                    <tr>
                        <td class="table-header">是否支持登录后保存微信用户信息（头像，昵称，openid）</td>
                        <td colspan="7">&nbsp;<c:if test="${bean.supportGetWechatMsg == -1}">不支持</c:if><c:if test="${bean.supportGetWechatMsg == 1}">支持</c:if></td>
                    </tr>
                </c:if>
                <tr>
                    <td class="table-header">绑定的订阅号，用于验证是否需要关注该订阅号才能参与活动。</td>
                    <td colspan="7">&nbsp;${subscribeWechat}</td>
                </tr>
                <tr>
                    <td class="table-header">是否支持抽奖</td>
                    <td colspan="7">&nbsp;<c:if test="${bean.prizes == -1}">不支持</c:if><c:if test="${bean.prizes == 1}">支持</c:if></td>
                </tr>
                <c:if test="${bean.prizes == 1}">
                    <tr>
                        <td class="table-header">抽奖形式</td>
                        <td colspan="7">&nbsp;${bean.prizesType}</td>
                    </tr>
                </c:if>
                <tr>
                    <td class="table-header">报名时间</td>
                    <td colspan="7">
                        &nbsp;${bean.beginTime}
                        &nbsp;-&nbsp;${bean.endTime}
                    </td>
                </tr>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="7">&nbsp;{bean.createTime}</td>
                </tr>
                <tr>
                    <td class="table-header">状态</td>
                    <td colspan="7">&nbsp;<c:if test="${bean.status == -1}">冻结的活动</c:if><c:if test="${bean.status == 1}">进行中的活动</c:if></td>
                </tr>
                <tr>
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">活动报名动态属性</td>
                </tr>
                <tr>
                    <c:forEach items="${listSet }" var="listbean">
                    <td class="table-header">属性<span style="color: red">*</span></td>
                    <td colspan="7">
                        属性名称：${listbean.name}
                        <br>属性类型：${listbean.typese}
                        <br>是否必须：
                            <c:if test="${listbean.required == '-1'}">非必须</c:if>
                            <c:if test="${listbean.required == '1'}">必须</c:if>
                        <br>属性验证类型：
                            <c:if test="${listbean.verificationType == '-1'}">不需要验证</c:if>
                            <c:if test="${listbean.verificationType == 'phone'}">手机号码</c:if>
                            <c:if test="${listbean.verificationType == 'email'}">email邮箱</c:if>
                        <br>属性是否隐藏：
                            <c:if test="${listbean.hide == '-1'}">不隐藏</c:if>
                            <c:if test="${listbean.hide == '1'}">隐藏</c:if>
                        <br>是否验证重复：
                            <c:if test="${listbean.repeat == '-1'}">不允许重复</c:if>
                            <c:if test="${listbean.repeat == '1'}">忽略验证重复</c:if>
                        <br>属性描述：${listbean.descM}
                        <br>属性排序：${listbean.sort}
                    </td>
                </tr>
                </c:forEach>

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
