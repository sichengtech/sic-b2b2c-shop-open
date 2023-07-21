$(function(){
	/**
	 * 删除
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href); 
	});
	
	/**
	 * 加入购物车
	 */
	$(".addCartSpec").click(function(){
		var pId = $(this).attr("pId");
		var myTitle=fy.getMsg('选择规格');
		var url = ctxm + "/collect/memberCollectionProduct/addTradeCart.htm?pId="+pId;
		$.post(url, {}, function(str){
			layer.open({
				 type: 1,
				 title:myTitle,
				 area: ['600px', '300px'],
				 shadeClose: true, //点击遮罩关闭
				 content: str, //注意，如果str是object，那么需要字符拼接。
				 btn:[fy.getMsg('确定'),fy.getMsg('取消')],
				 yes: function(index, layero){
					 alert(fy.getMsg('加入购物车'));
				}
			});
		});
	});
	
});