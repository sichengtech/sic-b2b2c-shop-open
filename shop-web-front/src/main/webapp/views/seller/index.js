 $(function(){
	/*
	 * ajax异步获取首页数据
	 */
		var storeId=$("#storeId").attr("storeId");
		$.ajax({																	
			type: 'get',
			url: ctxs + "/index/ajaxGetData/?storeId="+storeId,
			dataType: 'json',
			success: function(data){
				$("#spu1").html(data.spu1);
				$("#spu2").html(data.spu2);
				$("#spu3").html(data.spu3);
				$("#tradeOrder1").html(data.tradeOrder1);
				$("#tradeOrder2").html(data.tradeOrder2);
				$("#tradeOrder3").html(data.tradeOrder3);
				$("#returnMoney").html(data.returnMoney);
				$("#consul").html(data.consul);
				$("#complaint").html(data.complaint);
				$("#tradeOrderMonthNumber").html(data.tradeOrderMonthNumber);
				$("#tradeOrderMonthMoney").html("￥"+data.tradeOrderMonthMoney);
			},
			error: function(data){
				fdp.msg(fy.getMsg('获取首页数据失败'));
				return false;
			}
		});
});