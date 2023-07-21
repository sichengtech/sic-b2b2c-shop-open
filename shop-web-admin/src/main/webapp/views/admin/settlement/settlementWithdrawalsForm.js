$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"withdrawalsId":{required: true,maxlength:19,},
				"rechargeNumber":{maxlength:64,},
				"mId":{required: true,maxlength:19,},
				"money":{required: true,maxlength:12,},
				"receivablesNumber":{required: true,maxlength:19,},
				"payWayId":{required: true,maxlength:19,},
				"status":{required: true,maxlength:1,},
				"transactionNumber":{maxlength:64,},
				"payTerminal":{maxlength:64,},
				"applyDate":{required: true,},
				"payTime":{},
				"adminId":{maxlength:19,},
				"rejectionReason":{maxlength:512,},
				"accountName":{maxlength:64,},
			},
			messages: {
				"withdrawalsId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"rechargeNumber":{maxlength:"最大长度不能超过64字符",},
				"mId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"money":{required: "必填项",maxlength:"最大长度不能超过12字符",},
				"receivablesNumber":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"payWayId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"status":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"transactionNumber":{maxlength:"最大长度不能超过64字符",},
				"payTerminal":{maxlength:"最大长度不能超过64字符",},
				"applyDate":{required: "必填项",},
				"payTime":{},
				"adminId":{maxlength:"最大长度不能超过19字符",},
				"rejectionReason":{maxlength:"最大长度不能超过512字符",},
				"accountName":{maxlength:"最大长度不能超过64字符",},
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