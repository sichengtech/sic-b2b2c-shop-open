$(document).ready(function() {
	/**
	 * 列表中删除按钮的弹框
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href); 
	});
	
});
