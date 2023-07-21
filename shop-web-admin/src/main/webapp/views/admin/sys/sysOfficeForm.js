$(document).ready(function () {
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "name": {required: true, maxlength: 100,},
                "code": {maxlength: 100,},
                "address": {maxlength: 255,},
                "zipCode": {regex: /^[1-9]\d{5}(?!\d)$/, maxlength: 6,},
                "master": {maxlength: 100,},
                "phone": {regex: /^0\d{2,3}-?\d{7,8}$/, maxlength: 64,},
                "fax": {maxlength: 200,},
                "email": {
                    regex: /^[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?$/,
                    maxlength: 200,
                },
                "remarks": {maxlength: 255,},
            },
            messages: {
                "name": {required: "不能为空", maxlength: fy.getMsg('最大长度不能超过') + 100 + fy.getMsg('字符'),},
                "code": {maxlength: fy.getMsg('最大长度不能超过') + 100 + fy.getMsg('字符'),},
                "address": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "zipCode": {regex: fy.getMsg('请输入正确格式邮编'), maxlength: fy.getMsg('最大长度不能超过') + 6 + fy.getMsg('字符'),},
                "master": {maxlength: fy.getMsg('最大长度不能超过') + 100 + fy.getMsg('字符'),},
                "phone": {regex: fy.getMsg('请输入正确格式电话'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "fax": {maxlength: fy.getMsg('最大长度不能超过') + 200 + fy.getMsg('字符'),},
                "email": {regex: fy.getMsg('请输入正确格式邮箱'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "remarks": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
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