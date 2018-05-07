<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
    <title>微信问答题编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <style>
        .divleft{
            float:left;width: auto;max-height:60px;
        }
        .divright{
            float:right;min-width: 30px;line-height:30px;max-height:60px;background-color: #eeeeee;
        }
    </style>
    <script src="${path}/resources/js/plugins/layer/layer.min.js"></script>

    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/wechatActivityTestQuestions/saveOrUpdate.htm";
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
        <form id="editForm" action="${path}/wechatActivityTestQuestions/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <table class="m-table-forms inline" id="table">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">问答题编辑</td>
                    </tr>
                    <tr>
                        <td class="table-header">题目<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="title" id="title" value="${bean.title}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >描述</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >排序</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="sort" value="${bean.sort}"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >正确选项</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="answerId" value="${bean.answerId}" readonly="readonly"></td>
                    </tr>
                </table>

                <table class="m-table-forms inline" id="table2">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">编辑答案</td>
                    </tr>
                    <tr>
                        <c:forEach items="${bean.listWechatActivityAnswer }" var="listbean">
                        <td class="table-header">编辑答案<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div class="divleft">
                                答案：<input type="text" name="setoptions" placeholder='答案' value="${listbean.options}">
                                是否正确：
                                <select name="setrequired">
                                    <option value="-1" <c:if test="${listbean.descM == '-1'}">selected="selected"</c:if> >错误</option>
                                    <option value="1" <c:if test="${listbean.descM == '1'}">selected="selected"</c:if> >正确</option>
                                </select>
                            </div>
                            <div class="divright" align="center"onclick='deleteTr(this)'><a>删除</a></div>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                <button class="u-btn sm success" type="button" onClick="addNew()">
                    <i class="iconfont">&#xe61f;</i> 添加新答案
                </button>
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

<script type="text/javascript">

    /**
     * 添加新属性
     * */
    function addNew(){
        var trcomp="<tr><td class='table-header'><span >编辑答案</span><span style='color: red'>*</span></td><td colspan='7'>" +
            "<div class='divleft'>答案：<input type='text' name='setoptions' placeholder='答案'></div>" +
            "是否正确：<select name='setcorrect'><option value='-1'>错误</option><option value='1'>正确</option></select>" +
            "<div class='divright' align='center'onclick='deleteTr(this)'><a>删除</a></div>" +
            "</td></tr>";
        $("#table2 tr:last-child").after(trcomp);
    }

    /**
     * 删除属性
     */
    function deleteTr(e){
        var tr = e.parentNode.parentNode;//获取tr节点对象
        var con = confirm("确定要删除该项属性吗？"); //在页面上弹出对话框
        if(con == true){
            tr.remove();
        }
    }


</script>

</body>
</html>
