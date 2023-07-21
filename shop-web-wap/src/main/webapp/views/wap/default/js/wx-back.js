/**
 * 页面返回按钮的点击事件
 */
$(function(){
	$("body").delegate(".click_back","click",function(){
		var isLogin=wxusm.isLogin();//登录状态
		var backUrl=document.referrer;//返回地址
		var currentUrl=window.location.href;//当前地址
		var loginUrl=window.location.protocol+"//"+window.location.host+ctxw+"/user/login/form.htm";//登录地址
		if((isLogin && backUrl==loginUrl) || backUrl==currentUrl){
			window.location.reload();
		}else{
			window.history.go(-1);
		}
	});
});
