$(function () {
    //删除提示
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm('确定要删除么？', href);
    });
    //搜索
    $("#searchForm").submit(function () {
        setTimeout(function () {
            //如果1.5秒内未完成操作，则给出请等待的提示
            layer.msg('正在提交，请稍等...', {icon: 16, shade: 0.30, time: 0});
        }, 1500);
    });
});
