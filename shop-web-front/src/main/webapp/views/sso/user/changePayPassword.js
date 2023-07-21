$(function(){
	/**
	 * 使用账号注册
	 */
	$("#inputForm").submit(function(){
		var action = $(this).attr('action');
		var oldPaymentPassword = $("#oldPaymentPassword").val();
		var oldPaymentPassword1 = $("#oldPaymentPassword").length;
		var paymentPassword = $("#paymentPassword").val();
		var rePaymentPassword=$("#rePaymentPassword").val();
		var msg={'paymentPassword1':'','paymentPassword2':'','paymentPassword3':'','paymentPassword4':'','paymentPassword5':'','paymentPassword6':''};
		var pass=true;
		if(oldPaymentPassword1==1){
			if(oldPaymentPassword==null || oldPaymentPassword==''){
				pass=false;
				msg.paymentPassword1="原支付密码不能为空";
			}
			$(this).attr('action',action+"?cp=1");
		}
	    if(paymentPassword==null || paymentPassword==''){
			pass=false;
			msg.paymentPassword2="支付密码不能为空";
		}else{
			if(paymentPassword.length>pwdMax){
				pass=false;
				msg.paymentPassword3="支付密码不能大于"+pwdMax+fy.getMsg('字符');
			}
			if(paymentPassword.length<pwdMin){
				pass=false;
				msg.paymentPassword4="支付密码不能少于"+pwdMin+fy.getMsg('字符');
			}
		}
	    if(rePaymentPassword==null || rePaymentPassword==''){
			pass=false;
			msg.paymentPassword5="确认支付密码不能为空";
		}
		if(paymentPassword!=rePaymentPassword){
			pass=false;
			msg.paymentPassword6="两次密码必须输入一致";
		}
		if(!pass){
			var msgAll=msg.paymentPassword1+" "+msg.paymentPassword2+" "+msg.paymentPassword3+" "+msg.paymentPassword4+" "+msg.paymentPassword5+" "+msg.paymentPassword6;
			layer.msg(msgAll);
			return false;
		}
		layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
});
