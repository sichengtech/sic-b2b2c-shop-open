$(function(){
	/**
	 * 表单验证
	 */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64,},
				"introduce":{required: true,maxlength:255,},
				"burns":{required: true,maxlength:64,},
				"bossName":{required: true,maxlength:64,},
				"bossMobile":{regex:/^1\d{10}$/,required: true,maxlength:64,},
				"hotline":{required: true,maxlength:64,},
				"shopkeeperName":{required: true,maxlength:64,},
				"shopkeeperMobile":{regex:/^1\d{10}$/,required: true,maxlength:64,},
				"contactsName":{required: true,maxlength:64,},
				"detailedAddress":{required: true,maxlength:255,},
				"mobile":{regex:/^1\d{10}$/,required: true,maxlength:64,},
			},
			messages: {
				"name":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"name":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "255" + fy.getMsg('字符'),},
				"burns":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"bossName":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"bossMobile":{regex: fy.getMsg('请输入正确手机号'),required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"hotline":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"shopkeeperName":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"shopkeeperMobile":{regex:fy.getMsg('请输入正确手机号'),required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"contactsName":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"detailedAddress":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "255" + fy.getMsg('字符'),},
				"mobile":{regex: fy.getMsg('请输入正确手机号'),required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
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
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				//门店类型
				var type =$(".type").val();
				if(type==null || type==''){
					layer.msg(fy.getMsg('请选择门店类型'));
					return false;
				}
				//门店所在地
				var areas = $(".areas").val();
				if(areas==null || areas==''){
					var provinceId =$("#provinceId").val();
					var cityId = $("#cityId").val();
					var districtId = $("#districtId").val();
					var detailedAddress = $("#detailedAddress").val();
					if(provinceId==null || provinceId==''){
						layer.msg(fy.getMsg('请选择门店所在地省'));
						return false;
					}
					if(cityId==null || cityId==''){
						layer.msg(fy.getMsg('请选择门店所在地市'));
						return false;
					}
					if(districtId==null || districtId==''){
						layer.msg(fy.getMsg('请选择门店所在地区/县'));
						return false;
					}
					if(detailedAddress==null || detailedAddress==''){
						layer.msg(fy.getMsg('请选择门店详细所在地'));
						return false;
					}
				}
				//把地区名字赋值到隐藏域中
				$("input[name='provinceName']").val($("#provinceName").text());
				$("input[name='cityName']").val($("#cityName").text());
				$("input[name='districtName']").val($("#districtName").text());
				//门店人数
				var peopleCount =$(".peopleCount").val();
				if(peopleCount==null || peopleCount==''){
					layer.msg(fy.getMsg('请选择门店人数'));
					return false;
				}
				//所在部门
				var department =$(".department").val();
				if(department==null || department==''){
					layer.msg(fy.getMsg('请选择所在部门'));
					return false;
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
});