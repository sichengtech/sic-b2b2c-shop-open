$(function () {

    /**
     * 添加新内容
     * */
    $(".addContent").click(function () {
        //去掉li的id和class属性和img的class属性
        $(".shopdate-list .img-list li.selected").attr("id", "");
        $(".shopdate-list .img-list li.selected img").attr("class", "");
        $(".shopdate-list .img-list li.selected").removeClass("selected");
        //把上传数量置为0
        $(".existSize_photo").val(0);
        var rowData = {
            "class": "selected",
            "id": "img_photo_0",
            "path": ctxStatic + "/sicheng-admin/images/recom_default_image.png",
            "path2": ""
        };//模板的数据
        var add_content_tpl = $("#add_content_tpl").html();
        var contentLi = $(render(add_content_tpl, rowData));//渲染模板
        $(".shopdate-list .img-list").append(contentLi);
        //还原编辑区域，把编辑区域置为空
        returnEditContent();
    });

    /**
     * 还原编辑区域，把编辑区域置为空
     * */
    function returnEditContent() {
        $(".selectpicker").find("option[value='1']").attr("selected", true);
        $(".linkValue").val("");
        $(".linkValue").attr("placeholder", fy.getMsg('点击内容不进行任何形式的链接操作'));
        $(".linkValue").attr("readonly", "readonly");
        $("input[name='addInfo1']").val("");
        $("input[name='addInfo2']").val("");
        $("input[name='addInfo3']").val("");
        $("input[name='addInfo4']").val("");
        $("input[name='pId']").val("");
    }

    /**
     * 刚进页面添加一个内容
     */
    //如果是编辑则回显推荐过的内容
    if (typeof (siteRecommendItemList) != "undefined" && siteRecommendItemList != null && siteRecommendItemList.length != 0) {
        for (var i = 0; i < siteRecommendItemList.length; i++) {
            var rowData = {
                "class": "",
                "id": "",
                "path": ctxfs + siteRecommendItemList[i].path,
                "path2": siteRecommendItemList[i].path
            };//模板的数据
            var add_content_tpl = $("#add_content_tpl").html();
            var contentLi = $(render(add_content_tpl, rowData));//渲染模板
            contentLi.attr("riId", siteRecommendItemList[i].riId);
            contentLi.attr("pId", siteRecommendItemList[i].pid);
            contentLi.attr("operationType", siteRecommendItemList[i].operationType);
            contentLi.attr("operationContent", siteRecommendItemList[i].operationContent);
            contentLi.attr("addInfo1", siteRecommendItemList[i].addInfo1);
            contentLi.attr("addInfo2", siteRecommendItemList[i].addInfo2);
            contentLi.attr("addInfo3", siteRecommendItemList[i].addInfo3);
            contentLi.attr("addInfo4", siteRecommendItemList[i].addInfo4);
            $(".shopdate-list .img-list").append(contentLi);
        }
    } else {
        $(".addContent").click();
    }

    /**
     * 选择内容选项
     */
    $(".shopdate-list .img-list").delegate("li", "click", function () {
        changeContent();
        if ($(this).hasClass("selected")) {
            return false;
        }
        $(".shopdate-list .img-list li.selected").attr("id", "");
        $(".shopdate-list .img-list li.selected").removeClass("selected");
        $(this).addClass("selected");
        $(".shopdate-list .img-list li.selected").attr("id", "img_photo_0");
        $(".shopdate-list .img-list li.selected img").attr("class", "preview");
        //回显数据
        //type:1图片,2商品
        if ("1" == type) {
            //附加信息1
            var addInfo1 = $(this).attr("addinfo1");
            if (addInfo1 != null && typeof (addInfo1) != "undefined") {
                $("input[name='addInfo1']").val(addInfo1);
            } else {
                $("input[name='addInfo1']").val("");
            }
            //附加信息2
            var addInfo2 = $(this).attr("addInfo2");
            if (addInfo2 != null && typeof (addInfo2) != "undefined") {
                $("input[name='addInfo2']").val(addInfo2);
            } else {
                $("input[name='addInfo2']").val("");
            }
            //附加信息3
            var addInfo3 = $(this).attr("addInfo3");
            if (addInfo3 != null && typeof (addInfo3) != "undefined") {
                $("input[name='addInfo3']").val(addInfo3);
            } else {
                $("input[name='addInfo3']").val("");
            }
            //附加信息4
            var addInfo4 = $(this).attr("addInfo4");
            if (addInfo4 != null && typeof (addInfo4) != "undefined") {
                $("input[name='addInfo4']").val(addInfo4);
            } else {
                $("input[name='addInfo4']").val("");
            }
        }
        if ("2" == type) {
            //商品id
            var pId = $(this).attr("pId");
            if (pId != null && typeof (pId) != "undefined") {
                $("input[name='pId']").val(pId);
            } else {
                $("input[name='pId']").val("");
            }
        }
        //操作类型
        var operationtype = $(this).attr("operationtype");
        if (operationtype != null && typeof (operationtype) != "undefined") {
            $(".selectpicker").find("option[value=" + operationtype + "]").attr("selected", true);
        } else {
            $(".selectpicker").find("option[value='1']").attr("selected", true);
        }
        //根据不同的操作类型显示不同的提示信息
        $(".selectpicker").change();
        //操作内容
        var operationContent = $(this).attr("operationContent");
        if (operationContent != null && typeof (operationContent) != "undefined") {
            $("input[name='operationContent']").val(operationContent);
        } else {
            $("input[name='operationContent']").val("");
        }
    });

    /**
     * 移除内容
     */
    $(".shopdate-list .img-list").delegate(".del", "click", function () {
        $(this).parent().remove();
        //如果没有被选中的内容，就还原内容区域
        var length = $(".shopdate-list .img-list li.selected").length;
        if (length == 0) {
            returnEditContent();
        }
    });

    /**
     * 内容的拖动
     * */
    $(".shopdate-list ul").sortable({
        revert: false,//revert:sortable 项目是否使用一个流畅的动画还原到它的新位置。
        items: "li:not(.ui-state-disabled)"//items:指定元素内的哪一个项目应是 sortable。
    }).disableSelection();//disableSelection:元素集合内的文本内容(因为要实现拖动，所以要让文字不能选择)

    /**
     * 操作的改变事件
     * 根据不同的操作类型显示不同的提示信息
     * */
    $(".selectpicker").change(function () {
        var val = $(this).val();
        //无操作
        if (val == "1") {
            $(".linkValue").attr("placeholder", fy.getMsg('点击内容不进行任何形式的链接操作'));
            $(".linkValue").val("");
            $(".shopdate-list .img-list li.selected").attr("operationcontent", "");
            $(".linkValue").attr("readonly", "readonly");
        }
        //链接地址
        if (val == "2") {
            $(".linkValue").attr("placeholder", fy.getMsg('网站设置.推荐位.操作提示14'));
            $(".linkValue").removeAttr("readonly");
        }
        //关键字
        if (val == "3") {
            $(".linkValue").attr("placeholder", fy.getMsg('网站设置.推荐位.操作提示15'));
            $(".linkValue").removeAttr("readonly");
        }
        //商品编号
        if (val == "4") {
            $(".linkValue").attr("placeholder", "");
            $(".linkValue").removeAttr("readonly");
        }
        //店铺编号
        if (val == "5") {
            $(".linkValue").attr("placeholder",fy.getMsg('网站设置.推荐位.操作提示16')  );
            $(".linkValue").removeAttr("readonly");
        }
        //商品分类
        if (val == "6") {
            $(".linkValue").attr("placeholder", fy.getMsg('网站设置.推荐位.操作提示16'));
            $(".linkValue").removeAttr("readonly");
        }
    });

    /**
     * 换图
     */
    $(".uploadBtn").click(function () {
        $(".existSize_photo").val(0);
        $(".file").click();
    });

    /**
     * 推荐商品：根据商品id查询商品
     * */
    $(".recommend").click(function () {
        var pId = $(".productId").val();
        if (pId == "" || typeof (pId) == "undefined") {
            layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+ fy.getMsg('请输入商品编号'));
            return false;
        }
        var reg = new RegExp("^[0-9]*$");
        if (!reg.test(pId)) {
            layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+ fy.getMsg('商品编号只能是数字'));
            return false;
        }
        //var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            url: ctxa + "/product/productSpu/selectById.do",
            type: 'POST',
            data: {"pId": pId},
            dataType: 'json',
            success: function (data) {
                layer.close(index);
                if (data == null) {
                    layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+ fy.getMsg('推荐失败，商品不存在'));
                    return false;
                }
                //回显图片(默认商品封面图)
                $(".shopdate-list .img-list li.selected img").attr("src", ctxfs + data.image);
                $(".shopdate-list .img-list li.selected input[name='path']").val(data.image);
                //操作类型变为“商品编号”
                $(".selectpicker").find("option[value='4']").attr("selected", true);
                //操作内容变为 商品的id
                $(".linkValue").attr("placeholder", fy.getMsg('网站设置.推荐位.操作提示16'));
                $(".linkValue").val(data.pid);
                $(".linkValue").removeAttr("readonly");
                $(".shopdate-list .img-list li.selected").attr("operationtype", "4");
                $(".shopdate-list .img-list li.selected").attr("operationcontent", data.pid);
                $(".shopdate-list .img-list li.selected").attr("pid", data.pid);
                layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+ fy.getMsg('推荐成功'));
            }, error: function () {
                layer.close(index);
                layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+ fy.getMsg('推荐失败'));
            }
        });
    });

    /**
     * 上传图片
     * */
    $(".fileUploadClass").change(function () {
        $(".shopdate-list .img-list li.selected").find(".imgPath").val("");
    });

    /**
     * 标签对应内容的该表事件
     * */
    $(".itemMsg").change(function () {
        var itemMsg = $(this).val();
        var name = $(this).attr("name");
        $(".shopdate-list .img-list li.selected").attr(name, itemMsg);
    });

    /**
     * 选择第二个内容框时，把第一个内容框中的值都付给第一个内容框
     * */
    function changeContent() {
        $(".itemMsg").each(function () {
            var itemMsg = $(this).val();
            var name = $(this).attr("name");
            $(".shopdate-list .img-list li.selected").attr(name, itemMsg);
        });
    }

    /**
     * 渲染模板
     */
    function render(tpl, data) {
        return laytpl(tpl).render(data);
    }

});