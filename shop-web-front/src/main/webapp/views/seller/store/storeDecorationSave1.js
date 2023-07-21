$(function(){
	$(".store_decoration_div1").click(function(){
		$(".store_decoration_div1").css("border","");
		var t = $(this).find("h2").html();
		if(t==fy.getMsg('模板一')){
			$("#solution").val("1");
			$(".bs-docs-example").css("display","none");
			$(this).attr("style","border: solid 4px #DD2726");
		}
		if(t==fy.getMsg('模板二')){
			$("#solution").val("2");
			$(".bs-docs-example").css("display","block");
			$(this).css("border","solid 4px #DD2726");
		}
		if(t==fy.getMsg('模板三')){
			$("#solution").val("3");
			$(".bs-docs-example").css("display","block");
			$(this).css("border","solid 4px #DD2726");
		}
	});
	
	if(jsValidate){
		$("#inputForm").validate({
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(".input-xlarge")){
					error.appendTo(element.parent());
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
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});