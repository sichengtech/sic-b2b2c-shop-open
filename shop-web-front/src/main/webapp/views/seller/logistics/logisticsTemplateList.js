	/**
	 * 删除
	 * */
	$(".del").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('删除将影响所有使用该运费模板的商品的运费计算，确定继续删除吗？'),href);
	});
	
	/**
	 * 复制
	 **/
	$(".copyBtn").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要复制吗？'),href);
	});