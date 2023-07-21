$(function(){
	//搜索
	$("#searchForm").submit(function(){
		//处理申请时间
		var endApplyDate=$(this).find('input[name="endApplyDate"]').val();
		if(endApplyDate!=null && endApplyDate!=""){
			endApplyDate2=endApplyDate.split(" "); 
			$(this).find('input[name="endApplyDate"]').val(endApplyDate2[0]+" 23:59:59");
		}
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
});