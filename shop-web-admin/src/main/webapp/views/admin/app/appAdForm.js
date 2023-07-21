$(function () {
	
	/**
	 * 
	 */
	/**
	 * 初始化MyUpload控件
	 */
	var img_path=$("input[name='backgroundImage']").val();//图片地址
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
        		$("input[name='backgroundImage']").val("");
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
                "backgroundColor": {maxlength: 32,},
                "backgroundImage": {maxlength: 64,},
                "isShow": {maxlength: 1,},
                "wordOne": {maxlength: 128,},
                "wordTwo": {maxlength: 128,},
                "wordThree": {maxlength: 128,},
                "buttonWord": {maxlength: 64,},
                "buttonColour": {maxlength: 32,},
            },
            messages: {
                "backgroundColor": {maxlength: fy.getMsg('最大长度不能超过') + 32 + fy.getMsg('字符'),},
                "backgroundImage": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "isShow": {maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "wordOne": {maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                "wordTwo": {maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                "wordThree": {maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                "buttonWord": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "buttonColour": {maxlength: fy.getMsg('最大长度不能超过') + 32 + fy.getMsg('字符'),},
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
					$("input[name='backgroundImage']").val(upload.datas[0].path);
				}
                
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});