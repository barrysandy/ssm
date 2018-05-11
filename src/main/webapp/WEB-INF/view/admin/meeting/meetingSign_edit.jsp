<!doctype html>
<html>
<head>
    <title>参会人员编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/meetingSign/saveOrUpdate";
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
        <form id="editForm" action="${path}/meetingSign/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}"/>
                <input type="hidden" name="signCode" value="${bean.signCode}"/>
                <input type="hidden" name="status" value="${bean.status}"/>
                <input type="hidden" name="createTime" value="${bean.createTime}"/>
                <input type="hidden" name="updateTime" value="${bean.updateTime}"/>
                <input type="hidden" name="meetingId" value="${bean.meetingId}"/>
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">参会人员编辑</td>
                    </tr>
                    <tr>
                        <td class="table-header">姓名<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="name" value="${bean.name}" maxlength="12"></td>
                    </tr>

                    <tr id="showImaList">
                        <td class="table-header"><span >电话</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="phone" value="${bean.phone}" maxlength="11"></td>
                    </td>
                    </tr>
                    <tr >
                        <td class="table-header">机构/公司<span style="color: red">*</span></td>
                        <td colspan="7">
                            <input type="text" class="u-input" name="company" value="${bean.company}" maxlength="50">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">类型<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="personType" value="${bean.personType}" maxlength="11" placeholder="VIP  政府代表  风景区代表  涉旅企业代表  个人  媒体"></td>
                    </tr>
                    <tr >
                        <td class="table-header">职位<span style="color: red">*</span></td>
                        <td colspan="7">
                            <input type="text" class="u-input" name="position" value="${bean.position}" maxlength="20">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">是否参加晚宴<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select id="joinDinner" name="joinDinner" class="u-input">
                                <option value="0" <c:if test="${bean.joinDinner == 0}">selected="selected"</c:if> >不参加</option>
                                <option value="1" <c:if test="${bean.joinDinner == 1}">selected="selected"</c:if> >参加</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">详情<span style="color: red">*</span></td>
                        <td colspan="7">
                            <textarea id="descM" name="descM" type="text/plain" class="text-word" style="width:99%;height:300px;">${bean.descM }</textarea>
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
<!--=========编辑器插件=========-->
<script type="text/javascript" charset="utf-8" src="${path}/resources/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path}/resources/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${path}/resources/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('descM');

    function isFocus(e){
        alert(UE.getEditor('descM').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('descM').blur();
        UE.dom.domUtils.preventDefault(e)
    }
</script>
</html>
