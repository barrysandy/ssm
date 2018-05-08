//上传文件JS
function doUploadVoice(){
    var formData = new FormData($( "#editForm" )[0]);
    var path = $("#fileVoice").val();
    if (path == "") {
        alert("请选择上传语音，支持mp3/wma/wav/amr格式！");
        return;
    }
    //判断上传文件的后缀名
    var strExtension = path.substr(path.lastIndexOf('.') + 1);
    if (strExtension != 'mp3' && strExtension != 'wma'
        && strExtension != 'wav' && strExtension != 'amr') {
        alert("请选择语音文件");
        return;
    }
    $.ajax({
        url: 'http://www.daxi51.com/ssm_file/upload/upVoice' ,
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
             updateVoice = result.url;
             materialId = result.material_id;
             //alert("updateImg : " + updateImg + " material_id : " + material_id );
             //获取提交表单的隐藏图片输入框
             var inputImg = document.getElementById("inputImg");
             //设置inputImg输入框的值为上传完成返回的图片路径
             inputImg.setAttribute("value", materialId );
             var voice = document.getElementById("voice");
             voice.setAttribute("src", updateVoice);
        },
        error: function (data) {
             alert("语音上传失败，服务器错误!");
        }
    });
}