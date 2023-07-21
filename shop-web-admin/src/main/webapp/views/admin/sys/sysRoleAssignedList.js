$(function () {

    /**
     * 提交角色分配表单
     * */
    $("#assignedBtn").click(function () {
        var ids = "";
        $('.ms-selection li').each(function (i) {
            if ($(this).is(":visible")) {
                var id = $(this).attr("id");
                id = id.substring(0, id.indexOf("-"));
                ids += id + ",";
            }
        });
        ids = ids.substring(0, ids.length - 1);
        $("input[id='inRoleUser']").val(ids);
        $("button[type='submit']").prop("disabled", true);
        $("#assignedForm").submit();
    });

    $("#assignedForm").submit(function () {
        layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
        return true;
    });
});