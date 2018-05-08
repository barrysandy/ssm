<!doctype html>
<html>
<head>
    <title>商家编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/seller/saveOrUpdate";
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
        <form id="editForm" action="${path}/seller/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">商家</td>
                    </tr>
                    <tr>
                        <td class="table-header">名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="sellerName" value="${bean.sellerName}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">登录名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="loginName" value="${bean.loginName}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">登录密码<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="password" value="${bean.password}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">email<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="email" value="${bean.email}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">电话<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="phone" value="${bean.phone}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">openid<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="openid" value="${bean.openid}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">qq<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="qq" value="${bean.qq}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">所属公众号<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="menuId" value="${bean.menuId}"></td>
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
