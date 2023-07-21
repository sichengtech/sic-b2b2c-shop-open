$(function(){
    /**
     * 安全图片的密钥
     */
    var accessKey=$("input[name='accessKey']").val();
    
    /**
     * 是否允许上传
     */
    var againUpload = true;
    if(status=='30'){
    	againUpload = false;
    }
	
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
		formData: {
			isSafe: true
		},
    	isSafe: true,
    	againUpload:againUpload,
    	accessKey: accessKey,
        accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1
    });
    
    /**
     * 回显页面图片
     */
    var img_path=$("input[name='filePath']").val();
    if(img_path != "" && img_path != null && typeof img_path != "undefined"){
    	upload.init([ctxfs+img_path]);
    }
	
	/**
	 * 表单验证
	 */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
			},
			messages: {
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
				//凭证文件
				if(upload.datas.length!=0){
					$("input[name='filePath']").val(upload.datas[0].path);
				}
				//交易类型
				var type =$(".type").val();
				if(type==null || type==''){
					layer.msg(fy.getMsg('请选择交易类型'));
					return false;
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});