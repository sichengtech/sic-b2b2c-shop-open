$(function(){
	$(".article-left-menu .menu li").click(function(){
		$(this).parent().find(".cur").removeClass("cur");
	    $(this).addClass("cur");
	});
});