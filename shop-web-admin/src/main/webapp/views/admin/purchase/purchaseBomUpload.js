$(function(){
	
	/**
	 * 提交表单
	 * */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				//"expiryTime":{required: true,},
			},
			messages: {
				//"expiryTime":{required: "必填项",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				}else if("expiryTime"==element.attr("name")){
					error.appendTo(element.parent().parent());
				}else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				if (document.getElementById("excel").files.length > 0) {
					layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
					form.submit();
			} else {
				layer.msg(fy.getMsg('请选择上传BOM文件'));
			}
			}
		});
	}
	
});