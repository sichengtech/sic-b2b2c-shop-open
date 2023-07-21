$(function(){
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"pId":{required: true,maxlength:19,},
				"allBrowse":{maxlength:19,},
				"allBrowseDate":{},
				"weekBrowse":{maxlength:19,},
				"weekBrowseDate":{},
				"monthBrowse":{maxlength:19,},
				"monthBrowseDate":{},
				"month3Browse":{maxlength:19,},
				"month3BrowseDate":{},
				"allSales":{maxlength:19,},
				"allSalesDate":{},
				"weekSales":{maxlength:19,},
				"weekSalesDate":{},
				"monthSales":{maxlength:19,},
				"monthSalesDate":{},
				"month3Sales":{maxlength:19,},
				"month3SalesDate":{},
			},
			messages: {
				"pId":{required: "必填项",maxlength:"最大长度不能超过19字符",},
				"allBrowse":{maxlength:"最大长度不能超过19字符",},
				"allBrowseDate":{},
				"weekBrowse":{maxlength:"最大长度不能超过19字符",},
				"weekBrowseDate":{},
				"monthBrowse":{maxlength:"最大长度不能超过19字符",},
				"monthBrowseDate":{},
				"month3Browse":{maxlength:"最大长度不能超过19字符",},
				"month3BrowseDate":{},
				"allSales":{maxlength:"最大长度不能超过19字符",},
				"allSalesDate":{},
				"weekSales":{maxlength:"最大长度不能超过19字符",},
				"weekSalesDate":{},
				"monthSales":{maxlength:"最大长度不能超过19字符",},
				"monthSalesDate":{},
				"month3Sales":{maxlength:"最大长度不能超过19字符",},
				"month3SalesDate":{},
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