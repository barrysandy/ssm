//锁定行列
(function ($) {
    $.fn.extend({
        TableLock: function(options) {
            var tl = $.extend({
                table:'lockTable',//table的id
                lockRow:1,//固定行数
                lockColumn:1,//固定列数
                width:'100%',//表格显示宽度（实质是外出div宽度）
                height:'100%',//表格显示高度（实质是外出div高度）
                lockRowCss:'lockRowBg',//锁定行的样式
                lockColumnCss:'lockColumnBg'//锁定列的样式
            }, options);

            var tableId=tl.table;
            var table=$('#'+tableId);
            var boxid = 'divBoxing-' + tableId;
            var bodyid = 'divBoxingbody-' + tableId;
            var headid = 'divBoxinghead-' + tableId;
            var fixedid = 'divBoxingfixed-'+tableId;
            var topindex = 50;
            var bottomindex = 1;
            var fiexdindex = 100;
            var rowSpan=function(tr){

            }
            if(table){

                // jQuery.fx.interval = 10000;
                var box=$("<div id='"+boxid+"'></div>").scroll(function(e){//在此处添加事件
                    if(e.target.scrollTop > 0){
                        divBoxbody.css({"z-index":bottomindex});
                        divBoxhead.css({"z-index":topindex});
                    }
                    if(e.target.scrollLeft > 0){
                        divBoxbody.css({"z-index":topindex});
                        divBoxhead.css({"z-index":bottomindex});
                    }
                    divBoxbody.stop().animate({"scrollTop":e.target.scrollTop+'px'}, 0);
                    divBoxhead.stop().animate({"scrollLeft":e.target.scrollLeft+'px'}, 0);
                });
                box.css({'width':tl.width, 'height':tl.height, 'overflow':'auto', 'position':'relative', 'clear':'both'});//设置高度和宽度
                table.wrap(box);
                table.addClass('tbLock');

                //创建div
                var divBox = $("#" + boxid);
                divBox.after("<div id = '"+headid+"'></div>");
                divBox.after("<div id = '"+bodyid+"'></div>");
                divBox.after("<div id = '"+fixedid+"'></div>");
                var divBoxhead = $("#" + headid);
                var divBoxbody = $("#" + bodyid);
                var divBoxfixed = $("#" + fixedid);

                var crossNum=tl.lockRow*tl.lockColumn;
                var rowheights = 0;
                var colwidths = 0;
                if(tl.lockRow>0){
                    var tr;
                    for(var r=0;r<tl.lockRow;++r){//添加行锁定
                        tr=table.find('thead tr:eq('+r+') >th').addClass('LockRow').addClass(tl.lockRowCss);
                        rowheights += tr[r].offsetHeight;
                        for(var c=0;c<tl.lockColumn;++c){//设置交叉单元格样式，除了锁定单元格外还有交叉单元格自身样式
                            if(tr){
                                table.find('thead tr th:eq('+c+')').addClass('LockCell');
                                tr.find('td:eq('+c+')').addClass('LockCell').addClass(tl.lockRowCss);
                            }
                            if(r == 0){
                                colwidths += tr[c].offsetWidth;
                            }
                        }
                    }
                }
                if(tl.lockColumn>0){
                    var rowNum=$('#'+tableId+' tr').length;
                    var tr;
                    for(var r=(tl.lockRow);r<rowNum;++r){
                        tr=table.find('tr:eq('+r+')');
                        for(var c=0;c<tl.lockColumn;++c){//添加列锁定
                            tr.find('td:eq('+c+')').addClass('LockCell').addClass(tl.lockColumnCss);
                        }
                    }
                }


                //复制横向

                var boxwidth = parseInt(divBox.css("width")) - 17;
                // console.log(divBox.css("width"), "|", tl.width);
                divBoxhead.css('width',boxwidth + 'px');//设置高度和宽度
                var th = table.clone().attr({"id":tableId + "_h"});
                divBoxhead.css({"position":"absolute", "top":"0px", "left":"0px", "overflow":"hidden"});
                divBoxhead.css({'height':rowheights + 'px'});
                // th.find("tbody").remove();
                divBoxhead.append(th);

                //复制纵向
                divBoxbody.css({"position":"absolute", "top":"0px", "left":"0px", "overflow":"hidden"});
                divBoxbody.css('height',parseInt(divBox.css("height")) - 17 + 'px').css('width', colwidths+'px');//设置高度和宽度
                var tw = table.clone().attr({"id":tableId + "_w"});
                // tw.find("thead tr th:not(.LockCell)").remove();
                // tw.find("tbody tr td:not(.LockCell)").remove();
                divBoxbody.append(tw);
                // $("#lockTable_w").css({"width":"200px"});

                //复制交叉固定
                divBoxfixed.css({"position":"absolute", "top":"0px", "left":"0px", "overflow":"hidden"});
                divBoxfixed.css({"z-index":fiexdindex});
                divBoxfixed.css({"width":colwidths+"px", "height":rowheights+"px"});
                var fixed = table.clone().attr({"id":tableId + "_f"});
                // fixed.find("thead tr th:not(.LockCell)").remove();
                divBoxfixed.append(fixed);
                // $("#lockTable_f").css({'width':'200px', "_width":"200px"});

            }else{
                alert('没有找到对应的table元素，请确保table属性正确性！');
            }
            if(tl.width == "100%px"){
                $(window).resize(function(){
                    var divBox = $("#" + boxid);
                    var divBoxhead = $("#" + headid);
                    var divBoxbody = $("#" + bodyid);
                    var divBoxfixed = $("#" + fixedid);
                    var boxwidth = parseInt(divBox.css("width")) - 17;
                    divBoxhead.css("width", boxwidth + "px");
                    // console.log(divBox.css("width"));
                })
            }
        }
    });
})(jQuery);