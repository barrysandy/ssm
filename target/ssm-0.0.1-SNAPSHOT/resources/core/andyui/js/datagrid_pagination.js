/*datagrid_pagination */
/**
 * 分页数据列表组件
 * author:林耘宇
 **/

(function($){
	$.fn.extend({
		an_datagridPagination:function(){
			var options = {};
            var funstyle = "";
            for(var i= 0; i <arguments.length;i++){
                // console.log(arguments[0]);
                var a = arguments[0];
                if(typeof a == "object"){
                    options = a;
                }else if(typeof a == "string"){
                    funstyle = a;
                }
            };

            var _options = $.extend({
            	dpId:"",
            	url:"",
                ajaxType:"get",
            	onClickRow:function(){},//单击行事件
                onDblClickRow:function(){},//双击行事件
                onClickCell:function(){},//点击单元
                onDblClickCell:function(){}//双击单元
            }, options);

            var dpId=_options.dpId;
            if(dpId == ''){
                //没有传入id自己通过对象获取
                dpId = $(this).attr("id");
            }
            if(!dpId){
                dpId = "datagrid_"+andy.getRandom(1000);
                $(this).attr("id", dpId);
            }
            var dp = $("#" + dpId);
            var getOption = dp.attr("options");
            var getValueElement = "";
            if(getOption){
                 getOption = "{"+ getOption+"}";
                 getOption = andy.stringToJson(getOption);
                // 处理设置
                for(var name in getOption){
                    if(getOption[name] == "true"){
                        _options[name] = true;
                    }else if(getOption[name] == "false"){
                        _options[name] = false;
                    }else{
                        _options[name] = getOption[name];
                    }
                }
            }

            var loadListData;
            loadListData = "EVENT_LOAD_LIST";

            if(funstyle != ""){
            	if(funstyle == "loadListData"){
            		var url = arguments[1];
            		var isNewPagination = arguments[2];//默认刷新分页
            		if(isNewPagination != false){
            			isNewPagination = true;
            		}
            		var obj = {
            			url:url,
            			isNewPagination:isNewPagination
            		}
                    dp.trigger(loadListData, obj);
            	}
            }else{
				var table = dp.find("table");
				var pagination = dp.find(".m-pagebar");
				var paginationId = pagination.attr("id");
				var initPagination = function(){
					if(pagination[0]){
						var templateStr = "<div class='f-left'> 每页显示"+
			              "<select class='u-select xs auto' page-select>"+
			                "<option>10</option>"+
			                "<option>20</option>"+
			                "<option>30</option>"+
			                "<option>40</option>"+
			                "<option>50</option>"+
			              "</select>"+
			              "<button class='u-btn xs texture' page-fist><i class='iconfont'>&#xe60e;</i></button>"+
			              "<button class='u-btn xs texture' page-prev><i class='iconfont'>&#xe626;</i></button>"+
			              "第<input type='text' style='width:50px' value='1' class='u-input xs auto' page-index>共<a page-counts>10</a>页"+
			              "<button class='u-btn xs texture'><i class='iconfont' page-next>&#xe60f;</i></button>"+
			              "<button class='u-btn xs texture'><i class='iconfont' page-last>&#xe60d;</i></button>"+
				            "</div><div class='f-right' page-record>共2000记录</div>";

				        pagination.append(templateStr);
					}
				}

                var goTable = function(data){
                    var tableOption = $.extend(true, _options, {
                        bodyUrl:data
                    })
                    table.an_datagrid(tableOption)

                    if(data['total'] && pagination.hasClass("promty") == false){
                        var obj = {};
                        var pageObject = {};
                        obj = $.extend(true, data, {
                            onSelectPage:function(index, pageCounts){
                                // console.log(_options.url+"?pageNumber="+index+"&pageSize="+pageCounts)
                                loadTable(_options.url+"?pageNumber="+index+"&pageSize="+pageCounts)
                                // console.log("当前页:"+index+",显示条数:"+pageCounts);
                            }
                        })
                        pagination.empty();
                        initPagination()
                        pagination.an_pagination(obj);
                        pagination.addClass("promty");
                    }
                    
                }

				var loadTable = function(url){
                    table.find("tbody").empty();
                    
					if(_options.ajaxType == "get"){
                        andy.loaddata(url, function(data){
                            goTable(data);
                        })
                    }else if(_options.ajaxType == "post"){
                        andy.postdata(url, function(data){
                            goTable(data);
                        })
                    }
				}
                
                if(_options.url != ""){
                	loadTable(_options.url)
                }

                dp.bind(loadListData, function(e, obj){
                	// console.log("url")
                	if(obj.isNewPagination){
                		pagination.removeClass("promty");
                	}
                	loadTable(obj.url)
                })
            }
		}
	})
})($);
