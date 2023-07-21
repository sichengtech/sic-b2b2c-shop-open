$(function(){
	//搜索
	$("#searchForm").submit(function(){
		//处理下单时间
		var endCreateDate=$(this).find('input[name="endCreateDate"]').val();
		if(endCreateDate!=null && endCreateDate!=""){
			endCreateDate2=endCreateDate.split(" "); 
			$(this).find('input[name="endCreateDate"]').val(endCreateDate2[0]+" 23:59:59");
		}
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
});