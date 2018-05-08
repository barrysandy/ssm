/*global andy,$,template*/
/**工具提示模块**/
(function (andy, $) {
    var tooltipRender;
    var htmlStr = '<div class="infobox" style="width:{{width}};top:{{top}}; left:{{left}}; bottom:{{bottom}}; right:{{right}}; display: none;">' +
        '<i class="iconfont {{ip}}">{{itype}}</i>' +
        '<div class="content">{{content}}</div>' +
        '</div>';
    var defaults = {
        width: 'auto',
        top: 0,
        left: 0,
        bottom: 'auto',
        right: 'auto',
        ip: "b",
        itype: '&#xe605;',
        content: "提示",
        position :'bottom'
    };
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
     * tooltip 提示
     * @param element 需要提示的对象
     * @param position 方位，
     * @param content 需要提示的内容
     * @param clazz 样式，为空为黑色，"terr"为红色
     * @param isMouseOverShow bool是否鼠标移上去显示  未定义的话代表外部控制
     */
    andy.tooltip = function (element, position, content, clazz, isMouseOverShow) {
        var that = {}, infobox,
            data = $.extend({},defaults, {content:content,position:position});

        if (!element.parent().is('.m-tooltip')) {
            // 在 套div时候 给当前对象-2PX
            element.wrap("<div class='m-tooltip " + clazz + "'></div>");
        }
        if (position == 'bottom') {
            data.top = element.outerHeight() + 2 + 'px';
            data.itype = '&#xe605;';
        } else if (position == 'top') {
            data.top = 'auto';
            data.bottom = element.outerHeight() + 2 + 'px';
            data.ip = 't';
            data.itype = '&#xe611;';
        } else if (position == 'left') {
            data.ip = 'l';
            data.left = (-content.length * 15 + 5) + "px";
            data.itype = '&#xe610;';

        } else if (position == 'right') {
            data.ip = 'r';
            data.left = element.outerWidth() + "px";
            data.width = content.length * 15 + "px";
            data.itype = '&#xe612;';
        }

        tooltipRender = tooltipRender || template.compile(htmlStr);

        $(tooltipRender(data)).insertAfter(element);
        infobox = element.next('.infobox');

        that.show = function () {
            infobox.fadeIn(200);
        };
        that.hide = function () {
            infobox.fadeOut(200);
        };
        that.change = function (str) {
            infobox.find('.content').html(str);
        };

        if (isMouseOverShow) {
            element.on("mouseover", function () {
                that.show();
            });
            element.on("mouseleave", function () {
                that.hide();
            });
        }
        return that;
    };

    andy.showTooltip = function (el,hideAfterShow) {
        $(el).next('.infobox').fadeIn(200);
        if(hideAfterShow){
            setTimeout(function(){
                $(el).next('.infobox').fadeOut(2500);
            },500);
        }
    };
    andy.hideTooltip = function (el) {
        $(el).next('.infobox').fadeOut(200);
    };
    andy.changeTooltip = function (el, str) {
        $(el).next('.infobox').find('.content').html(str);
    };

    //自动渲染
    $(document).ready(function () {
        $(".tooltip").each(function (index, el) {
            var data = getOptionsFromDom(el);
                andy.tooltip($(el),data.position,data.content,data.type,data.isMouseOverShow);
            }
        );
    });

    return andy;
}(andy, $));
