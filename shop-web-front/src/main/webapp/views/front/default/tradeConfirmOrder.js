$(function(){
	
	/**
	 * 计算每条订单的总金额
	 * */
	orderPrice();
	
	/**
	 * 保存发票按钮点击事件
	 * */
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
	
	/**
	 * 添加发票
	 * */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"companyName":{maxlength:64},
				"axpayerIdentityNumber":{maxlength:64},
				"openingBank":{maxlength:64},
				"accountNumber":{maxlength:64},
				"address":{maxlength:128},
				"phone":{regex:/^0\d{2,3}-?\d{7,8}$|^1\d{10}$/,maxlength:64},
			},
			messages: {
				"companyName":{maxlength:fy.getMsg('超长字符','64')},
				"axpayerIdentityNumber":{maxlength:fy.getMsg('超长字符','64')},
				"openingBank":{maxlength:fy.getMsg('超长字符','64')},
				"accountNumber":{maxlength:fy.getMsg('超长字符','64')},
				"address":{maxlength:fy.getMsg('超长字符','128')},
				"phone":{regex:fy.getMsg('请输入正确的值',fy.getMsg('手机号或电话')),maxlength:fy.getMsg('超长字符','64')},
			},
			submitHandler: function(form){
				//stat==1,提交订单，否则保存发票
				if($("#inputForm").attr("stat") =="1"){
					layer.msg(fy.getMsg('正在提交，请稍等'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}else{
					var deliverType=$("input[name='deliverType']:checked").val();
					var deliverTypeName=$("input[name='deliverType']:checked").next("label").text();
					var companyName=$("input[name='companyName']").val();
					var axpayerIdentityNumber=$("input[name='axpayerIdentityNumber']").val();
					var openingBank=$("input[name='openingBank']").val();
					var accountNumber=$("input[name='accountNumber']").val();
					var address=$("input[name='address']").val();
					var phone=$("input[name='phone']").val();
					$.ajax({
						url:ctxm+"/trade/tradeDeliver/save2.htm",
						type:'POST',
						data:{"deliverType":deliverType,"companyName":companyName,"axpayerIdentityNumber":axpayerIdentityNumber,
							"openingBank":openingBank,"accountNumber":accountNumber,"address":address,"phone":phone},
						dataType:'json',
						success:function(data){
							if(data!=null && data.message == "success"){
								$(".invoiceMsg").text(deliverTypeName+" "+companyName);
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
	
	/**
	 * 添加发票后的操作
	 * */
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
	
	/**
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
	
	/**
	 * 不需要发票点击事件
	 * */
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
	
	/**
	 * 选择发票点击事件
	 * */
	$("#editDeliverDiv").delegate(".deliverRadio","click",function(){
		if($(this).attr("isNew") == "1"){
			$("input[name='companyName']").val("");
			$("input[name='axpayerIdentityNumber']").val("");
			$("input[name='openingBank']").val("");
			$("input[name='accountNumber']").val("");
			$("input[name='address']").val("");
			$("input[name='phone']").val("");
			$(".newInvoice").css("display","");
		}else{
			$(".newInvoice").css("display","none");
		}
	});
	
	/**
	 * 修改发票信息
	 * */
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
					var deliver_tpl=$("#deliver_tpl").html();
					if(typeof(deliverList)!="undefined" && deliverList!=null){
						for(var i=0;i<deliverList.length;i++){
							var checked="";
							if(deliverId == deliverList[i].deliverId){
								checked="checked";
							}
							var companyName="";
							if(typeof(deliverList[i].companyName)!="undefined"){
								companyName=deliverList[i].companyName;
							}
							var rowData={"deliverId":deliverList[i].deliverId,"checked":checked,"deliverType":fdp.getDictLabel(dicts,deliverList[i].deliverType,''),"companyName":companyName};//模板的数据
							var deliverTpl=render(deliver_tpl,rowData);//渲染模板
							$(".moreInvoice .moreInvoiceList").prepend(deliverTpl);
						}
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
	
	/**
	 * 删除发票信息
	 * */
	$("#editDeliverDiv").delegate(".deleDeliver","click",function(){
		var deliverId=$(this).attr("deliverId");
		fdp.confirm(fy.getMsg('确定要删除么'),function(){
		},function(){
			$.ajax({
				url:ctxm+'/trade/tradeDeliver/deleteDeliver.htm',
				type:'POST',
				data:{"deliverId":deliverId},
				dataType:'json',
				success:function(data){
					if(data == "1"){
						$("a[deliverId="+deliverId+"]").parent().remove();
					}else{
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('删除失败'),2000);
					}
					layer.close(index);
				}
			});
		});
	});
	
	/**
	 * 修改收货地址
	 * */
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
					var address_tpl=$("#address_tpl").html();
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
						var addressTpl=render(address_tpl,rowData);//渲染模板
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
	
	/**
	 * 选择收货地址
	 * */
	$("#editMsgDiv").delegate(".addressRadio","click",function(){
		var addressId=$(this).attr("addressId");
		var ids=$("input[name='ids']").val();
		if(ids==""){
			$("input[name='cartId']").each(function(){
				ids+=$(this).val()+",";
			});
		}
		var addressMsg=$(this).siblings("label").html();
		selectAddress(addressId,ids,addressMsg);
	});
	
	/**
	 * 选择收货地址方法
	 * */
	function selectAddress(addressId,ids,addressMsg){
		$.ajax({
			url:ctxm+'/trade/tradeOrder/selectAddress.htm',
			type:'post',
			data:{"addressId":addressId,"ids":ids},
			dataType:'json',
			success:function(data){
				if(data !=null && data.message =="success"){
					var expressPriceSum=0;
					$(".expressPrice").val("0");
					for(var i=0;i<data.cartList.length;i++){
						var cart=data.cartList[i];
						if(cart.freight == "-1.0"){
							$(".productCount[pid="+cart.pid+"]").find(".noproduct").css("display","");
							$(".productCount[pid="+cart.pid+"]").find(".hasProduct").css("display","none");
							$(".expressPrice[pid="+cart.cartId+"]").val("0");
						}else{
							$(".expressPrice[cartId="+cart.cartId+"]").val(cart.freight);
							$(".productCount[pid="+cart.pid+"]").find(".noproduct").css("display","none");
							$(".productCount[pid="+cart.pid+"]").find(".hasProduct").css("display","");
						}
						//expressPriceSum+=parseFloat(cart.productSpu.expressPrice);
						expressPriceSum+=parseFloat(cart.freight);
					}
					orderPrice();
					if(addressMsg!="" && addressMsg!=null){
						$(".defaultAddress label").html(addressMsg);
						$(".sendAddress").html(addressMsg);
					}
					$("input[name='addressId']").val(addressId);
					$(".addAddress").css("display","none");
					$(".moreAddress").css("display","none");
					$(".editAddress").css("display","");
					$(".defaultAddress").css("display","");
					//显示发票编辑按钮
					$(".editDeliver").css("display","");
					$("#editMsgDiv").attr("class","");
				}else{
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>修改失败",2000);
				}
			}
		});
	}
	
	/**
	 * 删除收货地址
	 * */
	$("#editMsgDiv").delegate(".deleAddress","click",function(){
		var addressId=$(this).attr("addressId");
		fdp.confirm('确定要删除么',function(){
		},function(){
			$.ajax({
				url:ctxm+'/user/memberAddress/deleteAddress.htm',
				type:'POST',
				data:{"addressId":addressId},
				dataType:'json',
				success:function(data){
					if(data == "1"){
						$("a[addressId="+addressId+"]").parent().remove();
						//判断当前有没有地址，如果没有地址清除"寄送至"后的地址信息
						if($("ul.addressList li").length==0){
							$(".sendAddress").html("");
							$("input[name='addressId']").val("");
						}
					}else{
						fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('删除失败'),2000);
					}
				}
			});
		});
	});
	
	/**
	 * 添加收货地址
	 * */
	$(".addAddress").click(function(){
		var content=$("#addAddressModal").html();
		openAddressBox(content,"");
	});
	
	/**
	 * 编辑收货地址
	 * */
	$("#editMsgDiv").delegate(".editAddress2","click",function(){
		var addressId=$(this).siblings("input[name='address']").attr("addressid");
		var content=$("#addAddressModal").html();
		openAddressBox(content,addressId);
	});

	/**
	 * 给地址弹框中赋值
	 * */
	function addressAssignment(layero,addressId){
		$.ajax({
			url:ctxm+"/user/memberAddress/getAddress.htm",
			type:'POST',
			data:{"addressId":addressId},
			dataType:'json',
			success:function(data){
				if(data==null){
					return;
				}
				$(layero).find("input[name='addressId']").attr("value",addressId);
				$(layero).find("input[name='addressName']").attr("value",data[0].addressName);
				$(layero).find("input[name='name']").attr("value",data[0].name);
				$(layero).find("input[name='provinceId']").attr("value",data[0].provinceId);
				$(layero).find("input[name='cityId']").attr("value",data[0].cityId);
				$(layero).find("input[name='districtId']").attr("value",data[0].districtId);
				$(layero).find("span[id='provinceName']").text(data[0].provinceName);
				$(layero).find("span[id='cityName']").text(data[0].cityName);
				$(layero).find("span[id='districtName']").text(data[0].districtName);
				$(layero).find("input[name='detailedAddress']").attr("value",data[0].detailedAddress);
				$(layero).find("input[name='mobile']").attr("value",data[0].mobile);
				$(layero).find("input[name='zipCode']").attr("value",data[0].zipCode);
				if("1"==data[0].isDefault){
					$(layero).find("input[name='isDefault']").attr('checked', true);
				}else{
					$(layero).find("input[name='isDefault']").attr('checked', false);
				}
			}
		});
	}
	
	/**
	 * 打开地址弹出框
	 * */
	function openAddressBox(content,addressId){
		var title=fy.getMsg('添加收货地址');
		if(typeof(addressId)!="undefined" && addressId!=""){
			title=fy.getMsg('编辑收货地址');
		}
		layer.open({
			type: 1,
			title:title,
			area: ['730px', '420px'],
			shadeClose: true, //点击遮罩关闭
			//btn: ['确定','关闭'],
			content: content,
			success: function(layero, index){
				//如果是编辑，需要回显
				if(typeof(addressId)!="undefined" && addressId!=""){
					addressAssignment(layero,addressId);
				}
				$(layero).find(".addAddressBtn").click(function(){
					if(jsValidate){
						var isPassValidate=false;
						$(layero).find("#addressForm").validate({
							rules: {
								"name":{required: true,maxlength:64},
								"detailedAddress":{required: true,maxlength:255},
								"mobile":{required: true,maxlength:11,regex:/^1[0-9]{10}$/},
								"zipCode":{maxlength:64,regex:/^[1-9]\d{5}(?!\d)/},
							},
							messages: {
								"name":{required: fy.getMsg('请输入',fy.getMsg('收货人')),maxlength:fy.getMsg('超长字符','64'),},
								"detailedAddress":{required:fy.getMsg('请输入',fy.getMsg("详细地址")),maxlength:fy.getMsg('超长字符','255'),},
								"mobile":{required:fy.getMsg('请输入',fy.getMsg("手机号码")),maxlength:fy.getMsg('超长字符','11'),regex:fy.getMsg('请输入正确的值',fy.getMsg("手机号码"))},
								"zipCode":{maxlength:fy.getMsg('超长字符','6'),regex:fy.getMsg('请输入',fy.getMsg('6位数字的邮编'))},
							},
							submitHandler: function(form){
								isPassValidate=true;
								var provinceId=$(layero).find("#provinceId").val();
								var cityId=$(layero).find("#cityId").val();
								var districtId=$(layero).find("#districtId").val();
								if(provinceId == "" || cityId == "" || districtId == ""){
									isPassValidate=false;
									fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择完整的地址'),2000);
								}
								if(isPassValidate){
									addAddress(layero,index);
								}
							}
						});
					}else{
						addAddress(layero,index);
					}
				});
			}
		}); 
	}
	
	/**
	 * 添加收货地址方法
	 * */
	function addAddress(layero,index){
		//所选地址
		var addressId=$(layero).find("input[name='addressId']").val();
		var countryId=$(layero).find("#countryId").val();
		var provinceId=$(layero).find("#provinceId").val();
		var cityId=$(layero).find("#cityId").val();
		var districtId=$(layero).find("#districtId").val();
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
		var ids=$("input[name='ids']").val();
		if(ids==""){
			$("input[name='cartId']").each(function(){
				ids+=$(this).val()+",";
			});
		}
		$.ajax({
			url:ctxm+"/user/memberAddress/addAddress.htm",
			type:'POST',
			data:{"addressId":addressId,"addressName":addressName,"name":name,"detailedAddress":detailedAddress,"mobile":mobile,
				"zipCode":zipCode,"isDefault":isDefault,"countryId":countryId,"countryName":countryName,
				"provinceId":provinceId,"cityId":cityId,"districtId":districtId,"provinceName":provinceName,
				"cityName":cityName,"districtName":districtName},
			dataType:'json',
			success:function(data){
				if(data!=null && data.message == "success"){
					//$("input[name='addressId']").val(data.memberAddress.addressId);
					//修改收货人信息
					var addressName=data.memberAddress.name;
					var address=data.memberAddress.provinceName+" "+data.memberAddress.cityName+" "+data.memberAddress.districtName+" "+data.memberAddress.detailedAddress;
					var mobile=data.memberAddress.mobile;
					$(".defaultAddress").find(".name").text(addressName);
					$(".defaultAddress").find(".address").text(address);
					$(".defaultAddress").find(".mobile").text(mobile);
					//$(".defaultAddress").css("display","none");
					/*if(data.memberAddress.isDefault=='1'){
						$(".defaultAddress").css("display","");
					}else{
						$(".defaultAddress").css("display","none");
					}*/
					//修改“寄送至：”的地址：
					$(".sendAddress").html(addressName+" "+address+" "+mobile);
					/*$(".addAddress").css("display","none");
					$(".moreAddress").css("display","none");
					$(".editAddress").css("display","");
					$(".defaultAddress").css("display","");*/
					//显示发票编辑按钮
					/*$(".editDeliver").css("display","");
					$("#editMsgDiv").attr("class","");*/
					//修改运费
					var addressId2=data.memberAddress.addressId;
					selectAddress(addressId2,ids,"");
					layer.close(index);
				}else{
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+data.message,2000);
				}
			}
		});
	}
	
	/**
	 * 计算每条订单的总金额
	 * */
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
			//var orderSumFreight=parseFloat($(this).find(".productPriceSum").find("input").last().val());
			$(this).find(".productPriceSum").each(function(){
				orderSumPrice+=parseFloat($(this).find("span").text());
				orderSumFreight+=parseFloat($(this).find("input").val());
			});
			totalProductSumPrice+=orderSumPrice;
			totalProductFreight+=orderSumFreight;
			$(this).find("font[class='orderSumPrice']").text(orderSumPrice.toFixed(2));
			$(this).find("input[class='orderSumPrice']").val(orderSumPrice.toFixed(2));
			$(this).find("span[class='orderSumFreight']").text(orderSumFreight.toFixed(2));
			$(this).find("tbody .orderSumFreight").text(orderSumFreight.toFixed(2));
			$(this).find("input[class='orderSumFreight']").val(orderSumFreight.toFixed(2));
		});
		$(".totalProductSumPrice").text(fy.getMsg("元")+totalProductSumPrice.toFixed(2));
		$(".totalProductFreight").text(totalProductFreight.toFixed(2));
		orderPrice=totalProductSumPrice+totalProductFreight;
		$(".orderPrice").text(orderPrice.toFixed(2));
		$("input[name='orderPrice']").val(orderPrice.toFixed(2));
	}
	
    /**
	 * 渲染模板
	 */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
    
    /**
     * 提交订单
     * */
	$(".addOrder").click(function(){
		//收货地址id
		var addressId=$("input[name='addressId']").val();
		if(addressId == "" || addressId==null || typeof(addressId)=="undefined"){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择收货地址'),2000);
			return false;
		}
		if($(".noproduct").not(".noproduct:hidden").length >0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('您购买的商品无货了，请购买其他商品'),2000);
			return false;
		}
		//运费
		var freight=$(".totalProductFreight").text();
		var amountPaid=$(".orderPrice").text();
		$("input[name='freight']").val(freight);
		$("input[name='amountPaid']").val(amountPaid);
		$("#inputForm").attr("stat","1");
		var ids=$("input[name='ids']").val();
		if(ids==""){
			$("input[name='cartId']").each(function(){
				ids+=$(this).val()+",";
			});
		}
		var keys=$("input[name='keys']").val();
		var addressId=$("input[name='addressId']").val();
		var deliverId=$("input[name='deliverId']").val();
		var storeId=$("input[name='storeId']").val();
		var _data={"freight":freight,"amountPaid":amountPaid,"ids":ids,"keys":keys,"addressId":addressId,
				"deliverId":deliverId,"storeId":storeId};
		//每条订单的总额
		$("input[class='orderSumPrice']").each(function(){
			var name=$(this).attr("name");
			var val=$(this).val();
			_data[name] = val;
		});
		//每条订单的运费
		$("input[class='orderSumFreight']").each(function(){
			var name=$(this).attr("name");
			var val=$(this).val();
			_data[name] = val;
		});
		//每条订单的留言
		$(".order-msg-textarea").each(function(){
			var name=$(this).attr("name");
			var val=$(this).val();
			_data[name] = val;
		});
		//每个商品的优惠
		$(".benefitTd").each(function(){
			var name=$(this).find("span").attr("name");
			var val=$(this).find("span").text();
			_data[name] = val;
		});
		$.ajax({
			url:ctxf+"/trade/order/addOrder.htm",
			type:'POST',
			data:_data,
			dataType:'json',
			success:function(data){
				if(data.success){
					$("input[name='orderIds']").val(data.orderIds);
					$("#inputForm").attr("action",ctxf+data.returnUrl);
					$("#inputForm").attr("stat","1");
					$("#inputForm").submit();
				}else{
					var message=fy.getMsg('Order creation failed');
					if(data.message!=""){
						message=data.message;
					}
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+message,2000);
					return false;
				}
			}
		});
		
	});

	//刚进页面重新计算运费
	var addressId=$("input[name='addressId']").val();
	if(addressId!=null){
		//购物车ids
		var ids=$("input[name='ids']").val();
		if(ids==""){
			$("input[name='cartId']").each(function(){
				ids+=$(this).val()+",";
			});
		}
		var addressMsg=$(".addressRadio[addressid='"+addressId+"']").length;
		selectAddress(addressId,ids,"");
	}

});

