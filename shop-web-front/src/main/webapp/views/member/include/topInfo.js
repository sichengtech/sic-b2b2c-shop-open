$(function(){
	//获取代办数量修改头部
	$.ajax({
    	type: "get",
        url:ctxm+'/user/member/info.htm',
        dataType: 'json',
        success: function(data){
        	//头像
        	$(".face img").attr("src",ctxfs+data.userMember.headPicPath+"@!97x97");
        	//登录名
        	$(".face-info h3").html(data.userMain.loginName);
        	//登录身份
        	var identity = "";
        	if(data.userMain.typeUserPurchaser==true || data.userMain.typeUserSeller==true || data.userMain.typeUserService==true){
        		if(data.userMain.typeUserPurchaser==true){
        			identity+=",";
        			identity+=fy.getMsg("采购商");
        		}
        		if(data.userMain.typeUserSeller==true){
        			identity+=",";
        			identity+=fy.getMsg("供应商");
        		}
        		if(data.userMain.typeUserService==true){
        			identity+=",";
        			identity+=fy.getMsg("门店");
        		}
        		identity = identity.substr(1);
        	}else{
        		identity = fy.getMsg("个人");
        	}
        	$(".face-info .identity").html(identity);
        	//账号安全
        	var accountSecurity = "";
        	if(data.userMain.emailValidate=='1' || data.userMain.mobileValidate=='1'){
        		accountSecurity = fy.getMsg("安全");
        	}else{
        		accountSecurity="<a href="+ctx+"'/sso/index.htm' target='_blank'>"+fy.getMsg('未绑定')+"</a>";
        	}
        	$(".face-info .accountSecurity").find('b').html(accountSecurity);
			//是否认证
			if(data.userMain.typeUserPurchaser==true || data.userMain.typeUserSeller==true){
				$(".face-info .isAuthentication").css("display","block");
			}
			//待付款
			$(".status10Count b").html(data.status10Count);
			//待收货
			$(".status30Count b").html(data.status30Count);
			//待评价
			$(".status40Count b").html(data.status40Count);
			//收藏店铺
			$(".memberCollectionStoreCount b").html(data.memberCollectionStoreCount);
			//收藏商品
			$(".memberCollectionProductCount b").html(data.memberCollectionProductCount);
			//我的足迹
			$(".myFootprint b").html(data.myFootprint);
			//退款退货
			$(".isReturnStatusCount1 b").html(data.isReturnStatusCount1);
			//退款
			$(".isReturnStatusCount2 b").html(data.isReturnStatusCount2);
			//投诉
			$(".tradeComplaintCount b").html(data.tradeComplaintCount);
        },
        error: function(){
            layer.open({content: "很遗憾，出错啦，请稍后再试!",skin: 'msg',time: 2});
        }
    });
});