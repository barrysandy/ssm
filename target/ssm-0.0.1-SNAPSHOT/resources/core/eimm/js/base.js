var P = {
    getSmallImage:(function (imagePath,width,height) {
        var imageSuffix = imagePath.substring(imagePath.lastIndexOf('.')+1);  //这样就获取到了最后的'ba'
        var imagePre = imagePath.substring(0,imagePath.lastIndexOf('.'));
        var smallImage = imagePre + "_"+width+"x"+height+"." + imageSuffix;
        return smallImage;
    }),
    previousPage: function () {
        var form = $("#listForm");
        var pageCount = $("#pageCount").val();
        var pageNumber = $("#pageNumber").val();
        if (1 == pageNumber) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg("亲，已经是第一页了！");
            });
        } else {
            var previousNumber = parseInt(pageNumber) && parseInt(pageNumber) - 1;
            $("#pageNumber").val(previousNumber);
            form.submit();
        }
    },
    nextPage: function () {
        var form = $("#listForm");
        var pageCount = $("#pageCount").val();
        var pageNumber = $("#pageNumber").val();
        if (pageCount == pageNumber) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg("亲，已经是最后一页了！");
            });
        } else {
            var nextNumber = parseInt(pageNumber) && parseInt(pageNumber) + 1;
            $("#pageNumber").val(nextNumber);
            form.submit();
        }
    },
    gotoPage: function (pageNumber) {

        $("#pageNumber").val(pageNumber);
        $("#listForm").submit();
    }
    ,
    gotoPage2: function () {
        var inPageNumber = $("#inPageNumber").val();
        $("#pageNumber").val(inPageNumber);
        $("#listForm").submit();
    }
    ,
    tripGotoPage: function (pageNumber, id) {
        id = document.getElementById(id).form.id;
        $("#pageNumber").val(pageNumber);
        $("#" + id).submit();
    }
    ,
    tripPreviousPage: function (id) {
        id = document.getElementById(id).form.id;
        var form = $("#" + id);
        var pageNumber = $("#pageNumber").val();
        if (1 == pageNumber) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg("亲，已经是第一页了！");
            });
        } else {
            var previousNumber = parseInt(pageNumber) && parseInt(pageNumber) - 1;
            $("#pageNumber").val(previousNumber);
            form.submit();
        }
    },
    tripNextPage: function (id) {
        id = document.getElementById(id).form.id;
        var form = $("#" + id);
        var pageNumber = $("#pageNumber").val();
        var pageCount = $("#pageCount").val();
        if (pageCount == pageNumber) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg("亲，已经是最后一页了！");
            });
        } else {
            var nextNumber = parseInt(pageNumber) && parseInt(pageNumber) + 1;
            $("#pageNumber").val(nextNumber);
            form.submit();
        }

    },
    pageSizeChange: function () {
        $("#listForm").submit();
    }
};
function msgSuccess() {
    $(document).an_dialog({
        id: 'msgSuccess',
        massage: {
            type: '成功',
            content: '操作成功！',
            callback: function (dialog, event) {
                alert(event.target.innerText);
            }
        }
    })
}
function msgFail() {
    $(document).an_dialog({
        id: 'msgFail',
        massage: {
            type: '失败',
            content: '操作失败！'
        }
    })
}


function words_deal(id, len) {
    var curLength = $("#" + id).val().length;
    var str = $("#" + id).val();
    var brNumArray = str.match(/[\r\n]/g);
    var brNum = 0;
    if (brNumArray != null) {
        brNum = brNumArray.length;
    }

    if (curLength + brNum > len) {
        var num = $("#" + id).val().substr(0, len);
        $("#" + id).val(num);
        $("#" + id + "_textCount").text("0");
        alert("超过字数限制，多出的字将被截断！");
    } else {
      //  $("#" + id + "_textCount").text(len - $("#" + id).val().length - brNum);
        $("#" + id + "_textCount").text($("#" + id).val().length);
    }
}


function sumitForm2(obj) {
    if (obj == '1') {
        $(".m-table-form").find("input").each(function (i) {
            $(this).val("");
        })
        $("#listForm").submit();
    } else {
        $("input[name='search']").val("");
        $("input[name='keyWord']").val("");
        $("#listForm").submit();
    }
}

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}
function words_sy() {
    $(".words_sy").each(function () {
        var len = $(this).attr("len");
        var str = $(this).text().replace(/[\r\n]/g, "").replace(/^\s\s*/, '').replace(/\s\s*$/, '');//要展示的字符串
        //var str = $(this).text();
        if (str.length > len) {
            str = str.substring(0, len) + "...";
            $(this).text(str);
        }
    })
}

function myAlert(msg) {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg(msg);
    });
}