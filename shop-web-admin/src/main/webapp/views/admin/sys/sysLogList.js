$(function () {
    //快速查询
    $('.search').on('click', function () {
        var content = $("#myModal").html();
        layer.open({
            type: 1,
            title: ' <i class="fa fa-search"></i> ' + fy.getMsg('查询'),
            area: ['550px', '375px'],
            shadeClose: true, //点击遮罩关闭
            content: content,
        });
    });

    function resetParam() {
        $("#searchForm input").val("");
    }
});