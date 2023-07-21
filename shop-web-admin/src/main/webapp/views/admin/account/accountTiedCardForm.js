$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"tiedCardId":{required: true,maxlength:19,},
				"uId":{required: true,maxlength:19,},
				"bankCardNumber":{required: true,maxlength:64,},
				"payee":{required: true,maxlength:64,},
				"idNumber":{required: true,maxlength:63,},
				"accountOpeningBank":{required: true,maxlength:64,},
				"mobilePhoneNumber":{required: true,maxlength:64,},
				"auditStatus":{required: true,maxlength:1,},
				"auditOpinion":{maxlength:255,},
			},
			messages: {
				"tiedCardId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"uId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"bankCardNumber":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"payee":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"idNumber":{required: "必填项",maxlength:"最大长度不能超过63字符",},
				"accountOpeningBank":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"mobilePhoneNumber":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"auditStatus":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"auditOpinion":{maxlength:"最大长度不能超过255字符",},
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