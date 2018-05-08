<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>消息列表</title>
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
                title: '订单详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/persistentMessageQueue/toView?id='+id
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
            var url = "${path}/persistentMessageQueue/del";
            $.post(url,{'id':id },function(result){
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
                    <form id="listForm" method="post" action="${path}/persistentMessageQueue/listAllParam">
                        <input type="hidden" name="menuid" value="${menuId}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/persistentMessageQueue/list">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                                <div class="u-group sm f-m-t-xxss select-combo-s" style="width: auto;">
                                    <span class="clabel" style="width: 65px;color: #6B6B6B;">消息等级:</span>
                                    <select style="width: 165px;" class="cselect" name="rank" onchange="sumitForm()">
                                        <option value="-1" selected="selected">全部</option>
                                        <option value="1" <c:if test="${rank == 1}">selected="selected"</c:if> >1 级消息</option>
                                        <option value="2" <c:if test="${rank == 2}">selected="selected"</c:if>>2 级消息</option>
                                        <option value="3" <c:if test="${rank == 3}">selected="selected"</c:if> >3 级消息</option>
                                        <option value="4" <c:if test="${rank == 4}">selected="selected"</c:if>>4 级消息</option>
                                        <option value="5" <c:if test="${rank == 5}">selected="selected"</c:if> >5 级消息</option>
                                        <option value="6" <c:if test="${rank == 6}">selected="selected"</c:if>>6 级消息</option>
                                        <option value="7" <c:if test="${rank == 7}">selected="selected"</c:if> >7 级消息</option>
                                        <option value="8" <c:if test="${rank == 8}">selected="selected"</c:if>>8 级消息</option>
                                        <option value="9" <c:if test="${rank == 9}">selected="selected"</c:if> >9 级消息</option>
                                        <option value="10" <c:if test="${rank == 10}">selected="selected"</c:if>>10 级消息</option>
                                    </select>
                                </div>
                                <div class="u-group sm f-m-t-xxss select-combo-s" style="width: auto;">
                                    <span class="clabel" style="width: 65px;color: #6B6B6B;">消息类型:</span>
                                    <select style="width: 165px;" class="cselect" name="msgFrom" onchange="sumitForm()">
                                        1，系统消息SYSTEM_MQ 2，用户消息 USER_MQ 3，管理员消息 ADMIN_MQ 4，接口消息  INTERFACE_MQ 5，其他消息
                                        <option value="" <c:if test="${msgFrom eq ''}">selected="selected"</c:if> >全部</option>
                                        <option value="SYSTEM_MQ" <c:if test="${msgFrom eq 'SYSTEM_MQ'}">selected="selected"</c:if>>系统消息</option>
                                        <option value="USER_MQ" <c:if test="${msgFrom eq 'USER_MQ'}">selected="selected"</c:if>>用户消息</option>
                                        <option value="ADMIN_MQ" <c:if test="${msgFrom eq 'ADMIN_MQ'}">selected="selected"</c:if>>管理员消息</option>
                                        <option value="INTERFACE_MQ" <c:if test="${msgFrom eq 'INTERFACE_MQ'}">selected="selected"</c:if>>接口消息</option>
                                        <option value="OTHER_MQ" <c:if test="${msgFrom eq 'OTHER_MQ'}">selected="selected"</c:if>>其他消息</option>
                                    </select>
                                </div>
                                <div class="u-group sm f-m-t-xxss select-combo-s" style="width: auto;">
                                    <span class="clabel" style="width: 65px;color: #6B6B6B;">状态: </span>
                                    <select style="width: 165px;" class="cselect" name="status" onchange="sumitForm()">
                                        <option value="-1" selected="selected">全部</option>
                                        <option value="0" <c:if test="${status == 0}">selected="selected"</c:if> >未处理</option>
                                        <option value="1" <c:if test="${status == 1}">selected="selected"</c:if>>已消费</option>
                                    </select>
                                </div>
                                <div class="panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss search-s" >
                                        <input type="text" class="u-input u-group-left c-input" name="search" value="${search}" placeholder="请输关键字查询" >
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
                                        <th style="width:auto" align="center">消息地址</th>
                                        <th style="width:5%" align="center">消息等级</th>
                                        <th style="width:10%" align="center">消息来源</th>
                                        <th style="width:10%" align="center">消息描述</th>
                                        <th style="width:10%" align="center">消息处理结果</th>
                                        <th style="width:5%" align="center">消息状态</th>
                                        <th style="width:10%" align="center">消息预处理时间</th>
                                        <th style="width:10%" align="center">消息消费时间</th>
                                        <th style="width:10%" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.url}</td>
                                            <td align="center">${bean.rank}</td>
                                            <td align="center">${bean.msgFrom}</td>
                                            <td align="center">${bean.descM}</td>
                                            <td align="center">${bean.result}</td>
                                            <td align="center">
                                                <c:if test="${bean.status == 0}"><span style="color: red;">待处理</span></c:if>
                                                <c:if test="${bean.status == 1}"><span style="color: lightskyblue;">已消费</span></c:if>
                                            </td>
                                            <td align="center">${bean.preExecutionTime}</td>
                                            <td align="center">${bean.consumeTime}</td>
                                            <td align="center">
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
