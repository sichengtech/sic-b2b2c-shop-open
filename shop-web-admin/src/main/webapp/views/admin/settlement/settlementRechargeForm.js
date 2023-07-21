$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"rechargeId":{required: true,maxlength:19,},
				"rechargeNumber":{maxlength:64,},
				"mId":{required: true,maxlength:19,},
				"rechargeMoney":{required: true,maxlength:12,},
				"rechargeTime":{required: true,},
				"payDate":{},
				"payWayId":{maxlength:19,},
				"staus":{required: true,maxlength:1,},
				"payTerminal":{maxlength:1,},
				"tradeNumber":{maxlength:64,},
				"adminId":{maxlength:19,},
			},
			messages: {
				"rechargeId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"rechargeNumber":{maxlength:"最大长度不能超过64字符",},
				"mId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"rechargeMoney":{required: "必填项",maxlength:"最大长度不能超过12字符",},
				"rechargeTime":{required: "必填项",},
				"payDate":{},
				"payWayId":{maxlength:"最大长度不能超过19字符",},
				"staus":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"payTerminal":{maxlength:"最大长度不能超过1字符",},
				"tradeNumber":{maxlength:"最大长度不能超过64字符",},
				"adminId":{maxlength:"最大长度不能超过19字符",},
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