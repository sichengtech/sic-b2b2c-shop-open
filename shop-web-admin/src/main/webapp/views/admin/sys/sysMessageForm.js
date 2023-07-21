$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"informationId":{required: true,maxlength:19,},
				"sender":{required: true,maxlength:1,},
				"receiveType":{required: true,maxlength:1,},
				"mId":{maxlength:19,},
				"sellerId":{maxlength:19,},
				"type":{required: true,maxlength:1,},
				"statusMsg":{required: true,maxlength:1,},
				"statusSms":{required: true,maxlength:1,},
				"statusEmail":{required: true,maxlength:1,},
				"statusWeixin":{required: true,maxlength:1,},
				"statusB":{required: true,maxlength:1,},
				"title":{maxlength:64,},
				"content":{required: true,maxlength:512,},
				"reading":{required: true,maxlength:1,},
			},
			messages: {
				"informationId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"sender":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"receiveType":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"mId":{maxlength:"最大长度不能超过19字符",},
				"sellerId":{maxlength:"最大长度不能超过19字符",},
				"type":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"statusMsg":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"statusSms":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"statusEmail":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"statusWeixin":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"statusB":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"title":{maxlength:"最大长度不能超过64字符",},
				"content":{required: "必填项",maxlength:"最大长度不能超过512字符",},
				"reading":{required: "必填项",maxlength:"最大长度不能超过1字符",},
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