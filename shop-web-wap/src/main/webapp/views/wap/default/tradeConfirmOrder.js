$(function(){
	
	var tradeDeliverId = "";//发票id
	var companyName = "";//公司名称
	var isCancel = "";//是否取消发票
	/**
	 * 从发票信息页面返回我的订单页面
	 */
	$("body").delegate(".returnOrder","click",function(){
		isCancel = "false";
		tradeDeliverId = $(this).parent().parent().find(".default-invoice-input").attr("for");//发票id
		companyName =$(this).find(".invoice-companyName").html();//公司名称
		$.router.back();
	});
	$("body").delegate("#backTradeConfirmOrder","click",function(){
		isCancel = "true";
		tradeDeliverId = "";
		$.router.back();
	});
	
	/**
	 * 从收货地址列表页面返回我的订单页面
	 */
	var addressId = "";//收货地址id
	var name = "";//收货人
	var mobile = "";//收货人手机号
	var addressDetail = "";//详细地址
	var isUserAddress=false;//是否存在选中的收货地址
	$("body").delegate(".orderEntry","click",function(){
		addressId=$(this).attr("addressid");
		name=$(this).find(".l.name").html();
		mobile=$(this).find(".r.phone").html();
		addressDetail=$(this).find(".detail-address").html();
		isUserAddress=true;
		$.router.back();
	});
	
	/**
	 * 路由切换(动画开始)
	 */
	var addressIdArray = [];//存储收货地址列表页的收货地址id
	$(document).on("beforePageSwitch", function(e, pageId, $page) {
		if(pageId == "invoiceList") {
			var tradeDeliverIdArray = [];
			$(".returnOrder").parent().parent().find(".default-invoice-input").each(function(){
				tradeDeliverIdArray.push($(this).attr("for"));
			});
			if(tradeDeliverIdArray.length==0){
				isCancel = "true";
			}else{
				if(contains(tradeDeliverIdArray, tradeDeliverId)){
					isCancel = "false";
				}else{
					isCancel = "true";
				}
			};
		}
		//路由切换(动画开始),获取收货地址列表页的收货地址id
		if(pageId == "addressList") {
			addressIdArray= [];
			$(".orderEntry").each(function(){
				addressIdArray.push($(this).attr("addressId"));
			});
		}
	});
	
	/**
	 * 路由切换(动画结束)
	 */
	$(document).on("pageAnimationEnd", function(e, pageId, $page) {
	  	if(pageId == "userOrderOk") {
	  		//从发票列表页面返回确认订单页面
	  		assignmentTradeDeliverId(tradeDeliverId,companyName,isCancel);
	  		//从收货地址列表页面返回我的订单页面,设置收回地址信息
	  		if(addressId==null || addressId==""){
	  			addressId=$(".my-address").attr("addressId");//收货地址id
	  			name=$(".my-address .my-name").text();//收货人
	  			mobile=$(".my-address .my-phone").text();//收货人手机号
	  			addressDetail=$(".my-address .my-adds p").text();//详细地址
	  		}
	  		if(addressIdArray.length==0){
	  			isUserAddress=false;
	  		}else{
	  			if(contains(addressIdArray, addressId)){
					isUserAddress=true;
				}else{
					isUserAddress=false;
				}
	  		}
	  		assignmentAddress(addressId,name,mobile,addressDetail,isUserAddress);
	  		$(".weui-picker-modal-visible").remove();
	  	}
	  	if(pageId == "invoiceList") {
	  		$(".weui-picker-modal-visible").remove();
			//移除收货地址列表和收货地址表单的js
	  		$("script[src='"+ctx+"/views/wap/default/addressList.js']").remove();
			$("script[src='"+ctx+"/views/wap/default/addressForm.js']").remove();
	  		//加载发票列表页面后，加载发票列表列表js
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverList.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverForm.js']").remove();
	  		var head = $("head");
	        var script = $("<script>");
	        $(script).attr('type','text/javascript');
	        $(script).attr('src',ctx+'/views/wap/default/tradeDeliverList.js'); 
	        $(head).append(script);
	  	}
	  	if(pageId == "invoiceForm") {
	  		//移除收货地址列表和收货地址表单的js
	  		$("script[src='"+ctx+"/views/wap/default/addressList.js']").remove();
			$("script[src='"+ctx+"/views/wap/default/addressForm.js']").remove();
	  		//加载发票表单页面后，加载发票表单js
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverList.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverForm.js']").remove();
	  		var head = $("head");
	        var script = $("<script>");
	        $(script).attr('type','text/javascript');
	        $(script).attr('src',ctx+'/views/wap/default/tradeDeliverForm.js'); 
	        $(head).append(script);
	  	}
	  	if(pageId == "addressList") {
	  		//移除发票列表和发票表单的js
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverList.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverForm.js']").remove();
	  		//加载地址表列表后，加载地址表列表js
			$("script[src='"+ctx+"/views/wap/default/addressList.js']").remove();
			$("script[src='"+ctx+"/views/wap/default/addressForm.js']").remove();
	  		var head = $("head");
	        var script = $("<script>");
	        $(script).attr('type','text/javascript');
	        $(script).attr('src',ctx+'/views/wap/default/addressList.js'); 
	        $(head).append(script);
	  	}
	  	if(pageId == "addressView") {
	  		//移除发票列表和发票表单的js
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverList.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverForm.js']").remove();
	  		//加载地址表列表后，加载地址表列表js
	  		$("script[src='"+ctx+"/views/wap/default/addressList.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/addressForm.js']").remove();
	  		var head = $("head");
	  		var script = $("<script>");
	  		$(script).attr('type','text/javascript');
	  		$(script).attr('src',ctx+'/views/wap/default/addressForm.js'); 
	  		$(head).append(script);
	  	}
	});
	
	/**
	 * 获取商品数据
	 * */
	(function(){
		layer.open({type: 2});
		var data={};
		if(stat==1){
			data={"stat":stat,"ids":ids,"productSumPrice":productSumPrice};
		}else{
			data={"stat":stat,"pid":pid,"skuMsg":skuMsg,"type":type};
		}
		$.ajax({
			type:'GET',
			url: ctxw + '/api/v1/trade/order/confimOrder.htm',
			data:data,
			dataType: 'json',
			//async: false,
			success: function(data){
				if(data==null || data.status!=200){
					layer.closeAll();
					layer.open({content: data.message!=""?data.message:'获取商品数据出现错误',skin: 'msg',time: 2});
					return;
				}
				if(1==stat){
					$(".total .orderSumPrice").text(productSumPrice);
				}
				var carMap=data.data.cartMap;
	        	var cartHtml="";
	        	var priceSum=0;
	        	//是否支持开发票
	        	var invoice=data.data.invoice;
	        	if("1"==invoice){
	        		$(".invoice-div a").removeClass("hide");
	        	}
	        	var pids="";
	        	var skuIds="";
	        	for(var key in carMap){
	        		var cartList=carMap[key];
	        		if(cartList==null || cartList==""){
	        			continue;
	        		}
	        		for(var i=0;i<cartList.length;i++){
	        			var cart=cartList[i];
	        			pids+=cart.pid+",";
	        			skuIds+=cart.skuId+",";
	        		}
	        	}
	        	//根据pids获取商品spu的list	
	        	var productList=getProductSpuList(pids);
	        	//根据skuIds获取商品sku的list
	        	var productSkuList=getProductSkuList(skuIds);
	        	for(var key in carMap){
	        		var cartList=carMap[key];
	        		if(cartList==null || cartList==""){
	        			continue;
	        		}
	        		var deliverGoodsDate="24小时内发货";
	        		var productHtml="";
	        		var orderPrice=0;
	        		var orderCount=0;
	        		for(var i=0;i<cartList.length;i++){
	        			var cart=cartList[i];
	        			var product=null;
	        			//循环productList
	        			if(productList!=null && productList!=""){
	        				for(var j=0;j<productList.length;j++){
	        					if(productList[j].pid==cart.pid){
	        						product=productList[j];
	        						break;
	        					}
	        				}
	        			}
	        			var productImg="";
	        			var productName="";
	        			var sku="";
	        			var pid="";
	        			if(product!=null && product!=""){
	        				pid=product.pid;
	        				productImg=ctxfs+product.image+"@200x200";
	        				productName=product.name;
	        				if(typeof(product.deliverGoodsDate)!="undefined" && product.deliverGoodsDate!=""){
	        					deliverGoodsDate=product.deliverGoodsDate;
	        				}
	        				//循环productSkuList
		        			if(productSkuList!=null && productSkuList!=""){
		        				for(var k=0;k<productSkuList.length;k++){
		        					if(productSkuList[k].skuId==cart.skuId){
		        						sku=getProductSkuContent(productSkuList[k]);
		        					}
		        				}
		        			}
	        			}
	        			orderPrice+=cart.priceSum;
	        			orderCount+=cart.count;
	        			var orderProductTplData={"productImg":productImg,"productName":productName,"sku":sku,"productPrice":cart.price,
	        					"pid":pid,"cartId":cart.cartId,"count":cart.count};
						var order_product_tpl=$("#order_product_tpl").html();
						productHtml+=wx_common.render(order_product_tpl,orderProductTplData);//渲染模板
	        		}
	        		var storeName="";
	        		var storeId="";
	        		if(key.split("-").length>1){
	        			storeName=key.split("-")[0];
	        			storeId=key.split("-")[1];
	        		}
	        		//拼模板
	        		var orderStoreTplData={"storeName":storeName,"products":productHtml,"orderPrice":orderPrice,"deliverGoodsDate":deliverGoodsDate,"storeId":storeId,"orderCount":orderCount};
					var order_store_tpl=$("#order_store_tpl").html();
					cartHtml+=wx_common.render(order_store_tpl,orderStoreTplData);//渲染模板
					priceSum+=cart.priceSum;
	        	}
	        	if(2==stat){
	        		$(".total .orderSumPrice").text(priceSum.toFixed(2));
	        	}
	        	$(".my-address").after(cartHtml);
	            //初始化收货地址
	        	addressOne("",addressIdArray);
	        	layer.closeAll();
	        	wx_common.LazyloadImg();//使用图片延迟加载
			}
		});
	})();
	
	/**
	 * 获取sku的各个规格
	 * */
	function getProductSkuContent(productSku){
		var skuContent="";
		if(productSku==null || productSku==""){
			return skuContent;
		}
		var spec1="";
		var spce2="";
		var spce3="";
		if(productSku.spec1!=null && productSku.spec1.split("_").length>1){
			spec1=productSku.spec1.split("_")[1]+" : "+productSku.spec1V;
		}
		if(productSku.spec2!=null && productSku.spec2.split("_").length>1){
			spce2=", "+productSku.spec2.split("_")[1]+" : "+productSku.spec2V;
		}
		if(productSku.spec3!=null && productSku.spec3.split("_").length>1){
			spce3=", "+productSku.spec3.split("_")[1]+" : "+productSku.spec3V;;
		}
		skuContent=spec1+spce2+spce3
		return skuContent;
	}
	
	/**
	 * 根据多个pid查商品list
	 * */
	function getProductSpuList(pids){
		var productSpuList=null;
		if(typeof(pids)=="undefined" || pids==""){
    		return productSpuList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/list.htm',
	        data:{"pids":pids},
	        dataType: 'json',
	        async:false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	productSpuList=data.data.productList;
	        }
		});
		return productSpuList;
	}
	
	/**
	 * 获取多个skuIds查询商品sku list
	 * */
	function getProductSkuList(skuIds){
		if(skuIds==null || skuIds==""){
			return;
		}
		var productSkuList=null;
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/sku/list.htm',
	        data:{"skuIds":skuIds},
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	productSkuList=data.data;
	        }
		});
		return productSkuList;
	}
	
	/**
     * 提交订单
     * */
	$("body").delegate(".addOrder","click",function(){
		layer.open({type: 2});
		//收货地址id
		var addressId=$(".my-address").attr("addressId");
		if(typeof(addressId)=="undefined" || addressId == "" || addressId==null){
			layer.closeAll();
			layer.open({content: "请选择收货地址",skin: 'msg',time: 2});
			return false;
		}
		
		if($(".order-message .freight.noProduct").length >0){
			layer.closeAll();
			layer.open({content: "您购买的商品无货了，请购买其他商品",skin: 'msg',time: 2});
			return false;
		}
		//运费
		var freight=$(".moneyAll .totalFreight").text();
		var amountPaid=$(".moneyAll .orderSumPrice").text();
		var ids="";
		$(".order-list-ok .order-product").each(function(){
			ids+=$(this).attr("cartId")+",";
		});
		//店铺名-店铺id
		var keys="";
		$(".order-list-ok").each(function(){
			var storeId=$(this).find("label.storeName").attr("storeId");
			var storeName=$(this).find("label.storeName").text();
			keys+=storeName+"-"+storeId+",";
		});
		var deliverId=$(".invoice-div .tradeDeliver").attr("tradedeliverid");
		var _data={"freight":freight,"amountPaid":amountPaid,"ids":ids,"keys":keys,"addressId":addressId,
				"deliverId":deliverId};
		
		$(".order-list-ok").each(function(){
			//每条订单的订单总价
			var orderSumPrice=$(this).find(".order-message font.orderPrice").text();
			var orderSumPriceName=$(this).find(".order-message font.orderPrice").attr("name");
			
			//每条订单的运费
			var freight=$(this).find(".order-message span.freight").attr("freight");
			var freightName=$(this).find(".order-message span.freight").attr("name");
			
			//每条订单的留言
			var memo=$(this).find(".order-message textarea").val();
			var memoName=$(this).find(".order-message textarea").attr("name");
			
			_data[orderSumPriceName] = orderSumPrice;
			_data[freightName] = freight;
			_data[memoName] = memo;
		});
		$.ajax({
			url:ctxw+"/api/v1/trade/order/save.htm",
			type:'POST',
			data:_data,
			dataType:'json',
			success:function(data){
				if(data==null || data.status==null){
					layer.closeAll();
					layer.open({content: data.message!=""?data.message:"创建订单失败",skin: 'msg',time: 2});
					return;
				}
				if(data.status=='401'){
					layer.closeAll();
					wx_common.routerLogin();
					return;
				}
				if(data.status!='200'){
					layer.closeAll();
					layer.open({content: data.message!=""?data.message:"创建订单失败",skin: 'msg',time: 2});
					return;
				}
				var tradeOrderList=data.data;
				var orderIds="";
				if(tradeOrderList!=null){
					for(var i=0;i<tradeOrderList.length;i++){
						orderIds+=tradeOrderList[i].orderId+",";
					}
				}
				var productName=$(".d-text  span.productName").eq(0).text();
				var total_fee=$(".moneyAll .orderSumPrice").text();
				layer.closeAll();
				wx.pay(total_fee,orderIds,productName,function(){
					window.location.href=ctxw+"/trade/order/list.htm";
				});
			}
		});
		
	});
	
	/**
     * 获取未读消息
     * */
    var count=wx_common.getUnreadMsgCount();
    if(count!=0){
    	$(".header-default-box a.icon-more b").text(count);
    }else{
    	$(".header-default-box a.icon-more b").css("display","none");
    }
	
});

