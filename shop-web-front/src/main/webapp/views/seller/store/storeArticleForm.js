$(function(){
	if(jsValidate){
		$("#myForm").validate({
			rules: {
				"title":{required: true,maxlength:255,},
				"sort":{maxlength:10,regex:/^[0-9]*$/,},
			},
			messages: {
				"title":{required: fy.getMsg('请填写文章标题'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
				"sort":{maxlength:fy.getMsg('最大长度10字符'),regex:fy.getMsg('排序要输入数字'),},
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
				//验证文章内容
				 var content = UE.getEditor('container').getContent();
		         if(content==""||content==null){
		        	 fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 文章内容不能为空",2000);
		             return false;
		         }
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});