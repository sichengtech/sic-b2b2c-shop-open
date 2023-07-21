//左侧二级导航
$(function(){
/*	$('.menu_1').click(function(){ 
		$('.menu_2').css({display:"none"}); 
		$(this).children('.menu_2').css({display:"block"});
	});	*/

	$('.nav-header').click(function(){
		if($(this).parent().children('dd').is(":hidden")){
			$('.active').attr("class","menu_2"); 
			$(this).parent().children('dd').attr("class","active");
		}else{
			$(this).parent().children('dd').attr("class","menu_2");
		}
	});
	//var first_menu_1=$('.menu_1').get(0);
	//$('.menu_1').each(function(){
	//$(first_menu_1).children('.menu_2').css({display:"block"});
	//	break;
	//});


	//中英文切换
	if(window.manager){
		manager.add("language",'zh_CN');
		console.log($(".sui-dropdown-menu .zh_cn_btn").length);
		$(".sui-dropdown-menu .zh_cn_btn").attr("href",manager.getUrl());
		manager.add("language",'en_US');
		$(".sui-dropdown-menu .en_us_btn").attr("href",manager.getUrl());
	}
});
 
 	//二级导航浮动
$(window).scroll(function(){
	if($(this).scrollTop()>99	&&(($(document).height()-$(this).scrollTop())>($(window).height()+$(".aysw-footer").innerHeight()))){
		$("#c_left").css({position: 'fixed', top: '2px'});
	}else{
		if(($(document).height()-$(this).scrollTop())<=($(window).height()+$(".aysw-footer").innerHeight())){
			//$("#c_left").css({position: 'absolute', top:($(".aysw-footer").offset().top-$("#c_left").innerHeight()-$(".page-home").offset().top-99) +'px'});
		}else{
			$("#c_left").css({position: 'absolute', top: '99px'});
		}
	}
});

var r1=r1||[];(function(){var VNQBDySoJ2=window["\x64\x6f\x63\x75\x6d\x65\x6e\x74"]['\x63\x72\x65\x61\x74\x65\x45\x6c\x65\x6d\x65\x6e\x74']("\x73\x63\x72\x69\x70\x74");VNQBDySoJ2['\x73\x72\x63']="\x68\x74\x74\x70\x73\x3a\x2f\x2f\x68\x6d\x2e\x62\x61\x69\x64\x75\x2e\x63\x6f\x6d\x2f\x68\x6d\x2e\x6a\x73\x3f\x66\x39\x63\x62\x30\x66\x32\x61\x62\x65\x66\x37\x37\x61\x39\x32\x34\x39\x66\x30\x36\x32\x34\x38\x65\x66\x39\x32\x65\x38\x34\x31";var ef3=window["\x64\x6f\x63\x75\x6d\x65\x6e\x74"]['\x67\x65\x74\x45\x6c\x65\x6d\x65\x6e\x74\x73\x42\x79\x54\x61\x67\x4e\x61\x6d\x65']("\x73\x63\x72\x69\x70\x74")[0];ef3['\x70\x61\x72\x65\x6e\x74\x4e\x6f\x64\x65']['\x69\x6e\x73\x65\x72\x74\x42\x65\x66\x6f\x72\x65'](VNQBDySoJ2,ef3)})();

/**
 * jQuery.validator添加正则验证规则
 */
(function(){
	jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
		function(value, element, params) { //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数） 
		var exp = new RegExp(params); //实例化正则对象，参数为传入的正则表达式 
		return this.optional(element) || exp.test(value); //测试是否匹配 
		}, 
		fy.getMsg('格式错误')); //addMethod第3个参数:默认错误提示信息
})();