$(function () {
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "content": {required: true,},
                "sort": {maxlength: 9, regex: /^(0|[1-9][0-9]*)$/,},
            },
            messages: {
                "content": {required: fy.getMsg('必填项'),},
                "sort": {maxlength: fy.getMsg('最大长度不能超过') + 9 + fy.getMsg('字符'), regex: fy.getMsg('排序要输入数字'),},
            },
            errorPlacement: function (error, element) {
                //错误提示信息的显示位置
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-sm")) {
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