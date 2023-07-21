$(function(){
	/**
	 * 单行删除
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href); 
	});
	/**
	 * 批量删除
	 */
	$(".deleteAll").click(function(){
		if($(".trCheck:checked").length == 0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择要删除的行'),2000);
			return false;
		}
		var arr=[];//定义一个数组
		//遍历并获取当前被选中的id
		$(".trCheck:checked").each(function(){
			if ($(this).prop("checked")) {
				var id=$(this).val();
				arr.push(id);//向数组中放入ID
			}
		});
		var href=$(this).attr("href")+"?ids="+arr;
		fdp.confirm(fy.getMsg('确定要删除所选么？'),href); 
	});
	/**
	 * 批量标记已读
	 */
	$(".read").click(function(){
		if($(".trCheck:checked").length == 0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('请选择要标记已读的行'),2000);
			return false;
		}
		var arr=[];//定义一个数组
		//遍历并获取当前被选中的id
		$(".trCheck:checked").each(function(){
			if ($(this).prop("checked")) {
				var id=$(this).val();
				arr.push(id);//向数组中放入ID
			}
		});
		var href=$(this).attr("href")+"?ids="+arr;
		fdp.confirm(fy.getMsg('确定要标记所选为已读么？'),href); 
	});
	
	$(".leader").click(function(){
		if ($(this).prop("checked")) {
			$("#tbody").find('input[type="checkbox"]').prop("checked", true);
		}else {
			$("#tbody").find('input[type="checkbox"]').prop("checked", false);
		} 
	});
	
	$("#tbody").find('input[type="checkbox"]').click(function(){
		var bl=false;
		$(this).each(function(){
			if ($(this).prop("checked")){
				bl=true;
			}
		});
		if(bl){
			var b2=true;
			$("#tbody").find('input[type="checkbox"]').not(".leader").each(function(){
				if (!$(this).prop("checked")){
					b2 = false;
				}
			});
			if(b2){
				$(".leader").prop("checked", true);
			}
		}else{
			$(".leader").prop("checked", false);
		} 
	});
});