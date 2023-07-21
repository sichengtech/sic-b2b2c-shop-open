$(function () {
	
    /**
     * 初始化MyUpload控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var upload = new MyUpload({
        // 获取token的路径
        tokenPath: ctxs+"/sys/sysToken/getToken.htm",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel',
        buttonStyle: 1,
        accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1
    });
	
	/**
	 * 隐藏一句话发布 BOM发布
	 **/
	$('.piliang-fb-1').hide();
	$('.piliang-fb-2').hide();
	
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
	 * 点击添加按钮添加一行
	 * */
	$("#addrow").click(function () {
		addRow();
	});

	/**
	 * 移除一行
	 * */
	$("#listTable").delegate(".delButton", 'click', function () {
		$(this).parent().parent().remove();
	});

	/**
	 * 初始添加1行
	 * */
	addRow();

	/**
	 * 添加一行
	 * */
	function addRow() {
		var trTpl = $("#purchase_batch_tr_tpl").html();//模板
		var length=$("#listTable tbody tr").length;
		var nameId="name_"+length;
		var modelId="model_"+length;
		var amountId="amount_"+length;
		var unitId="unit_"+length;
		var trData={"nameId":nameId,"modelId":modelId,"amountId":amountId,"unitId":unitId};//模板的数据
		var trHtml=render(trTpl,trData);//渲染模板
		$("#listTable tbody").append(trHtml);
	}

	/**
	 * 导航条点击事件
	 * */
	$('.fabu li').click(function () {
		var type=$(this).attr("type");
		if ('0' == type) {
			$('.piliang-fb').show();
			$('.piliang-fb-1').hide();
			$('.piliang-fb-2').hide();
			$('.fabu li').removeClass('cur');
			$(this).addClass('cur');
		} else if ('1'  ==type) {
			$('.piliang-fb-1').show();
			$('.piliang-fb').hide();
			$('.piliang-fb-2').hide();
			$('.fabu li').removeClass('cur');
			$(this).addClass('cur');
		} else if ('2' ==type) {
			$('.piliang-fb-2').show();
			$('.piliang-fb-1').hide();
			$('.piliang-fb').hide();
			$('.fabu li').removeClass('cur');
			$(this).addClass('cur');
		}
	});

	
	/**
	 * 根据发布类型来显示发布页面
	 * */
	if(releaseType=="" || releaseType=="0"){
		$('.piliang-fb-0').removeClass("hide");
	}else{
		$('.piliang-fb-'+releaseType).removeClass("hide");
		$("ul.fabu li.cur").removeClass("cur");
		$("ul.fabu li[type="+releaseType+"]").addClass("cur");
	}
	
	/**
	 * 渲染模板
	 */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
	
	/**
	 * 批量发布-提交表单
	 * */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"title":{required: true,maxlength:64,},
				"cycle":{required: true,maxlength:64,},
				"purchaseExplain":{maxlength:64,},
				"expiryTime":{required: true,maxlength:64,},
				"name":{required: true,maxlength:64,},
				"model":{required: true,maxlength:64,},
				"amount":{required: true,maxlength:9,regex:/^([1-9][0-9]*)$/},
				"priceRequirement":{maxlength:12,regex:/^([1-9]\d{0,8}|0)(\.\d{1,2})?$/},
				"unit":{required: true,maxlength:5,},
				"purchaseRemark":{maxlength:255,},
			},
			messages: {
				"title":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"cycle":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"purchaseExplain":{maxlength:"最大长度不能超过64字符",},
				"expiryTime":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"name":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"model":{required: "必填项",maxlength:"最大长度不能超过64字符",},
				"amount":{required: "必填项",maxlength:"最大长度不能超过9字符",regex:"输入正整数",},
				"priceRequirement":{maxlength:"最大长度不能超过12字符",regex:"输入正整数或两位以内的正小数",},
				"unit":{required: "必填项",maxlength:"最大长度不能超过5字符",},
				"purchaseRemark":{maxlength:"最大长度不能超过255字符",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				}else if("expiryTime" == element.attr("name") || "title" == element.attr("name") || "cycle" == element.attr("name")) {
					error.insertAfter(element);
				}else{
					error.insertAfter(element.siblings("br"));
				}
			},
			submitHandler: function(form){
				//对checkbox处理，1：选中；0：未选中
				$("input[type=checkbox]").each(function(){
					$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
							+($(this).attr("checked")?"1":"0")+"\"/>");
					$(this).attr("name", "_"+$(this).attr("name"));
				});
				var trLength=$("#listTable tbody tr.purchase_content").length;
				if(trLength==0){
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 请输入采购内容",2000);
					return false;
				}
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
	/**
	 * 一句话发布-提交表单
	 * */
	if(jsValidate){
		$("#releaseOneForm").validate({
			rules: {
				"title":{required: true,maxlength:64,},
				"cycle":{required: true,maxlength:64,},
				"purchaseExplain":{maxlength:64,},
				"expiryTime":{required: true,maxlength:64,},
				"content":{required: true,maxlength:2000,},
			},
			messages: {
				"title":{required: "采购标题不能为空",maxlength:"最大长度不能超过64字符",},
				"cycle":{required: "交货周期不能为空",maxlength:"最大长度不能超过64字符",},
				"purchaseExplain":{maxlength:"最大长度不能超过64字符",},
				"expiryTime":{required: "采购到期时间不能为空",maxlength:"最大长度不能超过64字符",},
				"content":{required: "采购内容不能为空",maxlength:"最大长度不能超过2000字符",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				}else{
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
	
	/**
	 * bom发布-提交表单
	 * */
	if(jsValidate){
		$("#releaseBomForm").validate({
			rules: {
				"title":{required: true,maxlength:64,},
				"cycle":{required: true,maxlength:64,},
				"purchaseExplain":{maxlength:64,},
				"expiryTime":{required: true,maxlength:64,},
			},
			messages: {
				"title":{required: "采购标题不能为空",maxlength:"最大长度不能超过64字符",},
				"cycle":{required: "交货周期不能为空",maxlength:"最大长度不能超过64字符",},
				"purchaseExplain":{maxlength:"最大长度不能超过64字符",},
				"expiryTime":{required: "采购到期时间不能为空",maxlength:"最大长度不能超过64字符",},
			},
			errorPlacement: function(error, element) {
				//错误提示信息的显示位置
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				}else{
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
				//bom表验证
				if(upload.datas.length==0){
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 请上传bom表",2000);	
					return false;
				}else{
					$("input[name='bomPath']").val(upload.datas[0].path);//bom地址
				}
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
	
});