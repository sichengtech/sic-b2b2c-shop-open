$(function(){
	/**
	 * 表单验证
	 */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64,},
				"introduce":{required: true,maxlength:255,},
				"contacts":{required: true,maxlength:64,},
				"contactsTelephone":{regex:/^1\d{10}$/,required: true,maxlength:64,},
			},
			messages: {
				"name":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"introduce":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "255" + fy.getMsg('字符'),},
				"contacts":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
				"contactsTelephone":{regex:fy.getMsg('请输入正确手机号'),required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') + "64" + fy.getMsg('字符'),},
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
				//企业类型
				var type =$(".type").val();
				if(type==null || type==''){
					layer.msg(fy.getMsg('请选择企业类型'));
					return false;
				}
				//企业属性
				var industry =$(".industry").val();
				if(industry==null || industry==''){
					layer.msg(fy.getMsg('请选择企业属性'));
					return false;
				}
				//营业执照
				var img_businesslicense = $("#img_businesslicense_0").find(".imgPath").val();
				if(img_businesslicense==null || img_businesslicense==''){
					layer.msg(fy.getMsg('请上传营业执照电子版'));
					return false;
				}
				//企业所在地
				var areas = $(".areas").val();
				if(areas==null || areas==''){
					var provinceId =$("#provinceId").val();
					var cityId = $("#cityId").val();
					var districtId = $("#districtId").val();
					var detailedAddress = $("#detailedAddress").val();
					if(provinceId==null || provinceId==''){
						layer.msg(fy.getMsg('请选择企业所在地省'));
						return false;
					}
					if(cityId==null || cityId==''){
						layer.msg(fy.getMsg('请选择企业所在地市'));
						return false;
					}
					if(districtId==null || districtId==''){
						layer.msg(fy.getMsg('请选择企业所在地区/县'));
						return false;
					}
					if(detailedAddress==null || detailedAddress==''){
						layer.msg(fy.getMsg('请选择企业详细所在地'));
						return false;
					}
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	/**
	 * 升级采购商所在地区的点击变换
	 */
	$("#change").click(function(){
		$("#area1").css("display","none");
		$("#area2").css("display","inline-block");
	});
	
});