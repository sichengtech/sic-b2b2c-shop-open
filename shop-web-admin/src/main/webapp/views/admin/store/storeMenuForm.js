$(function () {
    if (jsValidate) {
        var oldMenuNum = $("#oldMenuNum").val();
        $("#inputForm").validate({
            rules: {
                "name": {required: true, maxlength: 64,},
                "sort": {required: true, maxlength: 10, regex: /^(0|[1-9][0-9]*)$/,},
                "href": {maxlength: 2000,},
                "target": {maxlength: 64,},
                "icon": {maxlength: 64,},
                "isShow": {required: true, maxlength: 1,},
                "permission": {maxlength: 512,},
                // "menuNum": {
                //     required: true, maxlength: 64, regex: /^[0-9]*$/,
                //     remote: ctxa + "/store/storeMenu/exitNum.do?oldMenuNum=" + encodeURIComponent(oldMenuNum),
                // },
            },
            messages: {
                "name": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "sort": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: "排序要输入数字",
                },
                "href": {maxlength: fy.getMsg('最大长度不能超过') + 2000 + fy.getMsg('字符'),},
                "target": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "icon": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "isShow": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "permission": {maxlength: fy.getMsg('最大长度不能超过') + 512 + fy.getMsg('字符'),},
                // "menuNum": {
                //     required: fy.getMsg('必填项'),
                //     maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                //     regex: fy.getMsg('编号要输入数字'),
                //     remote: fy.getMsg('编号已存在'),
                // },
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