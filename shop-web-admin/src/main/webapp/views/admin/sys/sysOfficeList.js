$(function () {
    //点击删除
    $("#treeTable").delegate(".deleteSure", "click", function () {
//	$(".deleteSure").click(function(){
        var href = $(this).attr("href");
        fdp.confirm(fy.getMsg('确定要删除么'), href);
    });
});

$(document).ready(function () {
    var tpl = $("#treeTableTpl").html();//模板
    var data = jsonData;//jsonData是从库中查出来的数据
    //var rootId = rootId;
    var htmlArr = new Array();//存储生成的html
    addRow(htmlArr, tpl, data, rootId, true);
    $("#view").append(htmlArr.join());
    $("#treeTable").treeTable({expandLevel: 5});//展开层级
});

function addRow(htmlArr, tpl, data, pid, root) {
    for (var i = 0; i < data.length; i++) {
        var row = data[i];
        var parentIds = !row ? '' : !row.parentId ? '' : row.parentId;
        if ((parentIds) == pid) {
            var dd = {
                dict: {type: fdp.getDictLabel(officeType, row.type)},
                pid: (root ? 0 : pid),
                row: row,
            }
            var html = laytpl(tpl).render(dd);
            htmlArr.push(html);
            addRow(htmlArr, tpl, data, row.id);
        }
    }
}
