<!doctype html>
<html>
<head>
    <title>价格编辑器</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/commodityPrice/saveOrUpdate";
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
        <form id="editForm" action="${path}/commodityPrice/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="address" value="${bean.address}">
                <input type="hidden" name="remark" value="${bean.remark}">
                <input type="hidden" name="commodityId" value="${commodityId}">
                <input type="hidden" name="menuId" value="${menuId}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">价格编辑器</td>
                    </tr>
                    <tr>
                        <td class="table-header">选择时间[ ${bean.priceTime} ]<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div class="calendarWarp">
                                <input class='ECalendar text-word' style="width:200px;" type="text" id="ECalendar_date"  name="createTime"  value="${bean.createTime }"/>
                            </div>
                            <script type="text/javascript">
                                $(function(){
                                    $("#ECalendar_date").ECalendar({
                                        type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
                                        stamp : false,   //是否转成时间戳，默认true;
                                        offset:[0,2],   //弹框手动偏移量;
                                        format:"yyyy-mm-dd hh:ii",   //时间格式 默认 yyyy-mm-dd hh:ii;
                                        skin:3,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
                                        step:10,   //选择时间分钟的精确度;
                                        callback:function(v,e){} //回调函数
                                    });
                                })
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">价格名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="priceName" value="${bean.priceName}" placeholder="例如：成人价格"></td>
                    </tr>
                    <tr>
                        <td class="table-header">价格（￥）<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="price" value="${bean.price}" placeholder="保留两位小数"></td>
                    </tr>
                    <tr id="shareTitle">
                        <td class="table-header"><span >价格单位</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="priceUnit" value="${bean.priceUnit}" placeholder="单位 例如： /个 /人 ....." maxlength="255"></td>
                    </tr>
                    <tr>
                        <td class="table-header">价格排序<span style="color: red">*</span></td>
                        <td colspan="7">
                            <input type="text" class="u-input" name="sort" value="${bean.sort}" placeholder="越小越排前面" >
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">价格状态<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="status" class="u-input" id="status">
                                <option value="1" <c:if test="${bean.status == 1}">selected="selected"</c:if> >正常</option>
                                <option value="0" <c:if test="${bean.status == 0}">selected="selected"</c:if> >隐藏</option>
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
