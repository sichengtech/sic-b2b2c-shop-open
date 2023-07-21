$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"purchaseMatchmakingId":{required: true,maxlength:19,},
				"purchaseId":{required: true,maxlength:19,},
				"purchaseItemId":{required: true,maxlength:19,},
				"purchaseTradeId":{maxlength:19,},
				"content":{required: true,maxlength:2000,},
				"uId":{required: true,maxlength:19,},
				"status":{required: true,maxlength:2,},
			},
			messages: {
				"purchaseMatchmakingId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"purchaseId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"purchaseItemId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"purchaseTradeId":{maxlength:"最大长度不能超过19字符",},
				"content":{required: "必填项",maxlength:"最大长度不能超过2000字符",},
				"uId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
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