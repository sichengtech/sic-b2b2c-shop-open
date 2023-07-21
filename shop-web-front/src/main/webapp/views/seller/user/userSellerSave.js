$(function(){
	var myreg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
	var usernameMin = $("#usernameMin").val();
	var usernameMax = $("#usernameMax").val();
	var pwdMin = $("#pwdMin").val();
	var pwdMax = $("#pwdMax").val();
	if(usernameMin==null || usernameMin==''){
		usernameMin=6;
	}
	if(usernameMax==null || usernameMax==''){
		usernameMax=20;
	}
	if(pwdMin==null || pwdMin==''){
		pwdMin=6;
	}
	if(pwdMax==null || pwdMax==''){
		pwdMax=20;
	}
	//表单的js验证
	if(jsValidate){
		var oldLoginName = $("#oldLoginName").val();
		var uId = $("#uId").val();
		if(uId==null || uId==''){
			//保存表单的js验证
			$("#myFrom").validate({
				rules: {
					"loginName":{required: true,maxlength:usernameMax,minlength:usernameMin,remote: ctxs+"/user/userSeller/exitLoginName.htm?oldLoginName=" + encodeURIComponent(oldLoginName),},
					"password":{required: true,maxlength:pwdMax,minlength:pwdMin,},
					"nextPassword":{required: true,equalTo:"#password",},
					/*"phone":{required: true,maxlength:64,regex:/^[0-9]*$/,},*/
					"listRole":{required: true,},
				},
				messages: {
					"loginName":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('长度不能超过')+usernameMax+fy.getMsg('字符'),minlength:fy.getMsg('长度不能小于')+usernameMin+fy.getMsg('字符'),remote: fy.getMsg('账号名已存在'),},
					"password":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过')+pwdMax+fy.getMsg('字符'),minlength:fy.getMsg('最大长度不能小于')+pwdMin+fy.getMsg('字符'),},
					"nextPassword":{required: true,equalTo:fy.getMsg('密码和确认密码不一致'),},
					/*"phone":{required: fy.getMsg('必填项'),maxlength:"最大长度不能超过64字符",regex:"手机号要输入数字",},*/
					"listRole":{required: fy.getMsg('必填项'),},
				},
				errorPlacement: function(error, element) {
					//错误提示信息的显示位置
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent());
					} else {
						error.insertAfter(element);
					}
				},
				submitHandler: function(form){
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}
			});
		}else{
			//修改表单的js验证
			$("#myFrom").validate({
				rules: {
					"loginName":{required: true,maxlength:usernameMax,minlength:usernameMin,remote: ctxs+"/user/userSeller/exitLoginName.htm?oldLoginName=" + encodeURIComponent(oldLoginName),},
					"nextPassword":{equalTo:"#password",},
					"email":{required: true,maxlength:64,regex:myreg,},
					/*"phone":{required: true,maxlength:64,regex:/^[0-9]*$/,},*/
					"listRole":{required: true,},
				},
				messages: {
					"loginName":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('长度不能超过')+usernameMax+fy.getMsg('字符'),minlength:fy.getMsg('长度不能小于')+usernameMin+fy.getMsg('字符'),remote: fy.getMsg('账号名已存在'),},
					"nextPassword":{equalTo:fy.getMsg('密码和确认密码不一致'),},
					"email":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过64字符'),regex:fy.getMsg('请输入正确的邮箱地址'),},
					/*"phone":{required: fy.getMsg('必填项'),maxlength:"最大长度不能超过64字符",regex:"手机号要输入数字",},*/
					"listRole":{required: fy.getMsg('必填项'),},
				},
				errorPlacement: function(error, element) {
					//错误提示信息的显示位置
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent());
					} else {
						error.insertAfter(element);
					}
				},
				submitHandler: function(form){
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
				}
			});
		}
	}
});