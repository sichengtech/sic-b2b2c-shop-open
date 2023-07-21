$(function(){
	
	/**
	 * 筛选选择状态
	 * */
	$("body").delegate("dd.selected","click",function(){
	//$("dd.selected").click(function(){
		//$(this).parent("dd.selected").removeClass("cur");
		if($(this).hasClass("cur")){
			$(this).removeClass("cur");
		}else{
			$(this).addClass("cur");
		}
	});
	
	/**
	 * 重置点击事件
	 * */
	$("body").delegate("#btn-reset","click",function(){
	//$(".bottom-btn #btn-reset").click(function(){
		$(this).removeClass("icon-35");
		$(".sift-iscroll dd.cur").removeClass("cur");
		$(".deal-price input").val("");
		//$(".sift-expand").removeClass("show");
	});
	
	/**
	 * 展开/收起筛选条件
	 * */
	$("body").delegate(".sift-iscroll .weui-border-b .icon","click",function(){
	//$(".weui-border-b .icon").click(function(){
		var iconStatus = $(this).hasClass('icon-74');
		if(iconStatus){
			//下拉状态准备收起
			$(this).parent().parent().find('.selected').css("display","none");
			$(this).removeClass("icon-74");
			$(this).addClass("icon-35");
		}else{
			//收起状态准备展开
			$(this).parent().parent().find('.selected').removeAttr("style");
			$(this).removeClass("icon-35");
			$(this).addClass("icon-74");
		}
	});
});