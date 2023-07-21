$(function () {
    /**
     * 删除
     */
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });

    /**
     * 清除栏目
     */
    $(".clearArticleChannel").click(function () {
        $("#categoryId").val("");
        $("#categoryName").val("");
    });
});
