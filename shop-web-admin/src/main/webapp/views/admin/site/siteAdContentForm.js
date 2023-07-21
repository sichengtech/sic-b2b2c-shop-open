$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"adContentId":{required: true,maxlength:19,},
				"adId":{required: true,maxlength:19,},
				"content":{required: true,},
				"status":{required: true,maxlength:1,},
			},
			messages: {
				"adContentId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"adId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"content":{required: "必填项",},
				"status":{required: "必填项",maxlength:"最大长度不能超过1字符",},
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