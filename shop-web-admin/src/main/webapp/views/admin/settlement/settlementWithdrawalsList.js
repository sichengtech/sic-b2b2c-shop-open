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
		var swId=$(this).attr("swId");//提现id
		var swNumber=$(this).attr("swNumber");//提现编号
		var swMoney=$(this).attr("swMoney");//提现金额
		var swMemName=$(this).attr("swMemName");//会员名称
		var swPayWay=$(this).attr("swPayWay");//收款方式
		var swReNumber=$(this).attr("swReNumber");//收款账号
		var swAccountName=$(this).attr("swAccountName");//开户名
		var uId=$(this).attr("uId");//开户名
		var content=$("#examineModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-edit"></i> 提现审核',
			area: ['700px', '600px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find("#swNumber_id").html(swNumber);
				$(layero).find("#swMoney_id").html(swMoney);
				$(layero).find("input[name='money']").val(swMoney);
				$(layero).find("#swMemName_id").html(swMemName);
				$(layero).find("#swPayWay_id").html(swPayWay);
				$(layero).find("#swReNumber_id").html(swReNumber);
				$(layero).find("#swAccountName_id").html(swAccountName);
				$(layero).find("input[name='id']").val(swId);
				$(layero).find("input[name='uId']").val(uId);
				$(layero).find("#radio1").click(function(){
					$(".dateTime").css("display","");
					$(".reason").css("display","none");
				});
				$(layero).find("#radio2").click(function(){
					$(".dateTime").css("display","none");
					$(".reason").css("display","");
				});
				if(jsValidate){
					$(layero).find("#examineForm").validate({
						rules: {
							"payTime":{required: true},
							"rejectionReason":{required: true,maxlength:255}},
					 	messages: {
					 		"payTime":{required: "请选择付款时间"},
					 		"rejectionReason":{required:"请输入拒绝理由",maxlength:"拒绝最大长度不能超过521字符"}},
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