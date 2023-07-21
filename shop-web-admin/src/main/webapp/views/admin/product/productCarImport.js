$(document).ready(function(){
	//如果js验证开关是开的就进行js验证
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
			},
			messages: {
			},
			errorPlacement: function(error, element) {
			},
			submitHandler: function(form){
				if (document.getElementById("excel").files.length > 0) {
						layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
						form.submit();
				} else {
					layer.msg('请选择文件');
				}
			}
		});
	}
 });