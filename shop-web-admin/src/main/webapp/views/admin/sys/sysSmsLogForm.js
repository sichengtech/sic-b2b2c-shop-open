$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"sslId":{required: true,maxlength:19,},
				"content":{maxlength:255,},
				"templatecode":{maxlength:128,},
				"status":{maxlength:1,},
				"bewrite":{maxlength:128,},
				"type":{maxlength:1,},
				"sendDate":{required: true,},
			},
			messages: {
				"sslId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"content":{maxlength:"最大长度不能超过255字符",},
				"templatecode":{maxlength:"最大长度不能超过128字符",},
				"status":{maxlength:"最大长度不能超过1字符",},
				"bewrite":{maxlength:"最大长度不能超过128字符",},
				"type":{maxlength:"最大长度不能超过1字符",},
				"sendDate":{required: "必填项",},
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