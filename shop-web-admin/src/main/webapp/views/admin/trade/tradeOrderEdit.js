$(document).ready(function(){
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
	$("#inputForm").validate({
		rules: {"payOrderTime":{required: true},"thirdPayOrderNumber":{maxlength:64,}},
	 	messages: {"payOrderTime":{required: fy.getMsg("请选择付款时间")},"thirdPayOrderNumber":{maxlength:fy.getMsg("最大长度不能超过64字符"),}},
		submitHandler: function(form){
			layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
			form.submit();
			}
		});
	}
});