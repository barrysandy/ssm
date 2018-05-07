<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>公众号设置</title>
    <%@ include file="/WEB-INF/common.jsp" %>
    <link href="${path }/resources/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
    <link href="${path }/resources/css/animate.css" rel="stylesheet">
    <link href="${path }/resources/css/style.css?v=4.1.0" rel="stylesheet">

    <script>
        function submitForm(){
            var accountName = $("input[name='accountName']");
            var token = $("input[name='token']");
            var appId = $("input[name='appId']");
            var appSecret = $("input[name='appSecret']");
            if (accountName.val() == null || accountName.val() == ''){
                LayuiUtil.msg("请填写公众号帐号名称");
                return
            }
            if (token.val() == null || token.val() == ''){
                LayuiUtil.msg("请填写token");
                return
            }
            if (appId.val() == null || appId.val() == ''){
                LayuiUtil.msg("请填写appId");
                return
            }
            if (appSecret.val() == null || appSecret.val() == ''){
                LayuiUtil.msg("请填写appSecret");
                return
            }
            $('#publicAccountFrom').submit();
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-content">
                    <div class="jqGrid_wrapper">
                        <form id="publicAccountFrom" action="publicAccountSave.htm" method="post">
                            <input type="hidden" name="id" value="${bean.id}">
                            <input type="hidden" name="menuid" value="${menuid}">
                            <input type="hidden" name="parentId" value="${parentId}">
                            <div class="form-group">
                                <label>公众号帐号名称:<span style="color: red">*</span></label>
                                <input type="text" class="form-control" placeholder="公众号帐号名称" name="accountName" value="${bean.accountName}">
                            </div>
                            <div class="form-group">
                                <label>公众账号原始ID:<span style="color: red">*</span></label>
                                <input type="text" class="form-control" placeholder="公众账号原始ID" name="accountId" value="${bean.accountId}">
                            </div>
                            <div class="form-group">
                                <label>AppId:<span style="color: red">*</span></label>
                                <input type="text" class="form-control" placeholder="AppId" name="appId" value="${bean.appId}">
                            </div>
                            <div class="form-group">
                                <label>AppSecret:<span style="color: red">*</span></label>
                                <input type="text" class="form-control" placeholder="AppSecret" name="appSecret" value="${bean.appSecret}">
                            </div>
                            <div class="form-group">
                                <label>token:<span style="color: red">*</span></label>
                                <input type="text" class="form-control" placeholder="您的token" name="token" value="${bean.token}">
                            </div>

                            <button type="button" class="btn btn-info" style="margin:2px 45%" onclick="submitForm()">保存</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</table>
</body>
</html>
