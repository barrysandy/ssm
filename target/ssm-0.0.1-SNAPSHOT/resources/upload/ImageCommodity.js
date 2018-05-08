//上传文件JS
//url: 'http://www.daxi51.com/ssm_file/upload/upImage' ,
//url: 'http://118.24.118.23/ssm_file/upload/upImage' ,
function doUploadImage(){
    var formData = new FormData($( "#editForm" )[0]);
    var imgPath = $("#file").val();
    if (imgPath == "") {
        alert("请选择上传图片！");
        return;
    }
    //判断上传文件的后缀名
    var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
    if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'bmp' && strExtension != 'JPG' && strExtension != 'GIF' && strExtension != 'PNG' && strExtension != 'BMP') {
        alert("请选择图片文件");
        return;
    }
    $.ajax({
        url: 'http://www.daxi51.com/ssm_file/upload/upImage' ,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        dataType:'json',
        success: function (result) {
             //alert("success : " + result.toString());
             //上传完成返回的图片路径
             updateImg = result.url;
             material_id = result.material_id;
             //alert("updateImg : " + updateImg + " material_id : " + material_id );
             //获取提交表单的隐藏图片输入框
             var inputImg = document.getElementById("inputImg");

             //设置inputImg输入框的值为上传完成返回的图片路径
             var strValue = inputImg.getAttribute("value");
             strValue = strValue + material_id +",";
             /** 替换掉 ',,'  为 ','  */
             strValue = strValue.replace(',,', ',');
             inputImg.setAttribute("value", strValue);

             //添加 img
             //获取添加图片的div，id为showIma
             var div = document.getElementById("showIma");
             //创建img元素
             var img = document.createElement("img");
             //设置img元素
             img.setAttribute("src", updateImg);
             img.setAttribute("id", material_id);
             img.setAttribute("height", "120px;");
             //为img绑定点击事件
             img.onclick = objclick;
             //将img绑定到div中
             div.appendChild(img);

        },
        error: function (data) {
             alert("图片上传失败，服务器错误!");
        }
    });
}


/**
 * 图片点击事件
 * @param e
 */
function objclick(e){
    var img = e.target;
    var url =  img.getAttribute("id");
    var arrayImg = document.getElementById("inputImg");
    var con = confirm("确定要删除该图片吗？"); //在页面上弹出对话框
    if(con == true){
        var strValue = arrayImg.getAttribute("value");
        var newStrArr = "";
        strs = strValue.split(","); //字符分割
        for (i=0;i<strs.length ;i++ )
        {
            if(strs[i] == url){

            }else{
                newStrArr = newStrArr + strs[i]+",";
            }
        }
        /** 替换掉 ',,'  为 ','  */
        newStrArr = newStrArr.replace(',,', ',');
        if(newStrArr == ","){newStrArr = "";}
        img.parentNode.removeChild(img);
        arrayImg.setAttribute("value", newStrArr);
    }
};