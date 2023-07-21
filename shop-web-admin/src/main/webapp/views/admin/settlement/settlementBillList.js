$(function(){
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#payForm").validate({
		rules: {"payDate":{required: true}},
	 	messages: {"payDate":{required: fy.getMsg("请选择付款时间")}},
		submitHandler: function(form){
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
			form.submit();
			}
		});
	}
	//删除提示
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});
	
	//审核
	$(".audit").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要审核么？'),href);
	});
	
	//列表搜索
	$(".searchBtn").click(function(){
		$("#searchForm").submit();
	});
	
	//搜索
	$("#searchForm").submit(function(){
		var reg = new RegExp("^[0-9]*$");
		var pId = $("input[name='billId']").val();
	    if(!reg.test(pId)){
	    	layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('账单编号只能是数字'));
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
			area: ['600px', '326px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				//快速搜索
				$(layero).find("#fastSearch").submit(function(){
					//出账日期
					var endBalanceDate=$(this).find('input[name="endBalanceDate"]').val();
					if(endBalanceDate!=null && endBalanceDate!=""){
						$(this).find('input[name="endBalanceDate"]').val(endBalanceDate+" 23:59:59");
					}
					setTimeout(function(){
						layer.close(index);
						//如果1.5秒内未完成操作，则给出请等待的提示
						layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					},1500);
				});
			}
		});
	});
	
	//支付
	$('.pay').on('click', function(){
		var billId=$(this).attr("id");
		var billAmount=$(this).parent().parent().find(".billAmount").text();
		var storeName=$(this).parent().parent().find(".storeName").text();
		if(billId=="" || typeof(billId)=="undefined"){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择账单'),2000);
		}
		var content=$("#payModal").html();
		layer.open({
			type: 1,
			title:' <i class="fa fa-credit-card"></i> '+fy.getMsg('支付'),
			area: ['600px', '476px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find("input[name='billId']").val(billId);
				$(layero).find('div[id="billId"]').text(billId);
				$(layero).find('div[id="billAmount"]').text(billAmount);
				$(layero).find('div[id="sellerName"]').text(storeName);
				if(jsValidate){
					$(layero).find("#payForm").validate({
						rules: {"payDate":{required: true}},
					 	messages: {"payDate":{required: fy.getMsg("请选择付款时间")}},
						submitHandler: function(form){
							layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
							form.submit();
						}
					});
				}
			}
		});
	});
	
	//重算
	$(".retry").click(function(){
		var billId=$(this).attr("id");
		$.ajax({
			url:ctxa+"/settlement/settlementBill/retry.do",
			type:'POST',
			data:{"billId":billId},
			dataType:'json',
			success:function(data){
				if(data!=null && data==1){
					$("#"+billId).parent().parent().find(".billAmount").text(data.billAmount);
					fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('重算成功'),2000);
					window.location.reload();
				}else{
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('重算失败'),2000);
				}
			}
		});
	});
});