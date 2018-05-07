/*selector */
/**
 * 选择器组件
 * author:林耘宇
 **/
    //必须引入z-tree

(function ($) {
    $.fn.extend({
        an_selector: function () {
            var options = {}, METHODS = {};
            var funstyle = "";
            for (var i = 0; i < arguments.length; i++) {
                // console.log(arguments[0]);
                var a = arguments[0];
                if (typeof a == "object") {
                    options = a;
                } else if (typeof a == "string") {
                    funstyle = a;
                }
            }
            ;

            if (funstyle != "") {
                if (METHODS[funstyle]) {
                    //有mothed则调用之
                    return METHODS[funstyle](this, arguments[1]);
                }
                throw 'The method of' + funstyle + 'is undefined';
                return false;
            } else {
                //合并设置
                var _options = $.extend({
                    treeid: "",//ztree dom的id
                    selects:true,//是否多选 true为多选
                    url: "",//传入ztree结构json
                    nodes: [],//传入本地数据同步加载-ztree
                    rightNodes: [],//已选好的数据
                    getUrl: "",//获取地址数据链接 节点数据连接
                    searchText: "输入工号/名字",
                    leftTitle: "部门结构",
                    setting: "",//默认ztree配置
                    //clickTree: function (url, treeId, text)//自己组织点击的数据 传入一个闭包函数 function(id){return function(){}}
                    submit: function () {
                    }
                }, options);

                var sel = $(this);
                var center = sel.find("[center]");
                var right = sel.find("[right]");
                var choose = sel.find("[choose]");//全选按钮
                var cancel = sel.find("[cancel]");//全部取消按钮
                var submit = sel.find("[submit]");//提交按钮
                var inputText = sel.find("[inputText]");//搜索框
                var search = sel.find("[search]");//搜索按钮
                var leftTitle = sel.find("[leftTitle]");//左边标题
                var chooseData = [];
                var currentTreeNode = "";

                //树设置
                var default_setting = {                    
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };
                
                var setting = {
                    callback: {
                        onClick: function (event, treeId, treeNode) {
                            currentTreeNode = treeNode;
                            onTreeClick(currentTreeNode);
                        }
                    }
                };
                if (_options.setting != "") {
                    setting = $.extend(true,setting, _options.setting,default_setting);
                }
                 
                setting.callback.onClick = function (event, treeId, treeNode) {
                    currentTreeNode = treeNode;
                    onTreeClick(currentTreeNode);
                };

                var tree = "";
                // 判断是否加载ztree结构json
                if (_options.url == "") {
                    $.fn.zTree.init($("#" + _options.treeid), setting, _options.nodes);
                    tree = $("#" + _options.treeid);
                } else {
                    andy.loaddata(_options.url, function (data) {
                        data = andy.stringToJson(data);
                        $.fn.zTree.init($("#" + _options.treeid), setting, data);
                        tree = $("#" + _options.treeid);
                    })
                }

                //已选框是否有数据
                if (_options.rightNodes.length > 0) {
                    for (var i = 0; i < _options.rightNodes.length; i++) {
                        var data = _options.rightNodes[i];
                        addToRight(data);
                    }
                }
                ;

                // 左边标题
                if (leftTitle) {
                    leftTitle.text(_options.leftTitle);
                }
                ;

                // 进行全选操作
                if (choose) {
                    if(_options.selects == false){
                        choose.addClass("f-hidden");
                    }
                    choose.click(function () {
                        if (center) {
                            center.children().each(function (index, e) {
                                for (var i = 0; i < chooseData.length; i++) {
                                    if ($(e).attr("id") == chooseData[i].id) {
                                        clearSelf($(e));
                                        addToRight(chooseData[i]);
                                    }
                                }
                            })
                        }
                    })
                }
                ;

                // 进行全部取消操作
                if (cancel) {
                    if(_options.selects == false){
                        cancel.addClass("f-hidden");
                    }
                    cancel.click(function () {
                        if (right) {
                            right.children().each(function (index, e) {
                                clearSelf($(e));
                                changeCenterFrame(chooseData);
                            })
                        }
                    })
                }


                // 提交按钮
                if (submit) {
                    submit.click(function () {
                        _options.submit(getChooseJson());
                    })
                }


                //搜索按钮
                if (search) {
                    search.click(function () {
                        onTreeClick(currentTreeNode, true);
                    });
                }

                if (inputText) {
                    inputText.val(_options.searchText);
                    inputText.blur(function (e) {
                        if ($(e.target).val() == "") {
                            inputText.val(_options.searchText);
                        }
                    });
                    inputText.focus(function (e) {
                        if ($(e.target).val() == _options.searchText) {
                            inputText.val("");
                        }

                    });
                }

                // 树点击事件 event, treeId, treeNode
                function onTreeClick(treeNode, isSearch) {
                    var params = {}, url = _options.getUrl;
                    var text = "";
                    if (url != "") {
                        if (inputText.val() != _options.searchText && isSearch) {
                            params.search = inputText.val();
                        }
                        params.id = treeNode.id;
                        if (_options.clickTree) {
                            params = _options.clickTree(treeNode);
                        }
                        andy.loaddata(url, params, function (data) {
                            data = andy.stringToJson(data);
                            chooseData = data;
                            changeCenterFrame(data);
                        })
                    }
                };

                function changeCenterFrame(data) {
                    if (center || data) {
                        center.empty();
                        for (var i = 0; i < data.length; i++) {
                            var d = data[i];
                            if (!checkId(right, d.id)) {
                                addToCenter(d);
                            }
                        }
                    }

                };

                // 添加人员到中间
                function addToCenter(data) {
                    if (center) {
                        if (checkDataId(chooseData, data.id) && !checkId(center, data.id)) {
                            center.append("<button class='u-btn sm' id = " + data.id + " data = " + data.data + " name = " + data.name + ">" + data.name + "</button>");
                            var button = $("#" + data.id);
                            button.click(function (e) {
                                clearSelf(button);
                                addToRight(data);
                            })
                        }
                    }
                };

                // 添加人员到右侧
                function addToRight(data) {
                    if (right) {
                        if (!checkId(right, data.id) && _options.selects) {
                            right.append("<button class='u-btn sm' id = " + data.id + " data = " + data.data + " name = " + data.name + ">" + data.name + "<i class='iconfont'>&#xe602;</i></button>");
                            var button = $("#" + data.id);
                            button.click(function (e) {
                                clearSelf(button);
                                addToCenter(data);
                            })
                        }else if(_options.selects == false){
                            // 单选人员
                            right.children('button').each(function(i, button){
                                var btn = $(button);
                                clearSelf(btn);
                                var btnData = {
                                    id:btn.attr('id'),
                                    name:btn.attr('name'),
                                    data:btn.attr('data')
                                }
                                addToCenter(btnData);
                            })
                            right.append("<button class='u-btn sm' id = " + data.id + " data = " + data.data + " name = " + data.name + ">" + data.name + "<i class='iconfont'>&#xe602;</i></button>");
                            var button = $("#" + data.id);
                            button.click(function (e) {
                                clearSelf(button);
                                addToCenter(data);
                            })
                        }
                    }
                };

                // 查询对象是否有相同id
                function checkId(element, id) {
                    var ishave = false;
                    element.children().each(function (index, e) {
                        if (id == $(e).attr("id")) {
                            ishave = true;
                        }
                    })
                    return ishave;
                };

                // 检查数组是否有相同id
                function checkDataId(data, id) {
                    var ishave = false;
                    for (var i = 0; i < data.length; i++) {
                        var d = data[i];
                        if (id == d.id) {
                            ishave = true;
                        }
                    }
                    ;
                    return ishave;
                }

                //移除自己
                function clearSelf(element) {
                    element.unbind('click');
                    element.remove();
                };

                //获取被选框对象 生成数据后
                function getChooseJson() {
                    var str = "[";
                    right.children().each(function (index, e) {
                        var $this = $(e);
                        var s = "{'id':'" + $this.attr("id") + "','name':'" + $this.attr("name") + "','data':'" + $this.attr("data") + "', 'tId':'" + $this.attr("tId") + "'}";
                        if (str == "[") {
                            str += s;
                        } else {
                            str += "," + s;
                        }

                    })
                    str += "]";
                    var jstr = eval('(' + str + ')');
                    return jstr;
                };
            }
        },
        //属性选择器 一般用在部门选择器
        an_selectorTree: function () {
            var options = {};
            var funstyle = "";
            for (var i = 0; i < arguments.length; i++) {
                // console.log(arguments[0]);
                var a = arguments[0];
                if (typeof a == "object") {
                    options = a;
                } else if (typeof a == "string") {
                    funstyle = a;
                }
            }
            ;

            if (funstyle != "") {
                //方法写入
                // if(funstyle == "total"){
                //     $(this).trigger("total", options.total);
                // }
            } else {
                //合并设置
                var _options = $.extend({
                    treeid: "",//ztree dom的id
                    selects:true,//是否多选 true为多选
                    url: "",//传入ztree结构json
                    nodes: [],//传入本地数据同步加载-ztree
                    rightNodes: [],//已选好的数据
                    leftTitle: "部门结构",
                    submit: function () {
                    }
                }, options);

                var sel = $(this);
                var right = sel.find("[right]");
                var cancel = sel.find("[cancel]");//全部取消按钮
                var submit = sel.find("[submit]");//提交按钮
                var leftTitle = sel.find("[leftTitle]");//左边标题
                var chooseData = [];
                var currentTreeNode = "";
                var treeObject = $.fn.zTree.getZTreeObj(_options.treeid);

                //树设置
                var setting = {
                    treeId: "1",
                    treeObj: null,
                    callback: {
                        onCheck: function (event, treeId, treeNode) {
                            onTreeCheck(treeNode);
                        }
                    },
                    check: {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: {"Y": "s", "N": "s"}
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };

                if(_options.selects == false){
                    setting.check = {
                        enable:true,
                        chkStyle:"radio"
                    }
                }

                var tree = "";
                // 判断是否加载ztree结构json
                if (_options.url == "") {
                    $.fn.zTree.init($("#" + _options.treeid), setting, _options.nodes);
                    tree = $("#" + _options.treeid);
                    initRight();
                } else {
                    andy.loaddata(_options.url, function (data) {
                        data = andy.stringToJson(data);
                        $.fn.zTree.init($("#" + _options.treeid), setting, data);
                        tree = $("#" + _options.treeid);
                        initRight();
                    })
                }

                //已选框是否有数据
                function initRight(){
                    if (_options.rightNodes.length > 0) {
                        for (var i = 0; i < _options.rightNodes.length; i++) {
                            var data = _options.rightNodes[i];
                            addToRight(data);
                            var t = $.fn.zTree.getZTreeObj(_options.treeid);
                            // var node = t.getNodeByTId(data.tId);
                            var node = t.getNodeByParam("id", data.id);
                            t.checkNode(node, true, false);
                        }
                    }
                };

                // 左边标题
                if (leftTitle) {
                    leftTitle.text(_options.leftTitle);
                };

                // 进行全部取消操作
                if (cancel) {
                    if(_options.selects == false){
                        cancel.addClass("f-hidden");
                    }
                    cancel.click(function () {
                        if (right) {
                            right.children().each(function (index, e) {
                                var t = $(e);
                                var tObject = $.fn.zTree.getZTreeObj(_options.treeid);
                                var id = t.attr("id");
                                var node = tObject.getNodeByParam("id", id);
                                removeToRight(node);
                            })
                        }
                    })
                }
                ;

                // 提交按钮
                if (submit) {
                    submit.click(function () {
                        _options.submit(getChooseJson());
                    })
                }
                ;

                //节点选取
                function onTreeCheck(treeNode) {
                    if (treeNode) {
                        if (treeNode.checked) {
                            // 选取
                            goCheck(treeNode, true);
                        } else {
                            // 取消
                            goCheck(treeNode, false);
                        }
                    }
                };

                function goCheck(treeNode, isCheck) {
                    if (treeNode && isCheck) {
                        if (treeNode.children && _options.selects) {
                            addToRight(treeNode);
                            for (var i = 0; i < treeNode.children.length; i++) {
                                goCheck(treeNode.children[i], isCheck);
                            }
                        } else {
                            addToRight(treeNode);
                        }
                    } else if (!isCheck) {
                        if (treeNode.children) {
                            removeToRight(treeNode);
                            for (var i = 0; i < treeNode.children.length; i++) {
                                goCheck(treeNode.children[i], isCheck);
                            }
                        } else {
                            removeToRight(treeNode);
                        }
                    }
                };

                // 添加人员到右侧
                function addToRight(data) {
                    if (right) {
                        if (!checkId(right, data.id) && _options.selects) {
                            right.append("<button class='u-btn sm' id = " + data.id + " data = " + data.data + " name = " + data.name + ">" + data.name + "<i class='iconfont'>&#xe602;</i></button>");
                            var button = right.find("#" + data.id);
                            button.click(function (e) {
                                removeToRight(data);
                            })
                        }else if(_options.selects == false){
                            var tObject = $.fn.zTree.getZTreeObj(_options.treeid);
                            // 获取结点
                            right.children('button').each(function(i, button){
                                var id = $(button).attr("id");
                                var btnData = tObject.getNodeByParam("id", id);
                                removeToRight(btnData);
                            })
                            right.append("<button class='u-btn sm' id = " + data.id + " data = " + data.data + " name = " + data.name + ">" + data.name + "<i class='iconfont'>&#xe602;</i></button>");
                            var button = right.find("#" + data.id);
                            button.click(function (e) {
                                removeToRight(data);
                            })
                        }
                    }
                };

                function removeToRight(data) {
                    var button = right.find("#" + data.id);
                    clearSelf(button);
                    var t = $.fn.zTree.getZTreeObj(_options.treeid);
                    if(!data.tId){
                        data = t.getNodeByParam("id", data.id);
                    }
                    
                    t.checkNode(data, false, false);
                }

                // 查询对象是否有相同id
                function checkId(element, id) {
                    var ishave = false;
                    element.children().each(function (index, e) {
                        if (id == $(e).attr("id")) {
                            ishave = true;
                        }
                    })
                    return ishave;
                }

                // 检查数组是否有相同id
                function checkDataId(data, id) {
                    var ishave = false;
                    for (var i = 0; i < data.length; i++) {
                        var d = data[i];
                        if (id == d.id) {
                            ishave = true;
                        }
                    }
                    ;
                    return ishave;
                }

                //移除自己
                function clearSelf(element) {
                    element.unbind('click');
                    element.remove();
                };

                //获取被选框对象 生成数据后
                function getChooseJson() {
                    var str = "[";
                    right.children().each(function (index, e) {
                        var $this = $(e);
                        var s = "{'id':'" + $this.attr("id") + "','name':'" + $this.attr("name") + "','data':'" + $this.attr("data") + "'}";
                        if (str == "[") {
                            str += s;
                        } else {
                            str += "," + s;
                        }
                    })
                    str += "]";
                    var jstr = eval('(' + str + ')');
                    return jstr;
                };
            }
            ;
        }
    })
})(jQuery);