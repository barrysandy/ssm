<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>短信列表</title>
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
                title: '短信详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/messageRecord/toView?id='+id
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
            var url = "${path}/messageRecord/del";
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

        function add(menuid){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/messageRecord/toEdit.htm?menuid='+ menuid
            });
        }

        function currentQueue() {
            layer.open({
                type: 2,
                title: '当前预群发队列',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/messageRecord/preList'
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
                    <form id="listForm" method="post" action="${path}/messageRecord/list">
                        <input type="hidden" name="menuid" value="${menuId}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="add('${menuid}')">
                                        <i class="iconfont">&#xe61f;</i> 新增
                                    </button>
                                    <a class="u-btn sm success" type="button" href="${path}/messageRecord/list">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                    <button class="u-btn sm success" type="button" onClick="addQueueMessage()" title="加入消息预群发队列">
                                        <i class="iconfont">&#xe60b;</i> 加入队列
                                    </button>
                                    <button class="u-btn sm success" type="button" onClick="currentQueue()" title="当前预群发队列">
                                        <i class="iconfont">&#xe66d;</i> 当前队列
                                    </button>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <div class="u-group sm f-m-t-xxss calender-d">
                                        <span class="clabel" style="color: #6B6B6B;">短信发送时间:</span>
                                        <span class="cstart-span" style="width: 175px;">
                                       	    <input type="text" class="laydate-icon u-input" name="date1" onclick="laydate()" value="${date1}" placeholder="请选择开始日期">
                                       </span>
                                        <span class="cline">--</span>
                                        <span class="cend-span" style="width: 175px;">
                                       	    <input type="text" class="laydate-icon u-input" name="date2" onclick="laydate({max:laydate.now()})" value="${date2}" placeholder="请选择结束日期" style="width: 208px;">
                                         </span>
                                    </div>
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
                                        <th align="center" style="width:30px"><input id="all" type="checkbox"/></th>
                                        <th align="center" style="width:30px">序号</th>
                                        <th style="width:80px" align="center">电话号码</th>
                                        <th style="width:80px" align="center">签名</th>
                                        <th style="width:25%" align="center">短信内容</th>
                                        <th style="width:50px" align="center">发送状态</th>
                                        <th style="width:80px" align="center">描述</th>
                                        <th style="width:80px" align="center">发送时间</th>
                                        <th style="width:8%" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center" valign="middle"><input name="checkbox" type="checkbox" value="${bean.id }"/></td>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.mobile}</td>
                                            <td align="center">${bean.sign}</td>
                                            <td align="center">${bean.content}</td>
                                            <td align="center">${bean.responseStatus}</td>
                                            <td align="center">${bean.descM}</td>
                                            <td align="center"><fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm" ></fmt:formatDate></td>
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
<script>
    //全选-反选
    $("#all").click(function(){
        $("input[name='checkbox']").each(function(){
            if($(this).attr("checked"))
            {
                $(this).removeAttr("checked");
            }
            else
            {
                $(this).attr("checked","true");
            }
        })
    })
    //获取值
    arr = "";//记录选择的id
    function addQueueMessage(){
        arr = "";//重置id
        $("input[name='checkbox']:checkbox:checked").each(function(){
            arr = $(this).val() + "," + arr;
        })
        if(arr != "" || arr.length > 0){
            var con = confirm("确定要将当前选择的加入消息预群发队列吗？"); //在页面上弹出对话框
            if(con == true){
                addQueueMessagePost(arr);
            }
        }
        else{
            layer.open({
                type: 1,
                title: '提示',
                skin: 'layui-layer-rim',
                area: ['500px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >提示，请至少选择一项!</div>',
                btn: ['关闭'],
                btn2: function(){
                    layer.closeAll();
                },
                end: function () {
                    location.reload();
                }
            });
        }
    }

    function addQueueMessagePost(arr) {
        var url = "${path}/messageRecord/addQueueMessage";
        $.post(url,{'ids':arr},function(result){
            if(result.state) {
                layer.open({
                    type: 1,
                    title: '提示',
                    skin: 'layui-layer-rim',
                    area: ['400px', '200px'],
                    content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >执行成功</div>',
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
                    content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >执行失败</div>',
                    end: function () {
                        location.reload();
                    }
                });
            }
        }, "json");
    }

</script>
</html>
