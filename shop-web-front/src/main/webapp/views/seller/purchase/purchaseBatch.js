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
     * 渲染模板
     */
    function render(tpl, data) {
        return laytpl(tpl).render(data);
    }

    /**
     * 初始添加一行
     */
    for (var i = 0; i < 1; i++) {
        addRow();
    }

    /**
     * 点击增加一行
     */
    $(".addPurchaseRow").click(function () {
        addRow();
    });

    /**
     * 删除一行
     */
    $("#listTable").delegate("input[name='delButton']", 'click', function () {
        $(this).parent().parent().remove();
    });

    /**
     * 添加一行方法
     */
    function addRow() {
        var trTpl = $("#purchase_batch_tr_tpl").html();//模板
        var length = $("#listTable tbody tr").length;
        var nameId = "name_" + length;
        var modelId = "model_" + length;
        var amountId = "amount_" + length;
        var unitId = "unit_" + length;
        var trData = {"nameId": nameId, "modelId": modelId, "amountId": amountId, "unitId": unitId};//模板的数据
        var trHtml = render(trTpl, trData);//渲染模板
        $("#listTable tbody").append(trHtml);
    }

    /**
     * 表单验证
     */
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "title": {required: true, maxlength: 64,},
                "cycle": {required: true, maxlength: 64,},
                "purchaseExplain": {maxlength: 64,},
                "expiryTime": {required: true, maxlength: 64,},
                "name": {required: true, maxlength: 64,},
                "model": {required: true, maxlength: 64,},
                "amount": {required: true, maxlength: 9, regex: /^([1-9][0-9]*)$/},
                "priceRequirement": {maxlength: 12, regex: /^([1-9]\d{0,8}|0)(\.\d{1,2})?$/},
                "unit": {required: true, maxlength: 5,},
                "purchaseRemark": {maxlength: 255,},
            },
            messages: {
                "title": {required: fy.getMsg('采购标题不能为空'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "cycle": {required: fy.getMsg('交货周期不能为空'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "purchaseExplain": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "expiryTime": {
                    required: fy.getMsg('采购到期时间不能为空'),
                    maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),
                },
                "name": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "model": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "amount": {
                    required: fy.getMsg('必填项'),
                    maxlength: fy.getMsg('最大长度不能超过') + 9 + fy.getMsg('字符'),
                    regex: fy.getMsg('输入正整数'),
                },
                "priceRequirement": {
                    maxlength: fy.getMsg('最大长度不能超过') + 12 + fy.getMsg('字符'),
                    regex: fy.getMsg('输入正整数或两位以内的正小数'),
                },
                "unit": {required: fy.getMsg('必填项'), maxlength: fy.getMsg('最大长度不能超过') + 5 + fy.getMsg('字符'),},
                "purchaseRemark": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
            },
            errorPlacement: function (error, element) {
                //错误提示信息的显示位置
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                    error.appendTo(element.parent().parent());
                } else if ("expiryTime" == element.attr("name") || "title" == element.attr("name") || "cycle" == element.attr("name")) {
                    error.insertAfter(element);
                } else {
                    error.insertAfter(element.siblings("br"));
                }
            },
            submitHandler: function (form) {
                //对checkbox处理，1：选中；0：未选中
                $("input[type=checkbox]").each(function () {
                    $(this).after("<input type=\"hidden\" name=\"" + $(this).attr("name") + "\" value=\""
                        + ($(this).attr("checked") ? "1" : "0") + "\"/>");
                    $(this).attr("name", "_" + $(this).attr("name"));
                });
                //判断是否添加行
                var trLength = $("#listTable tbody tr.purchase_content").length;
                if (trLength == 0) {
                    fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> " + fy.getMsg('请输入采购内容'), 2000);
                    return false;
                }
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            }
        });
    }
});