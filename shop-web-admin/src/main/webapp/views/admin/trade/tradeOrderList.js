$(function(){
	//删除提示
	$(".deleteSure").click(function(){
		var href=$(this).attr("href1");
		fdp.confirm(fy.getMsg('确定取消订单吗？'),href);
	});
	//列表搜索
	$(".searchBtn").click(function(){
		$("#searchForm").submit();
	});
	//搜索
	$("#searchForm").submit(function(){
		var reg = new RegExp("^[0-9]*$");
		var orderId = $("input[name='orderId']").val();
	    if(!reg.test(orderId)){
	    	layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('订单编号只能是数字'));
	        return false;
	    }
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
	//快速查询
	$('.search').on('click', function(){
		var content=$("#searchModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-search"></i> '+fy.getMsg('查询'),
			area: ['600px', '600px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				//快速搜索
				//处理下单时间
				var endPlaceOrderTime=$(this).find('input[name="endPlaceOrderTime"]').val();
				if(endPlaceOrderTime!=null && endPlaceOrderTime!=""){
					$(this).find('input[name="endPlaceOrderTime"]').val(endPlaceOrderTime+" 23:59:59");
				}
				//处理支付时间
				var endPayOrderTime=$(this).find('input[name="endPayOrderTime"]').val();
				if(endPayOrderTime!=null && endPayOrderTime!=""){
					$(this).find('input[name="endPayOrderTime"]').val(endPayOrderTime+" 23:59:59");
				}
				//处理完成时间
				var endProductReceiptDate=$(this).find('input[name="endProductReceiptDate"]').val();
				if(endProductReceiptDate!=null && endProductReceiptDate!=""){
					$(this).find('input[name="endProductReceiptDate"]').val(endProductReceiptDate+" 23:59:59");
				}
				if(jsValidate){
					$(layero).find("#fastSearch").validate({
						rules: {
							"orderId":{maxlength:18,regex:/^[0-9]*$/},
							"payOrderNumber":{maxlength:64,},
						},
						messages: {
							"orderId":{maxlength:fy.getMsg("最大长度不能超过18字符"),regex:fy.getMsg("订单编号只能为数字")},
							"payOrderNumber":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
							
						},
						errorPlacement: function(error, element) {
							//错误提示信息的显示位置
							if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
								error.appendTo(element.parent().parent());
							} else {
								error.insertAfter(element);
							}
						},
						submitHandler: function(form){
							layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
							form.submit();
						}
					});
				}else{
					$(layero).find("#fastSearch").submit(function(){
						setTimeout(function(){
							layer.close(index);
							//如果1.5秒内未完成操作，则给出请等待的提示
							layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
						},1500);
					});
				}
			}
		});
	});
});