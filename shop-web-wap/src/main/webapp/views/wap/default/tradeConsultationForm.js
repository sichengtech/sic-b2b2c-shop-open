$(function(){
	
	/**
	 * 咨询类型点击事件
	 **/
	$("body").delegate(".consultation-type-span span","click",function(){
		$(".consultation-type-span").find("span").removeClass("cur");
		$(this).addClass("cur");
		return false;
	});
	
	/**
	 * 获取咨询类型
	 * */
	(function(){
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/sys/dict/list.htm?type=trade_consultation_categor',
	        dataType: 'json',
	        success: function(data){
	        	if(data==null || data.data=="" || data.status==""){
	        		return;
	        	}
	        	if(data.status!='200'){
	        		layer.open({content: '获取咨询类型失败',skin: 'msg',time: 3});
    				return;
    			}
	        	var dictList=data.data;
	        	var consultationHtml="";
	        	var consultation_type_Tpl=$("#consultation_type_Tpl").html();
				for(var i=0;i<dictList.length;i++){
					var className="";
					if(i==0){
						className="cur";
					}
					var consultationTypeData={"className":className,"value":dictList[i].value,"label":dictList[i].label};
					consultationHtml+=wx_common.render(consultation_type_Tpl,consultationTypeData);//渲染模板
				}
				$('.consultation-type-span').html(consultationHtml);
	        }
		});
	})();
	
	/**
	 * 根据pid查商品
	 * */
	(function(){
		if(typeof(pid)=="undefined" || pid==""){
    		return;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/one.htm?pid='+pid,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null || data.data=="" || data.status==""){
	        		return;
	        	}
	        	if(data.status!='200'){
    				return;
    			}
	        	var product=data.data;
				//商品图片
	        	$(".weui_media_appmsg").remove();
				var productImgData={"imgPath":ctxfs+product.image+"@200x200","name":product.name};
				var product_img_Tpl=$("#product_Tpl").html();
				var productImgHtml=wx_common.render(product_img_Tpl,productImgData);//渲染模板
				$('.consultation_form_panel').prepend(productImgHtml);
	        }
		});
	})();
	
	/**
	 * 提交咨询
	 * */
	$("#formSubmitBtn").unbind("click").click(function(){
		var $form = $("#form");
		$form.form();
		$form.validate(function(error){
			if(!error){
				$(this).attr("href","");
				$(this).addClass("back use-router");
				var param=$form.serialize()+"&category="+$(".consultation-type-span .cur").attr("category");
				$.ajax({
			    	type: 'POST',
			        url:ctxw+'/api/v1/trade/consultation/save.htm?'+param,
			        dataType: 'json',
			        success: function(data){
			        	if(data==null || data.status==""){
			        		layer.open({content: '发布咨询失败',skin: 'msg',time: 3});
			        		return;
			        	}
			        	//没有登录
			        	if(data.status=='401'){
			        		wx_common.routerLogin();
		    				return;
		    			}
			        	if(data.status!='200'){
			        		layer.open({content:data.message!=null?data.message:'发布咨询失败',skin: 'msg',time: 3});
			        		return;
			        	}
			        	layer.open({content: '发布咨询成功',skin: 'msg',time: 3});
			        	$.router.back();
			        }
				});
			}
		});
		return;
	});
	
});
