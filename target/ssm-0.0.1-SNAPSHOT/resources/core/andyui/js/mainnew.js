(function ($) {
    //预定义 json字段

    var _id = "id";
    var str = "";//增加dom字符串
    var _class = "class";
    var _url = "url";
    var _iCons = "iCons";
    var _text = "text";
    var _iCode = "iCode";
    var _children = "children";

    var _asideUserHeight = 0;//用户组高度
    var _topNavHeight = 0;//声明顶部导航高度
    var _bannerHeight = 0;//声明banner高度
    var _currentBodyHeight = 0;//声明内容高度
    var _asideMenuHeight = -0//声明侧边菜单高度

    var _currentMenuJson = []; //当前加载的菜单json
    var _menujson = [];//加载的菜单json
    var _options = {};//传入数据
    var _currentMenuId = "";//当前选择的顶部导航菜单id

    var _mainCenter = "";//获取center

    var _path = [];//路径数据
    var _pathData = [];//所有路径数据

    var maskLeft, maskRight, leftBt, rightBt;//一级菜单滑动

    var _stringid = {
        menurightid: "menu-right",//右侧菜单
        tabid: "tabs",//分页id
        menunavid: "menu-nav-left",//菜单导航id
        topnavid: "top_nav",//顶部导航id
        pageIndexid: "page_indexid",//主页分页标签id
        footerid: "footer"//底部id
    };

    var _stringclass = {
        hmax: "g-h-max",//高度自适应
        gmax: "g-max",//整体自适应
        haveicon: "mor",//有图标class让位
        moreicon: "iconfont moreicon",//让位
        classicon: "iconfont classicon",
        topnav: "u-topmenu",//顶部导航ul
        footer: "panel-foot",//foot
        active: "activate",//选中状态
        menunav: "u-sidemenu",//菜单类
        menutitle: "u-menutitle",//菜单title
        path: "u-path",//路径导航
        navsub: "nav-sub"//子菜单类
    };

    var menuTextList = [];//easyui风格路径数据 -fwq

    $.fn.an_main = function (option) {
        // console.log("执行an_main");

        //设置默认值并用逗号隔开
        var defaults = {
            tabId: "",//分页id
            menuNavId: "",//菜单导航id
            topNavId: "",//顶部导航id
            footerId: "",//底部id
            iframeId: "stage",//iframeid
            iframeName: "stage",//iframeName

            stylePackage: "",//引入新增样式包

            unOftenMenuWidth: 200,//不常用菜单宽度

            menuIndex: 0,  //当前菜单索引 用于菜单 查询所属导航
            menuJsonUrl: "/Templet/Data/sys_menu.json", //获取菜单json文件地址
            menuStrLength: 10, //左侧菜单字符长度
            data: [],//如果在外部获取的json直接通过data属性传入
            isBaseTopMenu: true,//是否生成基础topmenu， 默认为true
            menuInTop: false,//侧边导航是否设置到顶部，默认为flase
            menuInRight: false,//二级菜单放入右侧
            menuTopWidth: 200,//顶部菜单宽度设置
            menuForTree: false,//是否以树形式出现在侧边菜单

            indexUrl: "",//默认无主页
            isHaveIndex: true,// 是否需要默认主页
            isCloseIndex: false,//主页是否可以关闭 默认不能关闭

            isPagination: false,//默认没有分页标签
            isBreadcrumb: false,//默认没有路径导航
            breadcrumbHeight: 34,//默认路径导航高度
            isAsideUser: false,//默认没有侧边用户信息

            footer: "",//默认无footer
            footerHeight: 30,//footer高度
            banner: "",//默认无banner
            bannerHeight: 0,//banner高度

            onDataLoad: function () {
            },//菜单数据加载完成
            onFullClick: function () {
            },//点击全屏事件
            onRefreshClick: function () {
            },//点击刷新事件
            onBackClick: function () {
            },//点击返回事件
            onBackComplete: function () {
            },//返回完成事件
            onForwardClick: function () {
            },//点击前进事件
            onForwardComplete: function () {
            }//前进完成事件

        };

        _options = $.extend(defaults, option);

        // 如果menuInRight == true;自动设置menuInTop为true;
        //if (_options.menuInRight) {
        //    _options.menuInTop = true;
        //}

        extendString();//合并class, id设置,属性

        //执行框架前的工作
        //创建头部banner
        createBanner();
        //创建功能组 预留
        createComponentList();
        //创建分页标签
        createPagination();

        //创建footer
        createFooter();

        // 获取center
        _mainCenter = $("#" + _options.iframeId).parent();

        //纯架子，没有一级菜单，需要计算head的高度
        //可能有不常用菜单，点击以后在 路径里显示对应项

        //获取第一个导航对应有菜单的JSON，并创建对应菜单
        getFistForNav("#" + _stringid.topnavid, function (data) {
            /*添加无级树*/
            // an_setZindex($("#" + _stringid.menunavid), 1);

            //判断隐藏 展开侧边用户
            if (!_options.isAsideUser) {
                toggleAsideUser();
            }

            //创建侧边导航
            if (!_options.pure) {
                //生成基础导航
                if (_options.isBaseTopMenu) {
                    if (_options.menuInRight && _mainCenter[0]) {
                        // 创建右侧二级导航
                        createRightMenuForParent();
                    }
                    // console.log(_menujson)
                    appendIn("#" + _stringid.topnavid, "<div class='" + _stringclass.topnav + "' ><ul class='" + _stringclass.topnav + "ul'></ul></div>");
                    createTopMenu();
                }
                if (_options.menuInTop) {
                    var side = $(".m-side");
                    var sideWidth = side.width();
                    side.empty();
                    andy.shrinkageLayout({
                        element: side,
                        max: sideWidth,
                        direction: "l",
                        min: 0
                    });
                } else {
                    if (_options.menuForTree || data.tree) {
                        createTreeMenu(data);
                    } else {
                        var menuNav = $("#" + _stringid.menunavid);
                        menuNav.removeClass();
                        createMenu(data.children);
                    }
                }
            }
            if (_options.pure) {
                _options.isBreadcrumb = true;
                if (_options.menuInRight && _mainCenter[0]) {
                    createRightMenuForParent();
                }

                //创建路径导航
                //createBreadcrumb();
                //打开路径导航
                //pathlist();

                //$(".layout-head").css("height", $("#" + _stringid.topnavid).css('height'));
                //andy.layout($("body"));
                ////创建主页
                //if (_options.isHaveIndex) {
                //    // console.log($("#"+_currentMenuId));
                //    //建立主页分页
                //    createMainPage(_options.indexUrl);
                //    //打开主页
                //    urlTabPage(_options.indexUrl);
                //    refreshCurrentNav($("#" + _currentMenuId).children());
                //}
                //return;
            }
            if (_options.menuInRight) {
                createRightMenu(data.children, data.help);
            }

            //创建路径导航
            createBreadcrumb();
            //打开路径导航
            pathlist();
            if (_options.pure) {
                $(".layout-head").css("height", $("#" + _stringid.topnavid).css('height'));
                andy.layout($("body"));
            }

            //创建主页
            if (_options.isHaveIndex) {
                // console.log($("#"+_currentMenuId));
                //建立主页分页
                createMainPage(_options.indexUrl);
                //打开主页
                urlTabPage(_options.indexUrl);
                refreshCurrentNav($("#" + _currentMenuId).children());
            }

            //是否插入主页链接到顶部导航,可以关闭主页设置为true
            if ($("#" + _stringid.topnavid + " >li").first().children("a").attr("href") == "#" && _options.isCloseIndex) {
                $("#" + _stringid.topnavid + " >li").first().children("a").attr({
                    "href": _options.indexUrl,
                    "target": _options.iframeName
                });
            }


            //全局事件侦听 banner功能组
            var side = $(".m-side");
            var sideWidth = side.width();
            $(document).on('click', function (e) {
                var element = $(e.target);

                //顶部导航&& !element.hasClass(_stringclass.active) 判断重复点击
                if (element.parent().parent().attr("class") == _stringclass.topnav + "ul") {
                    var id = element.parent().attr("id");
                    _options.menuIndex = id;
                    refreshCurrentNav(element);
                    element.parent().parent().find(">li a").each(function (index, e) {
                        $(e).attr("class", "");
                    })
                    element.toggleClass(_stringclass.active);
                    onNavButtonClick(id);
                }

                //u-menutitle 收缩
                // console.log(element.parent().attr("class"), _stringclass.menutitle)
                if (element.parent().hasClass(_stringclass.menutitle)) {
                    side.toggleClass("g-min-side");
                    andy.shrinkageLayout({
                        element: side,
                        max: sideWidth,
                        direction: "l",
                        max: sideWidth,
                        min: 45,
                        callback: function () {
                            if (side.hasClass("g-min-side")) {
                                $('.' + _stringclass.menutitle).children('i').replaceWith("<i class='iconfont pull'>&#xe627;</i>");
                            } else {
                                $('.' + _stringclass.menutitle).children('i').replaceWith("<i class='iconfont pull'>&#xe628;</i>");
                            }
                        }
                    });
                }

            })

            //数据加载完成回调
            _options.onDataLoad();

            //框架布局
            //$.fn.pagebuild();

            //检查是否需要滚动一级菜单
            showTopMenuOver();
        });
    };

    //----------------------------------------------分页操作

    //创建分页
    var createPagination = function () {
        if (_options.isPagination) {
            $("#top_navbar").after("<div><ul class='app-content nav nav-tabs  m-b-none  m-t-none hidden-xs' id='" + _stringid.tabid + "'><li></li></ul></div>")
        }
    };

    //创建分页主页
    var createMainPage = function (url) {
        if (_options.isPagination) {
            //在json里面找到home 属性，如果属性为1则可以关闭,如果为0则不能关闭
            var isHave = checkIsHavePage(url, true);
            var isIndexPage = $("#" + _stringid.pageIndexid);
            if (_options.isCloseIndex && !isHave && isIndexPage.length == 0) {
                prependIn("#" + _stringid.tabid, "<li class='active'><a id = '" + _stringid.pageIndexid + "' href=" + url + " target = 'stage'><i class='fa fa-home'></i></a><i class='text-danger fa fa-times'></li>")
            } else if (!_options.isCloseIndex && isIndexPage.length == 0) {
                prependIn("#" + _stringid.tabid, "<li class='active'><a id = '" + _stringid.pageIndexid + "' href=" + url + " target = 'stage'><i class='fa fa-home'></i></a></li>")
            } else if (isIndexPage.length > 0) {
                isIndexPage.attr("href", url);
                if (!isIndexPage.parent().hasClass("active")) {
                    isIndexPage.parent().toggleClass("active");
                }
            }
        }
    }

    //分页跳转页面
    var urlTabPage = function (url) {
        // console.log(url);
        $("#" + _options.iframeId).attr("src", url);
    }

    //分页状态
    var setPageState = function (element) {
        if (_options.isPagination) {
            $("#" + _stringid.tabid + " [class = 'active']").toggleClass('active');
            if (element != null) {
                $(element).toggleClass("active");
            }
        }
    }

    //检测是否有分页了 传入分页地址判断 isSetState：是否改变分页状态
    var checkIsHavePage = function (url, isSetState) {
        var isHave = false;
        //如果有和增加分页链接相同的 则不添加
        $("#" + _stringid.tabid + " >li").each(function (index, e) {
            // console.log($(e).children("a").attr("href"),url)
            if ($(e).children("a").attr("href") == url) {
                isHave = true;
                if (isSetState) {
                    setPageState(e);
                }
            }
        })
        return isHave
    }

    //检测分页是否铺满 需要折叠
    var checkIsTabDropdown = function () {
        var bodywidth = getBodyWidth();
        var asideWidth = getAsideMenuWidth();
        var tabWidth = bodywidth - asideWidth; //tabid可以占用的宽度
        var cw = 0//当前tabid 所有分页占用宽度
        $("#" + _stringid.tabid + " li").each(function (i, e) {
            cw += $(e).outerWidth();
            if (cw > tabWidth) {
                // addList(e);
                //超出向左1移除
                $("#" + _stringid.tabid + " >li").get(1).remove();
            }
        })

        function addList(element) {
            $("#" + _stringid.tabid + " li").hasClass("dropdown") || buildDropdown(element);

        }

        // 创建下拉
        function buildDropdown(element) {
            $(element).prev()
            // console.log("build")
        }
    }

    //增加分页 传入对象
    var addTabPage = function (element) {
        var isHave = checkIsHavePage(element.attr("href"), true);
        // console.log(element.attr("href"))
        if (!isHave && element.attr("href") != "#" && element.attr("target") != "blank") {
            $("#" + _stringid.tabid + " [class = 'active']").toggleClass('active');
            appendIn("#" + _stringid.tabid, "<li class='active'><a href='" +
                element.attr("href") + "' target = '" + _options.iframeName + "' >" + element.text() +
                "</a><i class='text-danger fa fa-times'></i></li>");
            checkIsTabDropdown();
        }
    }

    //删除分页
    var deletTabPage = function (element) {
        if (element != null) {
            //当删除的是当前标签页
            if (element.attr("class") == "active") {
                if (element.next().children("a").attr("href") != null) {
                    // console.log(element.next().children("a").attr("href"), "next")
                    setPageState(element.next());
                    urlTabPage(element.next().children("a").attr("href"));
                } else if (element.prev().children("a").attr("href") != null) {
                    // console.log(element.prev().children("a").attr("href"), "prev")
                    setPageState(element.prev());
                    urlTabPage(element.prev().children("a").attr("href"));
                } else {
                    urlTabPage("");
                }
            }
            element.remove();
        }
    }

    //----------------------------------------------分页操作 结束
    //
    // ----------------------------------------------路径导航操作

    //创建路径导航
    var createBreadcrumb = function () {
        if (_options.isBreadcrumb) {
            $("#" + _stringid.topnavid).append("<ul class='" + _stringclass.path + " '></ul>")
        } else {
            var topnav = $("#" + _stringid.topnavid);
            topnav.css("height", parseInt(topnav.css('height')) - _options.breadcrumbHeight + 'px');
        }

    };

    //排列路径
    var pathlist = function (element) {
        if (_options.isBreadcrumb) {
            //清除老路径
            if (element != null) {
                $("." + _stringclass.path).empty();

                creatList(element);
                //首
                prependIn("." + _stringclass.path, '<li class="activate home"><a href="' + _options.indexUrl + '" target="stage"><i class="iconfont">&#xe609;</i>首页</a><i class="iconfont"></i></li>');
            }
            //之后创建顶级路径
            //if(_options.menuInTop == false ){
            // 如果是顶部二三级菜单则不需要单独创建顶级路径
            //var e = $("#" + _currentMenuId);
            //creatPath(e);
            //}
        }
    }

    var creatList = function (element) {
        if (element.parent().is("li")) {
            creatPath(element.parent(), _stringclass.active);
            creatList(element.parent());
        } else if (element.parent().parent().is("li")) {
            creatPath(element.parent().parent());
            creatList(element.parent().parent());
        } else if (element.parent().parent().parent().is("li")) {
            creatPath(element.parent().parent().parent());
            creatList(element.parent().parent().parent());
        }
    };

    // 点击路径导航 跳转到对应页面
    var clickPath = function (e) {
        var name = e.text();
        var element = getE(name);

        function getE(name) {
            $('#' + _stringid.menunavid + ' a').each(function (i, e) {
                if (name == $(this).text() && $(this).attr("href") != "#") {
                    pathlist($(this));
                }
            })
        }
    }

    var creatPath = function (e, sclass) {
        var _url = e.children("a").attr("href");

        var _text = e.children("a").clone();
        var _name = _text.children("i").text("").html();//.html();
        _name = _text.text();
        var _target = e.children("a").attr("target");
        addPath(_name, _url, _target, sclass);
    }

    var addPath = function (name, url, target, sclass) {
        var icon = "<i class='iconfont'>&#xe600;</i>";
        if (sclass) {
            icon = "";
        }
        if (url == "#") {
            prependIn("." + _stringclass.path, "<li class = '" + sclass + "'>" + name + icon + "</li>");
        } else if (target != "blank") {
            prependIn("." + _stringclass.path, "<li class = '" + sclass + "'><a href='" + url + "' target = '" + _options.iframeName + "'></a>" + name + icon + "</li>");
        }

    }

    //----------------------------------------------路径导航操作 结束

    //----------------------------------------------及时替换操作

    //刷新当前操作
    var refreshCurrentNav = function (element) {
        if ($(".u-menutitle")) {
            var _text = element.clone();
            var _name = _text.children("i").text("");
            _name = _text.text();
            $(".u-menutitle").children().children("span").text(_name);
            // console.log(_text.html())
            // $(".u-menutitle").children("h4").html(_text.html());
        }
    }

    //隐藏展开 aside-user 侧边用户信息
    var toggleAsideUser = function () {
        $("#aside-user").slideToggle("fast");
    }

    //----------------------------------------------及时替换操作 结束

    //插入标签结尾
    var appendIn = function (element, str) {
        $(element).append(str);
    }

    //插入标签开头
    var prependIn = function (element, str) {
        $(element).prepend(str);
    }

    //获取第一个导航对应有菜单的JSON
    var getFistForNav = function (element, callback) {
        //首次获取加载_menuJson
        if (_options.data.length > 0) {
            //遍历顶部菜单id 查找JSON数据
            var data = _options.data;
            for (var i = 0; i < data.length; i++) {
                var menuId = data[i].id;

                var value = getTargetJson(menuId, data);
                _currentMenuJson = value;
                _menujson = data;
                if (value) {
                    callback(value);
                    return false;
                }
            }
        } else if (typeof _options.menuJsonUrl == "string") {
            andy.loaddata(_options.menuJsonUrl, function (data) {
                if (typeof data == "string") {
                    data = eval("(" + data + ")");
                }
                //遍历顶部菜单id 查找JSON数据
                for (var i = 0; i < data.length; i++) {
                    var menuId = data[i].id;

                    var value = getTargetJson(menuId, data);
                    _currentMenuJson = value;
                    _menujson = data;
                    if (value) {
                        callback(value);
                        return false;
                    }
                }
            })
        } else if (typeof _options.menuJsonUrl == "object") {
            //多地址导航
            var index = 0;
            var length = _options.menuJsonUrl.length;
            var menuurl = _options.menuJsonUrl[index];
            var menudata = [];
            goload();
            function goload() {

                if (index == length - 1) {
                    andy.loaddata(menuurl, function (data) {
                        if (typeof data == "string") {
                            data = eval("(" + data + ")");
                        }
                        for (var i = 0; i < data.length; i++) {
                            menudata.push(data[i]);
                        }

                        //遍历顶部菜单id 查找JSON数据
                        for (var i = 0; i < menudata.length; i++) {
                            // console.log(i)
                            var menuId = menudata[i].id;
                            var value = getTargetJson(menuId, menudata);
                            _currentMenuJson = value;
                            _menujson = menudata;
                            if (value) {
                                callback(value);
                                return false;
                            }
                        }
                    })
                } else if (index < length) {
                    try {

                        andy.loaddata(menuurl, function (data) {
                            if (typeof data == "string") {
                                data = eval("(" + data + ")");
                            }
                            for (var i = 0; i < data.length; i++) {
                                menudata.push(data[i]);
                            }

                            index += 1;
                            menuurl = _options.menuJsonUrl[index];
                            goload()
                        })
                    } catch (e) {
                    }
                }
            }
        }

    };

    //获取目标json相同字段数据 字段名 数据 字段类型
    //getTargetJson("xxx", {{name:"xxx", other:"bbb"}, {name:"xx1", other:"ddd"}}, name)
    //不清楚字段类型可以不写 getTargetJson("xxx", {name:"xxx", other:"bbb"})
    var getTargetJson = function (str, data, typename) {
        if (typename) {
            //传入字段类型
            for (var i in data) {
                if (str == data[i][typename]) {
                    _currentMenuId = str;
                    return data[i];
                }

            }
        } else {
            //不清楚字段类型
            for (var i in data) {
                for (var j in data[i]) {
                    if (str == data[i][j]) {
                        _currentMenuId = str;
                        return data[i];
                    }
                }
            }
        }
        return null;
    }

    //清空对象所有子元素
    var clearTarget = function (element) {
        $(element).empty()
    }

    //导航点击
    var onNavButtonClick = function (value) {
        var leftBox = $('#leftnavbox'), hasChild = true;

        var menuData = getTargetJson(value, _menujson, "id");
        _currentMenuId = value;
        //当点击导航有url就默认跳装url
        for (var i in _menujson) {
            var url = _menujson[i]["url"];
            if (_menujson[i]["id"] == value && url && url != "" && url != "#") {
                _options.indexUrl = url;
                hasChild = _menujson[i].children && _menujson[i].children.length > 0 ? true : false;
            }
        }
        //启动左侧菜单    没有子菜单&&没有打开
        if (!_options.menuInTop) {
            //没有子菜单&&打开=收回
            if (leftBox.css('display') === "block" && !hasChild) {
                andy.floatbox(leftBox);
            } else if (hasChild && (leftBox.css('display') === "none" || leftBox.attr("currentSys") === value)) {
                andy.floatbox(leftBox);
            }
        }
        leftBox.attr({"currentSys": value});

        if (menuData) {

            // console.log(_stringid.menunavid);
            if (_options.menuInTop) {
                // createTopMenu();
                // $("#"+_currentMenuId).children("ul").remove();
                // _options.menuIndex = 0;
                // createTopMenuList(_currentMenuId, menuData.children);
                if (_options.menuInRight) {
                    _options.menuIndex = 0;
                    if (menuData.children) {
                        var rightMenu = "#" + _stringid.menurightid;
                        $(rightMenu).show()
                        removeRightUl();
                        createRightMenu(menuData.children, menuData.help);
                    } else {
                        var rightMenu = "#" + _stringid.menurightid;
                        $(rightMenu).hide()
                    }
                }
            } else {
                //移除对象时 需要移除nav a的事件
                removeMenuListener();
                clearTarget("#" + _stringid.menunavid);
                _options.menuIndex = 0;
                if (_options.menuForTree || menuData.tree) {
                    createTreeMenu(menuData);
                } else {
                    var menuNav = $("#" + _stringid.menunavid);
                    menuNav.removeClass();
                    createMenu(menuData.children);
                }
                //左侧情况也要显示不常用菜单
                if (_options.menuInRight && menuData.children) {
                    var rightMenu = "#" + _stringid.menurightid;
                    $(rightMenu).show();
                    removeRightUl();
                    createRightMenu(menuData.children, menuData.help);
                }
                // createMenu(menuData.children);
            }

            //对应主页
            if (_options.isHaveIndex) {
                setPageState();//设置分页状态
                //建立主页分页
                createMainPage(_options.indexUrl);
                //打开路径导航
                pathlist();//刷新路径导航
                //打开主页
                urlTabPage(_options.indexUrl);
            }
        }

    }

    //移除所有菜单绑定事件
    var removeMenuListener = function () {
        $(document).off('click', '#' + _stringid.menunavid + ' a');
        $(document).off('click', '[data-toggle^="class"]');
        $(document).off('click', '#' + _stringid.tabid + ' a');
        $(document).off("click", "#" + _stringid.tabid + " i[class ^= 'text-danger']");
        $(document).off("mouseenter", "#" + _stringid.menunavid + ' a');
        $(document).off("mouseleave", '.' + _stringclass.active);
    };

    /*递归实现获取无级树数据并生成DOM结构,unOften生成不常用菜单*/
    forTree = function (jsonData, unOften) {
        str += "<ul>";
        for (var i = 0; i < jsonData.length; i++) {
            var urlstr = "";
            try {
                var tar = jsonData[i];
                var isHaveChildren = false;
                var target = " target = '" + _options.iframeName + "'";
                var liclass = "ic mor";
                var beforicon = "";
                var arrow = "";//添加有子菜单的箭头样式
                //常用菜单跳过
                if ((!unOften && (tar.often === true || tar.often === undefined)) || (unOften && tar.often === false)) {
                    if (tar["children"] != null) {
                        isHaveChildren = true;
                        arrow = "<i class='" + _stringclass.moreicon + "'>&#xe607;</i>";
                    } else {
                        liclass = "ic";
                    }

                    if (tar['icons'] != null && tar['icons'] != "") {
                        beforicon = "<i class='" + _stringclass.classicon + "'>" + tar['icons'] + "</i>";
                    } else {
                        liclass = "";
                        if (liclass.indexOf("mor")) {
                            liclass = "mor";
                        }
                    }

                    //设置主页链接 如果默认没设置主页 就把第一个有链接的 地址赋予主页
                    if (_options.indexUrl == "" && tar["url"] != "#" && !tar["target"] && _options.isHaveIndex) {
                        _options.indexUrl = tar["url"];
                    }
                    if (_options.menuIndex != 0 && _options.isHaveIndex && tar["url"] != "#" && !tar["target"]) {
                        _options.indexUrl = tar["url"];
                        _options.menuIndex = 0;
                    }

                    //判断外连接
                    if (tar["target"]) {
                        target = " target = '" + tar["target"] + "'";
                    }

                    //处理字符过长
                    var a_str = tar['text'];
                    var strlength = a_str.length;
                    var strtitle = " title = '" + a_str + "'";
                    if (strlength > _options.menuStrLength) {
                        a_str = a_str.substring(0, _options.menuStrLength) + '..';
                        urlstr += "<li class = '" + liclass + "'><a href= " + tar['url'] + target + strtitle + ">" + beforicon +
                            a_str + arrow + "</a>";
                    } else {
                        urlstr += "<li class = '" + liclass + "'><a href= " + tar['url'] + target + ">" + beforicon +
                            a_str + arrow + "</a>";
                    }

                    str += urlstr;

                    if (isHaveChildren) {
                        forTree(tar["children"]);
                    }
                }

                str += "</li>";
            } catch (e) {
            }
        }
        str += "</ul>";
        return str;
    }

    forTopTree = function (jsonData) {
        str += "<ul>";
        for (var i = 0; i < jsonData.length; i++) {
            var urlstr = "";
            try {
                var tar = jsonData[i];
                var isHaveChildren = false;
                var target = " target = '" + _options.iframeName + "'";
                var liclass = "ic mor";
                var beforicon = "";
                var arrow = "";//添加有子菜单的箭头样式
                if (tar["children"] != null) {
                    isHaveChildren = true;
                    arrow = "<i class='" + _stringclass.moreicon + "'>&#xe613;</i>";
                } else {
                    liclass = "ic";
                }

                if (tar['icons'] != null && tar['icons'] != "") {
                    beforicon = "<i class='" + _stringclass.classicon + "'>" + tar['icons'] + "</i>";
                } else {
                    liclass = "";
                    if (liclass.indexOf("mor")) {
                        liclass = "mor";
                    }
                }
                if (tar["url"] === "#" || tar["url"] === undefined || tar["url"] === "") {
                    tar["url"] = "javascript:void(0)";
                }

                //设置主页链接 如果默认没设置主页 就把第一个有链接的 地址赋予主页
                if (_options.indexUrl == "" && tar["url"] != "#" && !tar["target"] && _options.isHaveIndex) {
                    _options.indexUrl = tar["url"];
                }
                if (_options.menuIndex != 0 && _options.isHaveIndex && tar["url"] != "#" && !tar["target"]) {
                    _options.indexUrl = tar["url"];
                    _options.menuIndex = 0;
                }

                //判断外连接
                if (tar["target"]) {
                    target = " target = '" + tar["target"] + "'";
                }

                //处理字符过长
                var a_str = tar['text'];
                var strlength = a_str.length;
                var strtitle = " title = '" + a_str + "'";
                if (strlength > _options.menuStrLength) {
                    a_str = a_str.substring(0, _options.menuStrLength) + '..';
                    urlstr += "<li class = '" + liclass + "'><a href= " + tar['url'] + target + strtitle + ">" + beforicon +
                        a_str + arrow + "</a>";
                } else {
                    urlstr += "<li class = '" + liclass + "'><a href= " + tar['url'] + target + ">" + beforicon +
                        a_str + arrow + "</a>";
                }

                str += urlstr;

                if (isHaveChildren) {
                    forTree(tar["children"]);
                }

                str += "</li>";
            } catch (e) {
            }
        }
        str += "</ul>";
        if (jsonData.length == 0) {
            str = "";
        }
        return str;
    }

    forRightTree = function (jsonData, help) {
        str += "<ul class='u-down-menu' style = 'width:" + _options.unOftenMenuWidth + "px' combo = 'nav1'>";
        var end;
        for (var i = 0; i < jsonData.length; i++) {
            var urlstr = "";
            try {
                var tar = jsonData[i];
                var isHaveChildren = false;
                var target = " target = '" + _options.iframeName + "'";
                var liclass = "ic mor";
                var beforicon = "";
                var arrow = "";
                //添加有子菜单的箭头样式
                if (tar["children"] != null) {
                    isHaveChildren = true;
                    arrow = "<i class='" + _stringclass.moreicon + "'>&#xe613;</i>";
                } else {
                    liclass = "ic";
                }

                if (tar['icons'] != null && tar['icons'] != "") {
                    beforicon = "<i class='" + _stringclass.classicon + "'>" + tar['icons'] + "</i>";
                } else {
                    liclass = "";
                    if (liclass.indexOf("mor")) {
                        liclass = "mor";
                    }
                }


                //设置主页链接 如果默认没设置主页 就把第一个有链接的 地址赋予主页
                if (_options.indexUrl == "" && tar["url"] != "#" && !tar["target"] && _options.isHaveIndex) {
                    _options.indexUrl = tar["url"];
                }
                if (_options.menuIndex != 0 && _options.isHaveIndex && tar["url"] != "#" && !tar["target"]) {
                    _options.indexUrl = tar["url"];
                    _options.menuIndex = 0;
                }

                //判断外连接
                if (tar["target"]) {
                    target = " target = '" + tar["target"] + "'";
                }

                //处理字符过长
                var a_str = tar['text'];
                var strlength = a_str.length;
                var strtitle = " title = '" + a_str + "'";
                if (strlength > _options.menuStrLength) {
                    a_str = a_str.substring(0, _options.menuStrLength) + '..';
                    urlstr += "<li class = '" + liclass + "'><a href= " + tar['url'] + target + strtitle + ">" + beforicon +
                        a_str + arrow + "</a>";
                } else {
                    urlstr += "<li class = '" + liclass + "'><a href= " + tar['url'] + target + ">" + beforicon +
                        a_str + arrow + "</a>";
                }

                if (tar.often === false) {
                    str += urlstr;
                }

                if (isHaveChildren) {
                    forTree(tar["children"], true);
                }

                str += "</li>";
            } catch (e) {
            }
        }

        //end= '<li class="mor"> <a href="' + help||'javascript:void(0)' + '" target="stage"><i class="iconfont">&#xe621;</i>帮助？</a></li></ul>';
        end = '<li class="mor"> <a href="' + help + '" target="stage"><i class="iconfont">&#xe621;</i>帮助？</a></li></ul>';
        str += end;
        if (jsonData.length == 0) {
            str = "";
        }
        return str;
    };

    //无极菜单操作
    var menuTree = function () {
        //给有子对象的元素加class
        var menunav = "#" + _stringid.menunavid;
        $(menunav + " ul").first().attr("class", _stringclass.menunav);
        // $(menunav + " li").first().attr("class", "active");
        $(menunav + " ul").first().children().children('ul').attr("class", _stringclass.navsub);
        $("[class = '" + _stringclass.navsub + "'] ul").attr("class", "bg");

        //顶部导航
        var menutopnav = "#" + _stringid.topnavid;
        // $(menutopnav + " ul").first().attr("class", _stringclass.menunav);
        $(menutopnav + " ul").first().children().children('ul').css("width", "200px");
        // $("[class = '" + _stringclass.navsub + "'] ul").attr("class", "bg");

        //主框架操作响应
        // $(document).on(
        //     'click',
        //     '[data-toggle^="class"]',
        //     function (e) {
        //         e && e.preventDefault();
        //         var $this = $(e.target), $class, $target, $tmp, $classes, $targets;
        //         !$this.data('toggle') && ($this = $this.closest('[data-toggle^="class"]'));
        //         $class = $this.data()['toggle'];
        //         $target = $this.data('target') || $this.attr('href');
        //         $class && ($tmp = $class.split(':')[1]) && ($classes = $tmp.split(','));
        //         $target && ($targets = $target.split(','));
        //         $classes && $classes.length && $.each($targets,

        //             function (index, value) {
        //                 if ($classes[index].indexOf('*') !== -1) {
        //                     var patt = new RegExp('\\s' + $classes[index].replace(/\*/g, '[A-Za-z0-9-_]+').split(' ').join('\\s|\\s') + '\\s', 'g');
        //                     $($this).each(function (i, it) {
        //                         var cn = ' ' + it.className + ' ';
        //                         while (patt.test(cn)) {
        //                             cn = cn.replace(patt, ' ');
        //                         }
        //                         it.className = $.trim(cn);
        //                     });
        //                 }
        //                 ($targets[index] != '#') && $($targets[index]).toggleClass($classes[index]) || $this.toggleClass($classes[index]);
        //             });
        //         $this.toggleClass('active');
        //     }
        // );

        // collapse nav
        var st = "";
        //鼠标进入侧边菜单执行 除去一级菜单
        $(document).on('mouseenter', '#' + _stringid.menunavid + ' a', function (e) {
            var $this = $(e.target);
            $this.is('a') || ($this = $this.closest('a'));//如果没有a 就返回a的第一个父级
            var $li = $this.parent().siblings("li");
            if ($li.parent().attr("class") != _stringclass.menunav) {
                // $li.find("ul").slideUp(240);
                $li.find("ul:visible").animate({width: 'hide'}, "fast");
            }
            if ($this.parent().parent().attr("class") != _stringclass.menunav) {
                // $this.next().slideDown(0);
                $this.next().animate({width: 'show'}, "fast");
            }
        });

        //鼠标离开侧边菜单执行 除去一级菜单
        $(document).on('mouseleave', '.' + _stringclass.active, function (e) {
            var $this = $(e.target);
            $this.is('a') || ($this = $this.closest('a'));//如果没有a 就返回a的第一个父级

            var $ul = $("." + _stringclass.menunav).find('ul:visible');
            // console.log($ul.attr("class"))
            $ul.each(function (index, e) {
                if ($(e).attr("class") != _stringclass.navsub) {
                    // $(e).slideUp(240);
                    $(e).animate({width: 'hide'}, "fast");
                }
            })
        });

        //顶部导航鼠标进入
        $(document).on('mouseenter', '#' + _stringid.topnavid + ' a', function (e) {
            var $this = $(e.target);
            $this.is('a') || ($this = $this.closest('a'));//如果没有a 就返回a的第一个父级
            var $li = $this.parent().siblings("li");
            $li.find("ul:visible").animate({width: 'hide'}, "fast");
            if ($this.parent().parent().attr("id") != _stringid.topnavid) {
                $this.next().animate({width: 'show'}, "fast");
            }
        });
        //鼠标离开顶部菜单执行 除去一级菜单
        $(document).on('mouseleave', '.u-topmenuul', function (e) {
            var $ul = $("." + _stringclass.topnav + "ul").find('ul:visible');
            $ul.each(function (index, e) {
                $(e).animate({width: 'hide'}, "fast");
            })
        });

        // 顶部菜单二三级点击
        $(document).unbind("click", '#' + _stringid.topnavid + ' a,#menu-right a');
        $(document).on('click', '#' + _stringid.topnavid + ' a,#menu-right a', function (e) {
            var $this = $(e.target);
            $this.is('a') || ($this = $this.closest('a'));//如果没有a 就返回a的第一个父级
            //清除老路径
            // $("."+_stringclass.path).empty();
            // 切换顶部状态和数据
            if ($this.parents().hasClass(_stringclass.topnav + "ul")) {
                var id = "";
                var li = "";
                $this.parents().each(function (i, e) {
                    if ($(e).parent().hasClass(_stringclass.topnav + "ul")) {
                        id = $(e).attr("id");
                        li = $(e);
                    }
                });
                _options.menuIndex = id;
                li.siblings().each(function (index, e) {
                    $(e).find(">a").attr("class", "");
                });
                li.find(">a").toggleClass(_stringclass.active);
                _currentMenuId = id;
                // $("."+_stringclass.path).empty();
                pathlist($this);
            }
            ;

        });

        // 侧边菜单二三级点击
        $(document).on('click', '#' + _stringid.menunavid + ' a', function (e) {
            var $this = $(e.target), $active;
            $this.is('a') || ($this = $this.closest('a'));//如果没有a 就返回a的第一个父级
            $active = $this.parent().siblings("." + _stringclass.active);//返回与.active同级的所有元素
            $active && $active.toggleClass(_stringclass.active).find('> ul:visible').slideUp(200);
            ($this.parent().hasClass(_stringclass.active) && $this.next().slideUp(200)) || $this.next().slideDown(200);

            $this.parent().parent().find("li").each(function (index, e) {
                if ($(e).parent().parent().attr("id") != _stringid.menunavid) {
                    $(e).attr("class", "");
                }
            })
            $this.parent().toggleClass(_stringclass.active);
            // $this.next().is('ul') && e.preventDefault();
            // 如果有下一层
            if ($this.next().is('ul')) {
                if ($this.attr("href") == "#") {
                    e.preventDefault();
                }

            }

            clearTimeout(st);
            //获取所有ul数量 计算等待执行时间
            var thisLength = $("nav ul").length;
            // setTimeout(function() {
            // }, 10);
            st = setTimeout(function () {
                $(document).trigger('updateNav');
                //点击增加分页 传入a标签
                addTabPage($this);
                pathlist($this);
                // checkIsMouseWheel(menunav);
            }, thisLength * 40)
        });

        //所有分页选择事件
        $(document).on('click', '#' + _stringid.tabid + ' a', function (e) {
            setPageState($(e.target).parent());
        });
        //所有分页关闭事件
        // text-danger fa fa-times
        $(document).on("click", "#" + _stringid.tabid + " i[class ^= 'text-danger']", function (e) {
            deletTabPage($(e.target).parent());
        });

        //导航路径点击
        $(document).on('click', '#path a', function (e) {
            clickPath($(e.target));
        });
    }

    //侦听元素鼠标滚动
    var mousewheelFun = function (element, cheight) {
        var top_val = 0;
        var t = $(element + "> ul");

        $(element).mousewheel(function (event, delta, deltaX, deltaY) {

            if (t.is(":animated")) {
                t.stop();
            }
            top_val += deltaY * 80;
            if (top_val > 0) {
                top_val = 0;

            } else if (top_val < -cheight) {
                top_val = -cheight;
            }
            t.animate({top: top_val}, 300, "linear")
            // t.css("top", top_val);
        });
    };

    //取消侦听元素鼠标滚动
    var unmousewheelFun = function (element) {
        $(element).unmousewheel();
    };

    //检测是否发生鼠标滚动
    var checkIsMouseWheel = function (element) {
        var menuHeight = getAsideMenuHeight();
        var bodyHeight = getBodyHeight();
        //计算剩余高度
        // console.log("menuHeight", menuHeight);
        var cheight = menuHeight - (bodyHeight - _asideUserHeight - _topNavHeight - _bannerHeight);
        // console.log("b-a-t-b", bodyHeight , _asideUserHeight , _topNavHeight ,_bannerHeight);
        // console.log("cheight", cheight);
        if (cheight > 0) {
            // console.log("可以滚动",menuHeight, cheight);
            mousewheelFun(element, cheight);
            menuHeight = 0;
        } else {
            // console.log("不可以滚动",menuHeight)
            $(element + "> ul").css("top", 0);
            unmousewheelFun(element);
        }
    }

    //----------------------------------------------创建基本对象

    //创建footer
    var createFooter = function () {
        // console.log($("#" + _stringid.footerid).length, "aaaaa")
        var footer = $("." + _stringclass.footer);
        if (footer.length > 0) {
            footer.css("height", _options.footerHeight);
            appendIn("." + _stringclass.footer, _options.footer);
        }
    }

    //创建功能组
    var createComponentList = function () {

    }

    //创建banner
    var createBanner = function () {
        if (_options.banner != "") {
            $(".g-mian").attr("style", "left:200px; top:" + _options.bannerHeight + "px; bottom:0; _top:0; _left:0; _margin-left:200px; _height:auto");
            $(".g-side").attr("style", "width:200px; top:" + _options.bannerHeight + "px; bottom:0;");
            $(".g-head").css("height", _options.bannerHeight + "px");
            $(".g-head").append(_options.banner);
        } else {
            // $(".g-mian").attr("style", "left:200px; top:0px; bottom:0; _top:0; _left:0; _margin-left:200px; _height:auto");
            // $(".g-side").attr("style", "width:200px; top:0px; bottom:0;");
        }
    }

    // 创建右侧菜单父级对象
    var createRightMenuForParent = function (parent) {
        var menuRightDiv = "<div class='u-btn-eject' id = 'menu-right' an-combo> " +
            "<span class = 'u-but-group u-button-icon'><i class='iconfont'>&#xe634;</i><button type='button' class='u-but-button' combo = 'nav1'>" +
            "</button></span></div>";

        var menuRightScript = "<script> andy.perform() </script>";
        if (!parent) {
            $(".u-navmore").append(menuRightDiv);
            $("body").after(menuRightScript);
        }
    };

    // 创建右侧菜单
    var createRightMenu = function (data, help) {
        var menuRight = "#" + _stringid.menurightid;
        // var menuNav = "#" + _stringid.menunavid;
        var thisStr = "";
        if (data) {
            thisStr = forRightTree(data, help);
        }

        str = "";
        $(menuRight).append(thisStr);
        // console.log($("#menu-right").children("ul")[0])
        var ul = $("#menu-right").children("ul");
        $("#menu-right").an_combo("setShowTarget", ul);

        if (_options.pure) {
            $(document).on('click', "#menu-right a", function (e) {
                var $this = $(e.target);
                $this.is('a') || ($this = $this.closest('a'));//如果没有a 就返回a的第一个父级
                //清除老路径
                // $("."+_stringclass.path).empty();
                pathlist($this);
            });
        }
    };

    // 移除右键菜单 ul
    var removeRightUl = function () {
        var menuRight = "#" + _stringid.menurightid;
        $(menuRight).find(">ul").remove();
    }

    //创建顶部菜单
    var createTopMenu = function () {
        for (var i = 0; i < _menujson.length; i++) {
            var data = _menujson[i];
            var c = "";
            var str = "";
            if (_options.menuInTop) {
                //创建顶部下拉顶部导航
                var arrow = '';
                var activeclass = '';
                var str = '';
                var liclass = "ic mor";
                var beforicon = "";
                //if (data.children != null && _options.menuInRight == false) {
                if (data.children != null) {
                    arrow = "<i class='" + _stringclass.moreicon + "'>&#xe613;</i>";
                } else {
                    liclass = "ic";
                }

                if (data.icons != null && data.icons != "") {
                    beforicon = "<i class='" + _stringclass.classicon + "'>" + data.icons + "</i>";
                } else {
                    liclass = "";
                    if (liclass.indexOf("mor")) {
                        liclass = "mor";
                    }
                }

                if (i == 0 && _options.isHaveIndex) {
                    //加上选中状态
                    activeclass = _stringclass.active;
                }
                str = "<li class = '" + liclass + "' id = '" + data.id + "'><a href = '" + data.url + "' target='stage' class = '" + activeclass + "'>" + beforicon + data.text + arrow + "</a></li>";
            } else {
                //创建联系侧边菜单顶部导航
                var liclass = '';
                var aclass = '';
                var activeclass = '';
                var str = '';

                if (data.icons) {
                    liclass = 'ic';
                    aclass = "<i class = '" + _stringclass.classicon + "'>" + data.icons + "</i>";
                }
                if (i == 0 && _options.isHaveIndex) {
                    //加上选中状态
                    activeclass = _stringclass.active;
                }
                str = "<li class = '" + liclass + "' id = '" + data.id + "'><a href = 'javascript:void(0)' class = '" + activeclass + "'>" + data.text + aclass + "</a></li>";
            }
            $("." + _stringclass.topnav + "ul").append(str);

            //if (_options.menuInTop && _options.menuInRight == false) {
            if (_options.menuInTop) {
                // 生成顶部导航子菜单
                createTopMenuList(data.id, data.children);
            }
        }
    }

    //创建顶部菜单
    var createTopMenuList = function (id, data) {
        var menuNav = "#" + id;
        var thisStr = "";

        if (data) {
            thisStr = forTopTree(data);
        }

        str = "";
        $(menuNav).append(thisStr);
        menuTree();
        //收起所有菜单
        $('#' + _stringid.topnavid + ' a').each(function (i, e) {
            var $this = $(e), $active;
            // console.log($this)
            $this.parent().find("> ul").slideUp(0);
        })
    }

    //树数据生成
    var buildTreeData = function (data) {
        for (var i = 0; i < data.length; i++) {
            var cell = data[i];
            cell.name = cell.text;
            if (cell.target) {
                cell.target = cell.target;
            } else {
                cell.target = _options.iframeName;
            }
            if (cell.children) {
                buildTreeData(cell.children);
            }
        }
    };

    //创建树形菜单
    var createTreeMenu = function (data) {
        //树设置
        var setting = {
            callback: {
                onClick: function (event, treeId, treeNode) {
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            view: {
                fontCss: {color: "#ffffff"}
            }
        };

        var treeId = "tree_" + data.id;
        var menuNav = $("#" + _stringid.menunavid);
        menuNav.addClass(_stringclass.hmax);
        menuNav.append("<ul id='" + treeId + "' class='ztree'></ul>");
        if (data.children && data.children != []) {
            buildTreeData(data.children);
            $.fn.zTree.init($("#" + treeId), setting, data.children);
        }
        andy.layout(menuNav);
        andy.a();
    };

    //创建菜单
    var createMenu = function (data) {
        var menuNav = "#" + _stringid.menunavid;
        var thisStr = "";
        if (data) {
            thisStr = forTopTree(data);
        }
        ;
        str = "";
        $(menuNav).html(thisStr);
        menuTree();

        //收起所有菜单
        $('#' + _stringid.menunavid + ' a').each(function (i, e) {
            var $this = $(e), $active;
            $this.parent().find("> ul").slideUp(0);
        })

        //让菜单浮动起来
        // $("." + _stringclass.navsub + " li").css({"position":"relative", "border":#ff0004});
        // $("." + _stringclass.navsub + " li ul").css({"position":"absolute", "left":"200px", "width":"200px","top":"0px","z-index":"99990"});
        //获取菜单div高度
        _asideUserHeight = $("#aside-user").outerHeight();
        _topNavHeight = $("#bs-example-navbar-collapse-1").outerHeight();
        _bannerHeight = $("#banner").outerHeight();

        setTimeout(function () {
            //延迟计算鼠标滚动判断
            // checkIsMouseWheel(menuNav);
        }, 100)

    }

    //----------------------------------------------创建基本对象 结束

    //---------------------------------------------- 基本方法

    var extendString = function () {
        if (_options.tabId != "") {
            //合并标签页
            _stringid.tabid = _options.tabId;
        } else if (_options.menuNavId != "") {
            //合并菜单id
            _stringid.menunavid = _options.menuNavId;
        } else if (_options.topNavId != "") {
            //合并顶部导航id
            _stringid.topnavid = _options.topNavId;
        } else if (_options.footerId != "") {
            //合并底部id
            _stringid.footerid = _options.footerId;
        }

        if ($(".g-head")) {
            var h = parseInt($(".g-head").css("height"));
            if (h > 0) {
                _options.bannerHeight = h;
            }
        }
    }

    window.onresize = function () {
        // $.fn.pagebuild();
        // checkIsMouseWheel("#"+_stringid.menunavid);
        // TuneIframeHeight();
    }

    //获取侧边菜单高度
    var getAsideMenuHeight = function () {
        $("#" + _stringid.menunavid + " >ul").resize();
        return $("#" + _stringid.menunavid + " >ul").outerHeight();
    }

    //获取侧边菜单宽度
    var getAsideMenuWidth = function () {
        $("#" + _stringid.menunavid).resize();
        return $("#" + _stringid.menunavid).outerWidth();
    }

    //获取当前内容高度
    var getBodyHeight = function () {
        return document.body.clientHeight;
    }

    //获取当前内容宽度
    var getBodyWidth = function () {
        return document.body.clientWidth;
    }


    /*********************************************************
     * 对于一级菜单过多，增加左右两个按钮控制，判断是否有隐藏部分,这个可以单独作为一个控件
     * 步长可以设置为一次移一个，或者移剩下的宽度的倍数
     * 后面可以考虑支持滑动控制
     * ---cxy----
     * ******************************************************/
    var showTopMenuOver = function () {
        var bgImg, ulBox, stepValue, bg, padLeft, absolutWidth,showBt,
        //显示的个数
            showCount;
        //背景层
        bg = $(".u-topmenu")[0];
        padLeft = parseInt(getComputedStyle(bg, false)["paddingLeft"], 10);
        //bgImg = getComputedStyle(bg, false)["background"];

        //应该显示的宽度
        absolutWidth = bg.offsetWidth - padLeft - padLeft - 40;

        //ul层
        ulBox = bg.firstChild;
        //一个li的宽度
        stepValue = ulBox.firstChild.offsetWidth;

        //菜单个数*宽度
        ulBox.style.width = ulBox.childNodes.length * stepValue + "px";

        //是否显示按钮
        showBt = function(){
            ulBox.offsetLeft - padLeft >= 0?leftBt.hide():leftBt.show();
            ulBox.offsetLeft + ulBox.offsetWidth - showCount * stepValue - padLeft <= 0? rightBt.hide():rightBt.show();
        };

        if (!leftBt) {
            leftBt = "<div id ='leftBt'><i class='iconfont'>&#xe612;</i></div>";
            rightBt = "<div id = 'rightBt'><i class='iconfont'>&#xe610;</i></div>";

            maskLeft = "<div id ='maskLeft'></div>";
            maskRight = "<div id = 'maskRight'></div>";
            $(bg).append(maskLeft + maskRight + leftBt + rightBt);

            leftBt = $('#leftBt');
            rightBt = $('#rightBt');
            maskLeft = $('#maskLeft');
            maskRight = $('#maskRight');

            $('#maskLeft,#maskRight').css({
                "height": bg.offsetHeight,
                "top": bg.offsetTop,
                "z-index": 33
            });
            $('#leftBt,#rightBt').css({
                "height": bg.offsetHeight,
                "lineHeight": bg.offsetHeight + "px",
                "top": bg.offsetTop,
                "z-index": 34
            });
        }

        if (ulBox.offsetWidth > absolutWidth) {
            showCount = parseInt(absolutWidth / stepValue, 10);

            leftBt.css({"left": padLeft - 15});
            rightBt.css({"right": absolutWidth - showCount * stepValue});

            maskLeft.css({"width": padLeft, "left": 0});
            maskRight.css({"width": absolutWidth + 40 - showCount * stepValue, "right": 0});

            leftBt.hide();
            rightBt.show();
            maskLeft.show();
            maskRight.show();

            leftBt.on("click", function () {
                $(ulBox).css({"left": (ulBox.offsetLeft + showCount * stepValue) + "px"});
                setTimeout(function() {
                    showBt();
                },300);
            });
            rightBt.on("click", function () {
                $(ulBox).css({"left": (ulBox.offsetLeft - showCount * stepValue) + "px"});
                setTimeout(function(){
                    showBt();
                },300);
            });

        } else {
            ulBox.style.left = padLeft + "px";
            ulBox.style.width = "100%";
            leftBt.hide();
            rightBt.hide();
            maskLeft.hide();
            maskRight.hide();
        }
    };
    $(window).resize(function () {
        setTimeout(showTopMenuOver,400);
    });
})
(jQuery);