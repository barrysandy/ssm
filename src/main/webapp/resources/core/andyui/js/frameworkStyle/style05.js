/*global $,andy*/
/**
 * 只有左侧菜单  成都经信委企业填报
 * @param dataUl
 * @param mainUl
 */
var frameworkInit = function (dataUl,mainUl) {

    andy.frame.loadData(dataUl, function (data) {
        //创建左侧菜单/////////////////////////////////////////////////////////
        var side = $(".m-side");
        var sideWidth = side.width();

        andy.frame.leftMenu($('#menu-nav-left')[0], "stage", function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e,document.getElementById("u-path"),mainUl);

            //只有首页的时候要求首页导航不显示
            if($(".u-path li").length===1){
                $(".u-path").parent()[0].style.display="none";
                $(".u-path").parent().height(0);
                andy.layout();
            }else{
                $(".u-path").parent()[0].style.display="block";
                $(".u-path").parent().height(40);
                andy.layout();
            }
        },true);

        //var firstA = $("#leftM_0:first-child").children().first().find("a");
        //andy.frame.refreshPath(firstA[0],document.getElementById("u-path"),mainUl);
        //不要导航条
        $(".u-path").parent()[0].style.display="none";
        $(".u-path").parent().height(0);
        andy.layout();

        //载入主页
        if(mainUl){
            $("#stage").attr("src", mainUl);
        }

        /**
         * 左侧菜单收起事件
         */
        $(".u-menutitle").on('click', function () {
            side.toggleClass("g-min-side");

            //特殊处理收起的时候，子菜单收起
            if (side.hasClass("g-min-side")) {
                var id = $('#menu-nav-left .u-sidemenu:visible')[0].id;
                var activateUl = $('#' + id + ' .activate ul');
                activateUl.hide();
                //if(andy.frame.leftLastUl[id]){
                //    andy.frame.leftLastUl[id].toggleClass("activate");
                //    andy.frame.leftLastUl[id].parent().toggleClass("activate");
                //    andy.frame.leftLastUl[id] = undefined;
                //}
            }

            andy.shrinkageLayout({
                element: side,
                max: sideWidth,
                direction: "l",
                min: 45,
                callback: function () {
                    if (side.hasClass("g-min-side")) {
                        $('.u-menutitle').children('i').replaceWith("<i class='iconfont pull'>&#xe627;</i>");
                    } else {
                        $('.u-menutitle').children('i').replaceWith("<i class='iconfont pull'>&#xe628;</i>");
                    }
                }
            });
        });

        ////左侧菜单鼠标滑过收起，g-min-side指菜单收起
        $(document).on('mouseleave', '.g-min-side .nav-sub', function(e){
            $(this).slideUp(200);
            //var id = $('#menu-nav-left .u-sidemenu:visible')[0].id;
            //if(andy.frame.leftLastUl[id]){
            //    andy.frame.leftLastUl[id].parent().toggleClass("activate");
            //    andy.frame.leftLastUl[id] = undefined;
            //}
        });
    });
};

