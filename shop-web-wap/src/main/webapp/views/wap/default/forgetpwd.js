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
	 * 找回密码方式的切换
	 */
	$('#tab2').tab({defaultIndex:0,activeClass:"tab-red"});
	$(".weui_navbar_item").click(function(){
		var id=$(this).attr("id");
		if(id=="mobileNum"){
			$("#form1").css("display","");
			$("#form2").css("display","none");
		}
		if(id=="emailNum"){
			$("#form1").css("display","none");
			$("#form2").css("display","");
		}
	});
	
	/**
	 * 手机号找回密码表单非空验证
	 */
	var forgetPwd1=function(){
		var mobile=$("#mobile").val();
		var mcode=$("#mcode").val();
		var mpassword=$("#mpassword").val();
		var mnextPassword=$("#mnextPassword").val();
		if((mobile==""||mobile==null)||(mcode==""||mcode==null)||
				(mpassword==""||mpassword==null)||(mnextPassword==""||mnextPassword==null)){
			$("#form1 .weui_btn").attr("class","weui_btn weui_btn_default weui_btn_disabled");
		}else{
			$("#form1 .weui_btn").attr("class","weui_btn weui_btn_warn submit1");
		}
	}
	$("#mobile,#mcode,#mpassword,#mnextPassword").keyup(function() {
		forgetPwd1();
	});
	
	/**
	 * 手机号找回密码提交表单
	 */
	var $form1 = $("#form1");
	$form1.form();
	$("#form1").delegate(".submit1",'click',function(){
	    $form1.validate(function(error){
	    	var password=$("#mpassword").val();//设置密码
			var nextPassword=$("#mnextPassword").val();//确认密码
			var registerInfo=siteRegisterInfo();//注册设置信息
			//判断密码最大长度
			if(registerInfo==null && password.length>64){
				layer.open({content: "密码不能超过64字符",skin: 'msg',time: 2});
				return false;
			}
			//判断密码最大长度
			if(registerInfo!=null && password.length>registerInfo.pwdMax){
				layer.open({content: "密码不能超过"+pwdMax+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断密码最小长度
			if(registerInfo!=null && password.length<registerInfo.pwdMin){
				layer.open({content: "密码不能超过"+pwdMin+"字符",skin: 'msg',time: 2});
				return false;
			}
			//判断两次密码输入是否一致
			if(password!=nextPassword){
				layer.open({content: "设置密码与确认密码不一致",skin: 'msg',time: 2});
				return false;
			}
	        if(!error){
	        	$.ajax({
	            	type: 'post',
	                url:ctxw+'/api/v1/user/forgetPwd/edit.htm',
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
	 * 手机号找回密码获取验证码
	 */
	 $("#getSmsCode").click(function() {
		if($(this).hasClass("weui_btn_default")){
			return false;
		}
		var mobile=$("#mobile").val();
		var regex=/^(\+\d{2})?(1[0-9]\d{9})$/;
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
			 url:ctxw+'/api/v1/sms/forgetPwd/getCode.htm',
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
	 * 邮箱地址找回密码表单非空验证
	 */
	var forgetPwd2=function(){
		var email=$("#email").val();
		var ecode=$("#ecode").val();
		var epassword=$("#epassword").val();
		var enextPassword=$("#enextPassword").val();
		if((email==""||email==null)||(ecode==""||ecode==null)||
				(epassword==""||epassword==null)||(enextPassword==""||enextPassword==null)){
			$("#form2 .weui_btn").attr("class","weui_btn weui_btn_default weui_btn_disabled");
		}else{
			$("#form2 .weui_btn").attr("class","weui_btn weui_btn_warn submit2");
		}
	}
	$("#email,#ecode,#epassword,#enextPassword").keyup(function() {
		forgetPwd2();
	});
	
	/**
	 *  邮箱地址找回密码提交表单
	 */
	var $form2 = $("#form2");
	$form2.form();
	$("#form2").delegate(".submit2",'click',function(){
	    $form2.validate(function(error){
	    	var password=$("#epassword").val();//设置密码
			var nextPassword=$("#enextPassword").val();//确认密码
			var registerInfo=siteRegisterInfo();//注册设置信息
			//判断密码最大长度
			if(registerInfo==null && password.length>64){
				layer.open({content: "密码不能超过64字符",skin: 'msg',time: 2});
				return false;
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
			//判断两次密码输入是否一致
			if(password!=nextPassword){
				layer.open({content: "设置密码与确认密码不一致",skin: 'msg',time: 2});
				return false;
			}
	        if(!error){
	        	$.ajax({
	            	type: 'post',
	                url:ctxw+'/api/v1/user/forgetPwd/edit.htm',
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
	 *  邮箱地址找回密码获取验证码
	 */
	 $("#getEmailCode").click(function() {
		if($(this).hasClass("weui_btn_default")){
			return false;
		}
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
            url:ctxw+'/api/v1/eamil/forgetPwd/getCode.htm',
            data: {email : email},
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
	var password_is_visible=function(e){
		var a= $(e).parent().parent().find("input").attr("type");
		if (a=="password"){
			$(e).css("color","#f1224b");
			$(e).parent().parent().find("input").attr("type","text");
		}else{
			$(e).css("color","");
			$(e).parent().parent().find("input").attr("type","password");
		}
	}
	$("#span1,#span2,#span3,#span4").on("click", function(){
		password_is_visible(this);
	});
});