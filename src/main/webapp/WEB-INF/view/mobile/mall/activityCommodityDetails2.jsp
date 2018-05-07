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
<div class="dialogShare" id="dialogShare" style="display:none" align="center">
    <img src="${path}/resources/mobile/mall/shareImage/shareIng1.png"
         style="margin-top:0rem;margin-left:5%;width:80%;height:auto;">
</div>
<div class="ydBody">
    <!-- 轮播图插件  -->
    <figure id="full_feature" class="swipslider" style="margin: 0;">
        <ul class="sw-slides">
            <c:forEach items="${commodity.resource }" var="img" begin="1">
                <li class="sw-slide"><img src="${img.url }" alt=""></li>
            </c:forEach>
        </ul>
    </figure>
    <div class="ydCon">
        <div class="title">${commodity.commodityName }</div>
        <div class="f-clear ydLine">
            <ul class="ydTime">
                <c:forEach items="${commodityPriceList }" var="price">
                    <li style="color:#f56e4e;font-size: 1.5rem;">${price.priceName}：￥${price.price}${price.priceUnit}</li>
                </c:forEach>
            </ul>
        </div>
        <div class="f-clear ydLine">
            ${commodity.commodityDetails }
        </div>
    </div>
    <div class="ydFooter" style="height: 0;">
        <div class="mui-bar mui-bar-tab" style="width: 100%;">
            <div class="t-line aui-on-cell" style="width: 100%;">
                <div class="aui-onc" style="position: relative;width: 100%;">
                    <a href="tel:‭13882178971‬" style="width: 25%;">
                        <img src="${path}/resources/img/icon/activityCommodity/phone.png"
                             style="height: 1.5rem;width: 1.5rem;vertical-align: middle;">
                        <span class="span1" style="vertical-align: middle;margin-right: 0rem;color: #1277fe;">咨询</span>
                    </a>
                    <a href="JavaScript:;" id="shareButton" style="width: 25%;">
                        <img src="${path}/resources/img/icon/activityCommodity/share.png"
                             style="height: 1.5rem;width: 1.5rem;vertical-align: middle;">
                        <span class="span1" style="vertical-align: middle;margin-right: 0rem;color: #1277fe;">分享</span>
                    </a>

                    <div style="float:right;width: 45%">
                        <c:if test="${commodity.commodityState == 0}">
                            <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,grey,grey) no-repeat;" >已下架</a>
                        </c:if>
                        <c:if test="${commodity.commodityState == 1}">
                            <c:if test="${buy eq '1'}">
                                <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,#f7373d,#fa8078) no-repeat;" onclick="subSign()">立即报名</a>
                            </c:if>
                            <c:if test="${buy eq '0'}">
                                <a href="JavaScript:;" class="aui-got" style="width: 100%;text-decoration:none;background: -webkit-linear-gradient(left,grey,grey) no-repeat;" >报名已满</a>
                            </c:if>
                        </c:if>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="Price" value="${commodity.commodityPrice }"/><br>
<input type="hidden" id="commodityStock" value="${commodity.commodityStock }"/><br>
<input type="hidden" id="id" value="${commodity.id }"/><br>
<input type="hidden" id="commodityState" value="${commodity.commodityState }"/><br>
<div align="center" id="div1" class="alertText">xxx</div>

<div class="xuanfu" style="position: fixed;bottom: 0;width: 3.6rem;z-index: 100;margin-bottom: 5.0rem;left: 84%;">
    <img src="${path}/resources/img/icon/userCenter/usercenter.png" onclick="location='${path}/wechatInUser/userCenter?menuId=${menuId}'">
</div>

</body>

<script type="text/javascript">
    /** 轮播图 */
    $('#full_feature').swipeslider();

    function subSign() {
        window.location.href = "${path}/shop/toCreateOrderInUser?id=" + $('#id').val() + "&menuId=${menuId }&from=singlemessage&isappinstalled=0";
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