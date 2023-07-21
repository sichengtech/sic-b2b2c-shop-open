$(document).ready(function () {
    $("#name").focus();
    //如果js验证开关是开的就进行js验证
    var oldName = $("#oldName").val();
    var oldEnname = $("#oldEnname").val();
    if (jsValidate) {
        $("#inputForm").validate({
            rules: {
                "name": {
                    remote: ctxa + "/sys/role/checkName.do?oldName=" + encodeURIComponent(oldName),
                    required: true,
                    maxlength: 100
                },
                "enname": {
                    remote: ctxa + "/sys/role/checkEnname.do?oldEnname=" + encodeURIComponent(oldEnname),
                    required: true,
                    maxlength: 100
                }
            },
            messages: {
                "name": {
                    remote: fy.getMsg('角色名已存在'),
                    required: fy.getMsg('角色名不能为空'),
                    maxlength: fy.getMsg('角色名不能超过') + 100 + fy.getMsg('字符')
                },
                "enname": {
                    remote: fy.getMsg('英文名已存在'),
                    required: fy.getMsg('英文名不能为空'),
                    maxlength: fy.getMsg('英文名不能超过') + 100 + fy.getMsg('字符')
                }
            },
            submitHandler: function (form) {
                var ids = [], nodes = tree.getCheckedNodes(true);
                for (var i = 0; i < nodes.length; i++) {
                    ids.push(nodes[i].id);
                }
                $("#menuIds").val(ids);
                var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
                for (var i = 0; i < nodes2.length; i++) {
                    ids2.push(nodes2[i].id);
                }
                $("#officeIds").val(ids2);
                layer.msg(fy.getMsg('正在提交，请稍等') + '...', {icon: 16, shade: 0.30, time: 0});
                form.submit();
            },
            errorContainer: "#messageBox",
            errorPlacement: function (error, element) {
                $("#messageBox").text(fy.getMsg('输入有误，请先更正'));
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });
    }
    var setting = {
        check: {enable: true, nocheckInherit: true}, view: {selectedMulti: false},
        data: {simpleData: {enable: true}}, callback: {
            beforeClick: function (id, node) {
                tree.checkNode(node, !node.checked, true, true);
                return false;
            }
        }
    };

    // 用户-菜单
    // 初始化树结构
    var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
    // 不选择父节点
    tree.setting.check.chkboxType = {"Y": "ps", "N": "s"};
    // 默认选择节点
    var ids = menuIds.split(",");
    for (var i = 0; i < ids.length; i++) {
        var node = tree.getNodeByParam("id", ids[i]);
        try {
            tree.checkNode(node, true, false);
        } catch (e) {
        }
    }
    // 默认展开全部节点
    tree.expandAll(true);

    // 用户-机构
    // 初始化树结构
    var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
    // 不选择父节点
    tree2.setting.check.chkboxType = {"Y": "ps", "N": "s"};
    // 默认选择节点
    var ids2 = officeIds.split(",");
    for (var i = 0; i < ids2.length; i++) {
        var node = tree2.getNodeByParam("id", ids2[i]);
        try {
            tree2.checkNode(node, true, false);
        } catch (e) {
        }
    }
    // 默认展开全部节点
    tree2.expandAll(true);
    // 刷新（显示/隐藏）机构
    refreshOfficeTree();
    $("#dataScope").change(function () {
        refreshOfficeTree();
    });
});

function refreshOfficeTree() {
    if ($("#dataScope").val() == 9) {
        $("#officeTree").show();
    } else {
        $("#officeTree").hide();
    }
}