$(function(){
	
	/**
	 * 查询购物车数据
	 * */
	(function(){
		layer.open({type: 2});
		$.ajax({
			type: 'GET',
	        url:ctxw+'/api/v1/trade/cart/list.htm',
	        dataType: 'json',
	        success: function(data){
	        	if(data==null || data.data==""){
	        		layer.closeAll();
	        		layer.open({content: data.message!=""?data.message:"获取购物车数据失败",skin: 'msg',time: 2});
	        		return;
	        	}
	        	//没登录
	        	if(data.status=='401'){
	        		layer.closeAll();
	        		wx_common.routerLogin();
					return;
	        	}
	        	if(data.status!='200'){
	        		layer.closeAll();
	        		layer.open({content: data.message!=""?data.message:"获取购物车数据失败",skin: 'msg',time: 2});
	        		return;
	        	}
	        	var carMap=data.data;
	        	//无数据
	        	if($.isEmptyObject(carMap)){
	        		noProduct();
	        		layer.closeAll();
	        		return;
	        	}
	        	var pids="";
        		var skuIds="";
	        	for(var key in carMap){
	        		var cartList=carMap[key];
	        		if(cartList==null || cartList==""){
	        			continue;
	        		}
	        		for(var i=0;i<cartList.length;i++){
	        			var tradCart=cartList[i];
	        			pids+=tradCart.pid+",";
	        			skuIds+=tradCart.skuId+",";
	        		}
	        	}	
	        	//根据pids获取商品spu的list	
	        	var productList=getProductSpuList(pids);
	        	//根据skuIds获取商品sku的list
	        	var productSkuList=getProductSkuList(skuIds);
	        	var cartHtml="";
	        	for(var key in carMap){
		        	var cartList=carMap[key];
		        	if(cartList==null || cartList==""){
		        		continue;
		        	}
		        	var sellerId="";
		        	var productHtml="";
		        	for(var i2=0;i2<cartList.length;i2++){
	        			var cart=cartList[i2];
	        			var productImg="";
	        			var productName="";
	        			var sku="";
	        			var pid="";
	        			var purchasingAmount=1;
	        			var stock=0;
	        			var unit="件";
	        			//循环productList
	        			if(productList!=null && productList!=""){
	        				for(var j=0;j<productList.length;j++){
	        					var product=productList[j];
	        					if(product.pid==cart.pid){
	        						pid=product.pid;
			        				productImg=ctxfs+product.image+"@200x200";
			        				productName=product.name;
			        				purchasingAmount=product.purchasingAmount;
			        				if(typeof(product.unit)!="undefined" && product.unit!=""){
			        					unit=product.unit;
			        				}
			        				break;
	        					}
	        				}
	        			}
	        			//循环productSkuList
	        			if(productSkuList!=null && productSkuList!=""){
	        				for(var k=0;k<productSkuList.length;k++){
	        					var productSku=productSkuList[k];
	        					if(productSku.skuId==cart.skuId){
	        						sku=getProductSkuContent(productSku);
		        					stock=productSku.stock;
		        					break;
	        					}
	        				}
	        			}
	        			//是否下架
	        			if(cart.isOffShelf){
	        				var cartOffSaleProductData={"productImg":productImg,"productName":productName,"cartId":cart.cartId};
							var cart_offSale_product_tpl=$("#cart_offSale_product_tpl").html();
							productHtml+=wx_common.render(cart_offSale_product_tpl,cartOffSaleProductData);//渲染模板
	        			}else if(0==stock){
	        				//无货
	        				var cartOffSaleProductData={"productImg":productImg,"productName":productName,"cartId":cart.cartId,"sku":sku,"price":cart.price,
	        					"count":cart.count,"purchasingAmount":purchasingAmount,"stock":stock,"unit":unit};
							var cart_offSale_product_tpl=$("#cart_noGoods_product_tpl").html();
							productHtml+=wx_common.render(cart_offSale_product_tpl,cartOffSaleProductData);//渲染模板
	        			}else{
	        				var cartOffSaleProductData={"productImg":productImg,"productName":productName,"sku":sku,"price":cart.price,
	        					"pid":pid,"cartId":cart.cartId,"count":cart.count,"purchasingAmount":purchasingAmount,"stock":stock,"unit":unit};
							var cart_offSale_product_tpl=$("#cart_product_tpl").html();
							productHtml+=wx_common.render(cart_offSale_product_tpl,cartOffSaleProductData);//渲染模板
	        			}
		        	}
		        	var storeName="";
		        	var storeId="";
		        	if(key.split("-").length>1){
		        		storeName=key.split("-")[0];
		        		storeId=key.split("-")[1];
		        	}
		        	var cartData={"storeName":storeName,"products":productHtml,"sellerId":sellerId,"storeId":storeId};
		        	var cart_tpl=$("#cart_tpl").html();
		        	cartHtml+=wx_common.render(cart_tpl,cartData);//渲染模板
	        	}
	        	$(".content").prepend(cartHtml);
	        	layer.closeAll();
	        	wx_common.LazyloadImg();//使用图片延迟加载
	        }
		});
	})();
	
	 /**
     * 无数据提示
     * */
    function noProduct(){
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	//隐藏购物车内容div
		$("div.content").addClass("hide");
		//隐藏编辑按钮
		$("a.myCartEdit").addClass("hide");
		//隐藏完成按钮
		$("a.myCartEditOK").addClass("hide");
    	$(".dropload-down").remove();
    }
	
	/**
	 * 根据多个pid查商品list
	 * */
	function getProductSpuList(pids){
		var productSpuList=null;
		if(typeof(pids)=="undefined" || pids==""){
    		return productSpuList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/list.htm',
	        data:{"pids":pids},
	        dataType: 'json',
	        async:false,
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
	 * 获取多个skuIds查询商品sku list
	 * */
	function getProductSkuList(skuIds){
		if(skuIds==null || skuIds==""){
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
		skuContent=spec1+spce2+spce3
		return skuContent;
	}
	
	/**
	 * 删除
	 * */
    $(".delete").click(function(){
    	var cartIds="";
    	var isAll="0";
    	if($(".content").find('input.cartCheckbox:checked').length==0){
    		layer.open({content:"请选择要删除的商品",skin: 'msg',time: 2});
    		return;
    	}
    	var isAllChecked=$("input.allCheckbox").prop('checked');
    	if(isAllChecked){
    		isAll="1";
    	}else{
    		$(".content").find('input.cartCheckbox:checked').each(function(){
    			cartIds+=$(this).attr("id")+",";
        	});
    	}
    	$.confirm("您确定要删除商品吗?", "确认删除?", function(){
    		//layer.open({type: 2});
    		$.ajax({
	   			type: 'GET',
	   	        url:ctxw+'/api/v1/trade/cart/delete.htm',
	   	        data:{"cartIds":cartIds,"isAll":isAll},
	   	        dataType: 'json',
	   	        success: function(data){
	   	        	if(data==null || data.status==""){
	   	        		layer.open({content: data.message!=""?data.message:"删除失败",skin: 'msg',time: 2});
	   	        		return;
	   	        	}
	   	        	//没有登录
		        	if(data.status=='401'){
		        		wx_common.routerLogin();
	    				return;
	    			}
		        	if(data.status!='200'){
		        		layer.open({content:data.message!=null?data.message:'删除失败',skin: 'msg',time: 3});
		        		return;
		        	}
	   	        	//移除删除的商品
	   	        	if("1"==isAll){
	   	        		$(".content").html("");
	   	        		//显示无数据提示
	   	        		noProduct();
	   	        	}else{
	   	        		$(".content").find('input.cartCheckbox:checked').parent().parent().remove();
	   	        		$(".cart-list.myCart").each(function(){
		   	         		if($(this).find(".weui-border-t").length==0){
		   	         			$(this).remove();
		   	         		}
		   	         	});
	   	        	}
	   	        	layer.closeAll();
	   	        	calculation();
	   	        	layer.open({content: data.message!=""?data.message:"删除成功",skin: 'msg',time: 2});
	   	        }
    		});
		}, function() {
			//取消操作
		});
    });
    
    /**
     * 编辑
     * */
	$(".myCartEdit").click(function(){
		$(this).css('display','none'); 
		$(".myCartEditOK").removeAttr("style");  
		$(".settlement").css('display','none'); 
		$(".moneyAll").css('display','none'); 
		$(".settlement").css('display','none'); 
		$(".delete").css('display','block'); 
	});
	
	/**
	 * 完成 
	 * */
	$(".myCartEditOK").click(function(){
		$(this).css('display','none'); 
		$(".myCartEdit").removeAttr("style");  
		$(".settlement").removeAttr("style"); 
		$(".moneyAll").removeAttr("style"); 
		$(".settlement").removeAttr("style");  
		$(".delete").css('display','none'); 
	});
	
	/**
	 * 加数量
	 * */
	$(".myCart-body").delegate(".weui-number-plus","click",function(){
		//是否更新数据库中的数量，点击弹出框中的加减不更新，点击购物车页面中的加减要更新数量
		var isUpdate=$(this).parent().attr("isUpdate");
		upDownOperation($(this),isUpdate);
	});
	
	/**
	 * 减数量
	 * */
	$(".myCart-body").delegate(".weui-number-sub","click",function(){
		//是否更新数据库中的数量，点击弹出框中的加减不更新，点击购物车页面中的加减要更新数量
		var isUpdate=$(this).parent().attr("isUpdate");
		upDownOperation($(this),isUpdate);
	});
    
    /**
	 * 修改购物车数量
	 * */
    function upDownOperation(element,isUpdate){
    	var _input = element.parent().find('input'),
    		_value = _input.val(),
    		_step = _input.attr('data-step') || 1;
		//检测当前操作的元素是否有disabled，有则去除
		element.hasClass('disabled') && element.removeClass('disabled');
		//检测当前操作的元素是否是操作的添加按钮（.input-num-up）‘是’ 则为加操作，‘否’ 则为减操作
		var flag=false;
		var cartId=$(element).parent().attr("cartId");
		if(element.hasClass('weui-number-plus')){
			var _new_value = parseInt( parseFloat(_value) + parseFloat(_step)),
			_max = _input.attr('data-max') || false,
			_down = element.parent().find('.weui-number-sub');
			//若执行‘加’操作且‘减’按钮存在class='disabled'的话，则移除‘减’操作按钮的class 'disabled'
			_down.hasClass('disabled') && _down.removeClass('disabled');
			if(_max && _new_value >= _max){
				_new_value = _max;
				element.addClass('disabled');
			}
			//不更新数据，只修改输入框中的值
			if(isUpdate==0){
				flag=true;
			}else{
				flag=updateCount(_new_value,cartId);
			}
		}else{
			var _new_value = parseInt( parseFloat(_value) - parseFloat(_step) ),
			_min = _input.attr('data-min') || false,
			_up = element.parent().find('.weui-number-plus');
			//若执行‘减’操作且‘加’按钮存在class='disabled'的话，则移除‘加’操作按钮的class 'disabled'
			_up.hasClass('disabled') && _up.removeClass('disabled');
			if(_min && _new_value <= _min){
				_new_value = _min;
				element.addClass('disabled');
			}
			//不更新数据，只修改输入框中的值
			if(isUpdate==0){
				flag=true;
			}else{
				flag=updateCount(_new_value,cartId);
			}
		}
		if(flag){
			_input.val(_new_value);
			_input.attr("oldCount",_new_value);
			calculation();
		}
    }
    
    /**
     * 数量输入框点击事件，
     * 点击输入框弹出修改购买数量弹出框
     * */
    $(".content").delegate(".buyNumber","click",function(){
    	var _input=$(this);
    	var value=_input.val();
    	var dataMax=_input.attr("data-max");
    	var cartId=_input.parent().attr("cartId");
    	var cartCountInputData={"count":value,"dataMax":dataMax,"cartId":cartId};
		var cart_count_input_tpl=$("#cart_count_input_tpl").html();
		var content=wx_common.render(cart_count_input_tpl,cartCountInputData);//渲染模板
		layer.open({
			title:'修改购买数量',
			content: content,
			btn: ['确定', '取消'],
			yes: function(index){
				var _newValue=$(".buyNumber2").val();
		    	if(_newValue==""){
		    		return;
		    	}
		    	var flag=updateCount(_newValue,cartId);
		    	if(flag){
		    		_input.val(_newValue);
		    		calculation();
		    	}
				layer.close(index);
			}
		});
	});
    
    /**
     * 修改购买数量弹出框中，输入输入框的keyup事件
     * */
    $(".myCart-body").delegate(".buyNumber2","keyup",function(){
		var cartId=$(this).parent().attr("cartId");
		//如果购买数量不是数字，把购买数量置为最小值
		var pattern=/^[0-9]\d*$/;
		var oldCount=$(this).attr("oldCount");
    	var count=$(this).val();
    	var max=$(this).attr("data-max");
    	var min=$(this).attr("data-min");
    	//如果输入的不是数字，把数量置为原来的值
    	if(count!="" && !pattern.test($.trim(count))){
    		count=oldCount;
    	}
    	//如果输入的数字小于最小值，把数量置为最小值
    	if(parseInt(count)<min){
    		count=min;
    	}
    	//如果数量大于最大值,把数量置为最大值
    	if(parseInt(count)>max){
    		count=max;
    	}
    	$(this).val(count);
    });
    
    /**
     * 修改购物车数量
     * */
    function updateCount(count,cartId){
    	var flag=false;
    	$.ajax({
   			type: 'GET',
   	        url:ctxw+'/api/v1/trade/cart/updateCount.htm',
   	        data:{"count":count,"cartId":cartId},
   	        dataType: 'json',
   	        async: false,
   	        success: function(data){
   	        	if(data==null || data.status==""){
   	        		layer.open({content: data.message!=""?data.message:"修改数量失败",skin: 'msg',time: 2});
   	        		return;
   	        	}
   	        	//没有登录
	        	if(data.status=='401'){
	        		wx_common.routerLogin();
    				return;
    			}
	        	if(data.status!='200'){
	        		layer.open({content:data.message!=null?data.message:'修改数量失败',skin: 'msg',time: 3});
	        		return;
	        	}
   	        	flag=true;
   	        }
		});
    	return flag;
    }
    
	/**
	 * 全选
	 * */
	$(".myCartAll label").click(function(){
		var isAllChecked = $(this).find('input').prop('checked'); 
		if(isAllChecked){
			//全选状态
			$(this).find('input').prop('checked',false);
			$(".cartStore").find('input').prop('checked',false);
			$(".cartProduct").find('input').prop('checked',false);
		}else{
			//不是全选状态
			$(this).find('input').prop('checked',true);
			$(".cartStore").find('input').prop('checked',true);
			$(".cartProduct").find('input').prop('checked',true);
		}
		calculation();
		return false;
	});
  
	/**
	 * 点击每个店铺全选
	 * */
	$("body").delegate(".cartStore","click",function(){
		var isAllChecked = $(this).find('input').prop('checked'); 
		if(isAllChecked){
			//全选状态
			$(this).find('input').prop('checked',false);
			$(this).parent().parent().find(".cartProduct").find('input').prop('checked',false);
		}else{
			//不是全选状态
			$(this).find('input').prop('checked',true);
			$(this).parent().parent().find(".cartProduct").find('input').prop('checked',true);
		}
		var isCheckedAllCount = 0;
		$(".cartStore").find('input').each(function(){
			if($(this).prop('checked')){
				isCheckedAllCount++;
			}
		});
		if($(".cartStore").find('input').length==isCheckedAllCount){
			$(".myCartAll").find('input').prop('checked',true);
		}else{
			$(".myCartAll").find('input').prop('checked',false);
		}
		calculation();
		return false;
	});
	
	/**
	 * 点击每个商品中的选择
	 * */
	$("body").delegate(".cartProduct","click",function(){
		var isChecked = $(this).find('input').prop('checked'); 
		if(isChecked){
			//由选中到不选中
			$(".myCartAll").find('input').prop('checked',false);
			$(this).find('input').prop('checked',false);
			$(this).parent().parent().find(".cartStore").find('input').prop('checked',false);
		}else{
			$(this).find('input').prop('checked',true);
			//由不选中到选中
			var isCheckedAllProductCount = 0;
			$(this).parent().parent().find(".cartProduct").find('input').each(function(){
				if($(this).prop('checked')){
					isCheckedAllProductCount++;
				}
			});
			if($(this).parent().parent().find(".cartProduct").find('input').length==isCheckedAllProductCount){
				$(this).parent().parent().find(".cartStore").find('input').prop('checked',true);
			}else{
				$(this).parent().parent().find(".cartStore").find('input').prop('checked',false);
			}
			var isCheckedAllStoreCount = 0;
			$(".cartStore").find('input').each(function(){
				if($(this).prop('checked')){
					isCheckedAllStoreCount++;
				}
			});
			if($(".cartStore").find('input').length==isCheckedAllStoreCount){
				$(".myCartAll").find('input').prop('checked',true);
			}else{
				$(".myCartAll").find('input').prop('checked',false);
			}
		}
		calculation();
		return false;
	});
	
	/**
	 * 根据选择的商品计算总价格
	 * */
	function calculation(){
		//商品总价
		var productPrice=0;
		$(".content").find('input.cartCheckbox:checked').each(function(){
			var count=$(this).parent().parent().find(".weui-number-input.buyNumber").val();
			if(count=="" || typeof(count)=="undefined"){
				count=0;
			}
			var price=$(this).parent().parent().find("input.price").val();
			if(price==""|| typeof(price)=="undefined"){
				price=0;
			}
			var pricef=parseFloat(price);
			productPrice+=pricef*parseInt(count);
		});
		$(".myCartAll span.productSumPrice").text(productPrice.toFixed(2));
	}
	
	/**
	 * 结算
	 * */
    $(".settlement").click(function(){
    	layer.open({type: 2});
    	if($(".content").find('input.cartCheckbox:checked').length==0){
    		layer.closeAll();
    		layer.open({content:"请选择要购买的商品",skin: 'msg',time: 3});
    		return;
    	}
    	var userMemberId=wxusm.uid();
    	var cartIds="";
    	var flag=true;
    	$(".content").find('input.cartCheckbox:checked').each(function(){
			var isInvalid=$(this).attr("invalid");
			if("1"==isInvalid){
				flag=false;
				msg="不能购买无效的商品";
				return false;
			}
    		cartIds+=$(this).attr("id")+",";
			var userSellerId=$(this).parents(".cart-list.myCart").find("label.storeName").attr("sellerId");
			//购买数量
			var buyNum=$(this).parent().parent().find("input.buyNumber").val();
			//库存
			var stock=$(this).parent().parent().find("input.stock").val();
			//起购量
			var purchasingAmount=$(this).parent().parent().find("input.purchasingAmount").val();
			//商品名称
			var productName=$(this).parent().siblings(".d-text").find(".productName").text();
			//验证购买数量
			if(buyNum=="" || typeof(buyNum)=="undefined"){
				flag=false;
				msg=productName+"购买数量不能为空";
				layer.closeAll();
				return false;
			}
			//验证库存
			if(parseInt(buyNum) > parseInt(stock)){
				flag=false;
				msg=productName+"库存不足";
				layer.closeAll();
				return false;
			}
			//验证是否是自己的商品
			if(userSellerId==userMemberId){
				flag=false;
				msg="不能购买自己店铺的商品";
				return false;
			}
			//验证购买数量是否大于起购量
			if(parseInt(buyNum)<parseInt(purchasingAmount)){
				flag=false;
				msg="购买数量必须大于起购量,"+productName+"的起购量是"+purchasingAmount;
				layer.closeAll();
				return false;
			}
		});
		if(!flag){
			layer.closeAll();
			layer.open({content:msg,skin: 'msg',time: 3});
			return false;
		}
		var productSumPrice=$(".myCartAll span.productSumPrice").text();
		$.ajax({
			url:ctxw+"/api/1.0/trade/order/confimOrder/validate.htm",
			type:'GET',
			data:{"stat":"1","ids":cartIds,"productSumPrice":productSumPrice},
			dataType:'json',
			success:function(data){
				if(data==null || data.status=="" || !data.data){
					layer.closeAll();
					layer.open({content:data.message!=""?data.message:"结算失败",skin: 'msg',time: 3});
					return;
				}
				//没有登录
	        	if(data.status=='401'){
	        		layer.closeAll();
	        		wx_common.routerLogin();
    				return;
    			}
	        	if(data.status!='200' || !data.data){
	        		layer.closeAll();
	        		layer.open({content:data.message!=null?data.message:'结算失败',skin: 'msg',time: 3});
	        		return;
	        	}
				window.location.href=ctxw+"/trade/order/confirmOrder.htm?stat=1&ids="+cartIds+"&productSumPrice="+productSumPrice;
			}
		});
    });
    
});