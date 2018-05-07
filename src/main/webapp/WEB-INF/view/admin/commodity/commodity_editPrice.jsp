<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<head>
    <title>商品价格列表</title>
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
        function add(id,commodityId){
            layer.open({
                type: 2,
                title: '价格编辑器',
                skin: 'layui-layer-rim',
                area: ['80%', '80%'],
                content: '${path}/commodityPrice/toEdit?id='+ id + '&commodityId='+ commodityId
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
            var url = "${path}/commodityPrice/del";
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

        function cancel() {
            window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        /**
         *提交表单
         */
        function submitForm() {
            $("#listForm").submit();
        }

        function setAddress(descM,commodityId) {
            var address;
            address = prompt("请输入要设置的地址"); /*在页面上弹出提示对话框，将用户输入的结果赋给变量total*/
            $.post("${path}/commodityPrice/updateAddress",{address:address,descM:descM,commodityId:commodityId},function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >设置成功</div>',
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
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >设置失败</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }


        function setContacts(descM,commodityId) {
            var contacts;
            contacts = prompt("请输入要设置的联系人姓名"); /*在页面上弹出提示对话框，将用户输入的结果赋给变量total*/
            $.post("${path}/commodityPrice/updateContacts",{contacts:contacts,descM:descM,commodityId:commodityId},function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >设置成功</div>',
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
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >设置失败</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }


        function setContactsPhone(descM,commodityId) {
            var contactsPhone;
            contactsPhone = prompt("请输入要设置的联系人电话"); /*在页面上弹出提示对话框，将用户输入的结果赋给变量total*/
            $.post("${path}/commodityPrice/updateContactsPhone",{contactsPhone:contactsPhone,descM:descM,commodityId:commodityId},function(result){
                if(result.state) {
                    layer.open({
                        type: 1,
                        title: '删除',
                        skin: 'layui-layer-rim',
                        area: ['400px', '200px'],
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >设置成功</div>',
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
                        content: '<div style="padding: 42px 112px; font-size: 16px; color: #808080;" >设置失败</div>',
                        end: function () {
                            location.reload();
                        }
                    });
                }
            }, "json");
        }



    </script>
</head>
<body>
<div class="g-max">
    <div class="m-tabs head-top style01" id="tabs" an-tabs>
        <div class="m-tabs-content">
            <div class="item f-p-xs activate">
                <div class="g-h-max">
                    <form id="listForm" method="post" action="${path}/commodity/toEditPrice">
                        <input type="hidden" id="commodityId" value="${id}">
                        <input type="hidden" name="menuid" value="${menuid}">
                        <input type="hidden" name="sellerId" value="${sellerId}">
                        <div class="g-layout m-panel u-datagrid f-b f-b-n-b">
                            <div class="layout-head panel-head">
                                <div class=" panel-head-btnbar">
                                    <button class="u-btn sm success" type="button" onClick="add('${bean.id}','${id}')">
                                        <i class="iconfont">&#xe61f;</i> 新增
                                    </button>
                                </div>
                                <div class=" panel-head-btnbar">
                                    <a class="u-btn sm success" type="button" href="${path}/commodity/toEditPrice?id=${id}&menuid=${menuid}&sellerId=${sellerId}">
                                        <i class="iconfont">&#xe60b;</i> 刷新重载
                                    </a>
                                </div>
                            </div>
                            <div class="layout-center">
                            <c:forEach var="day" items="${choseDay}" varStatus="status">
                                <button style="font-size: 14px;background-color: rebeccapurple;" type="button" class="u-btn sm success">${day}</button>
                                <button style="font-size: 14px;background-color: grey;border: 0" type="button" class="u-btn sm success" onclick="setAddress('${day}','${id}')">设置地址</button>
                                <button style="font-size: 14px;background-color: grey;border: 0" type="button" class="u-btn sm success" onclick="setContacts('${day}','${id}')">设置联系人</button>
                                <button style="font-size: 14px;background-color: grey;border: 0" type="button" class="u-btn sm success" onclick="setContactsPhone('${day}','${id}')">设置联系人电话</button>
                                <table class="m-table" id="table1" an-datagrid style="width:100%">
                                    <thead>
                                        <tr>
                                            <th align="center" style="width:30px">序号</th>
                                            <th style="width:80px" align="center">时间</th>
                                            <th style="width:80px" align="center">价格名称</th>
                                            <th style="width:80px" align="center">商品价格</th>
                                            <th style="width:80px" align="center">单位名称（ /人 /位 ... ）</th>
                                            <th style="width:80px" align="center">地址</th>
                                            <th style="width:80px" align="center">电话</th>
                                            <th style="width:80px" align="center">联系人</th>
                                            <th style="width:80px" align="center">状态</th>
                                            <th style="width:80px" align="center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    <c:if test="${status.index == 0}">
                                        <c:forEach var="bean" items="${list0}" varStatus="status">
                                            <tr>
                                                <td align="center">${status.index+1}</td>
                                                <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                                <td align="center">${bean.priceName}</td>
                                                <td align="center">${bean.price}</td>
                                                <td align="center">${bean.priceUnit}</td>
                                                <td align="center">${bean.address}</td>
                                                <td align="center">${bean.contacts}</td>
                                                <td align="center">${bean.contactsPhone}</td>
                                                <td align="center">
                                                    <c:if test="${bean.status == 1}">
                                                        <span style="color: green;">显示</span>
                                                    </c:if>
                                                    <c:if test="${bean.status == 0}">
                                                        <span style="color: red;">隐藏</span>
                                                    </c:if>
                                                </td>
                                                <td align="center">
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
                                    </c:if>

                                    <c:if test="${status.index == 1}">
                                        <c:forEach var="bean" items="${list1}" varStatus="status">
                                            <tr>
                                                <td align="center">${status.index+1}</td>
                                                <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                                <td align="center">${bean.priceName}</td>
                                                <td align="center">${bean.price}</td>
                                                <td align="center">${bean.priceUnit}</td>
                                                <td align="center">${bean.address}</td>
                                                <td align="center">
                                                    <c:if test="${bean.status == 1}">
                                                        <span style="color: green;">显示</span>
                                                    </c:if>
                                                    <c:if test="${bean.status == 0}">
                                                        <span style="color: red;">隐藏</span>
                                                    </c:if>
                                                </td>
                                                <td align="center">
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
                                    </c:if>

                                    <c:if test="${status.index == 2}">
                                        <c:forEach var="bean" items="${list2}" varStatus="status">
                                            <tr>
                                                <td align="center">${status.index+1}</td>
                                                <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                                <td align="center">${bean.priceName}</td>
                                                <td align="center">${bean.price}</td>
                                                <td align="center">${bean.priceUnit}</td>
                                                <td align="center">${bean.address}</td>
                                                <td align="center">
                                                    <c:if test="${bean.status == 1}">
                                                        <span style="color: green;">显示</span>
                                                    </c:if>
                                                    <c:if test="${bean.status == 0}">
                                                        <span style="color: red;">隐藏</span>
                                                    </c:if>
                                                </td>
                                                <td align="center">
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
                                    </c:if>

                                    <c:if test="${status.index == 3}">
                                        <c:forEach var="bean" items="${list3}" varStatus="status">
                                            <tr>
                                                <td align="center">${status.index+1}</td>
                                                <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                                <td align="center">${bean.priceName}</td>
                                                <td align="center">${bean.price}</td>
                                                <td align="center">${bean.priceUnit}</td>
                                                <td align="center">${bean.address}</td>
                                                <td align="center">
                                                    <c:if test="${bean.status == 1}">
                                                        <span style="color: green;">显示</span>
                                                    </c:if>
                                                    <c:if test="${bean.status == 0}">
                                                        <span style="color: red;">隐藏</span>
                                                    </c:if>
                                                </td>
                                                <td align="center">
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
                                    </c:if>

                                    <c:if test="${status.index == 4}">
                                        <c:forEach var="bean" items="${list4}" varStatus="status">
                                            <tr>
                                                <td align="center">${status.index+1}</td>
                                                <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                                <td align="center">${bean.priceName}</td>
                                                <td align="center">${bean.price}</td>
                                                <td align="center">${bean.priceUnit}</td>
                                                <td align="center">${bean.address}</td>
                                                <td align="center">
                                                    <c:if test="${bean.status == 1}">
                                                        <span style="color: green;">显示</span>
                                                    </c:if>
                                                    <c:if test="${bean.status == 0}">
                                                        <span style="color: red;">隐藏</span>
                                                    </c:if>
                                                </td>
                                                <td align="center">
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
                                    </c:if>

                                    <c:if test="${status.index == 5}">
                                        <c:forEach var="bean" items="${list5}" varStatus="status">
                                            <tr>
                                                <td align="center">${status.index+1}</td>
                                                <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                                <td align="center">${bean.priceName}</td>
                                                <td align="center">${bean.price}</td>
                                                <td align="center">${bean.priceUnit}</td>
                                                <td align="center">${bean.address}</td>
                                                <td align="center">
                                                    <c:if test="${bean.status == 1}">
                                                        <span style="color: green;">显示</span>
                                                    </c:if>
                                                    <c:if test="${bean.status == 0}">
                                                        <span style="color: red;">隐藏</span>
                                                    </c:if>
                                                </td>
                                                <td align="center">
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
                                    </c:if>
                                    <c:if test="${status.index == 6}">
                                        <c:forEach var="bean" items="${list6}" varStatus="status">
                                            <tr>
                                                <td align="center">${status.index+1}</td>
                                                <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                                <td align="center">${bean.priceName}</td>
                                                <td align="center">${bean.price}</td>
                                                <td align="center">${bean.priceUnit}</td>
                                                <td align="center">${bean.address}</td>
                                                <td align="center">
                                                    <c:if test="${bean.status == 1}">
                                                        <span style="color: green;">显示</span>
                                                    </c:if>
                                                    <c:if test="${bean.status == 0}">
                                                        <span style="color: red;">隐藏</span>
                                                    </c:if>
                                                </td>
                                                <td align="center">
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
                                    </c:if>

                                    </tbody>
                                </table>
                            </c:forEach>
                                <c:if test="${status.index == 1}">
                                    <c:forEach var="bean" items="${list1}" varStatus="status">
                                        <tr>
                                            <td align="center">${status.index+1}</td>
                                            <td align="center">${bean.descM}（${bean.priceTime}）</td>
                                            <td align="center">${bean.priceName}</td>
                                            <td align="center">${bean.price}</td>
                                            <td align="center">${bean.priceUnit}</td>
                                            <td align="center">${bean.address}</td>
                                            <td align="center">
                                                <c:if test="${bean.status == 1}">
                                                    <span style="color: green;">显示</span>
                                                </c:if>
                                                <c:if test="${bean.status == 0}">
                                                    <span style="color: red;">隐藏</span>
                                                </c:if>
                                            </td>
                                            <td align="center">
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
                                </c:if>
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
