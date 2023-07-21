$(function(){
	footHighlight(highlight);
	/**
	 * 高亮
	 */
	function footHighlight(highlight){
		$(".weui_tabbar a").removeClass(".weui_bar_item_on");
		if(highlight=='indexHighlight'){
			$(".weui_tabbar .footIndex").addClass("weui_bar_item_on");
		}
		if(highlight=='categoryIndexHighlight'){
			$(".weui_tabbar .footCategory").addClass("weui_bar_item_on");
		}
		if(highlight=='userCentralHighlight'){
			$(".weui_tabbar .footUserCentral").addClass("weui_bar_item_on");
		}
		if(highlight=='cartHighlight'){
			$(".weui_tabbar .footCart").addClass("weui_bar_item_on");
		}
		
	};
	
	/**
	 * 点击高亮
	 */
	$("body").delegate(".weui_tabbar a","click",function(){
		$(".weui_tabbar a").removeClass("weui_bar_item_on");
		$(this).addClass("weui_bar_item_on");
	});	
	
	/**
	 * 页面底部点击咨询
	 */
	$("body").delegate(".foot-consultation","click",function(){
		var consultationHtml = $("#popup").html();
		layer.open({
			type: 1,
			content: consultationHtml,
			anim: 'up',
			style: 'position:fixed; bottom:0; left:0; width: 100%; height: 400px; padding:10px 0; border:none;'
		});
	});	
	
	/**
	 * 关闭弹出的咨询
	 */
	$("body").delegate(".layui-m-layer .title span","click",function(){
		$(".layui-m-layer").remove();
	});	
});	