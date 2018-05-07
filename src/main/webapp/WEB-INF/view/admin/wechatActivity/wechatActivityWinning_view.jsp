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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">中奖详情</td>
                </tr>
                <tr>
                    <td class="table-header">核销码</td>
                    <td colspan="7">&nbsp;${bean.code}</td>
                </tr>
                <tr>
                    <td class="table-header"><span id="titleName">描述</span></td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>
                <tr>
                    <td class="table-header">状态</td>
                    <td colspan="7">
                        <c:if test="${bean.status == 0}">未核销</c:if><c:if test="${bean.status == 1}">已核销</c:if>
                    </td>
                </tr>
                <c:if test="${!empty bean.userId}">
                    <tr>
                        <td class="table-header">中奖玩家ID</td>
                        <td colspan="7">&nbsp;${focusedUserInfo.id}</td>
                    </tr>
                    <tr>
                        <td class="table-header">中奖昵称</td>
                        <td colspan="7">&nbsp;${focusedUserInfo.nickName}</td>
                    </tr>
                    <tr>
                        <td class="table-header">中奖openid</td>
                        <td colspan="7">&nbsp;${focusedUserInfo.openid}</td>
                    </tr>
                    <tr>
                        <td class="table-header">中奖unionid</td>
                        <td colspan="7">&nbsp;${focusedUserInfo.unionid}</td>
                    </tr>
                </c:if>
                <c:if test="${!empty listSet }">
                    <c:forEach items="${listSet }" var="listSet">
                        <tr>
                            <td class="table-header">${listSet.name}</td>
                            <td colspan="7">&nbsp;<c:if test="${!empty listSet.descM }">(${listSet.descM})</c:if>${listSet.valuese}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:forEach items="${bean.prizeList }" var="prizeList">
                    <tr>
                        <td class="table-header">奖品信息</td>
                        <td colspan="7">&nbsp;${prizeList.name} * ${prizeList.quantity} 失效时间：<fmt:formatDate value="${prizeList.invalidTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="7">&nbsp;<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
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
