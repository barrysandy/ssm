/*global $,andy*/
/**
 * 左侧推拉式，点击一级菜单展开，中钞廉政，经信委综合管理平台
 */

/**
 * @param dataUl 数据
 * @param mainUl 主页
 * @param bool homeExpandLeftMenu 主页是否展开左侧菜单
 * @param bool clickFMExpandLeftMenu 点击一级菜单是否展开左侧菜单
 */
var frameworkInit = function (dataUl, mainUl, homeExpandLeftMenu, clickFMExpandLeftMenu) {

    andy.frame.loadData(dataUl, function (data) {
        var sideWidth, center = $('.index-center'), switchLeftMenu, side = $(".m-side"), centerMain = $("#stage");

        //收缩的时候需要重新布局center
        var layoutCenter = function (left) {
            if (parseInt(center.css("left"), 10) !== left) {
                center.css("left", left);
                center.width(center.parent().width() - left);
                if (side.is(":hidden")) {
                    side.width(0);
                }
                else {
                    side.width(left);
                }
                andy.layout(center);
            }
        };
        switchLeftMenu = function () {
            if (side.css("display") !== 'none') {
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
                        if (side.hasClass("g-min-side")) {
                            $('.u-menutitle').children('i').replaceWith("<i class='iconfont pull'>&#xe627;</i>");
                            layoutCenter(side.width());
                        } else {
                            $('.u-menutitle').children('i').replaceWith("<i class='iconfont pull'>&#xe628;</i>");
                            layoutCenter(sideWidth);
                        }

                    }
                });
            } else {
                center = $('.index-center');
                layoutCenter(0);
            }
        };

        /**
         * 选中一级菜单
         * @param e
         */
        var chooseTopMenu = function (e) {
            var forShowBox, index = e.getAttribute("index");
            if(index==0){
            	 centerMain.attr("src", "/lz_sys/protalTotal/newfile.dhtml");
                 side.hide();
                 layoutCenter(0);
                 andy.frame.refreshPath(e, document.getElementById("u-path"), "/lz_sys/protalTotal/newfile.dhtml");
            	//andy.frame.refreshPath(e, document.getElementById("u-path"), "/protalTotal/newfile.dhtml");
            }else{
            	/*var isHidden = fase;
                $(".u-sidemenu").each(function(){
             	   if($(this).is(":hidden")){
             		   isHidden = true;
             	   }
                })
            	if(isHidden){
            		$(".u-menutitle").click();
            	}*/
            	
            	//更新面包屑路径
                andy.frame.refreshPath(e, document.getElementById("u-path"), mainUl);
            }

            //刷新常用菜单
            andy.frame.refreshUnusualMenu(document.getElementById("unusualMenu"), index);

            //切换左侧菜单
            $("#menu-nav-left").css("top", 0);
            forShowBox = $("#leftM_" + index);
            $("#leftM_" + side.attr("currentSys")).hide();
            forShowBox.fadeIn();
            side.attr({"currentSys": index});

            //如果当前左侧菜单没有子项的话，隐藏左侧菜单
            if (forShowBox.find("li").length > 0) {
                if (side.css("display") === "none") {
                    side.show();
                    layoutCenter(side.hasClass("g-min-side") ? 45 : sideWidth);
                }

                // 如果顶部才菜单没有链接
                // 如果第一项有子菜单展开第一项,选中子菜单第一项，
                // 没有的话直接选中第一项
                if(!data[index].url||data[index].url==""){
                    //if (data[index].children && data[index].children[0].children && data[index].children[0].children.length > 0) {
                    if (data[index].children) {
                        var firstA = forShowBox.find('a:first');
                        if(firstA.length>0){
                            //如果第一项没有展开展开第一项
                            if(!firstA.parent().is(".activate")){
                                andy.frame.clickLeftChild(firstA);
                            }
                            if(data[index].children[0].children && data[index].children[0].children.length > 0){
                                andy.frame.clickLeftChild(firstA.parent().find("ul").find('a:first'));
                            }
                            centerMain.attr("src", firstA.href||data[index].children[0].url||data[index].children[0].children[0].url);
                        }
                    }
                }


            } else {
                if (side.css("display") === "block") {
                    side.hide();
                    layoutCenter(0);
                }

            }

            //更新左侧菜单标题
            $(".u-menutitle").find("h4").html(e.innerHTML);


            //如果左侧菜单收起，展开菜单，中钞需求
            if (clickFMExpandLeftMenu && side.hasClass("g-min-side")) {
                switchLeftMenu();
            }

            andy.frame.leftMenuOverFlow();
        };

        side = $(".m-side");
        //创建左侧菜单/////////////////////////////////////////////////////////
        sideWidth = side.width();

        //创建头部菜单///////////////////////////////////////////////////////
        andy.frame.topMenu($('.u-topmenu')[0], "stage", false, chooseTopMenu);


        andy.frame.leftMenu($('#menu-nav-left')[0], "stage", function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"), mainUl);
        });


        //激活不常用菜单//////////////////////////////////////////////////
        andy.frame.unusualMenu("#unusualMenu", function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"), mainUl);
        });

        //载入主页,首页是没有左侧菜单的
        if (mainUl && mainUl!=="") {
            centerMain.attr("src", mainUl);
            side.hide();
            layoutCenter(0);
        }else{
            //如果默认不展开左侧菜单
            //if (homeExpandLeftMenu === false) {
            //    //switchLeftMenu($('.u-float.left'));
            //    switchLeftMenu(side);
            //} else {
            //    layoutCenter(sideWidth);
            //}
            andy.frame.chooseTopFirstMenu();
        }

        ////如果当前左侧菜单没有子项的话，隐藏左侧菜单
        //if ($("#leftM_0").find("li").length > 0) {
        //    side.show();
        //} else {
        //    side.hide();
        //}



        /**点击路径中的"首页"，选中第一个一级菜单**/
        $(document).on('click', '.home', function () {
            andy.frame.chooseTopFirstMenu();
        });

        /**
         * 左侧菜单收起事件
         */
        $(".u-menutitle").on('click', switchLeftMenu);

        ////左侧菜单鼠标滑过收起，g-min-side指菜单收起
        $(document).on('mouseleave', '.g-min-side .nav-sub', function (e) {
            $(this).slideUp(200);
        });
        //$('.g-min-side .nav-sub').slideUp(200);
    });
};

/**
 * 反向指定系统
 * @param topId
 * @param leftId
 * @param functionName
 * @param args
 */
window.frameworkSetSys = function(topId,leftId,functionName,args){
    var pLi,aa = $('#'+leftId).find('a:first');
    andy.frame.clickTopChild($('#'+topId).find('a:first'));
    //如果父级没有展开需要展开父级
    if(aa.attr("index").split("_").length>2){
        pLi = aa.parent().parent().parent();
        if(!pLi.is(".activate")){
            andy.frame.clickLeftChild(pLi.find('a:first'));
        }
    }
    andy.frame.clickLeftChild($('#'+leftId).find('a:first'));
    //有可能需要手动设置页面路径$('#'+leftId).find('a:first').url
    var f = document.getElementById("stage");
    f.src = aa.attr("href");
    //f.contentWindow.$.(document).ready(function(){
    //f.contentWindow[functionName](args);
    // });
};



