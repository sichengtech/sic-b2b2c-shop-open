$(function(){
	var orderId=$("input[name='orderId']").val();
	/**
	 * 获取订单详情
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/trade/orderItem/list.htm?orderIds='+orderId+'&count=1',
		dataType: 'json',
		success: function(data){
			if(data==null || data.status==""){
        		layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
        		return;
        	}
        	var status = data.status;//状态码
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
			var tradeOrderItem = data.data;
			if(tradeOrderItem!=null){
				$(".weui_media_appmsg_thumb").attr("src",ctxfs+tradeOrderItem.thumbnailPath+"@200x200");
			}
		},
		error: function(data){
			layer.open({content: "获取订单详情出错",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 获取收货地址
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/trade/order/one.htm?orderId='+orderId,
		dataType: 'json',
		success: function(data){
			if(data==null || data.status==""){
        		layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
        		return;
        	}
        	var status = data.status;//状态码
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
			var tradeOrder = data.data;
			$(".weui_cell_primary").find('p').html("[收货地址]:"+"&nbsp;"+tradeOrder.provinceName+tradeOrder.cityName+tradeOrder.districtName+tradeOrder.detailedAddress);
		},
		error: function(data){
			layer.open({content: "获取收货地址出错",skin: 'msg',time: 2 });
		}
	});
	
	/**
	 * 获取物流信息
	 */
	$.ajax({						
		type: 'GET',
		url: ctxw + '/api/v1/trade/logistics/info.htm?orderId='+orderId,
		dataType: 'json',
		success: function(data){
			if(data==null || data.status==""){
        		layer.open({content: data.message!=null?data.message:msg,skin: 'msg',time: 2});
        		return;
        	}
        	var status = data.status;//状态码
			if(status!="200"){
				layer.open({content: data.message,skin: 'msg',time: 2 });
				return false;
			}
			var result = data.data;
			if(!result.Success){
				layer.open({
					content: '物流信息获取失败',
					skin: 'msg',
					time: 2 //2秒后自动关闭
				});
				return false;
			}
			var firstlogistics_Tpl=$("#firstlogistics_Tpl").html();
			var lastlogistics_Tpl=$("#lastlogistics_Tpl").html();
			var logistics_Tpl=$("#logistics_Tpl").html();
			var nologistics_Tpl=$("#nologistics_Tpl").html();
			if(result.State=='0'){
				$(".weui_media_title").html("已发货");
				$(".weui_media_desc").html("运单号："+"无运单号");
				var info="";
				var rowData={"acceptStation":result.Reason};//模板数据
				var nologisticsInfoHtml=wx_common.render(nologistics_Tpl,rowData);//渲染模板
				info+=nologisticsInfoHtml;
				$(".timeline ul").append(info);
			}else{
				if(result.State=='1'){
					$(".weui_media_title").html("已取件");
				}
				if(result.State=='2'){
					$(".weui_media_title").html("在途中");
				}
				if(result.State=='3'){
					$(".weui_media_title").html("已签收");
				}
				$(".weui_media_desc").html("运单号："+result.LogisticCode);
				var Traces = result.Traces;
				if(Traces==null){
					var info="";
					var rowData={"acceptStation":result.Reason};//模板数据
					var nologisticsInfoHtml=wx_common.render(nologistics_Tpl,rowData);//渲染模板
					info+=nologisticsInfoHtml;
					$(".timeline ul").append(info);
				}else{
					for (var i = 0; i < Traces.length; i++) {
						var rowData={"acceptStation":Traces[i].AcceptStation,"acceptTime":Traces[i].AcceptTime};//模板数据
						if(i == 0){
							var info="";
							var firstlogisticsInfoHtml=wx_common.render(firstlogistics_Tpl,rowData);//渲染模板
							info+=firstlogisticsInfoHtml;
							$(".timeline ul").append(info);
						}
						if(i >0 &&  i < Traces.length-1){
							var info="";
							var logisticsInfoHtml=wx_common.render(logistics_Tpl,rowData);//渲染模板
							info+=logisticsInfoHtml;
							$(".timeline ul").append(info);
						}
						if(i==Traces.length-1){
							var info="";
							var lastlogisticsInfoHtml=wx_common.render(lastlogistics_Tpl,rowData);//渲染模板
							info+=lastlogisticsInfoHtml;
							$(".timeline ul").append(info);
						}
					}
				}
			}
		},
		error: function(data){
			layer.open({content: "获取物流信息出错",skin: 'msg',time: 2 });
		}
	});
	
});