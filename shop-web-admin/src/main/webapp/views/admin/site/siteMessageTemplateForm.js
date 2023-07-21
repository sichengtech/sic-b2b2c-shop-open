$(function () {
    if (jsValidate) {
        var oldNum = $("#oldNum").val();
        $("#inputForm").validate({
            rules: {
                "num": {
                    required: true,
                    maxlength: 64,
                    remote: ctxa + "/site/siteMessageTemplate/exitNum.do?oldNum=" + encodeURIComponent(oldNum),
                },
                "name": {required: true, maxlength: 64,},
                "type": {required: true, maxlength: 64,},
                "sort": {required: true, maxlength: 10, regex: /^(0|[1-9][0-9]*)$/,},
                "isOpen": {required: true, maxlength: 1,},
                "msgContent": {maxlength: 512,},
                "smsOpen": {required: true, maxlength: 1,},
                "smsContent": {maxlength: 512,},
                "emailOpen": {required: true, maxlength: 1,},
                "emailTitle": {maxlength: 255,},
                "emailContent": {},
            },
            messages: {
                "num": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                    remote: fy.getMsg('此编号已存在'),
                },
                "name": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "type": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "sort": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('排序要输入数字'),
                },
                "isOpen": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "msgContent": {maxlength: fy.getMsg('最大长度不能超过') + 512 + fy.getMsg('字符'),},
                "smsOpen": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "smsContent": {maxlength: fy.getMsg('最大长度不能超过') + 512 + fy.getMsg('字符'),},
                "emailOpen": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "emailTitle": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "emailContent": {},
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