$(function () {
    
	/**
	 * 初始化MyUpload控件
	 */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var img_path=$("input[name='path']").val();//图片地址
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
        		$("input[name='path']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片
     */
    if(img_path != "" && img_path != null && typeof img_path != "undefined"){
    	upload.init([ctxfs+img_path]);
    }
	
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "path": {required: true, maxlength: 64,},
                "url": {required: true, maxlength: 255,},
                "title": {required: true, maxlength: 64,},
                "sort": {required: true, maxlength: 10, regex: /^(0|[1-9][0-9]*)$/,},
                "status": {required: true, maxlength: 1,},
                "type": {required: true, maxlength: 20,},
                //"action":{required: true,maxlength:10,},
                "target": {required: true, maxlength: 64,},
                "txt": {maxlength: 255,},
            },
            messages: {
                "path": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "url": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "title": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "sort": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('排序要输入数字'),
                },
                "status": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "type": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 20 + fy.getMsg('字符'),},
                //"action":{required: fy.getMsg('必填项'),maxlength:fy.getMsg('最大长度不能超过') +10+ fy.getMsg('字符'),},
                "target": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "txt": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
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
					$("input[name='path']").val(upload.datas[0].path);
				}
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});