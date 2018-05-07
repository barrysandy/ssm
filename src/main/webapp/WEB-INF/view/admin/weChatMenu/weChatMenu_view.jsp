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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">微信公众号菜单配置表详情</td>
                </tr>
                <tr>
                    <td class="table-header">菜单名称</td>
                    <td colspan="7">${bean.menuName}</td>
                </tr>
                <tr>
                    <td class="table-header">菜单类型</td>
                    <td colspan="7">${bean.menuType}</td>
                </tr>
                <tr>
                    <td class="table-header">点击按钮的key</td>
                    <td colspan="7">${bean.menuKey}</td>
                </tr>
                <tr>
                    <td class="table-header">视图菜单链接</td>
                    <td colspan="7">${bean.menuUrl}</td>
                </tr>
                <tr>
                    <td class="table-header">一级菜单</td>
                    <td colspan="7">${bean.menuFirst}</td>
                </tr>
                <tr>
                    <td class="table-header">二级菜单</td>
                    <td colspan="7">${bean.menuSecond}</td>
                </tr>
                <tr>
                    <td class="table-header">菜单描述</td>
                    <td colspan="7">${bean.menuDescribe}</td>
                </tr>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="7"><fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
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
