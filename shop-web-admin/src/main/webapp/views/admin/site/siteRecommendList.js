$(function () {

    /**
     * 删除提示
     */
    $(".deleteSure").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
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

    /**
     * 绑定商品
     */
    $(".recommend").click(function () {
        var type = $(this).attr("type");
        var recommendId = $(this).attr("recommendId");
        layer.open({
            type: 2,
            title: '',
            shadeClose: true,
            shade: 0.8,
            area: ['860px', '550px'],
            btn: ['<i class="fa fa-check"></i> ' + fy.getMsg('保存'), '<i class="fa fa-times"></i> ' + fy.getMsg('关闭')],
            content: ctxa + '/site/siteRecommendItem/save1.do?type=' + type + "&recommendId=" + recommendId,
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var data_array = new Array();
                var flag = true;
                var massage = "";
                $(body).find(".img-list li").each(function (index, element) {
                    var _data2 = new Object();
                    if ("1" == type) {
                        var addinfo1 = $(this).attr("addinfo1");
                        var addinfo2 = $(this).attr("addinfo2");
                        var addinfo3 = $(this).attr("addinfo3");
                        var addinfo4 = $(this).attr("addinfo4");
                        _data2.addinfo1 = addinfo1;
                        _data2.addinfo2 = addinfo2;
                        _data2.addinfo3 = addinfo3;
                        _data2.addinfo4 = addinfo4;
                    }
                    if ("2" == type) {
                        var id = $(this).attr("pid");
                        _data2.pid = id;
                        if (id == "" || id == null || typeof (id) == "undefined") {
                            massage = fy.getMsg('推荐商品不能为空');
                            flag = false;
                            return flag;
                        }
                    }
                    var path = $(this).find("input[name='path']").val();
                    if (path == "" || path == null || typeof (path) == "undefined") {
                        massage = fy.getMsg('图片不能为空');
                        flag = false;
                        return flag;
                    }
                    var operationtype = $(this).attr("operationtype");
                    var operationcontent = $(this).attr("operationcontent");
                    if (operationtype != 1 && (operationcontent == "" || operationcontent == null && typeof (operationcontent) == "undefined")) {
                        massage = fy.getMsg('操作内容不能为空');
                        flag = false;
                        return flag;
                    }
                    _data2.path = path;
                    _data2.operationtype = operationtype;
                    _data2.operationcontent = operationcontent;
                    _data2.sort = index;
                    data_array.push(_data2);
                });
                if (!flag) {
                    layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>" + massage);
                    return flag;
                }
                $.ajax({
                    url: ctxa + "/site/siteRecommendItem/save2.do",
                    type: 'POST',
                    data: {"type": type, "recommendId": recommendId, "data_array": JSON.stringify(data_array)},
                    dataType: 'json',
                    success: function (data) {
                        if (data == null) {
                            layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>" + fy.getMsg('推荐失败'));
                            return;
                        }
                        layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i>" + data.message);
                        return;
                    }, error: function (data) {
                        layer.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>" + fy.getMsg('推荐失败'));
                    }
                });
                layer.close(index);
            }
        });
    });
});