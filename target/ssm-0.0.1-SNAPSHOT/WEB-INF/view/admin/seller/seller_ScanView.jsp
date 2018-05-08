<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>添加核销人员</title>
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
    <div align="center" style="margin-top: 12%;">
        <img src="http://qr.liantu.com/api.php?text=${url }"/>
    </div>
    <div align="center" style="color: black;font-size: 16px;">
        <b>请需要添加为核销人员的微信号扫描此二维码，点击绑定即可。</b>
        <div style="display: none;">${url }</div>
    </div>
    <div class="layout-foot f-b-t f-p-t-xs f-bg-light noprint" style="height:40px">
        <div class="f-right f-m-r-xs">
            <button type="button" class="u-btn" onClick="cancel()">关闭</button>
        </div>
    </div>
</div>
</body>
</html>
