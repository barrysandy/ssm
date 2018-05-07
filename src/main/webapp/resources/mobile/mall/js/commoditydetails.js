$(function(){
	setFontSize = function() {
        var w = $(window).width();
        $("html").css({
            fontSize: (w) / 360 * 10
        });
    }
    setFontSize()
    $(window).resize(function () {
        setFontSize();
    });
    $('#carousel4').carouFredSel({
		circular: true,
		responsive:true,
		direction:'left',
		height:250,
		items: {
			visible: 1,
			minimum: 1,
			height:'100%'
		},
		pagination: "#carousel-pager4",//指定圆点坐标
		scroll:{
			items:1,
			fx:'directscroll',
			easing:'linear',
			duration:500,
			pauseOnHover:false
			},
		auto:{
			play:'auto',
			pauseDuration:2500
			}
	});
})