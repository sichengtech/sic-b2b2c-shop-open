$(function () {
    if (jsValidate) {
        var oldAdNumber = $("#oldAdNumber").val();
        $("#inputForm").validate({
            rules: {
                "adNumber": {
                    remote: ctxa + "/site/siteAd/validateAdNumber.do?oldAdNumber=" + encodeURIComponent(oldAdNumber),
                    required: true,
                    maxlength: 64,
                },
                "name": {required: true, maxlength: 255,},
                "isOpen": {required: true, maxlength: 1,},
                "sort": {required: true, maxlength: 10, regex: /^(0|[1-9][0-9]*)$/,},
                "siteAdcontent": {required: true,},
                "content": {required: true,},
            },
            messages: {
                "adNumber": {
                    remote: fy.getMsg('编号已存在'),
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                },
                "name": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "isOpen": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "sort": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('排序要输入数字'),
                },
                "siteAdcontent": {required: fy.getMsg('必填项'),},
                "content": {required: fy.getMsg('必填项'),},
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

    //删除提示
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });
});