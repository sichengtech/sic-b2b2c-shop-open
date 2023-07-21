$(function(){
	
	/**
	 * 初始化热销
	 */
	rightRecommend("rx");
	
	/**
	 * 左侧切换
	 */
	$("#leftnav_item li a").click(function(){
		$("#leftnav_item li").removeClass("cur");
		$(this).parent().addClass("cur");
		var attr = $(this).attr("attr");
		$('.category_m').css('display','none'); 
		$('.'+attr).css('display','block'); 
		rightRecommend(attr);
	});
	
	/**
	 * 根据左侧点击获取右侧广告位
	 * @param attr
	 * @returns
	 */
	function rightRecommend(attr){
		var brandList = $('.'+attr).find('.logo_list').find('ul').html();
		var categoryList1 = $("."+attr+"_category1").find('ul').html();
		var categoryList2 = $("."+attr+"_category2").find('ul').html();
		if(brandList=="" || categoryList1=="" || categoryList2==""){
			/**
			 * 获取品牌
			 */
			$.ajax({						
				type: 'GET',
				url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_categoryIndex_'+attr+'_brand',
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
					var categoryIndexRecommendList = data.data.siteRecommendItemList;
					var categoryIndex_recommend_brand_Tpl=$("#category_brand_Tpl").html();
					var result = '';
					for (var i = 0; i < categoryIndexRecommendList.length; i++) {
						var url = '';
						if(categoryIndexRecommendList[i].weixinUrl==null){
							url = 'javascript:;';
						}else{
							url = categoryIndexRecommendList[i].weixinUrl;
						}
		    			var rowData={"url":url,"path":ctxfs+categoryIndexRecommendList[i].path+"@70x45"};//模板数据
						var categoryIndexRecommendBrandHtml=wx_common.render(categoryIndex_recommend_brand_Tpl,rowData);//渲染模板
						result+=categoryIndexRecommendBrandHtml;
					}
					$('.'+attr).find('.logo_list').find('ul').html(result); 
				},
				error: function(data){
					layer.open({content: "获取热销品牌数据报错",skin: 'msg',time: 2 });
				}
			});
			
			/**
			 * 获取分类数据1
			 */
			$.ajax({						
				type: 'GET',
				url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_categoryIndex_'+attr+'_category1',
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
					var categoryIndexRecommendList = data.data.siteRecommendItemList;
					var categoryIndex_recommend_category_Tpl=$("#category_Tpl").html();
					var result = '';
					for (var i = 0; i < categoryIndexRecommendList.length; i++) {
						var url = '';
						if(categoryIndexRecommendList[i].weixinUrl==null){
							url = 'javascript:;';
						}else{
							url = categoryIndexRecommendList[i].weixinUrl;
						}
		    			var rowData={"url":url,"path":ctxfs+categoryIndexRecommendList[i].path+"@100x100","cName":categoryIndexRecommendList[i].addInfo1};//模板数据
						var categoryIndexRecommendCategoryHtml=wx_common.render(categoryIndex_recommend_category_Tpl,rowData);//渲染模板
						result+=categoryIndexRecommendCategoryHtml;
					}
					$("."+attr+"_category1").find('ul').html(result);
					wx_common.LazyloadImg();//使用图片延迟加载
				},
				error: function(data){
					layer.open({content: "获取分类数据报错",skin: 'msg',time: 2 });
				}
			});
			
			/**
			 * 获取分类数据1
			 */
			$.ajax({						
				type: 'GET',
				url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_categoryIndex_'+attr+'_category2',
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
					var categoryIndexRecommendList = data.data.siteRecommendItemList;
					var categoryIndex_recommend_category_Tpl=$("#category_Tpl").html();
					var result = '';
					for (var i = 0; i < categoryIndexRecommendList.length; i++) {
						var url = '';
						if(categoryIndexRecommendList[i].weixinUrl==null){
							url = 'javascript:;';
						}else{
							url = categoryIndexRecommendList[i].weixinUrl;
						}
						var rowData={"url":url,"path":ctxfs+categoryIndexRecommendList[i].path+"@100x100","cName":categoryIndexRecommendList[i].addInfo1};//模板数据
						var categoryIndexRecommendCategoryHtml=wx_common.render(categoryIndex_recommend_category_Tpl,rowData);//渲染模板
						result+=categoryIndexRecommendCategoryHtml;
					}
					$("."+attr+"_category2").find('ul').html(result);
					wx_common.LazyloadImg();//使用图片延迟加载
				},
				error: function(data){
					layer.open({content: "获取分类数据报错",skin: 'msg',time: 2 });
				}
			});
			
		}
		
	};
	
});