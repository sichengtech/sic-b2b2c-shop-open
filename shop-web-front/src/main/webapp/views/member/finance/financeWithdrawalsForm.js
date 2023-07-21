 $(document).ready(function(){
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		var balance=$(".balance").text();
		$("#inputForm").validate({
			rules: {
				"money":{required: true,regex:/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/,maxlength:12,max:balance},
				"receivablesNumber":{required: true,maxlength:64},
				"accountName":{maxlength:64},
			},
			messages: {
				"money":{required: "请输入提现金额",regex:"请输入正整数或两位以内的小数",maxlength:"最大长度不能超过12字符",max:"提现金额不能超余额"},
				"receivablesNumber":{required: "请输入收款账号",maxlength:"最大长度不能超过64字符"},
				"accountName":{maxlength:"最大长度不能超过64字符"},
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