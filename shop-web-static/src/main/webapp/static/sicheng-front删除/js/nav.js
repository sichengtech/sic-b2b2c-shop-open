/*
*author:Null
*DATE:2013.5.24
*/

$(function(){		   
	//头页登录
	$("#navul > li").not(".navhome").hover(function(){
		$(this).addClass("navmoon")
	},function(){
		$(this).removeClass("navmoon")
	});
	
}); 

(function($){
    $.fn.capacityFixed = function(options) {
        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
        var FixedFun = function(element) {
            var top = opts.top;
            element.css({
                "top":top
            });
            $(window).scroll(function() {
                var scrolls = $(this).scrollTop();
                if (scrolls > top) {

                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
                            top: 0							
                        });
                    } else {
                        element.css({
                            top: scrolls
                        });
                    }
                }else {
                    element.css({
                        position: "absolute",
                        top: top
                    });
                }
            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    $.fn.capacityFixed.deflunt={
		right : 0,//相对于页面宽度的右边定位
        top:0
	};
})(jQuery);

$(function(){		   
	//头页登录
	$(".infoli li").hover(function(){
		$(this).addClass("cur")
	},function(){
		$(this).removeClass("cur")
	});
}); 

/*店铺商品分类-左侧*/
$(function() {
    $(".goods-category li b").click(function() {
    	var a = $(this).text();
    	if(a == "+"){
    		$(this).parent("li").addClass("cur");
    		$(this).text("-");
    	}
    	if(a == "-"){
    		$(this).parent("li").removeClass("cur");
    		$(this).text("+");
    	}
    });
});

/*商品详情-配置列表*/
$(function(){
	//隐藏规格，显示下拉按钮
	if($('.summary-list-box ul').size() > 2){
		var html = '';
		$.each($('.summary-list-box ul:gt(2)'),function(){
			html += $(this).prop("outerHTML");
			$(this).remove();
		});
		$('.summary-list-box').after('<span class="dd summary-list-box-more summary-list-box-section">' + html + '<div class="more show"><a></a></div><div class="more hide" style="display:none;"><a></a></div>' + '</span>')
	}
	//显示更多的规格
	$(".summary-list-box-more .show").click(function() {
		$(this).siblings("ul").addClass("cur");
		$(this).css("display","none");
		$(this).siblings(".hide").css("display","block");
	});
	//隐藏更多的规格
	$(".summary-list-box-more .hide").click(function() {
		$(this).siblings("ul").removeClass("cur");
		$(this).css("display","none");
		$(this).siblings(".show").css("display","block");
	});
});

//选择物流地址
$(function(){		   
	//头页登录
	/*$(".summary-freight span.dd").hover(function(){
		$(this).addClass("hover")
	},function(){
		$(this).removeClass("hover")
	});*/
	
});

////侧导航
//
//<!--
// $(function(){
//  //alert("jQuery loaded!");
//  $(".menu-category").each(function(){
//   $(this).hover(function() {
//    $(".menu-category").removeClass("cur");
//	$(this).addClass("cur");
//   });
//
//
//  });
//  
//
//  
//  //
//  $(".leftmenu").each(function(){
//	  $(this).hover(function() {
//	  	$(this).css({"height":"457px","overflow":"visible"});
//	  });
//  });
// });
//-->
