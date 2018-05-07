/**
 * jQuery Calendar Plugin
 */
(function($, window) {

    'use strict';
    $.fn.calendar = function(options) {
        //check is select, if nothing select, return this
        if (!this.length) {
            if (options && options.debug && window.console) {
                console.log("nothing select");
            }
            return this;
        }
        var self = $(this);

        // default parameter setting
        var defaults = {
            cssPath: '', //user-define loading path of css file
            eventName: 'click', //user-define the event name that triggers the control
            onSelectDate: null, //callback function after select date
            autoClose: false
        };

        //inherit user-defined parameter
        defaults = $.extend(defaults, options);

        //default as data of the day
        var d_date = new Date();
        var _date = {
            year: d_date.getFullYear(),
            month: d_date.getMonth() + 1,
            day: d_date.getDate(),
            week: d_date.getDay()
        };

        //default template of plugin
        var calendarDiv = '<div id="calendar" class="cld_grid" style="display:none;z-index:100;">';
        calendarDiv += '<div id="calendar_year_month" class="cld_year_month" style="position:relative;">';
        calendarDiv += '<div id="last-year" style="position:absolute;left:30px;"><</div>';
        calendarDiv += '<div id="last-month" style="position:absolute;left:60px;"><<</div>';
        calendarDiv += '<div id="next-year" style="position:absolute;right:30px;">></div>';
        calendarDiv += '<div id="next-month" style="position:absolute;right:60px;">>></div>';
        calendarDiv += '<input style="width:40px;text-align:center;" type="text" id="calendar_year" value="' + _date.year + '">-<input style="width:25px;text-align:center;" type="text" id="calendar_month" value="' + _date.month + '"></div>'
        calendarDiv += '<div id="calendar_week_box" class="cld_week_box clearfix">';
        calendarDiv += '<div class="header-day" data-index = "0">日</div>';
        calendarDiv += '<div class="header-day" data-index = "1">一</div>';
        calendarDiv += '<div class="header-day" data-index = "2">二</div>';
        calendarDiv += '<div class="header-day" data-index = "3">三</div>';
        calendarDiv += '<div class="header-day" data-index = "4">四</div>';
        calendarDiv += '<div class="header-day" data-index = "5">五</div>';
        calendarDiv += '<div class="header-day" data-index = "6">六</div>';
        calendarDiv += '</div>';
        calendarDiv += '<div class="days clearfix">';
        for (var k = 0; k < 35; k++) {
            calendarDiv += '<div class="day"><span class="day-number">' + '' + '</span></div>';
        }
        calendarDiv += '</div></div>';

        var calendarAction = {
            //initialization
            initAction: function() {
                calendarAction.thisClick();
                calendarAction.inputChange();
                calendarAction.buttonChange();
                calendarAction.chooseDate();
            },

            //click to display
            thisClick: function() {
                self.bind(defaults.eventName, function(e) {
                    calendarAction.showCalendar();
                });
            },

            //when year and month in the input box changes
            inputChange: function() {
                $('#calendar_year, #calendar_month').bind('change', function() {
                    var year = $('#calendar_year').val(),
                        month = $('#calendar_month').val();
                    if (!/^\d{4}$/.test(year)) {
                        alert('please input four-digit year');
                        return false;
                    }
                    if (!/^\d{1,2}$/.test(month) || (month > 12 || month <= 0)) {
                        alert('please input proper month');
                        return false;
                    }
                    //Initialize after year and month changes
                    init_day_numbers(year, month);
                });
            },

            //click to change year and month
            buttonChange: function() {
                $('#last-year').bind('click', function() {
                    var lastYear = parseInt($('#calendar_year').val()) - 1 > 0 ? parseInt($('#calendar_year').val()) - 1 : 1;
                    $('#calendar_year').val(lastYear);
                    init_day_numbers(lastYear, $('#calendar_month').val());
                });

                $('#last-month').bind('click', function() {
                    var lastMonth = parseInt($('#calendar_month').val()) - 1 > 0 ? parseInt($('#calendar_month').val()) - 1 : 12,
                        thisYear = lastMonth == 12 ? parseInt($('#calendar_year').val()) - 1 : $('#calendar_year').val();
                    $('#calendar_month').val(lastMonth);
                    $('#calendar_year').val(thisYear);
                    init_day_numbers(thisYear, lastMonth);
                });

                $('#next-year').bind('click', function() {
                    var nextYear = parseInt($('#calendar_year').val()) + 1;
                    $('#calendar_year').val(nextYear);
                    init_day_numbers(nextYear, $('#calendar_month').val());
                });

                $('#next-month').bind('click', function() {
                    var nextMonth = parseInt($('#calendar_month').val()) + 1 <= 12 ? parseInt($('#calendar_month').val()) + 1 : 1,
                        thisYear = nextMonth == 1 ? parseInt($('#calendar_year').val()) + 1 : $('#calendar_year').val();
                    $('#calendar_month').val(nextMonth);
                    $('#calendar_year').val(thisYear);
                    init_day_numbers(thisYear, nextMonth);
                });
            },

            //set the relative offset between control and webpage, then display
            showCalendar: function() {
                var o_x = self.offset().left,
                    o_y = self.offset().top + self[0].offsetHeight;
                $('#calendar').css({
                    'position': 'absolute',
                    'left': o_x,
                    'top': o_y,
                    'display': 'block'
                });
            },

            //close plugin
            closeCalendar: function() {
                $('#calendar').hide();
            },

            //select date
            chooseDate: function() {
                $('.day').live('click', function() {
                    var _date = $(this).attr('data-date');
                    //trigger callback function
                    if ($.isFunction(defaults.onSelectDate)) {
                        defaults.onSelectDate.call(this, _date);
                    }
                    if (defaults.autoClose) {
                        calendarAction.closeCalendar();
                    }
                });
            }
        };

        //write calendar template in webpage
        $('body').append(calendarDiv);

        //initialize the year, month, day of calendar list
        init_day_numbers(_date.year, _date.month);

        //initialize plugin action
        calendarAction.initAction();

        function init_day_numbers(year, month) {
            var maxday = getmaxDay(year, month),
                firstWeek,beforeNumber,
                startIndex = beforeNumber = firstWeek = getFirstWeek(year, month),
                afterNumber = 35 - maxday - beforeNumber,
                lastMonthMaxDay = getMaxDayByLastMonth(year, month),
                nextMonthMaxDay = getMaxDayByNextMonth(year, month),
                Edge = getLastAndNextDate(year, month);
            var default_html = '';
            for (var k = 0; k < 35; k++) {
                default_html += '<div class="day"><span class="day-number">' + '' + '</span></div>';
            };
            $('.days').html(default_html);
            for (var s = 1; s <= maxday; s++) {
                $('.day-number').eq(startIndex).text(s);
                $('.day').eq(startIndex).attr('data-date', year + '-' + month + '-' + s);
                if (s == _date.day && year == _date.year && month == _date.month) {
                    $('.day').eq(startIndex).addClass('today');
                }
                startIndex++;
            };
            for (var k = 0; k < beforeNumber; k++) {
                var realDay = lastMonthMaxDay - beforeNumber + k + 1;
                $('.day-number').eq(k).text(realDay);
                $('.day-number').eq(k).addClass('last-month');
                $('.day').eq(k).attr('data-date', Edge.lastY + '-' + Edge.lastM + '-' + realDay);
            };
            for (var l = 1; l <= afterNumber; l++) {
                var _index = beforeNumber + maxday + (l - 1);
                $('.day-number').eq(_index).text(l);
                $('.day-number').eq(_index).addClass('next-month');
                $('.day').eq(_index).attr('data-date', Edge.nextY + '-' + Edge.nextM + '-' + l);
            }
        }

        function getLastAndNextDate(year, month) {
            var lastM = month - 1 > 0 ? month - 1 : 12,
                lastY = lastM == 12 ? year - 1 : year,
                nextM = month + 1 > 12 ? 1 : month + 1,
                nextY = nextM == 1 ? year + 1 : year;
            return {
                lastM: lastM,
                lastY: lastY,
                nextM: nextM,
                nextY: nextY
            };
        }

        //obtain what the first day of one month is in a week
        function getFirstWeek(year, month) {
            var date = new Date(year, month - 1, 1);
            return date.getDay();
        }

        //obtain the year, month, day of today
        function getToday() {
            var date = new Date();
            return today = [date.getFullYear(), date.getMonth() + 1, date.getDate(), date.getDay()];
        }

        //obtain the number of days for a month
        function getmaxDay(year, month) {
            var date = new Date(year, month, 0);
            return date.getDate();
        }

        //obtain the number of days for last month
        function getMaxDayByLastMonth(year, month) {
            month = month - 1 <= 0 ? 12 : month - 1;
            year = month == 12 ? year - 1 : year;
            return getmaxDay(year, month);
        }

        //obtain the number of days for next month
        function getMaxDayByNextMonth(year, month) {
            month = month + 1 > 12 ? 1 : month + 1;
            year = month == 1 ? year + 1 : year;
            return getmaxDay(year, month);
        }

        //obtain the days list of a certain year and month, return array
        function getDayList(year, month) {
            var _list = [],
                maxday = getmaxDay(year, month),
                month = month - 1;
            for (var i = 1; i <= maxday; i++) {
                var d = new Date(year, month, i);
                _list[i - 1] = d.getDay();
            }
            return _list;
        }
    }
})(jQuery, window);
