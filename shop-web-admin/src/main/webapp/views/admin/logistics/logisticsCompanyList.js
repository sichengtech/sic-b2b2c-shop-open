$(function(){
	//删除提示
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});
	//搜索
	$("#searchForm").submit(function(){
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
	//状态数据更新
	$(".status_td span").click(function(){
		var status=$(this).parent().find("input[class='status']").val();//状态值
		var id=$(this).parent().find("input[class='status']").attr("id");//快递公司id
		$.ajax({
			url: ctxa+"/logistics/logisticsCompany/updateStatus.do",
			type: "post",
			data: {"status":status,"id":id},
			dataType: "json",
			success: function(data) {
				if(data==1){
					fdp.msg(fy.getMsg('状态数据已更新'));
					//location.reload();//刷新页面
				}
			}
		});
	});
	//常用快递数据更新
	$(".isCommonUse_td span").click(function(){
		var isCommonUse=$(this).parent().find("input[class='isCommonUse']").val();
		var id=$(this).parent().find("input[class='isCommonUse']").attr("id");
		$.ajax({
			url: ctxa+"/logistics/logisticsCompany/updateIsCommonUse.do",
			type: "post",
			data: {"isCommonUse":isCommonUse,"id":id},
			dataType: "json",
			success: function(data) {
				if(data==1){
					fdp.msg(fy.getMsg('常用快递数据已更新'));
				}
			}
		});
	});
	//按公司名称查询列表
	$(".search").click(function(){
		$("#searchForm").submit();
	});
});