<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>信誉规则列表</title>
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
        function view(id) {
            layer.open({
                type: 2,
                title: '查看详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/weChatMenu/toView.htm?id=' + id
            });
        }

        function add(menuId) {
            var parentNum = $('#parentList').children('tr').length;
            if (parentNum > 2) {
                LayuiUtil.msg("主菜单最多只能有三个!");
                return;
            }
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/weChatMenu/toEdit.htm?menuId=' + menuId
            });
        }

        function addSecondMenu() {
            var firstMenuId = $('#selectedMenuId').val();
            if (firstMenuId == null || firstMenuId == ''){
                LayuiUtil.msg("请选择一级菜单");
                return;
            }
            var parentNum = $('#codeList').children('tr').length;
            if (parentNum > 4) {
                LayuiUtil.msg("二级菜单最多只能有五个!");
                return;
            }
            layer.open({
                type: 2,
                title: '新增二级菜单',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/weChatMenu/toEdit.htm?firstMenuId=' + firstMenuId
            });
        }

        function editSecondMenu(id) {
            var firstMenuId = $('#selectedMenuId').val();
            layer.open({
                type: 2,
                title: '新增二级菜单',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/weChatMenu/toEdit.htm?firstMenuId=' + firstMenuId + '&id=' + id
            });
        }

        function edit(id, menuId) {
            layer.open({
                type: 2,
                title: '修改',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/weChatMenu/toEdit.htm?id=' + id + '&menuId=' + menuId
            });
        }
        function confirmDelete(id) {
            layer.open({
                type: 1,
                title: '删除',
                skin: 'layui-layer-rim',
                area: ['500px', '300px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认删除?</div>',
                btn: ['确认', '取消'],
                yes: function () {
                    layer.closeAll();
                    deleted(id);
                },
                btn2: function () {
                    console.log('no');
                }
            });
        }
        /**
         *提交表单
         */
        function submitForm() {
            $("#listForm").submit();
        }
        function deleted(id) {
            var url = "${path}/weChatMenu/del.htm";
            $.post(url, {'id': id}, function (result) {
                if (result.state) {
                    layer.open({
                        type: 1,
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['500px', '300px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >删除成功</div>',
                        btn: ['关闭'],
                        btn2: function () {
                            layer.closeAll();
                        },
                        end: function () {
                            location.reload();
                        }
                    });
                } else {
                    layer.open({
                        type: 1,
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['500px', '300px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >删除失败</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }

        function addCurrentClass(ele,firstMenuId) {
            var tr =  $("tr");
            for(var i=0 ;i < tr.length;i++){
                tr[i].setAttribute("class","");
            }
            ele.setAttribute("class","current");
            initSecondMenu(firstMenuId);
            $('#selectedMenuId').val(firstMenuId);
        }


        function initSecondMenu(firstMenuId) {
            var url = '${path}/weChatMenu/interface/getSecondMenuList';
            $.post(url,{'firstMenuId':firstMenuId},function(data){
                var result = data.result;
                var html="";
                for(var i=0;i<result.length;i++) {
                    var status = i+1;
                    var map = result[i];
                    var id = map.id;
                    var menuName = map.menuName;
                    var menuKey = map.menuKey;
                    var parentId = map.parentId;
                    var menuSort = map.menuSort;
                    var menuUrl = map.menuUrl;
                    var descM = map.menuDescribe;
                    html += "<tr>"
                        + "<td align=\"center\">"+status+"</td>"
                        + "<td align=\"center\">"+menuName+"</td>"
                        + "<td align=\"center\">"+menuKey+"</td>"
                        + "<td align=\"center\">"+menuUrl+"</td>"
                        + "<td align=\"center\">"+menuSort+"</td>"
                        + "<td align=\"center\">"+descM+"</td>"
                        + "<td align=\"center\">"
                        + "<button class=\"u-btn sm texture f-m-l-xs\" title=\"编辑\" type=\"button\" onClick=\"editSecondMenu('"+id+"')\">"
                        + "<i class=\"iconfont\" style=\"color: blue;\">&#xe619;</i>"
                        + "</button>"
                        + "<button class=\"u-btn sm texture f-m-l-xs\" title=\"查看\" type=\"button\" onClick=\"view('"+id+"')\">"
                        + "<i class=\"iconfont\">&#xe63d;</i>"
                        + "</button>"
                        + "<button class=\"u-btn sm texture f-m-l-xs\" title=\"删除\" type=\"button\" onClick=\"confirmDelete('"+id+"')\">"
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

        function synWeChatMeun(parentMenuId) {
            $.get("interface/updateWechatMenu",{parentMenuId:parentMenuId},function(data){
                var result = data.result;
                LayuiUtil.msg(result);
            });
        }
    </script>
</head>
<body>
<div class="g-layout">
    <div class="layout-center f-b-l">
        <div class="g-h-max m-panel panel-base">
            <form id="listForm" action="${path}/weChatMenu/list" method="post">
                <input type="hidden" name="menuid" value="${menuId}"/>
                <div class=" panel-head">
                    <strong class="title">一级菜单</strong>
                    <div class="panel-head-btnbar">
                        <div class="m-btn-group f-clear  f-p-l-sm">
                            <!--左侧按钮工具-->
                            <button class="u-btn sm success" type="button" onclick="add('${menuId}')"><i class="iconfont">
                                &#xe61f;</i> 新增
                            </button>
                            <!--左侧按钮工具-->
                            &nbsp;&nbsp;&nbsp;
                            <button class="u-btn sm success" style="margin-left: 10px;" type="button" onclick="synWeChatMeun('${menuId}')"><i class="iconfont">
                                &#xe61f;</i> 同步微信菜单
                            </button>
                            <!--end-->
                        </div>
                    </div>
                    <div class="panel-head-btnbar f-right">
                        <!--搜索工具-->
                        <div class="u-group sm f-m-t-xxss" style="width:200px; display:inline-block; float:left">
                            <input type="text" class="u-input u-group-left" name="search" value="${search}"
                                   placeholder="输入关键字...">
                            <span class=" u-but-group u-group-right">搜索
                                <button class="u-but-button" type="button" onclick="submitForm()"></button>
                            </span>
                        </div>
                        <!--end-->
                    </div>
                </div>
            </form>
            <div class="panel-body f-b-n g-h-max" style="color: #58666e;">
                <table class="m-table" id="table1" an-datagrid style="width:100%">
                    <thead>
                    <tr>
                        <th align="center" style="width:30px">序号</th>
                        <th style="width:80px" align="center">菜单名称</th>
                        <th style="width:80px" align="center">菜单类型</th>
                        <th style="width:80px" align="center">按钮的key</th>
                        <th style="width:80px" align="center">视图菜单链接</th>
                        <th style="width:80px" align="center">菜单描述</th>
                        <th style="width:80px" align="center">排序</th>
                        <th style="width:80px" align="center">创建时间</th>
                        <th style="width:80px" align="center">操作</th>
                    </tr>
                    </thead>
                    <tbody id="parentList">
                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                        <tr onclick="addCurrentClass(this,'${bean.id}')">
                            <td align="center">${status.index+1}</td>
                            <td align="center">${bean.menuName}</td>
                            <td align="center">
                                <c:if test="${bean.menuType eq 'CompleteButton'}"><span style="color: green;">复合型菜单（其下包含子菜单）</span></c:if>
                                <c:if test="${bean.menuType eq 'ClickButton'}"><span style="color: red;">按钮菜单（需要绑定key）ClickButton</span></c:if>
                                <c:if test="${bean.menuType eq 'ViewButton'}"><span style="color: grey;">视图菜单（点击后进入某个页面，需要填写url）ViewButton</span></c:if>
                            </td>
                            <td align="center">
                                <c:if test="${bean.menuType eq 'CompleteButton'}"><span style="color: grey;">-----</span></c:if>
                                <c:if test="${bean.menuType eq 'ClickButton'}"><span style="color: green;">${bean.menuKey}</span></c:if>
                                <c:if test="${bean.menuType eq 'ViewButton'}"><span style="color: grey;">-----</span></c:if>
                            </td>
                            <td align="center">
                                <c:if test="${bean.menuType eq 'CompleteButton'}"><span style="color: grey;">-----</span></c:if>
                                <c:if test="${bean.menuType eq 'ClickButton'}"><span style="color: grey;">-----</span></c:if>
                                <c:if test="${bean.menuType eq 'ViewButton'}"><span style="color: green;">${bean.menuUrl}</span></c:if>
                            </td>
                            <td align="center">${bean.menuDescribe}</td>
                            <td align="center">${bean.menuSort}</td>
                            <td align="center"><fmt:formatDate value="${bean.createTime}"
                                                               pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
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
            </div>
            <ui:page pager="${pager}"></ui:page>
        </div>
        <input type="hidden" id="selectedMenuId" name="selectedMenuId" value="">
        <div class="g-h-max m-panel panel-base">
            <div class=" panel-head"><strong class="title">二级菜单</strong>
                <div class=" panel-head-btnbar">
                    <div class="m-btn-group f-clear f-p-l">
                        <!--左侧按钮工具-->
                        <button class="u-btn sm success" type="button" onclick="addSecondMenu()"><i class="iconfont">
                            &#xe61f;</i> 新增
                        </button>
                        <!--end-->
                    </div>
                </div>
            </div>
            <div class="panel-body f-b-n g-h-max" style="color: #58666e;">
                <!--数据列表-->
                <table class="m-table" id="codeTable" an-datagrid style="width:100%">
                    <thead>
                    <tr>
                        <th align="center" style="width:30px">序号</th>
                        <th style="width:80px" align="center">菜单名字</th>
                        <th style="width:80px" align="center">菜单key</th>
                        <th style="width:80px" align="center">菜单Url</th>
                        <th style="width:80px" align="center">排序</th>
                        <th style="width:80px" align="center">菜单描述</th>
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
