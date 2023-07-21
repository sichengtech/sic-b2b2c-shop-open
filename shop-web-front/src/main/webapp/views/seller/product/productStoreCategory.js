$(function(){
	var className = "dark",newCount = 1,lastValue = "", key = $("#name");
	var setting = {
		view: {
			addHoverDom: addHoverDom,//用于当鼠标移动到节点上时，显示用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
			removeHoverDom: removeHoverDom,//用于当鼠标移出节点时，隐藏用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
			selectedMulti: false,//设置是否允许同时选中多个节点
		},
		edit:{
			drag: {
				isMove:false,
				isCopy:false,
			},
			enable: true,//设置 zTree 是否处于编辑状态
			showRemoveBtn:showRemoveBtn,//设置是否显示删除按钮
			showRenameBtn:showRenameBtn,//设置是否显示编辑名称按钮
		},
		data:{
			//确定 zTree 初始化时的节点数据、异步加载时的节点数据、或 addNodes 方法中输入的 newNodes 数据是否采用简单数据模式 (Array)
			//不需要用户再把数据库中取出的 List 强行转换为复杂的 JSON 嵌套格式
			simpleData:{enable:true}
		},
		callback:{
			beforeRemove: beforeRemove,//用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
			beforeRename: beforeRename,//用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
			onRemove: onRemove,//用于捕获删除节点之后的事件回调函数。
			onRename: onRename,//用于捕获节点编辑名称结束之后的事件回调函数。
			onDblClick:onDblClick, //用于捕获 zTree 上鼠标双击之后的事件回调函数
		},
	};
	
	/**
	 * 初始化树结构
	 * */
	zNodes.push({id:"0", pId:"", name:fy.getMsg('店铺分类'),isParent:true,drag:false});
	zTree = $.fn.zTree.init($("#categoryTree"), setting, zNodes);
	// 默认展开全部节点
	zTree.expandAll(true);
	//给输入框绑定事件
	key.bind("blur", searchNode).bind("change cut input propertychange", searchNode);
	key.bind('keydown', function (e){if(e.which == 13){searchNode();}});
	
	/**
	 * 是否显示移除按钮
	 * 非根节点都显示移除按钮
	 * */
	function showRemoveBtn(treeId, treeNode) {
		return !treeNode.level == 0;
	}
	
	/**
	 * 是否显示编辑按钮
	 * 非根节点都显示编辑按钮
	 * */
	function showRenameBtn(treeId, treeNode) {
		return !treeNode.level == 0;
	}
	
	/**
	 * 鼠标移出节点时移除添加按钮
	 * */
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	/**
	 * 添加店铺分类
	 * */
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0){
			return;
		}
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn){
			btn.bind("click", function(){
				addStoreCategory(treeNode,0);
				//var nodes = zTree.getNodesByParam("parentTId", treeNode.tId, null);
				//alert(nodes.length+"-----"+nodes[nodes.length-1].name+"---"+nodes[nodes.length-1].sort);
				return false;
			});
		}
	};
	
	/**
	 * 添加店铺分类方法
	 * */
	function addStoreCategory(treeNode,isEdit){
		var name=fy.getMsg('店铺分类') + (newCount++);
		var parentId=treeNode.id;
		var sort=treeNode.sort;
		var storeCategoryId=treeNode.id;
		if("1"==isEdit){
			name=treeNode.name;
			if(treeNode.getParentNode()==null){
				parentId=0;
			}else{
				parentId=treeNode.getParentNode().id;
			}
		}
		//添加时，排序=当前分类下的最高排序+1
		if("0"==isEdit){
			//获取当前分类下的所有分类
			var nodes = zTree.getNodesByParam("parentTId", treeNode.tId, null);
			//获取最有一个分类的排序（最高的排序）,如果当前分类下没有子分类，排序从0开始
			if(nodes.length==0){
				sort=0;
			}else{
				sort=parseInt(nodes[nodes.length-1].sort)+1;
			}
		}
		if(name=="" || typeof(name)=="undefined"){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('分类名不能为空'),2000);
			return false;
		}
		var _data={};
		if("0"==isEdit){
			_data={"parentId":parentId,"name":name,"sort":sort};
		}else{
			_data={"storeCategoryId":storeCategoryId,"parentId":parentId,"name":name,"sort":sort};
		}
		$.ajax({
			url:ctxs+"/store/storeCategory/addStoreCategory.htm",
			type:'post',
			data:_data,
			dataType:'json',
			success:function(data){
				if(data.success){
					if("0"==isEdit){
						zTree.addNodes(treeNode, {id:data.store_category_id, pId:treeNode.id, name:name,sort:sort});
					}
				}else{
					var message= isEdit=="0"?fy.getMsg('添加失败'):fy.getMsg('修改失败');
					if(data.message!=""){
						message=data.message;
					}
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+message,2000);
				}
			},
			error:function(data){
				fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('添加失败'),2000);
			}
		});
	}
	
	/**
	 * 编辑分类前执行的方法
	 * */
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "":"dark");
		if (newName.length == 0) {
			setTimeout(function() {
				zTree.cancelEditName();
				fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('分类名不能为空'),2000);
			}, 0);
			return false;
		}
		return true;
	}
	
	/**
	 * 编辑分类
	 * */
	function onRename(e, treeId, treeNode, isCancel) {
		var name=treeNode.name;
		var storeCategoryId=treeNode.id;
		if(name=="" || typeof(name)=="undefined"){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('分类名不能为空'),2000);
			return false;
		}
		if(storeCategoryId=="" || typeof(storeCategoryId)=="undefined"){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('分类Id不能为空'),2000);
			return false;
		}
		addStoreCategory(treeNode,1);
	}
	
	/**
	 * 删除分类之前给出提示
	 * */
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		zTree.selectNode(treeNode);
		var flag=true;
		layer.confirm(fy.getMsg('确认删除分类') + treeNode.name + fy.getMsg('吗？'), {
			btn: [fy.getMsg('确定'),fy.getMsg('取消')] //按钮
		}, function(index){
			 onRemove("", treeId, treeNode);
			 layer.close(index);
		}, function(index){
			layer.close(index);
		});
		return false;
	}
	
	/**
	 * 删除分类
	 * */
	function onRemove(e, treeId, treeNode) {
		$.ajax({
			url:ctxs+"/store/storeCategory/deleteStoreCategory.htm",
			type:'post',
			data:{"storeCategoryId":treeNode.id},
			dataType:'json',
			success:function(data){
				if(!data.success){
					var message=fy.getMsg('删除失败');
					if(data.message!=""){
						message=data.message;
					}
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+message,2000);
					return;
				}
				zTree.removeNode(treeNode);
			},
			error:function(data){
				fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('删除失败'),2000);
			}
		});
	}
	
	/**
	 * 搜索点击事件
	 * */
	$("#search").click(function(){
		$("#key").focus();
	});
	
	/**
	 * 搜索节点
	 * */
	function searchNode() {
		// 取得输入的关键字的值
		var value = $.trim(key.get(0).value);
		// 按名字查询
		var keyType = "name";
		// 如果和上次一次，就退出不查了。
		if (lastValue === value) {
			return;
		}
		// 保存最后一次
		lastValue = value;
		var nodes = zTree.getNodes();
		// 如果要查空字串，就退出不查了。
		if (value == "") {
			showAllNode(nodes);
			return;
		}
		hideAllNode(nodes);
		nodeList = zTree.getNodesByParamFuzzy(keyType, value);
		updateNodes(nodeList);
	}

	/**
	 * 隐藏所有节点
	 */
	function hideAllNode(nodes){
		nodes = zTree.transformToArray(nodes);
		for(var i=nodes.length-1; i>=0; i--) {
			zTree.hideNode(nodes[i]);
		}
	}

	/**
	 * 显示所有节点
	 */
	function showAllNode(nodes){
		nodes = zTree.transformToArray(nodes);
		for(var i=nodes.length-1; i>=0; i--) {
			if(nodes[i].getParentNode()!=null){
				zTree.expandNode(nodes[i],false,false,false,false);
			}else{
				zTree.expandNode(nodes[i],true,true,false,false);
			}
			zTree.showNode(nodes[i]);
			showAllNode(nodes[i].children);
		}
	}

	/**
	 * 更新节点状态
	 */
	function updateNodes(nodeList) {
		zTree.showNodes(nodeList);
		for(var i=0, l=nodeList.length; i<l; i++) {
			//展开当前节点的父节点
			zTree.showNode(nodeList[i].getParentNode()); 
			//zTree.expandNode(nodeList[i].getParentNode(), true, false, false);
			//显示展开符合条件节点的父节点
			while(nodeList[i].getParentNode()!=null){
				zTree.expandNode(nodeList[i].getParentNode(), true, false, false);
				nodeList[i] = nodeList[i].getParentNode();
				zTree.showNode(nodeList[i].getParentNode());
			}
			//显示根节点
			zTree.showNode(nodeList[i].getParentNode());
			//展开根节点
			zTree.expandNode(nodeList[i].getParentNode(), true, false, false);
		}
	}
	
	/**
	 * 双击节点
	 * */
	function onDblClick(e,treeId,treeNode){
		$(".selectOk").click();
	}
	
	/**
	 * 确定按钮点击事件
	 * */
	$(".selectOk").click(function(){
		var layer=parent.layer;
		var layero=$("#layui-layer"+parent.layer.index);
		var zTree=parent.window['layui-layer-iframe' + parent.layer.index].window.zTree;
		var nodes=zTree.getSelectedNodes();
		if(nodes.length==0){
			layer.close(parent.layer.index);
			return false;
		}
		if (nodes[0].isParent){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('不能选择父节点（')+nodes[0].name+fy.getMsg('）请重新选择。'),15000);
			return false;
		}
		if (nodes[0].level == 0){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('不能选择根节点（')+nodes[0].name+fy.getMsg('）请重新选择。'),15000);
			return false;
		}
		var id=nodes[0].id;
		var name=nodes[0].name;
		$(window.parent.document).find("input[name='storeCategoryName']").val(name);
		$(window.parent.document).find("input[name='storeCategoryId']").val(id);
		layer.close(layer.index);
	});
	
});