$(function(){
	//清除图片
	$(".clean").click(function(){
		var attr =$(this).attr("attr");
		$(this).parent().parent().find(".imgPath").val("");
		$(this).parent().parent().find("img").attr("src",ctxStatic+"/sicheng-seller/images/nopreview.png");
		$(this).parent().parent().find(".existSize_"+attr).val("0");
		$(this).parent().parent().find(".result").html("");
		$(this).parent().parent().find(".input-large").val("");
		layer.msg(fy.getMsg('清除成功'));
	});
	
	//提交验证
	$("#inputForm").submit(function(){
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
});