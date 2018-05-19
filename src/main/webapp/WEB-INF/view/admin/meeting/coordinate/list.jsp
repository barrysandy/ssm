<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>地图坐标列表</title>
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
                title: '点位详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/meetingCoordinate/toView?id='+id
            });
        }

        function add(id,meetingId){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/meetingCoordinate/toEdit?id='+ id + '&meetingId=' + meetingId
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
            var url = "${path}/meetingCoordinate/del";
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
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/meetingCoordinate/list?meetingId=${meetingId}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="add('-1','${meetingId}')">
                                        <i class="iconfont">&#xe61f;</i> 新增
                                    </button>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/meetingCoordinate/list?meetingId=${meetingId}">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                            </div>
                            <div class="layout-center">
                                <table class="m-table" id="table1" an-datagrid style="width:100%">
                                    <thead>
                                    <tr>
                                        <th align="center" style="width:5%">序号</th>
                                        <th style="width:10%" align="center">坐标</th>
                                        <th style="width:10%" align="center">经度</th>
                                        <th style="width:8%" align="center">维度</th>
                                        <th style="width:8%" align="center">点位名称</th>
                                        <th style="width:18%;" align="center">点位描述</th>
                                        <th style="width:auto" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.coordinate}</td>
                                            <td align="center">${bean.x}</td>
                                            <td align="center">${bean.y}</td>
                                            <td align="center">${bean.name}</td>
                                            <td align="center">${bean.descM}</td>
                                            <td align="center">
                                                <button class="u-btn sm texture f-m-l-xs" title="查看" type="button"
                                                        onClick="view('${bean.id}')">
                                                    <i class="iconfont">&#xe63d;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button"
                                                        onClick="add('${bean.id}','${meetingId}')">
                                                    <i class="iconfont" style="color: blue;">&#xe619;</i>
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
