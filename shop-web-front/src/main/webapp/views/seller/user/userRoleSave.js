$(document).ready(function(){
	//表单的js验证
	if(jsValidate){
		var oldRoleName = $("#oldRoleName").val();
		$("#myFrom").validate({
			rules: {
				"roleName":{remote: ctxs+"/store/storeRole/exitRoleName.htm?oldRoleName=" + encodeURIComponent(oldRoleName),required: true,maxlength:64,},
				"listMenuId":{required: true,},
			},
			messages: {
				"roleName":{remote: fy.getMsg('账号组名已存在'),required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"listMenuId":{required: fy.getMsg('必填项'),},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent().siblings(".selectAll"));
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	//回显全选,回显全组  .leader
	(function(){
		var bl0=true;//回显全选的标识
		$(".leader").each(function(){
			//回显全组  .leader
			var bl=true;//回显全组的标识
			//找平级的checkbox,并排除.leader,进行循环，若都选中则选中.leader
			$(this).siblings("input[type='checkbox']").not(".leader").each(function(){
				if (!$(this).prop("checked")){
					bl=false;
				}
			});
			if(bl){
				$(this).prop("checked", true);
			}
			if(!$(this).prop("checked")){
				bl0=false;
			}
		});
		//回显全选
		if(bl0){
			$("#selectAll_id").prop("checked", true);
		}
	})();
	
	/*//回显全选
	(function(){
		var bl=true;
		$(".leader").each(function(){
			if (!$(this).prop("checked")){
				bl=false;
			}
		});
		if(bl){
			$("#selectAll_id").prop("checked", true);
		}
	})();*/
	
	//全选或全不选
	$("#selectAll_id").click(function(){
		if ($(this).prop("checked")) {
			$("input[type='checkbox']").prop("checked", true);
		}else {
			$("input[type='checkbox']").prop("checked", false);
		} 
	});

	//.leader组长 控制全组
	$(".leader").click(function(){
		if ($(this).prop("checked")) {
			$(this).siblings("input[type='checkbox']").not(this).prop("checked", true);
		}else {
			$(this).siblings("input[type='checkbox']").not(this).prop("checked", false);
		} 
	});

	//组员 反控制 组长.leader
	$("input[type='checkbox']").not("#selectAll_id,.leader").click(function(){
		if ($(this).prop("checked")) {
			var bl=true;
			//找平级的checkbox,并排除.leader,进行循环，若都选中则选中.leader
			$(this).siblings("input[type='checkbox']").not(".leader").each(function(){
				if (!$(this).prop("checked")){
					bl=false;
				}
			});
			if(bl){
				$(this).parent().children(".leader").prop("checked", true);
			}
		}else {
			$(this).parent().children(".leader").prop("checked", false);
		} 
	});

	//组员 反控制 组长 全选
	$("input[type='checkbox']").not("#selectAll_id").click(function(){
		if ($(this).prop("checked")) {
			var bl=true;
			$("input[type='checkbox']").not("#selectAll_id").each(function(){
				if (!$(this).prop("checked")){
					bl=false;
				}
			});
			if(bl){
				$("#selectAll_id").prop("checked", true);
			}
		}else {
			$("#selectAll_id").prop("checked", false);
		} 
	});
});