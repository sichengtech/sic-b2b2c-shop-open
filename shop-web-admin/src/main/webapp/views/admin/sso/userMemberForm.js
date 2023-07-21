$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"uId":{required: true,maxlength:19,},
				"point":{required: true,maxlength:64,},
				"isBuy":{required: true,maxlength:1,},
				"notBuyDateStart":{},
				"notBuyDateEnd":{},
				"balance":{required: true,maxlength:12,},
				"frozenMoney":{required: true,maxlength:12,},
				"paymentPassword":{maxlength:64,},
				"memberTagId":{maxlength:19,},
				"realName":{maxlength:64,},
				"headPicPath":{maxlength:64,},
				"sex":{required: true,maxlength:1,},
				"birthday":{},
				"postcode":{maxlength:64,},
				"countryId":{maxlength:19,},
				"countryName":{maxlength:64,},
				"provinceId":{maxlength:19,},
				"provinceName":{maxlength:64,},
				"cityId":{maxlength:19,},
				"cityName":{maxlength:64,},
				"districtId":{maxlength:19,},
				"districtName":{maxlength:64,},
				"detailedAddress":{maxlength:255,},
				"qq":{maxlength:64,},
				"microblog":{maxlength:64,},
				"weChat":{maxlength:64,},
			},
			messages: {
				"uId":{required: fy.getMsg('必填项') ,maxlength:fy.getMsg('最大长度不能超过') + 19+ fy.getMsg('字符') ,},
				"point":{required: fy.getMsg('必填项') ,maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"isBuy":{required: fy.getMsg('必填项') ,maxlength:fy.getMsg('最大长度不能超过') + 1+ fy.getMsg('字符') ,},
				"notBuyDateStart":{},
				"notBuyDateEnd":{},
				"balance":{required: fy.getMsg('必填项') ,maxlength:fy.getMsg('最大长度不能超过') + 12+ fy.getMsg('字符') ,},
				"frozenMoney":{required: fy.getMsg('必填项') ,maxlength:fy.getMsg('最大长度不能超过') + 12+ fy.getMsg('字符') ,},
				"paymentPassword":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"memberTagId":{maxlength:fy.getMsg('最大长度不能超过') + 19+ fy.getMsg('字符') ,},
				"realName":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"headPicPath":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"sex":{required: fy.getMsg('必填项') ,maxlength:fy.getMsg('最大长度不能超过') + 1+ fy.getMsg('字符') ,},
				"birthday":{},
				"postcode":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"countryId":{maxlength:fy.getMsg('最大长度不能超过') + 19+ fy.getMsg('字符') ,},
				"countryName":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"provinceId":{maxlength:fy.getMsg('最大长度不能超过') + 19+ fy.getMsg('字符') ,},
				"provinceName":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"cityId":{maxlength:fy.getMsg('最大长度不能超过') + 19+ fy.getMsg('字符') ,},
				"cityName":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"districtId":{maxlength:fy.getMsg('最大长度不能超过') + 19+ fy.getMsg('字符') ,},
				"districtName":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"detailedAddress":{maxlength:fy.getMsg('最大长度不能超过') + 255+ fy.getMsg('字符') ,},
				"qq":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"microblog":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
				"weChat":{maxlength:fy.getMsg('最大长度不能超过') + 64+ fy.getMsg('字符') ,},
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
				layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});