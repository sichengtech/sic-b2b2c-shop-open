	 $(document).ready(function(){
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