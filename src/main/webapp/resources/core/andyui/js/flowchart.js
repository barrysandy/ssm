/*********************************************************************
 * 插件名称:直线流程图 基于raphael开发
 * 
 * 制作人: 林耘宇
 * 
 * 调用方法:$.fn.an_flowchart(); 还可以更详细这里
 * 
 * 提交时间:2015-11-25
 *********************************************************************/

 (function($) {
    var lineStyle = "";
    $.fn.extend({
        //开始loading
        an_flowchart:function() {
            var arg1 = arguments[0];
            var arg2 = arguments[1];
            var _options = {};
            var _paper;
            var _currentData = [];

            var connections = [];
            var shapes = [];
            var isNotClick = false; //不为点击事件
            var main = $(this);
            //设置默认值并用逗号隔开
            var defaults = {  
                url:"",//数据加载地址
                data:"",//传入数据

                px:0,//画布X坐标
                py:0,//画布Y坐标
                pwidth:400,//画布宽度
                pheight:400,//画布高度

                sx:0,//初始X
                sy:0,//初始y
                swidth:100,//初始宽度
                sheight:50,//初始高度
                vertical:20,//垂直间距
                horizontal:20,//横向间距

                textColor:"#ffffff",//字体默认颜色

                shapeColor:"#003399",//图形默认颜色
                shapeFrameColor:"#cccccc",//图形边框颜色
                lineColor:"#000000",//连线颜色
                lineWidth:2,//连线宽度
                lineStyle:"L",//连线样式 "C"曲线 "L"直线
                isdrag:true,//默认允许拖拽
                target:"",//默认没有渲染对象

                click:function(){}//点击事件
            };

            //如果是传入的是options 则开始进行布局
            if(typeof arg1 == "object"){
                _options = $.extend(defaults, arg1);
                lineStyle = _options.lineStyle;
                creatMain();
            }else if(typeof arg1 == "string"){
                //调用方法
            }
            function creatMain(){
                if (_options.url != ""){
                    //加载数据 全局类
                    an_loaddata(_options.url, function(data){
                        //创建内部结构
                        _currentData = data;
                        creatContent(data);
                    })
                }else if(_options.data){
                    _currentData = _options.data;
                    creatContent(_options.data);
                }
            }

            function creatContent(data){
                //创建画布
                if (_options.target == ""){ 
                    _paper = Raphael(_options.px, _options.py, _options.pwidth, _options.pheight);
                }else{
                    _paper = Raphael(_options.target, _options.pwidth, _options.pheight);
                }
                // console.log(Raphael.getColor())

                var th = _options.sy;//当前高度
                //创建对象形状
                // console.log(data.length);
                for(var i = 0;i < data.length; i++){
                    // console.log(data[i], data.length);
                    var c = new CreatCell(data[i]);
                    var x = _options.sx;
                    if (x == 0){
                        x = _options.pwidth/2;
                    }
                    th = setCell(c, data[i], x, th)//设置对象后返回对象Y坐标
                    
                    //设置下一个图形初始位置
                    th += _options.sheight + _options.vertical;

                }

                th = 0;

                //查找子集
                for(var j in data){

                    if (data[j].children){
                        var d = data[j].children;
                        var cc = new CreatCell(d[0]);
                        var x = _options.sx;
                        if (x == 0){
                            x = _options.pwidth/2;
                        }
                        var cx = x + _options.horizontal + cc.width;
                        var shapey = shapes[j].attr("y");//
                        // (shapes[j].attr("y") == 0) || shapes[j].attr("cy");
                        if(shapey == 0){
                            shapey = shapes[j].attr("cy");
                        }
                        th = shapey;
                        var cy = th;
                        setCell(cc, d[0], cx, cy);
                        cc.shape.click(clickfun);//点击出现子集
                        connections.push(_paper.connection(shapes[j], cc.shape, _options.lineColor, _options.lineColor + "|" + _options.lineWidth));
                    }
                    
                }
                
                var length = data.length - 1;
                //连线
                for(var j = 0; j < length; j++){
                    try{
                        if (j < length){
                            connections.push(_paper.connection(shapes[j], shapes[j + 1], _options.lineColor, _options.lineColor + "|" + _options.lineWidth));
                            
                        }
                    }catch(e){}
                }
                
            }

            //设置对象
            function setCell(cell, data, x, y){
                //设置图形 文本位置
                cell.label.attr({
                    x:x + _options.swidth/2,
                    y:y + _options.sheight/2
                })
                cell.shape.attr({
                    x:x,
                    y:y,
                    cx:x,
                    cy:y,
                    fill: _options.shapeColor, 
                    stroke: _options.shapeFrameColor, 
                    "fill-opacity":.8, 
                    "stroke-width": 4, 
                    cursor: "move"
                })
                if (_options.isdrag){
                    cell.shape.drag(move, dragger, up).data("label", cell.label).toBack();
                }
                
                shapes.push(cell.shape);
                return y;
            }
            
            //点击节点
            function clickfun(e){
                // console.log(e.target.id)
                if(!isNotClick){
                    var id = e.target.id;
                    getChildrenData(_currentData, id, function(data){
                        resetAll();
                        creatContent(data);
                    })
                    _options.click(e)
                }
                isNotClick = false;                
            }

            //初始化
            function resetAll(){
                _paper.remove();
                _currentData = [];

                connections = [];
                shapes = [];
            }
            
            //获取子数据
            var d = [];
            function getChildrenData(data, id, callback){
                for(var i in data){
                    try{
                        if (id == data[i].id){
                            if(d.children){
                                //预留来源 功能
                                // delete d['children']; 
                                // console.log(d)
                                // data.unshift(d);
                            }
                            
                            callback(data);
                            break;
                        }
                        if(data[i].children){
                            //克隆
                            d = $.extend({}, data[i]);
                            // d = data[i];
                            getChildrenData(data[i].children, id, callback)
                        }
                    }catch(e){}
                }
            }

            function dragger() {
                this.ox = this.type == "rect" ? this.attr("x") : this.attr("cx");
                this.oy = this.type == "rect" ? this.attr("y") : this.attr("cy");
                this.animate({"fill-opacity": .5}, 200);
            }
            function move(dx, dy) {
                var att = this.type == "rect" ? {x: this.ox + dx, y: this.oy + dy} : {cx: this.ox + dx, cy: this.oy + dy};
                this.attr(att);
                isNotClick = true;
                //处理标签内部文字
                var lb = this.data("label"); 
                var attr1 = {x: this.ox + dx + this.attr("width") / 2, y: this.oy + dy + this.attr("height") / 2}; 
                lb.attr(attr1);
                for (var i = connections.length; i--;) {
                    _paper.connection(connections[i]);
                }
                // _paper.safari();
            }
            function up() {
                this.animate({"fill-opacity": .8}, 200);
            }

            //创建形状
            function CreatCell(data){
                this.width = _options.swidth;
                this.height = _options.sheight;

                this.shape = _paper.rect(0, 0, this.width, this.height);
                this.label = _paper.text(this.width/2, this.height/2, data.name);
                
                //添加id
                this.shape.id = data.id;
                this.shape.node.id = data.id;//加入nodeid
                
                this.label.attr({fill:_options.textColor});
            }

            //创建三角形 传入边长
            function creatTriangle(line){
                var x1 = 0,  x2 = line, y1 = 0, y2 = 0;
                var x3 = line/2, y3 = line;
                return _paper.path("M" + x1 +","+ y1 +
                    "L"+ x2+","+ y2 +"L"+ x3+","+ y3 +
                    "L"+ x1+","+ y1+ "z").attr({fill: "#000", stroke: "none"});
            }

        }
        
    })

    // 连接线
    Raphael.fn.connection = function (obj1, obj2, line, bg) {
        if (obj1.line && obj1.from && obj1.to) {
            line = obj1;
            obj1 = line.from;
            obj2 = line.to;
        }
        var bb1 = obj1.getBBox(),
            bb2 = obj2.getBBox(),
            p = [{x: bb1.x + bb1.width / 2, y: bb1.y - 1},
            {x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1},
            {x: bb1.x - 1, y: bb1.y + bb1.height / 2},
            {x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2},
            {x: bb2.x + bb2.width / 2, y: bb2.y - 1},
            {x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1},
            {x: bb2.x - 1, y: bb2.y + bb2.height / 2},
            {x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2}],
            d = {}, dis = [];
        for (var i = 0; i < 4; i++) {
            for (var j = 4; j < 8; j++) {
                var dx = Math.abs(p[i].x - p[j].x),
                    dy = Math.abs(p[i].y - p[j].y);
                if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
                    dis.push(dx + dy);
                    d[dis[dis.length - 1]] = [i, j];
                }
            }
        }
        if (dis.length == 0) {
            var res = [0, 4];
        } else {
            res = d[Math.min.apply(Math, dis)];
        }
        var x1 = p[res[0]].x,
            y1 = p[res[0]].y,
            x4 = p[res[1]].x,
            y4 = p[res[1]].y;

        //箭头构造
        var ax1 = x4 - 5,
            ax2 = x4 + 5,
            ay1 = y4 - 10,
            ay2 = y4 - 10;
        if (x4 >= bb2.x +bb2.width){
            ax1 = x4 + 10,
            ax2 = x4 + 10,
            ay1 = y4 - 5,
            ay2 = y4 + 5;
        }else if (x4 <= bb2.x){
            ax1 = x4 - 10,
            ax2 = x4 - 10,
            ay1 = y4 - 5,
            ay2 = y4 + 5;
        }else if(y4 >= bb2.y + bb2.height){
            ax1 = x4 - 5,
            ax2 = x4 + 5,
            ay1 = y4 + 10,
            ay2 = y4 + 10;
        }
        var arrow = ["M", x4, y4, "L", ax1, ay1, "L", ax2, ay2, "L", x4, y4, "Z"].join(",");

        dx = Math.max(Math.abs(x1 - x4) / 2, 10);
        dy = Math.max(Math.abs(y1 - y4) / 2, 10);
        var x2 = [x1, x1, x1 - dx, x1 + dx][res[0]].toFixed(3),
            y2 = [y1 - dy, y1 + dy, y1, y1][res[0]].toFixed(3),
            x3 = [0, 0, 0, 0, x4, x4, x4 - dx, x4 + dx][res[1]].toFixed(3),
            y3 = [0, 0, 0, 0, y1 + dy, y1 - dy, y4, y4][res[1]].toFixed(3);
        var path = ["M", x1.toFixed(3), y1.toFixed(3), lineStyle, x2, y2, x3, y3, x4.toFixed(3), y4.toFixed(3)].join(",");
        if (line && line.line) {
            line.bg && line.bg.attr({path: arrow});
            line.line.attr({path: path});
        } else {
            var color = typeof line == "string" ? line : "#000"; 
            return {
                bg:this.path(arrow).attr({fill:"#000",stroke: "none"}), //bg && bg.split && this.path(path).attr({stroke: bg.split("|")[0], fill: "none", "stroke-width": bg.split("|")[1] || 3}),
                line: this.path(path).attr({stroke: color, fill: "none"}),
                from: obj1,
                to: obj2
            };
        }
    }
})(jQuery);