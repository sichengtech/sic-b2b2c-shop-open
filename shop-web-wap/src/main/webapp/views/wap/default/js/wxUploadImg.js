/**
 * 微信用户登录状态管理器
 * @author 张加利
 * @version 2017-12-21
 */
var configData=null;

(function(){
	/**
	 * wx命名空间,起隔离作用，防止同名变量冲突
	 */
	if(!window.weixin) {window.weixin={};};
	
	/**
	 * 获取微信的appId、timestamp、nonceStr、signature
	 * */
	weixin.info=function(){
		if(configData!=null){
			return configData;
		}
		console.log("地址："+window.location.href);
		$.ajax({
    		url:ctxw+"/api/v1/weixin/info.htm",
    		type:'GET',
    		data:{"url":window.location.href},
    		dataType:'json',
    		async: false,
    		success:function(data){
    			if(data==null || data.status!='200' || data.data==null){
    				return;
    			}
    			configData=data.data;
    		}
		});
		return configData;
	}
	
	/**
	 * 下载图片
	 * @param mediaIds 图片在微信服务器上对应的id
	 * @param $imgPath_input 存图片地址的input
	 * */
	weixin.downloadImg = function(mediaIds,$imgPath_input){
		var flag=false;
		if(typeof(mediaIds)=="undefined" || mediaIds==""){
			layer.closeAll();
			layer.open({content: 'mediaIds不能为空',skin: 'msg',time: 2});
			return flag;
		}
		var token=weixin.getToken();
		if(token=="" || token==null){
			layer.closeAll();
			layer.open({content: 'token不能为空',skin: 'msg',time: 2});
			return flag;
		}
		$.ajax({
    		url:ctxu+"/common/downloadImage.htm",
    		type:'POST',
    		data:{"accessToken":weixin.info().accessToken,"mediaIds":mediaIds,"token":token},
    		dataType:'json',
    		async: false,
    		success:function(data){
    			if(data==null || !data.status || data.path==""){
    				return;
    			}
    			var paths=$imgPath_input.val();
    			$imgPath_input.val(paths+data.path);
    			flag=true;
    		}
		});
		return flag;
	}
	
	/**
	 * 下载图片之前获取token获取token
	 * */
	weixin.getToken = function(){
		var token="";
		$.ajax({
    		url:ctxw+"/api/v1/sys/getToken.htm",
    		type:'POST',
    		dataType:'json',
    		async: false,
    		success:function(data){
    			if(data==null){
    				return;
    			}
    			if(data.status=='401'){
    				wx_common.routerLogin();
    				return;
    			}
    			if(data.status!='200' || data.data==null){
    				layer.open({content: data.message!=""?data.message:'获取token失败',skin: 'msg',time: 2});
    				return;
    			}
    			token=data.data;
    		}
		});
		return token;
	}
})();

/**
 * 微信上传图片
 * */
wx.config({
     //debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	 appId: weixin.info().appId, // 必填，公众号的唯一标识
     timestamp: weixin.info().timestamp, // 必填，生成签名的时间戳
     nonceStr: weixin.info().nonceStr, // 必填，生成签名的随机串
     signature: weixin.info().signature,// 必填，签名，见附录1
     jsApiList: ['chooseImage','uploadImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
 });
