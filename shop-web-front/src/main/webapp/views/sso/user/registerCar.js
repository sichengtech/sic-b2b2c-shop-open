$(function(){
	/**
	 * 会员注册条款
	 */
	$(".agreement").click(function(){
		var agreement = $("#agree").html();
		//页面层
		layer.open({
		  type: '会员注册条款',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['800px', '500px'], //宽高
		  content: agreement
		});
	});
	
	/**
	 * 个人注册点击身份选择中的普通用户,使用车型的隐藏
	 */
	$(".identity_ordinary").click(function(){
		$(".identity_apply_car").css('display','none'); 
		$(".carName").parent().parent().css('display','none');
	});
	
	/**
	 * 个人注册点击身份选择中的车主用户，使用车型的显示
	 */
	$(".identity_car").click(function(){
		$(".identity_apply_car").css('display','block');
	});
	
	/**
	 * 个人注册点击身份选择中的车主用户,编辑
	 */
	$(".changeCarName").click(function(){
		$(this).parent().parent().css('display','none');
		$(".identity_apply_car").css('display','block');
	});
	
	/**
	 * 适用车型确定
	 */
	$(".identity_car_button").click(function(){
		var carTwoName = $("#twoName").html();
		var carThreeName = $("#threeName").html();
		var carFourName = $("#fourName").html();
		if(carTwoName == '请选择'){
			carTwoName = '';
		}
		if(carThreeName == '请选择'){
			carThreeName = '';
		}
		if(carFourName == '请选择'){
			carFourName = '';
		}
		$(".carName").parent().parent().css('display','block');
		$(".carName").html(carTwoName+" "+carThreeName+" "+carFourName);
		$(".identity_apply_car").css('display','none');
	});
	
	/**
	 * 个人注册，商家注册，门店注册表单验证
	 */
	if(jsValidate){
		//个人注册
		$("#inputForm1").validate({
			rules: {
				"loginName":{remote: ctxsso+"/register/validateLoginName.htm",required: true,minlength:usernameMin,maxlength:usernameMax,regex:/^[A-Za-z0-9]+$/,},
				"password":{required: true,minlength:pwdMin,maxlength:pwdMax,regex:/^[A-Za-z0-9]+$/,},
				"repassword":{required: true,minlength:pwdMin,maxlength:pwdMax,regex:/^[A-Za-z0-9]+$/,equalTo:"#password1"},
				"mobile":{remote: ctxsso+"/register/validateMobile.htm",required: true,maxlength:11,regex:/^1\d{10}$/,},
				"validateCode":{required: true,},
				"smsVerification":{required: true,},
			},
			messages: {
				"loginName":{remote: "用户名已存在",required: "必填项",minlength: "用户名不能少于"+usernameMin+fy.getMsg('字符'),maxlength: "用户名不能大于"+usernameMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字"},
				"password":{required: "必填项",minlength: "密码不能小于"+pwdMin+fy.getMsg('字符'),maxlength: "密码不能大于"+pwdMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字"},
				"repassword":{required: "必填项",minlength: "密码不能小于"+pwdMin+fy.getMsg('字符'),maxlength: "密码不能大于"+pwdMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字",equalTo:"两次密码不一致",},
				"mobile":{remote: "手机号已存在",required: "必填项",maxlength: "手机号不能超过11字符",regex:"请填写正确手机号",},
				"validateCode":{required: "必填项",},
				"smsVerification":{required: "必填项",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox") || element.is(":radio")||element.is(".input-Code")){
					error.appendTo(element.parent());
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				//判断用户名是否有违禁
				var loginName = $("#loginName1").val();
				if(disableUsername!=""){
					var disableName = disableUsername.split(",");
					for (i=0;i<disableName.length;i++){
						if(disableName[i]==loginName){
							layer.msg("用户名不能为"+disableName[i]);
							return false;
						}
					} 
				}
				//获取是否同意注册条款
				var agreement = $("#agreeType1").is(':checked');
				if(!agreement){
					layer.msg('请同意会员注册条款');
					return false;
				}
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
		//商家注册
		$("#inputForm2").validate({
			rules: {
				"loginName":{remote: ctxsso+"/register/validateLoginName.htm",required: true,minlength:usernameMin,maxlength:usernameMax,regex:/^[A-Za-z0-9]+$/,},
				"password":{required: true,minlength:pwdMin,maxlength:pwdMax,regex:/^[A-Za-z0-9]+$/,},
				"repassword":{required: true,minlength:pwdMin,maxlength:pwdMax,regex:/^[A-Za-z0-9]+$/,equalTo:"#password2"},
				"name":{required: true,maxlength: 64,},
				"contacts":{required: true,maxlength: 32,},
				"mobile":{remote: ctxsso+"/register/validateMobile.htm",required: true,maxlength:11,regex:/^1\d{10}$/,},
				"validateCode":{required: true,},
				"smsVerification":{required: true,},
			},
			messages: {
				"loginName":{remote: "用户名已存在",required: "必填项",minlength: "用户名不能少于"+usernameMin+fy.getMsg('字符'),maxlength: "用户名不能大于"+usernameMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字"},
				"password":{required: "必填项",minlength: "密码不能小于"+pwdMin+fy.getMsg('字符'),maxlength: "密码不能大于"+pwdMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字"},
				"repassword":{required: "必填项",minlength: "密码不能小于"+pwdMin+fy.getMsg('字符'),maxlength: "密码不能大于"+pwdMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字",equalTo:"两次密码不一致",},
				"name":{required: "必填项",maxlength: "不能超过64字符",},
				"contacts":{required: "必填项",maxlength: "不能超过32字符",},
				"mobile":{remote: "手机号已存在",required: "必填项",maxlength: "手机号不能超过11字符",regex:"请填写正确手机号",},
				"validateCode":{required: "必填项",},
				"smsVerification":{required: "必填项",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox") || element.is(":radio")||element.is(".input-Code")){
					error.appendTo(element.parent());
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				//判断用户名是否有违禁
				var loginName = $("#loginName2").val();
				if(disableUsername!=""){
					var disableName = disableUsername.split(",");
					for (i=0;i<disableName.length;i++){
						if(disableName[i]==loginName){
							layer.msg("用户名不能为"+disableName[i]);
							return false;
						}
					} 
				}
				//企业类型
				var type =$(".type").val();
				if(type==null || type==''){
					layer.msg('请选择企业类型');
					return false;
				}
				//企业属性
				var industry =$(".industry").val();
				if(industry==null || industry==''){
					layer.msg('请选择行业属性');
					return false;
				}
				//获取是否同意注册条款
				var agreement = $("#agreeType2").is(':checked');
				if(!agreement){
					layer.msg('请同意会员注册条款');
					return false;
				}
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
		//门店注册
		$("#inputForm3").validate({
			rules: {
				"loginName":{remote: ctxsso+"/register/validateLoginName.htm",required: true,minlength:usernameMin,maxlength:usernameMax,regex:/^[A-Za-z0-9]+$/,},
				"password":{required: true,minlength:pwdMin,maxlength:pwdMax,regex:/^[A-Za-z0-9]+$/,},
				"repassword":{required: true,minlength:pwdMin,maxlength:pwdMax,regex:/^[A-Za-z0-9]+$/,equalTo:"#password3"},
				"name":{required: true,maxlength: 64,},
				"introduce":{required: true,maxlength: 256,},
				"detailedAddress":{required: true,maxlength: 100,},
				"burns":{required: true,maxlength: 64,},
				"brands":{maxlength: 64,},
				"bossName":{required: true,maxlength: 64,},
				"bossMobile":{required: true,maxlength:11,regex:/^1\d{10}$/,},
				"hotline":{required: true,maxlength: 64,},
				"shopkeeperName":{required: true,maxlength: 64,},
				"shopkeeperMobile":{required: true,maxlength:11,regex:/^1\d{10}$/,},
				"contactsName":{required: true,maxlength: 32,},
				"mobile":{remote: ctxsso+"/register/validateMobile.htm",required: true,maxlength:11,regex:/^1\d{10}$/,},
				"validateCode":{required: true,},
				"smsVerification":{required: true,},
			},
			messages: {
				"loginName":{remote: "用户名已存在",required: "必填项",minlength: "用户名不能少于"+usernameMin+fy.getMsg('字符'),maxlength: "用户名不能大于"+usernameMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字"},
				"password":{required: "必填项",minlength: "密码不能小于"+pwdMin+fy.getMsg('字符'),maxlength: "密码不能大于"+pwdMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字"},
				"repassword":{required: "必填项",minlength: "密码不能小于"+pwdMin+fy.getMsg('字符'),maxlength: "密码不能大于"+pwdMax+fy.getMsg('字符'),regex:"用户名请输入字母或数字",equalTo:"两次密码不一致",},
				"name":{required: "必填项",maxlength: "不能超过64字符",},
				"introduce":{required: "必填项",maxlength: "不能超过100字符",},
				"detailedAddress":{required: "必填项",maxlength: "不能超过100字符",},
				"burns":{required: "必填项",maxlength: "不能超过64字符",},
				"brands":{maxlength: "不能超过64字符",},
				"bossName":{required: "必填项",maxlength: "不能超过64字符",},
				"bossMobile":{required: "必填项",maxlength: "老板手机号不能超过11字符",regex:"老板手机号请填写正确手机号",},
				"hotline":{required: "必填项",maxlength: "不能超过64字符",},
				"shopkeeperName":{required: "必填项",maxlength: "不能超过64字符",},
				"shopkeeperMobile":{required: "必填项",maxlength: "店长手机号不能超过11字符",regex:"店长手机号请填写正确手机号",},
				"contactsName":{required: "必填项",maxlength: "不能超过32字符",},
				"mobile":{remote: "手机号已存在",required: "必填项",maxlength: "手机号不能超过11字符",regex:"请填写正确手机号",},
				"validateCode":{required: "必填项",},
				"smsVerification":{required: "必填项",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox") || element.is(":radio")||element.is(".input-Code")){
					error.appendTo(element.parent());
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				//判断用户名是否有违禁
				var loginName = $("#loginName2").val();
				if(disableUsername!=""){
					var disableName = disableUsername.split(",");
					for (i=0;i<disableName.length;i++){
						if(disableName[i]==loginName){
							layer.msg("用户名不能为"+disableName[i]);
							return false;
						}
					} 
				}
				//门店类型
				var type =$(".repairShoptype").val();
				if(type==null || type==''){
					layer.msg('请选择门店类型');
					return false;
				}
				//门店所在地
				var provinceId =$(".repairShopProvinceId").val();
				var cityId = $(".repairShopCityId").val();
				var districtId = $(".repairShopDistrictId").val();
				if(provinceId==null || provinceId==''){
					layer.msg('请选择门店所在地省');
					return false;
				}
				if(cityId==null || cityId==''){
					layer.msg('请选择门店所在地市');
					return false;
				}
				if(districtId==null || districtId==''){
					layer.msg('请选择门店所在地区/县');
					return false;
				}
				//门店人数
				var peopleCount =$(".repairShopPeopleCount").val();
				if(peopleCount==null || peopleCount==''){
					layer.msg('请选择门店人数');
					return false;
				}
				//所在部门
				var department =$(".repairShopDepartment").val();
				if(department==null || department==''){
					layer.msg('请选择所在部门');
					return false;
				}
				//获取是否同意注册条款
				var agreement = $("#agreeType3").is(':checked');
				if(!agreement){
					layer.msg('请同意会员注册条款');
					return false;
				}
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	/**
	 * 发送短信验证码(个人注册)
	 */
	$("#smsSender1").click(function(){
		var mobile = $("#mobile1").val();
		var validateCode = $("#validateCode1").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','mobile3':'','validateCode':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1="手机号码不能为空";
		}
		if(mobile.length != 11){
			pass=false;
			msg.mobile2="请输入11位手机号";
		}
		var reg = /^1\d{10}$/;
		if (!reg.test(mobile)) {
			pass=false;
			msg.mobile3="请输入正确的手机号";
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode="验证码不能为空";
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.mobile3+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}else{
			var t = 60;
			var v = '发送验证码';
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
							$("#smsSender1").html("重新获取(" + t-- + ")");
							$("#smsSender1").attr("disabled", "");
						} else {
							window.clearInterval(0);
							$("#smsSender1").html("发送验证码");
							$("#smsSender1").removeAttr("disabled");
							clearInterval(sh);
						}
					}, 1000);
					$("#smsSender").attr("disabled", "disabled");
					layer.msg(data.message);
					return true;
				},
				error: function(data){
					layer.msg("发送失败：请重新发送手机验证码！");
					return false;
				}
			});
		}
	});
	
	/**
	 * 发送短信验证码(商家注册)
	 */
	$("#smsSender2").click(function(){
		var mobile = $("#mobile2").val();
		var validateCode = $("#validateCode2").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','mobile3':'','validateCode':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1="手机号码不能为空";
		}
		if(mobile.length != 11){
			pass=false;
			msg.mobile2="请输入11位手机号";
		}
		var reg = /^1\d{10}$/;
		if (!reg.test(mobile)) {
			pass=false;
			msg.mobile3="请输入正确的手机号";
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode="验证码不能为空";
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.mobile3+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}else{
			var t = 60;
			var v = '发送验证码';
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
							$("#smsSender2").html("重新获取(" + t-- + ")");
							$("#smsSender2").attr("disabled", "");
						} else {
							window.clearInterval(0);
							$("#smsSender2").html("发送验证码");
							$("#smsSender2").removeAttr("disabled");
							clearInterval(sh);
						}
					}, 1000);
					$("#smsSender").attr("disabled", "disabled");
					layer.msg(data.message);
					return true;
				},
				error: function(data){
					layer.msg("发送失败：请重新发送手机验证码！");
					return false;
				}
			});
		}
	});
	
	/**
	 * 发送短信验证码(汽车门店注册)
	 */
	$("#smsSender3").click(function(){
		var mobile = $("#mobile3").val();
		var validateCode = $("#validateCode3").val();
		var pass=true;
		var msg={'mobile1':'','mobile2':'','mobile3':'','validateCode':''};
		if(mobile==null || mobile==''){
			pass=false;
			msg.mobile1="手机号码不能为空";
		}
		if(mobile.length != 11){
			pass=false;
			msg.mobile2="请输入11位手机号";
		}
		var reg = /^1\d{10}$/;
		if (!reg.test(mobile)) {
			pass=false;
			msg.mobile3="请输入正确的手机号";
		}
		if(validateCode==null || validateCode==''){
			pass=false;
			msg.validateCode="验证码不能为空";
		}
		if(!pass){
			var msgAll=msg.mobile1+" "+msg.mobile2+" "+msg.mobile3+" "+msg.validateCode;
			layer.msg(msgAll);
			return false;
		}else{
			var t = 60;
			var v = '发送验证码';
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
							$("#smsSender3").html("重新获取(" + t-- + ")");
							$("#smsSender3").attr("disabled", "");
						} else {
							window.clearInterval(0);
							$("#smsSender3").html("发送验证码");
							$("#smsSender3").removeAttr("disabled");
							clearInterval(sh);
						}
					}, 1000);
					$("#smsSender").attr("disabled", "disabled");
					layer.msg(data.message);
					return true;
				},
				error: function(data){
					layer.msg("发送失败：请重新发送手机验证码！");
					return false;
				}
			});
		}
	});
	
});