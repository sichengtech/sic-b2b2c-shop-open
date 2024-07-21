/**
 * 微信用户登录状态管理器
 * @author zjl
 * @version 2017-12-21
 */
(function(){
	/**
	 * usm命名空间,起隔离作用，防止同名变量冲突
	 * usm 代表 user status manager(用户状态管理器)
	 */
	if(!window.wxusm) {window.wxusm={};};
	
	/**
	 * 操作cookie的通用函数
	 * 
	 * 示例0：写入一个临时cookie，关闭浏览器后失效 fdp.cookie('键', '值');
	 * 示例1：写入一个持久cookie，fdp.cookie(key, '0', {path:'/',expires:7}); //7表示有效期7天
	 * 示例2：删除cookie  fdp.cookie(key, '0', {path:'/',expires:-1}); //清空，把时间设置为负数，会让cookie立即过期
	 * （注意path要相同才能删除成功，如果cookie存储的path是/，删除时则要指定path为/）
	 * 示例3：写入一个cookie，7天后失效  fdp.cookie('键', '值', {path:'/',expires:7});//7表示有效期7天
	 * 示例4：写入一个cookie，指定域名、路径、有效期 fdp.cookie('键', '值', {domain:"www.test.com",path:'/',expires:7});
	 * 
	 * options.domain:指定域名
	 * options.path:指定路径，一般可指定/,若为null则写入当前页面的URL中取出当前路径
	 * options.expires:有效期，单位天
	 * options.secure:安全，true表示，该cookie只会在https下发送，而不会再http下发送
	 * 
	 * 示例5：取cookie, fdp.cookie('键') ,不能指定path路径,一个key存储在多个路径下，先返回目录层级长的。
	 */
	wxusm.cookie=function (name, value, options) {
		if (typeof value != 'undefined') { // name and value given, set cookie
			options = options || {};
			if (value === null) {
				value = '';
				options.expires = -1;
			}
			var expires = '';
			if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
				var date;
				if (typeof options.expires == 'number') {
					date = new Date();
					date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
				} else {
					date = options.expires;
				}
				expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
			}
			var path = options.path ? '; path=' + options.path : '';
			var domain = options.domain ? '; domain=' + options.domain : '';
			var secure = options.secure ? '; secure' : '';
			document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
		} else { // only name given, get cookie
			var cookieValue = null;
			if (document.cookie && document.cookie != '') {
				var cookies = document.cookie.split(';');
				for (var i = 0; i < cookies.length; i++) {
					var cookie = $.trim(cookies[i]);
					// Does this cookie string begin with the name we want?
					if (cookie.substring(0, name.length + 1) == (name + '=')) {
						cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
						break;
					}
				}
			}
			return cookieValue;
		}
	}
	
	
	/**
	 * 取出cookie
	 */
	wxusm.getCookie = function(name){
		return wxusm.cookie(name);
	};
	
	/**
	 * 获取用户id 
	 * 登录   true
	 * 未登录 false
	 */
	wxusm.uid = function(){
		return wxusm.getCookie('wxusm.uid');;
	};
	
	/**
	 * 判断用户登录状态 
	 * 登录   true
	 * 未登录 false
	 */
	wxusm.isLogin = function(){
		var uid = wxusm.getCookie('wxusm.uid');
		var isloginInvalid = wxusm.getCookie('wxusm.isloginInvalid');
		var status = true;
		if((uid==null || uid=='') || (isloginInvalid==null || isloginInvalid=='')){
			status=false;
		}
		return status;
	};
	
	/**
	 * 判断用户是否是采购商
	 */
	wxusm.isTypeUserPurchaser = function(){
		var isTypeUserPurchaser = wxusm.getCookie('wxusm.isTypeUserPurchaser');
		if(isTypeUserPurchaser=="true"){
			return true;
		}else{
			return false;
		}
	};
})();
