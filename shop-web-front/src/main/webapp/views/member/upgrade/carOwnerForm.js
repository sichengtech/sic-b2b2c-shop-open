$(function(){
	/**
	 * 点击编辑
	 */
	$(".change").click(function(){
		$(".apply_carIds").css("display","block");
	});
	
	/**
	 * 提交验证
	 */
	$("#inputForm").submit(function(){
		if($(".apply_carIds").css("display")=='none'){
			layer.msg(fy.getMsg('请选择车型'));
			return false;
		}	
		var carTwoId = $("#carTwoId").val();
		var carThreeId = $("#carThreeId").val();
		if(carTwoId==null || carTwoId=='' || carThreeId==null || carThreeId==''){
			layer.msg(fy.getMsg('请选择车型'));
			return false;
		}
		layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
		form.submit();
	});
});