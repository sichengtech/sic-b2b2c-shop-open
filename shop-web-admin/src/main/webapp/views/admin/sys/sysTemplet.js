/**
 * 初始化ztree
 */
$(document).ready(function(){
	//点击选择项回调
	function treeOnClick(event, treeId, treeNode, clickFlag){
		if(!treeNode.isDirectory){
			//id:"/views/front/demo/sql/a.sql", 
			//pId:"/views/front/demo/sql", 
			//name:"a.sql", 
			//fullName:"/views/front/demo/sql/a.sql", 
			//isDirectory: false
			//参数说明：全名、目录、文件名、是否是文件夹
			tempateFile(treeNode.fullName,treeNode.pId,treeNode.name,treeNode.isDirectory);
		}else{
			//参数说明：全名、目录、文件名、是否是文件夹
			directory(treeNode.fullName,treeNode.pId,treeNode.name,treeNode.isDirectory);
		}
	}
	
	//异步加载完成后回调
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var treeObj = $.fn.zTree.getZTreeObj(treeId);
		// 展开第2级节点
		var nodes = tree.getNodesByParam("level", 1);
		for(var i=0; i<nodes.length; i++) {
			treeObj.expandNode(nodes[i], true, false, false);
		}  	    
	};
	
	var setting = {
			view:{selectedMulti:false},
			data:{simpleData:{enable:true}},
			callback: {onClick: treeOnClick,onAsyncSuccess: zTreeOnAsyncSuccess},
			async:{
				enable: true,
				url: ctxa+'/cms/template/queryTree.do',
			}
	};
	var tree = $.fn.zTree.init($("#tree"), setting);
	
	//刷新树
	$("#treeRefresh").click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		treeObj.reAsyncChildNodes(null, "refresh");			
	});

});

/**
 * 初始化CodeMirror代码编辑器
 */
$(function(){
	var path=$("#path").html();//模板目录
	var content="";
	if(path==null || path==''){
		content=fy.getMsg('请在左侧选择要编辑的模板文件');
	}
	editor = CodeMirror(document.getElementById("code"), {
      mode: "text/html",
      value: content,
      lineNumbers: true,//设置显示行号
      //styleActiveLine: true,//当前行高亮
      lineWrapping: true, //
      indentUnit: 4,//缩进块用多少个空格表示 默认是2
      autofocus: true,
      matchTags: {bothTags: true},//寻找闭合标签
      extraKeys: {
          "Ctrl-J": "toMatchingTag",//寻找闭合标签
          "Ctrl-Space": "autocomplete",//代码提示
          "F11": function(cm) {
            cm.setOption("fullScreen", !cm.getOption("fullScreen"));
          },
          "Esc": function(cm) {
            if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
          }
        }
    });
});

/**
 * 编辑模板
 * 向服务器请求模板内容，展示在页面上供编辑
 * 参数说明：全名、目录、文件名、是否是文件夹
 */
function tempateFile(fullName,dir,fileName,isDirectory){
	$.ajax({
		url: ctxa+'/cms/template/getTemplateContent.do',
        type: "post",
        data: {"fullName":fullName},
        dataType: "json",
        success: function(data) {
        	$("#templateName").css("display","");//显示
        	$("#templateContent").css("display","");//显示
        	$("#directoryList").css("display","none");//隐藏
			$("#fullName").val(fullName);
			$("#path").html(dir);
			$("#fileName").val(fileName);
			if(data==null ||  data.content==null){
				editor.setValue("");//给代码编辑器赋值
				fdp.msg("未找到模板!");
			}else{
				editor.setValue(data.content);//给代码编辑器赋值
				fdp.msg("模板已加载");
			}
			editor.refresh();
			editor.focus();
        }
	});
}
/**
 * 点击模板目录,取本目录下的一层文件list
 * 参数说明：全名、目录、文件名、是否是文件夹
 */
