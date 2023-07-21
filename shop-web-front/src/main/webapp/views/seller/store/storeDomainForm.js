$(function(){
	if(jsValidate){
		var oldDomain = $("#oldDomain").val();
		$("#inputForm").validate({
			rules: {
				"domain":{regex:/^[A-Za-z][A-Za-z0-9]*$/,remote: ctxs+"/store/storeDomain/validateDomain.htm?oldDomain=" + encodeURIComponent(oldDomain),required: true,maxlength:64,minlength:4,},
			},
			messages: {
				"domain":{regex:fy.getMsg('请输入合格的域名要求'),remote: fy.getMsg('二级域名已存在'),required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过64字符'),minlength:fy.getMsg('最小长度不能少于4字符'),},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(".input-xlarge")){
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
				var domain = $("#domain").val();
				if(domain.length >= 4){
					if(domain.substr(0,4)=='shop'){
						layer.msg(fy.getMsg('二级域名不能已shop开头'));
						return false;
					}
				}
				var domainForbiddenWordss = domainForbiddenWords.split(',');
				for (var i = 0; i < domainForbiddenWordss.length; i++) {
					if(domain == domainForbiddenWordss[i]){
						layer.msg(fy.getMsg('二级域名不能是')+domainForbiddenWordss[i]);
						return false;
					}
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});