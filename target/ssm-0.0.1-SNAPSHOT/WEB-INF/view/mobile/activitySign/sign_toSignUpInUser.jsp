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
	<title>${activity.title }</title>
	<%@ include file="/WEB-INF/common_andy.jsp" %>
	<script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
	<style type="text/css">
		img{ height: auto; width: auto\9; width:100%; }
		.enabled{
			border: none;
			padding: 2px 12px;
			font-size: 1.4rem;
			height: 3rem;
			line-height: 1.5;
			background-color: #fff;
			border-bottom: 1px solid #ccc;
			outline: none;
		}
		input[type="text"]:focus,
		input[type="password"]:focus {
			border-bottom: 1px solid #5cb85c;
		}
	</style>
	<!--=========引入Alert=========-->
	<script type="text/javascript" src="${path}/resources/alert/1.1/alertPopShow.js"></script>
	<script type="text/javascript" src="${path}/resources/alert/1.1/jquery-1.10.1.min.js"></script>
	<link rel="stylesheet" href="${path}/resources/alert/1.1/common.css">
</head>
<html>
<body>
<div align="center" style="margin-top: 0%;">
	<c:if test="${sign == 1 }">
		<div style="height: 3rem;background-color:white">
			<div align="center" style="margin-top: 0rem;background-color:#e1e6f6;height:3rem;" >
				<div style="padding-top: 0.5rem;font-size:1.2rem;" >你已经报名成功了</div>
			</div>
		</div>
	</c:if>
	<br>
		<form action="${path}/activityReception/saveSignUp" method="post" id="formSubmit" style="margin-top: 6rem;">
				<c:forEach items="${attribute}" var="attribute" varStatus="idxStatus">
					<c:if test="${attribute.hide == -1 }">
						<span style="font-size: 1.4rem;margin-left: -2.2rem;">${attribute.chineseCharacterName}:</span>&nbsp;&nbsp;
						<c:if test="${attribute.typese eq 'input'}">
							<input type="hidden" name="params" value="${attribute.name}" id="params${idxStatus.index}">
							<input type="hidden" name="sorts" value="${attribute.sort}" id="sorts${idxStatus.index}">
							<c:if test="${attribute.verificationType ne'phone'}">
								<c:if test="${attribute.name eq'name'}">
									<c:if test="${sign == 0 }">
										<input type="text" name="values" id="values${idxStatus.index}" class="enabled"  value="${user.username}"><br>
									</c:if>
									<c:if test="${sign == 1 }">
										<input type="text" name="values" id="values${idxStatus.index}" class="enabled"><br>
									</c:if>
								</c:if>
								<c:if test="${attribute.name ne'name'}">
									<input type="text" name="values" id="values${idxStatus.index}" class="enabled"><br>
								</c:if>


							</c:if>
							<c:if test="${attribute.verificationType eq 'phone'}">
								<c:if test="${sign == 0 }">
									<input name="values" id="values${idxStatus.index}" class="enabled" type="tel" maxlength="11" value="${user.userphone}"/><br>
								</c:if>
								<c:if test="${sign == 1 }">
									<input name="values" id="values${idxStatus.index}" class="enabled" type="tel" maxlength="11" value=""/><br>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${attribute.typese eq 'textArea'}">
							<input type="hidden" name="params" value="${attribute.name}" id="params${idxStatus.index}">
							<input type="hidden" name="sorts" value="${attribute.sort}" id="sorts${idxStatus.index}">
							<textarea name="values" id="values${idxStatus.index}"></textarea><br>
						</c:if>
						<input type="hidden" value="${attribute.verificationType}" id="verificationType${idxStatus.index}">
						<input type="hidden" value="${attribute.descM}" id="descM${idxStatus.index}">
						<input type="hidden" value="${attribute.chineseCharacterName}" id="chineseCharacterName${idxStatus.index}">
						<br/>
					</c:if>
				</c:forEach>
				<input type="hidden" id="id"  name="id" value="${activity.id }">
				<input type="hidden"  name="share" value="${activity.share }">
				<input type="hidden" id="authorised"  name="authorised" value="${activity.authorised }">
				<input type="hidden"  name="prizes" value="${activity.prizes }">
				<input type="hidden"  id="setsize" value="${setsize}">
				<input type="hidden"  id="subscribe" value="${subscribe }">
				<input type="hidden"  id="time" value="${time}">
				<!-- 提交按钮 -->
				<div align="center" style="margin-top: 3rem;" id="sub">
					<div  style="width: 70%;height:auto;margin-top: 9rem;">
						<img alt="" src="${path}/resources/activity/image/1.1/submit_btn.png">
					</div>
				</div>

				<!-- 底部图片 -->
				<div align="center" style="margin-top: 1rem;margin-bottom: 6rem;display:none;" id="buttonImage">
					<div style="margin-bottom: 0.5rem;margin-top: 3rem;">如果你内心住着爱吃爱玩闲不住的灵魂，就关注我们吧！</div>
					<div  style="width: 40%;height:auto;margin-top: 0rem;">
						<img alt="" src="${path}/resources/activity/image/1.1/QRcodeDYH.png">
					</div>
				</div>

		</form>
	</div>

	<!-- 表达验证 -->
	<script type="text/javascript">
        $("#sub").click(function(){
            var hasSign = "${sign}";
            var subscribe = "${subscribe}";
            var time = "${time}";
            if(hasSign == "1" || hasSign == 1){
                webToast("你已经报名成功了","bottom",1000);//top middle bottom 控制显示位置
                return false;
			}
            if(subscribe != "-1" && subscribe == "0"){
                webToast("该活动需要关注订阅号才能报名。","bottom",1000);//top middle bottom 控制显示位置
                return false;
            }
            if(time != "0" && time == "1"){
                webToast("还没到报名时间。","bottom",1000);//top middle bottom 控制显示位置
                return false;
            }
            if(time != "0" && time == "2"){
                webToast("报名已经结束了。","bottom",1000);//top middle bottom 控制显示位置
                return false;
            }
            /** 拼接参数allParams */
            var allParams = "";
            var id = $("#id").val();
            var authorised = $("#authorised").val();
            var setsize = $("#setsize").val();
            var signMax = setsize;
            var signTotal = 0;
            for (var i = 0 ;i < setsize ;i++){
                var sign = 1;//该循环项的属性验证情况 1表示通过 0表示未通过
                var params = $("#params" + i).val();//参数名称
                var values = $("#values" + i).val();//参数值
                var sorts = $("#sorts" + i).val();//参数排序
                var descM = $("#descM" + i).val();//参数描述
                var chineseCharacterName = $("#chineseCharacterName" + i).val();//参数中文名称
                var verificationType = $("#verificationType" + i).val();//参数验证方式
                allParams = allParams + params + "%" + values + "%" + sorts + "%~";
                //alert("[params]" + params + "[values]" + values + "[descM]" + descM + "[verificationType]" + verificationType);
                if(values.length == 0){
                    webToast(chineseCharacterName + "不能为空","bottom",1000);//top middle bottom 控制显示位置
                    sign = 0;
                    return false;
                }
                if(verificationType != "-1"){
                    if(verificationType == "phone"){
                        if(!isPoneAvailable(values)){
                            webToast("请输入正确的手机号码","bottom",1000);//top middle bottom 控制显示位置
                            sign = 0;
                            return false;
                        }
                    }
                    if(verificationType == "email"){
                        if(!isEmailAvailable(values)){
                            webToast("请输入正确的电子邮箱","bottom",1000);//top middle bottom 控制显示位置
                            sign = 0;
                            return false;
                        }
                    }
                }

                signTotal = parseInt(sign) + parseInt(signTotal);
            }

            if(signMax == signTotal){//验证通过,提交表单
                $.post("${path}/activityReception/saveSignUpAjaxNoUser",{id:id,authorised:authorised,allParams:allParams},function(data){
                    if(data == "1" || data == 1){
                        webToast("报名成功!" ,"bottom",2000);//top middle bottom 控制显示位置
						$("#buttonImage").show();
					}else if(data == "-1" || data == -1){
                        webToast("你已经报名成功了!" ,"bottom",1000);//top middle bottom 控制显示位置
					}
                    //webToast("返回码：" + info ,"bottom",1000);//top middle bottom 控制显示位置
                });
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