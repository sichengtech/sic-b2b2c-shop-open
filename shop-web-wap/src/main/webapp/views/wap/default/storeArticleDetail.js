$(function(){
	/**
	 * 重新加载js(导致问题：登录切换)
	 */
	/*$(document).on("pageAnimationEnd", function(e, pageId, $page) {
		if(pageId == "storeArticleDetail-page") {
			$("script[src='"+ctx+"/views/wap/default/js/swiper.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/storeNav.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/storeArticleDetail.js']").remove();
	  		var head = $("head");
	        var script1 = $("<script>");
	        $(script1).attr('type','text/javascript');
	        $(script1).attr('src',ctx+'/views/wap/default/storeNav.js'); 
	        var script2 = $("<script>");
	        $(script2).attr('type','text/javascript');
	        $(script2).attr('src',ctx+'/views/wap/default/storeArticleDetail.js'); 
	        $(head).append(script1);
	        $(head).append(script2);
	  	}
	});*/
	
	/**
	 * 获取店铺详情
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/store/article/one.htm?saId='+saId,
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
			var storeArticle = data.data;
			$(".weui-weixin-title").html(storeArticle.title);//标题
			$(".weui-weixin-info").find('em').html(storeArticle.createDate);//时间
			$(".weui-weixin-content").html(storeArticle.content);//内容
		},
		error: function(data){
			layer.open({content: "获取店铺文章详情报错",skin: 'msg',time: 2 });
		}
	});
});