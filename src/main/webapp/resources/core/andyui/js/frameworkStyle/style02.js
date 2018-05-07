/*global $,andy*/
/**
 * 左侧触碰  文保
 */

/**
 *
 * @param dataUl 数据
 * @param mainUl 主页
 * @param bool homeExpandLeftMenu 主页是否展开左侧菜单
 * @param bool clickFMExpandLeftMenu 点击一级菜单是否展开左侧菜单
 */
var frameworkInit = function (dataUl, mainUl, homeExpandLeftMenu, clickFMExpandLeftMenu) {

    andy.frame.loadData(dataUl, function (data) {
        var sideWidth, center = $('.index-center'), switchLeftMenu, side = $(".m-side"), centerMain = $("#stage");
        var switchLeftMenu = function (el) {
            el.stop();
            if (!el.is(":animated")) {
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
            andy.frame.refreshPath(e, document.getElementById("u-path"), mainUl);

            //刷新常用菜单
            andy.frame.refreshUnusualMenu(document.getElementById("unusualMenu"), index);

            //切换左侧菜单
            leftBox = $('#leftnavbox');

            hasChild = data[index].children && data[index].children.length > 0 ? true : false;

            if (hasChild && leftBox.is(":hidden")) {
                leftBox.show();
            }
            if (!hasChild) {
                leftBox.hide();
            }

            if (homeExpandLeftMenu || homeExpandLeftMenu === undefined) {
                //没有子菜单&&打开=收回
                if (parseInt(leftBox.css("left"), 10) >= 0 && !hasChild) {
                    switchLeftMenu(leftBox);
                } else if (hasChild && (parseInt(leftBox.css("left"), 10) < 0 || leftBox.attr("currentSys") === index)) {
                    switchLeftMenu(leftBox);
                }
            } else {
                leftBox.find(".an-floathandle").html("<i class='iconfont'>&#xe6ce;</i>");
            }


            $("#leftM_" + leftBox.attr("currentSys")).hide();
            $("#leftM_" + index).fadeIn();
            leftBox.attr({"currentSys": index});

        };

        side = $(".m-side");
        //创建左侧菜单/////////////////////////////////////////////////////////
        sideWidth = side.width();

        //创建头部菜单///////////////////////////////////////////////////////
        andy.frame.topMenu($('.u-topmenu')[0], "stage", false, chooseFirstMenu);

        //创建左侧菜单/////////////////////////////////////////////////////////
        andy.frame.leftMenu($('#menu-nav-left')[0], "stage", function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"), mainUl);
        });

        //激活不常用菜单//////////////////////////////////////////////////
        andy.frame.unusualMenu("#unusualMenu", function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"), mainUl);
        });

        //载入主页
        if (mainUl) {
            centerMain.attr("src", mainUl);
        } else {
            andy.frame.chooseTopFirstMenu();
        }

        //如果当前左侧菜单没有子项的话，隐藏左侧菜单
        if ($("#leftM_0").find("li").length > 0) {
            side.show();
        } else {
            side.hide();
        }


        //如果默认不展开左侧菜单
        if (homeExpandLeftMenu === false) {
            //switchLeftMenu($('.u-float.left'));
            switchLeftMenu(side);
        }

        /**点击路径中的"首页"，选中第一个一级菜单**/
        $(document).on('click', '.home', function () {
            andy.frame.chooseTopFirstMenu();
        });

        //鼠标滑过左侧菜单
        //$(document).on('mouseleave', '.u-float.left', function (e) {
        //    switchLeftMenu($(this));
        //});
        //$(document).on('mouseleave', '.an-floathandle', function (e) {
        //    switchLeftMenu($(this).parent());
        //});
        //$(document).on('mouseenter', '.an-floathandle', function (e) {
        //    switchLeftMenu($(this).parent());
        //});
        //
        $(document).on('click', '.an-floathandle', function (e) {
            switchLeftMenu($(this).parent());
        });
    });
};

