$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"ptoiId":{required: true,maxlength:19,},
				"ptoId":{required: true,maxlength:19,},
				"purchaseItemId":{required: true,maxlength:19,},
				"pmiId":{required: true,maxlength:19,},
				"name":{required: true,maxlength:64,},
				"model":{required: true,maxlength:64,},
				"brand":{maxlength:64,},
				"amount":{required: true,maxlength:10,},
				"priceRequirement":{maxlength:12,},
				"unit":{maxlength:64,},
				"purchaseRemarks":{maxlength:255,},
				"offerPrice":{required: true,maxlength:12,},
				"offerRemarks":{maxlength:255,},
			},
			messages: {
				"ptoiId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"ptoId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"purchaseItemId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"pmiId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"name":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"model":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"brand":{maxlength:"最大长度不能超过64字符",},
				"amount":{required: "必填项",maxlength:"最大长度不能超过10字符",},
				"priceRequirement":{maxlength:"最大长度不能超过12字符",},
				"unit":{maxlength:"最大长度不能超过64字符",},
				"purchaseRemarks":{maxlength:"最大长度不能超过255字符",},
				"offerPrice":{required: "必填项",maxlength:"最大长度不能超过12字符",},
				"offerRemarks":{maxlength:"最大长度不能超过255字符",},
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