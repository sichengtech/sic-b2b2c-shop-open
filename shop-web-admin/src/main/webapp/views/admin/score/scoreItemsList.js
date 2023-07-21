//快速查询
$(document).ready(function(){
	$('.search').on('click', function(){
		var content=$("#myModal").html();
		layer.open({
		type: 1,
		title:' <i class="fa fa-search"></i> 查询',
		area: ['550px', '320px'],
		shadeClose: true, //点击遮罩关闭
		content: content
		});
	});
});