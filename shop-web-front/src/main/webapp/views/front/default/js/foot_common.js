/**
 * jQuery.validator添加正则验证规则
 */
(function(){
	jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
		function(value, element, params) { //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数） 
		var exp = new RegExp(params); //实例化正则对象，参数为传入的正则表达式 
		return this.optional(element) || exp.test(value); //测试是否匹配 
		}, 
		"格式错误"); //addMethod第3个参数:默认错误提示信息
})();

var _hmt = _hmt || [];
(function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?f9cb0f2abef77a9249f06248ef92e841";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();

/**
 * 前天展示服务端返回的提示信息，
 * 相当于seller和member系统的<sys:message>标签
 * */
function sysMessage(){
	var ctype="";
	if((typeof(type)=="undefined" || type=="") && typeof(content)!="undefined" && content!=""){
		ctype=content.indexOf('失败')=='-1'?'1':'0';
	}
	(function(){
		if(typeof(content)!="undefined" && content!=""){
			if(ctype=="1"){
				var icon="<i class='fa fa-smile-o' style='font-size:24px;color:green'></i>";
				var t=3*1000;
			}else{
				var icon="<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>";
				var t=20*1000;
			}
			fdp.msg(icon+content,t);
		}
	})();
}