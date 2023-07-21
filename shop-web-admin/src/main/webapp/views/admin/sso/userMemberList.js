$(function () {
    /**
     * 删除提示
     */
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });

    /**
     * 锁定提示
     */
    $(".locked").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要锁定么'), href);
    });

    /**
     * 解锁提示
     */
    $(".unLocked").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要解锁么'), href);
    });

    /**
     * 列表搜索
     */
    $(".searchList").click(function () {
        $("#searchForm").submit();
    });

    /**
     * 快速查询
     */
    $('.search').on('click', function () {
        var content = $("#myModal").html();
        layer.open({
            type: 1,
            title: ' <i class="fa fa-search"></i> ' + fy.getMsg('查询'),
            area: ['600px', '495px'],
            shadeClose: true, //点击遮罩关闭
            content: content,
            success: function (layero, index) {
                //快速搜索
                $(layero).find("#searchFormMyModal").submit(function () {
                    setTimeout(function () {
                        layer.close(index);
                        //如果1.5秒内未完成操作，则给出请等待的提示
                        layer.msg(fy.getMsg('正在提交，请稍等'), {icon: 16, shade: 0.30, time: 0});
                    }, 1500);
                });
            }
        });
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

});