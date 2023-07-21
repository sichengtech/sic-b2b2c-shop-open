$(function(){
	//删除提示
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？',href);
	});
	//搜索
	$("#searchForm").submit(function(){
		var reg = new RegExp("^[0-9]*$");
		var purchaseTradeId = $("input[name='purchaseTradeId']").val();
	    if(!reg.test(purchaseTradeId)){
	    	layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('订单编号只能是数字');
	        return false;
	    }
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
});