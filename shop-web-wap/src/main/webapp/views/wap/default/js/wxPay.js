/**
 * 微信用户登录状态管理器
 * @author 张加利
 * @version 2017-12-21
 */
(function(){
	/**
	 * wx命名空间,起隔离作用，防止同名变量冲突
	 */
	if(!window.wx) {window.wx={};};
	
	/**
	 * 申请微信支付
	 * @param total_fee 支付总金额
	 * @param orderId 订单编号(多个订单编号用逗号分隔)
	 * @param productName 商品名称
	 * @param callBack 支付成功后的回调函数
	 * */
	wx.pay = function(total_fee,orderId,productName,callBack){
		//function weixinPay(total_fee,orderId,productName,callBack){
		$.post(ctxw+"/api/v1/weixin/pay.htm",
		{total_fee:total_fee,orderId:orderId,body:productName},
		function(res){
			if(res==null || res.status==""){
				return;
			}
			if(res.status=='401'){
				wx_common.routerLogin();
				return;
			}
			if(res.status!='200'){
				layer.open({content:res.message!=""?'支付失败':res.message,skin: 'msg',time: 2});
				return;
			}
			var data=$.parseJSON(res.data);
			if (typeof WeixinJSBridge == "undefined"){
				if(document.addEventListener){
					document.addEventListener('WeixinJSBridgeReady', wx.onBridgeReady(data,callBack), false);
				}else if(document.attachEvent){
					document.attachEvent('WeixinJSBridgeReady', wx.onBridgeReady(data,callBack)); 
					document.attachEvent('onWeixinJSBridgeReady', wx.onBridgeReady(data,callBack));
				}
			}else{
				wx.onBridgeReady(data,callBack);
			}
		});
	}

	/**
	 * 微信支付
	 * */
	wx.onBridgeReady = function(data,callBack){
	    WeixinJSBridge.invoke(
	       'getBrandWCPayRequest',
	       data,
	       function(res){
	        	//alert(res.err_code+res.err_desc+res.err_msg);
	        	//支付成功，重新加载数据
	            if(!res.err_msg == "get_brand_wcpay_request:ok" ) {
	            	layer.open({content:"支付失败："+res.err_desc+res.err_msg,skin: 'msg',time: 2});
	            	return;
	            }
	            layer.open({content:res.message!=""?'支付成功':res.message,skin: 'msg',time: 2});
	            //执行回调函数
	            callBack();
	        }
	    );
	}
})();

