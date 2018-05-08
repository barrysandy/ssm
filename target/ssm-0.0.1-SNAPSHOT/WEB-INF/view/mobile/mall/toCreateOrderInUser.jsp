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
    <title>选择日期/人数</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" href="${path}/resources/css/mail/toCreatOrderInUser.css" />
    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/mui.min.css">
    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/home.css">

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
                    <h6 data-v-4fce1214="" style="font-size: 1.3rem;">选择赏花日期</h6>
                    <div data-v-4fce1214="" style="font-size: 1.3rem;margin-top: 1.5rem;margin-bottom: 1.5rem;">
                        <button style="font-size: 1.3rem;" data-v-4fce1214="" id="time1" type="button" class="btn btn-primary btn-sm" >4月5日</button><!--onclick="addTime('4月5日',event)"-->
                        <input type="hidden" id="userUserTime" value="4月5日"/>
                        <input type="hidden" id="timePrice" value="1"/>
                    </div>
                </div>
                <div data-v-4fce1214="" class="content-cell">
                    <h6 data-v-4fce1214="" style="font-size: 1.3rem;">选择出行人数(<span style="font-size: 1.1rem;">一个成人最多携带两名儿童，儿童不能单独购买</span>)</h6>
                    <div data-v-4fce1214="" class="row" style="margin-top: 1.5rem;">
                        <div data-v-4fce1214="" class="col-7">
                            <lable data-v-4fce1214="" style="font-size: 1.3rem;">成人</lable><!--(<b id="price1" style="color: red;"></b>/张)-->
                        </div>
                        <div data-v-4fce1214="" class="col-5">
                            <div data-v-4fce1214="" class="input-group input-group-sm">
                                <div data-v-4fce1214="" class="input-group-prepend">
                                    <button style="font-size: 1.7rem;margin-top: -0.3rem;" data-v-4fce1214="" type="button" class="btn" onclick="reduceNumber()">-</button>
                                </div>
                                <input style="font-size: 1.2rem;height: 2.3rem;margin-top: 0.3rem;" class="form-control" data-v-4fce1214="" type="number"  id="Number" onchange="changeTotal()" value="1" readonly="readonly">
                                <div data-v-4fce1214="" class="input-group-append">
                                    <button style="font-size: 1.7rem;margin-top: -0.3rem;" data-v-4fce1214="" type="button" class="btn" onclick="addNumber()">+</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div data-v-4fce1214="" class="row" style="margin-bottom: 1.5rem;">
                        <div data-v-4fce1214="" class="col-7">
                            <lable data-v-4fce1214="" style="font-size: 1.3rem;">儿童(1.1米以下)</lable><!--(<b id="price2" style="color: red;"></b>/张)-->
                        </div>
                        <div data-v-4fce1214="" class="col-5">
                            <div data-v-4fce1214="" class="input-group input-group-sm">
                                <div data-v-4fce1214="" class="input-group-prepend">
                                    <button style="font-size: 1.7rem;margin-top: -0.3rem;" data-v-4fce1214="" type="button" class="btn" onclick="reduceNumber2()">-</button>
                                </div>
                                <input style="font-size: 1.2rem;height: 2.3rem;margin-top: 0.3rem;" data-v-4fce1214="" type="number" class="form-control"  id="Number2" onchange="changeTotal2()" value="0" readonly="readonly">
                                <div data-v-4fce1214="" class="input-group-append">
                                    <button style="font-size: 1.7rem;margin-top: -0.3rem;" data-v-4fce1214="" type="button" class="btn" onclick="addNumber2()">+</button>
                                </div>
                            </div>
                        </div>
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
                <div data-v-4fce1214="" class="content-cell">
                    <div data-v-4fce1214="" class="row" style="margin-top: 10rem;">
                    </div>
                </div>
            </div>
            <div class="mui-bar mui-bar-tab" id="footer" style="position: absolute;">
                <div class="t-line aui-on-cell">
                    <div class="aui-onc">
                        <span style="color:#f56e4e;margin-right: 6rem;" id="Total1">合计 ：¥${order.order_amountMoney}</span>
                        <a href="JavaScript:;" class="aui-got" style="padding:0 54px;text-decoration:none;background: -webkit-linear-gradient(left,#f7373d,#fa8078) no-repeat;" onclick="subOrder()">下一步</a><!-- Pay() -->
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="Price" value="${commodity.commodityPrice }"/>
        <input type="hidden" id="commodityStock" value="${commodity.commodityStock }"/>
        <input type="hidden" id="id" value="${commodity.id }"/>
        <input type="hidden" id="commodityState" value="${commodity.commodityState }"/>
        <input type="hidden" id="Total" value="${commodity.commodityPrice }"/>
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

        //prize = 0.01; prize2 = 0.02;
        prize = 60; prize2 = 139;
        $("#price1").text(prize);
        $("#price2").text(prize2);
        changeTotal();
        /** 选择时间 */
        function addTime(str,event) {
            var div = event.target;
            var div1 = document.getElementById("time1");
            var div2 = document.getElementById("time2");
            var div3 = document.getElementById("time3");
            div1.setAttribute("class", "btn btn-light btn-sm");
            div2.setAttribute("class", "btn btn-light btn-sm");
            div3.setAttribute("class", "btn btn-light btn-sm");
            var css =  div.getAttribute("class");
            if(css == "btn btn-light btn-sm"){
                div.setAttribute("class", "btn btn-primary btn-sm");
            }
            if(css == "btn btn-primary btn-sm"){
                div.setAttribute("class", "btn btn-light btn-sm");
            }
            if(div == div1){$("#timePrice").val(1);prize = 60;prize2 = 139;}
            if(div == div2){$("#timePrice").val(2);prize = 111;prize2 = 70;}
            if(div == div3){$("#timePrice").val(3);prize = 0.01;prize2 = 0.01;}
            $("#price1").text(prize);
            $("#price2").text(prize2);
            $("#userUserTime").val(str);
            changeTotal();
        }
        /** 点击提交订单按钮 */
        function alertText(text){
            $('#div1').text(text);
            $('#div1').fadeIn(500);        //淡入
            setTimeout(function () {
                $('#div1').fadeOut(500);       //淡出
            }, 1500);
        }
        function subOrder(){
            var Number = $("#Number").val();
            var Number2 = $("#Number2").val();
            var Price = $("#Price").val();
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
            toOrder(id,Price,Number,Number2,Total,userPhone,userUserTime,userName);
        }

        function toOrder(id,Price,Number,Number2,Total,userPhone,userUserTime,userName){
            var $loadingToast = $('#loadingToast');
            $loadingToast.fadeIn(100);
            setTimeout(function () {
                $loadingToast.fadeOut(100);
            }, 1200);
            /**
             * 发起提交订单请求（Post）
             * info 请求返回数据（非0 则为订单id，否则订单生成失败）
             */
            prize = prize;
            $.post("${path}/order/placeAnOrderNoUserActivity",{id:id,Price:prize,Number:Number,Number2:Number2,Total:Total,userPhone:userPhone,userUserTime:userUserTime,userName:userName},function(info){
                if(info != -1 && info != -2 && info != -3 && info != -4 && info != 0){
                    window.location.href="${path}/order/payOrderInUser?order_id="+info+"&menuId=${menuId }";
                }else if(info == -1){
                    alertText("发起请求参数错误，请刷新后重试！");
                }else if(info == -2){
                    alertText("购买的商品已经被商家下架！");
                }else if(info == -3){
                    alertText("生成订单编失败，请刷新后重试！");
                }else if(info == -4){
                    alertText("商品库存不足！");
                }else if(info == 0){
                    alertText("订单生成失败，请重试！");
                }
            });

        }


        /**
         * 计算数量
         */
        function changeTotal(){
            var Number = $("#Number").val();
            var Number2 = $("#Number2").val();
            var Price = prize;
            var Price2 = prize2;
            var Total = parseFloat(Price) * Number + parseFloat(Price2) * Number2 ;
            var money = Math.round(Total*100)/100;
            $("#Total1").text('合计：¥'+money);
            $("#Total").text(money);
        }
        /**
         * 减少数量
         */
        function reduceNumber(){
            var Number = parseInt($("#Number").val());
            var Number2 = parseInt($("#Number2").val());
            if(Number < 1){
                Number = 0;
            }else{
                Number = Number - 1;
            }
            if(Number2 > Number){
                Number2 = Number * 2;
            }
            $("#Number").val(Number);
            $("#Number2").val(Number2);
            changeTotal();
        }
        /**
         * 增加数量
         */
        function addNumber(){
            var Number = parseInt($("#Number").val());
            Number = Number + 1;
            $("#Number").val(Number);
            changeTotal();
        }

        /**
         * 减少数量2
         */
        function reduceNumber2(){
            var Number2 = parseInt($("#Number2").val());
            if(Number2 < 1){
                Number2 = 0;
            }
            else{
                Number2 = Number2 - 1;
            }
            $("#Number2").val(Number2);
            changeTotal();
        }
        /**
         * 增加数量2
         */
        function addNumber2(){
            var Number = parseInt($("#Number").val());
            var Number2 = parseInt($("#Number2").val());
            if(Number2 >= Number * 2){
                Number2 = Number * 2;
            }else{
                Number2 = Number2 + 1;
            }

            $("#Number2").val(Number2);
            changeTotal();
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