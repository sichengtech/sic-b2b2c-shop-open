$(function(){
	
	/**
	 * 获取分类
	 */
	isStore();
	
	/**
	 * 获取分类
	 */
	function isStore(){
		if(storeId!=""){
			$(".search_hotword").find("dd").remove();
			$.ajax({						
				type: 'GET',
				url: ctxw + '/api/v1/store/category/list.htm?storeId='+storeId,
				dataType: 'json',
				success: function(data){
					if(data==null || data.status==""){
						layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
						return;
					}
		        	var status = data.status;//状态码
					if(status!="200"){
						layer.open({content: data.message,skin: 'msg',time: 2 });
						return false;
					}
					//填充商品搜索页面分类
					var storeCategoryList = data.data;
					var storeCategory_Tpl=$("#storeCategory_Tpl").html();//搜索页面分类模板模板
					var storeCategoryHtml = "";
					for (var i = 0; i < storeCategoryList.length; i++) {
						if(i<=10){
							var storeCategoryData={"url":ctxw+"/product/list.htm?sid="+storeId+"&cid="+storeCategoryList[i].storeCategoryId,"name":storeCategoryList[i].name};
							storeCategoryHtml+=wx_common.render(storeCategory_Tpl,storeCategoryData);//渲染模板
						}
					}
					$(".search_hotword").append(storeCategoryHtml);
					//填充店铺底部商品分类
					var storeCategoryParentList = data.data;
					var storeCategoryChildList = data.data;
					var storeCategoryHtml = "";
					var storeCategory_parent_Tpl=$("#storeCategoryParent_Tpl").html();//店铺商品分类一级模板
					var storeCategory_child_Tpl=$("#storeCategoryChild_Tpl").html();//店铺商品分类一级模板
					var noStoreCategory_Tpl=$("#noStoreCategory_Tpl").html();//无店铺商品分类
					if(storeCategory_parent_Tpl!=null){
						if(storeCategoryParentList.length==0 || storeCategoryChildList.length==0){
							$("#popup1").find(".array-msg-div").html(noStoreCategory_Tpl);
							return;
						}
						//修改底部-店铺商品分类
						for (var i = 0; i < storeCategoryParentList.length; i++) {
							if(storeCategoryParentList[i].parentId==0){
								var storeCategoryParentData={"storeId":storeId,"storeCategoryId":storeCategoryParentList[i].storeCategoryId,"name":storeCategoryParentList[i].name};
								storeCategoryHtml+=wx_common.render(storeCategory_parent_Tpl,storeCategoryParentData);//渲染模板
								for (var j = 0; j < storeCategoryChildList.length; j++) {
									if(storeCategoryChildList[j].parentId == storeCategoryParentList[i].storeCategoryId){
										var storeCategoryChildData={"storeId":storeId,"storeCategoryId":storeCategoryChildList[j].storeCategoryId,"name":storeCategoryChildList[j].name};
										storeCategoryHtml+=wx_common.render(storeCategory_child_Tpl,storeCategoryChildData);//渲染模板
									}
								}
							}
						}
						$("#popup1").find(".array-msg-div").html(storeCategoryHtml);
					}
				},
				error: function(data){
					layer.open({content: "获取店铺分类报错",skin: 'msg',time: 2 });
				}
			});
		}
	}
	
	/**
	 * 点击搜索
	 */
	$(".search_button").click(function(){
		var search_value = $(".search-text-input").val();
		var search_storeId = $(".search_storeId").val();
		if(search_storeId==null || search_storeId==''){
			if(search_value==null || search_value==''){
				search_value="热水器";
			}
		}
		location.href = ctxw + "/product/list.htm?sid="+search_storeId+"&k="+search_value;
	});
});