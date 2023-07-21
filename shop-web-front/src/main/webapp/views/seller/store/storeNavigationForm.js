$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"storeNavigationId":{required: true,maxlength:19,},
				"navNumber":{required: true,maxlength:10,},
				"sellerId":{required: true,maxlength:19,},
				"name":{required: true,maxlength:64,},
				"sort":{required: true,maxlength:10,},
				"action":{required: true,maxlength:10,},
				"url":{required: true,maxlength:255,},
			},
			messages: {
				"storeNavigationId":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过19字符'),},
				"navNumber":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过10字符'),},
				"sellerId":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过19字符'),},
				"name":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"sort":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过10字符'),},
				"action":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过10字符'),},
				"url":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
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
				var url=$("#url").val();//输入的导航链接
				var localProtocol=window.location.protocol;//获取地址栏的协议部分
				var localHostname=window.location.hostname;//获取地址栏的域名  
				var domain= localProtocol+"//"+localHostname;
				var reg=/^((https|http)?:\/\/)[^\s]+/;
				if(reg.test(url)){
					if(!url.indexOf(domain)==0){  
						//来自其他网站url    
						layer.msg(fy.getMsg('请输入本站导航链接'));
						return false;
					}
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});