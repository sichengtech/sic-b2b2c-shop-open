$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"addressId":{required: true,maxlength:19,},
				"mId":{required: true,maxlength:19,},
				"name":{required: true,maxlength:64,},
				"countryId":{required: true,maxlength:19,},
				"countryName":{required: true,maxlength:64,},
				"provinceId":{required: true,maxlength:19,},
				"provinceName":{required: true,maxlength:64,},
				"cityId":{required: true,maxlength:19,},
				"cityName":{required: true,maxlength:64,},
				"districtId":{required: true,maxlength:19,},
				"districtName":{required: true,maxlength:64,},
				"detailedAddress":{required: true,maxlength:255,},
				"mobile":{required: true,maxlength:64,},
				"zipCode":{maxlength:64,},
				"isDefault":{required: true,maxlength:1,},
			},
			messages: {
				"addressId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"mId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"name":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"countryId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"countryName":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"provinceId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"provinceName":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"cityId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"cityName":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"districtId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"districtName":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"detailedAddress":{required: "必填项",maxlength:"最大长度不能超过255字符",},
				"mobile":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"zipCode":{maxlength:"最大长度不能超过64字符",},
				"isDefault":{required: "必填项",maxlength:"最大长度不能超过1字符",},
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