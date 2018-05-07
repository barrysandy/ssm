/*uploadSWF */
/**
 * 上传控件 单文件 多文件上传
 * author:林耘宇
 **/
(function ($) {
    $.fn.extend({
    	an_uploadSWF:function(){
    		
    		var options = {};
            var funstyle = "";
            var pathName = document.location.pathname;
    	    var index = pathName.substr(1).indexOf("/");
    	    var result = pathName.substr(0,index+1);
            for(var i= 0; i <arguments.length;i++){
                var a = arguments[0];
                if(typeof a == "object"){
                    options = a;
                }else if(typeof a == "string"){
                    funstyle = a;
                }
            };
            var _options = $.extend({
            	entityId:"",
            	viewFlag:false,
                uploadUrl:"",//上载文件路径
                uploadTemporaryUrl:"",//多文件上传临时目录
                touchFile:"",//触发打开文件事件对象
                fileType:"",//允许文件类型 "Images:*.jpg;*.gif;*.png" 可后续增加mac系统文件类型
                uploadDataFieldName:"Filedata",//上传文件数据 数据头属性名
                //imgUrl:"/andyui/admin/img/",//图片资源地址
                //swfUrl:"/andyui/admin/swf/",//swf资源地址
            	swfUrl : result+"/static/core/andyui/swf/",
    			imgUrl : result+"/static/core/andyui/img/",
                swf:"uploadSWF.swf",//加载FLASH文件名
                xmlUrl:"",//xml文件路径配置
                isFile:false,//xml文件路径配置
                width:113,//flash宽度
                height:129,//flash高度
                files:false,//默认多文件为true
                dialog:true,//多文件上传是否出现对话框
                oneImgView:true,//单文件上传预览
                data:"",//需要同步POST的数据 只支持字符串类型
                icon:{
                	img:"img.jpg",//预览图标
                	s_img:"s_imgs.jpg",//小图标
                	file:"file_img.jpg", //预览文件图标
                	s_file:"s_file_img.jpg" //预览文件小图标
                },
                uploadComplete:function(){},//上传完成回调
                onSubmit:function(){}
            }, options);

            var upload = $(this);
            var upload_id = upload.attr("id");
            if(upload_id == false){
				upload_id = "upload" + andy.getRandom(999);
            }
            var touchTarget = upload.find("[touchTarget]");//点击对象 打开file的
            var preview = upload.find("[preview]");//显示对象
            var dialogId = upload_id+"_uploadDialog";
            var dialog = "";
        //    var isUploadListComplete = false;
          //  var isSuccess = false;

            // 私有事件
            var showFun = "SHOW_FUNCTION";//显示缩略图
            var progress = "UPLOAD_PROGRESS";//加载进度
            var completeData = "COMPLETE_DATA";//加载数据完成
            var uploadListComplete = "UPLOAD_LIST_COMPLETE";//多文件加载完毕
            var getCurrentData = "GET_CURRENT_DATA";//获取当前文件数据

            // 图片格式定义
            var imgStyle = [".jpg", ".png", ".bmp", ".gif", ".eps", ".tif", ".PNG"];
            var b_img = "<img src='"+_options.imgUrl+_options.icon.img+"'>";//预览大图
            var s_img = "<img src='"+_options.imgUrl+_options.icon.s_img+"'>";//预览小图
            var b_file = "<img src='"+_options.imgUrl+_options.icon.file+"'>";
            var s_file = "<img src='"+_options.imgUrl+_options.icon.s_file+"'>";
            var myloading;
            if(funstyle != ""){
				if(funstyle == "showFun"){
					var data = arguments[1];
					upload.trigger(showFun, data);
				}else
				if(funstyle == "progress"){
					var loaded = arguments[1];
					var total = arguments[2];
					var num = arguments[3];
					var target = arguments[4];
					var data = {
						loaded:loaded,
						total:total,
						num:num,
						target:target
					}
				//	upload.trigger(progress, data);
				}else
				if(funstyle == "completeData"){
					var data = arguments[1];
					upload.trigger(completeData, data);
				}else if(funstyle == "uploadListComplete"){
					var isOk = arguments[1];
					upload.trigger(uploadListComplete, isOk);
				}else if(funstyle == "getCurrentData"){
					var fun = arguments[1];
					upload.trigger(getCurrentData, fun);
				};
            }else{
            	// flash对象插入
            	var swfId = upload_id+"_flashvars";
            	var swf  = "<div style=' position:absolute; right:-1px; top:-1px; filter: alpha(opacity=0);-moz-opacity:0;-khtml-opacity:0;opacity:0;'>"+
		          "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width="+_options.width+" height="+_options.height+"  name='"+swfId+"' id='"+swfId+"' align='center'>"+
		            "<param name='wmode' value='transparent' />"+
		            "<param name='allowscriptAccess' value='always' />"+
		            "<param name='movie' value='"+_options.swfUrl +_options.swf+"' />"+
		            "<param name='FlashVars' value='uploadUrl="+_options.uploadUrl+"&uploadTemporaryUrl="+_options.uploadUrl+"&uploadId="+upload_id+"&files="+_options.files+"&data="+_options.data+"&uploadDataFieldName="+_options.uploadDataFieldName+"&xmlUrl="+_options.xmlUrl+"' />"+
		            "<embed src='"+_options.swfUrl +_options.swf+"' FlashVars='uploadUrl="+_options.uploadUrl+"&uploadTemporaryUrl="+_options.uploadUrl+"&uploadId="+upload_id+"&files="+_options.files+"&data="+_options.data+"&uploadDataFieldName="+_options.uploadDataFieldName+"&xmlUrl="+_options.xmlUrl+"&fileType="+_options.fileType+"' allowscriptAccess='always' quality='high' bgcolor='#ffffff' width="+_options.width+" height="+_options.height+" name='"+swfId+"' allowFullScreen='true' align='center' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />"+
		          "</object></div>";
		        // 对话框底部按钮
		        var dialog_bottom = "<div class='u-p-l-bottom u-upload-bot' style = 'width:100%;height:40px;background:#EEE;position:absolute;bottom:0px;left:0px;_top:385px;_left:5px;_z-index:10000;'>"+
								        "<div class='f-right f-p-xs'>"+
							          "<a href='javascript:;' class='u-button-default success submit' style='float:left; margin-left:10px;'>关闭</a>"+
							        "</div></div>";

			    // ul生成
		        var ul = "<ul class='u-upload-down u-upload-load f-hidden' showimg><ul>";
		        upload.append(ul);
		        var showImg = upload.find("[showimg]");

		        // 当前选中数据结构
		        var currentData = [];

		        // 插入script

		        var _showFun = upload_id+"_showFun";
		        var _completeDataFun = upload_id+"_completeData";
		        var _progressFun = upload_id+"_progress";
		        var _uploadListCompleteFun = upload_id+"_uploadListComplete";
		        var script = "<script>"+
			        // 添加显示对象图片方法
			        "function "+_showFun+"(data)"+
			        "{ $('#"+upload_id+"').an_uploadSWF('showFun', data)};"+
			        "window['"+_showFun+"'] = "+_showFun+";"+
			        "function "+_completeDataFun+"(data)"+
			        "{ $('#"+upload_id+"').an_uploadSWF('completeData', data)};"+
			        "window['"+_completeDataFun+"'] = "+_completeDataFun+";"+
			        "function "+_progressFun+"(loaded, total, num, target)"+
			        "{ $('#"+upload_id+"').an_uploadSWF('progress', loaded, total, num, target)};"+
			        "window['"+_progressFun+"'] = "+_progressFun+";"+
			        "function "+_uploadListCompleteFun+"(isOk)"+
			        "{ $('#"+upload_id+"').an_uploadSWF('uploadListComplete', isOk)};"+
			        ""
			        "window['"+_uploadListCompleteFun+"'] = "+_uploadListCompleteFun+";"+
		        "</script>";

		        // if(_options.files == false){
		        upload.after(script);
		        // }
		        var content = "<div class='f-p' style = 'height:325px;overflow-y:auto;'><ul class='u-upload-load'>";
		        if(!_options.viewFlag){
		        	content = content +"<li class='last'><img src='"+_options.imgUrl+"img1.jpg'></li>";
		        }
		        content = content + "</ul></div>"+dialog_bottom;
		        var openDialog = function(){
                	touchTarget.on("click", function(){
                		$(document).an_dialog({
                			id:dialogId,
                			locMyself:true,
                			onBeforeClose:function(){
                				// 关闭对话框验证
                				var lis = $(".u-upload-load").find("li").length-1;
//                				if(_options.viewFlag){
//                					lis = $(".u-upload-load").find("li").length;
//                				}
                				if(!_options.viewFlag){
	                				preview.attr("lang",lis);
	                				if(lis == 0){
	                					preview.html("暂无文件。");
			                    		isSuccess = true;
	                				}else{
	                					preview.html("有"+lis+"个文件。");
	                				}
                				}
                				_options.uploadComplete(currentData);//触发提交完成方法
								return true;
                			},
	                        title: "文件上传管理",
	                        html: content,
	                        width:675,
							height:430
	                    });
	                    dialog = $("#"+dialogId);
	                    if(currentData.length == 0){
	                    	var lis = FileUploadUtil.findFilesById(_options.entityId,currentData,_options.viewFlag);
	                    	dialog.find(".u-upload-load").prepend(lis);
	                    }else{
	                    	var lis = FileUploadUtil.findFilesById(_options.entityId,null,_options.viewFlag);
	                    	dialog.find(".u-upload-load").prepend(lis);
	                    }
	                    if(!_options.viewFlag){
	                    	dialog.find(".last").append(swf);
	                    }
	                    var submit = dialog.find(".submit");
	                    submit.isClick = false;
	                    submit.on("click", function(e){
	                    	if(currentData.length == 0){
	                    		isSuccess = true;
								dialog.an_dialog("close");
	                    	}
                    		// 上传成功返回
							$.support.cors = true;
                			//_options.uploadComplete(currentData);//触发提交完成方法
							dialog.an_dialog("close");
	                    })
                	});
                }

                var creatList = function(){
                	var contentHeight = upload.height();
                	upload.append("<div class='f-p g-h-max' id = '"+dialogId+"' style = 'overflow-y:auto;'><ul class='u-upload-load'><li class='last'><img src='"+_options.imgUrl+"img1.jpg'></li></ul></div>");
					dialog = $("#"+dialogId);
					dialog.outerHeight(contentHeight);
                    dialog.find(".last").append(swf);
                }

            	// 通过文件名查找多文件列表对象
            	var getFileByFilename = function(filename){
					return dialog.find(".u-upload-load [filename = '"+filename+"']")
            	}
            	var getFileById = function(dataid){
            		return dialog.find("#"+dataid)
            	}

            	// 对象上传完成
            	var changeComplete = function(imgUrl,fileId,isImage){
            		var $img = preview.find("img").last();
					$img.css("opacity", 1);
        			upload.isComplete = true;
					showImg.find("span").remove();
					// 预览图片加载
					if(imgUrl){
						setImgSize($img, imgUrl,fileId,isImage);
					}
            	}

            	var setImgSize2 = function(imgPic, url){
            		if(!imgPic){
            			return false;
            		}
            		var imgPicWidth = imgPic.width();
            		var imgPicHeight = imgPic.height();
            		if(_options.isFile){
            			imgPic.attr("src",_options.imgUrl+_options.icon.file);
            			imgPic.attr("url", url);
            		}else{
            			imgPic.attr("url", url);
            			imgPic.attr("src", url);
            		}
            		imgPic.width(imgPicWidth);
            		imgPic.height(imgPicHeight);
            	}
            	
            	// 设置图片大小
            	var setImgSize = function(imgPic, url,fileId,isImage){
            		if(!imgPic){
            			return false;
            		}
            		var imgPicWidth = imgPic.width();
            		var imgPicHeight = imgPic.height();
            		if(isImage == "false"){
            			imgPic.attr("src",_options.imgUrl+_options.icon.file);
            			imgPic.attr("url", url);
            			imgPic.attr("dataid", fileId);
            		}else{
            			imgPic.attr("url", url);
            			imgPic.attr("dataid", fileId);
            			imgPic.attr("src", url);
            			imgPicWidth = 102;
            			imgPicHeight = 77;
            		}
            		imgPic.width(imgPicWidth);
            		imgPic.height(imgPicHeight);
            	}

                var createUl = function(imgPic, data){
					showImg.removeClass("f-hidden").addClass("f-show");
					var li = "<li>"+imgPic+"<p>"+data.filename+"</p></li>";
					showImg.append(li);

					var showPic = showImg.find("img");
					var previewPic = preview.find("img");
					if(showPic.attr("src") != previewPic.attr("src") && data.isImage=="true"){
						setImgSize2(showPic, previewPic.attr("src"));
					}
                }

                var removeUl = function(){
                	showImg.removeClass("f-show").addClass("f-hidden");
                	showImg.empty();
                }
		        
                // 打开对话框
                if(_options.files == false){
                	touchTarget.after(swf);
                }else{
                	if(_options.dialog == true){
                		openDialog();
                	}else{
                		creatList();
                	}
                }

                // 获取当前数据方法
                upload.bind(getCurrentData, function(e, fun){
					fun(currentData);
                })

            	// 方法事件绑定
            	upload.bind(showFun, function(e, data){
            		myloading = andy.loading('top','请稍等，文件上传中。。。');
            	})

            	upload.bind(completeData, function(e, data){
            		//上传返回的json 包含有上传后文件信息
            		var json=eval("("+data.data+")");
            		if(json.acceptFile == "true"){
	            		var imgUrl = json.pathUrl;//文件路劲
	            		var fileId = json.fileId;//fileId
	            		var s_imgPic = s_file;
	            		var imgPic = b_file;
	            		var isImage = json.isImage;
	            		if(json.isImage=="true"){
	            			//s_imgPic = s_img;
	            			s_imgPic = "<img src='"+imgUrl+"'>";//预览小图
	            			imgPic = b_img;
	            			//"<img src='"+imgUrl+"'>";
	            		}
	            		if(_options.files == false){
							preview.empty();
	            		}
	            		
	            		if(dialog != ""){
							var li = "<li filename ='"+json.filename+"' id='"+fileId+"'>"+imgPic+"<p>"+json.filename+"</p></li>";
							dialog.find(".u-upload-load").prepend(li);
							var file = getFileById(fileId);
							file.find("img").css("opacity", 1);
							file.attr("isComplete", true);
							
							file.find("img").css("opacity", 1);
							file.find("span").remove();
							
							if(!_options.viewFlag){
								file.append("<i id=\"delFile\" class='iconfont'>&#xe62a;</i>");
							}
							file.append("<i id=\"downloadFile\" class='iconfont'>&#xe61b;</i>");
							file.attr("isComplete", "true");
							
							file.find("#delFile").on("click", function(e){
								// 移除当前file == li
								var dataId = $(this).parent().find("img").attr("dataid");
								FileUploadUtil.deleteFile(dataId,currentData);
								$(e.target).unbind("click");
								file.remove();
							});
							file.find("#downloadFile").on("click", function(e){
								// 移除当前file == li
								var dataId = $(this).parent().find("img").attr("dataid");
								FileUploadUtil.dowloadFile(dataId);
							});
						}else{
							// 单文件预览操作
							preview.append(s_imgPic);
							var $img = preview.find("img").last();
							$img.css("opacity", 0.5);
							if(_options.oneImgView){
								$img.on("mouseover", function(e){
									createUl(imgPic, json);
								});
								$img.on("mouseout", function(e){
									removeUl();
								})
							}
						}
	            		//上传的文件ID
	            		//var fileId;
	            		if(_options.files){
							// 多文件
							var li = getFileById(fileId);
							
							currentData.push(json);
							if(imgUrl){
								var imgPic = li.find("img");
								setImgSize(imgPic, imgUrl,fileId,isImage);
							}
							
	            		}else{
	            			//完成回调
	            			_options.uploadComplete(json);
	            			changeComplete(imgUrl,fileId,isImage);
	            		}
	            		myloading.close(); 
            		}else{
            			myloading.close();
                        $(document).an_dialog({
                            width: 400,
                            height: 300,
                            massage: {
                                type: '错误',
                                content: '该不能上传该类型文件！请上传：'+json.acceptFilemessage,
                                callback: function (dialog, event) {
                                	dialog.an_dialog("close");
                                }
                            }
                        });
            		}
            	})

            	upload.bind(uploadListComplete, function(e, isOk){
            		// 多文件上传完毕
            		isUploadListComplete = isOk;
            	//	_options.uploadComplete(currentData, isOk);
            	})

            	upload.bind(progress, function(e, data){
            		// data.loaded, data.total, data.num
            		if(data.num == 100){
            			changeComplete();
            		}else{
            			if(showImg.hasClass("speed")){
            				showImg.find(".speed").css("width", data.num +"%");
            			}else{
            				showImg.find("li").prepend("<span class='loading'><span class='speed' style='width:0%'></span></span>");
            				showImg.find(".speed").css("width", data.num +"%");
            			}
						
            		}
            		// 多文件状态处理
            		if(dialog != "" && _options.files){
            			var file = getFileByFilename(data.target.name);
            			if(data.num == 100 ){
            				file.find("img").css("opacity", 1);
							file.find("span").remove();
							if(!_options.viewFlag){
								file.append("<i id=\"delFile\" class='iconfont'>&#xe62a;</i>");
							}
							file.append("<i id=\"downloadFile\" class='iconfont'>&#xe61b;</i>");
							file.attr("isComplete", "true");
							
							
							file.find("#delFile").on("click", function(e){
								// 移除当前file == li
								var dataId = $(this).parent().find("img").attr("dataid");
								FileUploadUtil.deleteFile(dataId,currentData);
								$(e.target).unbind("click");
								file.remove();
							});
							file.find("#downloadFile").on("click", function(e){
								// 移除当前file == li
								var dataId = $(this).parent().find("img").attr("dataid");
								FileUploadUtil.dowloadFile(dataId);
							});
            			}else{
            				file.find(".speed").css("width", data.num +"%");
            			}
            		}
						
            	});

            }; 
            return upload;
    	}
	});
})(jQuery);