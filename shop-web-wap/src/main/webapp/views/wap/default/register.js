var registerInfo=null;
$(function(){
	/**
	 * 获取注册设置信息
	 */
	var siteRegisterInfo=function(){
		if(registerInfo!=null){
			return registerInfo;
		}
		$.ajax({
	    	type: 'get',
	        url:ctxw+'/api/v1/siteRegister/info.htm',
	        dataType: 'json',
	        async: false,
	        success: function(data){
				if(data.status!="200"){
					layer.open({content: data.message,skin: 'msg',time: 5});
					return false;
				}
				registerInfo=data.data;
	        },
	        error: function(){
	            layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	        }
	    });
		return registerInfo;
	}
	
	/**
	 * 给页面添加注册协议
	 */
	if(siteRegisterInfo()!=null){
		$("#agreementContent").html(siteRegisterInfo().agreement);
	}
	
	/**
	 * 注册方式的切换
	 */
	$('#tab2').tab({defaultIndex:0,activeClass:"tab-red"});
	$(".weui_navbar_item").click(function(){
		var id=$(this).attr("id");
		if(id=="smsCode"){
			$("#form1").css("display","");
			$("#form2").css("display","none");
		}
		if(id=="logiName"){
			$("#form1").css("display","none");
			$("#form2").css("display","");
		}
	});
	
	/**
	 * 短信验证码登录表单非空验证
	 */
	var register1=function(){
		var mobile=$("#mobile").val();
		var mobileCode=$("#mobileCode").val();
		if((mobile==""||mobile==null)|(mobileCode==""||mobileCode==null)){
			$("#form1 .weui_btn").attr("class","weui_btn weui_btn_default weui_btn_disabled");
		}else{
			$("#form1 .weui_btn").attr("class","weui_btn weui_btn_warn submit1");
		}
	}
	$("#mobile,#mobileCode").keyup(function() {
		register1();
	});
	
	/**
	 *手机号注册表单提交
	 */
	var $form1 = $("#form1");
	$form1.form();
	$("#form1").delegate(".submit1",'click',function(){
		$form1.validate(function(error){
			var checkbox = $("#checkbox").is(':checked');//是否勾选用户注册协议
			//判断是否勾选用户注册协议
			if(!checkbox){
				layer.open({content: "请勾选注册协议",skin: 'msg',time: 2});
				return false;
			}
			if(!error){
	        	$.ajax({
	            	type: 'post',
	                url:ctxw+'/api/v1/user/register/save.htm',
	                data: $("#form1").serialize(),
	                dataType: 'json',
	                success: function(data){
	    				if(data.status!="200"){
	    					layer.open({content: data.message,skin: 'msg',time: 5});
	    					return false;
	    				}
	    				layer.open({content: data.message,skin: 'msg',time: 2});
    					location.href = ctxw+data.data;
	                },
	                error: function(){
	                    layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	                }
	            });
			}
		});
	});
	
	 /**
	  * 获取短信验证码
	  */
	 $("#getSmsCode").click(function() {
		 if($(this).hasClass("weui_btn_default")){
			 return false;
		 }
		 var mobile=$("#mobile").val();
		 var regex=/^(\+\d{2})?(1[0-9]\d{9})$/;//+8613569696968 TODO  zjl测试
		 var t = 60;
		 var obj = $('#getSmsCode');
		 var v = '获取验证码';
		 if(mobile=="" || mobile==null || typeof(mobile)=="undefined"){
			 layer.open({content: "请输入手机号",skin: 'msg',time: 2});
			 return false;
		 }
		 if(!regex.test(mobile)){
			 layer.open({content: "请输入正确手机号",skin: 'msg',time: 2});
			return false;
		 }
		 $.ajax({
			 type: 'post',
			 url:ctxw+'/api/v1/sms/register/getCode.htm',
			 data: {mobile : mobile},
			 dataType: 'json',
			 success: function(data){
				 if(data.status!="200"){
					 window.clearInterval(0);
					 obj.text(v);
					 obj.removeClass("weui_btn_default weui_btn_disabled");
					 clearInterval(sh);
					 layer.open({content: data.message,skin: 'msg',time: 5});
					 return false;
				 }
				 var sh = setInterval(function() {
					 if (t > 0) {
						 obj.text("重新获取(" + t-- + ")");
						 $("#getSmsCode").addClass("weui_btn_default weui_btn_disabled");
					 } else {
						 window.clearInterval(0);
						 obj.text(v);
						 obj.removeClass("weui_btn_default weui_btn_disabled");
						 clearInterval(sh);
					 }
				 }, 1000);
				 layer.open({content: data.message,skin: 'msg',time: 2});
			 },
			 error: function(){
				 layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
			 }
		 });
	 });
	
	/**
	 * 账号注册表单非空验证
	 */
	var register2=function(){
		var loginName=$("#loginName").val();
		var password=$("#password").val();
		var email=$("#email").val();
		var emailCode=$("#emailCode").val();
		if((loginName==""||loginName==null)||(password==""||password==null)||
				(email==""||email==null)||(emailCode==""||emailCode==null)){
			$("#form2 .weui_btn").attr("class","weui_btn weui_btn_default weui_btn_disabled");
		}else{
			$("#form2 .weui_btn").attr("class","weui_btn weui_btn_warn submit2");
		}
	}
	$("#loginName,#password,#email,#emailCode").keyup(function() {
		register2();
	});

	/**
	 * 账号登录表单提交
	 */
	var $form2 = $("#form2");
	$form2.form();
	$("#form2").delegate(".submit2",'click',function(){
	    $form2.validate(function(error){
	    	var loginName=$("#loginName").val();//用户名
			var password=$("#password").val();//密码
			var checkbox = $("#checkbox").is(':checked');//是否勾选用户注册协议
			var registerInfo=siteRegisterInfo();//注册设置信息
			var regex=/^[A-Za-z0-9]+$/;//字母或数字的正则表达式
			//判断用户名是否是字母或数字
			if(!regex.test(loginName)){
				layer.open({content: "用户名请输入字母或数字",skin: 'msg',time: 2});
				return false;
			}
			//判断密码是否是字母或数字
			if(!regex.test(password)){
				layer.open({content: "密码请输入字母或数字",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名最大长度
			if(registerInfo==null && loginName.length>64){
				layer.open({content: "用户名不能超过64字符",skin: 'msg',time: 2});
				return false;
			}
			//判断密码最大长度
			if(registerInfo==null && password.length>64){
				layer.open({content: "密码不能超过64字符",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名最大长度
			if(registerInfo!=null && loginName.length>registerInfo.usernameMax){
				layer.open({content: "用户名不能超过"+registerInfo.usernameMax+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名最小长度
			if(registerInfo!=null && loginName.length<registerInfo.usernameMin){
				layer.open({content: "用户名不能小于"+registerInfo.usernameMin+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断用户名是否有违禁
			if(registerInfo!=null && registerInfo.disableUsername!=null && registerInfo.disableUsername!=""){
				var disableName = registerInfo.disableUsername.split(",");
				for (i=0;i<disableName.length;i++){
					if(disableName[i]==loginName){
						layer.open({content: "用户名不能为"+disableName[i],skin: 'msg',time: 2});
						return false;
					}
				} 
			}
			//判断密码最大长度
			if(registerInfo!=null && password.length>registerInfo.pwdMax){
				layer.open({content: "密码不能超过"+registerInfo.pwdMax+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断密码最小长度
			if(registerInfo!=null && password.length<registerInfo.pwdMin){
				layer.open({content: "密码不能小于"+registerInfo.pwdMin+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断是否勾选用户注册协议
			if(!checkbox){
				layer.open({content: "请勾选注册协议",skin: 'msg',time: 2});
				return false;
			}
	        if(!error){
	        	$.ajax({
	            	type: 'post',
	                url:ctxw+'/api/v1/user/register/save.htm',
	                data: $("#form2").serialize(),
	                dataType: 'json',
	                success: function(data){
	    				if(data.status!="200"){
	    					layer.open({content: data.message,skin: 'msg',time: 5});
	    					return false;
	    				}
	    				layer.open({content: data.message,skin: 'msg',time: 2});
    					location.href = ctxw+data.data;
	                },
	                error: function(){
	                    layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	                }
	            });
	        }
	    });
	});
	
	/**
	 * 获取邮箱验证码
	 */
	 $("#getEmailCode").click(function() {
		if($(this).hasClass("weui_btn_default")){
			return false;
		}
		var loginName=$("#loginName").val();
		var email=$("#email").val();
		var regex=/\w+@\w+\.[a-z]+(\.[a-z]+)?/;
		var t = 60;
		var obj = $('#getEmailCode');
		var v = '获取验证码';
		if(email=="" || email==null || typeof(email)=="undefined"){
			 layer.open({content: "请输入邮箱地址",skin: 'msg',time: 2});
			 return false;
		}
		if(!regex.test(email)){
			layer.open({content: "请输入正确邮箱地址",skin: 'msg',time: 2});
			return false;
		}
    	$.ajax({
        	type: 'post',
            url:ctxw+'/api/v1/email/register/getCode.htm',
            data: {email : email,loginName:loginName},
            dataType: 'json',
            success: function(data){
            	if(data.status!="200"){
           			window.clearInterval(0);
         			obj.text(v);
         			obj.removeClass("weui_btn_default weui_btn_disabled");
         			clearInterval(sh);
         			layer.open({content: data.message,skin: 'msg',time: 5});
         			return false;
           		}
       			var sh = setInterval(function() {
       				if (t > 0) {
       					obj.text("重新获取(" + t-- + ")");
       					$("#getEmailCode").addClass("weui_btn_default weui_btn_disabled");
       				} else {
       					window.clearInterval(0);
       					obj.text(v);
       					obj.removeClass("weui_btn_default weui_btn_disabled");
       					clearInterval(sh);
       				}
       			}, 1000);
       			layer.open({content: data.message,skin: 'msg',time: 2});
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
	 });
	
	/**
	 * 密码是否可见
	 */
	$("#span").on("click", function(){
		var a= $(this).parent().parent().find("input").attr("type");
		if (a=="password"){
			$(this).css("color","#f1224b");
			$(this).parent().parent().find("input").attr("type","text");
		}else{
			$(this).css("color","");
			$(this).parent().parent().find("input").attr("type","password");
		}
	});
});