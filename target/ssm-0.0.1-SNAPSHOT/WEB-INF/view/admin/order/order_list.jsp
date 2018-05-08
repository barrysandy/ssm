<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>订单列表</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script src="${path }/resources/layui/layui.js"></script>
    <script src="${path }/resources/layui/layuiUtil.js"></script>
    <style>
        .m-table tbody tr td {
            padding: 0 !important;
            vertical-align: middle;
        }

        .status i {
            color: #CCCCCC;
            font-size: 26px;
            width: 26px;
            height: 18px;
            display: block
        }

        /*双日期*/
        .calender-d {  width:auto; display:inline-block; float:left }
        .calender-d .clabel {float:left;height:25px;line-height: 25px; width: auto;margin-right: 10px}
        .calender-d .cstart-span {float:left;margin-right:10px;width: 130px;}
        .calender-d .cline {float:left;margin-right:10px;width: auto;line-height: 22px}
        .calender-d .cend-span {float:left;width: 130px;}

        /*下拉框*/
        .select-combo-s {width:150px; display:inline-block; float:left;margin-left: 20px;}
        .select-combo-s .clabel {float:left;height:25px;line-height: 25px; width: 60px;}
        .select-combo-s .cselect {width:200px;height:25px;line-height: 25px;border: 1px solid #C6C6C6;border-radius: 3px;}

        /*搜索框*/
        .search-s {width:200px; display:inline-block; float:left;margin: 1px 20px 0px 20px}
        .search-s .c-input{line-height: 25px !important;width: 140px}
        .search-s .c-btn{line-height: 25px !important;}

        table {
            table-layout: fixed;
        }

        #table1 td {
            text-overflow: ellipsis;
            white-space: nowrap;
            overflow: hidden;
        }
        #table1 td:hover{
            text-overflow: clip;
            white-space: normal;
            overflow:visible;
        }

    </style>
    <script>
        function view (id) {
            layer.open({
                type: 2,
                title: '订单详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/order/toView?id='+id
            });
        }

        function viewWaterWall (orderNo) {
            layer.open({
                type: 2,
                title: '订单流水详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/waterBill/viewWaterWall?orderNo='+orderNo
            });
        }

        function viewRefund (orderNo) {
            layer.open({
                type: 2,
                title: '订单退款详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/orderRefund/viewRefund?orderNo='+orderNo
            });
        }

        function confirmDelete(id,isDel) {
            layer.open({
                type: 1,
                title: '删除',
                skin: 'layui-layer-rim',
                area: ['400px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认删除?</div>',
                btn: ['确认','取消'],
                yes: function(){
                    layer.closeAll();
                    if(isDel == 0){ isDel = -1; }
                    if(isDel == -1){ isDel = 0; }
                    deleted(id,isDel);
                },
                btn2: function(){
                    console.log('no');
                }
            });
        }

        function deleted(id,isDel) {
            var url = "${path}/order/del";
            $.post(url,{'id':id,'isDel':isDel},function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >删除成功</div>',
                        btn: ['关闭'],
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            location.reload();
                        }
                    });
                }else{
                    layer.open({
                        type: 1,
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >删除失败</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }



        function confirmReurn(id) {
            layer.open({
                type: 1,
                title: '删除',
                skin: 'layui-layer-rim',
                area: ['400px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认申请退款?</div>',
                btn: ['确认','取消'],
                yes: function(){
                    layer.closeAll();
                    applicationRefund(id);
                },
                btn2: function(){
                    console.log('no');
                }
            });
        }

        function applicationRefund(id) {
            var url = "${path}/orderRefund/interfaceApplicationRefund";
            $.post(url,{'id':id},function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '申请结果',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >申请成功</div>',
                        btn: ['关闭'],
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            location.reload();
                        }
                    });
                }else{
                    layer.open({
                        type: 1,
                        title: '申请结果',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >申请失败,请重试！</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }


        /*function sendMsg(id) {
            var url = "/messageRecord/interfaceSendMsg";
            $.post(url,{'id':id,'type':'CONSUME'},function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '发送短信通知',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >发送成功</div>',
                        btn: ['关闭'],
                        btn2: function(){
                            layer.closeAll();
                        },
                        end: function () {
                            location.reload();
                        }
                    });
                }else{
                    layer.open({
                        type: 1,
                        title: '发送短信通知',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >发送失败,请重试！</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }*/


        function getUserInfo(id) {
            layer.open({
                type: 2,
                title: '用户详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/apiInterface/interfaceGetUserInfoByID?id='+id
            });
        }

        function listExcel() {
            var pageNo = $("#pageNo").val();
            var pageSize = $("#pageSize").val();
            var search = "${search}";
            var date1 = "${date1}";
            var date2 = "${date2}";
            var typeState = "${typeState}";
            var status = "${status}";
            var userId = "${userId}";
            var sellerId = "${sellerId}";
            var commodityId = "${commodityId}";
            window.location.href="${path}/order/listExcel?pageNo=" + pageNo +"&pageSize="+pageSize+"&search="+search+"&date1="+date1+"&date2="+date2+"&typeState="+typeState+"&status="+status+"&userId="+userId+"&sellerId="+sellerId+"&commodityId="+commodityId;
        }

        function listExcelAll() {
            var search = "${search}";
            var date1 = "${date1}";
            var date2 = "${date2}";
            var typeState = "${typeState}";
            var status = "${status}";
            var userId = "${userId}";
            var sellerId = "${sellerId}";
            var commodityId = "${commodityId}";
            window.location.href="${path}/order/listExcelAll?search="+search+"&date1="+date1+"&date2="+date2+"&typeState="+typeState+"&status="+status+"&userId="+userId+"&sellerId="+sellerId+"&commodityId="+commodityId;
        }


        function layuiUtilMsg(data) {
            LayuiUtil.msg(data);
        }

        /**
         *提交表单
         */
        function sumitForm() {
            $("#listForm").submit();
        }
    </script>
</head>
<body>
<div class="g-max">
    <input type="hidden" id="pageNo" value="${pager.pageNumber}">
    <input type="hidden" id="pageSize" value="${pager.pageSize}">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/order/listAllParam">
                        <input type="hidden" name="menuid" value="${menuId}">
                        <input type="hidden" name="userId" value="${userId}">
                        <input type="hidden" name="sellerId" value="${sellerId}">
                        <input type="hidden" name="commodityId" value="${commodityId}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/order/list">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" onClick="listExcelAll()" title="导出全部已选择类型的订单到excel">
                                        <i class="iconfont">&#xe606;</i> 导出全部
                                    </a>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" onClick="listExcel()" title="导出当前页面的订单到excel">
                                        <i class="iconfont">&#xe68c;</i> 导出当前
                                    </a>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <div class="u-group sm f-m-t-xxss calender-d">
                                        <span class="clabel" style="color: #6B6B6B;">订单时间:</span>
                                        <span class="cstart-span" style="width: 185px;">
                                       	    <input type="text" class="laydate-icon u-input" name="date1" onclick="laydate()" value="${date1}" placeholder="请选择开始日期">
                                       </span>
                                        <span class="cline">--</span>
                                        <span class="cend-span" style="width: 185px;">
                                       	    <input type="text" class="laydate-icon u-input" name="date2" onclick="laydate({max:laydate.now()})" value="${date2}" placeholder="请选择结束日期">
                                         </span>
                                    </div>
                                </div>
                                <div class="u-group sm f-m-t-xxss select-combo-s" style="width: 150px;">
                                    <span class="clabel" style="width: 40px;color: #6B6B6B;">类型:</span>
                                    <select style="width: 110px;" class="cselect" name="typeState" onchange="sumitForm()">
                                        <option value="-1" <c:if test="${typeState == '-1'}">selected="selected"</c:if> >全部</option>
                                        <option value="0" <c:if test="${typeState == 0}">selected="selected"</c:if>>未付款</option>
                                        <option value="1" <c:if test="${typeState == 1}">selected="selected"</c:if>>已付款</option>
                                        <option value="2" <c:if test="${typeState == 2}">selected="selected"</c:if>>已消费</option>
                                        <option value="3" <c:if test="${typeState == 3}">selected="selected"</c:if>>退款中</option>
                                        <option value="4" <c:if test="${typeState == 4}">selected="selected"</c:if>>已退款</option>
                                        <option value="5" <c:if test="${typeState == 5}">selected="selected"</c:if>>退款失败</option>
                                        <option value="6" <c:if test="${typeState == 6}">selected="selected"</c:if>>赠品订单-待消费</option>
                                    </select>
                                </div>
                                <div class="u-group sm f-m-t-xxss select-combo-s" style="width: 150px;">
                                    <span class="clabel" style="width: 40px;color: #6B6B6B;">状态:</span>
                                    <select style="width: 110px;" class="cselect" name="status" onchange="sumitForm()">
                                        <option value="0" <c:if test="${status == 0}">selected="selected"</c:if> >正常</option>
                                        <option value="-1" <c:if test="${status == -1}">selected="selected"</c:if>>已删除</option>
                                    </select>
                                </div>
                                <div class="panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss search-s" >
                                        <input type="text" class="u-input u-group-left c-input" name="search" value="${search}" placeholder="请输订单号查询" >
                                        <span class=" u-but-group u-group-right c-btn" >搜索
                                                <button class="u-but-button" type="button" onclick="sumitForm()"></button>
                                            </span>
                                    </div>
                                </div>
                            </div>
                            <div class="layout-center">
                                <table class="m-table" id="table1" an-datagrid style="width:100%">
                                    <thead>
                                    <tr>
                                        <th align="center" style="width:30px">序号</th>
                                        <th style="width:80px" align="center">订单名称</th>
                                        <th style="width:65px" align="center">订单图片</th>
                                        <th style="width:12%" align="center">订单号</th>
                                        <th style="width:80px" align="center">下单时间</th>
                                        <th style="width:80px" align="center">订单金额（￥）</th>
                                        <th style="width: auto;max-width:20%;" align="center">订单状态</th>
                                        <th style="width:80px" align="center">订单联系人</th>
                                        <th style="width:80px" align="center">订单联系人电话</th>
                                        <th style="width:80px" align="center">订单消费时间</th>
                                        <th style="width:80px" align="center">订单数量描述</th>
                                        <th style="width:15%" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.orderName}</td>
                                            <td align="center">
                                                <img src="${bean.image}" height="40px" width="auto" style="margin-top: 4px;" onerror="this.src='${path}/resources/img/usermen.png'" >
                                            </td>
                                            <td align="center">${bean.orderNo}</td>
                                            <td align="center">${bean.createTime}</td>
                                            <td align="center">${bean.orderAmountMoney}</td>
                                            <td align="center">
                                                <c:if test="${bean.payType eq 'WECHAT_PAY'}"><span style="color: red;">微信</span></c:if>
                                                <c:if test="${bean.payType eq 'ALIBABA_PAY'}"><span style="color: green;">支付宝</span></c:if>
                                                <c:if test="${bean.typeState == 0}"><span style="color: red;">未付款</span></c:if>
                                                <c:if test="${bean.typeState == 1}"><span style="color: green;">已付款</span></c:if>
                                                <c:if test="${bean.typeState == 2}"><span style="color: grey;">已消费</span></c:if>
                                                <c:if test="${bean.typeState == 3}"><span style="color: orangered;">退款中</span></c:if>
                                                <c:if test="${bean.typeState == 4}"><span style="color: dodgerblue;">已退款</span>(${bean.descM})</c:if>
                                                <c:if test="${bean.typeState == 5}"><span style="color: violet;">退款失败</span>(${bean.descM})</c:if>
                                                <c:if test="${bean.typeState == 6}"><span style="color: lawngreen;">赠品订单-待消费</span></c:if>
                                            </td>
                                            <td align="center" onclick="getUserInfo('${bean.userId }')" style="color: blue;">${bean.userName}</td>
                                            <td align="center" onclick="getUserInfo('${bean.userId }')" style="color: blue;">${bean.userPhone}</td>
                                            <td align="center">${bean.userUseTime}</td>
                                            <td align="center">${bean.numberDescM}</td>
                                            <td align="center">
                                                <c:if test="${bean.typeState != 1  && bean.typeState == 5 }">
                                                    <button class="u-btn sm texture f-m-l-xs" title="退款重试" type="button" onClick="confirmReurn('${bean.id}')">
                                                        <i class="iconfont" style="color: green;">&#xe664;</i>
                                                    </button>
                                                </c:if>
                                                <c:if test="${bean.typeState != 1  && bean.typeState != 5 }">
                                                    <button class="u-btn sm texture f-m-l-xs" title="不可退款" type="button" onclick="layuiUtilMsg('不可退款')">
                                                        <i class="iconfont" style="color: black;">&#xe664;</i>
                                                    </button>
                                                </c:if>
                                                <c:if test="${bean.typeState == 1}">
                                                    <c:if test="${bean.transactionId ne '' && bean.transactionId != null}">
                                                        <button class="u-btn sm texture f-m-l-xs" title="退款" type="button" onClick="confirmReurn('${bean.id}')">
                                                            <i class="iconfont" style="color: red;">&#xe664;</i>
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${bean.transactionId eq '' || bean.transactionId == null}">
                                                        <button class="u-btn sm texture f-m-l-xs" title="支付回调未完成，暂时不可退款" type="button" onclick="layuiUtilMsg('支付回调未完成，暂时不可退款')">
                                                            <i class="iconfont" style="color: lightpink;">&#xe664;</i>
                                                        </button>
                                                    </c:if>
                                                </c:if>

                                                <c:if test="${bean.typeState >= 1}">
                                                    <button class="u-btn sm texture f-m-l-xs" title="查看账单流水" type="button"
                                                            onClick="viewWaterWall('${bean.orderNo}')">
                                                        <i class="iconfont" style="color: red;">&#xe645;</i>
                                                    </button>
                                                </c:if>
                                                <c:if test="${bean.typeState >= 3}">
                                                    <button class="u-btn sm texture f-m-l-xs" title="查看退款处理详情" type="button"
                                                            onClick="viewRefund('${bean.orderNo}')">
                                                        <i class="iconfont" style="color: deeppink;">&#xe62f;</i>
                                                    </button>
                                                </c:if>
                                                <c:if test="${bean.typeState < 3}">
                                                    <button class="u-btn sm texture f-m-l-xs" title="查看退款处理详情" type="button"
                                                            onclick="layuiUtilMsg('该订单未退款')">
                                                        <i class="iconfont" style="color: grey;">&#xe62f;</i>
                                                    </button>
                                                </c:if>
                                                <button class="u-btn sm texture f-m-l-xs" title="查看" type="button"
                                                        onClick="view('${bean.id}')">
                                                    <i class="iconfont">&#xe63d;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="删除" type="button"
                                                        onClick="confirmDelete('${bean.id}','${bean.status}')">
                                                    <i class="iconfont" style="color: red;">&#xe62a;</i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <ui:page pager="${pager}"></ui:page>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
