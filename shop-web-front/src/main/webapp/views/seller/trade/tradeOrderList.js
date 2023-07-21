 $(function(){
	//取消订单
	$(".delOrder").click(function(){
		var orderId=$(this).attr("orderId");
		//thpe=1代表点击的是订单列表中的
		var type=$(this).attr("type");
		$("#orderIdModal").html(orderId);
		$(".cancelOrderBtn").attr("id",orderId);
		var content=$("#delModal").html();
		layer.open({
			type: 1,
			title:fy.getMsg('取消订单'),
			area: ['460px', '235px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				$(layero).find(".cancelOrderBtn").click(function(){
					var orderId=$(this).attr("id");
					var cancelOrderReason=$(this).parent().parent().find('.checked input[name="cancelOrderReason"]:checked ').val();
					$.ajax({
						url:ctxs+"/trade/tradeOrder/cancelOrder.htm",
						type:'POST',
						data:{"orderId":orderId,"cancelOrderReason":cancelOrderReason},
						dataType:'json',
						success:function(data){
							if("201"!=data.statusCode && "202"!=data.statusCode){
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+data.message,2000);
								return false;
							}
							if("201"==data.statusCode){
								$(".status[orderId="+orderId+"]").html(fy.getMsg('已取消'));
								$(".delOrder[orderId="+orderId+"]").css("display","none");
								layer.closeAll('page');
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('取消成功'),2000);
							}
							if("202"==data.statusCode){
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+data.message,5000);
								if(type == 2){
									window.location.reload();
								}else{
									window.location.href=ctxs+"/trade/tradeOrder/list.htm";
								}
							}
						}
					});
				});
			}
		}); 
	});
	
	//修改运费
	$(".freightModal1").click(function(){
		//订单id
		var orderId=$(this).attr("orderId");
		//会员名
		var buyer=$(this).attr("buyer");
		//原运费
		var oldFreight=$(this).attr("freight");
		$("#updateFreightBtn").attr("orderId",orderId);
		$("#freightFormOrderId").html(orderId);
		$("#freightFormBuyer").html(buyer);
		$("#freightFormMoney").attr("value",oldFreight);
		var content=$("#freightModal").html();
		layer.open({
			type: 1,
			title:fy.getMsg('修改运费'),
			area: ['460px', '210px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				$(layero).find("#updateFreightBtn").click(function(){
					if(oldFreight == null){
						oldFreight=0;
					}
					//现运费
					var newFreight=$(this).parent().parent().find('#freightFormMoney').val();
					//原订单总金额
					var OldOrderAmountPaid=$(".orderAmountPaid[orderId="+orderId+"]").html();
					//现订单金额=原订单金额-原运费+现运费
					var newOrderAmountPaid=parseFloat(OldOrderAmountPaid)-parseFloat(oldFreight)+parseFloat(newFreight);
					newOrderAmountPaid=newOrderAmountPaid.toFixed(2);
					if(jsValidate){
						if (newFreight==undefined||newFreight.length==0) {
							fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请输入运费'),2000);
					    	return false;
						}
						
						var pattern=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
						if(!pattern.test(newFreight)){
							fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('运费只能是整数或两位以内的小数'),2000);
					    	return false;
						}
					}
					var offsetAmount=$(".offsetAmount[orderId="+orderId+"]").val();
					var _data={};
					if(offsetAmount=="" || typeof(offsetAmount)=="undefined"){
						_data={"orderId":orderId,"freight":newFreight,"amountPaid":newOrderAmountPaid,"type":1};
					}else{
						_data={"orderId":orderId,"freight":newFreight,"offsetAmount":newOrderAmountPaid,"type":1};
					}
					$.ajax({
						url:ctxs+"/trade/tradeOrder/edit.htm",
						type:'POST',
						data:_data,
						dataType:'json',
						success:function(data){
							if(data == '1'){
								$(".orderAmountPaid[orderId="+orderId+"]").html(newOrderAmountPaid);
								$(".offsetAmount[orderId="+orderId+"]").val(newOrderAmountPaid);
								$(".freightBtn[orderId="+orderId+"]").attr("freight",newFreight);
								$(".orderFreight[orderId="+orderId+"]").html(newFreight);
								layer.closeAll('page');
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('修改成功'),2000);
							}else{
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('修改失败'),2000);
							}
						}
					});
				});
			}
		}); 
	});
	
	//修改价格
	$(".orderMoneyModal").click(function(){
		//订单id
		var orderId=$(this).attr("orderId");
		//会员名
		var buyer=$(this).attr("buyer");
		//原价格
		var oldAmountPaid=$(this).attr("amountPaid");
		$(".updateOrderId").html(orderId);
		$(".updateOrderBuyer").html(buyer);
		$(".updateOrderMoney").attr("value",oldAmountPaid);
		var content=$("#orderMoneyModal").html();
		layer.open({
			type: 1,
			title:fy.getMsg('修改价格'),
			area: ['460px', '210px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				$(layero).find(".updateAmountPaidBtn").click(function(){
					var newAmountPaid=$(this).parent().parent().find('#amountPaidInput').val();
					if(jsValidate){
						if (newAmountPaid==undefined||newAmountPaid.length==0) {
							fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请输入价格'),2000);
					    	return false;
						}
						var pattern=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
						if(!pattern.test(newAmountPaid)){
							fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('价格只能是整数或两位以内的小数'),2000);
					    	return false;
						}
					}
					$.ajax({
						url:ctxs+"/trade/tradeOrder/edit.htm",
						type:'POST',
						data:{"orderId":orderId,"offsetAmount":newAmountPaid,"type":2},
						dataType:'json',
						success:function(data){
							if(data == '1'){
								$(".orderMoneyBtn[orderId="+orderId+"]").attr("amountPaid",newAmountPaid);
								$(".orderAmountPaid[orderId="+orderId+"]").html(newAmountPaid);
								$(".offsetAmount[orderId="+orderId+"]").val(newAmountPaid);
								layer.closeAll('page');
								fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('修改成功'),2000);
							}else{
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('修改失败'),2000);
							}
						}
					});
				});
			}
		});
	});
	
	//发货的弹出框
	$(".shipmentModal").click(function(){
		var orderId=$(this).attr("orderId");
		var myRemoteUrl=ctxs+"/trade/tradeOrder/deliverGoods1.htm?orderId="+orderId;
		$.post(myRemoteUrl, {}, function(str){
				layer.open({
				type: 1,
				title:fy.getMsg('发货'),
				area: ['600px', '330px'],
				shadeClose: true, //点击遮罩关闭
				content: str, //注意，如果str是object，那么需要字符拼接。
				success: function(layero, index){
					//发货框中--编辑收货人信息
					$(layero).find(".finishEdit").click(function(){
						//订单号
						var orderId=$('span[name=orderId]').text();
						//收货人
						var consignee=$('input[name=consignee]').val();
						//电话
						var phone=$('input[name=phone]').val();
						//省id
						var provinceId=$('input[name=provinceId]').val();
						//市id
						var cityId=$('input[name=cityId]').val();
						//县id
						var districtId=$('input[name=districtId]').val();
						//省名称
						var provinceName;
						//市名称
						var cityName;
						//县名称
						var districtName;
						//详细地址
						var detailedAddress=$('input[name=detailedAddress]').val();
						//原省名称
						var oldProvinceName=$('input[name=oldProvinceName]').val();
						//原市名称
						var oldCityName=$('input[name=oldCityName]').val();
						//原县名称
						var oldDistrictName=$('input[name=oldDistrictName]').val();
						//如果没有选择地址(省、市、县)，则使用原地址，如果选择了地址，使用现地址
						if(provinceId =="" && cityId==""  && districtId==""){
							provinceName=oldProvinceName;
							cityName=oldCityName;
							districtName=oldDistrictName;
						}else{
							if(provinceId ==""){
								provinceName="";
							}else{
								provinceName=$('#provinceName').text();
							}
							if(cityId ==""){
								cityName="";
							}else{
								cityName=$('#cityName').text();
							}
							if(districtId ==""){
								districtName="";
							}else{
								districtName=$('#districtName').text();
							}
						}
						if(jsValidate){
							//验证收货人
							if (consignee==""|| consignee==null) {
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请输入收货人'),2000);
						    	return false;
							}
							if (consignee.length>64) {
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('收货人最大长度不能超过64字符'),2000);
						    	return false;
							}
							//验证电话
							if (phone==""|| phone==null) {
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请输入电话'),2000);
						    	return false;
							}
							if (phone.length>64) {
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('电话最大长度不能超过64字符'),2000);
						    	return false;
							}
							var pattern=/^1[0-9]{10}$/;
							if(!pattern.test(phone)){
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请输入正确的手机号'),2000);
						    	return false;
							}
							//验证详细地址
							if (detailedAddress==""||detailedAddress==null) {
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请输入输入详细地址'),2000);
						    	return false;
							}
							if (detailedAddress.length>256) {
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('详细地址最大长度不能超过255字符'),2000);
						    	return false;
							}
						}
						//卖家留言
						var sellerMemo=$(".sellerMemo").val();
						$.ajax({
							url:ctxs+"/trade/tradeOrder/edit.htm",
							type:'POST',
							data:{"orderId":orderId,"consignee":consignee,"phone":phone,"provinceName":provinceName,
								"cityName":cityName,"districtName":districtName,"detailedAddress":detailedAddress,"sellerMemo":sellerMemo},
							dataType:'json',
							success:function(data){
								if(data == '1'){
									$("span[name='oldConsigneeMsg']").text(consignee+","+phone+","+provinceName+" "+cityName+" "
											+districtName+" "+detailedAddress);
									$('#sui-msg').css('display','none');
								}else{
									fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('修改失败'),2000);
								}
							}
						});
					});
					
					//修改发货
					$(layero).find(".deliverGoods").click(function(){
						//物流公司id
						var logisticsTemplateId=$("input[name='logisticsTemplateId']").val();
						//运单号
						var trackingNo=$("input[name='trackingNo']").val();
						//备注
						var sellerMemo=$("textarea[name='sellerMemo']").val();
						//物流公司名称
						var logisticsTemplateName;
						//是否需物流
						var isNeedLogistics="0";
						if(logisticsTemplateId=="-1"){
							logisticsTemplateName="";
							logisticsTemplateId="";
						}else{
							isNeedLogistics="1";
							logisticsTemplateName=$("span[name='logisticsTemplateName']").text();
							if(trackingNo==""){
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请输入运单号'),2000);
						    	return false;
							}
						}
						$.ajax({
							url:ctxs+"/trade/tradeOrder/deliverGoods2.htm",
							type:'POST',
							data:{"orderId":orderId,"logisticsTemplateId":logisticsTemplateId,"trackingNo":trackingNo,
								"sellerMemo":sellerMemo,"logisticsTemplateName":logisticsTemplateName,"isNeedLogistics":isNeedLogistics},
							dataType:'json',
							success:function(data){
								if(data == '1'){
									layer.closeAll('page');
									$(".shipmentModal[orderId="+orderId+"]").text(fy.getMsg('编辑发货'));
									$(".status[orderId="+orderId+"]").html(fy.getMsg('待收货'));
									$(".freightBtn[orderId="+orderId+"]").css("display","none");
									$(".orderMoneyBtn[orderId="+orderId+"]").css("display","none");
									fdp.msg("<i class='fa fa-smile-o' style='font-size:24px;color:green'></i> "+fy.getMsg('修改成功'),2000);
								}else{
									fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('修改失败'),2000);
								}
							}
						});
						
					});
				}
			});
		});
	});

	//查看物流的弹出框
	$(".logisticsView").click(function(){
		var orderId=$(this).attr("orderId");
		var myRemoteUrl=ctxs+"/trade/tradeOrder/showLogisticsMsg.htm?orderId="+orderId;
		$.post(myRemoteUrl, {}, function(str){
			layer.open({
				type: 1,
				title:fy.getMsg('查看物流'),
				area: ['600px', '400px'],
				shadeClose: true, //点击遮罩关闭
				btn: [fy.getMsg('关闭')],
				content: str
			}); 
		});
	});
	
	//搜索、导出点击事件
	$("#searchForm .formBtn").click(function(){
		var id=$(this).attr("id");
		if("searchBtn"==id){
			$(".isSelect").val("1");
			$("#searchForm").submit();
		}else{
			fdp.confirm(fy.getMsg('确定要导出么？'),function(){},function(){
				$(".isSelect").val("0");
				$("#searchForm").attr("action",ctxs+"/trade/tradeOrder/exportOrder.htm");
				$("#searchForm").submit();
			}); 
		}
	});
	
	//搜索
	$("#searchForm").submit(function(){
		//处理下单时间
		var endPlaceOrderTime=$(this).find('input[name="endPlaceOrderTime"]').val();
		if(endPlaceOrderTime!=null && endPlaceOrderTime!=""){
			endPlacesOrderTime2=endPlaceOrderTime.split(" "); 
			$(this).find('input[name="endPlaceOrderTime"]').val(endPlacesOrderTime2[0]+" 23:59:59");
		}
		var isSelect=$(".isSelect").val();
		if("1"==isSelect){
			setTimeout(function(){
				//如果1.5秒内未完成操作，则给出请等待的提示
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
			},1500);
		}
	});

});
