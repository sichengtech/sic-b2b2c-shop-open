$(function () {

    /**
     * 初始化MyUpload控件
     */
    window.MYUPLOAD_PREFIX_URL = ctxStatic + "/";
    var img_path = $("input[name='path']").val();//图片地址
    var upload = new MyUpload({
        // 获取token的路径
        tokenPath: ctxa + "/sys/sysToken/getToken.do",
        // 文件上传到的服务器
        server: ctxu + '/upload/webUploadServer.htm',
        // 容器Id
        container: '#vessel',
        buttonStyle: 1,
        //accept: 'file',
        fileSingleSizeLimit: 1024 * 1024 * 5,
        fileNumLimit: 1,
        uploadDelete: function (a) {
            if (a == (ctxfs + img_path)) {
                $("input[name='path']").val("");
            }
        },
        // 上传成功触发，file为文件信息， success为服务端返回的数据。上传成功一个返回一个
        uploadSuccess: function (file, success) {
            $("input[name='path']").val(success.path);
            $("input[name='name']").val(success.original);
        },
    });

    /**
     * 回显页面图片
     */
    if (img_path != "" && img_path != null && typeof img_path != "undefined") {
        console.log(ctxfs + img_path);
        upload.init([ctxfs + img_path]);
    }

    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "category": {required: true, maxlength: 64,},
                "remarks": {maxlength: 200,},
            },
            messages: {
                "category": {required: fy.getMsg('请选择文件分类'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "remarks": {maxlength: fy.getMsg('最大长度不能超过') + 200 + fy.getMsg('字符'),},
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
                //判断是否上传了文件
                var imgSize = $(".existSize_image").val();
                if ("0" == imgSize) {
                    fdp.msg(fy.getMsg('请上传文件'), 3000);
                    return false;
                }
                //对checkbox处理，1：选中；0：未选中
                $("input[type=checkbox]").each(function () {
                    $(this).after("<input type=\"hidden\" name=\"" + $(this).attr("name") + "\" value=\""
                        + ($(this).attr("checked") ? "1" : "0") + "\"/>");
                    $(this).attr("name", "_" + $(this).attr("name"));
                });
                layer.msg(fy.getMsg('正在提交，请稍等') + '...',
                    {
                        icon: 16, shade:
                            0.30, time:
                            0
                    }
                )
                ;
                form.submit();
            }
        });
    }

});
