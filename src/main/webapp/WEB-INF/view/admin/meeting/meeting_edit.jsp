<!doctype html>
<html>
<head>
    <title>会议编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/meeting/saveOrUpdate";
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
        <form id="editForm" action="${path}/meeting/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}"/>
                <input type="hidden" name="status" value="${bean.status}"/>
                <input type="hidden" name="createTime" value="${bean.createTime}"/>
                <input type="hidden" name="updateTime" value="${bean.updateTime}"/>
                <input type="hidden" name="signTotal" value="${bean.signTotal}"/>
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">会议</td>
                    </tr>
                    <tr>
                        <td class="table-header">会议标题<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="title" value="${bean.title}" maxlength="120"></td>
                    </tr>
                    <tr>
                        <td class="table-header">会议时间<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div class="calendarWarp">
                                <input class='ECalendar text-word' style="width:200px;" type="text" id="ECalendar_date"  name="beginTime"  value="${bean.beginTime }"/>
                            </div>
                            <script type="text/javascript">
                                $(function(){
                                    $("#ECalendar_date").ECalendar({
                                        type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
                                        stamp : false,   //是否转成时间戳，默认true;
                                        offset:[0,2],   //弹框手动偏移量;
                                        format:"yyyy-mm-dd hh:ii",   //时间格式 默认 yyyy-mm-dd hh:ii;
                                        skin:3,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
                                        step:10,   //选择时间分钟的精确度;
                                        callback:function(v,e){} //回调函数
                                    });
                                })
                            </script>
                            -
                            <div class="calendarWarp">
                                <input class='ECalendar text-word' style="width:200px;" type="text" id="ECalendar_date2"  name="endTime"  value="${bean.endTime }"/>
                            </div>
                            <script type="text/javascript">
                                $(function(){
                                    $("#ECalendar_date2").ECalendar({
                                        type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
                                        stamp : false,   //是否转成时间戳，默认true;
                                        offset:[0,2],   //弹框手动偏移量;
                                        format:"yyyy-mm-dd hh:ii",   //时间格式 默认 yyyy-mm-dd hh:ii;
                                        skin:3,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
                                        step:10,   //选择时间分钟的精确度;
                                        callback:function(v,e){} //回调函数
                                    });
                                })
                            </script>
                        </td>
                    </tr>
                    <tr id="showImaList">
                        <td class="table-header"><span >会议封面图片</span><span style="color: red">*</span></td>
                        <td colspan="7">
                        <div id="showIma">
                            <img src="${image}" height="120px;" onclick="objclick2(event)">
                        </div>
                        <input id="file" type="file" name="file"/>
                        <script type="text/javascript" src="${path }/resources/upload/Image.js">
                            updateImg = "";//上传文件的返回url
                            material_id = "";//上传文件的返回material_id
                        </script>
                        <input type="text" class="u-input" id="inputImg"  name="image" value="${bean.image}" readonly="readonly">
                    </td>
                    </tr>
                    <tr >
                        <td class="table-header">会议联系人<span style="color: red">*</span></td>
                        <td colspan="7">
                            <input type="text" class="u-input" name="name" value="${bean.name}" maxlength="20">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">会议联系人电话<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="phone" value="${bean.phone}" maxlength="11"></td>
                    </tr>
                    <tr>
                        <td class="table-header">会议地址<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="address" value="${bean.address}" maxlength="200"></td>
                    </tr>

                    <tr>
                        <td class="table-header">是否启用<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select id="status" name="status" class="u-input">
                                <option value="0" <c:if test="${bean.status == 0}">selected="selected"</c:if> >不启用</option>
                                <option value="1" <c:if test="${bean.status == 1}">selected="selected"</c:if> >启用</option>
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
<script type="text/javascript">


    $("input[type=file]").change(function(evenet){
        var inputFile = evenet.target;
        var id = inputFile.getAttribute("id");
        doUploadImage();
    })//选择文件后自动上传

    function objclick2(event){
        var img = event.target;
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
    }

</script>
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
