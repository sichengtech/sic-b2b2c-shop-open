$(function(){
	//删除
	$(".deleDate").click(function(){
		//询问框
		layer.confirm('您确定要进行删除操作吗?', {
			btn: ['确定','关闭'] //按钮
		}, function(){
			layer.msg('确定删除', {icon: 1});
		}); 
	});
});