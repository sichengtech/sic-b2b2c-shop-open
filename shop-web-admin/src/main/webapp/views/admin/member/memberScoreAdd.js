$(document).ready(function(){
		//如果js验证开关是开的就进行js验证
		if(jsValidate){
			$("#inputForm").validate({
				submitHandler: function(form){
					top.$.jBox.tip("正在提交，请稍等...",'loading',{opacity:0});
					$("button[type='submit']").prop("disabled",true);
					//form.submit();
				}
			});
		}
	 });