$(function(){
	/**
	 * 修改jquery validate的验证规则，
	 * 修改为可以验证相同name的多个input框
	 * */
	if($.validator){
		$.validator.prototype.elements = function () {
			var validator = this,
			rulesCache = {};
			return $(this.currentForm)
			.find("input, select, textarea")
			.not(":submit, :reset, :image, [disabled]")
			.not(this.settings.ignore)
			.filter(function () {
				if (!this.name && validator.settings.debug && window.console) {
					console.error("%o has no name assigned", this);
				}
				rulesCache[this.name] = true;
				return true;
			});
		}
	}
	
	/**
	 * 提交表单
	 * */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64,},
				"model":{required: true,maxlength:64,},
				"brand":{maxlength:64,},
				"amount":{required: true,maxlength:9,regex:/^([1-9][0-9]*)$/},
				"priceRequirement":{maxlength:9,regex:/^([1-9]\d{0,8}|0)(\.\d{1,2})?$/},
				"unit":{required: true,maxlength:64,},
				"purchaseRemarks":{maxlength:255,},
			},
			messages: {
				"name":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"model":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"brand":{maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"amount":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过9字符'),regex:fy.getMsg('输入非0开头的数字'),},
				"priceRequirement":{maxlength:"最大长度不能超过9字符",regex:fy.getMsg('输入正整数或两位以内的正小数'),},
				"unit":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"purchaseRemarks":{maxlength:fy.getMsg('最大长度不能超过255字符'),},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				}else if("expiryTime"==element.attr("name")){
					error.appendTo(element.parent().parent());
				}else {
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
				var detailLen=$(".purchase-spilt-table tbody tr").length;//菜单明细数据长度
				if(0==detailLen){
					layer.msg(fy.getMsg('请添加采购明细'));
					return false;
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	/**
	 * 添加属性
	 * */
	$(".add-attr").click(function(){
		//采购明细的字段id
		var nameId="name_"+$(".purchase-spilt-table tbody tr").length;
		var modelId="model_"+$(".purchase-spilt-table tbody tr").length;
		var brandId="brand_"+$(".purchase-spilt-table tbody tr").length;
		var amountId="amount_"+$(".purchase-spilt-table tbody tr").length;
		var priceRequirementId="priceRequirement_"+$(".purchase-spilt-table tbody tr").length;
		
		
		var unitId="unit_"+$(".purchase-spilt-table tbody tr").length;
		
		var purchaseRemarksId="purchaseRemarks_"+$(".purchase-spilt-table tbody tr").length;
		
		//采购明细模板
		var purchase_spilt_tpl=$("#purchase_spilt_tpl").html();
		var pay_way_attr_data={"nameId":nameId,"modelId":modelId,"brandId":brandId,"amountId":amountId,
				"priceRequirementId":priceRequirementId,"unitId":unitId,"purchaseRemarksId":purchaseRemarksId};
		var commentPanelHtml=render(purchase_spilt_tpl,pay_way_attr_data);//渲染模板
		$(".purchase-spilt-table tbody").append(commentPanelHtml);
	});
	
	/**
	 * 删除参数
	 * */
	$(".purchase-spilt-table").delegate(".delete-attr","click",function(){
		$(this).parent().parent().remove();
	});
	
	/**
	 * 渲染模板
	 * */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
});