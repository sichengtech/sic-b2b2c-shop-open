$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"uId":{required: true,maxlength:19,},
				"type":{maxlength:2,},
				"industry":{maxlength:2,},
				"name":{maxlength:64,},
				"introduce":{maxlength:512,},
				"countryId":{maxlength:19,},
				"countryName":{maxlength:64,},
				"provinceId":{maxlength:19,},
				"provinceName":{maxlength:64,},
				"cityId":{maxlength:19,},
				"cityName":{maxlength:64,},
				"districtId":{maxlength:19,},
				"districtName":{maxlength:64,},
				"detailedAddress":{maxlength:128,},
				"customCall":{maxlength:32,},
				"companyWebsite":{maxlength:32,},
				"companyBrand":{maxlength:64,},
				"department":{maxlength:2,},
				"contacts":{maxlength:32,},
				"contactsTelephone":{maxlength:64,},
			},
			messages: {
				"uId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"type":{maxlength:"最大长度不能超过2字符",},
				"industry":{maxlength:"最大长度不能超过2字符",},
				"name":{maxlength:"最大长度不能超过64字符",},
				"introduce":{maxlength:"最大长度不能超过512字符",},
				"countryId":{maxlength:"最大长度不能超过19字符",},
				"countryName":{maxlength:"最大长度不能超过64字符",},
				"provinceId":{maxlength:"最大长度不能超过19字符",},
				"provinceName":{maxlength:"最大长度不能超过64字符",},
				"cityId":{maxlength:"最大长度不能超过19字符",},
				"cityName":{maxlength:"最大长度不能超过64字符",},
				"districtId":{maxlength:"最大长度不能超过19字符",},
				"districtName":{maxlength:"最大长度不能超过64字符",},
				"detailedAddress":{maxlength:"最大长度不能超过128字符",},
				"customCall":{maxlength:"最大长度不能超过32字符",},
				"companyWebsite":{maxlength:"最大长度不能超过32字符",},
				"companyBrand":{maxlength:"最大长度不能超过64字符",},
				"department":{maxlength:"最大长度不能超过2字符",},
				"contacts":{maxlength:"最大长度不能超过32字符",},
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