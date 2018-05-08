<!doctype html>
<html>
<head>
    <title>关注用户信息表新增</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/focusedUserInfo/saveOrUpdate.htm";
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
        <form id="editForm" action="${path}/focusedUserInfo/saveOrUpdate.htm" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="9" class="f-b-n" style="font-size:18px">关注用户信息表</td>
                    </tr>
                    <tr>
                        <td class="table-header" rowspan="4">头像<span style="color: red">*</span></td>
                        <td rowspan="4"><img src="${bean.headImgUrl}" width="80" style="margin: 10px 5px" onerror="this.src='${path}/resources/img/usermen.png'"></td>
                    </tr>
                    <tr>
                        <td class="table-header">微信昵称<span style="color: red">*</span></td>
                        <td colspan="6"><input type="text" class="u-input" value="${bean.nickName}" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">备注</td>
                        <td colspan="6"><input type="text" class="u-input" name="username" value="${bean.username}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">性别<span style="color: red">*</span></td>
                        <td colspan="6">
                            <select class="u-input" name="sex">
                                <option value="1" <c:if test="${bean.sex == 1}">selected="selected"</c:if> >男</option>
                                <option value="2" <c:if test="${bean.sex == 2}">selected="selected"</c:if> >女</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">openId</td>
                        <td colspan="8"><input type="text" class="u-input" name="openid" value="${bean.openid}" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">unionid</td>
                        <td colspan="8"><input type="text" class="u-input" name="unionid" value="${bean.unionid}" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">关注时间</td>
                        <td colspan="8"><input type="text" class="u-input" value="${bean.subscribeTime}" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">关注状态</td>
                        <td colspan="8"><input type="text" class="u-input" value="${bean.subscribeStr}" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">入库时间</td>
                        <td colspan="8"><input type="text" class="u-input" value="<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate>" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">更新时间</td>
                        <td colspan="8"><input type="text" class="u-input" value="<fmt:formatDate value="${bean.updateTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate>" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">手机号码</td>
                        <td colspan="8"><input type="text" class="u-input" name="userphone" value="${bean.userphone}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">密码</td>
                        <td colspan="8"><input type="text" class="u-input" name="password" value="${bean.password}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">国家</td>
                        <td colspan="2"><input type="text" class="u-input" name="country" value="${bean.country}"></td>
                        <td class="table-header">省份</td>
                        <td colspan="2"><input type="text" class="u-input" name="province" value="${bean.province}"></td>
                        <td class="table-header">城市</td>
                        <td colspan="2"><input type="text" class="u-input" name="city" value="${bean.city}"></td>
                    </tr>
                    <tr style="display: none;">
                        <td class="table-header">菜单ID</td>
                        <td colspan="8"><input type="text" class="u-input" name="menuId" value="${bean.menuId}"></td>
                    </tr>
                    <tr style="display: none;">
                        <td class="table-header">父级菜单ID</td>
                        <td colspan="8"><input type="text" class="u-input" name="parentMenuId" value="${bean.parentMenuId}"></td>
                    </tr>
                    <%--<tr>
                        <td class="table-header">设置分组</td>
                        <td colspan="7">
                            <select class="u-input" name="groupId">
                                <option value="" <c:if test="${bean.groupId == ''}">selected="selected"</c:if> >未选择分组</option>
                                <c:forEach items="${groupList}" var="group" varStatus="status">
                                    <option value="${group.id}" <c:if test="${bean.groupId == group.id}">selected="selected"</c:if> >${group.groupName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>--%>
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
