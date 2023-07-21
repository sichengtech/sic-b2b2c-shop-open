$(function(){
	alert(fy.getMsg("任务已存在，请选择其他日期"));
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"beginTime":{required: true,maxlength:19,},
			},
			messages: {
				"beginTime":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
			},
			submitHandler: function(form){
				var name=$("input[name='beginTime']").val();
				console.log(name);
				$.ajax({
					url:ctxa+"/settlement/settlementTaskMain/checkTaskName.do?name="+name,
					type:'get',
					dataType:'json',
					success:function(data){
						if(data){
							layer.msg(fy.getMsg("任务已存在，请选择其他日期"));
							return false;
						}else{
							$("input[name='endTime']").val(name+" 23:59:59")
							layer.msg(fy.getMsg("正在提交，请稍等..."), {icon: 16,shade: 0.30,time: 0});
							form.submit();
						}
					},
					error:function(data){
						layer.msg(fy.getMsg("请求发生错误，请稍后再试"));
						return false;
					}
				});
			}
		});
	}
});