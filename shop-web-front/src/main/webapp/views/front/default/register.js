$(function(){
	$(".input-ok").click(function(){
		var type = $(this).attr('attr');
		$(this).parent().parent().find('.type').val(type);
	});

	/**
	 * 使用账号注册
	 */
	$("#inputForm1").submit(function(){
		var loginName = $("#loginName").val();
		var email = $("#email").val();
		var password = $("#password").val();
		var repassword=$("#repassword").val();
		var validateCode = $("#emailCodeVerification").val();
		var agreement = $("#agreeType1").is(':checked');
		var msg={'loginName1':'','loginName2':'','loginName3':'','loginName4':'','loginName5':'','email1':'','email2':'','password1':'','password2':'','password3':'','password4':'','password5':'','validateCode':'','agreement':''};
		var pass=true;
		var repPass=/^[0-9a-zA-Z _-]{1,}$/;//验证由数字和26个英文字母组成的字符串
		if(loginName==null || loginName==''){
			pass=false;
			msg.loginName1=fy.getMsg('用户名不能为空');
		}
		if(disableUsername!=""){
			var disableName = disableUsername.split(",");
			for (i=0;i<disableName.length;i++){
				if(disableName[i]==loginName){
					pass=false;
					msg.loginName2=fy.getMsg('用户名不能为')+disableName[i];
				}
			} 
		}
		if(loginName.length<usernameMin){
			pass=false;
			msg.loginName3=fy.getMsg('用户名不能少于')+usernameMin+fy.getMsg('字符');
		}
		if(loginName.length>usernameMax){
			pass=false;
			msg.loginName4=fy.getMsg('用户名不能大于')+usernameMax+fy.getMsg('字符');
		}
		if(!repPass.test(loginName)){
			pass=false;
			msg.loginName5=fy.getMsg('用户名请输入字母或数字');
		}
		if(email==null || email==''){
			pass=false;
			msg.email1=fy.getMsg('邮件不能为空');
		}
		var reg = /^[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?$/;
	    if (!reg.test(email)) {
			pass=false;
			msg.email2=fy.getMsg('请输入正确的邮箱');
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
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(!agreement){
			pass=false;
			msg.agreement=fy.getMsg('请同意会员注册条款');
		}
		if(!pass){
			var msgAll=msg.loginName1+" "+msg.loginName2+" "+msg.loginName3+" "+msg.loginName4+" "+msg.loginName5+" "+msg.email1+" "+msg.email2+" "+msg.password1+" "+msg.password2+" "+msg.password3+" "+msg.password4+" "+msg.password5+" "+msg.validateCode+""+msg.agreement;
			layer.msg(msgAll);
			return false;
		}
		var error = '';
		//验证账号是否有效
		$.ajax({																	
			type: 'post',
			url: ctxsso + "/register/ajaxAccountRegisterVerification.htm?loginName="+loginName+"&email="+email,
			dataType: 'json',
			async: false, 
			success: function(data){
				if(data.status1=="1"){
					error = error + data.error1;
				}
				if(data.status2=="2"){
					error = error + "," + data.error2;
				}
			},
			error: function(data){
				layer.msg(fy.getMsg('验证失败'));
				error = fy.getMsg('验证失败');
			}
		});
		if(error!=null && error!=''){
			layer.msg(error);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});

	/**
	 * 发送邮箱验证码
	 */
	$("#emailSender").click(function(){
		var email = $("#email").val();
		var pass=true;
		var msg={'email1':'','email2':''};
		if(email==null || email==''){
			pass=false;
			msg.email1=fy.getMsg('邮件不能为空');
		}
		var reg = /^[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?$/;
		if (!reg.test(email)) {
			pass=false;
			msg.email2=fy.getMsg('请输入正确的邮箱');
		}
		if(!pass){
			var msgAll=msg.email1+" "+msg.email2;
			layer.msg(msgAll);
			return false;
		}else{
			var t = 60;
			var v = fy.getMsg('发送验证码');
			$.ajax({
				type: 'post',
				url: ctxsso + "/security/registerGetEmail.htm?email="+email,
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
					layer.msg(fy.getMsg('发送失败：请重新发送邮箱验证码！'));
					return false;
				}
			});
		}
	});
	
	/**
	 * 使用手机注册
	 */
	$("#inputForm2").submit(function(){
		var mobile = $("#mobile").val();
		var smsVerification = $("#smsVerification").val();
		var validateCode = $("#validateCode2").val();
		var agreement = $("#agreeType2").is(':checked');
		var pass=true;
		var msg={'mobile1':'','mobile2':'','mobile3':'','smsVerification':'','validateCode':'','agreement':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1=fy.getMsg('手机号码不能为空');
		}
		if(mobile.length > 11){
			pass=false;
			msg.mobile2=fy.getMsg('请输入11位手机号');
		}
		var reg = /^1\d{10}$/;
	    if (!reg.test(mobile)) {
			pass=false;
			msg.mobile3=fy.getMsg('请输入正确的手机号');
	    }
		if(smsVerification==null || smsVerification==''){
			pass=false;
			msg.smsVerification=fy.getMsg('短信验证码不能为空');
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(!agreement){
			pass=false;
			msg.agreement=fy.getMsg('请同意会员注册条款');
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.mobile3+" "+msg.smsVerification+" "+msg.validateCode+" "+msg.agreement;
			layer.msg(msgAll);
			return false;
		}
		var error = '';
		//验证手机是否有效
		$.ajax({																	
			type: 'post',
			url: ctxsso + "/register/ajaxMobileRegisterVerification.htm?mobile="+mobile,
			dataType: 'json',
			async: false, 
			success: function(data){
				if(data.status1=="1"){
					error = error + data.error1;
				}
			},
			error: function(data){
				layer.msg(fy.getMsg('验证失败'));
				error = fy.getMsg('验证失败');
			}
		});
		if(error!=null && error!=''){
			layer.msg(error);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
	/**
	 * 发送短信验证码
	 */
	$("#smsSender").click(function(){
		var mobile = $("#mobile").val();
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
				url: ctxsso + "/security/registerGetSms.htm?mobile="+mobile+"&validateCode="+validateCode,
				dataType: 'json',
				success: function(data){
					if(data.status!="0"){
						layer.msg(data.message);
						return false;
					}
					var sh = setInterval(function() {
						if (t > 0) {
							$("#smsSender").html(fy.getMsg('重新获取')+"(" + t-- + ")");
							$("#smsSender").attr("disabled", "");
						} else {
							window.clearInterval(0);
							$("#smsSender").html(fy.getMsg('发送验证码'));
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
	
	/**
	 * 会员注册条款
	 */
	$(".agreement").click(function(){
		var agreement = $("#agree").html();
		//页面层
		layer.open({
			type: fy.getMsg('会员注册条款'),
			skin: 'layui-layer-rim', //加上边框
			area: ['800px', '500px'], //宽高
			content: agreement
		});
	});
});
