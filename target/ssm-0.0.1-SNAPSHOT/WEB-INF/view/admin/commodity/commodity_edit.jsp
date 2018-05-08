<!doctype html>
<html>
<head>
    <title>商品编辑</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <!-- 日历组件 URL(/public/calendar12.24)-->
    <link rel="stylesheet" href="${path}/resources/calendar12.24/css/style.css" />
    <script src="${path}/resources/calendar12.24/js/jQuery-2.1.4.min.js"></script>
    <script src="${path}/resources/calendar12.24/js/Ecalendar.jquery.min.js"></script>
    <script>
        function saveOrUpdate() {
            var url = "${path}/commodity/saveOrUpdate";
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
        <form id="editForm" action="${path}/commodity/saveOrUpdate" method="post">
            <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
                <input type="hidden" name="id" value="${bean.id}">
                <input type="hidden" name="menuId" value="${menuId}">
                <input type="hidden" name="timeStatus" value="${bean.timeStatus}">
                <input type="hidden" name="videoMaterialId" value="${bean.videoMaterialId}">
                <input type="hidden" name="sellerId" value="${bean.sellerId}">
                <table class="m-table-forms inline">
                    <tr>
                        <td align="center" colspan="8" class="f-b-n" style="font-size:18px">商品</td>
                    </tr>
                    <tr>
                        <td class="table-header">名称(不能超过25个字符)<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="commodityName" value="${bean.commodityName}" maxlength="25"></td>
                    </tr>
                    <tr>
                        <td class="table-header">商品有效时间<span style="color: red">*</span></td>
                        <td colspan="7">
                            <div class="calendarWarp">
                                <input class='ECalendar text-word' style="width:200px;" type="text" id="ECalendar_date"  name="createDate"  value="${bean.createDate }"/>
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
                                <input class='ECalendar text-word' style="width:200px;" type="text" id="ECalendar_date2"  name="invalidDate"  value="${bean.invalidDate }"/>
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
                        <td class="table-header">商品类型（类型不能更改）<span style="color: red">*</span></td>
                        <td colspan="7">
                            <c:if test="${bean.id == 0}">
                                <select id="typese" name="typese" class="u-input" >
                                    <option value="0" <c:if test="${bean.typese == 0 }">selected="selected"</c:if> >报名（将与活动建立维护，具体属性配置请到活动管理进行配置）</option>
                                    <option value="3" <c:if test="${bean.typese == 3 }">selected="selected"</c:if> >公众号会话报名（将与活动建立维护，具体属性配置请到活动管理进行配置）</option>
                                    <option value="1" <c:if test="${bean.typese == 1 }">selected="selected"</c:if> >单一购买商品</option>
                                    <option value="2" <c:if test="${bean.typese == 2 }">selected="selected"</c:if> >组团商品</option>
                                </select>
                            </c:if>
                            <c:if test="${bean.id != 0}">
                                <c:if test="${bean.typese == 3 }">公众号会话报名：</c:if>
                                <c:if test="${bean.typese == 0 }">报名类型商品：</c:if>
                                <c:if test="${bean.typese == 1 }">单一购买商品：</c:if>
                                <c:if test="${bean.typese == 2 }">组团商品：</c:if>
                                <input id="typese" type="hidden" name="typese" value="${bean.typese}">
                            </c:if>

                        </td>
                    </tr>
                    <tr>
                        <td class="table-header"><span >活动关联</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="wechatActivityId" value="${bean.wechatActivityId}" placeholder="该项在保存后自动补全，不可更改。" maxlength="255" readonly="readonly"></td>
                    </tr>
                    <tr>
                        <td class="table-header">列表显示设置<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="showList" class="u-input" id="showList">
                                <option value="1" <c:if test="${bean.showList == 1}">selected="selected"</c:if> >商品在列表显示</option>
                                <option value="0" <c:if test="${bean.showList == 0}">selected="selected"</c:if> >商品在列表隐藏</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="shareSet">
                        <td class="table-header">分享设置<span style="color: red">*</span></td>
                        <td colspan="7">
                            <select name="share" class="u-input" id="share">
                                <option value="1" <c:if test="${bean.share == 1}">selected="selected"</c:if> >开启</option>
                                <option value="0" <c:if test="${bean.share == 0}">selected="selected"</c:if> >关闭</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="shareTitle">
                        <td class="table-header"><span >分享标题(不能超过25个字符)</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="shareTitle" value="${bean.shareTitle}" placeholder="配置页面分享的标题" maxlength="25"></td>
                    </tr>
                    <tr id="shareDescM">
                        <td class="table-header"><span >分享描述(不能超过50个字符)</span><span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="shareDescM" value="${bean.shareDescM}" placeholder="配置页面分享的描述信息" maxlength="50"></td>
                    </tr>
                    <tr id="showImaList">
                        <td class="table-header"><span >商品图片（第一张默认为封面,也是分享的图片）</span><span style="color: red">*</span></td>
                        <td colspan="7">
                                <div id="showIma">
                                    <c:forEach items="${bean.resource }" var="img">
                                        <img src="${img.url}" height="120px;" onclick="objclick2(event)" id="${img.materialId}">
                                    </c:forEach>
                                </div>
                            <input id="file" type="file" name="file"/>
                            <script type="text/javascript" src="${path }/resources/upload/ImageCommodity.js">
                                updateImg = "";//上传文件的返回url
                                material_id = "";//上传文件的返回material_id
                            </script>
                            <input type="text" id="inputImg" class="u-input" name="arrayImg" value="${bean.arrayImg}">
                        </td>
                    </tr>
                    <tr id="commodityState">
                        <td class="table-header">库存<span style="color: red">*</span></td>
                        <td colspan="7">
                            <input type="text" class="u-input" name="commodityStock" value="${bean.commodityStock}">
                            <input type="hidden" name="commodityState" value="${bean.commodityState}">
                        </td>
                    </tr>
                    <tr id="commodityPrice">
                        <td class="table-header">价格<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="commodityPrice" value="${bean.commodityPrice}"></td>
                    </tr>
                    <tr id="maxNumber">
                        <td class="table-header" title="设置本商品最多能购买的成人票数量，不限制请填写-1或者一个大数字，到达该值，将禁止购买">商品购买成人票上限<span style="color: red">***</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="maxNumber" value="${bean.maxNumber}" placeholder="不限制请填写-1" maxlength="8"></td>
                    </tr>
                    <tr id="maxNumber2">
                        <td class="table-header" title="设置本商品最多能购买的儿童票数量，不限制请填写-1或者一个大数字，到达该值，将禁止购买">商品购买儿童票上限<span style="color: red">***</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="maxNumber2" value="${bean.maxNumber2}" placeholder="不限制请填写-1" maxlength="8"></td>
                    </tr>
                    <tr id="groupNumber">
                        <td class="table-header" title="设置本商品成人票达到的值，群发组团成功短信，不限制请填写-1，开启此值，需要为该商品绑定成团群发短信模板">商品成团成人人数<span style="color: red">***</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="groupNumber" value="${bean.groupNumber}" placeholder="不限制请填写-1" maxlength="8"></td>
                    </tr>
                    <tr id="groupNumber2" title="设置本商品儿童票达到的值，群发组团成功短信，不限制请填写-1，开启此值，需要为该商品绑定成团群发短信模板">
                        <td class="table-header">商品成团儿童人数<span style="color: red">***</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="groupNumber2" value="${bean.groupNumber2}" placeholder="不限制请填写-1" maxlength="8"></td>
                    </tr>
                    <tr id="createGroupNumber" title="设置组团商品一个团创建的人数，到达该值，该本就成功组建，并群发提醒短信到每一个队员，不限制请填写-1">
                        <td class="table-header">成团人数<span style="color: red">***</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="createGroupNumber" value="${bean.createGroupNumber}" placeholder="不限制请填写-1" maxlength="8"></td>
                    </tr>
                    <tr id="maxGroup">
                        <td class="table-header"title="设置组团商品最多创建的团数量，到达该值，将禁止购买，禁止创团，禁止参团，不限制请填写-1">最多允许组团数<span style="color: red">***</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="maxGroup" value="${bean.maxGroup}" placeholder="不限制请填写-1" maxlength="8"></td>
                    </tr>
                    <tr class="url">
                        <td class="table-header">商品详情页面<span style="color: red">*</span></td>
                        <td colspan="7"><input type="text" class="u-input" name="url" value="${bean.url}" placeholder="不填写默认跳转，商品默认页面"></td>
                    </tr>
                    <tr class="url">
                        <td class="table-header">商品详情页面支持页面：<span style="color: red">*</span></td>
                        <td colspan="7">
                            默认：mobile/mall/activityCommodityDetails<br>
                            新版：mobile/mall/activityCommodityDetails2<br>
                        </td>
                    </tr>
                    <tr class="commodityDetails">
                        <td class="table-header">编辑器文字替换功能<span style="color: red">*</span></td>
                        <td colspan="7">user_center_icon：用户中心的图标</td>
                    </tr>
                    <tr class="commodityDetails">
                        <td class="table-header">商品详情<span style="color: red">*</span></td>
                        <td colspan="7">
                            <textarea id="commodityDetails" name="commodityDetails" type="text/plain" class="text-word" style="width:99%;height:300px;">${bean.commodityDetails }</textarea>
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


    /**
     * 初始化隐藏和显示选择的类型
     */
    $(function(){
        heidall();
    });

    /**
     * select选择的类型
     */
    $("select#typese").change(function(){
        heidall();
    });

    function heidall() {
        $("#maxNumber").hide();
        $("#maxNumber2").hide();
        $("#groupNumber").hide();
        $("#groupNumber2").hide();
        $("#createGroupNumber").hide();
        $("#maxGroup").hide();

        $("#commodityState").hide();
        $("#commodityPrice").hide();
        $(".url").hide();
        $(".commodityDetails").hide();


        if($("#typese").val() == "0" ){
            $("#maxNumber").hide();
            $("#maxNumber2").hide();
            $("#groupNumber").hide();
            $("#groupNumber2").hide();
            $("#createGroupNumber").hide();
            $("#maxGroup").hide();
            $("#shareSet").show();
        }
        if($("#typese").val() == "1" ){
            $("#maxNumber").show();
            $("#maxNumber2").show();
            $("#groupNumber").show();
            $("#groupNumber2").show();
            $("#createGroupNumber").hide();
            $("#maxGroup").hide();
            $("#shareSet").show();

            $("#commodityState").show();
            $("#commodityPrice").show();
            $(".url").show();
            $(".commodityDetails").show();
            $("#showImaList").show();
            $("#shareSet").show();
        }
        if($("#typese").val() == "2" ){
            $("#maxNumber").hide();
            $("#maxNumber2").hide();
            $("#groupNumber").hide();
            $("#groupNumber2").hide();
            $("#createGroupNumber").show();
            $("#maxGroup").show();

            $("#commodityState").show();
            $("#commodityPrice").show();
            $(".url").show();
            $(".commodityDetails").show();
            $("#showImaList").show();
            $("#shareSet").show();
        }
        if($("#typese").val() == "3" ){
            $("#maxNumber").hide();
            $("#maxNumber2").hide();
            $("#groupNumber").hide();
            $("#groupNumber2").hide();
            $("#createGroupNumber").hide();
            $("#maxGroup").hide();
        }
    }


    /**
     * 初始化隐藏和显示选择的类型
     */
    $(function(){
        heidShare();
        if("${bean.share }" == "1" ){
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
    var ue = UE.getEditor('commodityDetails');

    function isFocus(e){
        alert(UE.getEditor('commodityDetails').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('commodityDetails').blur();
        UE.dom.domUtils.preventDefault(e)
    }
</script>
</html>
