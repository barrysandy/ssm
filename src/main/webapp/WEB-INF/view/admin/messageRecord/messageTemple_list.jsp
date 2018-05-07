<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>短信模板列表</title>
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
                title: '模板详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/messageTemple/toView?id='+id
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
            var url = "${path}/messageTemple/del";
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

        function add(id,commodityId){
            layer.open({
                type: 2,
                title: '新增模板',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/messageTemple/toEdit?id='+ id + '&commodityId=' + commodityId
            });
        }

        function getTemplates() {
            layer.open({
                type: 2,
                title: '支持的模板ID',
                skin: 'layui-layer-rim',
                area: ['100%', '100%'],
                content: '${path}/messageTemple/getTemplates'
            });
        }

        /** 一键创建模板 */
        function CreateAllTemplates(id) {
            layer.open({
                type: 1,
                title: '为当前的商品一键创建短信模板',
                skin: 'layui-layer-rim',
                area: ['400px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认创建?</div>',
                btn: ['确认','取消'],
                yes: function(){
                    layer.closeAll();
                    sign = prompt("请输入短信模板的签名:","一个半小时旅游圈");
                    if (sign != null){
                        CreateAllTemplatesYes(id,sign);
                    }else{
                        alert("你按了[取消]按钮");
                    }

                },
                btn2: function(){
                    console.log('no');
                }
            });



        }

        function CreateAllTemplatesYes(id,sign) {
            var url = "${path}/messageTemple/createAll";
            $.post(url,{ 'id':id ,'sign':sign },function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >创建成功</div>',
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
                        title: '提示',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >创建失败</div>',
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
        function sumitForm() {
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
                    <form id="listForm" method="post" action="${path}/messageTemple/list">
                        <input type="hidden" name="menuid" value="${menuId}">
                        <input type="hidden" name="status" value="${status}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="add('${menuid}','${commodityId}')">
                                        <i class="iconfont">&#xe61f;</i> 新增
                                    </button>
                                </div>
                                <c:if test="${commodityId != -1 }">
                                    <div class=" panel-head-btnbar">
                                        <button class="u-btn sm success" type="button" onClick="CreateAllTemplates('${commodityId }')">
                                            <i class="iconfont">&#xe6b6;</i> 一键创建模板
                                        </button>
                                    </div>
                                </c:if>
                                <c:if test="${commodityId == -1 }">
                                    <div class=" panel-head-btnbar">
                                        <a class="u-btn sm success" type="button" href="${path}/messageTemple/list">
                                            <i class="iconfont">&#xe60b;</i> 刷新重载
                                        </a>
                                    </div>
                                </c:if>
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="getTemplates()">
                                        <i class="iconfont">&#xe6d8;</i> 支持的模板ID
                                    </button>
                                </div>
                                <div class="panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss search-s" >
                                        <input type="text" class="u-input u-group-left c-input" name="search" value="${search}" placeholder="关键字查询" >
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
                                        <th style="width:80px" align="center">模板ID</th>
                                        <th style="width:10%" align="center">模板签名</th>
                                        <th style="width:80px" align="center">模板所属商品ID</th>
                                        <th style="width:15%" align="center">模板名称</th>
                                        <th style="width:15%" align="center">模板类型</th>
                                        <th style="width:80px" align="center">描述</th>
                                        <th style="width:110px" align="center">创建时间</th>
                                        <th style="width:15%" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.templeId}</td>
                                            <td align="center">${bean.sign}</td>
                                            <td align="center">${bean.commodityId}</td>
                                            <td align="center">${bean.templeName}</td>
                                            <td align="center">
                                                <c:if test="${bean.templeType == 'SINGLE_BUY' }">
                                                    <span style="color: red;">【单一商品】购买</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'SINGLE_BUY_GROUP_SUC_MASS' }">
                                                    <span style="color: orange;">【单一商品】购买成团成功群发短信</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'SINGLE_BUY_GROUP_SUC_TOBUY' }">
                                                    <span style="color: greenyellow;">【单一商品】购买成团时组合购买短信</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'SINGLE_BUY_GROUP_FIAL_MASS' }">
                                                    <span style="color: orange;">【单一商品】购买成团超时失败群发短信</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'GROUP_BUY' }">
                                                    <span style="color: greenyellow;">【组团商品】购买</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'GROUP_BUY_SUC_MASS' }">
                                                    <span style="color: green;">【组团商品】购买成团成功群发短信</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'GROUP_BUY_SUC_TOBUY' }">
                                                    <span style="color: green;">【组团商品】购买成团时组合购买短信</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'GROUP_BUY_FIAL_MASS' }">
                                                    <span style="color: green;">【组团商品】购买成团超时失败群发短信</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'REFUND' }">
                                                    <span style="color: green;">【统一商品】统一退款模板</span>
                                                </c:if>
                                                <c:if test="${bean.templeType == 'WINNING' }">
                                                    <span style="color: tomato;">【会话活动商品】中奖生成订单提醒</span>
                                                </c:if>
                                            </td>
                                            <td align="center">${bean.descM}</td>
                                            <td align="center">${bean.createTime}</td>
                                            <td align="center">
                                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button" onClick="add('${bean.id}','${commodityId}')">
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
