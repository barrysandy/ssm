<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>微信公众号菜单新增</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/weChatMenu/saveOrUpdate.htm";
            var parentId = '${firstMenuId}';
            $.post(url, $("#editForm").serializeArray(), function (result) {
                if (result.state) {
                    layer.open({
                        type: 1,
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['300px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >操作成功</div>',
                        btn: ['关闭'],
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            cancel(parentId);
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
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            cancel(parentId);
                        }
                    });
                }
            },"json");
        }
        function cancel(parentId) {
            /*if (parentId != null){
                window.parent.initSecondMenu(parentId);
            }else{*/
                window.parent.location.reload();
//            }
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

    </script>
</head>
<body>
<div class="g-layout">
    <div class="layout-center">
        <form id="editForm" action="${path}/weChatMenu/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuId}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">微信公众号菜单配置表</td>
                    </tr>
                    <c:if test="${firstMenuId != null && firstMenuId != ''}">
                        <tr>
                            <td class="table-header">父级菜单ID<span style="color: red">*</span></td>
                            <td colspan="7"><input type="text" class="u-input" name="parentId" value="${firstMenuId}"></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="table-header">菜单名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="menuName" value="${bean.menuName}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">菜单类型<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="menuType" class="u-input">
                                <option value="CompleteButton" <c:if test="${bean.menuType == 'CompleteButton'}">selected="selected"</c:if> >复合型菜单（其下包含子菜单）CompleteButton</option>
                                <option value="ClickButton" <c:if test="${bean.menuType == 'ClickButton'}">selected="selected"</c:if> >按钮菜单（需要绑定key）ClickButton</option>
                                <option value="ViewButton" <c:if test="${bean.menuType == 'ViewButton'}">selected="selected"</c:if> >视图菜单（点击后进入某个页面，需要填写url）ViewButton</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">按钮的key<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="menuKey" value="${bean.menuKey}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">视图菜单链接url<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="menuUrl" value="${bean.menuUrl}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">菜单描述<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="menuDescribe" value="${bean.menuDescribe}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">菜单排序（越小越排前面）<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="menuSort" value="${bean.menuSort}"></td>
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
