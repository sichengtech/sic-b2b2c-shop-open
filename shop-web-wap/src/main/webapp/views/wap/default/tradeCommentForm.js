$(function(){
	/**
	 * 评分js
	 */
	$(".comment-form-body").delegate(".weui-rater a","mouseover",function(){
		var thisL = $(this).index();
  		for(var i = 0;i < thisL;i++){
  			$(this).parent().find("a").eq(i).addClass('checked');
  		}
  		for(var j = thisL; j < 5;j++){
  			$(this).parent().find("a").eq(j).removeClass('checked');
  		}
  		$(this).addClass('checked');
  		$(this).parent().siblings(".score_number").html(thisL+1+"分");
  		$(this).siblings("input").val(thisL+1);
	});
	
	/**
	 * 设为默认点击事件
	 */
	$(".comment-form-body").delegate(".comment_grade_input","click",function(){
		$(this).parent().find("input").removeAttr("checked");
		$(this).find("input").attr("checked","checked");
		return false;
	});
	
	/**
	 * 提交表单
	 */
	var $form = $("#form");
	$form.form();
	$("#formSubmitBtn").on("click", function(){
		$form.validate(function(error){
			if(error){
				return false;
			}
			layer.open({type: 2});
			var flag=true;
			$(".weui_panel input.serverIds").each(function(){
				var serverIds=$(this).val();
				if(serverIds=="" || serverIds==null){
					return true;
				}
				var $imgPath_input=$(this).siblings("input.imgPath");
				flag=weixin.downloadImg(serverIds,$imgPath_input);
				if(!flag){
					return false;
				}
			});
			if(!flag){
				layer.open({content:data.message==""?'下载图片发生错误':data.message,skin: 'msg',time: 2});
				return false;
			}
			var _data=$("#form").serialize();
			$.ajax({
				type: 'POST',
				url: ctxw + '/api/v1/trade/comment/save.htm',
				data:_data,
				dataType: 'json',
				async: false,
				success: function(data){
					if(data==null || data.status==null){
						layer.open({content:'发布评价发生错误',skin: 'msg',time: 2});
						return false;
					}
					if(data.status=='401'){
						wx_common.routerLogin();
						return false;
					}
					if(data.status!='200'){
						layer.open({content:data.message==""?'发布评价发生错误':data.message,skin: 'msg',time: 2});
						return false;
					}
					layer.closeAll();
					layer.open({content:'评价成功',skin: 'msg',time: 2});
					//var uid=usm.getUserMain().uid;
					window.location.href=ctxw+"/trade/myComment/list.htm";
				}
			});
		});
	});
	
	/**
     * 根据订单id获取订单详情列表
     * */
    (function(){
    	if(orderId==null){
    		return;
    	}
    	$.ajax({
    		url:ctxw+"/api/v1/trade/orderItem/list.htm",
    		type:'GET',
    		data:{"orderIds":orderId},
    		dataType:'json',
    		success:function(data){
    			if(data==null || data.data==null || data.status==null){
    				return;
    			}
    			if(data.status=='401'){
    				wx_common.routerLogin();
    				return;
    			}
    			if(data.status!='200'){
    				layer.open({content: data.message!=""?data.message:'获取订单失败',skin: 'msg',time: 2});
    				return;
    			}
    			var tradeOrderItemList=data.data;
    			var commentPanelHtml="";
    			var addComment="";
    			//追评：隐藏商品评分和店铺评分
    			if(isAddComment=='1'){
    				addComment="hide";
    				$(".store_score , .service-score-hd , .delivery-score-hd").addClass("hide");
    			}
				for(var j=0;j<tradeOrderItemList.length;j++){
					var orderItem=tradeOrderItemList[j];
					var commentPanelData={"productImg":ctxfs+orderItem.thumbnailPath+"@200x200","productName":orderItem.name,
						"productId":orderItem.pid,"skuId":orderItem.skuId,"addComment":addComment};//模板的数据
					var comment_panel_Tpl=$("#comment_panel_Tpl").html();
					commentPanelHtml+=wx_common.render(comment_panel_Tpl,commentPanelData);//渲染模板
				}
				$(".comment-form-body .header-default-box").after(commentPanelHtml);
				wx_common.LazyloadImg();//使用图片延迟加载
    		}
    	});
    })();
    
    /**
     * 图片的点击事件（调用微信的选择图片和上传图片的接口）
     * 通过ready接口处理成功验证，config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后 
     * */
	wx.ready(function(){
		//得到上传图片按钮 21 
		$("body").delegate(".addCommentImg",'click',function(e){
			var images = {localId:[],serverId:[]};
			//最多可上传图片图片数
			var count=5-$(this).siblings(".comment_img").length;
			//存放图片id的input
			var $serverIds_input=$(this).parents(".weui_panel").find("input.serverIds");
			var $comment_imgs=$(this);
			//显示已上传图片个数的span
			var $commentImgcount=$(this).find("span.commentImgcount");
			//获取图片id
			var serverIds=$serverIds_input.val();
			//调用 拍照或从手机相册中选图接口
			wx.chooseImage({
				count: count,//最多可上传5张
				success: function(res) {
					var comment_img_Tpl=$("#comment_img_Tpl").html();
					var commentImgHtml="";
					//用js模板拼接图片到页面中
					for(var i=0;i<res.localIds.length;i++){
						var commentImgData={"imgUrl":res.localIds[i]};//模板的数据
						commentImgHtml+=wx_common.render(comment_img_Tpl,commentImgData);//渲染模板
					}
					$comment_imgs.before(commentImgHtml);
					//已上传图片的数量
					var validCount=$comment_imgs.siblings(".comment_img").length;
					$commentImgcount.text(validCount+" / "+5);
					//超过5张隐藏上传按钮
					if(validCount==5){
						$commentImgcount.parent().addClass("hide");
					}
					//调用上传图片接口
					images.localIds= res.localIds;
					var i = 0; var length = images.localIds.length;//循环上传多个图片
					  var upload = function() {
						  wx.uploadImage({
							  localId:images.localIds[i],
							  success: function(res) {
								  serverIds+=res.serverId+",";
								  images.serverId.push(res.serverId);
								  $serverIds_input.val(serverIds);
                                 //如果还有照片，继续上传
                                 i++;
                                 if (i < length) {
                                     upload();
                                 }
                             }
                         });
                     };
                     upload();
				 }
			});
		});
	});
	
	/**
	 * 删除图片
	 * */
	$(".comment-form-body").delegate(".icon-guanbi2fill","click",function(){
		//移除上传框的hide类
		$(this).parent().siblings(".addCommentImg").removeClass("hide");
		var index=$(this).parent().index();
		var serverIds=$(this).parents(".weui_panel").find("input.serverIds").val();
		var serverIdss=serverIds.split(",");
		var serverIdsNew="";
		for(var i=0;i<serverIdss.length;i++){
			if(i!=index){
				serverIdsNew+=serverIdss[i]+",";
			}
		}
		$(this).parents(".weui_panel").find("input.serverIds").val(serverIdsNew);
		var text="添加图片";
		var imgCount=$(this).parent().parent().find(".comment_img").length-1;
		if(imgCount!=0){
			text=imgCount+" / "+5;
		}
		$(this).parent().parent().find(".commentImgcount").text(text);
		$(this).parent().remove();
	});
	
}); 