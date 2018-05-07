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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">持久化MQ:<span style="color: green;"> ${bean.id}</span></td>
                </tr>
                <tr>
                    <td class="table-header">消息等级</td>
                    <td colspan="7">&nbsp;${bean.rank} （一共10等级，1~10级 1级为最高等级 最优先执行的消息队列）</td>
                </tr>
                <tr>
                    <td class="table-header">描述</td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>
                <tr>
                    <td class="table-header">消息处理的地址</td>
                    <td colspan="7">&nbsp;${bean.url}</td>
                </tr>
                <tr>
                    <td class="table-header">消息来源</td>
                    <td colspan="7">&nbsp;${bean.msgFrom}</td>
                </tr>
                <tr>
                    <td class="table-header">消息状态</td>
                    <td colspan="7">
                        <c:if test="${bean.status == 0}"><span style="color: red;">&nbsp;待处理</span></c:if>
                        <c:if test="${bean.status == 1}"><span style="color: lightskyblue;">&nbsp;已消费</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td class="table-header">创建时间</td>
                    <td colspan="7">&nbsp;${bean.createTime}</td>
                </tr>
                <tr>
                    <td class="table-header">消息预执行时间</td>
                    <td colspan="7">&nbsp;${bean.preExecutionTime}</td>
                </tr>
                <tr>
                    <td class="table-header">消息消费时间</td>
                    <td colspan="7">&nbsp;${bean.consumeTime}</td>
                </tr>
                <tr>
                    <td class="table-header">消息处理结果</td>
                    <td colspan="7">&nbsp;${bean.result}</td>
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
