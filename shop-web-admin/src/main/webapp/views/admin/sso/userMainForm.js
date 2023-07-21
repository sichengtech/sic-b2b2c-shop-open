$(function () {
    var oldLoginName = $("#oldLoginName").val();
    var oldEmail = $("#oldEmail").val();
    var oldMobile = $("#oldMobile").val();
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "loginName": {
                    remote: ctxa + "/sso/userMain/validateLoginName.do?oldLoginName=" + encodeURIComponent(oldLoginName),
                    required: true,
                    maxlength: 64,
                },
                "password": {maxlength: 64,},
                "repassword": {equalTo: "#password", maxlength: 200,},
                "email": {
                    remote: ctxa + "/sso/userMain/validateEmail.do?oldEmail=" + encodeURIComponent(oldEmail),
                    regex: /^[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?$/,
                    maxlength: 64,
                },
                "mobile": {
                    remote: ctxa + "/sso/userMain/validateMobile.do?oldMobile=" + encodeURIComponent(oldMobile),
                    regex: /^1\d{10}$/,
                    maxlength: 64,
                },
            },
            messages: {
                "loginName": {
                    remote: fy.getMsg('用户名已存在'),
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                },
                "password": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "repassword": {
                    equalTo: fy.getMsg('新密码和确认新密码不一致'),
                    maxlength: fy.getMsg('密码不能超过') + 10 + fy.getMsg('字符'),
                },
                "email": {remote: fy.getMsg('邮箱已存在'), regex: fy.getMsg('请输入正常格式邮箱'), maxlength: 64,},
                "mobile": {
                    remote: fy.getMsg('电话已存在'),
                    regex: fy.getMsg('请输入正常格式电话'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                },
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