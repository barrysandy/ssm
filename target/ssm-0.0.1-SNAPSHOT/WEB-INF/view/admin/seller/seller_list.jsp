<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>商家列表</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
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
        function scanUser(id,menuId) {
            layer.open({
                type: 2,
                title: '核销人员管理',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/seller/listScanUser?id='+id+'&menuId='+menuId
            });
        }

        function view (id) {
            layer.open({
                type: 2,
                title: '商家详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/seller/toView?id='+id
            });
        }

        function add(menuid){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/seller/toEdit?menuid='+ menuid
            });
        }

        function edit(id,menuid) {
            layer.open({
                type: 2,
                title: '修改',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/seller/toEdit.htm?id='+id+'&menuid='+menuid
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
            var url = "${path}/seller/del";
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

        /**
         *提交表单
         */
        function submitForm() {
            $("#listForm").submit();
        }

        function addCommodity(sellerId,menuid){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['100%', '100%'],
                content: '${path}/commodity/toEdit?menuid='+ menuid + '&sellerId=' + sellerId
            });
        }

        function selectOrder (id,menuid) {
            layer.open({
                type: 2,
                title: '商品订单详情',
                skin: 'layui-layer-rim',
                area: ['100%', '100%'],
                content: '${path}/order/listAllParam?fullListSize=0&list=&pageSize=10&pageNumber=1&pageCount=0&searchId=&sortCriterion=&search=&menuid='+menuid+'&typeState=-1&status=0&date1=0&date2=0&sellerId='+id+'&userId=-1&commodityId=-1'
            });
        }

    </script>
</head>
<body>
<div class="g-max">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/seller/list">
                        <input type="hidden" name="menuid" value="${menuid}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="add('${menuid}')">
                                        <i class="iconfont">&#xe61f;</i> 新增
                                    </button>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/seller/list">
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
                                        <th align="center" style="width:30px">序号</th>
                                        <th style="width:80px" align="center">商家名称</th>
                                        <th style="width:80px" align="center">所属公众号ID / 商家id（绑定商品时使用）</th>
                                        <th style="width:80px" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.sellerName}</td>
                                            <td align="center">${bean.menuId} / ${bean.id}</td>
                                            <td align="center">

                                                <button class="u-btn sm texture f-m-l-xs" title="添加商品" type="button" onClick="addCommodity('${bean.id}','${bean.menuId}')">
                                                    <i class="iconfont" style="color: blue;">&#xe66f;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="核销人员管理" type="button" onClick="scanUser('${bean.id}','${bean.menuId}')">
                                                    <i class="iconfont" style="color: blue;">&#xe785;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="商家订单" type="button" onClick="selectOrder('${bean.id}','${menuid}')">
                                                    <i class="iconfont" style="color: blue;">&#xe6b6;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button" onClick="edit('${bean.id}','${menuid}')">
                                                    <i class="iconfont" style="color: blue;">&#xe619;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="查看" type="button" onClick="view('${bean.id}')">
                                                    <i class="iconfont">&#xe63d;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="删除" type="button" onClick="confirmDelete('${bean.id}')">
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
