$(function(){
	/**
	 * 商品的hover事件
	 * 鼠标移上去加边框
	 * */
	$(".store-goods-list .goods-pic-list li").hover(function(){
	    $(this).addClass("cur");
	},function(){
		$(this).removeClass("cur");
	});
	
	/**
	 * 商品小图片的hover事件
	 * 鼠标移上去加边框，商品大图换成当前指的图片
	 * */
	$(".go-color img").hover(function(){
		$(this).parent().find(".cur").removeClass("cur");
		$(this).addClass("cur");
		$(this).parent().siblings(".p-picture").find("img").attr("src",$(this).attr("src"));
	});
	
	/**
	 * 综合
	 */
	$("#comprehensiveSort").click(function(){
		manager.del("sort");
		manager.del("sortMode");
		var url = manager.getUrl();
		location.href = url;
	});
	
	/**
	 * 销量排行(默认降序)
	 */
	$("#salesVolumeSort").click(function(){
		manager.add("sort","allSales");
		manager.add("sortMode","DESC");
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		location.href = url;
	});
	
	/**
	 * 收藏排行(默认降序)
	 */
	$("#collectionSort").click(function(){
		manager.add("sort","collectionCount");
		manager.add("sortMode","DESC");
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		location.href = url;
	});
	
	/**
	 * 评论排行(默认降序)
	 */
	$("#commentCountSort").click(function(){
		manager.add("sort","commentCount");
		manager.add("sortMode","DESC");
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		location.href = url;
	});
	
	/**
	 * 价格
	 */
	$("#priceSort").click(function(){
		var priceType = $(this).find("i").attr("class");
		if(priceType=='desc'){
			$(this).find("i").removeAttr("class");
			$(this).find("i").attr("class","asc");
			manager.add("sort","minPrice");
			manager.add("sortMode","ASC");
			manager.del("pageNo");
			manager.del("pageSize");
			var url = manager.getUrl();
			location.href = url;
		}
		if(priceType=='asc'){
			$(this).find("i").removeAttr("class");
			$(this).find("i").attr("class","desc");
			manager.add("sort","minPrice");
			manager.add("sortMode","DESC");
			manager.del("pageNo");
			manager.del("pageSize");
			var url = manager.getUrl();
			location.href = url;
		}
	});
	
	/**
	 * 价格区间
	 */
	$(".searchBtn").click(function(){
		var mixPrice1 = $("#mixPrice1").val();
		var maxPrice1 = $("#maxPrice1").val();
		var v1 = "";
		var v2 = "";
		var v3 = "";
		var status = false;
		if(mixPrice1!=null && mixPrice1!=""){
			v1 += mixPrice1;
			status =true;
		}
		if(maxPrice1!=null && maxPrice1!=""){
			v2 += maxPrice1;
			status =true;
		}
		if(status){
			var v3 = v1 + "-" + v2; 
			manager.add("price",v3);
		}else{
			manager.del("price");
		}
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		location.href = url;
	});
	
});