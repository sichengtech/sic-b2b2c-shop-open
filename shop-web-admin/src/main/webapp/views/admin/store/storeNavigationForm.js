$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"storeNavigationId":{required: true,maxlength:19,},
				"navNumber":{required: true,maxlength:10,},
				"sellerId":{required: true,maxlength:19,},
				"name":{required: true,maxlength:64,},
				"isOpen":{required: true,maxlength:1,},
				"sort":{required: true,maxlength:10,regex:/^(0|[1-9][0-9]*)$/,},
				"action":{required: true,maxlength:10,},
				"url":{required: true,maxlength:255,},
				"target":{required: true,maxlength:64,},
			},
			messages: {
				"storeNavigationId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"navNumber":{required: "必填项",maxlength:"最大长度不能超过10字符",},
				"sellerId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"name":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"isOpen":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"sort":{required: "必填项",maxlength:"最大长度不能超过10字符",regex:"排序要输入数字",},
				"action":{required: "必填项",maxlength:"最大长度不能超过10字符",},
				"url":{required: "必填项",maxlength:"最大长度不能超过255字符",},
				"target":{required: "必填项",maxlength:"最大长度不能超过64字符",},
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