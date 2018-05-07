<!doctype html>
<html>
<head>
    <title>文章新增</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <style>
        .upload_img{height: 120px;}
    </style>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/art/saveOrUpdate.htm";
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
        <form id="editForm" action="${path}/art/saveOrUpdate.htm" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuId}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">文章</td>
                    </tr>
                    <tr>
                        <td class="table-header">标题<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="artTitle" value="${bean.artTitle}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">内容<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="artSummary" value="${bean.artSummary}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">封面<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div id="showIma">
                                <img src="${bean.artImg}" onclick="objclick2(event)" class="upload_img">
                            </div>
                            <input id="file" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Image.js">
                                updateImg = "";//上传文件的返回url
                                material_id = "";//上传文件的返回material_id
                            </script>
                            <input type="hidden" id="inputImg" class="u-input" name="artImg" value="${bean.artImg}">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">链接<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="artUrl" value="${bean.artUrl}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">第几期<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="currentPeriod" value="${bean.currentPeriod}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">本期的位置<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="currentPeriodSort" value="${bean.currentPeriodSort}"></td>
                    </tr>
                    <tr>
                        <td class="table-header">其他描述<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}"></td>
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
