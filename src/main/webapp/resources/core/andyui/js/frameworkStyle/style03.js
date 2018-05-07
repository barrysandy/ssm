/*global $,andy*/
/**
 * 极简版，无左侧菜单，无顶部菜单
 * @param dataUl
 * @param mainUl
 */
var frameworkInit = function (dataUl, mainUl) {

    andy.frame.loadData(dataUl, function (data) {
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

