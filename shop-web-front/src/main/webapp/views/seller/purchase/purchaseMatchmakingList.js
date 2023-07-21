$(function(){
	
	/**
	 * 采购
	 */
	$(".purchaseOk").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要采购么？'),href);
	});
	
});