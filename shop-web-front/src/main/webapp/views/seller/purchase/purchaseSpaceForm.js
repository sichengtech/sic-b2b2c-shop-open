$(function(){
    /**
     * 初始化MyUpload(采购空间Logo)控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var upload1 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxs+"/sys/sysToken/getToken.htm",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel1',
        buttonStyle: 1,
        //accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1
    });
    
    /**
     * 回显页面图片(采购空间Logo)
     */
    var img_path1=$("input[name='logo']").val();
    if(img_path1 != "" && img_path1 != null && typeof img_path1 != "undefined"){
    	upload1.init([ctxfs+img_path1]);
    }
    
    /**
     * 初始化MyUpload(采购空间Banner)控件
     */
    var upload2 = new MyUpload({
    	// 获取token的路径
    	tokenPath: ctxs+"/sys/sysToken/getToken.htm",
    	// 文件上传到的服务器
    	server: ctxu+'/upload/webUploadServer.htm',
    	// 容器Id
    	container: '#vessel2',
    	buttonStyle: 1,
    	//accept: 'file',
    	fileSingleSizeLimit: 1024 * 1024 * 5,
    	fileNumLimit: 1
    });
    
    /**
     * 回显页面图片(采购空间Banner)
     */
    var img_path2=$("input[name='banner']").val();
    if(img_path2 != "" && img_path2 != null && typeof img_path2 != "undefined"){
    	upload2.init([ctxfs+img_path2]);
    }
	
	/**
	 * 表单验证
	 */
	if(jsValidate){
		$("#inputForm").validate({
			rules: {
				"name":{required: true,maxlength:64,},
				"synopsis":{required: true,maxlength:255,},
			},
			messages: {
				"name":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过64字符'),},
				"synopsis":{required: fy.getMsg('必填項'),maxlength:fy.getMsg('最大长度不能超过255字符'),},
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
				//采购空间logo
				if(upload1.datas.length==0){
					layer.msg(fy.getMsg('请上传采购空间logo'));
					return false;
				}else{
					$("input[name='logo']").val(upload1.datas[0].path);//采购空间LOGO
				}
				//采购空间banner
				if(upload2.datas.length==0){
					layer.msg(fy.getMsg('请上传采购空间banner'));
					return false;
				}else{
					$("input[name='banner']").val(upload2.datas[0].path);//采购空间banner
				}
				layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16,shade: 0.30,time: 0});
				form.submit();
			}
		});
	}
});