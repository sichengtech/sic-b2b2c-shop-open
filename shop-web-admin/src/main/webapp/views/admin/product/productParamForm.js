$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"paramId":{required: true,maxlength:19,},
				"categoryId":{required: true,maxlength:19,},
				"paramSort":{required: true,maxlength:10,regex:/^(0|[1-9][0-9]*)$/,},
				"name":{required: true,maxlength:64,},
				"paramValues":{required: true,maxlength:1024,},
				"valuesImg":{maxlength:1024,},
				"type":{maxlength:1,},
				"format":{maxlength:1,},
				"isDisplay":{required: true,maxlength:1,},
				"isRequired":{required: true,maxlength:1,},
			},
			messages: {
				"paramId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"categoryId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"paramSort":{required: "必填项",maxlength:"最大长度不能超过10字符",regex:"排序要输入数字",},
				"name":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"paramValues":{required: "必填项",maxlength:"最大长度不能超过1024字符",},
				"valuesImg":{maxlength:"最大长度不能超过1024字符",},
				"type":{maxlength:"最大长度不能超过1字符",},
				"format":{maxlength:"最大长度不能超过1字符",},
				"isDisplay":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"isRequired":{required: "必填项",maxlength:"最大长度不能超过1字符",},
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