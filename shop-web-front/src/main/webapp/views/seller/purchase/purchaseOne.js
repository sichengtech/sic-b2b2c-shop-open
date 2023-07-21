$(function(){
	/**
	 * 表单验证
	 */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"title":{required: true,maxlength:64,},
				"cycle":{required: true,maxlength:64,},
				"purchaseExplain":{maxlength:64,},
				"expiryTime":{required: true,maxlength:64,},
				"content":{required: true,maxlength:255,},
			},
			messages: {
				"title":{required: fy.getMsg('采购标题不能为空'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"cycle":{required: fy.getMsg('交货周期不能为空'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"purchaseExplain":{maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"expiryTime":{required: fy.getMsg('采购到期时间不能为空'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"content":{required: fy.getMsg('一句话不能为空'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
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
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});