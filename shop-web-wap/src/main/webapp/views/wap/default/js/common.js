(function(){
	/**
	 * wx_common命名空间,起隔离作用，防止同名变量冲突
	 */
	if(!window.wx_common) {window.wx_common={};}
	
	/**
	 * 渲染模板
	*/
	wx_common.render=function(tpl,data){
		return laytpl(tpl).render(data);
	}

	/**
	 * 获取未读消息数量
	 */
	 wx_common.getUnreadMsgCount=function(){
		var count=0;
		$.ajax({
			url:ctxw+"/api/v1/user/userMessage/unreadCount.htm",
			type:'GET',
			dataType:'json',
			async: false,
			success:function(data){
				if(data==null || data.status!='200' || data.data==null){
					return;
				}
				count=data.data;
			}
		});
		return count;
	}

	/**
	 * 获取字典键
	 */
	 wx_common.getDictLabel=function(type,value){
		var label = "";
		$.ajax({
	    	type: "post",
	        url:ctxw+'/api/v1/sys/dict/labelOne.htm?type='+type+"&value="+value,
	        dataType: 'json',
	        async: false,
	        success: function(data){
				if(data.status=="200"){
					label = data.data.label;
				}
	        },
	        error: function(){
	            layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	        }
	    });
		return label;
	};

	/**
	 * 获取多个字典键
	 */
	wx_common.getDictLabelList=function(type,values){
		var labelList = "";
		$.ajax({
	    	type: "post",
	        url:ctxw+'/api/v1/sys/dict/labelList.htm?type='+type+"&values="+values,
	        dataType: 'json',
	        async: false,
	        success: function(data){
				if(data.status=="200"){
					labelList = data.data;
				}
	        },
	        error: function(){
	            layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	        }
	    });
		return labelList;
	};

	/**
	 * 获取字典值
	 */
	wx_common.getDictValue=function(type,label){
		var value = "";
		$.ajax({
	    	type: "post",
	        url:ctxw+'/api/v1/sys/dict/labelOne.htm?type='+type+"&label="+label,
	        dataType: 'json',
	        async: false,
	        success: function(data){
				if(data.status=="200"){
					value = data.data.value;
				}
	        },
	        error: function(){
	            layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
	        }
	    });
		return label;
	};

	/**
	 * 匿名访问具有登录权限的接口,路由返回登录页面
	 */
	wx_common.routerLogin=function(){
		window.location.href=ctxw+"/user/login/form.htm";
	}
	
	/**
	 * 图片延迟加载
	 */
	wx_common.LazyloadImg=function(){
		new LazyloadImg({
			el: '[data-src]', //匹配元素
			position: { // 只要其中一个位置符合条件，都会触发加载机制
				top: 0, // 元素距离顶部
				right: 0, // 元素距离右边
				bottom: 0, // 元素距离下面
				left: 0 // 元素距离左边
		    },
			qriginal: false, // true，自动将图片剪切成默认图片的宽高；false显示图片真实宽高
			before: function (el) {//图片加载之前执行的回调方法
				$(el).removeAttr("data-src");
				el.style.cssText += 'width: 32px;height: 32px;'
				el.src = ctx+"/views/wap/default/img/loading.gif";
				//让等待图标居中
				$(el).parent().css("display","table-cell");
				$(el).parent().css("text-align","center");
				$(el).parent().css("vertical-align","middle");
		    },
			load: function(el) {//图片加载成功后执行的回调方法，传入一个参数
				$(el).css("width",null);
				$(el).css("height",null);
				$(el).parent().css("display","block");
				$(el).parent().css("text-align","left");
				$(el).parent().css("vertical-align","baseline");
				//el.style.cssText += '-webkit-animation: fadeIn 01s ease 0.2s 1 both;animation: fadeIn 1s ease 0.2s 1 both;';
				$(el).css("-webkit-animation","fadeIn 01s ease 0.2s 1 both");
				$(el).css("animation","fadeIn 1s ease 0.2s 1 both");
			},
			error: function(el) {//图片加载失败后执行的回调方法
				$(el).css("width",null);
				$(el).css("height",null);
				$(el).parent().css("display","block");
				$(el).parent().css("text-align","left");
				$(el).parent().css("vertical-align","baseline");
				el.src = ctxStatic+"/images/default_goods.png";
			}
		});
	}
	
	/**
	 * 按分辨率显示字号
	 */
	wx_common.resi=function(){ 
    	//querySelector选择器，该方法返回满足条件的单个元素。
        var html = document.querySelector("html");
        //clientWidth获取对象可见内容的宽度，不包括滚动条，不包括边框；
        //document.documentElement返回的是html元素，它是只读的。
        //document.body返回的就是body元素。
        var wW = document.body.clientWidth || document.documentElement.clientWidth;  
        var maxW = 640;  
        var minW = 320;  
        if (wW > maxW) wW = maxW;  
        var ratio = wW / minW;  
        //html.style.fontSize = 50 * ratio + "px"; 
        $(html).css("fontSize",50 * ratio + "px!important");
    }
	
})();

/**
 * 公共js
 */
//$(function(){
//	TagNav('#tagnav',{
//			type: 'scrollToFirst',
//	});
//	$('.weui_tab').tab({
//		defaultIndex: 0,
//		activeClass:'weui_bar_item_on',
//		onToggle:function(index){
//			if(index>0){
//				alert(index)
//			}
//		}
//	});
//	$('.searchbar_wrap').searchBar({
//		cancelText:"取消",
//		searchText:'关键字',
//		onfocus: function (value) {
//		   
//		},
//		onblur:function(value) {
//	
//		},
//		oninput: function(value) {
//	
//		},
//		onsubmit:function(value){
//		},
//		oncancel:function(){
//	
//		},
//	
//		onclear:function(){
//	
//		}
//	});
//});

/**
 * 按分辨率显示字号
 */
$(function  () {  
    //windowaddEventListener(event, function, useCapture) 方法，事件监听
    //第一个参数是事件的类型 (如 "click" 或 "mousedown").
    //第二个参数是事件触发后调用的函数。
    //第三个参数是个布尔值用于描述事件是冒泡还是捕获。该参数是可选的。
    //DOMContentLoaded 事件是当页面的初始dom tree加载好了就会执行。
    window.addEventListener("DOMContentLoaded", function() {
    	//querySelector选择器，该方法返回满足条件的单个元素。
        var bodys = document.querySelector("body").style;  
        bodys.opacity = "1";//opacity设置元素的不透明级别 
        bodys.filter = "alpha(opacity=100)";//filter 属性定义了元素(通常是<img>)的可视效果(例如：模糊与饱和度)。设置背景透明度 。 
        wx_common.resi();  
    });  
    //resize()对浏览器窗口调整大小进行计数,当调整浏览器窗口的大小时，发生 resize 事件
    window.addEventListener("resize", wx_common.resi); 
}); 

/**
 * 查看批发价(页面中的点击事件)
 */
$(document).on("click", ".viewTradePrice", function() {
	var pid = $(this).attr("pid");
	var isLogin = wxusm.isLogin();
	var isTypeUserPurchaser = wxusm.isTypeUserPurchaser();
	if(isLogin==true && isTypeUserPurchaser==true){
		location.href = ctxw + "/product/detail.htm?pid=" + pid;
		return false;
	}
	var content=$("#viewTradePriceModel").html();
	layer.open({content: content,btn: '取消'});
	if(isLogin==true && isTypeUserPurchaser==false){
		$(".layui-m-layer .viewTradePriceModel .weui_btn_area").css("display","none");
		return false;
	}
});
