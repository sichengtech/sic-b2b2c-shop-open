$(function(){
	//账号登录
	$(".input-ok1").click(function(){
		var loginName = $("#loginName1").val();
		var password = $("#password1").val();
		var rememberMe = $("#rememberMe").val();
		var pId = parent.$("#pId").val();
		var pass=true;
		var msg={'loginName':'','password':''};
		if(loginName==null || loginName==''){
			pass=false;
			msg.loginName=fy.getMsg("用户名不能为空");
		}
		if(password==null || password==''){
			pass=false;
			msg.password=fy.getMsg("密码不能为空");
		}
		if(!pass){
			var msgAll=msg.loginName+" "+msg.password;
			layer.msg(msgAll);
			return false;
		}
		$.ajax({																			
			  type: 'post',
			  url: ctxsso + "/login/login.htm",
			  data: {"loginName":loginName,"password":password,"rememberMe":rememberMe,'ssoReg':'1' },
			  dataType: 'json',
			  success: function(data){
				  if(data.status=="5"){
					  //关闭弹出登录框
					  var index = parent.layer.getFrameIndex(window.name);
					  parent.layer.close(index);
					  //提示登陆成功
					  parent.layer.msg(fy.getMsg("登录成功"));
					  //修改页面顶部信息
					  parent.changeTopBar();
				  }
				  if(data.status=="6"){
					  layer.msg(data.message);
				  }
			  },
			  error: function(data){
				  return false;
			  }
		});
	});
	
	$(".input-ok2").click(function(){
		//手机号登录
		var mobile = $("#loginName2").val();
		var password = $("#password2").val();
		var imgcode = $("#validateCode2").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','password':'','imgcode':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1=fy.getMsg("手机号码不能为空");
		}
		if(mobile.length > 11){
			pass=false;
			msg.mobile2=fy.getMsg("请输入11位手机号");
		}
		if(password==null || password==''){
			pass=false;
			msg.password=fy.getMsg("密码不能为空");
		}
		if(imgcode==null || imgcode==''){
			pass=false;
			msg.imgcode=fy.getMsg("验证码不能为空");
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.password+" "+msg.imgcode;
			layer.msg(msgAll);
			return false;
		}
		$.ajax({																			
			  type: 'post',
			  url: ctxsso + "/login/login.htm",
			  data: {"loginName":mobile,"password":password,'ssoReg':'2' },
			  dataType: 'json',
			  success: function(data){
				  if(data.status=="5"){
					  //关闭弹出登录框
					  var index = parent.layer.getFrameIndex(window.name);
					  parent.layer.close(index);
					  //提示登陆成功
					  parent.layer.msg(fy.getMsg("登录成功"));
					  //修改页面顶部信息
					  parent.changeTopBar();
				  }
				  if(data.status=="6"){
					  layer.msg(data.message);
				  }
			  },
			  error: function(data){
				  return false;
			  }
		});
	});
	
	/**
	 * 发送短信验证码
	 */
	$("#smsSender").click(function(){
		var mobile = $("#loginName2").val();
		var validateCode = $("#validateCode2").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','validateCode':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1=fy.getMsg("手机号码不能为空");
		}
		if(mobile.length > 11){
			pass=false;
			msg.mobile2=fy.getMsg("请输入11位手机号");
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode=fy.getMsg("验证码不能为空");
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}else{
			var t = 60;
			var v = fy.getMsg('发送验证码');
			$.ajax({																	
				type: 'post',
				url: ctxsso + "/security/loginGetSms.htm?mobile="+mobile+"&validateCode="+validateCode,
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
							$("#smsSender").html(fy.getMsg("发送验证码"));
							$("#smsSender").removeAttr("disabled");
							clearInterval(sh);
						}
					}, 1000);
					$("#smsSender").attr("disabled", "disabled");
					layer.msg(data.message);
					return true;
				},
				error: function(data){
					layer.msg(fy.getMsg("发送失败：请重新发送手机验证码！"));
					return false;
				}
			});
		}
	});
});