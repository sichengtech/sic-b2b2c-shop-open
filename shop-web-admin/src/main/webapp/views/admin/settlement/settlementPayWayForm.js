$(function(){
	
	/**
	 * 初始化MyUpload控件
	 */
	window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
	var img_path=$("input[name='payWayLogo']").val();//图片地址
	var upload = new MyUpload({
		// 获取token的路径
		tokenPath: ctxa+"/sys/sysToken/getToken.do",
		// 文件上传到的服务器
		server: ctxu+'/upload/webUploadServer.htm',
		// 容器Id
		container: '#vessel',
		buttonStyle: 1,
		//accept: 'file',
		fileSingleSizeLimit: 1024 * 1024 * 5,
		fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path)){
        		$("input[name='payWayLogo']").val("");
        	}
        }
	});

	/**
	 * 回显页面图片
	 */
	if(img_path != "" && img_path != null && typeof img_path != "undefined"){
		upload.init([ctxfs+img_path]);
	}
	
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
		var oldNum = $("#oldNum").val();
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64,},
				"payWayNum":{required: true,maxlength:64,remote: ctxa+"/settlement/settlementPayWay/exitNum.do?oldNum=" + encodeURIComponent(oldNum),},
				"poundage":{required: true,maxlength:11,},
				"useTerminal":{required: true,maxlength:1,},
				"payWayLogo":{maxlength:128,},
				"status":{required: true,maxlength:1,},
				"payWayKey":{required: true,maxlength:64,},
				"payWayValue":{required: true,maxlength:4000,},
				"payWayDescribe":{maxlength:255,},
			},
			messages: {
				"name":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"payWayNum":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),remote: fy.getMsg("编号已存在"),},
				"poundage":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过11字符"),},
				"useTerminal":{required: fy.getMsg("必选项"),maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"payWayLogo":{maxlength:fy.getMsg("最大长度不能超过128字符"),},
				"status":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过1字符"),},
				"payWayKey":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过64字符"),},
				"payWayValue":{required: fy.getMsg("必填项"),maxlength:fy.getMsg("最大长度不能超过4000字符"),},
				"payWayDescribe":{maxlength:fy.getMsg("最大长度不能超过255字符"),},
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
				//获取上传图片路径
				if(upload.datas.length!=0){
					$("input[name='payWayLogo']").val(upload.datas[0].path);
				}
				var imgValue=$("input[name='payWayLogo']").val();
				if(imgValue==null || imgValue==""){
					layer.msg(fy.getMsg('请上传支付方式')+'Logo');
					return false;
				}
				//验证支付方式属性键是否重复
				var payWayKeys=new Array();
				$(".payWayKey").each(function(){
					payWayKeys.push($(this).val());
				});
				var nary=payWayKeys.sort();
				for(var i=0;i<payWayKeys.length;i++){
					if (nary[i]==nary[i+1]){
						layer.msg(fy.getMsg('支付方式属性键重复'));
						return false;
					}
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
		var pay_way_attr_tpl=$("#pay_way_attr_tpl").html();
		var payWayKeyId="payWayKey_"+$(".pay-way-attr-table tbody tr").length;
		var payWayValueId="payWayValue_"+$(".pay-way-attr-table tbody tr").length;
		var payWayDescribeId="payWayDescribe_"+$(".pay-way-attr-table tbody tr").length;
		var pay_way_attr_data={"payWayKeyId":payWayKeyId,"payWayValueId":payWayValueId,"payWayDescribeId":payWayDescribeId,
				"payWayKey":"","payWayValue":"","payWayDescribe":""};
		var commentPanelHtml=render(pay_way_attr_tpl,pay_way_attr_data);//渲染模板
		$(".pay-way-attr-table tbody").append(commentPanelHtml);
	});
	
	/**
	 * 删除参数
	 * */
	$(".pay-way-attr-table").delegate(".delete-attr","click",function(){
		$(this).parent().parent().remove();
	});
	
	/**
	 * 渲染模板
	 * */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
	
	/**
	 * 编辑接口时，回显接口的参数
	 * */
	if(typeof(payWayAttrList)!="undefined" && payWayAttrList!=null && payWayAttrList!=""){
		var commentPanelHtml="";
		var pay_way_attr_tpl=$("#pay_way_attr_tpl").html();
		var aa=payWayAttrList.length;
		for(var i=0;i<payWayAttrList.length;i++){
			var payWayKeyId="payWayKey_"+$(".pay-way-attr-table tbody tr").length;
			var payWayValueId="payWayValue_"+$(".pay-way-attr-table tbody tr").length;
			var payWayDescribeId="payWayDescribe_"+$(".pay-way-attr-table tbody tr").length;
			var pay_way_attr_data={"payWayKeyId":payWayKeyId,"payWayValueId":payWayValueId,"payWayDescribeId":payWayDescribeId,
					"payWayKey":payWayAttrList[i].payWayKey,"payWayValue":payWayAttrList[i].payWayValue,"payWayDescribe":payWayAttrList[i].payWayDescribe};
			commentPanelHtml+=render(pay_way_attr_tpl,pay_way_attr_data);//渲染模板
		}
		$(".pay-way-attr-table tbody").append(commentPanelHtml);
	}
});