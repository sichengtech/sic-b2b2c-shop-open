//商品规格
var productSkuTotalList=null;
var productSpu=null;
$(function(){
	
	/**
	 * 根据pid查商品
	 * */
	(function(){
		if(typeof(pid)=="undefined" || pid==""){
    		window.location.href=ctxw+"/product/error.htm";
    		return;
		}
		$.ajax({
			type: 'GET',
			url:ctxw+'/api/v1/product/one.htm?pid='+pid,
			dataType: 'json',
			success: function(data){
	        	if(data==null || data.status!=200 || data.data==""){
	        		window.location.href=ctxw+"/product/error.htm";
	        		return;
	        	}
	        	var product=data.data;
	        	productSpu=data.data;
	        	//商品名称
	        	$(".goods_name .name").text(product.name);
	        	//商品副标题
	        	//$(".goods_name .nameSub").text(product.nameSub);
	        	if(typeof(product.nameSub)=="undefined" || product.nameSub==""){
	        		$(".goods_name .nameSub").css("display","none");
	        	}else{
	        		$(".goods_name .nameSub").text(product.nameSub);
	        	}
	        	//商品评价数
	        	if(typeof(product.commentCount)!="undefined" && product.commentCount!=""){
	        		$(".activity .commentCount").text(product.commentCount);
	        	}
	        	//商品销量
	        	if(typeof(product.allSales)!="undefined"){
	        		$(".activity .salleCount").text(product.monthSales);
	        	}
	        	//商品收藏
	        	if(typeof(product.collectionCount)!="undefined" && product.collectionCount!=""){
	        		$(".activity .collectionCount").text(product.collectionCount);
	        	}
	        	//商品单位
	        	$("input.unit").val(typeof(product.unit)=="undefined" || product.unit==""?'件':product.unit);
	        	//商品图片
	        	$(".widgets-cover .img-wrap img").attr("src",ctxfs+product.image+"@200x200");
	        	//更新起购量
        		$(".sku-wrap .header input.purchasingAmount").val(product.purchasingAmount);
	        	//移除商品规格
	        	if(product.type==2 || (product.type==3 && wxusm.isTypeUserPurchaser())){
	        		//批发模式的商品，移除零售模式的规格
	        		$(".widgets-cover.productRetail").remove();
	        		//加入商品的类型
	        		$("input.productType").val(2);
	        		//批发模式的商品，显示商品的批发价
	        		getSectionPriceList(product);
	        		//批发模式的商品，隐藏零售模式的div
	        		$(".goods_price").addClass("hide");
	        	}else{
	        		//零售模式的商品，移除批发模式的规格
	        		$(".widgets-cover.productWholesale").remove();
	        		//默认将购买数量置为起购量
	        		$(".sku-wrap .number-wrap .number .number_input").val(product.purchasingAmount);
	        		//加入商品的类型
	        		$("input.productType").val(1);
	        		//零售模式的商品，拼装商品的零售价格
	        		var productPrice1Data={"pid":product.pid,"price":product.minPrice1,"type":product.type!=2?"零":"批","display":product.type==1?"hide":""};
					var product_price1_Tpl=$("#product_price1_Tpl").html();
					var productPrice1Html=wx_common.render(product_price1_Tpl,productPrice1Data);//渲染模板
					$('.goods_price').append(productPrice1Html);
					//零售模式的商品，隐藏批发模式的div
	        		$(".goods_price2").addClass("hide");
	        	}
				//获取店铺
				getStore(product.storeId);
				//获取店铺信息（关注人数、全部商品、店铺动态）
				getStoreInfo(product.storeId);
				//判断是否收藏店铺
				isCollentionStore(product.storeId);
	        }
		});
	})();
	
	/**
	 * 获取商品图片
	 * */
	(function(){
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/image/list.htm?pid='+pid,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null ||data.status!=200 || data.data==null || typeof(data.data)=="undefined" || data.data.length==0){
	        		return;
	        	}
	        	var productImgList=data.data;
	        	//商品图片
	        	var productImgHtml="";
	    		for(var i=0;i<productImgList.length;i++){
	    			var productImgData={"imgPath":ctxfs+productImgList[i].path+"@400x400"};
	    			var product_img_Tpl=$("#product_img_Tpl").html();
	    			productImgHtml+=wx_common.render(product_img_Tpl,productImgData);//渲染模板
	    		}
	    		$('.productImgs').html(productImgHtml);
	    		//加载轮播图片js
	    		var swiper = new Swiper('.swiper-container', {
	    			pagination: {
	    				el: '.swiper-pagination',
	    			},
	    		});
	        }
		});
	})();
	
	/**
	 * 根据店铺id查店铺
	 * */
	function getStore(storeId){
		if(storeId==null){
			return;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/store/one.htm?storeId='+storeId,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null || data.data==null || typeof(data.data)=="undefined"){
	        		return;
	        	}
	        	var store=data.data;
	        	//设置店铺名称
				$(".store .storename").text(store.name);
				$(".weui_cells_access.store-info .store-name").text(store.name);
				$(".weui_cells_access.store-info .storeId").val(store.storeId);
				//设置店铺logo
				$(".weui_cells_access.store-info .logo img").attr("src",ctxfs+store.logo+"@150x75");
				//设置店铺类型（旗舰店加旗舰店的标志）
				if(store.storeType==2){
					$(".store .storetype").css("display","inline-block");
				}
				//修改去店铺首页的地址
				$("a.storeIndex , .weui_cells_access.store a.storeName").attr("href",ctxw+"/store/index.htm?storeId="+store.storeId);
	        }
		});
	}
	
	/**
	 * 根据pid获取商品详情
	 * */
	(function(){
		if(typeof(pid)=="undefined" || pid==""){
    		return;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/detail.htm',
	        data:{"pid":pid},
	        dataType: 'json',
	        success: function(data){
	        	if(data==null ||data.status!=200 || data.data==null || typeof(data.data)=="undefined"){
	        		return;
	        	}
	        	$(".product_detail").html(data.data.introduction);
	        }
		});
	})();
	
	/**
	 * 获取用户信息
	 * */
	function getUserMainList(uids){
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
	 * 根据商品id查询商品的批发价列表
	 **/
	function getSectionPriceList(product){
		if(product==null || product.pid==null){
			return;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/sectionPrice/list.htm?pid='+product.pid,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null || data.data==null || typeof(data.data)=="undefined" || data.data.length==0){
	        		return;
	        	}
	        	var sectionPriceList=data.data;
				//商品价格
	        	var productPrice2Html="";
				for(var i=0;i<sectionPriceList.length;i++){
					var number;
					if(i==sectionPriceList.length-1){
						number="≥"+sectionPriceList[i].section;
					}else{
						number=sectionPriceList[i].section+"~"+(sectionPriceList[i+1].section-1);
					}
					var productPrice2Data={"price":sectionPriceList[i].price,"number":number+" "+(product.unit==null?'件':product.unit)};
		        	var product_price2_Tpl=$("#product_price2_Tpl").html();
					productPrice2Html+=wx_common.render(product_price2_Tpl,productPrice2Data);//渲染模板
				}
				$('.goods_price2').append(productPrice2Html);
	        }
		});
	}
	
	/**
	 * 批发模式下，根据数量获取商品价格
	 **/
	function getSectionPrice(pid,count){
		var price="";
		$.ajax({
			url:ctxw+"/api/v1/product/getSectionPrice.htm?pid="+pid+"&count="+count,
			type:'GET',
	        success: function(data){
	        	if(data==null || data.data==null || typeof(data.data)=="undefined" || data.status!='200'){
	        		layer.open({content: data.message!=""?data.message:'获取商品价格出现错误',skin: 'msg',time: 2});
	        		return;
	        	}
	        	price=data.data;
	        	if(price!=null && price!=""){
	        		$(".sku-wrap .sectionPrice").text(price);
	    		}
	    		$(".sku-wrap .header .main .price").text("¥ "+price);
	        }
		});
		return price;
	}
	
	/**
	 * 根据商品id查询商品的skuList
	 * */
	function getProductSkuListBySkuIds(skuIds){
		var productSkuList=null;
		if(skuIds==null || skuIds==""){
			return productSkuList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/sku/list.htm',
	        data:{"skuIds":skuIds},
	        dataType: 'json',
	        async:false,
	        success: function(data){
	        	if(data==null || data.status!='200' || data.data==null || typeof(data.data)=="undefined" || data.data.length==0){
	        		return;
	        	}
	        	productSkuList=data.data;
	        }
		});
		return productSkuList;
	}
	
	/**
	 * 根据商品id获取商品sku list
	 * */
	function getProductSkuListByPid(pid){
		if(pid==null || pid=="" || productSkuTotalList!=null){
			return productSkuTotalList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/sku/list.htm',
	        data:{"pid":pid},
	        dataType: 'json',
	        async:false,
	        success: function(data){
	        	if(data==null || data.status!='200' || data.data==null || typeof(data.data)=="undefined" || data.data.length==0){
	        		return;
	        	}
	        	productSkuTotalList=data.data;
	        }
		});
		return productSkuTotalList;
	}
	
	/**
	 * 拼装零售模式的规格模板
	 * */
	function retailSkuHtml(pid){
		if(pid==null || pid==""){
			return;
		}
		var productSkuList=getProductSkuListByPid(pid);
		if(productSkuList==null || productSkuList=="" || productSkuList.length==0){
			return;
		}
		var spec1="";
		var spec2="";
		var spec3="";
		var spec1vArray=new Array();
		var spec2vArray=new Array();
		var spec3vArray=new Array();
		var productSku=productSkuList[0];
		//默认显示第一个规格的库存信息
		var purchasingAmount=$(".sku-wrap .header input.purchasingAmount").val();
		if(productSku.stock >= parseInt(purchasingAmount)){
			$(".sku-wrap .header .stock span").text("有货");
		}else{
			$(".sku-wrap .header .stock span").text("无货");
			$("input.number_input").val("0");
		}
		//更新skuId
		$(".sku-wrap .number-wrap .skuId").val(productSku.skuId);
		//修改最大购买数量
		$("input.number_input").attr("data-max",productSku.stock);
		//无货
		if(0==productSku.stock){
			//将加减数量的按钮置为不可点击
			$(".number .weui-number-sub").addClass("disabled");
			$(".number .weui-number-plus").addClass("disabled");
			$("input.number_input").val(0);
		}
		//设置skuId,加入购物车用
		$(".sku-wrap .number-wrap .skuId").val(productSku.skuId);
		if(productSku.spec1!=null && productSku.spec1.split("_").length>1){
			spec1=productSku.spec1.split("_")[1];
		}
		if(productSku.spec2!=null && productSku.spec2.split("_").length>1){
			spec2=productSku.spec2.split("_")[1];
		}
		if(productSku.spec3!=null && productSku.spec3.split("_").length>1){
			spec3=productSku.spec3.split("_")[1];
		}
		for(var i=0;i<productSkuList.length;i++){
			if(productSkuList[i].spec1V!=null && $.inArray(productSkuList[i].spec1V, spec1vArray)==-1){
				spec1vArray.push(productSkuList[i].spec1V);
			}
			if(productSkuList[i].spec2V!=null && $.inArray(productSkuList[i].spec2V, spec2vArray)==-1){
				spec2vArray.push(productSkuList[i].spec2V);
			}
			if(productSkuList[i].spec3V!=null && $.inArray(productSkuList[i].spec3V, spec3vArray)==-1){
				spec3vArray.push(productSkuList[i].spec3V);
			}
		}
		var retailSkuHtml="";
		if(spec1vArray.length>0){
			retailSkuHtml+=getRetailSpec(spec1vArray,spec1,1);
		}
		if(spec2vArray.length>0){
			retailSkuHtml+=getRetailSpec(spec2vArray,spec2,0);
		}
		if(spec3vArray.length>0){
			retailSkuHtml+=getRetailSpec(spec3vArray,spec3,0);
		}
		$(".sku-list-wrap").html(retailSkuHtml);
		//根据选中的规格，显示不同的图片、价格和库存信息
		updateProductMsgBySpec();
	}
	
	/**
	 * 拼装零售模式的规格模板
	 * */
	function getRetailSpec(specvArray,specName,isColor){
		var specvHtml=""
		for(var i=0;i<specvArray.length;i++){
			var className="";
			if(i==0){
				className="checked"
			}
			var productSpecValueData={"className":className,"specV":specvArray[i],"isColor":isColor};
        	var product_spec_value_Tpl=$("#product_spec_value_Tpl").html();
        	specvHtml+=wx_common.render(product_spec_value_Tpl,productSpecValueData);//渲染模板
		}
		var productRetailSpecData={"specName":specName,"specvs":specvHtml};
    	var product_retail_spec_Tpl=$("#product_retail_spec_Tpl").html();
    	return wx_common.render(product_retail_spec_Tpl,productRetailSpecData);//渲染模板
	}
	
	/**
	 * 拼装批发模式的规格模板
	 * */
	function wholesaleSkuHtml(pid,unit){
		if(pid==null || pid==""){
			return;
		}
		var productSkuList=getProductSkuListByPid(pid);
		if(productSkuList==null || productSkuList=="" || productSkuList.length==0){
			return;
		}
		var wholesaleSkuHtml="";
		for(var i=0;i<productSkuList.length;i++){
			var productSku=productSkuList[i];
			var stock=productSku.stock;
			var spce1="";
			var spce2="";
			var spce3="";
			var spec1V="";
			var spec2V="";
			var spec3V="";
			if(productSku.spec1!=null && productSku.spec1.split("_").length>1){
				spec1=productSku.spec1.split("_")[1]+":";
			}
			if(productSku.spec2!=null && productSku.spec2.split("_").length>1){
				spce2=productSku.spec2.split("_")[1]+":";
			}
			if(productSku.spec3!=null && productSku.spec3.split("_").length>1){
				spce3=productSku.spec3.split("_")[1]+":";
			}
			if(productSku.spec1V!=null){
				spec1V=productSku.spec1V;
			}
			if(productSku.spec2V!=null){
				spec2V=productSku.spec2V;
			}
			if(productSku.spec3V!=null){
				spec3V=productSku.spec3V;
			}
			var specV="";
			if(spec1V!="" || spec2V!="" || spec3V!=""){
				var productWholesaleSpecvData={"spec1V":spec1V,"spec2V":spec2V,"spec3V":spec3V};
				var product_wholesale_specv_Tpl=$("#product_wholesale_specv_Tpl").html();
				specV=wx_common.render(product_wholesale_specv_Tpl,productWholesaleSpecvData);//渲染模板
			}else{
				$(".productWholesale .productWholesale-table thead .spec").remove();
			}
	    	if(typeof(unit)=="undefined" || unit==""){
	    		unit="件";
	    	}
	    	var productWholesaleSpecData={"specV":specV,"unit":unit,"stock":stock,"skuId":productSku.skuId};
	    	var product_wholesale_spec_Tpl=$("#product_wholesale_spec_Tpl").html();
	    	wholesaleSkuHtml+=wx_common.render(product_wholesale_spec_Tpl,productWholesaleSpecData);//渲染模板
		}
		$(".productWholesale .productWholesale-table tbody").html(wholesaleSkuHtml);
		//显示是否有货
		var totalCount=$(".activity .totalCount").text();
		//起购量
		var purchasingAmount=$(".sku-wrap .header .main .sku-info .purchasingAmount").val();
		if("0"==totalCount || parseInt(totalCount)<parseInt(purchasingAmount)){
			$(".sku-wrap .header .main .stock span").text("无货");
		}
		//供应总量<=起购量时，将加数量的按钮置为不可点击
		if(parseInt(totalCount)<=parseInt(purchasingAmount)){
			$(".productWholesale-table tbody tr .weui-number-plus").addClass("disabled");
		}
		//供应总量>0时，将减数量按钮置为可点击
		if(parseInt(totalCount)>0){
			$(".productWholesale-table tbody tr .weui-number-sub").removeClass("disabled");
		}
		//只有一个规格时，回显起购量
		if(productSkuList.length==1){
			if("0"==totalCount || parseInt(totalCount)<parseInt(purchasingAmount)){
				$(".productWholesale .productWholesale-table tbody tr .number_input").val(totalCount);
			}else{
				$(".productWholesale .productWholesale-table tbody tr .number_input").val(purchasingAmount);
			}
		}
		
	}
	
	/**
	 * 加载评价页面后，加载评价js
	 * */
	$(document).on("pageAnimationEnd", function(e, pageId, $page) {
		if(pageId == "productView") {
			var swiper = new Swiper('.swiper-container', {
				pagination: {
					el: '.swiper-pagination',
				},
			});
	  	}
	  	if(pageId == "productView1") {
  			$("script[src='"+ctx+"/views/wap/default/tradeCommentList.js']").remove();
  			var head = $("head");
  			var script = $("<script>");
  			$(script).attr('src',ctx+'/views/wap/default/tradeCommentList.js'); 
  			$(script).attr('type','text/javascript');
  			$(head).append(script);
	  	}
	});
	
	/**
	 * 数量加减js
	 * */
	function upDownOperation(element){
		var _input = element.parent().find('input'),
	    _value = _input.val(),
		_step = _input.attr('data-step') || 1;
	    //检测当前操作的元素是否有disabled，有则去除
	    element.hasClass('disabled') && element.removeClass('disabled');
	    //检测当前操作的元素是否是操作的添加按钮（.input-num-up）‘是’ 则为加操作，‘否’ 则为减操作
    	if (element.hasClass('weui-number-plus') ){
    		var _new_value = parseInt( parseFloat(_value) + parseFloat(_step) ),
            _max = _input.attr('data-max') || false,
            _down = element.parent().find('.weui-number-sub');
	        //若执行‘加’操作且‘减’按钮存在class='disabled'的话，则移除‘减’操作按钮的class 'disabled'
	        _down.hasClass('disabled') && _down.removeClass('disabled');
	        if (_max && _new_value >= _max) {
	            _new_value = _max;
	            element.addClass('disabled');
	        }
    	}else{
    		var _new_value = parseInt( parseFloat(_value) - parseFloat(_step) ),
            _min = _input.attr('data-min') || false,
            _up = element.parent().find('.weui-number-plus');
	        //若执行‘减’操作且‘加’按钮存在class='disabled'的话，则移除‘加’操作按钮的class 'disabled'
	        _up.hasClass('disabled') && _up.removeClass('disabled');
	        if (_min && _new_value <= _min) {
	            _new_value = _min;
	            element.addClass('disabled');
	        }
    	}
    	_input.val( _new_value );
	}
	
	/**
	 * 打开规格弹框
	 * */
	$(".product-detail-body").delegate(".go-addcat","click",function(){
		//商品类型，1零售、2批发
		var productType=$("input.productType").val();
		if("1"==productType){
			//零售模式的商品，拼装零售模式的规格
    		retailSkuHtml(pid);
		}else{
			//批发模式的商品，拼装批发模式的规格
    		wholesaleSkuHtml(pid,productSpu.unit);
    		//设置价格
    		$(".sku-wrap .sectionPrice").text(productSpu.maxPrice2);
    		$(".sku-wrap .header .main .price").text("¥ "+productSpu.maxPrice2);
		}
	    $(".widgets-cover").addClass("show");
	    $("body").css("overflow","hidden");
	    var id=$(this).attr("id");
	    $(".cover-content .sku-wrap .ok").attr("type",id);
	});
	
	/**
	 * 关闭规格弹框点击事件
	 * */
	$(".product-detail-body").delegate(".sku-close","click",function(){
		skuClose();
	});
	
	/**
	 * 关闭规格弹框
	 * */
	function skuClose(){
		$(".widgets-cover").removeClass("show");
		$("body").css("overflow","auto");
	    //回显当前选中的规格
	    //商品类型:1零售，2批发
	    var type=$("input.productType").val();
	    //单位
	    var unit=$("input.unit").val();
	    var skuMsg="";
	    if("1"==type){
	    	$(".sku-list-wrap .items a.checked").each(function(){
	    		skuMsg+=$(this).text()+",";
	    	});
	    	var count=$(".sku-wrap .number-wrap .number_input").val();
	    	skuMsg+=count+unit;
	    }
	    if("2"==type){
	    	$(".sku-wrap .productWholesale-table tbody tr").each(function(){
	    		var count=$(this).find("input.number_input").val();
	    		if(count==0){
	    			return true;
	    		}
	    		var spec1V=$(this).find(".spec1V").text();
	    		var spec2V=$(this).find(".spec2V").text();
	    		var spec3V=$(this).find(".spec3V").text();
	    		if(spec1V!="" && spec1V!=null){
	    			skuMsg+=spec1V+",";
	    		}
	    		if(spec2V!="" && spec2V!=null){
	    			skuMsg+=spec2V+",";
	    		}
	    		if(spec3V!="" && spec3V!=null){
	    			skuMsg+=spec3V+",";
	    		}
	    		skuMsg+=count+unit+"; ";
	    	});
	    }
	    $(".choose .go-addcat p .selectedSpec").text(skuMsg);
	}
	
	/**
	 * 商品详情、商品参数tab点击事件
	 * */
	//是否获取了参数数据
	var isGetParam=false;
	$(".product-detail-body").delegate(".weui_tabbar_item","click",function(){
		$(this).addClass('cur').siblings().removeClass('cur');
		var tabId = $(this).attr('href');
		$('.weui_tab_bd').find(tabId).addClass('active').siblings().removeClass('active');
		var id=$(this).attr("id");
		if("param"==id && !isGetParam){
			var productParamList=getProductParam(pid);
			isGetParam=true;
			if(productParamList==null || productParamList=="" || productParamList.length==0){
				var productNoParamData={};
		    	var product_no_param_Tpl=$("#product_no_param_Tpl").html();
		    	var productnoParamHtml=wx_common.render(product_no_param_Tpl,productNoParamData);//渲染模板
		    	$(".goods-des .detailParameter").html(productnoParamHtml);
				return;
			}
			var productParamHtml="";
			for(var i=0;i<productParamList.length;i++){
				var paramName=productParamList[i].name;
				var paramValue=productParamList[i].value;
				var productParamData={"paramName":paramName,"paramValue":paramValue};
		    	var product_param_Tpl=$("#product_param_Tpl").html();
		    	productParamHtml+=wx_common.render(product_param_Tpl,productParamData);//渲染模板
			}
			$(".goods-des .detailParameter").html(productParamHtml);
		}
	});
	
	/**
	 * 获取商品参数
	 * */
	function getProductParam(pid){
		var productParamList=null;
		if(pid==null || pid==""){
			return productParamList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/param/list.htm?pid='+pid,
	        dataType: 'json',
	        async:false,
	        success: function(data){
	        	if(data==null || data.data==null || typeof(data.data)=="undefined" || data.data.length==0){
	        		return;
	        	}
	        	productParamList=data.data;
	        }
		});
		return productParamList;
	}
	
	/**
	 * 选择地区控件
	 * */
	$(".product-detail-body").delegate(".weui_cell.address","click",function(){
		$("#ssx").cityPicker({
			title: "选择省市县"
		});
		$("#ss").cityPicker({
			title: "选择省市",
	        showDistrict: false
		}); 
		$(".weui_cell.address").click();
	});
	
	/**
	 * 加数量
	 * */
	$(".product-detail-body").delegate(".weui-number-plus","click",function(){
		if($(this).hasClass("disabled")){
			return false;
		}
		upDownOperation($(this));
		//批发模式：根据数量更新商品价格
		if($(this).attr("iswholesale")=='1'){
			updataPriceByCount();
		}
	});
	
	/**
	 * 减数量
	 * */
	$(".product-detail-body").delegate(".weui-number-sub","click",function(){
		if($(this).hasClass("disabled")){
			return false;
		}
		upDownOperation($(this));//批发模式：根据数量更新商品价格
		if($(this).attr("iswholesale")=='1'){
			updataPriceByCount();
		}
	});
	
	/**
	 * 批发模式：不同的数量对应不同的价格
	 * */
	function updataPriceByCount(){
		var totalCount=0;
		$(".sku-wrap .number-wrap input.number_input").each(function(){
			var count=$(this).val();
			if(typeof(count)!="undefined" && count!=""){
				totalCount+=parseInt(count);
			}
		});
		getSectionPrice(pid,totalCount);
	}
	
	/**
	 * 零售模式：规格的点击事件
	 * */
	$(".product-detail-body").delegate(".sku-list-wrap a.spec","click",function(){
		//更新背景颜色
		$(this).siblings("a.checked").not("a[class='disabled']").removeClass("checked");
		$(this).addClass("checked");
		//根据不同的规格显示不同的价格、库存、图片
		updateProductMsgBySpec();
	});
	
	/**
	 * 零售模式下：根据不同的规格显示不同的价格、库存、图片
	 * */
	function updateProductMsgBySpec(){
		//更新价格和库存
		var productSkuList=getProductSkuListByPid(pid);
		if(productSkuList==null || productSkuList.length==0){
			return;
		}
		var currentSpecArray=new Array();
		$(".sku-list-wrap .items a.checked").each(function(){
			//如果是选中的是颜色，需要更新图片
			if($(this).attr("isColor")=="1"){
				var color=$(this).text();
				getProductImg(pid,color);
			}
			currentSpecArray.push($(this).text());
		});
		//没有选中规格或者无规格默认设置第一个sku的价格
		if(currentSpecArray.length==0){
			//更新价格
			$(".sku-wrap .header .price").text("¥ "+productSkuList[0].price);
			return;
		}
		for(var i=0;i<productSkuList.length;i++){
			var flag=true;
			for(var j=0;j<currentSpecArray.length;j++){
				var specv="spec"+(j+1)+"V";
				var sku="";
				if(specv=="spec1V"){
					sku=productSkuList[i].spec1V;
				}
				if(specv=="spec2V"){
					sku=productSkuList[i].spec2V;
				}
				if(specv=="spec3V"){
					sku=productSkuList[i].spec3V;
				}
				if(sku !=currentSpecArray[j]){
					flag=false;
				}
			}
			if(flag){
				//更新价格
				$(".sku-wrap .header .price").text("¥ "+productSkuList[i].price);
				//更新库存
				var stock=productSkuList[i].stock;
				$(".sku-wrap .header input.productStock").val(stock);
				//起购量
				var purchasingAmount=$(".sku-wrap .header input.purchasingAmount").val();
				//if(productSkuList[i].stock >= parseInt(purchasingAmount)){
				if(stock>0){
					$(".sku-wrap .header .stock span").text("有货");
				}else{
					$(".sku-wrap .header .stock span").text("无货");
				}
				//更新skuId
				$(".sku-wrap .number-wrap .skuId").val(productSkuList[i].skuId);
				//更新最大可购买数
				$(".sku-wrap .number input.number_input").attr("data-max",productSkuList[i].stock);
				//操作购买数量（如果购买数量大于库存，把购买数量置为库存数，把加号置为不可点击，否则将加号置为可点击）
				var buyCount=$(".sku-wrap .number input.number_input").val();
				if(stock!=0){
					if(buyCount>=stock){
						$(".sku-wrap .number input.number_input").val(stock);
						$(".sku-wrap .number .weui-number-plus").addClass("disabled");
						$(".sku-wrap .number .weui-number-sub").removeClass("disabled");
					}else{
						$(".sku-wrap .number .weui-number-plus").removeClass("disabled");
						$(".sku-wrap .number .weui-number-sub").removeClass("disabled");
						if("0"==buyCount || "1"==buyCount){
							$(".sku-wrap .number input.number_input").val("1");
							$(".sku-wrap .number .weui-number-sub").addClass("disabled");
						}
					}
				}else{
					$(".sku-wrap .number .weui-number-plus").addClass("disabled");
					$(".sku-wrap .number .weui-number-sub").addClass("disabled");
					$(".sku-wrap .number input.number_input").val("0");
				}
			}
		}
	}
	
	/**
     * 购买数量小于1时默认值为1
     */
	$(".product-detail-body").delegate(".number_input","keyup",function(){
		//如果购买数量不是数字，把购买数量置为最小值
		var pattern=/^[1-9]\d*$/;
    	var count=$(this).val();
    	var max=$(this).attr("data-max");
    	var min=$(this).attr("data-min");
    	//如果输入的不是数字，把数量置为原来的值
    	if(count!="" && !pattern.test($.trim(count))){
    		count=min;
    	}
    	//如果数量不是数字或输入的数字小于最小值，把数量置为最小值
    	if(parseInt(count)<min){
    		count=min;
    	}
    	//如果数量大于最大值,把数量置为最大值
    	if(parseInt(count)>max){
    		count=max;
    	}
    	$(this).val(count);
    	//批发模式：根据数量更新商品价格
    	var iswholesale=$(this).siblings("button.weui-number-plus").attr("iswholesale");
		if(iswholesale=='1'){
			updataPriceByCount();
		}
    });
	
	/**
	 * 根据pid查商品图片
	 * */
	function getProductImg(pid,color){
		var productImgList=null;
		if(typeof(pid)=="undefined" || pid==""){
    		return productImgList;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/image/list.htm?pid='+pid+"&color="+color,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null ||data.status!=200 || data.data==null || typeof(data.data)=="undefined" || data.data.length==0){
	        		return;
	        	}
	        	var productImgList=data.data;
				var productImg=productImgList[0];
				$(".img-wrap img").attr("src",ctxfs+productImg.path+"@200x200");
	        }
		});
	};
	
	/**
	 * 查询商品是否收藏
	 * */
	(function(){
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/user/collectionProduct/isCollection.htm?pid='+pid,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null || data.data==null || typeof(data.data)=="undefined"){
	        		return;
	        	}
	        	var collectionId=data.data;
	        	if(collectionId!=null && collectionId!=""){
	        		$(".view_nav .collectionProduct .view_nav_icon .icon").attr("class","icon icon-shoucang1");
	        		$(".view_nav .collectionProduct input.collectionId").val(collectionId);
	        		$(".view_nav .collectionProduct").attr("iscollection","1");
	        	}
	        }
		});
	})();
	
	/**
	 * 收藏商品
	 * */
	$("body").delegate(".view_nav .collectionProduct","click",function(){
		var productCollectionCount = $(".collectionCount").html();
		//是否收藏
		var isCollection=$(this).attr("isCollection");
		if(isCollection=="1"){
			//取消收藏
			cancleCollectionProduct(pid);
			$(".collectionCount").html(parseInt(productCollectionCount)-1);
		}else{
			//收藏
			collectionProduct(pid);
			$(".collectionCount").html(parseInt(productCollectionCount)+1);
		}
	});
	
	/**
	 * 收藏商品方法
	 * */
	function collectionProduct(pid){
		if(pid==null){
			return;
		}
		$.ajax({
	    	type: 'POST',
	        url:ctxw+'/api/v1/user/collectionProduct/save.htm?pid='+pid,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null){
	        		layer.open({content: data.message!=""?data.message:"收藏失败",skin: 'msg',time: 2});
	        		return;
	        	}
	        	if(data.status=='401'){
					wx_common.routerLogin();
					return false;
				}
				if(data.status!='200'){
					layer.open({content:data.messge!=""?data.message:"收藏失败",skin: 'msg',time: 2});
					return false;
				}
	        	$(".view_nav .collectionProduct .view_nav_icon .icon").attr("class","icon icon-shoucang1");
	        	$(".view_nav .collectionProduct").attr("isCollection","1");
	        	var collention=data.data;
	        	$(".view_nav .collectionProduct input.collectionId").val(collention.collectionId);
	        	layer.open({content:data.message,skin: 'msg',time: 2});
	        }
		});
	}
	
	/**
	 * 取消收藏商品方法
	 * */
	function cancleCollectionProduct(pid){
		if(pid==null){
			return;
		}
		var collectionId=$(".view_nav .collectionProduct input.collectionId").val();
		$.ajax({
	    	type: 'POST',
	        url:ctxw+'/api/v1/user/collectionProduct/cancel.htm?collectionIds='+collectionId,
	        dataType: 'json',
	        success: function(data){
	        	if(data==null){
	        		layer.open({content: data.message!=""?data.message:"取消收藏失败",skin: 'msg',time: 2});
	        		return;
	        	}
	        	if(data.status=='401'){
					wx_common.routerLogin();
					return false;
				}
				if(data.status!='200'){
					layer.open({content:data.messge!=""?data.message:"取消收藏失败",skin: 'msg',time: 2});
					return false;
				}
	        	$(".view_nav .collectionProduct .view_nav_icon .icon").attr("class","icon icon-favorite1");
	        	$(".view_nav .collectionProduct").attr("isCollection","0");
	        	layer.open({content:data.message,skin: 'msg',time: 2});
	        }
		});
	}
	
	/**
	 * 查询店铺是否收藏
	 * */
	function isCollentionStore(storeId){
		if(storeId==null){
			return;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/user/collectionStore/isCollection.htm',
	        data:{"storeId":storeId},
	        dataType: 'json',
	        success: function(data){
	        	if(data==null || data.data==null || typeof(data.data)=="undefined" || data.status!='200'){
	        		return;
	        	}
	        	var collectionId=data.data;
	        	if(collectionId!=null && collectionId!=""){
	        		$(".weui-flex-item .collectionstore span").attr("class","icon icon-shoucang1");
		        	$(".weui-flex-item .collectionstore").attr("isCollection","1");
		        	$(".weui-flex.button input").val(collectionId);
	        	}
	        }
		});
	}
	
	/**
	 * 收藏店铺点击事件
	 * */
	$("body").delegate(".collectionstore","click",function(){
		var isCollection=$(this).attr("isCollection");
		var storeId=$(".weui_cells_access.store-info .storeId").val();
		//是否收藏
		if(isCollection=='0'){
			//收藏店铺
			collectionStore(storeId);
		}else{
			cancleCollectionStore(storeId);
		}
	});
	
	/**
	 * 收藏店铺
	 */
	function collectionStore(storeId){
		$.ajax({
			type: 'GET',
			url: ctxw + '/api/v1/user/collectionStore/save.htm',
			data:{"storeId":storeId},
			dataType: 'json',
			success: function(data){
				if(data==null){
					layer.open({content: "收藏失败",skin: 'msg',time: 2});
					return false;
				}
				if(data.status=='401'){
					wx_common.routerLogin();
					return false;
				}
				if(data.status!='200'){
					layer.open({content:data.messge!=""?data.message:"收藏失败",skin: 'msg',time: 2});
					return false;
				}
				$(".weui-flex-item .collectionstore span").attr("class","icon icon-shoucang1");
	        	$(".weui-flex-item .collectionstore").attr("isCollection","1");
	        	var collention=data.data;
	        	$(".weui-flex.button input").val(collention.collectionStoreId);
				layer.open({content: data.messge!=""?data.message:"收藏成功",skin: 'msg',time: 2});
			},
			error: function(data){
				layer.open({content: '店铺收藏出现错误！',skin: 'msg',time: 2});
			}
		});
	}
	
	/**
	 * 取消收藏商品方法
	 * */
	function cancleCollectionStore(storeId){
		if(storeId==null){
			return;
		}
		var collectionId=$(".weui-flex.button input").val();
		$.ajax({
	    	type: 'POST',
	        url:ctxw+'/api/v1/user/collectionStore/cancel.htm',
	        data:{collectionIds:collectionId},
	        dataType: 'json',
	        success: function(data){
	        	if(data==null){
	        		layer.open({content: data.message!=""?data.message:"取消收藏失败",skin: 'msg',time: 2});
	        		return;
	        	}
	        	if(data.status=='401'){
					wx_common.routerLogin();
					return false;
				}
				if(data.status!='200'){
					layer.open({content:data.messge!=""?data.message:"取消收藏失败",skin: 'msg',time: 2});
					return false;
				}
	        	$(".weui-flex-item .collectionstore span").attr("class","icon icon-favorite1");
	        	$(".weui-flex-item .collectionstore").attr("isCollection","0");
	        	$(".weui-flex.button input").val("");
	        	layer.open({content:data.message,skin: 'msg',time: 2});
	        }
		});
	}
	
	/**
	 * 根据pid获取商品评价
	 * */
	(function(){
		if(typeof(pid)=="undefined" || pid==""){
    		return;
		}
		$.ajax({
	    	type: 'GET',
	        url:ctxw+'/api/v1/product/comment/list.htm',
	        data:{"pid":pid,"type":"0","limit":"3"},
	        dataType: 'json',
	        success: function(data){
	        	if(data==null ||data.status!=200 || data.data==null || typeof(data.data)=="undefined"){
	        		return;
	        	}
	        	//评价数：暂无评价
	        	if(data.data.length==0){
	        		var productCommentCountData={"commentCount":"暂无评价(0)"};
					var product_comment_count_Tpl=$("#product_comment_count_Tpl").html();
					var productCommentCountHtml=wx_common.render(product_comment_count_Tpl,productCommentCountData);//渲染模板
					$(".commentRate").append(productCommentCountHtml);
					return;
	        	}
	        	//评价内容
	        	var tradeCommentList=data.data;
	        	var commentHtml="";
	        	//多个用户
            	var uids="";
            	//多个skuid
            	var skuIds="";
            	for(var i=0;i<tradeCommentList.length;i++){
            		var comment=tradeCommentList[i];
            		uids+=comment.uid+",";
            		skuIds+=comment.skuId+",";
           		}
            	//多个用户
            	var userList=getUserMainList(uids);
            	//多个商品sku
            	var skuList=getProductSkuListBySkuIds(skuIds);
            	for(var j=0;j<tradeCommentList.length;j++){
            		var tradeComment=tradeCommentList[j];
            		var username="shop商城会员";
            		//循环用户list
            		if(userList!=null && userList!="" && userList.length>0){
            			for(var k=0;k<userList.length;k++){
            				var user=userList[k];
            				if(user.uid==tradeComment.uid && user.loginName!=""){
            					var reg = /^(.).+(.)$/g;
        	        			username=user.loginName.replace(reg, "$1***$2");
            				}
            			}
            		}
            		//循环商品sku list
            		var skuContent="";
            		if(skuList!="" && skuList!=null && skuList.length>0){
            			for(var j2=0;j2<skuList.length;j2++){
            				var sku=skuList[j2];
            				if(sku.skuId==tradeComment.skuId){
            					skuContent=getProductSkuContent(sku);
            				}
            			}
            		}
            		var productCommentData={"commentId":tradeComment.commentId,"username":username,"content":tradeComment.content,"score":tradeComment.productScore,
	        				"date":tradeComment.createDate,"sku":skuContent};
					var product_comment_Tpl=$("#product_comment_Tpl").html();
					commentHtml+=wx_common.render(product_comment_Tpl,productCommentData);//渲染模板
            	}
	        	$(".commmentList_ul").html(commentHtml);
	        	//好评率
	        	//显示查看全部评价按钮
        		$("a.allConmment").removeClass("hide");
	        }
		});
	})();
	
	/**
	 * 获取店铺商品数据
	 */
	function getStoreInfo(storeId){
		$.ajax({						
			type: 'GET',
			url: ctxw + '/api/v1/store/nav/count.htm',
			data:{"storeId":storeId},
			dataType: 'json',
			success: function(data){
				if(data==null || data.data==null || data.status==null || data.status!='200'){
					return;
				}
				$(".store-info .weui_tabbar_item p.collentionCount").text(data.data.collectionStoreCount);//店铺收藏数
				$(".store-info .weui_tabbar_item p.productCount").text(data.data.productAllCount);//全部商品数
				$(".store-info .weui_tabbar_item p.articleCount").text(data.data.storeArticleCount);//店铺动态数目
			},
			error: function(data){
				layer.open({content: '获取店铺信息数据信息出现问题！',skin: 'msg',time: 2});
			}
		});
	}
	
	/**
	 * 计算供应总量
	 * */
	(function(){
		var productSkuList=getProductSkuListByPid(pid);
		if(productSkuList==null || productSkuList=="" || productSkuList.length==0){
			return;
		}
		var stock=0;
		for(var i=0;i<productSkuList.length;i++){
			var productSku=productSkuList[i];
			stock+=productSku.stock;
		}
		$(".weui_cells_access.activity font.totalCount").text(stock);
	})();
	
	/**
	 * 规格弹框中“确定”按钮点击事件
	 * */
	$("body").delegate(".cover-content .sku-wrap .ok","click",function(){
		//根据类型判断是加入购物车/立即购买/关闭弹框
		var type=$(this).attr("type");
		//加入购物车
		if("cart"==type){
			addCart();
		}
		//立即购买
		if("buy"==type){
			buyNow();
		}
		if("choose"==type){
			skuClose();
		}
	});
	
	 /**
	 * 加入购物车
	 */
    function addCart(){
    	if(typeof(pid)=="undefined" || pid == ""){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 请选择要加入购物车商品",2000);
			return false;
		}
    	var type=$("input.productType").val();
    	//type==1:零售，2：批发
    	var skuMsg="";
    	if(type=="1"){
    		var numInput=$(".sku-wrap .number-wrap .number .number_input").val();
    		var skuId=$(".sku-wrap .number-wrap .skuId").val();
    		if(skuId!="" && numInput!="" && numInput!='0'){
    			skuMsg+=skuId+"-"+numInput+",";
    		}
    	}else{
    		$(".sku-wrap .productWholesale-table tbody tr").each(function(){
    			//countChange(this);
    			var num=$(this).find(".number-wrap .number .number_input").val();
    			if(num !=0){
    				var skuId=$(this).find(".number-wrap .skuId").val();
    				skuMsg+=skuId+"-"+num+",";
    			}
    		});
    	}
    	if(skuMsg==""){
    		layer.open({content:"请选择规格数量",skin: 'msg',time: 2});
			return false;
    	}
    	$.ajax({
			url:ctxw+'/api/v1/trade/cart/save.htm',
			type:'POST',
			data:{pid:pid,skuMsg:skuMsg,type:type},
			dataType:'json',
			success:function(data){
				if(data==null){
	        		layer.open({content: data.message!=""?data.message:"加入购物车失败",skin: 'msg',time: 2});
	        		return;
	        	}
				if(data.status=='401'){
					wx_common.routerLogin();
					return false;
				}
				if(data.status!='200'){
					layer.open({content:data.messge!=""?data.message:"加入购物车失败",skin: 'msg',time: 2});
					return false;
				}
				layer.open({content:data.message,skin: 'msg',time: 2});
			}
    	});
    }
    
    /**
     * 立即购买
     * */
    function buyNow(){
    	layer.open({type: 2});
    	if(typeof(pid)=="undefined" || pid == ""){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 请选择要购买的商品",2000);
			return false;
		}
    	var type=$("input.productType").val();
    	//type==1:零售，2：批发
    	var skuMsg="";
    	//购买总量
    	var totalCount=0;
    	if(type=="1"){
    		var numInput=$(".sku-wrap .number-wrap .number .number_input").val();
    		totalCount+=parseInt(numInput);
    		var skuId=$(".sku-wrap .number-wrap .skuId").val();
    		skuMsg+=skuId+"-"+numInput+",";
    	}else{
    		$(".sku-wrap .productWholesale-table tbody tr").each(function(){
    			//countChange(this);
    			var num=$(this).find(".number-wrap .number .number_input").val();
    			totalCount+=parseInt(num);
    			if(num !=0){
    				var skuId=$(this).find(".number-wrap .skuId").val();
    				skuMsg+=skuId+"-"+num+",";
    			}
    		});
    	}
    	if(skuMsg==""){
    		layer.closeAll();
    		layer.open({content:"请选择规格数量",skin: 'msg',time: 2});
			return false;
    	}
    	//验证购买数量是否大于起购量
    	var purchasingAmount=$(".sku-wrap .header input.purchasingAmount").val();
    	if(totalCount<purchasingAmount){
    		layer.closeAll();
    		layer.open({content:"购买数量应大于起购量，起购量是"+purchasingAmount,skin: 'msg',time: 2});
			return false;
    	}
    	$.ajax({
			url:ctxw+"/api/v1/trade/order/confimOrder/validate.htm",
			type:'GET',
			data:{"stat":"2","pid":pid,"skuMsg":skuMsg,"type":type},
			dataType:'json',
			success:function(data){
				if(data==null){
					layer.closeAll();
					layer.open({content:data.message!=""?data.message:"购买失败",skin: 'msg',time: 2});
					return;
				}
				if(data.status=='401'){
					wx_common.routerLogin();
					return false;
				}
				if(data.status!='200' || !data.data){
					layer.closeAll();
					layer.open({content:data.messge!=""?data.message:"购买失败",skin: 'msg',time: 2});
					return false;
				}
				layer.closeAll();
				window.location.href=ctxw+'/trade/order/confirmOrder.htm?stat=2&pid='+pid+'&skuMsg='+skuMsg+'&type='+type;
			}
		});
    }
    
    /**
     * 获取未读消息
     * */
    var count=wx_common.getUnreadMsgCount();
    if(count!=0){
   	 $(".weui_cells_title a.icon-xiaoxi4 b").text(count);
    }else{
    	$(".weui_cells_title a.icon-xiaoxi4 b").css("display","none");
    }
});