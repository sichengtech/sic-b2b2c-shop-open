 $(document).ready(function(){
	 /**
	  * 如果js验证开关是开的就进行js验证
	  */
	function validate1(){
		if(jsValidate){
			$("#inputForm").validate({
				rules: {"systemHandleRemarks":{required: true,maxlength:1024,}},
				messages: {"systemHandleRemarks":{required: fy.getMsg("请填入备注"),maxlength:fy.getMsg("最大长度不能超过1024字符"),}},
				submitHandler: function(form){
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}
			});
		}
	}
	
	function validate2(){
		if(jsValidate){
			$("#inputForm").validate({
				rules: {
					"systemHandleRemarks":{required: true,maxlength:1024,},
					"onlineReturnMoney":{required: true,maxlength:9,regex:/^[0-9]+(.[0-9]{1,2})?$/,}
					},
				messages: {
					"systemHandleRemarks":{required: fy.getMsg("请输入备注"),maxlength:fy.getMsg("最大长度不能超过1024字符")},
					"onlineReturnMoney":{required: fy.getMsg("请输入在线退款金额"),maxlength:fy.getMsg("最大长度不能超过9字符"),regex:fy.getMsg("请输入正整数或两位以内的小数")}
					},
				submitHandler: function(form){
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}
			});
		}
	}
	
	/**
	 * 审核失败
	 */
	$(".authError").click(function(){
		validate1();
		fdp.confirm(fy.getMsg('确定审核失败吗？'),function(){
			$(".buttonState").val($(".authError").attr('attrr'));
			$("#inputForm").submit();
		});
	});
	
	/**
	 * 审核成功
	 */
	$(".authOk").click(function(){
		validate2();
		fdp.confirm(fy.getMsg('确定审核成功吗？'),function(){
			$(".buttonState").val($(".authOk").attr('attrr'));
			$("#inputForm").submit();
		});
	});
	
	/**
	 * 审核成功并退款
	 */
	$(".authOkAndMoney").click(function(){
		validate1();
		fdp.confirm(fy.getMsg('确定审核成功并退款吗？'),function(){
			$(".buttonState").val($(".authOkAndMoney").attr('attrr'));
			$("#inputForm").submit();
		});
	});
});