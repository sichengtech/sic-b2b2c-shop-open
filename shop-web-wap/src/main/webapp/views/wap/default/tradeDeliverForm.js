$(function(){
	
	var dictListArray = [];
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/sys/dict/list.htm?type=deliver_type',
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
			var dictList = data.data;
			for (var i = 0; i < dictList.length; i++) {
				var map = {}; 
				map['title'] = dictList[i].label;
				map['value'] = dictList[i].value;
				dictListArray.push(map);
			}
			return dictListArray;
		},
		error: function(data){
			layer.open({content: "获取发票类型报错",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 发票类型
	 */
	$("body").delegate("#deliverType","click",function(){
		$("#deliverType").select({
			title: "选择发票类型",
			items: dictListArray,
		});
	});
	
	var deliverId=$("#deliverId").val();//发票id
	if(deliverId!=null && deliverId!=""){
		/**
		 * 获取用户的发票信息
		 */
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/trade/deliver/one.htm?deliverId='+deliverId,
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
				var tradeDeliver=data.data;
				$("#deliverId").val(tradeDeliver.deliverId);
				$("#deliverType").val(wx_common.getDictLabel("deliver_type",tradeDeliver.deliverType));
				$("#deliverType").attr("data-values",tradeDeliver.deliverType);
				$("#companyName").val(tradeDeliver.companyName);
				$("#axpayerIdentityNumber").val(tradeDeliver.axpayerIdentityNumber);
				$("#openingBank").val(tradeDeliver.openingBank);
				$("#accountNumber").val(tradeDeliver.accountNumber);
				$("#address").val(tradeDeliver.address);
				$("#phone").val(tradeDeliver.phone);
				if("1"==tradeDeliver.hbjbuyer){
					$("#isDefault").prop('checked',true);
				}
            },
            error: function(){
            	layer.open({content: "获取用户发票信息报错",skin: 'msg',time: 2 });
            }
        });
	}
	
	/**
	 * 提交表单
	 */
	$("body").off('click','#formSubmitBtn').delegate("#formSubmitBtn","click",function(){
		var url=ctxw+'/api/v1/trade/deliver/save.htm';
		if(deliverId!=null && deliverId!=""){
			url=ctxw+'/api/v1/trade/deliver/edit.htm';
		}
		var $form = $("#invoice_form");
		var deliverType = $("#deliverType").attr("data-values");
		$form.form();
		$form.validate(function(error){
			if(error){
				
			}else{
	        	$.ajax({
	            	type: 'post',
	                url:url,
	                data: $("#invoice_form").serialize()+"&deliverType="+deliverType,
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
	    				layer.open({content: data.message,skin: 'msg',time: 2});
	    				$.router.back();
	                },
	                error: function(){
	                	layer.open({content: "保存发票信息报错",skin: 'msg',time: 2 });
	                }
	            });
			}
		});
	});
	
});
