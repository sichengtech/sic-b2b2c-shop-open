$(function(){
	/**
	 * 隐藏、展开点击事件
	 */	
	$(".retract").click(function(){
		var ul=$(this).parent().parent().siblings("ul");
		if($(ul).is(":hidden")){
			$(ul).css("display","");
			$(this).children("span").text(fy.getMsg("隐藏"));
			$(this).children("i").attr("class","sui-icon icon-tb-unfold");
		}else{
			$(ul).css("display","none");
			$(this).children("span").text(fy.getMsg("展开"));
			$(this).children("i").attr("class","sui-icon icon-tb-fold");
		}
	});
	
	/**
	 * 全选：全选控制每行记录
	 */	
	$(".productCheckAll").click(function(){
		if ($(this).prop("checked")) {
			$(".input-check").prop("checked", true);
			$(".productCheckAll").not(this).prop("checked", true);
		}else {
			$(".input-check").prop("checked", false);
			$(".productCheckAll").not(this).prop("checked", false);
		}
		calculation();
	});
	 
	/**
	 * 每行记录控制全选
	 */
	$(".input-check").click(function(){
		var isTitle=$(this).attr("isTitle");
		if ($(this).prop("checked")) {
			if(isTitle == '1'){
				$(this).parents(".store").find("input[isTitle='0']").prop("checked", true);
			}else if(isTitle == '0'){
				var notchecked=$(this).parents(".store").find("input[isTitle='0']").not("input:checked");  
				if(notchecked.length>0){
					$(this).parents(".store").find("input[isTitle='1']").prop("checked", false);
				}else{
					$(this).parents(".store").find("input[isTitle='1']").prop("checked", true);
				}
			}
			var bl1=true;
			//找到其他行记录
			$(".input-check").not(this).not($(".productCheckAll")).each(function(){
				if (!$(this).prop("checked")){
					bl1=false;
				}
			});
			if(bl1){
				$(".productCheckAll").prop("checked",true);
			}
		}else {
			$(".productCheckAll").prop("checked",false);
			if(isTitle == '1'){
				$(this).parents(".store").find("input[isTitle='0']").prop("checked", false);
			}else if(isTitle == '0'){
				$(this).parents(".store").find("input[isTitle='1']").prop("checked", false);
			}
		}
		calculation();
	});
	
	/**
	 * 获取要购买的商品数量及价格
	 */
	function calculation(){
		//商品种类
		var productCate=0;
		//商品总数量
		var productCount=0;
		//商品总价
		var productPrice=0;
		$("input[isTitle='0']:checked").each(function(){
			productCate+=1;
			var count=$(this).parent().parent().find(".con_6 .buyNum").val();
			if(count=="" || typeof(count)=="undefined"){
				count=0;
			}
			var counti=parseInt(count);
			productCount+=counti;
			var price=$(this).parent().parent().find(".price").text();
			if(price==""|| typeof(price)=="undefined"){
				price=0;
			}
			var pricef=parseFloat(price);
			productPrice+=pricef*counti;
		});
		$("span[class='productCate']").text(productCate);
		$("span[class='productCount']").text(productCount);
		$("span[class='productSumPrice']").text(productPrice.toFixed(2));
	}
	
	/**
	 * 删除提示
	 */
	$(".deleteSure").click(function(){
		var href=$(this).attr("href1");
		fdp.confirm(fy.getMsg('确定要删除么'),href);
	});
	
	/**
	 * 批量删除
	 */
	$(".batchDele").click(function(){
		var cartIds="";
		$("input[isTitle='0']:checked").each(function(){
			cartIds+=$(this).attr("cartId")+",";
		});
		if(cartIds == ""){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('请选择要删除的商品'),2000);
			return false;
		}
		var href=$(this).attr("href1")+"?cartIds="+cartIds+"&isBatch=1";
		fdp.confirm(fy.getMsg('确定要删除么'),href);
	});
	
	/**
	 * 结算
	 */
	$("#buyBtn").click(function(){
		var cartIds="";
		var priceSum=$("span[class='productSumPrice']").text();
		var userMemberId=$("input[name='userMemberId']").val();
		var flag=true;
		var msg="";

		//同一个spu的数量
		var map=new Map();
		$("input[isTitle='0']:checked").each(function(){
			cartIds+=$(this).attr("cartId")+",";
			var userSellerId=$(this).siblings("input[name='userSellerId']").val();
			//购买数量
			var buyNum=$(this).parent().parent().find("input[class='buyNum']").val();
			var stock=$(this).parent().parent().find(".countBtn .stock").val();
			//验证库存
			if(stock=="" || typeof(stock)=="undefined"){
				flag=false;
				msg=fy.getMsg("库存不能为空");
				return false;
			}
			if(parseInt(buyNum) > parseInt(stock)){
				flag=false;
				msg=fy.getMsg("库存不足");
				return false;
			}
			//起购量
			var purchasingAmount=$(this).attr("purchasingAmount");
			if(userSellerId==userMemberId){
				flag=false;
				msg=fy.getMsg("不能购买自己店铺的商品");
				return false;
			}
			if(buyNum=="" || typeof(buyNum)=="undefined"){
				flag=false;
				msg=fy.getMsg("购买数量不能为空");
				return false;
			}

			var pid=$(this).attr("pid");
			if(typeof(map.get(pid))=="undefined" || map.get(pid)=="undefined"){
				map.set(pid,0);
			}
			var count=parseInt(map.get(pid));
			map.set(pid,count+parseInt(buyNum));

			//获取同pid数量
			var buyNum2=parseInt(buyNum);
			$(".cartSub[pid='"+pid+"']:checked").not(this).each(function(){
				var buyNum3=$(this).parent().parent().find("input[class='buyNum']").val();
				buyNum2=buyNum2+parseInt(buyNum3);
			});
			if(purchasingAmount=="" || typeof(purchasingAmount)=="undefined"){
				flag=false;
				msg=fy.getMsg("起购量不能为空");
				return false;
			}
			if(parseInt(buyNum2)<parseInt(purchasingAmount)){
				flag=false;
				msg=fy.getMsg("购买数量必须大于起购量，起购量是")+purchasingAmount;
				return false;
			}
		});
		if(!flag){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i> "+msg,2000);
			return false;
		}
		if(cartIds == ""){
			fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg('请选择要购买的商品'),2000);
			return false;
		}
		$("input[name='ids']").val(cartIds);
		$("input[name='priceSum']").val(priceSum);
		$("#cartFrom").submit();
	});
	
	/**
	 * 减数量
	 */
	$(".countBtn .r").click(function(){
		var num=parseInt($(this).siblings(".input").find("input[class='buyNum']").val());
    	var minStock=1;
    	if(num<=minStock){
    		return false;
    	}
    	var count=num-1;
		$(this).siblings(".input").find("input[class='buyNum']").val(count);
		$(this).siblings(".input").find("input[class='buyNum']").attr("value2",count);
    	var price=$(this).parent().parent().siblings(".con_5").find(".price").text();
    	var priceSum=price*count;
    	$(this).parent().parent().siblings(".con_7").text(fy.getMsg("元")+priceSum.toFixed(2));
    	var cartId=$(this).parent().parent().siblings(".con_1").find(".input-check").attr("cartId");
    	//修改数据库中的值
    	editCount(cartId,count,priceSum);
    	//修改价格
    	calculation();
	});
	
	/**
	 * 加数量
	 */
	$(".countBtn .l").click(function(){
		var num=parseInt($(this).siblings(".input").find("input[class='buyNum']").val());
    	var stock=$(this).siblings(".stock").val();
    	if(num>=stock){
    		fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg("库存不足"),2000);
    		return false;
    	}
    	var count=num+1;
    	$(this).siblings(".input").find("input[class='buyNum']").val(count);
    	$(this).siblings(".input").find("input[class='buyNum']").attr("value2",count);
    	var price=$(this).parent().parent().siblings(".con_5").find(".price").text();
    	var priceSum=price*count;
    	$(this).parent().parent().siblings(".con_7").text(fy.getMsg("元")+priceSum.toFixed(2));
    	var cartId=$(this).parent().parent().siblings(".con_1").find(".input-check").attr("cartId");
    	//修改数据库中的值
    	editCount(cartId,count,priceSum);
    	//修改价格
    	calculation();
	});
	
	/**
	 * 数量改变事件
	 * */
	$(".countBtn .buyNum").change(function(){
		var count=$(this).val()*1;
		var stock=$(this).parent().siblings(".stock").val()*1;
		var oldCount=$(this).attr("value2");
		var minStock=1;
    	//如果购买数量不是数字，把购买数量置为原来的值
    	var pattern=/^[1-9]\d*$/;
    	if(!pattern.test($(this).val())){
    		$(this).val(oldCount);
    		return false;
    	}
    	//如果购买数量小于1,把购买数量置为原来的值
    	if(count<minStock){
    		$(this).val(oldCount);
    		return false;
    	}
    	//如果购买数量大于库存量,把购买数量置为库存量
    	if(count>stock){
    		$(this).val(stock);
    	}
    	var count2=$(this).val();
    	var price=$(this).parents(".con_6").siblings(".con_5").find(".price").text();
    	var priceSum=count2*price;
    	$(this).parents(".con_6").siblings(".con_7").text(fy.getMsg("元")+priceSum.toFixed(2));
    	var cartId=$(this).parents(".con_6").siblings(".con_1").find(".input-check").attr("cartId");
    	//修改数据库中的值
    	editCount(cartId,count2,priceSum);
    	//修改价格
    	calculation();
	});
	
	/**
	 * 修改购物车的数量
	 * */
	function editCount(cartId,count,priceSum){
		if(cartId==null || typeof(cartId)=="undefined" ||count==null || typeof(count)=="undefined" || priceSum==null || typeof(priceSum)=="undefined"){
			return false;
		}
		$.ajax({
			url:ctxm+"/trade/tradeCart/editCount.htm",
			type:'POST',
			data:{"cartId":cartId,"count":count,"priceSum":priceSum},
			dataType:'json',
			success:function(data){
				if(data==null || typeof(data)=="undefined" || data=="0"){
					fdp.msg("<i class='fa fa-meh-o' style='font-size:24px;color:red'></i>"+fy.getMsg("修改失败"),2000);
					return false;
				}
			}
		});
	}
});

