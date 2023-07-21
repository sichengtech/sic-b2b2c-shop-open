function selectThree(obj) {
	var e = $(obj).parent().parent().parent().parent();
	$(e).find('ul[name=threeAttr]').empty();
	$(e).find("#threeName").text(fy.getMsg('请选择'));
	$(e).find("#carThreeId").val("");
	$(e).find('ul[name=fourAttr]').empty();
	$(e).find("#fourName").text(fy.getMsg('请选择'));
	$(e).find("#carFourId").val("");
	var carTwoId = $(e).find("#carTwoId").val();
	var carFourId = $(e).find("#carFourId").val();
	$.get(ctxs + "/product/productCar/selectProductCar.htm", {parentId:carTwoId}, function(data, status) {
		$("<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>"+fy.getMsg('请选择')+"</a></li>").prependTo($(e).find('ul[name=threeAttr]'));
		for (var i = 0; i < data.length; i++) {
			var productCar=data[i];
			var msg = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="+ productCar.carId +">"+ productCar.name+"</a></li>";
			$(e).find('ul[name=threeAttr]').append(msg);
		}
	});
}

function selectFour(obj) {
	var e = $(obj).parent().parent().parent().parent();
	$(e).find('ul[name=fourAttr]').empty();
	$(e).find("#fourName").text(fy.getMsg('请选择'));
	var carThreeId = $(e).find("#carThreeId").val();
	if(carThreeId=="" || carThreeId==null){
		$(e).find('ul[name=fourAttr]').empty();
		$(e).find("#fourName").text(fy.getMsg('请选择'));
		$(e).find("#carFourId").val("");
		var msg = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>"+fy.getMsg('请选择')+"</a></li>";
		$(e).find('ul[name=fourAttr]').append(msg);
	}else{
		$.get(ctxs + "/product/productCar/selectProductCar.htm", {parentId:carThreeId}, function(data, status) {
			$("<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>"+fy.getMsg('请选择')+"</a></li>").prependTo($(e).find('ul[name=fourAttr]'));
			for (var i = 0; i < data.length; i++) {
				var productCar=data[i];
				var msg = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="+ productCar.carId +">"+ productCar.name+"</a></li>";
				$(e).find('ul[name=fourAttr]').append(msg);
			}
		});
	}
}