$(function(){
	/**
	 * 下拉刷新
	 */
    function messagedata(type){
        var page = 0;//页数 
        var size =10;// 每页展示10个
        layer.open({type: 2});
        var type =type;//消息类型
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
	            /* window.history.pushState(null, document.title, window.location.href); */
	            var result = '';
            	$.ajax({
                	type: 'GET',
                	url:ctxw+'/api/v1/user/userMessage/page.htm?pageNo='+page+'&pageSize='+size+'&type='+type,
                    dataType: 'json',
                    success: function(data){
                    	var status = data.status;//状态码
                    	if(status=="401"){
                    		wx_common.routerLogin();
                    		return false;
    	            	}
                    	if(status!="200"){
	                		layer.open({content: data.message,skin: 'msg',time: 2});
	    					return false;
	                	}
                    	var messageList = data.data;
                    	if(messageList.length==0){
                    		noList();
                    		layer.closeAll();
                    		return false;
                    	}
                    	//渲染消息模板
                    	if(messageList.length > 0){
                            for(var i=0; i<messageList.length; i++){
                            	var message=messageList[i];
                            	var rowData={"messageId":message.id,"content":message.content,"time":message.createDate};//模板的数据
                				var myMessage_Tpl=$("#myMessage_Tpl").html();
                				var myMessage=wx_common.render(myMessage_Tpl,rowData);//渲染模板
                				result+=myMessage;
                   			}
                    	}
                    	// 如果没有数据或已是最后一页
                    	if(messageList.length==0 || data.page.isLastPage){
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
                    		$('.message_type'+type).append(result);
                    		layer.closeAll();
                       		isAllChecked();
                        	// 每次数据加载完，必须重置
                        	me.resetload();
                    	},1000);
                    },
                    error: function(xhr, type){
                        layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
                        // 即使加载出错，也得重置
                        me.resetload();
                        layer.closeAll();
                    }
                });
            }
        }); 
    }
    /**
     * 页面初始化，加载消息数据
     */
    messagedata("");
    
    /**
     * 按消息类型，加载消息数据
     */
    $("body").delegate(".weui_cells .messageType","click",function(){
		$(this).siblings().find(".messageStyle").removeClass("type_style");
    	$(this).find(".messageStyle").addClass("type_style");
    	$(".dropload-down").remove();
		$('.weui_panel_bd').html("");
    	var type=$(this).attr("type");
    	$(".weui_panel_bd.hide").removeClass("hide");
		$(".weui_panel_bd").not(".message_type"+type).addClass("hide");
    	$('.noList').html("");
		//$('.noList').addClass("hide");
    	messagedata(type);
    });
	
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
	$(".myMessageAll").click(function(){
		var isAllChecked = $(this).find('input').prop('checked'); 
		if(isAllChecked){
			//全选状态
			$(this).find('input').prop('checked',false);
			$(".myMessage").find('input').prop('checked',false);
		}else{
			//不是全选状态
			$(this).find('input').prop('checked',true);
			$(".myMessage").find('input').prop('checked',true);
		}
		return false;
	});
	
	/**
	 * 点击每个全选
	 */
	$("body").delegate(".myMessage","click",function(){
		var isChecked = $(this).find('input').prop('checked'); 
		if(isChecked){
			//由选中到不选中
			$(".myMessageAll").find('input').prop('checked',false);
			$(this).find('input').prop('checked',false);
		}else{
			$(this).find('input').prop('checked',true);
			//由不选中到选中
			var isCheckedAllCount = 0;
			$(".myMessage").find('input').each(function(){
				if($(this).prop('checked')){
					isCheckedAllCount++;
				}
			});
			if($(".myMessage").find('input').length==isCheckedAllCount){
				$(".myMessageAll").find('input').prop('checked',true);
			}else{
				$(".myMessageAll").find('input').prop('checked',false);
			}
		}
		return false;
	});
	
	/**
	 * 判断是否为全选状态
	 */
    function isAllChecked(){
		var isDisplayChecked = $(".administration")[0].style.display;
		if(isDisplayChecked=='block'){
			$(".weui_check_label").css('display','none'); 
		}else{
			$(".weui_check_label").css('display','block'); 
	    	var isAllChecked = $(".myMessageAll").find('input').prop('checked'); 
			if(isAllChecked){
				//全选状态
				$(".myMessageAll").find('input').prop('checked',true);
				$(".myMessage").find('input').prop('checked',true);
			}else{
				//不是全选状态
				$(".myMessageAll").find('input').prop('checked',false);
			}
			
		}
		return false;
    }
    
    /**
	 * 删除
	 */
    $(".deleteCollection").click(function(){
    	var type=$(".type_style").parent().parent().attr("type");
		var messageIds=[];
		$(".myMessage").find('input').each(function(){
			if($(this).prop('checked')){
				messageIds.push($(this).val());
			}
		});
		if(messageIds.length==0){
			layer.open({content: "请选择要删除的消息",skin: 'msg',time: 2 });
			return false;
		}
    	$.confirm("您确定要删除消息吗?", "确认删除?", function() {
    		$.ajax({						
    			type: 'post',
    			url: ctxw + '/api/v1/user/userMessage/delete.htm?messageIds='+messageIds,
    			dataType: 'json',
    			success: function(data){
    				var status = data.status;//状态码
    				if(status=="401"){
    					wx_common.routerLogin();
                		return false;
	            	}
    				if(status!="200"){
    					layer.open({content: data.message,skin: 'msg',time: 2});
    					return false;
    				}
    				$(".myMessage").find('input').each(function(){
    					if($(this).prop('checked')){
    						$(this).parent().parent().parent().remove();
    					}
    				});
    				layer.open({content: data.message,skin: 'msg',time: 2});
    				if($(".myMessage").length==0){
    					$(".dropload-down").remove();
    					messagedata(type);
    				}
    			},
    			error: function(data){
    				layer.open({content: '删除消息出现问题！',skin: 'msg',time: 2});
    			}
    		});
		}, function() {
			//取消操作
		});
    });
    
    /**
     * 没有消息
     * */
    function noList(){
    	$(".noList").html("");
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	$(".dropload-down").remove();
    	$('.noList').css("height","76.5%");
    }
});