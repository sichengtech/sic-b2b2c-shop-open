$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"logId":{required: true,maxlength:19,},
				"sellerId":{required: true,maxlength:19,},
				"type":{maxlength:1,},
				"title":{maxlength:512,},
				"remoteAddr":{maxlength:255,},
				"userAgent":{maxlength:255,},
				"requestUri":{maxlength:255,},
				"method":{maxlength:64,},
				"params":{},
				"exception":{},
			},
			messages: {
				"logId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"sellerId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"type":{maxlength:"最大长度不能超过1字符",},
				"title":{maxlength:"最大长度不能超过512字符",},
				"remoteAddr":{maxlength:"最大长度不能超过255字符",},
				"userAgent":{maxlength:"最大长度不能超过255字符",},
				"requestUri":{maxlength:"最大长度不能超过255字符",},
				"method":{maxlength:"最大长度不能超过64字符",},
				"params":{},
				"exception":{},
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