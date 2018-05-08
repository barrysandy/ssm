<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>查看详情</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script>
        function cancel() {
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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">系统字典目录表详情</td>
                </tr>
                <tr>
                    <td class="table-header">目录名</td>
                    <td colspan="3">&nbsp;${bean.name}</td>
                    <td class="table-header">目录code</td>
                    <td colspan="3">&nbsp;${bean.code}</td>
                </tr>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="3">&nbsp;<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                    <td class="table-header">更新时间</td>
                    <td colspan="3">&nbsp;<fmt:formatDate value="${bean.updateTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                </tr>
                <tr>
                    <td class="table-header">描述</td>
                    <td colspan="7">
                        <textarea class="u-textarea" style="font-family: 'Microsoft YaHei',serif;width: 99%;"
                                  readonly="readonly">${bean.descM}</textarea>
                    </td>
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
