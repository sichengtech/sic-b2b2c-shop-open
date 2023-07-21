$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"pId":{required: true,maxlength:19,},
				"carIds":{required: true,maxlength:4000,},
				"n1":{maxlength:10,},
				"n2":{maxlength:10,},
				"n3":{maxlength:10,},
				"f1":{maxlength:12,},
				"f2":{maxlength:12,},
				"f3":{maxlength:12,},
				"d1":{},
				"d2":{},
				"d3":{},
			},
			messages: {
				"pId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"carIds":{required: "必填项",maxlength:"最大长度不能超过4000字符",},
				"n1":{maxlength:"最大长度不能超过10字符",},
				"n2":{maxlength:"最大长度不能超过10字符",},
				"n3":{maxlength:"最大长度不能超过10字符",},
				"f1":{maxlength:"最大长度不能超过12字符",},
				"f2":{maxlength:"最大长度不能超过12字符",},
				"f3":{maxlength:"最大长度不能超过12字符",},
				"d1":{},
				"d2":{},
				"d3":{},
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