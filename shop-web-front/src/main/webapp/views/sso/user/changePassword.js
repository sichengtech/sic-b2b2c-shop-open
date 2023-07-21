$(function(){
	/**
	 * 使用账号注册
	 */
	$("#inputForm").submit(function(){
		var action = $(this).attr('action');
		var oldPassword = $("#oldPassword").val();
		var oldPassword1 = $("#oldPassword").length;
		var password = $("#password").val();
		var repassword=$("#repassword").val();
		var msg={'password1':'','password2':'','password3':'','password4':'','password5':'','password6':''};
		var pass=true;
		if(oldPassword1==1){
			if(oldPassword==null || oldPassword==''){
				pass=false;
				msg.password1=fy.getMsg('原密码不能为空');
			}
			$(this).attr('action',action+"?cp=1");
		}
	    if(password==null || password==''){
			pass=false;
			msg.password2=fy.getMsg('密码不能为空');
		}else{
			if(password.length>pwdMax){
				pass=false;
				msg.password3=fy.getMsg('密码不能大于')+pwdMax+fy.getMsg('字符');
			}
			if(password.length<pwdMin){
				pass=false;
				msg.password4=fy.getMsg('密码不能少于')+pwdMin+fy.getMsg('字符');
			}
		}
	    if(repassword==null || repassword==''){
			pass=false;
			msg.password5=fy.getMsg('确认密码不能为空');
		}
		if(password!=repassword){
			pass=false;
			msg.password6=fy.getMsg('两次密码必须输入一致');
		}
		if(!pass){
			var msgAll=msg.password1+" "+msg.password2+" "+msg.password3+" "+msg.password4+" "+msg.password5+" "+msg.password6;
			layer.msg(msgAll);
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
	
});
