//上传文件JS
//url: 'http://www.daxi51.com/ssm_file/upload/upImage' ,
//url: 'http://118.24.118.23/ssm_file/upload/upImage' ,
function doUploadFile(id,path){
    var formData = new FormData($( "#editForm" )[0]);
    var filePath = $("#file").val();
    if (filePath == "") {
        layuiUtilMsg("请选择上传的文件！");
        return;
    }
    //判断上传文件的后缀名
    var strExtension = filePath.substr(filePath.lastIndexOf('.') + 1);
    if (strExtension != 'xlsx' && strExtension != 'XLSX') {
        layuiUtilMsg("请选择Excel格式【.xlsx】后缀的文件");
        return;
    }
    $.ajax({
        url: 'http://www.daxi51.com/ssm_file/upload/upFileExcle' ,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        dataType:'json',
        success: function (result) {
             // updateImg = result.url;
             material_id = result.material_id;
             //alert("updateImg : " + updateImg + " material_id : " + material_id );
             //获取提交表单的隐藏图片输入框
             var inputFile = document.getElementById("inputFile");
             //设置inputImg输入框的值为上传完成返回的图片路径
            inputFile.setAttribute("value", material_id );
            $.get(path +"/meeting/updateExcel",{'id':id ,'excelPath':material_id },function(data){
                if(data == "0"){
                    layuiUtilMsg("应用的文件失败！");
                }
                if(data == "1"){
                    layuiUtilMsg("应用的文件成功！");
                }
            });
        },
        error: function (data) {
            layuiUtilMsg("文件上传失败，服务器错误! data = " + data);
        }
    });
}