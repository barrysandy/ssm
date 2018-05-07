/*global $*/
function layout() {

    $('.g-layout').each(function (index, element) {
        var ParentWidth, ParentHeight, $win, $el;
        $win = $(window);
        $el = $(element);
        //设置框架高宽
        if ($el.parent().is('body')) {
            ParentWidth = $win.width();
            ParentHeight = $win.height();
        } else {
            ParentWidth = $el.parent().width();
            ParentHeight = $el.parent().height();
        }
        alert(ParentHeight);
        //获得结构框架尺寸
        var LayoutHeadHeight = $el.children('.layout-head').height(),
            LayoutLeftWidth = $el.children('.layout-left').width(),
            LayoutRigthWidth = $el.children('.layout-right').width(),
            LayoutFootHeight = $el.children('.layout-foot').height();
        //设置框架各个元素的尺寸
        autolayout(element, ParentWidth, ParentHeight, LayoutHeadHeight, LayoutLeftWidth, LayoutRigthWidth, LayoutFootHeight);
    });
}
//动态调整框架方法	
function autolayout(index, pw, ph, ind_head, ind_left, ind_right, ind_foot) {
    var $el=$(index);
    $el.width(pw).height(ph);
    $el.children('.layout-head').width(pw).height(ind_head);
    $el.children('.layout-left').height(ph - ind_head - ind_foot).width(ind_left).css('top', ind_head + 'px');
    $el.children('.layout-right').height(ph - ind_head - ind_foot).width(ind_right).css('top', ind_head + 'px');
    $el.children('.layout-foot').width(pw).height(ind_foot);
    $el.children('.layout-center').width(pw - ind_left - ind_right).height(ph - ind_head - ind_foot - 2).css({
        'top': ind_head + 'px',
        'left': ind_left + 'px'
    });
}

//动态高度填充
function hmax() {
    $('.g-h-max').each(function (index, element) {
        var i,hmaxH,$el=$(element),
            parenth = $el.parent().height(),
            hmaxn = $el.siblings('.g-h-max').length + 1,
            sibls = $el.siblings().not('.g-h-max'),
            sibl = sibls.not('.u-float'),
            siblingn = sibl.length,
            sum = 0;
        for ( i = 0; i < siblingn; i++) {
            sum += $(sibl[i]).outerHeight();
        }

        hmaxH = (parenth - sum) / hmaxn;
        $el.css('height', hmaxH + 'px');
        $el.siblings('.g-h-max').css('height', hmaxH + 'px');
    });
}

//动态处理填充尺寸溢出
function pmauto() {
    $('.f-fit').each(function (index, element) {
        var $el=$(element), Pw = $el.parent().width(),
            Ph = $el.parent().height(),
            wp = parseInt($el.css('padding-left')) + parseInt($el.css('padding-right')),
            hp = parseInt($el.css('padding-top')) + parseInt($el.css('padding-bottom'));
        $el.height(Ph - hp);
        $el.width(Pw - wp);
    });
};

$(function () {
    layout();
});