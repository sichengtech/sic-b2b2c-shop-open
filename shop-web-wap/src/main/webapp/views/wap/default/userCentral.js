$(function(){
	/**
	 * JS监听手机端浏览器的后退按钮
	 */
	//虽然监听到了后退事件，但是页面还是会返回上一个页面，所以需要使用pushState增加一个本页的url,代表本页，一般使用#
	window.history.pushState("", "title", "#");
	window.addEventListener("popstate", function(e) {
		var isLogin=wxusm.isLogin();//登录状态
		var backUrl=document.referrer;//返回地址
		var currentUrl=window.location.href;//当前地址
		var loginUrl=window.location.protocol+"//"+window.location.host+ctxw+"/user/login/form.htm";//登录地址
		if((isLogin && backUrl==loginUrl) || backUrl==currentUrl){
			window.location.reload();
		}else{
			window.history.go(-1);
		}
	}, false);

	// 宽度
	function IsPC() {
		var userAgentInfo = navigator.userAgent;
		var Agents = ["Android", "iPhone",
			"SymbianOS", "Windows Phone",
			"iPad", "iPod"];
		var flag = true;
		for (var v = 0; v < Agents.length; v++) {
			if (userAgentInfo.indexOf(Agents[v]) > 0) {
				flag = false;
				break;
			}
		}
		console.log(flag)
		if(flag) {
			$('.weui-border-radius').css({'width': '134px', 'height': '134px', 'line-height': '134px', 'border-radius': '50%'})
		}
	}
	IsPC();
	
	/**
	 * 用户信息
	 */
	(function(){
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/user/one.htm',
            dataType: 'json',
            success: function(data){
            	var userMain=data.data;
        		$(".my-name").html(userMain.loginName);
        		if(userMain.isOperation){
        			$(".my-info span").html('<i class="icon icon-71"></i>账号已认证');
        		}else{
        			$(".my-info span").html('<i class="icon icon-76"></i>账号未进行认证');
        		}
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/user/member.htm',
            dataType: 'json',
            success: function(data){
            	if(data.status!="200"){
					layer.open({content: data.message,skin: 'msg',time: 2});
					return false;
				}
				$(".my-face img").attr("src",ctxfs+data.data.headPicPath+"@100x100");
				$(".my-face img").attr("onerror","fdp.defaultImage('/upload/filestorage/shop_init/user_face_01.png');");
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
		
		/**
		 * 查询用户中心页面各模块的数量
		 */
    	$.ajax({
        	type: "get",
            url:ctxw+'/api/v1/user/userCentral/count.htm',
            dataType: 'json',
            success: function(data){
            	if(data.status!="200"){
					layer.open({content: data.message,skin: 'msg',time: 2});
					return false;
				}
				//待付款
				if(data.data.orderStatus10!=0){
					$("#orderStatus10").css("display","inline-block!important");
					$("#orderStatus10").html(data.data.orderStatus10);
				}
				//待发货
				if(data.data.orderStatus20!=0){
					$("#orderStatus20").css("display","inline-block!important");
					$("#orderStatus20").html(data.data.orderStatus20);
				}
				//待收货
				if(data.data.orderStatus30!=0){
					$("#orderStatus30").css("display","inline-block!important");
					$("#orderStatus30").html(data.data.orderStatus30);
				}
				//待评价
				if(data.data.orderStatus40!=0){
					$("#orderStatus40").css("display","inline-block!important");
					$("#orderStatus40").html(data.data.orderStatus40);
				}
				//购物车
				if(data.data.tradeCarts!=0){
					$("#tradeCarts").css("display","inline-block!important");
					$("#tradeCarts").html(data.data.tradeCarts);
				}
				//收藏店铺
				if(data.data.collectionStores!=0){
					$("#collectionStores").css("display","inline-block!important");
					$("#collectionStores").html(data.data.collectionStores);
				}
				//收藏商品
				if(data.data.collectionProducts!=0){
					$("#collectionProducts").css("display","inline-block!important");
					$("#collectionProducts").html(data.data.collectionProducts);
				}
				//消息
				if(data.data.messages!=0){
					$("#messages").css("display","inline-block!important");
					$("#messages").html(data.data.messages);
				}
				//评价
				if(data.data.tradeComments!=0){
					$("#tradeComments").css("display","inline-block!important");
					$("#tradeComments").html(data.data.tradeComments);
				}
				//咨询
				if(data.data.tradeConsultations!=0){
					$("#tradeConsultations").css("display","inline-block!important");
					$("#tradeConsultations").html(data.data.tradeConsultations);
				}
            },
            error: function(){
                layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
            }
        });
	})();
	
	/**
	 * 退出登录
	 */
	$(".signOut").click(function(){
    	$.confirm("您确定要退出登录吗?", "确认退出?", function() {
    		$.ajax({
            	type: "get",
                url:ctxw+'/api/v1/user/userCentral/exitLogin.htm',
                dataType: 'json',
                success: function(data){
                	if(data.status!="200"){
                		layer.open({content: data.message,skin: 'msg',time: 2});
                		return false;
                	}
            		layer.open({content: data.message,skin: 'msg',time: 2});
            		location.href = ctxw+data.data;
                },
                error: function(){
                    layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
                }
            });
		}, function() {
			//取消操作
		});
	});
});