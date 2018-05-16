<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <title>签到人详细</title>
    <%@ include file="/WEB-INF/common_andy.jsp" %>
    <script src="${path }/resources/js/plugins/layer/layer.min.js"></script>
    <script src="${path }/resources/layui/layui.js"></script>
    <script src="${path }/resources/layui/layuiUtil.js"></script>
    <script>
        function cancel() {
            window.parent.location.reload();
//            var index = parent.layer.getFrameIndex(window.name);
//            parent.layer.close(index);
        }
    </script>
</head>
<body>
<div class="g-layout">
    <div class="layout-center">
        <div class="g-max f-p-xs f-p-t-n" id="PrintContentDiv">
            <table class="m-table-forms inline">
                <tr>
                    <td align="center" colspan="8" class="f-b-n" style="font-size:18px">签到人详情</td>
                </tr>
                <tr>
                    <td class="table-header">姓名</td>
                    <td colspan="7">&nbsp;${bean.name}</td>
                </tr>
                <tr>
                    <td class="table-header">机构-公司</td>
                    <td colspan="7">&nbsp;${bean.company}</td>
                </tr>
                <tr>
                    <td class="table-header">类型</td>
                    <td colspan="7">&nbsp;${bean.personType}</td>
                </tr>
                <tr>
                    <td class="table-header">职位</td>
                    <td colspan="7">&nbsp;${bean.position}</td>
                </tr>
                <tr>
                    <td class="table-header">头像</td>
                    <td colspan="7"><img src="${image}" height="40px" width="auto" style="margin-top: 4px;" onerror="this.src='${path}/resources/img/usermen.png'" ></td>
                </tr>
                <tr>
                    <td class="table-header">电话</td>
                    <td colspan="7">&nbsp;${bean.phone}</td>
                </tr>
                <tr>
                    <td class="table-header">签到码</td>
                    <td colspan="7">&nbsp;${bean.signCode}</td>
                </tr>
                <tr>
                    <td class="table-header">是否参加晚宴</td>
                    <td colspan="7">
                        <c:if test="${bean.joinDinner == 0}"><span style="color: red;">不参加</span></c:if>
                        <c:if test="${bean.joinDinner == 1}"><span style="color: green;">参加</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td class="table-header">描述</td>
                    <td colspan="7">&nbsp;${bean.descM}</td>
                </tr>
                 
            </table>
        </div>


    </div>
    <div class="layout-foot f-b-t f-p-t-xs f-bg-light noprint" style="height:40px">
        <div class="f-right f-m-r-xs">
            <button type="button" class="u-btn" onClick="cancel()">关闭</button>
        </div>
    </div>
</div>
<input type="hidden" id="id" value="${bean.id}"/>
<script>
    function layuiUtilMsg(data) {
        LayuiUtil.msg(data);
    }

    $(function() {// 初始化内容
        //延迟刷新
        setTimeout(function(){
            //location.reload();
            var url = "${path}/meeting/interfaceGetMeetingUpdateStatus";
            var id = $("#id").val();
            $.get(url,{'id':id},function(data){
                if(data == 1 || data == "1"){
                    layuiUtilMsg("签到成功。")
                    setTimeout(function(){
                        cancel();
                    }, 500);

                }else{
                    layuiUtilMsg("签到失败!!!")
                    setTimeout(function(){
                        cancel();
                    }, 500);
                }

            });
        }, 1200);

    });
</script>
</body>
</html>
