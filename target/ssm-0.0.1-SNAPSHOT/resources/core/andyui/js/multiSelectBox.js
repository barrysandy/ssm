/*global andy,$,template*/
/**
 * 多选排序盒子
 * 依赖于拖动模块,拖动比它先加载
 * 作者:陈晓莹
 */
(function (andy) {
    var htmlStr = '<div  class="multi-select-box" style="position:relative;width: 100%;height:100%">'
        + '{{each list as value i}}<span multiSelect="true" index="{{i+1}}" data-id="{{list[i].val||list[i].id||list[i].text}}" class="option-block" nametext="{{list[i].text||list[i].name}}" ><span class="indexnum">{{i+1}}.</span> {{list[i].text||list[i].name}}<i notdrag="true" class="iconfont">&#xe62d;</i></span>{{/each}}'
        + '</div><input type="hidden" name="{{dataKey}}">';

    //修改索引
    var changeIndex = function (listBox) {
        var strArry = [];
        var multiSelectInput = listBox.next("input")[0] || listBox.find("input")[0];
        listBox.find("[multiselect]").each(function (index, el) {
            el.setAttribute("index", index + 1);
            $(el).find(".indexnum").html((index + 1) + ".");
            strArry.push(el.getAttribute("data-id"));
        });
        //修改input的值,
        if (multiSelectInput) {
            multiSelectInput.value = strArry.join(",");
        }
    };
    var exchange = function (a, b) {
        var n = a.next(), p = b.prev();
        if (n[0] === b[0]) {
            b.insertBefore(a);
        } else {
            b.insertBefore(n);
            a.insertAfter(p);
        }
    };
    //绑定拖动逻辑
    var dragBind = function () {
        var lineCount, allCount, mouseIndex, lastIndex, lastIndexX, lastIndexY, elIndex, boxChildren;

        if (andy.dragElement) {
            andy.dragElement({
                dragName: "multiSelect",
                dragCopyEl: true,
                dragSelf: false,
                notDragEl: "notdrag",
                onDragBeforeElement: function (dragEl, argu, copyEl) {
                    //将原来的元素谈化
                    dragEl.style.visibility = "hidden";
                    copyEl.style.opacity = 0.5;
                    copyEl.removeAttribute("multiselect");
                    allCount = $(dragEl.parentNode).find("[multiselect]").length;
                    lineCount = parseInt(dragEl.parentNode.offsetWidth / dragEl.offsetWidth, 10);
                    elIndex = parseInt(dragEl.getAttribute("index"), 10);
                    boxChildren = $(dragEl).parent().children();

                    return true;
                },
                onDragElement: function (dragEl, argu, copyEl) {
                    var i = 0, point, index, el;
                    //区域覆盖判断
                    //中心点在 smodule 内

                    for (i; i < boxChildren.length - 1; i++) {
                        point = {};
                        index = i;
                        el = boxChildren[i];
                        point.x = (el.offsetLeft + el.offsetLeft + el.offsetWidth) / 2;
                        point.y = (el.offsetTop + el.offsetTop + el.offsetHeight) / 2;
                        if (el.className === "") {
                            return;
                        }
                        if (point.x > copyEl.offsetLeft && point.x < copyEl.offsetLeft + copyEl.offsetWidth) {
                            if (point.y > copyEl.offsetTop && point.y < copyEl.offsetTop + copyEl.offsetHeight) {
                                mouseIndex = index + 1;
                                if (( !lastIndex && mouseIndex !== elIndex) || (lastIndex && lastIndex !== mouseIndex)) {
                                    lastIndex = mouseIndex;
                                    exchange($(el), $(dragEl));
                                    break;
                                }
                            }
                        }
                    }



                    //var indexX, indexY, elIndex;
                    ////判断偏移和元素宽度倍数关系
                    //indexX = Math.round(copyEl.offsetLeft / dragEl.offsetWidth);
                    //indexY = parseInt(copyEl.offsetTop / dragEl.offsetHeight, 10);
                    //if (lastIndexX === undefined) {
                    //    lastIndexX = indexX;
                    //    lastIndexY = indexY;
                    //}
                    //mouseIndex = (indexY * lineCount + indexX) + 1;
                    //elIndex = parseInt(dragEl.getAttribute("index"), 10);
                    //if (elIndex !== mouseIndex) {
                    //    if (lastIndex === undefined) {
                    //        lastIndex = mouseIndex;
                    //    }
                    //    //把被拖动元素插入位置
                    //    //逻辑，往前移和往上移都是在之前插入
                    //    if (mouseIndex >= allCount) {
                    //        if (elIndex !== allCount) {
                    //            $(dragEl).insertAfter($(dragEl).parent().find("[index=" + allCount + "]"));
                    //        }
                    //    } else {
                    //        if (lastIndexX - indexX > 0 || lastIndexY - indexY > 0) {
                    //            $(dragEl).insertBefore($(dragEl).parent().find("[index=" + mouseIndex + "]"));
                    //        } else {
                    //            $(dragEl).insertAfter($(dragEl).parent().find("[index=" + mouseIndex + "]"));
                    //        }
                    //    }
                    //}
                    //if (lastIndex !== mouseIndex && elIndex === mouseIndex) {
                    //    $(dragEl).insertAfter($(dragEl).parent().find("[index=" + lastIndex + "]"));
                    //}
                },
                onDragEndElement: function (dragEl, argu, copyEl) {
                    lastIndexX = undefined;
                    lastIndexY = undefined;
                    lastIndex = undefined;
                    changeIndex($(dragEl).parent());
                    //$newEl.remove();
                    dragEl.style.visibility = "visible";
                }
            });
        } else {
            throw "需要在这之前加载拖动drag.js";
        }
    };

    andy.multiSelect = function (data) {
        var that={}, parent = $(data.parent);
        var selectBox = parent.find(".multi-select-box");
        parent.css("z-index",1);
        if (selectBox.length > 0) {
            parent.empty();
        }
        if (data.list) {
            var render = template.compile(htmlStr);
            var str = render(data);
            parent.append(str);
            //parent.find(".multi-select-box").height("auto").css({
            //    width: parent.width()
            //});
            parent.find(".multi-select-box").addClass("f-clear");
        }

        changeIndex(parent);

        //绑定关闭按钮
        parent.find("i").mousedown(function (e) {
            var index, box = $(this).parent().parent();
            //执行删除回调
            if (data.onDelete) {
                //被删除的节点索引，数据节点
                index = parseInt($(this).parent().attr("index"), 10);
                data.onDelete(index);
            }
            changeIndex(box);
            $(this).parent().remove();


            //要重置高度？？
            parent.find(".multi-select-box").height("auto").css({
                width: parent.width(),
                height: parent.height() || 23
            });
        });

        //设置是否可用
        that.setEnable = function(isEnable){
            if(!isEnable){
                parent.append("<div multiSelectMask style='position: absolute;width: 100%; height: auto;z-index: 2;background: #fff; opacity: 0.5; top: 0px;'></div>");
                parent.find('[multiSelectMask]').height(parent.height());
            }else{
                parent.find('[multiSelectMask]').remove();
            }
        };

        //添加一个数据
        that.addElement = function(element){
            data.list.push(element);
            var htmlStr='<span multiSelect="true" index="'+ data.list.length +'" data-id="'+(element.val||element.id||element.text)+'" class="option-block" nametext="'+(element.text||element.name)+'" ><span class="indexnum">'+ data.list.length +'.</span> '+(element.text||element.name)+'<i notdrag="true" class="iconfont">&#xe62d;</i></span>';
            parent.find(".multi-select-box").append(htmlStr);
        };
        //删除一个节点数据,因为数据情况比较多，可能是val,可能是id,
        that.deleteElement = function(element){
            parent.find("[data-id="+element.id+"]").remove();
            changeIndex(parent);
        };

        return that;
    };


    dragBind();

}(andy));