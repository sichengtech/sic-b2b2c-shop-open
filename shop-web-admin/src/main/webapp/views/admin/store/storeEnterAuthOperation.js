$(function () {
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "enterId": {maxlength: 19,},
                "status": {maxlength: 1,},
                "isPerfect": {maxlength: 1,},
                "companyName": {maxlength: 64,},
                "countryId": {maxlength: 19,},
                "countryName": {maxlength: 64,},
                "provinceId": {maxlength: 19,},
                "provinceName": {maxlength: 64,},
                "cityId": {maxlength: 19,},
                "cityName": {maxlength: 64,},
                "districtId": {maxlength: 19,},
                "districtName": {maxlength: 64,},
                "detailedAddress": {maxlength: 255,},
                "companyPhone": {maxlength: 64,},
                "staffCount": {maxlength: 10,},
                "registeredCapital": {maxlength: 10,},
                "contact": {maxlength: 64,},
                "contactNumber": {maxlength: 64,},
                "qq": {maxlength: 64,},
                "email": {maxlength: 64,},
                "sellerLicense": {maxlength: 64,},
                "sellerLicensePath": {maxlength: 64,},
                "sellerScope": {maxlength: 255,},
                "organizationCode": {maxlength: 64,},
                "organizationCodePath": {maxlength: 64,},
                "generalTaxpayerPath": {maxlength: 64,},
                "accountName": {maxlength: 255,},
                "bankAccount": {maxlength: 64,},
                "bankName": {maxlength: 255,},
                "branchLineContact": {maxlength: 64,},
                "bankCountryId": {maxlength: 19,},
                "bankCountryName": {maxlength: 64,},
                "bankProvinceId": {maxlength: 19,},
                "bankProvinceName": {maxlength: 64,},
                "bankCityId": {maxlength: 19,},
                "bankCityName": {maxlength: 64,},
                "bankDistrictId": {maxlength: 19,},
                "bankDistrictName": {maxlength: 64,},
                "districtImgPath": {maxlength: 64,},
                "settlementAccountName": {maxlength: 64,},
                "settlementBankAccount": {maxlength: 64,},
                "settlementBankName": {maxlength: 64,},
                "settlementBranchLineContact": {maxlength: 64,},
                "settlementCountryId": {maxlength: 19,},
                "settlementCountryName": {maxlength: 64,},
                "settlementProvinceId": {maxlength: 19,},
                "settlementProvinceName": {maxlength: 64,},
                "settlementCityId": {maxlength: 19,},
                "settlementCityName": {maxlength: 64,},
                "settlementDistrictId": {maxlength: 19,},
                "settlementDistrictName": {maxlength: 64,},
                "taxRegistrationNumber": {maxlength: 64,},
                "taxIdentificationNumber": {maxlength: 64,},
                "taxRegistrationNumberPath": {maxlength: 64,},
                "storeName": {maxlength: 64,},
                "levelId": {maxlength: 19,},
                "industryId": {maxlength: 19,},
                "categoryId": {maxlength: 19,},
                "summaryOfCoping": {required: true, maxlength: 10,},
                "paymentVoucherPath": {maxlength: 64,},
                "paymentInstructions": {maxlength: 255,},
                "oneAuditOpinion": {required: true, maxlength: 255,},
                "twoAuditOpinion": {required: true, maxlength: 255,},
                "auditOpinion": {required: true, maxlength: 255,},
                "commission": {maxlength: 12,},
            },
            messages: {
                "enterId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "status": {maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "isPerfect": {maxlength: fy.getMsg('最大长度不能超过') + 1 + fy.getMsg('字符'),},
                "companyName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "countryId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "countryName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "provinceId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "provinceName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "cityId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "cityName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "districtId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "districtName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "detailedAddress": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "companyPhone": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "staffCount": {maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),},
                "registeredCapital": {maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),},
                "contact": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "contactNumber": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "qq": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "email": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "sellerLicense": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "sellerLicensePath": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "sellerScope": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "organizationCode": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "organizationCodePath": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "generalTaxpayerPath": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "accountName": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "bankAccount": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bankName": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "branchLineContact": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bankCountryId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "bankCountryName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bankProvinceId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "bankProvinceName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bankCityId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "bankCityName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "bankDistrictId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "bankDistrictName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "districtImgPath": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementAccountName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementBankAccount": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementBankName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementBranchLineContact": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementCountryId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "settlementCountryName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementProvinceId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "settlementProvinceName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementCityId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "settlementCityName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "settlementDistrictId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "settlementDistrictName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "taxRegistrationNumber": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "taxIdentificationNumber": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "taxRegistrationNumberPath": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "storeName": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "levelId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "industryId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "categoryId": {maxlength: fy.getMsg('最大长度不能超过') + 19 + fy.getMsg('字符'),},
                "summaryOfCoping": {required: fy.getMsg('必填项') , maxlength: fy.getMsg('最大长度不能超过') + 10 + fy.getMsg('字符'),},
                "paymentVoucherPath": {maxlength: fy.getMsg('最大长度不能超过') + 64 + fy.getMsg('字符'),},
                "paymentInstructions": {maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "oneAuditOpinion": {required: fy.getMsg('必填项') , maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "twoAuditOpinion": {required: fy.getMsg('必填项') , maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "auditOpinion": {required: fy.getMsg('必填项') , maxlength: fy.getMsg('最大长度不能超过') + 255 + fy.getMsg('字符'),},
                "commission": {maxlength: fy.getMsg('最大长度不能超过') + 12 + fy.getMsg('字符'),},
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

    /*
     * 应付总金额下拉选
     */
    $(".summaryOfCoping-input").click(function () {
        var isOneEnter = $(this).attr("isOneEnter");
        if (isOneEnter == "0") {
            $(this).parent().parent().parent().find(".change").css("display", "block");
        }
    });

    /**
     * 选择下拉选的值
     */
    $(".change1").click(function () {
        $(this).parent().parent().find(".change").css("display", "none");
        $(".summaryOfCoping-input").val($(this).html());
        $(".summaryOfCoping-input").attr("value", $(this).html());
    });
    $(".change2").click(function () {
        $(this).parent().parent().find(".change").css("display", "none");
        $(".summaryOfCoping-input").val($(this).html());
        $(".summaryOfCoping-input").attr("value", $(this).html());
    });
    $(".change3").click(function () {
        $(this).parent().parent().find(".change").css("display", "none");
        $(".summaryOfCoping-input").val("0");
        $(".summaryOfCoping-input").attr("value", "0");
    });

    /**
     * 点击任何的位置都可以关闭应付总金额的下拉选
     */
    $(".panel").not(".summaryOfCoping-input").click(function (e) {
        var inputClass = e.target.className;
        if (inputClass == null || inputClass == "") {
            $(".summaryOfCoping-input").parent().parent().parent().find(".change").css("display", "none");
        } else {
            if (inputClass.indexOf("summaryOfCoping-input") != -1) {
                $(".summaryOfCoping-input").parent().parent().parent().find(".change").css("display", "block");
            } else {
                $(".summaryOfCoping-input").parent().parent().parent().find(".change").css("display", "none");
            }
        }
    });

});