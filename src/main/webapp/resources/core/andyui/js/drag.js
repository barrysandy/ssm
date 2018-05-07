/*global andy,$*/
/**
 * 拖动模块
 */
(function (andy) {
    /***
     * 向外查找元素  (向外查找)
     * <br />一直查找到document节点，如果没有设置value，则直接返回该节点， 否则与value值进行比较
     * @param {element} el 指定元素
     * @param {string} name 属性名
     * @param {string} value 属性值
     * @param {string} stopAttr 结束属性名
     * @param {string} notAttr 被排除的元素
     * @return {element} 查找的元素
     */
    andy.findOutElement = function (el, name, value, stopAttr, notAttr) {
        if (el) {
            var v, doc = el.ownerDocument;
            while (el && el !== doc) {
                if (el.getAttribute) {
                    if (stopAttr) {
                        v = el.getAttribute(stopAttr);
                        if (v === "" || v) {
                            return;
                        }
                    }
                    if (notAttr) {
                        v = el.getAttribute(notAttr);
                        if (v === "" || v) {
                            return;
                        }
                    }
                    v = el.getAttribute(name);
                    if (v === "" || v) {
                        if (value !== undefined) {
                            if (v.indexOf(value) > -1) {
                                return el;
                            }
                        } else {
                            return el;
                        }
                    }
                }
                el = el.parentNode;
            }
        }
    };

    //所有的拖动实例，实例的创建先后决定了事件的优先级
    var drags = [],
    //用于标识drags中拖动实例计数，方便管理
        dragCount = 0, currentDrag,
        dragStart = false,
        pos = {}, posing = {},
        lockTime = new Date(),
        doDragBefore;

    //增加样式，控制禁止选中文字
    var fixUserSelect = function (rootElement) {
        rootElement.className = rootElement.className + " u-global-drag";
    };
    var restoreUserSelect = function (rootElement) {
        rootElement.className = rootElement.className.replace(" u-global-drag", "");
    };
    var mouseDown = function (event) {
        var i;
        if (!dragStart && drags.length > 0) {
            for (i = drags.length - 1; i >= 0; i--) {
                dragStart = andy.arrayFunCall(drags[i].onDragCheck, {event: event, data: drags[i].data});
                if (dragStart) {
                    currentDrag = drags[i];
                    break;
                }
            }
            if (dragStart) {
                fixUserSelect(currentDrag.element || document.body);
                pos.x = posing.x = event.clientX;
                pos.y = posing.y = event.clientY;
                if (event.stopPropagation) {
                    event.stopPropagation();
                } else {
                    event.cancelBubble = true;
                }
            }
        }
    };
    var mouseMove = function (event) {
        var i, isTrue, offset = {};
        if (drags.length > 0) {
            if (dragStart && currentDrag) {
                var time = new Date() - lockTime;
                if (time > currentDrag.restrictedMoveTime) {//减少调用次数，防止卡死IE6-8
                    lockTime = time;
                    if (doDragBefore === undefined) {
                        doDragBefore = true;
                        if (currentDrag.onDragBefore.length > 0) {
                            doDragBefore = andy.arrayFunCall(currentDrag.onDragBefore, [event]);
                        }
                    }
                    //fixUserSelect(currentDrag);
                    offset.x = event.clientX - posing.x;
                    offset.y = event.clientY - posing.y;

                    posing.x = event.clientX;
                    posing.y = event.clientY;

                    if (doDragBefore) {
                        //有拖动区域限制
                        if (currentDrag.lockRect) {
                            if (event.clientX > currentDrag.lockRect.x + currentDrag.lockRect.w) {
                                offset.x = 0;
                                posing.x = currentDrag.lockRect.x + currentDrag.lockRect.w;
                            }
                            if (event.clientX < currentDrag.lockRect.x) {
                                offset.x = 0;
                                posing.x = currentDrag.lockRect.x;
                            }
                            if (event.clientY > currentDrag.lockRect.y + currentDrag.lockRect.h) {
                                offset.y = 0;
                                posing.y = currentDrag.lockRect.y + currentDrag.lockRect.h;
                            }
                            if (event.client < currentDrag.lockRect.y) {
                                offset.y = 0;
                                posing.x = currentDrag.lockRect.y;
                            }
                        }
                        if (currentDrag.lockX) {
                            offset.y = 0;
                        }
                        if (currentDrag.lockY) {
                            offset.x = 0;
                        }
                        //移动过程，移动偏移
                        andy.arrayFunCall(currentDrag.onDragOn, {
                            "offset": offset,
                            "data": currentDrag.data,
                            "event": event
                        });
                    }
                }
                return false;
            } else {
                for (i = drags.length - 1; i >= 0; i--) {
                    isTrue = andy.arrayFunCall(drags[i].onMoveCheck, {event: event, data: drags[i].data});
                    if (isTrue) {
                        break;
                    }
                }
            }
        }
    };
    var mouseUp = function (event) {
        if (dragStart && currentDrag) {
            //移动过程，移动偏移
            var r = andy.arrayFunCall(currentDrag.onDragEnd, {event: event, data: currentDrag.data});
            restoreUserSelect(currentDrag.element || document.body);
            dragStart = false;
            doDragBefore = undefined;
            currentDrag = undefined;
            return r;
        }
    };
    var bindEvent = function () {
        //andy.addGlobalEvent("mousedown", mouseDown);
        //andy.addGlobalEvent("mousemove", mouseMove);
        //andy.addGlobalEvent("mouseup", mouseUp);
        $(document).on("mousedown", mouseDown);
        $(document).on("mousemove", mouseMove);
        $(document).on("mouseup", mouseUp);
    };
    //元素被移除的时候要解除绑定，
    // 这个是使用的时候主动解绑，还是制定一个自动解绑机制？？？
    var unbindEvent = function () {
        //andy.removeGlobalEvent("mousedown", mouseDown);
        //andy.removeGlobalEvent("mousemove", mouseMove);
        //andy.removeGlobalEvent("mouseup", mouseUp);
        $(document).off("mousedown", mouseDown);
        $(document).off("mousemove", mouseMove);
        $(document).off("mouseup", mouseUp);
    };


    /**
     *  初始化一个拖动机制
     * @param options
     * @returns {{}}
     */
    andy.drag = function (options) {
        var defaults = {
            lockX: false,
            lockY: false,
            lockRect: undefined,//{x:0,y:0,w:0,h,0}

            restrictedMoveTime: 12,//拖动时间限定，提高性能
            //eventRegion: window.top,//事件绑定域，指定一个元素默认为全局事件
            data: {},//回调的参数
            onMoveCheck: [],//移动到触发元素上回调，例如移动上触发元素显示特殊的鼠标
            onDragCheck: [],//拖动前检查,检查是否启动拖动，对拖动区域，拖动对象进行判断
            onDragBefore: [],//拖动前回调，例如创建影子，创建拖动框
            onDragOn: [],//拖动中的回调，
            onDragBeforeEnd: [],//结束之前的回调
            onDragEnd: []//结束的回调
            // delay: 0,//延时，
            //scroll: true,考虑拖动时候关联滚动条，以后完善
        };
        var that = {};
        var args = andy.extend(defaults, options);

        //添加事件回调
        that.addEventCall = function (funcName, func) {
            if (args[funcName]) {
                args[funcName].push(func);
            }
        };
        //获取参数
        that.getData = function () {
            return args.data;
        };
        //销毁
        that.destroy = function () {
            drags.splice(args.dragIndex, 1);
            if (drags.length === 0) {
                unbindEvent();
            }
        };
        if (drags.length === 0) {
            bindEvent();
        }
        args.dragIndex = dragCount;
        dragCount++;
        drags.push(args);
        return that;
    };


    /*************一下为元素拖动**********************************************************************/
    //为指定元素绑定拖动属性 andyDrag=true;
    var bindDragEvent = function (el) {
        if (typeof el === "string") {
            //TODO 查找该元素
        }
        if (typeof el === "object") {
            el.setAttribute("andyDrag", "true");
        }
    };
    //通过page扫描来调用，这里传递字符串，还是dom呢？
    var scan = function () {
        //TODO 把元素上的data 属性都获取到
        //如果没有只是
        //创建对象
    };

    andy.dragElement = function (options) {
        var that, args, dragEl, copyEl, maxLeft, maxTop;
        var posCheck = function (argu, el) {
            el = el || andy.findOutElement(argu.event.target, args.dragName, "true", undefined, args.notDragEl);
            if (el) {
                //el.style.cursor = "move";
                return true;
            }
            return false;
        };
        var dragCheck = function (argu) {
            var el = andy.findOutElement(argu.event.target, args.dragName, "true", undefined, args.notDragEl);
            if (el && posCheck(argu, el)) {
                dragEl = el;
                return true;
            } else {
                dragEl = undefined;
                return false;
            }
        };
        var dragBefore = function (e) {
            if (args.dragHelp) {
                //TODO 创建拖动操作辅助框，
                //moveRect = window.top.$('<div dialogMoveRect="true" style="border: 1px dashed dodgerblue; position: absolute; "></div>');
                //moveRect.css("left", wMain.offset().left);
                //moveRect.css("top", wMain.offset().top);
                //moveRect.css("z-index", DIALOGZ + root.winIndex * 4 + 3);
                //moveRect.width(wMain.outerWidth());
                //moveRect.height(wMain.outerHeight());
            } else if (args.dragCopyEl) {
                //TODO 创建拷贝元素拖动
                copyEl = dragEl.cloneNode(true);
                copyEl.style.position = "absolute";
                copyEl.style.left = dragEl.offsetLeft + "px";
                copyEl.style.top = dragEl.offsetTop + "px";
                dragEl.parentNode.appendChild(copyEl);
            }
            return args.onDragBeforeElement(dragEl, e, copyEl) && true;
        };
        var dragOn = function (argu) {
            var fixPos, parentW, parentH, setPos, left, top;
            setPos = function (el) {
                left = el.offsetLeft - parseInt($(el).css("margin-left"), 10) + argu.offset.x;
                top = el.offsetTop - parseInt($(el).css("margin-top"), 10) + argu.offset.y;

                if (args.lockInParent) {
                    parentW = el.parentNode.offsetWidth;
                    parentH = el.parentNode.offsetHeight;
                    fixPos(el);
                }
                argu.left = left;
                argu.top = top;
                el.style.left = left + "px";
                el.style.top = top + "px";
            };

            fixPos = function (el) {
                var marginleft = parseInt($(el).css("margin-left"), 10),
                //marginright = parseInt($(el).css("margin-right"), 10),
                    margintop = parseInt($(el).css("margin-top"), 10);

                if (maxLeft === undefined) {
                    //为什么需要减两次
                    maxLeft = parentW - $(el).outerWidth() - marginleft - marginleft;
                }
                if (maxTop === undefined) {
                    //为什么需要减两次
                    maxTop = parentH - $(el).outerHeight() - margintop;
                }

                left = left < 0 ? 0 : left;
                left = left > maxLeft ? maxLeft : left;

                top = top < 0 ? 0 : top;
                top = top > maxTop ? maxTop : top;
            };

            if (args.dragSelf) {
                if (dragEl.getAttribute("lockx") === "true") {
                    argu.offset.y = 0;
                }
                if (dragEl.getAttribute("locky") === "true") {
                    argu.offset.x = 0;
                }
                setPos(dragEl);
            }
            //拖动拷贝元素
            if (copyEl) {
                setPos(copyEl);
            }

            args.onDragElement(dragEl, argu, copyEl);
            //TODO 区域判断
            //moveRect.css("left", moveRect.offset().left + offset.x * coe[0]);
            //moveRect.css("top", moveRect.offset().top + offset.y * coe[1]);
        };
        var dragEnd = function (argu) {
            //moveRect.remove();
            args.onDragEndElement(dragEl, argu, copyEl);
            if (copyEl && copyEl.parentNode) {
                copyEl.parentNode.removeChild(copyEl);
            }
            dragEl = undefined;
            maxLeft = undefined;
            maxTop = undefined;
            return false;
        };
        var defaults = {
            element: document.body,
            lockInParent: true,//如果有父元素，只能在父元素所在区域拖动
            dragSelf: true,//拖动自己
            dragHelp: false,//用辅助拖动框
            dragCopyEl: false,//拖动拷贝元素
            dragName: "andyDrag",
            //notDragEl:"",//被排除的元素
            "onDragCheck": [dragCheck],
            "onMoveCheck": [posCheck],
            "onDragBefore": [dragBefore],
            "onDragOn": [dragOn],
            "onDragEnd": [dragEnd],
            posCheck: function () {
            },
            onDragElement: function () {
                return true;
            },
            onDragEndElement: function () {
                return true;
            },
            onDragBeforeElement: function () {
                return true;
            }
        };
        args = andy.extend(defaults, options);
        that = andy.drag(args);
        return that;
    };

    andy.dragElement();
}(andy));
