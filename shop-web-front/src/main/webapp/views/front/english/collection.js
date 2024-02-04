$(function(){
	
	/**
	 * 防止collection.js重复加载
	 * 一个页面中有多处收藏模块，各个模块做为独立的文件被includ到需要的页面中。可能会造成collection.js重复加载
	 */
	if(!window.collection_48tig8r3749849508fjg5) {
		window.collection_48tig8r3749849508fjg5={};
	}else{
		//走到这里说明collection.js在之前已加载了过了，我们不希望重复加载。
		//所以下面return了，防止重复加载。
		return;
	}
	
	/**
     * 收藏商品
     */
	$(".fav").click(function(){ 
		var status = usm.isLogin();
		if(status == false){
			//未登录
			layer.open({
				  type: 2,
				  title: '',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['500px', '390px'],
				  content:ctxf+"/custom/login.htm"
				});
		}else{
			//已登录直接收藏
			var pId = $(this).attr("pId");
			$.ajax({																			
				  type: 'post',
				  url: ctxf + "/method/collectionProduct.htm?pId="+pId,
				  dataType: 'json',
				  success: function(data){
					  if(data.status=="0"){
						  layer.msg(fy.getMsg("收藏成功！"));
					  }
					  if(data.status=="1"){
						  layer.msg(fy.getMsg("收藏失败，账号未登录！"));
					  }
					  if(data.status=="3"){
						  layer.msg(fy.getMsg("收藏失败，商品不存在！"));
					  }
					  if(data.status=="4"){
						  layer.msg(fy.getMsg("商品已收藏过！"));
					  }
				  },
				  error: function(data){
					  return false;
				  }
			});
		}
	});
	
	/**
	 * 收藏店铺
	 */
	$(".collectionStore").click(function(){
		var status = usm.isLogin();
		if(status == false){
			//未登录
			layer.open({
				  type: 2,
				  title: '',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['500px', '390px'],
				  content:ctxf+"/custom/login.htm"
				});
		}else{
			//已登录直接收藏
			var storeId = $(this).attr("storeId");
			$.ajax({																			
				  type: 'post',
				  url: ctxf + "/method/collectionStore.htm?storeId="+storeId,
				  dataType: 'json',
				  success: function(data){
					  if(data.status=="0"){
						  layer.msg(fy.getMsg("收藏成功！"));
					  }
					  if(data.status=="1"){
						  layer.msg(fy.getMsg("收藏失败，账号未登录！"));
					  }
					  if(data.status=="3"){
						  layer.msg(fy.getMsg("收藏失败，店铺不存在！"));
					  }
					  if(data.status=="4"){
						  layer.msg(fy.getMsg("店铺已收藏过！"));
					  }
				  },
				  error: function(data){
					  return false;
				  }
			});
		}
	});
});