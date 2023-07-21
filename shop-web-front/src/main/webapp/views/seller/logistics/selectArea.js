function selectCity(obj) {
	var e = $(obj).parent().parent().parent().parent();
	$(e).find('ul[name=cityAttr]').empty();
	$(e).find("#cityName").text(fy.getMsg('市'));
	$(e).find("#cityId").val("");
	$(e).find('ul[name=districtAttr]').empty();
	$(e).find("#districtName").text(fy.getMsg('县'));
	$(e).find("#districtId").val("");
	var provinceId = $(e).find("#provinceId").val();
	var districtId = $(e).find("#districtId").val();
	$.get(ctxs + "/sys/area/selectArea.htm", {parentId:provinceId}, function(data, status) {
		$("<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>"+fy.getMsg('市')+"</a></li>").prependTo($(e).find('ul[name=cityAttr]'));
		$("<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>"+fy.getMsg('县')+"</a></li>").prependTo($(e).find('ul[name=districtAttr]'));
		for (var i = 0; i < data.length; i++) {
			var area=data[i];
			var msg = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="+ area.id +">"+ area.name+"</a></li>";
			$(e).find('ul[name=cityAttr]').append(msg);
		}
	});
}

function selectDistrict(obj) {
	var e = $(obj).parent().parent().parent().parent();
	$(e).find('ul[name=districtAttr]').empty();
	$(e).find("#districtName").text(fy.getMsg('县'));
	var cityId = $(e).find("#cityId").val();
	if(cityId=="" || cityId==null){
		$(e).find('ul[name=districtAttr]').empty();
		$(e).find("#districtName").text(fy.getMsg('县'));
		$(e).find("#districtId").val("");
		var msg = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>"+fy.getMsg('县')+"</a></li>";
		$(e).find('ul[name=districtAttr]').append(msg);
	}else{
		$.get(ctxs + "/sys/area/selectArea.htm", {parentId:cityId}, function(data, status) {
			$("<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>"+fy.getMsg('县')+"</a></li>").prependTo($(e).find('ul[name=districtAttr]'));
			for (var i = 0; i < data.length; i++) {
				var area=data[i];
				var msg = "<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="+ area.id +">"+ area.name+"</a></li>";
				$(e).find('ul[name=districtAttr]').append(msg);
			}
		});
	}
}