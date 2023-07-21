$(function(){
	/**
	 * 使用邮箱找回密码
	 */
	$("#inputForm1").submit(function(){
		var loginName = $("#loginName1").val();
		var email = $("#email1").val();
		var validateCode = $("#validateCode1").val();
		var emailVerification = $("#emailVerification1").val();
		var password = $("#password1").val();
		var repassword=$("#repassword1").val();
		var msg={'loginName1':'','email1':'','email2':'','validateCode':'','emailVerification':'','password1':'','password2':'','password3':'','password4':'','password5':''};
		var pass=true;
		if(loginName==null || loginName==''){
			pass=false;
			msg.loginName1=fy.getMsg('用户名不能为空');
		}
		if(email==null || email==''){
			pass=false;
			msg.email1=fy.getMsg('邮件不能为空');
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
			msg.emailVerification=fy.getMsg('邮件验证码不能为空');
		}
	    if(password==null || password==''){
			pass=false;
			msg.password1=fy.getMsg('密码不能为空');
		}else{
			if(password.length>pwdMax){
				pass=false;
				msg.password2=fy.getMsg('密码不能大于')+pwdMax+fy.getMsg('字符');
			}
			if(password.length<pwdMin){
				pass=false;
				msg.password3=fy.getMsg('密码不能少于')+pwdMin+fy.getMsg('字符');
			}
		}
	    if(repassword==null || repassword==''){
			pass=false;
			msg.password4=fy.getMsg('确认密码不能为空');
		}
		if(password!=repassword){
			pass=false;
			msg.password5=fy.getMsg('两次密码必须输入一致');
		}
		if(!pass){
			var msgAll=msg.loginName1+" "+msg.email1+" "+msg.email2+" "+msg.validateCode+" "+msg.emailVerification+" "+msg.password1+" "+msg.password2+" "+msg.password3+" "+msg.password4+" "+msg.password5;
			layer.msg(msgAll);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
	
	/**
	 * 使用手机号找回密码
	 */
	$("#inputForm2").submit(function(){
		var mobile = $("#mobile2").val();
		var validateCode = $("#validateCode2").val();
		var smsVerification = $("#smsVerification1").val();
		var password = $("#password2").val();
		var repassword = $("#repassword2").val();
		var msg={'mobile1':'','mobile2':'','validateCode':'','smsVerification':'','password1':'','password2':'','password3':'','password4':'','password5':''};
		var pass=true;
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobil1=fy.getMsg('手机号不能为空');
		}
		var reg = /^1\d{10}$/;
		if (!reg.test(mobile)) {
			pass=false;
			msg.mobile2=fy.getMsg('请输入正确的手机号');
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(smsVerification==null || smsVerification==''){
			pass=false;
			msg.smsVerification=fy.getMsg('短信验证码不能为空');
		}
	    if(password==null || password==''){
			pass=false;
			msg.password1=fy.getMsg('密码不能为空');
		}else{
			if(password.length>pwdMax){
				pass=false;
				msg.password2=fy.getMsg('密码不能大于')+pwdMax+fy.getMsg('字符');
			}
			if(password.length<pwdMin){
				pass=false;
				msg.password3=fy.getMsg('密码不能少于')+pwdMin+fy.getMsg('字符');
			}
		}
	    if(repassword==null || repassword==''){
			pass=false;
			msg.password4=fy.getMsg('确认密码不能为空');
		}
		if(password!=repassword){
			pass=false;
			msg.password5=fy.getMsg('两次密码必须输入一致');
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.validateCode+" "+msg.smsVerification+" "+msg.password1+" "+msg.password2+" "+msg.password3+" "+msg.password4+" "+msg.password5;
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
		var loginName = $("#loginName1").val();
		var email = $("#email1").val();
		var validateCode = $("#validateCode1").val();
		var pass=true;
		var msg={'loginName1':'','email1':'','email2':'','validateCode':''};
		if(loginName==null || loginName==''){
			pass=false;
			msg.loginName1=fy.getMsg('用户名不能为空');
		}
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
				url: ctxsso + "/security/forgetPasswordGetEmail.htm?loginName="+loginName+"&email="+email+"&validateCode="+validateCode,
				dataType: 'json',
				success: function(data){
					if(data.status!="0"){
						layer.msg(data.message);
						return false;
					}
					var sh = setInterval(function() {
						if (t > 0) {
							$("#emailSender").val(fy.getMsg('重新获取')+"(" + t-- + ")");
							$("#emailSender").attr("disabled", "");
						} else {
							window.clearInterval(0);
							$("#emailSender").val(fy.getMsg('发送邮件验证码'));
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
	
	/**
	 * 发送短信验证码
	 */
	$("#smsSender").click(function(){
		var mobile = $("#mobile2").val();
		var validateCode = $("#validateCode2").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','mobile3':'','validateCode':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1=fy.getMsg('手机号码不能为空');
		}
		if(mobile.length != 11){
			pass=false;
			msg.mobile2=fy.getMsg('请输入11位手机号');
		}
		var reg = /^1\d{10}$/;
		if (!reg.test(mobile)) {
			pass=false;
			msg.mobile3=fy.getMsg('请输入正确的手机号');
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.mobile3+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}else{
			var t = 60;
			var v = fy.getMsg('发送验证码');
			$.ajax({																	
				type: 'post',
				url: ctxsso + "/security/forgetPasswordGetSms.htm?mobile="+mobile+"&validateCode="+validateCode,
				dataType: 'json',
				success: function(data){
					if(data.status!="0"){
						layer.msg(data.message);
						return false;
					}
					var sh = setInterval(function() {
						if (t > 0) {
							$("#smsSender").val(fy.getMsg('重新获取')+"(" + t-- + ")");
							$("#smsSender").attr("disabled", "");
						} else {
							window.clearInterval(0);
							$("#smsSender").val(fy.getMsg('发送短信验证码'));
							$("#smsSender").removeAttr("disabled");
							clearInterval(sh);
						}
					}, 1000);
					$("#smsSender").attr("disabled", "disabled");
					layer.msg(data.message);
					return true;
				},
				error: function(data){
					layer.msg(fy.getMsg('发送失败：请重新发送手机验证码！'));
					return false;
				}
			});
		}
	});
	
});
