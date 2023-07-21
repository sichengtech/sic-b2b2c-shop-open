$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"largeArea":{maxlength:2,},
				"companyName":{required: true,maxlength:64,},
				"companyNumber":{maxlength:64,},
				"companyurl":{maxlength:64,},
				"status":{maxlength:1,},
				"isCommonUse":{maxlength:1,},
			},
			messages: {
				"largeArea":{maxlength:fy.getMsg("最大长度不能超过2字符"),},
				"companyName":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"companyNumber":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"companyurl":{maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"status":{maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"isCommonUse":{maxlength:fy.getMsg("最大长度不能超过1字符"),},
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