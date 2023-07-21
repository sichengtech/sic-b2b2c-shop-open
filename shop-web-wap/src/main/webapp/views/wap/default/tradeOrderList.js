$(function(){
	
	/**
	 * 下拉刷新
	 * */
	function initPage(status){
		//页数 
		var page = 0;
		// 每页展示10个
		var size =10;
		layer.open({type: 2});
		$('.weui_panel').dropload({
			scrollArea : window,
			autoLoad : true,//自动加载
			domUp : {//下拉
				domClass   : 'dropload-up',
				domRefresh : '<div class="dropload-refresh"><i class="icon icon-114"></i>上拉加载更多</div>',
				domUpdate  : '<div class="dropload-load f15"><i class="icon icon-20"></i>释放更新...</div>',
				domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>'
			},
			loadDownFn : function(me){//加载更多
				page++;
				assemblyOrderData(page,size,status,me);
			}
		});
	}
	
	/**
	 * 拼装订单数据
	 * */
	function assemblyOrderData(page,size,status,me){
		var result = '';
		$.ajax({
			type: 'GET',
			url:ctxw+'/api/v1/trade/order/page.htm',
			data:{"pageNo":page,"pageSize":size,"status":status},
			dataType: 'json',
			success: function(data){
				if(data==null){
					layer.closeAll();
					layer.open({content: data.message!=""?data.message:'获取订单数据出现错误',skin: 'msg',time: 2});
					return;
				}
				if(data.status=='401'){
					wx_common.routerLogin();
					return;
				}
				if(data.status!='200'){
					layer.open({content: data.message!=""?data.message:'获取订单数据出现错误',skin: 'msg',time: 2});
					return;
				}
				if(data.data.length==0){
					noOrder();
					layer.closeAll();
					return;
				}
				var tradeOrderList=data.data;
				var orderIds="";
				for(var i=0; i<tradeOrderList.length; i++){
					orderIds+=tradeOrderList[i].orderId+",";
				}
				//根据多个订单id查询订单详情
				var tradeOrderItemList=getOrderItem(orderIds);
				if(tradeOrderItemList==null || tradeOrderItemList.length==0){
					noOrder();
					layer.closeAll();
					return;
				}
				for(var i=0; i<tradeOrderList.length; i++){
					var order=tradeOrderList[i];
					var orderId=order.orderId;
					var tradeOrderItemHtml="";
					//商品总数量
					var count=0;
					for(var j=0;j<tradeOrderItemList.length;j++){
						var orderItem=tradeOrderItemList[j];
						if(orderItem.orderId==orderId){
							var sku= typeof(orderItem.skuValue)!="undefined" && orderItem.skuValue!=""?orderItem.skuValue:"";
							var orderItemData={"productImg":ctxfs+orderItem.thumbnailPath+"@200x200","productPrice":orderItem.price,
									"count":orderItem.quantity,"productName":orderItem.name,"sku":sku};//模板的数据
							var order_item_Tpl=$("#trade_order_item_tpl").html();
							tradeOrderItemHtml+=wx_common.render(order_item_Tpl,orderItemData);//渲染模板
							count+=orderItem.quantity;
						}
					}
					var status="";
					var payOrder="";
					var logistics="";
					var commentOrder="";
					var receiveOrder="";
					var addCommentOrder="";
					var delOrder="";
					if("10"==order.orderStatus){
						status="待付款";
						commentOrder="hide";
						receiveOrder="hide";
						addCommentOrder="hide";
						logistics="hide";
					}else if("20"==order.orderStatus){
						status="待发货";
						payOrder="hide";
						delOrder="hide";
						receiveOrder="hide";
						commentOrder="hide";
						addCommentOrder="hide";
						logistics="hide";
					}else if("30"==order.orderStatus){
						status="待收货";
						payOrder="hide";
						delOrder="hide";
						commentOrder="hide";
						addCommentOrder="hide";
						if(order.isNeedLogistics==0){
							logistics="hide";
						}
					}else if("40"==order.orderStatus){
						status="交易完成";
						payOrder="hide";
						delOrder="hide";
						receiveOrder="hide";
						addCommentOrder="hide";
						logistics="hide";
					}else if("50"==order.orderStatus){
						status="交易完成";
						payOrder="hide";
						delOrder="hide";
						commentOrder="hide";
						receiveOrder="hide";
						logistics="hide";
						//已经追评过就隐藏追评按钮
	    				if(order.isAdditionalComment=='1'){
	    					addCommentOrder="hide";
	    				}
					}else if("60"==order.orderStatus){
						status="已取消";
						payOrder="hide";
						addCommentOrder="hide";
						delOrder="hide";
						commentOrder="hide";
						receiveOrder="hide";
						logistics="hide";
					}
					var orderPrice="";
					if(typeof(order.offsetAmount)!="undefined" && order.offsetAmount!=""){
						orderPrice=order.offsetAmount;
					}else{
						orderPrice=order.amountPaid;
					}
					var myDate = new Date(order.createDate);  
					var date=myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
					var tradeOrderData={"orderId":order.orderId,"status":status,"orderItems":tradeOrderItemHtml,
							"date":date+"","orderPrice":orderPrice,"freight":order.freight,
							"commentOrder":commentOrder,"payOrder":payOrder,"logistics":logistics,"count":count,
							"receiveOrder":receiveOrder,"delOrder":delOrder,"addCommentOrder":addCommentOrder};//模板的数据
					var trade_order_tpl=$("#trade_order_tpl").html();
					result+=wx_common.render(trade_order_tpl,tradeOrderData);//渲染模板
				}
				
				// 如果没有数据或已是最后一页
				if(data.page.isLastPage){
					// 锁定
					me.lock();
					// 无数据
					me.noData();
					setTimeout(function(){
						$(".dropload-down").fadeOut("slow");
					},2000);
				}
				
				// 为了测试，延迟1秒加载
				setTimeout(function(){
					$('.trade-order-items').append(result);  
					layer.closeAll();
					wx_common.LazyloadImg();//使用图片延迟加载
					// 每次数据加载完，必须重置
					me.resetload();
				},1000);
			},
			error: function(xhr, type){
				layer.open({content: '获取订单数据出现错误',skin: 'msg',time: 2});
				// 即使加载出错，也得重置
				me.resetload();
				layer.closeAll();
			}
		});
	}
	
	initPage(status);
	
    /**
     * 没有订单信息
     * */
    function noOrder(){
    	var noList_Tpl=$("#noList_Tpl").html();
    	$(".dropload-down").remove();
    	$('.noList').append(noList_Tpl);
    }
    
    /**
     * 根据多个订单id获取订单详情列表
     * @param orderIds	逗号分割的多个订单编号
     * */
    function getOrderItem(orderIds){
    	var orderItemList=null;
    	if(orderIds==null){
    		return;
    	}
    	$.ajax({
    		url:ctxw+"/api/v1/trade/orderItem/list.htm",
    		type:'GET',
    		data:{"orderIds":orderIds},
    		dataType:'json',
    		async: false,
    		success:function(data){
    			if(data==null || data.data==null){
    				return;
    			}
    			if(data.status=='401'){
					wx_common.routerLogin();
					return;
				}
    			if(data.status!='200'){
    				return;
    			}
    			orderItemList=data.data;
    		}
    	});
    	return orderItemList;
    }
    
    /**
     * 订单状态导航点击事件
     * 获取当前点击状态的订单数据
     * */
    //$('#tab2').tab({defaultIndex:0,activeClass:"tab-red"});
    $("body").delegate(".order_status_navbar div","click",function(){
    	$(".order_status_navbar .tab-red").removeClass("tab-red");
    	$(this).addClass("tab-red");
    	var status=$(this).attr("status");
    	$('.trade-order-items').html("");
    	$(".noList").html("");
    	$(".dropload-down").remove();
    	initPage(status);
    });
    
    /**
     * 获取取消订单的理由
     * */
    $(function(){
    	$.ajax({
    		url:ctxw+"/api/v1/sys/dict/list.htm",
    		type:'GET',
    		data:{"type":"trade_cancel_order_reason2"},
    		dataType:'json',
    		success:function(data){
    			if(data==null || data.status!='200' || data.data==null || data.data.length==0){
    				return;
    			}
    			var dictList=data.data;
    			var dictHtml="";
    			var cancle_order_reason_Tpl=$("#cancle_order_reason_Tpl").html();
    			for(var i=0;i<dictList.length;i++){
    				var cancleOrderReasonData={"reason":dictList[i].label,"value":dictList[i].value};//模板的数据
    				dictHtml+=wx_common.render(cancle_order_reason_Tpl,cancleOrderReasonData);//渲染模板
    			}
    			$("#delModal .weui_cells_radio").html(dictHtml);
    		}
    	});
    });
    
    /**
     * 取消订单弹框
     */
    $(document).on("tap", ".delOrder", function() {
    	var orderId=$(this).attr("orderId");
    	var content=$("#delModal").html();
    	$("#delModal input[type='radio']").removeAttr("id");
    	$.modal({
    		title: "请选择取消订单的理由",
    		text: content,
    		buttons: [
    			{ text: "取消", className: "default",onClick: function(){
    				$("#delModal label").each(function(){
    					var id=$(this).attr("for");
    					$(this).find("input.weui_check").attr("id",id);
    				});
    			}},
    			{ text: "确定", onClick: function(){
    				$("#delModal label").each(function(){
    					var id=$(this).attr("for");
    					$(this).find("input.weui_check").attr("id",id);
    				});
    				var reason=$(".order_reason_label").find("input:checked").parent().parent().find("p.reason").text();
    				if(typeof(reason)=="undefined" || reason=="" || reason==null){
    					layer.open({content:'取消订单理由不能为空',skin: 'msg',time: 2});
    					return;
    				}
    				calcelOrder(orderId,reason);
    			}},
    		]
    	});
    	$(".trade_order_center .weui_dialog").css("top","15%");
    });
    
    /**
     * 取消订单方法
     * @param orderId	订单编号
     * @param reason	取消订单的理由
     * */
    function calcelOrder(orderId,reason){
    	layer.open({type: 2});
    	if(typeof(orderId)=="undefined" || orderId==""){
    		layer.closeAll();
			layer.open({content: '订单id不能为空',skin: 'msg',time: 2});
			return;
		}
    	if(typeof(reason)=="undefined" || reason==""){
    		layer.closeAll();
			layer.open({content: '取消订单理由不能为空',skin: 'msg',time: 2});
			return;
		}
    	$.ajax({
    		url:ctxw+"/api/v1/trade/order/cancelOrder.htm",
    		type:'POST',
    		data:{"orderId":orderId,"reason":reason},
    		dataType:'json',
    		success:function(data){
    			if(data==null){
    				layer.closeAll();
    				layer.open({content: data.message!=""?data.message:'取消订单失败',skin: 'msg',time: 2});
    				return;
    			}
    			if(data.status=='401'){
    				layer.closeAll();
					wx_common.routerLogin();
					return;
				}
    			if(data.status!='200'){
    				layer.closeAll();
    				layer.open({content: data.message!=""?data.message:'取消订单失败',skin: 'msg',time: 2});
    				return;
    			}
				$("#"+orderId).find("font.status").text("已取消");
				$("a.delOrder[orderId='"+orderId+"']").addClass("hide");
				$("a.payOrder[orderId='"+orderId+"']").addClass("hide");
				//修改详情页状态
				$(".order_status .r").text("已取消");
				//如果当前是在订单列表的待收货状态下，确认收货成功后需要移除当前订单
				var pageId=$(".sui-page-current").attr("id");
				var status=$(".order_status_navbar .tab-red").attr("status");
				if(pageId=='order-list' && status=='10'){
					$(".trade-order-items .order-list[id='"+orderId+"']").remove();
				}
				var orderListLength=$(".trade-order-items .order-list").length;
				if(orderListLength==0){
					noOrder();
				}
				layer.closeAll();
				layer.open({content: data.message,skin: 'msg',time: 2});
    		}
    	});
    }
    
    /**
     * 确认收货点击事件
     */
     $(document).on("tap", ".receiveOrder", function() {
		var orderId=$(this).attr("orderId");
		$.modal({
			title: "确认收货",
			text: "您确认已收货？",
			buttons: [
				{text: "取消", className: "default"},
				{text: "确定", 
					onClick: function(){
						receiveOrder(orderId);
					} 
				},
			]
		});
     });
     
     /**
      * 确认收货方法
      * @param orderId	订单编号
      * */
     function receiveOrder(orderId){
    	layer.open({type: 2});
		if(typeof(orderId)=="undefined" || orderId==""){
			layer.closeAll();
			layer.open({content: '订单id不能为空',skin: 'msg',time: 2});
			return;
		}
     	$.ajax({
     		url:ctxw+"/api/v1/trade/order/confirmReceipt.htm",
     		type:'POST',
     		data:{"orderId":orderId},
     		dataType:'json',
     		success:function(data){
     			if(data==null){
     				layer.closeAll();
     				layer.open({content: data.message!=""?data.message:'确认收货失败',skin: 'msg',time: 2});
     				return;
     			}
     			if(data.status=='401'){
     				layer.closeAll();
					wx_common.routerLogin();
					return;
				}
    			if(data.status!='200'){
    				layer.closeAll();
    				layer.open({content: data.message!=""?data.message:'确认收货失败',skin: 'msg',time: 2});
    				return;
    			}
     			//更新订单状态
     			$("#"+orderId).find("font.status").text("交易完成");
     			//隐藏确认收货按钮
     			$("#"+orderId).find("a.receiveOrder").addClass("hide");
     			//隐藏查看物流按钮
     			$("#"+orderId).find("a.logistics").addClass("hide");
     			//显示我要评价按钮
     			$("#"+orderId).find("a.commentOrder").removeClass("hide");
     			
     			//隐藏确认收货按钮
     			$("a.receiveOrder[orderId='"+orderId+"']").addClass("hide");
     			//隐藏查看物流按钮
				$("a.logistics[orderId='"+orderId+"']").addClass("hide");
				//显示我要评价按钮
				$("a.commentOrder[orderId='"+orderId+"']").removeClass("hide");
				//修改详情页状态
				$(".order_status .r").text("交易完成");
				
				//如果当前是在订单列表的待收货状态下，确认收货成功后需要移除当前订单
				var pageId=$(".sui-page-current").attr("id");
				var status=$(".order_status_navbar .tab-red").attr("status");
				if(pageId=='order-list' && status=='30'){
					$(".trade-order-items .order-list[id='"+orderId+"']").remove();
				}
				var orderListLength=$(".trade-order-items .order-list").length;
				if(orderListLength==0){
					noOrder();
				}
				layer.closeAll();
     			layer.open({content: data.message,skin: 'msg',time: 2});
     		}
     	});
     }
     
     /**
      * 获取未读消息
      * */
     var count=wx_common.getUnreadMsgCount();
     if(count!=0){
    	 $(".header-default-box a.icon-more b").text(count);
     }else{
    	 $(".header-default-box a.icon-more b").css("display","none");
     }
     
 	/**
 	 * 详情页面后，加载详情js
 	 * */
 	$(document).on("pageAnimationEnd", function(e, pageId, $page){
 		if(pageId == "order-detail") {
 			$("script[src='"+ctx+"/views/wap/default/tradeOrderDetail.js']").remove();
 	  		var head = $("head");
 	        var script = $("<script>");
 	        $(script).attr('src',ctx+'/views/wap/default/tradeOrderDetail.js'); 
 	        $(script).attr('type','text/javascript');
 	        $(head).append(script);
 	  	}
 	  	if(pageId == "order-list") {
 	  		var status=$(".weui_tab .order_status_navbar .tab-red").attr("status");
  	    	$('.trade-order-items').html("");
  	    	$(".noList").html("");
  	    	$(".dropload-down").remove();
  	    	initPage(status);
  	    	layer.closeAll();
 	  	}
 	  	if(pageId == "logisticsInfo") {
 			$("script[src='"+ctx+"/views/wap/default/tradeLogisticsInfo.js']").remove();
 	  		var head = $("head");
 	        var script = $("<script>");
 	        $(script).attr('src',ctx+'/views/wap/default/tradeLogisticsInfo.js'); 
 	        $(script).attr('type','text/javascript');
 	        $(head).append(script);
 	        $(".timeline ul").html("");
 	  	}
 	});
 	
 	/**
 	 * 申请微信支付
 	 * */
 	$(".trade-order-items").delegate(".payOrder","click",function(){
 		var total_fee=$(this).parent().siblings(".weui_cells").find("font.red").attr("price");
		var orderId=$(this).attr("orderId");
		var productName=$(this).parent().parent().find(".d-name").html();
		wx.pay(total_fee,orderId,productName,function(){
			var status=$(".order_status_navbar .tab-red").attr("status");
	    	$('.trade-order-items').html("");
	    	$(".noList").html("");
	    	$(".dropload-down").remove();
	    	initPage(status);
		});
 	});
});
