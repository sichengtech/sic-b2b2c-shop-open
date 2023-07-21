$(function(){
	/**
	 *	获取发票列表数据
	 */
	$.ajax({
		type: 'GET',
		url: ctxw + '/api/v1/trade/deliver/list.htm',
		dataType: 'json',
		async: false,
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
			var noList_Tpl=$("#noList_Tpl").html();
			var tradeDeliverList_Tpl=$("#tradeDeliverList_Tpl").html();
			var result='';
			var tradeDeliverList = data.data;
			if(tradeDeliverList.length==0){
				$(".trade-invoice-list").html(noList_Tpl);
			}else{
				var deliverTypes = "";
				for (var i = 0; i < tradeDeliverList.length; i++) {
					deliverTypes+=",";
					deliverTypes+=tradeDeliverList[i].deliverType;
				}
				var dictList = wx_common.getDictLabelList("deliver_type",deliverTypes.substr(1));
				for (var i = 0; i < tradeDeliverList.length; i++) {
					for (var j = 0; j < dictList.length; j++) {
						if(tradeDeliverList[i].deliverType==dictList[j].value){
							var deliverId = tradeDeliverList[i].deliverId;
							var label = "发票类型:"+dictList[j].label;
							var companyName = '公司名称:';
							if(tradeDeliverList[i].companyName!=undefined){
								companyName+=tradeDeliverList[i].companyName;
							}
							var hbjbuyer = tradeDeliverList[i].hbjbuyer;
							var checked = '';
							if(hbjbuyer=='1'){
								checked = 'checked=checked';
							}
							var rowData={"deliverId":deliverId,"label":label,"companyName":companyName,"checked":checked};//模板数据
							var tradeDeliverListHtml=wx_common.render(tradeDeliverList_Tpl,rowData);//渲染模板
		    				result+=tradeDeliverListHtml;
						}
					}
        		}
				$(".trade-invoice-list").html(result);
			}
		},
		error: function(data){
			layer.open({content: "获取发票数据报错",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 删除发票
	 * */
	$("body").delegate(".dele-invoice","click",function(){
		$(".weui_mask").remove();
	  	$(".weui_dialog").remove();
	  	$(".weui_mask_visible").remove();
	  	$(".weui_dialog_visible").remove();
		var current=$(this);
		var deliverId=$(this).attr("deliverId");//发票id
		$.confirm("您确定要删除吗?", "确认删除?", function() {
	    	$.ajax({
	        	type: "post",
	            url:ctxw+'/api/v1/trade/deliver/delete.htm?deliverId='+deliverId,
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
					current.parent().parent().parent().remove();
					isList();
					layer.open({content: data.message,skin: 'msg',time: 2});
	            },
	            error: function(){
	            	layer.open({content: "删除发票报错",skin: 'msg',time: 2 });
	            }
	        });
		}, function() {
			//取消操作
		});
	});
	
	/**
	 * 设为默认点击事件
	 * */
	$("body").off('click','.default-invoice-input').delegate(".default-invoice-input","click",function(){
		var current=$(this);
		var deliverId=$(this).attr("for");//发票id
		var hb = $(this).find(".hbjbuyer").prop('checked');
		var hbjbuyer ="";
		if(hb){
			//由选中变成不选中
			hbjbuyer = "0";
		}else{
			//由不选中变成选中
			hbjbuyer = "1";
		}
    	$.ajax({
        	type: "post",
            url:ctxw+'/api/v1/trade/deliver/default.htm?deliverId='+deliverId+"&hbjbuyer="+hbjbuyer,
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
				$(".default-invoice-input").find("input").prop('checked',false);
				if(!hb){
					current.find("input").prop("checked",true);
				}
				layer.open({content: data.message,skin: 'msg',time: 2});
            },
            error: function(){
            	layer.open({content: "设置默认报错",skin: 'msg',time: 2 });
            }
        });
    	return false;
	});
	
	/**
	 * 判断当前是否有数据
	 */
	function isList(){
		var length = $(".trade-invoice-list .invoice-item").length;
		if(length==0){
			var noList_Tpl=$("#noList_Tpl").html();
			$(".trade-invoice-list").html(noList_Tpl);
		}
	};
	
	
	
});