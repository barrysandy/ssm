<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
    <title>微信活动报名动态属性编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <style>
        .divleft{
            float:left;width: auto;max-height:110px;
        }
        .divright{
            float:right;min-width: 60px;line-height:110px;max-height:110px;background-color: #eeeeee;
        }
    </style>

    <script src="${path}//resources/js/plugins/layer/layer.min.js"></script>

    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/activity/saveOrUpdateSignSet";
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
        <form id="editForm" action="${path}/activity/saveOrUpdateSignSet" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuid}">
                <table class="m-table-forms inline" id="table">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">动态属性配置</td>
                    </tr>
                    <tr>
                        <c:forEach items="${listSet }" var="listbean" varStatus="i">
                        <td class="table-header">属性<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div class="divleft">
                                属性名称：<input type="text" name="name" placeholder='属性名称' value="${listbean.name}">
                                属性类型：
                                <select name="typese">
                                    <option value="radio" <c:if test="${listbean.typese == 'radio'}">selected="selected"</c:if> >单选框</option>
                                    <option value="checkBox" <c:if test="${listbean.typese == 'checkBox'}">selected="selected"</c:if> >复选框</option>
                                    <option value="input" <c:if test="${listbean.typese == 'input'}">selected="selected"</c:if> >输入框</option>
                                    <option value="textArea" <c:if test="${listbean.typese == 'textArea'}">selected="selected"</c:if> >文本框</option>
                                </select>
                                是否必须：
                                <select name="required">
                                    <option value="-1" <c:if test="${listbean.typese == '-1'}">selected="selected"</c:if> >非必须</option>
                                    <option value="1" <c:if test="${listbean.typese == '1'}">selected="selected"</c:if> >必须</option>
                                </select>
                                属性验证类型：
                                <select name="verificationType">
                                    <option value="-1" <c:if test="${listbean.typese == 'phone'}">selected="selected"</c:if> >不需要验证</option>
                                    <option value="phone" <c:if test="${listbean.typese == 'phone'}">selected="selected"</c:if> >手机号码</option>
                                    <option value="email" <c:if test="${listbean.typese == 'email'}">selected="selected"</c:if> >email邮箱</option>
                                </select>
                                属性是否隐藏：
                                <select name="hide">
                                    <option value="-1" <c:if test="${listbean.typese == '-1'}">selected="selected"</c:if> >不隐藏</option>
                                    <option value="1" <c:if test="${listbean.typese == '1'}">selected="selected"</c:if> >隐藏</option>
                                </select>
                                是否验证重复：
                                <select name="repeat">
                                    <option value="1" <c:if test="${listbean.typese == '1'}">selected="selected"</c:if> >不允许重复</option>
                                    <option value="-1" <c:if test="${listbean.typese == '-1'}">selected="selected"</c:if> >忽略验证重复</option>
                                </select>
                                <br><textarea name="descM" placeholder='属性描述信息'>${listbean.descM}</textarea>
                                属性排序：<input type="text" name="sort" placeholder='数字越小，越排前面。' value="${listbean.sort}">
                                <input type="hidden" name="setType" value="${listbean.setType}">
                            </div>
                            <div class="divright" align="center"onclick='deleteTr(this)'><a>删除</a></div>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                <b onclick="addNewAttribute()">添加属性</b>
            </div>
            <input type="hidden" name="wId" value="${wechatActivityId}">
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
    /**
     * 添加新属性
     * */
    function addNewAttribute(){
        var wechatActivityId = $("#wechatActivityId").val();
        var trcomp="<tr><td class='table-header'><span >属性</span><span style='color: red'>*</span></td><td colspan='7'>" +
            "<div class='divleft'>属性名称：<input type='text' name='name' placeholder='属性名称'>" +
            "属性类型：<select name='typese'><option value='radio'>单选框</option><option value='checkBox'>复选框</option><option value='input'>输入框</option><option value='textArea'>文本框</option></select>" +
            "是否必须：<select name='required'><option value='-1'>非必须</option><option value='1'>必须</option></select>" +
            "属性验证类型：<select name='verificationType'><option value='-1'>不需要验证</option><option value='phone'>手机号码</option><option value='email'>email邮箱</option></select>" +
            "属性是否隐藏：<select name='hide'><option value='-1'>不隐藏</option><option value='1'>隐藏</option></select>" +
            "是否验证重复：<select name='repeat'><option value='1'>不允许重复</option><option value='-1'>忽略验证重复</option></select>" +
            "<br><textarea name='descM' placeholder='属性描述信息'></textarea>" +
            "属性排序：<input type='text' name='sort' placeholder='数字越小，越排前面。' value='999'>" +
            "<input type='hidden' name='setType' value='0'></div>" +
            "<div class='divright' align='center'onclick='deleteTr(this)'><a>删除</a></div>" +
            "</td></tr>";
        $("#table tr:last-child").after(trcomp);
    }

    function deleteTr(e){
        var tr = e.parentNode.parentNode;//获取tr节点对象
        var con = confirm("确定要删除该项属性吗？"); //在页面上弹出对话框
        if(con == true){
            tr.remove();
        }
    }
</script>
</html>
