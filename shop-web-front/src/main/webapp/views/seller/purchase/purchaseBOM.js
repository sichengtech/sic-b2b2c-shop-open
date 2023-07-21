$(function(){
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
        accept: {type: 'custom',extensions: 'xls,xlsx'},
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1
    });
	
	/**
	 * 表单验证
	 */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"title":{required: true,maxlength:64,},
				"cycle":{required: true,maxlength:64,},
				"purchaseExplain":{maxlength:64,},
				"expiryTime":{required: true,maxlength:64,},
			},
			messages: {
				"title":{required: fy.getMsg('采购标题不能为空'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"cycle":{required: fy.getMsg('交货周期不能为空'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"purchaseExplain":{maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"expiryTime":{required: fy.getMsg('采购到期时间不能为空'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
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
				//bom表验证
				if(upload.datas.length==0){
					layer.msg(fy.getMsg('请上传BOM表'));
					return false;
				}else{
					$("input[name='bomPath']").val(upload.datas[0].path);//bom地址
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});