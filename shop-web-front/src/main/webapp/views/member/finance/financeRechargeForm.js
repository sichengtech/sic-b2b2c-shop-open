 $(document).ready(function(){
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"rechargeMoney":{required: true,regex:/^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/,maxlength:12},
			},
			messages: {
				"rechargeMoney":{required: "请输入充值金额",regex:"请输入正整数或两位以内的小数",maxlength:"最大长度不能超过12字符"},
			},
			submitHandler: function(form){
				//var rechargeMoney=$("input[name='rechargeMoney']").val();
				//$(".rechargeMoney").text("￥"+rechargeMoney);
				//$(".myfinancial-content1").css("display","none");
				//$(".gopay").css("display","");
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
 });