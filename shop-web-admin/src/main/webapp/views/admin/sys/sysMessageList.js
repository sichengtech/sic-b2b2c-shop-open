$(function(){
	//删除菜单
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么') ,href);
	});
	
	//快读查询
	$('.search').on('click', function(){
			var content=$("#myModal").html();
			layer.open({
			type: 1,
			title:' <i class="fa fa-search"></i> '+fy.getMsg('查询') ,
			area: ['550px', '400px'],
			shadeClose: true, //点击遮罩关闭
			content: content,
			success: function(layero, index){
				//快速搜索
					$(layero).find("#searchFormMyModal").submit(function(){
					setTimeout(function(){
						layer.close(index);
						//如果1.5秒内未完成操作，则给出请等待的提示
						layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16,shade: 0.30,time: 0});
					},1500);
				});
			}
			});
		});
	//提交表单
	$("#searchForm").submit(function(){
		setTimeout(function(){
			//如果1.5秒内未完成操作，则给出请等待的提示
			layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16,shade: 0.30,time: 0});
		},1500);
	});
});