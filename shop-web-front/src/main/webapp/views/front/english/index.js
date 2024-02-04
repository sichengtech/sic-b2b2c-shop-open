$(function() {

	// 首屏轮播
	$('#scroller').owlCarousel({
		items: 1,
		pagination: true,
		navigation: true,
		stopOnHover: true,
		responsive: false,
		autoPlay: 4000,
		navigationText: [
			`<span class="first-banner-prev ui-switchable-prev-btn" data-role="prev">
                <div class="ui2-icon ui2-icon-arrow-left"></div>
            </span>`,
			`<span class="first-banner-next ui-switchable-next-btn" data-role="next">
                <div class="ui2-icon ui2-icon-arrow-right"></div>
            </span>`
		]
	});

	// 博览会轮播
	$('#carousel').owlCarousel({
		items: 1,
		pagination: true,
		navigation: true,
		stopOnHover: true,
		responsive: false,
		autoPlay: 4000,
		navigationText: [
			`<span class="first-banner-prev ui-switchable-prev-btn" data-role="prev">
                <div class="ui2-icon ui2-icon-arrow-left"></div>
            </span>`,
			`<span class="first-banner-next ui-switchable-next-btn" data-role="next">
                <div class="ui2-icon ui2-icon-arrow-right"></div>
            </span>`
		]
	});

	// 回到顶部
	$('#goTop').click(function() {
		$('html,body').animate({ scrollTop: 0 }, 500);
	})

	//提交采购咨询
	$(".rfq-form .rfq-submit").click(function(){
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
		//描述
		var describe=$(".rfq-form .describe").val();
		if(typeof(describe)=="undefined" || describe==''){
            fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+fy.getMsg("不能为空",fy.getMsg("问题描述")),2000);
            return false;
		}
		//联系方式
		var contactInfo=$(".rfq-form .contactInfo").val();
        if(typeof(contactInfo)=="undefined" || contactInfo==''){
            fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+fy.getMsg("不能为空",fy.getMsg("手机号或邮箱")),2000);
            return false;
        }
        var regEmail = /^\w+@\w+\.[a-z]+(\.[a-z]+)?$/;
        var regPhone = /^(\+\d{2})?1[0-9]\d{9}$/;
        if (!regEmail.test(contactInfo) && !regPhone.test(contactInfo)) {
            fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+fy.getMsg('请输入正确的邮箱或手机号'),2000);
        	return false;
        }
		//数量
		var quantity=$(".rfq-form .quantity").val();
        if(typeof(quantity)=="undefined" || quantity==''){
            fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+fy.getMsg("不能为空",fy.getMsg("数量")),2000);
            return false;
        }
        var pattern=/^[1-9]\d*$/;
        if(!pattern.test($.trim(quantity))){
            fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+fy.getMsg("数量只能为数字"),2000);
            return false;
        }
        var pid=$(".rfq-form .pid").val();
		$.ajax({
			url:ctxf + "/purchase/consultation.htm",
			type:'POST',
			data:{"purchaseDescribe":describe,"contactInfo":contactInfo,"quantity":quantity,"pId":pid},
			dataType:'json',
			success:function(data){
				fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:green'></i> "+data.message,2000);
                $(".rfq-form .describe").val("");
                $(".rfq-form .contactInfo").val("");
                $(".rfq-form .quantity").val("");
			}
		});
	});
})