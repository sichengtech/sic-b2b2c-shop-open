$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				//"epId":{required: true,maxlength:19,},
				/*"orderId":{required: true,maxlength:19,},
				"storeId":{maxlength:19,},
				"storeName":{maxlength:255,},
				"transactionId":{required: true,maxlength:64,},
				"billType":{required: true,maxlength:1,},
				"payWayId":{maxlength:19,},
				"payWayName":{maxlength:64,},
				"orderStatus":{maxlength:2,},
				"transactionStatus":{maxlength:64,},
				"ordertime":{},*/
				"handlestatus":{required: true,maxlength:1,},
				"handleremark":{maxlength:256,},
			},
			messages: {
				//"epId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				/*"orderId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"storeId":{maxlength:"最大长度不能超过19字符",},
				"storeName":{maxlength:"最大长度不能超过255字符",},
				"transactionId":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"billType":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"payWayId":{maxlength:"最大长度不能超过19字符",},
				"payWayName":{maxlength:"最大长度不能超过64字符",},
				"orderStatus":{maxlength:"最大长度不能超过2字符",},
				"transactionStatus":{maxlength:"最大长度不能超过64字符",},
				"ordertime":{},*/
				"handlestatus":{required: fy.getMsg("请选择处理状态"),maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"handleremark":{maxlength:fy.getMsg("最大长度不能超过256字符"),},
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