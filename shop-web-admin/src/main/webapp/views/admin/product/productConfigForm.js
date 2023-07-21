$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"set1":{maxlength:64,},
				"set2":{maxlength:64,},
				"set3":{maxlength:64,},
				"set4":{maxlength:64,},
				"set5":{maxlength:64,},
				"set6":{maxlength:64,},
				"set7":{maxlength:64,},
				"set8":{maxlength:64,},
				"set9":{maxlength:64,},
				"set10":{maxlength:64,},
			},
			messages: {
				"set1":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set2":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set3":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set4":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set5":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set6":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set7":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set8":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set9":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"set10":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
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