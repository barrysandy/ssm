/*global $,andy*/
//框架初始化
var frameworkInit = function (dataUl, mainUl) {

    andy.frame.loadData(dataUl, function (data) {
        var expandLeftMenu,side = $(".m-side"),centerMain = $("#stage");
        /**
         * 选中一级菜单
         * @param e
         */
        var chooseFirstMenu = function (e) {
            var leftBox, index = e.getAttribute("index");

            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"));

            //刷新常用菜单
            andy.frame.refreshUnusualMenu(document.getElementById("unusualMenu"), index);

            //切换左侧菜单
            leftBox = $('#leftnavbox');
            $("#leftM_" + leftBox.attr("currentSys")).hide();
            $("#leftM_" + index).fadeIn();
            leftBox.attr({"currentSys": index});

            //如果菜单收起，展开菜单
            if (side.hasClass("g-min-side")){
                expandLeftMenu();
            }

            andy.frame.leftMenuOverFlow();
        };


        //创建头部菜单///////////////////////////////////////////////////////
        andy.frame.topMenu($('.u-topmenu')[0], "stage", false, chooseFirstMenu);

        //创建左侧菜单/////////////////////////////////////////////////////////

        var sideWidth = side.width();
        andy.frame.leftMenu($('#menu-nav-left')[0], "stage", function (e) {
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
            centerMain.attr("src", mainUl);
        }


         expandLeftMenu = function () {

            side.toggleClass("g-min-side");

            //特殊处理收起的时候，子菜单收起
            if (side.hasClass("g-min-side")) {
                var id = $('#menu-nav-left .u-sidemenu:visible')[0].id;
                var activateUl = $('#' + id + ' .activate ul');
                activateUl.hide();
                //if(andy.frame.leftLastUl[id]){
                //    andy.frame.leftLastUl[id].parent().toggleClass("activate");
                //    andy.frame.leftLastUl[id] = undefined;
                //}
            }

            andy.shrinkageLayout({
                element: side,
                max: sideWidth,
                direction: "l",
                min: 45,
                layout: false,
                callback: function () {
                    var center = $('.index-center'), activateA = $('.menu-nav-left .activate a');
                    if (side.hasClass("g-min-side")) {
                        $('.u-menutitle').children('i').replaceWith("<i class='iconfont pull'>&#xe627;</i>");
                        center.width(center.width() + 155);
                        center.css("left", 45);
                        andy.layout(center);
                        //if (activateA[0]) {
                        //    centerMain.attr("src", activateA[0].src);
                        //}
                    } else {
                        $('.u-menutitle').children('i').replaceWith("<i class='iconfont pull'>&#xe628;</i>");
                        center.width(center.width() - 155);
                        center.css("left", 200);
                        andy.layout(center);
                        //if (activateA[0]) {
                        //    centerMain.attr("src", activateA[0].src);
                        //}
                    }
                }
            });
        };

        /**
         * 左侧菜单收起事件
         */
        $(".u-menutitle").on('click',expandLeftMenu);

        ////左侧菜单鼠标滑过收起，g-min-side指菜单收起
        $(document).on('mouseleave', '.g-min-side .nav-sub', function (e) {
            $(this).slideUp(200);
            var id = $('#menu-nav-left .u-sidemenu:visible')[0].id;
            if (andy.frame.leftLastUl[id]) {
                andy.frame.leftLastUl[id].parent().toggleClass("activate");
                andy.frame.leftLastUl[id] = undefined;
            }
        });


    });
};

