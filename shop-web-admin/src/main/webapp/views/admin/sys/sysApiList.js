$(function () {
    //删除提示
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });
    //搜索
    $("#searchForm").submit(function () {
        setTimeout(function () {
            //如果1.5秒内未完成操作，则给出请等待的提示
            layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
        }, 1500);
    });

    /**
     * 查看参数
     * */
    $(".param-view").click(function () {
        var apiId = $(this).attr("apiId");
        if (apiId == null || apiId == "") {
            layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> " + fy.getMsg('接口id不能为空'));
            return false;
        }
        var myRemoteUrl = ctxa + "/sys/sysApiParam/list.do?apiId=" + apiId;
        $.post(myRemoteUrl, {}, function (str) {
            layer.open({
                type: 1,
                title: fy.getMsg('查看参数'),
                area: ['650px', '450px'],
                shadeClose: true, //点击遮罩关闭
                content: str, //注意，如果str是object，那么需要字符拼接。
            });
        });
    });

    /**
     * 渲染模板
     * */
    function render(tpl, data) {
        return laytpl(tpl).render(data);
    }
});