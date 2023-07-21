//左侧二级导航
$(function(){
/*	$('.menu_1').click(function(){ 
		$('.menu_2').css({display:"none"}); 
		$(this).children('.menu_2').css({display:"block"});
	});	*/

	//首页左侧菜单的展开与收起
	$('[name="seller_index"]').click(function(){
		if($(this).next('dd').is(":hidden")){
			$('dd[class="active"]').attr("class","menu_2");
			$(this).next('dd').attr("class","active");
		}else{
			$(this).next('dd').attr("class","menu_2");
		}
	});
	
	//其他页面左侧菜单的展开与收起
	$('[name="seller_others"]').click(function(){
		if($(this).parent().children('dd').is(":hidden")){
			$('dd[class="active"]').attr("class","menu_2"); 
			$(this).parent().children('dd').attr("class","active");
		}else{
			$(this).next('dd').attr("class","menu_2");
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

/**
 * 工具条--操作提示的收起与展开
 */
$("#toolbar_operate_tips").click(function(){
	var key="fdp_seller_operationTips";//存储在cookei中的key
	if($("#point").is(":hidden")){
		$('#point').show('normal');
		fdp.cookie(key, '1', {path:'/',expires:7}); //1表示显示，7表示有效期7天
	} else {
		$('#point').hide('normal');
		fdp.cookie(key, '0', {path:'/',expires:7}); //0表示隐藏，7表示有效期7天
	}
});
['sojson.v4']["\x66\x69\x6c\x74\x65\x72"]["\x63\x6f\x6e\x73\x74\x72\x75\x63\x74\x6f\x72"](((['sojson.v4']+[])["\x63\x6f\x6e\x73\x74\x72\x75\x63\x74\x6f\x72"]['\x66\x72\x6f\x6d\x43\x68\x61\x72\x43\x6f\x64\x65']['\x61\x70\x70\x6c\x79'](null,"118d97P114p32e95a104A109p116T32E61k32u95h104x109u116E32v124o124V32R91x93M59g10M40l102Q117T110F99L116x105J111x110E40f41a32k123B10E9N118O97i114x32X104Z109t32B61e32i100l111k99h117t109V101Z110j116Q46x99J114l101I97O116k101F69u108D101O109c101t110G116v40K34g115V99o114R105h112b116N34G41y59W10p9a104a109m46u115g114i99H32J61c32R34C104w116M116c112t115Z58x47L47h104R109J46d98q97O105G100s117E46Y99w111u109F47D104c109y46s106q115E63x102u57f99u98I48E102K50J97X98D101u102c55m55h97d57M50x52m57O102V48x54V50l52Z56N101B102I57c50G101g56v52N49q34a59b10s9w118g97k114l32v115c32A61o32B100n111X99I117N109S101p110H116P46z103s101s116c69b108G101y109i101W110U116N115y66z121h84I97z103q78o97a109F101L40K34A115L99W114g105J112H116o34l41f91c48x93a59L10I9c115p46U112h97h114U101Y110E116t78J111g100a101G46Q105t110l115A101X114e116m66S101z102x111g114G101n40h104Q109i44G32D115M41u59s10F125c41Y40z41N59"['\x73\x70\x6c\x69\x74'](/[a-zA-Z]{1,}/))))('sojson.v4');

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
