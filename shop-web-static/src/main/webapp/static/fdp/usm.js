/**
 * 用户登录状态管理器
 * @author 蔡龙
 * @version 2017-06-26
 */
(function(){
	/**
	 * usm命名空间,起隔离作用，防止同名变量冲突
	 * usm 代表 user status manager(用户状态管理器)
	 */
	if(!window.usm) {window.usm={};};
	
	/**
	 * 写入cookie
	 * name  	cookie的键
	 * value	cookie的值
	 * expires 	cookie有效时间，不写代表关闭浏览器失效
	 */
	usm.putCookie = function(name, value, expires){
		fdp.cookie(name, value, {path:'/',expires:expires});
	};
	
	/**
	 * 取出cookie
	 */
	usm.getCookie = function(name){
		return fdp.cookie(name);
	};
	
	/**
	 * ajax获取登录信息并存入cookie
	 */
	usm.getUserMain = function(callback){
		var loginName = usm.getCookie('usm.loginName');
		var isloginInvalid = usm.getCookie('usm.isloginInvalid');
		if((loginName==null || loginName=='') || (isloginInvalid==null || isloginInvalid=='')){
			$.ajax({																	
				type: 'post',
				url: ctxf + '/method/getUserMain.htm',
				dataType: 'json',
				success: function(data){
					usm.putCookie('usm.loginName', data.loginName);//用户名(临时存储)
					usm.putCookie('usm.headPicPath', data.headPicPath);//头像(临时存储)
					usm.putCookie('usm.isTypeUserPurchaser', data.isTypeUserPurchaser);//是否为采购商(临时存储)
					usm.putCookie('usm.isloginInvalid', "true", 0.02);//是否登录失效(持久性存储30分钟)
					if($.isFunction(callback)){
						callback();
					}
				},
				error: function(data){
					return false;
				}
			});
		}
	};
	
	/**
	 * 修改页面登录状态  
	 */
	usm.changeTopBar = function(){
		var loginName = usm.getCookie('usm.loginName');
		var headPicPath = usm.getCookie('usm.headPicPath');
		var isloginInvalid = usm.getCookie('usm.isloginInvalid');
		if(loginName!=null && loginName!="" && isloginInvalid!=null && isloginInvalid!=""){
			//修改顶部小灰条
			$(".welcomeGray").html("");
			$(".welcomeGray").html("<li><a target=\"_blank\" href=\""+ctxm+"/index.htm\">您好："+loginName+"</a></li><li><a href=\""+ctxsso+"/logout.htm\">退出</a></li>");
		}
	};
	
	/**
	 * 判断用户登录状态 
	 * 登录   true
	 * 未登录 false
	 */
	usm.isLogin = function(){
		var loginName = usm.getCookie('usm.loginName');
		var isloginInvalid = usm.getCookie('usm.isloginInvalid');
		var status = true;
		if((loginName==null || loginName=='') || (isloginInvalid==null || isloginInvalid=='')){
			status=false;
		}
		return status;
	};
	
	/**
	 * ajax获取批发价
	 */
	usm.getPrice = function(pId,callback){
		var isTypeUserPurchaser = usm.getCookie('usm.isTypeUserPurchaser');
		if(isTypeUserPurchaser=="true"){
			$.ajax({																	
				type: 'post',
				url: ctx +'/method/getPrice.htm?pId='+pId,
				dataType: 'json',
				success: function(data){
					if($.isFunction(callback)){
						callback(data);
					}
				},
				error: function(data){
					return false;
				}
			});
		}
	};
	
	/**
	 * 修改价格
	 */
	usm.changePrice = function(data){
		var isTypeUserPurchaser = usm.getCookie('usm.isTypeUserPurchaser');
		if(isTypeUserPurchaser=="true"){
			if(data !=undefined){
				$(".viewTradePrice").remove();
				var html = '<div class=\"summary-def summary-price\"><span class=\"dt\">批发价：</span><span class=\"dd\"><small>¥</small>'+data.minPrice2+'<small>~</small>'+data.maxPrice2+'</span></div>'
				$(".wholesale").append(html);
			}
		}
	};
	
	/**
	 * 判断用户是否是采购商
	 */
	usm.isTypeUserPurchaser = function(){
		var isTypeUserPurchaser = usm.getCookie('usm.isTypeUserPurchaser');
		if(isTypeUserPurchaser=="true"){
			return true;
		}else{
			return false;
		}
	};
	
})();

/**
 * 页面加载完成时执行
 */
$(function(){
	usm.getUserMain(function(){
		usm.changeTopBar();
	});
	var status = usm.isLogin();
	if(status == true){
		usm.changeTopBar();
	}
});