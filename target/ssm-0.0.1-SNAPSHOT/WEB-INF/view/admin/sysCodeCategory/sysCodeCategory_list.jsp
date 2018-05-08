<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>系统字典目录表列表</title>
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

        .m-table tbody tr.current:hover td {
            background-color: #308CFC;
            cursor: pointer;
            color: #fff;
        }

        .g-layout .layout-foot {
            width: 100%;
        }

        table {
            table-layout: fixed;
        }

        #table1 td {
            text-overflow: ellipsis;
            white-space: nowrap;
            overflow: hidden;
        }

        #table1 td:hover {
            text-overflow: clip;
            white-space: normal;
            overflow: visible;
        }
    </style>
    <script>
        function view (id,code) {
            var baseUrl = 'sysCodeCategory';
            if (code != null && code != ''){
                baseUrl = 'sysCode';
            }
            layer.open({
                type: 2,
                title: '查看详情',
                skin: 'layui-layer-rim',
                area: ['90%', '90%'],
                content: '${path}/'+baseUrl+'/toView.htm?id='+id
            });
        }

        function add(code){
            var baseUrl = 'sysCodeCategory';
            var categoryId = '';
            if (code != null && code != ''){
                baseUrl = 'sysCode';
                categoryId = $('#currentCategoryId').val();
                if (categoryId == null || categoryId == ''){
                    LayuiUtil.msg("请选择一个目录");
                    return;
                }
            }
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['90%', '90%'],
                content: '${path}/'+baseUrl+'/toEdit.htm?categoryId=' + categoryId
            });
        }

        function edit(id,code) {
            var baseUrl = 'sysCodeCategory';
            if (code != null && code != ''){
                baseUrl = 'sysCode';
            }
            layer.open({
                type: 2,
                title: '修改',
                skin: 'layui-layer-rim',
                area: ['90%', '90%'],
                content: '${path}/'+baseUrl+'/toEdit.htm?id='+id
            });
        }

        function confirmDelete(id,code) {
            layer.open({
                type: 1,
                title: '删除',
                skin: 'layui-layer-rim',
                area: ['400px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认删除?</div>',
                btn: ['确认','取消'],
                yes: function(){
                    layer.closeAll();
                    deleted(id,code);
                },
                btn2: function(){
                    console.log('no');
                }
            });
        }

        function deleted(id,code) {
            var baseUrl = 'sysCodeCategory';
            if (code != null && code != ''){
                baseUrl = 'sysCode';
            }
            var url = "${path}/"+baseUrl+"/del";
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

        function addCurrentClass(ele,categoryId,menuId) {
            var tr =  $("tr");
            for(var i=0 ;i < tr.length;i++){
                tr[i].setAttribute("class","");
            }
            ele.setAttribute("class","current");
            initCodeList(categoryId,menuId);
            $('#currentCategoryId').val(categoryId);
        }

        function initCodeList(categoryId) {
            var url = '${path}/sysCode/interface/getListByCategoryId';
            $.post(url,{'categoryId':categoryId},function(data){
                var result = data.result;
                var html="";
                for(var i=0;i<result.length;i++) {
                    var status = i+1;
                    var map = result[i];
                    var id = map.id;
                    var codeName = map.name;
                    var codeValue = map.value;
                    var categoryId = map.categoryId;
                    var sort = map.sort;
                    var descM = map.descM;
                    html += "<tr>"
                        + "<td align=\"center\">"+status+"</td>"
                        + "<td align=\"center\">"+codeName+"</td>"
                        + "<td align=\"center\">"+codeValue+"</td>"
                        + "<td align=\"center\">"+descM+"</td>"
                        + "<td align=\"center\">"
                            + "<button class=\"u-btn sm texture f-m-l-xs\" title=\"编辑\" type=\"button\" onClick=\"edit('"+id+"','code')\">"
                                + "<i class=\"iconfont\" style=\"color: blue;\">&#xe619;</i>"
                            + "</button>"
                            + "<button class=\"u-btn sm texture f-m-l-xs\" title=\"查看\" type=\"button\" onClick=\"view('"+id+"','code')\">"
                                + "<i class=\"iconfont\">&#xe63d;</i>"
                            + "</button>"
                            + "<button class=\"u-btn sm texture f-m-l-xs\" title=\"删除\" type=\"button\" onClick=\"confirmDelete('"+id+"','code')\">"
                                + "<i class=\"iconfont\" style=\"color: red;\">&#xe62a;</i>"
                            + "</button>"
                        + "</td></tr>"
                }
                $("#codeList").html(html);
            });

        }

        $(function () {
            $('#firstList').attr('style','height: 213px;');
            $('#secondList').attr('style','height: 213px;');
        })
    </script>
</head>
<body>
<div class="g-layout">
    <div class="layout-center f-b-l">
        <div class="g-h-max m-panel panel-base">
            <form id="listForm" action="${path}/sysCodeCategory/list" method="post">
                <div class=" panel-head">
                    <strong class="title">信息目录</strong>
                    <div class="panel-head-btnbar">
                        <div class="m-btn-group f-clear  f-p-l-sm">
                            <!--左侧按钮工具-->
                            <button class="u-btn sm success" type="button" onclick="add()"><i class="iconfont">&#xe61f;</i> 新增</button>
                            <!--end-->
                        </div>
                    </div>
                    <div class="panel-head-btnbar f-right">
                        <!--搜索工具-->
                        <div class="u-group sm f-m-t-xxss" style="width:200px; display:inline-block; float:left">
                            <input type="text" class="u-input u-group-left" name="search" value="${search}" placeholder="输入关键字...">
                            <span class=" u-but-group u-group-right">搜索
                                <button class="u-but-button" type="button" onclick="submitForm()"></button>
                            </span>
                        </div>
                        <!--end-->
                    </div>
                </div>
            </form>
            <div class="panel-body f-b-n g-h-max" style="color: #58666e;">
                <!--数据列表-->
                <table  class = "m-table" id="codeCategoryTable" an-datagrid style="width:100%">
                    <thead>
                        <tr>
                            <th align="center" style="width:30px">序号</th>
                            <th style="width:80px" align="center">目录名</th>
                            <th style="width:80px" align="center">目录CODE</th>
                            <th style="width:80px" align="center">描述</th>
                            <th style="width:80px" align="center">创建时间</th>
                            <th style="width:80px" align="center">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                        <tr onclick="addCurrentClass(this,'${bean.id}','${menuId}')">
                            <td align="center">${status.index+1}</td>
                            <td align="center">${bean.name}</td>
                            <td align="center">${bean.code}</td>
                            <td align="center">${bean.descM}</td>
                            <td align="center"><fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
                            <td align="center">
                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button" onClick="edit('${bean.id}','${menuId}')">
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
                <!--end-->
            </div>
            <ui:page pager="${pager}"></ui:page>
        </div>
        <input type="hidden" id="currentCategoryId" name="currentCategoryId" value="">
        <div class="g-h-max m-panel panel-base">
            <div class=" panel-head"> <strong class="title">编码列表</strong>
                <div class=" panel-head-btnbar">
                    <div class="m-btn-group f-clear f-p-l">
                        <!--左侧按钮工具-->
                        <button class="u-btn sm success" type="button" onclick="add('code')"><i class="iconfont">&#xe61f;</i> 新增</button>
                        <!--end-->
                    </div>
                </div>
                <%--<div class=" panel-head-btnbar f-right">
                    <!--搜索工具-->
                    <div class="u-group sm f-m-t-xxss" style="width:200px; display:inline-block; float:left">
                        <input type="text" class="u-input u-group-left" name="search" placeholder="输入关键字...">
                        <span class=" u-but-group u-group-right">搜索
                            <button class="u-but-button" type="button"></button>
                        </span>
                    </div>
                    <!--end-->
                </div>--%>
            </div>
            <div class="panel-body f-b-n g-h-max" style="color: #58666e;">
                <!--数据列表-->
                <table  class = "m-table" id="codeTable" an-datagrid style="width:100%">
                    <thead>
                    <tr>
                        <th align="center" style="width:30px">序号</th>
                        <th style="width:80px" align="center">名字</th>
                        <th style="width:80px" align="center">值</th>
                        <th style="width:80px" align="center">描述</th>
                        <th style="width:80px" align="center">操作</th>
                    </tr>
                    </thead>
                    <tbody id="codeList"></tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
