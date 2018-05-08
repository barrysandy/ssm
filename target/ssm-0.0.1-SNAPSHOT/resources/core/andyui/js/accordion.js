/*accordion */
/**
 * 手风琴模块
 * author:林耘宇
 **/
(function ($) {
    $.fn.extend({
        an_accordion:function(options){
            var _options = $.extend({
                index:1,//默认打开第一个 设置0为全关闭
                isAnimation:true,
                multiple:false,//允许同时打开多个或者关闭多个
                isAllOpen:false,//是否全打开，必须multiple为true时才生效

                fit:true,//默认自适应
                height:0,
                width:0,
                onClick:function(){}
            }, options);

            var _stringclass = {
                head:"panel-head",
                body:"panel-body",
                title:"panel-head-title",
                hidden:"f-hidden",
                active:"activate"
            }

            var accordion = $(this);
            var head = accordion.children().children("."+_stringclass.head);
            var body = accordion.children().children("."+_stringclass.body);
            // var tabstyle = "";
            // var contentstyle = "";

            // if(!_options.fit){
            //     tabstyle = "height:"+_options.height+"px;_height:"+_options.height+"px;width:"+_options.width+"px;";
            //     contentstyle = "height:"+(_options.height - 66)+"px;_height:"+(_options.height - 66)+"px;width:"+(_options.width - 30)+"px;overflow:auto";
            // }

            // tabs.attr("style", tabstyle);
            // body.children().attr("style", contentstyle);

            var show = function(index){
                var page = _options.index;
                if(index){
                    page = index;
                }

                body.each(function(i, e){
                    var t = $(e);
                    if(page > 0 &&  page == (i + 1)){
                        if(_options.isAnimation){
                            if(_options.multiple){
                                t.slideToggle(200);
                            }else{
                                t.slideDown(200);
                            }
                        }else{
                            if(_options.multiple){
                                t.toggleClass(_stringclass.hidden);
                            }else{
                                t.removeClass(_stringclass.hidden);
                            }
                        }
                    }else{
                        if(!_options.multiple){
                            if(_options.isAnimation){
                                t.slideUp(200);
                            }else{
                                t.addClass(_stringclass.hidden);
                            }
                        }
                    }
                })
            }

            var choose = function(index){
                body.each(function(i, e){
                    var t = $(e);
                    if(t.css("display") == "block" && _options.multiple){

                    }
                })
                show(index);
            }

            var build = function(){
                if(_options.multiple || !_options.isAllOpen){
                    body.each(function(i, e){
                        var t = $(e);
                        if(_options.index > 0 && _options.index == (i+1)){
                            if(_options.isAnimation){
                                t.slideDown(200);
                            }
                        }else{
                            if(_options.isAnimation){
                                t.slideUp(200);
                            }else{
                                t.addClass(_stringclass.hidden);
                            }
                        }
                    });
                }else{
                    show();
                }
            }

            var start = function(){
                if(body){
                    build();
                };

                //绑定事件
                head.each(function(i, e){
                    $(e).click(function(e){
                        show(i+1);
                        // activate
                        head.each(function(index, head){
                            $(head).removeClass("activate");
                        });
                        $(this).addClass("activate");
                        _options.onClick(e);
                    })
                })
            };
            start();
        }
    })
})(jQuery);