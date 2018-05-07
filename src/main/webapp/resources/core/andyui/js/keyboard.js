/*global andy, $*/
/**
 * Created by chen_muse on 2016/6/14.
 * 键盘事件存在特殊性，无法直接绑定在div等dom元素上，故直接绑定在document上
 *
 *
 */

(function (andy, $) {
    //String.fromCharCode 可接受一个指定的 Unicode 值，然后返回一个字符串,适用于字母数字符号
    //stringObject.charCodeAt(index) 返回指定位置的字符的 Unicode 编码
    //toLowerCase()
    var
    //CODE_MSG_MAPPING,
    //MSG_CODE_MAPPING,
        msg2CodeFun = function (code2Msg) {
            var code, msg2Code = {};
            for (code in code2Msg) {
                if (code2Msg.hasOwnProperty(code)) {
                    msg2Code[code2Msg[code]] = parseInt(code, 10);
                }
            }
            return msg2Code;
        };

    //keyUpHandler = function (e, doc) {
    //    var args, activeElement;
    //
    //    if (!module.CODE_MSG_MAPPING[e.keyCode]) {
    //        return;
    //    }
    //    //输入框被激活
    //    activeElement = doc.activeElement;
    //    if (activeElement && activeElement.localName === "input" && (activeElement.type === "text" || activeElement.type === "password")) {
    //        return;
    //    }
    //    if (activeElement && activeElement.localName === "textarea") {
    //        return;
    //    }
    //    if (activeElement && activeElement.localName === "div" && activeElement.contentEditable === "true") {
    //        return;
    //    }
    //    args = {
    //        "event": e,
    //        "keyCode": e.keyCode,
    //        "keyMsg": module.CODE_MSG_MAPPING[e.keyCode]
    //    };
    //    e.preventDefault();
    //},
    //keyDownHandler = function (e, doc) {
    //    var args, activeElement;
    //
    //    if (!module.CODE_MSG_MAPPING[e.keyCode]) {
    //        return;
    //    }
    //    //输入框被激活
    //    activeElement = doc.activeElement;
    //    if (activeElement && activeElement.localName === "input" && (activeElement.type === "text" || activeElement.type === "password")) {
    //        return;
    //    }
    //    if (activeElement && activeElement.localName === "textarea") {
    //        return;
    //    }
    //    if (activeElement && activeElement.localName === "div" && activeElement.contentEditable === "true") {
    //        return;
    //    }
    //    args = {
    //        "event": e,
    //        "keyCode": e.keyCode,
    //        "keyMsg": module.CODE_MSG_MAPPING[e.keyCode]
    //    };
    //
    //    e.preventDefault();
    //};


    //key_code 与 key名称的映射关系
    andy.CODE_MSG_MAPPING = {
        "8": "BackSpace",
        "9": "Tab",
        "12": "Clear",
        "13": "Enter",
        "16": "Shift",
        "17": "Ctrl",
        "18": "Alt",
        "19": "Pause",
        "20": "Caps_Lock",
        "27": "Esc",
        "32": "Space",
        "33": "PageUp",
        "34": "PageDown",
        "35": "End",
        "36": "Home",
        "37": "Left",
        "38": "Up",
        "39": "Right",
        "40": "Down",
        "41": "Select",
        "42": "Print",
        "43": "Execute",
        "45": "Insert",
        "46": "Delete",
        "47": "Help",
        "48": "0",
        "49": "1",
        "50": "2",
        "51": "3",
        "52": "4",
        "53": "5",
        "54": "6",
        "55": "7",
        "56": "8",
        "57": "9",
        "65": "A",
        "66": "B",
        "67": "C",
        "68": "D",
        "69": "E",
        "70": "F",
        "71": "G",
        "72": "H",
        "73": "I",
        "74": "J",
        "75": "K",
        "76": "L",
        "77": "M",
        "78": "N",
        "79": "O",
        "80": "P",
        "81": "Q",
        "82": "R",
        "83": "S",
        "84": "T",
        "85": "U",
        "86": "V",
        "87": "W",
        "88": "X",
        "89": "Y",
        "90": "Z",
        "91": "Win",
        "92": "Win",
        "96": "KP_0",
        "97": "KP_1",
        "98": "KP_2",
        "99": "KP_3",
        "100": "KP_4",
        "101": "KP_5",
        "102": "KP_6",
        "103": "KP_7",
        "104": "KP_8",
        "105": "KP_9",
        "106": "*",
        "107": "+",
        "109": "-",
        "110": ".",
        "111": "/",
        "112": "F1",
        "113": "F2",
        "114": "F3",
        "115": "F4",
        "116": "F5",
        "117": "F6",
        "118": "F7",
        "119": "F8",
        "120": "F9",
        "121": "F10",
        "122": "F11",
        "123": "F12",
        "144": "Num_Lock",
        "145": "Scroll_Lock",
        "187": "+",
        "186": ";",
        "188": ",",
        "189": "-",
        "190": ".",
        "191": "/",
        "192": "`",
        "219": "[",
        "220": "\\",
        "221": "]",
        "222": "'"
    };
    //key名称 与 key_code的映射关系
    andy.MSG_CODE_MAPPING = msg2CodeFun(andy.CODE_MSG_MAPPING);


    var keydownEvents = {};
    var keyupEvents = {};


    /**
     * 键盘按下
     * @param event
     */
    var doKeydown = function (event) {
        var keyMsg, e = event || window.event;
        //esc和回车 输入框失去焦点
        // || e.target.localName === "textarea"
        if ((e.target.localName === "input") && e && (e.keyCode == 27 || e.keyCode == 13)) {
            event.target.blur();
            return;
        }
        keyMsg = andy.CODE_MSG_MAPPING[e.keyCode];
        if (keyMsg) {
            keyMsg = keyMsg.toLowerCase();
            if (keydownEvents[keyMsg] && keydownEvents[keyMsg].length > 0) {
                keydownEvents[keyMsg].pop()(e);
                return;
            }
        }


        //e.preventDefault();
    };
    /**
     * 键盘弹起
     * 一些统一的处理，输入框，文本框等在回车以后失去焦点
     * @param event
     */
    var doKeyup = function (event) {
        var e = event || window.event;
        var keyMsg = andy.CODE_MSG_MAPPING[e.keyCode];
        if (keyMsg) {
            keyMsg = keyMsg.toLowerCase();
            if (keyupEvents[keyMsg] && keyupEvents[keyMsg].length > 0) {
                keyupEvents[keyMsg].pop()(e);
                return;
            }
        }
        //e.preventDefault();

    };
    /**
     * 增加键盘事件
     * document 事件是一个数组，通过 pop来执行
     * el元素 的事件就直接绑定
     * @param el 默认是 document
     * @param keycode 键盘值 'enter'
     * @param fun 函数
     * @param type "keyup","keydown" 默认是keyup
     */
    var addKeyEvent = function (el, keycode, type, fun) {
        type = type || "keyup";
        keycode = keycode.toLowerCase();
        if (el) {
            $(el)[type](function (event) {
                if (event.keyCode == keycode) {
                    fun(event);
                }
            });
        } else {
            if (type === "keyup") {
                keyupEvents[keycode] = keyupEvents[keycode] || [];
                keyupEvents[keycode].push(fun);
            } else if (type === "keydown") {
                keydownEvents[keycode] = keydownEvents[keycode] || [];
                keydownEvents[keycode].push(fun);
            }
        }

    };


    /**
     * 移除事件
     * @param el 事件对象
     * @param type 类型
     */
    var removeKeyEvent = function (el, type) {
        if (el) {
            $(el).off(type || "keydown");
        }
    };

    andy.addKeyEvent = addKeyEvent;
    andy.removeKeyEvent = removeKeyEvent;

    $(document).keydown(doKeydown);
    $(document).keyup(doKeyup);

}(andy, $));

