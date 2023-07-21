$(function () {
    //删除提示
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });
    //锁定提示
    $(".locked").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要锁定么'), href);
    });
    //解锁提示
    $(".unLocked").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要解锁么'), href);
    });
    //一般搜索，点击放大镜执行搜索
    $("#searchFormButton").click(function () {
        $("#searchForm").submit();
    });

    $('.search').on('click', function () {
        var content = $("#myModal").html();
        layer.open({
            type: 1,
            title: ' <i class="fa fa-search"></i> ' + fy.getMsg('查询'),
            area: ['550px', '450px'],
            shadeClose: true, //点击遮罩关闭
            content: content,
            success: function (layero, index) {
                //快速搜索
                $(layero).find("#searchFormMyModal").submit(function () {
                    setTimeout(function () {
                        layer.close(index);
                        //如果1.5秒内未完成操作，则给出请等待的提示
                        layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                    }, 1500);
                });
            }
        });
    });
    //搜索
    $("#searchForm").submit(function () {
        setTimeout(function () {
            //如果1.5秒内未完成操作，则给出请等待的提示
            layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
        }, 1500);
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
                            window.open(ctxs + "/store/storeEnterAuth/auth4.htm");
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