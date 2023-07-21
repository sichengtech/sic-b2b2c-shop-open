$(function(){
	
	/**
	 * 隐藏评价点击事件
	 * */
	$(".comment-list-body").delegate(".hide-comment-btn","click",function(){
		var commentId=$(this).attr("commentId");
		var isShow=$(this).attr("isShow");
		var $btn=$(this);
		var message1="隐藏后，该评价在商品详情页中的评价列表中不会出现。";
		var message2="确认隐藏评价?";
		if('1'==isShow){
			message1="显示后，该评价将会在商品详情页中的评价列表中显示";
			message2="确认显示评价?";
		}
		$.confirm(message1, message2, function() {
			$.ajax({
		    	type: 'POST',
		        url:ctxw+'/api/v1/trade/comment/hide.htm',
		        data:{"commentId":commentId,"isShow":isShow},
		        dataType: 'json',
		        async: false,
		        success: function(data){
		        	var msg=isShow=='0'?'隐藏评价失败':'显示评价失败';
		        	if(data==null || data.status==""){
		        		layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 3});
		        		return;
		        	}
		        	if(data.status=='401'){
		        		window.logcation.href=ctxw+"/user/login/form.htm";
		        		return;
		        	}
		        	if(data.status!='200'){
		        		layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 3});
		        		return;
		        	}
		        	var btnText=isShow=='0'?'显示评价':'隐藏评价';
		        	var isShowAttr=isShow=='0'?'1':'0';
		        	$btn.text(btnText);
		        	$btn.attr("isShow",isShowAttr);
		        }
			});
		}, function() {
			//取消操作
		});
	});
	
	/**
	 * 评论图片点击预览
	 * */
	$(".comment-list-body").delegate(".comment-img img","click",function(){
		//当前点击的第几张图
		var index=$(this).index();
        var pswpElement = document.querySelectorAll('.pswp')[0];
        var items = [];
        $(this).parent().find("img").each(function(){
        	var dataSize=$(this).attr("data-size");
        	if(typeof dataSize=="undefined"){
        		dataSize="500x500";
        	}
        	var size=dataSize.split('x');
        	var src=$(this).attr("src");
        	if(-1 != src.indexOf("@")){
        		var srcIndex=$(this).attr("src").indexOf("@");
        		src=$(this).attr("src").substring(0,srcIndex);
        	}
			var item = {
                src:src,
                w: parseInt(size[0], 10),
                h: parseInt(size[1], 10)
            };
			items.push(item);
		});
        var options = {
            history: false,
            focus: false,
            index: index,
            showAnimationDuration: 0,
            hideAnimationDuration: 0
        };
        var gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
        gallery.init();
	});
	
	/**
	 * 给图片添加尺寸属性
	 * */
	function auto_data_size(){
		$(".comment-img img").each(function() {
			var image = new Image();
			var index=$(this).attr("data-src").indexOf("@");
			var src=$(this).attr("data-src").substring(0,index);
			image.src=src;
			var img=$(this);
			image.onload = function () {
				var w = image.width;
				var h =image.height;
				img.attr("data-size",w+"x"+h);
			}
		});
	}
	
	/**
	 * 下拉刷新
	 * */
	function page(){
		//页数 
		var page = 0;
		// 每页展示10个
		var size =10;
		$('.weui_panel').dropload({
			scrollArea :window,
			autoLoad : true,//自动加载
			domUp : {//下拉
				domClass   : 'dropload-up',
				domRefresh : '<div class="dropload-refresh"><i class="icon icon-114"></i>上拉加载更多</div>',
				domUpdate  : '<div class="dropload-load f15"><i class="icon icon-20"></i>释放更新...</div>',
				domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>'
			},
			loadDownFn : function(me){//加载更多
				page++;
				var grade=$(".weui_navbar_item.tab-red").attr("grade");
				if(page==1){
					if($(".dropload-down").length>1){
						$(".dropload-down:first").remove();
					}
					$(".comment-items").html("");
					$(".comment-items.hide").removeClass("hide");
					$(".comment-items").not(".comment-items-grade"+grade).addClass("hide");
					$(".noList").html("");
				}
				initCommentDate(grade,page,size,me);
			}
		});
	}
    
    /**
	 * 加载评价数据
	 * */
	var uid=wxusm.uid();
    function initCommentDate(grade,page,size,me){
    	var result = '';
    	$.ajax({
        	type: 'GET',
            url:ctxw+'/api/v1/trade/comment/page.htm',
            data:{"uid":uid,"pageNo":page,"pageSize":size,"grade":grade,type:"0"},
            dataType: 'json',
            success: function(data){
            	if(data==null || data.data==null){
            		layer.open({content: data.message!=null?data.message:'很遗憾，出错啦，请稍后再试',skin: 'msg',time: 3});
            		return false;
            	}
            	if(data.status=='401'){
					layer.open({content: data.messge!=""?data.message:"没有登录",skin: 'msg',time: 2});
					return false;
				}
				if(data.status!='200'){
					layer.open({content:data.messge!=""?data.message:"很遗憾，出错啦，请稍后再试",skin: 'msg',time: 2});
					return false;
				}
            	var tradeCommentList=data.data;
            	if(tradeCommentList.length==0){
            		noComment();
            		return false;
            	}
            	//隐藏没有数据div
            	$('.noList').addClass("hide");
            	//多个评价id
            	var commentIds="";
            	//多个用户
            	var uids="";
            	//多个pid
            	var pids="";
            	//多个skuid
            	var skuIds="";
            	//多个追评id
            	var addCommentIds="";
            	for(var i=0; i<tradeCommentList.length; i++){
            		var comment=tradeCommentList[i];
            		commentIds+=comment.commentId+",";
            		uids+=comment.uid+",";
            		pids+=comment.pid+",";
            		skuIds+=comment.skuId+",";
           		}
            	//多个用户
            	var userList=getUserMain(uids);
            	//多个评价商品
            	var productList=getProductSpu(pids);
            	//多个商品sku
            	var skuList=getProductSku(skuIds);
            	//多个追评
            	var addCommentList=getAddComment(commentIds);
            	if(addCommentList!="" && addCommentList.length>0){
            		for(var j=0;j<addCommentList.length;j++){
            			addCommentIds+=addCommentList[j].commentId+",";
            		}
            	}
            	//多个评价和追评的图片
            	var commentImgList=getCommentImg(commentIds+addCommentIds);
            	//多个评价和追评回复
            	var replyCommentList=getCommentReply(commentIds+addCommentIds);
            	//循环拼装数据
            	for(var k=0; k<tradeCommentList.length;k++){
            		var tradeComment=tradeCommentList[k];
            		var commentId=tradeComment.commentId;
            		var username="shop商城会员";
            		if(userList!="" && userList.length>0){
            			for(var i=0;i<userList.length;i++){
            				var user=userList[i];
            				if(user.uid==tradeComment.uid && user.loginName!=""){
            					var reg = /^(.).+(.)$/g;
        	        			username=user.loginName.replace(reg, "$1***$2");
            				}
            			}
            		}
            		//sku内容
            		var skuContent="";
            		if(skuList!="" && skuList!=null && skuList.length>0){
            			for(var j=0;j<skuList.length;j++){
            				var sku=skuList[j];
            				if(sku.skuId==tradeComment.skuId){
            					skuContent=getProductSkuContent(sku);
            				}
            			}
            		}
            		
            		//商品spu内容
            		var commentProductHtml="";
            		if(productList!="" && productList!=null && productList.length>0){
            			for(var j2=0;j2<productList.length;j2++){
            				var product=productList[j2];
            				if(product.pid==tradeComment.pid){
            					//评价商品内容
                        		var commentProductData={"pid":product.pid,"productImg":ctxfs+product.image+"@200x200","productName":product.name,"price":product.minPrice};
                    			var comment_product_Tpl=$("#comment_product_Tpl").html();
                    			commentProductHtml=wx_common.render(comment_product_Tpl,commentProductData);//渲染模板
            				}
            			}
            		}
            		
            		//评价图片
            		var commentImgArray=new Array();
            		if(commentImgList!="" && commentImgList!=null && commentImgList.length>0){
            			for(var i2=0; i2<commentImgList.length;i2++){
            				var commentImg=commentImgList[i2];
            				if(commentImg.commentId==commentId){
            					commentImgArray.push(commentImg);
            				}
            			}
            		}
            		//评价图片模板
            		var commentImgHtml=getCommentImgHtml(commentImgArray);
            		
            		//评价解释
            		var replyCommentHtml="";
            		if(replyCommentList!="" && replyCommentList.length>0){
            			for(var j3=0;j3<replyCommentList.length;j3++){
            				var replyComment=replyCommentList[j3];
            				if(replyComment.replyId==commentId){
            					replyCommentHtml=getCommentExplainHtml(replyComment);
            				}
            			}
            		}
            		
            		//追评
            		var addCommentHtml="";
	        		//追评解释html
	        		var replyAddCommentHtml=""
	        		//追评图片
	        		var addCommentImgArray=new Array();
            		if(addCommentList!="" && addCommentList.length>0){
            			for(var i3=0;i3<addCommentList.length;i3++){
            				var addComment=addCommentList[i3];
            				if(addComment.replyId==commentId){
            					addCommentHtml=getAddCommentHtml(addComment,tradeComment);
            					
            					//拼装追评图片模板
            					if(commentImgList!="" && commentImgList.length>0){
            						for(var j4=0;j4<commentImgList.length;j4++){
            							var addCommentImg=commentImgList[j4];
            							if(addCommentImg.commentId == addComment.commentId){
            								addCommentImgArray.push(addCommentImg);
            							}
            						}
            					}
            					//拼装追评解释
            					if(replyCommentList!="" && replyCommentList.length>0){
            						for(var k2=0;k2<replyCommentList.length;k2++){
            							var replyComment=replyCommentList[k2];
            							if(replyComment.replyId==commentId){
            								replyAddCommentHtml=getCommentExplainHtml(replyComment);
            							}
            						}
            					}
            					break;
            				}
            			}
            		}
            		//是否显示，
	        		var btnText="隐藏";
	        		var isShow="0";
	        		var addCommentShow="";
	        		if("0"==tradeComment.isShow){
	        			btnText="显示"
	        			isShow="1";
	        		}
	        		if(addCommentHtml!="" && addCommentHtml!=null){
	        			addCommentShow="hide";
	        		}
	        		//追评图片html
	        		var addCommentImgHtml=getCommentImgHtml(addCommentImgArray);
            		var productCommentData={"username":username,"commentContent":tradeComment.content,"commentId":tradeComment.commentId,"date":tradeComment.createDate,
            				"orderId":tradeComment.orderId,"sku":skuContent,"commentImgs":commentImgHtml,"replyComment":replyCommentHtml,"addComment":addCommentHtml,
            				"addCommentImgs":addCommentImgHtml,"replyAddComment":replyAddCommentHtml,"btnText":btnText,"isShow":isShow,"addCommentShow":addCommentShow,
            				"commentProduct":commentProductHtml};
        			var commet_Tpl=$("#commet_Tpl").html();
        			result+=wx_common.render(commet_Tpl,productCommentData);//渲染模板
            	}
            	// 如果没有数据或已是最后一页
            	if(tradeCommentList.length==0 || data.page.isLastPage){
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
               		$('.comment-items-grade'+grade).append(result);  
               		wx_common.LazyloadImg();//使用图片延迟加载
               		//加载图片尺寸
               		auto_data_size();
                	// 每次数据加载完，必须重置
                	me.resetload();
            	},1000);
            	me2=me;
            },
            error: function(xhr, type){
            	layer.open({content: data.message!=null?data.message:'很遗憾，出错啦，请稍后再试',skin: 'msg',time: 3});
                // 即使加载出错，也得重置
                me.resetload();
                me2=me;
            }
        });
    }
    
    page();
    
    /**
	 * 获取评价图片
	 * */
	function getCommentImg(commentIds){
		if(commentIds==null){
			return;
		}
		var commentImgList="";
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/trade/comment/image/list.htm',
	        data:{"commentIds":commentIds},
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	commentImgList=data.data;
	        }
		});
		return commentImgList;
	}
	
	/**
	 * 拼接评价图片的模板
	 * */
	function getCommentImgHtml(commentImgList){
		var commentImgHtml=""
		if(commentImgList==null || commentImgList=="" || commentImgList.length==0){
			return commentImgHtml;
		}
    	for(var i=0;i<commentImgList.length;i++){
    		/*var img = new Image();
			//var src=ctxfs+commentImgList[i].path;
			img.src=ctxfs+commentImgList[i].path;
			var w=null;
			var h=null;
			img.onload = function () {
				w = img.width;
				h =img.height;
			}*/
			var commentImgData={"path":ctxfs+commentImgList[i].path+"@150x150"};
			var commet_img_Tpl=$("#comment_img_Tpl").html();
			commentImgHtml+=wx_common.render(commet_img_Tpl,commentImgData);//渲染模板
    	}
    	return commentImgHtml;
	}
	
	
	/**
	 * 获取评价回复
	 * */
	function getCommentReply(commentIds){
		var commentReplyList=null;
		if(commentIds==null){
			return commentReplyList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/trade/comment/page.htm',
	        data:{"replyIds":commentIds,"type":"2"},
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	commentReplyList=data.data;
	        }
		});
		return commentReplyList;
	}
	
	/**
	 * 拼接评价回复的模板
	 * */
	function getCommentExplainHtml(replyComment){
		var commentReplyHtml="";
		if(replyComment==null || replyComment==""){
			return commentReplyHtml;
		}
		var commentReplyData={"replyContent":replyComment.content};
		var commet_replay_Tpl=$("#comment_reply_Tpl").html();
		commentReplyHtml=wx_common.render(commet_replay_Tpl,commentReplyData);//渲染模板
    	return commentReplyHtml;
	}
	
	/**
	 * 获取追评
	 * */
	function getAddComment(commentIds){
		var addCommentList=null;
		if(commentIds==null){
			return addCommentList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/trade/comment/page.htm',
	        data:{"replyIds":commentIds,"type":"1"},
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	addCommentList=data.data;
	        }
		});
		return addCommentList;
	}
	
	/**
	 * 拼接追评的模板
	 * */
	function getAddCommentHtml(addComment,comment){
		var addCommentHtml="";
		if(addComment==null){
			return addCommentHtml;
		}
		var days=Math.abs(parseInt((Date.parse(addComment.createDate) - Date.parse(comment.createDate))/1000/3600/24));
		var addDays="";
		if(days==0){
			addDays="用户当天追评";
		}else{
			addDays="用户"+days+"天后评价";
		}
		var addCommentData={"addCommentContent":addComment.content,"days":addDays,"addCommentId":addComment.commentId};
		var commet_add_Tpl=$("#comment_add_Tpl").html();
		addCommentHtml=wx_common.render(commet_add_Tpl,addCommentData);//渲染模板
    	return addCommentHtml;
	}
	
	/**
	 * 获取用户信息
	 * */
	function getUserMain(uids){
		if(uids==null){
			return;
		}
		var userMainList=null;
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/user/list.htm',
	        data:{uids:uids},
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	userMainList=data.data;
	        }
		});
		return userMainList;
	}
	
	/**
	 * 获取商品spu
	 * */
	function getProductSpu(pids){
		if(pids==null){
			return;
		}
		var productSpuList=null;
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/list.htm',
	        data:{"pids":pids},
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	productSpuList=data.data.productList;
	        }
		});
		return productSpuList;
	}
	
	/**
	 * 获取商品sku
	 * */
	function getProductSku(skuIds){
		if(skuIds==null){
			return;
		}
		var productSkuList=null;
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/sku/list.htm',
	        data:{"skuIds":skuIds},
	        dataType: 'json',
	        async: false,
	        success: function(data){
	        	if(data==null || data.status!=200 || typeof(data.data)=="undefined" || data.data==null){
	        		return;
	        	}
	        	productSkuList=data.data;
	        }
		});
		return productSkuList;
	}
	
	/**
	 * 获取sku的各个规格
	 * */
	function getProductSkuContent(productSku){
		var skuContent="";
		if(productSku==null || productSku==""){
			return skuContent;
		}
		var spec1="";
		var spce2="";
		var spce3="";
		if(productSku.spec1!=null && productSku.spec1.split("_").length>1){
			spec1=productSku.spec1.split("_")[1]+" : "+productSku.spec1V;
		}
		if(productSku.spec2!=null && productSku.spec2.split("_").length>1){
			spce2=", "+productSku.spec2.split("_")[1]+" : "+productSku.spec2V;
		}
		if(productSku.spec3!=null && productSku.spec3.split("_").length>1){
			spce3=", "+productSku.spec3.split("_")[1]+" : "+productSku.spec3V;;
		}
		skuContent=spec1+spce2+spce3;
		
		return skuContent;
	}
	
	/**
	 * 评价导航点击事件
	 * */
	$(".comment_grade_navbar .weui_navbar_item").click(function(){
		$(this).siblings(".tab-red").removeClass("tab-red");
		$(this).addClass("tab-red");
		$(".dropload-down").remove();
		$(".comment-items").html("");
		var grade=$(this).attr("grade");
		$(".comment-items.hide").removeClass("hide");
		$(".comment-items").not(".comment-items-grade"+grade).addClass("hide");
		$(".noList").html("");
		$('.noList').addClass("hide");
		page();
	});
    
    /**
     * 没有评价信息
     * */
    function noComment(){
    	$(".noList").html("");
    	var noList_Tpl=$("#noList_Tpl").html();
    	$(".dropload-down").remove();
    	$('.noList').append(noList_Tpl);
    	$('.noList').removeClass("hide");
    }
});