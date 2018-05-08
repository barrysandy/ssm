/*tabs */
/**
 * 分页模块
 * author:林耘宇
 **/
(function ($) {
    $.fn.extend({
        an_tabs:function(){
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
                index:1,//默认打开
                fit:true,//默认自适应
                height:0,
                width:0,
                hidden:"",//隐藏tabs:0不可见 1可见 [1, 0, 1];
                onClick:function(){}
            }, options);

            var _stringclass = {
                head:"m-tabs-header",
                body:"m-tabs-content",
                layouted:"layouted",
                hidden:"f-hidden",
                item:"item",
                active:"activate",
                headTop:"head-top",
                headLeft:"head-left",
                headRight:"head-right",
                headBottom:"head-bottom"
            };

            var tabs = $(this);
            var head = $(this).children("."+_stringclass.head);
            var body = $(this).children("."+_stringclass.body);
            var item = body.children("." + _stringclass.item);
            var showEvent = "SHOW_EVENT";
            
            // 获取 设置对象
            var getOption = tabs.attr("options");
            if(getOption){
                getOption = getOption.split(",");
                // 处理设置
                for(var i = 0; i < getOption.length; i++){
                    var o = getOption[i].split(":");
                    var name = o[0];
                    var attr = o[1];
                    if(name == "show"){
                        _options.index = attr;
                    }else if(name == "hidden"){
                        _options.hidden = attr;
                    }else if(name == "fit"){
                        if(attr == "false"){
                            _options.fit = false;
                        }
                    }else if(name == "width"){
                        _options.width = parseInt(attr);
                    }else if(name == "height"){
                        _options.height = parseInt(attr);
                    }
                }
            };

            tabs.data("fit", _options.fit);
            var show = function(index){
                body.children().each(function(i, e){
                    var t = $(e);
                    if(index == i){
                        if(!t.hasClass(_stringclass.active)){
                            t.addClass(_stringclass.active);
                        }
                        // setTimeout(function(){
                            if(!t.hasClass(_stringclass.layouted)){
                                andy.layout(t);
                                t.addClass(_stringclass.layouted);
                            }
                            
                        // }, 50)
                    }else{
                        t.removeClass(_stringclass.active);
                    }
                });
                // if(body[0]){
                //     andy.layout(body);
                // };
            };

            // 执行隐藏
            var isHidden = function(index){
                if(_options.hidden != ""){
                    if(typeof(_options.hidden[index]) == "number"){
                        return _options.hidden[index];
                    }
                }else{
                    return 1;
                };
            };

            if(funstyle != ""){
                //方法写入
                if(funstyle == "hidden"){
                    // 默认选择第一个 显示的页签
                    var getChoose = function(arr){
                        var choose = 0;
                        for(var i = 0; i < arr.length; i++){
                            if(arr[i] == 1){
                                choose = i;
                                break;
                            }
                        };
                        return choose;
                    };
                    var arr = arguments[1];
                    var choose = getChoose(arr);
                    _options.hidden = arr;
                    show(choose);

                    head.find("li").each(function(i, e){
                        var t = $(e);
                        t.removeClass();
                        if(isHidden(i) == 0){
                            t.hide();
                        }else if(isHidden(i) == 1){
                            t.show();
                            if(i == choose){
                                t.toggleClass(_stringclass.active);
                            }
                        };
                    });

                    
                }else if(funstyle = "show"){
                    var index = parseInt(arguments[1]);
                    if(index < 0){
                        index = 0;
                    }
                    tabs.trigger(showEvent, index);
                };
            }else{

                if($(this)[0]){
                    $(this)[0].option = _options;
                }
                
                
                var tabstyle = "";
                var contentstyle = "";
                // var pheight = tabs.parent().height();//父级高度

                // console.log(tabs.parent().css("height"))
                var bodyPadding = parseInt(body.css("padding-left"));
                if(!bodyPadding){
                    bodyPadding = 0;
                }
                var bodyBorder = parseInt(body.css("border-left"));
                if(!bodyBorder){
                    bodyBorder = 0;
                }
                var bodyBottomBorder = parseInt(body.css("border-bottom"));
                if(!bodyBottomBorder){
                    bodyBottomBorder = 0;
                }
    			//alert(bodyPadding);
    			
                var headHeight = 0;//head.outerHeight() + bodyPadding *2;
                var headWidth = 0;
                if(!tabs.hasClass(_stringclass.headTop) && !tabs.hasClass(_stringclass.headLeft) && !tabs.hasClass(_stringclass.headRight) && !tabs.hasClass(_stringclass.headBottom)){
                    headHeight = head.outerHeight();
                    // tabs.addClass(_stringclass.headTop);
                }else if(tabs.hasClass(_stringclass.headTop)){
                    headHeight = head.outerHeight();
                }else if(tabs.hasClass(_stringclass.headLeft)){
                    headWidth = head.outerWidth();
                }else if(tabs.hasClass(_stringclass.headRight)){
                    headWidth = head.outerWidth();
                }else if(tabs.hasClass(_stringclass.headBottom)){
                    headHeight = head.outerHeight();
                }

                var choose = function(index){
                    head.children("ul").children().each(function(i, e){
                        var t = $(e);
                        if(index == i){
                            if(!t.hasClass(_stringclass.active)){
                                t.toggleClass(_stringclass.active);
                            }
                        }else{
                            if(isHidden(i) == 0){
                                t.hide()
                                // t.addClass(_stringclass.hidden);
                            }
                            t.removeClass();
                        }
                    })
                    // hiddenTabs();
                    show(index);
                };

                var start = function(){
                    if(tabs.children().find(_stringclass.head)){
                        choose(_options.index - 1);
                    }

                    //绑定事件
                    head.find("li").each(function(i, e){
                        var $e = $(e);
                        $e.is('li') || ($e = $e.closest('li'));
                        $e.click(function(event){
                            choose(i);
                            _options.onClick(event);
                            tabs.trigger(andy.EVENT_CLICK, i+1);
                        })
                    })
                };

                var fitlayout= function(){
                    var pheight = tabs.parent().height();
                    var pwidth = tabs.parent().width();
                    // tabs.height(pheight).width(pwidth);
                    tabs.outerHeight(pheight).outerWidth(pwidth);
                    body.outerHeight(pheight - headHeight).outerWidth(pwidth - headWidth);
                    // tabs.trigger("layout_complete");//出发布局结束
                    var bodyHeight = body.height();
                    var bodyWidth = body.width();
                    body.children().each(function(index, content){
                        var $content = $(content);
                        $content.outerHeight(bodyHeight).outerWidth(bodyWidth);
                    });
                };

                // 主动选择分页
                tabs.bind(showEvent, function(e, index){
                    choose(index);
                })
                
                if(!tabs.data("fit")){
                    var pheight = tabs.parent().height();
                    var pwidth = tabs.parent().width();
                    if(_options.height>0){
                        pheight = _options.height;
                    }
                    if(_options.width>0){
                        pwidth = _options.width;
                    }
                    tabs.outerHeight(pheight).outerWidth(pwidth);
                    body.outerHeight(pheight - headHeight).outerWidth(pwidth - headWidth);
                    var bodyHeight = body.height();
                    var bodyWidth = body.width();
                    body.children().each(function(index, content){
                        var $content = $(content);
                        $content.outerHeight(bodyHeight).outerWidth(bodyWidth);
                    });
                }else{
                    fitlayout();
                    tabs.bind("layout", function(){
                        fitlayout();
                    })
                };

                start();
            };
        }
    })
})(jQuery);