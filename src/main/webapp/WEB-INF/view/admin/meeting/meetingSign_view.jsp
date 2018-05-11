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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">参会人员详情</td>
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
                    <td class="table-header">签到码</td>
                    <td colspan="7">&nbsp;${bean.signCode}</td>
                </tr>
                <tr>
                    <td class="table-header">单位/机构</td>
                    <td colspan="7">&nbsp;${bean.company}</td>
                </tr>
                <tr>
                    <td class="table-header">类型</td>
                    <td colspan="7">&nbsp;${bean.personType}</td>
                </tr>

                <tr>
                    <td class="table-header">职位</td>
                    <td colspan="7">&nbsp;${bean.position}</td>
                </tr>
                <tr>
                    <td class="table-header">是否参加晚宴</td>
                    <td colspan="7">
                        <c:if test="${bean.joinDinner == 0}"><span style="color: red;">不参加</span></c:if>
                        <c:if test="${bean.joinDinner == 1}"><span style="color: green;">参加</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td class="table-header">签到状态</td>
                    <td colspan="7">
                        <c:if test="${bean.status == 0}"><span style="color: red;">未签到</span></c:if>
                        <c:if test="${bean.status == 1}"><span style="color: green;">已签到</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="7">&nbsp;${bean.createTime}</td>
                </tr>
                <tr>
                    <td class="table-header">签到时间</td>
                    <td colspan="7">&nbsp;${bean.updateTime}</td>
                </tr>
                <tr>
                    <td class="table-header">详情</td>
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
