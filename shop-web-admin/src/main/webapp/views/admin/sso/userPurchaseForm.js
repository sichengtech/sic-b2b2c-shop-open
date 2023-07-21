$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"uId":{required: true,maxlength:19,},
				"authType":{maxlength:2,},
				"name":{maxlength:64,},
				"type":{maxlength:2,},
				"industry":{maxlength:2,},
				"businesslicense":{maxlength:64,},
				"introduce":{maxlength:255,},
				"countryId":{maxlength:19,},
				"countryName":{maxlength:64,},
				"provinceId":{maxlength:19,},
				"provinceName":{maxlength:64,},
				"cityId":{maxlength:19,},
				"cityName":{maxlength:64,},
				"districtId":{maxlength:19,},
				"districtName":{maxlength:64,},
				"detailedAddress":{maxlength:255,},
				"contacts":{maxlength:64,},
				"contactsTelephone":{maxlength:64,},
			},
			messages: {
				"uId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"authType":{maxlength:"最大长度不能超过2字符",},
				"name":{maxlength:"最大长度不能超过64字符",},
				"type":{maxlength:"最大长度不能超过2字符",},
				"industry":{maxlength:"最大长度不能超过2字符",},
				"businesslicense":{maxlength:"最大长度不能超过64字符",},
				"introduce":{maxlength:"最大长度不能超过255字符",},
				"countryId":{maxlength:"最大长度不能超过19字符",},
				"countryName":{maxlength:"最大长度不能超过64字符",},
				"provinceId":{maxlength:"最大长度不能超过19字符",},
				"provinceName":{maxlength:"最大长度不能超过64字符",},
				"cityId":{maxlength:"最大长度不能超过19字符",},
				"cityName":{maxlength:"最大长度不能超过64字符",},
				"districtId":{maxlength:"最大长度不能超过19字符",},
				"districtName":{maxlength:"最大长度不能超过64字符",},
				"detailedAddress":{maxlength:"最大长度不能超过255字符",},
				"contacts":{maxlength:"最大长度不能超过64字符",},
				"contactsTelephone":{maxlength:"最大长度不能超过64字符",},
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
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});