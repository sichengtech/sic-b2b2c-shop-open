$(function () {
	var speed = 50,
		newTop = 0,
		ZJJDemo = $('#viphy-g'),
		ZJJDemo1 = $('#viphy1'),
		ZJJDemo2 = $('#viphy2');
	if ($('#viphy1').height() >= 280) {
		ZJJDemo2.html(ZJJDemo1.html())
	}

	function Marquee1() {
		newTop -= 1;
		if (Math.abs(newTop) > ZJJDemo1.innerHeight()) {
			newTop = 0;
		}
		ZJJDemo.css({
			'top': newTop + 'px'
		});
	}
	if ($('#viphy1').height() >= 280) {
		var MyMar1 = setInterval(Marquee1, speed)
	}
	
	ZJJDemo.mouseover(function () {
		clearInterval(MyMar1)
	})
	ZJJDemo.mouseout(function () {
		if ($('#viphy1').height() >= 280) {
			MyMar1 = setInterval(Marquee1, speed)
		}
		
	})

	var a = fy.getMsg('写下您的真实需求，包话产品、型号、品牌等相关参数，收到后我们立即给您回电话确认，剩下的就交给我们吧！'),
		content = null;
	content = $("#content").val();
	if (content == null || content == '') {
		$("#content").val(a);
		$("#content").css("color", "#B3B3B3");
	}
	$("#content").focus(function () {
		content = $("#content").val();
		if (content == a) {
			$("#content").val("");
			$("#content").css("color", "black");
		}
	});
	$("#content").blur(function () {
		content = $("#content").val();
		if (content == '') {
			$("#content").val(a);
			$("#content").css("color", "#B3B3B3");
		}
	});

	/**
	 * 一句话发布采购
	 * */
	/*$("#fabu1").click(function () {
		var status = usm.isLogin();//usm-判断用户是否登录
		if(status == false){
	    	layer.open({
			  type: 2,
			  title: '',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['500px', '390px'],
			  content:ctxf+"/custom/login.htm"
			});
	    	return false;
		}
		var content = $("#content").val();
		var length = $("#content").val().length;
		if (content == null || content == '' || a == content) {
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 请输入采购内容",2000);
			return;
		}
		if (length >= 2000) {
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> 内容过大，需要在2000个字符",2000);
			return;
		} else {
			$("#inputForm1").submit();
		}
	});*/

	/**
	 * 报价
	 * */
	$(".offer").click(function(){
		//采购商id
		var uId=$(this).attr("uId");
		var status=$(this).attr("status");
		//报价人id
		var uId2=$("input[name='uId']").val();
		//非交易中的采购单不能报价
		if("35" != status){
			return false;
		}
		//不能给自己发的采购单报价
		if(uId2!="" && uId2==uId){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+fy.getMsg('不能给自己发的采购单报价'),2000);
			return false;
		}
		var purchaseId=$(this).attr("purchaseId");
		window.location.href=ctxf+'/purchase/offer.htm?purchaseId='+purchaseId;
	});
	
})