/*global andy,$,template*/
/**加载等待模块**/
(function (andy,$) {
    /**
     * 关闭等待
     * @param target 弹出目标层  top最外层  self自己页面
     * @param callback
     */
    var close = function (target,callback) {
        var loading_bg;
        if(target==="top"){
            loading_bg = window.top.$('body').find('.loading_bg');
        }else{
            loading_bg = window.$('body').find('.loading_bg');
        }

        loading_bg.addClass('fadeOut');
        setTimeout(function () {
            loading_bg.remove();
            if (typeof callback == 'function') {
                callback();
            }
        },500);
    };

    /**
     * 弹出覆盖层...
     * @param target 弹出目标层  top最外层  self自己页面
     * @param content 内容 可以是html字符串
     * @param clazz  样式类名
     * @param callback 回调
     */
    andy.overlay = function (target, content, clazz,callback) {
        var overlaydiv = '<div class="loading_bg ' + clazz + ' animated fadeIn">' + content + '</div>';
        if (target == 'top' && window.top.$('body').find('.loading_bg').length == 0) {
            window.top.$('body').append(overlaydiv);
        }
        else if (target == 'top' && window.top.$('body').find('.loading_bg').length == 1) {
            window.top.$('body').find('.loading_bg').html(content);
        }
        else if (target == 'self' && $('body').find('.loading_bg').length == 0) {
            $('body').append(overlaydiv);
        }
        else if (target == 'self' && $('body').find('.loading_bg').length == 1) {
            $('body').find('.loading_bg').html(content);
        }
        else if (target != 'self' && target != 'top' && $('body').find('.loading_bg').length == 0) {
            $(target).append(overlaydiv);
            $(target).find('.loading_bg').css({
                'position': 'absolute'
            });
        }
        else if (target != 'self' && target != 'top' && $('body').find('.loading_bg').length == 1) {
            $(target).find('.loading_bg').html(content);
        }
        if (typeof callback == 'function') {
            callback();
        }
    };

    /**
     * 页面加载loding...
     * @param target 弹出目标层  top最外层  self自己页面
     * @param text 文本内容
     * @param callback 回调
     */
    andy.loading = function (target, text, callback) {
        var that={};
        var content = '<div class="loading_bg_center"><span class="quarters-loader"></span><p id="loadingtext">' + text + '</p></div>';
        andy.overlay(target, content);
        if (typeof callback == 'function') {
            callback();
        }
        //关闭
        that.close = function(callback){
            close(target,callback);
        };
        //修改消息
        that.changeText = function(text){
            var content2 = '<div class="loading_bg_center"><span class="quarters-loader"></span><p id="loadingtext">' + text + '</p></div>';
            andy.overlay(target, content2);
        };
        return that;
    };
    //原来的方法保留
    andy.closeOverlay = function(callback){
        close("top",callback);
    };

    return andy;
}(andy,$));
