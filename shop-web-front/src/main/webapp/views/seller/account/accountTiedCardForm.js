$(function(){
	var oldBankCardNumber = $("#oldBankCardNumber").val();
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"bankCardNumber":{remote: ctxs+"/account/tiedCard/validateBankCardNumber.htm?oldBankCardNumber=" + encodeURIComponent(oldBankCardNumber),regex:/^[1-9]\d*$/,required: true,maxlength:64,},
				"payee":{required: true,maxlength:64,},
				"idNumber":{regex:/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/,required: true,maxlength:63,},
				"accountOpeningBank":{required: true,maxlength:64,},
				"mobilePhoneNumber":{regex:/^1\d{10}$/,required: true,maxlength:11,},
			},
			messages: {
				"bankCardNumber":{remote:fy.getMsg("银行卡号已存在"),regex:fy.getMsg("请输入正确银行卡号"),required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"payee":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"idNumber":{regex:fy.getMsg("请输入正确身份证号"),required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过63字符"),},
				"accountOpeningBank":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"mobilePhoneNumber":{regex: fy.getMsg("请输入正确手机号"),required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过11字符"),},
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
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});