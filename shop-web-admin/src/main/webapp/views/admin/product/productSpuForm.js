$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"pId":{required: true,maxlength:19,},
				"name":{required: true,maxlength:128,},
				"categoryId":{required: true,maxlength:19,},
				"storeCategoryId":{required: true,maxlength:19,},
				"storeId":{required: true,maxlength:19,},
				"sellerId":{required: true,maxlength:19,},
				"status":{required: true,maxlength:2,},
				"image":{required: true,maxlength:128,},
				"brandId":{required: true,maxlength:19,},
				"nameSub":{maxlength:255,},
				"unit":{maxlength:12,},
				"type":{maxlength:1,},
				"isGift":{required: true,maxlength:1,},
				"isRecommend":{required: true,maxlength:1,},
				"recommendSort":{required: true,maxlength:10,regex:/^(0|[1-9][0-9]*)$/,},
				"marketPrice":{},
				"point":{maxlength:64,},
				"weight":{maxlength:12,},
				"volume":{maxlength:12,},
				"invoice":{maxlength:1,},
				"action":{maxlength:1,},
				"expressType":{maxlength:1,},
				"expressPrice":{},
				"ltId":{maxlength:19,},
			},
			messages: {
				"pId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"name":{required: "必填项",maxlength:"最大长度不能超过128字符",},
				"categoryId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"storeCategoryId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"storeId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"sellerId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"status":{required: "必填项",maxlength:"最大长度不能超过2字符",},
				"image":{required: "必填项",maxlength:"最大长度不能超过128字符",},
				"brandId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"nameSub":{maxlength:"最大长度不能超过255字符",},
				"unit":{maxlength:"最大长度不能超过12字符",},
				"type":{maxlength:"最大长度不能超过1字符",},
				"isGift":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"isRecommend":{required: "必填项",maxlength:"最大长度不能超过1字符",},
				"recommendSort":{required: "必填项",maxlength:"最大长度不能超过10字符",regex:"排序要输入数字",},
				"marketPrice":{},
				"point":{maxlength:"最大长度不能超过64字符",},
				"weight":{maxlength:"最大长度不能超过12字符",},
				"volume":{maxlength:"最大长度不能超过12字符",},
				"invoice":{maxlength:"最大长度不能超过1字符",},
				"action":{maxlength:"最大长度不能超过1字符",},
				"expressType":{maxlength:"最大长度不能超过1字符",},
				"expressPrice":{},
				"ltId":{maxlength:"最大长度不能超过19字符",},
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
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});