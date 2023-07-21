$(function(){
	
	/**
	 * 搜索
	 */
	$("#searchForm").submit(function(){
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
	
	/**
	 * 完成采购
	 */
	$(".purchaseCancel").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要取消采购么？'),href);
	});
});