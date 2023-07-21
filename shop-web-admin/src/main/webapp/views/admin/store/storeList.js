$(function () {
    /**
     * 删除提示
     */
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });

    /**
     * 列表搜索
     */
    $(".searchList").click(function () {
        $("#searchForm").submit();
    });

    /**
     * 搜索
     */
    $("#searchForm").submit(function () {
        setTimeout(function () {
            //如果1.5秒内未完成操作，则给出请等待的提示
            layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
        }, 1500);
    });

    /**
     * 保证金
     */
    $(".storeMarking").click(function () {
        var storeId = $(this).attr("storeId");
        layer.open({
            type: 2,//2表示iframe层
            title: ' <i class="sui-icon icon-tb-explore"></i> ' + fy.getMsg('保证金'),
            area: ['500px', '250px'],
            btn: [fy.getMsg('保存'), fy.getMsg('取消')],
            shadeClose: true, //点击遮罩关闭
            content: ctxa + '/store/store/storeMarkingSave1.do?storeId=' + storeId,//在iframe窗口中打开这个地址
            success: function (layero, index) {
            },
            yes: function (index, layero) {
                var markingImgPath = layer.getChildFrame('body', index).find(".imgPath").val();
                if (markingImgPath == null || markingImgPath == '') {
                    layer.msg(fy.getMsg('打标失败:没有上传图片'));
                    return false;
                }
                setTimeout(function () {
                    //如果1.5秒内未完成操作，则给出请等待的提示
                    layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                }, 1500);
                location.href = ctxa + "/store/store/storeMarkingSave2.do?storeId=" + storeId + "&markingImgPath=" + markingImgPath;
            }
        });
    });

    /**
     * 绑定佣金
     */
    $(".bindCommission").click(function () {
        var storeId = $(this).attr("storeId");
        layer.open({
            type: 2,//2表示iframe层
            title: ' <i class="sui-icon icon-tb-explore"></i> '+fy.getMsg('绑定佣金'),
            area: ['400px', '255px'],
            btn: [fy.getMsg('立即绑定'), fy.getMsg('取消')],
            shadeClose: true, //点击遮罩关闭
            content: ctxa + '/store/store/storeCommissionSave1.do?storeId=' + storeId,//在iframe窗口中打开这个地址
            success: function (layero, index) {
            },
            yes: function (index, layero) {
                var commission = layer.getChildFrame('body', index).find("#commission").val();
                if (commission == null || commission == '') {
                    layer.msg(fy.getMsg('佣金不能为空'));
                    return false;
                }
                ;
                var reg = /^(\d|[1-9]\d|100)(\.\d{1,3})?$/;
                var r = commission.match(reg);
                if (r == null) {
                    layer.msg(fy.getMsg('店铺管理.店铺管理.操作提示10'));
                    return false;
                }
                ;
                setTimeout(function () {
                    //如果1.5秒内未完成操作，则给出请等待的提示
                    layer.msg('正在提交，请稍等...', {icon: 16, shade: 0.30, time: 0});
                }, 1500);
                location.href = ctxa + "/store/store/storeCommissionSave2.do?storeId=" + storeId + "&commission=" + commission;
            }
        });
    });

    /**
     * 弹出绑定品牌页面
     */
    $(".bindBrand").click(function () {
        var storeId = $(this).attr("storeId");
        layer.open({
            type: 2,//2表示iframe层
            title: ' <i class="sui-icon icon-tb-explore"></i> '+fy.getMsg('绑定品牌'),
            area: ['750px', '500px'],
            btn: [fy.getMsg('立即绑定'), fy.getMsg('取消')],
            shadeClose: true, //点击遮罩关闭
            content: ctxa + '/store/store/bindBrand1.do?storeId=' + storeId,//在iframe窗口中打开这个地址
            success: function (layero, index) {
            },
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                // var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var brandIds = [];
                body.find(".selectedClass .brandId").each(function () {
                    brandIds.push($(this).val());
                })
                if (brandIds.length == 0) {
                    layer.msg(fy.getMsg('请选择品牌'));
                    return;
                }
                $.ajax({
                    type: 'get',
                    url: ctxa + '/store/store/bindBrand2.do?storeId=' + storeId + '&brandIds=' + brandIds,
                    dataType: 'json',
                    success: function (data) {
                        var status = data.status;
                        var content = data.content;
                        if (status == '0') {
                            layer.closeAll();
                            parent.layer.msg(content);
                        }
                        if (status == '1') {
                            layer.msg(content);
                            return false;
                        }
                    },
                    error: function (data) {
                        layer.msg(fy.getMsg('绑定失败'));
                        return false;
                    }
                });
            }
        });
    });

});