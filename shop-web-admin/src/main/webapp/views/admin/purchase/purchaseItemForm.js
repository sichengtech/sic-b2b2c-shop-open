$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"purchaseItemId":{required: true,maxlength:19,},
				"purchaseId":{maxlength:19,},
				"uId":{maxlength:19,},
				"name":{required: true,maxlength:64,},
				"model":{required: true,maxlength:64,},
				"brand":{required: true,maxlength:64,},
				"encapsulation":{maxlength:64,},
				"amount":{required: true,maxlength:10,},
				"cycle":{maxlength:64,},
				"priceRequirement":{required: true,maxlength:12,},
				"area":{maxlength:64,},
				"batchNumber":{maxlength:64,},
				"unit":{maxlength:64,},
				"status":{required: true,maxlength:2,},
			},
			messages: {
				"purchaseItemId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"purchaseId":{maxlength:"最大长度不能超过19字符",},
				"uId":{maxlength:"最大长度不能超过19字符",},
				"name":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"model":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"brand":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"encapsulation":{maxlength:"最大长度不能超过64字符",},
				"amount":{required: "必填项",maxlength:"最大长度不能超过10字符",},
				"cycle":{maxlength:"最大长度不能超过64字符",},
				"priceRequirement":{required: "必填项",maxlength:"最大长度不能超过12字符",},
				"area":{maxlength:"最大长度不能超过64字符",},
				"batchNumber":{maxlength:"最大长度不能超过64字符",},
				"unit":{maxlength:"最大长度不能超过64字符",},
				"status":{required: "必填项",maxlength:"最大长度不能超过2字符",},
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