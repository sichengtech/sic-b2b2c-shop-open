$(function(){
	orderPrice();
	//保存发票按钮点击事件
	$(".addInvoiceBtn").click(function(){
		if($("input[name='deliver']:checked").attr("isNew") =="1"){
			$("#inputForm").submit();
		}else{
			var deliverId=$("input[name='deliver']:checked").attr("deliverId");
			var deliverContent=$("input[name='deliver']:checked").next("label").text();
			$(".invoiceMsg").text(deliverContent);
			$("input[name='deliverId']").val(deliverId);
			operate();
		}
	});
	
	//添加发票
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"deliverTitle":{required: true,maxlength:64},
				"deliverContent":{required: true,maxlength:255}
			},
			messages: {
				"deliverTitle":{required: fy.getMsg('请输入发票抬头'),maxlength:fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符')},
				"deliverContent":{required: fy.getMsg('请输入发票内容'),maxlength:fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符')}
			},
			submitHandler: function(form){
				//stat==1,提交订单，否则保存发票
				if($("#inputForm").attr("stat") =="1"){
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}else{
					var deliverType=$("input[name='deliverType']:checked").val();
					var deliverTitle=$("input[name='deliverTitle']").val();
					var deliverContent=$("input[name='deliverContent']").val();
					var deliverTypeName=$("input[name='deliverType']:checked").next("label").text();
					$.ajax({
						url:ctxm+"/trade/tradeDeliver/save2.htm",
						type:'POST',
						data:{"deliverType":deliverType,"deliverTitle":deliverTitle,"deliverContent":deliverContent},
						dataType:'json',
						success:function(data){
							if(data!=null && data.message == "success"){
								$(".invoiceMsg").text(deliverTypeName+" "+deliverTitle+" "+" "+deliverContent);
								$("input[name='deliverId']").val(data.tradeDeliver.deliverId);
								operate();
							}else{
								fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+data.message,2000);
							}
						}
					});
				}
			}
		});
	}
	
	//添加发票后的操作
	function operate(){
		//取消编辑状态
		$(".editDeliver").parents("#editDeliverDiv").attr("class","");
		//显示现添加的发票信息
		$(".defaultInvoice").css("display","");
		//隐藏发票列表
		$(".moreInvoice").css("display","none");
		//显示编辑按钮
		$(".editAddress").css("display","");
		//显示编辑发票按钮
		$(".editDeliver").css("display","");
		$(".noInvoice").css("display","none");
		$(".invoiceMsg").css("display","");
	}
	
	/*
	 * 发票列表点击事件
	 * 点击现有发票信息时，隐藏添加发票信息
	 * 否则显示发票信息
	*/
	$("input[name='deliver']").click(function(){
		if($(this).attr("isNew") == "1"){
			$(".newInvoice").css("display","");
		}else{
			$(".newInvoice").css("display","none");
		}
	});
	
	//不需要发票点击事件
	$(".noInvoiceBtn").click(function(){
		$("input[name='deliverId']").val("");
		//取消编辑状态
		$(this).parents("#editDeliverDiv").attr("class","");
		//隐藏发票列表
		$(".moreInvoice").css("display","none");
		//显示编辑按钮
		$(".editAddress").css("display","");
		//显示编辑发票按钮
		$(".editDeliver").css("display","");
		$(".defaultInvoice").css("display","");
		$(".noInvoice").css("display","");
		$(".invoiceMsg").css("display","none");
	});
	
	//选择发票点击事件
	$("#editDeliverDiv").delegate(".deliverRadio","click",function(){
		if($(this).attr("isNew") == "1"){
			$(".newInvoice").css("display","");
		}else{
			$(".newInvoice").css("display","none");
		}
	});
	//修改收货地址
	$(".editAddress").click(function(){
		var addressId=$("input[name='addressId']").val();
		$.ajax({
			url:ctxm+"/user/memberAddress/getAddress.htm",
			type:'POST',
			data:{},
			dataType:'json',
			success:function(data){
				if(data!=null){
					$(".moreAddress .addressList").html("");
					for(var i=0;i<data.length;i++){
						var checked="";
						if(addressId == data[i].addressId){
							checked="checked";
						}
						var css="";
						if(data[i].isDefault == "0"){
							css="none";
						}
						var rowData={"addressId":data[i].addressId,"checked":checked,"name":data[i].name,"provinceName":data[i].provinceName,"cityName":data[i].cityName,
								"districtName":data[i].districtName,"detailedAddress":data[i].detailedAddress,"mobile":data[i].mobile,"css":css,};//模板的数据
						var addressTpl=render_template("#address_tpl",rowData);//渲染模板
						$(".moreAddress .addressList").prepend(addressTpl);
					}
					//隐藏编辑按钮和默认地址，显示添加按钮和地址列表
					$(".editAddress").css("display","none");
					$(".addAddress").css("display","");
					$(".defaultAddress").css("display","none");
					$(".moreAddress").css("display","");
					//隐藏发票编辑按钮
					$(".editDeliver").css("display","none");
					$("#editMsgDiv").attr("class","editMsgDiv");
				}
			}
		});
		
	});
	
	//选择收货地址
	$("#editMsgDiv").delegate(".addressRadio","click",function(){
		var addressId=$(this).attr("addressId");
		var ids=$("input[name='ids']").val();
		var addressMsg=$(this).siblings("label").html();
		$.ajax({
			url:ctxm+'/trade/tradeOrder/selectAddress.htm',
			type:'post',
			data:{"addressId":addressId,"ids":ids},
			dataType:'json',
			success:function(data){
				if(data !=null && data.message =="success"){
					var expressPriceSum=0;
					for(var i=0;i<data.cartList.length;i++){
						var cart=data.cartList[i];
						//if(cart.productSpu.expressPrice == "-1.0"){
						if(cart.freight == "-1.0"){
							$(".productCount[cartId="+cart.cartId+"]").html("<span class='sui-text-warning noproduct' style='font-size:14px;font-weight: bolder;'>(无货)</span>")
							$(".expressPrice[cartId="+cart.cartId+"]").val("0");
						}else{
							//$(".expressPrice[cartId="+cart.cartId+"]").val(cart.productSpu.expressPrice);
							//$(".productCount[cartId="+cart.cartId+"]").html(cart.productSpu.expressPrice);
							$(".expressPrice[cartId="+cart.cartId+"]").val(cart.freight);
							$(".productCount[cartId="+cart.cartId+"]").html(cart.freight);
						}
						//expressPriceSum+=parseFloat(cart.productSpu.expressPrice);
						expressPriceSum+=parseFloat(cart.freight);
					}
					orderPrice();
					$(".defaultAddress label").html(addressMsg);
					$(".sendAddress").html(addressMsg);
					$("input[name='addressId']").val(addressId);
					$(".addAddress").css("display","none");
					$(".moreAddress").css("display","none");
					$(".editAddress").css("display","");
					$(".defaultAddress").css("display","");
					//显示发票编辑按钮
					$(".editDeliver").css("display","");
					$("#editMsgDiv").attr("class","");
				}else{
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('修改失败'),2000);
				}
			}
		});
	});
	
	//修改发票信息
	$(".editDeliver").click(function(){
		var deliverId=$("input[name='deliverId']").val();
		$.ajax({
			url:ctxm+"/trade/tradeDeliver/getDeliver.htm",
			type:'POST',
			data:{},
			dataType:'json',
			success:function(data){
				if(data!=null){
					$(".moreInvoice .moreInvoiceList").html("");
					var deliverList=data.deliverList;
					var dicts=data.dicts;
					for(var i=0;i<deliverList.length;i++){
						var checked="";
						if(deliverId == deliverList[i].deliverId){
							checked="checked";
						}
						var rowData={"deliverId":deliverList[i].deliverId,"checked":checked,"deliverType":fdp.getDictLabel(dicts,deliverList[i].deliverType,''),"deliverTitle":deliverList[i].deliverTitle,"deliverContent":deliverList[i].deliverContent};//模板的数据
						var deliverTpl=render_template("#deliver_tpl",rowData);//渲染模板
						$(".moreInvoice .moreInvoiceList").prepend(deliverTpl);
					}
					$(".editDeliver").css("display","none");
					$(".defaultInvoice").css("display","none");
					$(".moreInvoice").css("display","");
					//隐藏收货地址编辑按钮
					$(".editAddress").css("display","none");
					$(".editDeliver").parents("#editDeliverDiv").attr("class","editMsgDiv");
					if($("input[name='deliver']:checked").attr("isNew") =="1"){
						$(".newInvoice").css("display","");
					}else{
						$(".newInvoice").css("display","none");
					}
				}
			}
		});
	});
	
	//删除收货地址
	$("#editMsgDiv").delegate(".deleAddress","click",function(){
		var addressId=$(this).attr("addressId");
		layer.confirm(fy.getMsg('确定要删除么？'), {
			btn: [fy.getMsg('确定'),fy.getMsg('关闭')]
		}, function(index){
			$.ajax({
				url:ctxm+'/user/memberAddress/deleteAddress.htm',
				type:'POST',
				data:{"addressId":addressId},
				dataType:'json',
				success:function(data){
					if(data == "1"){
						$("a[addressId="+addressId+"]").parent().remove();
					}else{
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('删除失败'),2000);
					}
					layer.close(index);
				}
			});
		});
	});
	
	//删除发票信息
	$("#editDeliverDiv").delegate(".deleDeliver","click",function(){
		var deliverId=$(this).attr("deliverId");
		layer.confirm(fy.getMsg('确定要删除么？'), {
			btn: [fy.getMsg('确定'),fy.getMsg('关闭')]
		}, function(index){
			$.ajax({
				url:ctxm+'/trade/tradeDeliver/deleteDeliver.htm',
				type:'POST',
				data:{"deliverId":deliverId},
				dataType:'json',
				success:function(data){
					if(data == "1"){
						$("a[deliverId="+deliverId+"]").parent().remove();
					}else{
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>删除失败",2000);
					}
					layer.close(index);
				}
			});
		});
	});
	
	//添加收货地址
	$(".addAddress").click(function(){
		var content=$("#addAddressModal").html();
		layer.open({
			type: 1,
			title:fy.getMsg('添加收货地址'),
			area: ['620px', '390px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				$(layero).find(".addAddressBtn").click(function(){
					var delayDays=$(layero).find('.delayDays').val();
					if(jsValidate){
						$(layero).find("#addressForm").validate({
							rules: {
								"name":{required: true,maxlength:64},
								"detailedAddress":{required: true,maxlength:255},
								"mobile":{required: true,maxlength:64,regex:/^1[0-9]{10}$/},
								"zipCode":{maxlength:64,regex:/^[1-9]\d{5}(?!\d)/}
							},
							messages: {
								"name":{required: fy.getMsg('请输入收货人'),maxlength:fy.getMsg('收货人最大长度不能超过')+ 64 + fy.getMsg('字符'),},
								"detailedAddress":{required: fy.getMsg('请输入详情地址')"",maxlength:fy.getMsg('最大长度不能超过')+ 255 + fy.getMsg('字符'),},
								"mobile":{required: fy.getMsg('请输入电话'),maxlength:fy.getMsg('请输入发票抬头')+ 64 + fy.getMsg('字符'),regex:fy.getMsg('请输入正确格式的手机号')},
								"zipCode":{maxlength:fy.getMsg('最大长度不能超过')+ 64 + fy.getMsg('字符'),regex:fy.getMsg('请输入')+ 6 + fy.getMsg('位数字的邮编')},
							},
							submitHandler: function(form){
								//所选地址
								var countryId=$(layero).find("#countryId").val();
								var provinceId=$(layero).find("#provinceId").val();
								var cityId=$(layero).find("#cityId").val();
								var districtId=$(layero).find("#districtId").val();
								if(provinceId == "" || cityId == "" || districtId == ""){
									fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择完整的地址'),2000);
									return false;
								}
								var countryName=$(layero).find("#countryName").val();
								var provinceName=$(layero).find("#provinceName").text();
								var cityName=$(layero).find("#cityName").text();
								var districtName=$(layero).find("#districtName").text();
								var addressName=$(layero).find("input[name='addressName']").val();
								var name=$(layero).find("input[name='name']").val();
								var detailedAddress=$(layero).find("input[name='detailedAddress']").val();
								var mobile=$(layero).find("input[name='mobile']").val();
								var zipCode=$(layero).find("input[name='zipCode']").val();
								var isDefault="0";
								if($(layero).find("input[name='isDefault']").is(":checked")){
									isDefault="1";
								}
								$.ajax({
									url:ctxm+"/user/memberAddress/addAddress.htm",
									type:'POST',
									data:{"addressName":addressName,"name":name,"detailedAddress":detailedAddress,"mobile":mobile,
										"zipCode":zipCode,"isDefault":isDefault,"countryId":countryId,"countryName":countryName,
										"provinceId":provinceId,"cityId":cityId,"districtId":districtId,"provinceName":provinceName,
										"cityName":cityName,"districtName":districtName,},
									dataType:'json',
									success:function(data){
										if(data!=null && data.message == "success"){
											$("input[name='addressId']").val(data.memberAddress.addressId);
											$(".defaultAddress").find(".name").text(data.memberAddress.name);
											$(".defaultAddress").find(".address").text(data.memberAddress.provinceName+data.memberAddress.cityName
												+data.memberAddress.districtName+data.memberAddress.detailedAddress);
											$(".defaultAddress").find(".mobile").text(data.memberAddress.mobile);
											//$(".defaultAddress").css("display","none");
											if(data.memberAddress.isDefault=='1'){
												$(".defaultAddress").css("display","");
											}else{
												$(".defaultAddress").css("display","none");
											}
											$(".addAddress").css("display","none");
											$(".moreAddress").css("display","none");
											$(".editAddress").css("display","");
											$(".defaultAddress").css("display","");
											//显示发票编辑按钮
											$(".editDeliver").css("display","");
											$("#editMsgDiv").attr("class","");
											layer.close(index);
										}else{
											fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+data.message,2000);
										}
									}
								});
							}
						});
					}
				});
			}
		}); 
	});
	
	//计算每条订单的总金额
	function orderPrice(){
		//总商品金额
		var totalProductSumPrice=0;
		//总商品运费
		var totalProductFreight=0;
		//总订单金额
		var orderPrice=0;
		$(".productTable").each(function(){
			//每个订单的金额
			var orderSumPrice=0;
			//每个订单运费
			var orderSumFreight=0;
			$(this).find(".productPriceSum").each(function(){
				orderSumPrice+=parseFloat($(this).find("span").text());
				orderSumFreight+=parseFloat($(this).find("input").val());
			});
			$(this).find("font[class='orderSumPrice']").text(orderSumPrice);
			$(this).find("input[class='orderSumPrice']").val(orderSumPrice);
			$(this).find(".orderSumFreight").text(orderSumFreight);
			$(this).find("input[class='orderSumFreight']").val(orderSumFreight);
			//$(this).find("tfoot ")
			totalProductSumPrice+=orderSumPrice;
			totalProductFreight+=orderSumFreight;
		});
		$(".totalProductSumPrice").text("￥"+totalProductSumPrice);
		$(".totalProductFreight").text(totalProductFreight);
		orderPrice=totalProductSumPrice+totalProductFreight;
		$(".orderPrice").text(orderPrice);
		$("input[name='orderPrice']").val(orderPrice);
	}
	
	/**
     * 渲染模板
     * js模板 Mustache
     * 
     * templateId：模板ID，String类型
     * rowData：数据，object类型
     */
    function render_template(templateId,rowData){
    	var tpl = $(templateId).html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
    	var html=Mustache.render(tpl, rowData);//渲染模板
    	return html;
    }
    //提交订单
	$(".addOrder").click(function(){
		//收货地址id
		var addressId=$("input[name='addressId']").val();
		if(addressId == "" || addressId==null || typeof(addressId)=="undefined"){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择收货地址'),2000);
			return false;
		}
		if($(".noproduct").length >0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('您购买的商品无货了，请购买其他商品'),2000);
			return false;
		}
		//运费
		var freight=$(".totalProductFreight").text();
		var amountPaid=$(".orderPrice").text();
		$("input[name='freight']").val(freight);
		$("input[name='amountPaid']").val(amountPaid);
		$("#inputForm").attr("stat","1");
		$("#inputForm").submit();
	});
});

