 $(function(){
	 
	/*
	 * 搜索
	 * */
	$("#searchForm").submit(function(){
		//处理下单时间
		var endPlaceOrderTime=$(this).find('input[name="endPlaceOrderTime"]').val();
		if(endPlaceOrderTime!=null && endPlaceOrderTime!=""){
			endPlacesOrderTime2=endPlaceOrderTime.split(" "); 
			$(this).find('input[name="endPlaceOrderTime"]').val(endPlacesOrderTime2[0]+" 23:59:59");
		}
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
	
	/*
	 * 取消订单
	 * */
	$(".delOrder").click(function(){
		var orderId=$(this).attr("orderId");//订单编号
		//thpe=1代表点击的是订单列表中的
		//var type=$(this).attr("type");
		$("#orderIdModal").html(orderId);
		$(".cancelOrderBtn").attr("id",orderId);
		var content=$("#delModal").html();
		//type:1在列表中的操作，2表示订单详情中的操作
		var type=$(this).attr("type");
		layer.open({
			type: 1,
			title:fy.getMsg('取消订单'),
			area: ['460px', '280px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				$(layero).find(".cancelOrderBtn").click(function(){
					var cancelOrderReason=$(this).parent().parent().find('.checked input[name="cancelOrderReason"]:checked ').val();
					$.ajax({
						url:ctxm+"/trade/tradeOrder/cancelOrder.htm",
						type:'POST',
						data:{"orderId":orderId,"cancelOrderReason":cancelOrderReason},
						dataType:'json',
						success:function(data){
							if("201"!=data.statusCode && "202"!=data.statusCode){
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+data.message,2000);
								return false;
							}
							if("201"==data.statusCode){
								$(".order_status[orderId="+orderId+"]").html(fy.getMsg('已取消'));
								$(".delOrder[orderId="+orderId+"]").parent().parent().html("");
								$(".customer_service[orderId="+orderId+"]").html("");	
								if(type == 2){
									$("#sui_steps").css("display","none");
									var date=new Date();
									$(".statusTip").html("<p>"+fy.getMsg('买家于')+date.format("yyyy-MM-dd hh:mm:ss")+fy.getMsg('取消订单')+"</p>");
									$(".orderStatus").find("span").text(fy.getMsg('已取消'));
								}
								layer.closeAll('page');
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+data.message,2000);
							}
							if("202"==data.statusCode){
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+data.message,5000);
								if(type == 2){
									window.location.reload();
								}else{
									window.location.href=ctxm+"/trade/tradeOrder/list.htm";
								}
							}
						}
					});
				});
			}
		}); 
	});
	
	/*
	 * 确认收货
	 */	
	$(".receiptGoods").click(function(){
		if(!$(this).hasClass("receiptGoods")){
			return false;
		}
		var orderId=$(this).attr("orderId");
		$("#receiptOrderId").html(orderId);
		var content=$("#receiptModal").html();
		//type:1在列表中的操作，2表示订单详情中的操作
		var type=$(this).attr("type");
		layer.open({
			type: 1,
			title:fy.getMsg('确认收货'),
			area: ['460px', '200px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				$(layero).find(".confirmrReceiptBtn").click(function(){
					var cancelOrderReason=$(this).parent().parent().find('.checked input[name="cancelOrderReason"]:checked ').val();
					$.ajax({
						url:ctxm+"/trade/tradeOrder/confirmReceipt.htm",
						type:'POST',
						data:{"orderId":orderId},
						dataType:'json',
						success:function(data){
							if(data.success){
								$(".receiptGoods[orderId="+orderId+"]").parent().html("<a href="+ctxm+"/trade/tradeComment/save1.htm?orderId="+orderId+" class=\"sui-btn btn-bordered commentbtn\" orderId="+orderId+" data-keyboard=\"false\"><i class=\"sui-icon icon-tb-appreciate\"></i> "+fy.getMsg('我要评价')+"</a>");
								//把状态置为交易完成
								$(".order_status[orderId="+orderId+"]").text(fy.getMsg('交易完成'));
								//隐藏延迟收货按钮
								$(".delayReceipt[orderId="+orderId+"]").css("display","none");
								//隐藏订单退款按钮
								$(".returnOrderBtn[orderId="+orderId+"]").css("display","none");
								if(type==2){
									//更新进度条的进度
									$("div[class='current']").next("div").attr("class","current");
									$("div[class='current']").attr("class","finished");
									$(".statusTip").html("<p>1."+fy.getMsg('如果收到货后出现问题，您可以联系商家协商解决。')+"</p>"
											+"<p>2."+fy.getMsg('如果商家没有履行应尽的承诺，您可以申请 投诉维权。')+"</p>"
											+"<p>3."+fy.getMsg('交易已完成，你可以对购买的商品及商家的服务进行评价。')+"</p>");
									$(".orderStatus").find("span").text(fy.getMsg('交易成功'));
								}
								layer.closeAll('page');
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('收货成功'),2000);
								return;
							}
							var message=fy.getMsg('收货失败');
							if(data.message!="" && typeof(data.message)!="undefined"){
								message=data.message;
							}
							fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+message,2000);
						}
					});
				});
			}
		}); 
	});
	
	/*
	 * 延迟收货
	 * */
	$(".delayReceipt").click(function(){
		var orderId=$(this).attr("orderId");
		var placeOrderTime=$(this).attr("placeOrderTime");
		var date = new Date(placeOrderTime);
		var da = date.valueOf();
		da = da + daysOfReceipt * 24 * 60 * 60 * 1000;
		da = new Date(da);
		var newDate = da.format("yyyy-MM-dd hh:mm:ss");
		$("#delayReceiptId").html(orderId);
		$("#delayReceiptDate").html(newDate);
		var content=$("#delayReceiptModal").html();
		//type:1在列表中的操作，2表示订单详情中的操作
		var type=$(this).attr("type");
		layer.open({
			type: 1,
			title:fy.getMsg('延迟收货'),
			area: ['460px', '235px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				$(layero).find(".delayReceiptBtn").click(function(){
					var delayDays=$(layero).find('.delayDays').val();
					$.ajax({
						url:ctxm+"/trade/tradeOrder/delayedReceipt.htm",
						type:'POST',
						data:{"orderId":orderId,"delayDays":delayDays},
						dataType:'json',
						success:function(data){
							if(data == '1'){
								$(".delayReceipt[orderId="+orderId+"]").css("display","none");
								if(type==2){
									var da1 = da.valueOf();
									da1 = da1 +delayDays* 24 * 60 * 60 * 1000;
									var date1 = new Date(da1);
									var newDate1 = date1.format("yyyy-MM-dd hh:mm:ss");
									$(".lateTime").text(newDate1);
								}
								layer.closeAll('page');
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('延迟收货成功'),2000);
							}else{
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('延迟收货失败'),2000);
							}
						}
					});
				});
			}
		}); 
	});
	
	/*
	 * 查看物流的弹出框
	 * */
	$(".logisticsView").click(function(){
		var orderId=$(this).attr("orderId");
		var myRemoteUrl=ctxm+"/trade/tradeOrder/showLogisticsMsg.htm?orderId="+orderId;
		$.post(myRemoteUrl, {}, function(str){
			layer.open({
				type: 1,
				title:fy.getMsg('查看物流'),
				area: ['600px', '400px'],
				shadeClose: true, //点击遮罩关闭
				btn: ['关闭'],
				content: str
			}); 
		});
	});
	
	/*
	 * 格式化日期
	 * */
	Date.prototype.format = function(format){
		var o = {
			"M+" : this.getMonth()+1, //month 
			"d+" : this.getDate(), //day 
			"h+" : this.getHours(), //hour 
			"m+" : this.getMinutes(), //minute 
			"s+" : this.getSeconds(), //second 
			"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
			"S" : this.getMilliseconds() //millisecond 
		}
		if(/(y+)/.test(format)){
			format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		}
		for(var k in o) {
			if(new RegExp("("+ k +")").test(format)){
				format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
			}
		}
		return format; 
	}
	
	/*
	 * 付款
	 */
	/*$(".payOrder").click(function(){
		var orderId=$(this).attr("orderId");
		//payOrder(orderId);
		//var queryOrderInfo=weixin.orderquery(orderId);
		//商品描述
		var body="美的（Midea）大1匹 智弧 智能 静音 光线感应 定速冷暖壁挂式空调 KFR-26GW/WDAD3@";
		//总价格
		var totalFee=$("input.totalFeee").val();
		//查询支付方式
		//var payWayId=$("input.totalFeee").val();
		//微信统一下单
		weixin.unifiedorder(orderId,body,totalFee);
	});*/
	
	/*
	 * 退货退款
	 * */
	$(".applyReturn").click(function(){
		var href=$(this).attr("path");
		fdp.confirm(fy.getMsg('在退货退款前请确认是否已经收到货？'),href);
	});
	
});