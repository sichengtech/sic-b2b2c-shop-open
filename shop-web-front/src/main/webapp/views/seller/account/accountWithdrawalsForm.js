$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"money":{required: true,maxlength:10,regex:/^[1-9]\d*$/},
			},
			messages: {
				"money":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过10字符"),regex:fy.getMsg("请输入正整数")},
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
				var tiedCardId = $("#tiedCardId").val();
				if(tiedCardId==null || tiedCardId==''){
					layer.msg(fy.getMsg("请选择收款账号"));
					return false;
				}
				layer.msg(fy.getMsg("正在提交，请稍等..."), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});