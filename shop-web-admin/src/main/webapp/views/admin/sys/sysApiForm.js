$(function () {
    /**
     * 修改jquery validate的验证规则，
     * 修改为可以验证相同name的多个input框
     * */
    if ($.validator) {
        $.validator.prototype.elements = function () {
            var validator = this,
                rulesCache = {};
            return $(this.currentForm)
                .find("input, select, textarea")
                .not(":submit, :reset, :image, [disabled]")
                .not(this.settings.ignore)
                .filter(function () {
                    if (!this.name && validator.settings.debug && window.console) {
                        console.error("%o has no name assigned", this);
                    }
                    rulesCache[this.name] = true;
                    return true;
                });
        }
    }

    /**
     * 提交表单
     * */
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "apiCategory": {required: true, maxlength: 1,},
                "apiName": {required: true, maxlength: 64,},
                "apiVersion": {required: true, maxlength: 64,},
                "apiDescribe": {maxlength: 256,},
                "apiUrl": {required: true, maxlength: 64,},
                "paramName": {required: true, maxlength: 64,},
                "paramDescribe": {maxlength: 256,}
            },
            messages: {
                "apiCategory": {
                    required: fy.getMsg('请选择接口分类'),
                    maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),
                },
                "apiName": {required: fy.getMsg('接口名不能为空'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "apiVersion": {
                    required: fy.getMsg('请选择接口版本号'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                },
                "apiDescribe": {
                    maxlength: fy.getMsg(fy.getMsg('最大长度不能超过') + 256 + fy.getMsg('字符')),
                },
                "apiUrl": {
                    required: fy.getMsg('接口url不能为空'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                },
                "paramName": {
                    required: fy.getMsg('参数名不能为空'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                },
                "paramDescribe": {maxlength: fy.getMsg('最大长度不能超过') + 256 + fy.getMsg('字符'),}
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

    /**
     * 添加参数
     * */
    $(".add-param").click(function () {
        var api_param_Tpl = $("#api_param_Tpl").html();
        var paramNameId = "paramName_" + $(".api-param-table tbody tr").length;
        var paramDescribeId = "paramDescribe_" + $(".api-param-table tbody tr").length;
        var api_param_data = {
            "paramNameId": paramNameId,
            "paramDescribeId": paramDescribeId,
            "paramName": "",
            "paramDescribe": "",
            "paramId": ""
        };
        var commentPanelHtml = render(api_param_Tpl, api_param_data);//渲染模板
        $(".api-param-table tbody").append(commentPanelHtml);
    });

    /**
     * 删除参数
     * */
    $(".api-param-table").delegate(".delete-param", "click", function () {
        $(this).parent().parent().remove();
    });

    /**
     * 渲染模板
     * */
    function render(tpl, data) {
        return laytpl(tpl).render(data);
    }

    /**
     * 编辑接口时，回显接口的参数
     * */
    if (typeof (apiParamList) != "undefined" && apiParamList != null && apiParamList != "") {
        var commentPanelHtml = "";
        var api_param_Tpl = $("#api_param_Tpl").html();
        for (var i = 0; i < apiParamList.length; i++) {
            var paramNameId = "paramName_" + $(".api-param-table tbody tr").length;
            var paramDescribeId = "paramDescribe_" + $(".api-param-table tbody tr").length;
            var api_param_data = {
                "paramNameId": paramNameId,
                "paramDescribeId": paramDescribeId,
                "paramId": apiParamList[i].paramId,
                "paramName": apiParamList[i].paramName,
                "paramDescribe": apiParamList[i].paramDescribe,
                "paramType": apiParamList[i].paramType,
                "isRequired": apiParamList[i].isRequired
            };
            commentPanelHtml += render(api_param_Tpl, api_param_data);//渲染模板
        }
        $(".api-param-table tbody").append(commentPanelHtml);
        $(".api-param-table select option").each(function () {
            var value = $(this).attr("value");
            var type = $(this).attr("type");
            if (value == type) {
                $(this).attr("selected", "selected");
            }
        });
    }
});