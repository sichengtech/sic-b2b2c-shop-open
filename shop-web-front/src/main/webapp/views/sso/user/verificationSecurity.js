$(function(){
	/**
	 * 安全验证页表单验证
	 */
	$("#inputForm").submit(function(){
		var validatekey = $("#validatekey").val();
		var validateCode = $("#validateCode").val();
		var verification = $("#verification").val();
		var msg={'validatekey':'','validateMode':'','validateCode':'','verification':''};
		var pass=true;
		if(validatekey==null || validatekey==''){
			pass=false;
			msg.validatekey=fy.getMsg('请选择验证方式');
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(verification==null || verification==''){
			pass=false;
			msg.verification=fy.getMsg('动态码不能为空');
		}
		if(!pass){
			var msgAll=msg.validatekey+" "+msg.validateCode+" "+msg.verification;
			layer.msg(msgAll);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
//		form.submit();
	});
	
	/**
	 * 发送证码
	 */
	$("#sender").click(function(){
		var validatekey = $("#validatekey").val();
		var validateCode = $("#validateCode").val();
		var msg={'validatekey':'','validateMode':'','validateCode':''};
		var pass=true;
		if(validatekey==null || validatekey==''){
			pass=false;
			msg.validatekey=fy.getMsg('请选择验证方式');
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(!pass){
			var msgAll=msg.validatekey+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}else{
			if(validatekey == '1'){
				//发送邮箱动态码
				var email=$("#email").val();
				var t = 60;
				var v = fy.getMsg('发送验证码');
				$.ajax({																	
					type: 'post',
					url: ctxsso + "/security/verificationSecurityGetEmail.htm?email="+email+"&validateCode="+validateCode,
					dataType: 'json',
					success: function(data){
						if(data.status!="0"){
							layer.msg(data.message);
							return false;
						}
						var sh = setInterval(function() {
							if (t > 0) {
								$("#sender").html(fy.getMsg('重新获取')+"(" + t-- + ")");
								$("#sender").attr("disabled", "");
							} else {
								window.clearInterval(0);
								$("#sender").html(fy.getMsg('发送验证码'));
								$("#sender").removeAttr("disabled");
								clearInterval(sh);
							}
						}, 1000);
						$("#sender").attr("disabled", "disabled");
						layer.msg(data.message);
						return true;
					},
					error: function(data){
						layer.msg(fy.getMsg('发送失败：请重新发送邮件验证码！'));
						return false;
					}
				});
				
			}
			if(validatekey == '2'){
				//发送手机动态码
				var t = 60;
				var v = fy.getMsg('发送验证码');
				var mobile=$("#mobile").val();
				$.ajax({																	
					type: 'post',
					url: ctxsso + "/security/verificationSecurityGetSms.htm?mobile="+mobile+"&validateCode="+validateCode,
					dataType: 'json',
					success: function(data){
						if(data.status!="0"){
							layer.msg(data.message);
							return false;
						}
						var sh = setInterval(function() {
							if (t > 0) {
								$("#sender").html(fy.getMsg('重新获取')+"(" + t-- + ")");
								$("#sender").attr("disabled", "");
							} else {
								window.clearInterval(0);
								$("#sender").html(fy.getMsg('发送验证码'));
								$("#sender").removeAttr("disabled");
								clearInterval(sh);
							}
						}, 1000);
						$("#sender").attr("disabled", "disabled");
						layer.msg("发送成功！");
						return true;
					},
					error: function(data){
						layer.msg(fy.getMsg('发送失败：请重新发送手机验证码！'));
						return false;
					}
				});
			}
		}
	});
});
