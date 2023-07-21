$(function () {
    var oldNumber = $("#oldNumber").val();
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "number": {
                    required: true,
                    maxlength: 64,
                    remote: ctxa + "/site/siteSpecialEdition/checkSpecialNumber.do?oldNumber=" + encodeURIComponent(oldNumber),
                },
                "seName": {maxlength: 64,},
                "info": {maxlength: 255,},
            },
            messages: {
                "number": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                    remote: fy.getMsg("编号已存在"),
                },
                "seName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "info": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
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