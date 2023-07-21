$(function(){
	/**
	 * 删除提示
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});

	/**
	 * 页面初始化加载订单
	 */
	//var beginPlaceOrderTime=$("#beginPlaceOrderTime").val();
	//var endPlaceOrderTime=$("#endPlaceOrderTime").val();
	var orderId=$("#orderId").val();
	var orderType=$("#orderType").val();
	search(orderId,orderType,1);
	
	/**
	 * 查询点击事件
	 */
	$("#search").click(function(){
		//var beginPlaceOrderTime=$("#beginPlaceOrderTime").val();
		//var endPlaceOrderTime=$("#endPlaceOrderTime").val();
		var orderId=$("#orderId").val();
		var orderType=$("#orderType").val();
		search(orderId,orderType,1);
	});
	
	/**
	 * 上一页
	 */
	$("#tableDiv").delegate(".lastPage","click",function(){
		//var beginPlaceOrderTime=$("#beginPlaceOrderTime").val();
		//var endPlaceOrderTime=$("#endPlaceOrderTime").val();
		var orderId=$("#orderId").val();
		var orderType=$("#orderType").val();
		var pageNo=parseInt($(this).attr("pageNo"));
		pageNo-=1;
		search(orderId,orderType,pageNo);
	});
	
	/**
	 * 下一页
	 */
	$("#tableDiv").delegate(".nextPage","click",function(){
		//var beginPlaceOrderTime=$("#beginPlaceOrderTime").val();
		//var endPlaceOrderTime=$("#endPlaceOrderTime").val();
		var orderId=$("#orderId").val();
		var orderType=$("#orderType").val();
		var pageNo=parseInt($(this).attr("pageNo"));
		pageNo+=1;
		search(orderId,orderType,pageNo);
	});
	
	/**
	 * 查询
	 */
	function search(orderId,orderType,pageNo){
		var reg = new RegExp("^[0-9]*$");
	    if(!reg.test(orderId)){
	    	layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 订单编号只能是数字");
	        return false;
	    }
		var billId=$("input[name='billId']").val();
		//orderType:1订单列表，2退单列表
		var url=ctxa+"/settlement/settlementBill/detailOrder.do?billId="+billId+"&orderId="+orderId+"&type="+orderType+"&pageNo="+pageNo;
		$.get(url,function(data){
			$("#tableDiv").html(data);
		},"html");
	};
	
});