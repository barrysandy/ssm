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
    <title>填写联系人信息</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" href="${path}/resources/css/mail/toCreatOrderInUser.css" />
    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/mui.min.css">
    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/home.css">

    <!-- 微信UI -->
    <link rel="stylesheet" href="${path}/resources/weUI/weui.css">
    <link rel="stylesheet" href="${path}/resources/weUI/example.css">
    <style type="text/css">
        img{ height: auto; width: auto\9; width:100%; }
        .alertText{
            z-index:999;
            width:100%;
            height:2.5rem;
            background:red;
            position: fixed;
            top: 0px;
            margin: auto;
            font-size:1.3rem;
            color:#ffffff;
            display:none;
        }
    </style>
    <script>
        $(function(){
            setFontSize = function() {
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
                responsive:true,
                direction:'left',
                height:250,
                items: {
                    visible: 1,
                    minimum: 1,
                    height:'100%'
                },
                pagination: "#carousel-pager4",//指定圆点坐标
                scroll:{
                    items:1,
                    fx:'directscroll',
                    easing:'linear',
                    duration:500,
                    pauseOnHover:false
                },
                auto:{
                    play:'auto',
                    pauseDuration:2500
                }
            });
        })
    </script>
  </head>
	<body>
    <div class="floatbox">

    </div>
    <div id="app" class="rich_media">
        <div data-v-4fce1214="">
            <div data-v-4fce1214="" class="content" style="overflow-x: hidden;margin-top: -2.5rem;">
                <div data-v-4fce1214="" class="content-cell">
                    <h6 data-v-4fce1214="" style="font-size: 1.3rem;">当前组团日期</h6>
                    <div data-v-4fce1214="" style="font-size: 1.3rem;margin-top: 1.5rem;margin-bottom: 1.5rem;">
                        <button style="font-size: 1.3rem;background-color: grey;border-color: grey;" type="button" class="btn btn-primary btn-sm" >${day}</button>
                    </div>
                </div>
                <div data-v-4fce1214="" class="content-cell">
                    <h6 data-v-4fce1214="" style="font-size: 1.3rem;">联系人信息</h6>
                    <div data-v-4fce1214="" class="row" style="margin-top: 1.5rem;">
                        <div data-v-4fce1214="" class="col-3">
                            <lable data-v-4fce1214="" style="font-size: 1.3rem;margin-top: 0.3rem;">联系人</lable>
                        </div>
                        <div data-v-4fce1214="" class="col-8">
                            <input style="font-size: 1.1rem;" data-v-4fce1214="" type="text" class="form-control userInput1" id="userName" maxlength="8">
                        </div>
                    </div>
                    <div data-v-4fce1214="" class="row" style="margin-top: 0rem;">
                        <div data-v-4fce1214="" class="col-3">
                            <lable data-v-4fce1214="" style="font-size: 1.3rem;">手机号</lable>
                        </div>
                        <div data-v-4fce1214="" class="col-8">
                            <input style="font-size: 1.1rem;" type="tel" class="form-control userInput2" id="userPhone" maxlength="11">
                        </div>
                    </div>
                </div>
            </div>
            <div class="mui-bar mui-bar-tab" id="footer" style="position: absolute;">
                <div class="t-line aui-on-cell">
                    <div class="aui-onc">
                        <span style="color:#f56e4e;margin-right: 6rem;" id="Total1">合计 ：¥${price}</span>
                        <a href="JavaScript:;" class="aui-got" style="padding:0 54px;text-decoration:none;background: -webkit-linear-gradient(left,#f7373d,#fa8078) no-repeat;" onclick="subOrder()">下一步</a><!-- Pay() -->
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="commodityStock" value="${commodity.commodityStock }"/>
        <input type="hidden" id="id" value="${commodity.id }"/>
        <input type="hidden" id="commodityState" value="${commodity.commodityState }"/>
        <input type="hidden" id="Total" value="${price }"/>
        <input type="hidden" id="userUserTime" value="${day }" />
        <div align="center" id="div1" class="alertText">xxx</div>

        <!-- loading toast -->
        <div id="loadingToast" style="display:none;">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">订单提交中</p>
            </div>
        </div>
    </div>
	</body>
    <script type="text/javascript">
        // 初始化内容
        var h1 = $('body').height();
        var h2 = $('.sign').height();
        var h3 = $('.register').height();
        var h4 = $('.look').height();
        $(window).resize(function() {
            $('.all').height(h1);
            $('.sign').height(h2);
            $('.register').height(h3);
            $('.look').height(h4);
        });

        changeTotal();

        /** 点击提交订单按钮 */
        function alertText(text){
            $('#div1').text(text);
            $('#div1').fadeIn(500);        //淡入
            setTimeout(function () {
                $('#div1').fadeOut(500);       //淡出
            }, 1500);
        }
        function subOrder(){
            var Total = $("#Total").text();
            var id = $("#id").val();
            var userName = $("#userName").val();
            var userPhone = $("#userPhone").val();
            var userUserTime = $("#userUserTime").val();
            if(Number == 0 && Number2 == 0){
                alertText("选择出行人数");
                return false;
            }
            if(userName == "" || userName == null){
                alertText("请填写联系人姓名");
                return false;
            }
            if(userPhone == "" || userPhone == null){
                alertText("请填写联系人手机号码");
                return false;
            }
            if(userPhone != "" || userPhone != null){
                if(!isPoneAvailable(userPhone)){
                    alertText("联系人手机号码格式不正确");
                    return false;
                }
            }
            toOrder(id,userPhone,userUserTime,userName);
        }

        function toOrder(id,userPhone,userUserTime,userName){
            var $loadingToast = $('#loadingToast');
            setTimeout(function () {
                $loadingToast.fadeIn(500);
                //判断能否组团
                $.post("${path}/shop/interfaceCanCreateGroupOrder",{id:id},function(result){
                    if(result == "ok"){
                        /**
                         * 发起提交订单请求（Post）
                         * info 请求返回数据（非0 则为订单id，否则订单生成失败）
                         */
                        var total = $("#Total").val();
                        var groupId = '${groupId }';
                        //type = 'join' 表示下单类型为创建组团
                        $.post("${path}/order/placeAnOrderGroupJoinInUser",{id:id,total:total,userPhone:userPhone,userUserTime:userUserTime,userName:userName,type:'join',groupId:groupId},function(info){
                            $loadingToast.fadeOut(100);
                            if(info != -1 && info != -2 && info != -3 && info != -4 && info != -5  && info != 0){
                                window.location.href="${path}/order/payOrderInUser?order_id="+info+"&menuId=${menuId }";
                            }else if(info == -1){
                                alertText("发起请求参数错误，请刷新后重试！");
                            }else if(info == -2){
                                alertText("购买的商品已经被商家下架！");
                            }else if(info == -3){
                                alertText("生成订单编失败，请刷新后重试！");
                            }else if(info == -4){
                                alertText("你所参加的团不存在！");
                            }else if(info == -5){
                                alertText("你所参加的团,已经满员！");
                            }else if(info == 0){
                                alertText("订单生成失败，请重试！");
                            }
                        });
                    }
                    else if(result == "max"){
                        $loadingToast.fadeOut(100);
                        alertText("组团已满，请下次参与！");
                        return false;
                    }
                    else if(result == "exception"){
                        $loadingToast.fadeOut(100);
                        alertText("组团异常，请稍后重试！");
                        return false;
                    }
                });
            }, 10);
        }

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

        $("input").foucus(function(){$(".mui-bar mui-bar-tab").hide();});
    </script>
</html>