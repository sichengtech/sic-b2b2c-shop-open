$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"skuId":{required: true,maxlength:19,},
				"pId":{required: true,maxlength:19,},
				"sort":{required: true,maxlength:10,regex:/^(0|[1-9][0-9]*)$/,},
				"isNotSpec":{maxlength:1,},
				"spec1":{maxlength:64,},
				"spec1V":{maxlength:64,},
				"spec2":{maxlength:64,},
				"spec2V":{maxlength:64,},
				"spec3":{maxlength:64,},
				"spec3V":{maxlength:64,},
				"spec4":{maxlength:64,},
				"spec4V":{maxlength:64,},
				"price":{maxlength:12,},
				"stock":{maxlength:10,},
				"sn":{maxlength:64,},
			},
			messages: {
				"skuId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"pId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"sort":{required: "必填项",maxlength:"最大长度不能超过10字符",regex:"排序要输入数字",},
				"isNotSpec":{maxlength:"最大长度不能超过1字符",},
				"spec1":{maxlength:"最大长度不能超过64字符",},
				"spec1V":{maxlength:"最大长度不能超过64字符",},
				"spec2":{maxlength:"最大长度不能超过64字符",},
				"spec2V":{maxlength:"最大长度不能超过64字符",},
				"spec3":{maxlength:"最大长度不能超过64字符",},
				"spec3V":{maxlength:"最大长度不能超过64字符",},
				"spec4":{maxlength:"最大长度不能超过64字符",},
				"spec4V":{maxlength:"最大长度不能超过64字符",},
				"price":{maxlength:"最大长度不能超过12字符",},
				"stock":{maxlength:"最大长度不能超过10字符",},
				"sn":{maxlength:"最大长度不能超过64字符",},
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