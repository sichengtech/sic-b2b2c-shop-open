$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"ltiId":{required: true,maxlength:19,},
				"ltId":{required: true,maxlength:19,},
				"area.id":{required: true,},
				"areaName":{maxlength:64,},
				"firstItem":{maxlength:10,},
				"firstPrice":{maxlength:12,},
				"continueItem":{maxlength:12,},
				"continuePrice":{maxlength:10,},
			},
			messages: {
				"ltiId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"ltId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"area.id":{required: "必填项",},
				"areaName":{maxlength:"最大长度不能超过64字符",},
				"firstItem":{maxlength:"最大长度不能超过10字符",},
				"firstPrice":{maxlength:"最大长度不能超过12字符",},
				"continueItem":{maxlength:"最大长度不能超过12字符",},
				"continuePrice":{maxlength:"最大长度不能超过10字符",},
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