$(function(){
	/**
	 * 修改jquery validate的验证规则，
	 * 修改为可以验证相同name的多个input框
	 * */
	if($.validator){
		$.validator.prototype.elements = function () {
			var validator = this,
			rulesCache = {};
			return $(this.currentForm)
			.find("input, select, textarea")
			.not(":submit, :reset, :image, [disabled]")
			.not(this.settings.ignore)
			.filter(function () {
				if (!this.name && validator.settings.debug && window.console) {
					console.error("%o has no name assigned", this);
				}
				rulesCache[this.name] = true;
				return true;
			});
		}
	}
	
	/**
	 * 立即报价-提交表单
	 * */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"offerPrices":{required: true,maxlength:12,regex:/^([1-9]\d{0,8}|0)(\.\d{1,2})?$/},
				"offerRemarks":{maxlength:200,},
			},
			messages: {
				"offerPrices":{required: fy.getMsg('单价不能为空'),maxlength:fy.getMsg('最大长度不能超过12字符'),regex:fy.getMsg('输入正整数或两位以内的正小数')},
				"offerRemarks":{maxlength:fy.getMsg('最大长度不能超过200字符')},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				}else{
					error.insertAfter(element.siblings("br"));
				}
			},
			submitHandler: function(form){
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				var status = usm.isLogin();//usm-判断用户是否登录
				if(status == false){
			    	layer.open({
					  type: 2,
					  title: '',
					  shadeClose: true,
					  shade: 0.8,
					  area: ['500px', '390px'],
					  content:ctxf+"/custom/login.htm"
					});
			    	return false;
		    	}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
});