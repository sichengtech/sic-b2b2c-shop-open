$(function(){
	//删除提示
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm('确定要删除么？',href);
	});
	//搜索
	$("#searchForm").submit(function(){
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
	//按会员登录名查询列表
	$(".search").click(function(){
		$("#searchForm").submit();
	});
	//审核
	$('.examine').on('click', function(){
		var reId=$(this).attr("reId");//充值id
		var reNumber=$(this).attr("reNumber");//充值编号
		var reMoney=$(this).attr("reMoney");//充值金额
		var reMemName=$(this).attr("reMemName");//会员名称
		var content=$("#examineModal").html();
		var uId=$(this).attr("uId");
		layer.open({
			type: 1,
			title:' <i class="fa fa-edit"></i> 充值审核',
			area: ['700px', '550px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find("#reNumber_id").html(reNumber);
				$(layero).find("#reMoney_id").html(reMoney);
				$(layero).find("#reMemName_id").html(reMemName);
				$(layero).find("input[name='rechargeId']").val(reId);
				$(layero).find("input[name='uId']").val(uId);
				$(layero).find("input[name='rechargeMoney']").val(reMoney);
				if(jsValidate){
					$(layero).find("#examineForm").validate({
						rules: {
							"payDate":{required: true},
							"tradeNumber":{required: true,maxlength:64}},
					 	messages: {
					 		"payDate":{required: "请选择支付时间"},
					 		"tradeNumber":{required:"请输入交易平台号",maxlength:"交易平台号最大长度不能超过64字符"}},
						submitHandler: function(form){
							layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
							form.submit();
						}
					});
				}
			}
		});
	});
});