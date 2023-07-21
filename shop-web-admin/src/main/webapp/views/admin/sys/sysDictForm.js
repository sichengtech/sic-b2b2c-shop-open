$(function () {
    //如果js验证开关是开的就进行js验证
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "value": {required: true, maxlength: 100},
                "label": {required: true, maxlength: 100},
                "type": {regex: /^[A-Za-z0-9_]+$/, required: true, maxlength: 100},
                "sort": {required: true, maxlength: 10, regex: /^(0|[1-9][0-9]*)$/,},
                "description": {required: true, maxlength: 100},
                "remarks": {maxlength: 255}
            },
            messages: {
                "value": {required: fy.getMsg('字典值不能为空'), maxlength: fy.getMsg('字典值不能超过') + 100 + fy.getMsg('字符')},
                "label": {required: fy.getMsg('字典标签不能为空'), maxlength: fy.getMsg('字典标签不能超过') + 100 + fy.getMsg('字符')},
                "type": {
                    regex: fy.getMsg('请输入字母数字或下划线'),
                    required: fy.getMsg('字典类型不能为空'),
                    maxlength: fy.getMsg('字典类型不能超过') + 100 + fy.getMsg('字符')
                },
                "sort": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),
                    regex: fy.getMsg('排序要输入数字'),
                },
                "description": {required: fy.getMsg('描述不能为空'), maxlength: fy.getMsg('描述不能超过') + 100 + fy.getMsg('字符')},
                "remarks": {
                    maxlength: fy.getMsg('备注不能超过') + 255 + fy.getMsg('字符')
                }
            },
            submitHandler: function (form) {
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});