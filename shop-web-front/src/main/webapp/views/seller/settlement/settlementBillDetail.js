$(function(){
	//页面初始化加载
	search(1,1);
	
	/**
	 *  面包削的切换
	 */
	$(".search").click(function(){
		var orderType=$(this).attr("type");
		search(orderType,1);
	});
	
	/**
	 * 上一页
	 */
	$("#tableDiv").delegate(".lastPage","click",function(){
		var orderType=$(".search").parent().find(".active").attr("type");
		var pageNo=parseInt($(this).attr("pageNo"));
		pageNo-=1;
		search(orderType,pageNo);
	});
	
	/**
	 * 下一页
	 */
	$("#tableDiv").delegate(".nextPage","click",function(){
		var orderType=$(".search").parent().find(".active").attr("type");
		var pageNo=parseInt($(this).attr("pageNo"));
		pageNo+=1;
		search(orderType,pageNo);
	});
	
	/**
	 * 加载
	 */
	function search(orderType,pageNo){
		var billId=$(".billId").text();
		//orderType:1订单列表，2退单列表
		var url=ctxs+"/settlement/settlementBill/detailOrder.htm?billId="+billId+"&type="+orderType+"&pageNo="+pageNo;
		$.get(url,function(data){
			$("#tableDiv").html(data);
			if(orderType==1){
				$("li[type='1']").addClass("active");
				$("li[type='2']").removeClass("active");
			}
			if(orderType==2){
				$("li[type='1']").removeClass("active");
				$("li[type='2']").addClass("active");
			}
		},"html");
	};
});