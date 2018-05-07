//上传文件JS
function doUploadThumb(){
    var formData = new FormData($( "#editForm" )[0]);
    var imgPath = $("#fileThumb").val();
    if (imgPath == "") {
        alert("请选择上传图片！");
        return;
    }
    //判断上传文件的后缀名
    var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
    if (strExtension != 'jpg') {
        alert("请选择图片文件,支持的格式jpg");
        return;
    }
    $.ajax({
        url: 'http://www.daxi51.com/ssm_file/upload/upThumb' ,
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
            updateThumb = result.url;
            material = result.material_id;
            //alert("updateImg : " + updateImg + " material_id : " + material_id );
            //获取提交表单的隐藏图片输入框
            var inputImg = document.getElementById("inputImg");
            //设置inputImg输入框的值为上传完成返回的图片路径
            inputImg.setAttribute("value", material );

            //添加 img
            //获取添加图片的div，id为showIma
            var div = document.getElementById("showThumb");
            $("#showIma").empty();//移除div的元素
            //创建img元素
            var img = document.createElement("img");
            //设置img元素
            img.setAttribute("src", updateThumb);
            img.setAttribute("class", "upimageClass upimageClick");
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