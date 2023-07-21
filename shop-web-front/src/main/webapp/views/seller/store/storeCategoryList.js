//jsonData是从库中查出来的数据，可使用<%--${fns:toJson(list)}--%>工具来转换
$(document).ready(function() {
	var tpl = $("#treeTableTpl").html();
	var data = jsonData, ids = [], rootIds = [];
	for (var i=0; i<data.length; i++){
		ids.push(data[i].id);
	}
	ids = ',' + ids.join(',') + ',';
	for (var i=0; i<data.length; i++){
		if (ids.indexOf(','+data[i].parentId+',') == -1){
			if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
				rootIds.push(data[i].parentId);
			}
		}
	}
	
	var htmlArr=new Array();//存储生成的html
	for (var i=0; i<rootIds.length; i++){
		addRow(htmlArr, tpl, data, rootIds[i], true);
	}
	$("#treeTableList").append(htmlArr.join());
	$("#treeTable").treeTable({expandLevel : 5});//展开层级

	//删除分类
	$("#treeTable").delegate(".deleteSure","click",function(){
		var href=$(this).attr("href");
		fdp.confirm(fy.getMsg('确定要删除么？'),href);
	});
});
function addRow(htmlArr, tpl, data, pid, root){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		var parentIds=!row?'':!row.parentId?'':row.parentId;
		if ((parentIds) == pid){
			var dd={
				dict: {isOpen: fdp.getDictLabel(storeCategoryIsOpen, row.isOpen)},
				pid: (root?0:pid), 
				row: row,
			}
			var html = laytpl(tpl).render(dd);
			htmlArr.push(html);
			addRow(htmlArr, tpl, data, row.id);
		}
	}
}
