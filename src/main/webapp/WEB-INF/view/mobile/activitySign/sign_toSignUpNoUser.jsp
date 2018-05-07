<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width">
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>活动报名</title>
	<%@ include file="/WEB-INF/common_andy.jsp" %>
</head>
<html>
<body>
<div align="center" style="margin-top: 10%;">
	<form action="${path}/activityReception/saveSignUp" method="post" id="formSubmit">
		<c:forEach items="${attribute}" var="attribute" varStatus="idxStatus">
			<c:if test="${attribute.typese eq 'input'}">
				<input type="hidden" name="params" value="${attribute.name}" id="params${idxStatus.index}">
				<input type="hidden" name="sorts" value="${attribute.sort}" id="sorts${idxStatus.index}">
				<input type="text" name="values" placeholder="${attribute.descM}" id="values${idxStatus.index}"><br>
			</c:if>
			<c:if test="${attribute.typese eq 'textArea'}">
				<input type="hidden" name="params" value="${attribute.name}" id="params${idxStatus.index}">
				<input type="hidden" name="sorts" value="${attribute.sort}" id="sorts${idxStatus.index}">
				<textarea name="values" placeholder="${attribute.descM}" id="values${idxStatus.index}"></textarea><br>
			</c:if>
			<input type="hidden" value="${attribute.verificationType}" id="verificationType${idxStatus.index}">
			<input type="hidden" value="${attribute.descM}" id="descM${idxStatus.index}">
		</c:forEach>
		<input type="hidden"  name="id" value="${activity.id }">
		<input type="hidden"  name="share" value="${activity.share }">
		<input type="hidden"  name="authorised" value="${activity.authorised }">
		<input type="hidden"  name="prizes" value="${activity.prizes }">
		<input type="hidden"  id="setsize" value="${setsize}">
		<button type="button" id="sub">btn参与活动</button>
	</form>
</div>

<!-- 表达验证 -->
<script type="text/javascript">
    $("#sub").click(function(){
        var setsize = $("#setsize").val();
        var signMax = setsize;
        var signTotal = 0;
        for (var i = 0 ;i < setsize ;i++){
            var sign = 1;//该循环项的属性验证情况 1表示通过 0表示未通过
            var params = $("#params" + i).val();//参数名称
            var values = $("#values" + i).val();//参数值
            var descM = $("#descM" + i).val();//参数描述
            var verificationType = $("#verificationType" + i).val();//参数验证方式
            alert("[params]" + params + "[values]" + values + "[descM]" + descM + "[verificationType]" + verificationType);
            if(values.length == 0){
                alert(descM + "不能为空");
                sign = 0;
            }
            if(verificationType != "-1"){
                if(verificationType == "phone"){
                    if(!isPoneAvailable(values)){
                        alert("请输入正确的手机号码");
                        sign = 0;
                    }
                }
                if(verificationType == "email"){
                    if(!isEmailAvailable(values)){
                        alert("请输入正确的电子邮箱");
                        sign = 0;
                    }
                }
            }

            signTotal = parseInt(sign) + parseInt(signTotal);
        }

        if(signMax == signTotal){//验证通过,提交表单
            alert("验证通过,提交表单");
            document.getElementById("formSubmit").submit();
        }
    });

    /**
     * 手机号码验证
     * @param input
     * @returns {boolean}
     */
    function isPoneAvailable(input) {
        var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (!reg.test(input)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 邮箱验证
     * @param input
     * @returns {boolean}
     */
    function isEmailAvailable(input) {
        var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
        if (!reg.test(input)) {
            return false;
        } else {
            return true;
        }
    }
</script>

	<!-- 引入微信JS API -->
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
		/** 初始化参数 */
		appId = "";/** JSSDK签名：appid */
		timestamp = "";/** JSSDK签名时间戳：timestamp */
		noncestr = "";/** JSSDK签名随机字符串：noncestr */
		signature = "";/** JSSDK签名标识：signature */
		url = "${url }";/** JSSDK签名请求的参数String*/
		mapKey = "${mapKey }";/** JSSDK签名请求的参数String*/
		menuId = "${menuId }";/** JSSDK签名请求的参数String*/
		jssdk_url = "http://" + window.location.host +"/ssm/interfaceJSSDK/sign";/** JSSDK签名请求的接口*/
		/** 初始化函数，并为参数赋值 */
		$(function(){
			/** 获取JSSDK签名 */
			$.get(jssdk_url,{url:url,mapKey:mapKey,menuId:menuId},function(data){
				var jssdkObj = eval("("+data+")");
				/** 赋值 */
				appId = jssdkObj.appId;
				timestamp = jssdkObj.timestamp;
				noncestr = jssdkObj.noncestr;
				signature = jssdkObj.signature;
				//alert("timestamp：" + timestamp + " noncestr：" + noncestr + " signature：" + signature);
				/** 微信jssdk 配置 */
				wx.config({
					debug: true, //true false 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
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
		wx.ready(function (){
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

</body>
</html>