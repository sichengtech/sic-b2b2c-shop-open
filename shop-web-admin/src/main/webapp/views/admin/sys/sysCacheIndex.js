$(function(){
	/**
	 * 清理页面缓存
	 */
	$(".delPage").click(function(){
		var href=$(this).attr("href");
		fdp.confirm('确定要清除页面缓存么？',href); 
	});
	
	/**
	 * 清理SQL数据缓存
	 */
	$(".delSQL").click(function(){
		var href=$(this).attr("href");
		fdp.confirm('确定要清理SQL数据缓存么？',href); 
	});
});