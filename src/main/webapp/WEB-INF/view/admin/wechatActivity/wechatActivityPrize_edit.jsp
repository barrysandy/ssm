<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
    <title>微信活动奖品编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <style>
        .divleft{
            float:left;width: auto;max-height:110px;
        }
        .divright{
            float:right;min-width: 60px;line-height:110px;max-height:110px;background-color: #eeeeee;
        }
        .upload_img{
            width: 100px;
        }
    </style>
    <script src="${path}/resources/js/plugins/layer/layer.min.js"></script>

    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/activityPrize/saveOrUpdate.htm";
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
        <form id="editForm" action="${path}/activityPrize/saveOrUpdate.htm" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuid}">
                <table class="m-table-forms inline" id="table">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">奖品编辑</td>
                    </tr>
                    <tr>
                        <td class="table-header">奖品名称<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="name" id="title" value="${bean.name}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >奖品描述</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >奖品图片</span><span style="color: red">*</span></td>
                        <td colspan="7">
                            <div id="showIma">
                                <img src="${image}" height="120px;" onclick="objclick2(event)">
                            </div>
                            <input id="file" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Image.js">
                                updateImg = "";//上传文件的返回url
                                material_id = "";//上传文件的返回material_id
                            </script>
                            <input type="text" id="inputImg" class="u-input" name="image" value="${bean.image}">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >奖品数量</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="quantity" value="${bean.quantity}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >获奖概率</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="odds" value="${bean.odds}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">奖品失效时间<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div class="calendarWarp">
                                <input class='ECalendar text-word' style="width:200px;" type="text" id="ECalendar_date"  name="invalidTime"  value="${bean.invalidTime }"/>
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
                        <td class="table-header">奖品所属活动<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="wechatActivityId" class="u-input">
                                <option value="-1">暂不绑定</option>
                                <c:forEach items="${activityList }" var="listBean">
                                    <option value="${listBean.id}" <c:if test="${bean.wechatActivityId == listBean.id}">selected="selected"</c:if> >${listBean.title}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">奖品关联商品，该值在非-1情况下，抽奖会创建该绑定值的商品订单<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="commodityId" id="commodityId" value="${bean.commodityId}"></td>
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
<script type="text/javascript">
    $("input[type=file]").change(function(){
        doUploadImage();
    })//选择文件后自动上传

    /**
     * 图片点击事件
     * @param e
     */
    function objclick2(e){
        //获取当前被点击的图片
        var img = e.target;
        //获取提交表单的隐藏图片输入框
        var inputImg = document.getElementById("inputImg");
        //删除提示
        var con = confirm("确定要删除该图片吗？"); //在页面上弹出对话框
        if(con == true){
            //设置输入框为值为""并删除点击的img元素
            inputImg.setAttribute("value", "");
            img.parentNode.removeChild(img);
        }
    };
</script>
</html>
