$(function(){
	var orderId=$("input[name='orderId']").val();
	
	/**
	 * 根据订单id查询订单
	 * */
	(function(){
    	if(orderId==null){
    		return;
    	}
    	$.ajax({
    		url:ctxw+"/api/v1/trade/order/one.htm",
    		type:'GET',
    		data:{"orderId":orderId},
    		dataType:'json',
    		success:function(data){
    			if(data==null || data.data==null || data.status==null){
    				return;
    			}
    			if(data.status=='401'){
    				wx_common.routerLogin();
    				return;
    			}
    			if(data.status!='200'){
    				layer.open({content: data.message!=""?data.message:'获取订单失败',skin: 'msg',time: 2});
    				return;
    			}
    			var order=data.data;
    			//订单详情
    			getOrderItem();
    			//发票
    			if(order.isInvoice=="1"){
    				getDeliver(order.deliverId);
    			}else{
    				$(".order_deliver").addClass("hide");
    			}
    			//收货地址
    			$(".order_address .address_name").text(order.consignee);
    			$(".order_address .address_phone").text(order.phone);
    			$(".order_address .address_city").text(order.provinceName+" "+order.cityName+" "+order.districtName+" "+order.detailedAddress);
    			//店铺名称
    			$(".order-list .order_store_name span.store_name").text(order.bname);
    			//店铺链接
    			$(".order-list a.store").attr("href",ctxw+"/store/index.htm?storeId="+order.storeId);
    			//订单编号
    			$(".order_other .orderId span").text(orderId);
    			//下单时间
    			$(".order_other .createDate span").text(order.placeOrderTime);
    			//交易状态
    			var status="交易完成";
    			if(order.orderStatus=='10'){
    				status="待付款";
    				//显示"付款"和"取消订单"按钮
    				$(".order_btns .payOrder").removeClass("hide");
    				$(".order_btns .delOrder").removeClass("hide");
    			}else if(order.orderStatus=='20'){
    				status="待发货";
    				//隐藏按钮div
    				$(".order_btns").addClass("hide");
    				//付款方式
        			$(".order_pay_way .r").text(order.paymentMethodName);
    				//付款时间
    				$(".order_other .payDate span").text(order.payOrderTime);
    				//显示付款时间
    				$(".order_other .payDate").removeClass("hide");
    			}else if(order.orderStatus=='30'){
    				status="待收货";
    				//显示"确认收货"和"查看物流"按钮
    				$(".order_btns .receiveOrder").removeClass("hide");
    				if(order.isNeedLogistics==1){
    					$(".order_btns .logistics").removeClass("hide");
    				}
    				//付款方式
        			$(".order_pay_way .r").text(order.paymentMethodName);
    				//付款时间
    				$(".order_other .payDate span").text(order.payOrderTime);
    				//显示付款时间
    				$(".order_other .payDate").removeClass("hide");
    				//发货时间
    				$(".order_other .deliverProductDate span").text(order.deliverProductDate);
    				//显示发货时间
    				$(".order_other .deliverProductDate").removeClass("hide");
    			}else if(order.orderStatus=='40'){
    				//显示评价按钮
    				$(".order_btns .commentOrder").removeClass("hide");
    				//付款时间
    				$(".order_other .payDate span").text(order.payOrderTime);
    				//发货时间
    				$(".order_other .deliverProductDate span").text(order.deliverProductDate);
    				//完成时间
    				$(".order_other .productReceiptDate span").text(order.deliverProductDate);
    				//显示发货时间
    				$(".order_other .deliverProductDate").removeClass("hide");
    				//完成时间
    				$(".order_other .productReceiptDate").removeClass("hide");
    				//显示付款时间
    				$(".order_other .payDate").removeClass("hide");
    			}else if(order.orderStatus=='50'){
    				//没有追评过，显示追评按钮
    				if(order.isAdditionalComment=='0'){
    					$(".order_btns .addCommentOrder").removeClass("hide");
    				}
    				//付款时间
    				$(".order_other .payDate span").text(order.payOrderTime);
    				//发货时间
    				$(".order_other .deliverProductDate span").text(order.deliverProductDate);
    				//完成时间
    				$(".order_other .productReceiptDate span").text(order.deliverProductDate);
    				//显示发货时间
    				$(".order_other .deliverProductDate").removeClass("hide");
    				//完成时间
    				$(".order_other .productReceiptDate").removeClass("hide");
    				//显示付款时间
    				$(".order_other .payDate").removeClass("hide");
    			}else if(order.orderStatus=='60'){
    				status="已取消";
    				//隐藏按钮div
    				$(".order_btns").addClass("hide");
    				//取消时间
    				$(".order_other .cancelOrderDate span").text(order.cancelOrderDate);
    				//取消付款时间
    				$(".order_other .cancelOrderDate").removeClass("hide");
    			}
    			$(".order_status .r").text(status);
    			//买家留言
    			if(typeof(order.memo)!="undefined" && order.memo!=""){
    				$(".order_member_memo .member_memo_msg").text(order.memo);
    			}else{
    				$(".order_member_memo").addClass("hide");
    			}
    			//商家留言
    			if(typeof(order.sellerMemo)!="undefined" && order.sellerMemo!=""){
    				$(".order_seller_memo .seller_memo_msg").text(order.sellerMemo);
    			}else{
    				$(".order_seller_memo").addClass("hide");
    			}
    			//订单金额
    			var amountPaid=order.amountPaid;
    			//如果金额调整了，就用调整后的金额
    			if(typeof(order.offsetAmount)!="undefined" && order.offsetAmount!=""){
    				amountPaid=order.offsetAmount;
    			}
    			$(".weui_cell.amountPaid .red").text(amountPaid);
    			$(".weui_cell.amountPaid span.freight").text(order.freight);
    		}
    	});
	})();
	
	/**
     * 根据订单id获取订单详情列表
     * */
    function getOrderItem(){
    	if(orderId==null){
    		return;
    	}
    	$.ajax({
    		url:ctxw+"/api/v1/trade/orderItem/list.htm",
    		type:'GET',
    		data:{"orderIds":orderId},
    		dataType:'json',
    		success:function(data){
    			if(data==null || data.data==null || data.status==null){
    				return;
    			}
    			if(data.status=='401'){
    				wx_common.routerLogin();
    				return;
    			}
    			if(data.status!='200'){
    				layer.open({content: data.message!=""?data.message:'获取订单失败',skin: 'msg',time: 2});
    				return;
    			}
    			$(".orderItem").remove();
    			var tradeOrderItemList=data.data;
    			var tradeOrderItemHtml="";
				for(var j=0;j<tradeOrderItemList.length;j++){
					var orderItem=tradeOrderItemList[j];
					var sku= typeof(orderItem.skuValue)!="undefined" && orderItem.skuValue!=""?orderItem.skuValue:"";
					var orderItemData={"productImg":ctxfs+orderItem.thumbnailPath+"@200x200","productPrice":orderItem.price,
							"count":orderItem.quantity,"productName":orderItem.name,"sku":sku,pid:orderItem.pid};//模板的数据
					var order_item_Tpl=$("#order_item_Tpl").html();
					tradeOrderItemHtml+=wx_common.render(order_item_Tpl,orderItemData);//渲染模板
				}
				$(".order-list .store").after(tradeOrderItemHtml);
				wx_common.LazyloadImg();//使用图片延迟加载
    		}
    	});
    }
    
    /**
     * 根据发票id获取发票
     * */
    function getDeliver(deliverId){
    	if(deliverId==null){
    		return;
    	}
    	$.ajax({
    		url:ctxw+"/api/v1/trade/deliver/one.htm",
    		type:'GET',
    		data:{"deliverId":deliverId},
    		dataType:'json',
    		success:function(data){
    			if(data==null || data.data==null || data.status==null){
    				return;
    			}
    			if(data.status=='401'){
    				wx_common.routerLogin();
    				return;
    			}
    			if(data.status!='200'){
    				layer.open({content: data.message!=""?data.message:'获取发票失败',skin: 'msg',time: 2});
    				return;
    			}
    			var deliver=data.data;
    			var deliverType=gwx_common.etDictLabel("deliver_type",deliver.deliverType);
				var deliverData={"deliverType":deliverType,"companyName":deliver.companyName,"axpayerIdentityNumber":deliver.axpayerIdentityNumber,
					"openingBank":deliver.openingBank,"accountNumber":deliver.accountNumber,"address":deliver.address,"phone":deliver.phone};//模板的数据
				var deliver_Tpl=$("#deliver_Tpl").html();
				var deliverHtml=wx_common.render(deliver_Tpl,deliverData);//渲染模板
				$(".order_deliver").html(deliverHtml);
    		}
    	});
    }
    
    /**
 	 * 申请微信支付
 	 * */
 	$("body").delegate(".payOrder","click",function(){
 		var total_fee=$(".amountPaid font.red").text();
		var orderId=$(".orderId span").text();
		var productName=$(".d-text .d-name").eq(0).text();
		wx.pay(total_fee,orderId,productName,function(){
			window.location.href=ctxw+"/trade/order/list.htm";
		});
 	});
});
