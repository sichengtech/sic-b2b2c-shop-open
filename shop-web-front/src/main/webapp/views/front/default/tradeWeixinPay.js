$(function(){
	/**
	 * 查询订单，付款成功后跳转到订单列表页
	 * */
	var repeat = 200; // 限制执行次数为200次  
	var orderIds=$(".orderIds").val();
	if(orderIds!=""){
		var orderId=orderIds.split(",")[0];
		var timer = setInterval(function(){
			if (repeat == 0){
				clearInterval(timer);
			} else{
				$.ajax({
					url:ctxf+"/trade/order/orderStatus.htm",
					type:'POST',
					data:{"orderId":orderId},
					dataType:'json',
					success:function(data){
						if(data!=null && data.status && data.tradeOrderStatus!="10"){
							layer.load(1, {shade: [0.1,'#aaa']});
							window.location.href =ctxf+"/trade/pay/payOk.htm";
						}
					}
				});
			}
			repeat--;
		}, 3000);//3秒执行一次 总共200次 10分钟  
	}
	
});