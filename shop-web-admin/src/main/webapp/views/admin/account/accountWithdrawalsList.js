$(function(){
	/**
	 * 支付
	 */
	$(".pay").click(function(){
		//用户
		var user = $(this).parent().parent().find(".user").html();
		//账户
		var accountType = $(this).parent().parent().find(".accountType").html();
		//金额
		var money = $(this).parent().parent().find(".money").html();
		//提示内容
		var content =fy.getMsg("确定要支付么？",user,accountType,money);
		var href=$(this).attr("href");
		fdp.confirm(content,href);
	});
});