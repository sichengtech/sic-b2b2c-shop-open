$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"mId":{required: true,maxlength:19,},
				"availableMoney":{required: true,},
				"frozenMoney":{},
				"operationMoney":{required: true,},
				"operationDescribe":{maxlength:1024,},
				"adminId":{maxlength:19,},
				"bak1":{maxlength:64,},
				"bak2":{maxlength:64,},
				"bak3":{maxlength:64,},
				"bak4":{maxlength:64,},
				"bak5":{maxlength:64,},
				"bak6":{maxlength:64,},
				"bak7":{maxlength:64,},
				"bak8":{maxlength:64,},
				"bak9":{maxlength:64,},
				"bak10":{maxlength:64,},
			},
			messages: {
				"mId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"availableMoney":{required: "必填项",},
				"frozenMoney":{},
				"operationMoney":{required: "必填项",},
				"operationDescribe":{maxlength:"最大长度不能超过1024字符",},
				"adminId":{maxlength:"最大长度不能超过19字符",},
				"bak1":{maxlength:"最大长度不能超过64字符",},
				"bak2":{maxlength:"最大长度不能超过64字符",},
				"bak3":{maxlength:"最大长度不能超过64字符",},
				"bak4":{maxlength:"最大长度不能超过64字符",},
				"bak5":{maxlength:"最大长度不能超过64字符",},
				"bak6":{maxlength:"最大长度不能超过64字符",},
				"bak7":{maxlength:"最大长度不能超过64字符",},
				"bak8":{maxlength:"最大长度不能超过64字符",},
				"bak9":{maxlength:"最大长度不能超过64字符",},
				"bak10":{maxlength:"最大长度不能超过64字符",},
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