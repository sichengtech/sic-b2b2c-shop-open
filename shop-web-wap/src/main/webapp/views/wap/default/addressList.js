$(function(){
	/**
	 * 获取用户的收货地址列表
	 */
	function addressList(){
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/user/userAddress/list.htm',
            dataType: 'json',
            success: function(data){
            	if(data==null || data.status!=200 || data.data=="" || data.data.length==0){
	        		return false;
	        	}
				var addressListHtml="";
				for(var i=0;i<data.data.length;i++){
					var address=data.data[i];
					var addressDetail=address.provinceName+" "+address.cityName+" "+address.districtName+" "+address.detailedAddress;
					var editUrl=ctxw+'/user/address/form.htm?addressId='+address.id;
					var addressId=address.id;
					var isDefault="";
					var entryUrl="javascript:;";
					if("1"==address.isDefault){
						isDefault="checked";
					}
					var addressData={"name":address.name,"mobile":address.mobile,
							"addressDetail":addressDetail,"editUrl":editUrl,
							"addressId":addressId,"isDefault":isDefault};
					var address_tpl=$("#address_tpl").html();
					addressListHtml+=wx_common.render(address_tpl,addressData);//渲染模板
				}
				$("#addressContent").html(addressListHtml);
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
	};
	
	/**
	 * 加载收货地址列表
	 */
	addressList();
	
	/**
	 * 删除收货地址
	 */
	$("body").delegate(".dele-address","click",function(){
		$(".weui_mask").remove();
	  	$(".weui_dialog").remove();
	  	$(".weui_mask_visible").remove();
	  	$(".weui_dialog_visible").remove();
		var deleAddress=$(this);
		var addressId=deleAddress.attr("attrId");//收货地址id
		$.confirm("您确定要删除吗?", "确认删除?", function() {
	    	$.ajax({
	        	type: "post",
	            url:ctxw+'/api/v1/user/userAddress/delete.htm?addressId='+addressId,
	            dataType: 'json',
	            success: function(data){
	            	if(data.status=="401"){
	            		wx_common.routerLogin();
                		return false;
	            	}
	            	if(data.status!="200"){
                		layer.open({content: data.message,skin: 'msg',time: 2});
    					return false;
                	}
					deleAddress.parent().parent().parent().remove();
					layer.open({content: data.message,skin: 'msg',time: 2});
	            },
	            error: function(){
	                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	            }
	        });
		}, function() {
			//取消操作
		});
	});
	
	/**
	 * 设为默认点击事件
	 * */
	$("body").off('click','.default-address-input').delegate(".default-address-input","click",function(){
		var def=$(this);
		var isDefault="";
		var checkbox = def.find("input").is(':checked');//当前地址是否是默认地址
		if(checkbox){
			isDefault="0"
		}else{
			isDefault="1"
		}
		var addressId=def.attr("for");
    	$.ajax({
        	type: "post",
            url:ctxw+'/api/v1/user/userAddress/default.htm?addressId='+addressId+"&isDefault="+isDefault,
            dataType: 'json',
            success: function(data){
            	if(data.status=="401"){
            		wx_common.routerLogin();
            		return false;
            	}
            	if(data.status!="200"){
            		layer.open({content: data.message,skin: 'msg',time: 2});
					return false;
            	}
				$(".default-address-input").find("input").prop('checked',false);
				if(!checkbox){
					def.find("input").prop('checked',true);
				}
				layer.open({content: data.message,skin: 'msg',time: 2});
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
    	return false;
	});
	
	/**
	 * 加载地址表单页面后，加载地址表单js
	 */
	$(document).off('pageAnimationEnd').on("pageAnimationEnd", function(e, pageId, $page) {
		if(pageId == "addressList") {
			$("#addressContent").html("");
			addressList();
		}
		if(pageId == "addressView") {
			//移除发票列表和发票表单的js
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverList.js']").remove();
	  		$("script[src='"+ctx+"/views/wap/default/tradeDeliverForm.js']").remove();
	  		//加载地址表单页面后，加载地址表单js
			$("script[src='"+ctx+"/views/wap/default/addressList.js']").remove();
			$("script[src='"+ctx+"/views/wap/default/addressForm.js']").remove();
	  		var head = $("head");
	        var script = $("<script>");
	        $(script).attr('type','text/javascript');
	        $(script).attr('src',ctx+'/views/wap/default/addressForm.js'); 
	        $(head).append(script);
	  	}
	});
	
});
