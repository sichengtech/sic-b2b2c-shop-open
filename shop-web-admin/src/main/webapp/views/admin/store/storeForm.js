$(function () {
    
    /**
     * 初始化MyUpload(店铺Logo)控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic+"/";
    var img_path1=$("input[name='logo']").val();//图片地址
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
        		$("input[name='logo']").val("");
        	}
        }
    });
    
    /**
     * 初始化MyUpload(店铺横幅)控件
     */
    var img_path2=$("input[name='banner']").val();//图片地址
    var upload2 = new MyUpload({
        // 获取token的路径
        tokenPath: ctxa+"/sys/sysToken/getToken.do",
        // 文件上传到的服务器
        server: ctxu+'/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel2',
        buttonStyle: 1,
        //accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1,
        uploadDelete:function(a){
        	if(a==(ctxfs+img_path2)){
        		$("input[name='banner']").val("");
        	}
        }
    });
    
    /**
     * 回显页面图片(店铺Logo)
     */
    if(img_path1 != "" && img_path1 != null && typeof img_path1 != "undefined"){
    	upload1.init([ctxfs+img_path1]);
    }
    
    /**
     * 回显页面图片(店铺横幅)
     */
    if(img_path2 != "" && img_path2 != null && typeof img_path2 != "undefined"){
    	upload2.init([ctxfs+img_path2]);
    }
	
	var oldStoreName = $("#oldStoreName").val();
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "name": {
                    remote: ctxa + "/store/store/validateStoreName.do?oldStoreName=" + encodeURIComponent(oldStoreName),
                    required: true,
                    maxlength: 20,
                },
                "seoTitle": {required: true, maxlength: 255,},
                "seoKeyword": {required: true, maxlength: 255,},
                "seoDescribe": {required: true, maxlength: 255,},
            },
            messages: {
                "name": {
                    remote: fy.getMsg('店铺名称已存在'),
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 18 + fy.getMsg('字符'),
                },
                "seoTitle": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "seoKeyword": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "seoDescribe": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
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
                //店铺Logo
				if(upload1.datas.length!=0){
					$("input[name='logo']").val(upload1.datas[0].path);
				}
				//店铺横幅
				if(upload2.datas.length!=0){
					$("input[name='banner']").val(upload2.datas[0].path);
				}
				var imgValue=$("input[name='banner']").val();
				if(imgValue==""||imgValue==null){
					layer.msg(fy.getMsg('请上传店铺横幅'));
					return false;
				}
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});