function directory(fullName,dir,fileName,isDirectory){
	$("#path").html(fullName);
	$("#templateName").css("display","none");//隐藏
	$("#templateContent").css("display","none");//隐藏
	$("#directoryList").css("display","");//显示
	$.ajax({
		url: ctxa+'/cms/template/queryFiles.do',
        type: "post",
        data: {"path":fullName},
        dataType: "json",
        success: function(data) {
        	var result = '';
        	var tpl = $("#tplTable_tpl").html();//模板
        	var htmlAll="";
    		for(var i=0; i<data.length; i++){
    			var html = laytpl(tpl).render(data[i]);
    			htmlAll+=html;
    		}
    		$("#tbody").html(htmlAll);
        }
	});
}

$(function(){
	/**
	 * 创建模板
	 */
	$("#create").click(function(){
		var path=$("#path").html();//模板目录
		if(path==null || path==''){
			fdp.msg("请选择模板目录");
			return false;
		}
		$("#templateName").css("display","");//显示
		$("#templateContent").css("display","");//显示
		$("#directoryList").css("display","none");//隐藏
		$("#fileName").val("");//清空
		$("#fullName").val("");//清空
		editor.setValue("");//清空代码编辑器的内容
		editor.refresh();
		editor.focus();
	});
	
	/**
	 * 保存模板内容
	 */
	$("#save").click(function(){
		var content=editor.getValue();//模板内容
		var fullName=$("#fullName").val();//全名
		var path=$("#path").html();//模板目录
		var fileName=$("#fileName").val();//模板文件名
		if(path==null || path==''){
			fdp.msg("请选择模板目录");
			return false;
		}
		if(fullName==null || fullName==''){
			fullName=path+"/"+fileName;
		}
		
		if(fullName!=null && fullName!=''){
			var t=setTimeout(function(){
				//如果1.5秒内未完成操作，则给出请等待的提示
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
			},1500);
			
			//编辑模板文件
			$.ajax({
				url: ctxa+"/cms/template/save2.do",
		        type: "post",
		        data: {"content":content,"fullName":fullName},
		        dataType: "json",
		        success: function(data) {
		        	clearTimeout(t);
		            if(data){
		            	fdp.msg("保存模板成功");
		        	}else{
		        		fdp.msg("保存模板失败");
		        	}
		        }
			});
		}
	});
	
	/**
	 * 点击编辑按钮编辑模板文件
	 */
	$("#tbody").delegate(".editTempateFile",'click',function(){
		var fullName=$(this).parent().attr("fullName");
		var fileName=$(this).parent().attr("fileName");
		var dir=$(this).parent().attr("dir");
		var isDirectory=$(this).parent().attr("isDirectory");
		if(isDirectory!="true"){
			//参数说明：全名、目录、文件名、是否是文件夹
			tempateFile(fullName,dir,fileName,isDirectory);
		}else{
			fdp.msg("不能编辑文件夹");
		}
	});
	
	/**
	 * 删除模板
	 */
	$("#tbody").delegate(".deleteSure",'click',function(){
		var fullName=$(this).parent().attr("fullName");
		var isDirectory=$(this).parent().attr("isDirectory");
		var tr=$(this).parent().parent();
		
		if(isDirectory=="true"){
			fdp.msg("不能删除文件夹");
			return;
		}
		
		fdp.confirm('确定要删除么？',function(){
			var t=setTimeout(function(){
				//如果1.5秒内未完成操作，则给出请等待的提示
				layer.msg('正在提交，请稍等...', {icon: 16,shade: 0.30,time: 0});
			},1500);
			
			$.ajax({
				url: ctxa+'/cms/template/deleteTemplateFile.do',
		        type: "post",
		        data: {"name":fullName},
		        dataType: "json",
		        success: function(data) {
		        	clearTimeout(t);
		        	if(data){
		        		$(tr).remove();
		            	fdp.msg("删除模板文件成功");
		        	}else{
		        		fdp.msg("删除模板文件失败");
		        	}
		        }
			});
		});	
		
	});
});