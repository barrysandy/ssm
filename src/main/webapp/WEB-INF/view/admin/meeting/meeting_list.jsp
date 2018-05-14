<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>会议列表</title>
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
                title: '会议详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/meeting/toView?id='+id
            });
        }

        function add(id){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/meeting/toEdit?id='+ id
            });
        }

        function viewMeetingSign(id) {
            layer.open({
                type: 2,
                title: '会议签到列表',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/meetingSign/list?id='+id
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
            var url = "${path}/meeting/del";
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

        function msgTempleas(refId) {
            layer.open({
                type: 2,
                title: '会议短信模板管理',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: "${path}/messageTemple/listMeeting?search=&refId="+refId + "&refType=meeting"
            });
        }


        function sendMeetingMessage(id) {
            layer.open({
                type: 1,
                title: '开始群发短信嘛',
                skin: 'layui-layer-rim',
                area: ['400px', '200px'],
                content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >确认发送?</div>',
                btn: ['确认','取消'],
                yes: function(){
                    layer.closeAll();
                    sendMeetingMessageYes(id);
                },
                btn2: function(){
                    console.log('no');
                }
            });
        }
        function sendMeetingMessageYes(id) {
            var url = "${path}/meeting/sendMeetingMessage";
            $.get(url,{'id':id},function(result){
                layuiUtilMsg("发送短信:" + result + "条");
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
    <input type="hidden" id="pageNo" value="${pager.pageNumber}">
    <input type="hidden" id="pageSize" value="${pager.pageSize}">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/meeting/list">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="add('-1')">
                                        <i class="iconfont">&#xe61f;</i> 新增
                                    </button>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/meeting/list">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                                <div class="u-group sm f-m-t-xxss select-combo-s" style="width: 150px;">
                                    <span class="clabel" style="width: 40px;color: #6B6B6B;">状态:</span>
                                    <select style="width: 110px;" class="cselect" name="status" onchange="sumitForm()">
                                        <option value="" <c:if test="${status == null || status == -1 }">selected="selected"</c:if>>全部</option>
                                        <option value="1" <c:if test="${status == 1}">selected="selected"</c:if> >已启用</option>
                                        <option value="0" <c:if test="${status == 0}">selected="selected"</c:if> >未启用</option>
                                    </select>
                                </div>
                                <div class="panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss search-s" >
                                        <input type="text" class="u-input u-group-left c-input" name="search" value="${search}" placeholder="请输标题查询" >
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
                                        <th align="center" style="width:5%">序号</th>
                                        <th style="width:10%" align="center">会议标题</th>
                                        <th style="width:10%" align="center">会议封面</th>
                                        <th style="width:8%" align="center">会议联系人</th>
                                        <th style="width:8%" align="center">会议联系人电话</th>
                                        <th style="width:18%;" align="center">会议时间</th>
                                        <th style="width:5%" align="center">签到总人数</th>
                                        <th style="width:5%" align="center">状态</th>
                                        <th style="width:auto" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.title}</td>
                                            <td align="center">
                                                <img src="${bean.image}" height="40px" width="auto" style="margin-top: 4px;" onerror="this.src='${path}/resources/img/usermen.png'" >
                                            </td>
                                            <td align="center">${bean.name}</td>
                                            <td align="center">${bean.phone}</td>
                                            <td align="center">${bean.beginTime} - ${bean.endTime}</td>
                                            <td align="center">${bean.signTotal}</td>
                                            <td align="center">
                                                <c:if test="${bean.status == 1}"><span style="color: green;">已启用</span></c:if>
                                                <c:if test="${bean.status == 0}"><span style="color: red;">未启用</span></c:if>
                                            </td>
                                            <td align="center">
                                                <a class="u-btn sm texture f-m-l-xs" title="开始签到" type="button"
                                                        href="https://www.baidu.com/" target="_blank">
                                                    <i class="iconfont" style="color: green">&#xe62f;</i>
                                                </a>
                                                <button class="u-btn sm texture f-m-l-xs" title="查看参会人员" type="button"
                                                        onClick="viewMeetingSign('${bean.id}')">
                                                    <i class="iconfont" style="color: deeppink">&#xe785;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="会议短信模板管理" type="button" onClick="msgTempleas('${bean.id}')" style="color: green;">
                                                    <i class="iconfont">&#xe6ee;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="向所有参会人员发送提醒信息" type="button" onClick="sendMeetingMessage('${bean.id}')" style="color: red;">
                                                    <i class="iconfont">&#xe6ee;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="查看" type="button"
                                                        onClick="view('${bean.id}')">
                                                    <i class="iconfont">&#xe63d;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button"
                                                        onClick="add('${bean.id}')">
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
