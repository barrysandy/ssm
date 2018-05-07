<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>商品列表</title>
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
                title: '商品详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/commodity/toView?id='+id
            });
        }

        function selectOrder (id,menuid) {
            layer.open({
                type: 2,
                title: '商品订单详情',
                skin: 'layui-layer-rim',
                area: ['100%', '100%'],
                content: '${path}/order/listAllParam?fullListSize=0&list=&pageSize=10&pageNumber=1&pageCount=0&searchId=&sortCriterion=&search=&menuid='+menuid+'&typeState=-1&status=0&date1=0&date2=0&sellerId=-1&userId=-1&commodityId='+ id
            });
        }

        function add(menuid){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['100%', '100%'],
                content: '${path}/commodity/toEdit?menuid='+ menuid
            });
        }

        function edit(id,menuid,sellerId) {
            layer.open({
                type: 2,
                title: '修改',
                skin: 'layui-layer-rim',
                area: ['100%', '100%'],
                content: '${path}/commodity/toEdit.htm?id='+id+'&menuid='+menuid + '&sellerId=' + sellerId
            });
        }

        function editVideo(id,menuid) {
            layer.open({
                type: 2,
                title: '修改',
                skin: 'layui-layer-rim',
                area: ['60%', '60%'],
                content: '${path}/commodity/toEditVideo?id='+id+'&menuid='+menuid
            });
        }



        function editPrice(id,menuid,sellerId) {
            layer.open({
                type: 2,
                title: '商品价格编辑器',
                skin: 'layui-layer-rim',
                area: ['85%', '85%'],
                content: '${path}/commodity/toEditPrice?id='+id+'&menuid='+menuid + '&sellerId=' + sellerId
            });
        }

        function confirmDelete(id) {
            layer.open({
                type: 1,
                title: '删除',
                skin: 'layui-layer-rim',
                area: ['400px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认删除?</div>',
                btn: ['确认','取消'],
                yes: function(){
                    layer.closeAll();
                    deleted(id);
                },
                btn2: function(){
                    console.log('no');
                }
            });
        }

        function deleted(id) {
            var url = "${path}/commodity/del";
            $.post(url,{'id':id},function(result){
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


        /** 显示链接*/
        function copeLink(id) {
            $.get("${path}/commodity/interfaceCopeLink",{'id':id},function(data){
                if(data != null || data != "0"){
                    prompt("请复制输入框内的商品地址。",data);
                }
            });
        }

        //商品上下架操作
        function updateState(id){
            $.get("${path}/commodity/getState",{'id':id},function(data){
                var msg = "";
                if(data == 0){msg = "确定要上架改商品吗？";}
                if(data == 1){msg = "确定要将该商品下架吗？";}
                var con = confirm(msg); //在页面上弹出对话框
                if(con == true){
                    $.get("${path}/commodity/upState",{id:id,commodityState:data},function(){
                        location.reload();
                    });
                };
            });
        }

        function msgTempleas(id) {
            layer.open({
                type: 2,
                title: '商品短信模板管理',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: "${path}/messageTemple/list?searchId=&sortCriterion=&search=&menuid=&status=-1&commodityId="+id
            });
        }
        
        function senMsgAll(id) {
            layer.open({
                type: 1,
                title: '向所有已付款的用户发送消费提醒信息',
                skin: 'layui-layer-rim',
                area: ['400px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认发送?</div>',
                btn: ['确认','取消'],
                yes: function(){
                    layer.closeAll();
                    senMsgAllYes(id);
                },
                btn2: function(){
                    console.log('no');
                }
            });
        }

        function senMsgAllYes(id) {
            //type CONSUME表示消费提醒短信
            var url = "${path}/messageTemple/interfaceSendAllMeg?id="+id+"type=CONSUME";
            $.post(url,{'id':id},function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '删除',
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
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >发送失败</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }

        function cancel() {
            window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        function layuiUtilMsg(data) {
            LayuiUtil.msg(data);
        }

        /**
         *提交表单
         */
        function submitForm() {
            $("#listForm").submit();
        }
    </script>
</head>
<body>
<div class="g-max">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/commodity/list">
                        <input type="hidden" name="menuid" value="${menuid}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/commodity/list">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                                <div class=" panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss"
                                         style="width:200px; display:inline-block; float:left">
                                        <input type="text" class="u-input u-group-left" name="search" value="${search}"
                                               placeholder="输入关键字...">
                                        <span class=" u-but-group u-group-right">搜索
									<button class="u-but-button" type="button" onclick="submitForm()"></button>
									</span>
                                    </div>
                                </div>
                            </div>
                            <div class="layout-center">
                                <table class="m-table" id="table1" an-datagrid style="width:100%">
                                    <thead>
                                    <tr>
                                        <th align="center" style="width:5%">序号 / ID</th>
                                        <th style="width:65px" align="center">商品图片</th>
                                        <th style="width:25%" align="center">商品名称</th>
                                        <th style="width:25%" align="center">类型 / 库存 / 状态 / 显示状态</th>
                                        <th style="width:10%" align="center">所属的商家</th>
                                        <th style="width:35%" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1} / ${bean.id}</td>
                                            <td align="center">
                                                <c:if test="${bean.shareImage != null && bean.shareImage ne ''}">
                                                    <a href="${bean.shareImage}" target="_blank"><img src="${bean.shareImage}" height="40px" width="auto" style="margin-top: 4px;"></a>
                                                </c:if>
                                            </td>
                                            <td align="center">${bean.commodityName}</td>
                                            <td align="center">
                                                <c:if test="${bean.typese == 2}"><span style="color: lightseagreen;">组团商品</span></c:if>
                                                <c:if test="${bean.typese == 1}"><span style="color: blueviolet;">单一商品</span></c:if>
                                                <c:if test="${bean.typese == 0}"><span style="color: orange;">报名商品</span></c:if>
                                                <c:if test="${bean.typese == 3}"><span style="color: orange;">公众号会话报名</span></c:if>

                                                /

                                                <c:if test="${bean.commodityStock == 0}"><span style="color: red;">${bean.commodityStock}(无库存)</span></c:if>
                                                <c:if test="${bean.commodityStock != 0}"><span style="color: green;">${bean.commodityStock}(库存)</span></c:if>

                                                /

                                                <c:if test="${bean.commodityState == 1}">
                                                    <span style="color: green;">已上架</span>
                                                </c:if>
                                                <c:if test="${bean.commodityState == 0}">
                                                    <span style="color: red;">已下架</span>
                                                </c:if>

                                                /

                                                <c:if test="${bean.timeStatus == 1}">
                                                    <span style="color: green;">售卖中</span>
                                                </c:if>
                                                <c:if test="${bean.timeStatus == 0}">
                                                    <span style="color: red;">已过期</span>
                                                </c:if>

                                                /

                                                <c:if test="${bean.showList == 1}">
                                                    <span style="color: dodgerblue;">显示在列表</span>
                                                </c:if>
                                                <c:if test="${bean.showList == 0}">
                                                    <span style="color: darkgrey;">列表隐藏</span>
                                                </c:if>

                                            </td>
                                            <td align="center">${bean.seller.sellerName}</td>
                                            <td align="center">
                                                <button class="u-btn sm texture f-m-l-xs" title="复制链接" type="button" onClick="copeLink('${bean.id}')">
                                                    <i class="iconfont" style="color: blue;">&#xe633;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="商品订单" type="button" onClick="selectOrder('${bean.id}','${menuid}')">
                                                    <i class="iconfont" style="color: blue;">&#xe6b6;</i>
                                                </button>
                                                <c:if test="${bean.commodityState == 1}">
                                                    <button class="u-btn sm texture f-m-l-xs" title="让商品下架" type="button " onclick="updateState(${bean.id })"><i class="iconfont" style="color: red;">&#xe613;</i></button>
                                                </c:if>
                                                <c:if test="${bean.commodityState == 0}">
                                                    <button class="u-btn sm texture f-m-l-xs" title="让商品上架" type="button " onclick="updateState(${bean.id })"><i class="iconfont" style="color: green;">&#xe615;</i></button>
                                                </c:if>

                                                <c:if test="${bean.typese == 0}">
                                                    <button class="u-btn sm texture f-m-l-xs" title="商品价格编辑" type="button"
                                                            onclick="layuiUtilMsg('报名商品不支持价格编辑器')" style="color: grey;">
                                                        <i class="iconfont" >&#xe645;</i>
                                                    </button>
                                                    <button class="u-btn sm texture f-m-l-xs" title="商品短信模板管理" type="button"
                                                            onclick="layuiUtilMsg('报名商品不支持短信模板')"  style="color: grey;">
                                                        <i class="iconfont">&#xe6ee;</i>
                                                    </button>
                                                    <button class="u-btn sm texture f-m-l-xs" title="向所有已付款的用户发送消费提醒信息" type="button"
                                                            onclick="layuiUtilMsg('报名商品不支持价该功能')" style="color: grey;">
                                                        <i class="iconfont">&#xe6ee;</i>
                                                    </button>
                                                </c:if>

                                                <c:if test="${bean.typese != 0}">
                                                    <button class="u-btn sm texture f-m-l-xs" title="商品价格编辑" type="button"
                                                            onClick="editPrice('${bean.id}','${menuid}','${bean.sellerId}')">
                                                        <i class="iconfont" style="color: red;">&#xe645;</i>
                                                    </button>
                                                    <button class="u-btn sm texture f-m-l-xs" title="商品短信模板管理" type="button" onClick="msgTempleas('${bean.id}')" style="color: green;">
                                                        <i class="iconfont">&#xe6ee;</i>
                                                    </button>
                                                    <button class="u-btn sm texture f-m-l-xs" title="向所有已付款的用户发送消费提醒信息" type="button" onClick="senMsgAll('${bean.id}')" style="color: red;">
                                                        <i class="iconfont">&#xe6ee;</i>
                                                    </button>
                                                </c:if>

                                                <button class="u-btn sm texture f-m-l-xs" title="上传视频" type="button"
                                                        onClick="editVideo('${bean.id}','${menuid}')">
                                                    <i class="iconfont" style="color: dodgerblue;">&#xe70a;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button"
                                                        onClick="edit('${bean.id}','${menuid}','${bean.sellerId}')">
                                                    <i class="iconfont" style="color: blue;">&#xe619;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="查看" type="button"
                                                        onClick="view('${bean.id}')">
                                                    <i class="iconfont">&#xe63d;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="删除" type="button"
                                                        onClick="confirmDelete('${bean.id}')">
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
