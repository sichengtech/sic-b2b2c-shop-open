$(function(){
	
	/**
	 * 重新加载js(导致问题：登录切换)
	 */
	/*$(document).on("pageAnimationEnd", function(e, pageId, $page) {
		if(pageId == "myTradeConsultationList-page") {
	  		$("script[src='"+ctx+"/views/wap/default/myTradeConsultationList.js']").remove();
	  		var head = $("head");
	        var script = $("<script>");
	        $(script).attr('type','text/javascript');
	        $(script).attr('src',ctx+'/views/wap/default/myTradeConsultationList.js'); 
	        $(head).append(script);
	  	}
	});*/
	
	page("");
	
	/**
	 * 咨询导航点击事件
	 * */
	$(".weui_navbar_item").click(function(){
		$(".dropload-down").remove();
		$(".consultation-items-all").css("display","none");
		$(".consultation-items-ok").css("display","none");
		$(".consultation-items-no").css("display","none");
		$(".consultation-items-all").html("");
		$(".consultation-items-ok").html("");
		$(".consultation-items-no").html("");
		$(".noList").html("");
		var attr = $(this).attr('consultation');
		$(".consultation-items-"+attr).css("display","block");
		if(attr=='all'){
			//全部咨询
			page("",attr);
		}
		if(attr=='ok'){
			//已回复咨询
			page("1",attr);
		}
		if(attr=='no'){
			//未回复咨询
			page("0",attr);
		}
		$(".weui_navbar_item").removeClass("tab-red");
		$(this).addClass("tab-red");
	});
	
	/**
	 * 下拉刷新
	 * */
	function page(isReply,attr){
		if(attr==undefined){
			attr ="all";
		}
		//页数 
		var page = 0;
		// 每页展示10个
		var size =10;
		$('.weui_panel').dropload({
			scrollArea : window,
			autoLoad : true,//自动加载
			domUp : {//下拉
				domClass   : 'dropload-up',
				domRefresh : '<div class="dropload-refresh"><i class="icon icon-114"></i>上拉加载更多</div>',
				domUpdate  : '<div class="dropload-load f15"><i class="icon icon-20"></i>释放更新...</div>',
				domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>'
			},
			loadDownFn : function(me){//加载更多
				page++;
				getConsultation(attr,isReply,page,size,me);
			}
		});
	};
    
    /**
     * 获取咨询信息
     * */
    function getConsultation(attr,isReply,pageNo,pageSize,me){
    	var result = $(".consultation-items-"+attr).html();
    	$.ajax({
        	type: 'GET',
            url:ctxw+'/api/v1/trade/consultation/page.htm?type=0'+'&isReply='+isReply+'&pageNo='+pageNo+'&pageSize='+pageSize,
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
            	var consultationList = data.data;
            	if(consultationList.length==0){
            		noConsultation();
            		return false;
            	}
            	$(".noList").addClass("hide");
            	var consultationIds = '';
    			for (var i = 0; i < consultationList.length; i++) {
    				consultationIds += "," + consultationList[i].consultationId;
    			}
    			if(consultationIds !=''){
    				consultationIds=consultationIds.substr(1);
    			}
    			var replyConsultationList = getReplyConsultationList(consultationIds);
    			var pids = '';
    			for (var i = 0; i < consultationList.length; i++) {
    				pids += "," + consultationList[i].pid;
    			}
    			if(pids !=''){
    				pids=pids.substr(1);
    			}
    			var productList = getProductList(pids);
    			var categorys = '';
    			for (var i = 0; i < consultationList.length; i++) {
    				categorys += "," + consultationList[i].category;
    			}
    			if(categorys !=''){
    				categorys=categorys.substr(1);
    			}
    			var categoryList = wx_common.getDictLabelList('trade_consultation_categor',categorys);
            	for(var i=0; i<consultationList.length; i++){
            		var disabled="disabled";
            		var answer="暂无回答";
            		var pid = "";
            		var image = "";
            		var name = "";
            		var consultationLabel = "商品咨询";
            		//该条咨询已经回复，获取回复内容
            		if(consultationList[i].isReply==1){
            			for (var j = 0; j < replyConsultationList.length; j++) {
            				if(consultationList[i].consultationId==replyConsultationList[j].replyId){
								answer=replyConsultationList[j].content;
							}
						}
            		}
            		//获取商品信息
            		for (var j = 0; j < productList.length; j++) {
            			if(consultationList[i].pid==productList[j].pid){
            				pid = productList[j].pid;
                			image = ctxfs + productList[j].image+"@200x200";
                			name = productList[j].name;
            			}
            		}
            		//获取咨询类型
            		for (var j = 0; j < categoryList.length; j++) {
            			if(consultationList[i].category==categoryList[j].value){
            				consultationLabel = categoryList[j].label;
            			}
            		}
                	var rowData={"consultationLabel":consultationLabel,"ask":consultationList[i].content,"disabled":disabled,"answer":answer,
                			"pid":pid,"image":image,"name":name,"date":consultationList[i].createDate};//模板的数据
    				var commet_Tpl=$("#consultation_Tpl").html();
    				var comment=wx_common.render(commet_Tpl,rowData);//渲染模板
    				result+=comment;
       			}
            	// 如果没有数据或已是最后一页
            	if(consultationList.length==0 || data.page.isLastPage){
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
               		$('.consultation-items-'+attr).html(result);  
               		wx_common.LazyloadImg();//使用图片延迟加载
	                // 每次数据加载完，必须重置
	                me.resetload();
            	},1000);
            },
            error: function(xhr, type){
            	layer.open({content: "获取咨询数据报错",skin: 'msg',time: 2 });
                // 即使加载出错，也得重置
                me.resetload();
            }
        });
    }
    
    /**
     * 没有咨询信息
     * */
    function noConsultation(){
    	$(".noList").removeClass("hide");
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	$(".dropload-down").remove();
    	$(".weui_panel").css("margin-bottom","auto");
    }
    
    /**
     * 获取咨询回复信息
     * */
    function getReplyConsultationList(consultationIds){
    	var replyConsultationList="";
    	$.ajax({
        	type: 'GET',
            url:ctxw+'/api/v1/trade/consultation/list.htm?consultationIds='+consultationIds+'&type=1',
            async: false,
            dataType: 'json',
            success: function(data){
            	if(data.status==200 && data.data!=null){
            		replyConsultationList=data.data;
            	}
            }
    	});
    	return replyConsultationList;
    }
    
    /**
	 * 获取单个商品
	 * @param pids	多个商品id
	 * @returns
	 */
	function getProductList(pids){
		var productList = '';
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/list.htm?pids='+pids,
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data.status==200){
	        		productList = data.data.productList;
	        	}
	        }
		});
		return productList;
	}
	
});