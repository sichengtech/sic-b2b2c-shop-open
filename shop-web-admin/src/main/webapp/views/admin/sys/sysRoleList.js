$(function () {
    //删除角色
    $(".deleteRole").click(function () {
        //询问框
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });

    $("#submit").click(function () {
        setTimeout(function () {
            //如果1.5秒内未完成操作，则给出请等待的提示
            layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
        }, 1500);
        $("#searchForm").submit();
    });
});