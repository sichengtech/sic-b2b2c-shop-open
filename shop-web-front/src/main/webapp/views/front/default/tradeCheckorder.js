$(document).ready(function(){
	/**
	 * 支付方式点击事件
	 * */
	$(".payment li").click(function(){
		$(".payment .sel_pay_way").removeClass("sel_pay_way");
		$(this).addClass("sel_pay_way");
	});
	
	/**
	 * 支付
	 * */
	$(".payOrders").click(function(){
		layer.load(1, {shade: [0.1,'#aaa']});
		//循环拼接订单编号
		var orderIds="";
		$("tbody .orderId").each(function(){
			var orderId=$(this).text();
			orderIds+=orderId+",";
		});
		var orderIdStr=orderIds.substring(0,orderIds.length-1);
		//支付方式id
		var payWayId=$(".sel_pay_way").attr("id");
		if(typeof(payWayId)=="undefined" || payWayId==""){
			layer.closeAll();
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择支付方式'),3000);
			return false;
		}
		window.location.href =ctxm+"/shop/pay/unifiedorder.htm?orderIds="+orderIdStr+"&payWayId="+payWayId;
	});
});