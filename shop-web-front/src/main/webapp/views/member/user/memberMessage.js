$(function(){
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
			$("#tbody").find('input[type="checkbox"]').each(function(){
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
	
	/**
	 * 单个删除
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href); 
	});
	
	/**
	 * 批量删除
	 */
	$(".deleteSureBatch").click(function(){
		var length = $("#tbody").find('input[type="checkbox"]:checked').length;
		if(length == 0){
			layer.msg(fy.getMsg('请选中消息在删除'));
		}else{
			var informationIds = new Array();
			$("#tbody").find('input[type="checkbox"]:checked').each(function(){
				informationIds.push($(this).attr('informationId'));
			});
			var href = ctxm + "/user/memberMessage/deleteBatch.htm?informationIds="+informationIds;
			fdp.confirm(fy.getMsg('确定要批量删除么？'),href); 
		}
	});
	
	/**
	 * 批量标记为已读
	 */
	$(".readBatch").click(function(){
		var length =$("#tbody").find('input[type="checkbox"]:checked').length;
		if(length == 0){
			layer.msg(fy.getMsg('请选中消息在标记为已读'));
		}else{
			var informationIds = new Array();
			$("#tbody").find('input[type="checkbox"]:checked').each(function(){
				informationIds.push($(this).attr('informationId'));
			});
			var href = ctxm + "/user/memberMessage/readBatch.htm?informationIds="+informationIds;
			fdp.confirm(fy.getMsg('确定要批量已读么？'),href); 
		}
	});
});