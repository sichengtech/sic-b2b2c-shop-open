$(function () {
	/**
	 * 报价
	 * */
	$(".offer a").click(function(){
		//采购商id
		var uId=$(this).attr("uId");
		var status=$(this).attr("status");
		//报价人id
		var uId2=$("input[name='uId']").val();
		//非报价中的采购单不能报价
		if("35" != status){
			return false;
		}
		//不能给自己发的采购单报价
		if(uId2!="" && uId2==uId){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('不能给自己发的采购单报价'),2000);
			return false;
		}
		var purchaseId=$(this).attr("purchaseId");
		window.location.href=ctxf+'/purchase/offer.htm?purchaseId='+purchaseId;
	});
	
})