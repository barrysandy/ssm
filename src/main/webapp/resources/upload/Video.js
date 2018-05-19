//上传文件JS
function doUploadVideo(){
    var formData = new FormData($( "#editForm" )[0]);
    var path = $("#file").val();
    if (path == "") {
        alert("请选择上传视频！");
        return;
    }
    //判断上传文件的后缀名mp4
    var strExtension = path.substr(path.lastIndexOf('.') + 1);
    if (strExtension != 'mp4') {
        alert("请选择视频文件，支持mp4格式");
        return;
    }
    $.ajax({
        url: 'http://www.daxi51.com/ssm_file/upload/upVideo' ,
        // url: 'http://120.55.49.68:30000/ssm_file/upload/upVideo' ,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        dataType:'json',
        success: function (result) {
            //alert("success : " + result.toString());
            //上传完成返回的资源路径
            updateVideo = result.url;
            material_id = result.material_id;
            //获取提交表单的隐藏图片输入框
            var inputImg = document.getElementById("inputImg");
            //设置inputImg输入框的值为上传完成返回的图片路径
            inputImg.setAttribute("value", material_id);

            var video = document.getElementById("video");
            video.setAttribute("src", updateVideo);
        },
        error: function (data) {
            alert("视频上传失败，服务器错误!");
        }
    });
}