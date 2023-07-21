 $(document).ready(function(){
	 /**
	 * 图片放大控件的使用
	 */
	function magnificPopup(){
		$('.gallery').each(function() { // 你所有的画廊的容器
			$('.gallery').magnificPopup({
				delegate : 'a', // 画廊项目的选择器
				type : 'image',
				gallery : {
					enabled : true
				}
			});
		});
	};
	magnificPopup();
	 
	/**
	 * 如果js验证开关是开的就进行js验证
	 */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {"businessHandleRemarks":{required: true,maxlength:1024,}},
			messages: {"businessHandleRemarks":{required: fy.getMsg('请填入备注'),maxlength:fy.getMsg('最大长度不能超过1024字符'),}},
			submitHandler: function(form){
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
 });