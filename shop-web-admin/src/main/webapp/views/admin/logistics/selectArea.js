function selectCity(obj) {
	$('select[name=cityId]').empty();
	var value = $(obj).find("option:selected").val();
	var districtName = $("#districtId").val();
	if(value=="" || value==null){
		$('select[name=cityId]').empty();
		var msg = "<option selected value=\"" + "" + "\">" + "请选择"+ "</option>";
		$('select[name=cityId]').append(msg);
		$('select[name=districtId]').empty();
		if(districtName!="" && districtName!=null){
			$('select[name=districtId]').empty();
			var msg = "<option selected value=\"" + "" + "\">" + "请选择"+ "</option>";
			$('select[name=districtId]').append(msg);
		}
	}else{
		$.get(ctxa + "/sys/area/selectArea.do", {parentId:value}, function(data, status) {
			$("<option selected value=''>请选择</option>").prependTo($('select[name=cityId]'));
			for (var i = 0; i < data.length; i++) {
				var area=data[i];
				var msg = "<option value=\"" + area.id + "\">" + area.name+ "</option>";
				$('select[name=cityId]').append(msg);
			}
		});
	}
}

function selectDistrict(obj) {
	$('select[name=districtId]').empty();
	var value = $(obj).find("option:selected").val();
	if(value=="" || value==null){
		$('select[name=districtId]').empty();
		var msg = "<option selected value=\"" + "" + "\">" + "请选择"+ "</option>";
		$('select[name=districtId]').append(msg);
	}else{
		$.get(ctxa + "/sys/area/selectArea.do", {parentId:value}, function(data, status) {
			$("<option selected value=''>请选择</option>").prependTo($('select[name=districtId]'));
			for (var i = 0; i < data.length; i++) {
				var area=data[i];
				var msg = "<option value=\"" + area.id + "\">" + area.name+ "</option>";
				$('select[name=districtId]').append(msg);
			}
		});
	}
}