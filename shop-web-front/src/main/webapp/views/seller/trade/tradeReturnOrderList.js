$(function(){
	/**
	 * 搜索
	 * */
	$("#searchForm").submit(function(){
		//处理下单时间
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

	/**
	 * 商家收货
	 * */
	$(".deliverGoodsBtn").click(function(){
		var returnOrderId=$(this).attr("returnOrderId");
		var deliverProductTime=$(this).parent().parent().find("input[name='deliverProductTime']").val();
		var logisticsTemplateName=$(this).parent().find("input[name='logisticsTemplateName']").val();
		var trackingNo=$(this).parent().find("input[name='trackingNo']").val();
		$("#goodsReceiptForm #goodsReiceiptTime").text(deliverProductTime);
		$("#goodsReceiptForm #logisticsMsg").text(logisticsTemplateName+","+trackingNo);
		$("#goodsReceiptForm #returnOrderId").text();
		var content=$("#goodsReceiptModal").html();
		//type:1在列表中的操作，2表示订单详情中的操作
		var type=$(this).attr("type");
		layer.open({
			type: 1,
			title:fy.getMsg('收货'),
			area: ['460px', '235px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				$(layero).find("#deliverGoodsBtn").click(function(){
					var isProductReceipt=$(layero).find(".isProductReceipt").val();
					if(isProductReceipt == '0'){
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择收货情况'),2000);
						return false;
					}
					$.ajax({
						url:ctxs+"/trade/tradeReturnOrder/goodsReceipt.htm",
						type:'POST',
						data:{"returnOrderId":returnOrderId,"isProductReceipt":isProductReceipt},
						dataType:'json',
						success:function(data){
							if(data.success){
								layer.closeAll('page');
								$(".deliverGoodsBtn[returnOrderId="+returnOrderId+"]").css("display","none");
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('收货成功'),2000);
							}
							var message=fy.getMsg('收货失败');
							if(data.message!=""){
								message=data.message;
							}
							fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+data.message ,2000);
						},
						error:function(data){
							layer.closeAll('page');
							fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('收货失败') ,2000);
						}
					});
				});
			}
		});
	});
});