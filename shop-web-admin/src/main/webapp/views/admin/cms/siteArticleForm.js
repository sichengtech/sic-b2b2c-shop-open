$(document).ready(function () {
    
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
	
	if ($("#link").val()) {
        $('#linkBody').show();
        $('#url').attr("checked", true);
    }
    //如果js验证开关是开的就进行js验证
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "title": {required: true, maxlength: 100,},
            },
            messages: {
                "title": {required: fy.getMsg('标题不能为空'), maxlength: fy.getMsg('标题不能超过100字符'),},
            },
            submitHandler: function (form) {
                if ($("#categoryId").val() == "") {
                    fdp.msg(fy.getMsg('请选择归属栏目'));
                    return false;
                }
                //验证文章内容
                var content = UE.getEditor('container').getContent();
                if (content == "" || content == null) {
                    fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> " + fy.getMsg('文章内容不能为空'), 2000);
                    return false;
                }
                
                //获取上传图片路径
                if(upload.datas.length!=0){
                	$("input[name='image']").val(upload.datas[0].path);
                }
                
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            },
        });
    }
});