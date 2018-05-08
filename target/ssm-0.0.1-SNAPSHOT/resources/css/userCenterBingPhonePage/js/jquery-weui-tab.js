/*
* @Author: 卓文理
* @Email : 531840344@qq.com
* @Desc  : 基于WEUI的tab插件
*/

(function (factory) {
    if ( typeof define === 'function' && define.amd ) {
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        module.exports = factory;
    } else {
        factory(jQuery);
    }
}(function ($) {
    var ua = navigator.userAgent;
    var isMobile = /(iPhone|Android|Mobile)/.test(ua);
    var EVENT_TAP = isMobile && !jQuery ? 'tap' : 'click';

    function tab (e, callback) {
        var that = this;
        var _tab_ = {};
        var $navbar = $(e);
        var $navbd  = $navbar.find('.weui_tab_bd');
        var $navitem = $navbar.find('.weui_navbar_item');

        this.callback = callback;

        $navbar.delegate('.weui_navbar_item', EVENT_TAP, function() {
            var $this = $(this);
            var tab = $this.data('tab');
            var index = $this.index();
            var click = parseInt($this[0].dataset.click || 0) + 1;

            _tab_.index = index;
            _tab_.tab = tab;
            _tab_.click = click;

            $this.attr('data-click', click);

            if ($this.hasClass('weui_bar_item_on')) {
                return that.call(_tab_);
            }

            $navitem.removeClass('weui_bar_item_on');
            $this.addClass('weui_bar_item_on');
            $navbd.find('.weui_tab_bd_item').removeClass('weui_tab_bd_item_active');
            $navbd.find('#' + tab).addClass('weui_tab_bd_item_active');

            that.call(_tab_);
        });
    }

    tab.prototype.call = function(_tab_) {
        if (this.callback && typeof this.callback === 'function') {
            this.callback(_tab_)
        }

        return(this);
    }

    $.fn.extend({
        tab: function(callback) {
            new tab(this, callback);
        }
    });
}));