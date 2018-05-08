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
                comboId:"",
                touchTargetType:"input",
                showTargetType:"list",
                showUl:"",
                checked:[],//已选id 初始化
                treeId:"",//下拉树ID
                selects:false,//是否多选
                isEnable:true,//是否可用
                row:0,//默认显示多少行,更多的出现滚动条
                open:false//默认关闭
            }, options);
            
            var combo = $(this);
            if(combo.attr("an-combo") != "an-combo"){
                combo.attr("an-combo", "an-combo");
            }
            combo = $(this);
            // 给combo生成id
            if(_options.comboId == ""){
                _options.comboId = combo.attr("id");
            }
            if(!_options.comboId){
                _options.comboId = "combo_"+getRandom(10000);
                combo.attr("id", _options.comboId);
            }
            var doc = $(document);
            var win = $(window);

            // var setTime = 200;
            // var timeHanld = "";

            var ul_height = 0;//第一层ul高度

            // 私有事件
            var setRow = "EVENT_ROW";
            var getVal = "EVENT_GET_VALUE";
            var getChooseId = "EVENT_GET_ID";
            var setShow = "EVENT_SHOW";//设置新的显示对象
            var setEnable = "EVENT_ENABLE";//设置是否禁用
            // 特殊设置事件
            var addTree = "EVENT_TREE";//创建树多选列表

            // 配置
            var showEvent = "click";//默认显示事件
            var touchTargetType = _options.touchTargetType;//默认触发对象类型
            var touchIsEnable = true;//触发对象是否可用
            var showTargetType = _options.showTargetType;//默认显示对象类型

            var showUrl = _options.showUrl;//异步加载url 包含list tree

            // 获取 设置对象
            var getOption = combo.attr("options");
            var getValueElement = "";
            if(getOption){
                getOption = getOption.split(",");
                // 处理设置
                for(var i = 0; i < getOption.length; i++){
                    var o = getOption[i].split(":");
                    var name = o[0];
                    var attr = o[1];
                    if(name == "show"){
                        showEvent = attr;
                    }else if(name == "selects"){
                        if(attr == "true"){
                            _options.selects = true;
                        }
                    }else if(name == "isEnable"){
                        if(attr == "true"){
                            _options.isEnable = true;
                        }else if(attr == "false"){
                            _options.isEnable = false;
                        }

                    }else if(name == "checked"){
                        _options.checked = attr;
                    }else if(name == "treeId"){
                        _options.treeId = attr;
                    }else if(name == "touchIsEnable"){
                        if(attr == "false"){
                            touchIsEnable = false;
                        }
                    }else if(name == "touchTargetType"){
                        touchTargetType = attr;
                    }else if(name == "showTargetType"){
                        showTargetType = attr;
                    }else if(name == "showUrl"){
                        showUrl = attr;
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
                }else if(funstyle == "getChooseId"){
                    var fun = arguments[1];
                    combo.trigger(getChooseId, fun);
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

                // "normal":input 普通类型  "ban":input 禁止修改
                // 当没有静态对象时 初始化默认对象
                if(combo.children().length == 0){
                    init(combo, _options.touchTargetType, _options.showTargetType);
                }else{
                    init(combo, touchTargetType, showTargetType);
                    combo.find(".u-input").addClass("u-diseditor");
                }

                function init(combo, touchType, showType){
                    combo.data("selects", _options.selects);
                    combo.data("isEnable", _options.isEnable);
                    combo.an_combo_creatTouch({
                        combo:combo,
                        checked:_options.checked,//已选id 初始化
                        isEnable:_options.isEnable,
                        selects:_options.selects,
                        touchTargetType:touchType
                    })
                    combo.an_combo_creatShow({
                        combo:combo,
                        checked:_options.checked,//已选id 初始化
                        treeId:_options.treeId,//下拉树ID
                        selects:_options.selects,
                        showTargetType:showType
                    })
                }

                andy.combo({
                    combo:combo,
                    showEvent:showEvent,
                    showComplete:function(){
                        if(showTargetType == "list"){
                            showUl(combo, combo.showTarget);
                        }else{
                            show(combo, combo.showTarget);
                        }
                    }
                });

                // 绑定显示对象事件 绑定事件 可以在创建方法里面直接绑定 也可以调用绑定事件方法
                combo.an_combo_creatShow("eventBind", {
                    combo:combo,
                    selects:_options.selects,
                    checked:_options.checked,//已选id 初始化
                    treeId:_options.treeId,//下拉树ID
                    touchTargetType:touchTargetType,
                    showTargetType:showTargetType,
                    showUrl:showUrl
                })
                
                // combo.an_combo_bindEvent({
                //     combo:combo,
                //     touchTargetType:touchTargetType,
                //     showTargetType:showTargetType,
                //     showUrl:showUrl
                // })

                // 是否禁用combo
                combo.bind(setEnable, function(e, isEnable){
                    combo.data("isEnable",isEnable);
                    setEnableFun();
                });

                setEnableFun();

                function setEnableFun(){
                    if(combo.touchTarget){
                        if(combo.data("isEnable")){
                            combo.removeClass("u-disabled");
                        }else{
                            combo.addClass("u-disabled");
                        }
                    }
                }

                // 修改限制行
                combo.bind(setRow, function(e, row){
                    _options.row = row;
                });

                // 设置新的显示对象
                combo.bind(setShow, function(e, show){
                    // setCurrentShow(show, true);
                });

                // 树结构事件绑定
                combo.bind(addTree, function(e, op){
                    // createTree(op.treeId, op.setting, op.nodes, op);
                });

                if(combo.showTarget.is("ul")){
                    // combo.find("ul li").click(function(e){
                    //     var t = $(e.target);
                    //     if(t.is('li') == false){
                    //         t = t.closest('li');
                    //     };
                    //     // 如果有 入值input 就附上value值
                    //     if(inputValue[0]){
                    //         inputValue.val(t.attr("value"));
                    //     };
                    //     if(span[0]){
                    //         if(span.children().has("p")){
                    //             var text = $(e.target).text();
                    //             span.children("p").text(text);
                    //         };
                            
                    //     };
                    //     combo.showTarget.css("display", "none");
                    //     combo.removeClass("open");
                    //     combo.trigger(andy.EVENT_CLICK, t.attr("value"));
                    // }); 
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
                    // 只有大于row的时候才出现滚动条
                    if(combo.showTarget.children().length > _options.row && _options.row != 0){
                        combo.showTarget.addClass("u-overflow-y");
                        ul_height = _options.row * combo.showTarget.children().outerHeight();
                        combo.showTarget.height(ul_height);
                        // u-overflow-y
                    }else{
                        combo.showTarget.removeClass("u-overflow-y");
                        ul_height = 0;//combo.showTarget.children().length * last_li.outerHeight();
                        combo.showTarget.css("height", "");
                    };
                    
                };

                function getRandom(n){
                    return Math.floor(Math.random()*n+1)
                }
            };
        },
        an_combo_creatTouch:function(){
            var options = {};
            var funstyle = "";
            for(var i= 0; i <arguments.length;i++){
                var a = arguments[0];
                if(typeof a == "object"){
                    options = a;
                }else if(typeof a == "string"){
                    funstyle = a;
                }
            };

            var _options = $.extend({
                combo:"",
                comboName:"combo",
                checked:"",//已选id 初始化
                selects:false,
                touchTargetType:""//默认显示多少行,更多的出现滚动条
                // open:false//默认关闭
            }, options);

            var addChoose = "EVENT_ADD_CHOOSE";
            var cancelChoose = "EVENT_CANCEL_CHOOSE";
            var getVal = "EVENT_GET_VALUE";
            var getChooseId = "EVENT_GET_ID";
            var comboId = $(this).attr("id");
            if($(this).data("selects") != undefined){
                _options.selects = $(this).data("selects");
            }
            
            if(funstyle != ""){
                if(funstyle == "addChoose"){
                    var touchData = arguments[1];
                    $(this).trigger(addChoose, touchData)
                }else if(funstyle == "cancelChoose"){
                    var touchData = arguments[1];
                    $(this).trigger(cancelChoose, touchData)
                }else if(funstyle == "getValue"){
                    // var fun = arguments[1];
                    // $(this).trigger(getVal, fun);
                };
            }else{
                // 根据不同触发对象 创建不同对象
                if(_options.touchTargetType == "input"){
                    if(_options.combo.children().length == 0){
                        _options.combo.append("<span class='u-up-menu'><input class = 'u-input u-diseditor' combo = '"+_options.comboName+"'><i class='iconfont u-nd'>&#xe613;</i></span>");
                        andy.formlayout();
                    }
                }else if(_options.touchTargetType == "button"){

                }

                $(this).bind(addChoose, function(e, data){
                    addTouchTarget(data);
                })

                $(this).bind(cancelChoose, function(e, data){
                    cancelTouchTarget(data);
                })

                $(this).bind(getVal, function(e, fun){
                    var touchTarget = andy.combo("getTouchTarget", $("#"+comboId));
                    if(touchTarget){
                        fun(touchTarget.attr("value"));
                    }
                })

                $(this).bind(getChooseId, function(e, fun){
                    var touchTarget = andy.combo("getTouchTarget", $("#"+comboId));
                    if(touchTarget){
                        fun(touchTarget.attr("idvalue"));
                    }
                })

                // touchTarget
                function addTouchTarget(touchData){
                    if(_options.touchTargetType == "input"){
                        // console.log("input")
                        insetInput(touchData);
                    }
                }

                function cancelTouchTarget(touchData){
                    if(_options.touchTargetType == "input"){
                        removeSetInput(touchData);
                    }
                }

                //-----------------------------------------------------input touch操作

                // input插入
                function insetInput(data){
                    // console.log(touchTarget[0])
                    var touchTarget = andy.combo("getTouchTarget", $("#"+comboId));
                    if(touchTarget && touchTarget.is("input")){
                        var idName = touchTarget.attr("idValue");
                        if(idName != undefined && idName != ""){
                            var idArr = idName.split(",");
                            var isHaveSameId = false;
                            for(var i = 0; i < idArr.length; i++){
                                if(idArr[i] == data.id){
                                    isHaveSameId = true;
                                }
                            }
                            if(!isHaveSameId){
                                addInput(data);
                            }
                        }else{
                            addInput(data);
                        }
                        
                    }
                }

                function addInput(node){
                    //插入input:id name
                    var touchTarget = andy.combo("getTouchTarget", $("#"+comboId));
                    var oldName = touchTarget.attr("value");
                    var idName = touchTarget.attr("idValue");
                    if(oldName == undefined || oldName == ""){
                        oldName = node.name;
                        idName = node.id;
                    }else{
                        oldName = oldName+"，"+node.name;
                        idName = idName+","+node.id;
                    }
                    
                    if(_options.selects == false){
                        touchTarget.attr({"value":node.name, "idValue":node.id});
                    }else{
                        touchTarget.attr({"value":oldName, "idValue":idName});
                    }
                    
                }

                function removeSetInput(treeNode){
                    // 移除input:id name
                    var touchTarget = andy.combo("getTouchTarget", $("#"+comboId));
                    if(touchTarget && touchTarget.is("input")){
                        var oldName = touchTarget.attr("value");
                        var idName = touchTarget.attr("idValue");
                        var oldArr = oldName.split("，");
                        var idArr = idName.split(",");
                        for(var i = 0; i < idArr.length; i++){
                            if(idArr[i] == treeNode.id){
                                idArr.splice(i, 1);
                                oldArr.splice(i, 1);
                            }
                        }
                        // 清空之前选择
                        touchTarget.attr("value", "");
                        touchTarget.attr("idValue", "");
                        var oldName = touchTarget.attr("value");
                        var idName = touchTarget.attr("idValue");
                        for(var i = 0; i < idArr.length; i++){
                            // addInput(idArr[i]);
                            var name = oldArr[i];
                            var id = idArr[i]; 
                            if(i == 0){
                                oldName = name;
                                idName = id;
                            }else{
                                oldName = oldName+"，"+name;
                                idName = idName+","+id;
                            }
                        }
                        touchTarget.attr("value", oldName);
                        touchTarget.attr("idValue", idName);
                    }
                }


            }
        },
        an_combo_creatShow:function(){
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
                combo:"",
                comboName:"combo",
                showTargetType:""//默认显示多少行,更多的出现滚动条
            }, options);

            var eventBind = "EVENT_BIND";

            if(funstyle != ""){
                if(funstyle == "eventBind"){
                    var obj = arguments[1];
                    $(this).trigger(eventBind, obj);
                };
            }else{
                if(_options.showTargetType == "list"){
                    if(_options.combo.children().length == 1){
                        _options.combo.append("<ul class = 'combo u-down-menu' combo = '"+_options.comboName+"'></ul>")
                    }
                }else if(_options.showTargetType == "tree"){
                    if(_options.combo.children().length == 1){
                        _options.combo.append("<div class='combo but-tree' style='width:300px; height:200px;' combo ='"+_options.comboName+"'></div>")
                    }
                }

                _options.combo.bind(eventBind, function(e, obj){
                    _options.combo.an_combo_bindEvent(obj)
                })
            }
        },
        an_combo_bindEvent:function(){
            // 绑定选中事件
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
                combo:"",
                selects:false,
                checked:[],//已选id 初始化
                treeId:"",//下拉树ID
                touchTargetType:"input",
                showTargetType:"list",
                showUrl:""
            }, options);

            var touchTarget = _options.combo.touchTarget;
            var showTarget = _options.combo.showTarget;

            if($(this).data("selects") != undefined){
                _options.selects = $(this).data("selects");
            }

            if(funstyle != ""){
                // if(funstyle == "row"){
                // };
            }else{
                if(_options.showUrl != ""){
                    andy.loaddata(_options.showUrl, function(data){
                        initShow(data);
                    })
                }else{
                    if(_options.showTargetType == "list"){
                        creatListShow();
                    }
                }

                // showTarget
                function initShow(data){
                    if(_options.showTargetType == "list"){
                        for(var i = 0; i < data.length; i++){
                            var liData = data[i];
                            var isFist = "";
                            if(i == 0){
                                isFist = "first";
                            }
                            var li = "<li class = '"+isFist+"' value = '"+liData.value+"' listId = '"+liData.id+"' ><a>"+liData.name+"</a></li>";
                            showTarget.append(li);
                        }
                        creatListShow();
                    }else if(_options.showTargetType == "tree"){
                        if(_options.treeId == ""){
                            _options.treeId = "treeId_"+getRandom(10000);
                        }
                        createTree(_options.treeId, null, data, _options);
                    }
                    
                }

                // 创建列表结构
                function creatListShow(){
                    // 绑定事件
                    var checkLenght = _options.checked.length;
                    showTarget.children().each(function(index, li){
                        // 初始化\
                        var $li = $(li);
                        // 如果li标签 没有识别listId 则主动以索引赋予
                        if(!$li.attr("listId")){
                            $li.attr("listId", _options.combo.attr("id")+"_"+index);
                        }
                        if(checkLenght > 0){

                            for(var i = 0; i< checkLenght; i++){
                                if($li.attr("listId") == String(_options.checked[i])){
                                    $li.addClass("active");
                                    var liData = getLiData($li);
                                    addTouch(liData)
                                }
                            }

                        }
                        $li.on("click", function(e){
                            var t = $(e.target);
                            if(t.is('li') == false){
                                t = t.closest('li');
                            };
                            
                            if(!_options.selects){
                                showTarget.children().each(function(index, li){
                                    $(li).removeClass("active");
                                })
                            }
                            t.toggleClass("active");
                            var touchData = getLiData(t);
                            addTouch(touchData)
                        })
                    })
                    
                }

                function getLiData(li){
                    // 从li标签里面获取数据
                    var liData = {
                        checked:li.hasClass("active"),
                        value:li.attr("value"),
                        id:li.attr("listId"),
                        name:li.text()
                    }
                    return liData;
                }

                // 创建树结构
                function createTree(id, set, nodes, op){
                    var treeId = id;
                    _options.selects = op.selects;//是否多选 true为多选
                    var checked = op.checked;
                    var combo = _options.combo;
                    
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
                            radioType: "all",
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
                }

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
                }

                function goTreeCheck(treeNode, isCheck){
                    // console.log(treeNode, isCheck);
                    // var nodes = treeObj.getSelectedNodes();
                    // console.log(treeNode.checked)
                    // 插入 触发对象 利用统一方法入口
                    if (treeNode && isCheck) {
                        if (treeNode.children && _options.selects) {
                            // insetInput(treeNode);
                            _options.combo.an_combo_creatTouch("addChoose", treeNode);
                            for (var i = 0; i < treeNode.children.length; i++) {
                                goTreeCheck(treeNode.children[i], isCheck);
                            }
                        } else {
                            // insetInput(treeNode);
                            _options.combo.an_combo_creatTouch("addChoose", treeNode);
                        }
                    } else if (!isCheck) {
                        if (treeNode.children) {
                            // removeSetInput(treeNode);
                            _options.combo.an_combo_creatTouch("cancelChoose", treeNode);
                            for (var i = 0; i < treeNode.children.length; i++) {
                                goTreeCheck(treeNode.children[i], isCheck);
                            }
                        } else {
                            // removeSetInput(treeNode);
                            _options.combo.an_combo_creatTouch("cancelChoose", treeNode);
                        }
                    }
                }

                function addTouch(touchData){
                    // if(_options.touchTargetType == "input"){
                        if(touchData.checked){
                            _options.combo.an_combo_creatTouch("addChoose", touchData);
                        }else{
                            _options.combo.an_combo_creatTouch("cancelChoose", touchData);
                        }
                    // }
                }

                // =========================================通用 显示对象方法

                // 更新显示对象 显示对象 是否清楚以前结构
                function setCurrentShow(show, isClear){
                    var combo = _options.combo;
                    if(isClear){
                        combo.showTarget.empty();
                    };
                    if(show instanceof jQuery){  
                        combo.showTarget = show;
                    }else{
                        combo.showTarget = $(show);
                    };
                };

                function getRandom(n){
                    return Math.floor(Math.random()*n+1)
                }
                
            }
        }
    });
})(jQuery);