<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>流水列表</title>
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
                title: '流水详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/waterBill/toView?id='+id
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
            var url = "${path}/waterBill/del";
            $.post(url,{'id':id,},function(result){
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

        function getUserInfo(openid) {
            layer.open({
                type: 2,
                title: '用户详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/apiInterface/interfaceGetUserInfo?openid='+openid
            });
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
                    <form id="listForm" method="post" action="${path}/waterBill/listAllParam">
                        <input type="hidden" name="menuid" value="${menuId}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/waterBill/list">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <div class="u-group sm f-m-t-xxss calender-d">
                                        <span class="clabel" style="color: #6B6B6B;">流水时间:</span>
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
                                    <span class="clabel" style="width: 40px;color: #6B6B6B;">状态:</span>
                                    <select style="width: 110px;" class="cselect" name="status" onchange="sumitForm()">
                                        <option value="-1" <c:if test="${status == -1}">selected="selected"</c:if> >全部</option>
                                        <option value="1" <c:if test="${status == 1}">selected="selected"</c:if> >正常</option>
                                        <option value="2" <c:if test="${status == 2}">selected="selected"</c:if> >异常</option>
                                        <option value="3" <c:if test="${status == 3}">selected="selected"</c:if> >已退款</option>
                                        <option value="0" <c:if test="${status == 0}">selected="selected"</c:if>>已删除</option>
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
                                        <th style="width:80px" align="center">流水号</th>
                                        <th style="width:80px" align="center">订单号</th>
                                        <th style="width:80px" align="center">备注</th>
                                        <th style="width:80px" align="center">金额( ￥ )</th>
                                        <th style="width:80px" align="center">用户标识</th>
                                        <th style="width:80px" align="center">创建时间</th>
                                        <th style="width:80px" align="center">状态</th>
                                        <th style="width:80px" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.id}</td>
                                            <td align="center">${bean.orderNo}</td>
                                            <td align="center">${bean.remarks}</td>
                                            <td align="center" style="color: dodgerblue;">${bean.money}</td>
                                            <td align="center" onclick="getUserInfo('${bean.openid }')" style="color: blue;">${bean.openid}</td>
                                            <td align="center"><fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                                            <td align="center">
                                                <c:if test="${bean.status == 1}"><span style="color: green;">正常</span></c:if>
                                                <c:if test="${bean.status == 2}"><span style="color: red;">异常</span></c:if>
                                                <c:if test="${bean.status == 3}"><span style="color: darkmagenta;">已退款</span></c:if>
                                                <c:if test="${bean.status == 0}"><span style="color: orangered;">已删除</span></c:if>
                                            </td>
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
