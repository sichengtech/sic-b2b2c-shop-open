$(function(){
	
	/**
	 * 重新加载js(导致问题：登录切换)
	 */
	/*$(document).on("pageAnimationEnd", function(e, pageId, $page) {
		if(pageId == "storeArticleList-page") {
			$("script[src='"+ctx+"/views/wap/default/js/swiper.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/storeNav.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/storeArticleList.js']").remove();
	  		var head = $("head");
	        var script1 = $("<script>");
	        $(script1).attr('type','text/javascript');
	        $(script1).attr('src',ctx+'/views/wap/default/storeNav.js'); 
	        var script2 = $("<script>");
	        $(script2).attr('type','text/javascript');
	        $(script2).attr('src',ctx+'/views/wap/default/storeArticleList.js'); 
	        $(head).append(script1);
	        $(head).append(script2);
	  	}
	});*/
	
	/**
	 * 下拉刷新
	 * */
    //页数 
   	var pageNo = 0;
    // 每页展示多少个
    var pageSize = 10;
    $('.weui_panel').dropload({
    	scrollArea : window,
        autoLoad : true,//自动加载
        domUp : {//下拉
            domClass   : 'dropload',
            domRefresh : '<div class="dropload-refresh"><i class="icon icon-114"></i>上拉加载更多</div>',
            domUpdate  : '<div class="dropload-load f15"><i class="icon icon-20"></i>释放更新...</div>',
            domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>'
        },
        loadDownFn : function(me){//加载更多
        	pageNo++;
	        var result = '';
	        var storeArticle_Tpl=$("#storeArticle_Tpl").html();
        	$.ajax({
            	type: 'GET',
                url:ctxw+'/api/v1/store/article/page.htm?pageNo='+pageNo+'&pageSize='+pageSize+'&storeId='+storeId,
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
                	var storeArticleList = data.data;
                	if(storeArticleList.length==0){
                		noList();
                		return false;
                	}
                	$(".noList").addClass("hide");
                	for (var i = 0; i < storeArticleList.length; i++) {
            			var rowData={"saId":storeArticleList[i].saId,"storeId":storeArticleList[i].storeId,"title":storeArticleList[i].title,"content":storeArticleList[i].content,"createDate":storeArticleList[i].createDate};//模板数据
        				var storeArticleListHtml=wx_common.render(storeArticle_Tpl,rowData);//渲染模板
        				result+=storeArticleListHtml;
					}
                	// 如果没有数据
            		if(storeArticleList.length==0 || data.page.isLastPage){
                   		// 锁定
                   		me.lock();
                    	// 无数据
                    	me.noData();
                    	setTimeout(function(){
                    		$(".dropload-noData").parent().fadeOut("slow");
                    	},2000);
                	}
                	// 为了测试，延迟1秒加载
            		setTimeout(function(){
                   		$('.weui_panel_bd').append(result); 
                   		wx_common.LazyloadImg();//使用图片延迟加载
                   		// 每次数据加载完，必须重置
	                    me.resetload();
	                },1000);
                },
                error: function(xhr, type){
                	layer.open({content: "获取店铺动态数据报错",skin: 'msg',time: 2 });
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        }
    });
    
    /**
     * 无店铺动态
     */
    function noList(){
    	$(".storeArticle-body .content").css("padding-bottom","0rem;");
    	$(".noList").removeClass("hide");
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	$(".dropload-down").remove();
    }
});