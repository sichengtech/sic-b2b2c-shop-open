$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"addressName":{maxlength:64,},
				"name":{required: true,maxlength:64,},
				"provinceId":{required: true,maxlength:19,},
				"cityId":{required: true,maxlength:19,},
				"districtId":{required: true,maxlength:19,},
				"detailedAddress":{required: true,maxlength:255,},
				"mobile":{required: true,maxlength:64,regex:/^((0\d{2,3}-\d{7,8})|(1[0-9]\d{9}))$/},
				"zipCode":{maxlength:64,regex:/^[1-9]\d{5}(?!\d)/},
			},
			messages: {
				"addressName":{maxlength:fy.getMsg('收货地址名称最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"name":{required:fy.getMsg('请输入收货人'),maxlength:fy.getMsg('收货人最大长度不能超过') + '64' + fy.getMsg('字符'),},
				"provinceId":{required:fy.getMsg('请选择所在省'),maxlength:fy.getMsg('所在省最大长度不能超过') + '19' + fy.getMsg('字符'),},
				"cityId":{required: fy.getMsg('请选择所在市'),maxlength:fy.getMsg('所在市最大长度不能超过') + '19' + fy.getMsg('字符'),},
				"districtId":{required: fy.getMsg('请选择所在县'),maxlength:fy.getMsg('所在县最大长度不能超过') + '19' + fy.getMsg('字符'),},
				"detailedAddress":{required:fy.getMsg('请输入详情地址'),maxlength:fy.getMsg('最大长度不能超过') + '255' + fy.getMsg('字符'),},
				"mobile":{required:fy.getMsg('请输入电话'),maxlength:fy.getMsg('电话最大长度不能超过')+ '64' + fy.getMsg('字符'),regex:fy.getMsg('请输入正确格式的手机号或固定电话')},
				"zipCode":{maxlength:fy.getMsg('最大长度不能超过')+ '64' + fy.getMsg('字符'),regex:fy.getMsg('请输入6位数字的邮编')},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form){
				//所选地址
				var provinceId=$("#provinceId").val();
				var cityId=$("#cityId").val();
				var districtId=$("#districtId").val();
				if(provinceId == "" || cityId == "" || districtId == ""){
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg("请选择完整的地址"),2000);
					return false;
				}
				var provinceName=$("#provinceName").text();
				var cityName=$("#cityName").text();
				var districtName=$("#districtName").text();
				$("input[name='provinceName']").val(provinceName);
				$("input[name='cityName']").val(cityName);
				$("input[name='districtName']").val(districtName);
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	//编辑地区，点击编辑显示下拉选
	$("#areaEdit").click(function(){
		$(this).css("display","none");
		$("#area2").css("display","");
		//把地区信息之空
		$("#provinceId").val("");
		$("#cityId").val("");
		$("#districtId").val("");
		$("#provinceName").text(fy.getMsg('请选择'));
		$("#cityName").text(fy.getMsg('请选择'));
		$("#districtName").text(fy.getMsg('请选择'));
	});
});