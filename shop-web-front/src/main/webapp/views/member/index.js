 $(function(){
	/*
	 * 付款
	 */
	$(".payOrder").click(function(){
		var orderId=$(this).attr("orderId");
		payOrder(orderId);
	});
});