/*related */
/**
 * 联动选择
 * author:林耘宇
 **/

 (function ($) {
    $.fn.extend({
    	an_related:function(){
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
                relatedId:"",//联动器ID
                showUrl:"",//
                item:[],//默认数据组["01", "02", "03"]
                checked:[],//默认选中 注意 数据里面是id数列
                row:5,//默认限制5行
                inputName:"",
                value:""//默认值
                // onClick:function(){}
            }, options);

            var as = $(this);
            var inValueInput = "";
            // 生成基本结构 没有结构的情况
            if(as.is("input")){
                var as_id = as.attr("id");
                as.removeAttr("id", "");
                as.removeAttr("an-related", "");
                if(!as_id){
                    as_id = "related_"+andy.getRandom(10000);
                }
                var as_style = "";
                if(as.attr("style")){
                    as_style = as.attr("style");
                    as.removeAttr("style", "");
                };
                if(as.attr("name")){
                    _options.inputName = as.attr("name");
                    as.removeAttr("name");    
                }
                if(as.attr("value")){
                    _options.value = as.attr("value");
                    as.removeAttr("value");
                }

                var as_class = "u-l-select";
                if(as.attr("class")){
                    as_class = as.attr("class");
                    as.attr("class", "");
                }
                
                var as_options = as.attr("options");
                as_options = "options = "+as_options;
                as.removeAttr("options");

                as.css("display", "none");
                // var subSpan = "<span class='u-but-group u-group-left'><i class='iconfont'>&#xe670;</i><button class='u-but-button' type='button' sub></button></span>";
                // var addSpan = "<span class='u-but-group u-group-right'><i class='iconfont'>&#xe66f;</i><button class='u-but-button' type='button' add></button></span>";
                var valueInput = "<input type = 'hidden' name = '"+_options.inputName+"' value='"+_options.value+"' val>";
                var div = "<div class='"+as_class+"' id = '"+as_id+"' style = '"+as_style+"' "+as_options+" an-related>"+valueInput+"</div>";
                as.before(div);
                as.remove();
                as = $("#"+as_id);
                andy.layout(as.parent());
            }

            // 给combo生成id
            if(_options.relatedId == ""){
                _options.relatedId = as.attr("id");
            }
            if(_options.relatedId == undefined){
                _options.relatedId = "as_"+andy.getRandom(10000);
                as.attr("id", _options.relatedId);
            }
            as = $("#"+_options.relatedId);

            as.find("input").each(function(index, input){
                var $input = $(input);
                if($input.attr("type") == "hidden"){
                    inValueInput = $input;
                    _options.inputName = $input.attr("name");
                }
            })

            // 获取 设置对象
            var getOption = as.attr("options");

            if(getOption){
                 getOption = "{"+ getOption+"}";
                 getOption = andy.stringToJson(getOption);
                // 处理设置
                for(var name in getOption){
                    if(name == "item"){
                        var data = getOption[name].split(",");
                        _options.item = data;
                    }else if(name == "checked"){
                        var data = getOption[name].split(",");
                        _options.checked = data;
                    }else{
                        _options[name] = getOption[name];
                    }
                    
                }
            };

            var itemLength = _options.item.length;
            var inputString = "";
            if(itemLength > 0){
                if(as.find("input").length < itemLength){
                    for(var i = 0; i < itemLength; i++){
                        // 最后除2 是 避免滚动条 整体布局 重新计算了宽度的
                        var itemWidth = Math.floor(as.innerWidth()/itemLength)/2;//向下取整
                        var itemClass = "g-combo u-group-center";
                        var itemIsEnable = true;
                        if(i == 0){
                            itemClass = "g-combo u-group-left";
                        }else if(i == itemLength  - 1){
                            itemClass = "g-combo u-group-right";
                        }
                        if(i > 0 && _options.checked.length == 0){
                            itemIsEnable = false;
                        }
                        var thisOptions = "options = defaultValue:'"+_options.item[i]+"',isEnable:'"+itemIsEnable+"',row:'"+_options.row+"'";
                        inputString += "<input class = '"+itemClass+"' style = 'width:"+itemWidth+"px' name = 'input_name' "+thisOptions+">";
                    }

                    as.prepend(inputString);
                }
            }

            // 私有事件
            var getValue = "EVENT_VALUE";
            if(funstyle != ""){
				if(funstyle == "getValue"){
					var fun = arguments[1];
					as.trigger(getValue, fun);
				};
            }else{
                var thisData = "";
                var inValueInput = "";
                var itemLength = 0;
                as.find("input").each(function(i, input){
                    var $input = $(input);
                    if($input.attr("type") == "hidden"){
                        inValueInput = $input;
                    }
                })
                var getDataById = function(id, data){
                    for(var i = 0;i < data.length; i ++){
                        if(data[i].id == id){
                            thisData = data[i].children;
                        }else if(data[i].children){
                            getDataById(id, data[i].children);
                        }
                    }
                    if(thisData != ""){
                        return thisData;
                    }
                }

                var setDataById = function(id, data, inData){
                    for(var i = 0;i < data.length; i ++){
                        if(data[i].id == id){
                            data[i].children = inData;
                        }else if(data[i].children){
                            setDataById(id, data[i].children, inData);
                        }
                    }
                }

                var createNewList = function(data, nextcombo){
                    // console.log(nextcombo.find("ul"))
                    var showTarget = nextcombo.find("ul");
                    showTarget.empty();
                    for(var i = 0; i < data.length; i++){
                        var liData = data[i];
                        var isFist = "";
                        if(i == 0){
                            isFist = "first";
                        }
                        var li = "<li class = '"+isFist+"' value = '"+liData.value+"' listId = '"+liData.id+"' ><a>"+liData.name+"</a></li>";
                        showTarget.append(li);
                    }
                    return showTarget;
                }

                var setValueInput = function(li, index){
                    var idValue = inValueInput.attr("idValue");
                    var valueData = inValueInput.attr("valueData");
                    if(!idValue){
                        var str = " ";
                        for(var i = 0; i< itemLength - 1; i++){
                            str += ", "
                        }
                        inValueInput.attr("idValue", str);
                        idValue = inValueInput.attr("idValue");
                    }
                    var arrId = idValue.split(",");
                    arrId[index] = li.attr("listid");
                    inValueInput.attr("idValue", arrId);
                    if(!valueData){
                        var str = " ";
                        for(var i = 0; i< itemLength - 1; i++){
                            str += ", "
                        }
                        inValueInput.attr("valueData", str);
                        valueData = inValueInput.attr("valueData");
                    }
                    var arrValue = valueData.split(",");
                    // con
                    arrValue[index] = li.attr("value");
                    inValueInput.attr("valueData", arrValue);
                }

                var resetChildren = function(i, idList){
                    for(var comboIndex = i+1; comboIndex < idList.length; comboIndex++){
                        var dcombo = $("#"+idList[comboIndex]);
                        dcombo.an_combo("setDefault");
                        dcombo.an_combo("isEnable", false);
                    }
                    
                }

                andy.loaddata(_options.showUrl, function(data){
                    // console.log(data)
                    var listData = data;
                    // combo初始化
                    as.find("input").each(function(i, inputList){
                        var $inputList = $(inputList);
                        if($inputList.attr("type") != "hidden"){
                            var url = ""
                            itemLength += 1;
                            if(i == 0){
                                url = _options.showUrl;
                            }
                            if(_options.checked.length >0){
                                // 初始化选择
                                var chooseData = [];
                                if(i == 0){
                                    chooseData = listData;
                                }else{
                                    chooseData = getDataById(_options.checked[i - 1], listData);
                                }
                                if(chooseData){
                                    $inputList.an_combo({
                                        row:_options.row,
                                        checked:[_options.checked[i]],
                                        showData:chooseData
                                    })
                                    if(inValueInput){
                                        var idArr = inValueInput.attr("idValue")
                                        if(!idArr){
                                            var str = " ";
                                            for(var i = 0; i< itemLength - 1; i++){
                                                str += ", "
                                            }
                                            inValueInput.attr("idValue", str);
                                            idArr = inValueInput.attr("idValue");
                                            inValueInput.attr("valueData", str);
                                        }
                                        idArr = idArr.split(",");
                                        var valueArr = inValueInput.attr("valueData").split(",");
                                        idArr[i] = _options.checked[i];
                                        var getArrValue = function(){
                                            var id = _options.checked[i];
                                            var idV = "";
                                            for(var index = 0; index < chooseData.length; index++){
                                                var cd = chooseData[index];
                                                if(id == cd.id){
                                                    idV = cd.value;
                                                }
                                            }
                                            return idV;
                                        }
                                        valueArr[i] = getArrValue();
                                        inValueInput.attr("idValue", idArr);
                                        inValueInput.attr("valueData", valueArr);
                                    }
                                }
                            }else{
                                $inputList.an_combo({
                                    row:_options.row,
                                    showUrl:url
                                })
                            }
                            
                        }
                    })
                    var idList = [];
                    as.find("[an-combo = an-combo]").each(function(i, combo){
                        var comboId = $(combo).attr("id");
                        var $combo = $("#"+comboId);
                        andy.inputLayout($combo);
                        idList.push(comboId);
                        $combo.bind(andy.COMBO_CHOOSE_LISET, function(e, li){
                            setValueInput($(li), i);
                            if(idList[i+1]){
                                var nextCombo = $("#"+idList[i+1]);
                                // var liId = parseInt($(li).attr("listId"));
                                var liId = $(li).attr("listId");
                                var childrenData = getDataById(liId, listData);
                                thisData = "";
                                // 判断children数据
                                // console.log(childrenData)
                                if(typeof(childrenData) == "object"){
                                    if(childrenData.length == 0){
                                        resetChildren(i, idList);
                                        return false;
                                    }
                                    var newList = createNewList(childrenData, nextCombo).clone();
                                    // 重置当前combo 之后的combo
                                    for(var comboIndex = i+1; comboIndex < idList.length; comboIndex++){
                                        var dcombo = $("#"+idList[comboIndex]);
                                        dcombo.an_combo("setDefault");
                                    }
                                    nextCombo.an_combo("setShowTarget", newList);
                                    nextCombo.an_combo("isEnable", true);
                                }else if(typeof(childrenData) == "string"){
                                    andy.loaddata(childrenData, function(thisNextData, statusData){
                                        // console.log(i)
                                        if(!thisNextData || thisNextData.length == 0){
                                            resetChildren(i, idList);
                                            return false;
                                        }
                                        setDataById(liId, listData, thisNextData);
                                        var newList = createNewList(thisNextData, nextCombo).clone();
                                        // // 重置当前combo 之后的combo
                                        for(var comboIndex = i+1; comboIndex < idList.length; comboIndex++){
                                            var dcombo = $("#"+idList[comboIndex]);
                                            dcombo.an_combo("setDefault");
                                        }
                                        nextCombo.an_combo("setShowTarget", newList);
                                        nextCombo.an_combo("isEnable", true);
                                    })
                                }else if(!childrenData){
                                    resetChildren(i, idList);
                                }
                                
                            }
                            
                        })
                    })
                })
            }; 
    	}
	});
})(jQuery);