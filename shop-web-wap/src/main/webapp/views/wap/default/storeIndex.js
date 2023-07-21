$(function(){
	/**
	 * 重新加载js(导致问题：登录切换)
	 */
	/*$(document).on("pageAnimationEnd", function(e, pageId, $page) {
		if(pageId == "storeIndex-page") {
			$("script[src='"+ctx+"/views/wap/default/js/swiper.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/storeNav.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/storeIndex.js']").remove();
	  		var head = $("head");
	        var script1 = $("<script>");
	        $(script1).attr('type','text/javascript');
	        $(script1).attr('src',ctx+'/views/wap/default/js/swiper.js'); 
	        var script2 = $("<script>");
	        $(script2).attr('type','text/javascript');
	        $(script2).attr('src',ctx+'/views/wap/default/storeNav.js'); 
	        var script3 = $("<script>");
	        $(script3).attr('type','text/javascript');
	        $(script3).attr('src',ctx+'/views/wap/default/storeIndex.js'); 
	        $(head).append(script1);
	        $(head).append(script2);
	        $(head).append(script3);
	  	}
	});*/
	
	/**
	 * 获取轮播图片
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/store/carouselPicture/list.htm?storeId='+storeId,
		dataType: 'json',
		success: function(data){
        	var status = data.status;//状态码
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
			var storeCarouselPictureList = data.data;
			var storeCarouselPicture_Tpl=$("#storeCarouselPicture_Tpl").html();
			var result = '';
			for (var i = 0; i < storeCarouselPictureList.length; i++) {
    			var rowData={"url":storeCarouselPictureList[i].url,"picturePath":ctxfs+storeCarouselPictureList[i].picturePath+"@375x130"};//模板数据
				var storeCarouselPictureHtml=wx_common.render(storeCarouselPicture_Tpl,rowData);//渲染模板
				result+=storeCarouselPictureHtml;
			}
			$('.swiper-wrapper').append(result); 
			//加载轮播图片js
			var swiper = new Swiper('.swiper-container', {
				pagination: {
					el: '.swiper-pagination',
				},
			});
		},
		error: function(data){
			layer.open({content: "店铺首页获取轮播图片出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 热卖产品
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/product/list.htm?sid='+storeId+"&limit=4&sort=allSales&sortModedesc",
		dataType: 'json',
		success: function(data){
        	var status = data.status;//状态码
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
        	var productList = data.data.productList;
        	var result = '';
        	if(productList.length > 0){
        		for(var i=0; i<productList.length; i++){
        			var storeTypeClass = "";
        			var storeType = "";
        			if(productList[i].storeType=="1"){
        				storeTypeClass="display:none;";
        			}
        			if(productList[i].storeType=="2"){
        				storeType="旗舰店";
        			}
                	var rowData={"pid":productList[i].pid,"storeTypeClass":storeTypeClass,"storeType":storeType,"imgPath":ctxfs+productList[i].image+"@200x200","name":productList[i].name,"type":productList[i].type==2?"批":"零",
                		"price":productList[i].type==2?productList[i].minPrice2:productList[i].minPrice1,"display":productList[i].type==3?"":"hide"};//模板的数据
    				var storeProduct_Tpl=$("#storeProduct_Tpl").html();
    				var productListHtml=wx_common.render(storeProduct_Tpl,rowData);//渲染模板
    				result+=productListHtml;
       			}
        	}else{
        		var storeNoProduct_Tpl=$("#storeNoProduct_Tpl").html();
				var storeNoProductHtml=wx_common.render(storeNoProduct_Tpl,rowData);//渲染模板
				result+=storeNoProductHtml;
        	}
        	$('.hotProductList').append(result);
        	wx_common.LazyloadImg();//使用图片延迟加载
		},
		error: function(data){
			layer.open({content: "店铺首页获取热卖产品出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 推荐产品
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/product/list.htm?sid='+storeId+"&isRecommend=1&limit=4",
		dataType: 'json',
		success: function(data){
        	var status = data.status;//状态码
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
        	var productList = data.data.productList;
        	var result = '';
        	if(productList.length > 0){
        		for(var i=0; i<productList.length; i++){
        			var storeTypeClass = "";
        			var storeType = "";
        			if(productList[i].storeType=="1"){
        				storeTypeClass="display:none;";
        			}
        			if(productList[i].storeType=="2"){
        				storeType="旗舰店";
        			}
                	var rowData={"pid":productList[i].pid,"storeTypeClass":storeTypeClass,"storeType":storeType,"imgPath":ctxfs+productList[i].image+"@200x200","name":productList[i].name,"type":productList[i].type==2?"批":"零",
                		"price":productList[i].type==2?productList[i].minPrice2:productList[i].minPrice1,"display":productList[i].type==3?"":"hide"};//模板的数据
    				var storeProduct_Tpl=$("#storeProduct_Tpl").html();
    				var productListHtml=wx_common.render(storeProduct_Tpl,rowData);//渲染模板
    				result+=productListHtml;
       			}
        	}else{
        		var storeNoProduct_Tpl=$("#storeNoProduct_Tpl").html();
				var storeNoProductHtml=wx_common.render(storeNoProduct_Tpl,rowData);//渲染模板
				result+=storeNoProductHtml;
        	}
        	$('.recommendProductList').append(result);
        	wx_common.LazyloadImg();//使用图片延迟加载
		},
		error: function(data){
			layer.open({content: "店铺首页获取推荐产品出现问题！",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 新品产品
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/product/list.htm?sid='+storeId+"&limit=4",
		dataType: 'json',
		success: function(data){
        	var status = data.status;//状态码
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
        	var productList = data.data.productList;
        	var result = '';
        	if(productList.length > 0){
        		for(var i=0; i<productList.length; i++){
        			var storeTypeClass = "";
        			var storeType = "";
        			if(productList[i].storeType=="1"){
        				storeTypeClass="display:none;";
        			}
        			if(productList[i].storeType=="2"){
        				storeType="旗舰店";
        			}
                	var rowData={"pid":productList[i].pid,"storeTypeClass":storeTypeClass,"storeType":storeType,"imgPath":ctxfs+productList[i].image+"@200x200","name":productList[i].name,"type":productList[i].type==2?"批":"零",
                		"price":productList[i].type==2?productList[i].minPrice2:productList[i].minPrice1,"display":productList[i].type==3?"":"hide"};//模板的数据
    				var storeProduct_Tpl=$("#storeProduct_Tpl").html();
    				var productListHtml=wx_common.render(storeProduct_Tpl,rowData);//渲染模板
    				result+=productListHtml;
       			}
        	}else{
        		var storeNoProduct_Tpl=$("#storeNoProduct_Tpl").html();
				var storeNoProductHtml=wx_common.render(storeNoProduct_Tpl,rowData);//渲染模板
				result+=storeNoProductHtml;
        	}
    		$('.newProductList').append(result);
    		wx_common.LazyloadImg();//使用图片延迟加载
		},
		error: function(data){
			layer.open({content: "店铺首页获取新品产品出现问题！",skin: 'msg',time: 2 });
		}
	});
	
});