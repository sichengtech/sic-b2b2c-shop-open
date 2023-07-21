$(function(){
	
	/**
	 * foot点击高亮
	 */
	$("body").delegate(".weui_tabbar a","click",function(){
		$(".weui_tabbar a").removeClass("weui_bar_item_on");
		$(this).addClass("weui_bar_item_on");
	});	
	
	/**
	 * 页面底部点击商品分类
	 */
	$("body").delegate(".foot-storeCategory","click",function(){
		var consultationHtml = $("#popup1").html();
		layer.open({
			type: 1,
			content: consultationHtml,
			anim: 'up',
			style: 'position:fixed; bottom:0; left:0; width: 100%; height: 500px; padding:10px 0; border:none;'
		});
	});	
	
	/**
	 * 页面底部点击联系我们
	 */
	$("body").delegate(".foot-contactUs","click",function(){
		var consultationHtml = $("#popup2").html();
		layer.open({
			type: 1,
			content: consultationHtml,
			anim: 'up',
			style: 'position:fixed; bottom:0; left:0; width: 100%; height: 400px; padding:10px 0; border:none;'
		});
	});
	
	/**
	 * 关闭弹出的联系我们,商品分类
	 */
	$("body").delegate(".layui-m-layer .title span","click",function(){
		$(".layui-m-layer").remove();
	});	
});	