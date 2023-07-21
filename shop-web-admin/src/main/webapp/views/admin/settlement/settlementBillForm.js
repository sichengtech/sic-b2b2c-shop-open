$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"billId":{required: true,maxlength:19,},
				"sellerId":{required: true,maxlength:19,},
				"balanceDate":{required: true,},
				"billAmount":{required: true,maxlength:12,},
				"status":{required: true,maxlength:2,},
				"orderAmount":{required: true,maxlength:12,},
				"platformCommission":{maxlength:12,},
				"returnCommission":{maxlength:12,},
				"refund":{maxlength:12,},
				"promotionExpenses":{maxlength:12,},
				"redPackets":{maxlength:12,},
				"returnRedPackets":{maxlength:12,},
				"deposit":{maxlength:12,},
				"beginTime":{},
				"endTime":{},
				"payDate":{},
				"payPerson":{required: true,maxlength:64,},
				"payRemarks":{maxlength:1024,},
			},
			messages: {
				"billId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"sellerId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"balanceDate":{required: "必填项",},
				"billAmount":{required: "必填项",maxlength:"最大长度不能超过12字符",},
				"status":{required: "必填项",maxlength:"最大长度不能超过2字符",},
				"orderAmount":{required: "必填项",maxlength:"最大长度不能超过12字符",},
				"platformCommission":{maxlength:"最大长度不能超过12字符",},
				"returnCommission":{maxlength:"最大长度不能超过12字符",},
				"refund":{maxlength:"最大长度不能超过12字符",},
				"promotionExpenses":{maxlength:"最大长度不能超过12字符",},
				"redPackets":{maxlength:"最大长度不能超过12字符",},
				"returnRedPackets":{maxlength:"最大长度不能超过12字符",},
				"deposit":{maxlength:"最大长度不能超过12字符",},
				"beginTime":{},
				"endTime":{},
				"payDate":{},
				"payPerson":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"payRemarks":{maxlength:"最大长度不能超过1024字符",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});