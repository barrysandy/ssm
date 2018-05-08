/*global $,andy*/
//框架初始化
var frameworkInit = function (dataUl, mainUl) {

    andy.frame.loadData(dataUl, function (data) {
        /**
         * 选中一级菜单
         * 有子节点的是两个回调参数
         * @param e
         */
        var chooseFirstMenu = function (root, e) {
            var index = root.getAttribute("index");
            e = e || root;

            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"));

            //刷新常用菜单
            andy.frame.refreshUnusualMenu(document.getElementById("unusualMenu"), index);

        };


        //创建头部菜单///////////////////////////////////////////////////////
        andy.frame.topMenu($('.u-topmenu')[0], "stage", true, chooseFirstMenu);


        //激活不常用菜单//////////////////////////////////////////////////
        andy.frame.unusualMenu("#unusualMenu", function (e) {
            //更新面包屑路径
            andy.frame.refreshPath(e, document.getElementById("u-path"));
        });

        //载入主页
        if (mainUl) {
            $("#stage").attr("src", mainUl);
        }
    });
};

