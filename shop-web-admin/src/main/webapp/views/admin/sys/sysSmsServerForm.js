$(function () {
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "status": {required: true, maxlength: 1,},
                "url": {maxlength: 255,},
                "username": {maxlength: 64,},
                "pwd": {maxlength: 64,},
                "accessKey": {maxlength: 64,},
                "appId": {maxlength: 64,},
                "clientId": {maxlength: 64,},
                "token": {maxlength: 64,},
            },
            messages: {
                "status": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "url": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "username": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "pwd": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "accessKey": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "appId": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "clientId": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "token": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
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
    $(".test-email-btn").click(function () {
        $(this).parent().css({"display": "none"});
        $(".test-email").css({"display": "block"});
    });
    /**
     * 测试发送短信
     */
    $("#send").click(function () {
        var myreg = /^1\d{10}$/;
        var phone = $("#phone").val();
        var text = $("#text").val();
        if (phone == null || phone == '') {
            fdp.msg(fy.getMsg('请输入手机号'));
            return false;
        }
        if (!myreg.test(phone)) {
            fdp.msg(fy.getMsg('手机号格式不正确'));
            return false;
        }
        if (text == null || text == '') {
            fdp.msg(fy.getMsg('请输入短信内容'));
            return false;
        }
        $.ajax({
            url: ctxa + "/sys/sysSmsServer/smsTest.do",
            type: 'POST',
            data: {"phone": phone, "text": text},
            dataType: 'json',
            success: function (data) {
                if (data.status == 1) {
                    fdp.msg(fy.getMsg('测试短信发送成功'));
                }
                if (data.status == 0) {
                    fdp.msg(fy.getMsg('测试短信发送失败'));
                }
            }
        });
    });
});