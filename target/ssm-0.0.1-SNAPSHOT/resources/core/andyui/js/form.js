/*global  window , an_IE,document,console */
/**
 * 表单模块  表单js交互，验证，响应样式，tooltip
 * author:chenxiaoying
 *
 * validateInKeyup 鼠标弹起的时候验证，默认是失去焦点验证
 *
 * 结构 <input type="text" class="u-input err" value="错误样式“err”">
 * <input type="text" class="u-input cor" value="正确样式“cor”">
 * <input type="text" class="u-input xs" value="MINI尺寸“xs”">
 * <input type="text" class="u-input" disabled value="禁用样式"><!--在IE8以下，禁用样式会有丢失-->
 * verify="required,phone"
 * 验证顺序和先后顺序有关，先验证required，再验证phone
 **/

(function (andy,$) {
    var customIndex = 1;
    // 日期校验 日期控件的话就不需要
    // isCorrectDate("2015-2-31") false isCorrectDate("2015-2-21") true
    var isCorrectDate = function (value) {
        if (value === "") {
            return;
        }
        if (typeof value === "string") { //是字符串但不能是空字符
            var arr = value.split("-"); //可以被-切成3份，并且第1个是4个字符
            if (arr.length === 3 && arr[0].length === 4) {
                var year = ~~arr[0];//全部转换为非负整数
                var month = ~~arr[1] - 1;
                var date = ~~arr[2];
                var d = new Date(year, month, date);
                return d.getFullYear() === year && d.getMonth() === month && d.getDate() === date;
            }
        }
        return false;
    };
    //过滤非法字符
    var quote = function (str) {
        var items = new Array("~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "{", "}", "[", "]", "(", ")");
        items.push(":", ";", "'", "|", "\\", "<", ">", "?", "/", "<<", ">>", "||", "//");
        items.push("admin", "administrators", "administrator", "管理员", "系统管理员");
        items.push("select", "delete", "update", "insert", "create", "drop", "alter", "trancate");
        str = str.toLowerCase();
        for (var i = 0; i < items.length; i++) {
            if (str.indexOf(items[i]) > -1) {
                return false;
            }
        }
        return true;
    };
    //身份证
    var checkIdCard = function (num) {
        num = num.toUpperCase();
        var cityCode = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外 "
        };
        if (!cityCode[num.substr(0, 2)]) {
            //alert("地址编码错误");
            return false;
        }
        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
        if (!( /(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num) )) {
            //alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
            return false;
        }

        //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
        //下面分别分析出生日期和校验位
        var len, re;
        len = num.length;
        if (len == 15) {
            re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
            var arrSplit = num.match(re);
            //检查生日日期是否正确
            var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
            var bGoodDay;
            bGoodDay = ( dtmBirth.getYear() == Number(arrSplit[2]) ) && ( ( dtmBirth.getMonth() + 1 ) == Number(arrSplit[3]) ) && ( dtmBirth.getDate() == Number(arrSplit[4]) );
            if (!bGoodDay) {
                //alert('输入的身份证号里出生日期不对！');
                return false;
            } else {
                //将15位身份证转成18位
                //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
                var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                var nTemp = 0,
                    k;
                num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
                for (k = 0; k < 17; k++) {
                    nTemp += num.substr(k, 1) * arrInt[k];
                }
                num += arrCh[nTemp % 11];
                return true;
            }
        }
        if (len == 18) {
            re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
            var arrSplit = num.match(re);
            //检查生日日期是否正确
            var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
            var bGoodDay;
            bGoodDay = ( dtmBirth.getFullYear() == Number(arrSplit[2]) ) && ( ( dtmBirth.getMonth() + 1 ) == Number(arrSplit[3]) ) && ( dtmBirth.getDate() == Number(arrSplit[4]) );
            if (!bGoodDay) {
                //alert(dtmBirth.getYear());
                //alert(arrSplit[2]);
                //alert('输入的身份证号里出生日期不对！');
                return false;
            } else {
                //检验18位身份证的校验码是否正确。
                //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
                var valnum;
                var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                var nTemp = 0,
                    k;
                for (k = 0; k < 17; k++) {
                    nTemp += num.substr(k, 1) * arrInt[k];
                }
                valnum = arrCh[nTemp % 11];
                if (valnum != num.substr(17, 1)) {
                    //alert('18位身份证的校验码不正确！应该为：' + valnum);
                    return false;
                }
                return true;
            }
        }
        return false;
    };
    //验证规则
    var VERIFY = {
        trim: {
            check: function (e) {//格式化
                //if (data.element.type !== "password") {
                //    value = String(value || "").trim()
                //}
                e.value = e.value.replace(/(^\s*)|(\s*$)/g, "");
                return true;
            }
        },
        required: {
            message: '不能为空',
            placeholder: "请输入文字...",
            check: function (e) {
                return e.value !== "" && e.value !== VERIFY.required.placeholder;
            }
        },
        "int": {
            message: "不是整数，请输入整数",
            placeholder: "请输入整数",
            check: function (e) {
                return /^\-?\d+$/.test(e.value);
            }
        },
        mobile: {
            message: "手机号码不合法",
            placeholder: "请输入手机号",
            check: function (e) {
                return /^(13[0-9]|14[57]|15[012356789]|17[0678]|18[0-9])\d{8}$/.test(e.value);
            }
        },
        phone: {
            message: "座机号码不合法",
            placeholder: "请输入座机号",
            check: function (e) {
                var phoneRegWithArea = /^[0][1-9]{2,3}-[0-9]{5,10}$/;
                var phoneRegNoArea = /^[1-9]{1}[0-9]{5,8}$/;
                if (e.value.length > 9) {
                    return phoneRegWithArea.test(e.value);
                }
                return phoneRegNoArea.test(e.value);
            }
        },
        mobile_phone: {
            message: "输入号码不合法",
            placeholder: "请输入手机或者座机号",
            check: function (e) {
                return VERIFY.phone.check(e) || VERIFY.mobile.check(e);
            }
        },
        decimal: {
            message: '不是小数，请输入小数',
            placeholder: '请输入小数',
            check: function (e) {
                return /^\-?\d*\.?\d+$/.test(e.value)
            }
        },
        alpha: {
            message: '包含字母以外的非法字符',
            placeholder: '请输入字母',
            check: function (e) {
                return /^[a-z]+$/i.test(e.value);
            }
        },
        alpha_numeric: {
            message: '包含字母或数字以外的非法字符',
            placeholder: '请输入字母或数字',
            check: function (e) {
                return /^[a-z0-9]+$/i.test(e.value)
            }
        },
        alpha_dash: {
            message: '包含字母或数字及下划线以外的非法字符',
            placeholder: '请输入字母或数字及下划线等字符',
            check: function (e) {
                return /^[a-z0-9_\-]+$/i.test(e.value);
            }
        },
        chs: {
            message: '请输入中文字符',
            placeholder: '请输入中文字符',
            check: function (e) {
                return /^[\u4e00-\u9fa5]+$/.test(e.value);
            }
        },
        chs_numeric: {
            message: '请输入中文字符或数字及下划线等特殊字符',
            placeholder: '请输入中文字符或数字及下划线等特殊字符',
            check: function (e) {
                return /^[\\u4E00-\\u9FFF0-9_\-]+$/i.test(e.value);
            }
        },
        qq: {
            message: "腾讯QQ号从10000开始",
            placeholder: "请输入QQ号",
            check: function (e) {
                return /^[1-9]\d{4,10}$/.test(e.value);
            }
        },
        postoffice: {
            message: "邮编格式错误",
            placeholder: "请输入邮编",
            check: function (e) {
                return /^[1-9][0-9]{5}$/.test(e.value);
            }
        },
        idcard: {
            message: "身份证格式错误",
            placeholder: "请输入身份证号",
            check: function (e) {
                return checkIdCard(e.value);
            }
        },
        ipv4: {
            message: "ip地址不正确",
            placeholder: "请输入ip地址",
            check: function (e) {
                //"(([0-9]{1,3}.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                var ripv4 = /^(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)$/i;
                return ripv4.test(e.value);
            }
        },
        email: {
            message: "邮箱地址错误",
            placeholder: "请输入邮箱",
            check: function (e) {
                return /^([A-Z0-9]+[_|\_|\.]?)*[A-Z0-9]+@([A-Z0-9]+[_|\_|\.]?)*[A-Z0-9]+\.[A-Z]{2,3}$/i.test(e.value);
            }
        },
        url: {
            message: "URL格式错误",
            placeholder: "请输入链接",
            check: function (e) {
                return /^(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/.test(e.value);
            }
        },
        //特殊字符
        quote: {
            message: "包含非法字符",
            placeholder: "请输入...",
            check: function (e) {
                return quote(e.value);
            }
        },
        repeat: {
            message: "密码输入不一致",
            check: function (e) {
                var otherId = e.getAttribute("repeat");
                if (otherId) {
                    var otherEl = e.ownerDocument.getElementById(otherId);
                }
                if (!otherEl) {
                    return false;
                }
                return e.value === otherEl.value;
            }
        },
        "norepeat": {
            message: "输入重复",
            check: function (e) {
                var result = true, i, otherEl, norepeatName = e.getAttribute("norepeat");
                if (norepeatName && VERIFY.required.check(e)) {
                    //这里用了jquery选择器
                    var otherEls = $('[norepeat="' + norepeatName + '"]');
                    if (otherEls.length > 0) {
                        for (i = 0; i < otherEls.length; i++) {
                            otherEl = otherEls[i];
                            if (e !== otherEl && VERIFY.required.check(otherEl) && e.value === otherEl.value) {
                                result = false;
                                break;
                            }
                        }
                    }
                }
                return result;
            }
        },
        date: {
            message: '必须符合日期格式 YYYY-MM-DD',
            placeholder: "请输入日期 例如：2016-3-2",
            check: function (e) {
                return isCorrectDate(e.value);
            }
        },
        passport: {
            message: '护照格式错误或过长',
            placeholder: "请输入护照号",
            check: function (e) {
                return /^[a-zA-Z0-9]{4,20}$/i.test(e.value);
            }
        },
        minlength: {
            message: '最少输入{{minlength}}个字',
            placeholder: "请输入{{minlength}}个以上字符",
            check: function (e) {
                var elem = e;
                var num = parseInt(elem.getAttribute("minlength"), 10);
                return elem.value.length >= num;
            }
        },
        maxlength: {
            message: '最多输入{{maxlength}}个字',
            placeholder: "请输入{{maxlength}}个以下字符",
            check: function (e) {
                var elem = e;
                var num = parseInt(elem.getAttribute("maxlength"), 10);
                return elem.value.length <= num;
            }
        },
        greaterthan: {
            message: '请输入大于{{greaterthan}}的数字',
            placeholder: '请输入大于{{greaterthan}}的数字',
            check: function (e) {
                var elem = e;
                var num = parseInt(elem.getAttribute("greaterthan"), 10);
                var v = parseFloat(elem.value);
                return isNaN(v) ?false : v > num ;
            }
        },
        lessthan: {
            message: '请输入小于{{lessthan}}的数字',
            placeholder: '请输入小于{{lessthan}}的数字',
            check: function (e) {
                var elem = e;
                var num = parseInt(elem.getAttribute("lessthan"), 10);
                var v = parseFloat(elem.value);
                return isNaN(v) ? false: v < num ;
            }
        },
        contains: {
            message: "必须包含{{contains}}中的一个",
            placeholder: "必须包含{{contains}}中的一个",
            check: function (e) {
                var val = e.value;
                var vmValue = [].concat(val).map(String);
                var domValue = (e.getAttribute("contains") || "").split(",");
                var has = false;
                for (var i = 0, n = vmValue.length; i < n; i++) {
                    var v = vmValue[i]
                    if (domValue.indexOf(v) >= 0) {
                        has = true;
                        break;
                    }
                }
                return has;
            }
        },
        contain: {//还有问题
            message: "必须包含{{contain}}",
            placeholder: '请输入包含{{contain}}这样格式的字符',
            check: function (e) {
                var val = e.value;
                var vmValue = [].concat(val).map(String);
                var domValue = (e.getAttribute("contain") || "").split(",")
                if (!vmValue.length) {
                    var has = false
                } else {
                    has = true
                    for (var i = 0, n = domValue.length; i < n; i++) {
                        var v = domValue[i]
                        if (vmValue.indexOf(v) === -1) {
                            has = false
                            break
                        }
                    }
                }
                return has;
            }
        },
        pattern: {
            message: '必须匹配/{{pattern}}/这样的格式',
            placeholder: '请输入匹配/{{pattern}}/这样格式的字符',
            check: function (e) {
                var elem = e;
                var pattern = elem.getAttribute("pattern");
                var re = new RegExp('^(?:' + pattern + ')$');
                return re.test(elem.value);
            }
        }
    };

    //在IE8下的input默认提示值
    var placeholder4IE = function (element) {
        var $el = $(element), holder;
        var defval = $el.attr('placeholder');
        if(!defval){
            return;
        }
        $el.parent().append("<div class='ie-placeholder place'>" + defval + "</div>");
        holder = $el.parent().find(".ie-placeholder");
        if ($el[0].value == '') {
            holder.show();
        }else{
            holder.hide();
        }
        holder.click(function (event) {//获得焦点时
            $(this).hide();
            var inpt = $(this).parent().find("[placeholder]");
                inpt[0].click();
                inpt[0].focus();
        });
        $el.blur(function () {//失去焦点时
            if ($(this).val() == '') {
                $(this).parent().find(".ie-placeholder").show();
            }
        });
        $el.focus(function () {//获得焦点时
            $(this).parent().find(".ie-placeholder").hide();
        });

    };
    /**
     * 验证正确
     * @param el
     */
    var setCorrect = function (el) {
        $(el).addClass("cor");
    };
    var setError = function (el) {
        $(el).addClass("err");
        setTimeout(function(){
            $(el).addClass("err1");
        //不支持颜色过度
        //$(el).animate({"border-color":'#D7D7D7', color:'#333', "background-color":'#fff'},3000);
        },3000)
    };
    //重置
    var reSet = function (el) {
        $(el).removeClass("err cor err1");
    };
    /**
     * /验证函数
     * @param el 输入框
     * @param toolTip 提示组件对象
     * @returns {boolean} 是否通过验证
     */
    var check = function (el) {
        var toolTip, iden = true, cIden, i, tipStr = "", rules = el.getAttribute("verify");
        if (!rules) {
            return true;
        }
        if ($(el).is(":disabled")) {
            return true;
        }
        if (el.value === "" && rules && rules.indexOf("required") < 0) {
            return true;
        }
        rules = rules.split(",");
        for (i = 0; i < rules.length; i++) {
            cIden = VERIFY[rules[i]].check(el);
            if (cIden === false) {
                if (el.getAttribute("message")) {
                    tipStr = el.getAttribute("message");
                } else {
                    tipStr = VERIFY[rules[i]].message;
                    if (tipStr.indexOf(rules[i]) > -1) {
                        tipStr = tipStr.replace("{{" + rules[i] + "}}", el.getAttribute(rules[i]));
                    }
                }
                toolTip = $(el).next('.infobox');

                andy.changeTooltip(el, tipStr);
                andy.showTooltip(el,true);
                setError(el);
                return cIden;
            }
            iden = iden && cIden;
        }
        if (iden) {
            reSet(el);
            andy.hideTooltip(el);
            setCorrect(el);
        } else {
            setError(el);
        }
        return iden;
    };
    var bindVerify = function (element, checkFun) {
        if (element.getAttribute("tipPos")) {
            andy.tooltip($(element), element.getAttribute("tipPos"), $(element).attr('title'), 'terr');
        } else {
            andy.tooltip($(element), 'bottom', $(element).attr('title'), 'terr');
        }
        var eFun = function (event) {
            return checkFun ? checkFun(event.target) : check(event.target);
        };
        //输入中判断，ps有些判断支持，有些不支持
        if (element.getAttribute("validateOnKeyup")) {
            $(element).keyup(eFun);
        } else if (element.getAttribute("validateOnblur")) {
            $(element).blur(eFun);
        } else {
            $(element).change(eFun);
        }
        $(element).unbind("focus");
        $(element).bind("focus", function (event) {
            reSet($(this));
            andy.hideTooltip(this);
        });
    };
    var fromInit = function () {
            $("[verify]").each(function (index, element) {
                    if (element.getAttribute("verify") === "") {
                        return;
                    }
                    if (element.parentNode.className.indexOf(".m-tooltip") < 0) {

                        var rules = element.getAttribute("verify").split(",");
                        bindVerify(element);
                        //如果是必填，在标题标注一个红色的*
                        if ((rules + "").indexOf("required") > -1) {
                            $(element).parents(".u-formitem").find("label").append("<span class='f-color-danger'>*</span>");
                        }
                        //element.parentNode.style.width = element.parentNode.parentNode.offsetWidth + "px";
                        // 这里计算了 验证表单父级宽度
                        // andy.inputLayout($(element).parent());
                        //andy.inputLayout($(element));

                        if (!element.getAttribute("placeholder")) {
                            var placeholder = VERIFY[rules[rules.length - 1]].placeholder;
                            if (placeholder) {
                                if (placeholder.indexOf("{{") > -1) {
                                    placeholder = placeholder.replace("{{" + rules[rules.length - 1] + "}}", element.getAttribute(rules[rules.length - 1]));
                                }
                                element.setAttribute("placeholder", placeholder);
                            }
                        }
                    }
                //判断ie
                if (andy.IE() <= 9 && andy.IE() !== 0) {
                    placeholder4IE(element);
                }
                }
            );

        }
        ;
//TODO 绑定自定义验证函数
//TODO 绑定后台ajax验证
//渲染，对IE8下的输入提示


    andy.fromInit = fromInit;
    /**
     * 绑定自定义验证函数
     * @param el input元素
     * @param options {massage:str check:function}
     */
    andy.addVerify = function (el, options) {
        var arg, checkName, def = {
            massage: "输入不合法",
            check: function () {
                return true;
            }
        };
        arg = $.extend(def, options);
        checkName = "custom" + customIndex;
        VERIFY[checkName] = arg;
        el.setAttribute("verify", el.getAttribute("verify") ? el.getAttribute("verify") + "," + checkName : checkName);
        bindVerify(el, arg.check);
        customIndex++;
    };
    /**
     * 绑定ajax验证
     * @param el input元素
     * @param options 增加一个message
     *
     * data如果需要自己组织，则 return 一个函数
     * 这里约定后台返回 success成功与否 msg错误消息
     *
     * 要处理异步的问题 还有和一般验证结果相与
     * 是否最后提交的时候也要执行
     */
    andy.ajaxVerify = function (el, options) {
        var arg, toolTip, defaults = {
            async: false,
            type: 'get',
            url: '',
            data: {},
            dataType: 'json',
            beforeSend: function () {
            },
            complete: function () {
            },
            //网络请求成功
            success: function () {
            },
            //网络错误
            error: function () {
            }
        };
        if (options) {
            arg = $.extend(defaults, options);
            arg.success = function (argu) {
                if (argu.success) {
                    setCorrect(el);
                } else {

                    if (tipStr) {
                        andy.changeTooltip(el, tipStr);
                        andy.showTooltip(el,true);

                        //andy.tooltip.setContent(toolTip, tipStr);
                        //andy.tooltip.showinfo(toolTip);
                    }
                    setError(el);
                }
                if (options.success) {
                    options.success(argu);
                }
            };
            arg.error = function (argu) {
                alert("error" + argu.toString());
            };
            bindVerify(el, function (e) {
                arg.data = options.data ? options.data() : e.value;
                $.ajax(arg);
            });
        }
    };
    andy.fromVerify = function (from) {
        var right = true;
        $(from).find("[verify]").each(function (index, element) {
            right = check(element) && right;
        });
        return right;
    };
    andy.inputCheck = check;

    andy.placeholder4IE = placeholder4IE;
    $(document).ready(function () {

        fromInit();
       
    });

})
(andy,jQuery);