/**
 * 计算运费
 * */
function calculateFreight(){
	var addressId=$(".my-address").attr("addressId");
	if(addressId==null){
		return;
	}
	var totalFreight=0;
	$(".order-list-ok").each(function(){
		var freight=0;
		var pids="";
		var addressIds="";
		var counts="";
		$(this).find(".order-product").each(function(){
			pids+=$(this).attr("pid")+",";
			counts+=$(this).find("font.count").text()+",";
			addressIds+=$(".my-address").attr("addressId")+",";
		});
		console.log("pids:"+pids+"---"+"counts:"+counts+"---"+"addressIds:"+addressIds);
		var freightList=calculateFreight2(pids,addressIds,counts);
		$(this).find(".order-product").each(function(){
			var pid=$(this).attr("pid");
			var count=$(this).find("font.count").text();
			//var freightItem=calculateFreight2(pid,addressId,count);
			var freightItem=0;
			for(var i=0;i<freightList.length;i++){
				if(freightList[i].pid==pid && freightList[i].count==count && freightList[i].addressId == addressId){
					freightItem=freightList[i].freight;
					break;
				}
			}
			if(freightItem=="-1"){
				freight=freightItem;
				return false;
			}
			freight+=parseInt(freightItem);
		});
		totalFreight+=parseInt(freight);
		//还原订单价格(减运费)
		restoreFreight();
		if(freight==0){
			$(this).find(".freight").text("包邮");
			$(this).find(".freight").attr("freight","0");
			$(this).find(".freight").removeClass("noProduct");
		}else if(freight==-1){
			$(this).find(".freight").text("无货");
			$(this).find(".freight").attr("freight","0");
			$(this).find(".freight").addClass("noProduct");
		}else{
			$(this).find(".freight").text(freight+"元");
			$(this).find(".freight").attr("freight",freight);
			$(this).find(".freight").removeClass("noProduct");
			//总价=商品价格+邮费
			var price=$(this).find(".orderPrice").text()*1;
			$(this).find(".orderPrice").text((price+freight).toFixed(2));
		}
	});
	var totalFreight2=totalFreight== -1?'0':totalFreight;
	$(".order-ok-list.total .totalFreight").text(totalFreight2);
	var totalPrice=$(".order-ok-list.total .orderSumPrice").text()*1;
	//更新总价格：总价格=商品总价+运费总价
	$(".order-ok-list.total .orderSumPrice").text((totalPrice+totalFreight2*1).toFixed(2));
}

