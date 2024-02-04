$(function(){

	/**
	 * 顶部导航中的hover事件
	 */
	$('.header-top-nav li').hover(function() {
		$('ul', this).slideDown(200);
		$(this).children('a:first').addClass("hov");
	}, function() {
		$('ul', this).slideUp(100);
		$(this).children('a:first').removeClass("hov");  
	});
	
		
	/**
	 * 头部的全部分类的hover事件
	 * */
	$("#topnavmenu").hover(function(){
		$(this).addClass("cur")
	},function(){
		$(this).removeClass("cur")
	});

	/**
	 * 头部热搜词的hover事件
	 */
	$(".search-div").hover(function(){
		if($(".ui-searchbar-type .searchType").val()=='2'){
			return;
		}
		$(".sc-hd-row .hotsearch").addClass("cur")
	},function(){
		$(".sc-hd-row .hotsearch").removeClass("cur")
	});

	/**
	 * 店铺导航中全部分类的hover事件
	 */
	$('#store-nav li').hover(function() {
		$('ul', this).slideDown(200);
		$(this).children('a:first').addClass("hov");
	}, function() {
		$('ul', this).slideUp(100);
		$(this).children('a:first').removeClass("hov");  
	});
	
		
	
	/**
	 * 店铺商品分类-左侧(store_left)
	 * */
    $(".goods-category li b").click(function() {
    	var a = $(this).text();
    	if(a == "+"){
    		$(this).parent("li").addClass("cur");
    		$(this).text("-");
    	}
    	if(a == "-"){
    		$(this).parent("li").removeClass("cur");
    		$(this).text("+");
    	}
    });
    
    /**
	 * 分类导航的点击事件和hover事件
	 * */
    var $subblock=$("body").not(".index").find(".subpage"),$head=$subblock.siblings('h2'), $ul = $subblock.find("#proinfo"), 
	//var $subblock = $(".subpage"), $head=$subblock.siblings('h2'), $ul = $("#proinfo"), 
	$lis = $("#proinfo").find("li"), inter=false;
	$head.click(function(e){
		e.stopPropagation();
		if(!inter){
			$ul.show();
		}else{
			$ul.hide();
		}
		inter=!inter;
	});
    	
	$ul.click(function(event){
		event.stopPropagation();
	});
    	
	$(document).click(function(){
		if(!$(document.body).hasClass("index")){
			$ul.hide();
		}
		inter=!inter;
	});


	$lis.hover(function(){
		if(!$(this).hasClass('nochild')){
			$(this).addClass("prosahover");
			$(this).find(".prosmore").removeClass('hide');
		}
	},function(){
		if(!$(this).hasClass('nochild')){
			if($(this).hasClass("prosahover")){
				$(this).removeClass("prosahover");
			}
			$(this).find(".prosmore").addClass('hide');
		}
	});
    

	/**
	 * 显示左侧导航
	 */
	$(window).scroll(function(){
		if($(window).scrollTop() > 400){
			$('#menu').show();
		}else{
			$("#menu").hide();
		}
	});

	/**
	 * 左侧导航一一对应
	 */
	var floor=[];
	$('.left-menunav').each(function(i){
		floor.push($($('.left-menunav')[i]).offset().top-300);
	});
	$(window).scroll(function(){
		for(var i=0;i<floor.length;i++){
			if($(window).scrollTop()>=floor[i] && $(window).scrollTop()<floor[i+1]){
				$($('#menu a')[i]).addClass("cur");
				$($('#menu a')[i-1]).removeClass("cur");
			}else{
				$($('#menu a')[i]).removeClass("cur");
			}
		}
	});
	
	/**
	 * 右侧工具条点击事件
	 */
	$(".r-tool li.r-tool-btn").click(function(){
		var href=$(this).attr("href");
		var status = usm.isLogin();//usm-判断用户是否登录
		if(status == false){
	    	layer.open({
			  type: 2,
			  title: '',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['500px', '390px'],
			  content:ctxf+"/custom/login.htm",
			  //layer层销毁后触发的回调
			  end:function(){
				  var status = usm.isLogin();//usm-判断用户是否登录
				  var purch=usm.isTypeUserPurchaser();//usm-判断用户是否是采购商
				  if(status && purch){
					  location.reload();
				  }
			  }
			});
    	}else{
    		location.href = href;
    	}
	});
	
	/**
	 * 查看批发价
	 */
	/*$(".p-vipprice").click(function(){
		var isTypeUserPurchaser = usm.getCookie('usm.isTypeUserPurchaser');
		if(isTypeUserPurchaser=="true"){
			var pId = $(this).attr("pId");
			window.open(ctx + '/detail/'+pId+'.htm');
		}else{
			layer.open({
				type: 2,
				title: '',
				shadeClose: true,
				shade: 0.8,
				area: ['400px', '300px'],
				content:ctxf+"/custom/tradePrice.htm"
			});
		}
	});*/
	
	/**
	 * 头部搜索
	 * */
	$(".header-main .search-box .head-searchBtn").click(function(){
		var type=$(this).siblings(".select").find(".searchType").val();
		if(typeof(type)=="undefined" || type=="" || type=="1"){
			$(".header-main .search-box form").submit();
		}else{
			return false;
		}
	});
	
	/*$('.header-main .search-box .text').keydown(function(e){
		if(e.keyCode==13){
			var type=$(this).siblings(".select").find(".searchType").val();
			alert(type);
			if(typeof(type)=="undefined" || type=="" || type=="1"){
				$(".header-main .search-box form").submit();
			}else{
				return false;
			}
		}
	});*/
	/**
	 * Tab控制函数
	 * */
	function tabs(tabId, tabNum){
		//设置点击后的切换样式
		$(tabId + " .tab li").removeClass("curr");
		$(tabId + " .tab li").eq(tabNum).addClass("curr");
		//根据参数决定显示内容
		$(tabId + " .tabcon").hide();
		$(tabId + " .tabcon").eq(tabNum).show();
	}
	
	(function($){
	    $.fn.capacityFixed = function(options) {
	    	alert(1);
	        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
	        var FixedFun = function(element) {
	            var top = opts.top;
	            element.css({
	                "top":top
	            });
	            $(window).scroll(function() {
	                var scrolls = $(this).scrollTop();
	                if (scrolls > top) {

	                    if (window.XMLHttpRequest) {
	                        element.css({
	                            position: "fixed",
	                            top: 0							
	                        });
	                    } else {
	                        element.css({
	                            top: scrolls
	                        });
	                    }
	                }else {
	                    element.css({
	                        position: "absolute",
	                        top: top
	                    });
	                }
	            });
	            element.find(".close-ico").click(function(event){
	                element.remove();
	                event.preventDefault();
	            })
	        };
	        return $(this).each(function() {
	            FixedFun($(this));
	        });
	    };
	    $.fn.capacityFixed.deflunt={
			right : 0,//相对于页面宽度的右边定位
	        top:0
		};
	})(jQuery);

	// 分类
	$('ul.component-list li').hover(function() {
		$('ul.component-list li').removeClass('current')
		$(this).addClass('current')
	})

	// 搜索下拉
	$('.ui-searchbar-type-option').click(function() {
		var type=$(this).attr("type");
		$(".ui-searchbar-type .searchType").val(type);
		$('#dropdownMenu1').html($(this).text() + '<i class="sc-hd-prefix2-icon sc-hd-prefix2-icon-arrow-down sc-hd-prefix2-icon-xs"></i>')
	})

	//控制页面的下拉选
	$('.dropdown').click(function() {
		$(this).addClass('open')
	});

	// 表单下拉
	$('.rfq-unit .ui-searchbar-type-option').click(function() {
		$('#dropdownMenu2 a').html($(this).text())
	})

	//提交搜索
	$(".ui-searchbar-submit").click(function(){
		query();
	});

	//搜索回车
	$(".ui-searchbar-keyword").keydown(function (event) {
		if (event.which == 13) {
			query();
		}
	});

	//头部搜索
	function query(){
		var type=$(".ui-searchbar-type .searchType").val();
		var k=$(".sc-hd-searchbar-wrap .ui-searchbar-keyword").val();
		var url=ctxf+"/product/list.htm?k=";
		if (type == "2") {
			url =ctxf+"/store/list.htm?k=";
		}
		url=url+k;
		window.location.href=url;
	}

	//店铺头部搜索
	$(".store-nav .store_search .search_icon").click(function () {
		var storeId=$(this).siblings(".storeId").val();
		var k=$(this).siblings(".keywords").val();
		window.location.href=ctxf+"/product/list.htm?sId="+storeId+"&k="+k;
	});

	//店内搜索回车
	//搜索回车
	$(".store-nav .store_search .keywords").keydown(function (event) {
		if (event.which == 13) {
			$(".store-nav .store_search .search_icon").click();
		}
	});

	//切换语言
	$("#saveLanguage").click(function () {
		var language=$("select.select-language").val();

	})
}); 
