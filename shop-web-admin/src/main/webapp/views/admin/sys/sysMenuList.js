$(document).ready(function () {
    $("#treeTable").treeTable({expandLevel: 3}).show();
    //删除菜单
    $(".deleteMenu").click(function () {
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });
    //排序失去焦点
    $(".sorts").blur(function () {
        v(this);
    });
    var flag = 0;

    function valid() {
        $('.sorts').each(function (i) {
            var rs = v(this);
            if (rs != 0) {
                flag = rs;
                return false;
            }
        });
        return flag;
    }

    //验证一个控件
    function v(obj) {
        var stort = $(obj).val();
        var pattern = /^(0|[1-9][0-9]*)$/;
        if (stort == undefined || stort.length == 0) {
            $(obj).css("border", "1px solid red");
            return 1;
        } else if (!pattern.test(stort)) {
            $(obj).css("border", "1px solid red");
            return 2;
        } else {
            $(obj).css("border", "1px solid #A9A9A9");
        }
        return 0;
    }

    $("#listForm").submit(function () {
        var rs = valid();
        if (rs == 0) {
            setTimeout(function () {
                //如果1.5秒内未完成操作，则给出请等待的提示
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
            }, 1500);
            return true;
        } else if (rs == 1) {
            layer.msg(fy.getMsg('排序不能为空'), {time: 3000, icon: 6});
            return false;
        } else if (rs == 2) {
            layer.msg(fy.getMsg('排序只能为正整数'), {time: 3000, icon: 6});
            return false;
        } else {
            return false;
        }
    });

});
