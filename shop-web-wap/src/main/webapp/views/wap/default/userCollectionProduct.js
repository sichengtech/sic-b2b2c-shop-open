$(function(){
	/**
	 * 获取失效商品数
	 */
	userCollectionProductInvalidCount();
	
	function userCollectionProductInvalidCount(){
		$.ajax({						
			type: 'GET',
			url: ctxw + '/api/v1/user/collectionProduct/invalidCount.htm',
			dataType: 'json',
			async:false, 
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
				var data = data.data;
				if(data==0){
					$(".weui_cells_btn a").remove("bleachNow");
				}
				$(".weui_cells_div").find("span").html(data);
			},
			error: function(data){
				layer.open({content:"获取失效商品数报错",skin: 'msg',time: 2 });
			}
		});
	};
	
	/**
	 * 管理 
	 */
	$(".administration").click(function(){
		//按钮的切换
		$(this).css('display','none');
		$(".administrationOK").css('display','block');
		$(".administration_footer").removeAttr("style"); 
		//多选框的出现
		$(".weui_check_label").css('display','block');
	}); 
	
	/**
	 * 完成 
	 */
	$(".administrationOK").click(function(){
		//按钮的切换
		$(this).css('display','none');
		$(".administration").css('display','block');
		$(".administration_footer").css('display','none');
		//多选框的隐藏
		$(".weui_check_label").css('display','none'); 
	}); 
	
	/**
	 * 点击全选 
	 */
	$(".collectionProductAll").click(function(){
		var isAllChecked = $(this).find('input').prop('checked'); 
		if(isAllChecked){
			//全选状态
			$(this).find('input').prop('checked',false);
			$(".collectionProduct").find('input').prop('checked',false);
		}else{
			//不是全选状态
			$(this).find('input').prop('checked',true);
			$(".collectionProduct").find('input').prop('checked',true);
		}
		return false;
	});
	
	/**
	 * 点击每个全选 
	 */
	$("body").delegate(".collectionProduct","click",function(){
		var isChecked = $(this).find('input').prop('checked'); 
		if(isChecked){
			//由选中到不选中
			$(".collectionProductAll").find('input').prop('checked',false);
			$(this).find('input').prop('checked',false);
		}else{
			$(this).find('input').prop('checked',true);
			//由不选中到选中
			var isCheckedAllCount = 0;
			$(".collectionProduct").find('input').each(function(){
				if($(this).prop('checked')){
					isCheckedAllCount++;
				}
			});
			if($(".collectionProduct").find('input').length==isCheckedAllCount){
				$(".collectionProductAll").find('input').prop('checked',true);
			}else{
				$(".collectionProductAll").find('input').prop('checked',false);
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
	    	var isAllChecked = $(".collectionProductAll").find('input').prop('checked'); 
			if(isAllChecked){
				//全选状态
				$(".collectionProductAll").find('input').prop('checked',true);
				$(".collectionProduct").find('input').prop('checked',true);
			}else{
				//不是全选状态
				$(".collectionProductAll").find('input').prop('checked',false);
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
    	        var collectionProduct_Tpl=$("#collectionProduct_Tpl").html();
    	        var undercarriageCollectionProduct_Tpl=$("#undercarriageCollectionProduct_Tpl").html();
            	$.ajax({
                	type: 'GET',
                	url:ctxw+'/api/v1/user/collectionProduct/list.htm?pageNo='+pageNo+'&pageSize='+pageSize,
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
                    	var collectionProductList = data.data;
                    	if(collectionProductList.length==0){
                    		noList();
                    		return false;
                    	}
                    	$(".noList").addClass("hide");
                		for (var i = 0; i < collectionProductList.length; i++) {
                			if(collectionProductList[i].status=='0'){
                				//下架
                				var rowData={"collectionId":collectionProductList[i].collectionId,"image":ctxfs+collectionProductList[i].image+"@200x200","pictureName":collectionProductList[i].pictureName};//模板数据
                				var collectionProductListHtml=wx_common.render(undercarriageCollectionProduct_Tpl,rowData);//渲染模板
                				result+=collectionProductListHtml;
                			}
                			if(collectionProductList[i].status=='1'){
                				//上架
                				var rowData={"collectionId":collectionProductList[i].collectionId,"pid":collectionProductList[i].pid,"image":ctxfs+collectionProductList[i].image+"@200x200","pictureName":collectionProductList[i].pictureName,"picturePrice":collectionProductList[i].picturePrice};//模板数据
                				var collectionProductListHtml=wx_common.render(collectionProduct_Tpl,rowData);//渲染模板
                				result+=collectionProductListHtml;
                			}
    					}
                    	// 如果没有数据
            			if(collectionProductList.length==0 || data.page.isLastPage){
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
                    	layer.open({content: "获取收藏商品数据失败",skin: 'msg',time: 2 });
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            }
        }); 
    }
    
    /**
	 * 立即清理
	 * */
    $(".bleachNow").click(function(){
    	var count = $(this).parent().parent().find(".weui_cells_tit").find("span").html();
    	if(count=="0"){
    		layer.open({content: "当前无下架商品",skin: 'msg',time: 2});
    		return;
    	}
    	$.confirm("您确定要清理下架商品吗?", "确认清理?", function() {
    		$.ajax({						
    			type: 'GET',
    			url: ctxw + '/api/v1/user/collectionProduct/cancel.htm?isAll=true',
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
    				$(".collectionProduct").find('.undercarriage').each(function(){
    					$(this).parent().parent().parent().remove();
    				});
    				layer.open({content: data.message,skin: 'msg',time: 2 });
    				userCollectionProductInvalidCount();
    				//加载下一页面
    	    		if($(".collectionProduct").length==0){
    	    			$(".dropload-down").remove();
    					page();
    				}
    			},
    			error: function(data){
    				layer.open({content: "清理收藏商品失败",skin: 'msg',time: 2 });
    			}
    		});
		}, function() {
			//取消操作
		});
    });
    
    /**
	 * 删除
	 * */
    $(".deleteCollection").click(function(){
    	var collectionIds=[];
		$(".collectionProduct").find('input').each(function(){
			if($(this).prop('checked')){
				collectionIds.push($(this).val());
			}
		});
		if(collectionIds.length==0){
			layer.open({content: "请选要删除的择商品",skin: 'msg',time: 2 });
			return false;
		}
    	$.confirm("您确定要删除商品吗?", "确认删除?", function() {
    		$.ajax({						
    			type: 'GET',
    			url: ctxw + '/api/v1/user/collectionProduct/cancel.htm?collectionIds='+collectionIds+"&isAll=false",
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
    				$(".collectionProduct").find('input').each(function(){
    					if($(this).prop('checked')){
    						$(this).parent().parent().parent().remove();
    					}
    				});
    				layer.open({content: data.message,skin: 'msg',time: 2 });
    				userCollectionProductInvalidCount();
    				//加载下一页面
    	    		if($(".collectionProduct").length==0){
    	    			$(".dropload-down").remove();
    					page();
    				}
    			},
    			error: function(data){
    				layer.open({content: "删除收藏商品失败",skin: 'msg',time: 2 });
    			}
    		});
		}, function() {
			//取消操作
		});
    });
    
    /**
     * 无收藏商品
     * @returns
     */
    function noList(){
    	$(".noList").removeClass("hide");
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	$(".weui_cells_div").remove();
    	$(".dropload-down").remove();
    }
    
	
});