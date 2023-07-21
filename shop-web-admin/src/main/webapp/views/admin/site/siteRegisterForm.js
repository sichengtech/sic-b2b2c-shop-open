$(function () {
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "isMemberRegister": {required: true, maxlength: 1,},
                "isSellerRegister": {required: true, maxlength: 1,},
                "usernameMax": {required: true, maxlength: 10, regex: /^[0-9]*$/,},
                "usernameMin": {required: true, maxlength: 10, regex: /^[0-9]*$/,},
                "pwdMax": {required: true, maxlength: 10, regex: /^[0-9]*$/,},
                "pwdMin": {required: true, maxlength: 10, regex: /^[0-9]*$/,},
                "disableUsername": {maxlength: 1024,},
                /*"agreement":{maxlength:1024,},*/
                "bak1": {maxlength: 64,},
                "bak2": {maxlength: 64,},
                "bak3": {maxlength: 64,},
                "bak4": {maxlength: 64,},
                "bak5": {maxlength: 64,},
                "bak6": {maxlength: 64,},
                "bak7": {maxlength: 64,},
                "bak8": {maxlength: 64,},
                "bak9": {maxlength: 64,},
                "bak10": {maxlength: 64,},
            },
            messages: {
                "isMemberRegister": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),
                },
                "isSellerRegister": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),
                },
                "usernameMax": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('请输入数字'),
                },
                "usernameMin": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('请输入数字'),
                },
                "pwdMax": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('请输入数字'),
                },
                "pwdMin": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('请输入数字'),
                },
                "disableUsername": {maxlength: fy.getMsg('最大长度不能超过') + 1024 + fy.getMsg('字符'),},
                /*"agreement":{maxlength:fy.getMsg('最大长度不能超过') +1024+ fy.getMsg('字符'),},*/
                "bak1": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak2": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak3": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak4": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak5": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak6": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak7": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak8": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak9": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bak10": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
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
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});