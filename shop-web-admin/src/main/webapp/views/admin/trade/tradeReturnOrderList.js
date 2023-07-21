$(function(){
	//删除提示
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});
	
	//列表搜索绑定点击事件
	$(".searchBtn").click(function(){
		$("#searchForm").submit();
	});
	//搜索
	$("#searchForm").submit(function(){
		var reg = new RegExp("^[0-9]*$");
		var pId = $("input[name='returnOrderId']").val();
	    if(!reg.test(pId)){
	    	layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('退款编号只能是数字'));
	        return false;
	    }
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
});