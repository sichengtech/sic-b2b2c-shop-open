$(function(){
	$("#inputForm").submit(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		if(username==null || username==''){
			layer.msg(fy.getMsg('用户名不能为空'));
			return false;
		}
		if(password==null || password==''){
			layer.msg(fy.getMsg('密码不能为空'));
			return false;
		}
		var index = layer.load(0, {shade: false});
		return true;
	});
});

//如果在框架或在对话框中，则弹出提示并跳转到首页
if (self.frameElement && self.frameElement.tagName == "IFRAME"
		|| $('#left').length > 0 || $('.jbox').length > 0) {
	layer.msg(fy.getMsg('未登录或登录超时。请重新登录，谢谢！'));
	top.location = "${ctxa}/login.do";
}