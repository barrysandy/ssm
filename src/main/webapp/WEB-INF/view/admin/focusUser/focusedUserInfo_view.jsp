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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">关注用户信息表详情</td>
                </tr>
                <tr>
                    <td class="table-header" rowspan="4">头像</td>
                    <td rowspan="4"><img src="${bean.headImgUrl}" width="80" style="margin: 10px 5px" onerror="this.src='${path}/resources/img/usermen.png'"></td>
                </tr>
                <tr>
                    <td class="table-header">昵称</td>
                    <td colspan="6">&nbsp;${bean.nickName}</td>
                </tr>
                <tr>
                    <td class="table-header">备注</td>
                    <td colspan="6">&nbsp;${bean.username}</td>
                </tr>
                <tr>
                    <td class="table-header">性别</td>
                    <td colspan="6">&nbsp;${bean.sexStr}</td>
                </tr>
                <tr>
                    <td class="table-header">openid</td>
                    <td colspan="8">&nbsp;${bean.openid}</td>
                </tr>
                <tr>
                    <td class="table-header">unionid</td>
                    <td colspan="8">&nbsp;${bean.unionid}</td>
                </tr>
                <tr>
                    <td class="table-header">手机号码</td>
                    <td colspan="8">&nbsp;${bean.userphone}</td>
                </tr>
                <tr>
                    <td class="table-header">密码</td>
                    <td colspan="8">&nbsp;${bean.password}</td>
                </tr>
                <tr>
                    <td class="table-header">关注状态</td>
                    <td colspan="8">&nbsp;${bean.subscribeStr}</td>
                </tr>
                <tr>
                    <td class="table-header">关注时间</td>
                    <td colspan="8">&nbsp;${bean.subscribeTime}</td>
                </tr>
                <tr>
                    <td class="table-header">入库时间</td>
                    <td colspan="8">&nbsp;<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                </tr>
                <tr>
                    <td class="table-header">更新时间</td>
                    <td colspan="8">&nbsp;<fmt:formatDate value="${bean.updateTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                </tr>
                <tr>
                    <td class="table-header">国家</td>
                    <td colspan="8">&nbsp;${bean.country}</td>
                </tr>
                <tr>
                    <td class="table-header">省份</td>
                    <td colspan="8">&nbsp;${bean.province}</td>
                </tr>
                <tr>
                    <td class="table-header">城市</td>
                    <td colspan="8">&nbsp;${bean.city}</td>
                </tr>
                <tr style="display: none;">
                    <td class="table-header">菜单ID</td>
                    <td colspan="8">&nbsp;${bean.menuId}</td>
                </tr>
                <tr style="display: none;">
                    <td class="table-header">父级菜单ID</td>
                    <td colspan="8">&nbsp;${bean.parentMenuId}</td>
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
