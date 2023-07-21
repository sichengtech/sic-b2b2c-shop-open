$(function(){
	//搜索
	$("#searchForm").submit(function(){
		//处理日志创建的开始时间
		var beginCreateDate=$(this).find('input[name="beginCreateDate"]').val();
		if(beginCreateDate!=null && beginCreateDate!=""){
			beginCreateDate2=beginCreateDate.split(" "); 
			$(this).find('input[name="beginCreateDate"]').val(beginCreateDate2[0]+" 00:00:00");
		}
		//处理日志创建的结束时间
		var endCreateDate=$(this).find('input[name="endCreateDate"]').val();
		if(endCreateDate!=null && endCreateDate!=""){
			endCreateDate2=endCreateDate.split(" "); 
			$(this).find('input[name="endCreateDate"]').val(endCreateDate2[0]+" 23:59:59");
		}
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg(('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
});