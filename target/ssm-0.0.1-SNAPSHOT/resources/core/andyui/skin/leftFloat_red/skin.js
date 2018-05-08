/*global $,andy*/
//框架初始化
var frameworkInit = function (dataUl, mainUl,isExpandLeftMenu) {

    andy.frame.loadData(dataUl, function (data) {
        var switchLeftMenu = function(el){
            if(!el.is(":animated")){
                var floatw = el.outerWidth();
                var handle = el.find(".an-floathandle");
                if (parseInt(el.css("left"), 10) >= 0) {
                    el.animate({left: -floatw + handle.outerWidth() + 'px'}, 300);
                    handle.html("<i class='iconfont'>&#xe6ce;</i>");
                    //handle.html("<i class='iconfont'>&#xe6cc;</i>");
                } else {
                    el.animate({left: '0px'}, 300);
                    handle.html("<i class='iconfont'>&#xe6cd;</i>");
                    //handle.html("<i class='iconfont'>&#xe6cf;</i>");
                }
            }

        };

        /**
         * 选中一级菜单
         * @param e
         */
        var chooseFirstMenu = function (e) {
            var leftBox, hasChild, index = e.getAttribute("index");

            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"));

            //刷新常用菜单
            andy.frame.refreshUnusualMenu(document.getElementById("unusualMenu"), index);

            //切换左侧菜单
            leftBox = $('#leftnavbox');

            hasChild = data[index].children && data[index].children.length > 0 ? true : false;

            //没有子菜单&&打开=收回
            if (parseInt(leftBox.css("left"), 10) >= 0 && !hasChild) {
                switchLeftMenu(leftBox);
            } else if (hasChild && (parseInt(leftBox.css("left"), 10) < 0 || leftBox.attr("currentSys") === index)) {
                switchLeftMenu(leftBox);
            }


            $("#leftM_" + leftBox.attr("currentSys")).hide();
            $("#leftM_" + index).fadeIn();
            leftBox.attr({"currentSys": index});

        };


        //创建头部菜单///////////////////////////////////////////////////////
        andy.frame.topMenu($('.u-topmenu')[0], "stage", false, chooseFirstMenu);

        //创建左侧菜单/////////////////////////////////////////////////////////
        andy.frame.leftMenu($('#menu-nav-left')[0], "stage",isExpandLeftMenu, function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"));
        });

        //激活不常用菜单//////////////////////////////////////////////////
        andy.frame.unusualMenu("#unusualMenu", function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"));
        });

        //载入主页
        if (mainUl) {
            $("#stage").attr("src", mainUl);
        }
        //如果默认不展开左侧菜单
        if (isExpandLeftMenu === false){
            switchLeftMenu($('.u-float.left'));
        }


        //鼠标滑过左侧菜单
        $(document).on('mouseleave', '.u-float.left', function (e) {
            switchLeftMenu($(this));
        });
        $(document).on('mouseleave', '.an-floathandle', function (e) {
            switchLeftMenu($(this).parent());
        });
        $(document).on('mouseenter', '.an-floathandle', function (e) {
            switchLeftMenu($(this).parent());
        });
    });
};