/**
 * 还原订单价格(减运费)
 * */
function restoreFreight(){
	var totalFreight=0;
	$(".order-list-ok").each(function(){
		var freight=$(this).find(".freight").attr("freight")*1;
		totalFreight+=freight;
		var price=$(this).find(".orderPrice").text()*1;
		$(this).find(".orderPrice").text((price-freight).toFixed(2));
		$(this).find(".freight").attr("freight","0");
		$(this).find(".freight").text("包邮");
	});
	var totalPrice=$(".order-ok-list.total .orderSumPrice").text()*1;
	$(".order-ok-list.total .orderSumPrice").text((totalPrice-totalFreight*1).toFixed(2));
}

/**
 * 根据多个商品id、多个地址id,多个数量计算运费方法
 * */
function calculateFreight2(pids,addressIds,counts){
	var freightList="";
	$.ajax({
		type: 'GET',
        url:ctxw+'/api/v1/trade/order/calculateFreight.htm',
        data:{"pids":pids,"counts":counts,"addressIds":addressIds},
        dataType: 'json',
        async: false,
        success: function(data){
        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
        		return;
        	}
        	freightList=data.data;
        }
	});
	return freightList;
}

/**
 * 获取会员收货地址
 * */
function addressOne(addressId,addressIdArray){
	$.ajax({
    	type: 'GET',
        url:ctxw+'/api/v1/user/userAddress/one.htm',
        data:{"addressId":addressId},
        dataType: 'json',
        success: function(data){
        	if(data==null || typeof(data.data)=="undefined" || data.data==""){
        		return;
        	}
        	if(data.status=='401'){
				wx_common.routerLogin();
				return;
			}
        	if(data.status!='200'){
        		return;
        	}
        	var address=data.data;
        	$(".my-address").attr("addressId",address.addressId);
        	$(".my-address .my-phone").text(address.mobile);
        	$(".my-address .my-name").text(address.name);
        	$(".my-address .my-adds p").text(address.provinceName+" "+address.cityName+" "+address.districtName+" "+address.detailedAddress);
        	addressIdArray.push($(".my-address").attr("addressId"));//初始化储收货地址列表页的收货地址id
        	calculateFreight();
        	layer.closeAll();
        }
	});
};

