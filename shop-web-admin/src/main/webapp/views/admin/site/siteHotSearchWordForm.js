$(function () {
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "word": {required: true, maxlength: 32,},
                "isShow": {maxlength: 1,},
                "type": {maxlength: 1,},
                "sort": {maxlength: 10,},
            },
            messages: {
                "word": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 32 + fy.getMsg('字符'),},
                "isShow": {maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "type": {maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "sort": {maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),},
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