/*global andy,$*/
/**
 * Created by chen_muse on 2016/5/23.
 * 滑动条模块 依赖于拖动模块，和其他输入控件一样 有 dataKey 可用disable
 *
 * 所有属性均可以通过slider 控件获取 渲染的触发由表单触发渲染还是主动渲染（需要将控件压缩到andyui）
 *
 * 需求：
 * 1.可以设置最小值和最大值
 * 2.可以设置步长
 * 3.允许拖动范围，即两个拖动手柄
 * 4.x,y两个方向
 * 5.鼠标中键滚动支持
 * 6.上下（左右）键支持步长修改
 * 7.点击滑动条任意位置可以修改数值
 *
 * 返回数据为 值和百分比两种
 *
 * 显示  有输入框的数字显示在输入框
 *       没有的直接显示在拉杆上
 */
(function (andy) {
    andy.pluginBone = andy.pluginBone || {};
    andy.pluginBone.slider =
        '<div class="slider-inner" style="width: 200px;">'
        + '<i class="iconfont slider-handle" {{if orientation==="horizontal"}} lockx="true" {{else}} locky="true" {{/if}} slider-handle="true" style="left: 0%;">&#xe70c;</i>'
        + '<span class="slider-tip" style="left: 0%;">{{value}}</span>'
        + '<span class="slider-min" >{{min}}</span>'
        + '<span class="slider-max" >{{max}}</span>'
        + '</div>'
        + '<input {{if !hasInput}}style="display: none"{{/if}} class="u-input xs slider-input" type="text" value="{{value}}">';


    var sliderRender,
        defaults = {
            max: 100, //@config 组件的最大值
            min: 0, //@config 组件的最小值
            width: -1,
            orientation: "horizontal", //@config 组件是水平拖动还是垂直拖动，垂直是“vertical”
            /**
             * @config 滑块是否显示滑动范围，配置值可以是true、min、max
             <p>true: 显示滑动范围</p>
             <p>min: 滑块值最小的一端固定</p>
             <p>max: 滑块值最大的一端固定</p>
             */
            range: false,
            step: 1, //@config 滑块滑动的步值
            value: 0, //@config 滑块的当前值，当range为true时，value是滑块范围表示的两个值，以“,”分隔
            values: null, //@config 当range为true时，values数组需要有两个值，表示滑块范围
            disabled: false,//@config 是否禁用滑块, 设为true时滑块禁用
            twohandlebars: false,
            hasInput:false//显示输入框,默认不显示
            ///**
            // * @config {Function} 滑动开始的回调
            // * @param event {Object} 事件对象
            // * @param data {Object} 滑动的数据信息
            // */
            //onDragStart: function () {
            //    //console.log("onDragStart");
            //    return true;
            //},
            ///**
            // * @config {Function} 滑动时的回调
            // * @param data {Object} 滑动的数据信息
            // */
            //onDrag: function () {
            //    //console.log("onDrag");
            //    return true;
            //},
            ///**
            // * @config {Function} 滑动结束时的回调
            // * @param data {Object} 滑动的数据信息
            // */
            //onDragEnd: function () {
            //    //console.log("onDrag");
            //    return true;
            //}
            //getTemplate: function(str, options) {
            //    return str;
            //}
        };
    //从dom获取数据
    var getOptionsFromDom = function (el) {
        var key, v, data = {};
        if (el.getAttribute("options")) {
            data = andy.stringToJson(el.getAttribute("options"));
        }
        for (key in defaults) {
            v = el.getAttribute(key);
            if (v) {
                data[key] = v;
            }
        }
        return data;
    };

    /**
     * 将value值转换为百分比
     * @param val
     * @returns {Number}
     */
    var value2Percent = function (val, valueMin, valueMax) { //
        if (val < valueMin) {
            val = valueMin
        }
        if (val > valueMax) {
            val = valueMax
        }
        return parseFloat(((val - valueMin) / (valueMax - valueMin) * 100).toFixed(5))
    };
    /**
     * 将percent百分比转换为值
     * @param percent //0~1
     * @returns {Number}
     */
    var percent2Value = function (percent) {
        var val = (valueMax - valueMin) * percent + valueMin
        val = correctValue(val);
        return parseFloat(val.toFixed(3))
    };

    var correctValue = function (val) {
        var step = (options.step > 0) ? options.step : 1
        var stepLength;
        try {
            stepLength = step.toString().split(".")[1].length
        }
        catch (e) {
            stepLength = 0
        }
        var m = Math.pow(10, stepLength)
        var valModStep = (val - valueMin) * m % step * m
        var n = (val - valueMin) / step
        val = (valueMin * m + (valModStep * 2 >= step ? step * m * Math.ceil(n) : step * m * Math.floor(n))) / m
        return val
    };

    var dragCaculate = function (slider, sliderHander, data, argu) {

        var prop = data.isHorizontal ? "left" : "top";
        var pixelMouse = data[prop] + parseFloat(slider.css("border-top-width"));
        //如果是垂直时,往上拖,值就越大
        var percent = (pixelMouse / vmodel.$pixelTotal); //求出当前handler在slider的位置
        if (!isHorizontal) { // 垂直滑块，往上拖动时pixelMouse变小，下面才是真正的percent，所以需要调整percent
            percent = Math.abs(1 - percent);
        }
        if (percent > 0.999) {
            percent = 1;
        }
        if (percent < 0.001) {
            percent = 0;
        }
        val = percent2Value(percent);

        if (twohandlebars) { //水平时，小的0在左边，大的1在右边，垂直时，小的0在下边，大的1在上边
            if (Index === 0) {
                var check = vmodel.values[1];
                if (val > check) {
                    val = check;
                }
            } else {
                check = vmodel.values[0];
                if (val < check) {
                    val = check;
                }
            }
            data.values[Index] = val;
            data["percent" + Index] = value2Percent(val, data.min, data.max);
            data.value = data.values.join();
            data.percent = value2Percent((data.values[1] - data.values[0] + valueMin), data.min, data.max);
        } else {
            data.value = val;
            data.percent = value2Percent(val, data.min, data.max);
        }
    };


//绑定拖动逻辑
    var eventBind = function () {
        var Handlers, Index, slider, data, tip, valueInput;

        if (andy.dragElement) {
            andy.dragElement({
                dragName: "slider-handle",
                //lockX: true,
                //notDragEl: "notdrag",
                onDragBeforeElement: function (sliderHander, argu) {
                    slider = $(sliderHander).parents(".slider");
                    data = slider.data("slider");
                    tip = slider.find(".slider-tip");
                    valueInput = slider.find("input");
                    data.pixelTotal = data.isHorizontal ? slider[0].offsetWidth : slider[0].offsetHeight;
                    //Handlers = handlers; // 很关键，保证点击的手柄始终在Handlers中，之后就可以通过键盘方向键进行操作
                    data.started = !data.disabled;
                    data.dragX = data.dragY = false;
                    //Index = handlers.indexOf(data.element);
                    if (data.onDragStart) {
                        //执行页面上，window下的方法
                        //data.onDragStart()
                    }
                    //dragEl.addClass("state-active");
                    //options.onDragStart.call(null, event, data);
                    document.body.style.cursor = "pointer";
                    return true;
                },
                onDragElement: function (sliderHander, argu) {
                    //tip跟着走

                    tip.css("left", argu.left + parseInt(tip.css("margin-left"), 10) + tip.width()/2);
                    valueInput[0].value = parseInt(argu.left / tip.parent().width() * (data.max - data.min),10)+ data.min;
                    tip.html(valueInput[0].value);
                    //dragCaculate(slider,sliderHander, data, argu);
                    //options.onDrag.call(null, vmodel, data);
                },
                onDragEndElement: function (dragEl, argu) {
                    //data.$element.removeClass("oni-state-active")
                    // dragCaculate(event, data, keyVal)
                    //options.onDragEnd.call(null, event, data);
                    document.body.style.cursor = "auto";
                    slider = undefined;
                    data = undefined;
                }
            });
        } else {
            throw "需要在这之前加载拖动drag.js";
        }

        // 当点击slider之外的区域取消选中状态
        //$(document).bind("click", function(e) {
        //    e.stopPropagation();
        //    var el = e.target;
        //    var Index = Handlers.indexOf(el);
        //    if (Index !== -1) {
        //        if (FocusElement) {
        //            FocusElement.removeClass("oni-state-focus");
        //        }
        //        FocusElement = avalon(el).addClass("oni-state-focus")
        //    } else if (FocusElement) {
        //        FocusElement.removeClass("oni-state-focus")
        //        FocusElement = null
        //    }
        //});

        // 当选中某个手柄之后通过键盘上的方向键控制手柄的slider
        $(document).bind("keydown", function (e) {
            // e.preventDefault();
            if (FocusElement) {
                var vmodel = FocusElement[0].sliderModel
                var percent = Handlers.length == 1 ? vmodel.percent : vmodel["percent" + Index]
                var val = vmodel.$percent2Value(percent / 100), keyVal;
                switch (e.which) {
                    case 34 : // pageDown
                    case 39:  // right
                    case 38:  // down
                        keyVal = Math.min(val + 1, vmodel.$valueMax);
                        break;
                    case 33: // pageUp
                    case 37: // left
                    case 40: // up
                        keyVal = Math.max(val - 1, vmodel.$valueMin);
                        break
                    case 36: // home
                        keyVal = data.min;
                        break
                    case 35: // end
                        keyVal = data.max;
                        break
                }
                if (isFinite(keyVal)) {
                    vmodel.drag(e, {}, keyVal)
                }
            }
        })
    };

    /**
     * 初始化
     * @param element
     * @param options
     */
    andy.slider = function (element, options) {
        console.log(defaults.max);
        var args = $.extend({},defaults, options);
        console.log(defaults.max);
        //console.log(options.max);

        var isHorizontal = args.orientation === "horizontal";
        //将整个slider划分为N等分, 比如100, 227
        var valueMin = args.min;
        var valueMax = args.max;
        var oRange = args.range; //true min max， 默认为false
        var values = args.values;
        var twohandlebars = oRange === true;
        var value;//第几等份
        //var value = Number(options.value) //第几等份
        //if (isNaN(value)) {
        //}
        //options.template = options.getTemplate(template, options);
        // 固定最小的一边
        if (oRange === "min" && values) {
            value = values[0]
        } else if (oRange === "max" && values) { // 固定最大的一边
            value = values[1]
        }
        // 如果没有配置value和values,且range是min或者max，重置value
        if (!value && oRange === "min" && !values && value !== 0) {
            value = valueMin || value;
        } else if (!value && oRange === 'max' && !values && value !== 0) {
            value = valueMax || value;
        }
        if (options.step !== 1 && !/\D/.test(options.step)) {
            value = correctValue(value);
        }
        // 如果滑动块有双手柄，重置values
        if (twohandlebars) {
            if (Array.isArray(values)) {
                values = values.length === 1 ? [values[0], values[0]] : values.concat();
            } else {
                values = [valueMin, valueMax];
            }
        }

        //vm.$skipArray = ["template","rootElement", "widgetElement", "step", "_dragEnd"]
        //options.rootElement = el;
        //vm.widgetElement = element
        args.step = (args.step > 0) ? args.step : 1;
        args.percent = twohandlebars ? value2Percent((values[1] - values[0] + valueMin), valueMin, valueMax) : value2Percent(value, valueMin, valueMax);
        args.percent0 = twohandlebars ? value2Percent(values[0], valueMin, valueMax) : 0;
        args.percent1 = twohandlebars ? value2Percent(values[1], valueMin, valueMax) : 0;
        args.value = twohandlebars ? values.join() : args.value;
        args.values = values;
        args.axis = isHorizontal ? "x" : "y";
        //vm.$valueMin = valueMin;
        //vm.$valueMax = valueMax;
        args.twohandlebars = twohandlebars;
        //vm.$percent2Value = percent2Value;
        args.pixelTotal = 0;
        args._dragEnd = false;

        sliderRender = sliderRender || template.compile(andy.pluginBone.slider);
        //组建
        if (!($(element).find(".slider-inner").length > 0)) {
            var str = sliderRender(args);
            $(element).append(str);
        }
        $(element).data("slider", args);
        //var sliderHTML = options.template.replace(/MS_OPTION_WIDTHORHEIGHT/g, isHorizontal ? "width" : "height").replace(/MS_OPTION_LEFTORBOTTOM/g, isHorizontal ? "left" : "bottom");
        //handlers保存滑动块上的手柄，域Handlers进行区分
        //var slider = avalon.parseHTML(sliderHTML).firstChild, handlers = [];
        //element.parentNode.insertBefore(slider, element.nextSibling);
        //$element.addClass("oni-helper-hidden-accessible");
    };

    //自动渲染
    $(document).ready(function () {
        $(".slider").each(function (index, el) {
                andy.slider(el, getOptionsFromDom(el));
            }
        );
        eventBind();
    });


}(andy));