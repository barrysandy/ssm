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
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">商品详情</td>
                </tr>
                <tr>
                    <td class="table-header">名称</td>
                    <td colspan="7">&nbsp;${bean.commodityName}</td>
                </tr>
                <tr>
                    <td class="table-header">商品有效时间</td>
                    <td colspan="7">&nbsp;${bean.createDate} - ${bean.invalidDate}</td>
                </tr>
                <tr>
                    <td class="table-header">商品图片（第一张默认为封面）</td>
                    <td colspan="7">&nbsp;
                        <c:forEach items="${listCommodityImage }" var="img">
                            <img src="${img.url}" height="120px;">
                        </c:forEach>
                        <input type="text" id="inputImg" class="u-input" name="arrayImg" value="${bean.arrayImg}">
                    </td>
                </tr>
                <tr>
                    <td class="table-header">库存</td>
                    <td colspan="7">&nbsp;${bean.commodityStock}</td>
                </tr>
                <tr>
                    <td class="table-header">价格</td>
                    <td colspan="7">&nbsp;${bean.commodityPrice}</td>
                </tr>
                <tr>
                    <td class="table-header">商品详情</td>
                    <td colspan="7">&nbsp;${bean.commodityDetails}</td>
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
