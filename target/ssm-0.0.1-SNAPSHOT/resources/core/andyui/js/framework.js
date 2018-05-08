/*global andy,template,$,jQuery*/
/**
 * 框架
 * author:chenxiaoying
 *
 * 由各个元件（顶部菜单，左侧菜单，等）独立的方法和参数形式存在
 * 方便编辑器接入
 * 可以应对千变万化的需求
 *
 1.数据 统一JSO区分 常用不常用
 2.当有一级菜单且为常用菜单时，头部导航显示一级菜单带图标；如果没有一级常用菜单，不显示头部导航（整个头部导航部分去除，不包括面包屑）；
 3.面包屑
 5.当点击头部一级菜单时，左侧滑动出当前菜单下子集菜单（手风琴菜单出现最好做一个css 动画，向下滑动渐变出现）
 6.右侧为当前所选节点下的不常用菜单，当前仅显示子集一级菜单先不做扩展（如果能支持多级最好）；右侧菜单随用户点击打开的业务菜单变化动态变化；
 7.当头部的一级菜单没有子集时，仅打开对应的页面，不在展开左侧导航面版
 8.参数支持配置打开默认页面
 9.保留原有的仅显示头部菜单模式，即头部多级下拉菜单，但注意头部仅显示常用菜单，不常用菜单还是在右侧隐藏菜单中显示
 */
