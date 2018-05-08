/*spinner */
/**
 * 数字调节器
 * author:林耘宇
 **/

 (function ($) {
    $.fn.extend({
    	an_spinner:function(){
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
                increment:1,//默认增减值
                min:0,//默认为0
                max:99999,//默认99999
                inputName:"",
                value:1,//默认值
                onClick:function(){}
            }, options);

            var spi = $(this);
            if(spi.is("input")){
                var spinner_id = spi.attr("id");
                spi.removeAttr("id", "");
                spi.removeAttr("an-spinner", "");
                if(!spinner_id){
                    spinner_id = "spinner_"+andy.getRandom(10000);
                }
                var spinner_style = spi.attr("style");
                if(spi.attr("name")){
                    _options.inputName = spi.attr("name");
                    spi.removeAttr("name");    
                }
                if(spi.attr("value")){
                    _options.value = spi.attr("value");
                    spi.removeAttr("value");
                }
                
                var spinner_options = spi.attr("options");

                spi.css("display", "none");
                var subSpan = "<span class='u-but-group u-group-left'><i class='iconfont'>&#xe670;</i><button class='u-but-button' type='button' sub></button></span>";
                var addSpan = "<span class='u-but-group u-group-right'><i class='iconfont'>&#xe66f;</i><button class='u-but-button' type='button' add></button></span>";
                var valueInput = "<input type='text' class='u-input u-group-center' name = '"+_options.inputName+"' style='text-align:center; width:0px; text-indent:0' value='"+_options.value+"' val>";
                var div = "<div class='u-group' id = '"+spinner_id+"' style = '"+spinner_style+"' options = '"+spinner_options+"' an-spinner>"+subSpan+valueInput+addSpan+"</div>";
                spi.before(div);
                spi = $("#"+spinner_id);
                andy.layout(spi.parent());
            }
            var sub = spi.find("[sub]");
            var val = spi.find("[val]");
            var add = spi.find("[add]");

            // 私有事件
            var getValue = "EVENT_VALUE";

            // 获取 设置对象
            var getOption = spi.attr("options");
            if(getOption){
                getOption = getOption.split(",");
                // 处理设置
                for(var i = 0; i < getOption.length; i++){
                    var o = getOption[i].split(":");
                    var name = o[0];
                    var attr = o[1];
                    if(name == "increment"){
                        _options.increment = parseInt(attr);
                    }else if(name == "min"){
                        _options.min = parseInt(attr);
                    }else if(name == "max"){
                        _options.max = parseInt(attr);
                    }
                }
            };

            if(funstyle != ""){
				if(funstyle == "getValue"){
					var fun = arguments[1];
					spi.trigger(getValue, fun);
				};
            }else{

            	// 方法事件绑定
            	spi.bind(getValue, function(e, fun){
					fun(val.val());
            	});

				sub.click(function(e){
					var c = parseInt(val.val());
					if(c > _options.min){
						c -= _options.increment;
						val.val(c);
                        val.attr("value", c);
					};
	            });

	            add.click(function(e){
					var c = parseInt(val.val());
					if(c < _options.max){
						c += _options.increment;
						val.val(c);
                        val.attr("value", c);
					};
	            });
            }; 
    	}
	});
})(jQuery);