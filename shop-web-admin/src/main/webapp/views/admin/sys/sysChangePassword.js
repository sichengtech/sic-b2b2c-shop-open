	 $(function(){
		if(jsValidate){
			$("#inputForm").validate({
				rules: {
					oldPassword:{required:true,maxlength:100,},
					newPassword:{regex:/^\S{6,}$/,required:true,maxlength:100,},
					confirmNewPassword:{required:true,equalTo:"#newPassword",maxlength:10,},
				},
				messages: {
					oldPassword:{required:fy.getMsg("旧密码不能为空"),maxlength:fy.getMsg("密码不能超过11字符"),},
					newPassword:{regex:fy.getMsg("密码至少6字符"),required:fy.getMsg("新密码不能为空"),maxlength:fy.getMsg("密码不能超过10字符"),},
					confirmNewPassword:{required:fy.getMsg("确认新密码不能为空"),equalTo:fy.getMsg("新密码和确认新密码不一致"),maxlength:fy.getMsg("密码不能超过10字符"),},
				},
				submitHandler: function(form){
					top.$.jBox.tip(fy.getMsg("正在提交，请稍等..."),'loading',{opacity:0});
					$("button[type='submit']").prop("disabled",true);
					form.submit();
				}
			});
		}
	 });