<!doctype html>
<html>
<head>
    <title>报名编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />

    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/meetingCard/saveOrUpdate";
            $.post(url, $("#editForm").serializeArray(), function (result) {
                if (result.state) {
                    layer.open({
                        type: 1,
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >操作成功</div>',
                        btn: ['关闭'],
                        btn2: function(){
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
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            cancel();
                        }
                    });
                }
            },"json");
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
        <form id="editForm" action="${path}/meetingCard/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}"/>
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">编辑</td>
                    </tr>
                    <tr>
                        <td class="table-header">姓名<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="name" value="${bean.name}" maxlength="20"></td>
                    </tr>
                    <tr>
                        <td class="table-header">电话<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="phone" value="${bean.phone}" maxlength="18"></td>
                    </tr>
                    <tr>
                        <td class="table-header">地址<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="address" value="${bean.address}" maxlength="120"></td>
                    </tr>
                    <tr>
                        <td class="table-header">数量<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="numberTotal" value="${bean.numberTotal}" maxlength="11"></td>
                    </tr>
                    <tr>
                        <td class="table-header">用户ID<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="userId" value="${bean.userId}" maxlength="200"></td>
                    </tr>
                    <tr>
                        <td class="table-header">用户Openid<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="userOpenid" value="${bean.userOpenid}" maxlength="200"></td>
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
