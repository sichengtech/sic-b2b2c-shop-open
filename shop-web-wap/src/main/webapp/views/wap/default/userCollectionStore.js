$(function(){
	//管理
	$(".administration").click(function(){
		//按钮的切换
		$(this).css('display','none');
		$(".administrationOK").css('display','block');
		$(".administration_footer").removeAttr("style"); 
		//多选框的出现
		$(".weui_check_label").css('display','block');
	}); 
	
	//完成
	$(".administrationOK").click(function(){
		//按钮的切换
		$(this).css('display','none');
		$(".administration").css('display','block');
		$(".administration_footer").css('display','none');
		//多选框的隐藏
		$(".weui_check_label").css('display','none'); 
	}); 
	
	//点击全选
	$(".collectionStoreAll").click(function(){
		var isAllChecked = $(".collectionStoreAll").find('input').prop('checked'); 
		if(isAllChecked){
			//全选状态
			$(this).find('input').prop('checked',false);
			$(".collectionStore").find('input').prop('checked',false);
		}else{
			//不是全选状态
			$(this).find('input').prop('checked',true);
			$(".collectionStore").find('input').prop('checked',true);
		}
		return false;
	});
	
	//点击每个选中
	$("body").delegate(".collectionStore","click",function(){
		var isChecked = $(this).find('input').prop('checked'); 
		if(isChecked){
			//由选中到不选中
			$(".collectionStoreAll").find('input').prop('checked',false);
			$(this).find('input').prop('checked',false);
		}else{
			$(this).find('input').prop('checked',true);
			//由不选中到选中
			var isCheckedAllCount = 0;
			$(".collectionStore").find('input').each(function(){
				if($(this).prop('checked')){
					isCheckedAllCount++;
				}
			});
			if($(".collectionStore").find('input').length==isCheckedAllCount){
				$(".collectionStoreAll").find('input').prop('checked',true);
			}else{
				$(".collectionStoreAll").find('input').prop('checked',false);
			}
		}
		return false;
	});
	
	/**
	 * 判断是否为全选状态
	 * */
    function isAllChecked(){
		var isDisplayChecked = $(".administration")[0].style.display;
		if(isDisplayChecked=='block'){
			$(".weui_check_label").css('display','none'); 
		}else{
			$(".weui_check_label").css('display','block'); 
	    	var isAllChecked = $("input[name='collectionStoreAll']").prop('checked'); 
			if(isAllChecked){
				//全选状态
				$(".collectionStoreAll").find('input').prop('checked',true);
				$(".collectionStore").find('input').prop('checked',true);
			}else{
				//不是全选状态
				$(".collectionStoreAll").find('input').prop('checked',false);
			}
			
		}
		return false;
    }
	
	/**
	 * 下拉刷新
	 * */
    page();
    
    function page(){
	    //页数 
	   	var pageNo = 0;
	    // 每页展示多少个
	    var pageSize = 10;
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
	        	pageNo++;
	        	/* window.history.pushState(null, document.title, window.location.href); */
		        var result = '';
		        var collectionStore_Tpl=$("#collectionStore_Tpl").html();
	        	$.ajax({
	            	type: 'GET',
	                url: ctxw+'/api/v1/user/collectionStore/list.htm?pageNo='+pageNo+'&pageSize='+pageSize,
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
	                	var collectionStoreList = data.data;
	                	if(collectionStoreList.length==0){
	                		noList();
	                		return false;
	                	}
	                	$(".noList").addClass("hide");
	                	var storeIds = '';
	        			for (var i = 0; i < collectionStoreList.length; i++) {
	        				storeIds += "," + collectionStoreList[i].storeId;
	        			}
	        			if(storeIds !=''){
	        				storeIds=storeIds.substr(1);
	        			}
	        			var storeList = getStoreList(storeIds);
	            		for (var i = 0; i < collectionStoreList.length; i++) {
	            			var storeId = collectionStoreList[i].storeId;
	            			for (var j = 0; j < storeList.length; j++) {
	            				if(storeId==storeList[j].storeId){
	            					var logo = ctxfs + storeList[j].logo+"@150x75";
	                    			var name = storeList[j].name;
	                    			var rowData={"collectionStoreId":collectionStoreList[i].collectionStoreId,"storeId":storeId,"logo":logo,"name":name};//模板数据
	                				var collectionStoreListHtml=wx_common.render(collectionStore_Tpl,rowData);//渲染模板
	                				result+=collectionStoreListHtml;
	            				}
							}
	            		}
	                	// 如果没有数据
	            		if(collectionStoreList.length==0 || data.page.isLastPage){
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
	                   		isAllChecked();
	                   		wx_common.LazyloadImg();//使用图片延迟加载
	                   		// 每次数据加载完，必须重置
		                    me.resetload();
		                },1000);
	                },
	                error: function(xhr, type){
	                	layer.open({content:"获取收藏店铺数据报错",skin: 'msg',time: 2 });
	                }
	            });
	        }
	    });
    };
    
    /**
	 * 删除
	 * */
    $(".deleteCollection").click(function(){
    	var collectionIds=[];
		$(".collectionStore").find('input').each(function(){
			if($(this).prop('checked')){
				collectionIds.push($(this).val());
			}
		});
		if(collectionIds.length==0){
			layer.open({content: "请选要删除的择店铺",skin: 'msg',time: 2 });
			return false;
		}
    	$.confirm("您确定要删除店铺吗?", "确认删除?", function() {
    		$.ajax({						
    			type: 'GET',
    			url: ctxw + '/api/v1/user/collectionStore/cancel.htm?collectionIds='+collectionIds,
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
    				$(".collectionStore").find('input').each(function(){
    					if($(this).prop('checked')){
    						$(this).parent().parent().parent().remove();
    					}
    				});
    				layer.open({content: data.message,skin: 'msg',time: 2 });
    				if($(".collectionStore").length==0){
    					$(".dropload-down").remove();
    					page();
    				}
    			},
    			error: function(data){
    				layer.open({content:"删除收藏店铺报错",skin: 'msg',time: 2 });
    			}
    		});
		}, function() {
			//取消操作
		});
    });
    
    /**
     * 获取多个店铺列表
     */
    function getStoreList(storeIds){
    	var storeList = "";
    	$.ajax({						
			type: 'GET',
			url: ctxw + '/api/v1/store/list.htm?storeIds='+storeIds,
			async: false,
			dataType: 'json',
			success: function(data){
				if(data.status==200){
					storeList=data.data;
	        	}
			},
		});
    	return storeList;
    };
    
    /**
     * 无收藏店铺
     * @returns
     */
    function noList(){
    	$(".noList").removeClass("hide");
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	$(".dropload-down").remove();
    }
	
});