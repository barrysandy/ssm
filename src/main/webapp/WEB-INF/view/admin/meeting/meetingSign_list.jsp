<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>参会人员列表</title>
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
                title: '参会人员详情',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/meetingSign/toView?id='+id
            });
        }

        function add(id,meetingId){
            layer.open({
                type: 2,
                title: '新增',
                skin: 'layui-layer-rim',
                area: ['95%', '95%'],
                content: '${path}/meetingSign/toEdit?id='+ id + "&meetingId=" + meetingId
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
            var url = "${path}/meetingSign/del";
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

        function doExcle(type) {
            var url = "${path}/meeting/readerExcelAndAddData";
            var id = '${id}';
            var tableName = $("#tableName").val();
            var x = $("#x").val();
            var y = $("#y").val();
            $.get(url,{'id':id,'tableName':tableName,'x':x,'y':y,'type':type},function(data){
                if(data == "0"){
                    layuiUtilMsg("执行完成，数据未发送变化。")
                }else{
                    if(type == "0"){
                        layuiUtilMsg("数据清空成功，新增数据 " + data + " 条。")
                    }else{
                        layuiUtilMsg("数据追加成功，追加数据 " + data + " 条。")
                    }
                }
                //延迟刷新
                setTimeout(function(){
                    location.reload();
                }, 2000);

            });
        }

        //更新晚宴情况
        function updateJoinDinner(id) {
            var url = "${path}/meetingSign/updateJoinDinner";
            $.get(url,{'id':id},function(data){
                if(data == "0"){
                    layuiUtilMsg("更新失败。")
                }else{
                    layuiUtilMsg("更新完成。")
                }
                //延迟刷新
                setTimeout(function(){
                    location.reload();
                }, 1000);

            });
        }

        //下载
        function downExcle(excelPath) {
            window.location.href="${path}/meeting/downloadExcel?excelPath=" + excelPath ;
        }
    </script>
</head>
<body>
<div style="margin-left: 10px;">
    <form id="editForm" action="${path}/meeting/saveOrUpdate" method="post">
        <c:if test="${exit == 0}"><a style="color: green;">还未上传Excel文件</a></c:if>
        <c:if test="${exit == 1}"><a style="color: red;" onclick="downExcle('${meeting.excelPath }')" title="点击下载该文件">当前应用的Excel文件：${meeting.excelPath }</a></c:if>
        <input id="file" type="file" name="file"/>
        <script type="text/javascript" src="${path }/resources/upload/File_Excle.js">
            updateImg = "";//上传文件的返回url
            material_id = "";//上传文件的返回material_id
        </script>
        输入文档标题行：<input type="text" class="u-input" id="x" placeholder="默认7行标题">
        输入文档数据列：<input type="text" class="u-input" id="y" placeholder="默认10条">
        输入执行文档表名：<input type="text" class="u-input" id="tableName" placeholder="默认Sheet1表">
        <input type="hidden" class="u-input" id="inputFile"  name="excelPath" value="${meeting.excelPath}" readonly="readonly">
    </form>
</div>
<div class="g-max">
    <input type="hidden" id="pageNo" value="${pager.pageNumber}">
    <input type="hidden" id="pageSize" value="${pager.pageSize}">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/meetingSign/list?id=${id}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="add('','${id}')">
                                        <i class="iconfont">&#xe61f;</i> 新增
                                    </button>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/meetingSign/list?id=${id}">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" onClick="doExcle(0)" title="将特定格式的excel名单导入到该会议下，此导入方式会清空当前的名单">
                                        <i class="iconfont">&#xe606;</i> 执行默认导入
                                    </a>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" onClick="doExcle(1)" title="将特定格式的excel名单导入到该会议下，此导入方式会把外部文件名单追加到当前的名单中">
                                        <i class="iconfont">&#xe68c;</i> 执行追加导入
                                    </a>
                                </div>
                                <div class="u-group sm f-m-t-xxss select-combo-s" style="width: 150px;">
                                    <span class="clabel" style="width: 40px;color: #6B6B6B;">状态:</span>
                                    <select style="width: 110px;" class="cselect" name="status" onchange="sumitForm()">
                                        <option value="" <c:if test="${status == null || status == -1 }">selected="selected"</c:if>>全部</option>
                                        <option value="0" <c:if test="${status == 0}">selected="selected"</c:if> >未签到</option>
                                        <option value="1" <c:if test="${status == 1}">selected="selected"</c:if> >已签到</option>
                                    </select>
                                </div>
                                <div class="panel-head-btnbar f-right">
                                    <div class="u-group sm f-m-t-xxss search-s" >
                                        <input type="text" class="u-input u-group-left c-input" name="search" value="${search}" placeholder="请输入名字查询" >
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
                                        <th style="width:10%" align="center">姓名</th>
                                        <th style="width:10%" align="center">手机电话</th>
                                        <th style="width:8%" align="center">签到码</th>
                                        <th style="width:8%" align="center">单位-公司-机构</th>
                                        <th style="width:10%;" align="center">类型</th>
                                        <th style="width:5%" align="center">职位</th>
                                        <th style="width:5%" align="center">是否参加晚宴</th>
                                        <th style="width:5%" align="center">签到状态</th>
                                        <th style="width:auto" align="center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bean" items="${pager.list}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.name}</td>
                                            <td align="center">${bean.phone}</td>
                                            <td align="center">${bean.signCode}</td>
                                            <td align="center">${bean.company}</td>
                                            <td align="center">${bean.personType}</td>
                                            <td align="center">${bean.position}</td>
                                            <td align="center">
                                                <c:if test="${bean.joinDinner == 0}"><span style="color: red;">不参加</span></c:if>
                                                <c:if test="${bean.joinDinner == 1}"><span style="color: green;">参加</span></c:if>
                                            </td>
                                            <td align="center">
                                                <c:if test="${bean.status == 0}"><span style="color: red;">未签到</span></c:if>
                                                <c:if test="${bean.status == 1}"><span style="color: green;">已签到</span></c:if>
                                            </td>
                                            <td align="center">
                                                <button class="u-btn sm texture f-m-l-xs" title="更新晚宴" type="button"
                                                        onClick="updateJoinDinner('${bean.id}')">
                                                    <i class="iconfont" style="color: deeppink">&#xe691;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="查看" type="button"
                                                        onClick="view('${bean.id}')">
                                                    <i class="iconfont">&#xe63d;</i>
                                                </button>
                                                <button class="u-btn sm texture f-m-l-xs" title="编辑" type="button"
                                                        onClick="add('${bean.id}','${id}')">
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
<script type="text/javascript">
    $("input[type=file]").change(function(){
        var id = '${id}';
        var path = '${path}';
        doUploadFile(id,path);
    })//选择文件后自动上传
</script>
</html>
