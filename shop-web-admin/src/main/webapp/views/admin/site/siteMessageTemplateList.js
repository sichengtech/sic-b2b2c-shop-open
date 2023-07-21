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
    //站内信
    $('.siteMessage').on('click', function () {
        var id = $(this).attr("messageId");//id
        var num = $(this).attr("messageNum");//编号
        var name = $(this).attr("messageName");//名称
        var isOpen = $(this).attr("messageIsOpen");//是否开启
        var msgContent = $(this).attr("msgContent");//内容
        var content = $("#siteMessageModal").html();
        layer.open({
            type: 1,
            title: ' <i class="fa fa-edit"></i> ' + fy.getMsg('编辑站内信'),
            area: ['550px', '450px'],
            shadeClose: true, //点击遮罩关闭
            content: content,
            success: function (layero, index) {
                $(layero).find("#messageNum_id").html(num);
                $(layero).find("#messageName_id").html(name);
                if (isOpen == 1) {
                    $(layero).find("input[id='radio1']").attr("checked", "checked");
                } else {
                    $(layero).find("input[id='radio2']").attr("checked", "checked");
                }
                $(layero).find("#msgContent_id").html(msgContent);
                $(layero).find("input[name='messageId']").val(id);
                $(layero).find("#siteMessageModal").submit(function () {
                    setTimeout(function () {
                        layer.close(index);
                        //如果1.5秒内未完成操作，则给出请等待的提示
                        layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                    }, 1500);
                });
            }
        });
    });
    //短信
    $('.sms').on('click', function () {
        var id = $(this).attr("smsId");//id
        var num = $(this).attr("smsNum");//编号
        var name = $(this).attr("smsName");//名称
        var smsOpen = $(this).attr("smsOpen");//是否开启
        var smsContent = $(this).attr("smsContent");//内容
        var thirdTemplateCode = $(this).attr("thirdTemplateCode");//内容
        var content = $("#smsModal").html();
        layer.open({
            type: 1,
            title: ' <i class="fa fa-edit"></i> ' + fy.getMsg('编辑短信'),
            area: ['600px', '500px'],
            shadeClose: true, //点击遮罩关闭
            content: content,
            success: function (layero, index) {
                $(layero).find("#smsNum_id").html(num);
                $(layero).find("#smsName_id").html(name);
                if (smsOpen == 1) {
                    $(layero).find("input[id='radio3']").attr("checked", "checked");
                } else {
                    $(layero).find("input[id='radio4']").attr("checked", "checked");
                }
                $(layero).find("#smsContent_id").html(smsContent);
                $(layero).find("#thirdTemplateCode_id").val(thirdTemplateCode);
                $(layero).find("input[name='smsId']").val(id);
                $(layero).find("#smsModal").submit(function () {
                    setTimeout(function () {
                        layer.close(index);
                        //如果1.5秒内未完成操作，则给出请等待的提示
                        layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                    }, 1500);
                });
            }
        });
    });
});