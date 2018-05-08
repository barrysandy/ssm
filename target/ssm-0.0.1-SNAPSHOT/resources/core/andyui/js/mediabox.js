/*global  window , an_IE,document,console,template */

/**
 * 多媒体盒子
 * 类型是根据类型名字，方便以后丰富
 * author:chenxiaoying
 * 使用了js数据模板的引擎
 **/
(function ($) {
    "use strict";
    var getHTML = function (o) {
        var htmltext,defaults = $.fn.mediabox.defaults[o.type], render;
        if (defaults) {
            o = $.extend({}, defaults, o);
            render = template.compile(o.source);
            htmltext = render(o);
        }
        return htmltext;
    };    //构造函数
    $.fn.mediabox = function (options, params) {
        var  method;
        if (typeof options === "string") {
            method = $.fn.mediabox.methods[options];
            if (method) {
                //有mothed则调用之
                return method(this, params);
            }
            throw 'The method of' + options + 'is undefined';
            return false;
        }        return this.each(function () {
            var htmlList;
            htmlList = getHTML(options);

            if (this.nodeName !== "#document") {
                this.innerHTML = htmlList;
            }
        });
    };
    //定义对外接口方法
    $.fn.mediabox.methods = {
        options: function (jq) {
            var opts = window.top.$.data(jq[0], "mediabox").options;
            return opts;
        },
        get: function (jq, options) {
            return getHTML(options);
        },
        refresh: function (jq, data) {

        }
    };
    $.fn.mediabox.defaults = {
        //题目列表
        titleList: {
            type: "titleList",
            styleType: "none",//disc square decimal 指定条目前的样式，提供几个好看的样式，如果没有就是css默认的
            top: 1,//头条的数量
            list: [{herf: "#", title: "如果中国进入“零利率”时代", titleR: "2015-12-16"}, {
                herf: "#",
                title: "如果中国进入“零利率”时代",
                titleR: "2015-12-16"
            }],
            source: '<ol style="padding-right: 10px; margin-bottom: 0px;">{{each list as value i}}<li style="{{if top && i<top}} font-weight:bold; {{/if}} list-style-type:{{styleType}}">' +
            '<a href="{{value.href}}">{{value.title||value}}</a>' +
            '{{if value.titleR}}  <a href="{{value.href}}" style="float: right">{{value.titleR}}</a>{{/if}}</li>{{/each}}</ol>'
        },
        imgTitleList: {
            type: "imgTitleList",
            width: 20,
            height: 20,
            list: [{
                href: "http://www.baidu.com",
                title: "如果中国进入“零利率”时代",
                titleR: "2015-12-16"
            }, {title: "如果中国进入“零利率”时代", imgsrc: "/Style/img/b2.jpg"}, {
                title: "如果中国进入“零利率”时代",
                imgsrc: "/Style/img/b3.jpg"
            }, {title: "如果中国进入“零利率”时代", imgsrc: "/Style/img/b3.jpg"}],//路径列表
            source: '<ol style="padding:0px 10px; margin-bottom: 0px;">{{each list as value i}}<li>'
            + '<img src="{{value.imgsrc||"#"}}" style="height: {{height}}px; width: {{width}}px; margin-right:5px;">'
            + '<a href="{{value.href}}">{{value.title}}</a>'
            + '{{if value.titleR}}  <a href="{{value.href}}" style="float: right">{{value.titleR}}</a>{{/if}}'
            + '</li>{{/each}}</ol>'
        },
        //背景题目块
        colorTitleBlock: {
            type: "titleList",
            backgroundColor: "#23b7e5",
            equally:true,//是否平均显示
            list: ["我们都是好孩子", "我们", "是好孩子"],
            source: '{{each list as value i}}' +
            '<div class="{{if equally}} col-{{12/list.length}}{{else}}pull-left m{{/if}}">' +
            '<div class="m-media-bgbox" style="background-color:{{backgroundColor}}">{{value}}</div></div>{{/each}}'+
            '<div style="clear: both"></div>'
        },
        titleContentList: {
            type: "titleContentList",
            list: [{title: "没有数据", content: "没有数据，或获取数据出错，请检查！"}, {title: "没有数据", content: "没有数据，或获取数据出错，请检查！"}],
            source: '<div class="list-group">{{each list as value i}}<div class="list-group-item" ><h4 class="list-group-item-heading">{{value.title}}</h4><p class="list-group-item-text" style="opacity: 0.8">{{value.content}}</p></div>{{/each}}</div>'
        },
        imgList: {
            type: "imgList",
            styleType: "rounded", //rounded circle thumbnail
            width: 80,
            height: 80,
            list: [{imgsrc: "/Style/img/b1.jpg"}, {imgsrc: "/Style/img/b2.jpg"}, {imgsrc: "/Style/img/b3.jpg"}],//路径列表
            source: '{{each list as value i}}<img class="img-{{styleType}}" src="{{value.imgsrc||"#"}}"  style = "margin: 5px; width:{{width}}px; height:{{height}}px;">{{/each}}'
        },
        imgTitleBlock: {
            type: "imgTitleBlock",
            height: 180,
            width: 180,
            list: [{title: "没有图片"}, {title: "没有图片"}, {title: "没有图片"}, {
                title: "好好的",
                imgsrc: "/Style/img/b2.jpg"
            }, {title: "好好的", imgsrc: "/Style/img/b3.jpg"}, {title: "好好的", imgsrc: "/Style/img/b3.jpg"}],//路径列表
            source: '{{each list as value i}}<div style="height: {{height}}px; width:{{width}}px; float: left; margin: 5px" >'
            + '<img src="{{value.imgsrc||"#"}}" style="height: {{height}}px; width:{{width}}px; display: block;">'
            + '<div class="m-media-imgfoot" > {{value.title}} </div></div>{{/each}}'
            + '<div style="clear: both"></div>'
        },
        imgTitleContentList: {
            type: "imgTitleContentList",
            height: 80,
            width: 80,
            styleType: "rounded", //rounded circle thumbnail
            list: [{
                title: "好好的",
                imgsrc: "/Style/img/b3.jpg",
                content: " Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo Cras purus odio, vestibulum "
            },
                {
                    title: "好好的",
                    imgsrc: "/Style/img/b2.jpg",
                    content: " Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo Cras purus odio, vestibulum "
                },
                {
                    title: "好好的",
                    imgsrc: "/Style/img/b4.jpg",
                    content: " Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo Cras purus odio, vestibulum "
                }],//路径列表
            source: '{{each list as value i}}'
            + '<div class="m-media">'
            + '<div class="m-media-left">'
            + '<img class="m-media-object img-{{styleType}}" src="{{value.imgsrc||"#"}}" style="height: {{height}}px;width:{{width}}px;display: block;">'
            + '</div>'
            + '<div class="m-media-body"><h3 class="m-media-heading"> {{value.title}} </h3>{{value.content}}'
            + '</div></div>{{/each}}'
        },
        imgTitleContentBlock: {
            type: "imgTitleContentBlock",
            height: 200,
            width: 200,
            styleType: "rounded", //rounded circle thumbnail
            list: [{
                title: "好好的",
                imgsrc: "/Style/img/b3.jpg",
                content: " Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo Cras purus odio, vestibulum "
            },
                {
                    title: "好好的",
                    imgsrc: "/Style/img/b2.jpg",
                    content: " Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo Cras purus odio, vestibulum "
                },
                {
                    title: "好好的",
                    imgsrc: "/Style/img/b4.jpg",
                    content: " Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo Cras purus odio, vestibulum "
                }],//路径列表
            source: '{{each list as value i}}'
            + '<div style="width: {{width+20}}px;float:left;margin: 5px;">'
            + '<div class="thumbnail">'
            + '<img class="m-media-object img-{{styleType}}" src="{{value.imgsrc||"#"}}" style="height: {{height}}px;width:{{width}}px;display: block;">'
            + ' <div class="caption">'
            + '<h3>{{value.title}} </h3>'
            + '<p>{{value.content}}</p>'
            + '</div></div></div>{{/each}}'
            + '<div style="clear: both"></div>'
        }
    };
})(jQuery);