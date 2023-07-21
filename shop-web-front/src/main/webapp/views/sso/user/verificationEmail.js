$(function(){
	/**
	 * 更换邮箱
	 */
	$("#inputForm1").submit(function(){
		var email = $("#email").val();
		var validateCode = $("#validateCode1").val();
	    var msg={'email1':'','email2':'','validateCode':''};
		var pass=true;
		if(email==null || email==''){
			pass=false;
			msg.email1=fy.getMsg('邮件不能为空');
		}
		var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	    if (!reg.test(email)) {
			pass=false;
			msg.email2=fy.getMsg('请输入正确的邮箱');
	    }
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.email1=fy.getMsg('验证码不能为空');
		}
		if(!pass){
			var msgAll=msg.email1+" "+msg.email2+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
	/**
	 * 发送激活邮件
	 */
	$("#inputForm2").submit(function(){
		var validateCode = $("#validateCode2").val();
		if(validateCode==null || validateCode==''){
			layer.msg(fy.getMsg('验证码不能为空'));
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
	/**
	 * 点击"换个邮箱按钮"
	 */
	$("#change1").click(function(){
		$("#change2").css("display","");
		$("#change2").css("display","block");
	});
	
	/**
	 * 点击"重新发送邮件"
	 */
	$("#send1").click(function(){
		$("#send2").css("display","");
		$("#send2").css("display","block");
	});
});
