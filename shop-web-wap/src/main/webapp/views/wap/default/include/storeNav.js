$(function(){
	
	/**
	 * 收藏或取消收藏店铺
	 */
	$(".collectionstore").click(function(){
		var collectionId = $(this).attr("isCollection");
		//是否收藏
		if(collectionId=='0'){
			//收藏店铺
			collectionStore(storeId);
		}else{
			cancleCollectionStore(collectionId);
		}
		
	});
	
	/**
	 * 收藏店铺
	 */
	function collectionStore(storeId){
		$.ajax({						
			type: 'GET',
			url: ctxw + '/api/v1/user/collectionStore/save.htm?storeId='+storeId,
			dataType: 'json',
			success: function(data){
				if(data==null || data.status==""){
					layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
					return;
				}
            	var status = data.status;//状态码
            	if(status=="401"){
            		wx_common.routerLogin();
            		return false;
            	}
				if(status!="200"){
					layer.open({content: data.message,skin: 'msg',time: 2 });
					return false;
				}
				var collectionStore=data.data;
				var collectionstore_hidden = parseInt($(".collectionstore_hidden").val())+1;
				$(".store-info").find('p').html("("+collectionstore_hidden+"人)");//店铺收藏数
				$(".store-info .collectionstore_hidden").val(collectionstore_hidden);//店铺收藏数
				$(".store-info .collectionstore span").removeClass("icon-shoucang");
        		$(".store-info .collectionstore span").addClass("icon-shoucang1");
        		$(".store-info .collectionstore").attr("isCollection",collectionStore.collectionStoreId);
				layer.open({content: data.message,skin: 'msg',time: 2 });
			},
			error: function(data){
				layer.open({content: "收藏店铺报错",skin: 'msg',time: 2 });
			}
		});
	};
	
	/**
	 * 取消收藏店铺
	 */
	function cancleCollectionStore(collectionId){
		$.ajax({						
			type: 'GET',
			url: ctxw + '/api/v1/user/collectionStore/cancel.htm?collectionIds='+collectionId,
			dataType: 'json',
			success: function(data){
				if(data==null || data.status==""){
					layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
					return;
				}
            	var status = data.status;//状态码
            	if(status=='401'){
					wx_common.routerLogin();
					return false;
				}
				if(status!='200'){
					layer.open({content: data.message,skin: 'msg',time: 2 });
					return false;
				}
				var collectionstore_hidden = parseInt($(".collectionstore_hidden").val())-1;
				$(".store-info").find('p').html("("+collectionstore_hidden+"人)");//店铺收藏数
				$(".store-info .collectionstore_hidden").val(collectionstore_hidden);//店铺收藏数
				$(".store-info .collectionstore span").removeClass("icon-shoucang1");
        		$(".store-info .collectionstore span").addClass("icon-shoucang");
        		$(".store-info .collectionstore").attr("isCollection","0");
				layer.open({content: data.message,skin: 'msg',time: 2 });
			},
			error: function(data){
				layer.open({content: "取消收藏报错",skin: 'msg',time: 2 });
			}
		});
	};
	
	/**
	 * 判断当前是否收藏店铺
	 */
	$.ajax({
    	type: 'GET',
        url:ctxw+'/api/v1/user/collectionStore/isCollection.htm?storeId='+storeId,
        dataType: 'json',
        success: function(data){
        	if(data==null || data.data==null || typeof(data.data)=="undefined"){
        		layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
        		return;
        	}
        	var collectionId=data.data;
        	if(collectionId!=null && collectionId!=""){
        		$(".store-info .collectionstore span").removeClass("icon-shoucang");
        		$(".store-info .collectionstore span").addClass("icon-shoucang1");
	        	$(".store-info .collectionstore").attr("isCollection",collectionId);
        	}else{
        		$(".store-info .collectionstore").attr("isCollection","0");
        	}
        }
	});
	
	/**
	 * 获取店铺信息修改头部
	 * 获取店铺信息修改底部
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/store/one.htm?storeId='+storeId,
		dataType: 'json',
		success: function(data){
			if(data==null || data.status!=200 || data.data==""){
				window.location.href = ctxw + "/store/error.htm"; 
        		return;
        	}
			var store = data.data;
			//导航高亮
			if(storeHighlight=='storeIndexHighlight'){
				$("#tagnav .store-nav li").removeClass("weui-state-active");
				$(".store-nav-index").addClass("weui-state-active");
			}
			if(storeHighlight=='storeArticleHighlight'){
				$("#tagnav .store-nav li").removeClass("weui-state-active");
				$(".store-nav-article").addClass("weui-state-active");
			}
			$(".store-info .logo").find('img').attr("src",ctxfs+store.logo+"@150x75");//logo
			$(".store-info .logo").find('h2').html(store.name);//店铺名
			//修改底部-联系卖家
			var storeContactTheSeller = "";
			var storeContactTheSeller_Tpl=$("#storeContactTheSeller_Tpl").html();//联系卖家模板
			var storeNameData={"title":"店铺名称:","value":store.name};
			storeContactTheSeller+=wx_common.render(storeContactTheSeller_Tpl,storeNameData);//渲染模板
			var storeAreaData={"title":"店铺地址:","value":store.provinceName+store.cityName+store.districtName};
			storeContactTheSeller+=wx_common.render(storeContactTheSeller_Tpl,storeAreaData);//渲染模板
			var storeDetailedAddressData={"title":"店铺详细地址:","value":store.detailedAddress};
			storeContactTheSeller+=wx_common.render(storeContactTheSeller_Tpl,storeDetailedAddressData);//渲染模板
			var storeTelData={"title":"店铺客服电话:","value":store.storeTel};
			storeContactTheSeller+=wx_common.render(storeContactTheSeller_Tpl,storeTelData);//渲染模板
			var storeQqData={"title":"店铺QQ:","value":store.storeQq};
			storeContactTheSeller+=wx_common.render(storeContactTheSeller_Tpl,storeQqData);//渲染模板
			var storeWechatData={"title":"店铺联系微信:","value":store.storeWechat};
			storeContactTheSeller+=wx_common.render(storeContactTheSeller_Tpl,storeWechatData);//渲染模板
			$("#popup2").find(".array-div").html(storeContactTheSeller);
		},
		error: function(data){
			layer.open({content: "获取店铺信息报错",skin: 'msg',time: 2 });
		}
	});

	/**
	 * 获取数据修改店铺头部数据
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/store/nav/count.htm?storeId='+storeId,
		dataType: 'json',
		success: function(data){
			if(data==null || data.status==""){
				layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
				return;
			}
        	var status = data.status;//状态码
        	if(status=="404"){
				 window.location.href = ctxw + "/store/error.htm"; 
				 return false;
			}
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
			$(".store-info").find('p').html("("+data.data.collectionStoreCount+"人)");//店铺收藏数
			$(".store-info .collectionstore_hidden").val(data.data.collectionStoreCount);//店铺收藏数
			$("#tagnav .store-nav-allProduct").find('span').html(data.data.productAllCount);//全部商品数
			$("#tagnav .store-nav-newProduct").find('span').html(data.data.productNewCount);//新品数
			$("#tagnav .store-nav-article").find('span').html(data.data.storeArticleCount);//店铺动态数目
		},
		error: function(data){
			layer.open({content: "获取店铺头部数报错",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 新品点击
	 */
	$("body").delegate(".store-nav-newProduct","click",function(){
		var now = new Date();//获取系统当前时间
		var year = now.getFullYear();       //年  
        var month = now.getMonth() + 1;     //月  
        var day = now.getDate();            //日  
        var hh = now.getHours();            //时  
        var mm = now.getMinutes();          //分  
        var ss = now.getSeconds();          //秒  
          
        var toDate = year + "-";  
        var toDate_first = year + "-";  
        if(month < 10){toDate += "0";toDate_first += "0";} 
        toDate += month + "-";  
        toDate_first += month + "-";  
        if(day < 10){toDate += "0";}  
        toDate += day + " ";  
        if(hh < 10){toDate += "0";}   
        toDate += hh + ":";  
        if (mm < 10){toDate += '0';}    
        toDate += mm + ":";   
        if (ss < 10){toDate += '0';}    
        toDate += ss;  
        toDate_first += "01"+" "+"00:00:00";
        location.href = ctxw + "/product/list.htm?sDate="+toDate_first+"&eDate="+toDate+"&storeId="+storeId;
	});
	
});