/**
 * 赋值发票信息
 * */
function assignmentTradeDeliverId(deliverId,companyName,isCancel){
	if(isCancel=="true"){
		$(".tradeDeliver").removeAttr("tradeDeliverId");
		$(".tradeDeliver").html("不需要发票");
	}
	if(isCancel=="false"){
		if(deliverId==null || deliverId==''){
			$(".tradeDeliver").removeAttr("tradeDeliverId");
			$(".tradeDeliver").html("不需要发票");
		}else{
			$(".tradeDeliver").attr("tradeDeliverId",deliverId);
			$(".tradeDeliver").html(companyName);
		}
	}
}

/**
 * 从收货地址列表页面返回我的订单页面,设置收回地址信息
 */
function assignmentAddress(addressId,name,mobile,addressDetail,isUserAddress){
	if(!isUserAddress){
		$(".my-address .my-name").text("请选择收货地址");
		$(".my-address").attr("addressId","");
		$(".my-address .my-phone").text("");
    	$(".my-address .my-adds p").text("");
    	//没有收货地址时，还原订单价格(减运费)
    	restoreFreight();
	}
	if(isUserAddress){
		if(addressId==null || addressId==''){
			$(".my-address .my-name").text("请选择收货地址");
			//没有收货地址时，还原订单价格(减运费)
	    	restoreFreight();
		}else{
			$(".my-address").attr("addressId",addressId);
			$(".my-address .my-name").text(name);
	    	$(".my-address .my-phone").text(mobile);
	    	$(".my-address .my-adds p").text(addressDetail);
	    	//重新结算运费
	    	calculateFreight();
		}
	}
}

/**
 * JS判断元素是否在数组内
 */
function contains(arr, obj) {
	var i = arr.length;
	while (i--) {
		if (arr[i] === obj) {
			return true;
		}
	}
	return false;
}
