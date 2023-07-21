$(function(){
	//showPicList("",1);
	
	/**
	 * 上传新图片
	 * */
	$("#showUploadPanelBtn").click(function(){
		$(this).parent().siblings("li").removeClass("active");
		$(this).parent().addClass("active");
		$(".upload-container").removeClass("none");
		$(".area-container").addClass("none");
	});
	
	/**
	 * 从图片空间中选择
	 * */
	$("#showAlbumPanelBtn").click(function(){
		$(this).parent().siblings("li").removeClass("active");
		$(this).parent().addClass("active");
		$(".area-container").removeClass("none");
		$(".upload-container").addClass("none");
		var albumId=$(".albumId").val();
		showPicList(albumId,1);
	});
	
	/**
	 * 选择图片
	 * */
	$("#searchResult").delegate(".mod-img","click",function(){
		if($(this).hasClass("selected")){
			return false;
		}
		$(this).addClass("selected");
		var tpl = $("#imgTpl2").html();//模板
		var id=$(this).attr("data-index");
		var path=$(this).find("img").attr("src");
		var pixel=$(this).find(".pixel").text();
		var src1=$(this).find("img").attr("src1");
		var data = {"id":id,"path":path,"pixel":pixel,"src1":src1};
		var imgLi=render(tpl,data);
		$("#modSelected ul").append(imgLi);
		var selectedPicNum=parseInt($("#selectedPicNum").text());
		$("#selectedPicNum").text(selectedPicNum+1);
	});
	
	/**
	 * 移除已选图片
	 * */
	$("#modSelected ul").delegate(".icon-tb-delete","click",function(){
		//移除当前图片
		$(this).parents("li").remove();
		//取消被选状态
		var index=$(this).parents("li").attr("data-index");
		$("#searchResult .mod-img[data-index="+index+"]").removeClass("selected");
		var selectedPicNum=parseInt($("#selectedPicNum").text());
		$("#selectedPicNum").text(selectedPicNum-1);
	});
	
	/**
	 * 已选图片的拖动
	 * */
	$("#modSelected ul").sortable({
		revert: true,//revert:sortable 项目是否使用一个流畅的动画还原到它的新位置。
		items: "li:not(.ui-state-disabled)"//items:指定元素内的哪一个项目应是 sortable。
	}).disableSelection();//disableSelection:元素集合内的文本内容(因为要实现拖动，所以要让文字不能选择)
	
	var setting = {
			view:{selectedMulti:false},
			data:{keep:{parent:true}},
			callback:{
				onClick: function(event, treeId, treeNode) {
					zTreeOnClick(event, treeId, treeNode, this);
				}
			}
		};

	/**
	 * 商家相册
	 * 初始化树结构
	 * */
	var tree = $.fn.zTree.init($("#zTreeElement"), setting, 
		[{
			id: 0,
			name: fy.getMsg('图片空间'),
			isParent: true,
			open: true,
			children: zNodes
		}]);
	// 默认展开全部节点
	tree.expandAll(true);
	//默认加载所有相册的图片
	$('#zTreeElement_1_a').trigger('click');
	
	/**
	 * 树形点击事件
	 * */
	function zTreeOnClick(event, treeId, treeNode, zObj) {
		// console.log("zTreeOnClick is running ");
		var self = this;
		currentPage = 1;
		$("#currentFolder").html(treeNode.name);
		$(".albumId").val(treeNode.id);
		showPicList(treeNode.id,1);
	}
	
	/**
	 * 显示图片列表
	 * */
	function showPicList(albumId,pageNo){
		if(albumId=="" || albumId=="0"){
			albumId="";
		}
		layer.load(1, {shade: [0.1,'#fff']});
		$.ajax({
			url:ctxs+"/store/storeAlbumPicture/albumPictureList.htm",
			type:"POST",
			data:{albumId:albumId,pageNo:pageNo},
			dataType:"json",
			success:function(data){
				if(data!=null){
					$("#searchResult").html("");
					if(typeof(data.page)!="undefined" && data.page.list!=null && typeof(data.page.list)!="undefined"){
						var length=data.page.list.length;
						var img=data.page.list;
						for(var i=0;i<length;i++){
							var tpl = $("#imgTpl1").html();//模板
							var id=img[i].pictureId;
							var date=img[i].createDate;
							var path=img[i].path;
							var pixel=img[i].pixel;
							if(typeof(pixel) == "undefined"){
								pixel="";
							}
							var dataJson = {"id":id,"date":date,"path":path,"pixel":pixel};//jsonData是从库中查出来的数据
							var imgLi=render(tpl,dataJson);
							$("#searchResult").append(imgLi);
						}
						//如果当前有被选中的图片，把图片列表中置为已选中状态
						$("#modSelected ul li").each(function(){
							var index=$(this).attr("data-index");
							$("#searchResult .mod-img[data-index="+index+"]").addClass("selected");
						});
					}else{
						var empTpl = $("#emptyImgTpl").html();//模板
						var content=fy.getMsg("对不起，您还未上传图片");
						var dataJson={"content":content};
						var imgLi=render(empTpl,dataJson);
						$("#searchResult").append(imgLi);
					}
					$("input[name='pageNo']").val(pageNo);
					$("input[name='albumId']").val(albumId);
					if(data.isLast){
						$("#mediaIconfontNext").addClass("page-grep");
					}else{
						$("#mediaIconfontNext").removeClass("page-grep");
					}
					if(data.isFirst){
						$("#mediaIconfontPrev").addClass("page-grep");
					}else{
						$("#mediaIconfontPrev").removeClass("page-grep");
					}
				}
				layer.closeAll();
			}
		});
	}
	
	/**
	 * 图片分页：下一页
	 * */
	$("#mediaIconfontNext").click(function(){
		if($(this).hasClass("page-grep")){
			return false;
		}
		var pageNo=parseInt($("input[name='pageNo']").val());
		var albumId=$("input[name='albumId']").val();
		pageNo+=1;
		showPicList(albumId,pageNo);
	});
	
	/**
	 * 图片分页：上一页
	 * */
	$("#mediaIconfontPrev").click(function(){
		if($(this).hasClass("page-grep")){
			return false;
		}
		var pageNo=parseInt($("input[name='pageNo']").val());
		var albumId=$("input[name='albumId']").val();
		pageNo-=1;
		showPicList(albumId,pageNo);
	});
	
	/**
	 * 渲染模板
	 * */
	function render(tpl,data){
		return laytpl(tpl).render(data);
	}
});