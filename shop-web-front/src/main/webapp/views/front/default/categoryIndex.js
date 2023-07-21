$(function(){
	/**
	 * 商品的hover
	 * 鼠标移进去加边框
	 * */
	$(".floorer-prolist .floorer-prolist-box li,.youlikegoods li").hover(function(){
	    $(this).addClass("cur");
	},function(){
		$(this).removeClass("cur");
	});
	
	/**
	 * 商品小图片的hover事件
	 * */
	$(".go-color img").hover(function(){
		$(this).parent().find(".cur").removeClass("cur");
		$(this).addClass("cur");
		$(this).parent().siblings(".p-pic").find("img").attr("src",$(this).attr("src"));
	});
	
});