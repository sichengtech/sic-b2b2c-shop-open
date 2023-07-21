$(function(){
	//列表中删除按钮的弹框
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm('确定要删除么？',href); 
	});
	
	//列表搜索
	$(".searchList").click(function(){
		$("#searchForm").submit();
	});
	
});
