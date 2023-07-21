$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"auId":{required: true,maxlength:19,},
				"uId":{required: true,maxlength:19,},
				"accountType":{required: true,maxlength:10,},
				"ownMoney":{required: true,},
				"frozenMoney":{required: true,},
			},
			messages: {
				"auId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"uId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"accountType":{required: "必填项",maxlength:"最大长度不能超过10字符",},
				"ownMoney":{required: "必填项",},
				"frozenMoney":{required: "必填项",},
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