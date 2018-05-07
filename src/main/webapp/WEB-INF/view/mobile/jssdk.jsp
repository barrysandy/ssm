<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>测试页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width">
    <meta name="viewport" content="initial-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <%@ include file="/WEB-INF/common.jsp"%>
</head>

<body>
    <div align="center" style="width: 100%;height: 100%;">
        <b style="margin-top: 15%;">JSSDK -  登录测试页面</b><br>
        <i style="margin-top: 15%;">${user.nickName}</i><br>
        <img src="${user.headImgUrl}" style="width: 100px;height: 100px;margin-top: 15%;">

    </div>


</body>

<!--微信JS-->
<!-- 引入微信JS API -->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    wx.config({
        debug: true, //true false 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: "${appId}", // 必填，公众号的唯一标识
        timestamp: "${timestamp}", // 必填，生成签名的时间戳
        nonceStr: "${noncestr}", // 必填，生成签名的随机串
        signature: "${signature}",// 必填，签名，见附录1
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

    wx.ready(function (){
        //分享到朋友圈
        wx.onMenuShareTimeline({
            title: '免费儿童职业体验名额，就在星期八小镇！', // 分享标题
            desc: '给孩子一个职业的梦想，我们一同助他实现！圈粉亲子活动福利专享。', // 分享描述
            link: '${url }', // 分享链接
            imgUrl: '${host }/views/jsp/activity/singup/page/act1_25/image/share.jpg', // 分享图标
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
            title: '免费儿童职业体验名额，就在星期八小镇！', // 分享标题
            desc: '给孩子一个职业的梦想，我们一同助他实现！圈粉亲子活动福利专享。', // 分享描述
            link: '${url }', // 分享链接
            imgUrl: '${host }/views/jsp/activity/singup/page/act1_25/image/share.jpg', // 分享图标
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
            title: '免费儿童职业体验名额，就在星期八小镇！', // 分享标题
            desc: '给孩子一个职业的梦想，我们一同助他实现！圈粉亲子活动福利专享。', // 分享描述
            link: '${url }', // 分享链接
            imgUrl: '${host }/views/jsp/activity/singup/page/act1_25/image/share.jpg', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //分享到微博
        wx.onMenuShareWeibo({
            title: '免费儿童职业体验名额，就在星期八小镇！', // 分享标题
            desc: '给孩子一个职业的梦想，我们一同助他实现！圈粉亲子活动福利专享。', // 分享描述
            link: '${url }', // 分享链接
            imgUrl: '${host }/views/jsp/activity/singup/page/act1_25/image/share.jpg', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //分享到qq空间
        wx.onMenuShareQZone({
            title: '免费儿童职业体验名额，就在星期八小镇！', // 分享标题
            desc: '给孩子一个职业的梦想，我们一同助他实现！圈粉亲子活动福利专享。', // 分享描述
            link: '${url }', // 分享链接
            imgUrl: '${host }/views/jsp/activity/singup/page/act1_4/share.jpg', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });

    });
</script>
</body>
</html>
