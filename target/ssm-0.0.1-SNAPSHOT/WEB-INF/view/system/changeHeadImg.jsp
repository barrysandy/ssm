<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>修改头像</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path}/resources/js/ajaxfileupload.js"></script>
    <script src="${path}/resources/layui/layui.js"></script>
    <script src="${path}/resources/layui/layuiUtil.js"></script>
    <style>
        /*个人资料-我的信息*/
        .myinfo-right{width:612px;min-height:600px;padding:45px 200px;border:1px solid #eeeeee;background:#ffffff;border-radius:3px;}
        .myinfo-right .tx-upload{display:inline-block;*display:inline;*zoom:1;width:95px;text-align:center;height:25px;line-height:25px;border:1px solid #c6e3fc;color:#03a9f4;border-radius:2px;margin:10px 0;}
        .save-btn-ff6600 a{text-align:center;display:inline-block;*display:inline;*zoom:1;background:#ff6600;width:120px;height:36px;line-height:36px;color:#ffffff;}
        .myinfo-right img{margin-left: 45px;width: 120px;height: 120px;margin-bottom: 30px;}
    </style>
    <script>
        function chooseImg() {
            $.ajaxFileUpload({
                url: '${path}/upload/uploadImage.htm',
                fileElementId: 'headImgUp',
                dataType: "json",
                success: function (data) {
                    var result = data.result;
                    if(result.fileUrl!="" && result.fileUrl!="null") {
                        document.getElementById("img_head").src = result.fileUrl;
                        document.getElementById("headImg").value = result.fileId;
                    }
                },
                error: function (data) {
                    var result = JSON.parse(data.responseText).result;
                    if(result.fileUrl!="" && result.fileUrl!="null") {
                        document.getElementById("img_head").src = result.fileUrl;
                        document.getElementById("headImg").value = result.fileId;
                        LayuiUtil.msg("上传成功");
                    }
                }
            });
        }

        function saveHeadImg(){
            var userId = $('#id').val();
            var headImg = $('#inputImg').val();
            alert(headImg);
            var url = '${path}/upload/saveHeadImg';
            $.post(url,{"userId":userId,"headImg":headImg},function (data) {
                if (data.state){
                    LayuiUtil.msg("操作成功");
                    window.parent.location.reload();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else{
                    LayuiUtil.msg("操作失败");
                    window.parent.location.reload();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }
            })
        }
    </script>
</head>
<body style="overflow: hidden">
<form id="editForm" target="_self" method="post" class="myinfo-right">
    <input type="hidden" name="headImg" id="headImg" value="">
    <input type="hidden" name="id" id="id" value="${id}">
    <div>
        <div id="showIma">
            <img src="${headImg}" height="120px;" onclick="objclick2(event)" id="imgUrl">
        </div>
        <input id="file" type="file" name="file"/>
        <script type="text/javascript" src="${path }/resources/upload/Image.js">
            updateImg = "";//上传文件的返回url
            material_id = "";//上传文件的返回material_id
        </script>
        <input type="hidden" id="inputImg" class="u-input" name="image" value="${bean.headImg}">
        <br>
        <a href="javascript:void(0)" class="tx-upload" onclick="saveHeadImg()">保存退出</a>
        <div>支持jpg、png、bmp，图片大小5M以内</div>
    </div>
</form>
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
