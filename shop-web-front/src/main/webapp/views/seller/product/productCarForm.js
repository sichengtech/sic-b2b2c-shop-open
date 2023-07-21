$(function(){
	var tree = {};
	/**
	 * 渲染模板
	 */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
	
	/**
	 * 页面初始化加载品牌2级品牌
	 */
	firstLetterClick("A");
	
	/**
	 * 点击字母换下面的品牌
	 */
	$(".firstLetter").click(
		function(){
			$(".ztree").html("");
			$(".firstLetter").removeClass("cur");
			$(this).addClass("cur");
			firstLetterClick($(this).html());
		}
	);
	
	/**
	 *	按照字母获取车型
	 */
	function firstLetterClick (firstLetter){
		$(".carsel-list").html("");
		var tpl = $("#productCar_tpl1").html();//模板
		for (var i = 0; i < carTwoNodes.length; i++) {
			if(carTwoNodes[i].firstLetter==firstLetter){
				var dataJson = {"carId":carTwoNodes[i].carId,"imgPath":carTwoNodes[i].imgPath,"name":carTwoNodes[i].name};
				var brandLi = render(tpl,dataJson);
				$(".carsel-list").append(brandLi);
			}
		}
	}
	 
	/**
	 *	点击品牌加载zTree
	 */
	$("#productCar").delegate(".productCarTwo","click",function(){
		var index = layer.msg(fy.getMsg('请稍等...'), {icon: 16,shade: 0.30,time: 0});
		$(".ztree").html("");
		$("#key").val("");
		$(".productCarTwo").removeClass("brandClass");
		$(this).addClass("brandClass");
		var carId = $(this).attr("carId");
		$.ajax({																	
			type: 'post',
			url: ctxs + "/product/productSpu/ajaxProductCar.htm?carId="+carId,
			dataType: 'json',
			success: function(data){
				carArr = [];
				for (var i = 0; i < data.length; i++) {
					var map = {};
					map["id"] = data[i].carId.toString();
					map["name"] = data[i].name.toString();
					map["pId"] = data[i].parentId.toString();
					carArr.push(map);
				}
				verificationSelectedInit(carArr);
				layer.close(index); 
			},
			error: function(data){
				layer.msg(fy.getMsg('选择车型加载失败'));
				return false;
			}
		});
	});
	
	/**
	 *	验证zTree是否选中并初始化
	 */
	function verificationSelectedInit(carArr){
		var setting = {check:{enable:true,nocheckInherit:true},
				   view:{selectedMulti:false,showIcon: false},
				   data:{simpleData:{enable:true}},
				   callback: {onCheck: zTreeOnCheck}
				  };
		// 初始化树结构
		tree = $.fn.zTree.init($("#carNameTree"), setting, carArr);
		// 默认选择节点(按照已选车型过滤)
		$(".carIds").each(function(){
			var cIds = $(this).val();
			var ids = cIds.split(",");
			var node = tree.getNodeByParam("id", ids[ids.length-1]);
			try{tree.checkNode(node, true, true);}catch(e){}
		});
		// 默认不展开全部节点
		tree.expandAll(false);
	};
	
	/**
	 *	去掉zTree选中项
	 */
	function verificationSelected(cIds){
		var ids = cIds.split(",");
		var node = tree.getNodeByParam("id", ids[ids.length-1]);
		try{tree.checkNode(node, false, true);}catch(e){}
	};
	
	/**
	 *	模糊查询选择车型(zTree)
	 */
	var key = $("#key");
	key.bind("focus", focusKey).bind("blur", blurKey).bind("change cut input propertychange", searchNode);
	key.bind('keydown', function (e){if(e.which == 13){searchNode();}});
	function focusKey(e) {
		if (key.hasClass("empty")) {
			key.removeClass("empty");
		}
	}
	function blurKey(e) {
		if (key.get(0).value === "") {
			key.addClass("empty");
		}
		searchNode(e);
	}
	function searchNode() {
		// 取得输入的关键字的值
		var value = $(this).val();
		// 按名字查询
		var keyType = "name";
		var nodes = tree.getNodes();
		hideAllNode(nodes);
		nodeList = tree.getNodesByParamFuzzy(keyType, value);
		updateNodes(nodeList);
	}
	//隐藏所有节点
	function hideAllNode(nodes){
		nodes = tree.transformToArray(nodes);
		for(var i=nodes.length-1; i>=0; i--) {
			tree.hideNode(nodes[i]);
		}
	}
	//更新节点状态
	function updateNodes(nodeList) {
		tree.showNodes(nodeList);
		for(var i=0, l=nodeList.length; i<l; i++) {
			//展开当前节点的父节点
			tree.showNode(nodeList[i].getParentNode()); 
			//显示展开符合条件节点的父节点
			while(nodeList[i].getParentNode()!=null){
				tree.expandNode(nodeList[i].getParentNode(), true, false, false);
				nodeList[i] = nodeList[i].getParentNode();
				tree.showNode(nodeList[i].getParentNode());
			}
			//显示根节点
			tree.showNode(nodeList[i].getParentNode());
			//展开根节点
			tree.expandNode(nodeList[i].getParentNode(), true, false, false);
		}
	}
	
	/**
	 *	拼接成已选车型
	 */
	function zTreeOnCheck(event, treeId, treeNode) {
		var carBrandId = $(".carsel-list .brandClass").attr("carid");
		var carBrandName = $(".carsel-list .brandClass span").html();
		if(treeNode.checked){
			var tpl = $("#productCar_tpl2").html();//模板
			//选中
			if(treeNode.isParent){
				//父节点
				for (var i = 0; i < treeNode.children.length; i++) {
					var carIds = carBrandId + "," + treeNode.id + "," + treeNode.children[i].id;
					var name = carBrandName + "-" + treeNode.name + "-" + treeNode.children[i].name;
					var dataJson = {"carIds":carIds,"name":name};
					var carLi = render(tpl,dataJson);
					$("#con_choose").append(carLi);
				}
			}else{
				var pId = treeNode.pId;
				if(pId==null || pId==''){
					//选择直接子节点
					var carIds = carBrandId + "," + treeNode.id;
					var name = carBrandName +"-"+ treeNode.name;
					var dataJson = {"carIds":carIds,"name":name};
					var carLi = render(tpl,dataJson);
					$("#con_choose").append(carLi);
				}else{
					//选择父节点中的子节点
					var carIds = carBrandId + "," + treeNode.pId +","+ treeNode.id;
					var name = carBrandName + "-" + treeNode.getParentNode().name +"-"+ treeNode.name;
					var dataJson = {"carIds":carIds,"name":name};
					var carLi = render(tpl,dataJson);
					$("#con_choose").append(carLi);
				}
			}
		}else{
			//取消
			$(".carIds").each(function(){
				var cIds = $(this).val();
				if(cIds.indexOf(treeNode.id.toString())!=-1){
					$("input[value='"+$(this).val()+"']").parent().remove();
				}
			});
		}
	}
	
	/**
	 *	删除单个已选车型
	 */
	$("#productCar").delegate(".removeCar","click",function(){
		$(this).parent().parent().remove();
		verificationSelected($(this).parent().parent().find(".carIds").val());
	});
	
	/**
	 *	选择好了
	 */
	$(".ok").click(function(){
		parent.layer.closeAll();
		$(".carIds").each(function(){
			var carIds = $(this).val();
			var status = true;
			parent.$(".carIds").each(function(){
				var carIdsParent = $(this).val();
				if(carIds == carIdsParent){
					status = false;
				}
			});
			if(status){
				parent.$(".carDiv-ul").append($(this).parent());
			}
		});
	});
	
});
