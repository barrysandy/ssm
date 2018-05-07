<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common_andy.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="${path}/resources/RandomDraw/css/style.css" />
    <script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
    <script src="${path}/resources/mobile/mall/js/jquery.min.js"></script>
</head>
<body>
<input type="hidden" id="ids" width="800px;" value=""/>
<div class='luck-back'>
    <div class="luck-content ce-pack-end">
        <div id="luckuser" class="slotMachine">
            <div class="slot">
                <span class="name">姓名</span>
            </div>
        </div>
        <div class="luck-content-btn">
            <a id="start" class="start" onclick="start()">开始</a>
        </div>
        <div class="luck-user">
            <div class="luck-user-title">
                <span>中奖名单</span>
            </div>
            <ul class="luck-user-list"></ul>
            <div class="luck-user-btn">
                <a href="JavaScript:;" onclick="subit()">确定</a>
            </div>
        </div>
    </div>

</div>
<input type="hidden" id="name" value="${name }"/>
<input type="hidden" id="max" value="${max }"/>
<input type="hidden" id="headImg" value="${headImg }"/>
<input type="text" id="id" value="${id }"/>
<script src="${path}/resources/RandomDraw/js/jquery-2.2.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    ids = "";
    name = $("#name").val();
    max = $("#max").val();
    headImg = $("#headImg").val();
    id = $("#id").val();

    phone = new Array();
    xinm = new Array();
    phone = name.split("、"); //字符分割
    xinm = headImg.split("、"); //字符分割

    function subit(){//提交
        if(ids.length == 0){
            alert("请点击开始进行随机抽取中奖者！");
        }else if(ids.length > 0){
            $.post("${path}/activityDraw/UpdateDrawFractionalline",{strArrStu:ids,id:id},function(info){
                alert(info);
            });
        }
    }


    var nametxt = $('.slot');
    var phonetxt = $('.name');
    var pcount = xinm.length-1;//参加人数
    var runing = true;
    var trigger = true;
    var num = 0;
    var Lotterynumber = max; //设置单次抽奖人数

    $(function () {
        nametxt.css('background-image','url('+xinm[0]+')');
        phonetxt.html(phone[0]);
    });

    // 开始停止
    function start() {

        if (runing) {

            if ( pcount <= Lotterynumber ) {
                alert("抽奖人数不足"+max+"人");
            }else{
                runing = false;
                $('#start').text('停止');
                startNum()
            }

        } else {
            $('#start').text('自动抽取中('+ Lotterynumber+')');
            zd();
        }

    }

    // 开始抽奖

    function startLuck() {
        runing = false;
        $('#btntxt').removeClass('start').addClass('stop');
        startNum();
    }

    // 循环参加名单
    function startNum() {
        num = Math.floor(Math.random() * pcount);
        nametxt.css('background-image','url('+xinm[num]+')');
        phonetxt.html(phone[num]);
        t = setTimeout(startNum, 0);
    }

    // 停止跳动
    function stop() {
        pcount = xinm.length-1;
        clearInterval(t);
        t = 0;
    }

    // 打印中奖人

    function zd() {
        if (trigger) {

            trigger = false;
            var i = 0;

            if ( pcount >= Lotterynumber ) {
                stopTime = window.setInterval(function () {
                    if (runing) {
                        runing = false;
                        $('#btntxt').removeClass('start').addClass('stop');
                        startNum();
                    } else {
                        runing = true;
                        $('#btntxt').removeClass('stop').addClass('start');
                        stop();

                        i++;
                        Lotterynumber--;

                        $('#start').text('自动抽取中('+ Lotterynumber+')');

                        if ( i == max ) {
                            console.log("抽奖结束");
                            window.clearInterval(stopTime);
                            $('#start').text("抽奖结束");
                            Lotterynumber = max;
                            trigger = true;
                            //alert(ids);
                        };
                        if ( i <= max ) {
                            //alert(phone[num]);
                            ids = ids + phone[num]+"、";
                            $("#ids").val(ids);
                            //打印中奖者名单
                            $('.luck-user-list').prepend("<li><div class='portrait' style='background-image:url("+xinm[num]+")'></div><div class='luckuserName'>"+phone[num]+"</div></li>");
                            $('.modality-list ul').append("<li><div class='luck-img' style='background-image:url("+xinm[num]+")'></div><p>"+phone[num]+"</p></li>");
                            //将已中奖者从数组中"删除",防止二次中奖
                            xinm.splice($.inArray(xinm[num], xinm), 1);
                            phone.splice($.inArray(phone[num], phone), 1);
                        };
                    }
                },1000);
            };
        }
    }
</script>
</body>
</html>