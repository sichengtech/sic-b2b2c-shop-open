$(function(){
	/**
	 * 登录方式的切换
	 */
	$("body").delegate(".weui_navbar_item","click",function(){
		var id=$(this).attr("id");
		if(id=="smsCodeLogin"){
			$("#"+id).addClass("tab-red");
			$("#logiName").removeClass("tab-red");
			$("#form1").css("display","");
			$("#form2").css("display","none");
		}
		if(id=="logiName"){
			$("#"+id).addClass("tab-red");
			$("#smsCodeLogin").removeClass("tab-red");
			$("#form1").css("display","none");
			$("#form2").css("display","");
		}
	});
	
	/**
	 * 短信验证码登录表单非空验证
	 */
	var login1=function(){
		var mobile=$("#mobile").val();
		var smsCode=$("#smsCode").val();
		if((mobile==""||mobile==null)||(smsCode==""||smsCode==null)){
			$("#form1 .weui_btn").attr("class","weui_btn weui_btn_default weui_btn_disabled");
		}else{
			$("#form1 .weui_btn").attr("class","weui_btn weui_btn_warn submit1");
		}
	}
	
	$("#mobile,#smsCode").keyup(function() {
		login1();
	});
	
	/**
	 * 短信验证码登录表单提交
	 */
	$("#form1").delegate(".submit1",'click',function(){
		var $form1 = $("#form1");
		$form1.form();
		$form1.validate(function(error){
			if(!error){
	        	$.ajax({
	            	type: 'post',
	                url:ctxw+'/api/v1/user/login/authentication.htm',
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
	 * 获取验证码
	 */
	$("body").delegate("#getCode",'click',function(){
		if($(this).hasClass("weui_btn_default")){
			return false;
		}
		var mobile=$("#mobile").val();
		var regex=/^(\+\d{2})?(1[0-9]\d{9})$/;
		var t = 60;
		var obj = $('#getCode');
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
			 url:ctxw+'/api/v1/sms/login/getCode.htm',
			 data: {mobile : mobile},
			 dataType: 'json',
			 success: function(data){
				 if(data.status!="200"){
					 window.clearInterval(0);
					 obj.text(v);
					 obj.removeClass("weui_btn_default weui_btn_disabled");
					 clearInterval(sh);
					 layer.open({
						 content: data.message,
						 skin: 'msg',
						 time: 5 //2秒后自动关闭
					 });
					 return false;
				 }
				 var sh = setInterval(function() {
					 if (t > 0) {
						 obj.text("重新获取(" + t-- + ")");
						 $("#getCode").addClass("weui_btn_default weui_btn_disabled");
					 } else {
						 window.clearInterval(0);
						 obj.text(v);
						 obj.removeClass("weui_btn_default weui_btn_disabled");
						 clearInterval(sh);
					 }
				 }, 1000);
				 layer.open({
					 content: data.message,
					 skin: 'msg',
					 time: 2 //2秒后自动关闭
				 });
			 },
			 error: function(){
				 layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
			 }
		 });
	 });
	
	/**
	 * 账号登录表单非空验证
	 */
	var login2=function(){
		var loginName=$("#loginName").val();
		var password=$("#password").val();
		if((loginName==""||loginName==null)||(password==""||password==null)){
			$("#form2 .weui_btn").attr("class","weui_btn weui_btn_default weui_btn_disabled");
		}else{
			$("#form2 .weui_btn").attr("class","weui_btn weui_btn_warn submit2");
		}
	}
	
	$("#loginName,#password").keyup(function() {
		login2();
	});
	
	/**
	 * 账号登录表单提交
	 */
	$("#form2").delegate(".submit2",'click',function(){
		var $form2 = $("#form2");
		$form2.form();
	    $form2.validate(function(error){
	        if(!error){
	        	$.ajax({
	            	type: 'post',
	                url:ctxw+'/api/v1/user/login/authentication.htm',
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
	 * 密码是否可见
	 */
	$("body").delegate("#span","click",function(){
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