(function (andy) {
        var module = {},
        //菜单数据
            menuData,
        //不常用菜单数据，遍历获得，避免二次获取数据{menu1:[1,2,3]}
            unusualMenu = {},
        //模板预编译
            unusualMenuRender, leftMenuRender, topMenuRender, pathRender,
        //一级菜单滑动
            maskLeft, maskRight, leftBt, rightBt;


        var topMenuStr = '<ul class="u-topmenuul">'
            + '{{each list as value i}}'
            + '<li class="ic" id="{{list[i].id}}">'
            + '<a href="{{if list[i].url}}{{list[i].url}}{{else}}javascript:void(0){{/if}}" target={{list[i].target||centerIframeID}} index="{{i}}" class="rootmenu">'
            + '<i class="iconfont classicon">{{list[i].icons}}</i>'
            + '{{list[i].text}}'
            + '{{if list[i].children}}{{if isShowChild}}<i class="iconfont moreicon"></i>{{/if}}{{/if}}</a>'
            + '{{if list[i].children}}'
            + '{{if isShowChild}}'
            + '<ul style="width: 200px; display: none;">'
            + '{{each list[i].children as value j}}'
            + '<li id="{{list[i].children[j].id}}" class="mor">'
            + '<a  href="{{if list[i].children[j].url}}{{list[i].children[j].url}}{{else}}javascript:void(0){{/if}}" index="{{i}}_{{j}}" target={{list[i].children[j].target||centerIframeID}} class="">{{list[i].children[j].text}}{{if list[i].children[j].children}}<i class="iconfont moreicon"></i>{{/if}}</a>'
            + '<ul style="display: none;">'
            + '{{each list[i].children[j].children as value k}}'
            + '<li id="{{list[i].children[j].children[k].id}}" class="mor"><a href={{list[i].children[j].children[k].url}} index="{{i}}_{{j}}_{{k}}" target={{list[i].children[j].children[k].target||centerIframeID}} class="">{{list[i].children[j].children[k].text}}</a></li>'
            + '{{/each}}'
            + '</ul>'
            + '</li>'
            + '{{/each}}'
            + '</ul>'
            + '{{/if}}{{/if}}'
            + '</li>'
            + '{{/each}}'
            + '</ul>';
        //+ '<div id="maskLeft" style="z-index:33;"></div>'
        //+ '<div id="maskRight" style="z-index: 33;"></div>'
        //+ '<div id="leftBt"><i class="iconfont">&#xe612;</i></div>'
        //+ '<div id="rightBt"><i class="iconfont">&#xe610;</i></div>';

        var leftMenuStr = ' {{each list as value i}}'
            + '{{if list[i].children}}'
            + '<ul class="u-sidemenu" id="leftM_{{i}}" style="display: none;">'
            + '{{each list[i].children as value j}}'
            + '<li id="{{list[i].children[j].id}}" class="{{if list[i].children[j].icons}}ic{{/if}} mor n1">'
            + '<a href="{{if list[i].children[j].url}}{{list[i].children[j].url}}{{else}}javascript:void(0){{/if}}" index="{{i}}_{{j}}" target="{{list[i].children[j].target||centerIframeID}}">{{if list[i].children[j].icons}}<i class="iconfont classicon" title="{{list[i].children[j].text}}">{{list[i].children[j].icons}}</i>{{/if}}'
            + '{{list[i].children[j].text}}'
            + '{{if list[i].children[j].children}}<i class="iconfont moreicon"></i>{{/if}}</a>'
            + ' {{if list[i].children[j].children}}'
            + '<ul class="nav-sub" style="display: none;">'
            + '{{each list[i].children[j].children as value k}}'
            + '<li id="{{list[i].children[j].children[k].id}}" class="n2">'
            + '<a href = "{{if list[i].children[j].children[k].url}}{{list[i].children[j].children[k].url}}{{else}}javascript:void(0){{/if}}" index="{{i}}_{{j}}_{{k}}" target="{{list[i].children[j].children[k].target||centerIframeID}}">'
            + '<i class="iconfont classicon" title="{{list[i].children[j].children[k].text}}">{{list[i].children[j].children[k].icons}}</i>{{list[i].children[j].children[k].text}}{{if list[i].children[j].children[k].children}}<i class="iconfont moreicon">&#xe607;</i>{{/if}}</a>'

            + ' {{if list[i].children[j].children[k].children}}'
            + '<ul class="nav-sub" style="display: none;">'
            + '   {{each list[i].children[j].children[k].children as value l}}'
            + '<li class="n3" id="{{list[i].children[j].children[k].children[l].id}}">'
            + '<a href = "{{list[i].children[j].children[k].children[l].url}}" index="{{i}}_{{j}}_{{k}}_{{l}}" target="{{list[i].children[j].target||centerIframeID}}">{{list[i].children[j].children[k].children[l].text}}{{if list[i].children[j].children[k].children[l].children}}<i class="iconfont moreicon">&#xe607;</i>{{/if}}</a>'
            + '</li>'
            + '{{/each}}'
            + '</ul>'
            + '{{/if}}'
            + '</li>'
            + '{{/each}}'
            + '</ul>'
            + '{{/if}}'
            + '</li>'
            + '{{/each}}'
            + '</ul>'
            + '{{/if}}'
            + '{{/each}}';
        var pathCfg = '{{each list as value i}} {{if list[i].text}} <li {{if i===list.length-1}}class="activate"{{/if}}> <a href="javascript:void(0)" target="stage">{{list[i].text}}</a> {{if i!==list.length-1}}<i class="iconfont"></i>{{/if}}</li>{{/if}}{{/each}}';
        /**
         * 对于一级菜单过多，增加左右两个按钮控制，判断是否有隐藏部分
         * 步长为菜单的宽度
         */
        var topMenuOverFlow = function () {
            var lastIndex, ulBox, topmenu, bg, topmenuPw, topmenu_ml, topmenu_mr, absolutWidth, fixBoxWidth, showBt,
                leftDataArr = [0],
                getLastNode,
                getNodesWidth,
                getLastNode,
                liList,//菜单列表
                offsetCount = 0;//偏移的菜单个数

            /**
             * 获取当前显示的最后一个菜单
             */
            getLastNode = function () {
                var i;
                lastIndex = liList.length - 1;
                for (i = offsetCount; i < liList.length; i++) {
                    if (liList[i].offsetLeft > -ulBox.offsetLeft + absolutWidth) {
                        //上一个就是最后一个
                        lastIndex = i - 1;
                        break;
                    }
                }
                return liList[lastIndex];
            };

            /**
             * 获取一段菜单的宽度
             */
            getNodesWidth = function (start, end) {
                var i, width = 0;
                end = end || liList.length - 1;
                for (i = start; i <= end; i++) {
                    width += liList[i].offsetWidth;
                }
                return width + 1;
            };

            /**
             *   是否显示按钮
             */
            showBt = function () {
                fixBoxWidth();
                leftBt[0].style.display = ulBox.offsetLeft >= 0 ? "none" : "block";
                rightBt[0].style.display = lastIndex === liList.length - 1 ? "none" : "block";
            };

            /**
             * 修正宽度
             */
            fixBoxWidth = function () {
                var lastNode = getLastNode();
                if (lastIndex !== liList.length - 1) {
                    topmenu.width(lastNode.offsetLeft + ulBox.offsetLeft);
                }
            };


            topmenu = $('.u-topmenu');
            topmenu_ml = parseInt(topmenu.css('margin-left'), 10);
            topmenu_mr = parseInt(topmenu.css('margin-right'), 10);

            topmenuPw = topmenu.parent().width();
            absolutWidth = topmenuPw - topmenu_ml - topmenu_mr;
            topmenu.width(absolutWidth);


            bg = topmenu[0];
            //ul层
            ulBox = topmenu[0].children[0];
            liList = ulBox.children || ulBox.childNodes;
            //菜单个数*宽度
            ulBox.style.width = getNodesWidth(0) + "px";


            fixBoxWidth();

            if (!leftBt) {
                leftBt = $('#leftBt');
                rightBt = $('#rightBt');
//
                $('#leftBt,#rightBt').css({
                    "height": bg.offsetHeight,
                    "lineHeight": bg.offsetHeight + "px",
                    "top": bg.offsetTop,
                    "z-index": 34
                });

                leftBt.css({"left": topmenu_ml - 18});
                rightBt.css({"right": topmenu_mr - 18});
            }

            if (ulBox.offsetWidth > absolutWidth) {
                leftBt.hide();
                rightBt.show();

                leftBt.off("click");
                rightBt.off("click");

                leftBt.on("click", function () {
                    leftDataArr.pop();
                    var left = leftDataArr[leftDataArr.length - 1];
                    $(ulBox).animate({"left": left}, "fast", function () {
                        showBt();
                    });
                });
                rightBt.on("click", function () {
                    var left = -liList[lastIndex].offsetLeft;
                    leftDataArr.push(left);
                    $(ulBox).animate({"left": left}, "fast", function () {
                        showBt();
                    });
                });

            } else {
                ulBox.style.left = "0px";
                leftBt.hide();
                rightBt.hide();
            }
        };
        /**
         * 左侧菜单溢出需要滚动
         */
        var leftMenuOverFlow = function () {
            var top_val = 0;
            var cBox = $('#menu-nav-left');
            var outBox = $('.layout-left');
            var cHeight = cBox.height();
            var outHeight = outBox.height() - 43;
            cBox.unmousewheel();
            if (cHeight > outHeight) {
                cBox.css({"position": "relative", "z-index": 0});
                cBox.mousewheel(function (event, delta, deltaX, deltaY) {
                    if (cBox.is(":animated")) {
                        return;
                        //cBox.stop();
                    }
                    top_val += deltaY * 80;
                    if (top_val > 0) {
                        top_val = 0;

                    } else if (top_val < outHeight - cHeight) {
                        top_val = outHeight - cHeight;
                    }
                    var cTop = parseInt(cBox.css("top"), 10);
                    if (parseInt(cBox.css("top"), 10) !== top_val) {
                        if (Math.abs(top_val - cTop) < 20) {
                            cBox.css({top: top_val});
                        } else {
                            cBox.animate({top: top_val}, 300, "linear");
                        }

                    }
                });
            }
        };
        module.leftMenuOverFlow = leftMenuOverFlow;

        /**
         * 更新路径
         * @param e
         * @param parent 路径父节点
         * @param mainUl 首页路径
         */
        module.refreshPath = function (e, parent, mainUl) {
            //var indexs = event.target.getAttribute("index").split("_");
            var indexs = e.getAttribute("index").split("_");
            var i = 0, str = "";
            var list = [], data = jQuery.extend(true, {}, menuData);
            for (i; i < indexs.length; i++) {
                data = data[indexs[i]] || data.children[indexs[i]];
                if (data) {
                    //中超需要在面包导航前面加一级菜单的前置说明
                    if (data.pathPre) {
                        list.push({text: data.pathPre, url: ""});
                    }
                    list.push(data);
                }
            }
            if (list.length > 0) {
                pathRender = pathRender || template.compile(pathCfg);
                str = pathRender({list: list});
            }
            parent.innerHTML = str;
            if (str.indexOf("首页") < 0) {
                var hrefA = mainUl && mainUl !== "" ? "href=" + mainUl : "";
                $(parent).prepend('<li class="activate home"><a ' + hrefA + ' target="stage"><i class="iconfont homeicon">&#xe609;</i>首页<i class="iconfont"></i></a></li>');
            } else {
                $(parent).children().first().addClass("home").find("a").prepend('<i class="iconfont homeicon">&#xe609;</i>');
            }
            if (!(mainUl && mainUl !== "")) {
                $(".home").off("click");
                $(".home").on("click", function () {
                    module.chooseTopFirstMenu();
                });
            }
        };


        /**
         * 激活不常用菜单
         * @param parentSelector 父节点的选择器 例如#menu-right
         * @param chooseCallback 点击以后的回调
         */
        module.unusualMenu = function (parentSelector, chooseCallback) {
            $("[an-combo]").an_combo({});
            $(document).on('click', parentSelector + " a", function (e) {
                var $this = $(e.target);
                //如果没有a 就返回a的第一个父级
                if (!$this.is('a')) {
                    $this = $this.closest('a');
                }
                chooseCallback($this[0]);
            });
        };
        /**
         * 更新不常用菜单
         * @param data 被选中节点的数据
         */
        module.refreshUnusualMenu = function (parent, index) {
            var list = [], getMenu;
            var str = "", sample = '{{each list as value i}} <li class="mor"> <a href="{{list[i].url}}" target="{{list[i].target}}" index="{{list[i].index}}">{{list[i].text}}</a></li>{{/each}}';
            var data = menuData[index];


            //获取不常用菜单的数据
            getMenu = function (data, indexS) {
                var i = 0, indexV;
                for (i; i < data.children.length; i++) {
                    indexV = indexS === "" ? indexS + i : indexS + "_" + i;
                    if (data.children[i].often === false) {
                        data.children[i].index = indexV;
                        list.push(data.children[i]);
                    }
                    if (data.children[i].children && data.children[i].children.length > 0) {
                        getMenu(data.children[i], indexV);
                    }
                }
            };
            if (data.children && data.id) {
                if (unusualMenu[data.id]) {
                    list = unusualMenu[data.id];
                } else {
                    getMenu(data, index);
                    unusualMenu[data.id] = list;
                }
                if (list.length > 0) {
                    unusualMenuRender = unusualMenuRender || template.compile(sample);
                    str = unusualMenuRender({list: list});
                }
            }
            parent.innerHTML = str + '<li class="mor"><a href="help.html" target="stage"><i class="iconfont"></i>帮助？</a></li>';
        };
        /**
         * 顶部菜单
         * @param parent
         * @param centerIframeID
         * @param chooseCallback
         * @param isShowChild
         */
        module.topMenu = function (parent, centerIframeID, isShowChild, chooseCallback) {
            var str, lastRoot, lastUl, currentFirstMenu;
            centerIframeID = centerIframeID || "stage";
            topMenuRender = topMenuRender || template.compile(topMenuStr);
            str = topMenuRender({list: menuData, isShowChild: isShowChild, centerIframeID: centerIframeID});
            parent.innerHTML = str;
            $(parent.parentNode).append('<div id="leftBt"><i class="iconfont">&#xe612;</i></div>'
                + '<div id="rightBt"><i class="iconfont">&#xe610;</i></div>');

            topMenuOverFlow();

            module.clickTopChild = function ($this) {
                var root, isRoot;
                isRoot = $this.is('.rootmenu');
                if (isRoot) {
                    root = $this;
                } else {
                    $(this).parentsUntil('.u-topmenuul').each(function (index, e) {
                        var elA = e.children[0];
                        if ($(elA).is('.rootmenu')) {
                            root = $(elA);
                            return false;
                        }
                    });
                }

                //切换按钮状态
                root.addClass("activate");
                if (currentFirstMenu && root[0] !== currentFirstMenu[0]) {
                    currentFirstMenu.removeClass("activate");
                }
                currentFirstMenu = root;

                chooseCallback(root[0], this);
            };

            module.chooseTopFirstMenu = function () {
            	if(menuData.length>0){
	                var defA = document.getElementById(menuData[0].id).children[0];
	                $(defA).addClass("activate");
	                if (currentFirstMenu && defA !== currentFirstMenu[0]) {
	                    currentFirstMenu.removeClass("activate");
	                }
	                if (defA.href) {
	                    $("#" + centerIframeID).attr("src", defA.href);
	                }
	
	                chooseCallback(defA);
	                currentFirstMenu = $(defA);
            	}
            };
            module.getSelectedTopMenu = function () {
                return {
                    id: currentFirstMenu.parent()[0].id,
                    index: currentFirstMenu.attr("index"),
                    nodeA: currentFirstMenu,
                    nodeLi: currentFirstMenu.parent()
                };
            };


            $(window).resize(function () {
                setTimeout(topMenuOverFlow, 400);
            });
            if (!isShowChild) {
                //绑定一级菜单点击事件
                $(".ic").find("a:first").on("click", function (event) {
                    //切换按钮状态
                    if (currentFirstMenu) {
                        currentFirstMenu.removeClass("activate");
                    }
                    $(this).addClass("activate");
                    currentFirstMenu = $(this);
                    chooseCallback(this);
                });
            } else {
                //////如果有子节点//////////////////////////////////////////////////////
                //鼠标移入
                $(document).on('mouseenter', '.u-topmenuul a', function (e) {
                    if ($(this).is('.rootmenu')) {
                        if (lastRoot) {
                            lastRoot.next().animate({height: 'hide'}, "fast");
                            lastRoot.contents(".iconfont.moreicon").css({transform: "rotate(0deg)"});
                        }
                        lastRoot = $(this);
                        $(this).contents(".iconfont.moreicon").css({transform: "rotate(180deg)"});
                    }
                    //如果不是上一级的子菜单,收起上一个菜单
                    if (lastUl && $(this).parent().parent()[0] !== lastUl[0]) {
                        lastUl.animate(lastUl.is('.rootmenu') ? {height: 'hide'} : {width: 'hide'}, "fast");
                    }
                    $(this).next().animate($(this).is('.rootmenu') ? {height: 'show'} : {width: 'show'}, "fast");
                    lastUl = $(this).next();
                });
                //鼠标移出
                $(document).on('mouseleave', '.u-topmenuul', function () {
                    if (lastRoot) {
                        //lastRoot.removeClass("active");
                        lastRoot.next().animate({height: 'hide'}, "fast");
                        lastRoot.contents(".iconfont.moreicon").css({transform: "rotate(0deg)"});
                        lastRoot = undefined;
                    }
                    if (lastUl) {
                        lastUl.hide();
                        lastUl = undefined;
                    }
                });
                //鼠标点击
                $(document).on('click', '.u-topmenuul a', function (e) {
                    module.clickTopChild($(e.target));
                });


                //默认选中第一个菜单////////////////////////////////////////////////
                //module.chooseTopFirstMenu();


            }
        };

        module.leftLastUl = {};

        /**
         * 左边菜单
         * @param parent
         * @param centerIframeID  被切换内容的ifream
         * @param chooseCallback 回调
         * @param onlyLeftMenu 是否只有左侧菜单 没有传为 false
         */
        module.leftMenu = function (parent, centerIframeID, chooseCallback, onlyLeftMenu) {
            var str, leftLastUl = module.leftLastUl, lastA, lastCA, lastCCUL;
            onlyLeftMenu = onlyLeftMenu === undefined ? false : onlyLeftMenu;

            menuData = onlyLeftMenu ? [{children: menuData}] : menuData;

            leftMenuRender = leftMenuRender || template.compile(leftMenuStr);
            str = leftMenuRender({list: menuData, centerIframeID: centerIframeID});
            parent.innerHTML = str;

            if (!onlyLeftMenu && !parent.children[0]) {
                parent.style.display = "none";
                return;
            }
            if(onlyLeftMenu){
                parent.children[0].style.display = "block";
            }


            leftMenuOverFlow();
            $(window).resize(function () {
                setTimeout(leftMenuOverFlow, 400);
            });

            //如果只有左侧菜单默认选中第一个
            if (onlyLeftMenu) {
                var firstLi = $("#leftM_0:first-child").children().first();
                firstLi.toggleClass("activate");
                leftLastUl["leftM_0"] = firstLi.find("a");
                leftLastUl["leftM_0"].toggleClass("activate");
            }
            /**
             * 获取当前选中的菜单项
             * @returns {{id: *, index: *, nodeA: *, nodeLi: *}}
             */
            module.getSelectedLeftMenu = function () {
                return {
                    id: lastA.parent()[0].id,
                    index: lastA.attr("index"),
                    nodeA: lastA,
                    nodeLi: lastA.parent()
                };
            };


            module.clickLeftChild = function ($this) {
                var index;
                $this = $this.is('a') ? $this : $this.closest('a');//如果没有a 就返回a的第一个父级
                index = $this.parent().parent().attr("id");
                //第一层a
                if (index) {
                    if (leftLastUl[index] && leftLastUl[index][0] === $this.next()[0]) {
                        if (leftLastUl[index].is('ul')) {
                            leftLastUl[index].slideUp(200);
                        }

                        leftLastUl[index].toggleClass("activate");//a 设置activate
                        leftLastUl[index].parent().toggleClass("activate");//li 设置activate

                        leftLastUl[index] = undefined;
                    } else if (leftLastUl[index] && leftLastUl[index][0] !== $this.next()[0]) {
                        if (leftLastUl[index].is('ul')) {
                            leftLastUl[index].slideUp(200);
                        }
                        $this.next().slideDown(200);

                        leftLastUl[index].toggleClass("activate");
                        leftLastUl[index].parent().toggleClass("activate");

                        leftLastUl[index] = $this.next().length > 0 ? $this.next() : $this;
                    } else {
                        $this.next().slideDown(200);
                        leftLastUl[index] = $this.next().length > 0 ? $this.next() : $this;
                    }
                    if (leftLastUl[index]) {
                        leftLastUl[index].toggleClass("activate");
                        leftLastUl[index].parent().toggleClass("activate");
                    }
                } else {
                    if (lastCA) {
                        lastCA.removeClass("activate");
                        lastCA.parent().removeClass("activate");
                    }
                    $this.addClass("activate");
                    $this.parent().addClass("activate");
                    lastCA = $this;
                }
                lastA = $this;

                chooseCallback($this[0]);
                //动画执行完以后
                setTimeout(function () {
                    leftMenuOverFlow();
                }, 300);

            };


            //////如果三级菜单有子节点//////////////////////////////////////////////////////
            //鼠标移入
            $(document).on('mouseover', '.u-sidemenu a', function (e) {
                var ull = $(this).next();
                ////如果不是上一级的子菜单,收起上一个菜单
                if (lastCCUL && $(this).parent().parent()[0] !== lastCCUL[0]) {
                    lastCCUL.width(0);
                    lastCCUL.hide();
                    //lastCCUL.animate({width: 'hide'}, "fast");
                }
                if (ull.is("ul") && ull.parent().parent().is(".nav-sub")) {
                    //ull.animate({width: 'show'}, "fast");
                    ull.show();
                    ull.width(200);
                    lastCCUL = ull;
                }
            });
            ////鼠标移出
            $(document).on('mouseleave', '.u-sidemenu', function () {
                if (lastCCUL) {
                    lastCCUL.width(0);
                    lastCCUL.hide();
                    lastCCUL = undefined;
                }
            });


            // 侧边菜单二三级点击
            $(document).on('click', '.u-sidemenu a', function (e) {
                module.clickLeftChild($(e.target));
            });
        };


        module.loadData = function (menuJsonUrl, callback) {
            if (typeof menuJsonUrl === typeof "") {
                if (menuJsonUrl.indexOf("{") > 0 || menuJsonUrl.indexOf("[") > 0) {
                    menuData = andy.stringToJson(menuJsonUrl);
                    callback(menuData);
                } else {
                    andy.loaddata(menuJsonUrl, function (data) {
                        menuData = andy.stringToJson(data);
                        callback(menuData);
                    });
                }
            } else {
                menuData = menuJsonUrl;
                callback(menuData);
            }
        };
        andy.frame = module;
    }(andy)
);