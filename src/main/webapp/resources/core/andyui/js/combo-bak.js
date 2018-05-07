/*combo */
/**
 * 组合框模块
 * author:林耘宇
 **/
 (function ($) {
    $.fn.extend({
        an_combo:function(){
            var options = {};
            var funstyle = "";
            for(var i= 0; i <arguments.length;i++){
                // console.log(arguments[0]);
                var a = arguments[0];
                if(typeof a == "object"){
                    options = a;
                }else if(typeof a == "string"){
                    funstyle = a;
                }
            };

            var _options = $.extend({
                selects:true,//多选单选 部分有效
                row:5,//默认显示多少行,更多的出现滚动条
                isEnable:true,//默认可用
                open:false//默认关闭
            }, options);
            
            var combo = $(this);
            var first_ul = combo.find("ul").first();
            var ultotal = combo.find("ul").length;
            var last_li = first_ul.children("li").last();
            var span = combo.children().first();
            
            var span_i = span.children("i");
            var span_button = span.children("button");

            var doc = $(document);
            var win = $(window);

            // var setTime = 200;
            // var timeHanld = "";
            var ul_height = 0;//第一层ul高度

            // 私有事件
            var setRow = "EVENT_ROW";
            var getVal = "EVENT_VALUE";
            var setShow = "EVENT_SHOW";//设置新的显示对象
            var setEnable = "EVENT_ENABLE";//设置是否禁用
            // 特殊设置事件
            var addTree = "EVENT_TREE";//创建树多选列表

            // 配置
            var showEvent = "click";//默认显示事件

            // 获取 设置对象
            var getOption = combo.attr("opstion");
            var getValueElement = "";
            if(getOption){
                getOption = getOption.split(",");
                // 处理设置
                for(var i = 0; i < getOption.length; i++){
                    var o = getOption[i].split(":");
                    if(o[0] == "show"){
                        showEvent = o[1];
                    }else if(o[0] == "getValue"){
                        getValueElement = combo.an_combo_choice({
                            showType:o[1]
                        });
                    }
                }
            };
            // console.log(getOption);
            // 入值input
            var inputValue = combo.find("input").each(function(i, input){
                if($(input).attr("type") == "hidden"){
                    return $(input);
                }
            });

            if(funstyle != ""){
                if(funstyle == "row"){
                    var row = arguments[1];
                    if(combo && combo[0]){
                        combo.trigger(setRow, row);
                    };
                }else if(funstyle == "getValue"){
                    var fun = arguments[1];
                    combo.trigger(getVal, fun);
                }else if(funstyle == "setShowTarget"){
                    // 设置新的显示对象
                    var show = arguments[1];
                    combo.trigger(setShow, show);
                }else if(funstyle == "addTree"){
                    // 传入树设置
                    var op = arguments[1];
                    combo.trigger(addTree, op);
                }else if(funstyle == "isEnable"){
                    var isEnable = arguments[1];
                    combo.trigger(setEnable, isEnable);
                };
            }else{
                 // 兼容IE6 的显示
                if(andy.IE() == 6){
                    combo.find("ul").each(function(i, e){
                        var cell = $(e);
                        if(!cell.prev().is("div")){
                            cell.before("<div></div>");
                        }
                    });
                };

                andy.combo({
                    combo:combo,
                    showEvent:showEvent,
                    isEnable:_options.isEnable,
                    showComplete:function(){
                        var cp = combo;
                        var f_ul = combo.showTarget;
                        if(combo.showTarget.is("ul")){
                            showUl(cp, f_ul);
                        }else{
                            show(cp, f_ul);
                        }
                    }
                });

                // 是否禁用combo
                combo.bind(setEnable, function(e, isEnable){
                    combo.enable = isEnable;
                    if(combo.touchTarget){
                        if(combo.enable){
                            combo.removeClass("u-disabled");
                        }else{
                            combo.addClass("u-disabled");
                        }
                    }
                    // andy.combo(setEnable, combo, isEnable);
                });

                // 修改限制行
                combo.bind(setRow, function(e, row){
                    _options.row = row;
                });

                // 获取值
                combo.bind(getVal, function(e, fun){
                    if(inputValue[0]){
                        fun(inputValue.val());
                    }
                });

                // 设置新的显示对象
                combo.bind(setShow, function(e, show){
                    setCurrentShow(show, true);
                });

                // 树结构事件绑定
                combo.bind(addTree, function(e, op){
                    createTree(op.treeId, op.setting, op.nodes, op);
                });

                // combo-tree处理
                function createTree(id, set, nodes, op){
                    var treeId = id;
                    _options.selects = op.selects;//是否多选 true为多选
                    var checked = op.checked;
                    
                    //树设置
                    var setting = {
                        treeObj:null,
                        callback:{
                            onCheck:function(event, treeId, treeNode) {
                                onTreeCheck(treeNode);
                            }
                        },
                        check: {
                            enable: true,
                            chkStyle: "checkbox",
                            chkboxType: { "Y": "s", "N": "s" }
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
                    if(set){
                        setting = $.extend(setting, set);
                    };

                    // 插入树结构
                    combo.showTarget.append("<div class='g-h-max'><ul id='"+treeId+"' class='ztree'></ul></div>");
                    var tree = "";
                    // 判断是否加载ztree结构json
                    if(nodes){
                        $.fn.zTree.init($("#" + treeId), setting, nodes);
                        tree = $("#" + treeId);
                        setChecked(treeId, checked);
                        setCurrentShow(combo.showTarget);
                    }else if(op && op.url){
                        andy.loaddata(op.url, function(data){
                            data = andy.stringToJson(data);
                            // console.log(combo.showTarget)
                            $.fn.zTree.init($("#" + treeId), setting, data);
                            tree = $("#" + treeId);
                            setChecked(treeId, checked);
                            setCurrentShow(combo.showTarget);
                        })
                    };
                };

                function setChecked(treeId, checked){
                    if(!checked){
                        return false;
                    }
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    for(var i = 0; i < checked.length; i++){
                        var node = treeObj.getNodeByParam("id", checked[i], null);
                        treeObj.checkNode(node, true, true);
                        onTreeCheck(node);
                    }

                }

                function onTreeCheck(treeNode){
                    if(treeNode){
                        if(treeNode.checked){
                            // 选取
                            goTreeCheck(treeNode, true);
                        }else{
                            // 取消
                            goTreeCheck(treeNode, false);
                        }
                    }
                };

                function goTreeCheck(treeNode, isCheck){
                    // console.log(treeNode, isCheck);
                    // var nodes = treeObj.getSelectedNodes();
                    // 插入 触发对象 利用统一方法入口
                    if (treeNode && isCheck) {
                        if (treeNode.children && _options.selects) {
                            insetInput(treeNode);
                            for (var i = 0; i < treeNode.children.length; i++) {
                                goTreeCheck(treeNode.children[i], isCheck);
                            }
                        } else {
                            insetInput(treeNode);
                        }
                    } else if (!isCheck) {
                        if (treeNode.children) {
                            removeSetInput(treeNode);
                            for (var i = 0; i < treeNode.children.length; i++) {
                                goTreeCheck(treeNode.children[i], isCheck);
                            }
                        } else {
                            removeSetInput(treeNode);
                        }
                    }
                };

                // input插入
                function insetInput(treeNode){
                    if(combo.touchTarget && combo.touchTarget.is("input")){
                        var idName = combo.touchTarget.attr("idValue");
                        if(idName != undefined && idName != ""){
                            var idArr = idName.split(",");
                            var isHaveSameId = false;
                            for(var i = 0; i < idArr.length; i++){
                                if(idArr[i] == treeNode.id){
                                    isHaveSameId = true;
                                }
                            }
                            if(!isHaveSameId){
                                addInput(treeNode);
                            }
                        }else{
                            addInput(treeNode);
                        }
                        
                    }
                }

                function addInput(node){
                    //插入input:id name
                    var oldName = combo.touchTarget.attr("value");
                    var idName = combo.touchTarget.attr("idValue");
                    if(oldName == undefined || oldName == ""){
                        oldName = node.name;
                        idName = node.id;
                    }else{
                        oldName = oldName+","+node.name;
                        idName = idName+","+node.id;
                    }
                    
                    if(_options.selects == false){
                        combo.touchTarget.attr({"value":node.name, "idValue":node.id});
                    }else{
                        combo.touchTarget.attr({"value":oldName, "idValue":idName});
                    }
                    
                }

                function removeSetInput(treeNode){
                    // 移除input:id name
                    if(combo.touchTarget && combo.touchTarget.is("input")){
                        var oldName = combo.touchTarget.attr("value");
                        var idName = combo.touchTarget.attr("idValue");
                        var oldArr = oldName.split(",");
                        var idArr = idName.split(",");
                        for(var i = 0; i < idArr.length; i++){
                            if(idArr[i] == treeNode.id){
                                idArr.splice(i, 1);
                                oldArr.splice(i, 1);
                            }
                        }
                        // 清空之前选择
                        combo.touchTarget.attr("value", "");
                        combo.touchTarget.attr("idValue", "");
                        var oldName = combo.touchTarget.attr("value");
                        var idName = combo.touchTarget.attr("idValue");
                        for(var i = 0; i < idArr.length; i++){
                            // addInput(idArr[i]);
                            var name = oldArr[i];
                            var id = idArr[i]; 
                            if(i == 0){
                                oldName = name;
                                idName = id;
                            }else{
                                oldName = oldName+","+name;
                                idName = idName+","+id;
                            }
                        }
                        combo.touchTarget.attr("value", oldName);
                        combo.touchTarget.attr("idValue", idName);
                    }
                }

                if(combo.showTarget.is("ul")){
                    combo.find("ul li").click(function(e){
                        var t = $(e.target);
                        if(t.is('li') == false){
                            t = t.closest('li');
                        };
                        // 如果有 入值input 就附上value值
                        if(inputValue[0]){
                            inputValue.val(t.attr("value"));
                        };
                        if(span[0]){
                            if(span.children().has("p")){
                                var text = $(e.target).text();
                                span.children("p").text(text);
                            };
                            
                        };
                        combo.showTarget.css("display", "none");
                        combo.removeClass("open");
                        combo.trigger(andy.EVENT_CLICK, t.attr("value"));
                    }); 
                };

                // 更新显示对象 显示对象 是否清楚以前结构
                function setCurrentShow(show, isClear){
                    if(isClear){
                        combo.showTarget.empty();
                    };
                    if(show instanceof jQuery){  
                        combo.showTarget = show;
                    }else{
                        combo.showTarget = $(show);
                    };
                };

                // 显示层排列
                function showUl(pul, ul){
                    var offset = pul.offset();
                    var pleft = offset.left;
                    var ptop = offset.top;
                    var doc_width = doc.width();
                    var doc_height = doc.height();
                    var doc_scrollTop = doc.scrollTop();
                    var ul_width = ul.outerWidth();
                    setUlHeight();
                    var ulHeight = ul.height();

                    var scrollWidth = 0;
                    if(isHaveScroll()){
                        scrollWidth = 17;
                    };
                    if(doc_width - pleft - scrollWidth >= ul_width){
                        ul.css({"left":"0px","right":"auto"});
                    }else{
                        ul.css({"left":"auto", "right":"0px"});
                    };
                    // 上下排列
                    // console.log(win.height() - (ptop - doc_scrollTop), ulHeight);
                    var buttonHeight = combo.touchTarget.outerHeight();
                    if($(window).height() - (ptop - doc_scrollTop) - buttonHeight <= ulHeight){
                        ul.css("top", -ulHeight + "px");
                    }else{
                        
                        ul.css("top", buttonHeight + "px");
                    }
                };

                // 除开ul的 显示位置
                function show(pdiv, div){
                    var offset = pdiv.offset();
                    var pleft = offset.left;
                    var ptop = offset.top;
                    var doc_width = doc.width();
                    var doc_height = doc.height();
                    var doc_scrollTop = doc.scrollTop();
                    var ul_width = div.outerWidth();
                    var ulHeight = div.height();

                    var scrollWidth = 0;
                    if(isHaveScroll()){
                        scrollWidth = 17;
                    };
                    // 左右排列
                    if(doc_width - pleft - scrollWidth >= ul_width){
                        div.css({"left":"0px","right":"auto"});
                    }else{
                        div.css({"left":"auto", "right":"0px"});
                    };

                    // 上下排列
                    // console.log(win.height() - (ptop - doc_scrollTop), ulHeight);
                    var buttonHeight = combo.touchTarget.outerHeight();
                    if($(window).height() - (ptop - doc_scrollTop) - buttonHeight <= ulHeight){
                        div.css("top", -ulHeight + "px");
                    }else{
                        
                        div.css("top", buttonHeight + "px");
                    }
                };
                // 判断是否有侧边滚动条
                function isHaveScroll(){
                    var have = false;
                    if(doc.height() > win.innerHeight()){
                        have = true;
                    };
                    return have;
                };
                function setUlHeight(){
                    if(ultotal == 1){
                        // 只有大于row的时候才出现滚动条
                        if(first_ul.children().length > _options.row && _options.row != 0){
                            first_ul.addClass("u-overflow-y");
                            ul_height = _options.row * last_li.outerHeight();
                            first_ul.height(ul_height);
                            // u-overflow-y
                        }else{
                            first_ul.removeClass("u-overflow-y");
                            ul_height = 0;//first_ul.children().length * last_li.outerHeight();
                            first_ul.css("height", "");
                        };
                        // console.log(ul_height, first_ul.children().length, last_li.outerHeight());
                        
                    }
                    
                };
            };
        },
        an_combo_choice:function(){
            var options = {};
            var funstyle = "";
            for(var i= 0; i <arguments.length;i++){
                // console.log(arguments[0]);
                var a = arguments[0];
                if(typeof a == "object"){
                    options = a;
                }else if(typeof a == "string"){
                    funstyle = a;
                }
            };

            var _options = $.extend({
                touchTarget:target,
                showType:""//默认显示多少行,更多的出现滚动条
                // open:false//默认关闭
            }, options);

            // type 多选类型:multi-select-close(多选可关闭)
            if(_options.showType == "multi-select-close"){

            };
        }
    });
})(jQuery);