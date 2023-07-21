$(function(){
	/**
	 * 关键字,价格区间
	 */
	$("#search").click(function(){
		var mixPrice = $("#mixPrice").val();
		var maxPrice = $("#maxPrice").val();
		var ssid = $("#ssid").val();
		var k = $("#k").val();
		manager.putUrl(ctxf + "/store/"+ssid+"/pList.htm");
		manager.add("price",mixPrice+"-"+maxPrice);
		manager.add("k",k);
		manager.del("pageNo");
		manager.del("pageSize");
		var url = manager.getUrl();
		location.href = url;
	});
});