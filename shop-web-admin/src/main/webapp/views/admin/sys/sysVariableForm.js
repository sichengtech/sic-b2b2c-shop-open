$(function () {
    if (jsValidate) {
        var oldName = $("#oldName").val();
        $("#inputForm").validate({
            rules: {
                "name": {
                    required: true,
                    maxlength: 64,
                    remote: ctxa + "/sys/sysVariable/exitName.do?oldName=" + encodeURIComponent(oldName),
                },
                "value": {maxlength: 1024,},
                "valueClob": {},
                "bewrite": {maxlength: 255,},
            },
            messages: {
                "name": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                    remote: fy.getMsg("当前的变量名存在,不可重复"),
                },
                "value": {maxlength: fy.getMsg('最大长度不能超过') + 1024 + fy.getMsg('字符'),},
                "valueClob": {},
                "bewrite": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
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