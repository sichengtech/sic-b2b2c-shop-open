$(function(){
	jQuery.validator.addMethod("validatorLength",
			function(value) { 
			var strs= new Array(); //定义一数组
			strs=value.split(",");
			var size = strs.length;
			if(size>10){
				return false;
			}else{
				return true;
			}
		}, 
		fy.getMsg("长度过长")); //addMethod第3个参数:默认错误提示信息
	if(jsValidate){
		var specValues = $("#specValues").val();
		$("#inputForm").validate({
			rules: {
				"specSort":{required: true,maxlength:10,regex:/^(0|[1-9][0-9]*)$/,},
				"isColor":{maxlength:1,},
				"name":{required: true,maxlength:64,},
				"specValues":{required: true,maxlength:1024,validatorLength:specValues},
			},
			messages: {
				"specSort":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过10字符"),regex:fy.getMsg("排序要输入数字"),},
				"isColor":{maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"name":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"specValues":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过1024字符"),validatorLength:fy.getMsg("规格值不能大于10个")},
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
