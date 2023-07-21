$(function () {
    /**
     * 一般搜索，点击放大镜执行搜索
     */
    $("#searchFormButton").click(function () {
        $("#searchForm").submit();
    });

    /**
     * 点击"进入店铺"
     */
    $(".storeOperate").click(function () {
        var UId = $(this).attr("UId");
        var isLocked = $(this).attr("isLocked");
        if (isLocked == '1') {
            layer.msg(fy.getMsg('该账号已经被锁定'));
            return false;
        }
        var loginName = "";
        var verification = "";
        $.ajax({
            type: 'post',
            async: false,
            url: ctxa + "/store/store/storeOperate.do",//在admin准备登录的信息(用户名和码)
            data: {'UId': UId},
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data.status == '0') {
                    layer.msg(data.content);
                    return false;
                }
                if (data.status == '1') {
                    loginName = data.loginName;
                    verification = data.verification;
                }
                $.ajax({
                    type: 'post',
                    url: ctxsso + "/login/loginSimulation.htm",//带着用户名和码，去sso验证登录是否成功
                    data: {'loginName': loginName, "verification": verification},
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.status == '0') {
                            layer.msg(data.content);
                            return false;
                        }
                        if (data.status == '1') {
                            window.open(ctxsso + "/login/loginSuccess.htm");
                        }
                    },
                    error: function (data) {
                        layer.msg(fy.getMsg('进入店铺失败'));
                        return false;
                    }
                });
            },
            error: function (data) {
                layer.msg(fy.getMsg('进入店铺失败'));
                return false;
            }
        });
    });

});