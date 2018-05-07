var LayuiUtil={
		showMsg:function(msg){
			layui.use('layer', function(){
				var layer = layui.layer;
				layer.open({
				  title: '消息',
				  content: msg
				});
			});
		},
		msg:function(msg){
			layui.use('layer', function(){
				var layer = layui.layer;
				layer.msg(msg);
			});
		},
	   alertShade:(function(closeTime,content){
		   layui.use('layer', function(){
			   var layer = layui.layer;
			   layer.msg(content, {
				   area: ['500px', '400px'],
				   time: closeTime, //20s后自动关闭
			   });
			   parent.location.reload();
		   });

		}),
		showNoLoginMsg:function(){
			//this.msg("亲，请先登录。");
			//this.loginPage();
			this.loginWpage();
		},
	    loginWpage:function(){
			layui.use('layer', function(){
				var layer = layui.layer;
				layer.open({
					id:"wLogin",
                    zIndex:8000,
                    title: '您尚未登录',
					type: 2,
					area: ['500px', '400px'],
					content: base+"/toWLogin"
				});
			});
		},
	    loginPage:function () {
			$(document).an_dialog({
				id:"wLogin",
				title: '您尚未登录',
				url:base+"/toWLogin",
				width: 500,
				height: 400,
				buttons: [ /*{
					text: '取消',
					handler: function (e) {
						e.data.an_dialog("close");
					}
				},{
					text: '登录',
					handler: function (e) {
						//alert("点击了OK");
						$(document).an_dialog("close", e.data[0].id);
					}
				}*/]
			});
		}
};