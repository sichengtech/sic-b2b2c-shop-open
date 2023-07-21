$(function () {
	
    /**
     * 初始化MyUpload(网站LOGO)控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var img_path1=$("input[name='siteLogo']").val();//图片地址
    var upload1 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxa+"/sys/sysToken/getToken.do",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel1',
        buttonStyle: 1,
        //accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path1)){
        		$("input[name='siteLogo']").val("");
        	}
        }
    });
    
    // /**
    //  * 初始化MyUpload(商家中心LOGO)控件
    //  */
    // var img_path2=$("input[name='sellerLogo']").val();//图片地址
    // var upload2 = new MyUpload({
    // 	// 获取token的路径
    // 	tokenPath: ctxa+"/sys/sysToken/getToken.do",
    // 	// 文件上传到的服务器
    // 	server: ctxu+'/upload/webUploadServer.htm',
    // 	// 容器Id
    // 	container: '#vessel2',
    // 	buttonStyle: 1,
    // 	//accept: 'file',
    // 	fileSingleSizeLimit: 1024 * 1024 * 5,
    // 	fileNumLimit: 1,
    //     uploadDelete:function(a){
    //     	if(a==(ctxfs+img_path2)){
    //     		$("input[name='sellerLogo']").val("");
    //     	}
    //     }
    // });
    
    /**
     * 回显页面图片(网站LOGO)
     */
    if(img_path1 != "" && img_path1 != null && typeof img_path1 != "undefined"){
    	upload1.init([ctxfs+img_path1]);
    }
    
    // /**
    //  * 回显页面图片(商家中心LOGO)
    //  */
    // if(img_path2 != "" && img_path2 != null && typeof img_path2 != "undefined"){
    // 	upload2.init([ctxfs+img_path2]);
    // }
	
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "name": {required: true, maxlength: 128,},
                "icp": {maxlength: 128,},
                "code": {maxlength: 1024,},
                "siteLogo": {required: true, maxlength: 128,},
                "sellerLogo": {required: true, maxlength: 128,},
                "email": {maxlength: 128,},
                "telephone": {maxlength: 128,},
            },
            messages: {
                "name": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                "icp": {maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                "code": {maxlength: fy.getMsg('最大长度不能超过') + 1024 + fy.getMsg('字符'),},
                "siteLogo": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                //"sellerLogo": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                "email": {maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
                "telephone": {maxlength: fy.getMsg('最大长度不能超过') + 128 + fy.getMsg('字符'),},
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
				if(upload1.datas.length!=0){
					$("input[name='siteLogo']").val(upload1.datas[0].path);
				}
				// if(upload2.datas.length!=0){
				// 	$("input[name='sellerLogo']").val(upload2.datas[0].path);
				// }
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});