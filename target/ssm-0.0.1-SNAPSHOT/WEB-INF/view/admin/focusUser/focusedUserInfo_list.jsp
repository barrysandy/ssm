<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <title>关注用户管理</title>
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
        .select-combo-s .cselect {width:90px;height:25px;line-height: 25px;border: 1px solid #C6C6C6;border-radius: 3px;}

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
                title: '查看详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/focusedUserInfo/toView.htm?id='+id
            });
        }

        function add(menuId){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/focusedUserInfo/toEdit.htm?menuId='+ menuId
            });
        }


        function updateAllUser(menuId){
            layer.open({
                type: 2,
                title: '处理中',
                skin: 'layui-layer-rim',
                area: ['60%', '60%'],
                content: '${path}/focusedUserInfo/toLord?content='
            });
            $.get("${path}/focusedUserInfo/interfaceUpdateUserListFromWeChatAPI",{menuId:menuId},function(data){
                layer.closeAll();
                location.reload();
            });
        }

        /** 能通过微信API/Unicode机制补全不完整的用户的信息（Unicode机制必须保证微信配置中两个微信号具有相同的开放平台关联码） */
        function updateUserInfo(menuId){
            var msg = "确定要使用该功能吗？";
            var con = confirm(msg); //在页面上弹出对话框
            if(con == true){
                $.get("${path}/scan/interfaceScanUpdateUserInfo",{menuId:menuId},function(info){
                    layuiUtilMsg(info);
                });
            };
        }

        function edit(id,menuId) {
            layer.open({
                type: 2,
                title: '修改',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/focusedUserInfo/toEdit.htm?id='+id+'&menuId='+menuId
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
        /**
         *提交表单
         */
        function sumitForm() {
            $("#listForm").submit();
        }
        function deleted(id) {
            var url = "${path}/focusedUserInfo/del.htm";
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

    </script>
</head>
<body>
<div class="g-max">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/focusedUserInfo/toUserListPage.htm">
                        <input type="hidden" name="menuid" value="${menuId}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button id="updateUser" class="u-btn sm success" type="button" onClick="updateAllUser('${parentId}')" title="通过关注列表选举最早关注者（必须保证当前列表至少有一个关注者），来拉取微信公众号关注的粉丝列表，该功能比较耗时也比较消耗资源，一般推荐在服务器相对空闲时或者需求强烈时使用。">
                                        <i class="iconfont">&#xe61f;</i> 完善关注列表
                                    </button>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="updateUserInfo('${parentId}')" title="能通过微信API/Unicode机制补全不完整的用户的信息（Unicode机制必须保证微信配置中两个微信号具有相同的开放平台关联码）">
                                        <i class="iconfont">&#xe61f;</i> 更新用户信息
                                    </button>
                                </div>
                                <div class="panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss calender-d">
                                        <span class="clabel" style="color: #6B6B6B;">关注时间:</span>
                                        <span class="cstart-span">
                                       	    <input type="text" class="laydate-icon u-input" name="startDate" onclick="laydate()" value="${startDate}" placeholder="请选择开始日期">
                                       </span>
                                        <span class="cline">--</span>
                                        <span class="cend-span">
                                       	    <input type="text" class="laydate-icon u-input" name="endDate" onclick="laydate({max:laydate.now()})" value="${endDate}" placeholder="请选择结束日期">
                                         </span>
                                    </div>
                                    <div class="u-group sm f-m-t-xxss search-s" >
                                        <input type="text" class="u-input u-group-left c-input" name="search" value="${search}" placeholder="请输入帐号,昵称或姓名" >
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
                                        <th align="center" style="width:50px;">序号</th>
                                        <th style="width:50px;" align="center">头像</th>
                                        <th style="width:10%" align="center">姓名</th>
                                        <th style="width:10%" align="center">备注</th>
                                        <th style="width:100px;" align="center">电话号码</th>
                                        <th style="width:50px;" align="center">性别</th>
                                        <th style="width:15%" align="center">openid</th>
                                        <th style="width:15%" align="center">unionid</th>
                                        <th style="width:50px;" align="center">关注状态</th>
                                        <th style="width:10%" align="center">关注时间</th>
                                        <th style="width:10%" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center"><img src="${bean.headImgUrl}" width="40" onerror="this.src='${path}/resources/img/usermen.png'" style="margin-top: 4px;"></td>
                                            <td align="center">${bean.nickName}</td>
                                            <td align="center">${bean.username}</td>
                                            <td align="center">${bean.userphone}</td>
                                            <td align="center">${bean.sexStr}</td>
                                            <td align="center">${bean.openid}</td>
                                            <td align="center">${bean.unionid}</td>
                                            <td align="center">
                                                <c:if test="${bean.subscribe == 0}"><span style="color: grey">未关注</span></c:if>
                                                <c:if test="${bean.subscribe == 1}"><span style="color: red">已关注</span></c:if>
                                            </td>
                                            <td align="center">${bean.subscribeTime}</td>
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
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
