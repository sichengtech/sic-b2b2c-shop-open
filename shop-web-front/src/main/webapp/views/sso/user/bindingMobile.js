$(function(){
	/**
	 * 提交验证
	 */
	$("#inputForm").submit(function(){
		var mobile = $("#mobile").val();
		var validateCode = $("#validateCode").val();
		var smsVerification = $("#smsVerification").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','mobile3':'','validateCode':'','smsVerification':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1=fy.getMsg('手机不能为空');
		}
		if(mobile.length != 11){
			pass=false;
			msg.mobile2=fy.getMsg('请输入11位手机号');
		}
		var reg = /^1\d{10}$/;
	    if (!reg.test(mobile)) {
			pass=false;
			msg.mobile3=fy.getMsg('请输入正确手机号');
	    }
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg('验证码不能为空');
		}
		if(smsVerification==null || smsVerification==''){
			pass=false;
			msg.smsVerification=fy.getMsg('动态码不能为空');
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.mobile3+" "+msg.validateCode+" "+msg.smsVerification;
			layer.msg(msgAll);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
	/**
	 * 发送手机验证码
	 */
	$("#smsSender").click(function(){
		var mobile = $("#mobile").val();
		var validateCode = $("#validateCode").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','mobile3':'','validateCode':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1=fy.getMsg('手机不能为空');
		}
		if(mobile.length != 11){
			pass=false;
			msg.mobile2=fy.getMsg('请输入11位手机号');
		}
		var reg = /^1\d{10}$/;
	    if (!reg.test(mobile)) {
			pass=false;
			msg.mobile3=fy.getMsg('请输入正确手机号');
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
				url: ctxsso + "/security/bindingGetSms.htm?mobile="+mobile+"&validateCode="+validateCode,
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
					layer.msg(fy.getMsg('发送失败：请重新发送手机验证码！'));
					return false;
				}
			});
		}
	});
});