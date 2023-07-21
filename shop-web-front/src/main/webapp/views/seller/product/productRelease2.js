$(function () {

    /**
     * 弹出“选择品牌”窗口
     * 有加载等待提示
     */
    $("#selectBrand").click(function () {
        var storeId = $("#storeId").val();
        layer.open({
            type: 2,//2表示iframe层
            title: ' <i class="sui-icon icon-tb-explore"></i> '+fy.getMsg('选择品牌'),
            area: ['900px', '430px'],
            shadeClose: true, //点击遮罩关闭
            content: ctxs + '/product/productSpu/selectBrand.htm?storeId=' + storeId,//在iframe窗口中打开这个地址
            success: function (layero, index) {
            }
        });
    });

    /**
     * 弹出选择车型窗口
     * 有加载等待提示
     */
    $("#selectProductCar").click(function () {
        var carDiv_ul = $(".carDiv-ul").html();
        layer.open({
            type: 2,
            title: '',
            shadeClose: true,
            shade: 0.8,
            area: ['1000px', '500px'],
            content: ctxs + '/product/productSpu/selectProductCar.htm',
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                body.find('#con_choose').html(carDiv_ul);
            }
        });
    });

    /**
     * 去除选中项
     */
    $(".carDiv-ul").delegate(".removeCar", "click", function () {
        $(this).parent().parent().remove();
    });

    /**
     * 编辑页面车型回显(如果是全车系不回显)
     */
    function carNameEdit() {
        var all_car = $("#all_car").is(':checked');
        if (all_car) {
            $("#selectProductCar").attr("disabled", "true");
        } else {
            var tpl = $("#productCar_tpl").html();//模板
            if (typeof (productCarlistMap) != "undefined") {
                for (var i in productCarlistMap) {
                    var map = productCarlistMap[i];
                    for (var key in map) {
                        var dataJson = {"carIds": key, "name": map[key]};
                        var carLi = render(tpl, dataJson);
                        $(".carDiv-ul").append(carLi);
                    }
                }
            }
        }
    };
    carNameEdit();

    /**
     * 点击全车系判断"选择车型按钮"
     */
    $("#all_car").click(function () {
        var all_car = $(this).is(':checked');
        if (all_car) {
            $("#selectProductCar").attr("disabled", "true");
        } else {
            $("#selectProductCar").removeAttr("disabled");
        }
    });

    /**
     * 弹出“选择计量单位”窗口
     * 有加载等待提示
     */
    $("#selectUnit").click(function () {
        layer.open({
            type: 2,//2表示iframe层
            title: ' <i class="sui-icon icon-tb-explore"></i> '+fy.getMsg('选择计量单位'),
            area: ['900px', '500px'],
            shadeClose: true, //点击遮罩关闭
            content: ctxs + "/product/productSpu/selectUnit.htm",//在iframe窗口中打开这个地址
            success: function (layero, index) {
            }
        });
    });

    /**
     * 规格点击事件
     * 选中规格变为可编辑
     */
    $("input[type='checkbox'].spec_checkbox").change(function () {
        //判断规格名是否可编辑（即输入框是否显示）
        var dt = $(this).parents("dd").siblings("dt");
        var flag = dt.children(".spec_input").is(":hidden");
        if ($(this).prop("checked")) {
            //规格值变为可编辑
            $(this).siblings(".spec_view").css("display", "none");
            $(this).siblings(".spec_input").css("display", "");
            //规格名变为可编辑
            dt.children(".spec_title").css("display", "none");
            dt.children(".spec_input").css("display", "");
            //给规格名添加一个select_spec类名，方便组合sku表格
            dt.children(".spec_input").addClass("select_spec");
        } else {
            //将规格值变为不可编辑
            $(this).siblings(".spec_view").css("display", "");
            $(this).siblings(".spec_input").css("display", "none");
            if ($(this).parents("dd.spec-value-box").find(".spec_checkbox:checked").length > 0) {
                flag = true;
            }
            //如果当前规格下一个规格值都没有选，则将规格名置为不可编辑
            if (!flag) {
                dt.children(".spec_title").css("display", "");
                dt.children(".spec_input").css("display", "none");
                //移除类名select_spec
                dt.children(".spec_input").removeClass("select_spec");
            }
        }
        //如果点击的颜色，需要上传一套图片
        var dlId = $(this).parents("dl").attr("id");
        if ("spec_0" == dlId) {
            $(".product-img-title").siblings("dd[isDefault='Y']").remove();
            var specvId = $(this).siblings(".spec_input").attr("id");
            if ($(this).is(":checked")) {
                var dt = $(this).parents("dd").siblings("dt");
                var specn = dt.find(".spec_title").text() + ":";
                var specnId = dt.find("input").attr("id");
                var specv = $(this).siblings("span").text();
                var specvId = $(this).siblings(".spec_input").attr("id");
                var rowData = {"specn": specn, "specnId": specnId, "specv": specv, "specvId": specvId};//模板的数据
                colorSpec(rowData);
            } else {
                $("." + specvId).parent().parent().remove();
                if ($("#spec_0 .spec_checkbox:checked").length == 0) {
                    var rowDate = {isDefault: "Y", "specn": "", "specv": "", "specnId": ""};
                    colorSpec(rowDate);
                }
            }
        }
        //添加sku组合
        step.Creat_Table();
    });

    /**
     * 规格的改变事件
     * 如果为空，默认置为原来的数据
     * 改变规格时要改变”价格和库存“表格中的规格名和规格值
     */
    $(".spec_input").change(function () {
        //如果规格为空，默认置为原来的值
        var skuv = $(this).siblings("span").text();
        if ($(this).val() == "") {
            $(this).val(skuv);
        }
        //修改“价格和库存”表的数据
        var specVal = $(this).val();
        var className = $(this).attr("id");
        $("." + className).text(specVal);
    });

    /**
     * 批发模式 销售规则 添加一行
     */
    $("#addLineBtn button").click(function () {
        //获取到上一行的购买量，加1,是本行的默认购买量
        var buyNumber = $("#wholesaleTable .buyNumber:last").val();
        var newBuyNumber = parseInt(buyNumber) + 1;
        //获取规则数量
        var length = $("#wholesaleTable tbody tr").length;
        //如果规则>=3,给出提示
        if(length>=3){
            fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('最多只能添加三个销售区间'),2000);
            return false;
        }
        //如果规则数量小于三，添加规则，并且添加规则预览
        var unitName = fy.getMsg("件");
        var unitName2 = $("input[id='unitName1']").val();
        if (unitName2 != "" && typeof (unitName2) != "undefined") {
            unitName = unitName2;
        }
        var rowData = {"buyNumber": newBuyNumber, "minData": newBuyNumber, "index": length, "unitName": unitName};//模板的数据
        var wholesale_Tpl = $("#wholesale_Tpl").html();
        var addContent = render(wholesale_Tpl, rowData);//渲染模板
        //移除规则表中的删除按钮
        $("#wholesaleTable #removeTr").remove();
        //添加规则
        $("#wholesaleTable tbody").append(addContent);
        //添加规则预览
        var rule = $("#wholesaleTable tbody tr").length;
        var name = $("#wholesaleTable tbody tr").length - 1;
        var rowData = {"name": name, "rule": rule, "buyNumber": newBuyNumber, "unitName": unitName};//模板的数据
        var wholesale_Tpl = $("#setViewTr_Tpl").html();
        var addViewContent = render(wholesale_Tpl, rowData);//渲染模板
        $("#setViewTable tbody").append(addViewContent);
        //库存和价格表格中添加1列记录
        var th11 = $("table[id='spec_div'] thead .stockTh");
        var id = $("#wholesaleTable tbody tr").length - 1;
        var rowData = {"id": id, "newBuyNumber": newBuyNumber};//模板的数据
        var spec_salePriceTh_tpl = $("#spec_salePriceTh_tpl").html();
        var salePriceTh = render(spec_salePriceTh_tpl, rowData);//渲染模板
        $(salePriceTh).insertBefore($(th11));
        var td11 = $("table[id='spec_div'] tbody tr .stockTd");
        var rowData = {"price": '0.00'};//模板的数据
        var spec_salePriceTd_tpl = $("#spec_salePriceTd_tpl").html();
        var salePriceTd = render(spec_salePriceTd_tpl, rowData);//渲染模板
        $(salePriceTd).insertBefore($(td11));

    });

    /**
     * 批发模式 销售规则 删除一行
     */
    $("#wholesaleTable").delegate("#removeTr", "click", function () {
        //获取当前点击的是哪一行，以方便移除后面的设置预览
        var index = $(this).parent().parent().index();
        $(this).parent().parent().remove();
        var viewTr = $("#setViewTable tbody").children("tr[name=" + parseInt(index) + "]");
        $(viewTr).remove();
        //规则设置表中的规则大于1条，则添加删除按钮
        //var removeBtn=$("#addLineContent .removeTd").html();

        var rowData = {"": ""};//模板的数据
        var wholesaledel_Tpl = $("#wholesaledel_Tpl").html();
        var removeBtn = render(wholesaledel_Tpl, rowData);//渲染模板

        if ($("#wholesaleTable tbody tr").length > 1) {
            $("#wholesaleTable tbody tr:last td:last").html(removeBtn);
        }
        //更新规则条数
        $("#setViewTable .rule").each(function (index, e) {
            $(this).text(index + 1);
        });
        //更新规格表(删除列)
        var span = $("#spec_div thead tr").find("span[id=" + index + "]");
        var thIndex = $(span).parent().parent().index();
        $("#spec_div thead tr").children("th").eq(thIndex).remove();
        $("#spec_div tbody tr").each(function () {
            $(this).children("td").eq(thIndex).remove();
        });

    });

    /**
     * 验证购买数量，规则2的购买数量要比规则1的购买数量大
     */
    $("#wholesaleTable").delegate(".buyNumber", "blur", function () {
        $(this).attr("value", $(this).val());
        //获取当前行的位置
        var currentTrIndex = $(this).parent().parent().parent().index();
        //修改规则设置预览表中的值
        $("#setViewTable tbody tr[name=" + currentTrIndex + "]").find(".buyNumberView").text($(this).val());
        //修改规格表中的数据
        $("table[id='spec_div']").find("#" + currentTrIndex).text($(this).val());
        //如果当前行是第一行,则不给第一行设置最小起购量，要给第二行设置起购量
        //如果当前行不是第一行,则要给当前行设置最小起购量
        if (currentTrIndex != "0") {
            //获取到当前行的前一行
            var preTr = $("#wholesaleTable").find("tbody tr").eq(parseInt(currentTrIndex) - 1);
            //获取前一行的起购量
            var preBuyNumber = $(preTr).find(".buyNumber").val();
            //当前行的起购量的最小值是preBuyNumber+1
            $(this).attr("data-rule-min", parseInt(preBuyNumber) + 1);
            //如果当前行的值为空，或当前行的值等于上一行的值，则赋默认值,默认值为上一行的值加1
            var currentVal = $(this).val();
            if (currentVal == "" || currentVal == null || currentVal == preBuyNumber) {
                $(this).val(parseInt(preBuyNumber) + 1);
            }
        } else {
            //获取当前行的起购量
            var currentBuyNumber = $(this).val();
            //如果当前行的值为空，则赋默认值1
            var currentVal = $(this).val();
            if (currentVal == "" || currentVal == null) {
                $(this).val("1");
            }
        }
        //设置当前行的下面行的起购量
        var nextTr = $("#wholesaleTable").find("tbody tr").eq(parseInt(currentTrIndex)).next("tr");
        var prevTh = $(this).prev("tr");
        var minCount = prevTh.find(".buyNumber").val();
        var minCount = $(this).val();
        nextTr.find(".buyNumber").attr("data-rule-min", parseInt(minCount) + 1);
        //更新规则条数
        $("#setViewTable .rule").each(function (index, e) {
            $(this).text(index + 1);
        });
    });

    /**
     * 批发商品价格的change事件
     * 当价格改变时更改相应的预览值和更改”价格和库存“表格中的预览值
     */
    $("#wholesaleTable").delegate(".salePrice", "change", function () {
        var index = $(this).parent().parent().parent().index();
        $("#setViewTable tbody tr[name=" + index + "]").find(".priceView").text($(this).val());
        var priceVal = $(this).val();
        $("table[id='spec_div'] tbody tr").each(function () {
            $(this).children(".salePriceTd").eq(index).find(".price").val(priceVal);
            $(this).children(".salePriceTd").eq(index).find("span[class='price']").text(priceVal);
        });

    });

    /**
     * 批发型点击事件
     */
    $(".saleRadio").click(function () {
        //显示销售规则
        $("#saleRule").css("display", "");
        //隐藏最小起购量
        $(".purchasingAmountDl").css("display", "none");
        step.Creat_Table();
    });

    /**
     * 零售型点击事件
     */
    $(".retailRadio").click(function () {
        //隐藏销售规则
        $("#saleRule").css("display", "none");
        //显示最小起购量
        $(".purchasingAmountDl").css("display", "");
        step.Creat_Table();
    });

    /**
     * 混合型点击事件
     */
    $(".retailAndSalRadio").click(function () {
        //显示销售规则
        $("#saleRule").css("display", "");
        //隐藏最小起购量
        $(".purchasingAmountDl").css("display", "none");
        step.Creat_Table();
    });

    /**
     * 运费类型点击事件
     * 使用固定运费点击事件
     * */
    $("input[class='fixedFreightRadio']").click(function () {
        //显示运费的输入框，隐藏运费模板的下拉选
        $("#fixedFreightDiv").css("display", "");
        $(this).parent().siblings(".fixedFreightDiv").css("display", "");
        $("#freigthtTemplateDiv").css("display", "none");
        $(".logistics-param-dl").css("display", "none");
    });

    /**
     * 运费类型点击事件
     * 使用运费规则点击事件
     * */
    $("input[class='expressTypeRadio']").click(function () {
        //隐藏运费的输入框，显示运费模板的下拉选
        var fixedFreightDiv = $(this).parent().parent().siblings("dd").find(".fixedFreightDiv");
        if (fixedFreightDiv.siblings(".error").length != 0) {
            fixedFreightDiv.siblings(".error").css("display", "none");
            fixedFreightDiv.find("input").val("0");
        }
        fixedFreightDiv.css("display", "none");
        $("#freigthtTemplateDiv").css("display", "inline-block");
        $(".logistics-param-dl").css("display", "");
    });

    /**
     * 运费类型点击事件
     * 运费到付、卖家承担运费点击事件
     * */
    $("input[class='expressToPayRadio']").click(function () {
        //隐藏运费的输入框，显示运费模板的下拉选
        var fixedFreightDiv = $(this).parent().parent().siblings("dd").find(".fixedFreightDiv");
        if (fixedFreightDiv.siblings(".error").length != 0) {
            fixedFreightDiv.siblings(".error").css("display", "none");
            fixedFreightDiv.find("input").val("0");
        }
        fixedFreightDiv.css("display", "none");
        //隐藏物流规则的下拉选
        $("#freigthtTemplateDiv").css("display", "none");
        $(".logistics-param-dl").css("display", "none");
    });

    /**
     * 刷新物流规则方法
     * */
    function refreshTemplate(ltId) {
        $.ajax({
            url: ctxs + "/logistics/logisticsTemplate/getStoreTemplate.htm",
            type: 'POST',
            data: '',
            dataType: 'json',
            success: function (data) {
                if (data == null || typeof (data) == "undefined") {
                    return false;
                }
                var ltId2 = $("select[name='ltId']").val();
                $("#freigthtTemplateDiv select option").not(".default").remove();
                for (var i = 0; i < data.length; i++) {
                    var select = "";
                    if (ltId == data[i].ltId || ltId2 == data[i].ltId) {
                        select = "selected='selected'";
                    }
                    var rowData = {"ltId": data[i].ltId, "select": select, "name": data[i].name};//模板的数据
                    var logistics_Tpl = $("#logistics_Tpl").html();
                    var logisticsOption = render(logistics_Tpl, rowData);//渲染模板
                    $(logisticsOption).appendTo($("#freigthtTemplateDiv select"));
                }
            }
        });
    }

    /**
     * 刷新物流规则点击事件
     * */
    $("#refreshTemplate").click(function () {
        var ltId = $(this).attr("ltId");
        refreshTemplate(ltId);
    });

    //刚进页面加载物流模板信息
    $("#refreshTemplate").click();
    //每两秒刷新一次物流规则
    //window.setInterval(refreshTemplate,2000);

    /**
     * 校验表单
     */
    if (jsValidate) {
        //jqueryValidation的验证
        $("#inputForm").validate({
            rules: {
                //"brandName":{required: true,},
                "name": {required: true, maxlength: 128,},
                "nameSub": {maxlength: 128,},
                "expressPrice": {regex: /^\d{1,9}$|^\d{1,9}[.]\d{1,3}$/},
                "marketPrice": {regex: /^\d{1,9}$|^\d{1,9}[.]\d{1,3}$/},
                "weight": {maxlength: 12, regex: /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/},
                "volume": {maxlength: 12, regex: /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/},
            },
            messages: {
                //"brandName":{required: "请输入商品品牌",},
                "name": {required: fy.getMsg('请输入商品名称'), maxlength: fy.getMsg('最大长度不能超过128字符'),},
                "nameSub": {maxlength: fy.getMsg('最大长度不能超过128字符'),},
                "expressPrice": {regex: fy.getMsg('运费整数位不能超过9位，小数位不能超过3字符'),},
                "marketPrice": {regex: fy.getMsg('市场价整数位不能超过9位，小数位不能超过3字符'),},
                "weight": {maxlength: fy.getMsg('最大长度不能超过12字符'), regex: fy.getMsg('请输入正整数或两位以内的小数')},
                "volume": {maxlength: fy.getMsg('最大长度不能超过12字符'), regex: fy.getMsg('请输入正整数或两位以内的小数')},
            },
            errorPlacement: function (error, element) {
                //错误提示信息的显示位置
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                    error.appendTo(element.parent().parent());
                } else if (element.attr("name") == "brandName") {
                    error.appendTo(element.siblings("span"));
                } else {
                    error.insertAfter(element);
                }
            },
            submitHandler: function (form) {
                //验证商品描述
                var introduction = UE.getEditor('container').getContent();
                if (introduction == "" || introduction == null) {
                    fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('商品描述不能为空'),2000);
                    return false;
                }
                //获取规格信息
                var productSkuArray = new Array();
                var priceFlag = true;
                $("table[id='spec_div'] tbody tr").each(function (index, e) {
                    var specArray = new Array();
                    //productSkuArray[index]=new Array();
                    $(this).children("td").each(function (index1, e1) {
                        //获取规格名
                        var specName = $("table[id='spec_div'] thead tr").children("th").eq(index1).find(".specName").text();
                        //规格类型（spec1、spec2、颜色...）
                        var specType = $("table[id='spec_div'] thead tr").children("th").eq(index1).find(".specName").attr("id");
                        var specId = $("table[id='spec_div'] thead tr").children("th").eq(index1).find(".specName").attr("specId");
                        //规格值
                        var specValue;
                        if ($(e1).find("input").length == 0) {
                            specValue = $(e1).text();
                        } else {
                            specValue = $(e1).find("input").val();
                        }
                        if ("price" == specType) {
                            var patrn = /^\d{0,9}$|^\d{1,9}[.]\d{1,3}$/;
                            var re = new RegExp(patrn);
                            if (!re.test(specValue)) {
                                priceFlag = false;
                                return priceFlag;
                            }
                        }
                        specArray.push(specType + "_" + specId + "_" + specName + "_" + specValue);
                    });

                    var specData = {"spec": specArray};//模板的数据
                    var spec_input_Tpl = $("#spec_input_Tpl").html();
                    var specInput = render(spec_input_Tpl, specData);//渲染模板
                    $(specInput).appendTo($(".productSpecs"));
                    //productSkuArray.push(specArray+"-");
                });
                if (!priceFlag) {
                    fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('商品价格整数位不能超过9位，小数位不能超过3字符'),2000);
                    return false;
                }
                $(".productSku").val(productSkuArray);
                //如果是批发型或混合型，获取到区间值及区间价
                var saleType = $(".saleType:checked").val();
                if (saleType != 1) {
                    //验证价格
                    var sectionArray = new Array();
                    var priceFlag2 = true;
                    $("#wholesaleTable tbody tr").each(function (index, element) {
                        //起购量
                        var buyNumber = $(this).find(".buyNumber").val();
                        //价格
                        var price = $(this).find(".salePrice").val();
                        var patrn = /^\d{1,9}$|^\d{1,9}[.]\d{1,3}$/;
                        var re = new RegExp(patrn);
                        if (!re.test(price)) {
                            priceFlag2 = false;
                            return priceFlag2;
                        }
                        sectionArray.push(buyNumber + "_" + price);
                        if (index == 0) {
                            $("input[name='purchasingAmount']").val(buyNumber);
                        }
                    });
                    $(".section").val(sectionArray);
                    if (!priceFlag2) {
                        fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('商品价格整数位不能超过9位，小数位不能超过3字符'),2000);
                        return false;
                    }
                }

                //获取商品图片(几个颜色就对应几个图片)
                var imgArray = new Array();
                var falgImg = true;
                var message = fy.getMsg("请上传商品图片");
                $(".product-img-title").siblings("dd").each(function () {
                    //获取颜色
                    var specv = $(this).find(".specvId").text();
                    //获取图片(一个颜色对应多个图片)
                    var picIds = "";
                    if ($(this).find("ul li").length == 0) {
                        falgImg = false;
                        return;
                    }
                    $(this).find("ul li").each(function () {
                        var picId = $(this).attr("picId");
                        if (picId == "" || typeof (picId) == "undefined") {
                            falgImg = false;
                            message = fy.getMsg('商品图片id不能为空');
                            return;
                        }
                        picIds += picId + "-";
                    });
                    imgArray.push(specv + ":" + picIds);
                });
                if (!falgImg) {
                    fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> " + message, 2000);
                    return false;
                }
                //获取商品参数
                var selectParams = "";
                $("input.selectParam").each(function () {
                    var paramV = $(this).val();
                    if (paramV != "" && typeof (paramV) != "undefined") {
                        var paramId = $(this).attr("paramId");
                        selectParams += paramId + "_" + paramV + ",";
                        $(this).parent().parent().siblings("input.valuesImg").attr("name", "valuesImg");
                        $(this).parent().parent().siblings("input.paramType").attr("name", "paramType");
                        $(this).parent().parent().siblings("input.format").attr("name", "format");
                        $(this).parent().parent().siblings("input.paramName").attr("name", "paramName");
                    }
                });
                $(".selectParams").val(selectParams);
                $(".productImgs").val(imgArray);
                //对checkbox处理，1：选中；0：未选中
                $("input[type=checkbox]").not("#all_car").each(function () {
                    $(this).after("<input type=\"hidden\" name=\"" + $(this).attr("name") + "\" value=\""
                        + ($(this).attr("checked") ? "1" : "0") + "\"/>");
                    $(this).attr("name", "_" + $(this).attr("name"));
                });
                var result = true;
                var name = $("#name").val();//商品名称
                var nameSub = $("#nameSub").val();//卖点描述
                var specification = $(".productSku").val();//商品规格
                var introduc = UE.getEditor('container').getContentTxt();//取商品描述
                //发起ajax验证
                $.ajax({
                    url: ctxs + "/product/productSpu/forbiddenWordsValidate.htm",
                    type: "post",
                    data: {"name": name, "nameSub": nameSub, "specification": specification, "introduction": introduc},
                    dataType: "json",
                    async: false,  //同步执行
                    success: function (data) {
                        if (data) {
                            if (data.status == false) {
                                fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> " + data.msg, 15000);
                                result = false;
                            }
                        }
                    }
                });
                if (result) {
                    layer.msg(fy.getMsg('正在提交，请稍等...'), {icon: 16, shade: 0.30, time: 0});
                    form.submit();
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * SKU添加组合
     */
    var step = {
        //SKU信息组合
        Creat_Table: function () {
            //step.hebingFunction();
            //被选中的规格对象
            var SKUObj = $(".select_spec");
            var arrayTile = new Array();//标题组数
            var arrayInfor = new Array();//盛放每组选中的CheckBox值的对象
            var arrayColumn = new Array();//指定列，用来合并哪些列
            var bCheck = true;//是否全选
            var columnIndex = 0;
            $.each(SKUObj, function (i, item) {
                arrayColumn.push(columnIndex);
                columnIndex++;
                //id只是用来标示，在改规格值的时候用
                var id = $(this).attr("id");
                arrayTile.push(id + "_" + $(this).val());
                //选中的CHeckBox取值
                var order = new Array();
                $(this).parent().siblings("dd").find("input[type=checkbox]:checked").each(function () {
                    var id = $(this).siblings(".spec_input").attr("id");
                    order.push(id + "_" + $(this).siblings(".spec_input").val());
                });
                arrayInfor.push(order);
            });
            //开始创建Table表
            if (bCheck == true) {
                var RowsCount = 0;
                $("#createTable").html("");
                if ($("table[id='spec_div']").length != 0) {
                    $("table[id='spec_div']").remove();
                }
                var rowData = {"": ""};//模板的数据
                var spec_table_tpl = $("#spec_table_tpl").html();
                var table = $(render(spec_table_tpl, rowData));//渲染模板
                //var table = $("<table id=\"spec_div\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"spec_table\"></table>");
                table.appendTo($(".spec-div"));
                var thead = $("<thead></thead>");
                thead.appendTo(table);
                var trHead = $("<tr></tr>");
                trHead.appendTo(thead);
                //创建表头
                $.each(arrayTile, function (index, item) {
                    var val = item.split("_");
                    var id = val[0];
                    var ind = parseInt(index) + 1;
                    var rowData = {"indx": ind, "id": id, "val": val[1]};//模板的数据
                    var spec_tableTh_tpl = $("#spec_tableTh_tpl").html();
                    var th = $(render(spec_tableTh_tpl, rowData));//渲染模板
                    th.appendTo(trHead);
                });
                var saleType = $(".saleType:checked").val();
                var th = "";
                if (saleType == '1') {
                    var rowData = {"": ""};//模板的数据
                    var spec_salePriceTh_tpl1 = $("#spec_salePriceTh_tpl1").html();
                    th = $(render(spec_salePriceTh_tpl1, rowData));//渲染模板
                } else if (saleType == '2') {
                    var rowData = {"": ""};//模板的数据
                    var spec_salePriceTh_tpl2 = $("#spec_salePriceTh_tpl2").html();
                    th = $(render(spec_salePriceTh_tpl2, rowData));//渲染模板
                } else {
                    var rowData = {"": ""};//模板的数据
                    var spec_salePriceTh_tpl3 = $("#spec_salePriceTh_tpl3").html();
                    th = $(render(spec_salePriceTh_tpl3, rowData));//渲染模板
                }
                var rowData = {"": ""};//模板的数据
                var spec_tableTh_tpl2 = $("#spec_tableTh_tpl2").html();
                var itemColumHead = $(render(spec_tableTh_tpl2, rowData));//渲染模板
                th.appendTo(trHead);
                itemColumHead.appendTo(trHead);
                var tbody = $("<tbody></tbody>");
                tbody.appendTo(table);
                //生成组合
                var zuheDate = step.doExchange(arrayInfor);
                if (typeof (zuheDate) != "undefined" && zuheDate.length > 0) {
                    //创建行
                    $.each(zuheDate, function (index, item) {
                        var td_array = item.split(",");
                        var tr = $("<tr></tr>");
                        tr.appendTo(tbody);
                        $.each(td_array, function (i, values) {
                            var val = values.split("_");
                            var id = val[0];
                            var rowData = {"id": id, "val": val[1]};//模板的数据
                            var spec_tableTd_tpl = $("#spec_tableTd_tpl").html();
                            var td = $(render(spec_tableTd_tpl, rowData));//渲染模板
                            td.appendTo(tr);
                        });
                        appendTd(saleType, tr, index);
                    });
                } else {
                    var tr = $("<tr></tr>");
                    tr.appendTo(tbody);
                    appendTd(saleType, tr, 0);
                }
                //如果是批发型或混合型，查找规则表格中的数据，拼接到规格信息表中
                var trLength = $("#wholesaleTable tbody tr").length;
                if (saleType == '2' || saleType == '3') {
                    refalshSaleTr();
                }
                //结束创建Table表
                arrayColumn.pop();//删除数组中最后一项
                //合并单元格
            } else {
                //未全选中,清除表格
                document.getElementById('createTable').innerHTML = "";
            }
        },
        //组合数组
        doExchange: function (doubleArrays) {
            var len = doubleArrays.length;
            if (len >= 2) {
                var arr1 = doubleArrays[0];
                var arr2 = doubleArrays[1];
                var len1 = doubleArrays[0].length;
                var len2 = doubleArrays[1].length;
                var newlen = len1 * len2;
                var temp = new Array(newlen);
                var index = 0;
                for (var i = 0; i < len1; i++) {
                    for (var j = 0; j < len2; j++) {
                        temp[index] = arr1[i] + "," + arr2[j];
                        index++;
                    }
                }
                var newArray = new Array(len - 1);
                newArray[0] = temp;
                if (len > 2) {
                    var _count = 1;
                    for (var i = 2; i < len; i++) {
                        newArray[_count] = doubleArrays[i];
                        _count++;
                    }
                }
                return step.doExchange(newArray);
            } else {
                return doubleArrays[0];
            }
        }
    }

    /**
     * 如果是编辑并且有sku值，则在页面加载时就执行创建表格的方法
     */
    //if(typeof(productSkuJsonList)!="undefined" && productSkuJsonList.length>0){
    step.Creat_Table();
    //编辑时，如果是批发或混合型，则要添加批发价的回显
    var saleType = $(".saleType:checked").val();
    if (saleType == '2' || saleType == '3') {
        refalshSaleTr();
    }

    //}

    /**
     * 在更新规格表时，如果是批发或混合型， 要更新批发价以及起购量列的数据
     */
    function refalshSaleTr() {
        $("table[id='spec_div'] thead .salePriceTh").remove();
        $("table[id='spec_div'] tbody tr .salePriceTd").remove();
        $("#wholesaleTable tbody tr").each(function (index, e) {
            //起购量
            var buyNumber = $(this).find(".buyNumber").val();
            //价格
            var price = $(this).find(".salePrice").val();
            //库存和价格表格中添加1列记录
            var th11 = $("table[id='spec_div'] thead .stockTh");
            var rowData = {"id": index, "newBuyNumber": buyNumber};//模板的数据
            var spec_salePriceTh_tpl = $("#spec_salePriceTh_tpl").html();
            var salePriceTh = render(spec_salePriceTh_tpl, rowData);//渲染模板
            $(salePriceTh).insertBefore($(th11));
            var td11 = $("table[id='spec_div'] tbody tr .stockTd");
            var rowData = {"price": price};//模板的数据
            var spec_salePriceTd_tpl = $("#spec_salePriceTd_tpl").html();
            var salePriceTd = render(spec_salePriceTd_tpl, rowData);//渲染模板
            $(salePriceTd).insertBefore($(td11));
        });
    }

    /**
     * 往规格表的行拼接列
     * saleType:销售类型，1零售，2批发，3混合
     * tr:往当前行中拼接列
     * index:tr所在位置
     */
    function appendTd(saleType, tr, index) {
        //如果是编辑商品，价格可能有值，做回显
        var price = 0.00;
        if (typeof (productSkuJsonList) != "undefined") {
            if (productSkuJsonList[index] != null && productSkuJsonList[index].price != null) {
                price = productSkuJsonList[index].price;
            }
        }
        //零售价输入框，可以编辑
        var rowData = {"price": price, "index": index};//模板的数据
        var fixedFreightDiv_tpl1 = $("#fixedFreightDiv_tpl1").html();
        var priceTd1 = render(fixedFreightDiv_tpl1, rowData);//渲染模板
        //批发价输入框，不可编辑
        var rowData = {"price": price, "index": index};//模板的数据
        var fixedFreightDiv_tpl2 = $("#fixedFreightDiv_tpl2").html();
        var priceTd2 = render(fixedFreightDiv_tpl2, rowData);//渲染模板
        if (saleType == '1') {
            var rowData = {"priceTd": priceTd1, "display": ""};//模板的数据
            var retailPriceTd_tpl = $("#retailPriceTd_tpl").html();
            var td1 = $(render(retailPriceTd_tpl, rowData));//渲染模板
            var rowData = {"priceTd": priceTd2, "display": "none"};//模板的数据
            var salePriceTd_tpl = $("#salePriceTd_tpl").html();
            var td4 = $(render(salePriceTd_tpl, rowData));//渲染模板
        } else if (saleType == '2') {
            var rowData = {"priceTd": priceTd1, "display": "none"};//模板的数据
            var retailPriceTd_tpl = $("#retailPriceTd_tpl").html();
            var td1 = $(render(retailPriceTd_tpl, rowData));//渲染模板
            var rowData = {"priceTd": priceTd2, "display": ""};//模板的数据
            var salePriceTd_tpl = $("#salePriceTd_tpl").html();
            var td4 = $(render(salePriceTd_tpl, rowData));//渲染模板
        } else {
            var rowData = {"priceTd": priceTd1, "display": ""};//模板的数据
            var retailPriceTd_tpl = $("#retailPriceTd_tpl").html();
            var td1 = $(render(retailPriceTd_tpl, rowData));//渲染模板
            var rowData = {"priceTd": priceTd2, "display": ""};//模板的数据
            var salePriceTd_tpl = $("#salePriceTd_tpl").html();
            var td4 = $(render(salePriceTd_tpl, rowData));//渲染模板
        }
        td1.appendTo(tr);
        td4.appendTo(tr);
        //如果是编辑商品，库存可能有值，做回显
        var stock = "";
        if (typeof (productSkuJsonList) != "undefined") {
            if (productSkuJsonList[index] != null && productSkuJsonList[index].stock != null) {
                stock = productSkuJsonList[index].stock;
            }
        }
        var rowData = {"index": index, "stock": stock};//模板的数据
        var stockTd_tpl = $("#stockTd_tpl").html();
        var td2 = $(render(stockTd_tpl, rowData));//渲染模板
        td2.appendTo(tr);
        //如果是编辑商品，库存可能有值，做回显
        var sn = "";
        if (typeof (productSkuJsonList) != "undefined") {
            if (productSkuJsonList[index] != null && productSkuJsonList[index].sn != null) {
                sn = productSkuJsonList[index].sn;
            }
        }
        var rowData = {"sn": sn};//模板的数据
        var snTd_tpl = $("#snTd_tpl").html();
        var td3 = $(render(snTd_tpl, rowData));//渲染模板
        td3.appendTo(tr);
    }

    /**
     * 上传图片
     */
    $("dl.specImg").delegate(".imgupload2", "click", function () {
        var isDetail = $(this).attr("isDetail");
        var ul = $(this).siblings("ul");
        layer.open({
            type: 2,
            title: fy.getMsg('图片空间'),
            shadeClose: true,
            shade: 0.8,
            area: ['860px', '600px'],
            content: ctxs + "/product/productSpu/productImgUpload.htm",
            btn: [fy.getMsg('插入'), fy.getMsg('取消')],
            btnAlign: 'c',
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var imgLis = "";
                $(body).find("#modSelected ul li").each(function () {
                    var picId = $(this).attr("data-index");
                    if (isDetail == "1") {
                        var path = $(this).find("img").attr("src1");
                        var rowData = {"path": ctxfs + path, "picId": picId};//模板的数据
                        var detailImg_Tpl = $("#detailImg_Tpl").html();
                        var imgLi = render(detailImg_Tpl, rowData);//渲染模板
                    } else {
                        var path = $(this).find("img").attr("src");
                        var src1 = $(this).find("img").attr("src1");
                        var rowData = {"path": path, "picId": picId, "src1": src1};//模板的数据
                        var selectImg_Tpl = $("#selectImg_Tpl").html();
                        var imgLi = render(selectImg_Tpl, rowData);
                    }
                    imgLis += imgLi;
                });
                if (isDetail == "1") {
                    var detail = UE.getEditor('container').getContent();
                    UE.getEditor('container').setContent(detail + imgLis);
                } else {
                    $(imgLis).appendTo(ul);
                }
                layer.close(index);
            }
        });
    });

    /**
     * 图片的mouseover事件
     * */
    $(".specImg").delegate('ul li', 'mouseover', function (e) {
        $(this).addClass("hover");
    });

    /**
     * 图片的mouseout事件
     * */
    $(".specImg").delegate('ul li', 'mouseout', function (e) {
        $(this).removeClass("hover");
    });

    /**
     * 移除图片
     * */
    $(".specImg").delegate('ul li .removeImg', 'click', function () {
        $(this).parent().remove();
    });

    /**
     * 拼接上传图片模板
     * */
    function colorSpec(data) {
        var specImg_Tpl = $("#specImg_Tpl").html();
        var specImg = render(specImg_Tpl, data);//渲染模板
        $(".product-img-title").parent().append(specImg);
    }

    /*
     * 加载页面是默认加载一个上传图片模板,如果是编辑,则回显当前商品的图片
     * pictureMap:有颜色时map的key是相应的颜色，值是该颜色下面的pictureMappingList，
     * 没有颜色时，map的key是”key“,值是pictureMappingList
    */
    var msg = "";
    if (typeof (pictureMap) != "undefined" && !($.isEmptyObject(pictureMap))) {
        for (var key in pictureMap) {
            //获取到规格的input
            var $specInput = $("input[value='" + key + "']");
            //规格值的id名
            var specvId = $specInput.attr("id");
            //规格名
            var specn = $specInput.parents(".spec-value-box").siblings("dt").find("input").val();
            //规格id
            var specnId = $specInput.parents(".spec-value-box").siblings("dt").find("input").attr("id");
            if (typeof (specvId) == "undefined") {
                specvId = "";
            }
            if (typeof (specn) == "undefined") {
                specn = "";
            }
            if (typeof (specnId) == "undefined") {
                specnId = "";
            }

            var rowData = {
                "specn": specn,
                "specnId": specnId,
                "specv": key == "key" ? '' : key,
                "specvId": specvId,
                "isDefault": key == "key" ? 'Y' : ''
            };//模板的数据
            //加载上传图片模板
            colorSpec(rowData);
            for (var i in pictureMap[key]) {
                var path = pictureMap[key][i].storeAlbumPicture.path;
                var picId = pictureMap[key][i].imgId;
                var rowData = {"path": ctxfs + path + "@!80X80", "picId": picId, "src1": path};//模板的数据
                var selectImg_Tpl = $("#selectImg_Tpl").html();
                var imgLi = $(render(selectImg_Tpl, rowData));//渲染模板
                var ul = "";
                //有“颜色”规格时,把图片加载到相应的颜色下面，否则加载一套图片
                if (typeof (specvId) != "undefined" && specvId.length != 0) {
                    ul = $(".spec_color ." + specvId).parent().siblings("ul");
                } else {
                    ul = $(".spec_color").siblings("ul");
                }
                imgLi.appendTo(ul);
            }
        }
    } else {
        var rowDate = {isDefault: "Y", "specn": "", "specv": "", "specnId": ""};
        colorSpec(rowDate);
    }

    /**
     * 图片的拖动
     * */
    $(".specImg").sortable({
        revert: false,//revert:sortable 项目是否使用一个流畅的动画还原到它的新位置。
        items: "li:not(.ui-state-disabled)"//items:指定元素内的哪一个项目应是 sortable。
    }).disableSelection();//disableSelection:元素集合内的文本内容(因为要实现拖动，所以要让文字不能选择)

    /**
     * 右侧导航点击事件
     * */
    $(".pordadd-position li").click(function () {
        $(".pordadd-position .cur").removeClass("cur");
        $(this).addClass("cur");
    });

    /**
     * 右侧导航一一对应
     * */
    var floor = [];
    $('.bs-docs-example').each(function (i) {
        floor.push($($('.bs-docs-example')[i]).offset().top - 200);
    });
    $(window).scroll(function () {
        for (var i = 0; i < floor.length; i++) {
            if ($(window).scrollTop() >= floor[i] && $(window).scrollTop() < floor[i + 1]) {
                $($('.pordadd-position li')[i]).addClass("cur");
                $($('.pordadd-position li')[i - 1]).removeClass("cur");
            } else {
                $($('.pordadd-position li')[i]).removeClass("cur");
            }
        }
    });

    /**
     * 商品参数可输入可选择的js
     * */
    function select_simulated(attr_input, attr_con) {
        //点击其他地方隐藏下拉选
        $(document).click(function () {
            $(attr_con).hide();
        });
        //点输入框显示下拉选
        $(attr_input).click(function (e) {
            //隐藏其他的下拉框
            $(this).parents("span[class='property']").siblings().find(attr_con).hide();
            //设置下拉框的宽度
            $(this).parent().find(attr_con).width($(this).width());
            //显示下拉框
            $(this).parent().find(attr_con).show();
            e ? e.stopPropagation() : event.cancelBubble = true;
            //点击每一个参数，把参数值显示到输入框中
            $(attr_con).find("dd").click(function (e) {
                var attr_value = $(this).text();
                $(this).addClass("selected").siblings().removeClass("selected");
                $(this).parents(attr_con).hide();
                $(this).parents("li").find(attr_input).val(attr_value);
                e ? e.stopPropagation() : event.cancelBubble = true;
            });
        });
        return false;
    }

    select_simulated(".attr_input", ".attr_con");

    /**
     * 选择店铺分类
     * */
    $("input[name='storeCategoryName']").click(function () {
        layer.open({
            type: 2,
            title: fy.getMsg('店铺分类'),
            shadeClose: true,
            shade: 0.8,
            area: ['330px', '500px'],
            content: ctxs + "/product/productSpu/productStoreCategory.htm",
            btn: [fy.getMsg('确定'), fy.getMsg('取消')],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                $(body).find(".selectOk").click();
            }
        });
    });

    /**
     * 清除店铺分类
     * */
    $(".delStoreCategory").click(function () {
        $("input[name='storeCategoryName']").val("");
        $("input[name='storeCategoryId']").val("");
    });

    /**
     * 渲染模板
     */
    function render(tpl, data) {
        return laytpl(tpl).render(data);
    }
});

