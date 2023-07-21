$(function(){
	
	var initUrl = ctxw + '/api/v1/product/page.htm?cid='+cid+"&k="+k+"&sid="+storeId+"&scid="+storeCategoryId+"&sDate="+sDate+"&eDate="+eDate;
	
	//初始化数据
	manager.putUrl(initUrl);
	var url = manager.getUrl();
	
	//初始化数据
	productPage(url);
	isK();
	
	/**
	 * 是否搜索关键字
	 */
	function isK(){
		if(k!=""){
			$("#gosearch-box .search-k").html(k);
		}
	}
	
	/**
	 * 下拉刷新
	 * */
	function productPage(url){
		//页数 
		var pageNo = 0;
		// 每页展示10个
		var pageSize = 10;
		$('.weui_panel').dropload({
			scrollArea : window,
			autoLoad : true,//自动加载
			domUp : {//下拉
				domClass   : 'dropload-up',
				domRefresh : '<div class="dropload-refresh"><i class="icon icon-114"></i>上拉加载更多</div>',
				domUpdate  : '<div class="dropload-load f15"><i class="icon icon-20"></i>释放更新...</div>',
				domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>'
			},
			loadDownFn : function(me){//加载更多
				pageNo++;
				manager.putUrl(url);
				manager.add("pageNo",pageNo);
				manager.add("pageSize",pageSize);
				getProductList(me);
			}
		});
	};
	
	/**
     * 获取商品列表
     * */
    function getProductList(me){
    	var result = '';
    	$.ajax({
        	type: 'GET',
            url: manager.getUrl(),
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
            	//如果没有数据或已是最后一页
				var productList = data.data.productList;
            	if(productList==undefined || productList.length==0){
            		noProduct();
            		return false;
            	}
            	$(".noList").addClass("hide");
            	if(productList!=undefined){
            		for(var i=0; i<productList.length; i++){
            			var storeTypeClass = "";
            			var storeType = "";
            			if(productList[i].storeType=="1"){
            				storeTypeClass="display:none;";
            			}
            			if(productList[i].storeType=="2"){
            				storeType="旗舰店";
            			}
                    	var rowData={"pid":productList[i].pid,"storeTypeClass":storeTypeClass,"storeType":storeType,"imgPath":ctxfs+productList[i].image+"@200x200","name":productList[i].name,
                    			"type":productList[i].type==2?"批":"零","price":productList[i].type==2?productList[i].minPrice2:productList[i].minPrice1,"display":productList[i].type==3?"":"hide"};//模板的数据
        				var productList_Tpl=$("#product_Tpl").html();
        				var productListHtml=wx_common.render(productList_Tpl,rowData);//渲染模板
        				result+=productListHtml;
           			}
            		//获取当前url
            		var cid = manager.get("cid");//分类id
            		var bid = manager.get("bid");//品牌id
            		//获取筛选值
            		var siftIscroll_deal_brand = $(".sift-iscroll .deal-brand").length;
            		var siftIscroll_deal_category = $(".sift-iscroll .deal-category").length;
            		var siftIscroll_deal_type = $(".sift-iscroll .deal-type").length;
            		if(siftIscroll_deal_brand==0 && siftIscroll_deal_category==0 && siftIscroll_deal_type==0 ){
            			var product_screen_Tpl=$("#product_screen_Tpl").html();//筛选模板
            			var product_screen_detail_Tpl=$("#product_screen_detail_Tpl").html();//筛选详情模板
            			var product_screen="";
                		//品牌
                        var brandList=data.data.brandList;
                        if(brandList.length>0){
                        	var productScreenBrandHtml="";
                        	for(var i=0;i<brandList.length;i++){
                        		var cla = "selected";
                        		if(bid==brandList[i].brandId){
                        			cla = "selected cur";
                        		}
                        		var brandData={"id":brandList[i].brandId,"cla":cla,"screenDetail":brandList[i].name};
                            	productScreenBrandHtml+=wx_common.render(product_screen_detail_Tpl,brandData);//渲染模板
                        	}
                        	var screenBrandData={"screenCla":"deal-brand","screenName":"品牌","screenDetail":productScreenBrandHtml};
            				var productBrandHtml=wx_common.render(product_screen_Tpl,screenBrandData);//渲染模板
            				product_screen+=productBrandHtml;
                        }
                        //分类
                        var categroyList=data.data.categoryList;
                        if(categroyList.length>0){
                        	var productScreenCateHtml="";
                        	for(var i=0;i<categroyList.length;i++){
                        		var cla = "selected";
                        		if(cid==categroyList[i].categoryId){
                        			cla = "selected cur";
                        		}
                        		var categoryData={"id":categroyList[i].categoryId,"cla":cla,"screenDetail":categroyList[i].name};
                            	productScreenCateHtml+=wx_common.render(product_screen_detail_Tpl,categoryData);//渲染模板
                        	}
                        	var screenCateData={"screenCla":"deal-category","screenName":"分类","screenDetail":productScreenCateHtml};
            				var productCategoryHtml=wx_common.render(product_screen_Tpl,screenCateData);//渲染模板
            				product_screen+=productCategoryHtml;
                        }
                        //参数
                        var paramValueMap=data.data.paramValueMap;
        				var paramValueHtml="";
        				for(key in paramValueMap){
        					if(paramValueMap[key]!=null){
        						var value = paramValueMap[key];
        						var productScreenParamHtml = "";
        						for (var i = 0; i < value.length; i++) {
        							var paramData={"id":"","cla":"selected","screenDetail":value[i]};
        							productScreenParamHtml+=wx_common.render(product_screen_detail_Tpl,paramData);//渲染模板
        						}
        					}
    						var screenParamData={"screenCla":"deal-type","screenName":key,"screenDetail":productScreenParamHtml};
    						var productParamHtml=wx_common.render(product_screen_Tpl,screenParamData);//渲染模板
    						paramValueHtml+=productParamHtml;
        				}
        				product_screen+=paramValueHtml;
        				//添加筛选
        				$('.sift-iscroll').append(product_screen);
        				//是否去掉全部
        				$(".sift-expand").css("width","auto");
        				showExtMore_brand();
        				showExtMore_category();
        				showExtMore_type();
        				$(".sift-expand").css("width","0vw");
            		}
            	}
            	// 如果没有数据或已是最后一页
            	if(productList==undefined || data.page.isLastPage){
            		// 锁定
               		me.lock();
                	// 无数据
                	me.noData();
                	setTimeout(function(){
                		$(".dropload-noData").parent().fadeOut("slow");
                	},2000);
            	}
            	// 为了测试，延迟1秒加载
            	setTimeout(function(){
               		$('.weui_panel_bd').append(result); 
               		wx_common.LazyloadImg();//使用图片延迟加载
		            // 每次数据加载完，必须重置
		                me.resetload();
		            },1000);
            },
            error: function(xhr, type){
            	layer.open({content: "获取商品数据报错",skin: 'msg',time: 2 });
                // 即使加载出错，也得重置
                me.resetload();
            }
        });
    }
    
    /**
     * 是否显示参数右侧的全部按钮(品牌)
     */
    function showExtMore_brand(){
		var _dl=$(".deal-brand");
		_dl.each(function(){
			var maxWidth = 0;
			var _dd=$(this).find("dd");
			//alert(_dd.length);
			//var _li=_ul.find("li");
			_dd.each(function(){
				maxWidth += ($(this).outerWidth(true) + 10);
			});
			if (maxWidth > ($(window).width()*0.84-30)) {
				$(this).addClass("screening-height");
			} else {
				$(this).removeClass("screening-height");
				$(this).find('span').remove();
			}
		});
	}
    
    /**
     * 是否显示参数右侧的全部按钮(分类)
     */
    function showExtMore_category(){
		var _dl=$(".deal-category");
		_dl.each(function(){
			var maxWidth = 0;
			var _dd=$(this).find("dd");
			//var _li=_ul.find("li");
			_dd.each(function(){
				maxWidth += ($(this).outerWidth(false) + 10);
				//alert(maxWidth);
			});
			if (maxWidth > ($(window).width()*0.84-30)) {
				$(this).addClass("screening-height");
			} else {
				$(this).removeClass("screening-height");
				$(this).find('span').remove();
			}
		});
	}
    
    /**
     * 是否显示参数右侧的全部按钮(参数)
     */
    function showExtMore_type(){
		var _dl=$(".deal-type");
		_dl.each(function(){
			var maxWidth = 0;
			var _dd=$(this).find("dd");
			_dd.each(function(){
				maxWidth += ($(this).outerWidth(true) + 10);
			});
			if (maxWidth > ($(window).width()*0.84-30)) {
				$(this).addClass("screening-height");
			} else {
				$(this).removeClass("screening-height");
				$(this).find('span').remove();
			}
		});
	}
    
    /**
     * 没有商品信息
     */
    function noProduct(){
    	$(".noList").removeClass("hide");
    	var noList_Tpl=$("#noList_Tpl").html();
    	$('.noList').append(noList_Tpl);
    	$(".dropload-down").remove();
    }
    
    /**
     * 清理
     */
    function clearProduct(){
    	//清理上下拉等待
    	$(".dropload-down").remove();
    	//商品列表清空
    	$(".weui_panel_bd").html("");
    	//无数据div清空
    	$(".noList").html("");
    	//筛选清空
    	/*$(".sift-iscroll .deal-brand").remove();
    	$(".sift-iscroll .deal-category").remove();
    	$(".sift-iscroll .deal-type").remove();*/
    }
    
    /**
	 * 搜索条件展开收起
	 */
	$(".prod_list").delegate(".search_list_tool li.sift","click",function(){
		var isSelected = $(this).hasClass('selected');
		if(isSelected){
			//下拉状态准备收起
			$(this).removeClass("selected");
			$(this).find('span').removeClass("icon-xiangxia1");
			$(this).find('span').addClass("icon-xiangshang1");
			$(".blackbg").css("display","none");
		}else{
			//收起状态准备展开
			$(".search_list_tool li.sift").removeClass("selected");
			$(".search_list_tool li.sift").find('span').removeClass("icon-xiangxia1");
			$(".search_list_tool li.sift").find('span').addClass("icon-xiangshang1");
			$(this).addClass("selected");
			$(this).find('span').removeClass("icon-xiangshang1");
			$(this).find('span').addClass("icon-xiangxia1");
			$(".blackbg").css("display","block");
		}
	});
	
	/**
     * 点击搜索条件
     */
	$(".prod_list").delegate(".sift ul li","click",function(){
		clearProduct();
		$(".sift ul li i").removeClass("icon-wancheng");
		$(this).children().addClass("icon-wancheng");
    	var dataText = $(this).attr("data-text");
    	manager.del("pageNo");
    	manager.del("pageSize");
    	if(dataText==null || dataText=='' ||  dataText=="默认"){
    		//综合-默认
    		manager.del("sort");
    		manager.del("sortMode");
    	}
    	if(dataText=="新品优先"){
    		//综合-默认
    		manager.del("sort");
    		manager.del("sortMode");
    	}
		if(dataText=="好评数从高到低"){
			//销量-好评数从高到低
			manager.add("sort","commentCount");
			manager.add("sortMode","DESC");
		}
		if(dataText=="销量从低到高"){
			//销量-从低到高
			manager.add("sort","allSales");
			manager.add("sortMode","ASC");
		}
		if(dataText=="销量从高到低"){
			//销量-从高到低
			manager.add("sort","allSales");
			manager.add("sortMode","DESC");
		}
		if(dataText=="价格从低到高"){
			//价格-从低到高
			manager.add("sort","minPrice");
			manager.add("sortMode","ASC");
		}
		if(dataText=="价格从高到低"){
			//价格-从高到低
			manager.add("sort","minPrice");
			manager.add("sortMode","DESC");
		}
		var url = manager.getUrl();
		productPage(url);
    });
	
	/**
	 * 点击蒙层灰色区域
	 */
	$(".prod_list").delegate(".blackbg","click",function(){
		$(".search_list_tool li.sift").removeClass("selected");
		$(".search_list_tool li.sift").find('span').removeClass("icon-8");
		$(".search_list_tool li.sift").find('span').addClass("icon-6");
		$(".blackbg").css("display","none");
	});
	
	/**
	 * 筛选按钮点击事件
	 * 打开筛选横屏
	 * */
	 $(".prod_list").delegate(".screen-button","click",function(){
		$(".search_list_tool li.sift").removeClass("selected");
		$(".search_list_tool li.sift").find('span').removeClass("icon-8");
		$(".search_list_tool li.sift").find('span').addClass("icon-6");
		$(".blackbg").css("display","none");
		$(this).parent().parent().find('.sift-expand').removeAttr("style");
		$(this).removeClass("icon-xiangshang2");
		$(".sift-expand").addClass("show");
		//$(".sift-expand").css("display","block");
		$(".weui_tab_bd").css("overflow","hidden");
		$(".sift-expand-mongolia").addClass("show");
		$(".sui-page, .sui-page-group").css("position","absolute");
	});
	
	/**
	 * 筛选框-返回按钮、蒙层点击事件
	 * 关闭筛选横屏
	* */
	$(".prod_list").delegate(".sift-expand .back , .sift-expand-mongolia","click",function(){
		$(".sift-expand").css("width","0vw");
		$(".sift-expand").removeClass("show");
		$(".weui_tab_bd").css("overflow","auto");
		$(".sift-expand-mongolia").removeClass("show");
		$(".sui-page, .sui-page-group").css("position","static");
	});
	
	 /**
	 * 展开/收起筛选类型
	 * */
	$(".prod_list").delegate(".sift-iscroll dl dt span","click",function(){
		var iconStatus = $(this).hasClass('icon-xiangshang2');
		if(iconStatus){
			//收起状态准备展开
			$(this).removeClass("icon-xiangshang2");
			$(this).addClass("icon-xiangxia2");
			$(this).parent().parent().removeClass("screening-height");
		}else{
			//下拉状态准备收起
			$(this).removeClass("icon-xiangxia2");
			$(this).addClass("icon-xiangshang2");
			$(this).parent().parent().addClass("screening-height");
		}
	});
	
	/**
	 * 筛选框-重置点击事件
	 * */
	$(".prod_list").delegate(".bottom-btn #btn-reset","click",function(){ 
		$(this).removeClass("icon-xiangshang2");
		$(".sift-iscroll dd.cur").removeClass("cur");
		$(".deal-price input").val("");
	});

	/**
	 * 筛选框-完成点击事件
	 * */
	$(".prod_list").delegate(".bottom-btn #btn-finish","click",function(){ 
		$(this).removeClass("icon-xiangshang2");
		$(".sift-expand").removeClass("show");
		$(".sift-expand-mongolia").removeClass("show");
		$(".sui-page, .sui-page-group").css("position","static");
		//初始搜索条件
		manager.putUrl(initUrl);
		//获取价格区间
		var startPrice = $(".start-price").val();//最小价格
		var endPrice = $(".end-price").val();//最大价格
		var price = startPrice+"-"+endPrice;
		manager.add("price",price);
		//获取品牌id
		var brandIds = "";
		$(".deal-brand").find(".cur").each(function(){
			brandIds+=",";
			brandIds+=$(this).attr("id");
		});
		if(brandIds!=""){
			manager.add("bid",brandIds.substr(1));
		}
		//获取分类id
		var categoryId = $(".deal-category").find(".cur").attr("id");
		if(categoryId!=undefined){
			manager.add("cid",categoryId);
		}
		//获取参数
		var attrs = "";
		var attrArray = [];
		$(".deal-type").each(function(){
			var attrTypeKey = "";
			var attrTypeValues = "";
			attrTypeKey += $(this).find('dt').find('p').html();
			$(this).find(".cur").each(function(){
				attrTypeValues+=":";
				attrTypeValues+=$(this).html();
			});
			if(attrTypeValues!=""){
				attrArray.push(attrTypeKey+"_"+attrTypeValues.substr(1));
			}
		});
		for (var i = 0; i < attrArray.length; i++) {
			attrs+=",";
			attrs+=attrArray[i];
		}
		if(attrs!=""){
			manager.add("attr",attrs.substr(1));
		}	
		clearProduct();
		var url = manager.getUrl();
		productPage(url);
	});

	/**
	 * 筛选选择状态
	 */
	$(".prod_list").delegate("dd.selected","click",function(){ 
		if($(this).hasClass("cur")){
			$(this).removeClass("cur");
		}else{
			if($(this).parent().hasClass('deal-category')){
				$(this).parent().find(".selected").removeClass("cur");
			}
			$(this).addClass("cur");
		}
	});
	
	/**
	 * 筛选框右侧滑动事件，关闭筛选框
	 * */
	$(".sift-expand-mongolia , .sift-expand").swipeRight(function(){
		$(this).removeClass("icon-xiangshang2");
		$(".sift-expand").removeClass("show");
		$(".sift-expand-mongolia").removeClass("show");
		$(".sui-page, .sui-page-group").css("position","static");
	});
	
});