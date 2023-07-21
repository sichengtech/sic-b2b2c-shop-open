$(function(){
	
	/**
	 * 下拉刷新
	 * */
	//页数 
	var page = 0;
	function initPage(){
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
				if(typeof(pid)=="undefined" || pid==null){
					noConsultation();
				}
				getConsultation(pid,page,size,me);
			}
		});
	}
	initPage();
    /**
     * 获取咨询信息
     * */
    function getConsultation(pid,pageNo,pageSize,me){
    	var result = '';
    	$.ajax({
        	type: 'GET',
            url:ctxw+'/api/v1/product/consultation/page.htm?pid='+pid+'&pageNo='+pageNo+'&pageSize='+pageSize,
            dataType: 'json',
            success: function(data){
            	if(data==null || data.status=="" || data.data==null){
            		layer.open({content: data.message!=null?data.message:'很遗憾，出错啦，请稍后再试',skin: 'msg',time: 3});
            	}
	        	if(data.status!='200'){
	        		layer.open({content:data.message!=null?data.message:'很遗憾，出错啦，请稍后再试',skin: 'msg',time: 3});
	        		return;
	        	}
            	var consultationList = data.data;
            	if(consultationList.length==0){
            		noConsultation();
            		return;
            	}
            	var consultationIds = '';
    			for (var i = 0; i < consultationList.length; i++) {
    				consultationIds += "," + consultationList[i].consultationId;
    			}
    			if(consultationIds !=''){
    				consultationIds=consultationIds.substr(1);
    			}
    			var categorys = '';
    			for (var i = 0; i < consultationList.length; i++) {
    				categorys += "," + consultationList[i].category;
    			}
    			if(categorys !=''){
    				categorys=categorys.substr(1);
    			}
    			var categoryList = wx_common.getDictLabelList('trade_consultation_categor',categorys);
    			var replyConsultationList = getReplyConsultationList(consultationIds);
            	for(var i=0; i<consultationList.length; i++){
            		var disabled="disabled";
            		var answer="暂无回答";
            		var consultationLabel = "商品咨询";
            		//该条咨询已经回复，获取回复内容
            		if(consultationList[i].isReply==1){
            			for (var j = 0; j < replyConsultationList.length; j++) {
							if(consultationList[i].consultationId==replyConsultationList[j].replyId){
								answer=replyConsultationList[j].content;
							}
						}
            		}
            		//获取咨询类型
            		for (var j = 0; j < categoryList.length; j++) {
            			if(consultationList[i].category==categoryList[j].value){
            				consultationLabel = categoryList[j].label;
            			}
            		}
                	var rowData={"consultationLabel":consultationLabel,"ask":consultationList[i].content,"disabled":disabled,"answer":answer,
                			"date":consultationList[i].createDate};//模板的数据
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
                		$(".dropload-down").fadeOut("slow");
                	},2000);
            	}
            	// 为了测试，延迟1秒加载
            	setTimeout(function(){
               		$('.consultation-items').append(result);  
               		wx_common.LazyloadImg();//使用图片延迟加载            
               		// 每次数据加载完，必须重置
               		me.resetload();
            	},1000);
            },
            error: function(xhr, type){
                layer.open({content: '很遗憾，出错啦，请稍后再试',skin: 'msg',time: 3});
                // 即使加载出错，也得重置
                me.resetload();
            }
        });
    }
    
    /**
     * 没有咨询信息
     * */
    function noConsultation(){
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	$(".dropload-down").remove();
    }
    
    /**
     * 获取咨询回复信息
     * */
    function getReplyConsultationList(consultationIds){
    	var replyConsultationList="";
    	$.ajax({
        	type: 'GET',
            url:ctxw+'/api/v1/product/consultation/list.htm?consultationIds='+consultationIds+'&type=1',
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
     * 进入咨询表单时，加载咨询表单的js
     * */
    $(document).on("pageAnimationEnd", function(e, pageId, $page) {
    	if(pageId == "consultatin-form-page") {
    		$("script[src='"+ctx+"/views/wap/default/tradeConsultationForm.js']").remove();
    		var head = $("head");
    		var script = $("<script>");
    		$(script).attr('src',ctx+'/views/wap/default/tradeConsultationForm.js'); 
    		$(script).attr('type','text/javascript');
    		$(head).append(script);
    	}
    	if(pageId=="consultatin-list-page"){
    		$('.consultation-items').html("");
    		page=0;
    		$(".dropload-down").remove();
    		$(".noList").html("");
    		initPage();
    	}
    });
});
