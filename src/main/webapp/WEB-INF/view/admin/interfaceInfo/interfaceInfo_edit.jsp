<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>接口配置表新增</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/interfaceInfo/saveOrUpdate.htm";
            $.post(url, $("#editForm").serializeArray(), function (result) {
                if (result.state) {
                    layer.open({
                        type: 1,
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['300px', '200px'],
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
                        area: ['300px', '200px'],
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
        <form id="editForm" action="${path}/interfaceInfo/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">接口配置表</td>
                    </tr>
                    <tr>
                        <td class="table-header">接口描述<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">接口标识<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="keyes" value="${bean.keyes}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">接口URL地址<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="url" value="${bean.url}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">参数<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="params" value="${bean.params}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">接口类型<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="type" class="u-input">
                                <option value="1" <c:if test="${bean.type == 1}">selected="selected"</c:if> >微信Api接口</option>
                                <option value="2" <c:if test="${bean.type == 2}">selected="selected"</c:if> >内部接口</option>
                            </select>
                        </td>
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
