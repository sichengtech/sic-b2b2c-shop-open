$(function () {
    
	/**
	 * 初始化MyUpload控件
	 */
	var img_path=$("input[name='image']").val();//图片地址
	window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
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
        		$("input[name='image']").val("");
        	}
        }
	});
	
	/**
	 * 回显页面图片
	 */
	if(img_path != "" && img_path != null && typeof img_path != "undefined"){
		upload.init([ctxfs+img_path]);
	}
	
	//如果js验证开关是开的就进行js验证
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "name": {required: true, maxlength: 100,},
                "sort": {required: true, maxlength: 10, regex: /^(0|[1-9][0-9]*)$/,},
            },
            messages: {
                "name": {
                    required: fy.getMsg('栏目名称不能为空'), maxlength: fy.getMsg('标题不能超过100字符'),
                },
                "sort": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('排序要输入数字'),
                },
            },
            submitHandler: function (form) {
                //对checkbox处理，1：选中；0：未选中
                $("input[type=checkbox]").each(function () {
                    $(this).after("<input type=\"hidden\" name=\"" + $(this).attr("name") + "\" value=\""
                        + ($(this).attr("checked") ? "1" : "0") + "\"/>");
                    $(this).attr("name", "_" + $(this).attr("name"));
                });
                
                //获取上传图片路径
                if(upload.datas.length!=0){
                	$("input[name='image']").val(upload.datas[0].path);
                }
                
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});