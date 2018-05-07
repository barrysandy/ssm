<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!doctype html>
<html>
<head>
    <title>微信活动编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <style>
        .divleft{
            float:left;width: auto;max-height:140px;
        }
        .divright{
            float:right;min-width: 60px;line-height:140px;max-height:140px;background-color: #eeeeee;
        }
        .divleft2{
            float:left;width: auto;max-height:35px;
        }
        .divright2{
            float:right;min-width: 60px;line-height:35px;max-height:35px;background-color: #eeeeee;
        }
        .upload_img{
            width: 100px;
        }
    </style>
    <script src="${path}/resources/js/plugins/layer/layer.min.js"></script>

    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/activity/saveOrUpdate.htm";
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
        <form id="editForm" action="${path}/activity/saveOrUpdate.htm" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="timeStatus" value="${bean.timeStatus}">
                <input type="hidden" name="menuId" value="${menuid}">
                <input type="hidden" name="parentMenuId" value="${parentMenuId}">
                <table class="m-table-forms inline" id="table">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">活动编辑</td>
                    </tr>
                    <tr>
                        <td class="table-header">活动标题<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="title" id="title" value="${bean.title}" placeholder="活动标题" maxlength="255"></td>
                    </tr>
                        <tr>
                            <td class="table-header"><span >活动描述</span><span style="color: red">*</span></td>
                            <td colspan="7"><input type="text" class="u-input" name="descM" value="${bean.descM}" placeholder="活动描述信息" maxlength="255"></td>
                        </tr>

                    <tr <c:if test="${bean.types eq 'signSession'}">style="display: none;" </c:if>>
                        <td class="table-header"><span >活动链接</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="url" value="${bean.url}" placeholder="活动链接由选择的活动配置项来自动生成，一般不需要运营者填写。" maxlength="255"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >配置新页面</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="returnPage" value="${bean.returnPage}" placeholder="如果填写了该项，会强制进入填写的页面，需要填写返回的view下的完整路径。" maxlength="255"></td>
                    </tr>
                    <tr>
                        <td class="table-header">活动类型<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="types" class="u-input">
                                <option value="sign" <c:if test="${bean.types eq 'sign'}">selected="selected"</c:if> >报名抽奖类型活动</option>
                                <option value="signSession" <c:if test="${bean.types eq 'signSession'}">selected="selected"</c:if> >公众号会话报名类型活动</option>
                                <option value="questionnaire" <c:if test="${bean.types eq 'questionnaire'}">selected="selected"</c:if> >问卷类型活动（暂未上线）</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">所属公众号<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="bindingWechatId" class="u-input">
                                <c:forEach items="${paInfoList }" var="paInfobean">
                                    <option value="${paInfobean.id}" <c:if test="${bean.bindingWechatId == paInfobean.id}">selected="selected"</c:if> >${paInfobean.accountName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">分享设置<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="share" class="u-input" id="share">
                                <option value="1" <c:if test="${bean.share == 1}">selected="selected"</c:if> >开启</option>
                                <option value="-1" <c:if test="${bean.share == -1}">selected="selected"</c:if> >关闭</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="shareTitle">
                        <td class="table-header"><span >分享标题</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="shareTitle" value="${bean.shareTitle}" placeholder="配置页面分享的标题" maxlength="255"></td>
                    </tr>
                    <tr id="shareDescM">
                        <td class="table-header"><span >分享描述</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="shareDescM" value="${bean.shareDescM}" placeholder="配置页面分享的描述信息" maxlength="255"></td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >分享图片</span><span style="color: red">*</span></td>
                        <td colspan="7">
                            <div id="showIma">
                                <img src="${image}" width="100px;" onclick="objclick2(event)">
                            </div>
                            <input id="file" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/Image.js">
                                updateImg = "";//上传文件的返回url
                                material_id = "";//上传文件的返回material_id
                            </script>
                            <input type="text" id="inputImg" class="u-input" name="shareImage" value="${bean.shareImage}">
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">授权设置<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="authorised" class="u-input" id="authorised">
                                <option value="-1" <c:if test="${bean.authorised == -1}">selected="selected"</c:if> >不支持授权登录 -（默认不支持）</option>
                                <option value="1" <c:if test="${bean.authorised == 1}">selected="selected"</c:if> >支持微信授权登录 -（必须为服务号才能开启此功能）</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="trSupportGetWechatMsg">
                        <td class="table-header">活动授权后增加配置属性<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="supportGetWechatMsg" class="u-input" id="supportGetWechatMsg">
                                <option value="-1" <c:if test="${bean.authorised == -1}">selected="selected"</c:if> >不支持 -（默认不支持）</option>
                                <option value="1" <c:if test="${bean.authorised == 1}">selected="selected"</c:if> >支持 -（开启此功能后会默认向报名项中添加头像，昵称，openid信息）</option>
                            </select>
                        </td>
                    </tr>
                    <tr <c:if test="${bean.types eq 'signSession'}">style="display: none;" </c:if>>
                        <td class="table-header">是否需要关注订阅号才能报名<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="subscribeWechatId" class="u-input" id="subscribeWechatId">
                                <option value="-1">不需要关注</option>
                                <c:forEach items="${paInfoList }" var="paInfobean">
                                    <option value="${paInfobean.id}" <c:if test="${bean.subscribeWechatId == paInfobean.id}">selected="selected"</c:if> >${paInfobean.accountName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">是否开启抽奖<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="prizes" class="u-input" id="prizes">
                                <option value="-1" <c:if test="${bean.prizes == -1}">selected="selected"</c:if> >关闭</option>
                                <option value="1" <c:if test="${bean.prizes == 1}">selected="selected"</c:if> >开启</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">抽奖类型<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="prizesType" class="u-input" id="prizesType">
                                <option value="auto" <c:if test="${bean.prizesType == 'auto'}">selected="selected"</c:if> >默认设置 -（当前只支持后台抽奖）</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-header">报名时间<span style="color: red">*</span></td>
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
                    <tr>
                        <td class="table-header">活动启用设置<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="status" class="u-input" id="status">
                                <option value="-1" <c:if test="${bean.status == -1}">selected="selected"</c:if> >冻结活动</option>
                                <option value="1" <c:if test="${bean.status == 1}">selected="selected"</c:if> >开启活动</option>
                            </select>
                        </td>
                    </tr>

                    <!-- 报名动态属性配置 -->
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">报名动态属性配置</td>
                    </tr>
                    <tr>
                        <c:forEach items="${listSet }" var="listbean">
                            <td class="table-header">属性<span style="color: red">*</span></td>
                            <td colspan="7">
                                <div class="divleft">
                                    中文名称：<input type="text" name="setchineseCharacterName" placeholder='属性的中文名称' value="${listbean.chineseCharacterName}">
                                    英文名称：<input type="text" name="setname" placeholder='属性名称，必须为英文' value="${listbean.name}">
                                    默认占位符：<input type="text" name="setnamePlaceholder" placeholder='默认显示内容' value="${listbean.namePlaceholder}"><br/>
                                    属性类型：
                                    <select name="settypese">
                                        <option value="input" <c:if test="${listbean.typese == 'input'}">selected="selected"</c:if> >输入框</option>
                                        <option value="radio" <c:if test="${listbean.typese == 'radio'}">selected="selected"</c:if> >单选框</option>
                                        <option value="checkBox" <c:if test="${listbean.typese == 'checkBox'}">selected="selected"</c:if> >复选框</option>
                                        <option value="textArea" <c:if test="${listbean.typese == 'textArea'}">selected="selected"</c:if> >文本框</option>
                                    </select>
                                    是否必须：
                                    <select name="setrequired">
                                        <option value="-1" <c:if test="${listbean.required == '-1'}">selected="selected"</c:if> >非必须</option>
                                        <option value="1" <c:if test="${listbean.required == '1'}">selected="selected"</c:if> >必须</option>
                                    </select>
                                    属性验证类型：
                                    <select name="setverificationType">
                                        <option value="-1" <c:if test="${listbean.verificationType == '-1'}">selected="selected"</c:if> >不需要验证</option>
                                        <option value="phone" <c:if test="${listbean.verificationType == 'phone'}">selected="selected"</c:if> >手机号码</option>
                                        <option value="email" <c:if test="${listbean.verificationType == 'email'}">selected="selected"</c:if> >email邮箱</option>
                                    </select>
                                    属性是否隐藏：
                                    <select name="sethide">
                                        <option value="-1" <c:if test="${listbean.hide == '-1'}">selected="selected"</c:if> >不隐藏</option>
                                        <option value="1" <c:if test="${listbean.hide == '1'}">selected="selected"</c:if> >隐藏</option>
                                    </select>
                                    是否验证重复：${listbean.repeat}
                                    <select name="setrepeat">
                                        <option value="1"  <c:if test="${listbean.repeat == '1'}">selected="selected"</c:if> >不允许重复</option>
                                        <option value="-1" <c:if test="${listbean.repeat == '-1'}">selected="selected"</c:if> >忽略验证重复</option>
                                    </select>
                                    <br><textarea name="setdescM" placeholder='属性描述信息'>${listbean.descM}</textarea>
                                    属性排序：<input type="text" name="setsort" placeholder='数字越小，越排前面。' value="${listbean.sort}">
                                    <input type="hidden" name="setsetType" value="${listbean.setType}">
                                </div>
                                <div class="divright" align="center"onclick='deleteTr(this)'><a>删除</a></div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <button class="u-btn sm success" type="button" onClick="addNewAttribute()">
                    <i class="iconfont">&#xe61f;</i> 添加新报名配置
                </button>

                <table class="m-table-forms inline" id="table2">
                    <!-- 活动奖品配置 -->
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">活动奖品配置</td>
                    </tr>
                    <tr>
                        <c:forEach items="${bean.listPrize }" var="list">
                        <td class="table-header">奖品<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div class="divleft2">
                                选择奖品：
                                <select name="prizeid">
                                    <c:forEach items="${listPrize }" var="allList">
                                        <option value="${allList.id}" <c:if test="${list.id == allList.id}">selected="selected"</c:if> >${allList.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="divright2" align="center"onclick='deletePrize(this)'><a>删除</a></div>
                        </td>
                    </tr>
                    </c:forEach>

                </table>
                <button class="u-btn sm success" type="button" onClick="addNewPrize()">
                    <i class="iconfont">&#xe61f;</i> 添加奖品
                </button>
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
<!-- 奖品选择的option -->
<input type="hidden" id="prizeSelet" value="${prizeSelet}"/>
</body>
<script type="text/javascript">

    /**
     * 添加新属性
     * */
    function addNewAttribute(){
        var wechatActivityId = $("#wechatActivityId").val();
        var trcomp="<tr><td class='table-header'><span >属性</span><span style='color: red'>*</span></td><td colspan='7'>" +
            "<div class='divleft'>中文名称：<input type='text' name='setchineseCharacterName' placeholder='属性的中文名称'>英文名称：<input type='text' name='setname' placeholder='属性名称，必须为英文'>默认占位符：<input type='text' name='setnamePlaceholder' placeholder='默认显示内容'><br/>" +
            "属性类型：<select name='settypese'><option value='input'>输入框</option><option value='radio'>单选框</option><option value='checkBox'>复选框</option><option value='textArea'>文本框</option></select>" +
            "是否必须：<select name='setrequired'><option value='-1'>非必须</option><option value='1'>必须</option></select>" +
            "属性验证类型：<select name='setverificationType'><option value='-1'>不需要验证</option><option value='phone'>手机号码</option><option value='email'>email邮箱</option></select>" +
            "属性是否隐藏：<select name='sethide'><option value='-1'>不隐藏</option><option value='1'>隐藏</option></select>" +
            "是否验证重复：<select name='setrepeat'><option value='1'>不允许重复</option><option value='-1'>忽略验证重复</option></select>" +
            "<br><textarea name='setdescM' placeholder='属性描述信息'></textarea>" +
            "属性排序：<input type='text' name='setsort' placeholder='数字越小，越排前面。' value='999'>" +
            "<input type='hidden' name='setsetType' value='0'></div>" +
            "<div class='divright' align='center'onclick='deleteTr(this)'><a>删除</a></div>" +
            "</td></tr>";
        $("#table tr:last-child").after(trcomp);
    }

    /**
     * 删除属性
     */
    function deleteTr(e){
        var tr = e.parentNode.parentNode;//获取tr节点对象
        var con = confirm("确定要删除该项属性吗？"); //在页面上弹出对话框
        if(con == true){
            tr.remove();
        }
    }

    /**
     * 删除属性
     */
    function deletePrize(e){
        var tr = e.parentNode.parentNode;//获取tr节点对象
        var con = confirm("确定要删除该项奖品吗？"); //在页面上弹出对话框
        if(con == true){
            tr.remove();
        }
    }

    /**
     * 添加奖品
     * */
    function addNewPrize(){
        var prizeSelet = $("#prizeSelet").val();
        var trcomp="<tr><td class='table-header'><span >奖品</span><span style='color: red'>*</span></td><td colspan='7'>" +
            "<div class='divleft2'>选择奖品：<select name='prizeid'>"+prizeSelet+"</select></div>" +
            "<div class='divright2' align='center'onclick='deletePrize(this)'><a>删除</a></div>" +
            "</td></tr>";
        $("#table2 tr:last-child").after(trcomp);
    }


    /**
     * 初始化隐藏和显示选择的类型
     */
    $(function(){
        heidShare();
        if($("#share").val() == "1" ){
            $("#shareTitle").show();
            $("#shareDescM").show();
            $("#shareImage").show();
        }
    });

    /**
     * select选择的类型
     */
    $("select#share").change(function(){
        heidShare();
        if($("#share").val() == "1" ){
            $("#shareTitle").show();
            $("#shareDescM").show();
            $("#shareImage").show();
        }
    });

    /**
     * 隐藏分享的元素tr
     */
    function heidShare(){
        $("#shareTitle").hide();
        $("#shareDescM").hide();
        $("#shareImage").hide();
    }


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
