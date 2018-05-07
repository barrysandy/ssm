<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>${commodity.commodityName }</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${path}/resources/azenui/css/ui.css">
    <link rel="stylesheet" href="${path}/resources/mobile/mall/css/style.css"/>
    <script src="${path}/resources/mobile/mall/js/jquery.min.js"></script>
    <script src="${path}/resources/mobile/mall/js/jquery.carouFredSel.js"></script>

    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/mui.min.css">
    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/home.css">

    <!-- 轮播图组件引入部分  -->
    <link rel="stylesheet" type="text/css" href="${path}/resources/jQuerySwipeslider/dist/swipeslider.css">
    <script type="text/javascript" src="${path}/resources/jQuerySwipeslider/dist/swipeslider.min.js"></script>

    <!-- 微信UI -->
    <link rel="stylesheet" href="${path}/resources/weUI/weui.css">
    <link rel="stylesheet" href="${path}/resources/weUI/example.css">

    <!--=========引入Alert=========-->
    <script type="text/javascript" src="${path}/resources/alert/1.1/alertPopShow.js"></script>
    <link rel="stylesheet" href="${path}/resources/alert/1.1/common.css">

    <style type="text/css">
        img {
            height: auto;
            width: auto \9;
            width: 100%;
        }
        video {
            height: auto;
            width: auto \9;
            width: 100%;
        }
        .alertText {
            z-index: 999;
            width: 100%;
            height: 2.5rem;
            background: red;
            position: fixed;
            top: 0px;
            margin: auto;
            font-size: 1.5rem;
            color: #ffffff;
            display: none;
        }

        .dialogShare {
            width: 100%;
            height: 110%;
            position: absolute;
            z-index: 999999;
            left: 0px;
            top: 0px;
            background: #333333;
            filter: alpha(Opacity=80);
            -moz-opacity: 0.9;
            opacity: 0.9;
            progid: DXImageTransform . Microsoft . gradient(startColorstr = #7f333333, endColorstr = #7f333333);
        }
    </style>
    <script>
        $(function () {
            setFontSize = function () {
                var w = $(window).width();
                $("html").css({
                    fontSize: (w) / 360 * 10
                });
            }
            setFontSize()
            $(window).resize(function () {
                setFontSize();
            });
            $('#carousel4').carouFredSel({
                circular: true,
                responsive: true,
                direction: 'left',
                height: 250,
                items: {
                    visible: 1,
                    minimum: 1,
                    height: '100%'
                },
                pagination: "#carousel-pager4",//指定圆点坐标
                scroll: {
                    items: 1,
                    fx: 'directscroll',
                    easing: 'linear',
                    duration: 500,
                    pauseOnHover: false
                },
                auto: {
                    play: 'auto',
                    pauseDuration: 2500
                }
            });
        })
    </script>
</head>
<body>
<div class="dialogShare" id="dialogShare" style="display: none;" align="center">
    <img src="${qrCode}" style="margin-top:35%;margin-left:5%;width:50%;height:auto;">
    <p style="margin-top:10%;margin-left:5%;width:auto;font-size: 1.5rem;color: white;">扫码关注一点五小时，立即报名。</p>
</div>
<div class="ydBody">
    <!-- 轮播图插件  -->
    <figure id="full_feature" class="swipslider" style="margin: 0;">
        <ul class="sw-slides">
            <c:if test="${commodity.videoResource != null }">
                <li class="sw-slide">
                    <video src="${commodity.videoResource.url}" controls="controls" height="100%" poster="${commodity.image}">
                        您的浏览器不支持视频。
                    </video>
                </li>
            </c:if>
            <c:forEach items="${commodity.resource }" var="img" begin="1">
                <li class="sw-slide"><img src="${img.url }" alt=""></li>
            </c:forEach>
        </ul>
    </figure>
    <div class="ydCon" style="background-color: #ffffff;">
        <div class="title" style="font-size: 2rem;">${commodity.commodityName }</div>
        <div class="f-clear ydLine">
            <ul class="ydTime" style="width: 100%;">
                <li style="width: 100%;">
                    <span style="color: #ffa200;font-size: 2rem;">￥${price.price} </span>
                    <span style="color: #c3c3c3;font-size: 1.2rem;float: right;">已售 ${total }</span>
                </li>
            </ul>
        </div>
        <c:if test="${groupId != null || shareGroupId != null && shareGroupId ne '1'}">
            <div class="f-clear ydLine">
                <c:if test="${groupState == 0}">
                    <c:if test="${groupFail == 0}">
                        <div style="color: #929292;margin-bottom: 0.5rem;font-size: 1.3rem;">还差${needNumber }人成团，倒计时<span class="times"></span></div>
                    </c:if>
                    <c:if test="${groupFail == 1}">
                        <div style="color: #929292;margin-bottom: 0.5rem;font-size: 1.3rem;">组团超时失败，还差${needNumber }人成团</div>
                    </c:if>
                </c:if>
                <c:if test="${groupState == 1}">
                    <c:if test="${countDown > 0}">
                        <div style="color: #929292;margin-bottom: 0.5rem;font-size: 1.3rem;">组团成功，活动开始倒计时<span class="times"></span></div>
                    </c:if>
                    <c:if test="${countDown <= 0}">
                        <div style="color: #929292;margin-bottom: 0.5rem;font-size: 1.3rem;">组团成功，活动进行中。</div>
                    </c:if>

                </c:if>
                <c:forEach items="${listCommodityGroupMember }" var="list" begin="0" end ="${hiddenSet - 1 }" varStatus="status">
                    <%--<img src="${list.user.headImgUrl }" style="width: 5rem;height: 5rem;border-radius:5rem; ">--%>
                    <c:if test="${status.index ==  0 }">
                        <div style="position: relative;" style="width: 5rem;height: 5rem;">
                            <img src="${path}/resources/img/icon/userCenter/lender.png" style="width: 5rem;height: 5rem;border-radius:0rem;margin-top: -0.05rem;margin-right: 0rem;position:absolute;z-index: 999; ">
                            <img src="${list.user.headImgUrl }" style="width: 5rem;height: 5rem;border-radius:5rem;position:absolute;z-index: 997;top: 0;">
                        </div>
                    </c:if>
                    <c:if test="${status.index ==  1 }">
                        <img src="${list.user.headImgUrl }" style="width: 5rem;height: 5rem;border-radius:5rem; margin-left: 5.7rem;background-color: grey;color: white;vertical-align:middle;font-size: 2rem;">
                    </c:if>
                    <c:if test="${status.index >  1 }">

                        <img src="${list.user.headImgUrl }" style="width: 5rem;height: 5rem;border-radius:5rem; border-radius:5rem;background-color: grey;color: white;vertical-align:middle;font-size: 2rem;">
                    </c:if>
                </c:forEach>
                <c:if test="${totalMember > hiddenSet }">
                    <input style="width: 5rem;height: 5rem;border-radius:5rem; border-radius:5rem;background-color: grey;color: white;vertical-align:middle;font-size: 2rem;" value="  +${hiddenTotal }" disabled="disabled"/>
                    <img src="${path }/resources/img/icon/activityCommodity/add.png" style="width: 5rem;height: 5rem;" onclick="toPrepJonin('${status}')">
                </c:if>
                <c:if test="${totalMember <= hiddenSet }">
                    <img src="${path }/resources/img/icon/activityCommodity/add.png" style="width: 5rem;height: 5rem;" onclick="toPrepJonin('${status}')">
                </c:if>
            </div>
        </c:if>

        <div class="f-clear ydLine">
            ${commodity.commodityDetails }
        </div>
    </div>
    <div class="ydFooter" style="height: 0;">
        <div class="mui-bar mui-bar-tab" style="width: 100%;">
            <div class="t-line aui-on-cell" style="width: 100%;">
                <div class="aui-onc" style="position: relative;width: 100%;">
                    <!-- status 0 -->
                    <a  onclick="index('${menuId }')" style="width: 20%;float:left;">
                        <span class="span1" style="vertical-align: middle;margin-right: 0rem;color: #1277fe;">首页</span>
                    </a>
                    <c:if test="${status == 0 }">
                        <div style="float:right;width: 80%">
                            <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,#f64649,#f06e6c) no-repeat;" onclick="subSign()">自己开团（马上报名）</a>
                        </div>
                    </c:if>

                    <!-- status 1 -->
                    <c:if test="${status == 1 }">
                        <div style="float:left;width: 80%;">
                            <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,#c3c3c3,#c3c3c3) no-repeat;">
                                <c:if test="${groupFail == 0}"> 开团中 </c:if>
                                <c:if test="${groupFail == 1}"> 开团失败（已超时） </c:if>
                            </a>
                        </div>
                    </c:if>

                    <!-- status 2 -->
                    <c:if test="${status == 2 }">
                        <div style="float:left;width: 80%;">
                            <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,#c3c3c3,#c3c3c3) no-repeat;">
                                <c:if test="${groupFail == 0}"> 已参团 </c:if>
                                <c:if test="${groupFail == 1}"> 已参团（组团超时失败） </c:if>
                            </a>
                        </div>
                    </c:if>

                    <!-- status 3 -->
                    <c:if test="${status == 3 }">
                        <div style="float:left;width: 40%;">
                            <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,#169bd5,#169bd5) no-repeat;" onclick="toJonin()">我要参团</a>
                        </div>
                        <div style="float:right;width: 40%">
                            <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,#ff99cc,#ff99cc) no-repeat;" onclick="subSign()">自己开团</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- loading toast -->
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中</p>
    </div>
</div>

<input type="hidden" id="Price" value="${commodity.commodityPrice }"/>
<input type="hidden" id="commodityStock" value="${commodity.commodityStock }"/>
<input type="hidden" id="id" value="${commodity.id }"/><br>
<input type="hidden" id="commodityState" value="${commodity.commodityState }"/>
<input type="hidden" id="joinGroupId" value="${joinGroupId }"/>
<input type="hidden" id="countDown" value="${countDown }"/>
<input type="hidden" id="binding"/>
<div align="center" id="div1" class="alertText">xxx</div>

</body>

<script type="text/javascript">


    /** canTouchmove 为1时 $(document).on('touchmove',function(e) 不受影响 如果为0时禁止页面body滑动 */
    var canTouchmove = 1;

    /** 遮盖层事件 */
    $("#shareButton").click(function () {
        $("#dialogShare").show();
        canTouchmove = 0;//body不可滑动
    });
    $("#dialogShare").click(function () {
        $("#dialogShare").hide();
        canTouchmove = 1;//body可滑动
    });

    /** 页面触摸滑动事件 */
    $(document).on('touchmove', function (e) {
        if (canTouchmove == 0) {
            e.preventDefault();//禁止页面body滑动
        }
    })

    /** 页面滑动事件 */
    window.onscroll = function () {
        var currentHeight = $(document).scrollTop();
        currentHeight = parseInt(currentHeight) - 10;
        var div = document.getElementById("dialogShare");
        div.style.top = currentHeight + 'px'; //设置遮盖层 的top为当前页面所在的高度
    };

    function toPrepJonin(status) {
        if(status == 1 || status == 2 ){
            webToast("你已经在团队中了","middle",2000);//top middle bottom 控制显示位置
        }else {
            toJonin();
        }

    }

    function index(menuId) {
        window.location.href = "${path}/commodityGroup/listInUser?menuId=" + menuId + "&typese=-1&cpage=1&addIndex=0";
    }


    countDown('${countDown }');
    //带天数的倒计时
    function countDown(times){
        var timer = null;
        timer = setInterval(function(){
            var day=0,
                hour=0,
                minute=0,
                second=0;//时间默认值
            if(times > 0){
                day = Math.floor(times / (60 * 60 * 24));
                hour = Math.floor(times / (60 * 60)) - (day * 24);
                minute = Math.floor(times / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(times) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }
            if (day <= 9) day = '0' + day;
            if (hour <= 9) hour = '0' + hour;
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            // console.log(day+"天:"+hour+"小时："+minute+"分钟："+second+"秒");
            $(".times").text(day + ":" + hour + ":" + minute + ":" + second);
            times --;
        },1000);
        if(times<=0){
            clearInterval(timer);
        }
    }

    /** 轮播图 */
    $('#full_feature').swipeslider();

    //创建团
    function subSign() {
        var subscribe = '${subscribe}';
        if(subscribe == 0 || subscribe == "0"){
            $("#dialogShare").show();
        } else {
            var id = $("#id").val();
            var $loadingToast = $('#loadingToast');
            setTimeout(function () {
                $loadingToast.fadeIn(500);
                //判断能否组团
                $.post("${path}/shop/interfaceCanCreateGroupOrder",{id:id},function(result){
                    $loadingToast.fadeOut(100);
                    if(result == "ok"){
                        //前往开团选择页面
                        window.location.href = "${path}/shop/toCreateGroupOrderInUser?id=" + id + "&menuId=${menuId }&from=singlemessage&isappinstalled=0";
                    }
                    else if(result == "max"){
                        webToast("组团数已满，不能再自己开团了！","middle",2000);//top middle bottom 控制显示位置
                        return false;
                    }
                    else if(result == "exception"){
                        webToast("开团异常，请稍后重试！","middle",2000);//top middle bottom 控制显示位置
                        return false;
                    }
                });
            }, 10);
        }
    }

    //参团
    function toJonin() {
        var subscribe = '${subscribe}';
        if(subscribe == 0 || subscribe == "0"){
            $("#dialogShare").show();
        } else {
            var id = $('#id').val();
            var groupId = $('#joinGroupId').val();
            var $loadingToast = $('#loadingToast');
            setTimeout(function () {
                $loadingToast.fadeIn(500);
                //判断能否参团
                $.get("${path}/shop/canJoinGroupOrderInUser",{id:id,groupId:groupId},function(result){
                    $loadingToast.fadeOut(100);
                    if(result == "ok"){
                        //前往参团选择页面
                        window.location.href = "${path}/shop/toJoinGroupOrderInUser?id=" + $('#id').val() + "&menuId=${menuId }&groupId="+groupId+"&from=singlemessage&isappinstalled=0";
                    }
                    else if(result == "timeout"){
                        webToast("该团已经组团超时失败了！","middle",2000);//top middle bottom 控制显示位置
                        return false;
                    }else{
                        webToast("该团已满，不能再加入了！","middle",2000);//top middle bottom 控制显示位置
                        return false;
                    }
                });
            }, 10);
        }

    }


    /** canTouchmove 为1时 $(document).on('touchmove',function(e) 不受影响 如果为0时禁止页面body滑动 */
    var canTouchmove = 1;

    /** 遮盖层事件 */
    $("#shareButton").click(function () {
        $("#dialogShare").show();
        canTouchmove = 0;//body不可滑动
    });
    $("#dialogShare").click(function () {
        $("#dialogShare").hide();
        canTouchmove = 1;//body可滑动
    });

    /** 页面触摸滑动事件 */
    $(document).on('touchmove', function (e) {
        if (canTouchmove == 0) {
            e.preventDefault();//禁止页面body滑动
        }
    })

    /** 页面滑动事件 */
    window.onscroll = function () {
        var currentHeight = $(document).scrollTop();
        currentHeight = parseInt(currentHeight) - 10;
        var div = document.getElementById("dialogShare");
        div.style.top = currentHeight + 'px'; //设置遮盖层 的top为当前页面所在的高度
    };
</script>
<!-- 引入微信JS API -->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    /** 初始化参数 */
    appId = "";
    /** JSSDK签名：appid */
    timestamp = "";
    /** JSSDK签名时间戳：timestamp */
    noncestr = "";
    /** JSSDK签名随机字符串：noncestr */
    signature = "";
    /** JSSDK签名标识：signature */
    url = "${url }";
    /** JSSDK签名请求的参数String*/
    mapKey = "${mapKey }";
    /** JSSDK签名请求的参数String*/
    menuId = "${menuId }";
    /** JSSDK签名请求的参数String*/
    jssdk_url = "http://" + window.location.host + "/ssm/interfaceJSSDK/sign";
    /** JSSDK签名请求的接口*/
    /** 初始化函数，并为参数赋值 */
    $(function () {
        /** 获取JSSDK签名 */
        $.get(jssdk_url, {url: url, mapKey: mapKey, menuId: menuId}, function (data) {
            var jssdkObj = eval("(" + data + ")");
            /** 赋值 */
            appId = jssdkObj.appId;
            timestamp = jssdkObj.timestamp;
            noncestr = jssdkObj.noncestr;
            signature = jssdkObj.signature;
            //alert("timestamp：" + timestamp + " noncestr：" + noncestr + " signature：" + signature);
            /** 微信jssdk 配置 */
            wx.config({
                debug: false, //true false 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: appId, // 必填，公众号的唯一标识
                timestamp: timestamp, // 必填，生成签名的时间戳
                nonceStr: noncestr, // 必填，生成签名的随机串
                signature: signature,// 必填，签名，见附录1
                jsApiList: [
                    'checkJsApi',
                    'onMenuShareTimeline',
                    'onMenuShareAppMessage',
                    'onMenuShareQQ',
                    'onMenuShareWeibo',
                    'onMenuShareQZone',
                    'startRecord',
                    'stopRecord',
                    'onVoiceRecordEnd',
                    'playVoice',
                    'pauseVoice',
                    'stopVoice',
                    'onVoicePlayEnd',
                    'uploadVoice',
                    'downloadVoice',
                    'chooseImage',
                    'previewImage',
                    'uploadImage',
                    'downloadImage',
                    'translateVoice',
                    'getNetworkType',
                    'openLocation',
                    'getLocation',
                    'hideOptionMenu',
                    'showOptionMenu',
                    'hideMenuItems',
                    'showMenuItems',
                    'hideAllNonBaseMenuItem',
                    'showAllNonBaseMenuItem',
                    'closeWindow',
                    'scanQRCode',
                    'chooseWXPay',
                    'openProductSpecificView',
                    'addCard',
                    'chooseCard',
                    'openCard '
                ]     // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
        });
    });

    /** 微信jssdk 分享函数 */
    wx.ready(function () {
        /** 配置分享的URL和Image和Desc等信息 */
        //分享到朋友圈
        wx.onMenuShareTimeline({
            title: '${shareTitle }', // 分享标题
            desc: '${shareDesc }', // 分享描述
            link: '${shareLink }', // 分享链接
            imgUrl: '${shareImgUrl }', // 分享图标
            type: 'link', // 分享类型,music、video或link，不填默认为link
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });

        //分享给朋友
        wx.onMenuShareAppMessage({
            title: '${shareTitle }', // 分享标题
            desc: '${shareDesc }', // 分享描述
            link: '${shareLink }', // 分享链接
            imgUrl: '${shareImgUrl }', // 分享图标
            type: 'link', // 分享类型,music、video或link，不填默认为link
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //分享到QQ
        wx.onMenuShareQQ({
            title: '${shareTitle }', // 分享标题
            desc: '${shareDesc }', // 分享描述
            link: '${shareLink }', // 分享链接
            imgUrl: '${shareImgUrl }', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //分享到微博
        wx.onMenuShareWeibo({
            title: '${shareTitle }', // 分享标题
            desc: '${shareDesc }', // 分享描述
            link: '${shareLink }', // 分享链接
            imgUrl: '${shareImgUrl }', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //分享到qq空间
        wx.onMenuShareQZone({
            title: '${shareTitle }', // 分享标题
            desc: '${shareDesc }', // 分享描述
            link: '${shareLink }', // 分享链接
            imgUrl: '${shareImgUrl }', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
    });
</script>
</html>