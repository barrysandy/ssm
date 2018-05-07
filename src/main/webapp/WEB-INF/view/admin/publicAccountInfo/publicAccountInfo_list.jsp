<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>公众号列表</title>
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
            content: '${path}/publicAccountInfo/toView.htm?id='+id
        });
    }

    function edit(id,menuId) {
        layer.open({
            type: 2,
            title: '修改',
            skin: 'layui-layer-rim',
            area: ['95%', '95%'],
            content: '${path}/publicAccountInfo/toEdit.htm?id='+id+'&menuId='+menuId
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
        var url = "${path}/publicAccountInfo/del.htm";
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
    </script>
</head>
<body>
<div class="g-max">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/publicAccountInfo/list">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss select-combo-s" style="width: 110px;">
                                        <span class="clabel" style="width: 35px;color: #6B6B6B;">状态:</span>
                                        <select style="width: 70px;" class="cselect" name="usable" onchange="submitForm()">
                                            <option value="-1" <c:if test="${usable == -1}">selected="selected"</c:if> >全部</option>
                                            <option value="1" <c:if test="${usable == 1}">selected="selected"</c:if>>已启用</option>
                                            <option value="0" <c:if test="${usable == 0}">selected="selected"</c:if>>未启用</option>
                                        </select>
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
                                            <th align="center" style="width:30px">序号</th>
                                            <th style="width:80px" align="center">公众帐号名称</th>
                                            <th style="width:80px" align="center">类型</th>
                                            <th style="width:80px" align="center">描述</th>
                                            <th style="width:80px" align="center">开放平台关联码</th>
                                            <th style="width:80px" align="center">TOKEN/授权ID</th>
                                            <th style="width:40px" align="center">是否启用</th>
                                            <th style="width:120px;" align="center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.accountName}</td>
                                            <td align="center">
                                                <c:if test="${bean.accountType == '1'}"><span style="color: black">个人号</span></c:if>
                                                <c:if test="${bean.accountType == '2'}"><span style="color: greenyellow">服务号</span></c:if>
                                                <c:if test="${bean.accountType == '3'}"><span style="color: dodgerblue">订阅号</span></c:if>
                                                <c:if test="${bean.accountType == '4'}"><span style="color: red">企业号</span></c:if>
                                                <c:if test="${bean.accountType == '5'}"><span style="color: greenyellow">小程序</span></c:if>
                                                <c:if test="${bean.accountType == '6'}"><span style="color: grey">测试号</span></c:if>
                                            </td>
                                            <td align="center">${bean.descM}</td>
                                            <td align="center">${bean.openPlatform}</td>
                                            <td align="center">${bean.token} / ${bean.parentMenuId}</td>
                                            <td align="center">
                                                <c:if test="${bean.usable == 1}">
                                                    <label style="color: green">${bean.usableStr}</label>
                                                </c:if>
                                                <c:if test="${bean.usable == 0}">
                                                    <label style="color: red">${bean.usableStr}</label>
                                                </c:if>
                                            </td>
                                            <td align="center">
                                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button" onClick="edit('${bean.id}')">
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
