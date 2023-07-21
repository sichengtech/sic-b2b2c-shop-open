$(function(){
	
	/**
	 * 顶部导航(初始化加载TagNav)
	 */
	TagNav('#tagnav',{
		type: 'scrollToFirst',
	});
	
	/**
     * 获取未读消息
     * */
    var count=wx_common.getUnreadMsgCount();
    if(count!=0){
    	$(".weui_cells_title a.icon-xiaoxi4 b").text(count);
    }else{
    	$(".weui_cells_title a.icon-xiaoxi4 b").remove();
    }
    
	/**
	 * 首页获取轮播图片
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/carouselPicture/list.htm?type=11',
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
			var indexCarouselPictureList = data.data;
			var indexCarouselPicture_Tpl=$("#index_carouselPicture_Tpl").html();
			var result = '';
			for (var i = 0; i < indexCarouselPictureList.length; i++) {
				var url = '';
				if(indexCarouselPictureList[i].url==null){
					url = 'javascript:;';
				}else{
					url = indexCarouselPictureList[i].url;
				}
    			var rowData={"url":url,"picturePath":ctxfs+indexCarouselPictureList[i].path+"@400x200"};//模板数据
				var indexCarouselPictureHtml=wx_common.render(indexCarouselPicture_Tpl,rowData);//渲染模板
				result+=indexCarouselPictureHtml;
			}
			$('.swiper-wrapper').append(result); 
			//轮播图片
			new Swiper('.swiper-container', {
				pagination: {
					el: '.swiper-pagination',
				},
			});
		},
		error: function(data){
			layer.open({content: "首页获取轮播图片出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 首页获取广告位
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/ad/one.htm?number=weixin_index_banner',
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
			var content = data.data;
			$(".index_ad").html(content);
		},
		error: function(data){
			layer.open({content: "首页获取广告位出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 首页获取活动专题广告位
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_index_hdzt',
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
			var indexRecommendHdztList = data.data.siteRecommendItemList;
			var index_recommend_hdzt_Tpl=$("#index_recommend_hdzt_Tpl").html();
			var result = '';
			for (var i = 0; i < indexRecommendHdztList.length; i++) {
				var url = '';
				if(indexRecommendHdztList[i].weixinUrl==null){
					url = 'javascript:;';
				}else{
					url = indexRecommendHdztList[i].weixinUrl;
				}
    			var rowData={"url":url,"path":ctxfs+indexRecommendHdztList[i].path+"@245x106"};//模板数据
				var indexRecommendHdztHtml=wx_common.render(index_recommend_hdzt_Tpl,rowData);//渲染模板
				result+=indexRecommendHdztHtml;
			}
			$('.index_hdzt').append(result); 
			wx_common.LazyloadImg();//使用图片延迟加载
		},
		error: function(data){
			layer.open({content: "首页获取活动专题广告位出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 首页获取品牌购广告位
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_index_brand_list',
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
			var indexRecommendBrandList = data.data.siteRecommendItemList;
			var index_recommend_brand_Tpl=$("#index_recommend_brand_Tpl").html();
			var result = '';
			for (var i = 0; i < indexRecommendBrandList.length; i++) {
				var url = '';
				if(indexRecommendBrandList[i].weixinUrl==null){
					url = 'javascript:;';
				}else{
					url = indexRecommendBrandList[i].weixinUrl;
				}
    			var rowData={"url":url,"path":ctxfs+indexRecommendBrandList[i].path};//模板数据
				var indexRecommendBrandHtml=wx_common.render(index_recommend_brand_Tpl,rowData);//渲染模板
				result+=indexRecommendBrandHtml;
			}
			$('.index_brand').append(result); 
			wx_common.LazyloadImg();//使用图片延迟加载
		},
		error: function(data){
			layer.open({content: "首页获取品牌购广告位出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 首页获取轮播活动广告位
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/carouselPicture/list.htm?type=12',
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
			var indexCarouselPictureList = data.data;
			var indexCarouselPicture_Tpl=$("#index_carousel_hd_Tpl").html();
			var result = '';
			for (var i = 0; i < indexCarouselPictureList.length; i++) {
				var url = '';
				if(indexCarouselPictureList[i].url==null){
					url = 'javascript:;';
				}else{
					url = indexCarouselPictureList[i].url;
				}
    			var rowData={"url":url,"path":ctxfs+indexCarouselPictureList[i].path+"@290x90"};//模板数据
				var indexCarouselPictureHtml=wx_common.render(indexCarouselPicture_Tpl,rowData);//渲染模板
				result+=indexCarouselPictureHtml;
			}
			$('.index_carousel_hd').append(result); 
			wx_common.LazyloadImg();//使用图片延迟加载
			//加载TagNav
			TagNav('#activitynav',{
				type: 'scrollToFirst',
			});
		},
		error: function(data){
			layer.open({content: "首页获取轮播活动广告位出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 首页获取工程机械专区活动广告位
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_index_gcjx_hd',
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
			var indexRecommendGcjxlHdList = data.data.siteRecommendItemList;
			var index_recommend_gcjx_hd_Tpl=$("#index_recommend_gcjx_hd_Tpl").html();
			var result = '';
			for (var i = 0; i < indexRecommendGcjxlHdList.length; i++) {
				var url = '';
				if(indexRecommendGcjxlHdList[i].weixinUrl==null){
					url = 'javascript:;';
				}else{
					url = indexRecommendGcjxlHdList[i].weixinUrl;
				}
				var cs = "con"+(i+1);
				var img_path=ctxfs+indexRecommendGcjxlHdList[i].path+"@135x175";
				if(i==0){
					img_path=ctxfs+indexRecommendGcjxlHdList[i].path+"@270x175";
				}
    			var rowData={"class":cs,"url":url,"path":img_path};//模板数据
				var indexRecommendGcjxHdHtml=wx_common.render(index_recommend_gcjx_hd_Tpl,rowData);//渲染模板
				result+=indexRecommendGcjxHdHtml;
			}
			$('.index_gcjx_hd').append(result); 
			wx_common.LazyloadImg();//使用图片延迟加载
		},
		error: function(data){
			layer.open({content: "首页获取工程机械专区活动广告位出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 首页获取工程机械专区商品广告位
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_index_gcjx_product',
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
			var indexRecommendGcjxProductList = data.data.siteRecommendItemList;
			var index_recommend_gcjx_product_Tpl=$("#index_recommend_gcjx_product_Tpl").html();
			var result = '';
			for (var i = 0; i < indexRecommendGcjxProductList.length; i++) {
				var url = '';
				var marketPrice = '';
				var marketPriceCla = "display:none;";
				if(indexRecommendGcjxProductList[i].weixinUrl==null){
					url = 'javascript:;';
				}else{
					url = indexRecommendGcjxProductList[i].weixinUrl;
				}
				if(indexRecommendGcjxProductList[i].addInfo3!=undefined && indexRecommendGcjxProductList[i].addInfo3!="" && indexRecommendGcjxProductList[i].addInfo3!=null){
					marketPriceCla = "display:block;";
					marketPrice = "¥"+indexRecommendGcjxProductList[i].addInfo3;
				}
				var rowData={"url":url,"path":ctxfs+indexRecommendGcjxProductList[i].path+"@200x200","name":indexRecommendGcjxProductList[i].addInfo1,"minPrice":"¥"+indexRecommendGcjxProductList[i].addInfo2,"marketPriceCla":marketPriceCla,"marketPrice":marketPrice};//模板数据
				var indexRecommendGcjxProductHtml=wx_common.render(index_recommend_gcjx_product_Tpl,rowData);//渲染模板
				result+=indexRecommendGcjxProductHtml;
			}
			$('.index_gcjx_product').append(result); 
			wx_common.LazyloadImg();//使用图片延迟加载
		},
		error: function(data){
			layer.open({content: "首页获取工程机械专区商品广告位出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 首页获取猜你喜欢商品广告位
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/site/recommend/one.htm?number=weixin_index_product_list',
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
			var indexRecommendProductList = data.data.siteRecommendItemList;
			var index_recommend_product_list_Tpl=$("#index_recommend_product_list_Tpl").html();
			var result = '';
			for (var i = 0; i < indexRecommendProductList.length; i++) {
				var url = '';
				var type = '';
				var isDisplay = '';
				var storeTypeClass = "";
    			var storeType = "";
    			if(indexRecommendProductList[i].addInfo4=="1"){
    				storeTypeClass="display:none;";
    			}
    			if(indexRecommendProductList[i].addInfo4=="2"){
    				storeType="旗舰店";
    			}
				if(indexRecommendProductList[i].weixinUrl==null){
					url = 'javascript:;';
				}else{
					url = indexRecommendProductList[i].weixinUrl;
				}
				if(indexRecommendProductList[i].addInfo3=='1'){
					//minPrice = "¥"+productOne.minPrice1;
					isDisplay = "style='display:none'";
					type = "零";
				}
				if(indexRecommendProductList[i].addInfo3=='2'){
					//minPrice = "¥"+productOne.minPrice2;
					isDisplay = "style='display:none'";
					type = "批";
				}
				if(indexRecommendProductList[i].addInfo3=='3'){
					//minPrice = "¥"+productOne.minPrice1;
					isDisplay = "style='display:none'";
					type = "零";
				}
    			var rowData={"pid":indexRecommendProductList[i].operationContent,"url":url,"storeTypeClass":storeTypeClass,"storeType":storeType,"path":ctxfs+indexRecommendProductList[i].path+"@200x200","name":indexRecommendProductList[i].addInfo1,"minPrice":"¥"+indexRecommendProductList[i].addInfo2,"type":type,"isDisplay":isDisplay};//模板数据
				var indexRecommendProductListHtml=wx_common.render(index_recommend_product_list_Tpl,rowData);//渲染模板
				result+=indexRecommendProductListHtml;
			}
			$('.index_product').append(result);
			wx_common.LazyloadImg();//使用图片延迟加载
			
		},
		error: function(data){
			layer.open({content: "首页获取猜你喜欢商品广告位出现问题！",skin: 'msg',time: 2 });
		}
	});
	
});