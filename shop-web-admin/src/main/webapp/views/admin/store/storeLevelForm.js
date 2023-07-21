$(function () {
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "name": {required: true, maxlength: 64,},
                "recommendProductCount": {required: true, maxlength: 10, regex: /^[0-9]*$/,},
                "releaseProcuctCount": {required: true, maxlength: 10, regex: /^[0-9]*$/,},
                "pictureSpace": {required: true, maxlength: 10, regex: /^[0-9]*$/,},
                "money": {required: true, maxlength: 12, regex: /^[0-9]+(.[0-9]{1,2})?$/,},
                "applicationNote": {required: true, maxlength: 255,},
                "sort": {required: true, maxlength: 10, regex: /^(0|[1-9][0-9]*)$/,},
                "isOpen": {required: true, maxlength: 1,},
            },
            messages: {
                "name": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "recommendProductCount": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('可推荐商品数必须是数字'),
                },
                "releaseProcuctCount": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('可发布商品数必须是数字'),
                },
                "pictureSpace": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('图片空间容量必须是数字'),
                },
                "money": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 12 + fy.getMsg('字符'),
                    regex: fy.getMsg('收费标准请输入正确数字'),
                },
                "applicationNote": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),
                },
                "sort": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('排序要输入数字'),
                },
                "isOpen": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
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