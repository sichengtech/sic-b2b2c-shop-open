$(function(){
	/**
	 * 提交验证
	 */
	$("#inputForm").submit(function(){
		var email = $("#email").val();
		var validateCode = $("#validateCode").val();
		var emailVerification = $("#emailVerification").val();
		var pass=true;
		var msg={'email1':'','email2':'','validateCode':'','emailVerification':''};
		if(email==null || email==''){
			pass=false;
			msg.email1=fy.getMsg('邮箱不能为空');
		}
		var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
	    if (!reg.test(email)) {
			pass=false;
			msg.email2=fy.getMsg('请输入正确的邮箱');
	    }
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(emailVerification==null || emailVerification==''){
			pass=false;
			msg.emailVerification=fy.getMsg('动态码不能为空');
		}
		if(!pass){
			var msgAll=msg.email1+" "+msg.email2+" "+msg.validateCode+" "+msg.emailVerification;
			layer.msg(msgAll);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
	/**
	 * 发送邮件验证码
	 */
	$("#emailSender").click(function(){
		var email = $("#email").val();
		var validateCode = $("#validateCode").val();
		var pass=true;
		var msg={'email1':'','email2':'','validateCode':''};
		if(email==null || email==''){
			pass=false;
			msg.email1=fy.getMsg('邮箱不能为空');
		}
		var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	    if (!reg.test(email)) {
			pass=false;
			msg.email2=fy.getMsg('请输入正确的邮箱');
	    }
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(!pass){
			var msgAll=msg.email1+" "+msg.email2+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}else{
			var t = 60;
			var v = fy.getMsg('发送验证码');
			$.ajax({																	
				type: 'post',
				url: ctxsso + "/security/bindingGetEmail.htm?email="+email+"&validateCode="+validateCode,
				dataType: 'json',
				success: function(data){
					if(data.status!="0"){
						layer.msg(data.message);
						return false;
					}
					var sh = setInterval(function() {
						if (t > 0) {
							$("#emailSender").html(fy.getMsg('重新获取')+"(" + t-- + ")");
							$("#emailSender").attr("disabled", "");
						} else {
							window.clearInterval(0);
							$("#emailSender").html(fy.getMsg('发送验证码'));
							$("#emailSender").removeAttr("disabled");
							clearInterval(sh);
						}
					}, 1000);
					$("#emailSender").attr("disabled", "disabled");
					layer.msg(data.message);
					return true;
				},
				error: function(data){
					layer.msg(fy.getMsg('发送失败：请重新发送邮件验证码！'));
					return false;
				}
			});
		}
	});
});