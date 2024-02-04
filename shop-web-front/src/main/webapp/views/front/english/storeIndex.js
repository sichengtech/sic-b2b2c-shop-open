$(function(){
	/**
	 * 轮播图片
	 */
	$('#scroll-stone').owlCarousel({
		items: 1,
		autoPlay: false,
		navigation: true,
		navigationText: ["",""],
		slideSpeed:1000,
		autoHeight:true,
		scrollPerPage: true
	});
	
	/**
	 * 商品的hover事件
	 * 鼠标移上去加边框
	 * */
	$(".store-goods-list .goods-pic-list li").hover(function(){
	    $(this).addClass("cur");
	},function(){
		$(this).removeClass("cur");
	});
	
	/**
	 * 商品小图片的hover事件
	 * 鼠标移上去加边框，商品大图换成当前指的图片
	 * */
	$(".go-color img").hover(function(){
		$(this).parent().find(".cur").removeClass("cur");
		$(this).addClass("cur");
		$(this).parent().siblings(".p-picture").find("img").attr("src",$(this).attr("src"));
	});
	
	
});