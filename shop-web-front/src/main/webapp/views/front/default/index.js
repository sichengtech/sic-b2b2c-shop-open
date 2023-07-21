$(function(){
	$(".youlike .youlikegoods li").hover(function(){
	    $(this).addClass("cur");
	},function(){
		$(this).removeClass("cur");
	});
});