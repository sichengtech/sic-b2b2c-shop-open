$(function () {
    
	/**
	 * 初始化MyUpload控件
	 */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var upload = new MyUpload({
        // 获取token的路径
        tokenPath: ctxa+"/sys/sysToken/getToken.do",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel',
        buttonStyle: 1,
        accept: {
        	type: 'custom',
            extensions: 'wgt,apk'
        },
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1,
        uploadSuccess:function(a,b){
        	$("#appPath").html(b.path);
        }
    });
    
	if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "version": {maxlength: 32, required: true},
                "type": {maxlength: 1,},
                "isNewVersion": {maxlength: 1,},
                "downloadPath": {maxlength: 255,},
                "explain": {maxlength: 255,},
            },
            messages: {
                "version": {maxlength: fy.getMsg('最大长度不能超过') + 32 + fy.getMsg('字符'),required: fy.getMsg('必填项'),},
                "type": {maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "isNewVersion": {maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "downloadPath": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'), required: "请先上传安装包"},
                "explain": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
            },
            errorPlacement: function (error, element) {
                //错误提示信息的显示位置
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
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
					$("input[name='downloadPath']").val(upload.datas[0].path);
				}
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});